package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;

public class Slime extends Mob implements Enemy {
   private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Slime.class, EntityDataSerializers.INT);
   public static final int MIN_SIZE = 1;
   public static final int MAX_SIZE = 127;
   public float targetSquish;
   public float squish;
   public float oSquish;
   private boolean wasOnGround;

   public Slime(EntityType<? extends Slime> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.moveControl = new Slime.SlimeMoveControl(this);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new Slime.SlimeFloatGoal(this));
      this.goalSelector.addGoal(2, new Slime.SlimeAttackGoal(this));
      this.goalSelector.addGoal(3, new Slime.SlimeRandomDirectionGoal(this));
      this.goalSelector.addGoal(5, new Slime.SlimeKeepOnJumpingGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, var1 -> Math.abs(var1.getY() - this.getY()) <= 4.0));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(ID_SIZE, 1);
   }

   protected void setSize(int var1, boolean var2) {
      int â˜ƒ = Mth.clamp(â˜ƒ, 1, 127);
      this.entityData.set(ID_SIZE, â˜ƒ);
      this.reapplyPosition();
      this.refreshDimensions();
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(â˜ƒ * â˜ƒ));
      this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)â˜ƒ));
      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)â˜ƒ);
      if (â˜ƒ) {
         this.setHealth(this.getMaxHealth());
      }

      this.xpReward = â˜ƒ;
   }

   public int getSize() {
      return this.entityData.get(ID_SIZE);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Size", this.getSize() - 1);
      â˜ƒ.putBoolean("wasOnGround", this.wasOnGround);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.setSize(â˜ƒ.getInt("Size") + 1, false);
      super.readAdditionalSaveData(â˜ƒ);
      this.wasOnGround = â˜ƒ.getBoolean("wasOnGround");
   }

   public boolean isTiny() {
      return this.getSize() <= 1;
   }

   protected ParticleOptions getParticleType() {
      return ParticleTypes.ITEM_SLIME;
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return this.getSize() > 0;
   }

   @Override
   public void tick() {
      this.squish += (this.targetSquish - this.squish) * 0.5F;
      this.oSquish = this.squish;
      super.tick();
      if (this.onGround && !this.wasOnGround) {
         int â˜ƒ = this.getSize();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ * 8; ++â˜ƒx) {
            float â˜ƒxx = this.random.nextFloat() * (float) (Math.PI * 2);
            float â˜ƒxxx = this.random.nextFloat() * 0.5F + 0.5F;
            float â˜ƒxxxx = Mth.sin(â˜ƒxx) * (float)â˜ƒ * 0.5F * â˜ƒxxx;
            float â˜ƒxxxxx = Mth.cos(â˜ƒxx) * (float)â˜ƒ * 0.5F * â˜ƒxxx;
            this.level.addParticle(this.getParticleType(), this.getX() + (double)â˜ƒxxxx, this.getY(), this.getZ() + (double)â˜ƒxxxxx, 0.0, 0.0, 0.0);
         }

         this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
         this.targetSquish = -0.5F;
      } else if (!this.onGround && this.wasOnGround) {
         this.targetSquish = 1.0F;
      }

      this.wasOnGround = this.onGround;
      this.decreaseSquish();
   }

   protected void decreaseSquish() {
      this.targetSquish *= 0.6F;
   }

   protected int getJumpDelay() {
      return this.random.nextInt(20) + 10;
   }

   @Override
   public void refreshDimensions() {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      super.refreshDimensions();
      this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (ID_SIZE.equals(â˜ƒ)) {
         this.refreshDimensions();
         this.setYRot(this.yHeadRot);
         this.yBodyRot = this.yHeadRot;
         if (this.isInWater() && this.random.nextInt(20) == 0) {
            this.doWaterSplashEffect();
         }
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   public EntityType<? extends Slime> getType() {
      return super.getType();
   }

   @Override
   public void remove(Entity.RemovalReason var1) {
      int â˜ƒ = this.getSize();
      if (!this.level.isClientSide && â˜ƒ > 1 && this.isDeadOrDying()) {
         Component â˜ƒx = this.getCustomName();
         boolean â˜ƒxx = this.isNoAi();
         float â˜ƒxxx = (float)â˜ƒ / 4.0F;
         int â˜ƒxxxx = â˜ƒ / 2;
         int â˜ƒxxxxx = 2 + this.random.nextInt(3);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxx) {
            float â˜ƒxxxxxxx = ((float)(â˜ƒxxxxxx % 2) - 0.5F) * â˜ƒxxx;
            float â˜ƒxxxxxxxx = ((float)(â˜ƒxxxxxx / 2) - 0.5F) * â˜ƒxxx;
            Slime â˜ƒxxxxxxxxx = this.getType().create(this.level);
            if (this.isPersistenceRequired()) {
               â˜ƒxxxxxxxxx.setPersistenceRequired();
            }

            â˜ƒxxxxxxxxx.setCustomName(â˜ƒx);
            â˜ƒxxxxxxxxx.setNoAi(â˜ƒxx);
            â˜ƒxxxxxxxxx.setInvulnerable(this.isInvulnerable());
            â˜ƒxxxxxxxxx.setSize(â˜ƒxxxx, true);
            â˜ƒxxxxxxxxx.moveTo(this.getX() + (double)â˜ƒxxxxxxx, this.getY() + 0.5, this.getZ() + (double)â˜ƒxxxxxxxx, this.random.nextFloat() * 360.0F, 0.0F);
            this.level.addFreshEntity(â˜ƒxxxxxxxxx);
         }
      }

      super.remove(â˜ƒ);
   }

   @Override
   public void push(Entity var1) {
      super.push(â˜ƒ);
      if (â˜ƒ instanceof IronGolem && this.isDealsDamage()) {
         this.dealDamage((LivingEntity)â˜ƒ);
      }
   }

   @Override
   public void playerTouch(Player var1) {
      if (this.isDealsDamage()) {
         this.dealDamage(â˜ƒ);
      }
   }

   protected void dealDamage(LivingEntity var1) {
      if (this.isAlive()) {
         int â˜ƒ = this.getSize();
         if (this.distanceToSqr(â˜ƒ) < 0.6 * (double)â˜ƒ * 0.6 * (double)â˜ƒ
            && this.hasLineOfSight(â˜ƒ)
            && â˜ƒ.hurt(DamageSource.mobAttack(this), this.getAttackDamage())) {
            this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.doEnchantDamageEffects(this, â˜ƒ);
         }
      }
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.625F * â˜ƒ.height;
   }

   protected boolean isDealsDamage() {
      return !this.isTiny() && this.isEffectiveAi();
   }

   protected float getAttackDamage() {
      return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isTiny() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isTiny() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
   }

   protected SoundEvent getSquishSound() {
      return this.isTiny() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
   }

   @Override
   protected ResourceLocation getDefaultLootTable() {
      return this.getSize() == 1 ? this.getType().getDefaultLootTable() : BuiltInLootTables.EMPTY;
   }

   public static boolean checkSlimeSpawnRules(EntityType<Slime> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getDifficulty() != Difficulty.PEACEFUL) {
         if (Objects.equals(â˜ƒ.getBiomeName(â˜ƒ), Optional.of(Biomes.SWAMP))
            && â˜ƒ.getY() > 50
            && â˜ƒ.getY() < 70
            && â˜ƒ.nextFloat() < 0.5F
            && â˜ƒ.nextFloat() < â˜ƒ.getMoonBrightness()
            && â˜ƒ.getMaxLocalRawBrightness(â˜ƒ) <= â˜ƒ.nextInt(8)) {
            return checkMobSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         if (!(â˜ƒ instanceof WorldGenLevel)) {
            return false;
         }

         ChunkPos â˜ƒ = new ChunkPos(â˜ƒ);
         boolean â˜ƒx = WorldgenRandom.seedSlimeChunk(â˜ƒ.x, â˜ƒ.z, ((WorldGenLevel)â˜ƒ).getSeed(), 987234911L).nextInt(10) == 0;
         if (â˜ƒ.nextInt(10) == 0 && â˜ƒx && â˜ƒ.getY() < 40) {
            return checkMobSpawnRules(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      return false;
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F * (float)this.getSize();
   }

   @Override
   public int getMaxHeadXRot() {
      return 0;
   }

   protected boolean doPlayJumpSound() {
      return this.getSize() > 0;
   }

   @Override
   protected void jumpFromGround() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setDeltaMovement(â˜ƒ.x, (double)this.getJumpPower(), â˜ƒ.z);
      this.hasImpulse = true;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      int â˜ƒ = this.random.nextInt(3);
      if (â˜ƒ < 2 && this.random.nextFloat() < 0.5F * â˜ƒ.getSpecialMultiplier()) {
         ++â˜ƒ;
      }

      int â˜ƒ = 1 << â˜ƒ;
      this.setSize(â˜ƒ, true);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   float getSoundPitch() {
      float â˜ƒ = this.isTiny() ? 1.4F : 0.8F;
      return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * â˜ƒ;
   }

   protected SoundEvent getJumpSound() {
      return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return super.getDimensions(â˜ƒ).scale(0.255F * (float)this.getSize());
   }

   static class SlimeAttackGoal extends Goal {
      private final Slime slime;
      private int growTiredTimer;

      public SlimeAttackGoal(Slime var1) {
         this.slime = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = this.slime.getTarget();
         if (â˜ƒ == null) {
            return false;
         } else {
            return !this.slime.canAttack(â˜ƒ) ? false : this.slime.getMoveControl() instanceof Slime.SlimeMoveControl;
         }
      }

      @Override
      public void start() {
         this.growTiredTimer = 300;
         super.start();
      }

      @Override
      public boolean canContinueToUse() {
         LivingEntity â˜ƒ = this.slime.getTarget();
         if (â˜ƒ == null) {
            return false;
         } else if (!this.slime.canAttack(â˜ƒ)) {
            return false;
         } else {
            return --this.growTiredTimer > 0;
         }
      }

      @Override
      public void tick() {
         this.slime.lookAt(this.slime.getTarget(), 10.0F, 10.0F);
         ((Slime.SlimeMoveControl)this.slime.getMoveControl()).setDirection(this.slime.getYRot(), this.slime.isDealsDamage());
      }
   }

   static class SlimeFloatGoal extends Goal {
      private final Slime slime;

      public SlimeFloatGoal(Slime var1) {
         this.slime = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
         â˜ƒ.getNavigation().setCanFloat(true);
      }

      @Override
      public boolean canUse() {
         return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof Slime.SlimeMoveControl;
      }

      @Override
      public void tick() {
         if (this.slime.getRandom().nextFloat() < 0.8F) {
            this.slime.getJumpControl().jump();
         }

         ((Slime.SlimeMoveControl)this.slime.getMoveControl()).setWantedMovement(1.2);
      }
   }

   static class SlimeKeepOnJumpingGoal extends Goal {
      private final Slime slime;

      public SlimeKeepOnJumpingGoal(Slime var1) {
         this.slime = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         return !this.slime.isPassenger();
      }

      @Override
      public void tick() {
         ((Slime.SlimeMoveControl)this.slime.getMoveControl()).setWantedMovement(1.0);
      }
   }

   static class SlimeMoveControl extends MoveControl {
      private float yRot;
      private int jumpDelay;
      private final Slime slime;
      private boolean isAggressive;

      public SlimeMoveControl(Slime var1) {
         super(â˜ƒ);
         this.slime = â˜ƒ;
         this.yRot = 180.0F * â˜ƒ.getYRot() / (float) Math.PI;
      }

      public void setDirection(float var1, boolean var2) {
         this.yRot = â˜ƒ;
         this.isAggressive = â˜ƒ;
      }

      public void setWantedMovement(double var1) {
         this.speedModifier = â˜ƒ;
         this.operation = MoveControl.Operation.MOVE_TO;
      }

      @Override
      public void tick() {
         this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
         this.mob.yHeadRot = this.mob.getYRot();
         this.mob.yBodyRot = this.mob.getYRot();
         if (this.operation != MoveControl.Operation.MOVE_TO) {
            this.mob.setZza(0.0F);
         } else {
            this.operation = MoveControl.Operation.WAIT;
            if (this.mob.isOnGround()) {
               this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
               if (this.jumpDelay-- <= 0) {
                  this.jumpDelay = this.slime.getJumpDelay();
                  if (this.isAggressive) {
                     this.jumpDelay /= 3;
                  }

                  this.slime.getJumpControl().jump();
                  if (this.slime.doPlayJumpSound()) {
                     this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
                  }
               } else {
                  this.slime.xxa = 0.0F;
                  this.slime.zza = 0.0F;
                  this.mob.setSpeed(0.0F);
               }
            } else {
               this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
         }
      }
   }

   static class SlimeRandomDirectionGoal extends Goal {
      private final Slime slime;
      private float chosenDegrees;
      private int nextRandomizeTime;

      public SlimeRandomDirectionGoal(Slime var1) {
         this.slime = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         return this.slime.getTarget() == null
            && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION))
            && this.slime.getMoveControl() instanceof Slime.SlimeMoveControl;
      }

      @Override
      public void tick() {
         if (--this.nextRandomizeTime <= 0) {
            this.nextRandomizeTime = 40 + this.slime.getRandom().nextInt(60);
            this.chosenDegrees = (float)this.slime.getRandom().nextInt(360);
         }

         ((Slime.SlimeMoveControl)this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
      }
   }
}
