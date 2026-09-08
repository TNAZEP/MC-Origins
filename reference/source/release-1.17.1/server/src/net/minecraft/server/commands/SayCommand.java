package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class SayCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("say").requires(var0x -> var0x.hasPermission(2)).then(Commands.argument("message", MessageArgument.message()).executes(var0x -> {
            Component â˜ƒ = MessageArgument.getMessage(var0x, "message");
            Component â˜ƒx = new TranslatableComponent("chat.type.announcement", var0x.getSource().getDisplayName(), â˜ƒ);
            Entity â˜ƒxx = var0x.getSource().getEntity();
            if (â˜ƒxx != null) {
               var0x.getSource().getServer().getPlayerList().broadcastMessage(â˜ƒx, ChatType.CHAT, â˜ƒxx.getUUID());
            } else {
               var0x.getSource().getServer().getPlayerList().broadcastMessage(â˜ƒx, ChatType.SYSTEM, Util.NIL_UUID);
            }
   
            return 1;
         }))
      );
   }
}
