package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;

public class DifficultyCommand {
   private static final DynamicCommandExceptionType ERROR_ALREADY_DIFFICULT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.difficulty.failure", var0)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("difficulty");

      for(Difficulty â˜ƒx : Difficulty.values()) {
         â˜ƒ.then(Commands.literal(â˜ƒx.getKey()).executes(var1x -> setDifficulty(var1x.getSource(), â˜ƒ)));
      }

      â˜ƒ.register(â˜ƒ.requires(var0x -> var0x.hasPermission(2)).executes(var0x -> {
         Difficulty â˜ƒ = var0x.getSource().getLevel().getDifficulty();
         var0x.getSource().sendSuccess(new TranslatableComponent("commands.difficulty.query", â˜ƒ.getDisplayName()), false);
         return â˜ƒ.getId();
      }));
   }

   public static int setDifficulty(CommandSourceStack var0, Difficulty var1) throws CommandSyntaxException {
      MinecraftServer â˜ƒ = â˜ƒ.getServer();
      if (â˜ƒ.getWorldData().getDifficulty() == â˜ƒ) {
         throw ERROR_ALREADY_DIFFICULT.create(â˜ƒ.getKey());
      } else {
         â˜ƒ.setDifficulty(â˜ƒ, true);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.difficulty.success", â˜ƒ.getDisplayName()), true);
         return 0;
      }
   }
}
