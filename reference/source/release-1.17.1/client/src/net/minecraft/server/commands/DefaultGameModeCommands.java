package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class DefaultGameModeCommands {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("defaultgamemode").requires(var0x -> var0x.hasPermission(2));

      for(GameType â˜ƒx : GameType.values()) {
         â˜ƒ.then(Commands.literal(â˜ƒx.getName()).executes(var1x -> setMode(var1x.getSource(), â˜ƒ)));
      }

      â˜ƒ.register(â˜ƒ);
   }

   private static int setMode(CommandSourceStack var0, GameType var1) {
      int â˜ƒ = 0;
      MinecraftServer â˜ƒx = â˜ƒ.getServer();
      â˜ƒx.setDefaultGameType(â˜ƒ);
      GameType â˜ƒxx = â˜ƒx.getForcedGameType();
      if (â˜ƒxx != null) {
         for(ServerPlayer â˜ƒxxx : â˜ƒx.getPlayerList().getPlayers()) {
            if (â˜ƒxxx.setGameMode(â˜ƒxx)) {
               ++â˜ƒ;
            }
         }
      }

      â˜ƒ.sendSuccess(new TranslatableComponent("commands.defaultgamemode.success", â˜ƒ.getLongDisplayName()), true);
      return â˜ƒ;
   }
}
