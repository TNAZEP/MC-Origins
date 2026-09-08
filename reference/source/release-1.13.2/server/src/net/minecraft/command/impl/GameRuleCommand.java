package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Map.Entry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameRules;

public class GameRuleCommand {
   public static void func_198487_a(CommandDispatcher<CommandSource> var0) {
      LiteralArgumentBuilder<CommandSource> ☃ = Commands.func_197057_a("gamerule").requires(var0x -> var0x.func_197034_c(2));

      for(Entry<String, GameRules.ValueDefinition> ☃x : GameRules.func_196231_c().entrySet()) {
         ☃.then(
            Commands.func_197057_a((String)☃x.getKey())
               .executes(var1x -> func_198492_a(var1x.getSource(), (String)☃.getKey()))
               .then(
                  ((GameRules.ValueDefinition)☃x.getValue())
                     .func_199594_b()
                     .func_199809_a("value")
                     .executes(var1x -> func_198488_a(var1x.getSource(), (String)☃.getKey(), var1x))
               )
         );
      }

      ☃.register(☃);
   }

   private static int func_198488_a(CommandSource var0, String var1, CommandContext<CommandSource> var2) {
      GameRules.Value ☃ = ☃.func_197028_i().func_200252_aR().func_196230_f(☃);
      ☃.func_180254_e().func_196222_a(☃, "value", ☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.gamerule.set", ☃, ☃.func_82756_a()), true);
      return ☃.func_180255_c();
   }

   private static int func_198492_a(CommandSource var0, String var1) {
      GameRules.Value ☃ = ☃.func_197028_i().func_200252_aR().func_196230_f(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.gamerule.query", ☃, ☃.func_82756_a()), false);
      return ☃.func_180255_c();
   }
}
