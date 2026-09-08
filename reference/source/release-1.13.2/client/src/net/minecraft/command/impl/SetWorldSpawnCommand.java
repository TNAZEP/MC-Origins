package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class SetWorldSpawnCommand {
   public static void func_198702_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("setworldspawn")
            .requires(var0x -> var0x.func_197034_c(2))
            .executes(var0x -> func_198701_a(var0x.getSource(), new BlockPos(var0x.getSource().func_197036_d())))
            .then(
               Commands.func_197056_a("pos", BlockPosArgument.func_197276_a())
                  .executes(var0x -> func_198701_a(var0x.getSource(), BlockPosArgument.func_197274_b(var0x, "pos")))
            )
      );
   }

   private static int func_198701_a(CommandSource var0, BlockPos var1) {
      ☃.func_197023_e().func_175652_B(☃);
      ☃.func_197028_i().func_184103_al().func_148540_a(new SPacketSpawnPosition(☃));
      ☃.func_197030_a(new TextComponentTranslation("commands.setworldspawn.success", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p()), true);
      return 1;
   }
}
