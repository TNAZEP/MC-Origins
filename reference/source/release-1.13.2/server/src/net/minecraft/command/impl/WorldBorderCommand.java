package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec2Argument;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.border.WorldBorder;

public class WorldBorderCommand {
   private static final SimpleCommandExceptionType field_198911_a = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.center.failed")
   );
   private static final SimpleCommandExceptionType field_198912_b = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.set.failed.nochange")
   );
   private static final SimpleCommandExceptionType field_198913_c = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.set.failed.small.")
   );
   private static final SimpleCommandExceptionType field_198914_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.set.failed.big.")
   );
   private static final SimpleCommandExceptionType field_198915_e = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.warning.time.failed")
   );
   private static final SimpleCommandExceptionType field_198916_f = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.warning.distance.failed")
   );
   private static final SimpleCommandExceptionType field_198917_g = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.damage.buffer.failed")
   );
   private static final SimpleCommandExceptionType field_198918_h = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.worldborder.damage.amount.failed")
   );

   public static void func_198894_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("worldborder")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("add")
                  .then(
                     Commands.func_197056_a("distance", FloatArgumentType.floatArg(-6.0E7F, 6.0E7F))
                        .executes(
                           var0x -> func_198895_a(
                                 var0x.getSource(),
                                 var0x.getSource().func_197023_e().func_175723_af().func_177741_h() + (double)FloatArgumentType.getFloat(var0x, "distance"),
                                 0L
                              )
                        )
                        .then(
                           Commands.func_197056_a("time", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> func_198895_a(
                                       var0x.getSource(),
                                       var0x.getSource().func_197023_e().func_175723_af().func_177741_h()
                                          + (double)FloatArgumentType.getFloat(var0x, "distance"),
                                       var0x.getSource().func_197023_e().func_175723_af().func_177732_i()
                                          + (long)IntegerArgumentType.getInteger(var0x, "time") * 1000L
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("set")
                  .then(
                     Commands.func_197056_a("distance", FloatArgumentType.floatArg(-6.0E7F, 6.0E7F))
                        .executes(var0x -> func_198895_a(var0x.getSource(), (double)FloatArgumentType.getFloat(var0x, "distance"), 0L))
                        .then(
                           Commands.func_197056_a("time", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> func_198895_a(
                                       var0x.getSource(),
                                       (double)FloatArgumentType.getFloat(var0x, "distance"),
                                       (long)IntegerArgumentType.getInteger(var0x, "time") * 1000L
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("center")
                  .then(
                     Commands.func_197056_a("pos", Vec2Argument.func_197296_a())
                        .executes(var0x -> func_198896_a(var0x.getSource(), Vec2Argument.func_197295_a(var0x, "pos")))
                  )
            )
            .then(
               Commands.func_197057_a("damage")
                  .then(
                     Commands.func_197057_a("amount")
                        .then(
                           Commands.func_197056_a("damagePerBlock", FloatArgumentType.floatArg(0.0F))
                              .executes(var0x -> func_198904_b(var0x.getSource(), FloatArgumentType.getFloat(var0x, "damagePerBlock")))
                        )
                  )
                  .then(
                     Commands.func_197057_a("buffer")
                        .then(
                           Commands.func_197056_a("distance", FloatArgumentType.floatArg(0.0F))
                              .executes(var0x -> func_198898_a(var0x.getSource(), FloatArgumentType.getFloat(var0x, "distance")))
                        )
                  )
            )
            .then(Commands.func_197057_a("get").executes(var0x -> func_198910_a(var0x.getSource())))
            .then(
               Commands.func_197057_a("warning")
                  .then(
                     Commands.func_197057_a("distance")
                        .then(
                           Commands.func_197056_a("distance", IntegerArgumentType.integer(0))
                              .executes(var0x -> func_198899_b(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "distance")))
                        )
                  )
                  .then(
                     Commands.func_197057_a("time")
                        .then(
                           Commands.func_197056_a("time", IntegerArgumentType.integer(0))
                              .executes(var0x -> func_198902_a(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                        )
                  )
            )
      );
   }

   private static int func_198898_a(CommandSource var0, float var1) throws CommandSyntaxException {
      WorldBorder ☃ = ☃.func_197023_e().func_175723_af();
      if (☃.func_177742_m() == (double)☃) {
         throw field_198917_g.create();
      } else {
         ☃.func_177724_b((double)☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", ☃)), true);
         return (int)☃;
      }
   }

   private static int func_198904_b(CommandSource var0, float var1) throws CommandSyntaxException {
      WorldBorder ☃ = ☃.func_197023_e().func_175723_af();
      if (☃.func_177727_n() == (double)☃) {
         throw field_198918_h.create();
      } else {
         ☃.func_177744_c((double)☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", ☃)), true);
         return (int)☃;
      }
   }

   private static int func_198902_a(CommandSource var0, int var1) throws CommandSyntaxException {
      WorldBorder ☃ = ☃.func_197023_e().func_175723_af();
      if (☃.func_177740_p() == ☃) {
         throw field_198915_e.create();
      } else {
         ☃.func_177723_b(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.worldborder.warning.time.success", ☃), true);
         return ☃;
      }
   }

   private static int func_198899_b(CommandSource var0, int var1) throws CommandSyntaxException {
      WorldBorder ☃ = ☃.func_197023_e().func_175723_af();
      if (☃.func_177748_q() == ☃) {
         throw field_198916_f.create();
      } else {
         ☃.func_177747_c(☃);
         ☃.func_197030_a(new TextComponentTranslation("commands.worldborder.warning.distance.success", ☃), true);
         return ☃;
      }
   }

   private static int func_198910_a(CommandSource var0) {
      double ☃ = ☃.func_197023_e().func_175723_af().func_177741_h();
      ☃.func_197030_a(new TextComponentTranslation("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", ☃)), false);
      return MathHelper.func_76128_c(☃ + 0.5);
   }

   private static int func_198896_a(CommandSource var0, Vec2f var1) throws CommandSyntaxException {
      WorldBorder ☃ = ☃.func_197023_e().func_175723_af();
      if (☃.func_177731_f() == (double)☃.field_189982_i && ☃.func_177721_g() == (double)☃.field_189983_j) {
         throw field_198911_a.create();
      } else {
         ☃.func_177739_c((double)☃.field_189982_i, (double)☃.field_189983_j);
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", ☃.field_189982_i), String.format("%.2f", ☃.field_189983_j)
            ),
            true
         );
         return 0;
      }
   }

   private static int func_198895_a(CommandSource var0, double var1, long var3) throws CommandSyntaxException {
      WorldBorder ☃ = ☃.func_197023_e().func_175723_af();
      double ☃x = ☃.func_177741_h();
      if (☃x == ☃) {
         throw field_198912_b.create();
      } else if (☃ < 1.0) {
         throw field_198913_c.create();
      } else if (☃ > 6.0E7) {
         throw field_198914_d.create();
      } else {
         if (☃ > 0L) {
            ☃.func_177738_a(☃x, ☃, ☃);
            if (☃ > ☃x) {
               ☃.func_197030_a(
                  new TextComponentTranslation("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", ☃), Long.toString(☃ / 1000L)), true
               );
            } else {
               ☃.func_197030_a(
                  new TextComponentTranslation("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", ☃), Long.toString(☃ / 1000L)), true
               );
            }
         } else {
            ☃.func_177750_a(☃);
            ☃.func_197030_a(new TextComponentTranslation("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", ☃)), true);
         }

         return (int)(☃ - ☃x);
      }
   }
}
