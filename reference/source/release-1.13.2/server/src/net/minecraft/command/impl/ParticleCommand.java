package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ParticleArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class ParticleCommand {
   private static final SimpleCommandExceptionType field_198569_a = new SimpleCommandExceptionType(new TextComponentTranslation("commands.particle.failed"));

   public static void func_198563_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("particle")
            .requires(var0x -> var0x.func_197034_c(2))
            .then(
               Commands.func_197056_a("name", ParticleArgument.func_197190_a())
                  .executes(
                     var0x -> func_198564_a(
                           var0x.getSource(),
                           ParticleArgument.func_197187_a(var0x, "name"),
                           var0x.getSource().func_197036_d(),
                           Vec3d.field_186680_a,
                           0.0F,
                           0,
                           false,
                           var0x.getSource().func_197028_i().func_184103_al().func_181057_v()
                        )
                  )
                  .then(
                     Commands.func_197056_a("pos", Vec3Argument.func_197301_a())
                        .executes(
                           var0x -> func_198564_a(
                                 var0x.getSource(),
                                 ParticleArgument.func_197187_a(var0x, "name"),
                                 Vec3Argument.func_197300_a(var0x, "pos"),
                                 Vec3d.field_186680_a,
                                 0.0F,
                                 0,
                                 false,
                                 var0x.getSource().func_197028_i().func_184103_al().func_181057_v()
                              )
                        )
                        .then(
                           Commands.func_197056_a("delta", Vec3Argument.func_197303_a(false))
                              .then(
                                 Commands.func_197056_a("speed", FloatArgumentType.floatArg(0.0F))
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.func_197056_a("count", IntegerArgumentType.integer(0))
                                                .executes(
                                                   var0x -> func_198564_a(
                                                         (CommandSource)var0x.getSource(),
                                                         ParticleArgument.func_197187_a(var0x, "name"),
                                                         Vec3Argument.func_197300_a(var0x, "pos"),
                                                         Vec3Argument.func_197300_a(var0x, "delta"),
                                                         FloatArgumentType.getFloat(var0x, "speed"),
                                                         IntegerArgumentType.getInteger(var0x, "count"),
                                                         false,
                                                         ((CommandSource)var0x.getSource()).func_197028_i().func_184103_al().func_181057_v()
                                                      )
                                                ))
                                             .then(
                                                Commands.func_197057_a("force")
                                                   .executes(
                                                      var0x -> func_198564_a(
                                                            var0x.getSource(),
                                                            ParticleArgument.func_197187_a(var0x, "name"),
                                                            Vec3Argument.func_197300_a(var0x, "pos"),
                                                            Vec3Argument.func_197300_a(var0x, "delta"),
                                                            FloatArgumentType.getFloat(var0x, "speed"),
                                                            IntegerArgumentType.getInteger(var0x, "count"),
                                                            true,
                                                            var0x.getSource().func_197028_i().func_184103_al().func_181057_v()
                                                         )
                                                   )
                                                   .then(
                                                      Commands.func_197056_a("viewers", EntityArgument.func_197094_d())
                                                         .executes(
                                                            var0x -> func_198564_a(
                                                                  var0x.getSource(),
                                                                  ParticleArgument.func_197187_a(var0x, "name"),
                                                                  Vec3Argument.func_197300_a(var0x, "pos"),
                                                                  Vec3Argument.func_197300_a(var0x, "delta"),
                                                                  FloatArgumentType.getFloat(var0x, "speed"),
                                                                  IntegerArgumentType.getInteger(var0x, "count"),
                                                                  true,
                                                                  EntityArgument.func_197090_e(var0x, "viewers")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             Commands.func_197057_a("normal")
                                                .executes(
                                                   var0x -> func_198564_a(
                                                         var0x.getSource(),
                                                         ParticleArgument.func_197187_a(var0x, "name"),
                                                         Vec3Argument.func_197300_a(var0x, "pos"),
                                                         Vec3Argument.func_197300_a(var0x, "delta"),
                                                         FloatArgumentType.getFloat(var0x, "speed"),
                                                         IntegerArgumentType.getInteger(var0x, "count"),
                                                         false,
                                                         var0x.getSource().func_197028_i().func_184103_al().func_181057_v()
                                                      )
                                                )
                                                .then(
                                                   Commands.func_197056_a("viewers", EntityArgument.func_197094_d())
                                                      .executes(
                                                         var0x -> func_198564_a(
                                                               var0x.getSource(),
                                                               ParticleArgument.func_197187_a(var0x, "name"),
                                                               Vec3Argument.func_197300_a(var0x, "pos"),
                                                               Vec3Argument.func_197300_a(var0x, "delta"),
                                                               FloatArgumentType.getFloat(var0x, "speed"),
                                                               IntegerArgumentType.getInteger(var0x, "count"),
                                                               false,
                                                               EntityArgument.func_197090_e(var0x, "viewers")
                                                            )
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

   private static int func_198564_a(
      CommandSource var0, IParticleData var1, Vec3d var2, Vec3d var3, float var4, int var5, boolean var6, Collection<EntityPlayerMP> var7
   ) throws CommandSyntaxException {
      int ☃ = 0;

      for(EntityPlayerMP ☃x : ☃) {
         if (☃.func_197023_e()
            .func_195600_a(☃x, ☃, ☃, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, (double)☃)) {
            ++☃;
         }
      }

      if (☃ == 0) {
         throw field_198569_a.create();
      } else {
         ☃.func_197030_a(new TextComponentTranslation("commands.particle.success", IRegistry.field_212632_u.func_177774_c(☃.func_197554_b()).toString()), true);
         return ☃;
      }
   }
}
