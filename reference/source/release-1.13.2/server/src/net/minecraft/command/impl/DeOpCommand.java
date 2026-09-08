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
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentTranslation;

public class DeOpCommand {
   private static final SimpleCommandExceptionType field_198326_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.deop.failed"));

   public static void func_198321_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("deop")
            .requires(var0x -> var0x.func_197034_c(3))
            .then(
               Commands.func_197056_a("targets", GameProfileArgument.func_197108_a())
                  .suggests((var0x, var1) -> ISuggestionProvider.func_197008_a(var0x.getSource().func_197028_i().func_184103_al().func_152606_n(), var1))
                  .executes(var0x -> func_198322_a(var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets")))
            )
      );
   }

   private static int func_198322_a(CommandSource var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      PlayerList ☃ = ☃.func_197028_i().func_184103_al();
      int ☃x = 0;

      for(GameProfile ☃xx : ☃) {
         if (☃.func_152596_g(☃xx)) {
            ☃.func_152610_b(☃xx);
            ++☃x;
            ☃.func_197030_a(new TextComponentTranslation("commands.deop.success", ((GameProfile)☃.iterator().next()).getName()), true);
         }
      }

      if (☃x == 0) {
         throw field_198326_a.create();
      } else {
         ☃.func_197028_i().func_205743_a(☃);
         return ☃x;
      }
   }
}
