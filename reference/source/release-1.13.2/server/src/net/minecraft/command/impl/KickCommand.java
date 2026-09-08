package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class KickCommand {
   public static void func_198514_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("kick")
            .requires(var0x -> var0x.func_197034_c(3))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .executes(
                     var0x -> func_198515_a(
                           var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), new TextComponentTranslation("multiplayer.disconnect.kicked")
                        )
                  )
                  .then(
                     Commands.func_197056_a("reason", MessageArgument.func_197123_a())
                        .executes(
                           var0x -> func_198515_a(
                                 var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), MessageArgument.func_197124_a(var0x, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int func_198515_a(CommandSource var0, Collection<EntityPlayerMP> var1, ITextComponent var2) {
      for(EntityPlayerMP ☃ : ☃) {
         ☃.field_71135_a.func_194028_b(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.kick.success", ☃.func_145748_c_(), ☃), true);
      }

      return ☃.size();
   }
}
