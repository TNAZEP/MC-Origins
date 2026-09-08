package net.minecraft.server.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.scores.Team;

public class SpreadPlayersCommand {
   private static final int MAX_ITERATION_COUNT = 10000;
   private static final Dynamic4CommandExceptionType ERROR_FAILED_TO_SPREAD_TEAMS = new Dynamic4CommandExceptionType(
      (var0, var1, var2, var3) -> new TranslatableComponent("commands.spreadplayers.failed.teams", var0, var1, var2, var3)
   );
   private static final Dynamic4CommandExceptionType ERROR_FAILED_TO_SPREAD_ENTITIES = new Dynamic4CommandExceptionType(
      (var0, var1, var2, var3) -> new TranslatableComponent("commands.spreadplayers.failed.entities", var0, var1, var2, var3)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("spreadplayers")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("center", Vec2Argument.vec2())
                  .then(
                     Commands.argument("spreadDistance", FloatArgumentType.floatArg(0.0F))
                        .then(
                           ((RequiredArgumentBuilder)Commands.argument("maxRange", FloatArgumentType.floatArg(1.0F))
                                 .then(
                                    Commands.argument("respectTeams", BoolArgumentType.bool())
                                       .then(
                                          Commands.argument("targets", EntityArgument.entities())
                                             .executes(
                                                var0x -> spreadPlayers(
                                                      var0x.getSource(),
                                                      Vec2Argument.getVec2(var0x, "center"),
                                                      FloatArgumentType.getFloat(var0x, "spreadDistance"),
                                                      FloatArgumentType.getFloat(var0x, "maxRange"),
                                                      var0x.getSource().getLevel().getMaxBuildHeight(),
                                                      BoolArgumentType.getBool(var0x, "respectTeams"),
                                                      EntityArgument.getEntities(var0x, "targets")
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("under")
                                    .then(
                                       Commands.argument("maxHeight", IntegerArgumentType.integer(0))
                                          .then(
                                             Commands.argument("respectTeams", BoolArgumentType.bool())
                                                .then(
                                                   Commands.argument("targets", EntityArgument.entities())
                                                      .executes(
                                                         var0x -> spreadPlayers(
                                                               var0x.getSource(),
                                                               Vec2Argument.getVec2(var0x, "center"),
                                                               FloatArgumentType.getFloat(var0x, "spreadDistance"),
                                                               FloatArgumentType.getFloat(var0x, "maxRange"),
                                                               IntegerArgumentType.getInteger(var0x, "maxHeight"),
                                                               BoolArgumentType.getBool(var0x, "respectTeams"),
                                                               EntityArgument.getEntities(var0x, "targets")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int spreadPlayers(CommandSourceStack var0, Vec2 var1, float var2, float var3, int var4, boolean var5, Collection<? extends Entity> var6) throws CommandSyntaxException {
      Random â˜ƒ = new Random();
      double â˜ƒx = (double)(â˜ƒ.x - â˜ƒ);
      double â˜ƒxx = (double)(â˜ƒ.y - â˜ƒ);
      double â˜ƒxxx = (double)(â˜ƒ.x + â˜ƒ);
      double â˜ƒxxxx = (double)(â˜ƒ.y + â˜ƒ);
      SpreadPlayersCommand.Position[] â˜ƒxxxxx = createInitialPositions(â˜ƒ, â˜ƒ ? getNumberOfTeams(â˜ƒ) : â˜ƒ.size(), â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      spreadPositions(â˜ƒ, (double)â˜ƒ, â˜ƒ.getLevel(), â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒxxxxx, â˜ƒ);
      double â˜ƒxxxxxx = setPlayerPositions(â˜ƒ, â˜ƒ.getLevel(), â˜ƒxxxxx, â˜ƒ, â˜ƒ);
      â˜ƒ.sendSuccess(
         new TranslatableComponent(
            "commands.spreadplayers.success." + (â˜ƒ ? "teams" : "entities"), â˜ƒxxxxx.length, â˜ƒ.x, â˜ƒ.y, String.format(Locale.ROOT, "%.2f", â˜ƒxxxxxx)
         ),
         true
      );
      return â˜ƒxxxxx.length;
   }

   private static int getNumberOfTeams(Collection<? extends Entity> var0) {
      Set<Team> â˜ƒ = Sets.<Team>newHashSet();

      for(Entity â˜ƒx : â˜ƒ) {
         if (â˜ƒx instanceof Player) {
            â˜ƒ.add(â˜ƒx.getTeam());
         } else {
            â˜ƒ.add(null);
         }
      }

      return â˜ƒ.size();
   }

   private static void spreadPositions(
      Vec2 var0,
      double var1,
      ServerLevel var3,
      Random var4,
      double var5,
      double var7,
      double var9,
      double var11,
      int var13,
      SpreadPlayersCommand.Position[] var14,
      boolean var15
   ) throws CommandSyntaxException {
      boolean â˜ƒ = true;
      double â˜ƒx = Float.MAX_VALUE;

      int â˜ƒ;
      for(â˜ƒ = 0; â˜ƒ < 10000 && â˜ƒ; ++â˜ƒ) {
         â˜ƒ = false;
         â˜ƒx = Float.MAX_VALUE;

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length; ++â˜ƒxx) {
            SpreadPlayersCommand.Position â˜ƒxxx = â˜ƒ[â˜ƒxx];
            int â˜ƒxxxx = 0;
            SpreadPlayersCommand.Position â˜ƒxxxxx = new SpreadPlayersCommand.Position();

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒ.length; ++â˜ƒxxxxxx) {
               if (â˜ƒxx != â˜ƒxxxxxx) {
                  SpreadPlayersCommand.Position â˜ƒxxxxxxx = â˜ƒ[â˜ƒxxxxxx];
                  double â˜ƒxxxxxxxx = â˜ƒxxx.dist(â˜ƒxxxxxxx);
                  â˜ƒx = Math.min(â˜ƒxxxxxxxx, â˜ƒx);
                  if (â˜ƒxxxxxxxx < â˜ƒ) {
                     ++â˜ƒxxxx;
                     â˜ƒxxxxx.x += â˜ƒxxxxxxx.x - â˜ƒxxx.x;
                     â˜ƒxxxxx.z += â˜ƒxxxxxxx.z - â˜ƒxxx.z;
                  }
               }
            }

            if (â˜ƒxxxx > 0) {
               â˜ƒxxxxx.x /= (double)â˜ƒxxxx;
               â˜ƒxxxxx.z /= (double)â˜ƒxxxx;
               double â˜ƒxxxxxx = â˜ƒxxxxx.getLength();
               if (â˜ƒxxxxxx > 0.0) {
                  â˜ƒxxxxx.normalize();
                  â˜ƒxxx.moveAway(â˜ƒxxxxx);
               } else {
                  â˜ƒxxx.randomize(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               }

               â˜ƒ = true;
            }

            if (â˜ƒxxx.clamp(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
               â˜ƒ = true;
            }
         }

         if (!â˜ƒ) {
            for(SpreadPlayersCommand.Position â˜ƒxx : â˜ƒ) {
               if (!â˜ƒxx.isSafe(â˜ƒ, â˜ƒ)) {
                  â˜ƒxx.randomize(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
                  â˜ƒ = true;
               }
            }
         }
      }

      if (â˜ƒx == Float.MAX_VALUE) {
         â˜ƒx = 0.0;
      }

      if (â˜ƒ >= 10000) {
         if (â˜ƒ) {
            throw ERROR_FAILED_TO_SPREAD_TEAMS.create(â˜ƒ.length, â˜ƒ.x, â˜ƒ.y, String.format(Locale.ROOT, "%.2f", â˜ƒx));
         } else {
            throw ERROR_FAILED_TO_SPREAD_ENTITIES.create(â˜ƒ.length, â˜ƒ.x, â˜ƒ.y, String.format(Locale.ROOT, "%.2f", â˜ƒx));
         }
      }
   }

   private static double setPlayerPositions(Collection<? extends Entity> var0, ServerLevel var1, SpreadPlayersCommand.Position[] var2, int var3, boolean var4) {
      double â˜ƒ = 0.0;
      int â˜ƒx = 0;
      Map<Team, SpreadPlayersCommand.Position> â˜ƒxx = Maps.<Team, SpreadPlayersCommand.Position>newHashMap();

      for(Entity â˜ƒxxx : â˜ƒ) {
         SpreadPlayersCommand.Position â˜ƒxxxx;
         if (â˜ƒ) {
            Team â˜ƒxxxxx = â˜ƒxxx instanceof Player ? â˜ƒxxx.getTeam() : null;
            if (!â˜ƒxx.containsKey(â˜ƒxxxxx)) {
               â˜ƒxx.put(â˜ƒxxxxx, â˜ƒ[â˜ƒx++]);
            }

            â˜ƒxxxx = (SpreadPlayersCommand.Position)â˜ƒxx.get(â˜ƒxxxxx);
         } else {
            â˜ƒxxxx = â˜ƒ[â˜ƒx++];
         }

         â˜ƒxxx.teleportToWithTicket((double)Mth.floor(â˜ƒxxxx.x) + 0.5, (double)â˜ƒxxxx.getSpawnY(â˜ƒ, â˜ƒ), (double)Mth.floor(â˜ƒxxxx.z) + 0.5);
         double â˜ƒxxxx = Double.MAX_VALUE;

         for(SpreadPlayersCommand.Position â˜ƒxxxxx : â˜ƒ) {
            if (â˜ƒxxxx != â˜ƒxxxxx) {
               double â˜ƒxxxxxx = â˜ƒxxxx.dist(â˜ƒxxxxx);
               â˜ƒxxxx = Math.min(â˜ƒxxxxxx, â˜ƒxxxx);
            }
         }

         â˜ƒ += â˜ƒxxxx;
      }

      return â˜ƒ.size() < 2 ? 0.0 : â˜ƒ / (double)â˜ƒ.size();
   }

   private static SpreadPlayersCommand.Position[] createInitialPositions(Random var0, int var1, double var2, double var4, double var6, double var8) {
      SpreadPlayersCommand.Position[] â˜ƒ = new SpreadPlayersCommand.Position[â˜ƒ];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         SpreadPlayersCommand.Position â˜ƒxx = new SpreadPlayersCommand.Position();
         â˜ƒxx.randomize(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ[â˜ƒx] = â˜ƒxx;
      }

      return â˜ƒ;
   }

   static class Position {
      double x;
      double z;

      double dist(SpreadPlayersCommand.Position var1) {
         double â˜ƒ = this.x - â˜ƒ.x;
         double â˜ƒx = this.z - â˜ƒ.z;
         return Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx);
      }

      void normalize() {
         double â˜ƒ = this.getLength();
         this.x /= â˜ƒ;
         this.z /= â˜ƒ;
      }

      double getLength() {
         return Math.sqrt(this.x * this.x + this.z * this.z);
      }

      public void moveAway(SpreadPlayersCommand.Position var1) {
         this.x -= â˜ƒ.x;
         this.z -= â˜ƒ.z;
      }

      public boolean clamp(double var1, double var3, double var5, double var7) {
         boolean â˜ƒ = false;
         if (this.x < â˜ƒ) {
            this.x = â˜ƒ;
            â˜ƒ = true;
         } else if (this.x > â˜ƒ) {
            this.x = â˜ƒ;
            â˜ƒ = true;
         }

         if (this.z < â˜ƒ) {
            this.z = â˜ƒ;
            â˜ƒ = true;
         } else if (this.z > â˜ƒ) {
            this.z = â˜ƒ;
            â˜ƒ = true;
         }

         return â˜ƒ;
      }

      public int getSpawnY(BlockGetter var1, int var2) {
         BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos(this.x, (double)(â˜ƒ + 1), this.z);
         boolean â˜ƒx = â˜ƒ.getBlockState(â˜ƒ).isAir();
         â˜ƒ.move(Direction.DOWN);

         boolean â˜ƒ;
         for(boolean â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ).isAir(); â˜ƒ.getY() > â˜ƒ.getMinBuildHeight(); â˜ƒxx = â˜ƒ) {
            â˜ƒ.move(Direction.DOWN);
            â˜ƒ = â˜ƒ.getBlockState(â˜ƒ).isAir();
            if (!â˜ƒ && â˜ƒxx && â˜ƒx) {
               return â˜ƒ.getY() + 1;
            }

            â˜ƒx = â˜ƒxx;
         }

         return â˜ƒ + 1;
      }

      public boolean isSafe(BlockGetter var1, int var2) {
         BlockPos â˜ƒ = new BlockPos(this.x, (double)(this.getSpawnY(â˜ƒ, â˜ƒ) - 1), this.z);
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         Material â˜ƒxx = â˜ƒx.getMaterial();
         return â˜ƒ.getY() < â˜ƒ && !â˜ƒxx.isLiquid() && â˜ƒxx != Material.FIRE;
      }

      public void randomize(Random var1, double var2, double var4, double var6, double var8) {
         this.x = Mth.nextDouble(â˜ƒ, â˜ƒ, â˜ƒ);
         this.z = Mth.nextDouble(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
