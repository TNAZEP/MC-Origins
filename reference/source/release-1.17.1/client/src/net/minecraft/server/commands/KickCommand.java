package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class KickCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("kick")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.argument("targets", EntityArgument.players())
                  .executes(
                     var0x -> kickPlayers(
                           var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), new TranslatableComponent("multiplayer.disconnect.kicked")
                        )
                  )
                  .then(
                     Commands.argument("reason", MessageArgument.message())
                        .executes(
                           var0x -> kickPlayers(var0x.getSource(), EntityArgument.getPlayers(var0x, "targets"), MessageArgument.getMessage(var0x, "reason"))
                        )
                  )
            )
      );
   }

   private static int kickPlayers(CommandSourceStack var0, Collection<ServerPlayer> var1, Component var2) {
      for(ServerPlayer â˜ƒ : â˜ƒ) {
         â˜ƒ.connection.disconnect(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.kick.success", â˜ƒ.getDisplayName(), â˜ƒ), true);
      }

      return â˜ƒ.size();
   }
}
