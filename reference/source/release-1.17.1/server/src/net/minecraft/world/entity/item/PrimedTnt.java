package net.minecraft.world.entity.item;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class PrimedTnt extends Entity {
   private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedTnt.class, EntityDataSerializers.INT);
   private static final int DEFAULT_FUSE_TIME = 80;
   @Nullable
   private LivingEntity owner;

   public PrimedTnt(EntityType<? extends PrimedTnt> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.blocksBuilding = true;
   }

   public PrimedTnt(Level var1, double var2, double var4, double var6, @Nullable LivingEntity var8) {
      this(EntityType.TNT, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      double â˜ƒ = â˜ƒ.random.nextDouble() * (float) (Math.PI * 2);
      this.setDeltaMovement(-Math.sin(â˜ƒ) * 0.02, 0.2F, -Math.cos(â˜ƒ) * 0.02);
      this.setFuse(80);
      this.xo = â˜ƒ;
      this.yo = â˜ƒ;
      this.zo = â˜ƒ;
      this.owner = â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_FUSE_ID, 80);
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   public boolean isPickable() {
      return !this.isRemoved();
   }

   @Override
   public void tick() {
      if (!this.isNoGravity()) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
      this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
      if (this.onGround) {
         this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
      }

      int â˜ƒ = this.getFuse() - 1;
      this.setFuse(â˜ƒ);
      if (â˜ƒ <= 0) {
         this.discard();
         if (!this.level.isClientSide) {
            this.explode();
         }
      } else {
         this.updateInWaterStateAndDoFluidPushing();
         if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
         }
      }
   }

   private void explode() {
      float â˜ƒ = 4.0F;
      this.level.explode(this, this.getX(), this.getY(0.0625), this.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putShort("Fuse", (short)this.getFuse());
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      this.setFuse(â˜ƒ.getShort("Fuse"));
   }

   @Nullable
   public LivingEntity getOwner() {
      return this.owner;
   }

   @Override
   protected float getEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.15F;
   }

   public void setFuse(int var1) {
      this.entityData.set(DATA_FUSE_ID, â˜ƒ);
   }

   public int getFuse() {
      return this.entityData.get(DATA_FUSE_ID);
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }
}
