package net.minecraft.command.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListBans;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class BanCommand {
   private static final SimpleCommandExceptionType field_198239_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.ban.failed"));

   public static void func_198235_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("ban")
            .requires(var0x -> var0x.func_197028_i().func_184103_al().func_152608_h().func_152689_b() && var0x.func_197034_c(3))
            .then(
               Commands.func_197056_a("targets", GameProfileArgument.func_197108_a())
                  .executes(var0x -> func_198236_a(var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets"), null))
                  .then(
                     Commands.func_197056_a("reason", MessageArgument.func_197123_a())
                        .executes(
                           var0x -> func_198236_a(
                                 var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets"), MessageArgument.func_197124_a(var0x, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int func_198236_a(CommandSource var0, Collection<GameProfile> var1, @Nullable ITextComponent var2) throws CommandSyntaxException {
      UserListBans ☃ = ☃.func_197028_i().func_184103_al().func_152608_h();
      int ☃x = 0;

      for(GameProfile ☃xx : ☃) {
         if (!☃.func_152702_a(☃xx)) {
            UserListBansEntry ☃xxx = new UserListBansEntry(☃xx, null, ☃.func_197037_c(), null, ☃ == null ? null : ☃.getString());
            ☃.func_152687_a(☃xxx);
            ++☃x;
            ☃.func_197030_a(new TextComponentTranslation("commands.ban.success", TextComponentUtils.func_197679_a(☃xx), ☃xxx.func_73686_f()), true);
            EntityPlayerMP ☃xxxx = ☃.func_197028_i().func_184103_al().func_177451_a(☃xx.getId());
            if (☃xxxx != null) {
               ☃xxxx.field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.banned"));
            }
         }
      }

      if (☃x == 0) {
         throw field_198239_a.create();
      } else {
         return ☃x;
      }
   }
}
