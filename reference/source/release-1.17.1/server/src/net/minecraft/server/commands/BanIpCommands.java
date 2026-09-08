package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.IpBanList;
import net.minecraft.server.players.IpBanListEntry;

public class BanIpCommands {
   public static final Pattern IP_ADDRESS_PATTERN = Pattern.compile(
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
   );
   private static final SimpleCommandExceptionType ERROR_INVALID_IP = new SimpleCommandExceptionType(new TranslatableComponent("commands.banip.invalid"));
   private static final SimpleCommandExceptionType ERROR_ALREADY_BANNED = new SimpleCommandExceptionType(new TranslatableComponent("commands.banip.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("ban-ip")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               ((RequiredArgumentBuilder)Commands.argument("target", StringArgumentType.word())
                     .executes(var0x -> banIpOrName((CommandSourceStack)var0x.getSource(), StringArgumentType.getString(var0x, "target"), null)))
                  .then(
                     Commands.argument("reason", MessageArgument.message())
                        .executes(
                           var0x -> banIpOrName(var0x.getSource(), StringArgumentType.getString(var0x, "target"), MessageArgument.getMessage(var0x, "reason"))
                        )
                  )
            )
      );
   }

   private static int banIpOrName(CommandSourceStack var0, String var1, @Nullable Component var2) throws CommandSyntaxException {
      Matcher â˜ƒ = IP_ADDRESS_PATTERN.matcher(â˜ƒ);
      if (â˜ƒ.matches()) {
         return banIp(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         ServerPlayer â˜ƒ = â˜ƒ.getServer().getPlayerList().getPlayerByName(â˜ƒ);
         if (â˜ƒ != null) {
            return banIp(â˜ƒ, â˜ƒ.getIpAddress(), â˜ƒ);
         } else {
            throw ERROR_INVALID_IP.create();
         }
      }
   }

   private static int banIp(CommandSourceStack var0, String var1, @Nullable Component var2) throws CommandSyntaxException {
      IpBanList â˜ƒ = â˜ƒ.getServer().getPlayerList().getIpBans();
      if (â˜ƒ.isBanned(â˜ƒ)) {
         throw ERROR_ALREADY_BANNED.create();
      } else {
         List<ServerPlayer> â˜ƒ = â˜ƒ.getServer().getPlayerList().getPlayersWithAddress(â˜ƒ);
         IpBanListEntry â˜ƒx = new IpBanListEntry(â˜ƒ, null, â˜ƒ.getTextName(), null, â˜ƒ == null ? null : â˜ƒ.getString());
         â˜ƒ.add(â˜ƒx);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.banip.success", â˜ƒ, â˜ƒx.getReason()), true);
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.banip.info", â˜ƒ.size(), EntitySelector.joinNames(â˜ƒ)), true);
         }

         for(ServerPlayer â˜ƒ : â˜ƒ) {
            â˜ƒ.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.ip_banned"));
         }

         return â˜ƒ.size();
      }
   }
}
