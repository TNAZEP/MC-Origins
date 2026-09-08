package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Ghast extends FlyingMob implements Enemy {
   private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(Ghast.class, EntityDataSerializers.BOOLEAN);
   private int explosionPower = 1;

   public Ghast(EntityType<? extends Ghast> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 5;
      this.moveControl = new Ghast.GhastMoveControl(this);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(5, new Ghast.RandomFloatAroundGoal(this));
      this.goalSelector.addGoal(7, new Ghast.GhastLookGoal(this));
      this.goalSelector.addGoal(7, new Ghast.GhastShootFireballGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, var1 -> Math.abs(var1.getY() - this.getY()) <= 4.0));
   }

   public boolean isCharging() {
      return this.entityData.get(DATA_IS_CHARGING);
   }

   public void setCharging(boolean var1) {
      this.entityData.set(DATA_IS_CHARGING, â˜ƒ);
   }

   public int getExplosionPower() {
      return this.explosionPower;
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (â˜ƒ.getDirectEntity() instanceof LargeFireball && â˜ƒ.getEntity() instanceof Player) {
         super.hurt(â˜ƒ, 1000.0F);
         return true;
      } else {
         return super.hurt(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_IS_CHARGING, false);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.FOLLOW_RANGE, 100.0);
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.GHAST_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.GHAST_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.GHAST_DEATH;
   }

   @Override
   protected float getSoundVolume() {
      return 5.0F;
   }

   public static boolean checkGhastSpawnRules(EntityType<Ghast> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getDifficulty() != Difficulty.PEACEFUL && â˜ƒ.nextInt(20) == 0 && checkMobSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getMaxSpawnClusterSize() {
      return 1;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putByte("ExplosionPower", (byte)this.explosionPower);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("ExplosionPower", 99)) {
         this.explosionPower = â˜ƒ.getByte("ExplosionPower");
      }
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 2.6F;
   }

   static class GhastLookGoal extends Goal {
      private final Ghast ghast;

      public GhastLookGoal(Ghast var1) {
         this.ghast = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         return true;
      }

      @Override
      public void tick() {
         if (this.ghast.getTarget() == null) {
            Vec3 â˜ƒ = this.ghast.getDeltaMovement();
            this.ghast.setYRot(-((float)Mth.atan2(â˜ƒ.x, â˜ƒ.z)) * (180.0F / (float)Math.PI));
            this.ghast.yBodyRot = this.ghast.getYRot();
         } else {
            LivingEntity â˜ƒ = this.ghast.getTarget();
            double â˜ƒx = 64.0;
            if (â˜ƒ.distanceToSqr(this.ghast) < 4096.0) {
               double â˜ƒxx = â˜ƒ.getX() - this.ghast.getX();
               double â˜ƒxxx = â˜ƒ.getZ() - this.ghast.getZ();
               this.ghast.setYRot(-((float)Mth.atan2(â˜ƒxx, â˜ƒxxx)) * (180.0F / (float)Math.PI));
               this.ghast.yBodyRot = this.ghast.getYRot();
            }
         }
      }
   }

   static class GhastMoveControl extends MoveControl {
      private final Ghast ghast;
      private int floatDuration;

      public GhastMoveControl(Ghast var1) {
         super(â˜ƒ);
         this.ghast = â˜ƒ;
      }

      @Override
      public void tick() {
         if (this.operation == MoveControl.Operation.MOVE_TO) {
            if (this.floatDuration-- <= 0) {
               this.floatDuration += this.ghast.getRandom().nextInt(5) + 2;
               Vec3 â˜ƒ = new Vec3(this.wantedX - this.ghast.getX(), this.wantedY - this.ghast.getY(), this.wantedZ - this.ghast.getZ());
               double â˜ƒx = â˜ƒ.length();
               â˜ƒ = â˜ƒ.normalize();
               if (this.canReach(â˜ƒ, Mth.ceil(â˜ƒx))) {
                  this.ghast.setDeltaMovement(this.ghast.getDeltaMovement().add(â˜ƒ.scale(0.1)));
               } else {
                  this.operation = MoveControl.Operation.WAIT;
               }
            }
         }
      }

      private boolean canReach(Vec3 var1, int var2) {
         AABB â˜ƒ = this.ghast.getBoundingBox();

         for(int â˜ƒx = 1; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            â˜ƒ = â˜ƒ.move(â˜ƒ);
            if (!this.ghast.level.noCollision(this.ghast, â˜ƒ)) {
               return false;
            }
         }

         return true;
      }
   }

   static class GhastShootFireballGoal extends Goal {
      private final Ghast ghast;
      public int chargeTime;

      public GhastShootFireballGoal(Ghast var1) {
         this.ghast = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.ghast.getTarget() != null;
      }

      @Override
      public void start() {
         this.chargeTime = 0;
      }

      @Override
      public void stop() {
         this.ghast.setCharging(false);
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = this.ghast.getTarget();
         double â˜ƒx = 64.0;
         if (â˜ƒ.distanceToSqr(this.ghast) < 4096.0 && this.ghast.hasLineOfSight(â˜ƒ)) {
            Level â˜ƒxx = this.ghast.level;
            ++this.chargeTime;
            if (this.chargeTime == 10 && !this.ghast.isSilent()) {
               â˜ƒxx.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
            }

            if (this.chargeTime == 20) {
               double â˜ƒxx = 4.0;
               Vec3 â˜ƒxxx = this.ghast.getViewVector(1.0F);
               double â˜ƒxxxx = â˜ƒ.getX() - (this.ghast.getX() + â˜ƒxxx.x * 4.0);
               double â˜ƒxxxxx = â˜ƒ.getY(0.5) - (0.5 + this.ghast.getY(0.5));
               double â˜ƒxxxxxx = â˜ƒ.getZ() - (this.ghast.getZ() + â˜ƒxxx.z * 4.0);
               if (!this.ghast.isSilent()) {
                  â˜ƒxx.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
               }

               LargeFireball â˜ƒxx = new LargeFireball(â˜ƒxx, this.ghast, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, this.ghast.getExplosionPower());
               â˜ƒxx.setPos(this.ghast.getX() + â˜ƒxxx.x * 4.0, this.ghast.getY(0.5) + 0.5, â˜ƒxx.getZ() + â˜ƒxxx.z * 4.0);
               â˜ƒxx.addFreshEntity(â˜ƒxx);
               this.chargeTime = -40;
            }
         } else if (this.chargeTime > 0) {
            --this.chargeTime;
         }

         this.ghast.setCharging(this.chargeTime > 10);
      }
   }

   static class RandomFloatAroundGoal extends Goal {
      private final Ghast ghast;

      public RandomFloatAroundGoal(Ghast var1) {
         this.ghast = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         MoveControl â˜ƒ = this.ghast.getMoveControl();
         if (!â˜ƒ.hasWanted()) {
            return true;
         } else {
            double â˜ƒ = â˜ƒ.getWantedX() - this.ghast.getX();
            double â˜ƒx = â˜ƒ.getWantedY() - this.ghast.getY();
            double â˜ƒxx = â˜ƒ.getWantedZ() - this.ghast.getZ();
            double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
            return â˜ƒxxx < 1.0 || â˜ƒxxx > 3600.0;
         }
      }

      @Override
      public boolean canContinueToUse() {
         return false;
      }

      @Override
      public void start() {
         Random â˜ƒ = this.ghast.getRandom();
         double â˜ƒx = this.ghast.getX() + (double)((â˜ƒ.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double â˜ƒxx = this.ghast.getY() + (double)((â˜ƒ.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double â˜ƒxxx = this.ghast.getZ() + (double)((â˜ƒ.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.ghast.getMoveControl().setWantedPosition(â˜ƒx, â˜ƒxx, â˜ƒxxx, 1.0);
      }
   }
}
