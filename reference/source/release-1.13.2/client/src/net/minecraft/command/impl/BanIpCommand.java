package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListIPBans;
import net.minecraft.server.management.UserListIPBansEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class BanIpCommand {
   public static final Pattern field_198225_a = Pattern.compile(
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
   );
   private static final SimpleCommandExceptionType field_198226_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.banip.invalid"));
   private static final SimpleCommandExceptionType field_198227_c = new SimpleCommandExceptionType(new TextComponentTranslation("commands.banip.failed"));

   public static void func_198220_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("ban-ip")
            .requires(var0x -> var0x.func_197028_i().func_184103_al().func_72363_f().func_152689_b() && var0x.func_197034_c(3))
            .then(
               ((RequiredArgumentBuilder)Commands.func_197056_a("target", StringArgumentType.word())
                     .executes(var0x -> func_198223_a((CommandSource)var0x.getSource(), StringArgumentType.getString(var0x, "target"), null)))
                  .then(
                     Commands.func_197056_a("reason", MessageArgument.func_197123_a())
                        .executes(
                           var0x -> func_198223_a(
                                 var0x.getSource(), StringArgumentType.getString(var0x, "target"), MessageArgument.func_197124_a(var0x, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int func_198223_a(CommandSource var0, String var1, @Nullable ITextComponent var2) throws CommandSyntaxException {
      Matcher ☃ = field_198225_a.matcher(☃);
      if (☃.matches()) {
         return func_198224_b(☃, ☃, ☃);
      } else {
         EntityPlayerMP ☃ = ☃.func_197028_i().func_184103_al().func_152612_a(☃);
         if (☃ != null) {
            return func_198224_b(☃, ☃.func_71114_r(), ☃);
         } else {
            throw field_198226_b.create();
         }
      }
   }

   private static int func_198224_b(CommandSource var0, String var1, @Nullable ITextComponent var2) throws CommandSyntaxException {
      UserListIPBans ☃ = ☃.func_197028_i().func_184103_al().func_72363_f();
      if (☃.func_199044_a(☃)) {
         throw field_198227_c.create();
      } else {
         List<EntityPlayerMP> ☃ = ☃.func_197028_i().func_184103_al().func_72382_j(☃);
         UserListIPBansEntry ☃x = new UserListIPBansEntry(☃, null, ☃.func_197037_c(), null, ☃ == null ? null : ☃.getString());
         ☃.func_152687_a(☃x);
         ☃.func_197030_a(new TextComponentTranslation("commands.banip.success", ☃, ☃x.func_73686_f()), true);
         if (!☃.isEmpty()) {
            ☃.func_197030_a(new TextComponentTranslation("commands.banip.info", ☃.size(), EntitySelector.func_197350_a(☃)), true);
         }

         for(EntityPlayerMP ☃ : ☃) {
            ☃.field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.ip_banned"));
         }

         return ☃.size();
      }
   }
}
