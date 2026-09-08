package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemPredicateArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class ClearCommand {
   private static final DynamicCommandExceptionType field_198249_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("clear.failed.single", var0)
   );
   private static final DynamicCommandExceptionType field_198250_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("clear.failed.multiple", var0)
   );

   public static void func_198243_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("clear")
            .requires(var0x -> var0x.func_197034_c(2))
            .executes(var0x -> func_198244_a(var0x.getSource(), Collections.singleton(var0x.getSource().func_197035_h()), var0xx -> true, -1))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .executes(var0x -> func_198244_a(var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), var0xx -> true, -1))
                  .then(
                     Commands.func_197056_a("item", ItemPredicateArgument.func_199846_a())
                        .executes(
                           var0x -> func_198244_a(
                                 var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), ItemPredicateArgument.func_199847_a(var0x, "item"), -1
                              )
                        )
                        .then(
                           Commands.func_197056_a("maxCount", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> func_198244_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       ItemPredicateArgument.func_199847_a(var0x, "item"),
                                       IntegerArgumentType.getInteger(var0x, "maxCount")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198244_a(CommandSource var0, Collection<EntityPlayerMP> var1, Predicate<ItemStack> var2, int var3) throws CommandSyntaxException {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         ☃ += ☃x.field_71071_by.func_195408_a(☃, ☃);
      }

      if (☃ == 0) {
         if (☃.size() == 1) {
            throw field_198249_a.create(((EntityPlayerMP)☃.iterator().next()).func_200200_C_().func_150254_d());
         } else {
            throw field_198250_b.create(☃.size());
         }
      } else {
         if (☃ == 0) {
            if (☃.size() == 1) {
               ☃.func_197030_a(new TextComponentTranslation("commands.clear.test.single", ☃, ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
            } else {
               ☃.func_197030_a(new TextComponentTranslation("commands.clear.test.multiple", ☃, ☃.size()), true);
            }
         } else if (☃.size() == 1) {
            ☃.func_197030_a(new TextComponentTranslation("commands.clear.success.single", ☃, ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.clear.success.multiple", ☃, ☃.size()), true);
         }

         return ☃;
      }
   }
}
