package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class WeatherCommand {
   private static final int DEFAULT_TIME = 6000;

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("weather")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("clear")
                  .executes(var0x -> setClear(var0x.getSource(), 6000))
                  .then(
                     Commands.argument("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> setClear(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
            .then(
               Commands.literal("rain")
                  .executes(var0x -> setRain(var0x.getSource(), 6000))
                  .then(
                     Commands.argument("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> setRain(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
            .then(
               Commands.literal("thunder")
                  .executes(var0x -> setThunder(var0x.getSource(), 6000))
                  .then(
                     Commands.argument("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(var0x -> setThunder(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "duration") * 20))
                  )
            )
      );
   }

   private static int setClear(CommandSourceStack var0, int var1) {
      â˜ƒ.getLevel().setWeatherParameters(â˜ƒ, 0, false, false);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.weather.set.clear"), true);
      return â˜ƒ;
   }

   private static int setRain(CommandSourceStack var0, int var1) {
      â˜ƒ.getLevel().setWeatherParameters(0, â˜ƒ, true, false);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.weather.set.rain"), true);
      return â˜ƒ;
   }

   private static int setThunder(CommandSourceStack var0, int var1) {
      â˜ƒ.getLevel().setWeatherParameters(0, â˜ƒ, true, true);
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.weather.set.thunder"), true);
      return â˜ƒ;
   }
}
