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
import net.minecraft.server.management.UserListWhitelist;
import net.minecraft.server.management.UserListWhitelistEntry;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class WhitelistCommand {
   private static final SimpleCommandExceptionType field_198887_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.whitelist.alreadyOn"));
   private static final SimpleCommandExceptionType field_198888_b = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.whitelist.alreadyOff")
   );
   private static final SimpleCommandExceptionType field_198889_c = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.whitelist.add.failed")
   );
   private static final SimpleCommandExceptionType field_198890_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.whitelist.remove.failed")
   );

   public static void func_198873_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("whitelist")
            .requires(var0x -> var0x.func_197034_c(3))
            .then(Commands.func_197057_a("on").executes(var0x -> func_198884_b(var0x.getSource())))
            .then(Commands.func_197057_a("off").executes(var0x -> func_198885_c(var0x.getSource())))
            .then(Commands.func_197057_a("list").executes(var0x -> func_198886_d(var0x.getSource())))
            .then(
               Commands.func_197057_a("add")
                  .then(
                     Commands.func_197056_a("targets", GameProfileArgument.func_197108_a())
                        .suggests(
                           (var0x, var1) -> {
                              PlayerList ☃ = var0x.getSource().func_197028_i().func_184103_al();
                              return ISuggestionProvider.func_197013_a(
                                 ☃.func_181057_v()
                                    .stream()
                                    .filter(var1x -> !☃.func_152599_k().func_152705_a(var1x.func_146103_bH()))
                                    .map(var0xx -> var0xx.func_146103_bH().getName()),
                                 var1
                              );
                           }
                        )
                        .executes(var0x -> func_198880_a(var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets")))
                  )
            )
            .then(
               Commands.func_197057_a("remove")
                  .then(
                     Commands.func_197056_a("targets", GameProfileArgument.func_197108_a())
                        .suggests((var0x, var1) -> ISuggestionProvider.func_197008_a(var0x.getSource().func_197028_i().func_184103_al().func_152598_l(), var1))
                        .executes(var0x -> func_198876_b(var0x.getSource(), GameProfileArgument.func_197109_a(var0x, "targets")))
                  )
            )
            .then(Commands.func_197057_a("reload").executes(var0x -> func_198883_a(var0x.getSource())))
      );
   }

   private static int func_198883_a(CommandSource var0) {
      ☃.func_197028_i().func_184103_al().func_187244_a();
      ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.reloaded"), true);
      ☃.func_197028_i().func_205743_a(☃);
      return 1;
   }

   private static int func_198880_a(CommandSource var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      UserListWhitelist ☃ = ☃.func_197028_i().func_184103_al().func_152599_k();
      int ☃x = 0;

      for(GameProfile ☃xx : ☃) {
         if (!☃.func_152705_a(☃xx)) {
            UserListWhitelistEntry ☃xxx = new UserListWhitelistEntry(☃xx);
            ☃.func_152687_a(☃xxx);
            ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.add.success", TextComponentUtils.func_197679_a(☃xx)), true);
            ++☃x;
         }
      }

      if (☃x == 0) {
         throw field_198889_c.create();
      } else {
         return ☃x;
      }
   }

   private static int func_198876_b(CommandSource var0, Collection<GameProfile> var1) throws CommandSyntaxException {
      UserListWhitelist ☃ = ☃.func_197028_i().func_184103_al().func_152599_k();
      int ☃x = 0;

      for(GameProfile ☃xx : ☃) {
         if (☃.func_152705_a(☃xx)) {
            UserListWhitelistEntry ☃xxx = new UserListWhitelistEntry(☃xx);
            ☃.func_199042_b(☃xxx);
            ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.remove.success", TextComponentUtils.func_197679_a(☃xx)), true);
            ++☃x;
         }
      }

      if (☃x == 0) {
         throw field_198890_d.create();
      } else {
         ☃.func_197028_i().func_205743_a(☃);
         return ☃x;
      }
   }

   private static int func_198884_b(CommandSource var0) throws CommandSyntaxException {
      PlayerList ☃ = ☃.func_197028_i().func_184103_al();
      if (☃.func_72383_n()) {
         throw field_198887_a.create();
      } else {
         ☃.func_72371_a(true);
         ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.enabled"), true);
         ☃.func_197028_i().func_205743_a(☃);
         return 1;
      }
   }

   private static int func_198885_c(CommandSource var0) throws CommandSyntaxException {
      PlayerList ☃ = ☃.func_197028_i().func_184103_al();
      if (!☃.func_72383_n()) {
         throw field_198888_b.create();
      } else {
         ☃.func_72371_a(false);
         ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.disabled"), true);
         return 1;
      }
   }

   private static int func_198886_d(CommandSource var0) {
      String[] ☃ = ☃.func_197028_i().func_184103_al().func_152598_l();
      if (☃.length == 0) {
         ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.none"), false);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.whitelist.list", ☃.length, String.join(", ", ☃)), false);
      }

      return ☃.length;
   }
}
