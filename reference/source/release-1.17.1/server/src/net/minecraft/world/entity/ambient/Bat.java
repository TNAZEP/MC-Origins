package net.minecraft.world.entity.ambient;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Bat extends AmbientCreature {
   public static final float FLAP_DEGREES_PER_TICK = 74.48451F;
   public static final int TICKS_PER_FLAP = Mth.ceil(2.4166098F);
   private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Bat.class, EntityDataSerializers.BYTE);
   private static final int FLAG_RESTING = 1;
   private static final TargetingConditions BAT_RESTING_TARGETING = TargetingConditions.forNonCombat().range(4.0);
   private BlockPos targetPosition;

   public Bat(EntityType<? extends Bat> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setResting(true);
   }

   @Override
   public boolean isFlapping() {
      return !this.isResting() && this.tickCount % TICKS_PER_FLAP == 0;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_FLAGS, (byte)0);
   }

   @Override
   protected float getSoundVolume() {
      return 0.1F;
   }

   @Override
   public float getVoicePitch() {
      return super.getVoicePitch() * 0.95F;
   }

   @Nullable
   @Override
   public SoundEvent getAmbientSound() {
      return this.isResting() && this.random.nextInt(4) != 0 ? null : SoundEvents.BAT_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.BAT_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.BAT_DEATH;
   }

   @Override
   public boolean isPushable() {
      return false;
   }

   @Override
   protected void doPush(Entity var1) {
   }

   @Override
   protected void pushEntities() {
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0);
   }

   public boolean isResting() {
      return (this.entityData.get(DATA_ID_FLAGS) & 1) != 0;
   }

   public void setResting(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_ID_FLAGS);
      if (â˜ƒ) {
         this.entityData.set(DATA_ID_FLAGS, (byte)(â˜ƒ | 1));
      } else {
         this.entityData.set(DATA_ID_FLAGS, (byte)(â˜ƒ & -2));
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isResting()) {
         this.setDeltaMovement(Vec3.ZERO);
         this.setPosRaw(this.getX(), (double)Mth.floor(this.getY()) + 1.0 - (double)this.getBbHeight(), this.getZ());
      } else {
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
      }
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      BlockPos â˜ƒ = this.blockPosition();
      BlockPos â˜ƒx = â˜ƒ.above();
      if (this.isResting()) {
         boolean â˜ƒxx = this.isSilent();
         if (this.level.getBlockState(â˜ƒx).isRedstoneConductor(this.level, â˜ƒ)) {
            if (this.random.nextInt(200) == 0) {
               this.yHeadRot = (float)this.random.nextInt(360);
            }

            if (this.level.getNearestPlayer(BAT_RESTING_TARGETING, this) != null) {
               this.setResting(false);
               if (!â˜ƒxx) {
                  this.level.levelEvent(null, 1025, â˜ƒ, 0);
               }
            }
         } else {
            this.setResting(false);
            if (!â˜ƒxx) {
               this.level.levelEvent(null, 1025, â˜ƒ, 0);
            }
         }
      } else {
         if (this.targetPosition != null && (!this.level.isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
            this.targetPosition = null;
         }

         if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerThan(this.position(), 2.0)) {
            this.targetPosition = new BlockPos(
               this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7),
               this.getY() + (double)this.random.nextInt(6) - 2.0,
               this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7)
            );
         }

         double â˜ƒ = (double)this.targetPosition.getX() + 0.5 - this.getX();
         double â˜ƒx = (double)this.targetPosition.getY() + 0.1 - this.getY();
         double â˜ƒxx = (double)this.targetPosition.getZ() + 0.5 - this.getZ();
         Vec3 â˜ƒxxx = this.getDeltaMovement();
         Vec3 â˜ƒxxxx = â˜ƒxxx.add(
            (Math.signum(â˜ƒ) * 0.5 - â˜ƒxxx.x) * 0.1F, (Math.signum(â˜ƒx) * 0.7F - â˜ƒxxx.y) * 0.1F, (Math.signum(â˜ƒxx) * 0.5 - â˜ƒxxx.z) * 0.1F
         );
         this.setDeltaMovement(â˜ƒxxxx);
         float â˜ƒxxxxx = (float)(Mth.atan2(â˜ƒxxxx.z, â˜ƒxxxx.x) * 180.0F / (float)Math.PI) - 90.0F;
         float â˜ƒxxxxxx = Mth.wrapDegrees(â˜ƒxxxxx - this.getYRot());
         this.zza = 0.5F;
         this.setYRot(this.getYRot() + â˜ƒxxxxxx);
         if (this.random.nextInt(100) == 0 && this.level.getBlockState(â˜ƒx).isRedstoneConductor(this.level, â˜ƒx)) {
            this.setResting(true);
         }
      }
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.EVENTS;
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      return false;
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
   }

   @Override
   public boolean isIgnoringBlockTriggers() {
      return true;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         if (!this.level.isClientSide && this.isResting()) {
            this.setResting(false);
         }

         return super.hurt(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.entityData.set(DATA_ID_FLAGS, â˜ƒ.getByte("BatFlags"));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putByte("BatFlags", this.entityData.get(DATA_ID_FLAGS));
   }

   public static boolean checkBatSpawnRules(EntityType<Bat> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getY() >= â˜ƒ.getSeaLevel()) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.getMaxLocalRawBrightness(â˜ƒ);
         int â˜ƒx = 4;
         if (isHalloween()) {
            â˜ƒx = 7;
         } else if (â˜ƒ.nextBoolean()) {
            return false;
         }

         return â˜ƒ > â˜ƒ.nextInt(â˜ƒx) ? false : checkMobSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static boolean isHalloween() {
      LocalDate â˜ƒ = LocalDate.now();
      int â˜ƒx = â˜ƒ.get(ChronoField.DAY_OF_MONTH);
      int â˜ƒxx = â˜ƒ.get(ChronoField.MONTH_OF_YEAR);
      return â˜ƒxx == 10 && â˜ƒx >= 20 || â˜ƒxx == 11 && â˜ƒx <= 3;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height / 2.0F;
   }
}
