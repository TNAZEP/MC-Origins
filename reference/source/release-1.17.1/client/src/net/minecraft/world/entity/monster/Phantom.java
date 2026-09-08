package net.minecraft.world.entity.monster;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Phantom extends FlyingMob implements Enemy {
   public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
   public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
   private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Phantom.class, EntityDataSerializers.INT);
   Vec3 moveTargetPoint = Vec3.ZERO;
   BlockPos anchorPoint = BlockPos.ZERO;
   Phantom.AttackPhase attackPhase = Phantom.AttackPhase.CIRCLE;

   public Phantom(EntityType<? extends Phantom> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 5;
      this.moveControl = new Phantom.PhantomMoveControl(this);
      this.lookControl = new Phantom.PhantomLookControl(this);
   }

   @Override
   public boolean isFlapping() {
      return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
   }

   @Override
   protected BodyRotationControl createBodyControl() {
      return new Phantom.PhantomBodyRotationControl(this);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new Phantom.PhantomAttackStrategyGoal());
      this.goalSelector.addGoal(2, new Phantom.PhantomSweepAttackGoal());
      this.goalSelector.addGoal(3, new Phantom.PhantomCircleAroundAnchorGoal());
      this.targetSelector.addGoal(1, new Phantom.PhantomAttackPlayerTargetGoal());
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(ID_SIZE, 0);
   }

   public void setPhantomSize(int var1) {
      this.entityData.set(ID_SIZE, Mth.clamp(â˜ƒ, 0, 64));
   }

   private void updatePhantomSizeInfo() {
      this.refreshDimensions();
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)(6 + this.getPhantomSize()));
   }

   public int getPhantomSize() {
      return this.entityData.get(ID_SIZE);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.35F;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (ID_SIZE.equals(â˜ƒ)) {
         this.updatePhantomSizeInfo();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   public int getUniqueFlapTickOffset() {
      return this.getId() * 3;
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level.isClientSide) {
         float â˜ƒ = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * (float) (Math.PI / 180.0) + (float) Math.PI);
         float â˜ƒx = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * (float) (Math.PI / 180.0) + (float) Math.PI);
         if (â˜ƒ > 0.0F && â˜ƒx <= 0.0F) {
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.PHANTOM_FLAP,
                  this.getSoundSource(),
                  0.95F + this.random.nextFloat() * 0.05F,
                  0.95F + this.random.nextFloat() * 0.05F,
                  false
               );
         }

         int â˜ƒ = this.getPhantomSize();
         float â˜ƒx = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)â˜ƒ);
         float â˜ƒxx = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * (1.3F + 0.21F * (float)â˜ƒ);
         float â˜ƒxxx = (0.3F + â˜ƒ * 0.45F) * ((float)â˜ƒ * 0.2F + 1.0F);
         this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() + (double)â˜ƒx, this.getY() + (double)â˜ƒxxx, this.getZ() + (double)â˜ƒxx, 0.0, 0.0, 0.0);
         this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() - (double)â˜ƒx, this.getY() + (double)â˜ƒxxx, this.getZ() - (double)â˜ƒxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void aiStep() {
      if (this.isAlive() && this.isSunBurnTick()) {
         this.setSecondsOnFire(8);
      }

      super.aiStep();
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
   }

   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.anchorPoint = this.blockPosition().above(5);
      this.setPhantomSize(0);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("AX")) {
         this.anchorPoint = new BlockPos(â˜ƒ.getInt("AX"), â˜ƒ.getInt("AY"), â˜ƒ.getInt("AZ"));
      }

      this.setPhantomSize(â˜ƒ.getInt("Size"));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("AX", this.anchorPoint.getX());
      â˜ƒ.putInt("AY", this.anchorPoint.getY());
      â˜ƒ.putInt("AZ", this.anchorPoint.getZ());
      â˜ƒ.putInt("Size", this.getPhantomSize());
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      return true;
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.PHANTOM_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.PHANTOM_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PHANTOM_DEATH;
   }

   @Override
   public MobType getMobType() {
      return MobType.UNDEAD;
   }

   @Override
   protected float getSoundVolume() {
      return 1.0F;
   }

   @Override
   public boolean canAttackType(EntityType<?> var1) {
      return true;
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      int â˜ƒ = this.getPhantomSize();
      EntityDimensions â˜ƒx = super.getDimensions(â˜ƒ);
      float â˜ƒxx = (â˜ƒx.width + 0.2F * (float)â˜ƒ) / â˜ƒx.width;
      return â˜ƒx.scale(â˜ƒxx);
   }

   static enum AttackPhase {
      CIRCLE,
      SWOOP;
   }

   class PhantomAttackPlayerTargetGoal extends Goal {
      private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);
      private int nextScanTick = 20;

      @Override
      public boolean canUse() {
         if (this.nextScanTick > 0) {
            --this.nextScanTick;
            return false;
         } else {
            this.nextScanTick = 60;
            List<Player> â˜ƒ = Phantom.this.level.getNearbyPlayers(this.attackTargeting, Phantom.this, Phantom.this.getBoundingBox().inflate(16.0, 64.0, 16.0));
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ.sort(Comparator.comparing(Entity::getY).reversed());

               for(Player â˜ƒx : â˜ƒ) {
                  if (Phantom.this.canAttack(â˜ƒx, TargetingConditions.DEFAULT)) {
                     Phantom.this.setTarget(â˜ƒx);
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public boolean canContinueToUse() {
         LivingEntity â˜ƒ = Phantom.this.getTarget();
         return â˜ƒ != null ? Phantom.this.canAttack(â˜ƒ, TargetingConditions.DEFAULT) : false;
      }
   }

   class PhantomAttackStrategyGoal extends Goal {
      private int nextSweepTick;

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = Phantom.this.getTarget();
         return â˜ƒ != null ? Phantom.this.canAttack(Phantom.this.getTarget(), TargetingConditions.DEFAULT) : false;
      }

      @Override
      public void start() {
         this.nextSweepTick = 10;
         Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
         this.setAnchorAboveTarget();
      }

      @Override
      public void stop() {
         Phantom.this.anchorPoint = Phantom.this.level
            .getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, Phantom.this.anchorPoint)
            .above(10 + Phantom.this.random.nextInt(20));
      }

      @Override
      public void tick() {
         if (Phantom.this.attackPhase == Phantom.AttackPhase.CIRCLE) {
            --this.nextSweepTick;
            if (this.nextSweepTick <= 0) {
               Phantom.this.attackPhase = Phantom.AttackPhase.SWOOP;
               this.setAnchorAboveTarget();
               this.nextSweepTick = (8 + Phantom.this.random.nextInt(4)) * 20;
               Phantom.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + Phantom.this.random.nextFloat() * 0.1F);
            }
         }
      }

      private void setAnchorAboveTarget() {
         Phantom.this.anchorPoint = Phantom.this.getTarget().blockPosition().above(20 + Phantom.this.random.nextInt(20));
         if (Phantom.this.anchorPoint.getY() < Phantom.this.level.getSeaLevel()) {
            Phantom.this.anchorPoint = new BlockPos(Phantom.this.anchorPoint.getX(), Phantom.this.level.getSeaLevel() + 1, Phantom.this.anchorPoint.getZ());
         }
      }
   }

   class PhantomBodyRotationControl extends BodyRotationControl {
      public PhantomBodyRotationControl(Mob var2) {
         super(â˜ƒ);
      }

      @Override
      public void clientTick() {
         Phantom.this.yHeadRot = Phantom.this.yBodyRot;
         Phantom.this.yBodyRot = Phantom.this.getYRot();
      }
   }

   class PhantomCircleAroundAnchorGoal extends Phantom.PhantomMoveTargetGoal {
      private float angle;
      private float distance;
      private float height;
      private float clockwise;

      @Override
      public boolean canUse() {
         return Phantom.this.getTarget() == null || Phantom.this.attackPhase == Phantom.AttackPhase.CIRCLE;
      }

      @Override
      public void start() {
         this.distance = 5.0F + Phantom.this.random.nextFloat() * 10.0F;
         this.height = -4.0F + Phantom.this.random.nextFloat() * 9.0F;
         this.clockwise = Phantom.this.random.nextBoolean() ? 1.0F : -1.0F;
         this.selectNext();
      }

      @Override
      public void tick() {
         if (Phantom.this.random.nextInt(350) == 0) {
            this.height = -4.0F + Phantom.this.random.nextFloat() * 9.0F;
         }

         if (Phantom.this.random.nextInt(250) == 0) {
            ++this.distance;
            if (this.distance > 15.0F) {
               this.distance = 5.0F;
               this.clockwise = -this.clockwise;
            }
         }

         if (Phantom.this.random.nextInt(450) == 0) {
            this.angle = Phantom.this.random.nextFloat() * 2.0F * (float) Math.PI;
            this.selectNext();
         }

         if (this.touchingTarget()) {
            this.selectNext();
         }

         if (Phantom.this.moveTargetPoint.y < Phantom.this.getY() && !Phantom.this.level.isEmptyBlock(Phantom.this.blockPosition().below(1))) {
            this.height = Math.max(1.0F, this.height);
            this.selectNext();
         }

         if (Phantom.this.moveTargetPoint.y > Phantom.this.getY() && !Phantom.this.level.isEmptyBlock(Phantom.this.blockPosition().above(1))) {
            this.height = Math.min(-1.0F, this.height);
            this.selectNext();
         }
      }

      private void selectNext() {
         if (BlockPos.ZERO.equals(Phantom.this.anchorPoint)) {
            Phantom.this.anchorPoint = Phantom.this.blockPosition();
         }

         this.angle += this.clockwise * 15.0F * (float) (Math.PI / 180.0);
         Phantom.this.moveTargetPoint = Vec3.atLowerCornerOf(Phantom.this.anchorPoint)
            .add((double)(this.distance * Mth.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * Mth.sin(this.angle)));
      }
   }

   class PhantomLookControl extends LookControl {
      public PhantomLookControl(Mob var2) {
         super(â˜ƒ);
      }

      @Override
      public void tick() {
      }
   }

   class PhantomMoveControl extends MoveControl {
      private float speed = 0.1F;

      public PhantomMoveControl(Mob var2) {
         super(â˜ƒ);
      }

      @Override
      public void tick() {
         if (Phantom.this.horizontalCollision) {
            Phantom.this.setYRot(Phantom.this.getYRot() + 180.0F);
            this.speed = 0.1F;
         }

         float â˜ƒ = (float)(Phantom.this.moveTargetPoint.x - Phantom.this.getX());
         float â˜ƒx = (float)(Phantom.this.moveTargetPoint.y - Phantom.this.getY());
         float â˜ƒxx = (float)(Phantom.this.moveTargetPoint.z - Phantom.this.getZ());
         double â˜ƒxxx = (double)Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx);
         if (Math.abs(â˜ƒxxx) > 1.0E-5F) {
            double â˜ƒxxxx = 1.0 - (double)Mth.abs(â˜ƒx * 0.7F) / â˜ƒxxx;
            â˜ƒ = (float)((double)â˜ƒ * â˜ƒxxxx);
            â˜ƒxx = (float)((double)â˜ƒxx * â˜ƒxxxx);
            â˜ƒxxx = (double)Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx);
            double â˜ƒxxxxx = (double)Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx + â˜ƒx * â˜ƒx);
            float â˜ƒxxxxxx = Phantom.this.getYRot();
            float â˜ƒxxxxxxx = (float)Mth.atan2((double)â˜ƒxx, (double)â˜ƒ);
            float â˜ƒxxxxxxxx = Mth.wrapDegrees(Phantom.this.getYRot() + 90.0F);
            float â˜ƒxxxxxxxxx = Mth.wrapDegrees(â˜ƒxxxxxxx * (180.0F / (float)Math.PI));
            Phantom.this.setYRot(Mth.approachDegrees(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 4.0F) - 90.0F);
            Phantom.this.yBodyRot = Phantom.this.getYRot();
            if (Mth.degreesDifferenceAbs(â˜ƒxxxxxx, Phantom.this.getYRot()) < 3.0F) {
               this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
            } else {
               this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
            }

            float â˜ƒxxxx = (float)(-(Mth.atan2((double)(-â˜ƒx), â˜ƒxxx) * 180.0F / (float)Math.PI));
            Phantom.this.setXRot(â˜ƒxxxx);
            float â˜ƒxxxxx = Phantom.this.getYRot() + 90.0F;
            double â˜ƒxxxxxx = (double)(this.speed * Mth.cos(â˜ƒxxxxx * (float) (Math.PI / 180.0))) * Math.abs((double)â˜ƒ / â˜ƒxxxxx);
            double â˜ƒxxxxxxx = (double)(this.speed * Mth.sin(â˜ƒxxxxx * (float) (Math.PI / 180.0))) * Math.abs((double)â˜ƒxx / â˜ƒxxxxx);
            double â˜ƒxxxxxxxx = (double)(this.speed * Mth.sin(â˜ƒxxxx * (float) (Math.PI / 180.0))) * Math.abs((double)â˜ƒx / â˜ƒxxxxx);
            Vec3 â˜ƒxxxxxxxxx = Phantom.this.getDeltaMovement();
            Phantom.this.setDeltaMovement(â˜ƒxxxxxxxxx.add(new Vec3(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx).subtract(â˜ƒxxxxxxxxx).scale(0.2)));
         }
      }
   }

   abstract class PhantomMoveTargetGoal extends Goal {
      public PhantomMoveTargetGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      protected boolean touchingTarget() {
         return Phantom.this.moveTargetPoint.distanceToSqr(Phantom.this.getX(), Phantom.this.getY(), Phantom.this.getZ()) < 4.0;
      }
   }

   class PhantomSweepAttackGoal extends Phantom.PhantomMoveTargetGoal {
      @Override
      public boolean canUse() {
         return Phantom.this.getTarget() != null && Phantom.this.attackPhase == Phantom.AttackPhase.SWOOP;
      }

      @Override
      public boolean canContinueToUse() {
         LivingEntity â˜ƒ = Phantom.this.getTarget();
         if (â˜ƒ == null) {
            return false;
         } else if (!â˜ƒ.isAlive()) {
            return false;
         } else if (!(â˜ƒ instanceof Player) || !((Player)â˜ƒ).isSpectator() && !((Player)â˜ƒ).isCreative()) {
            if (!this.canUse()) {
               return false;
            } else {
               if (Phantom.this.tickCount % 20 == 0) {
                  List<Cat> â˜ƒ = Phantom.this.level
                     .getEntitiesOfClass(Cat.class, Phantom.this.getBoundingBox().inflate(16.0), EntitySelector.ENTITY_STILL_ALIVE);
                  if (!â˜ƒ.isEmpty()) {
                     for(Cat â˜ƒx : â˜ƒ) {
                        â˜ƒx.hiss();
                     }

                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }

      @Override
      public void start() {
      }

      @Override
      public void stop() {
         Phantom.this.setTarget(null);
         Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = Phantom.this.getTarget();
         Phantom.this.moveTargetPoint = new Vec3(â˜ƒ.getX(), â˜ƒ.getY(0.5), â˜ƒ.getZ());
         if (Phantom.this.getBoundingBox().inflate(0.2F).intersects(â˜ƒ.getBoundingBox())) {
            Phantom.this.doHurtTarget(â˜ƒ);
            Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
            if (!Phantom.this.isSilent()) {
               Phantom.this.level.levelEvent(1039, Phantom.this.blockPosition(), 0);
            }
         } else if (Phantom.this.horizontalCollision || Phantom.this.hurtTime > 0) {
            Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
         }
      }
   }
}
