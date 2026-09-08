package net.minecraft.command.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.UserListEntryBan;
import net.minecraft.util.text.TextComponentTranslation;

public class BanListCommand {
   public static void func_198229_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("banlist")
            .requires(
               var0x -> (
                        var0x.func_197028_i().func_184103_al().func_152608_h().func_152689_b()
                           || var0x.func_197028_i().func_184103_al().func_72363_f().func_152689_b()
                     )
                     && var0x.func_197034_c(3)
            )
            .executes(var0x -> {
               PlayerList ☃ = var0x.getSource().func_197028_i().func_184103_al();
               return func_198230_a(
                  var0x.getSource(), Lists.newArrayList(Iterables.concat(☃.func_152608_h().func_199043_f(), ☃.func_72363_f().func_199043_f()))
               );
            })
            .then(
               Commands.func_197057_a("ips")
                  .executes(var0x -> func_198230_a(var0x.getSource(), var0x.getSource().func_197028_i().func_184103_al().func_72363_f().func_199043_f()))
            )
            .then(
               Commands.func_197057_a("players")
                  .executes(var0x -> func_198230_a(var0x.getSource(), var0x.getSource().func_197028_i().func_184103_al().func_152608_h().func_199043_f()))
            )
      );
   }

   private static int func_198230_a(CommandSource var0, Collection<? extends UserListEntryBan<?>> var1) {
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.banlist.none"), false);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.banlist.list", ☃.size()), false);

         for(UserListEntryBan<?> ☃ : ☃) {
            ☃.func_197030_a(new TextComponentTranslation("commands.banlist.entry", ☃.func_199041_e(), ☃.func_199040_b(), ☃.func_73686_f()), false);
         }
      }

      return ☃.size();
   }
}
