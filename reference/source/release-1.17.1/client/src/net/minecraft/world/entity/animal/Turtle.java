package net.minecraft.world.entity.animal;

import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class Turtle extends Animal {
   private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BLOCK_POS);
   private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> LAYING_EGG = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<BlockPos> TRAVEL_POS = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BLOCK_POS);
   private static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
   public static final Ingredient FOOD_ITEMS = Ingredient.of(Blocks.SEAGRASS.asItem());
   int layEggCounter;
   public static final Predicate<LivingEntity> BABY_ON_LAND_SELECTOR = var0 -> var0.isBaby() && !var0.isInWater();

   public Turtle(EntityType<? extends Turtle> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
      this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.DOOR_OPEN, -1.0F);
      this.moveControl = new Turtle.TurtleMoveControl(this);
      this.maxUpStep = 1.0F;
   }

   public void setHomePos(BlockPos var1) {
      this.entityData.set(HOME_POS, â˜ƒ);
   }

   BlockPos getHomePos() {
      return this.entityData.get(HOME_POS);
   }

   void setTravelPos(BlockPos var1) {
      this.entityData.set(TRAVEL_POS, â˜ƒ);
   }

   BlockPos getTravelPos() {
      return this.entityData.get(TRAVEL_POS);
   }

   public boolean hasEgg() {
      return this.entityData.get(HAS_EGG);
   }

   void setHasEgg(boolean var1) {
      this.entityData.set(HAS_EGG, â˜ƒ);
   }

   public boolean isLayingEgg() {
      return this.entityData.get(LAYING_EGG);
   }

   void setLayingEgg(boolean var1) {
      this.layEggCounter = â˜ƒ ? 1 : 0;
      this.entityData.set(LAYING_EGG, â˜ƒ);
   }

   boolean isGoingHome() {
      return this.entityData.get(GOING_HOME);
   }

   void setGoingHome(boolean var1) {
      this.entityData.set(GOING_HOME, â˜ƒ);
   }

   boolean isTravelling() {
      return this.entityData.get(TRAVELLING);
   }

   void setTravelling(boolean var1) {
      this.entityData.set(TRAVELLING, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(HOME_POS, BlockPos.ZERO);
      this.entityData.define(HAS_EGG, false);
      this.entityData.define(TRAVEL_POS, BlockPos.ZERO);
      this.entityData.define(GOING_HOME, false);
      this.entityData.define(TRAVELLING, false);
      this.entityData.define(LAYING_EGG, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("HomePosX", this.getHomePos().getX());
      â˜ƒ.putInt("HomePosY", this.getHomePos().getY());
      â˜ƒ.putInt("HomePosZ", this.getHomePos().getZ());
      â˜ƒ.putBoolean("HasEgg", this.hasEgg());
      â˜ƒ.putInt("TravelPosX", this.getTravelPos().getX());
      â˜ƒ.putInt("TravelPosY", this.getTravelPos().getY());
      â˜ƒ.putInt("TravelPosZ", this.getTravelPos().getZ());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      int â˜ƒ = â˜ƒ.getInt("HomePosX");
      int â˜ƒx = â˜ƒ.getInt("HomePosY");
      int â˜ƒxx = â˜ƒ.getInt("HomePosZ");
      this.setHomePos(new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx));
      super.readAdditionalSaveData(â˜ƒ);
      this.setHasEgg(â˜ƒ.getBoolean("HasEgg"));
      int â˜ƒxxx = â˜ƒ.getInt("TravelPosX");
      int â˜ƒxxxx = â˜ƒ.getInt("TravelPosY");
      int â˜ƒxxxxx = â˜ƒ.getInt("TravelPosZ");
      this.setTravelPos(new BlockPos(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx));
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setHomePos(this.blockPosition());
      this.setTravelPos(BlockPos.ZERO);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean checkTurtleSpawnRules(EntityType<Turtle> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getY() < â˜ƒ.getSeaLevel() + 4 && TurtleEggBlock.onSand(â˜ƒ, â˜ƒ) && â˜ƒ.getRawBrightness(â˜ƒ, 0) > 8;
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new Turtle.TurtlePanicGoal(this, 1.2));
      this.goalSelector.addGoal(1, new Turtle.TurtleBreedGoal(this, 1.0));
      this.goalSelector.addGoal(1, new Turtle.TurtleLayEggGoal(this, 1.0));
      this.goalSelector.addGoal(2, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
      this.goalSelector.addGoal(3, new Turtle.TurtleGoToWaterGoal(this, 1.0));
      this.goalSelector.addGoal(4, new Turtle.TurtleGoHomeGoal(this, 1.0));
      this.goalSelector.addGoal(7, new Turtle.TurtleTravelGoal(this, 1.0));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(9, new Turtle.TurtleRandomStrollGoal(this, 1.0, 100));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   @Override
   public boolean isPushedByFluid() {
      return false;
   }

   @Override
   public boolean canBreatheUnderwater() {
      return true;
   }

   @Override
   public MobType getMobType() {
      return MobType.WATER;
   }

   @Override
   public int getAmbientSoundInterval() {
      return 200;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return !this.isInWater() && this.onGround && !this.isBaby() ? SoundEvents.TURTLE_AMBIENT_LAND : super.getAmbientSound();
   }

   @Override
   protected void playSwimSound(float var1) {
      super.playSwimSound(â˜ƒ * 1.5F);
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.TURTLE_SWIM;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isBaby() ? SoundEvents.TURTLE_HURT_BABY : SoundEvents.TURTLE_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return this.isBaby() ? SoundEvents.TURTLE_DEATH_BABY : SoundEvents.TURTLE_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      SoundEvent â˜ƒ = this.isBaby() ? SoundEvents.TURTLE_SHAMBLE_BABY : SoundEvents.TURTLE_SHAMBLE;
      this.playSound(â˜ƒ, 0.15F, 1.0F);
   }

   @Override
   public boolean canFallInLove() {
      return super.canFallInLove() && !this.hasEgg();
   }

   @Override
   protected float nextStep() {
      return this.moveDist + 0.15F;
   }

   @Override
   public float getScale() {
      return this.isBaby() ? 0.3F : 1.0F;
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new Turtle.TurtlePathNavigation(this, â˜ƒ);
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return EntityType.TURTLE.create(â˜ƒ);
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return â˜ƒ.is(Blocks.SEAGRASS.asItem());
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      if (!this.isGoingHome() && â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER)) {
         return 10.0F;
      } else {
         return TurtleEggBlock.onSand(â˜ƒ, â˜ƒ) ? 10.0F : â˜ƒ.getBrightness(â˜ƒ) - 0.5F;
      }
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (this.isAlive() && this.isLayingEgg() && this.layEggCounter >= 1 && this.layEggCounter % 5 == 0) {
         BlockPos â˜ƒ = this.blockPosition();
         if (TurtleEggBlock.onSand(this.level, â˜ƒ)) {
            this.level.levelEvent(2001, â˜ƒ, Block.getId(this.level.getBlockState(â˜ƒ.below())));
         }
      }
   }

   @Override
   protected void ageBoundaryReached() {
      super.ageBoundaryReached();
      if (!this.isBaby() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
         this.spawnAtLocation(Items.SCUTE, 1);
      }
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(0.1F, â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         if (this.getTarget() == null && (!this.isGoingHome() || !this.getHomePos().closerThan(this.position(), 20.0))) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(â˜ƒ);
      }
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return false;
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
      this.hurt(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
   }

   static class TurtleBreedGoal extends BreedGoal {
      private final Turtle turtle;

      TurtleBreedGoal(Turtle var1, double var2) {
         super(â˜ƒ, â˜ƒ);
         this.turtle = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return super.canUse() && !this.turtle.hasEgg();
      }

      @Override
      protected void breed() {
         ServerPlayer â˜ƒ = this.animal.getLoveCause();
         if (â˜ƒ == null && this.partner.getLoveCause() != null) {
            â˜ƒ = this.partner.getLoveCause();
         }

         if (â˜ƒ != null) {
            â˜ƒ.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(â˜ƒ, this.animal, this.partner, null);
         }

         this.turtle.setHasEgg(true);
         this.animal.resetLove();
         this.partner.resetLove();
         Random â˜ƒ = this.animal.getRandom();
         if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), â˜ƒ.nextInt(7) + 1));
         }
      }
   }

   static class TurtleGoHomeGoal extends Goal {
      private final Turtle turtle;
      private final double speedModifier;
      private boolean stuck;
      private int closeToHomeTryTicks;
      private static final int GIVE_UP_TICKS = 600;

      TurtleGoHomeGoal(Turtle var1, double var2) {
         this.turtle = â˜ƒ;
         this.speedModifier = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         if (this.turtle.isBaby()) {
            return false;
         } else if (this.turtle.hasEgg()) {
            return true;
         } else if (this.turtle.getRandom().nextInt(700) != 0) {
            return false;
         } else {
            return !this.turtle.getHomePos().closerThan(this.turtle.position(), 64.0);
         }
      }

      @Override
      public void start() {
         this.turtle.setGoingHome(true);
         this.stuck = false;
         this.closeToHomeTryTicks = 0;
      }

      @Override
      public void stop() {
         this.turtle.setGoingHome(false);
      }

      @Override
      public boolean canContinueToUse() {
         return !this.turtle.getHomePos().closerThan(this.turtle.position(), 7.0) && !this.stuck && this.closeToHomeTryTicks <= 600;
      }

      @Override
      public void tick() {
         BlockPos â˜ƒ = this.turtle.getHomePos();
         boolean â˜ƒx = â˜ƒ.closerThan(this.turtle.position(), 16.0);
         if (â˜ƒx) {
            ++this.closeToHomeTryTicks;
         }

         if (this.turtle.getNavigation().isDone()) {
            Vec3 â˜ƒ = Vec3.atBottomCenterOf(â˜ƒ);
            Vec3 â˜ƒx = DefaultRandomPos.getPosTowards(this.turtle, 16, 3, â˜ƒ, (float) (Math.PI / 10));
            if (â˜ƒx == null) {
               â˜ƒx = DefaultRandomPos.getPosTowards(this.turtle, 8, 7, â˜ƒ, (float) (Math.PI / 2));
            }

            if (â˜ƒx != null && !â˜ƒx && !this.turtle.level.getBlockState(new BlockPos(â˜ƒx)).is(Blocks.WATER)) {
               â˜ƒx = DefaultRandomPos.getPosTowards(this.turtle, 16, 5, â˜ƒ, (float) (Math.PI / 2));
            }

            if (â˜ƒx == null) {
               this.stuck = true;
               return;
            }

            this.turtle.getNavigation().moveTo(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z, this.speedModifier);
         }
      }
   }

   static class TurtleGoToWaterGoal extends MoveToBlockGoal {
      private static final int GIVE_UP_TICKS = 1200;
      private final Turtle turtle;

      TurtleGoToWaterGoal(Turtle var1, double var2) {
         super(â˜ƒ, â˜ƒ.isBaby() ? 2.0 : â˜ƒ, 24);
         this.turtle = â˜ƒ;
         this.verticalSearchStart = -1;
      }

      @Override
      public boolean canContinueToUse() {
         return !this.turtle.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.turtle.level, this.blockPos);
      }

      @Override
      public boolean canUse() {
         if (this.turtle.isBaby() && !this.turtle.isInWater()) {
            return super.canUse();
         } else {
            return !this.turtle.isGoingHome() && !this.turtle.isInWater() && !this.turtle.hasEgg() ? super.canUse() : false;
         }
      }

      @Override
      public boolean shouldRecalculatePath() {
         return this.tryTicks % 160 == 0;
      }

      @Override
      protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
         return â˜ƒ.getBlockState(â˜ƒ).is(Blocks.WATER);
      }
   }

   static class TurtleLayEggGoal extends MoveToBlockGoal {
      private final Turtle turtle;

      TurtleLayEggGoal(Turtle var1, double var2) {
         super(â˜ƒ, â˜ƒ, 16);
         this.turtle = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.turtle.hasEgg() && this.turtle.getHomePos().closerThan(this.turtle.position(), 9.0) ? super.canUse() : false;
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && this.turtle.hasEgg() && this.turtle.getHomePos().closerThan(this.turtle.position(), 9.0);
      }

      @Override
      public void tick() {
         super.tick();
         BlockPos â˜ƒ = this.turtle.blockPosition();
         if (!this.turtle.isInWater() && this.isReachedTarget()) {
            if (this.turtle.layEggCounter < 1) {
               this.turtle.setLayingEgg(true);
            } else if (this.turtle.layEggCounter > 200) {
               Level â˜ƒx = this.turtle.level;
               â˜ƒx.playSound(null, â˜ƒ, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + â˜ƒx.random.nextFloat() * 0.2F);
               â˜ƒx.setBlock(
                  this.blockPos.above(),
                  Blocks.TURTLE_EGG.defaultBlockState().setValue(TurtleEggBlock.EGGS, Integer.valueOf(this.turtle.random.nextInt(4) + 1)),
                  3
               );
               this.turtle.setHasEgg(false);
               this.turtle.setLayingEgg(false);
               this.turtle.setInLoveTime(600);
            }

            if (this.turtle.isLayingEgg()) {
               ++this.turtle.layEggCounter;
            }
         }
      }

      @Override
      protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
         return !â˜ƒ.isEmptyBlock(â˜ƒ.above()) ? false : TurtleEggBlock.isSand(â˜ƒ, â˜ƒ);
      }
   }

   static class TurtleMoveControl extends MoveControl {
      private final Turtle turtle;

      TurtleMoveControl(Turtle var1) {
         super(â˜ƒ);
         this.turtle = â˜ƒ;
      }

      private void updateSpeed() {
         if (this.turtle.isInWater()) {
            this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0, 0.005, 0.0));
            if (!this.turtle.getHomePos().closerThan(this.turtle.position(), 16.0)) {
               this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.08F));
            }

            if (this.turtle.isBaby()) {
               this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 3.0F, 0.06F));
            }
         } else if (this.turtle.onGround) {
            this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.06F));
         }
      }

      @Override
      public void tick() {
         this.updateSpeed();
         if (this.operation == MoveControl.Operation.MOVE_TO && !this.turtle.getNavigation().isDone()) {
            double â˜ƒ = this.wantedX - this.turtle.getX();
            double â˜ƒx = this.wantedY - this.turtle.getY();
            double â˜ƒxx = this.wantedZ - this.turtle.getZ();
            double â˜ƒxxx = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
            â˜ƒx /= â˜ƒxxx;
            float â˜ƒxxxx = (float)(Mth.atan2(â˜ƒxx, â˜ƒ) * 180.0F / (float)Math.PI) - 90.0F;
            this.turtle.setYRot(this.rotlerp(this.turtle.getYRot(), â˜ƒxxxx, 90.0F));
            this.turtle.yBodyRot = this.turtle.getYRot();
            float â˜ƒxxxxx = (float)(this.speedModifier * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.turtle.setSpeed(Mth.lerp(0.125F, this.turtle.getSpeed(), â˜ƒxxxxx));
            this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0, (double)this.turtle.getSpeed() * â˜ƒx * 0.1, 0.0));
         } else {
            this.turtle.setSpeed(0.0F);
         }
      }
   }

   static class TurtlePanicGoal extends PanicGoal {
      TurtlePanicGoal(Turtle var1, double var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean canUse() {
         if (this.mob.getLastHurtByMob() == null && !this.mob.isOnFire()) {
            return false;
         } else {
            BlockPos â˜ƒ = this.lookForWater(this.mob.level, this.mob, 7, 4);
            if (â˜ƒ != null) {
               this.posX = (double)â˜ƒ.getX();
               this.posY = (double)â˜ƒ.getY();
               this.posZ = (double)â˜ƒ.getZ();
               return true;
            } else {
               return this.findRandomPosition();
            }
         }
      }
   }

   static class TurtlePathNavigation extends WaterBoundPathNavigation {
      TurtlePathNavigation(Turtle var1, Level var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected boolean canUpdatePath() {
         return true;
      }

      @Override
      protected PathFinder createPathFinder(int var1) {
         this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
         this.nodeEvaluator.setCanOpenDoors(false);
         this.nodeEvaluator.setCanPassDoors(false);
         return new PathFinder(this.nodeEvaluator, â˜ƒ);
      }

      @Override
      public boolean isStableDestination(BlockPos var1) {
         if (this.mob instanceof Turtle â˜ƒ && â˜ƒ.isTravelling()) {
            return this.level.getBlockState(â˜ƒ).is(Blocks.WATER);
         }

         return !this.level.getBlockState(â˜ƒ.below()).isAir();
      }
   }

   static class TurtleRandomStrollGoal extends RandomStrollGoal {
      private final Turtle turtle;

      TurtleRandomStrollGoal(Turtle var1, double var2, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.turtle = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return !this.mob.isInWater() && !this.turtle.isGoingHome() && !this.turtle.hasEgg() ? super.canUse() : false;
      }
   }

   static class TurtleTravelGoal extends Goal {
      private final Turtle turtle;
      private final double speedModifier;
      private boolean stuck;

      TurtleTravelGoal(Turtle var1, double var2) {
         this.turtle = â˜ƒ;
         this.speedModifier = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return !this.turtle.isGoingHome() && !this.turtle.hasEgg() && this.turtle.isInWater();
      }

      @Override
      public void start() {
         int â˜ƒ = 512;
         int â˜ƒx = 4;
         Random â˜ƒxx = this.turtle.random;
         int â˜ƒxxx = â˜ƒxx.nextInt(1025) - 512;
         int â˜ƒxxxx = â˜ƒxx.nextInt(9) - 4;
         int â˜ƒxxxxx = â˜ƒxx.nextInt(1025) - 512;
         if ((double)â˜ƒxxxx + this.turtle.getY() > (double)(this.turtle.level.getSeaLevel() - 1)) {
            â˜ƒxxxx = 0;
         }

         BlockPos â˜ƒ = new BlockPos((double)â˜ƒxxx + this.turtle.getX(), (double)â˜ƒxxxx + this.turtle.getY(), (double)â˜ƒxxxxx + this.turtle.getZ());
         this.turtle.setTravelPos(â˜ƒ);
         this.turtle.setTravelling(true);
         this.stuck = false;
      }

      @Override
      public void tick() {
         if (this.turtle.getNavigation().isDone()) {
            Vec3 â˜ƒ = Vec3.atBottomCenterOf(this.turtle.getTravelPos());
            Vec3 â˜ƒx = DefaultRandomPos.getPosTowards(this.turtle, 16, 3, â˜ƒ, (float) (Math.PI / 10));
            if (â˜ƒx == null) {
               â˜ƒx = DefaultRandomPos.getPosTowards(this.turtle, 8, 7, â˜ƒ, (float) (Math.PI / 2));
            }

            if (â˜ƒx != null) {
               int â˜ƒ = Mth.floor(â˜ƒx.x);
               int â˜ƒx = Mth.floor(â˜ƒx.z);
               int â˜ƒxx = 34;
               if (!this.turtle.level.hasChunksAt(â˜ƒ - 34, â˜ƒx - 34, â˜ƒ + 34, â˜ƒx + 34)) {
                  â˜ƒx = null;
               }
            }

            if (â˜ƒx == null) {
               this.stuck = true;
               return;
            }

            this.turtle.getNavigation().moveTo(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z, this.speedModifier);
         }
      }

      @Override
      public boolean canContinueToUse() {
         return !this.turtle.getNavigation().isDone() && !this.stuck && !this.turtle.isGoingHome() && !this.turtle.isInLove() && !this.turtle.hasEgg();
      }

      @Override
      public void stop() {
         this.turtle.setTravelling(false);
         super.stop();
      }
   }
}
