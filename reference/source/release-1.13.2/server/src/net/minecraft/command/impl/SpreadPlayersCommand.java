package net.minecraft.command.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec2Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.WorldServer;

public class SpreadPlayersCommand {
   private static final Dynamic4CommandExceptionType field_198723_a = new Dynamic4CommandExceptionType(
      (var0, var1, var2, var3) -> new TextComponentTranslation("commands.spreadplayers.failed.teams", var0, var1, var2, var3)
   );
   private static final Dynamic4CommandExceptionType field_198724_b = new Dynamic4CommandExceptionType(
      (var0, var1, var2, var3) -> new TextComponentTranslation("commands.spreadplayers.failed.entities", var0, var1, var2, var3)
   );

   public static void func_198716_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("spreadplayers")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("center", Vec2Argument.func_197296_a())
                  .then(
                     Commands.func_197056_a("spreadDistance", FloatArgumentType.floatArg(0.0F))
                        .then(
                           Commands.func_197056_a("maxRange", FloatArgumentType.floatArg(1.0F))
                              .then(
                                 Commands.func_197056_a("respectTeams", BoolArgumentType.bool())
                                    .then(
                                       Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                                          .executes(
                                             var0x -> func_198722_a(
                                                   var0x.getSource(),
                                                   Vec2Argument.func_197295_a(var0x, "center"),
                                                   FloatArgumentType.getFloat(var0x, "spreadDistance"),
                                                   FloatArgumentType.getFloat(var0x, "maxRange"),
                                                   BoolArgumentType.getBool(var0x, "respectTeams"),
                                                   EntityArgument.func_197097_b(var0x, "targets")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198722_a(CommandSource var0, Vec2f var1, float var2, float var3, boolean var4, Collection<? extends Entity> var5) throws CommandSyntaxException {
      Random ☃ = new Random();
      double ☃x = (double)(☃.field_189982_i - ☃);
      double ☃xx = (double)(☃.field_189983_j - ☃);
      double ☃xxx = (double)(☃.field_189982_i + ☃);
      double ☃xxxx = (double)(☃.field_189983_j + ☃);
      SpreadPlayersCommand.Position[] ☃xxxxx = func_198720_a(☃, ☃ ? func_198715_a(☃) : ☃.size(), ☃x, ☃xx, ☃xxx, ☃xxxx);
      func_198717_a(☃, (double)☃, ☃.func_197023_e(), ☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, ☃);
      double ☃xxxxxx = func_198719_a(☃, ☃.func_197023_e(), ☃xxxxx, ☃);
      ☃.func_197030_a(
         new TextComponentTranslation(
            "commands.spreadplayers.success." + (☃ ? "teams" : "entities"),
            ☃xxxxx.length,
            ☃.field_189982_i,
            ☃.field_189983_j,
            String.format(Locale.ROOT, "%.2f", ☃xxxxxx)
         ),
         true
      );
      return ☃xxxxx.length;
   }

   private static int func_198715_a(Collection<? extends Entity> var0) {
      Set<Team> ☃ = Sets.<Team>newHashSet();

      for(Entity ☃x : ☃) {
         if (☃x instanceof EntityPlayer) {
            ☃.add(☃x.func_96124_cp());
         } else {
            ☃.add(null);
         }
      }

      return ☃.size();
   }

   private static void func_198717_a(
      Vec2f var0,
      double var1,
      WorldServer var3,
      Random var4,
      double var5,
      double var7,
      double var9,
      double var11,
      SpreadPlayersCommand.Position[] var13,
      boolean var14
   ) throws CommandSyntaxException {
      boolean ☃ = true;
      double ☃x = Float.MAX_VALUE;

      int ☃;
      for(☃ = 0; ☃ < 10000 && ☃; ++☃) {
         ☃ = false;
         ☃x = Float.MAX_VALUE;

         for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
            SpreadPlayersCommand.Position ☃xxx = ☃[☃xx];
            int ☃xxxx = 0;
            SpreadPlayersCommand.Position ☃xxxxx = new SpreadPlayersCommand.Position();

            for(int ☃xxxxxx = 0; ☃xxxxxx < ☃.length; ++☃xxxxxx) {
               if (☃xx != ☃xxxxxx) {
                  SpreadPlayersCommand.Position ☃xxxxxxx = ☃[☃xxxxxx];
                  double ☃xxxxxxxx = ☃xxx.func_198708_a(☃xxxxxxx);
                  ☃x = Math.min(☃xxxxxxxx, ☃x);
                  if (☃xxxxxxxx < ☃) {
                     ++☃xxxx;
                     ☃xxxxx.field_198713_a = ☃xxxxx.field_198713_a + (☃xxxxxxx.field_198713_a - ☃xxx.field_198713_a);
                     ☃xxxxx.field_198714_b = ☃xxxxx.field_198714_b + (☃xxxxxxx.field_198714_b - ☃xxx.field_198714_b);
                  }
               }
            }

            if (☃xxxx > 0) {
               ☃xxxxx.field_198713_a = ☃xxxxx.field_198713_a / (double)☃xxxx;
               ☃xxxxx.field_198714_b = ☃xxxxx.field_198714_b / (double)☃xxxx;
               double ☃xxxxxx = (double)☃xxxxx.func_198712_b();
               if (☃xxxxxx > 0.0) {
                  ☃xxxxx.func_198707_a();
                  ☃xxx.func_198705_b(☃xxxxx);
               } else {
                  ☃xxx.func_198711_a(☃, ☃, ☃, ☃, ☃);
               }

               ☃ = true;
            }

            if (☃xxx.func_198709_a(☃, ☃, ☃, ☃)) {
               ☃ = true;
            }
         }

         if (!☃) {
            for(SpreadPlayersCommand.Position ☃xx : ☃) {
               if (!☃xx.func_198706_b(☃)) {
                  ☃xx.func_198711_a(☃, ☃, ☃, ☃, ☃);
                  ☃ = true;
               }
            }
         }
      }

      if (☃x == Float.MAX_VALUE) {
         ☃x = 0.0;
      }

      if (☃ >= 10000) {
         if (☃) {
            throw field_198723_a.create(☃.length, ☃.field_189982_i, ☃.field_189983_j, String.format(Locale.ROOT, "%.2f", ☃x));
         } else {
            throw field_198724_b.create(☃.length, ☃.field_189982_i, ☃.field_189983_j, String.format(Locale.ROOT, "%.2f", ☃x));
         }
      }
   }

   private static double func_198719_a(Collection<? extends Entity> var0, WorldServer var1, SpreadPlayersCommand.Position[] var2, boolean var3) {
      double ☃ = 0.0;
      int ☃x = 0;
      Map<Team, SpreadPlayersCommand.Position> ☃xx = Maps.<Team, SpreadPlayersCommand.Position>newHashMap();

      for(Entity ☃xxx : ☃) {
         SpreadPlayersCommand.Position ☃xxxx;
         if (☃) {
            Team ☃xxxxx = ☃xxx instanceof EntityPlayer ? ☃xxx.func_96124_cp() : null;
            if (!☃xx.containsKey(☃xxxxx)) {
               ☃xx.put(☃xxxxx, ☃[☃x++]);
            }

            ☃xxxx = (SpreadPlayersCommand.Position)☃xx.get(☃xxxxx);
         } else {
            ☃xxxx = ☃[☃x++];
         }

         ☃xxx.func_70634_a(
            (double)((float)MathHelper.func_76128_c(☃xxxx.field_198713_a) + 0.5F),
            (double)☃xxxx.func_198710_a(☃),
            (double)MathHelper.func_76128_c(☃xxxx.field_198714_b) + 0.5
         );
         double ☃xxxx = Double.MAX_VALUE;

         for(SpreadPlayersCommand.Position ☃xxxxx : ☃) {
            if (☃xxxx != ☃xxxxx) {
               double ☃xxxxxx = ☃xxxx.func_198708_a(☃xxxxx);
               ☃xxxx = Math.min(☃xxxxxx, ☃xxxx);
            }
         }

         ☃ += ☃xxxx;
      }

      return ☃.size() < 2 ? 0.0 : ☃ / (double)☃.size();
   }

   private static SpreadPlayersCommand.Position[] func_198720_a(Random var0, int var1, double var2, double var4, double var6, double var8) {
      SpreadPlayersCommand.Position[] ☃ = new SpreadPlayersCommand.Position[☃];

      for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
         SpreadPlayersCommand.Position ☃xx = new SpreadPlayersCommand.Position();
         ☃xx.func_198711_a(☃, ☃, ☃, ☃, ☃);
         ☃[☃x] = ☃xx;
      }

      return ☃;
   }

   static class Position {
      private double field_198713_a;
      private double field_198714_b;

      double func_198708_a(SpreadPlayersCommand.Position var1) {
         double ☃ = this.field_198713_a - ☃.field_198713_a;
         double ☃x = this.field_198714_b - ☃.field_198714_b;
         return Math.sqrt(☃ * ☃ + ☃x * ☃x);
      }

      void func_198707_a() {
         double ☃ = (double)this.func_198712_b();
         this.field_198713_a /= ☃;
         this.field_198714_b /= ☃;
      }

      float func_198712_b() {
         return MathHelper.func_76133_a(this.field_198713_a * this.field_198713_a + this.field_198714_b * this.field_198714_b);
      }

      public void func_198705_b(SpreadPlayersCommand.Position var1) {
         this.field_198713_a -= ☃.field_198713_a;
         this.field_198714_b -= ☃.field_198714_b;
      }

      public boolean func_198709_a(double var1, double var3, double var5, double var7) {
         boolean ☃ = false;
         if (this.field_198713_a < ☃) {
            this.field_198713_a = ☃;
            ☃ = true;
         } else if (this.field_198713_a > ☃) {
            this.field_198713_a = ☃;
            ☃ = true;
         }

         if (this.field_198714_b < ☃) {
            this.field_198714_b = ☃;
            ☃ = true;
         } else if (this.field_198714_b > ☃) {
            this.field_198714_b = ☃;
            ☃ = true;
         }

         return ☃;
      }

      public int func_198710_a(IBlockReader var1) {
         BlockPos ☃ = new BlockPos(this.field_198713_a, 256.0, this.field_198714_b);

         while(☃.func_177956_o() > 0) {
            ☃ = ☃.func_177977_b();
            if (!☃.func_180495_p(☃).func_196958_f()) {
               return ☃.func_177956_o() + 1;
            }
         }

         return 257;
      }

      public boolean func_198706_b(IBlockReader var1) {
         BlockPos ☃ = new BlockPos(this.field_198713_a, 256.0, this.field_198714_b);

         while(☃.func_177956_o() > 0) {
            ☃ = ☃.func_177977_b();
            IBlockState ☃x = ☃.func_180495_p(☃);
            if (!☃x.func_196958_f()) {
               Material ☃xx = ☃x.func_185904_a();
               return !☃xx.func_76224_d() && ☃xx != Material.field_151581_o;
            }
         }

         return false;
      }

      public void func_198711_a(Random var1, double var2, double var4, double var6, double var8) {
         this.field_198713_a = MathHelper.func_82716_a(☃, ☃, ☃);
         this.field_198714_b = MathHelper.func_82716_a(☃, ☃, ☃);
      }
   }
}
