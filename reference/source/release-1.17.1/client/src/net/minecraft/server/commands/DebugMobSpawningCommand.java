package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;

public class DebugMobSpawningCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("debugmobspawning").requires(var0x -> var0x.hasPermission(2));

      for(MobCategory â˜ƒx : MobCategory.values()) {
         â˜ƒ.then(
            Commands.literal(â˜ƒx.getName())
               .then(
                  Commands.argument("at", BlockPosArgument.blockPos())
                     .executes(var1x -> spawnMobs(var1x.getSource(), â˜ƒ, BlockPosArgument.getLoadedBlockPos(var1x, "at")))
               )
         );
      }

      â˜ƒ.register(â˜ƒ);
   }

   private static int spawnMobs(CommandSourceStack var0, MobCategory var1, BlockPos var2) {
      NaturalSpawner.spawnCategoryForPosition(â˜ƒ, â˜ƒ.getLevel(), â˜ƒ);
      return 1;
   }
}
