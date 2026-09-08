package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.TextFilter;
import net.minecraft.world.entity.Entity;

public class EmoteCommands {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(Commands.literal("me").then(Commands.argument("action", StringArgumentType.greedyString()).executes(var0x -> {
         String â˜ƒ = StringArgumentType.getString(var0x, "action");
         Entity â˜ƒx = var0x.getSource().getEntity();
         MinecraftServer â˜ƒxx = var0x.getSource().getServer();
         if (â˜ƒx != null) {
            if (â˜ƒx instanceof ServerPlayer â˜ƒxxx) {
               â˜ƒxxx.getTextFilter().processStreamMessage(â˜ƒ).thenAcceptAsync(var4x -> {
                  String â˜ƒ = var4x.getFiltered();
                  Component â˜ƒx = â˜ƒ.isEmpty() ? null : createMessage(var0x, â˜ƒ);
                  Component â˜ƒxx = createMessage(var0x, var4x.getRaw());
                  â˜ƒ.getPlayerList().broadcastMessage(â˜ƒxx, var3x -> â˜ƒ.shouldFilterMessageTo(var3x) ? â˜ƒ : â˜ƒ, ChatType.CHAT, â˜ƒ.getUUID());
               }, â˜ƒxx);
               return 1;
            }

            â˜ƒxx.getPlayerList().broadcastMessage(createMessage(var0x, â˜ƒ), ChatType.CHAT, â˜ƒx.getUUID());
         } else {
            â˜ƒxx.getPlayerList().broadcastMessage(createMessage(var0x, â˜ƒ), ChatType.SYSTEM, Util.NIL_UUID);
         }

         return 1;
      })));
   }

   private static Component createMessage(CommandContext<CommandSourceStack> var0, String var1) {
      return new TranslatableComponent("chat.type.emote", â˜ƒ.getSource().getDisplayName(), â˜ƒ);
   }
}
