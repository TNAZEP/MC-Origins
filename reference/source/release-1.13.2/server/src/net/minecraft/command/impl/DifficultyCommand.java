package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.dimension.DimensionType;

public class DifficultyCommand {
   private static final DynamicCommandExceptionType field_198349_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.difficulty.failure", var0)
   );

   public static void func_198344_a(CommandDispatcher<CommandSource> var0) {
      LiteralArgumentBuilder<CommandSource> ☃ = Commands.func_197057_a("difficulty");

      for(EnumDifficulty ☃x : EnumDifficulty.values()) {
         ☃.then(Commands.func_197057_a(☃x.func_151526_b()).executes(var1x -> func_198345_a(var1x.getSource(), ☃)));
      }

      ☃.register(☃.requires(var0x -> var0x.func_197034_c(2)).executes(var0x -> {
         EnumDifficulty ☃ = var0x.getSource().func_197023_e().func_175659_aa();
         var0x.getSource().func_197030_a(new TextComponentTranslation("commands.difficulty.query", ☃.func_199285_b()), false);
         return ☃.func_151525_a();
      }));
   }

   public static int func_198345_a(CommandSource var0, EnumDifficulty var1) throws CommandSyntaxException {
      MinecraftServer ☃ = ☃.func_197028_i();
      if (☃.func_71218_a(DimensionType.OVERWORLD).func_175659_aa() == ☃) {
         throw field_198349_a.create(☃.func_151526_b());
      } else {
         ☃.func_147139_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.difficulty.success", ☃.func_199285_b()), true);
         return 0;
      }
   }
}
