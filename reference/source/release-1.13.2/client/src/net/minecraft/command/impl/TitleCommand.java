package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Locale;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;

public class TitleCommand {
   public static void func_198839_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("title")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                  .then(Commands.func_197057_a("clear").executes(var0x -> func_198840_a(var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"))))
                  .then(Commands.func_197057_a("reset").executes(var0x -> func_198844_b(var0x.getSource(), EntityArgument.func_197090_e(var0x, "targets"))))
                  .then(
                     Commands.func_197057_a("title")
                        .then(
                           Commands.func_197056_a("title", ComponentArgument.func_197067_a())
                              .executes(
                                 var0x -> func_198846_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       ComponentArgument.func_197068_a(var0x, "title"),
                                       SPacketTitle.Type.TITLE
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("subtitle")
                        .then(
                           Commands.func_197056_a("title", ComponentArgument.func_197067_a())
                              .executes(
                                 var0x -> func_198846_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       ComponentArgument.func_197068_a(var0x, "title"),
                                       SPacketTitle.Type.SUBTITLE
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("actionbar")
                        .then(
                           Commands.func_197056_a("title", ComponentArgument.func_197067_a())
                              .executes(
                                 var0x -> func_198846_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197090_e(var0x, "targets"),
                                       ComponentArgument.func_197068_a(var0x, "title"),
                                       SPacketTitle.Type.ACTIONBAR
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.func_197057_a("times")
                        .then(
                           Commands.func_197056_a("fadeIn", IntegerArgumentType.integer(0))
                              .then(
                                 Commands.func_197056_a("stay", IntegerArgumentType.integer(0))
                                    .then(
                                       Commands.func_197056_a("fadeOut", IntegerArgumentType.integer(0))
                                          .executes(
                                             var0x -> func_198845_a(
                                                   var0x.getSource(),
                                                   EntityArgument.func_197090_e(var0x, "targets"),
                                                   IntegerArgumentType.getInteger(var0x, "fadeIn"),
                                                   IntegerArgumentType.getInteger(var0x, "stay"),
                                                   IntegerArgumentType.getInteger(var0x, "fadeOut")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198840_a(CommandSource var0, Collection<EntityPlayerMP> var1) {
      SPacketTitle ☃ = new SPacketTitle(SPacketTitle.Type.CLEAR, null);

      for(EntityPlayerMP ☃x : ☃) {
         ☃x.field_71135_a.func_147359_a(☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.cleared.single", ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.cleared.multiple", ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198844_b(CommandSource var0, Collection<EntityPlayerMP> var1) {
      SPacketTitle ☃ = new SPacketTitle(SPacketTitle.Type.RESET, null);

      for(EntityPlayerMP ☃x : ☃) {
         ☃x.field_71135_a.func_147359_a(☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.reset.single", ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.reset.multiple", ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198846_a(CommandSource var0, Collection<EntityPlayerMP> var1, ITextComponent var2, SPacketTitle.Type var3) throws CommandSyntaxException {
      for(EntityPlayerMP ☃ : ☃) {
         ☃.field_71135_a.func_147359_a(new SPacketTitle(☃, TextComponentUtils.func_197680_a(☃, ☃, ☃)));
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.title.show." + ☃.name().toLowerCase(Locale.ROOT) + ".single", ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
            ),
            true
         );
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.show." + ☃.name().toLowerCase(Locale.ROOT) + ".multiple", ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198845_a(CommandSource var0, Collection<EntityPlayerMP> var1, int var2, int var3, int var4) {
      SPacketTitle ☃ = new SPacketTitle(☃, ☃, ☃);

      for(EntityPlayerMP ☃x : ☃) {
         ☃x.field_71135_a.func_147359_a(☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.times.single", ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()), true);
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.title.times.multiple", ☃.size()), true);
      }

      return ☃.size();
   }
}
