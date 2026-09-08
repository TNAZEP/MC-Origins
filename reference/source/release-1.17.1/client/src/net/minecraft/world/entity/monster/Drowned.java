package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Drowned extends Zombie implements RangedAttackMob {
   public static final float NAUTILUS_SHELL_CHANCE = 0.03F;
   boolean searchingForLand;
   protected final WaterBoundPathNavigation waterNavigation;
   protected final GroundPathNavigation groundNavigation;

   public Drowned(EntityType<? extends Drowned> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.maxUpStep = 1.0F;
      this.moveControl = new Drowned.DrownedMoveControl(this);
      this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
      this.waterNavigation = new WaterBoundPathNavigation(this, â˜ƒ);
      this.groundNavigation = new GroundPathNavigation(this, â˜ƒ);
   }

   @Override
   protected void addBehaviourGoals() {
      this.goalSelector.addGoal(1, new Drowned.DrownedGoToWaterGoal(this, 1.0));
      this.goalSelector.addGoal(2, new Drowned.DrownedTridentAttackGoal(this, 1.0, 40, 10.0F));
      this.goalSelector.addGoal(2, new Drowned.DrownedAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new Drowned.DrownedGoToBeachGoal(this, 1.0));
      this.goalSelector.addGoal(6, new Drowned.DrownedSwimUpGoal(this, 1.0, this.level.getSeaLevel()));
      this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Drowned.class).setAlertOthers(ZombifiedPiglin.class));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::okTarget));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Axolotl.class, true, false));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty() && this.random.nextFloat() < 0.03F) {
         this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
         this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 2.0F;
      }

      return â˜ƒ;
   }

   public static boolean checkDrownedSpawnRules(EntityType<Drowned> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      Optional<ResourceKey<Biome>> â˜ƒ = â˜ƒ.getBiomeName(â˜ƒ);
      boolean â˜ƒx = â˜ƒ.getDifficulty() != Difficulty.PEACEFUL
         && isDarkEnoughToSpawn(â˜ƒ, â˜ƒ, â˜ƒ)
         && (â˜ƒ == MobSpawnType.SPAWNER || â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER));
      if (!Objects.equals(â˜ƒ, Optional.of(Biomes.RIVER)) && !Objects.equals(â˜ƒ, Optional.of(Biomes.FROZEN_RIVER))) {
         return â˜ƒ.nextInt(40) == 0 && isDeepEnoughToSpawn(â˜ƒ, â˜ƒ) && â˜ƒx;
      } else {
         return â˜ƒ.nextInt(15) == 0 && â˜ƒx;
      }
   }

   private static boolean isDeepEnoughToSpawn(LevelAccessor var0, BlockPos var1) {
      return â˜ƒ.getY() < â˜ƒ.getSeaLevel() - 5;
   }

   @Override
   protected boolean supportsBreakDoorGoal() {
      return false;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isInWater() ? SoundEvents.DROWNED_AMBIENT_WATER : SoundEvents.DROWNED_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return this.isInWater() ? SoundEvents.DROWNED_HURT_WATER : SoundEvents.DROWNED_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isInWater() ? SoundEvents.DROWNED_DEATH_WATER : SoundEvents.DROWNED_DEATH;
   }

   @Override
   protected SoundEvent getStepSound() {
      return SoundEvents.DROWNED_STEP;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.DROWNED_SWIM;
   }

   @Override
   protected ItemStack getSkull() {
      return ItemStack.EMPTY;
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      if ((double)this.random.nextFloat() > 0.9) {
         int â˜ƒ = this.random.nextInt(16);
         if (â˜ƒ < 10) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
         } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
         }
      }
   }

   @Override
   protected boolean canReplaceCurrentItem(ItemStack var1, ItemStack var2) {
      if (â˜ƒ.is(Items.NAUTILUS_SHELL)) {
         return false;
      } else if (â˜ƒ.is(Items.TRIDENT)) {
         if (â˜ƒ.is(Items.TRIDENT)) {
            return â˜ƒ.getDamageValue() < â˜ƒ.getDamageValue();
         } else {
            return false;
         }
      } else {
         return â˜ƒ.is(Items.TRIDENT) ? true : super.canReplaceCurrentItem(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected boolean convertsInWater() {
      return false;
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      return â˜ƒ.isUnobstructed(this);
   }

   public boolean okTarget(@Nullable LivingEntity var1) {
      if (â˜ƒ != null) {
         return !this.level.isDay() || â˜ƒ.isInWater();
      } else {
         return false;
      }
   }

   @Override
   public boolean isPushedByFluid() {
      return !this.isSwimming();
   }

   boolean wantsToSwim() {
      if (this.searchingForLand) {
         return true;
      } else {
         LivingEntity â˜ƒ = this.getTarget();
         return â˜ƒ != null && â˜ƒ.isInWater();
      }
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
         this.moveRelative(0.01F, â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
      } else {
         super.travel(â˜ƒ);
      }
   }

   @Override
   public void updateSwimming() {
      if (!this.level.isClientSide) {
         if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
            this.navigation = this.waterNavigation;
            this.setSwimming(true);
         } else {
            this.navigation = this.groundNavigation;
            this.setSwimming(false);
         }
      }
   }

   protected boolean closeToNextPos() {
      Path â˜ƒ = this.getNavigation().getPath();
      if (â˜ƒ != null) {
         BlockPos â˜ƒx = â˜ƒ.getTarget();
         if (â˜ƒx != null) {
            double â˜ƒxx = this.distanceToSqr((double)â˜ƒx.getX(), (double)â˜ƒx.getY(), (double)â˜ƒx.getZ());
            if (â˜ƒxx < 4.0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      ThrownTrident â˜ƒ = new ThrownTrident(this.level, this, new ItemStack(Items.TRIDENT));
      double â˜ƒx = â˜ƒ.getX() - this.getX();
      double â˜ƒxx = â˜ƒ.getY(0.3333333333333333) - â˜ƒ.getY();
      double â˜ƒxxx = â˜ƒ.getZ() - this.getZ();
      double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxxx * â˜ƒxxx);
      â˜ƒ.shoot(â˜ƒx, â˜ƒxx + â˜ƒxxxx * 0.2F, â˜ƒxxx, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
      this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.level.addFreshEntity(â˜ƒ);
   }

   public void setSearchingForLand(boolean var1) {
      this.searchingForLand = â˜ƒ;
   }

   static class DrownedAttackGoal extends ZombieAttackGoal {
      private final Drowned drowned;

      public DrownedAttackGoal(Drowned var1, double var2, boolean var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.drowned = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.drowned.okTarget(this.drowned.getTarget());
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && this.drowned.okTarget(this.drowned.getTarget());
      }
   }

   static class DrownedGoToBeachGoal extends MoveToBlockGoal {
      private final Drowned drowned;

      public DrownedGoToBeachGoal(Drowned var1, double var2) {
         super(â˜ƒ, â˜ƒ, 8, 2);
         this.drowned = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return super.canUse()
            && !this.drowned.level.isDay()
            && this.drowned.isInWater()
            && this.drowned.getY() >= (double)(this.drowned.level.getSeaLevel() - 3);
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse();
      }

      @Override
      protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
         BlockPos â˜ƒ = â˜ƒ.above();
         return â˜ƒ.isEmptyBlock(â˜ƒ) && â˜ƒ.isEmptyBlock(â˜ƒ.above()) ? â˜ƒ.getBlockState(â˜ƒ).entityCanStandOn(â˜ƒ, â˜ƒ, this.drowned) : false;
      }

      @Override
      public void start() {
         this.drowned.setSearchingForLand(false);
         this.drowned.navigation = this.drowned.groundNavigation;
         super.start();
      }

      @Override
      public void stop() {
         super.stop();
      }
   }

   static class DrownedGoToWaterGoal extends Goal {
      private final PathfinderMob mob;
      private double wantedX;
      private double wantedY;
      private double wantedZ;
      private final double speedModifier;
      private final Level level;

      public DrownedGoToWaterGoal(PathfinderMob var1, double var2) {
         this.mob = â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.level = â˜ƒ.level;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         if (!this.level.isDay()) {
            return false;
         } else if (this.mob.isInWater()) {
            return false;
         } else {
            Vec3 â˜ƒ = this.getWaterPos();
            if (â˜ƒ == null) {
               return false;
            } else {
               this.wantedX = â˜ƒ.x;
               this.wantedY = â˜ƒ.y;
               this.wantedZ = â˜ƒ.z;
               return true;
            }
         }
      }

      @Override
      public boolean canContinueToUse() {
         return !this.mob.getNavigation().isDone();
      }

      @Override
      public void start() {
         this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
      }

      @Nullable
      private Vec3 getWaterPos() {
         Random â˜ƒ = this.mob.getRandom();
         BlockPos â˜ƒx = this.mob.blockPosition();

         for(int â˜ƒxx = 0; â˜ƒxx < 10; ++â˜ƒxx) {
            BlockPos â˜ƒxxx = â˜ƒx.offset(â˜ƒ.nextInt(20) - 10, 2 - â˜ƒ.nextInt(8), â˜ƒ.nextInt(20) - 10);
            if (this.level.getBlockState(â˜ƒxxx).is(Blocks.WATER)) {
               return Vec3.atBottomCenterOf(â˜ƒxxx);
            }
         }

         return null;
      }
   }

   static class DrownedMoveControl extends MoveControl {
      private final Drowned drowned;

      public DrownedMoveControl(Drowned var1) {
         super(â˜ƒ);
         this.drowned = â˜ƒ;
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = this.drowned.getTarget();
         if (this.drowned.wantsToSwim() && this.drowned.isInWater()) {
            if (â˜ƒ != null && â˜ƒ.getY() > this.drowned.getY() || this.drowned.searchingForLand) {
               this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, 0.002, 0.0));
            }

            if (this.operation != MoveControl.Operation.MOVE_TO || this.drowned.getNavigation().isDone()) {
               this.drowned.setSpeed(0.0F);
               return;
            }

            double â˜ƒx = this.wantedX - this.drowned.getX();
            double â˜ƒxx = this.wantedY - this.drowned.getY();
            double â˜ƒxxx = this.wantedZ - this.drowned.getZ();
            double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
            â˜ƒxx /= â˜ƒxxxx;
            float â˜ƒxxxxx = (float)(Mth.atan2(â˜ƒxxx, â˜ƒx) * 180.0F / (float)Math.PI) - 90.0F;
            this.drowned.setYRot(this.rotlerp(this.drowned.getYRot(), â˜ƒxxxxx, 90.0F));
            this.drowned.yBodyRot = this.drowned.getYRot();
            float â˜ƒxxxxxx = (float)(this.speedModifier * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float â˜ƒxxxxxxx = Mth.lerp(0.125F, this.drowned.getSpeed(), â˜ƒxxxxxx);
            this.drowned.setSpeed(â˜ƒxxxxxxx);
            this.drowned
               .setDeltaMovement(
                  this.drowned.getDeltaMovement().add((double)â˜ƒxxxxxxx * â˜ƒx * 0.005, (double)â˜ƒxxxxxxx * â˜ƒxx * 0.1, (double)â˜ƒxxxxxxx * â˜ƒxxx * 0.005)
               );
         } else {
            if (!this.drowned.onGround) {
               this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, -0.008, 0.0));
            }

            super.tick();
         }
      }
   }

   static class DrownedSwimUpGoal extends Goal {
      private final Drowned drowned;
      private final double speedModifier;
      private final int seaLevel;
      private boolean stuck;

      public DrownedSwimUpGoal(Drowned var1, double var2, int var4) {
         this.drowned = â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.seaLevel = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return !this.drowned.level.isDay() && this.drowned.isInWater() && this.drowned.getY() < (double)(this.seaLevel - 2);
      }

      @Override
      public boolean canContinueToUse() {
         return this.canUse() && !this.stuck;
      }

      @Override
      public void tick() {
         if (this.drowned.getY() < (double)(this.seaLevel - 1) && (this.drowned.getNavigation().isDone() || this.drowned.closeToNextPos())) {
            Vec3 â˜ƒ = DefaultRandomPos.getPosTowards(
               this.drowned, 4, 8, new Vec3(this.drowned.getX(), (double)(this.seaLevel - 1), this.drowned.getZ()), (float) (Math.PI / 2)
            );
            if (â˜ƒ == null) {
               this.stuck = true;
               return;
            }

            this.drowned.getNavigation().moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, this.speedModifier);
         }
      }

      @Override
      public void start() {
         this.drowned.setSearchingForLand(true);
         this.stuck = false;
      }

      @Override
      public void stop() {
         this.drowned.setSearchingForLand(false);
      }
   }

   static class DrownedTridentAttackGoal extends RangedAttackGoal {
      private final Drowned drowned;

      public DrownedTridentAttackGoal(RangedAttackMob var1, double var2, int var4, float var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.drowned = (Drowned)â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.drowned.getMainHandItem().is(Items.TRIDENT);
      }

      @Override
      public void start() {
         super.start();
         this.drowned.setAggressive(true);
         this.drowned.startUsingItem(InteractionHand.MAIN_HAND);
      }

      @Override
      public void stop() {
         super.stop();
         this.drowned.stopUsingItem();
         this.drowned.setAggressive(false);
      }
   }
}
