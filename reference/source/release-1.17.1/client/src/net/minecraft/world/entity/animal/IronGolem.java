package net.minecraft.world.entity.animal;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class IronGolem extends AbstractGolem implements NeutralMob {
   protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(IronGolem.class, EntityDataSerializers.BYTE);
   private static final int IRON_INGOT_HEAL_AMOUNT = 25;
   private int attackAnimationTick;
   private int offerFlowerTick;
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   private UUID persistentAngerTarget;

   public IronGolem(EntityType<? extends IronGolem> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.maxUpStep = 1.0F;
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 0.6, false));
      this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
      this.goalSelector.addGoal(5, new OfferFlowerGoal(this));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new DefendVillageTargetGoal(this));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::isAngryAt));
      this.targetSelector
         .addGoal(3, new NearestAttackableTargetGoal(this, Mob.class, 5, false, false, var0 -> var0 instanceof Enemy && !(var0 instanceof Creeper)));
      this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_FLAGS_ID, (byte)0);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 100.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.ATTACK_DAMAGE, 15.0);
   }

   @Override
   protected int decreaseAirSupply(int var1) {
      return â˜ƒ;
   }

   @Override
   protected void doPush(Entity var1) {
      if (â˜ƒ instanceof Enemy && !(â˜ƒ instanceof Creeper) && this.getRandom().nextInt(20) == 0) {
         this.setTarget((LivingEntity)â˜ƒ);
      }

      super.doPush(â˜ƒ);
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.attackAnimationTick > 0) {
         --this.attackAnimationTick;
      }

      if (this.offerFlowerTick > 0) {
         --this.offerFlowerTick;
      }

      if (this.getDeltaMovement().horizontalDistanceSqr() > 2.5000003E-7F && this.random.nextInt(5) == 0) {
         int â˜ƒ = Mth.floor(this.getX());
         int â˜ƒx = Mth.floor(this.getY() - 0.2F);
         int â˜ƒxx = Mth.floor(this.getZ());
         BlockState â˜ƒxxx = this.level.getBlockState(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx));
         if (!â˜ƒxxx.isAir()) {
            this.level
               .addParticle(
                  new BlockParticleOption(ParticleTypes.BLOCK, â˜ƒxxx),
                  this.getX() + ((double)this.random.nextFloat() - 0.5) * (double)this.getBbWidth(),
                  this.getY() + 0.1,
                  this.getZ() + ((double)this.random.nextFloat() - 0.5) * (double)this.getBbWidth(),
                  4.0 * ((double)this.random.nextFloat() - 0.5),
                  0.5,
                  ((double)this.random.nextFloat() - 0.5) * 4.0
               );
         }
      }

      if (!this.level.isClientSide) {
         this.updatePersistentAnger((ServerLevel)this.level, true);
      }
   }

   @Override
   public boolean canAttackType(EntityType<?> var1) {
      if (this.isPlayerCreated() && â˜ƒ == EntityType.PLAYER) {
         return false;
      } else {
         return â˜ƒ == EntityType.CREEPER ? false : super.canAttackType(â˜ƒ);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("PlayerCreated", this.isPlayerCreated());
      this.addPersistentAngerSaveData(â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setPlayerCreated(â˜ƒ.getBoolean("PlayerCreated"));
      this.readPersistentAngerSaveData(this.level, â˜ƒ);
   }

   @Override
   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   @Override
   public void setRemainingPersistentAngerTime(int var1) {
      this.remainingPersistentAngerTime = â˜ƒ;
   }

   @Override
   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   @Override
   public void setPersistentAngerTarget(@Nullable UUID var1) {
      this.persistentAngerTarget = â˜ƒ;
   }

   @Override
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   private float getAttackDamage() {
      return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      this.attackAnimationTick = 10;
      this.level.broadcastEntityEvent(this, (byte)4);
      float â˜ƒ = this.getAttackDamage();
      float â˜ƒx = (int)â˜ƒ > 0 ? â˜ƒ / 2.0F + (float)this.random.nextInt((int)â˜ƒ) : â˜ƒ;
      boolean â˜ƒxx = â˜ƒ.hurt(DamageSource.mobAttack(this), â˜ƒx);
      if (â˜ƒxx) {
         â˜ƒ.setDeltaMovement(â˜ƒ.getDeltaMovement().add(0.0, 0.4F, 0.0));
         this.doEnchantDamageEffects(this, â˜ƒ);
      }

      this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
      return â˜ƒxx;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      IronGolem.Crackiness â˜ƒ = this.getCrackiness();
      boolean â˜ƒx = super.hurt(â˜ƒ, â˜ƒ);
      if (â˜ƒx && this.getCrackiness() != â˜ƒ) {
         this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
      }

      return â˜ƒx;
   }

   public IronGolem.Crackiness getCrackiness() {
      return IronGolem.Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 4) {
         this.attackAnimationTick = 10;
         this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
      } else if (â˜ƒ == 11) {
         this.offerFlowerTick = 400;
      } else if (â˜ƒ == 34) {
         this.offerFlowerTick = 0;
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   public int getAttackAnimationTick() {
      return this.attackAnimationTick;
   }

   public void offerFlower(boolean var1) {
      if (â˜ƒ) {
         this.offerFlowerTick = 400;
         this.level.broadcastEntityEvent(this, (byte)11);
      } else {
         this.offerFlowerTick = 0;
         this.level.broadcastEntityEvent(this, (byte)34);
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.IRON_GOLEM_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.IRON_GOLEM_DEATH;
   }

   @Override
   protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!â˜ƒ.is(Items.IRON_INGOT)) {
         return InteractionResult.PASS;
      } else {
         float â˜ƒ = this.getHealth();
         this.heal(25.0F);
         if (this.getHealth() == â˜ƒ) {
            return InteractionResult.PASS;
         } else {
            float â˜ƒ = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
            this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, â˜ƒ);
            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒ.shrink(1);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }
      }
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
   }

   public int getOfferFlowerTick() {
      return this.offerFlowerTick;
   }

   public boolean isPlayerCreated() {
      return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
   }

   public void setPlayerCreated(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_FLAGS_ID);
      if (â˜ƒ) {
         this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ | 1));
      } else {
         this.entityData.set(DATA_FLAGS_ID, (byte)(â˜ƒ & -2));
      }
   }

   @Override
   public void die(DamageSource var1) {
      super.die(â˜ƒ);
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      BlockPos â˜ƒ = this.blockPosition();
      BlockPos â˜ƒx = â˜ƒ.below();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (!â˜ƒxx.entityCanStandOn(â˜ƒ, â˜ƒx, this)) {
         return false;
      } else {
         for(int â˜ƒ = 1; â˜ƒ < 3; ++â˜ƒ) {
            BlockPos â˜ƒx = â˜ƒ.above(â˜ƒ);
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            if (!NaturalSpawner.isValidEmptySpawnBlock(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxx.getFluidState(), EntityType.IRON_GOLEM)) {
               return false;
            }
         }

         return NaturalSpawner.isValidEmptySpawnBlock(â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ), Fluids.EMPTY.defaultFluidState(), EntityType.IRON_GOLEM)
            && â˜ƒ.isUnobstructed(this);
      }
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.875F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }

   public static enum Crackiness {
      NONE(1.0F),
      LOW(0.75F),
      MEDIUM(0.5F),
      HIGH(0.25F);

      private static final List<IronGolem.Crackiness> BY_DAMAGE = (List<IronGolem.Crackiness>)Stream.of(values())
         .sorted(Comparator.comparingDouble(var0 -> (double)var0.fraction))
         .collect(ImmutableList.toImmutableList());
      private final float fraction;

      private Crackiness(float var3) {
         this.fraction = â˜ƒ;
      }

      public static IronGolem.Crackiness byFraction(float var0) {
         for(IronGolem.Crackiness â˜ƒ : BY_DAMAGE) {
            if (â˜ƒ < â˜ƒ.fraction) {
               return â˜ƒ;
            }
         }

         return NONE;
      }
   }
}
