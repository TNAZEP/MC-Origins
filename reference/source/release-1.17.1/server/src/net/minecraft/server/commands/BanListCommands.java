package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.BanListEntry;
import net.minecraft.server.players.PlayerList;

public class BanListCommands {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("banlist")
            .requires(var0x -> var0x.hasPermission(3))
            .executes(var0x -> {
               PlayerList â˜ƒ = var0x.getSource().getServer().getPlayerList();
               return showList(var0x.getSource(), Lists.newArrayList(Iterables.concat(â˜ƒ.getBans().getEntries(), â˜ƒ.getIpBans().getEntries())));
            })
            .then(
               Commands.literal("ips").executes(var0x -> showList(var0x.getSource(), var0x.getSource().getServer().getPlayerList().getIpBans().getEntries()))
            )
            .then(
               Commands.literal("players").executes(var0x -> showList(var0x.getSource(), var0x.getSource().getServer().getPlayerList().getBans().getEntries()))
            )
      );
   }

   private static int showList(CommandSourceStack var0, Collection<? extends BanListEntry<?>> var1) {
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.banlist.none"), false);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.banlist.list", â˜ƒ.size()), false);

         for(BanListEntry<?> â˜ƒ : â˜ƒ) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.banlist.entry", â˜ƒ.getDisplayName(), â˜ƒ.getSource(), â˜ƒ.getReason()), false);
         }
      }

      return â˜ƒ.size();
   }
}
