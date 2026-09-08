package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class TimeCommand {
   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("time")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("set")
                  .then(Commands.literal("day").executes(var0x -> setTime(var0x.getSource(), 1000)))
                  .then(Commands.literal("noon").executes(var0x -> setTime(var0x.getSource(), 6000)))
                  .then(Commands.literal("night").executes(var0x -> setTime(var0x.getSource(), 13000)))
                  .then(Commands.literal("midnight").executes(var0x -> setTime(var0x.getSource(), 18000)))
                  .then(
                     Commands.argument("time", TimeArgument.time())
                        .executes(var0x -> setTime(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                  )
            )
            .then(
               Commands.literal("add")
                  .then(
                     Commands.argument("time", TimeArgument.time())
                        .executes(var0x -> addTime(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                  )
            )
            .then(
               Commands.literal("query")
                  .then(Commands.literal("daytime").executes(var0x -> queryTime(var0x.getSource(), getDayTime(var0x.getSource().getLevel()))))
                  .then(
                     Commands.literal("gametime")
                        .executes(var0x -> queryTime(var0x.getSource(), (int)(var0x.getSource().getLevel().getGameTime() % 2147483647L)))
                  )
                  .then(
                     Commands.literal("day")
                        .executes(var0x -> queryTime(var0x.getSource(), (int)(var0x.getSource().getLevel().getDayTime() / 24000L % 2147483647L)))
                  )
            )
      );
   }

   private static int getDayTime(ServerLevel var0) {
      return (int)(â˜ƒ.getDayTime() % 24000L);
   }

   private static int queryTime(CommandSourceStack var0, int var1) {
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.time.query", â˜ƒ), false);
      return â˜ƒ;
   }

   public static int setTime(CommandSourceStack var0, int var1) {
      for(ServerLevel â˜ƒ : â˜ƒ.getServer().getAllLevels()) {
         â˜ƒ.setDayTime((long)â˜ƒ);
      }

      â˜ƒ.sendSuccess(new TranslatableComponent("commands.time.set", â˜ƒ), true);
      return getDayTime(â˜ƒ.getLevel());
   }

   public static int addTime(CommandSourceStack var0, int var1) {
      for(ServerLevel â˜ƒ : â˜ƒ.getServer().getAllLevels()) {
         â˜ƒ.setDayTime(â˜ƒ.getDayTime() + (long)â˜ƒ);
      }

      int â˜ƒ = getDayTime(â˜ƒ.getLevel());
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.time.set", â˜ƒ), true);
      return â˜ƒ;
   }
}
