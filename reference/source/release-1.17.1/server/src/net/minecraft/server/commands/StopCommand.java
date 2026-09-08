package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class StopCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(Commands.literal("stop").requires(var0x -> var0x.hasPermission(4)).executes(var0x -> {
         var0x.getSource().sendSuccess(new TranslatableComponent("commands.stop.stopping"), true);
         var0x.getSource().getServer().halt(false);
         return 1;
      }));
   }
}
