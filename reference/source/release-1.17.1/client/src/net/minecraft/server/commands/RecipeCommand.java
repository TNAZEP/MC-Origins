package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeCommand {
   private static final SimpleCommandExceptionType ERROR_GIVE_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.recipe.give.failed"));
   private static final SimpleCommandExceptionType ERROR_TAKE_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.recipe.take.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("recipe")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("give")
                  .then(
                     Commands.argument("targets", EntityArgument.players())
                        .then(
                           Commands.argument("recipe", ResourceLocationArgument.id())
                              .suggests(SuggestionProviders.ALL_RECIPES)
                              .executes(
                                 var0x -> giveRecipes(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       Collections.singleton(ResourceLocationArgument.getRecipe(var0x, "recipe"))
                                    )
                              )
                        )
                        .then(
                           Commands.literal("*")
                              .executes(
                                 var0x -> giveRecipes(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       var0x.getSource().getServer().getRecipeManager().getRecipes()
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("take")
                  .then(
                     Commands.argument("targets", EntityArgument.players())
                        .then(
                           Commands.argument("recipe", ResourceLocationArgument.id())
                              .suggests(SuggestionProviders.ALL_RECIPES)
                              .executes(
                                 var0x -> takeRecipes(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       Collections.singleton(ResourceLocationArgument.getRecipe(var0x, "recipe"))
                                    )
                              )
                        )
                        .then(
                           Commands.literal("*")
                              .executes(
                                 var0x -> takeRecipes(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       var0x.getSource().getServer().getRecipeManager().getRecipes()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int giveRecipes(CommandSourceStack var0, Collection<ServerPlayer> var1, Collection<Recipe<?>> var2) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒx.awardRecipes(â˜ƒ);
      }

      if (â˜ƒ == 0) {
         throw ERROR_GIVE_FAILED.create();
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(
               new TranslatableComponent("commands.recipe.give.success.single", â˜ƒ.size(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()), true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.recipe.give.success.multiple", â˜ƒ.size(), â˜ƒ.size()), true);
         }

         return â˜ƒ;
      }
   }

   private static int takeRecipes(CommandSourceStack var0, Collection<ServerPlayer> var1, Collection<Recipe<?>> var2) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒx.resetRecipes(â˜ƒ);
      }

      if (â˜ƒ == 0) {
         throw ERROR_TAKE_FAILED.create();
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(
               new TranslatableComponent("commands.recipe.take.success.single", â˜ƒ.size(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()), true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.recipe.take.success.multiple", â˜ƒ.size(), â˜ƒ.size()), true);
         }

         return â˜ƒ;
      }
   }
}
