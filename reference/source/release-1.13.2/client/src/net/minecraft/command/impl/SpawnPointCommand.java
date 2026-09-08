package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class SpawnPointCommand {
   public static void func_198695_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("spawnpoint")
            .requires(var0x -> var0x.func_197034_c(2))
            .executes(
               var0x -> func_198696_a(
                     var0x.getSource(), Collections.singleton(var0x.getSource().func_197035_h()), new BlockPos(var0x.getSource().func_197036_d())
                  )
            )
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .executes(
                     var0x -> func_198696_a(var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), new BlockPos(var0x.getSource().func_197036_d()))
                  )
                  .then(
                     Commands.func_197056_a("pos", BlockPosArgument.func_197276_a())
                        .executes(
                           var0x -> func_198696_a(
                                 var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), BlockPosArgument.func_197274_b(var0x, "pos")
                              )
                        )
                  )
            )
      );
   }

   private static int func_198696_a(CommandSource var0, Collection<EntityPlayerMP> var1, BlockPos var2) {
      for(EntityPlayerMP ☃ : ☃) {
         ☃.func_180473_a(☃, true);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.spawnpoint.success.single",
               ☃.func_177958_n(),
               ☃.func_177956_o(),
               ☃.func_177952_p(),
               ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
            ),
            true
         );
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation("commands.spawnpoint.success.multiple", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃.size()), true
         );
      }

      return ☃.size();
   }
}
