package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.TextComponentTranslation;

public class RecipeCommand {
   private static final SimpleCommandExceptionType field_198595_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.recipe.give.failed"));
   private static final SimpleCommandExceptionType field_198596_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.recipe.take.failed"));

   public static void func_198589_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("recipe")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("give")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                        .then(
                           Commands.func_197056_a("recipe", ResourceLocationArgument.func_197197_a())
                              .suggests(SuggestionProviders.field_197503_b)
                              .executes(
                                 var0x -> func_198594_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       Collections.singleton(ResourceLocationArgument.func_197194_b(var0x, "recipe"))
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("*")
                              .executes(
                                 var0x -> func_198594_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       var0x.getSource().func_197028_i().func_199529_aN().func_199510_b()
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("take")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                        .then(
                           Commands.func_197056_a("recipe", ResourceLocationArgument.func_197197_a())
                              .suggests(SuggestionProviders.field_197503_b)
                              .executes(
                                 var0x -> func_198590_b(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       Collections.singleton(ResourceLocationArgument.func_197194_b(var0x, "recipe"))
                                    )
                              )
                        )
                        .then(
                           Commands.func_197057_a("*")
                              .executes(
                                 var0x -> func_198590_b(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       var0x.getSource().func_197028_i().func_199529_aN().func_199510_b()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198594_a(CommandSource var0, Collection<EntityPlayerMP> var1, Collection<IRecipe> var2) throws CommandSyntaxException {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         ☃ += ☃x.func_195065_a(☃);
      }

      if (☃ == 0) {
         throw field_198595_a.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.recipe.give.success.single", ☃.size(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.recipe.give.success.multiple", ☃.size(), ☃.size()), true);
         }

         return ☃;
      }
   }

   private static int func_198590_b(CommandSource var0, Collection<EntityPlayerMP> var1, Collection<IRecipe> var2) throws CommandSyntaxException {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         ☃ += ☃x.func_195069_b(☃);
      }

      if (☃ == 0) {
         throw field_198596_b.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.recipe.take.success.single", ☃.size(), ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.recipe.take.success.multiple", ☃.size(), ☃.size()), true);
         }

         return ☃;
      }
   }
}
