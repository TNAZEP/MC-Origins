package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;

public class SaveAllCommand {
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.save.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("save-all")
            .requires(var0x -> var0x.hasPermission(4))
            .executes(var0x -> saveAll(var0x.getSource(), false))
            .then(Commands.literal("flush").executes(var0x -> saveAll(var0x.getSource(), true)))
      );
   }

   private static int saveAll(CommandSourceStack var0, boolean var1) throws CommandSyntaxException {
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.save.saving"), false);
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      â˜ƒ.getPlayerList().saveAll();
      boolean â˜ƒx = â˜ƒ.saveAllChunks(true, â˜ƒ, true);
      if (!â˜ƒx) {
         throw ERROR_FAILED.create();
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.save.success"), true);
         return 1;
      }
   }
}
