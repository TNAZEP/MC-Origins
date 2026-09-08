package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class TeleportCommand {
   private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.teleport.invalidPosition")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralCommandNode<CommandSourceStack> â˜ƒ = â˜ƒ.register(
         Commands.literal("teleport")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("location", Vec3Argument.vec3())
                  .executes(
                     var0x -> teleportToPos(
                           var0x.getSource(),
                           Collections.singleton(var0x.getSource().getEntityOrException()),
                           var0x.getSource().getLevel(),
                           Vec3Argument.getCoordinates(var0x, "location"),
                           WorldCoordinates.current(),
                           null
                        )
                  )
            )
            .then(
               Commands.argument("destination", EntityArgument.entity())
                  .executes(
                     var0x -> teleportToEntity(
                           var0x.getSource(), Collections.singleton(var0x.getSource().getEntityOrException()), EntityArgument.getEntity(var0x, "destination")
                        )
                  )
            )
            .then(
               Commands.argument("targets", EntityArgument.entities())
                  .then(
                     Commands.argument("location", Vec3Argument.vec3())
                        .executes(
                           var0x -> teleportToPos(
                                 var0x.getSource(),
                                 EntityArgument.getEntities(var0x, "targets"),
                                 var0x.getSource().getLevel(),
                                 Vec3Argument.getCoordinates(var0x, "location"),
                                 null,
                                 null
                              )
                        )
                        .then(
                           Commands.argument("rotation", RotationArgument.rotation())
                              .executes(
                                 var0x -> teleportToPos(
                                       var0x.getSource(),
                                       EntityArgument.getEntities(var0x, "targets"),
                                       var0x.getSource().getLevel(),
                                       Vec3Argument.getCoordinates(var0x, "location"),
                                       RotationArgument.getRotation(var0x, "rotation"),
                                       null
                                    )
                              )
                        )
                        .then(
                           Commands.literal("facing")
                              .then(
                                 Commands.literal("entity")
                                    .then(
                                       Commands.argument("facingEntity", EntityArgument.entity())
                                          .executes(
                                             var0x -> teleportToPos(
                                                   var0x.getSource(),
                                                   EntityArgument.getEntities(var0x, "targets"),
                                                   var0x.getSource().getLevel(),
                                                   Vec3Argument.getCoordinates(var0x, "location"),
                                                   null,
                                                   new TeleportCommand.LookAt(EntityArgument.getEntity(var0x, "facingEntity"), EntityAnchorArgument.Anchor.FEET)
                                                )
                                          )
                                          .then(
                                             Commands.argument("facingAnchor", EntityAnchorArgument.anchor())
                                                .executes(
                                                   var0x -> teleportToPos(
                                                         var0x.getSource(),
                                                         EntityArgument.getEntities(var0x, "targets"),
                                                         var0x.getSource().getLevel(),
                                                         Vec3Argument.getCoordinates(var0x, "location"),
                                                         null,
                                                         new TeleportCommand.LookAt(
                                                            EntityArgument.getEntity(var0x, "facingEntity"),
                                                            EntityAnchorArgument.getAnchor(var0x, "facingAnchor")
                                                         )
                                                      )
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.argument("facingLocation", Vec3Argument.vec3())
                                    .executes(
                                       var0x -> teleportToPos(
                                             var0x.getSource(),
                                             EntityArgument.getEntities(var0x, "targets"),
                                             var0x.getSource().getLevel(),
                                             Vec3Argument.getCoordinates(var0x, "location"),
                                             null,
                                             new TeleportCommand.LookAt(Vec3Argument.getVec3(var0x, "facingLocation"))
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.argument("destination", EntityArgument.entity())
                        .executes(
                           var0x -> teleportToEntity(
                                 var0x.getSource(), EntityArgument.getEntities(var0x, "targets"), EntityArgument.getEntity(var0x, "destination")
                              )
                        )
                  )
            )
      );
      â˜ƒ.register(Commands.literal("tp").requires(var0x -> var0x.hasPermission(2)).redirect(â˜ƒ));
   }

   private static int teleportToEntity(CommandSourceStack var0, Collection<? extends Entity> var1, Entity var2) throws CommandSyntaxException {
      for(Entity â˜ƒ : â˜ƒ) {
         performTeleport(
            â˜ƒ,
            â˜ƒ,
            (ServerLevel)â˜ƒ.level,
            â˜ƒ.getX(),
            â˜ƒ.getY(),
            â˜ƒ.getZ(),
            EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class),
            â˜ƒ.getYRot(),
            â˜ƒ.getXRot(),
            null
         );
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.teleport.success.entity.single", ((Entity)â˜ƒ.iterator().next()).getDisplayName(), â˜ƒ.getDisplayName()), true
         );
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.teleport.success.entity.multiple", â˜ƒ.size(), â˜ƒ.getDisplayName()), true);
      }

      return â˜ƒ.size();
   }

   private static int teleportToPos(
      CommandSourceStack var0,
      Collection<? extends Entity> var1,
      ServerLevel var2,
      Coordinates var3,
      @Nullable Coordinates var4,
      @Nullable TeleportCommand.LookAt var5
   ) throws CommandSyntaxException {
      Vec3 â˜ƒ = â˜ƒ.getPosition(â˜ƒ);
      Vec2 â˜ƒx = â˜ƒ == null ? null : â˜ƒ.getRotation(â˜ƒ);
      Set<ClientboundPlayerPositionPacket.RelativeArgument> â˜ƒxx = EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class);
      if (â˜ƒ.isXRelative()) {
         â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.X);
      }

      if (â˜ƒ.isYRelative()) {
         â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.Y);
      }

      if (â˜ƒ.isZRelative()) {
         â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.Z);
      }

      if (â˜ƒ == null) {
         â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT);
         â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT);
      } else {
         if (â˜ƒ.isXRelative()) {
            â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT);
         }

         if (â˜ƒ.isYRelative()) {
            â˜ƒxx.add(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT);
         }
      }

      for(Entity â˜ƒ : â˜ƒ) {
         if (â˜ƒ == null) {
            performTeleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒxx, â˜ƒ.getYRot(), â˜ƒ.getXRot(), â˜ƒ);
         } else {
            performTeleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒxx, â˜ƒx.y, â˜ƒx.x, â˜ƒ);
         }
      }

      if (â˜ƒ.size() == 1) {
         â˜ƒ.sendSuccess(
            new TranslatableComponent(
               "commands.teleport.success.location.single",
               ((Entity)â˜ƒ.iterator().next()).getDisplayName(),
               formatDouble(â˜ƒ.x),
               formatDouble(â˜ƒ.y),
               formatDouble(â˜ƒ.z)
            ),
            true
         );
      } else {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.teleport.success.location.multiple", â˜ƒ.size(), formatDouble(â˜ƒ.x), formatDouble(â˜ƒ.y), formatDouble(â˜ƒ.z)),
            true
         );
      }

      return â˜ƒ.size();
   }

   private static String formatDouble(double var0) {
      return String.format(Locale.ROOT, "%f", â˜ƒ);
   }

   private static void performTeleport(
      CommandSourceStack var0,
      Entity var1,
      ServerLevel var2,
      double var3,
      double var5,
      double var7,
      Set<ClientboundPlayerPositionPacket.RelativeArgument> var9,
      float var10,
      float var11,
      @Nullable TeleportCommand.LookAt var12
   ) throws CommandSyntaxException {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      if (!Level.isInSpawnableBounds(â˜ƒ)) {
         throw INVALID_POSITION.create();
      } else {
         float â˜ƒ = Mth.wrapDegrees(â˜ƒ);
         float â˜ƒx = Mth.wrapDegrees(â˜ƒ);
         if (â˜ƒ instanceof ServerPlayer) {
            ChunkPos â˜ƒxx = new ChunkPos(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
            â˜ƒ.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, â˜ƒxx, 1, â˜ƒ.getId());
            â˜ƒ.stopRiding();
            if (((ServerPlayer)â˜ƒ).isSleeping()) {
               ((ServerPlayer)â˜ƒ).stopSleepInBed(true, true);
            }

            if (â˜ƒ == â˜ƒ.level) {
               ((ServerPlayer)â˜ƒ).connection.teleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
            } else {
               ((ServerPlayer)â˜ƒ).teleportTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            }

            â˜ƒ.setYHeadRot(â˜ƒ);
         } else {
            float â˜ƒ = Mth.clamp(â˜ƒx, -90.0F, 90.0F);
            if (â˜ƒ == â˜ƒ.level) {
               â˜ƒ.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               â˜ƒ.setYHeadRot(â˜ƒ);
            } else {
               â˜ƒ.unRide();
               Entity â˜ƒ = â˜ƒ;
               â˜ƒ = â˜ƒ.getType().create(â˜ƒ);
               if (â˜ƒ == null) {
                  return;
               }

               â˜ƒ.restoreFrom(â˜ƒ);
               â˜ƒ.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               â˜ƒ.setYHeadRot(â˜ƒ);
               â˜ƒ.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
               â˜ƒ.addDuringTeleport(â˜ƒ);
            }
         }

         if (â˜ƒ != null) {
            â˜ƒ.perform(â˜ƒ, â˜ƒ);
         }

         if (!(â˜ƒ instanceof LivingEntity) || !((LivingEntity)â˜ƒ).isFallFlying()) {
            â˜ƒ.setDeltaMovement(â˜ƒ.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            â˜ƒ.setOnGround(true);
         }

         if (â˜ƒ instanceof PathfinderMob) {
            ((PathfinderMob)â˜ƒ).getNavigation().stop();
         }
      }
   }

   static class LookAt {
      private final Vec3 position;
      private final Entity entity;
      private final EntityAnchorArgument.Anchor anchor;

      public LookAt(Entity var1, EntityAnchorArgument.Anchor var2) {
         this.entity = â˜ƒ;
         this.anchor = â˜ƒ;
         this.position = â˜ƒ.apply(â˜ƒ);
      }

      public LookAt(Vec3 var1) {
         this.entity = null;
         this.position = â˜ƒ;
         this.anchor = null;
      }

      public void perform(CommandSourceStack var1, Entity var2) {
         if (this.entity != null) {
            if (â˜ƒ instanceof ServerPlayer) {
               ((ServerPlayer)â˜ƒ).lookAt(â˜ƒ.getAnchor(), this.entity, this.anchor);
            } else {
               â˜ƒ.lookAt(â˜ƒ.getAnchor(), this.position);
            }
         } else {
            â˜ƒ.lookAt(â˜ƒ.getAnchor(), this.position);
         }
      }
   }
}
