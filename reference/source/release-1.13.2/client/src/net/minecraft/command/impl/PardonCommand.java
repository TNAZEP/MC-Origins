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
import net.minecraft.server.management.UserListBans;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class PardonCommand {
   private static final SimpleCommandExceptionType field_198552_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.pardon.failed"));

   public static void func_198547_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("pardon")
            .requires(var0x -> var0x.func_197028_i().func_184103_al().func_72363_f().func_152689_b() && var0x.func_197034_c(3))
            .then(
               Commands.func_197056_a("targets", GameProfileArgument.func_197108_a())
                  .suggests(
                     (var0x, var1) -> ISuggestionProvider.func_197008_a(
                           var0x.getSource().func_197028_i().func_184103_al().func_152608_h().func_152685_a(), var1
                        )
                  )
                  .executes(var0x -> func_198548_a(var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets")))
            )
      );
   }

   private static int func_198548_a(CommandSource var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      UserListBans ☃ = ☃.func_197028_i().func_184103_al().func_152608_h();
      int ☃x = 0;

      for(GameProfile ☃xx : ☃) {
         if (☃.func_152702_a(☃xx)) {
            ☃.func_152684_c(☃xx);
            ++☃x;
            ☃.func_197030_a(new TextComponentTranslation("commands.pardon.success", TextComponentUtils.func_197679_a(☃xx)), true);
         }
      }

      if (☃x == 0) {
         throw field_198552_a.create();
      } else {
         return ☃x;
      }
   }
}
