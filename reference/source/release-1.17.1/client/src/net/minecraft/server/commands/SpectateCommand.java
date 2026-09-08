package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;

public class SpectateCommand {
   private static final SimpleCommandExceptionType ERROR_SELF = new SimpleCommandExceptionType(new TranslatableComponent("commands.spectate.self"));
   private static final DynamicCommandExceptionType ERROR_NOT_SPECTATOR = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.spectate.not_spectator", var0)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("spectate")
            .requires(var0x -> var0x.hasPermission(2))
            .executes(var0x -> spectate(var0x.getSource(), null, var0x.getSource().getPlayerOrException()))
            .then(
               Commands.argument("target", EntityArgument.entity())
                  .executes(var0x -> spectate(var0x.getSource(), EntityArgument.getEntity(var0x, "target"), var0x.getSource().getPlayerOrException()))
                  .then(
                     Commands.argument("player", EntityArgument.player())
                        .executes(var0x -> spectate(var0x.getSource(), EntityArgument.getEntity(var0x, "target"), EntityArgument.getPlayer(var0x, "player")))
                  )
            )
      );
   }

   private static int spectate(CommandSourceStack var0, @Nullable Entity var1, ServerPlayer var2) throws CommandSyntaxException {
      if (â˜ƒ == â˜ƒ) {
         throw ERROR_SELF.create();
      } else if (â˜ƒ.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
         throw ERROR_NOT_SPECTATOR.create(â˜ƒ.getDisplayName());
      } else {
         â˜ƒ.setCamera(â˜ƒ);
         if (â˜ƒ != null) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.spectate.success.started", â˜ƒ.getDisplayName()), false);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.spectate.success.stopped"), false);
         }

         return 1;
      }
   }
}
