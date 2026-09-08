package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;

public class SetWorldSpawnCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("setworldspawn")
            .requires(var0x -> var0x.hasPermission(2))
            .executes(var0x -> setSpawn(var0x.getSource(), new BlockPos(var0x.getSource().getPosition()), 0.0F))
            .then(
               Commands.argument("pos", BlockPosArgument.blockPos())
                  .executes(var0x -> setSpawn(var0x.getSource(), BlockPosArgument.getSpawnablePos(var0x, "pos"), 0.0F))
                  .then(
                     Commands.argument("angle", AngleArgument.angle())
                        .executes(var0x -> setSpawn(var0x.getSource(), BlockPosArgument.getSpawnablePos(var0x, "pos"), AngleArgument.getAngle(var0x, "angle")))
                  )
            )
      );
   }

   private static int setSpawn(CommandSourceStack var0, BlockPos var1, float var2) {
      â˜ƒ.getLevel().setDefaultSpawnPos(â˜ƒ, â˜ƒ);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.setworldspawn.success", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ), true);
      return 1;
   }
}
