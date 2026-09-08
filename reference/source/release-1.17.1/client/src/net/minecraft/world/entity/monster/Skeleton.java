package net.minecraft.world.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Skeleton extends AbstractSkeleton {
   private static final EntityDataAccessor<Boolean> DATA_STRAY_CONVERSION_ID = SynchedEntityData.defineId(Skeleton.class, EntityDataSerializers.BOOLEAN);
   public static final String CONVERSION_TAG = "StrayConversionTime";
   private int inPowderSnowTime;
   private int conversionTime;

   public Skeleton(EntityType<? extends Skeleton> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.getEntityData().define(DATA_STRAY_CONVERSION_ID, false);
   }

   public boolean isFreezeConverting() {
      return this.getEntityData().get(DATA_STRAY_CONVERSION_ID);
   }

   public void setFreezeConverting(boolean var1) {
      this.entityData.set(DATA_STRAY_CONVERSION_ID, â˜ƒ);
   }

   @Override
   public boolean isShaking() {
      return this.isFreezeConverting();
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
         if (this.isFreezeConverting()) {
            --this.conversionTime;
            if (this.conversionTime < 0) {
               this.doFreezeConversion();
            }
         } else if (this.isInPowderSnow) {
            ++this.inPowderSnowTime;
            if (this.inPowderSnowTime >= 140) {
               this.startFreezeConversion(300);
            }
         } else {
            this.inPowderSnowTime = -1;
         }
      }

      super.tick();
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("StrayConversionTime", this.isFreezeConverting() ? this.conversionTime : -1);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("StrayConversionTime", 99) && â˜ƒ.getInt("StrayConversionTime") > -1) {
         this.startFreezeConversion(â˜ƒ.getInt("StrayConversionTime"));
      }
   }

   private void startFreezeConversion(int var1) {
      this.conversionTime = â˜ƒ;
      this.entityData.set(DATA_STRAY_CONVERSION_ID, true);
   }

   protected void doFreezeConversion() {
      this.convertTo(EntityType.STRAY, true);
      if (!this.isSilent()) {
         this.level.levelEvent(null, 1048, this.blockPosition(), 0);
      }
   }

   @Override
   public boolean canFreeze() {
      return false;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SKELETON_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.SKELETON_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.SKELETON_DEATH;
   }

   @Override
   SoundEvent getStepSound() {
      return SoundEvents.SKELETON_STEP;
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      Entity â˜ƒx = â˜ƒ.getEntity();
      if (â˜ƒx instanceof Creeper â˜ƒ && â˜ƒ.canDropMobsSkull()) {
         â˜ƒ.increaseDroppedSkulls();
         this.spawnAtLocation(Items.SKELETON_SKULL);
      }
   }
}
