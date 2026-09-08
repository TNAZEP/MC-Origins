package net.minecraft.command.impl;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class HelpCommand {
   private static final SimpleCommandExceptionType field_206930_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.help.failed"));

   public static void func_198510_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(Commands.func_197057_a("help").executes(var1 -> {
         Map<CommandNode<CommandSource>, String> ☃ = ☃.getSmartUsage(☃.getRoot(), var1.getSource());

         for(String ☃x : ☃.values()) {
            var1.getSource().func_197030_a(new TextComponentString("/" + ☃x), false);
         }

         return ☃.size();
      }).then(Commands.func_197056_a("command", StringArgumentType.greedyString()).executes(var1 -> {
         ParseResults<CommandSource> ☃ = ☃.parse(StringArgumentType.getString(var1, "command"), var1.getSource());
         if (☃.getContext().getNodes().isEmpty()) {
            throw field_206930_a.create();
         } else {
            Map<CommandNode<CommandSource>, String> ☃ = ☃.getSmartUsage(Iterables.getLast(☃.getContext().getNodes().keySet()), var1.getSource());

            for(String ☃x : ☃.values()) {
               var1.getSource().func_197030_a(new TextComponentString("/" + ☃.getReader().getString() + " " + ☃x), false);
            }

            return ☃.size();
         }
      })));
   }
}
