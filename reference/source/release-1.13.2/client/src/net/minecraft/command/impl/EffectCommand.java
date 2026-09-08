package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.PotionArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;

public class EffectCommand {
   private static final SimpleCommandExceptionType field_198361_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.effect.give.failed"));
   private static final SimpleCommandExceptionType field_198362_b = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.effect.clear.everything.failed")
   );
   private static final SimpleCommandExceptionType field_198363_c = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.effect.clear.specific.failed")
   );

   public static void func_198353_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("effect")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("clear")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                        .executes(var0x -> func_198354_a(var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets")))
                        .then(
                           Commands.func_197056_a("effect", PotionArgument.func_197126_a())
                              .executes(
                                 var0x -> func_198355_a(
                                       var0x.getSource(), EntityArgument.func_197097_b(var0x, "targets"), PotionArgument.func_197125_a(var0x, "effect")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("give")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197093_b())
                        .then(
                           Commands.func_197056_a("effect", PotionArgument.func_197126_a())
                              .executes(
                                 var0x -> func_198360_a(
                                       var0x.getSource(),
                                       EntityArgument.func_197097_b(var0x, "targets"),
                                       PotionArgument.func_197125_a(var0x, "effect"),
                                       null,
                                       0,
                                       true
                                    )
                              )
                              .then(
                                 Commands.func_197056_a("seconds", IntegerArgumentType.integer(1, 1000000))
                                    .executes(
                                       var0x -> func_198360_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197097_b(var0x, "targets"),
                                             PotionArgument.func_197125_a(var0x, "effect"),
                                             IntegerArgumentType.getInteger(var0x, "seconds"),
                                             0,
                                             true
                                          )
                                    )
                                    .then(
                                       Commands.func_197056_a("amplifier", IntegerArgumentType.integer(0, 255))
                                          .executes(
                                             var0x -> func_198360_a(
                                                   var0x.getSource(),
                                                   EntityArgument.func_197097_b(var0x, "targets"),
                                                   PotionArgument.func_197125_a(var0x, "effect"),
                                                   IntegerArgumentType.getInteger(var0x, "seconds"),
                                                   IntegerArgumentType.getInteger(var0x, "amplifier"),
                                                   true
                                                )
                                          )
                                          .then(
                                             Commands.func_197056_a("hideParticles", BoolArgumentType.bool())
                                                .executes(
                                                   var0x -> func_198360_a(
                                                         var0x.getSource(),
                                                         EntityArgument.func_197097_b(var0x, "targets"),
                                                         PotionArgument.func_197125_a(var0x, "effect"),
                                                         IntegerArgumentType.getInteger(var0x, "seconds"),
                                                         IntegerArgumentType.getInteger(var0x, "amplifier"),
                                                         !BoolArgumentType.getBool(var0x, "hideParticles")
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int func_198360_a(CommandSource var0, Collection<? extends Entity> var1, Potion var2, @Nullable Integer var3, int var4, boolean var5) throws CommandSyntaxException {
      int ☃x = 0;
      int ☃;
      if (☃ != null) {
         if (☃.func_76403_b()) {
            ☃ = ☃;
         } else {
            ☃ = ☃ * 20;
         }
      } else if (☃.func_76403_b()) {
         ☃ = 1;
      } else {
         ☃ = 600;
      }

      for(Entity ☃ : ☃) {
         if (☃ instanceof EntityLivingBase) {
            PotionEffect ☃x = new PotionEffect(☃, ☃, ☃, false, ☃);
            if (((EntityLivingBase)☃).func_195064_c(☃x)) {
               ++☃x;
            }
         }
      }

      if (☃x == 0) {
         throw field_198361_a.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.effect.give.success.single", ☃.func_199286_c(), ((Entity)☃.iterator().next()).func_145748_c_(), ☃ / 20),
               true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.effect.give.success.multiple", ☃.func_199286_c(), ☃.size(), ☃ / 20), true);
         }

         return ☃x;
      }
   }

   private static int func_198354_a(CommandSource var0, Collection<? extends Entity> var1) throws CommandSyntaxException {
      int ☃ = 0;

      for(Entity ☃x : ☃) {
         if (☃x instanceof EntityLivingBase && ((EntityLivingBase)☃x).func_195061_cb()) {
            ++☃;
         }
      }

      if (☃ == 0) {
         throw field_198362_b.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.effect.clear.everything.success.single", ((Entity)☃.iterator().next()).func_145748_c_()), true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.effect.clear.everything.success.multiple", ☃.size()), true);
         }

         return ☃;
      }
   }

   private static int func_198355_a(CommandSource var0, Collection<? extends Entity> var1, Potion var2) throws CommandSyntaxException {
      int ☃ = 0;

      for(Entity ☃x : ☃) {
         if (☃x instanceof EntityLivingBase && ((EntityLivingBase)☃x).func_195063_d(☃)) {
            ++☃;
         }
      }

      if (☃ == 0) {
         throw field_198363_c.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation("commands.effect.clear.specific.success.single", ☃.func_199286_c(), ((Entity)☃.iterator().next()).func_145748_c_()),
               true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.effect.clear.specific.success.multiple", ☃.func_199286_c(), ☃.size()), true);
         }

         return ☃;
      }
   }
}
