package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;

public class GameModeCommand {
   public static final int PERMISSION_LEVEL = 2;

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("gamemode").requires(var0x -> var0x.hasPermission(2));

      for(GameType â˜ƒx : GameType.values()) {
         â˜ƒ.then(
            Commands.literal(â˜ƒx.getName())
               .executes(var1x -> setMode(var1x, Collections.singleton(var1x.getSource().getPlayerOrException()), â˜ƒ))
               .then(Commands.argument("target", EntityArgument.players()).executes(var1x -> setMode(var1x, EntityArgument.getPlayers(var1x, "target"), â˜ƒ)))
         );
      }

      â˜ƒ.register(â˜ƒ);
   }

   private static void logGamemodeChange(CommandSourceStack var0, ServerPlayer var1, GameType var2) {
      Component â˜ƒ = new TranslatableComponent("gameMode." + â˜ƒ.getName());
      if (â˜ƒ.getEntity() == â˜ƒ) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.gamemode.success.self", â˜ƒ), true);
      } else {
         if (â˜ƒ.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
            â˜ƒ.sendMessage(new TranslatableComponent("gameMode.changed", â˜ƒ), Util.NIL_UUID);
         }

         â˜ƒ.sendSuccess(new TranslatableComponent("commands.gamemode.success.other", â˜ƒ.getDisplayName(), â˜ƒ), true);
      }
   }

   private static int setMode(CommandContext<CommandSourceStack> var0, Collection<ServerPlayer> var1, GameType var2) {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         if (â˜ƒx.setGameMode(â˜ƒ)) {
            logGamemodeChange(â˜ƒ.getSource(), â˜ƒx, â˜ƒ);
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }
}
