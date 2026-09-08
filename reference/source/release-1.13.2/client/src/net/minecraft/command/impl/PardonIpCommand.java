package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.regex.Matcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.server.management.UserListIPBans;
import net.minecraft.util.text.TextComponentTranslation;

public class PardonIpCommand {
   private static final SimpleCommandExceptionType field_198558_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.pardonip.invalid"));
   private static final SimpleCommandExceptionType field_198559_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.pardonip.failed"));

   public static void func_198553_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("pardon-ip")
            .requires(var0x -> var0x.func_197028_i().func_184103_al().func_72363_f().func_152689_b() && var0x.func_197034_c(3))
            .then(
               Commands.func_197056_a("target", StringArgumentType.word())
                  .suggests(
                     (var0x, var1) -> ISuggestionProvider.func_197008_a(var0x.getSource().func_197028_i().func_184103_al().func_72363_f().func_152685_a(), var1)
                  )
                  .executes(var0x -> func_198557_a(var0x.getSource(), StringArgumentType.getString(var0x, "target")))
            )
      );
   }

   private static int func_198557_a(CommandSource var0, String var1) throws CommandSyntaxException {
      Matcher ☃ = BanIpCommand.field_198225_a.matcher(☃);
      if (!☃.matches()) {
         throw field_198558_a.create();
      } else {
         UserListIPBans ☃ = ☃.func_197028_i().func_184103_al().func_72363_f();
         if (!☃.func_199044_a(☃)) {
            throw field_198559_b.create();
         } else {
            ☃.func_152684_c(☃);
            ☃.func_197030_a(new TextComponentTranslation("commands.pardonip.success", ☃), true);
            return 1;
         }
      }
   }
}
