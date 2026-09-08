package net.minecraft.world.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Spider extends Monster {
   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Spider.class, EntityDataSerializers.BYTE);
   private static final float SPIDER_SPECIAL_EFFECT_CHANCE = 0.1F;

   public Spider(EntityType<? extends Spider> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
      this.goalSelector.addGoal(4, new Spider.SpiderAttackGoal(this));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new Spider.SpiderTargetGoal(this, Player.class));
      this.targetSelector.addGoal(3, new Spider.SpiderTargetGoal(this, IronGolem.class));
   }

   @Override
   public double getPassengersRidingOffset() {
      return (double)(this.getBbHeight() * 0.5F);
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new WallClimberNavigation(this, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_FLAGS_ID, (byte)0);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level.isClientSide) {
         this.setClimbing(this.horizontalCollision);
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0).add(Attributes.MOVEMENT_SPEED, 0.3F);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SPIDER_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.SPIDER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.SPIDER_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
   }

   @Override
   public boolean onClimbable() {
      return this.isClimbing();
   }

   @Override
   public void makeStuckInBlock(BlockState var1, Vec3 var2) {
      if (!â˜ƒ.is(Blocks.COBWEB)) {
         super.makeStuckInBlock(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public MobType getMobType() {
      return MobType.ARTHROPOD;
   }

   @Override
   public boolean canBeAffected(MobEffectInstance var1) {
      return â˜ƒ.getEffect() == MobEffects.POISON ? false : super.canBeAffected(â˜ƒ);
   }

   public boolean isClimbing() {
      return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
   }

   public void setClimbing(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_FLAGS_ID);
      if (â˜ƒ) {
         â˜ƒ = (byte)(â˜ƒ | 1);
      } else {
         â˜ƒ = (byte)(â˜ƒ & -2);
      }

      this.entityData.set(DATA_FLAGS_ID, â˜ƒ);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.getRandom().nextInt(100) == 0) {
         Skeleton â˜ƒ = EntityType.SKELETON.create(this.level);
         â˜ƒ.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
         â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, null, null);
         â˜ƒ.startRiding(this);
      }

      if (â˜ƒ == null) {
         â˜ƒ = new Spider.SpiderEffectsGroupData();
         if (â˜ƒ.getDifficulty() == Difficulty.HARD && â˜ƒ.getRandom().nextFloat() < 0.1F * â˜ƒ.getSpecialMultiplier()) {
            ((Spider.SpiderEffectsGroupData)â˜ƒ).setRandomEffect(â˜ƒ.getRandom());
         }
      }

      if (â˜ƒ instanceof Spider.SpiderEffectsGroupData) {
         MobEffect â˜ƒ = ((Spider.SpiderEffectsGroupData)â˜ƒ).effect;
         if (â˜ƒ != null) {
            this.addEffect(new MobEffectInstance(â˜ƒ, Integer.MAX_VALUE));
         }
      }

      return â˜ƒ;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.65F;
   }

   static class SpiderAttackGoal extends MeleeAttackGoal {
      public SpiderAttackGoal(Spider var1) {
         super(â˜ƒ, 1.0, true);
      }

      @Override
      public boolean canUse() {
         return super.canUse() && !this.mob.isVehicle();
      }

      @Override
      public boolean canContinueToUse() {
         float â˜ƒ = this.mob.getBrightness();
         if (â˜ƒ >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
            this.mob.setTarget(null);
            return false;
         } else {
            return super.canContinueToUse();
         }
      }

      @Override
      protected double getAttackReachSqr(LivingEntity var1) {
         return (double)(4.0F + â˜ƒ.getBbWidth());
      }
   }

   public static class SpiderEffectsGroupData implements SpawnGroupData {
      public MobEffect effect;

      public void setRandomEffect(Random var1) {
         int â˜ƒ = â˜ƒ.nextInt(5);
         if (â˜ƒ <= 1) {
            this.effect = MobEffects.MOVEMENT_SPEED;
         } else if (â˜ƒ <= 2) {
            this.effect = MobEffects.DAMAGE_BOOST;
         } else if (â˜ƒ <= 3) {
            this.effect = MobEffects.REGENERATION;
         } else if (â˜ƒ <= 4) {
            this.effect = MobEffects.INVISIBILITY;
         }
      }
   }

   static class SpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
      public SpiderTargetGoal(Spider var1, Class<T> var2) {
         super(â˜ƒ, â˜ƒ, true);
      }

      @Override
      public boolean canUse() {
         float â˜ƒ = this.mob.getBrightness();
         return â˜ƒ >= 0.5F ? false : super.canUse();
      }
   }
}
