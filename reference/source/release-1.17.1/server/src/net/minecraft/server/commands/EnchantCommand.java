package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantCommand {
   private static final DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.enchant.failed.entity", var0)
   );
   private static final DynamicCommandExceptionType ERROR_NO_ITEM = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.enchant.failed.itemless", var0)
   );
   private static final DynamicCommandExceptionType ERROR_INCOMPATIBLE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.enchant.failed.incompatible", var0)
   );
   private static final Dynamic2CommandExceptionType ERROR_LEVEL_TOO_HIGH = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.enchant.failed.level", var0, var1)
   );
   private static final SimpleCommandExceptionType ERROR_NOTHING_HAPPENED = new SimpleCommandExceptionType(new TranslatableComponent("commands.enchant.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("enchant")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("targets", EntityArgument.entities())
                  .then(
                     Commands.argument("enchantment", ItemEnchantmentArgument.enchantment())
                        .executes(
                           var0x -> enchant(
                                 var0x.getSource(),
                                 EntityArgument.getEntities(var0x, "targets"),
                                 ItemEnchantmentArgument.getEnchantment(var0x, "enchantment"),
                                 1
                              )
                        )
                        .then(
                           Commands.argument("level", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> enchant(
                                       var0x.getSource(),
                                       EntityArgument.getEntities(var0x, "targets"),
                                       ItemEnchantmentArgument.getEnchantment(var0x, "enchantment"),
                                       IntegerArgumentType.getInteger(var0x, "level")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int enchant(CommandSourceStack var0, Collection<? extends Entity> var1, Enchantment var2, int var3) throws CommandSyntaxException {
      if (â˜ƒ > â˜ƒ.getMaxLevel()) {
         throw ERROR_LEVEL_TOO_HIGH.create(â˜ƒ, â˜ƒ.getMaxLevel());
      } else {
         int â˜ƒ = 0;

         for(Entity â˜ƒx : â˜ƒ) {
            if (â˜ƒx instanceof LivingEntity â˜ƒxx) {
               ItemStack â˜ƒxxx = â˜ƒxx.getMainHandItem();
               if (!â˜ƒxxx.isEmpty()) {
                  if (â˜ƒ.canEnchant(â˜ƒxxx) && EnchantmentHelper.isEnchantmentCompatible(EnchantmentHelper.getEnchantments(â˜ƒxxx).keySet(), â˜ƒ)) {
                     â˜ƒxxx.enchant(â˜ƒ, â˜ƒ);
                     ++â˜ƒ;
                  } else if (â˜ƒ.size() == 1) {
                     throw ERROR_INCOMPATIBLE.create(â˜ƒxxx.getItem().getName(â˜ƒxxx).getString());
                  }
               } else if (â˜ƒ.size() == 1) {
                  throw ERROR_NO_ITEM.create(â˜ƒxx.getName().getString());
               }
            } else if (â˜ƒ.size() == 1) {
               throw ERROR_NOT_LIVING_ENTITY.create(â˜ƒx.getName().getString());
            }
         }

         if (â˜ƒ == 0) {
            throw ERROR_NOTHING_HAPPENED.create();
         } else {
            if (â˜ƒ.size() == 1) {
               â˜ƒ.sendSuccess(
                  new TranslatableComponent("commands.enchant.success.single", â˜ƒ.getFullname(â˜ƒ), ((Entity)â˜ƒ.iterator().next()).getDisplayName()), true
               );
            } else {
               â˜ƒ.sendSuccess(new TranslatableComponent("commands.enchant.success.multiple", â˜ƒ.getFullname(â˜ƒ), â˜ƒ.size()), true);
            }

            return â˜ƒ;
         }
      }
   }
}
