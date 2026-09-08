package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class MessageCommand {
   public static void func_198537_a(CommandDispatcher<CommandSource> var0) {
      LiteralCommandNode<CommandSource> ☃ = ☃.register(
         Commands.func_197057_a("msg")
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .then(
                     Commands.func_197056_a("message", MessageArgument.func_197123_a())
                        .executes(
                           var0x -> func_198538_a(
                                 var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"), MessageArgument.func_197124_a(var0x, "message")
                              )
                        )
                  )
            )
      );
      ☃.register(Commands.func_197057_a("tell").redirect(☃));
      ☃.register(Commands.func_197057_a("w").redirect(☃));
   }

   private static int func_198538_a(CommandSource var0, Collection<EntityPlayerMP> var1, ITextComponent var2) {
      for(EntityPlayerMP ☃ : ☃) {
         ☃.func_145747_a(
            new TextComponentTranslation("commands.message.display.incoming", ☃.func_197019_b(), ☃.func_212638_h())
               .func_211709_a(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC})
         );
         ☃.func_197030_a(
            new TextComponentTranslation("commands.message.display.outgoing", ☃.func_145748_c_(), ☃.func_212638_h())
               .func_211709_a(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}),
            false
         );
      }

      return ☃.size();
   }
}
