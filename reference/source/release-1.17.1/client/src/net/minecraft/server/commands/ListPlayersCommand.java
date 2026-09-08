package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

public class ListPlayersCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("list")
            .executes(var0x -> listPlayers(var0x.getSource()))
            .then(Commands.literal("uuids").executes(var0x -> listPlayersWithUuids(var0x.getSource())))
      );
   }

   private static int listPlayers(CommandSourceStack var0) {
      return format(â˜ƒ, Player::getDisplayName);
   }

   private static int listPlayersWithUuids(CommandSourceStack var0) {
      return format(â˜ƒ, var0x -> new TranslatableComponent("commands.list.nameAndId", var0x.getName(), var0x.getGameProfile().getId()));
   }

   private static int format(CommandSourceStack var0, Function<ServerPlayer, Component> var1) {
      PlayerList â˜ƒ = â˜ƒ.getServer().getPlayerList();
      List<ServerPlayer> â˜ƒx = â˜ƒ.getPlayers();
      Component â˜ƒxx = ComponentUtils.formatList(â˜ƒx, â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.list.players", â˜ƒx.size(), â˜ƒ.getMaxPlayers(), â˜ƒxx), false);
      return â˜ƒx.size();
   }
}
