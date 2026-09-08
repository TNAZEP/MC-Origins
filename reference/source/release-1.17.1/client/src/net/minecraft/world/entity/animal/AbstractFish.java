package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFish extends WaterAnimal implements Bucketable {
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(AbstractFish.class, EntityDataSerializers.BOOLEAN);

   public AbstractFish(EntityType<? extends AbstractFish> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.moveControl = new AbstractFish.FishMoveControl(this);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.65F;
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0);
   }

   @Override
   public boolean requiresCustomPersistence() {
      return super.requiresCustomPersistence() || this.fromBucket();
   }

   public static boolean checkFishSpawnRules(EntityType<? extends AbstractFish> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getBlockState(â˜ƒ).is(Blocks.WATER) && â˜ƒ.getBlockState(â˜ƒ.above()).is(Blocks.WATER);
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return !this.fromBucket() && !this.hasCustomName();
   }

   @Override
   public int getMaxSpawnClusterSize() {
      return 8;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(FROM_BUCKET, false);
   }

   @Override
   public boolean fromBucket() {
      return this.entityData.get(FROM_BUCKET);
   }

   @Override
   public void setFromBucket(boolean var1) {
      this.entityData.set(FROM_BUCKET, â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("FromBucket", this.fromBucket());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setFromBucket(â˜ƒ.getBoolean("FromBucket"));
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(2, new AvoidEntityGoal(this, Player.class, 8.0F, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
      this.goalSelector.addGoal(4, new AbstractFish.FishSwimGoal(this));
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new WaterBoundPathNavigation(this, â˜ƒ);
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(0.01F, â˜ƒ);
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
   public void aiStep() {
      if (!this.isInWater() && this.onGround && this.verticalCollision) {
         this.setDeltaMovement(
            this.getDeltaMovement()
               .add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F))
         );
         this.onGround = false;
         this.hasImpulse = true;
         this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
      }

      super.aiStep();
   }

   @Override
   protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
      return (InteractionResult)Bucketable.bucketMobPickup(â˜ƒ, â˜ƒ, this).orElse(super.mobInteract(â˜ƒ, â˜ƒ));
   }

   @Override
   public void saveToBucketTag(ItemStack var1) {
      Bucketable.saveDefaultDataToBucketTag(this, â˜ƒ);
   }

   @Override
   public void loadFromBucketTag(CompoundTag var1) {
      Bucketable.loadDefaultDataFromBucketTag(this, â˜ƒ);
   }

   @Override
   public SoundEvent getPickupSound() {
      return SoundEvents.BUCKET_FILL_FISH;
   }

   protected boolean canRandomSwim() {
      return true;
   }

   protected abstract SoundEvent getFlopSound();

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.FISH_SWIM;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
   }

   static class FishMoveControl extends MoveControl {
      private final AbstractFish fish;

      FishMoveControl(AbstractFish var1) {
         super(â˜ƒ);
         this.fish = â˜ƒ;
      }

      @Override
      public void tick() {
         if (this.fish.isEyeInFluid(FluidTags.WATER)) {
            this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, 0.005, 0.0));
         }

         if (this.operation == MoveControl.Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
            float â˜ƒ = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), â˜ƒ));
            double â˜ƒx = this.wantedX - this.fish.getX();
            double â˜ƒxx = this.wantedY - this.fish.getY();
            double â˜ƒxxx = this.wantedZ - this.fish.getZ();
            if (â˜ƒxx != 0.0) {
               double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
               this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0, (double)this.fish.getSpeed() * (â˜ƒxx / â˜ƒxxxx) * 0.1, 0.0));
            }

            if (â˜ƒx != 0.0 || â˜ƒxxx != 0.0) {
               float â˜ƒ = (float)(Mth.atan2(â˜ƒxxx, â˜ƒx) * 180.0F / (float)Math.PI) - 90.0F;
               this.fish.setYRot(this.rotlerp(this.fish.getYRot(), â˜ƒ, 90.0F));
               this.fish.yBodyRot = this.fish.getYRot();
            }
         } else {
            this.fish.setSpeed(0.0F);
         }
      }
   }

   static class FishSwimGoal extends RandomSwimmingGoal {
      private final AbstractFish fish;

      public FishSwimGoal(AbstractFish var1) {
         super(â˜ƒ, 1.0, 40);
         this.fish = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return this.fish.canRandomSwim() && super.canUse();
      }
   }
}
