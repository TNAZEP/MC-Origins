package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EnchantmentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class EnchantCommand {
   private static final DynamicCommandExceptionType field_202652_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.enchant.failed.entity", var0)
   );
   private static final DynamicCommandExceptionType field_202653_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.enchant.failed.itemless", var0)
   );
   private static final DynamicCommandExceptionType field_202654_c = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.enchant.failed.incompatible", var0)
   );
   private static final Dynamic2CommandExceptionType field_202655_d = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.enchant.failed.level", var0, var1)
   );
   private static final SimpleCommandExceptionType field_202656_e = new SimpleCommandExceptionType(new TextComponentTranslation("commands.enchant.failed"));

   public static void func_202649_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("enchant")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                  .then(
                     Commands.func_197056_a("enchantment", EnchantmentArgument.func_201945_a())
                        .executes(
                           var0x -> func_202651_a(
                                 var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets"), EnchantmentArgument.func_201944_a(var0x, "enchantment"), 1
                              )
                        )
                        .then(
                           Commands.func_197056_a("level", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> func_202651_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197097_b(var0x, "targets"),
                                       EnchantmentArgument.func_201944_a(var0x, "enchantment"),
                                       IntegerArgumentType.getInteger(var0x, "level")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_202651_a(CommandSource var0, Collection<? extends Entity> var1, Enchantment var2, int var3) throws CommandSyntaxException {
      if (☃ > ☃.func_77325_b()) {
         throw field_202655_d.create(☃, ☃.func_77325_b());
      } else {
         int ☃ = 0;

         for(Entity ☃x : ☃) {
            if (☃x instanceof EntityLivingBase) {
               EntityLivingBase ☃xx = (EntityLivingBase)☃x;
               ItemStack ☃xxx = ☃xx.func_184614_ca();
               if (!☃xxx.func_190926_b()) {
                  if (☃.func_92089_a(☃xxx) && EnchantmentHelper.func_201840_a(EnchantmentHelper.func_82781_a(☃xxx).keySet(), ☃)) {
                     ☃xxx.func_77966_a(☃, ☃);
                     ++☃;
                  } else if (☃.size() == 1) {
                     throw field_202654_c.create(☃xxx.func_77973_b().func_200295_i(☃xxx).getString());
                  }
               } else if (☃.size() == 1) {
                  throw field_202653_b.create(☃xx.func_200200_C_().getString());
               }
            } else if (☃.size() == 1) {
               throw field_202652_a.create(☃x.func_200200_C_().getString());
            }
         }

         if (☃ == 0) {
            throw field_202656_e.create();
         } else {
            if (☃.size() == 1) {
               ☃.func_197030_a(
                  new TextComponentTranslation("commands.enchant.success.single", ☃.func_200305_d(☃), ((Entity)☃.iterator().next()).func_145748_c_()), true
               );
            } else {
               ☃.func_197030_a(new TextComponentTranslation("commands.enchant.success.multiple", ☃.func_200305_d(☃), ☃.size()), true);
            }

            return ☃;
         }
      }
   }
}
