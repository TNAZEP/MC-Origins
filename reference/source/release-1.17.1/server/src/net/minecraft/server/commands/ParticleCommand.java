package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ParticleCommand {
   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.particle.failed"));

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("particle")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("name", ParticleArgument.particle())
                  .executes(
                     var0x -> sendParticles(
                           var0x.getSource(),
                           ParticleArgument.getParticle(var0x, "name"),
                           var0x.getSource().getPosition(),
                           Vec3.ZERO,
                           0.0F,
                           0,
                           false,
                           var0x.getSource().getServer().getPlayerList().getPlayers()
                        )
                  )
                  .then(
                     Commands.argument("pos", Vec3Argument.vec3())
                        .executes(
                           var0x -> sendParticles(
                                 var0x.getSource(),
                                 ParticleArgument.getParticle(var0x, "name"),
                                 Vec3Argument.getVec3(var0x, "pos"),
                                 Vec3.ZERO,
                                 0.0F,
                                 0,
                                 false,
                                 var0x.getSource().getServer().getPlayerList().getPlayers()
                              )
                        )
                        .then(
                           Commands.argument("delta", Vec3Argument.vec3(false))
                              .then(
                                 Commands.argument("speed", FloatArgumentType.floatArg(0.0F))
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("count", IntegerArgumentType.integer(0))
                                                .executes(
                                                   var0x -> sendParticles(
                                                         (CommandSourceStack)var0x.getSource(),
                                                         ParticleArgument.getParticle(var0x, "name"),
                                                         Vec3Argument.getVec3(var0x, "pos"),
                                                         Vec3Argument.getVec3(var0x, "delta"),
                                                         FloatArgumentType.getFloat(var0x, "speed"),
                                                         IntegerArgumentType.getInteger(var0x, "count"),
                                                         false,
                                                         ((CommandSourceStack)var0x.getSource()).getServer().getPlayerList().getPlayers()
                                                      )
                                                ))
                                             .then(
                                                Commands.literal("force")
                                                   .executes(
                                                      var0x -> sendParticles(
                                                            var0x.getSource(),
                                                            ParticleArgument.getParticle(var0x, "name"),
                                                            Vec3Argument.getVec3(var0x, "pos"),
                                                            Vec3Argument.getVec3(var0x, "delta"),
                                                            FloatArgumentType.getFloat(var0x, "speed"),
                                                            IntegerArgumentType.getInteger(var0x, "count"),
                                                            true,
                                                            var0x.getSource().getServer().getPlayerList().getPlayers()
                                                         )
                                                   )
                                                   .then(
                                                      Commands.argument("viewers", EntityArgument.players())
                                                         .executes(
                                                            var0x -> sendParticles(
                                                                  var0x.getSource(),
                                                                  ParticleArgument.getParticle(var0x, "name"),
                                                                  Vec3Argument.getVec3(var0x, "pos"),
                                                                  Vec3Argument.getVec3(var0x, "delta"),
                                                                  FloatArgumentType.getFloat(var0x, "speed"),
                                                                  IntegerArgumentType.getInteger(var0x, "count"),
                                                                  true,
                                                                  EntityArgument.getPlayers(var0x, "viewers")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             Commands.literal("normal")
                                                .executes(
                                                   var0x -> sendParticles(
                                                         var0x.getSource(),
                                                         ParticleArgument.getParticle(var0x, "name"),
                                                         Vec3Argument.getVec3(var0x, "pos"),
                                                         Vec3Argument.getVec3(var0x, "delta"),
                                                         FloatArgumentType.getFloat(var0x, "speed"),
                                                         IntegerArgumentType.getInteger(var0x, "count"),
                                                         false,
                                                         var0x.getSource().getServer().getPlayerList().getPlayers()
                                                      )
                                                )
                                                .then(
                                                   Commands.argument("viewers", EntityArgument.players())
                                                      .executes(
                                                         var0x -> sendParticles(
                                                               var0x.getSource(),
                                                               ParticleArgument.getParticle(var0x, "name"),
                                                               Vec3Argument.getVec3(var0x, "pos"),
                                                               Vec3Argument.getVec3(var0x, "delta"),
                                                               FloatArgumentType.getFloat(var0x, "speed"),
                                                               IntegerArgumentType.getInteger(var0x, "count"),
                                                               false,
                                                               EntityArgument.getPlayers(var0x, "viewers")
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

   private static int sendParticles(
      CommandSourceStack var0, ParticleOptions var1, Vec3 var2, Vec3 var3, float var4, int var5, boolean var6, Collection<ServerPlayer> var7
   ) throws CommandSyntaxException {
      int â˜ƒ = 0;

      for(ServerPlayer â˜ƒx : â˜ƒ) {
         if (â˜ƒ.getLevel().sendParticles(â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, (double)â˜ƒ)) {
            ++â˜ƒ;
         }
      }

      if (â˜ƒ == 0) {
         throw ERROR_FAILED.create();
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.particle.success", Registry.PARTICLE_TYPE.getKey(â˜ƒ.getType()).toString()), true);
         return â˜ƒ;
      }
   }
}
