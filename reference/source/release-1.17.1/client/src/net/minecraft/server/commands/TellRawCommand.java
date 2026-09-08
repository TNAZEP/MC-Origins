package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;

public class TellRawCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("tellraw")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("targets", EntityArgument.players()).then(Commands.argument("message", ComponentArgument.textComponent()).executes(var0x -> {
                  int â˜ƒ = 0;
         
                  for(ServerPlayer â˜ƒx : EntityArgument.getPlayers(var0x, "targets")) {
                     â˜ƒx.sendMessage(
                        ComponentUtils.updateForEntity(var0x.getSource(), ComponentArgument.getComponent(var0x, "message"), â˜ƒx, 0), Util.NIL_UUID
                     );
                     ++â˜ƒ;
                  }
         
                  return â˜ƒ;
               }))
            )
      );
   }
}
