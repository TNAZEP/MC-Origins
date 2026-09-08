package net.minecraft.world.entity;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AgeableMob extends PathfinderMob {
   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(AgeableMob.class, EntityDataSerializers.BOOLEAN);
   public static final int BABY_START_AGE = -24000;
   private static final int FORCED_AGE_PARTICLE_TICKS = 40;
   protected int age;
   protected int forcedAge;
   protected int forcedAgeTimer;

   protected AgeableMob(EntityType<? extends AgeableMob> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (â˜ƒ == null) {
         â˜ƒ = new AgeableMob.AgeableMobGroupData(true);
      }

      AgeableMob.AgeableMobGroupData â˜ƒ = (AgeableMob.AgeableMobGroupData)â˜ƒ;
      if (â˜ƒ.isShouldSpawnBaby() && â˜ƒ.getGroupSize() > 0 && this.random.nextFloat() <= â˜ƒ.getBabySpawnChance()) {
         this.setAge(-24000);
      }

      â˜ƒ.increaseGroupSizeByOne();
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   public abstract AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2);

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_BABY_ID, false);
   }

   public boolean canBreed() {
      return false;
   }

   public int getAge() {
      if (this.level.isClientSide) {
         return this.entityData.get(DATA_BABY_ID) ? -1 : 1;
      } else {
         return this.age;
      }
   }

   public void ageUp(int var1, boolean var2) {
      int â˜ƒ = this.getAge();
      â˜ƒ += â˜ƒ * 20;
      if (â˜ƒ > 0) {
         â˜ƒ = 0;
      }

      int â˜ƒ = â˜ƒ - â˜ƒ;
      this.setAge(â˜ƒ);
      if (â˜ƒ) {
         this.forcedAge += â˜ƒ;
         if (this.forcedAgeTimer == 0) {
            this.forcedAgeTimer = 40;
         }
      }

      if (this.getAge() == 0) {
         this.setAge(this.forcedAge);
      }
   }

   public void ageUp(int var1) {
      this.ageUp(â˜ƒ, false);
   }

   public void setAge(int var1) {
      int â˜ƒ = this.age;
      this.age = â˜ƒ;
      if (â˜ƒ < 0 && â˜ƒ >= 0 || â˜ƒ >= 0 && â˜ƒ < 0) {
         this.entityData.set(DATA_BABY_ID, â˜ƒ < 0);
         this.ageBoundaryReached();
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Age", this.getAge());
      â˜ƒ.putInt("ForcedAge", this.forcedAge);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setAge(â˜ƒ.getInt("Age"));
      this.forcedAge = â˜ƒ.getInt("ForcedAge");
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_BABY_ID.equals(â˜ƒ)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.level.isClientSide) {
         if (this.forcedAgeTimer > 0) {
            if (this.forcedAgeTimer % 4 == 0) {
               this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
            }

            --this.forcedAgeTimer;
         }
      } else if (this.isAlive()) {
         int â˜ƒ = this.getAge();
         if (â˜ƒ < 0) {
            this.setAge(++â˜ƒ);
         } else if (â˜ƒ > 0) {
            this.setAge(--â˜ƒ);
         }
      }
   }

   protected void ageBoundaryReached() {
   }

   @Override
   public boolean isBaby() {
      return this.getAge() < 0;
   }

   @Override
   public void setBaby(boolean var1) {
      this.setAge(â˜ƒ ? -24000 : 0);
   }

   public static class AgeableMobGroupData implements SpawnGroupData {
      private int groupSize;
      private final boolean shouldSpawnBaby;
      private final float babySpawnChance;

      private AgeableMobGroupData(boolean var1, float var2) {
         this.shouldSpawnBaby = â˜ƒ;
         this.babySpawnChance = â˜ƒ;
      }

      public AgeableMobGroupData(boolean var1) {
         this(â˜ƒ, 0.05F);
      }

      public AgeableMobGroupData(float var1) {
         this(true, â˜ƒ);
      }

      public int getGroupSize() {
         return this.groupSize;
      }

      public void increaseGroupSizeByOne() {
         ++this.groupSize;
      }

      public boolean isShouldSpawnBaby() {
         return this.shouldSpawnBaby;
      }

      public float getBabySpawnChance() {
         return this.babySpawnChance;
      }
   }
}
