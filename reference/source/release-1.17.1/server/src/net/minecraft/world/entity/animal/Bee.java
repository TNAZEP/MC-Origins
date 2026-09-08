package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Bee extends Animal implements NeutralMob, FlyingAnimal {
   public static final float FLAP_DEGREES_PER_TICK = 120.32113F;
   public static final int TICKS_PER_FLAP = Mth.ceil(1.4959966F);
   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Bee.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Bee.class, EntityDataSerializers.INT);
   private static final int FLAG_ROLL = 2;
   private static final int FLAG_HAS_STUNG = 4;
   private static final int FLAG_HAS_NECTAR = 8;
   private static final int STING_DEATH_COUNTDOWN = 1200;
   private static final int TICKS_BEFORE_GOING_TO_KNOWN_FLOWER = 2400;
   private static final int TICKS_WITHOUT_NECTAR_BEFORE_GOING_HOME = 3600;
   private static final int MIN_ATTACK_DIST = 4;
   private static final int MAX_CROPS_GROWABLE = 10;
   private static final int POISON_SECONDS_NORMAL = 10;
   private static final int POISON_SECONDS_HARD = 18;
   private static final int TOO_FAR_DISTANCE = 32;
   private static final int HIVE_CLOSE_ENOUGH_DISTANCE = 2;
   private static final int PATHFIND_TO_HIVE_WHEN_CLOSER_THAN = 16;
   private static final int HIVE_SEARCH_DISTANCE = 20;
   public static final String TAG_CROPS_GROWN_SINCE_POLLINATION = "CropsGrownSincePollination";
   public static final String TAG_CANNOT_ENTER_HIVE_TICKS = "CannotEnterHiveTicks";
   public static final String TAG_TICKS_SINCE_POLLINATION = "TicksSincePollination";
   public static final String TAG_HAS_STUNG = "HasStung";
   public static final String TAG_HAS_NECTAR = "HasNectar";
   public static final String TAG_FLOWER_POS = "FlowerPos";
   public static final String TAG_HIVE_POS = "HivePos";
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private UUID persistentAngerTarget;
   private float rollAmount;
   private float rollAmountO;
   private int timeSinceSting;
   int ticksWithoutNectarSinceExitingHive;
   private int stayOutOfHiveCountdown;
   private int numCropsGrownSincePollination;
   private static final int COOLDOWN_BEFORE_LOCATING_NEW_HIVE = 200;
   int remainingCooldownBeforeLocatingNewHive;
   private static final int COOLDOWN_BEFORE_LOCATING_NEW_FLOWER = 200;
   int remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(this.random, 20, 60);
   @Nullable
   BlockPos savedFlowerPos;
   @Nullable
   BlockPos hivePos;
   Bee.BeePollinateGoal beePollinateGoal;
   Bee.BeeGoToHiveGoal goToHiveGoal;
   private Bee.BeeGoToKnownFlowerGoal goToKnownFlowerGoal;
   private int underWaterTicks;

   public Bee(EntityType<? extends Bee> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.moveControl = new FlyingMoveControl(this, 20, true);
      this.lookControl = new Bee.BeeLookControl(this);
      this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
      this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_FLAGS_ID, (byte)0);
      this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return â˜ƒ.getBlockState(â˜ƒ).isAir() ? 10.0F : 0.0F;
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new Bee.BeeAttackGoal(this, 1.4F, true));
      this.goalSelector.addGoal(1, new Bee.BeeEnterHiveGoal());
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ItemTags.FLOWERS), false));
      this.beePollinateGoal = new Bee.BeePollinateGoal();
      this.goalSelector.addGoal(4, this.beePollinateGoal);
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25));
      this.goalSelector.addGoal(5, new Bee.BeeLocateHiveGoal());
      this.goToHiveGoal = new Bee.BeeGoToHiveGoal();
      this.goalSelector.addGoal(5, this.goToHiveGoal);
      this.goToKnownFlowerGoal = new Bee.BeeGoToKnownFlowerGoal();
      this.goalSelector.addGoal(6, this.goToKnownFlowerGoal);
      this.goalSelector.addGoal(7, new Bee.BeeGrowCropGoal());
      this.goalSelector.addGoal(8, new Bee.BeeWanderGoal());
      this.goalSelector.addGoal(9, new FloatGoal(this));
      this.targetSelector.addGoal(1, new Bee.BeeHurtByOtherGoal(this).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new Bee.BeeBecomeAngryTargetGoal(this));
      this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.hasHive()) {
         â˜ƒ.put("HivePos", NbtUtils.writeBlockPos(this.getHivePos()));
      }

      if (this.hasSavedFlowerPos()) {
         â˜ƒ.put("FlowerPos", NbtUtils.writeBlockPos(this.getSavedFlowerPos()));
      }

      â˜ƒ.putBoolean("HasNectar", this.hasNectar());
      â˜ƒ.putBoolean("HasStung", this.hasStung());
      â˜ƒ.putInt("TicksSincePollination", this.ticksWithoutNectarSinceExitingHive);
      â˜ƒ.putInt("CannotEnterHiveTicks", this.stayOutOfHiveCountdown);
      â˜ƒ.putInt("CropsGrownSincePollination", this.numCropsGrownSincePollination);
      this.addPersistentAngerSaveData(â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.hivePos = null;
      if (â˜ƒ.contains("HivePos")) {
         this.hivePos = NbtUtils.readBlockPos(â˜ƒ.getCompound("HivePos"));
      }

      this.savedFlowerPos = null;
      if (â˜ƒ.contains("FlowerPos")) {
         this.savedFlowerPos = NbtUtils.readBlockPos(â˜ƒ.getCompound("FlowerPos"));
      }

      super.readAdditionalSaveData(â˜ƒ);
      this.setHasNectar(â˜ƒ.getBoolean("HasNectar"));
      this.setHasStung(â˜ƒ.getBoolean("HasStung"));
      this.ticksWithoutNectarSinceExitingHive = â˜ƒ.getInt("TicksSincePollination");
      this.stayOutOfHiveCountdown = â˜ƒ.getInt("CannotEnterHiveTicks");
      this.numCropsGrownSincePollination = â˜ƒ.getInt("CropsGrownSincePollination");
      this.readPersistentAngerSaveData(this.level, â˜ƒ);
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      boolean â˜ƒ = â˜ƒ.hurt(DamageSource.sting(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
      if (â˜ƒ) {
         this.doEnchantDamageEffects(this, â˜ƒ);
         if (â˜ƒ instanceof LivingEntity) {
            ((LivingEntity)â˜ƒ).setStingerCount(((LivingEntity)â˜ƒ).getStingerCount() + 1);
            int â˜ƒx = 0;
            if (this.level.getDifficulty() == Difficulty.NORMAL) {
               â˜ƒx = 10;
            } else if (this.level.getDifficulty() == Difficulty.HARD) {
               â˜ƒx = 18;
            }

            if (â˜ƒx > 0) {
               ((LivingEntity)â˜ƒ).addEffect(new MobEffectInstance(MobEffects.POISON, â˜ƒx * 20, 0), this);
            }
         }

         this.setHasStung(true);
         this.stopBeingAngry();
         this.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
      }

      return â˜ƒ;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.hasNectar() && this.getCropsGrownSincePollination() < 10 && this.random.nextFloat() < 0.05F) {
         for(int â˜ƒ = 0; â˜ƒ < this.random.nextInt(2) + 1; ++â˜ƒ) {
            this.spawnFluidParticle(
               this.level, this.getX() - 0.3F, this.getX() + 0.3F, this.getZ() - 0.3F, this.getZ() + 0.3F, this.getY(0.5), ParticleTypes.FALLING_NECTAR
            );
         }
      }

      this.updateRollAmount();
   }

   private void spawnFluidParticle(Level var1, double var2, double var4, double var6, double var8, double var10, ParticleOptions var12) {
      â˜ƒ.addParticle(â˜ƒ, Mth.lerp(â˜ƒ.random.nextDouble(), â˜ƒ, â˜ƒ), â˜ƒ, Mth.lerp(â˜ƒ.random.nextDouble(), â˜ƒ, â˜ƒ), 0.0, 0.0, 0.0);
   }

   void pathfindRandomlyTowards(BlockPos var1) {
      Vec3 â˜ƒ = Vec3.atBottomCenterOf(â˜ƒ);
      int â˜ƒx = 0;
      BlockPos â˜ƒxx = this.blockPosition();
      int â˜ƒxxx = (int)â˜ƒ.y - â˜ƒxx.getY();
      if (â˜ƒxxx > 2) {
         â˜ƒx = 4;
      } else if (â˜ƒxxx < -2) {
         â˜ƒx = -4;
      }

      int â˜ƒ = 6;
      int â˜ƒx = 8;
      int â˜ƒxx = â˜ƒxx.distManhattan(â˜ƒ);
      if (â˜ƒxx < 15) {
         â˜ƒ = â˜ƒxx / 2;
         â˜ƒx = â˜ƒxx / 2;
      }

      Vec3 â˜ƒ = AirRandomPos.getPosTowards(this, â˜ƒ, â˜ƒx, â˜ƒx, â˜ƒ, (float) (Math.PI / 10));
      if (â˜ƒ != null) {
         this.navigation.setMaxVisitedNodesMultiplier(0.5F);
         this.navigation.moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, 1.0);
      }
   }

   @Nullable
   public BlockPos getSavedFlowerPos() {
      return this.savedFlowerPos;
   }

   public boolean hasSavedFlowerPos() {
      return this.savedFlowerPos != null;
   }

   public void setSavedFlowerPos(BlockPos var1) {
      this.savedFlowerPos = â˜ƒ;
   }

   @VisibleForDebug
   public int getTravellingTicks() {
      return Math.max(this.goToHiveGoal.travellingTicks, this.goToKnownFlowerGoal.travellingTicks);
   }

   @VisibleForDebug
   public List<BlockPos> getBlacklistedHives() {
      return this.goToHiveGoal.blacklistedTargets;
   }

   private boolean isTiredOfLookingForNectar() {
      return this.ticksWithoutNectarSinceExitingHive > 3600;
   }

   boolean wantsToEnterHive() {
      if (this.stayOutOfHiveCountdown <= 0 && !this.beePollinateGoal.isPollinating() && !this.hasStung() && this.getTarget() == null) {
         boolean â˜ƒ = this.isTiredOfLookingForNectar() || this.level.isRaining() || this.level.isNight() || this.hasNectar();
         return â˜ƒ && !this.isHiveNearFire();
      } else {
         return false;
      }
   }

   public void setStayOutOfHiveCountdown(int var1) {
      this.stayOutOfHiveCountdown = â˜ƒ;
   }

   public float getRollAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.rollAmountO, this.rollAmount);
   }

   private void updateRollAmount() {
      this.rollAmountO = this.rollAmount;
      if (this.isRolling()) {
         this.rollAmount = Math.min(1.0F, this.rollAmount + 0.2F);
      } else {
         this.rollAmount = Math.max(0.0F, this.rollAmount - 0.24F);
      }
   }

   @Override
   protected void customServerAiStep() {
      boolean â˜ƒ = this.hasStung();
      if (this.isInWaterOrBubble()) {
         ++this.underWaterTicks;
      } else {
         this.underWaterTicks = 0;
      }

      if (this.underWaterTicks > 20) {
         this.hurt(DamageSource.DROWN, 1.0F);
      }

      if (â˜ƒ) {
         ++this.timeSinceSting;
         if (this.timeSinceSting % 5 == 0 && this.random.nextInt(Mth.clamp(1200 - this.timeSinceSting, 1, 1200)) == 0) {
            this.hurt(DamageSource.GENERIC, this.getHealth());
         }
      }

      if (!this.hasNectar()) {
         ++this.ticksWithoutNectarSinceExitingHive;
      }

      if (!this.level.isClientSide) {
         this.updatePersistentAnger((ServerLevel)this.level, false);
      }
   }

   public void resetTicksWithoutNectarSinceExitingHive() {
      this.ticksWithoutNectarSinceExitingHive = 0;
   }

   private boolean isHiveNearFire() {
      if (this.hivePos == null) {
         return false;
      } else {
         BlockEntity â˜ƒ = this.level.getBlockEntity(this.hivePos);
         return â˜ƒ instanceof BeehiveBlockEntity && ((BeehiveBlockEntity)â˜ƒ).isFireNearby();
      }
   }

   @Override
   public int getRemainingPersistentAngerTime() {
      return this.entityData.get(DATA_REMAINING_ANGER_TIME);
   }

   @Override
   public void setRemainingPersistentAngerTime(int var1) {
      this.entityData.set(DATA_REMAINING_ANGER_TIME, â˜ƒ);
   }

   @Override
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   @Override
   public void setPersistentAngerTarget(@Nullable UUID var1) {
      this.persistentAngerTarget = â˜ƒ;
   }

   @Override
   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   private boolean doesHiveHaveSpace(BlockPos var1) {
      BlockEntity â˜ƒ = this.level.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof BeehiveBlockEntity) {
         return !((BeehiveBlockEntity)â˜ƒ).isFull();
      } else {
         return false;
      }
   }

   @VisibleForDebug
   public boolean hasHive() {
      return this.hivePos != null;
   }

   @Nullable
   @VisibleForDebug
   public BlockPos getHivePos() {
      return this.hivePos;
   }

   @VisibleForDebug
   public GoalSelector getGoalSelector() {
      return this.goalSelector;
   }

   @Override
   protected void sendDebugPackets() {
      super.sendDebugPackets();
      DebugPackets.sendBeeInfo(this);
   }

   int getCropsGrownSincePollination() {
      return this.numCropsGrownSincePollination;
   }

   private void resetNumCropsGrownSincePollination() {
      this.numCropsGrownSincePollination = 0;
   }

   void incrementNumCropsGrownSincePollination() {
      ++this.numCropsGrownSincePollination;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (!this.level.isClientSide) {
         if (this.stayOutOfHiveCountdown > 0) {
            --this.stayOutOfHiveCountdown;
         }

         if (this.remainingCooldownBeforeLocatingNewHive > 0) {
            --this.remainingCooldownBeforeLocatingNewHive;
         }

         if (this.remainingCooldownBeforeLocatingNewFlower > 0) {
            --this.remainingCooldownBeforeLocatingNewFlower;
         }

         boolean â˜ƒ = this.isAngry() && !this.hasStung() && this.getTarget() != null && this.getTarget().distanceToSqr(this) < 4.0;
         this.setRolling(â˜ƒ);
         if (this.tickCount % 20 == 0 && !this.isHiveValid()) {
            this.hivePos = null;
         }
      }
   }

   boolean isHiveValid() {
      if (!this.hasHive()) {
         return false;
      } else {
         BlockEntity â˜ƒ = this.level.getBlockEntity(this.hivePos);
         return â˜ƒ != null && â˜ƒ.getType() == BlockEntityType.BEEHIVE;
      }
   }

   public boolean hasNectar() {
      return this.getFlag(8);
   }

   void setHasNectar(boolean var1) {
      if (â˜ƒ) {
         this.resetTicksWithoutNectarSinceExitingHive();
      }

      this.setFlag(8, â˜ƒ);
   }

   public boolean hasStung() {
      return this.getFlag(4);
   }

   private void setHasStung(boolean var1) {
      this.setFlag(4, â˜ƒ);
   }

   private boolean isRolling() {
      return this.getFlag(2);
   }

   private void setRolling(boolean var1) {
      this.setFlag(2, â˜ƒ);
   }

   boolean isTooFarAway(BlockPos var1) {
      return !this.closerThan(â˜ƒ, 32);
   }

   private void setFlag(int var1, boolean var2) {
      if (â˜ƒ) {
         this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) | â˜ƒ));
      } else {
         this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) & ~â˜ƒ));
      }
   }

   private boolean getFlag(int var1) {
      return (this.entityData.get(DATA_FLAGS_ID) & â˜ƒ) != 0;
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 10.0)
         .add(Attributes.FLYING_SPEED, 0.6F)
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.ATTACK_DAMAGE, 2.0)
         .add(Attributes.FOLLOW_RANGE, 48.0);
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      FlyingPathNavigation â˜ƒ = new FlyingPathNavigation(this, â˜ƒ) {
         @Override
         public boolean isStableDestination(BlockPos var1) {
            return !this.level.getBlockState(â˜ƒ.below()).isAir();
         }

         @Override
         public void tick() {
            if (!Bee.this.beePollinateGoal.isPollinating()) {
               super.tick();
            }
         }
      };
      â˜ƒ.setCanOpenDoors(false);
      â˜ƒ.setCanFloat(false);
      â˜ƒ.setCanPassDoors(true);
      return â˜ƒ;
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return â˜ƒ.is(ItemTags.FLOWERS);
   }

   boolean isFlowerValid(BlockPos var1) {
      return this.level.isLoaded(â˜ƒ) && this.level.getBlockState(â˜ƒ).is(BlockTags.FLOWERS);
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.BEE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.BEE_DEATH;
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   public Bee getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return EntityType.BEE.create(â˜ƒ);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return this.isBaby() ? â˜ƒ.height * 0.5F : â˜ƒ.height * 0.5F;
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      return false;
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
   }

   @Override
   public boolean isFlapping() {
      return this.isFlying() && this.tickCount % TICKS_PER_FLAP == 0;
   }

   @Override
   public boolean isFlying() {
      return !this.onGround;
   }

   public void dropOffNectar() {
      this.setHasNectar(false);
      this.resetNumCropsGrownSincePollination();
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         if (!this.level.isClientSide) {
            this.beePollinateGoal.stopPollinating();
         }

         return super.hurt(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public MobType getMobType() {
      return MobType.ARTHROPOD;
   }

   @Override
   protected void jumpInLiquid(Tag<Fluid> var1) {
      this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.01, 0.0));
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.5F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.2F));
   }

   boolean closerThan(BlockPos var1, int var2) {
      return â˜ƒ.closerThan(this.blockPosition(), (double)â˜ƒ);
   }

   abstract class BaseBeeGoal extends Goal {
      public abstract boolean canBeeUse();

      public abstract boolean canBeeContinueToUse();

      @Override
      public boolean canUse() {
         return this.canBeeUse() && !Bee.this.isAngry();
      }

      @Override
      public boolean canContinueToUse() {
         return this.canBeeContinueToUse() && !Bee.this.isAngry();
      }
   }

   class BeeAttackGoal extends MeleeAttackGoal {
      BeeAttackGoal(PathfinderMob var2, double var3, boolean var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean canUse() {
         return super.canUse() && Bee.this.isAngry() && !Bee.this.hasStung();
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && Bee.this.isAngry() && !Bee.this.hasStung();
      }
   }

   static class BeeBecomeAngryTargetGoal extends NearestAttackableTargetGoal<Player> {
      BeeBecomeAngryTargetGoal(Bee var1) {
         super(â˜ƒ, Player.class, 10, true, false, â˜ƒ::isAngryAt);
      }

      @Override
      public boolean canUse() {
         return this.beeCanTarget() && super.canUse();
      }

      @Override
      public boolean canContinueToUse() {
         boolean â˜ƒ = this.beeCanTarget();
         if (â˜ƒ && this.mob.getTarget() != null) {
            return super.canContinueToUse();
         } else {
            this.targetMob = null;
            return false;
         }
      }

      private boolean beeCanTarget() {
         Bee â˜ƒ = (Bee)this.mob;
         return â˜ƒ.isAngry() && !â˜ƒ.hasStung();
      }
   }

   class BeeEnterHiveGoal extends Bee.BaseBeeGoal {
      @Override
      public boolean canBeeUse() {
         if (Bee.this.hasHive() && Bee.this.wantsToEnterHive() && Bee.this.hivePos.closerThan(Bee.this.position(), 2.0)) {
            BlockEntity â˜ƒx = Bee.this.level.getBlockEntity(Bee.this.hivePos);
            if (â˜ƒx instanceof BeehiveBlockEntity â˜ƒ) {
               if (!â˜ƒ.isFull()) {
                  return true;
               }

               Bee.this.hivePos = null;
            }
         }

         return false;
      }

      @Override
      public boolean canBeeContinueToUse() {
         return false;
      }

      @Override
      public void start() {
         BlockEntity â˜ƒx = Bee.this.level.getBlockEntity(Bee.this.hivePos);
         if (â˜ƒx instanceof BeehiveBlockEntity â˜ƒ) {
            â˜ƒ.addOccupant(Bee.this, Bee.this.hasNectar());
         }
      }
   }

   @VisibleForDebug
   public class BeeGoToHiveGoal extends Bee.BaseBeeGoal {
      public static final int MAX_TRAVELLING_TICKS = 600;
      int travellingTicks = Bee.this.level.random.nextInt(10);
      private static final int MAX_BLACKLISTED_TARGETS = 3;
      final List<BlockPos> blacklistedTargets = Lists.<BlockPos>newArrayList();
      @Nullable
      private Path lastPath;
      private static final int TICKS_BEFORE_HIVE_DROP = 60;
      private int ticksStuck;

      BeeGoToHiveGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canBeeUse() {
         return Bee.this.hivePos != null
            && !Bee.this.hasRestriction()
            && Bee.this.wantsToEnterHive()
            && !this.hasReachedTarget(Bee.this.hivePos)
            && Bee.this.level.getBlockState(Bee.this.hivePos).is(BlockTags.BEEHIVES);
      }

      @Override
      public boolean canBeeContinueToUse() {
         return this.canBeeUse();
      }

      @Override
      public void start() {
         this.travellingTicks = 0;
         this.ticksStuck = 0;
         super.start();
      }

      @Override
      public void stop() {
         this.travellingTicks = 0;
         this.ticksStuck = 0;
         Bee.this.navigation.stop();
         Bee.this.navigation.resetMaxVisitedNodesMultiplier();
      }

      @Override
      public void tick() {
         if (Bee.this.hivePos != null) {
            ++this.travellingTicks;
            if (this.travellingTicks > 600) {
               this.dropAndBlacklistHive();
            } else if (!Bee.this.navigation.isInProgress()) {
               if (!Bee.this.closerThan(Bee.this.hivePos, 16)) {
                  if (Bee.this.isTooFarAway(Bee.this.hivePos)) {
                     this.dropHive();
                  } else {
                     Bee.this.pathfindRandomlyTowards(Bee.this.hivePos);
                  }
               } else {
                  boolean â˜ƒ = this.pathfindDirectlyTowards(Bee.this.hivePos);
                  if (!â˜ƒ) {
                     this.dropAndBlacklistHive();
                  } else if (this.lastPath != null && Bee.this.navigation.getPath().sameAs(this.lastPath)) {
                     ++this.ticksStuck;
                     if (this.ticksStuck > 60) {
                        this.dropHive();
                        this.ticksStuck = 0;
                     }
                  } else {
                     this.lastPath = Bee.this.navigation.getPath();
                  }
               }
            }
         }
      }

      private boolean pathfindDirectlyTowards(BlockPos var1) {
         Bee.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
         Bee.this.navigation.moveTo((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), 1.0);
         return Bee.this.navigation.getPath() != null && Bee.this.navigation.getPath().canReach();
      }

      boolean isTargetBlacklisted(BlockPos var1) {
         return this.blacklistedTargets.contains(â˜ƒ);
      }

      private void blacklistTarget(BlockPos var1) {
         this.blacklistedTargets.add(â˜ƒ);

         while(this.blacklistedTargets.size() > 3) {
            this.blacklistedTargets.remove(0);
         }
      }

      void clearBlacklist() {
         this.blacklistedTargets.clear();
      }

      private void dropAndBlacklistHive() {
         if (Bee.this.hivePos != null) {
            this.blacklistTarget(Bee.this.hivePos);
         }

         this.dropHive();
      }

      private void dropHive() {
         Bee.this.hivePos = null;
         Bee.this.remainingCooldownBeforeLocatingNewHive = 200;
      }

      private boolean hasReachedTarget(BlockPos var1) {
         if (Bee.this.closerThan(â˜ƒ, 2)) {
            return true;
         } else {
            Path â˜ƒ = Bee.this.navigation.getPath();
            return â˜ƒ != null && â˜ƒ.getTarget().equals(â˜ƒ) && â˜ƒ.canReach() && â˜ƒ.isDone();
         }
      }
   }

   public class BeeGoToKnownFlowerGoal extends Bee.BaseBeeGoal {
      private static final int MAX_TRAVELLING_TICKS = 600;
      int travellingTicks = Bee.this.level.random.nextInt(10);

      BeeGoToKnownFlowerGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canBeeUse() {
         return Bee.this.savedFlowerPos != null
            && !Bee.this.hasRestriction()
            && this.wantsToGoToKnownFlower()
            && Bee.this.isFlowerValid(Bee.this.savedFlowerPos)
            && !Bee.this.closerThan(Bee.this.savedFlowerPos, 2);
      }

      @Override
      public boolean canBeeContinueToUse() {
         return this.canBeeUse();
      }

      @Override
      public void start() {
         this.travellingTicks = 0;
         super.start();
      }

      @Override
      public void stop() {
         this.travellingTicks = 0;
         Bee.this.navigation.stop();
         Bee.this.navigation.resetMaxVisitedNodesMultiplier();
      }

      @Override
      public void tick() {
         if (Bee.this.savedFlowerPos != null) {
            ++this.travellingTicks;
            if (this.travellingTicks > 600) {
               Bee.this.savedFlowerPos = null;
            } else if (!Bee.this.navigation.isInProgress()) {
               if (Bee.this.isTooFarAway(Bee.this.savedFlowerPos)) {
                  Bee.this.savedFlowerPos = null;
               } else {
                  Bee.this.pathfindRandomlyTowards(Bee.this.savedFlowerPos);
               }
            }
         }
      }

      private boolean wantsToGoToKnownFlower() {
         return Bee.this.ticksWithoutNectarSinceExitingHive > 2400;
      }
   }

   class BeeGrowCropGoal extends Bee.BaseBeeGoal {
      static final int GROW_CHANCE = 30;

      @Override
      public boolean canBeeUse() {
         if (Bee.this.getCropsGrownSincePollination() >= 10) {
            return false;
         } else if (Bee.this.random.nextFloat() < 0.3F) {
            return false;
         } else {
            return Bee.this.hasNectar() && Bee.this.isHiveValid();
         }
      }

      @Override
      public boolean canBeeContinueToUse() {
         return this.canBeeUse();
      }

      @Override
      public void tick() {
         if (Bee.this.random.nextInt(30) == 0) {
            for(int â˜ƒ = 1; â˜ƒ <= 2; ++â˜ƒ) {
               BlockPos â˜ƒx = Bee.this.blockPosition().below(â˜ƒ);
               BlockState â˜ƒxx = Bee.this.level.getBlockState(â˜ƒx);
               Block â˜ƒxxx = â˜ƒxx.getBlock();
               boolean â˜ƒxxxx = false;
               IntegerProperty â˜ƒxxxxx = null;
               if (â˜ƒxx.is(BlockTags.BEE_GROWABLES)) {
                  if (â˜ƒxxx instanceof CropBlock â˜ƒxxxxxx) {
                     if (!â˜ƒxxxxxx.isMaxAge(â˜ƒxx)) {
                        â˜ƒxxxx = true;
                        â˜ƒxxxxx = â˜ƒxxxxxx.getAgeProperty();
                     }
                  } else if (â˜ƒxxx instanceof StemBlock) {
                     int â˜ƒxxxxxx = â˜ƒxx.getValue(StemBlock.AGE);
                     if (â˜ƒxxxxxx < 7) {
                        â˜ƒxxxx = true;
                        â˜ƒxxxxx = StemBlock.AGE;
                     }
                  } else if (â˜ƒxx.is(Blocks.SWEET_BERRY_BUSH)) {
                     int â˜ƒxxxxxx = â˜ƒxx.getValue(SweetBerryBushBlock.AGE);
                     if (â˜ƒxxxxxx < 3) {
                        â˜ƒxxxx = true;
                        â˜ƒxxxxx = SweetBerryBushBlock.AGE;
                     }
                  } else if (â˜ƒxx.is(Blocks.CAVE_VINES) || â˜ƒxx.is(Blocks.CAVE_VINES_PLANT)) {
                     ((BonemealableBlock)â˜ƒxx.getBlock()).performBonemeal((ServerLevel)Bee.this.level, Bee.this.random, â˜ƒx, â˜ƒxx);
                  }

                  if (â˜ƒxxxx) {
                     Bee.this.level.levelEvent(2005, â˜ƒx, 0);
                     Bee.this.level.setBlockAndUpdate(â˜ƒx, â˜ƒxx.setValue(â˜ƒxxxxx, Integer.valueOf(â˜ƒxx.getValue(â˜ƒxxxxx) + 1)));
                     Bee.this.incrementNumCropsGrownSincePollination();
                  }
               }
            }
         }
      }
   }

   class BeeHurtByOtherGoal extends HurtByTargetGoal {
      BeeHurtByOtherGoal(Bee var2) {
         super(â˜ƒ);
      }

      @Override
      public boolean canContinueToUse() {
         return Bee.this.isAngry() && super.canContinueToUse();
      }

      @Override
      protected void alertOther(Mob var1, LivingEntity var2) {
         if (â˜ƒ instanceof Bee && this.mob.hasLineOfSight(â˜ƒ)) {
            â˜ƒ.setTarget(â˜ƒ);
         }
      }
   }

   class BeeLocateHiveGoal extends Bee.BaseBeeGoal {
      @Override
      public boolean canBeeUse() {
         return Bee.this.remainingCooldownBeforeLocatingNewHive == 0 && !Bee.this.hasHive() && Bee.this.wantsToEnterHive();
      }

      @Override
      public boolean canBeeContinueToUse() {
         return false;
      }

      @Override
      public void start() {
         Bee.this.remainingCooldownBeforeLocatingNewHive = 200;
         List<BlockPos> â˜ƒ = this.findNearbyHivesWithSpace();
         if (!â˜ƒ.isEmpty()) {
            for(BlockPos â˜ƒx : â˜ƒ) {
               if (!Bee.this.goToHiveGoal.isTargetBlacklisted(â˜ƒx)) {
                  Bee.this.hivePos = â˜ƒx;
                  return;
               }
            }

            Bee.this.goToHiveGoal.clearBlacklist();
            Bee.this.hivePos = (BlockPos)â˜ƒ.get(0);
         }
      }

      private List<BlockPos> findNearbyHivesWithSpace() {
         BlockPos â˜ƒ = Bee.this.blockPosition();
         PoiManager â˜ƒx = ((ServerLevel)Bee.this.level).getPoiManager();
         Stream<PoiRecord> â˜ƒxx = â˜ƒx.getInRange(var0 -> var0 == PoiType.BEEHIVE || var0 == PoiType.BEE_NEST, â˜ƒ, 20, PoiManager.Occupancy.ANY);
         return (List<BlockPos>)â˜ƒxx.map(PoiRecord::getPos)
            .filter(Bee.this::doesHiveHaveSpace)
            .sorted(Comparator.comparingDouble(var1x -> var1x.distSqr(â˜ƒ)))
            .collect(Collectors.toList());
      }
   }

   class BeeLookControl extends LookControl {
      BeeLookControl(Mob var2) {
         super(â˜ƒ);
      }

      @Override
      public void tick() {
         if (!Bee.this.isAngry()) {
            super.tick();
         }
      }

      @Override
      protected boolean resetXRotOnTick() {
         return !Bee.this.beePollinateGoal.isPollinating();
      }
   }

   class BeePollinateGoal extends Bee.BaseBeeGoal {
      private static final int MIN_POLLINATION_TICKS = 400;
      private static final int MIN_FIND_FLOWER_RETRY_COOLDOWN = 20;
      private static final int MAX_FIND_FLOWER_RETRY_COOLDOWN = 60;
      private final Predicate<BlockState> VALID_POLLINATION_BLOCKS = var0 -> {
         if (var0.is(BlockTags.FLOWERS)) {
            if (var0.is(Blocks.SUNFLOWER)) {
               return var0.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
            } else {
               return true;
            }
         } else {
            return false;
         }
      };
      private static final double ARRIVAL_THRESHOLD = 0.1;
      private static final int POSITION_CHANGE_CHANCE = 25;
      private static final float SPEED_MODIFIER = 0.35F;
      private static final float HOVER_HEIGHT_WITHIN_FLOWER = 0.6F;
      private static final float HOVER_POS_OFFSET = 0.33333334F;
      private int successfulPollinatingTicks;
      private int lastSoundPlayedTick;
      private boolean pollinating;
      private Vec3 hoverPos;
      private int pollinatingTicks;
      private static final int MAX_POLLINATING_TICKS = 600;

      BeePollinateGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canBeeUse() {
         if (Bee.this.remainingCooldownBeforeLocatingNewFlower > 0) {
            return false;
         } else if (Bee.this.hasNectar()) {
            return false;
         } else if (Bee.this.level.isRaining()) {
            return false;
         } else {
            Optional<BlockPos> â˜ƒ = this.findNearbyFlower();
            if (â˜ƒ.isPresent()) {
               Bee.this.savedFlowerPos = (BlockPos)â˜ƒ.get();
               Bee.this.navigation
                  .moveTo(
                     (double)Bee.this.savedFlowerPos.getX() + 0.5,
                     (double)Bee.this.savedFlowerPos.getY() + 0.5,
                     (double)Bee.this.savedFlowerPos.getZ() + 0.5,
                     1.2F
                  );
               return true;
            } else {
               Bee.this.remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(Bee.this.random, 20, 60);
               return false;
            }
         }
      }

      @Override
      public boolean canBeeContinueToUse() {
         if (!this.pollinating) {
            return false;
         } else if (!Bee.this.hasSavedFlowerPos()) {
            return false;
         } else if (Bee.this.level.isRaining()) {
            return false;
         } else if (this.hasPollinatedLongEnough()) {
            return Bee.this.random.nextFloat() < 0.2F;
         } else if (Bee.this.tickCount % 20 == 0 && !Bee.this.isFlowerValid(Bee.this.savedFlowerPos)) {
            Bee.this.savedFlowerPos = null;
            return false;
         } else {
            return true;
         }
      }

      private boolean hasPollinatedLongEnough() {
         return this.successfulPollinatingTicks > 400;
      }

      boolean isPollinating() {
         return this.pollinating;
      }

      void stopPollinating() {
         this.pollinating = false;
      }

      @Override
      public void start() {
         this.successfulPollinatingTicks = 0;
         this.pollinatingTicks = 0;
         this.lastSoundPlayedTick = 0;
         this.pollinating = true;
         Bee.this.resetTicksWithoutNectarSinceExitingHive();
      }

      @Override
      public void stop() {
         if (this.hasPollinatedLongEnough()) {
            Bee.this.setHasNectar(true);
         }

         this.pollinating = false;
         Bee.this.navigation.stop();
         Bee.this.remainingCooldownBeforeLocatingNewFlower = 200;
      }

      @Override
      public void tick() {
         ++this.pollinatingTicks;
         if (this.pollinatingTicks > 600) {
            Bee.this.savedFlowerPos = null;
         } else {
            Vec3 â˜ƒ = Vec3.atBottomCenterOf(Bee.this.savedFlowerPos).add(0.0, 0.6F, 0.0);
            if (â˜ƒ.distanceTo(Bee.this.position()) > 1.0) {
               this.hoverPos = â˜ƒ;
               this.setWantedPos();
            } else {
               if (this.hoverPos == null) {
                  this.hoverPos = â˜ƒ;
               }

               boolean â˜ƒ = Bee.this.position().distanceTo(this.hoverPos) <= 0.1;
               boolean â˜ƒx = true;
               if (!â˜ƒ && this.pollinatingTicks > 600) {
                  Bee.this.savedFlowerPos = null;
               } else {
                  if (â˜ƒ) {
                     boolean â˜ƒ = Bee.this.random.nextInt(25) == 0;
                     if (â˜ƒ) {
                        this.hoverPos = new Vec3(â˜ƒ.x() + (double)this.getOffset(), â˜ƒ.y(), â˜ƒ.z() + (double)this.getOffset());
                        Bee.this.navigation.stop();
                     } else {
                        â˜ƒx = false;
                     }

                     Bee.this.getLookControl().setLookAt(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
                  }

                  if (â˜ƒx) {
                     this.setWantedPos();
                  }

                  ++this.successfulPollinatingTicks;
                  if (Bee.this.random.nextFloat() < 0.05F && this.successfulPollinatingTicks > this.lastSoundPlayedTick + 60) {
                     this.lastSoundPlayedTick = this.successfulPollinatingTicks;
                     Bee.this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
                  }
               }
            }
         }
      }

      private void setWantedPos() {
         Bee.this.getMoveControl().setWantedPosition(this.hoverPos.x(), this.hoverPos.y(), this.hoverPos.z(), 0.35F);
      }

      private float getOffset() {
         return (Bee.this.random.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
      }

      private Optional<BlockPos> findNearbyFlower() {
         return this.findNearestBlock(this.VALID_POLLINATION_BLOCKS, 5.0);
      }

      private Optional<BlockPos> findNearestBlock(Predicate<BlockState> var1, double var2) {
         BlockPos â˜ƒ = Bee.this.blockPosition();
         BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxx = 0; (double)â˜ƒxx <= â˜ƒ; â˜ƒxx = â˜ƒxx > 0 ? -â˜ƒxx : 1 - â˜ƒxx) {
            for(int â˜ƒxxx = 0; (double)â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx <= â˜ƒxxx; â˜ƒxxxx = â˜ƒxxxx > 0 ? -â˜ƒxxxx : 1 - â˜ƒxxxx) {
                  for(int â˜ƒxxxxx = â˜ƒxxxx < â˜ƒxxx && â˜ƒxxxx > -â˜ƒxxx ? â˜ƒxxx : 0; â˜ƒxxxxx <= â˜ƒxxx; â˜ƒxxxxx = â˜ƒxxxxx > 0 ? -â˜ƒxxxxx : 1 - â˜ƒxxxxx) {
                     â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxxxx, â˜ƒxx - 1, â˜ƒxxxxx);
                     if (â˜ƒ.closerThan(â˜ƒx, â˜ƒ) && â˜ƒ.test(Bee.this.level.getBlockState(â˜ƒx))) {
                        return Optional.of(â˜ƒx);
                     }
                  }
               }
            }
         }

         return Optional.empty();
      }
   }

   class BeeWanderGoal extends Goal {
      private static final int WANDER_THRESHOLD = 22;

      BeeWanderGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         return Bee.this.navigation.isDone() && Bee.this.random.nextInt(10) == 0;
      }

      @Override
      public boolean canContinueToUse() {
         return Bee.this.navigation.isInProgress();
      }

      @Override
      public void start() {
         Vec3 â˜ƒ = this.findPos();
         if (â˜ƒ != null) {
            Bee.this.navigation.moveTo(Bee.this.navigation.createPath(new BlockPos(â˜ƒ), 1), 1.0);
         }
      }

      @Nullable
      private Vec3 findPos() {
         Vec3 â˜ƒ;
         if (Bee.this.isHiveValid() && !Bee.this.closerThan(Bee.this.hivePos, 22)) {
            Vec3 â˜ƒx = Vec3.atCenterOf(Bee.this.hivePos);
            â˜ƒ = â˜ƒx.subtract(Bee.this.position()).normalize();
         } else {
            â˜ƒ = Bee.this.getViewVector(0.0F);
         }

         int â˜ƒ = 8;
         Vec3 â˜ƒx = HoverRandomPos.getPos(Bee.this, 8, 7, â˜ƒ.x, â˜ƒ.z, (float) (Math.PI / 2), 3, 1);
         return â˜ƒx != null ? â˜ƒx : AirAndWaterRandomPos.getPos(Bee.this, 8, 4, -2, â˜ƒ.x, â˜ƒ.z, (float) (Math.PI / 2));
      }
   }
}
