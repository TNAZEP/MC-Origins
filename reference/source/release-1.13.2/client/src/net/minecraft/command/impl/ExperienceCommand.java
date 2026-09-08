package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;

public class ExperienceCommand {
   private static final SimpleCommandExceptionType field_198449_a = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.experience.set.points.invalid")
   );

   public static void func_198437_a(CommandDispatcher<CommandSource> var0) {
      LiteralCommandNode<CommandSource> ☃ = ☃.register(
         Commands.func_197057_a("experience")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197057_a("add")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a("amount", IntegerArgumentType.integer())
                                    .executes(
                                       var0x -> func_198448_a(
                                             (CommandSource)var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.POINTS
                                          )
                                    ))
                                 .then(
                                    Commands.func_197057_a("points")
                                       .executes(
                                          var0x -> func_198448_a(
                                                var0x.getSource(),
                                                EntityArgument.func_197090_e(var0x, "targets"),
                                                IntegerArgumentType.getInteger(var0x, "amount"),
                                                ExperienceCommand.Type.POINTS
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.func_197057_a("levels")
                                    .executes(
                                       var0x -> func_198448_a(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.LEVELS
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("set")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197094_d())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a("amount", IntegerArgumentType.integer(0))
                                    .executes(
                                       var0x -> func_198438_b(
                                             (CommandSource)var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.POINTS
                                          )
                                    ))
                                 .then(
                                    Commands.func_197057_a("points")
                                       .executes(
                                          var0x -> func_198438_b(
                                                var0x.getSource(),
                                                EntityArgument.func_197090_e(var0x, "targets"),
                                                IntegerArgumentType.getInteger(var0x, "amount"),
                                                ExperienceCommand.Type.POINTS
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.func_197057_a("levels")
                                    .executes(
                                       var0x -> func_198438_b(
                                             var0x.getSource(),
                                             EntityArgument.func_197090_e(var0x, "targets"),
                                             IntegerArgumentType.getInteger(var0x, "amount"),
                                             ExperienceCommand.Type.LEVELS
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("query")
                  .then(
                     Commands.func_197056_a("targets", EntityArgument.func_197096_c())
                        .then(
                           Commands.func_197057_a("points")
                              .executes(
                                 var0x -> func_198443_a(var0x.getSource(), EntityArgument.func_197089_d(var0x, "targets"), ExperienceCommand.Type.POINTS)
                              )
                        )
                        .then(
                           Commands.func_197057_a("levels")
                              .executes(
                                 var0x -> func_198443_a(var0x.getSource(), EntityArgument.func_197089_d(var0x, "targets"), ExperienceCommand.Type.LEVELS)
                              )
                        )
                  )
            )
      );
      ☃.register(Commands.func_197057_a("xp").requires(var0x -> var0x.func_197034_c(2)).redirect(☃));
   }

   private static int func_198443_a(CommandSource var0, EntityPlayerMP var1, ExperienceCommand.Type var2) {
      int ☃ = ☃.field_198433_f.applyAsInt(☃);
      ☃.func_197030_a(new TextComponentTranslation("commands.experience.query." + ☃.field_198432_e, ☃.func_145748_c_(), ☃), false);
      return ☃;
   }

   private static int func_198448_a(CommandSource var0, Collection<? extends EntityPlayerMP> var1, int var2, ExperienceCommand.Type var3) {
      for(EntityPlayerMP ☃ : ☃) {
         ☃.field_198430_c.accept(☃, ☃);
      }

      if (☃.size() == 1) {
         ☃.func_197030_a(
            new TextComponentTranslation(
               "commands.experience.add." + ☃.field_198432_e + ".success.single", ☃, ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
            ),
            true
         );
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.experience.add." + ☃.field_198432_e + ".success.multiple", ☃, ☃.size()), true);
      }

      return ☃.size();
   }

   private static int func_198438_b(CommandSource var0, Collection<? extends EntityPlayerMP> var1, int var2, ExperienceCommand.Type var3) throws CommandSyntaxException {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         if (☃.field_198431_d.test(☃x, ☃)) {
            ++☃;
         }
      }

      if (☃ == 0) {
         throw field_198449_a.create();
      } else {
         if (☃.size() == 1) {
            ☃.func_197030_a(
               new TextComponentTranslation(
                  "commands.experience.set." + ☃.field_198432_e + ".success.single", ☃, ((EntityPlayerMP)☃.iterator().next()).func_145748_c_()
               ),
               true
            );
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.experience.set." + ☃.field_198432_e + ".success.multiple", ☃, ☃.size()), true);
         }

         return ☃.size();
      }
   }

   static enum Type {
      POINTS("points", EntityPlayer::func_195068_e, (var0, var1) -> {
         if (var1 >= var0.func_71050_bK()) {
            return false;
         } else {
            var0.func_195394_a(var1);
            return true;
         }
      }, var0 -> MathHelper.func_76141_d(var0.field_71106_cc * (float)var0.func_71050_bK())),
      LEVELS("levels", EntityPlayerMP::func_82242_a, (var0, var1) -> {
         var0.func_195399_b(var1);
         return true;
      }, var0 -> var0.field_71068_ca);

      public final BiConsumer<EntityPlayerMP, Integer> field_198430_c;
      public final BiPredicate<EntityPlayerMP, Integer> field_198431_d;
      public final String field_198432_e;
      private final ToIntFunction<EntityPlayerMP> field_198433_f;

      private Type(String var3, BiConsumer<EntityPlayerMP, Integer> var4, BiPredicate<EntityPlayerMP, Integer> var5, ToIntFunction<EntityPlayerMP> var6) {
         this.field_198430_c = ☃;
         this.field_198432_e = ☃;
         this.field_198431_d = ☃;
         this.field_198433_f = ☃;
      }
   }
}
