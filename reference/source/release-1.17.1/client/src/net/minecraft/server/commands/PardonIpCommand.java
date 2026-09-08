package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.regex.Matcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.IpBanList;

public class PardonIpCommand {
   private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType(new TranslatableComponent("commands.pardonip.invalid"));
   private static final SimpleCommandExceptionType ERROR_NOT_BANNED = new SimpleCommandExceptionType(new TranslatableComponent("commands.pardonip.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("pardon-ip")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.argument("target", StringArgumentType.word())
                  .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(var0x.getSource().getServer().getPlayerList().getIpBans().getUserList(), var1))
                  .executes(var0x -> unban(var0x.getSource(), StringArgumentType.getString(var0x, "target")))
            )
      );
   }

   private static int unban(CommandSourceStack var0, String var1) throws CommandSyntaxException {
      Matcher â˜ƒ = BanIpCommands.IP_ADDRESS_PATTERN.matcher(â˜ƒ);
      if (!â˜ƒ.matches()) {
         throw ERROR_INVALID.create();
      } else {
         IpBanList â˜ƒ = â˜ƒ.getServer().getPlayerList().getIpBans();
         if (!â˜ƒ.isBanned(â˜ƒ)) {
            throw ERROR_NOT_BANNED.create();
         } else {
            â˜ƒ.remove(â˜ƒ);
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.pardonip.success", â˜ƒ), true);
            return 1;
         }
      }
   }
}
