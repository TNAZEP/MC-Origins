package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SayCommand {
   public static void func_198625_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("say")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("message", MessageArgument.func_197123_a())
                  .executes(
                     var0x -> {
                        ITextComponent ☃ = MessageArgument.func_197124_a(var0x, "message");
                        var0x.getSource()
                           .func_197028_i()
                           .func_184103_al()
                           .func_148539_a(new TextComponentTranslation("chat.type.announcement", var0x.getSource().func_197019_b(), ☃));
                        return 1;
                     }
                  )
            )
      );
   }
}
