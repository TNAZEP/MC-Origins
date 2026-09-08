package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Guardian extends Monster {
   protected static final int ATTACK_TIME = 80;
   private static final EntityDataAccessor<Boolean> DATA_ID_MOVING = SynchedEntityData.defineId(Guardian.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Guardian.class, EntityDataSerializers.INT);
   private float clientSideTailAnimation;
   private float clientSideTailAnimationO;
   private float clientSideTailAnimationSpeed;
   private float clientSideSpikesAnimation;
   private float clientSideSpikesAnimationO;
   private LivingEntity clientSideCachedAttackTarget;
   private int clientSideAttackTime;
   private boolean clientSideTouchedGround;
   protected RandomStrollGoal randomStrollGoal;

   public Guardian(EntityType<? extends Guardian> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 10;
      this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
      this.moveControl = new Guardian.GuardianMoveControl(this);
      this.clientSideTailAnimation = this.random.nextFloat();
      this.clientSideTailAnimationO = this.clientSideTailAnimation;
   }

   @Override
   protected void registerGoals() {
      MoveTowardsRestrictionGoal â˜ƒ = new MoveTowardsRestrictionGoal(this, 1.0);
      this.randomStrollGoal = new RandomStrollGoal(this, 1.0, 80);
      this.goalSelector.addGoal(4, new Guardian.GuardianAttackGoal(this));
      this.goalSelector.addGoal(5, â˜ƒ);
      this.goalSelector.addGoal(7, this.randomStrollGoal);
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Guardian.class, 12.0F, 0.01F));
      this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
      this.randomStrollGoal.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      â˜ƒ.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, false, new Guardian.GuardianAttackSelector(this)));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.ATTACK_DAMAGE, 6.0)
         .add(Attributes.MOVEMENT_SPEED, 0.5)
         .add(Attributes.FOLLOW_RANGE, 16.0)
         .add(Attributes.MAX_HEALTH, 30.0);
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new WaterBoundPathNavigation(this, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_MOVING, false);
      this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
   }

   @Override
   public boolean canBreatheUnderwater() {
      return true;
   }

   @Override
   public MobType getMobType() {
      return MobType.WATER;
   }

   public boolean isMoving() {
      return this.entityData.get(DATA_ID_MOVING);
   }

   void setMoving(boolean var1) {
      this.entityData.set(DATA_ID_MOVING, â˜ƒ);
   }

   public int getAttackDuration() {
      return 80;
   }

   void setActiveAttackTarget(int var1) {
      this.entityData.set(DATA_ID_ATTACK_TARGET, â˜ƒ);
   }

   public boolean hasActiveAttackTarget() {
      return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
   }

   @Nullable
   public LivingEntity getActiveAttackTarget() {
      if (!this.hasActiveAttackTarget()) {
         return null;
      } else if (this.level.isClientSide) {
         if (this.clientSideCachedAttackTarget != null) {
            return this.clientSideCachedAttackTarget;
         } else {
            Entity â˜ƒ = this.level.getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
            if (â˜ƒ instanceof LivingEntity) {
               this.clientSideCachedAttackTarget = (LivingEntity)â˜ƒ;
               return this.clientSideCachedAttackTarget;
            } else {
               return null;
            }
         }
      } else {
         return this.getTarget();
      }
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      super.onSyncedDataUpdated(â˜ƒ);
      if (DATA_ID_ATTACK_TARGET.equals(â˜ƒ)) {
         this.clientSideAttackTime = 0;
         this.clientSideCachedAttackTarget = null;
      }
   }

   @Override
   public int getAmbientSoundInterval() {
      return 160;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_AMBIENT : SoundEvents.GUARDIAN_AMBIENT_LAND;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_HURT : SoundEvents.GUARDIAN_HURT_LAND;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_DEATH : SoundEvents.GUARDIAN_DEATH_LAND;
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.EVENTS;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.5F;
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER) ? 10.0F + â˜ƒ.getBrightness(â˜ƒ) - 0.5F : super.getWalkTargetValue(â˜ƒ, â˜ƒ);
   }

   @Override
   public void aiStep() {
      if (this.isAlive()) {
         if (this.level.isClientSide) {
            this.clientSideTailAnimationO = this.clientSideTailAnimation;
            if (!this.isInWater()) {
               this.clientSideTailAnimationSpeed = 2.0F;
               Vec3 â˜ƒ = this.getDeltaMovement();
               if (â˜ƒ.y > 0.0 && this.clientSideTouchedGround && !this.isSilent()) {
                  this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), this.getFlopSound(), this.getSoundSource(), 1.0F, 1.0F, false);
               }

               this.clientSideTouchedGround = â˜ƒ.y < 0.0 && this.level.loadedAndEntityCanStandOn(this.blockPosition().below(), this);
            } else if (this.isMoving()) {
               if (this.clientSideTailAnimationSpeed < 0.5F) {
                  this.clientSideTailAnimationSpeed = 4.0F;
               } else {
                  this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
               }
            } else {
               this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
            }

            this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
            this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
            if (!this.isInWaterOrBubble()) {
               this.clientSideSpikesAnimation = this.random.nextFloat();
            } else if (this.isMoving()) {
               this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
            } else {
               this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
            }

            if (this.isMoving() && this.isInWater()) {
               Vec3 â˜ƒ = this.getViewVector(0.0F);

               for(int â˜ƒx = 0; â˜ƒx < 2; ++â˜ƒx) {
                  this.level
                     .addParticle(
                        ParticleTypes.BUBBLE,
                        this.getRandomX(0.5) - â˜ƒ.x * 1.5,
                        this.getRandomY() - â˜ƒ.y * 1.5,
                        this.getRandomZ(0.5) - â˜ƒ.z * 1.5,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }

            if (this.hasActiveAttackTarget()) {
               if (this.clientSideAttackTime < this.getAttackDuration()) {
                  ++this.clientSideAttackTime;
               }

               LivingEntity â˜ƒ = this.getActiveAttackTarget();
               if (â˜ƒ != null) {
                  this.getLookControl().setLookAt(â˜ƒ, 90.0F, 90.0F);
                  this.getLookControl().tick();
                  double â˜ƒx = (double)this.getAttackAnimationScale(0.0F);
                  double â˜ƒxx = â˜ƒ.getX() - this.getX();
                  double â˜ƒxxx = â˜ƒ.getY(0.5) - this.getEyeY();
                  double â˜ƒxxxx = â˜ƒ.getZ() - this.getZ();
                  double â˜ƒxxxxx = Math.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx);
                  â˜ƒxx /= â˜ƒxxxxx;
                  â˜ƒxxx /= â˜ƒxxxxx;
                  â˜ƒxxxx /= â˜ƒxxxxx;
                  double â˜ƒxxxxxx = this.random.nextDouble();

                  while(â˜ƒxxxxxx < â˜ƒxxxxx) {
                     â˜ƒxxxxxx += 1.8 - â˜ƒx + this.random.nextDouble() * (1.7 - â˜ƒx);
                     this.level
                        .addParticle(
                           ParticleTypes.BUBBLE,
                           this.getX() + â˜ƒxx * â˜ƒxxxxxx,
                           this.getEyeY() + â˜ƒxxx * â˜ƒxxxxxx,
                           this.getZ() + â˜ƒxxxx * â˜ƒxxxxxx,
                           0.0,
                           0.0,
                           0.0
                        );
                  }
               }
            }
         }

         if (this.isInWaterOrBubble()) {
            this.setAirSupply(300);
         } else if (this.onGround) {
            this.setDeltaMovement(
               this.getDeltaMovement()
                  .add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F))
            );
            this.setYRot(this.random.nextFloat() * 360.0F);
            this.onGround = false;
            this.hasImpulse = true;
         }

         if (this.hasActiveAttackTarget()) {
            this.setYRot(this.yHeadRot);
         }
      }

      super.aiStep();
   }

   protected SoundEvent getFlopSound() {
      return SoundEvents.GUARDIAN_FLOP;
   }

   public float getTailAnimation(float var1) {
      return Mth.lerp(â˜ƒ, this.clientSideTailAnimationO, this.clientSideTailAnimation);
   }

   public float getSpikesAnimation(float var1) {
      return Mth.lerp(â˜ƒ, this.clientSideSpikesAnimationO, this.clientSideSpikesAnimation);
   }

   public float getAttackAnimationScale(float var1) {
      return ((float)this.clientSideAttackTime + â˜ƒ) / (float)this.getAttackDuration();
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      return â˜ƒ.isUnobstructed(this);
   }

   public static boolean checkGuardianSpawnRules(EntityType<? extends Guardian> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return (â˜ƒ.nextInt(20) == 0 || !â˜ƒ.canSeeSkyFromBelowWater(â˜ƒ))
         && â˜ƒ.getDifficulty() != Difficulty.PEACEFUL
         && (â˜ƒ == MobSpawnType.SPAWNER || â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER));
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (!this.isMoving() && !â˜ƒ.isMagic() && â˜ƒ.getDirectEntity() instanceof LivingEntity â˜ƒ && !â˜ƒ.isExplosion()) {
         â˜ƒ.hurt(DamageSource.thorns(this), 2.0F);
      }

      if (this.randomStrollGoal != null) {
         this.randomStrollGoal.trigger();
      }

      return super.hurt(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getMaxHeadXRot() {
      return 180;
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(0.1F, â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         if (!this.isMoving() && this.getTarget() == null) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(â˜ƒ);
      }
   }

   static class GuardianAttackGoal extends Goal {
      private final Guardian guardian;
      private int attackTime;
      private final boolean elder;

      public GuardianAttackGoal(Guardian var1) {
         this.guardian = â˜ƒ;
         this.elder = â˜ƒ instanceof ElderGuardian;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = this.guardian.getTarget();
         return â˜ƒ != null && â˜ƒ.isAlive();
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && (this.elder || this.guardian.distanceToSqr(this.guardian.getTarget()) > 9.0);
      }

      @Override
      public void start() {
         this.attackTime = -10;
         this.guardian.getNavigation().stop();
         this.guardian.getLookControl().setLookAt(this.guardian.getTarget(), 90.0F, 90.0F);
         this.guardian.hasImpulse = true;
      }

      @Override
      public void stop() {
         this.guardian.setActiveAttackTarget(0);
         this.guardian.setTarget(null);
         this.guardian.randomStrollGoal.trigger();
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = this.guardian.getTarget();
         this.guardian.getNavigation().stop();
         this.guardian.getLookControl().setLookAt(â˜ƒ, 90.0F, 90.0F);
         if (!this.guardian.hasLineOfSight(â˜ƒ)) {
            this.guardian.setTarget(null);
         } else {
            ++this.attackTime;
            if (this.attackTime == 0) {
               this.guardian.setActiveAttackTarget(this.guardian.getTarget().getId());
               if (!this.guardian.isSilent()) {
                  this.guardian.level.broadcastEntityEvent(this.guardian, (byte)21);
               }
            } else if (this.attackTime >= this.guardian.getAttackDuration()) {
               float â˜ƒ = 1.0F;
               if (this.guardian.level.getDifficulty() == Difficulty.HARD) {
                  â˜ƒ += 2.0F;
               }

               if (this.elder) {
                  â˜ƒ += 2.0F;
               }

               â˜ƒ.hurt(DamageSource.indirectMagic(this.guardian, this.guardian), â˜ƒ);
               â˜ƒ.hurt(DamageSource.mobAttack(this.guardian), (float)this.guardian.getAttributeValue(Attributes.ATTACK_DAMAGE));
               this.guardian.setTarget(null);
            }

            super.tick();
         }
      }
   }

   static class GuardianAttackSelector implements Predicate<LivingEntity> {
      private final Guardian guardian;

      public GuardianAttackSelector(Guardian var1) {
         this.guardian = â˜ƒ;
      }

      public boolean test(@Nullable LivingEntity var1) {
         return (â˜ƒ instanceof Player || â˜ƒ instanceof Squid || â˜ƒ instanceof Axolotl) && â˜ƒ.distanceToSqr(this.guardian) > 9.0;
      }
   }

   static class GuardianMoveControl extends MoveControl {
      private final Guardian guardian;

      public GuardianMoveControl(Guardian var1) {
         super(â˜ƒ);
         this.guardian = â˜ƒ;
      }

      @Override
      public void tick() {
         if (this.operation == MoveControl.Operation.MOVE_TO && !this.guardian.getNavigation().isDone()) {
            Vec3 â˜ƒ = new Vec3(this.wantedX - this.guardian.getX(), this.wantedY - this.guardian.getY(), this.wantedZ - this.guardian.getZ());
            double â˜ƒx = â˜ƒ.length();
            double â˜ƒxx = â˜ƒ.x / â˜ƒx;
            double â˜ƒxxx = â˜ƒ.y / â˜ƒx;
            double â˜ƒxxxx = â˜ƒ.z / â˜ƒx;
            float â˜ƒxxxxx = (float)(Mth.atan2(â˜ƒ.z, â˜ƒ.x) * 180.0F / (float)Math.PI) - 90.0F;
            this.guardian.setYRot(this.rotlerp(this.guardian.getYRot(), â˜ƒxxxxx, 90.0F));
            this.guardian.yBodyRot = this.guardian.getYRot();
            float â˜ƒxxxxxx = (float)(this.speedModifier * this.guardian.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float â˜ƒxxxxxxx = Mth.lerp(0.125F, this.guardian.getSpeed(), â˜ƒxxxxxx);
            this.guardian.setSpeed(â˜ƒxxxxxxx);
            double â˜ƒxxxxxxxx = Math.sin((double)(this.guardian.tickCount + this.guardian.getId()) * 0.5) * 0.05;
            double â˜ƒxxxxxxxxx = Math.cos((double)(this.guardian.getYRot() * (float) (Math.PI / 180.0)));
            double â˜ƒxxxxxxxxxx = Math.sin((double)(this.guardian.getYRot() * (float) (Math.PI / 180.0)));
            double â˜ƒxxxxxxxxxxx = Math.sin((double)(this.guardian.tickCount + this.guardian.getId()) * 0.75) * 0.05;
            this.guardian
               .setDeltaMovement(
                  this.guardian
                     .getDeltaMovement()
                     .add(
                        â˜ƒxxxxxxxx * â˜ƒxxxxxxxxx,
                        â˜ƒxxxxxxxxxxx * (â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxx) * 0.25 + (double)â˜ƒxxxxxxx * â˜ƒxxx * 0.1,
                        â˜ƒxxxxxxxx * â˜ƒxxxxxxxxxx
                     )
               );
            LookControl â˜ƒxxxxxxxxxxxx = this.guardian.getLookControl();
            double â˜ƒxxxxxxxxxxxxx = this.guardian.getX() + â˜ƒxx * 2.0;
            double â˜ƒxxxxxxxxxxxxxx = this.guardian.getEyeY() + â˜ƒxxx / â˜ƒx;
            double â˜ƒxxxxxxxxxxxxxxx = this.guardian.getZ() + â˜ƒxxxx * 2.0;
            double â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getWantedX();
            double â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getWantedY();
            double â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getWantedZ();
            if (!â˜ƒxxxxxxxxxxxx.isHasWanted()) {
               â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx;
            }

            this.guardian
               .getLookControl()
               .setLookAt(
                  Mth.lerp(0.125, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx),
                  Mth.lerp(0.125, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx),
                  Mth.lerp(0.125, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx),
                  10.0F,
                  40.0F
               );
            this.guardian.setMoving(true);
         } else {
            this.guardian.setSpeed(0.0F);
            this.guardian.setMoving(false);
         }
      }
   }
}
