package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;

public class TimeCommand {
   public static void func_198823_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("time")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("set")
                  .then(Commands.func_197057_a("day").executes(var0x -> func_198829_a(var0x.getSource(), 1000)))
                  .then(Commands.func_197057_a("noon").executes(var0x -> func_198829_a(var0x.getSource(), 6000)))
                  .then(Commands.func_197057_a("night").executes(var0x -> func_198829_a(var0x.getSource(), 13000)))
                  .then(Commands.func_197057_a("midnight").executes(var0x -> func_198829_a(var0x.getSource(), 18000)))
                  .then(
                     Commands.func_197056_a("time", IntegerArgumentType.integer(0))
                        .executes(var0x -> func_198829_a(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                  )
            )
            .then(
               Commands.func_197057_a("add")
                  .then(
                     Commands.func_197056_a("time", IntegerArgumentType.integer(0))
                        .executes(var0x -> func_198826_b(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                  )
            )
            .then(
               Commands.func_197057_a("query")
                  .then(Commands.func_197057_a("daytime").executes(var0x -> func_198824_c(var0x.getSource(), func_198833_a(var0x.getSource().func_197023_e()))))
                  .then(
                     Commands.func_197057_a("gametime")
                        .executes(var0x -> func_198824_c(var0x.getSource(), (int)(var0x.getSource().func_197023_e().func_82737_E() % 2147483647L)))
                  )
                  .then(
                     Commands.func_197057_a("day")
                        .executes(var0x -> func_198824_c(var0x.getSource(), (int)(var0x.getSource().func_197023_e().func_72820_D() / 24000L % 2147483647L)))
                  )
            )
      );
   }

   private static int func_198833_a(WorldServer var0) {
      return (int)(☃.func_72820_D() % 24000L);
   }

   private static int func_198824_c(CommandSource var0, int var1) {
      ☃.func_197030_a(new TextComponentTranslation("commands.time.query", ☃), false);
      return ☃;
   }

   public static int func_198829_a(CommandSource var0, int var1) {
      for(WorldServer ☃ : ☃.func_197028_i().func_212370_w()) {
         ☃.func_72877_b((long)☃);
      }

      ☃.func_197030_a(new TextComponentTranslation("commands.time.set", ☃), true);
      return func_198833_a(☃.func_197023_e());
   }

   public static int func_198826_b(CommandSource var0, int var1) {
      for(WorldServer ☃ : ☃.func_197028_i().func_212370_w()) {
         ☃.func_72877_b(☃.func_72820_D() + (long)☃);
      }

      int ☃ = func_198833_a(☃.func_197023_e());
      ☃.func_197030_a(new TextComponentTranslation("commands.time.set", ☃), true);
      return ☃;
   }
}
