package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ClearInventoryCommands {
   private static final DynamicCommandExceptionType ERROR_SINGLE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("clear.failed.single", var0)
   );
   private static final DynamicCommandExceptionType ERROR_MULTIPLE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("clear.failed.multiple", var0)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("clear")
            .requires(var0x -> var0x.hasPermission(2))
            .executes(var0x -> clearInventory(var0x.getSource(), Collections.singleton(var0x.getSource().getPlayerOrException()), var0xx -> true, -1))
            .then(
               Commands.argument("targets", EntityArgument.players())
                  .executes(var0x -> clearInventory(var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), var0xx -> true, -1))
                  .then(
                     Commands.argument("item", ItemPredicateArgument.itemPredicate())
                        .executes(
                           var0x -> clearInventory(
                                 var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), ItemPredicateArgument.getItemPredicate(var0x, "item"), -1
                              )
                        )
                        .then(
                           Commands.argument("maxCount", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> clearInventory(
                                       var0x.getSource(),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       ItemPredicateArgument.getItemPredicate(var0x, "item"),
                                       IntegerArgumentType.getInteger(var0x, "maxCount")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int clearInventory(CommandSourceStack var0, Collection<ServerPlayer> var1, Predicate<ItemStack> var2, int var3) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒx.getInventory().clearOrCountMatchingItems(â˜ƒ, â˜ƒ, â˜ƒx.inventoryMenu.getCraftSlots());
         â˜ƒx.containerMenu.broadcastChanges();
         â˜ƒx.inventoryMenu.slotsChanged(â˜ƒx.getInventory());
      }

      if (â˜ƒ == 0) {
         if (â˜ƒ.size() == 1) {
            throw ERROR_SINGLE.create(((ServerPlayer)â˜ƒ.iterator().next()).getName());
         } else {
            throw ERROR_MULTIPLE.create(â˜ƒ.size());
         }
      } else {
         if (â˜ƒ == 0) {
            if (â˜ƒ.size() == 1) {
               â˜ƒ.sendSuccess(new TranslatableComponent("commands.clear.test.single", â˜ƒ, ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()), true);
            } else {
               â˜ƒ.sendSuccess(new TranslatableComponent("commands.clear.test.multiple", â˜ƒ, â˜ƒ.size()), true);
            }
         } else if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.clear.success.single", â˜ƒ, ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()), true);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.clear.success.multiple", â˜ƒ, â˜ƒ.size()), true);
         }

         return â˜ƒ;
      }
   }
}
