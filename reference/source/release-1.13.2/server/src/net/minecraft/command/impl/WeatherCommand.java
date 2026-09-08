package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentTranslation;

public class WeatherCommand {
   public static void func_198862_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("weather")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("clear")
                  .executes(var0x -> func_198869_a(var0x.getSource(), 6000))
                  .then(
                     Commands.func_197056_a("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> func_198869_a(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
            .then(
               Commands.func_197057_a("rain")
                  .executes(var0x -> func_198865_b(var0x.getSource(), 6000))
                  .then(
                     Commands.func_197056_a("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> func_198865_b(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
            .then(
               Commands.func_197057_a("thunder")
                  .executes(var0x -> func_198863_c(var0x.getSource(), 6000))
                  .then(
                     Commands.func_197056_a("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> func_198863_c(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
      );
   }

   private static int func_198869_a(CommandSource var0, int var1) {
      ☃.func_197023_e().func_72912_H().func_176142_i(☃);
      ☃.func_197023_e().func_72912_H().func_76080_g(0);
      ☃.func_197023_e().func_72912_H().func_76090_f(0);
      ☃.func_197023_e().func_72912_H().func_76084_b(false);
      ☃.func_197023_e().func_72912_H().func_76069_a(false);
      ☃.func_197030_a(new TextComponentTranslation("commands.weather.set.clear"), true);
      return ☃;
   }

   private static int func_198865_b(CommandSource var0, int var1) {
      ☃.func_197023_e().func_72912_H().func_176142_i(0);
      ☃.func_197023_e().func_72912_H().func_76080_g(☃);
      ☃.func_197023_e().func_72912_H().func_76090_f(☃);
      ☃.func_197023_e().func_72912_H().func_76084_b(true);
      ☃.func_197023_e().func_72912_H().func_76069_a(false);
      ☃.func_197030_a(new TextComponentTranslation("commands.weather.set.rain"), true);
      return ☃;
   }

   private static int func_198863_c(CommandSource var0, int var1) {
      ☃.func_197023_e().func_72912_H().func_176142_i(0);
      ☃.func_197023_e().func_72912_H().func_76080_g(☃);
      ☃.func_197023_e().func_72912_H().func_76090_f(☃);
      ☃.func_197023_e().func_72912_H().func_76084_b(true);
      ☃.func_197023_e().func_72912_H().func_76069_a(true);
      ☃.func_197030_a(new TextComponentTranslation("commands.weather.set.thunder"), true);
      return ☃;
   }
}
