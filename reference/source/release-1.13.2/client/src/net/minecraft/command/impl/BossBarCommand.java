package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.CustomBossEvent;
import net.minecraft.server.CustomBossEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.BossInfo;

public class BossBarCommand {
   private static final DynamicCommandExceptionType field_201432_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.bossbar.create.failed", var0)
   );
   private static final DynamicCommandExceptionType field_201433_c = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.bossbar.unknown", var0)
   );
   private static final SimpleCommandExceptionType field_201434_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.players.unchanged")
   );
   private static final SimpleCommandExceptionType field_201435_e = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.name.unchanged")
   );
   private static final SimpleCommandExceptionType field_201436_f = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.color.unchanged")
   );
   private static final SimpleCommandExceptionType field_201437_g = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.style.unchanged")
   );
   private static final SimpleCommandExceptionType field_201438_h = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.value.unchanged")
   );
   private static final SimpleCommandExceptionType field_201439_i = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.max.unchanged")
   );
   private static final SimpleCommandExceptionType field_201440_j = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.visibility.unchanged.hidden")
   );
   private static final SimpleCommandExceptionType field_201441_k = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.bossbar.set.visibility.unchanged.visible")
   );
   public static final SuggestionProvider<CommandSource> field_201431_a = (var0, var1) -> ISuggestionProvider.func_197014_a(
         var0.getSource().func_197028_i().func_201300_aS().func_201377_a(), var1
      );

   public static void func_201413_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("bossbar")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("add")
                  .then(
                     Commands.func_197056_a("id", ResourceLocationArgument.func_197197_a())
                        .then(
                           Commands.func_197056_a("name", ComponentArgument.func_197067_a())
                              .executes(
                                 var0x -> func_201400_a(
                                       var0x.getSource(), ResourceLocationArgument.func_197195_e(var0x, "id"), ComponentArgument.func_197068_a(var0x, "name")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("remove")
                  .then(
                     Commands.func_197056_a("id", ResourceLocationArgument.func_197197_a())
                        .suggests(field_201431_a)
                        .executes(var0x -> func_201407_e(var0x.getSource(), func_201416_a(var0x)))
                  )
            )
            .then(Commands.func_197057_a("list").executes(var0x -> func_201428_a(var0x.getSource())))
            .then(
               Commands.func_197057_a("set")
                  .then(
                     Commands.func_197056_a("id", ResourceLocationArgument.func_197197_a())
                        .suggests(field_201431_a)
                        .then(
                           Commands.func_197057_a("name")
                              .then(
                                 Commands.func_197056_a("name", ComponentArgument.func_197067_a())
                                    .executes(var0x -> func_201420_a(var0x.getSource(), func_201416_a(var0x), ComponentArgument.func_197068_a(var0x, "name")))
                              )
                        )
                        .then(
                           Commands.func_197057_a("color")
                              .then(
                                 Commands.func_197057_a("pink").executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.PINK))
                              )
                              .then(
                                 Commands.func_197057_a("blue").executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.BLUE))
                              )
                              .then(Commands.func_197057_a("red").executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.RED)))
                              .then(
                                 Commands.func_197057_a("green")
                                    .executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.GREEN))
                              )
                              .then(
                                 Commands.func_197057_a("yellow")
                                    .executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.YELLOW))
                              )
                              .then(
                                 Commands.func_197057_a("purple")
                                    .executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.PURPLE))
                              )
                              .then(
                                 Commands.func_197057_a("white")
                                    .executes(var0x -> func_201415_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Color.WHITE))
                              )
                        )
                        .then(
                           Commands.func_197057_a("style")
                              .then(
                                 Commands.func_197057_a("progress")
                                    .executes(var0x -> func_201390_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Overlay.PROGRESS))
                              )
                              .then(
                                 Commands.func_197057_a("notched_6")
                                    .executes(var0x -> func_201390_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Overlay.NOTCHED_6))
                              )
                              .then(
                                 Commands.func_197057_a("notched_10")
                                    .executes(var0x -> func_201390_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Overlay.NOTCHED_10))
                              )
                              .then(
                                 Commands.func_197057_a("notched_12")
                                    .executes(var0x -> func_201390_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Overlay.NOTCHED_12))
                              )
                              .then(
                                 Commands.func_197057_a("notched_20")
                                    .executes(var0x -> func_201390_a(var0x.getSource(), func_201416_a(var0x), BossInfo.Overlay.NOTCHED_20))
                              )
                        )
                        .then(
                           Commands.func_197057_a("value")
                              .then(
                                 Commands.func_197056_a("value", IntegerArgumentType.integer(0))
                                    .executes(var0x -> func_201397_a(var0x.getSource(), func_201416_a(var0x), IntegerArgumentType.getInteger(var0x, "value")))
                              )
                        )
                        .then(
                           Commands.func_197057_a("max")
                              .then(
                                 Commands.func_197056_a("max", IntegerArgumentType.integer(1))
                                    .executes(var0x -> func_201394_b(var0x.getSource(), func_201416_a(var0x), IntegerArgumentType.getInteger(var0x, "max")))
                              )
                        )
                        .then(
                           Commands.func_197057_a("visible")
                              .then(
                                 Commands.func_197056_a("visible", BoolArgumentType.bool())
                                    .executes(var0x -> func_201410_a(var0x.getSource(), func_201416_a(var0x), BoolArgumentType.getBool(var0x, "visible")))
                              )
                        )
                        .then(
                           Commands.func_197057_a("players")
                              .executes(var0x -> func_201405_a(var0x.getSource(), func_201416_a(var0x), Collections.emptyList()))
                              .then(
                                 Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                                    .executes(var0x -> func_201405_a(var0x.getSource(), func_201416_a(var0x), EntityArgument.func_201309_d(var0x, "targets")))
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("get")
                  .then(
                     Commands.func_197056_a("id", ResourceLocationArgument.func_197197_a())
                        .suggests(field_201431_a)
                        .then(Commands.func_197057_a("value").executes(var0x -> func_201414_a(var0x.getSource(), func_201416_a(var0x))))
                        .then(Commands.func_197057_a("max").executes(var0x -> func_201402_b(var0x.getSource(), func_201416_a(var0x))))
                        .then(Commands.func_197057_a("visible").executes(var0x -> func_201389_c(var0x.getSource(), func_201416_a(var0x))))
                        .then(Commands.func_197057_a("players").executes(var0x -> func_201425_d(var0x.getSource(), func_201416_a(var0x))))
                  )
            )
      );
   }

   private static int func_201414_a(CommandSource var0, CustomBossEvent var1) {
      ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.get.value", ☃.func_201369_e(), ☃.func_201365_c()), true);
      return ☃.func_201365_c();
   }

   private static int func_201402_b(CommandSource var0, CustomBossEvent var1) {
      ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.get.max", ☃.func_201369_e(), ☃.func_201367_d()), true);
      return ☃.func_201367_d();
   }

   private static int func_201389_c(CommandSource var0, CustomBossEvent var1) {
      if (☃.func_201359_g()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.get.visible.visible", ☃.func_201369_e()), true);
         return 1;
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.get.visible.hidden", ☃.func_201369_e()), true);
         return 0;
      }
   }

   private static int func_201425_d(CommandSource var0, CustomBossEvent var1) {
      if (☃.func_186757_c().isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.get.players.none", ☃.func_201369_e()), true);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.bossbar.get.players.some",
               ☃.func_201369_e(),
               ☃.func_186757_c().size(),
               TextComponentUtils.func_197677_b(☃.func_186757_c(), EntityPlayer::func_145748_c_)
            ),
            true
         );
      }

      return ☃.func_186757_c().size();
   }

   private static int func_201410_a(CommandSource var0, CustomBossEvent var1, boolean var2) throws CommandSyntaxException {
      if (☃.func_201359_g() == ☃) {
         if (☃) {
            throw field_201441_k.create();
         } else {
            throw field_201440_j.create();
         }
      } else {
         ☃.func_186758_d(☃);
         if (☃) {
            ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.visible.success.visible", ☃.func_201369_e()), true);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.visible.success.hidden", ☃.func_201369_e()), true);
         }

         return 0;
      }
   }

   private static int func_201397_a(CommandSource var0, CustomBossEvent var1, int var2) throws CommandSyntaxException {
      if (☃.func_201365_c() == ☃) {
         throw field_201438_h.create();
      } else {
         ☃.func_201362_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.value.success", ☃.func_201369_e(), ☃), true);
         return ☃;
      }
   }

   private static int func_201394_b(CommandSource var0, CustomBossEvent var1, int var2) throws CommandSyntaxException {
      if (☃.func_201367_d() == ☃) {
         throw field_201439_i.create();
      } else {
         ☃.func_201366_b(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.max.success", ☃.func_201369_e(), ☃), true);
         return ☃;
      }
   }

   private static int func_201415_a(CommandSource var0, CustomBossEvent var1, BossInfo.Color var2) throws CommandSyntaxException {
      if (☃.func_186736_g().equals(☃)) {
         throw field_201436_f.create();
      } else {
         ☃.func_186745_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.color.success", ☃.func_201369_e()), true);
         return 0;
      }
   }

   private static int func_201390_a(CommandSource var0, CustomBossEvent var1, BossInfo.Overlay var2) throws CommandSyntaxException {
      if (☃.func_186740_h().equals(☃)) {
         throw field_201437_g.create();
      } else {
         ☃.func_186746_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.style.success", ☃.func_201369_e()), true);
         return 0;
      }
   }

   private static int func_201420_a(CommandSource var0, CustomBossEvent var1, ITextComponent var2) throws CommandSyntaxException {
      ITextComponent ☃ = TextComponentUtils.func_197680_a(☃, ☃, null);
      if (☃.func_186744_e().equals(☃)) {
         throw field_201435_e.create();
      } else {
         ☃.func_186739_a(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.name.success", ☃.func_201369_e()), true);
         return 0;
      }
   }

   private static int func_201405_a(CommandSource var0, CustomBossEvent var1, Collection<EntityPlayerMP> var2) throws CommandSyntaxException {
      boolean ☃ = ☃.func_201368_a(☃);
      if (!☃) {
         throw field_201434_d.create();
      } else {
         if (☃.func_186757_c().isEmpty()) {
            ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.set.players.success.none", ☃.func_201369_e()), true);
         } else {
            ☃.func_197030_a(
               new TextComponentTranslation(
                  "commands.bossbar.set.players.success.some", ☃.func_201369_e(), ☃.size(), TextComponentUtils.func_197677_b(☃, EntityPlayer::func_145748_c_)
               ),
               true
            );
         }

         return ☃.func_186757_c().size();
      }
   }

   private static int func_201428_a(CommandSource var0) {
      Collection<CustomBossEvent> ☃ = ☃.func_197028_i().func_201300_aS().func_201378_b();
      if (☃.isEmpty()) {
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.list.bars.none"), false);
      } else {
         ☃.func_197030_a(
            new TextComponentTranslation("commands.bossbar.list.bars.some", ☃.size(), TextComponentUtils.func_197677_b(☃, CustomBossEvent::func_201369_e)),
            false
         );
      }

      return ☃.size();
   }

   private static int func_201400_a(CommandSource var0, ResourceLocation var1, ITextComponent var2) throws CommandSyntaxException {
      CustomBossEvents ☃ = ☃.func_197028_i().func_201300_aS();
      if (☃.func_201384_a(☃) != null) {
         throw field_201432_b.create(☃.toString());
      } else {
         CustomBossEvent ☃ = ☃.func_201379_a(☃, TextComponentUtils.func_197680_a(☃, ☃, null));
         ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.create.success", ☃.func_201369_e()), true);
         return ☃.func_201378_b().size();
      }
   }

   private static int func_201407_e(CommandSource var0, CustomBossEvent var1) {
      CustomBossEvents ☃ = ☃.func_197028_i().func_201300_aS();
      ☃.func_201360_b();
      ☃.func_201385_a(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.bossbar.remove.success", ☃.func_201369_e()), true);
      return ☃.func_201378_b().size();
   }

   public static CustomBossEvent func_201416_a(CommandContext<CommandSource> var0) throws CommandSyntaxException {
      ResourceLocation ☃ = ResourceLocationArgument.func_197195_e(☃, "id");
      CustomBossEvent ☃x = ☃.getSource().func_197028_i().func_201300_aS().func_201384_a(☃);
      if (☃x == null) {
         throw field_201433_c.create(☃.toString());
      } else {
         return ☃x;
      }
   }
}
