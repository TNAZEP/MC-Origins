package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;

public class BanPlayerCommands {
   private static final SimpleCommandExceptionType ERROR_ALREADY_BANNED = new SimpleCommandExceptionType(new TranslatableComponent("commands.ban.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("ban")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.argument("targets", GameProfileArgument.gameProfile())
                  .executes(var0x -> banPlayers(var0x.getSource(), GameProfileArgument.getGameProfiles(var0x, "targets"), null))
                  .then(
                     Commands.argument("reason", MessageArgument.message())
                        .executes(
                           var0x -> banPlayers(
                                 var0x.getSource(), GameProfileArgument.getGameProfiles(var0x, "targets"), MessageArgument.getMessage(var0x, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int banPlayers(CommandSourceStack var0, Collection<GameProfile> var1, @Nullable Component var2) throws CommandSyntaxException {
      UserBanList â˜ƒ = â˜ƒ.getServer().getPlayerList().getBans();
      int â˜ƒx = 0;

      for(GameProfile â˜ƒxx : â˜ƒ) {
         if (!â˜ƒ.isBanned(â˜ƒxx)) {
            UserBanListEntry â˜ƒxxx = new UserBanListEntry(â˜ƒxx, null, â˜ƒ.getTextName(), null, â˜ƒ == null ? null : â˜ƒ.getString());
            â˜ƒ.add(â˜ƒxxx);
            ++â˜ƒx;
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(â˜ƒxx), â˜ƒxxx.getReason()), true);
            ServerPlayer â˜ƒxxxx = â˜ƒ.getServer().getPlayerList().getPlayer(â˜ƒxx.getId());
            if (â˜ƒxxxx != null) {
               â˜ƒxxxx.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
            }
         }
      }

      if (â˜ƒx == 0) {
         throw ERROR_ALREADY_BANNED.create();
      } else {
         return â˜ƒx;
      }
   }
}
