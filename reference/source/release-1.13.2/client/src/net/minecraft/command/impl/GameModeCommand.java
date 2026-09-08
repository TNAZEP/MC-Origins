package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class GameModeCommand {
   public static void func_198482_a(CommandDispatcher<CommandSource> var0) {
      LiteralArgumentBuilder<CommandSource> ☃ = Commands.func_197057_a("gamemode").requires(var0x -> var0x.func_197034_c(2));

      for(GameType ☃x : GameType.values()) {
         if (☃x != GameType.NOT_SET) {
            ☃.then(
               Commands.func_197057_a(☃x.func_77149_b())
                  .executes(var1x -> func_198484_a(var1x, Collections.singleton(var1x.getSource().func_197035_h()), ☃))
                  .then(
                     Commands.func_197056_a("target", EntityArgument.func_197094_d())
                        .executes(var1x -> func_198484_a(var1x, EntityArgument.func_197090_e(var1x, "target"), ☃))
                  )
            );
         }
      }

      ☃.register(☃);
   }

   private static void func_208517_a(CommandSource var0, EntityPlayerMP var1, GameType var2) {
      ITextComponent ☃ = new TextComponentTranslation("gameMode." + ☃.func_77149_b());
      if (☃.func_197022_f() == ☃) {
         ☃.func_197030_a(new TextComponentTranslation("commands.gamemode.success.self", ☃), true);
      } else {
         if (☃.func_197023_e().func_82736_K().func_82766_b("sendCommandFeedback")) {
            ☃.func_145747_a(new TextComponentTranslation("gameMode.changed", ☃));
         }

         ☃.func_197030_a(new TextComponentTranslation("commands.gamemode.success.other", ☃.func_145748_c_(), ☃), true);
      }
   }

   private static int func_198484_a(CommandContext<CommandSource> var0, Collection<EntityPlayerMP> var1, GameType var2) {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         if (☃x.field_71134_c.func_73081_b() != ☃) {
            ☃x.func_71033_a(☃);
            func_208517_a(☃.getSource(), ☃x, ☃);
            ++☃;
         }
      }

      return ☃;
   }
}
