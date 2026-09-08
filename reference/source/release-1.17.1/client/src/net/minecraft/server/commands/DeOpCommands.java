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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.PlayerList;

public class DeOpCommands {
   private static final SimpleCommandExceptionType ERROR_NOT_OP = new SimpleCommandExceptionType(new TranslatableComponent("commands.deop.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("deop")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.argument("targets", GameProfileArgument.gameProfile())
                  .suggests((var0x, var1) -> SharedSuggestionProvider.suggest(var0x.getSource().getServer().getPlayerList().getOpNames(), var1))
                  .executes(var0x -> deopPlayers(var0x.getSource(), GameProfileArgument.getGameProfiles(var0x, "targets")))
            )
      );
   }

   private static int deopPlayers(CommandSourceStack var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      PlayerList â˜ƒ = â˜ƒ.getServer().getPlayerList();
      int â˜ƒx = 0;

      for(GameProfile â˜ƒxx : â˜ƒ) {
         if (â˜ƒ.isOp(â˜ƒxx)) {
            â˜ƒ.deop(â˜ƒxx);
            ++â˜ƒx;
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.deop.success", ((GameProfile)â˜ƒ.iterator().next()).getName()), true);
         }
      }

      if (â˜ƒx == 0) {
         throw ERROR_NOT_OP.create();
      } else {
         â˜ƒ.getServer().kickUnlistedPlayers(â˜ƒ);
         return â˜ƒx;
      }
   }
}
