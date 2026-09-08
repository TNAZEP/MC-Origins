package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec2;

public class WorldBorderCommand {
   private static final SimpleCommandExceptionType ERROR_SAME_CENTER = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.center.failed")
   );
   private static final SimpleCommandExceptionType ERROR_SAME_SIZE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.set.failed.nochange")
   );
   private static final SimpleCommandExceptionType ERROR_TOO_SMALL = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.set.failed.small")
   );
   private static final SimpleCommandExceptionType ERROR_TOO_BIG = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.set.failed.big", 5.999997E7F)
   );
   private static final SimpleCommandExceptionType ERROR_SAME_WARNING_TIME = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.warning.time.failed")
   );
   private static final SimpleCommandExceptionType ERROR_SAME_WARNING_DISTANCE = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.warning.distance.failed")
   );
   private static final SimpleCommandExceptionType ERROR_SAME_DAMAGE_BUFFER = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.damage.buffer.failed")
   );
   private static final SimpleCommandExceptionType ERROR_SAME_DAMAGE_AMOUNT = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.worldborder.damage.amount.failed")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("worldborder")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("add")
                  .then(
                     Commands.argument("distance", DoubleArgumentType.doubleArg(-5.999997E7F, 5.999997E7F))
                        .executes(
                           var0x -> setSize(
                                 var0x.getSource(),
                                 var0x.getSource().getLevel().getWorldBorder().getSize() + DoubleArgumentType.getDouble(var0x, "distance"),
                                 0L
                              )
                        )
                        .then(
                           Commands.argument("time", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> setSize(
                                       var0x.getSource(),
                                       var0x.getSource().getLevel().getWorldBorder().getSize() + DoubleArgumentType.getDouble(var0x, "distance"),
                                       var0x.getSource().getLevel().getWorldBorder().getLerpRemainingTime()
                                          + (long)IntegerArgumentType.getInteger(var0x, "time") * 1000L
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("set")
                  .then(
                     Commands.argument("distance", DoubleArgumentType.doubleArg(-5.999997E7F, 5.999997E7F))
                        .executes(var0x -> setSize(var0x.getSource(), DoubleArgumentType.getDouble(var0x, "distance"), 0L))
                        .then(
                           Commands.argument("time", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> setSize(
                                       var0x.getSource(),
                                       DoubleArgumentType.getDouble(var0x, "distance"),
                                       (long)IntegerArgumentType.getInteger(var0x, "time") * 1000L
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("center")
                  .then(Commands.argument("pos", Vec2Argument.vec2()).executes(var0x -> setCenter(var0x.getSource(), Vec2Argument.getVec2(var0x, "pos"))))
            )
            .then(
               Commands.literal("damage")
                  .then(
                     Commands.literal("amount")
                        .then(
                           Commands.argument("damagePerBlock", FloatArgumentType.floatArg(0.0F))
                              .executes(var0x -> setDamageAmount(var0x.getSource(), FloatArgumentType.getFloat(var0x, "damagePerBlock")))
                        )
                  )
                  .then(
                     Commands.literal("buffer")
                        .then(
                           Commands.argument("distance", FloatArgumentType.floatArg(0.0F))
                              .executes(var0x -> setDamageBuffer(var0x.getSource(), FloatArgumentType.getFloat(var0x, "distance")))
                        )
                  )
            )
            .then(Commands.literal("get").executes(var0x -> getSize(var0x.getSource())))
            .then(
               Commands.literal("warning")
                  .then(
                     Commands.literal("distance")
                        .then(
                           Commands.argument("distance", IntegerArgumentType.integer(0))
                              .executes(var0x -> setWarningDistance(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "distance")))
                        )
                  )
                  .then(
                     Commands.literal("time")
                        .then(
                           Commands.argument("time", IntegerArgumentType.integer(0))
                              .executes(var0x -> setWarningTime(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "time")))
                        )
                  )
            )
      );
   }

   private static int setDamageBuffer(CommandSourceStack var0, float var1) throws CommandSyntaxException {
      WorldBorder â˜ƒ = â˜ƒ.getLevel().getWorldBorder();
      if (â˜ƒ.getDamageSafeZone() == (double)â˜ƒ) {
         throw ERROR_SAME_DAMAGE_BUFFER.create();
      } else {
         â˜ƒ.setDamageSafeZone((double)â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", â˜ƒ)), true);
         return (int)â˜ƒ;
      }
   }

   private static int setDamageAmount(CommandSourceStack var0, float var1) throws CommandSyntaxException {
      WorldBorder â˜ƒ = â˜ƒ.getLevel().getWorldBorder();
      if (â˜ƒ.getDamagePerBlock() == (double)â˜ƒ) {
         throw ERROR_SAME_DAMAGE_AMOUNT.create();
      } else {
         â˜ƒ.setDamagePerBlock((double)â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", â˜ƒ)), true);
         return (int)â˜ƒ;
      }
   }

   private static int setWarningTime(CommandSourceStack var0, int var1) throws CommandSyntaxException {
      WorldBorder â˜ƒ = â˜ƒ.getLevel().getWorldBorder();
      if (â˜ƒ.getWarningTime() == â˜ƒ) {
         throw ERROR_SAME_WARNING_TIME.create();
      } else {
         â˜ƒ.setWarningTime(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.worldborder.warning.time.success", â˜ƒ), true);
         return â˜ƒ;
      }
   }

   private static int setWarningDistance(CommandSourceStack var0, int var1) throws CommandSyntaxException {
      WorldBorder â˜ƒ = â˜ƒ.getLevel().getWorldBorder();
      if (â˜ƒ.getWarningBlocks() == â˜ƒ) {
         throw ERROR_SAME_WARNING_DISTANCE.create();
      } else {
         â˜ƒ.setWarningBlocks(â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.worldborder.warning.distance.success", â˜ƒ), true);
         return â˜ƒ;
      }
   }

   private static int getSize(CommandSourceStack var0) {
      double â˜ƒ = â˜ƒ.getLevel().getWorldBorder().getSize();
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", â˜ƒ)), false);
      return Mth.floor(â˜ƒ + 0.5);
   }

   private static int setCenter(CommandSourceStack var0, Vec2 var1) throws CommandSyntaxException {
      WorldBorder â˜ƒ = â˜ƒ.getLevel().getWorldBorder();
      if (â˜ƒ.getCenterX() == (double)â˜ƒ.x && â˜ƒ.getCenterZ() == (double)â˜ƒ.y) {
         throw ERROR_SAME_CENTER.create();
      } else {
         â˜ƒ.setCenter((double)â˜ƒ.x, (double)â˜ƒ.y);
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", â˜ƒ.x), String.format("%.2f", â˜ƒ.y)), true
         );
         return 0;
      }
   }

   private static int setSize(CommandSourceStack var0, double var1, long var3) throws CommandSyntaxException {
      WorldBorder â˜ƒ = â˜ƒ.getLevel().getWorldBorder();
      double â˜ƒx = â˜ƒ.getSize();
      if (â˜ƒx == â˜ƒ) {
         throw ERROR_SAME_SIZE.create();
      } else if (â˜ƒ < 1.0) {
         throw ERROR_TOO_SMALL.create();
      } else if (â˜ƒ > 5.999997E7F) {
         throw ERROR_TOO_BIG.create();
      } else {
         if (â˜ƒ > 0L) {
            â˜ƒ.lerpSizeBetween(â˜ƒx, â˜ƒ, â˜ƒ);
            if (â˜ƒ > â˜ƒx) {
               â˜ƒ.sendSuccess(
                  new TranslatableComponent("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", â˜ƒ), Long.toString(â˜ƒ / 1000L)), true
               );
            } else {
               â˜ƒ.sendSuccess(
                  new TranslatableComponent("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", â˜ƒ), Long.toString(â˜ƒ / 1000L)), true
               );
            }
         } else {
            â˜ƒ.setSize(â˜ƒ);
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", â˜ƒ)), true);
         }

         return (int)(â˜ƒ - â˜ƒx);
      }
   }
}
