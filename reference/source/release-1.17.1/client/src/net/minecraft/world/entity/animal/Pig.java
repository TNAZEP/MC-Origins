package net.minecraft.world.entity.animal;

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
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Pig extends Animal implements ItemSteerable, Saddleable {
   private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.INT);
   private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.CARROT, Items.POTATO, Items.BEETROOT);
   private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);

   public Pig(EntityType<? extends Pig> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(Items.CARROT_ON_A_STICK), false));
      this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, FOOD_ITEMS, false));
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   @Nullable
   @Override
   public Entity getControllingPassenger() {
      return this.getFirstPassenger();
   }

   @Override
   public boolean canBeControlledByRider() {
      Entity â˜ƒ = this.getControllingPassenger();
      if (!(â˜ƒ instanceof Player)) {
         return false;
      } else {
         Player â˜ƒ = (Player)â˜ƒ;
         return â˜ƒ.getMainHandItem().is(Items.CARROT_ON_A_STICK) || â˜ƒ.getOffhandItem().is(Items.CARROT_ON_A_STICK);
      }
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
      this.entityData.define(DATA_SADDLE_ID, false);
      this.entityData.define(DATA_BOOST_TIME, 0);
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
   protected SoundEvent getAmbientSound() {
      return SoundEvents.PIG_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.PIG_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PIG_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
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
            return â˜ƒ;
         }
      }
   }

   @Override
   public boolean isSaddleable() {
      return this.isAlive() && !this.isBaby();
   }

   @Override
   protected void dropEquipment() {
      super.dropEquipment();
      if (this.isSaddled()) {
         this.spawnAtLocation(Items.SADDLE);
      }
   }

   @Override
   public boolean isSaddled() {
      return this.steering.hasSaddle();
   }

   @Override
   public void equipSaddle(@Nullable SoundSource var1) {
      this.steering.setSaddle(true);
      if (â˜ƒ != null) {
         this.level.playSound(null, this, SoundEvents.PIG_SADDLE, â˜ƒ, 0.5F, 1.0F);
      }
   }

   @Override
   public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
      Direction â˜ƒ = this.getMotionDirection();
      if (â˜ƒ.getAxis() == Direction.Axis.Y) {
         return super.getDismountLocationForPassenger(â˜ƒ);
      } else {
         int[][] â˜ƒ = DismountHelper.offsetsForDirection(â˜ƒ);
         BlockPos â˜ƒx = this.blockPosition();
         BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

         for(Pose â˜ƒxxx : â˜ƒ.getDismountPoses()) {
            AABB â˜ƒxxxx = â˜ƒ.getLocalBoundsForPose(â˜ƒxxx);

            for(int[] â˜ƒxxxxx : â˜ƒ) {
               â˜ƒxx.set(â˜ƒx.getX() + â˜ƒxxxxx[0], â˜ƒx.getY(), â˜ƒx.getZ() + â˜ƒxxxxx[1]);
               double â˜ƒxxxxxx = this.level.getBlockFloorHeight(â˜ƒxx);
               if (DismountHelper.isBlockFloorValid(â˜ƒxxxxxx)) {
                  Vec3 â˜ƒxxxxxxx = Vec3.upFromBottomCenterOf(â˜ƒxx, â˜ƒxxxxxx);
                  if (DismountHelper.canDismountTo(this.level, â˜ƒ, â˜ƒxxxx.move(â˜ƒxxxxxxx))) {
                     â˜ƒ.setPose(â˜ƒxxx);
                     return â˜ƒxxxxxxx;
                  }
               }
            }
         }

         return super.getDismountLocationForPassenger(â˜ƒ);
      }
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
      if (â˜ƒ.getDifficulty() != Difficulty.PEACEFUL) {
         ZombifiedPiglin â˜ƒ = EntityType.ZOMBIFIED_PIGLIN.create(â˜ƒ);
         â˜ƒ.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
         â˜ƒ.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
         â˜ƒ.setNoAi(this.isNoAi());
         â˜ƒ.setBaby(this.isBaby());
         if (this.hasCustomName()) {
            â˜ƒ.setCustomName(this.getCustomName());
            â˜ƒ.setCustomNameVisible(this.isCustomNameVisible());
         }

         â˜ƒ.setPersistenceRequired();
         â˜ƒ.addFreshEntity(â˜ƒ);
         this.discard();
      } else {
         super.thunderHit(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void travel(Vec3 var1) {
      this.travel(this, this.steering, â˜ƒ);
   }

   @Override
   public float getSteeringSpeed() {
      return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F;
   }

   @Override
   public void travelWithInput(Vec3 var1) {
      super.travel(â˜ƒ);
   }

   @Override
   public boolean boost() {
      return this.steering.boost(this.getRandom());
   }

   public Pig getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return EntityType.PIG.create(â˜ƒ);
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return FOOD_ITEMS.test(â˜ƒ);
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }
}
