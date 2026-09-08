package net.minecraft.world.entity.animal;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class Dolphin extends WaterAnimal {
   private static final EntityDataAccessor<BlockPos> TREASURE_POS = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BLOCK_POS);
   private static final EntityDataAccessor<Boolean> GOT_FISH = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> MOISTNESS_LEVEL = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.INT);
   static final TargetingConditions SWIM_WITH_PLAYER_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();
   public static final int TOTAL_AIR_SUPPLY = 4800;
   private static final int TOTAL_MOISTNESS_LEVEL = 2400;
   public static final Predicate<ItemEntity> ALLOWED_ITEMS = var0 -> !var0.hasPickUpDelay() && var0.isAlive() && var0.isInWater();

   public Dolphin(EntityType<? extends Dolphin> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
      this.lookControl = new SmoothSwimmingLookControl(this, 10);
      this.setCanPickUpLoot(true);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setAirSupply(this.getMaxAirSupply());
      this.setXRot(0.0F);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canBreatheUnderwater() {
      return false;
   }

   @Override
   protected void handleAirSupply(int var1) {
   }

   public void setTreasurePos(BlockPos var1) {
      this.entityData.set(TREASURE_POS, â˜ƒ);
   }

   public BlockPos getTreasurePos() {
      return this.entityData.get(TREASURE_POS);
   }

   public boolean gotFish() {
      return this.entityData.get(GOT_FISH);
   }

   public void setGotFish(boolean var1) {
      this.entityData.set(GOT_FISH, â˜ƒ);
   }

   public int getMoistnessLevel() {
      return this.entityData.get(MOISTNESS_LEVEL);
   }

   public void setMoisntessLevel(int var1) {
      this.entityData.set(MOISTNESS_LEVEL, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(TREASURE_POS, BlockPos.ZERO);
      this.entityData.define(GOT_FISH, false);
      this.entityData.define(MOISTNESS_LEVEL, 2400);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("TreasurePosX", this.getTreasurePos().getX());
      â˜ƒ.putInt("TreasurePosY", this.getTreasurePos().getY());
      â˜ƒ.putInt("TreasurePosZ", this.getTreasurePos().getZ());
      â˜ƒ.putBoolean("GotFish", this.gotFish());
      â˜ƒ.putInt("Moistness", this.getMoistnessLevel());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      int â˜ƒ = â˜ƒ.getInt("TreasurePosX");
      int â˜ƒx = â˜ƒ.getInt("TreasurePosY");
      int â˜ƒxx = â˜ƒ.getInt("TreasurePosZ");
      this.setTreasurePos(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx));
      super.readAdditionalSaveData(â˜ƒ);
      this.setGotFish(â˜ƒ.getBoolean("GotFish"));
      this.setMoisntessLevel(â˜ƒ.getInt("Moistness"));
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new BreathAirGoal(this));
      this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
      this.goalSelector.addGoal(1, new Dolphin.DolphinSwimToTreasureGoal(this));
      this.goalSelector.addGoal(2, new Dolphin.DolphinSwimWithPlayerGoal(this, 4.0));
      this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(5, new DolphinJumpGoal(this, 10));
      this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2F, true));
      this.goalSelector.addGoal(8, new Dolphin.PlayWithItemsGoal());
      this.goalSelector.addGoal(8, new FollowBoatGoal(this));
      this.goalSelector.addGoal(9, new AvoidEntityGoal(this, Guardian.class, 8.0F, 1.0, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Guardian.class).setAlertOthers());
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.ATTACK_DAMAGE, 3.0);
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new WaterBoundPathNavigation(this, â˜ƒ);
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      boolean â˜ƒ = â˜ƒ.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
      if (â˜ƒ) {
         this.doEnchantDamageEffects(this, â˜ƒ);
         this.playSound(SoundEvents.DOLPHIN_ATTACK, 1.0F, 1.0F);
      }

      return â˜ƒ;
   }

   @Override
   public int getMaxAirSupply() {
      return 4800;
   }

   @Override
   protected int increaseAirSupply(int var1) {
      return this.getMaxAirSupply();
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.3F;
   }

   @Override
   public int getMaxHeadXRot() {
      return 1;
   }

   @Override
   public int getMaxHeadYRot() {
      return 1;
   }

   @Override
   protected boolean canRide(Entity var1) {
      return true;
   }

   @Override
   public boolean canTakeItem(ItemStack var1) {
      EquipmentSlot â˜ƒ = Mob.getEquipmentSlotForItem(â˜ƒ);
      if (!this.getItemBySlot(â˜ƒ).isEmpty()) {
         return false;
      } else {
         return â˜ƒ == EquipmentSlot.MAINHAND && super.canTakeItem(â˜ƒ);
      }
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
         ItemStack â˜ƒ = â˜ƒ.getItem();
         if (this.canHoldItem(â˜ƒ)) {
            this.onItemPickup(â˜ƒ);
            this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(â˜ƒ, â˜ƒ.getCount());
            â˜ƒ.discard();
         }
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isNoAi()) {
         this.setAirSupply(this.getMaxAirSupply());
      } else {
         if (this.isInWaterRainOrBubble()) {
            this.setMoisntessLevel(2400);
         } else {
            this.setMoisntessLevel(this.getMoistnessLevel() - 1);
            if (this.getMoistnessLevel() <= 0) {
               this.hurt(DamageSource.DRY_OUT, 1.0F);
            }

            if (this.onGround) {
               this.setDeltaMovement(
                  this.getDeltaMovement()
                     .add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F))
               );
               this.setYRot(this.random.nextFloat() * 360.0F);
               this.onGround = false;
               this.hasImpulse = true;
            }
         }

         if (this.level.isClientSide && this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.03) {
            Vec3 â˜ƒ = this.getViewVector(0.0F);
            float â˜ƒx = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * 0.3F;
            float â˜ƒxx = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * 0.3F;
            float â˜ƒxxx = 1.2F - this.random.nextFloat() * 0.7F;

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 2; ++â˜ƒxxxx) {
               this.level
                  .addParticle(
                     ParticleTypes.DOLPHIN,
                     this.getX() - â˜ƒ.x * (double)â˜ƒxxx + (double)â˜ƒx,
                     this.getY() - â˜ƒ.y,
                     this.getZ() - â˜ƒ.z * (double)â˜ƒxxx + (double)â˜ƒxx,
                     0.0,
                     0.0,
                     0.0
                  );
               this.level
                  .addParticle(
                     ParticleTypes.DOLPHIN,
                     this.getX() - â˜ƒ.x * (double)â˜ƒxxx - (double)â˜ƒx,
                     this.getY() - â˜ƒ.y,
                     this.getZ() - â˜ƒ.z * (double)â˜ƒxxx - (double)â˜ƒxx,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 38) {
         this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   private void addParticlesAroundSelf(ParticleOptions var1) {
      for(int â˜ƒ = 0; â˜ƒ < 7; ++â˜ƒ) {
         double â˜ƒx = this.random.nextGaussian() * 0.01;
         double â˜ƒxx = this.random.nextGaussian() * 0.01;
         double â˜ƒxxx = this.random.nextGaussian() * 0.01;
         this.level.addParticle(â˜ƒ, this.getRandomX(1.0), this.getRandomY() + 0.2, this.getRandomZ(1.0), â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   @Override
   protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!â˜ƒ.isEmpty() && â˜ƒ.is(ItemTags.FISHES)) {
         if (!this.level.isClientSide) {
            this.playSound(SoundEvents.DOLPHIN_EAT, 1.0F, 1.0F);
         }

         this.setGotFish(true);
         if (!â˜ƒ.getAbilities().instabuild) {
            â˜ƒ.shrink(1);
         }

         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      }
   }

   public static boolean checkDolphinSpawnRules(EntityType<Dolphin> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getY() > 45 && â˜ƒ.getY() < â˜ƒ.getSeaLevel()) {
         Optional<ResourceKey<Biome>> â˜ƒ = â˜ƒ.getBiomeName(â˜ƒ);
         return (!Objects.equals(â˜ƒ, Optional.of(Biomes.OCEAN)) || !Objects.equals(â˜ƒ, Optional.of(Biomes.DEEP_OCEAN)))
            && â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER);
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.DOLPHIN_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.DOLPHIN_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return this.isInWater() ? SoundEvents.DOLPHIN_AMBIENT_WATER : SoundEvents.DOLPHIN_AMBIENT;
   }

   @Override
   protected SoundEvent getSwimSplashSound() {
      return SoundEvents.DOLPHIN_SPLASH;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.DOLPHIN_SWIM;
   }

   protected boolean closeToNextPos() {
      BlockPos â˜ƒ = this.getNavigation().getTargetPos();
      return â˜ƒ != null ? â˜ƒ.closerThan(this.position(), 12.0) : false;
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(this.getSpeed(), â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         if (this.getTarget() == null) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(â˜ƒ);
      }
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return true;
   }

   static class DolphinSwimToTreasureGoal extends Goal {
      private final Dolphin dolphin;
      private boolean stuck;

      DolphinSwimToTreasureGoal(Dolphin var1) {
         this.dolphin = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean isInterruptable() {
         return false;
      }

      @Override
      public boolean canUse() {
         return this.dolphin.gotFish() && this.dolphin.getAirSupply() >= 100;
      }

      @Override
      public boolean canContinueToUse() {
         BlockPos â˜ƒ = this.dolphin.getTreasurePos();
         return !new BlockPos((double)â˜ƒ.getX(), this.dolphin.getY(), (double)â˜ƒ.getZ()).closerThan(this.dolphin.position(), 4.0)
            && !this.stuck
            && this.dolphin.getAirSupply() >= 100;
      }

      @Override
      public void start() {
         if (this.dolphin.level instanceof ServerLevel) {
            ServerLevel â˜ƒ = (ServerLevel)this.dolphin.level;
            this.stuck = false;
            this.dolphin.getNavigation().stop();
            BlockPos â˜ƒx = this.dolphin.blockPosition();
            StructureFeature<?> â˜ƒxx = (double)â˜ƒ.random.nextFloat() >= 0.5 ? StructureFeature.OCEAN_RUIN : StructureFeature.SHIPWRECK;
            BlockPos â˜ƒxxx = â˜ƒ.findNearestMapFeature(â˜ƒxx, â˜ƒx, 50, false);
            if (â˜ƒxxx == null) {
               StructureFeature<?> â˜ƒxxxx = â˜ƒxx.equals(StructureFeature.OCEAN_RUIN) ? StructureFeature.SHIPWRECK : StructureFeature.OCEAN_RUIN;
               BlockPos â˜ƒxxxxx = â˜ƒ.findNearestMapFeature(â˜ƒxxxx, â˜ƒx, 50, false);
               if (â˜ƒxxxxx == null) {
                  this.stuck = true;
                  return;
               }

               this.dolphin.setTreasurePos(â˜ƒxxxxx);
            } else {
               this.dolphin.setTreasurePos(â˜ƒxxx);
            }

            â˜ƒ.broadcastEntityEvent(this.dolphin, (byte)38);
         }
      }

      @Override
      public void stop() {
         BlockPos â˜ƒ = this.dolphin.getTreasurePos();
         if (new BlockPos((double)â˜ƒ.getX(), this.dolphin.getY(), (double)â˜ƒ.getZ()).closerThan(this.dolphin.position(), 4.0) || this.stuck) {
            this.dolphin.setGotFish(false);
         }
      }

      @Override
      public void tick() {
         Level â˜ƒ = this.dolphin.level;
         if (this.dolphin.closeToNextPos() || this.dolphin.getNavigation().isDone()) {
            Vec3 â˜ƒx = Vec3.atCenterOf(this.dolphin.getTreasurePos());
            Vec3 â˜ƒxx = DefaultRandomPos.getPosTowards(this.dolphin, 16, 1, â˜ƒx, (float) (Math.PI / 8));
            if (â˜ƒxx == null) {
               â˜ƒxx = DefaultRandomPos.getPosTowards(this.dolphin, 8, 4, â˜ƒx, (float) (Math.PI / 2));
            }

            if (â˜ƒxx != null) {
               BlockPos â˜ƒx = new BlockPos(â˜ƒxx);
               if (!â˜ƒ.getFluidState(â˜ƒx).is(FluidTags.WATER) || !â˜ƒ.getBlockState(â˜ƒx).isPathfindable(â˜ƒ, â˜ƒx, PathComputationType.WATER)) {
                  â˜ƒxx = DefaultRandomPos.getPosTowards(this.dolphin, 8, 5, â˜ƒx, (float) (Math.PI / 2));
               }
            }

            if (â˜ƒxx == null) {
               this.stuck = true;
               return;
            }

            this.dolphin
               .getLookControl()
               .setLookAt(â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, (float)(this.dolphin.getMaxHeadYRot() + 20), (float)this.dolphin.getMaxHeadXRot());
            this.dolphin.getNavigation().moveTo(â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, 1.3);
            if (â˜ƒ.random.nextInt(80) == 0) {
               â˜ƒ.broadcastEntityEvent(this.dolphin, (byte)38);
            }
         }
      }
   }

   static class DolphinSwimWithPlayerGoal extends Goal {
      private final Dolphin dolphin;
      private final double speedModifier;
      private Player player;

      DolphinSwimWithPlayerGoal(Dolphin var1, double var2) {
         this.dolphin = â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         this.player = this.dolphin.level.getNearestPlayer(Dolphin.SWIM_WITH_PLAYER_TARGETING, this.dolphin);
         if (this.player == null) {
            return false;
         } else {
            return this.player.isSwimming() && this.dolphin.getTarget() != this.player;
         }
      }

      @Override
      public boolean canContinueToUse() {
         return this.player != null && this.player.isSwimming() && this.dolphin.distanceToSqr(this.player) < 256.0;
      }

      @Override
      public void start() {
         this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), this.dolphin);
      }

      @Override
      public void stop() {
         this.player = null;
         this.dolphin.getNavigation().stop();
      }

      @Override
      public void tick() {
         this.dolphin.getLookControl().setLookAt(this.player, (float)(this.dolphin.getMaxHeadYRot() + 20), (float)this.dolphin.getMaxHeadXRot());
         if (this.dolphin.distanceToSqr(this.player) < 6.25) {
            this.dolphin.getNavigation().stop();
         } else {
            this.dolphin.getNavigation().moveTo(this.player, this.speedModifier);
         }

         if (this.player.isSwimming() && this.player.level.random.nextInt(6) == 0) {
            this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), this.dolphin);
         }
      }
   }

   class PlayWithItemsGoal extends Goal {
      private int cooldown;

      @Override
      public boolean canUse() {
         if (this.cooldown > Dolphin.this.tickCount) {
            return false;
         } else {
            List<ItemEntity> â˜ƒ = Dolphin.this.level
               .getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Dolphin.ALLOWED_ITEMS);
            return !â˜ƒ.isEmpty() || !Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
         }
      }

      @Override
      public void start() {
         List<ItemEntity> â˜ƒ = Dolphin.this.level
            .getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Dolphin.ALLOWED_ITEMS);
         if (!â˜ƒ.isEmpty()) {
            Dolphin.this.getNavigation().moveTo((Entity)â˜ƒ.get(0), 1.2F);
            Dolphin.this.playSound(SoundEvents.DOLPHIN_PLAY, 1.0F, 1.0F);
         }

         this.cooldown = 0;
      }

      @Override
      public void stop() {
         ItemStack â˜ƒ = Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (!â˜ƒ.isEmpty()) {
            this.drop(â˜ƒ);
            Dolphin.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.cooldown = Dolphin.this.tickCount + Dolphin.this.random.nextInt(100);
         }
      }

      @Override
      public void tick() {
         List<ItemEntity> â˜ƒ = Dolphin.this.level
            .getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Dolphin.ALLOWED_ITEMS);
         ItemStack â˜ƒx = Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (!â˜ƒx.isEmpty()) {
            this.drop(â˜ƒx);
            Dolphin.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
         } else if (!â˜ƒ.isEmpty()) {
            Dolphin.this.getNavigation().moveTo((Entity)â˜ƒ.get(0), 1.2F);
         }
      }

      private void drop(ItemStack var1) {
         if (!â˜ƒ.isEmpty()) {
            double â˜ƒ = Dolphin.this.getEyeY() - 0.3F;
            ItemEntity â˜ƒx = new ItemEntity(Dolphin.this.level, Dolphin.this.getX(), â˜ƒ, Dolphin.this.getZ(), â˜ƒ);
            â˜ƒx.setPickUpDelay(40);
            â˜ƒx.setThrower(Dolphin.this.getUUID());
            float â˜ƒxx = 0.3F;
            float â˜ƒxxx = Dolphin.this.random.nextFloat() * (float) (Math.PI * 2);
            float â˜ƒxxxx = 0.02F * Dolphin.this.random.nextFloat();
            â˜ƒx.setDeltaMovement(
               (double)(
                  0.3F * -Mth.sin(Dolphin.this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(Dolphin.this.getXRot() * (float) (Math.PI / 180.0))
                     + Mth.cos(â˜ƒxxx) * â˜ƒxxxx
               ),
               (double)(0.3F * Mth.sin(Dolphin.this.getXRot() * (float) (Math.PI / 180.0)) * 1.5F),
               (double)(
                  0.3F * Mth.cos(Dolphin.this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(Dolphin.this.getXRot() * (float) (Math.PI / 180.0))
                     + Mth.sin(â˜ƒxxx) * â˜ƒxxxx
               )
            );
            Dolphin.this.level.addFreshEntity(â˜ƒx);
         }
      }
   }
}
