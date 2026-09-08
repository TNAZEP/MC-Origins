package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class MsgCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralCommandNode<CommandSourceStack> â˜ƒ = â˜ƒ.register(
         Commands.literal("msg")
            .then(
               Commands.argument("targets", EntityArgument.players())
                  .then(
                     Commands.argument("message", MessageArgument.message())
                        .executes(
                           var0x -> sendMessage(var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), MessageArgument.getMessage(var0x, "message"))
                        )
                  )
            )
      );
      â˜ƒ.register(Commands.literal("tell").redirect(â˜ƒ));
      â˜ƒ.register(Commands.literal("w").redirect(â˜ƒ));
   }

   private static int sendMessage(CommandSourceStack var0, Collection<ServerPlayer> var1, Component var2) {
      UUID â˜ƒxx = â˜ƒ.getEntity() == null ? Util.NIL_UUID : â˜ƒ.getEntity().getUUID();
      Entity â˜ƒxxx = â˜ƒ.getEntity();
      Consumer<Component> â˜ƒx;
      if (â˜ƒxxx instanceof ServerPlayer â˜ƒ) {
         â˜ƒx = var2x -> â˜ƒ.sendMessage(
               new TranslatableComponent("commands.message.display.outgoing", var2x, â˜ƒ)
                  .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}),
               â˜ƒ.getUUID()
            );
      } else {
         â˜ƒx = var2x -> â˜ƒ.sendSuccess(
               new TranslatableComponent("commands.message.display.outgoing", var2x, â˜ƒ)
                  .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}),
               false
            );
      }

      for(ServerPlayer â˜ƒ : â˜ƒ) {
         â˜ƒx.accept(â˜ƒ.getDisplayName());
         â˜ƒ.sendMessage(
            new TranslatableComponent("commands.message.display.incoming", â˜ƒ.getDisplayName(), â˜ƒ)
               .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}),
            â˜ƒxx
         );
      }

      return â˜ƒ.size();
   }
}
