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
import net.minecraft.server.players.UserBanList;

public class PardonCommand {
   private static final SimpleCommandExceptionType ERROR_NOT_BANNED = new SimpleCommandExceptionType(new TranslatableComponent("commands.pardon.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("pardon")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.argument("targets", GameProfileArgument.gameProfile())
                  .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(var0x.getSource().getServer().getPlayerList().getBans().getUserList(), var1))
                  .executes(var0x -> pardonPlayers(var0x.getSource(), GameProfileArgument.getGameProfiles(var0x, "targets")))
            )
      );
   }

   private static int pardonPlayers(CommandSourceStack var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      UserBanList â˜ƒ = â˜ƒ.getServer().getPlayerList().getBans();
      int â˜ƒx = 0;

      for(GameProfile â˜ƒxx : â˜ƒ) {
         if (â˜ƒ.isBanned(â˜ƒxx)) {
            â˜ƒ.remove(â˜ƒxx);
            ++â˜ƒx;
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.pardon.success", ComponentUtils.getDisplayName(â˜ƒxx)), true);
         }
      }

      if (â˜ƒx == 0) {
         throw ERROR_NOT_BANNED.create();
      } else {
         return â˜ƒx;
      }
   }
}
