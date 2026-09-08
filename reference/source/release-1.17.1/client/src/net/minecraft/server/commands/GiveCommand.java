package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class GiveCommand {
   public static final int MAX_ALLOWED_ITEMSTACKS = 100;

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("give")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("targets", EntityArgument.players())
                  .then(
                     Commands.argument("item", ItemArgument.item())
                        .executes(var0x -> giveItem(var0x.getSource(), ItemArgument.getItem(var0x, "item"), EntityArgument.getPlayers(var0x, "targets"), 1))
                        .then(
                           Commands.argument("count", IntegerArgumentType.integer(1))
                              .executes(
                                 var0x -> giveItem(
                                       var0x.getSource(),
                                       ItemArgument.getItem(var0x, "item"),
                                       EntityArgument.getPlayers(var0x, "targets"),
                                       IntegerArgumentType.getInteger(var0x, "count")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int giveItem(CommandSourceStack var0, ItemInput var1, Collection<ServerPlayer> var2, int var3) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getItem().getMaxStackSize();
      int â˜ƒx = â˜ƒ * 100;
      if (â˜ƒ > â˜ƒx) {
         â˜ƒ.sendFailure(new TranslatableComponent("commands.give.failed.toomanyitems", â˜ƒx, â˜ƒ.createItemStack(â˜ƒ, false).getDisplayName()));
         return 0;
      } else {
         for(ServerPlayer â˜ƒ : â˜ƒ) {
            int â˜ƒx = â˜ƒ;

            while(â˜ƒx > 0) {
               int â˜ƒxx = Math.min(â˜ƒ, â˜ƒx);
               â˜ƒx -= â˜ƒxx;
               ItemStack â˜ƒxxx = â˜ƒ.createItemStack(â˜ƒxx, false);
               boolean â˜ƒxxxx = â˜ƒ.getInventory().add(â˜ƒxxx);
               if (â˜ƒxxxx && â˜ƒxxx.isEmpty()) {
                  â˜ƒxxx.setCount(1);
                  ItemEntity â˜ƒxxxxx = â˜ƒ.drop(â˜ƒxxx, false);
                  if (â˜ƒxxxxx != null) {
                     â˜ƒxxxxx.makeFakeItem();
                  }

                  â˜ƒ.level
                     .playSound(
                        null,
                        â˜ƒ.getX(),
                        â˜ƒ.getY(),
                        â˜ƒ.getZ(),
                        SoundEvents.ITEM_PICKUP,
                        SoundSource.PLAYERS,
                        0.2F,
                        ((â˜ƒ.getRandom().nextFloat() - â˜ƒ.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                     );
                  â˜ƒ.containerMenu.broadcastChanges();
               } else {
                  ItemEntity â˜ƒxx = â˜ƒ.drop(â˜ƒxxx, false);
                  if (â˜ƒxx != null) {
                     â˜ƒxx.setNoPickUpDelay();
                     â˜ƒxx.setOwner(â˜ƒ.getUUID());
                  }
               }
            }
         }

         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(
               new TranslatableComponent(
                  "commands.give.success.single", â˜ƒ, â˜ƒ.createItemStack(â˜ƒ, false).getDisplayName(), ((ServerPlayer)â˜ƒ.iterator().next()).getDisplayName()
               ),
               true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.give.success.single", â˜ƒ, â˜ƒ.createItemStack(â˜ƒ, false).getDisplayName(), â˜ƒ.size()), true);
         }

         return â˜ƒ.size();
      }
   }
}
