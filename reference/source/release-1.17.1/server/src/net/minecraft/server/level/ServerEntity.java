package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerEntity {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int TOLERANCE_LEVEL_ROTATION = 1;
   private final ServerLevel level;
   private final Entity entity;
   private final int updateInterval;
   private final boolean trackDelta;
   private final Consumer<Packet<?>> broadcast;
   private long xp;
   private long yp;
   private long zp;
   private int yRotp;
   private int xRotp;
   private int yHeadRotp;
   private Vec3 ap = Vec3.ZERO;
   private int tickCount;
   private int teleportDelay;
   private List<Entity> lastPassengers = Collections.emptyList();
   private boolean wasRiding;
   private boolean wasOnGround;

   public ServerEntity(ServerLevel var1, Entity var2, int var3, boolean var4, Consumer<Packet<?>> var5) {
      this.level = â˜ƒ;
      this.broadcast = â˜ƒ;
      this.entity = â˜ƒ;
      this.updateInterval = â˜ƒ;
      this.trackDelta = â˜ƒ;
      this.updateSentPos();
      this.yRotp = Mth.floor(â˜ƒ.getYRot() * 256.0F / 360.0F);
      this.xRotp = Mth.floor(â˜ƒ.getXRot() * 256.0F / 360.0F);
      this.yHeadRotp = Mth.floor(â˜ƒ.getYHeadRot() * 256.0F / 360.0F);
      this.wasOnGround = â˜ƒ.isOnGround();
   }

   public void sendChanges() {
      List<Entity> â˜ƒ = this.entity.getPassengers();
      if (!â˜ƒ.equals(this.lastPassengers)) {
         this.lastPassengers = â˜ƒ;
         this.broadcast.accept(new ClientboundSetPassengersPacket(this.entity));
      }

      if (this.entity instanceof ItemFrame â˜ƒ && this.tickCount % 10 == 0) {
         ItemStack â˜ƒx = â˜ƒ.getItem();
         if (â˜ƒx.getItem() instanceof MapItem) {
            Integer â˜ƒxx = MapItem.getMapId(â˜ƒx);
            MapItemSavedData â˜ƒxxx = MapItem.getSavedData(â˜ƒxx, this.level);
            if (â˜ƒxxx != null) {
               for(ServerPlayer â˜ƒxxxx : this.level.players()) {
                  â˜ƒxxx.tickCarriedBy(â˜ƒxxxx, â˜ƒx);
                  Packet<?> â˜ƒxxxxx = â˜ƒxxx.getUpdatePacket(â˜ƒxx, â˜ƒxxxx);
                  if (â˜ƒxxxxx != null) {
                     â˜ƒxxxx.connection.send(â˜ƒxxxxx);
                  }
               }
            }
         }

         this.sendDirtyEntityData();
      }

      if (this.tickCount % this.updateInterval == 0 || this.entity.hasImpulse || this.entity.getEntityData().isDirty()) {
         if (this.entity.isPassenger()) {
            int â˜ƒ = Mth.floor(this.entity.getYRot() * 256.0F / 360.0F);
            int â˜ƒx = Mth.floor(this.entity.getXRot() * 256.0F / 360.0F);
            boolean â˜ƒxx = Math.abs(â˜ƒ - this.yRotp) >= 1 || Math.abs(â˜ƒx - this.xRotp) >= 1;
            if (â˜ƒxx) {
               this.broadcast.accept(new ClientboundMoveEntityPacket.Rot(this.entity.getId(), (byte)â˜ƒ, (byte)â˜ƒx, this.entity.isOnGround()));
               this.yRotp = â˜ƒ;
               this.xRotp = â˜ƒx;
            }

            this.updateSentPos();
            this.sendDirtyEntityData();
            this.wasRiding = true;
         } else {
            ++this.teleportDelay;
            int â˜ƒ = Mth.floor(this.entity.getYRot() * 256.0F / 360.0F);
            int â˜ƒx = Mth.floor(this.entity.getXRot() * 256.0F / 360.0F);
            Vec3 â˜ƒxx = this.entity.position().subtract(ClientboundMoveEntityPacket.packetToEntity(this.xp, this.yp, this.zp));
            boolean â˜ƒxxx = â˜ƒxx.lengthSqr() >= 7.6293945E-6F;
            Packet<?> â˜ƒxxxx = null;
            boolean â˜ƒxxxxx = â˜ƒxxx || this.tickCount % 60 == 0;
            boolean â˜ƒxxxxxx = Math.abs(â˜ƒ - this.yRotp) >= 1 || Math.abs(â˜ƒx - this.xRotp) >= 1;
            if (this.tickCount > 0 || this.entity instanceof AbstractArrow) {
               long â˜ƒxxxxxxx = ClientboundMoveEntityPacket.entityToPacket(â˜ƒxx.x);
               long â˜ƒxxxxxxxx = ClientboundMoveEntityPacket.entityToPacket(â˜ƒxx.y);
               long â˜ƒxxxxxxxxx = ClientboundMoveEntityPacket.entityToPacket(â˜ƒxx.z);
               boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx < -32768L
                  || â˜ƒxxxxxxx > 32767L
                  || â˜ƒxxxxxxxx < -32768L
                  || â˜ƒxxxxxxxx > 32767L
                  || â˜ƒxxxxxxxxx < -32768L
                  || â˜ƒxxxxxxxxx > 32767L;
               if (â˜ƒxxxxxxxxxx || this.teleportDelay > 400 || this.wasRiding || this.wasOnGround != this.entity.isOnGround()) {
                  this.wasOnGround = this.entity.isOnGround();
                  this.teleportDelay = 0;
                  â˜ƒxxxx = new ClientboundTeleportEntityPacket(this.entity);
               } else if ((!â˜ƒxxxxx || !â˜ƒxxxxxx) && !(this.entity instanceof AbstractArrow)) {
                  if (â˜ƒxxxxx) {
                     â˜ƒxxxx = new ClientboundMoveEntityPacket.Pos(
                        this.entity.getId(), (short)((int)â˜ƒxxxxxxx), (short)((int)â˜ƒxxxxxxxx), (short)((int)â˜ƒxxxxxxxxx), this.entity.isOnGround()
                     );
                  } else if (â˜ƒxxxxxx) {
                     â˜ƒxxxx = new ClientboundMoveEntityPacket.Rot(this.entity.getId(), (byte)â˜ƒ, (byte)â˜ƒx, this.entity.isOnGround());
                  }
               } else {
                  â˜ƒxxxx = new ClientboundMoveEntityPacket.PosRot(
                     this.entity.getId(),
                     (short)((int)â˜ƒxxxxxxx),
                     (short)((int)â˜ƒxxxxxxxx),
                     (short)((int)â˜ƒxxxxxxxxx),
                     (byte)â˜ƒ,
                     (byte)â˜ƒx,
                     this.entity.isOnGround()
                  );
               }
            }

            if ((this.trackDelta || this.entity.hasImpulse || this.entity instanceof LivingEntity && ((LivingEntity)this.entity).isFallFlying())
               && this.tickCount > 0) {
               Vec3 â˜ƒ = this.entity.getDeltaMovement();
               double â˜ƒx = â˜ƒ.distanceToSqr(this.ap);
               if (â˜ƒx > 1.0E-7 || â˜ƒx > 0.0 && â˜ƒ.lengthSqr() == 0.0) {
                  this.ap = â˜ƒ;
                  this.broadcast.accept(new ClientboundSetEntityMotionPacket(this.entity.getId(), this.ap));
               }
            }

            if (â˜ƒxxxx != null) {
               this.broadcast.accept(â˜ƒxxxx);
            }

            this.sendDirtyEntityData();
            if (â˜ƒxxxxx) {
               this.updateSentPos();
            }

            if (â˜ƒxxxxxx) {
               this.yRotp = â˜ƒ;
               this.xRotp = â˜ƒx;
            }

            this.wasRiding = false;
         }

         int â˜ƒ = Mth.floor(this.entity.getYHeadRot() * 256.0F / 360.0F);
         if (Math.abs(â˜ƒ - this.yHeadRotp) >= 1) {
            this.broadcast.accept(new ClientboundRotateHeadPacket(this.entity, (byte)â˜ƒ));
            this.yHeadRotp = â˜ƒ;
         }

         this.entity.hasImpulse = false;
      }

      ++this.tickCount;
      if (this.entity.hurtMarked) {
         this.broadcastAndSend(new ClientboundSetEntityMotionPacket(this.entity));
         this.entity.hurtMarked = false;
      }
   }

   public void removePairing(ServerPlayer var1) {
      this.entity.stopSeenByPlayer(â˜ƒ);
      â˜ƒ.connection.send(new ClientboundRemoveEntitiesPacket(this.entity.getId()));
   }

   public void addPairing(ServerPlayer var1) {
      this.sendPairingData(â˜ƒ.connection::send);
      this.entity.startSeenByPlayer(â˜ƒ);
   }

   public void sendPairingData(Consumer<Packet<?>> var1) {
      if (this.entity.isRemoved()) {
         LOGGER.warn("Fetching packet for removed entity {}", this.entity);
      }

      Packet<?> â˜ƒ = this.entity.getAddEntityPacket();
      this.yHeadRotp = Mth.floor(this.entity.getYHeadRot() * 256.0F / 360.0F);
      â˜ƒ.accept(â˜ƒ);
      if (!this.entity.getEntityData().isEmpty()) {
         â˜ƒ.accept(new ClientboundSetEntityDataPacket(this.entity.getId(), this.entity.getEntityData(), true));
      }

      boolean â˜ƒ = this.trackDelta;
      if (this.entity instanceof LivingEntity) {
         Collection<AttributeInstance> â˜ƒx = ((LivingEntity)this.entity).getAttributes().getSyncableAttributes();
         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.accept(new ClientboundUpdateAttributesPacket(this.entity.getId(), â˜ƒx));
         }

         if (((LivingEntity)this.entity).isFallFlying()) {
            â˜ƒ = true;
         }
      }

      this.ap = this.entity.getDeltaMovement();
      if (â˜ƒ && !(â˜ƒ instanceof ClientboundAddMobPacket)) {
         â˜ƒ.accept(new ClientboundSetEntityMotionPacket(this.entity.getId(), this.ap));
      }

      if (this.entity instanceof LivingEntity) {
         List<Pair<EquipmentSlot, ItemStack>> â˜ƒ = Lists.<Pair<EquipmentSlot, ItemStack>>newArrayList();

         for(EquipmentSlot â˜ƒx : EquipmentSlot.values()) {
            ItemStack â˜ƒxx = ((LivingEntity)this.entity).getItemBySlot(â˜ƒx);
            if (!â˜ƒxx.isEmpty()) {
               â˜ƒ.add(Pair.of(â˜ƒx, â˜ƒxx.copy()));
            }
         }

         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.accept(new ClientboundSetEquipmentPacket(this.entity.getId(), â˜ƒ));
         }
      }

      if (this.entity instanceof LivingEntity â˜ƒ) {
         for(MobEffectInstance â˜ƒx : â˜ƒ.getActiveEffects()) {
            â˜ƒ.accept(new ClientboundUpdateMobEffectPacket(this.entity.getId(), â˜ƒx));
         }
      }

      if (!this.entity.getPassengers().isEmpty()) {
         â˜ƒ.accept(new ClientboundSetPassengersPacket(this.entity));
      }

      if (this.entity.isPassenger()) {
         â˜ƒ.accept(new ClientboundSetPassengersPacket(this.entity.getVehicle()));
      }

      if (this.entity instanceof Mob â˜ƒ && â˜ƒ.isLeashed()) {
         â˜ƒ.accept(new ClientboundSetEntityLinkPacket(â˜ƒ, â˜ƒ.getLeashHolder()));
      }
   }

   private void sendDirtyEntityData() {
      SynchedEntityData â˜ƒ = this.entity.getEntityData();
      if (â˜ƒ.isDirty()) {
         this.broadcastAndSend(new ClientboundSetEntityDataPacket(this.entity.getId(), â˜ƒ, false));
      }

      if (this.entity instanceof LivingEntity) {
         Set<AttributeInstance> â˜ƒ = ((LivingEntity)this.entity).getAttributes().getDirtyAttributes();
         if (!â˜ƒ.isEmpty()) {
            this.broadcastAndSend(new ClientboundUpdateAttributesPacket(this.entity.getId(), â˜ƒ));
         }

         â˜ƒ.clear();
      }
   }

   private void updateSentPos() {
      this.xp = ClientboundMoveEntityPacket.entityToPacket(this.entity.getX());
      this.yp = ClientboundMoveEntityPacket.entityToPacket(this.entity.getY());
      this.zp = ClientboundMoveEntityPacket.entityToPacket(this.entity.getZ());
   }

   public Vec3 sentPos() {
      return ClientboundMoveEntityPacket.packetToEntity(this.xp, this.yp, this.zp);
   }

   private void broadcastAndSend(Packet<?> var1) {
      this.broadcast.accept(â˜ƒ);
      if (this.entity instanceof ServerPlayer) {
         ((ServerPlayer)this.entity).connection.send(â˜ƒ);
      }
   }
}
