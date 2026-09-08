package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

public class WhitelistCommand {
   private static final SimpleCommandExceptionType ERROR_ALREADY_ENABLED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.whitelist.alreadyOn")
   );
   private static final SimpleCommandExceptionType ERROR_ALREADY_DISABLED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.whitelist.alreadyOff")
   );
   private static final SimpleCommandExceptionType ERROR_ALREADY_WHITELISTED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.whitelist.add.failed")
   );
   private static final SimpleCommandExceptionType ERROR_NOT_WHITELISTED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.whitelist.remove.failed")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("whitelist")
            .requires(var0x -> var0x.hasPermission(3))
            .then(Commands.literal("on").executes(var0x -> enableWhitelist(var0x.getSource())))
            .then(Commands.literal("off").executes(var0x -> disableWhitelist(var0x.getSource())))
            .then(Commands.literal("list").executes(var0x -> showList(var0x.getSource())))
            .then(
               Commands.literal("add")
                  .then(
                     Commands.argument("targets", GameProfileArgument.gameProfile())
                        .suggests(
                           (var0x, var1) -> {
                              PlayerList â˜ƒ = var0x.getSource().getServer().getPlayerList();
                              return SharedSuggestionProvider.suggest(
                                 â˜ƒ.getPlayers()
                                    .stream()
                                    .filter(var1x -> !â˜ƒ.getWhiteList().isWhiteListed(var1x.getGameProfile()))
                                    .map(var0xx -> var0xx.getGameProfile().getName()),
                                 var1
                              );
                           }
                        )
                        .executes(var0x -> addPlayers(var0x.getSource(), GameProfileArgument.getGameProfiles(var0x, "targets")))
                  )
            )
            .then(
               Commands.literal("remove")
                  .then(
                     Commands.argument("targets", GameProfileArgument.gameProfile())
                        .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(var0x.getSource().getServer().getPlayerList().getWhiteListNames(), var1))
                        .executes(var0x -> removePlayers(var0x.getSource(), GameProfileArgument.getGameProfiles(var0x, "targets")))
                  )
            )
            .then(Commands.literal("reload").executes(var0x -> reload(var0x.getSource())))
      );
   }

   private static int reload(CommandSourceStack var0) {
      â˜ƒ.getServer().getPlayerList().reloadWhiteList();
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.reloaded"), true);
      â˜ƒ.getServer().kickUnlistedPlayers(â˜ƒ);
      return 1;
   }

   private static int addPlayers(CommandSourceStack var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      UserWhiteList â˜ƒ = â˜ƒ.getServer().getPlayerList().getWhiteList();
      int â˜ƒx = 0;

      for(GameProfile â˜ƒxx : â˜ƒ) {
         if (!â˜ƒ.isWhiteListed(â˜ƒxx)) {
            UserWhiteListEntry â˜ƒxxx = new UserWhiteListEntry(â˜ƒxx);
            â˜ƒ.add(â˜ƒxxx);
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.add.success", ComponentUtils.getDisplayName(â˜ƒxx)), true);
            ++â˜ƒx;
         }
      }

      if (â˜ƒx == 0) {
         throw ERROR_ALREADY_WHITELISTED.create();
      } else {
         return â˜ƒx;
      }
   }

   private static int removePlayers(CommandSourceStack var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      UserWhiteList â˜ƒ = â˜ƒ.getServer().getPlayerList().getWhiteList();
      int â˜ƒx = 0;

      for(GameProfile â˜ƒxx : â˜ƒ) {
         if (â˜ƒ.isWhiteListed(â˜ƒxx)) {
            UserWhiteListEntry â˜ƒxxx = new UserWhiteListEntry(â˜ƒxx);
            â˜ƒ.remove(â˜ƒxxx);
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.remove.success", ComponentUtils.getDisplayName(â˜ƒxx)), true);
            ++â˜ƒx;
         }
      }

      if (â˜ƒx == 0) {
         throw ERROR_NOT_WHITELISTED.create();
      } else {
         â˜ƒ.getServer().kickUnlistedPlayers(â˜ƒ);
         return â˜ƒx;
      }
   }

   private static int enableWhitelist(CommandSourceStack var0) throws CommandSyntaxException {
      PlayerList â˜ƒ = â˜ƒ.getServer().getPlayerList();
      if (â˜ƒ.isUsingWhitelist()) {
         throw ERROR_ALREADY_ENABLED.create();
      } else {
         â˜ƒ.setUsingWhiteList(true);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.enabled"), true);
         â˜ƒ.getServer().kickUnlistedPlayers(â˜ƒ);
         return 1;
      }
   }

   private static int disableWhitelist(CommandSourceStack var0) throws CommandSyntaxException {
      PlayerList â˜ƒ = â˜ƒ.getServer().getPlayerList();
      if (!â˜ƒ.isUsingWhitelist()) {
         throw ERROR_ALREADY_DISABLED.create();
      } else {
         â˜ƒ.setUsingWhiteList(false);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.disabled"), true);
         return 1;
      }
   }

   private static int showList(CommandSourceStack var0) {
      String[] â˜ƒ = â˜ƒ.getServer().getPlayerList().getWhiteListNames();
      if (â˜ƒ.length == 0) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.none"), false);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.whitelist.list", â˜ƒ.length, String.join(", ", â˜ƒ)), false);
      }

      return â˜ƒ.length;
   }
}
