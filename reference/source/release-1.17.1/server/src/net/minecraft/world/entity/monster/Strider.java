package net.minecraft.world.entity.monster;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class Strider extends Animal implements ItemSteerable, Saddleable {
   private static final float SUFFOCATE_STEERING_MODIFIER = 0.23F;
   private static final float SUFFOCATE_SPEED_MODIFIER = 0.66F;
   private static final float STEERING_MODIFIER = 0.55F;
   private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WARPED_FUNGUS);
   private static final Ingredient TEMPT_ITEMS = Ingredient.of(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK);
   private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_SUFFOCATING = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.BOOLEAN);
   private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);
   private TemptGoal temptGoal;
   private PanicGoal panicGoal;

   public Strider(EntityType<? extends Strider> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.blocksBuilding = true;
      this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
      this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
      this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
   }

   public static boolean checkStriderSpawnRules(EntityType<Strider> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      do {
         â˜ƒ.move(Direction.UP);
      } while(â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.LAVA));

      return â˜ƒ.getBlockState(â˜ƒ).isAir();
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_BOOST_TIME.equals(â˜ƒ) && this.level.isClientSide) {
         this.steering.onSynced();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_BOOST_TIME, 0);
      this.entityData.define(DATA_SUFFOCATING, false);
      this.entityData.define(DATA_SADDLE_ID, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      this.steering.addAdditionalSaveData(â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.steering.readAdditionalSaveData(â˜ƒ);
   }

   @Override
   public boolean isSaddled() {
      return this.steering.hasSaddle();
   }

   @Override
   public boolean isSaddleable() {
      return this.isAlive() && !this.isBaby();
   }

   @Override
   public void equipSaddle(@Nullable SoundSource var1) {
      this.steering.setSaddle(true);
      if (â˜ƒ != null) {
         this.level.playSound(null, this, SoundEvents.STRIDER_SADDLE, â˜ƒ, 0.5F, 1.0F);
      }
   }

   @Override
   protected void registerGoals() {
      this.panicGoal = new PanicGoal(this, 1.65);
      this.goalSelector.addGoal(1, this.panicGoal);
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
      this.temptGoal = new TemptGoal(this, 1.4, TEMPT_ITEMS, false);
      this.goalSelector.addGoal(3, this.temptGoal);
      this.goalSelector.addGoal(4, new Strider.StriderGoToLavaGoal(this, 1.5));
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0, 60));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Strider.class, 8.0F));
   }

   public void setSuffocating(boolean var1) {
      this.entityData.set(DATA_SUFFOCATING, â˜ƒ);
   }

   public boolean isSuffocating() {
      return this.getVehicle() instanceof Strider ? ((Strider)this.getVehicle()).isSuffocating() : this.entityData.get(DATA_SUFFOCATING);
   }

   @Override
   public boolean canStandOnFluid(Fluid var1) {
      return â˜ƒ.is(FluidTags.LAVA);
   }

   @Override
   public double getPassengersRidingOffset() {
      float â˜ƒ = Math.min(0.25F, this.animationSpeed);
      float â˜ƒx = this.animationPosition;
      return (double)this.getBbHeight() - 0.19 + (double)(0.12F * Mth.cos(â˜ƒx * 1.5F) * 2.0F * â˜ƒ);
   }

   @Override
   public boolean canBeControlledByRider() {
      Entity â˜ƒ = this.getControllingPassenger();
      if (!(â˜ƒ instanceof Player)) {
         return false;
      } else {
         Player â˜ƒ = (Player)â˜ƒ;
         return â˜ƒ.getMainHandItem().is(Items.WARPED_FUNGUS_ON_A_STICK) || â˜ƒ.getOffhandItem().is(Items.WARPED_FUNGUS_ON_A_STICK);
      }
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      return â˜ƒ.isUnobstructed(this);
   }

   @Nullable
   @Override
   public Entity getControllingPassenger() {
      return this.getFirstPassenger();
   }

   @Override
   public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
      Vec3[] â˜ƒ = new Vec3[]{
         getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), â˜ƒ.getYRot()),
         getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), â˜ƒ.getYRot() - 22.5F),
         getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), â˜ƒ.getYRot() + 22.5F),
         getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), â˜ƒ.getYRot() - 45.0F),
         getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), â˜ƒ.getYRot() + 45.0F)
      };
      Set<BlockPos> â˜ƒx = Sets.<BlockPos>newLinkedHashSet();
      double â˜ƒxx = this.getBoundingBox().maxY;
      double â˜ƒxxx = this.getBoundingBox().minY - 0.5;
      BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();

      for(Vec3 â˜ƒxxxxx : â˜ƒ) {
         â˜ƒxxxx.set(this.getX() + â˜ƒxxxxx.x, â˜ƒxx, this.getZ() + â˜ƒxxxxx.z);

         for(double â˜ƒxxxxxx = â˜ƒxx; â˜ƒxxxxxx > â˜ƒxxx; --â˜ƒxxxxxx) {
            â˜ƒx.add(â˜ƒxxxx.immutable());
            â˜ƒxxxx.move(Direction.DOWN);
         }
      }

      for(BlockPos â˜ƒxxxxx : â˜ƒx) {
         if (!this.level.getFluidState(â˜ƒxxxxx).is(FluidTags.LAVA)) {
            double â˜ƒxxxxxx = this.level.getBlockFloorHeight(â˜ƒxxxxx);
            if (DismountHelper.isBlockFloorValid(â˜ƒxxxxxx)) {
               Vec3 â˜ƒxxxxxxx = Vec3.upFromBottomCenterOf(â˜ƒxxxxx, â˜ƒxxxxxx);

               for(Pose â˜ƒxxxxxxxx : â˜ƒ.getDismountPoses()) {
                  AABB â˜ƒxxxxxxxxx = â˜ƒ.getLocalBoundsForPose(â˜ƒxxxxxxxx);
                  if (DismountHelper.canDismountTo(this.level, â˜ƒ, â˜ƒxxxxxxxxx.move(â˜ƒxxxxxxx))) {
                     â˜ƒ.setPose(â˜ƒxxxxxxxx);
                     return â˜ƒxxxxxxx;
                  }
               }
            }
         }
      }

      return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
   }

   @Override
   public void travel(Vec3 var1) {
      this.setSpeed(this.getMoveSpeed());
      this.travel(this, this.steering, â˜ƒ);
   }

   public float getMoveSpeed() {
      return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isSuffocating() ? 0.66F : 1.0F);
   }

   @Override
   public float getSteeringSpeed() {
      return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isSuffocating() ? 0.23F : 0.55F);
   }

   @Override
   public void travelWithInput(Vec3 var1) {
      super.travel(â˜ƒ);
   }

   @Override
   protected float nextStep() {
      return this.moveDist + 0.6F;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(this.isInLava() ? SoundEvents.STRIDER_STEP_LAVA : SoundEvents.STRIDER_STEP, 1.0F, 1.0F);
   }

   @Override
   public boolean boost() {
      return this.steering.boost(this.getRandom());
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
      this.checkInsideBlocks();
      if (this.isInLava()) {
         this.fallDistance = 0.0F;
      } else {
         super.checkFallDamage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void tick() {
      if (this.isBeingTempted() && this.random.nextInt(140) == 0) {
         this.playSound(SoundEvents.STRIDER_HAPPY, 1.0F, this.getVoicePitch());
      } else if (this.isPanicking() && this.random.nextInt(60) == 0) {
         this.playSound(SoundEvents.STRIDER_RETREAT, 1.0F, this.getVoicePitch());
      }

      BlockState â˜ƒ = this.level.getBlockState(this.blockPosition());
      BlockState â˜ƒx = this.getBlockStateOn();
      boolean â˜ƒxx = â˜ƒ.is(BlockTags.STRIDER_WARM_BLOCKS) || â˜ƒx.is(BlockTags.STRIDER_WARM_BLOCKS) || this.getFluidHeight(FluidTags.LAVA) > 0.0;
      this.setSuffocating(!â˜ƒxx);
      super.tick();
      this.floatStrider();
      this.checkInsideBlocks();
   }

   private boolean isPanicking() {
      return this.panicGoal != null && this.panicGoal.isRunning();
   }

   private boolean isBeingTempted() {
      return this.temptGoal != null && this.temptGoal.isRunning();
   }

   @Override
   protected boolean shouldPassengersInheritMalus() {
      return true;
   }

   private void floatStrider() {
      if (this.isInLava()) {
         CollisionContext â˜ƒ = CollisionContext.of(this);
         if (â˜ƒ.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)) {
            this.onGround = true;
         } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5).add(0.0, 0.05, 0.0));
         }
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.175F).add(Attributes.FOLLOW_RANGE, 16.0);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return !this.isPanicking() && !this.isBeingTempted() ? SoundEvents.STRIDER_AMBIENT : null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.STRIDER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.STRIDER_DEATH;
   }

   @Override
   protected boolean canAddPassenger(Entity var1) {
      return !this.isVehicle() && !this.isEyeInFluid(FluidTags.LAVA);
   }

   @Override
   public boolean isSensitiveToWater() {
      return true;
   }

   @Override
   public boolean isOnFire() {
      return false;
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new Strider.StriderPathNavigation(this, â˜ƒ);
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      if (â˜ƒ.getBlockState(â˜ƒ).getFluidState().is(FluidTags.LAVA)) {
         return 10.0F;
      } else {
         return this.isInLava() ? Float.NEGATIVE_INFINITY : 0.0F;
      }
   }

   public Strider getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return EntityType.STRIDER.create(â˜ƒ);
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return FOOD_ITEMS.test(â˜ƒ);
   }

   @Override
   protected void dropEquipment() {
      super.dropEquipment();
      if (this.isSaddled()) {
         this.spawnAtLocation(Items.SADDLE);
      }
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      boolean â˜ƒ = this.isFood(â˜ƒ.getItemInHand(â˜ƒ));
      if (!â˜ƒ && this.isSaddled() && !this.isVehicle() && !â˜ƒ.isSecondaryUseActive()) {
         if (!this.level.isClientSide) {
            â˜ƒ.startRiding(this);
         }

         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         InteractionResult â˜ƒ = super.mobInteract(â˜ƒ, â˜ƒ);
         if (!â˜ƒ.consumesAction()) {
            ItemStack â˜ƒx = â˜ƒ.getItemInHand(â˜ƒ);
            return â˜ƒx.is(Items.SADDLE) ? â˜ƒx.interactLivingEntity(â˜ƒ, this, â˜ƒ) : InteractionResult.PASS;
         } else {
            if (â˜ƒ && !this.isSilent()) {
               this.level
                  .playSound(
                     null,
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     SoundEvents.STRIDER_EAT,
                     this.getSoundSource(),
                     1.0F,
                     1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
                  );
            }

            return â˜ƒ;
         }
      }
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (this.isBaby()) {
         return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         Object var7;
         if (this.random.nextInt(30) == 0) {
            Mob â˜ƒ = EntityType.ZOMBIFIED_PIGLIN.create(â˜ƒ.getLevel());
            var7 = this.spawnJockey(â˜ƒ, â˜ƒ, â˜ƒ, new Zombie.ZombieGroupData(Zombie.getSpawnAsBabyOdds(this.random), false));
            â˜ƒ.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WARPED_FUNGUS_ON_A_STICK));
            this.equipSaddle(null);
         } else if (this.random.nextInt(10) == 0) {
            AgeableMob â˜ƒ = EntityType.STRIDER.create(â˜ƒ.getLevel());
            â˜ƒ.setAge(-24000);
            var7 = this.spawnJockey(â˜ƒ, â˜ƒ, â˜ƒ, null);
         } else {
            var7 = new AgeableMob.AgeableMobGroupData(0.5F);
         }

         return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, (SpawnGroupData)var7, â˜ƒ);
      }
   }

   private SpawnGroupData spawnJockey(ServerLevelAccessor var1, DifficultyInstance var2, Mob var3, @Nullable SpawnGroupData var4) {
      â˜ƒ.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
      â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ, MobSpawnType.JOCKEY, â˜ƒ, null);
      â˜ƒ.startRiding(this, true);
      return new AgeableMob.AgeableMobGroupData(0.0F);
   }

   static class StriderGoToLavaGoal extends MoveToBlockGoal {
      private final Strider strider;

      StriderGoToLavaGoal(Strider var1, double var2) {
         super(â˜ƒ, â˜ƒ, 8, 2);
         this.strider = â˜ƒ;
      }

      @Override
      public BlockPos getMoveToTarget() {
         return this.blockPos;
      }

      @Override
      public boolean canContinueToUse() {
         return !this.strider.isInLava() && this.isValidTarget(this.strider.level, this.blockPos);
      }

      @Override
      public boolean canUse() {
         return !this.strider.isInLava() && super.canUse();
      }

      @Override
      public boolean shouldRecalculatePath() {
         return this.tryTicks % 20 == 0;
      }

      @Override
      protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
         return â˜ƒ.getBlockState(â˜ƒ).is(Blocks.LAVA) && â˜ƒ.getBlockState(â˜ƒ.above()).isPathfindable(â˜ƒ, â˜ƒ, PathComputationType.LAND);
      }
   }

   static class StriderPathNavigation extends GroundPathNavigation {
      StriderPathNavigation(Strider var1, Level var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected PathFinder createPathFinder(int var1) {
         this.nodeEvaluator = new WalkNodeEvaluator();
         return new PathFinder(this.nodeEvaluator, â˜ƒ);
      }

      @Override
      protected boolean hasValidPathType(BlockPathTypes var1) {
         return â˜ƒ != BlockPathTypes.LAVA && â˜ƒ != BlockPathTypes.DAMAGE_FIRE && â˜ƒ != BlockPathTypes.DANGER_FIRE ? super.hasValidPathType(â˜ƒ) : true;
      }

      @Override
      public boolean isStableDestination(BlockPos var1) {
         return this.level.getBlockState(â˜ƒ).is(Blocks.LAVA) || super.isStableDestination(â˜ƒ);
      }
   }
}
