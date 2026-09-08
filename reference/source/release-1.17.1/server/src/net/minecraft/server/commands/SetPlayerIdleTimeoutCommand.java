package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class SetPlayerIdleTimeoutCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("setidletimeout")
            .requires(var0x -> var0x.hasPermission(3))
            .then(
               Commands.argument("minutes", IntegerArgumentType.integer(0))
                  .executes(var0x -> setIdleTimeout(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "minutes")))
            )
      );
   }

   private static int setIdleTimeout(CommandSourceStack var0, int var1) {
      â˜ƒ.getServer().setPlayerIdleTimeout(â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.setidletimeout.success", â˜ƒ), true);
      return â˜ƒ;
   }
}
