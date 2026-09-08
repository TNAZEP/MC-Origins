package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentUtils;

public class TellRawCommand {
   public static void func_198818_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("tellraw")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .then(Commands.func_197056_a("message", ComponentArgument.func_197067_a()).executes(var0x -> {
                     int ☃ = 0;
            
                     for(EntityPlayerMP ☃x : EntityArgument.func_197090_e(var0x, "targets")) {
                        ☃x.func_145747_a(TextComponentUtils.func_197680_a(var0x.getSource(), ComponentArgument.func_197068_a(var0x, "message"), ☃x));
                        ++☃;
                     }
            
                     return ☃;
                  }))
            )
      );
   }
}
