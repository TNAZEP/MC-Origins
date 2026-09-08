package net.minecraft.command.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentTranslation;

public class OpCommand {
   private static final SimpleCommandExceptionType field_198546_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.op.failed"));

   public static void func_198541_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("op")
            .requires(var0x -> var0x.func_197034_c(3))
            .then(
               Commands.func_197056_a("targets", GameProfileArgument.func_197108_a())
                  .suggests(
                     (var0x, var1) -> {
                        PlayerList ☃ = var0x.getSource().func_197028_i().func_184103_al();
                        return ISuggestionProvider.func_197013_a(
                           ☃.func_181057_v()
                              .stream()
                              .filter(var1x -> !☃.func_152596_g(var1x.func_146103_bH()))
                              .map(var0xx -> var0xx.func_146103_bH().getName()),
                           var1
                        );
                     }
                  )
                  .executes(var0x -> func_198542_a(var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets")))
            )
      );
   }

   private static int func_198542_a(CommandSource var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      PlayerList ☃ = ☃.func_197028_i().func_184103_al();
      int ☃x = 0;

      for(GameProfile ☃xx : ☃) {
         if (!☃.func_152596_g(☃xx)) {
            ☃.func_152605_a(☃xx);
            ++☃x;
            ☃.func_197030_a(new TextComponentTranslation("commands.op.success", ((GameProfile)☃.iterator().next()).getName()), true);
         }
      }

      if (☃x == 0) {
         throw field_198546_a.create();
      } else {
         return ☃x;
      }
   }
}
