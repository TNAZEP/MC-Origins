package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ExperienceCommand {
   private static final SimpleCommandExceptionType ERROR_SET_POINTS_INVALID = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.experience.set.points.invalid")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralCommandNode<CommandSourceStack> â˜ƒ = â˜ƒ.register(
         Commands.literal("experience")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("add")
                  .then(
                     Commands.argument("targets", EntityArgument.players())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer())
                                    .executes(
                                       var0x -> addExperience(
                                             (CommandSourceStack)var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.POINTS
                                          )
                                    ))
                                 .then(
                                    Commands.literal("points")
                                       .executes(
                                          var0x -> addExperience(
                                                var0x.getSource(),
                                                EntityArgument.getPlayers(var0x, "targets"),
                                                IntegerArgumentType.getInteger(var0x, "amount"),
                                                ExperienceCommand.Type.POINTS
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("levels")
                                    .executes(
                                       var0x -> addExperience(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.LEVELS
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("set")
                  .then(
                     Commands.argument("targets", EntityArgument.players())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer(0))
                                    .executes(
                                       var0x -> setExperience(
                                             (CommandSourceStack)var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.POINTS
                                          )
                                    ))
                                 .then(
                                    Commands.literal("points")
                                       .executes(
                                          var0x -> setExperience(
                                                var0x.getSource(),
                                                EntityArgument.getPlayers(var0x, "targets"),
                                                IntegerArgumentType.getInteger(var0x, "amount"),
                                                ExperienceCommand.Type.POINTS
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("levels")
                                    .executes(
                                       var0x -> setExperience(
                                             var0x.getSource(),
                                             EntityArgument.getPlayers(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.LEVELS
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("query")
                  .then(
                     Commands.argument("targets", EntityArgument.player())
                        .then(
                           Commands.literal("points")
                              .executes(var0x -> queryExperience(var0x.getSource(), EntityArgument.getPlayer(var0x, "targets"), ExperienceCommand.Type.POINTS))
                        )
                        .then(
                           Commands.literal("levels")
                              .executes(var0x -> queryExperience(var0x.getSource(), EntityArgument.getPlayer(var0x, "targets"), ExperienceCommand.Type.LEVELS))
                        )
                  )
            )
      );
      â˜ƒ.register(Commands.literal("xp").requires(var0x -> var0x.hasPermission(2)).redirect(â˜ƒ));
   }

   private static int queryExperience(CommandSourceStack var0, ServerPlayer var1, ExperienceCommand.Type var2) {
      int â˜ƒ = â˜ƒ.query.applyAsInt(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.experience.query." + â˜ƒ.name, â˜ƒ.getDisplayName(), â˜ƒ), false);
      return â˜ƒ;
   }

   private static int addExperience(CommandSourceStack var0, Collection<? extends ServerPlayer> var1, int var2, ExperienceCommand.Type var3) {
      for(ServerPlayer â˜ƒ : â˜ƒ) {
         â˜ƒ.add.accept(â˜ƒ, â˜ƒ);
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.experience.add." + â˜ƒ.name + ".success.single", â˜ƒ, ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()),
            true
         );
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.experience.add." + â˜ƒ.name + ".success.multiple", â˜ƒ, â˜ƒ.size()), true);
      }

      return â˜ƒ.size();
   }

   private static int setExperience(CommandSourceStack var0, Collection<? extends ServerPlayer> var1, int var2, ExperienceCommand.Type var3) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         if (â˜ƒ.set.test(â˜ƒx, â˜ƒ)) {
            ++â˜ƒ;
         }
      }

      if (â˜ƒ == 0) {
         throw ERROR_SET_POINTS_INVALID.create();
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(
               new TranslatableComponent("commands.experience.set." + â˜ƒ.name + ".success.single", â˜ƒ, ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()),
               true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.experience.set." + â˜ƒ.name + ".success.multiple", â˜ƒ, â˜ƒ.size()), true);
         }

         return â˜ƒ.size();
      }
   }

   static enum Type {
      POINTS("points", Player::giveExperiencePoints, (var0, var1) -> {
         if (var1 >= var0.getXpNeededForNextLevel()) {
            return false;
         } else {
            var0.setExperiencePoints(var1);
            return true;
         }
      }, var0 -> Mth.floor(var0.experienceProgress * (float)var0.getXpNeededForNextLevel())),
      LEVELS("levels", ServerPlayer::giveExperienceLevels, (var0, var1) -> {
         var0.setExperienceLevels(var1);
         return true;
      }, var0 -> var0.experienceLevel);

      public final BiConsumer<ServerPlayer, Integer> add;
      public final BiPredicate<ServerPlayer, Integer> set;
      public final String name;
      final ToIntFunction<ServerPlayer> query;

      private Type(String var3, BiConsumer<ServerPlayer, Integer> var4, BiPredicate<ServerPlayer, Integer> var5, ToIntFunction<ServerPlayer> var6) {
         this.add = â˜ƒ;
         this.name = â˜ƒ;
         this.set = â˜ƒ;
         this.query = â˜ƒ;
      }
   }
}
