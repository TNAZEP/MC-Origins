package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class DefaultGameModeCommand {
   public static void func_198340_a(CommandDispatcher<CommandSource> var0) {
      LiteralArgumentBuilder<CommandSource> ☃ = Commands.func_197057_a("defaultgamemode").requires(var0x -> var0x.func_197034_c(2));

      for(GameType ☃x : GameType.values()) {
         if (☃x != GameType.NOT_SET) {
            ☃.then(Commands.func_197057_a(☃x.func_77149_b()).executes(var1x -> func_198341_a(var1x.getSource(), ☃)));
         }
      }

      ☃.register(☃);
   }

   private static int func_198341_a(CommandSource var0, GameType var1) {
      int ☃ = 0;
      MinecraftServer ☃x = ☃.func_197028_i();
      ☃x.func_71235_a(☃);
      if (☃x.func_104056_am()) {
         for(EntityPlayerMP ☃xx : ☃x.func_184103_al().func_181057_v()) {
            if (☃xx.field_71134_c.func_73081_b() != ☃) {
               ☃xx.func_71033_a(☃);
               ++☃;
            }
         }
      }

      ☃.func_197030_a(new TextComponentTranslation("commands.defaultgamemode.success", ☃.func_196220_c()), true);
      return ☃;
   }
}
