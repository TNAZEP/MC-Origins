package net.minecraft.world.entity.animal.horse;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHorse extends Animal implements ContainerListener, PlayerRideableJumping, Saddleable {
   public static final int EQUIPMENT_SLOT_OFFSET = 400;
   public static final int CHEST_SLOT_OFFSET = 499;
   public static final int INVENTORY_SLOT_OFFSET = 500;
   private static final Predicate<LivingEntity> PARENT_HORSE_SELECTOR = var0 -> var0 instanceof AbstractHorse && ((AbstractHorse)var0).isBred();
   private static final TargetingConditions MOMMY_TARGETING = TargetingConditions.forNonCombat()
      .range(16.0)
      .ignoreLineOfSight()
      .selector(PARENT_HORSE_SELECTOR);
   private static final Ingredient FOOD_ITEMS = Ingredient.of(
      Items.WHEAT, Items.SUGAR, Blocks.HAY_BLOCK.asItem(), Items.APPLE, Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE
   );
   private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(AbstractHorse.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID = SynchedEntityData.defineId(
      AbstractHorse.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final int FLAG_TAME = 2;
   private static final int FLAG_SADDLE = 4;
   private static final int FLAG_BRED = 8;
   private static final int FLAG_EATING = 16;
   private static final int FLAG_STANDING = 32;
   private static final int FLAG_OPEN_MOUTH = 64;
   public static final int INV_SLOT_SADDLE = 0;
   public static final int INV_SLOT_ARMOR = 1;
   public static final int INV_BASE_COUNT = 2;
   private int eatingCounter;
   private int mouthCounter;
   private int standCounter;
   public int tailCounter;
   public int sprintCounter;
   protected boolean isJumping;
   protected SimpleContainer inventory;
   protected int temper;
   protected float playerJumpPendingScale;
   private boolean allowStandSliding;
   private float eatAnim;
   private float eatAnimO;
   private float standAnim;
   private float standAnimO;
   private float mouthAnim;
   private float mouthAnimO;
   protected boolean canGallop = true;
   protected int gallopSoundCounter;

   protected AbstractHorse(EntityType<? extends AbstractHorse> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.maxUpStep = 1.0F;
      this.createInventory();
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
      this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0, AbstractHorse.class));
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.addBehaviourGoals();
   }

   protected void addBehaviourGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_FLAGS, (byte)0);
      this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
   }

   protected boolean getFlag(int var1) {
      return (this.entityData.get(DATA_ID_FLAGS) & â˜ƒ) != 0;
   }

   protected void setFlag(int var1, boolean var2) {
      byte â˜ƒ = this.entityData.get(DATA_ID_FLAGS);
      if (â˜ƒ) {
         this.entityData.set(DATA_ID_FLAGS, (byte)(â˜ƒ | â˜ƒ));
      } else {
         this.entityData.set(DATA_ID_FLAGS, (byte)(â˜ƒ & ~â˜ƒ));
      }
   }

   public boolean isTamed() {
      return this.getFlag(2);
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(DATA_ID_OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID var1) {
      this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(â˜ƒ));
   }

   public boolean isJumping() {
      return this.isJumping;
   }

   public void setTamed(boolean var1) {
      this.setFlag(2, â˜ƒ);
   }

   public void setIsJumping(boolean var1) {
      this.isJumping = â˜ƒ;
   }

   @Override
   protected void onLeashDistance(float var1) {
      if (â˜ƒ > 6.0F && this.isEating()) {
         this.setEating(false);
      }
   }

   public boolean isEating() {
      return this.getFlag(16);
   }

   public boolean isStanding() {
      return this.getFlag(32);
   }

   public boolean isBred() {
      return this.getFlag(8);
   }

   public void setBred(boolean var1) {
      this.setFlag(8, â˜ƒ);
   }

   @Override
   public boolean isSaddleable() {
      return this.isAlive() && !this.isBaby() && this.isTamed();
   }

   @Override
   public void equipSaddle(@Nullable SoundSource var1) {
      this.inventory.setItem(0, new ItemStack(Items.SADDLE));
      if (â˜ƒ != null) {
         this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, â˜ƒ, 0.5F, 1.0F);
      }
   }

   @Override
   public boolean isSaddled() {
      return this.getFlag(4);
   }

   public int getTemper() {
      return this.temper;
   }

   public void setTemper(int var1) {
      this.temper = â˜ƒ;
   }

   public int modifyTemper(int var1) {
      int â˜ƒ = Mth.clamp(this.getTemper() + â˜ƒ, 0, this.getMaxTemper());
      this.setTemper(â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public boolean isPushable() {
      return !this.isVehicle();
   }

   private void eating() {
      this.openMouth();
      if (!this.isSilent()) {
         SoundEvent â˜ƒ = this.getEatingSound();
         if (â˜ƒ != null) {
            this.level
               .playSound(
                  null,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  â˜ƒ,
                  this.getSoundSource(),
                  1.0F,
                  1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
               );
         }
      }
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      if (â˜ƒ > 1.0F) {
         this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
      }

      int â˜ƒ = this.calculateFallDamage(â˜ƒ, â˜ƒ);
      if (â˜ƒ <= 0) {
         return false;
      } else {
         this.hurt(â˜ƒ, (float)â˜ƒ);
         if (this.isVehicle()) {
            for(Entity â˜ƒ : this.getIndirectPassengers()) {
               â˜ƒ.hurt(â˜ƒ, (float)â˜ƒ);
            }
         }

         this.playBlockFallSound();
         return true;
      }
   }

   @Override
   protected int calculateFallDamage(float var1, float var2) {
      return Mth.ceil((â˜ƒ * 0.5F - 3.0F) * â˜ƒ);
   }

   protected int getInventorySize() {
      return 2;
   }

   protected void createInventory() {
      SimpleContainer â˜ƒ = this.inventory;
      this.inventory = new SimpleContainer(this.getInventorySize());
      if (â˜ƒ != null) {
         â˜ƒ.removeListener(this);
         int â˜ƒx = Math.min(â˜ƒ.getContainerSize(), this.inventory.getContainerSize());

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
            ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
            if (!â˜ƒxxx.isEmpty()) {
               this.inventory.setItem(â˜ƒxx, â˜ƒxxx.copy());
            }
         }
      }

      this.inventory.addListener(this);
      this.updateContainerEquipment();
   }

   protected void updateContainerEquipment() {
      if (!this.level.isClientSide) {
         this.setFlag(4, !this.inventory.getItem(0).isEmpty());
      }
   }

   @Override
   public void containerChanged(Container var1) {
      boolean â˜ƒ = this.isSaddled();
      this.updateContainerEquipment();
      if (this.tickCount > 20 && !â˜ƒ && this.isSaddled()) {
         this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
      }
   }

   public double getCustomJump() {
      return this.getAttributeValue(Attributes.JUMP_STRENGTH);
   }

   @Nullable
   protected SoundEvent getEatingSound() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      if (this.random.nextInt(3) == 0) {
         this.stand();
      }

      return null;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.random.nextInt(10) == 0 && !this.isImmobile()) {
         this.stand();
      }

      return null;
   }

   @Nullable
   protected SoundEvent getAngrySound() {
      this.stand();
      return null;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      if (!â˜ƒ.getMaterial().isLiquid()) {
         BlockState â˜ƒ = this.level.getBlockState(â˜ƒ.above());
         SoundType â˜ƒx = â˜ƒ.getSoundType();
         if (â˜ƒ.is(Blocks.SNOW)) {
            â˜ƒx = â˜ƒ.getSoundType();
         }

         if (this.isVehicle() && this.canGallop) {
            ++this.gallopSoundCounter;
            if (this.gallopSoundCounter > 5 && this.gallopSoundCounter % 3 == 0) {
               this.playGallopSound(â˜ƒx);
            } else if (this.gallopSoundCounter <= 5) {
               this.playSound(SoundEvents.HORSE_STEP_WOOD, â˜ƒx.getVolume() * 0.15F, â˜ƒx.getPitch());
            }
         } else if (â˜ƒx == SoundType.WOOD) {
            this.playSound(SoundEvents.HORSE_STEP_WOOD, â˜ƒx.getVolume() * 0.15F, â˜ƒx.getPitch());
         } else {
            this.playSound(SoundEvents.HORSE_STEP, â˜ƒx.getVolume() * 0.15F, â˜ƒx.getPitch());
         }
      }
   }

   protected void playGallopSound(SoundType var1) {
      this.playSound(SoundEvents.HORSE_GALLOP, â˜ƒ.getVolume() * 0.15F, â˜ƒ.getPitch());
   }

   public static AttributeSupplier.Builder createBaseHorseAttributes() {
      return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH).add(Attributes.MAX_HEALTH, 53.0).add(Attributes.MOVEMENT_SPEED, 0.225F);
   }

   @Override
   public int getMaxSpawnClusterSize() {
      return 6;
   }

   public int getMaxTemper() {
      return 100;
   }

   @Override
   protected float getSoundVolume() {
      return 0.8F;
   }

   @Override
   public int getAmbientSoundInterval() {
      return 400;
   }

   public void openInventory(Player var1) {
      if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(â˜ƒ)) && this.isTamed()) {
         â˜ƒ.openHorseInventory(this, this.inventory);
      }
   }

   public InteractionResult fedFood(Player var1, ItemStack var2) {
      boolean â˜ƒ = this.handleEating(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.getAbilities().instabuild) {
         â˜ƒ.shrink(1);
      }

      if (this.level.isClientSide) {
         return InteractionResult.CONSUME;
      } else {
         return â˜ƒ ? InteractionResult.SUCCESS : InteractionResult.PASS;
      }
   }

   protected boolean handleEating(Player var1, ItemStack var2) {
      boolean â˜ƒ = false;
      float â˜ƒx = 0.0F;
      int â˜ƒxx = 0;
      int â˜ƒxxx = 0;
      if (â˜ƒ.is(Items.WHEAT)) {
         â˜ƒx = 2.0F;
         â˜ƒxx = 20;
         â˜ƒxxx = 3;
      } else if (â˜ƒ.is(Items.SUGAR)) {
         â˜ƒx = 1.0F;
         â˜ƒxx = 30;
         â˜ƒxxx = 3;
      } else if (â˜ƒ.is(Blocks.HAY_BLOCK.asItem())) {
         â˜ƒx = 20.0F;
         â˜ƒxx = 180;
      } else if (â˜ƒ.is(Items.APPLE)) {
         â˜ƒx = 3.0F;
         â˜ƒxx = 60;
         â˜ƒxxx = 3;
      } else if (â˜ƒ.is(Items.GOLDEN_CARROT)) {
         â˜ƒx = 4.0F;
         â˜ƒxx = 60;
         â˜ƒxxx = 5;
         if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
            â˜ƒ = true;
            this.setInLove(â˜ƒ);
         }
      } else if (â˜ƒ.is(Items.GOLDEN_APPLE) || â˜ƒ.is(Items.ENCHANTED_GOLDEN_APPLE)) {
         â˜ƒx = 10.0F;
         â˜ƒxx = 240;
         â˜ƒxxx = 10;
         if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
            â˜ƒ = true;
            this.setInLove(â˜ƒ);
         }
      }

      if (this.getHealth() < this.getMaxHealth() && â˜ƒx > 0.0F) {
         this.heal(â˜ƒx);
         â˜ƒ = true;
      }

      if (this.isBaby() && â˜ƒxx > 0) {
         this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
         if (!this.level.isClientSide) {
            this.ageUp(â˜ƒxx);
         }

         â˜ƒ = true;
      }

      if (â˜ƒxxx > 0 && (â˜ƒ || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
         â˜ƒ = true;
         if (!this.level.isClientSide) {
            this.modifyTemper(â˜ƒxxx);
         }
      }

      if (â˜ƒ) {
         this.eating();
         this.gameEvent(GameEvent.EAT, this.eyeBlockPosition());
      }

      return â˜ƒ;
   }

   protected void doPlayerRide(Player var1) {
      this.setEating(false);
      this.setStanding(false);
      if (!this.level.isClientSide) {
         â˜ƒ.setYRot(this.getYRot());
         â˜ƒ.setXRot(this.getXRot());
         â˜ƒ.startRiding(this);
      }
   }

   @Override
   protected boolean isImmobile() {
      return super.isImmobile() && this.isVehicle() && this.isSaddled() || this.isEating() || this.isStanding();
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return FOOD_ITEMS.test(â˜ƒ);
   }

   private void moveTail() {
      this.tailCounter = 1;
   }

   @Override
   protected void dropEquipment() {
      super.dropEquipment();
      if (this.inventory != null) {
         for(int â˜ƒ = 0; â˜ƒ < this.inventory.getContainerSize(); ++â˜ƒ) {
            ItemStack â˜ƒx = this.inventory.getItem(â˜ƒ);
            if (!â˜ƒx.isEmpty() && !EnchantmentHelper.hasVanishingCurse(â˜ƒx)) {
               this.spawnAtLocation(â˜ƒx);
            }
         }
      }
   }

   @Override
   public void aiStep() {
      if (this.random.nextInt(200) == 0) {
         this.moveTail();
      }

      super.aiStep();
      if (!this.level.isClientSide && this.isAlive()) {
         if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
            this.heal(1.0F);
         }

         if (this.canEatGrass()) {
            if (!this.isEating()
               && !this.isVehicle()
               && this.random.nextInt(300) == 0
               && this.level.getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
               this.setEating(true);
            }

            if (this.isEating() && ++this.eatingCounter > 50) {
               this.eatingCounter = 0;
               this.setEating(false);
            }
         }

         this.followMommy();
      }
   }

   protected void followMommy() {
      if (this.isBred() && this.isBaby() && !this.isEating()) {
         LivingEntity â˜ƒ = this.level
            .getNearestEntity(AbstractHorse.class, MOMMY_TARGETING, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0));
         if (â˜ƒ != null && this.distanceToSqr(â˜ƒ) > 4.0) {
            this.navigation.createPath(â˜ƒ, 0);
         }
      }
   }

   public boolean canEatGrass() {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
         this.mouthCounter = 0;
         this.setFlag(64, false);
      }

      if ((this.isControlledByLocalInstance() || this.isEffectiveAi()) && this.standCounter > 0 && ++this.standCounter > 20) {
         this.standCounter = 0;
         this.setStanding(false);
      }

      if (this.tailCounter > 0 && ++this.tailCounter > 8) {
         this.tailCounter = 0;
      }

      if (this.sprintCounter > 0) {
         ++this.sprintCounter;
         if (this.sprintCounter > 300) {
            this.sprintCounter = 0;
         }
      }

      this.eatAnimO = this.eatAnim;
      if (this.isEating()) {
         this.eatAnim += (1.0F - this.eatAnim) * 0.4F + 0.05F;
         if (this.eatAnim > 1.0F) {
            this.eatAnim = 1.0F;
         }
      } else {
         this.eatAnim += (0.0F - this.eatAnim) * 0.4F - 0.05F;
         if (this.eatAnim < 0.0F) {
            this.eatAnim = 0.0F;
         }
      }

      this.standAnimO = this.standAnim;
      if (this.isStanding()) {
         this.eatAnim = 0.0F;
         this.eatAnimO = this.eatAnim;
         this.standAnim += (1.0F - this.standAnim) * 0.4F + 0.05F;
         if (this.standAnim > 1.0F) {
            this.standAnim = 1.0F;
         }
      } else {
         this.allowStandSliding = false;
         this.standAnim += (0.8F * this.standAnim * this.standAnim * this.standAnim - this.standAnim) * 0.6F - 0.05F;
         if (this.standAnim < 0.0F) {
            this.standAnim = 0.0F;
         }
      }

      this.mouthAnimO = this.mouthAnim;
      if (this.getFlag(64)) {
         this.mouthAnim += (1.0F - this.mouthAnim) * 0.7F + 0.05F;
         if (this.mouthAnim > 1.0F) {
            this.mouthAnim = 1.0F;
         }
      } else {
         this.mouthAnim += (0.0F - this.mouthAnim) * 0.7F - 0.05F;
         if (this.mouthAnim < 0.0F) {
            this.mouthAnim = 0.0F;
         }
      }
   }

   private void openMouth() {
      if (!this.level.isClientSide) {
         this.mouthCounter = 1;
         this.setFlag(64, true);
      }
   }

   public void setEating(boolean var1) {
      this.setFlag(16, â˜ƒ);
   }

   public void setStanding(boolean var1) {
      if (â˜ƒ) {
         this.setEating(false);
      }

      this.setFlag(32, â˜ƒ);
   }

   private void stand() {
      if (this.isControlledByLocalInstance() || this.isEffectiveAi()) {
         this.standCounter = 1;
         this.setStanding(true);
      }
   }

   public void makeMad() {
      if (!this.isStanding()) {
         this.stand();
         SoundEvent â˜ƒ = this.getAngrySound();
         if (â˜ƒ != null) {
            this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
         }
      }
   }

   public boolean tameWithName(Player var1) {
      this.setOwnerUUID(â˜ƒ.getUUID());
      this.setTamed(true);
      if (â˜ƒ instanceof ServerPlayer) {
         CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)â˜ƒ, this);
      }

      this.level.broadcastEntityEvent(this, (byte)7);
      return true;
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isAlive()) {
         if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
            LivingEntity â˜ƒ = (LivingEntity)this.getControllingPassenger();
            this.setYRot(â˜ƒ.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(â˜ƒ.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            float â˜ƒx = â˜ƒ.xxa * 0.5F;
            float â˜ƒxx = â˜ƒ.zza;
            if (â˜ƒxx <= 0.0F) {
               â˜ƒxx *= 0.25F;
               this.gallopSoundCounter = 0;
            }

            if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
               â˜ƒx = 0.0F;
               â˜ƒxx = 0.0F;
            }

            if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
               double â˜ƒ = this.getCustomJump() * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
               double â˜ƒx = â˜ƒ + this.getJumpBoostPower();
               Vec3 â˜ƒxx = this.getDeltaMovement();
               this.setDeltaMovement(â˜ƒxx.x, â˜ƒx, â˜ƒxx.z);
               this.setIsJumping(true);
               this.hasImpulse = true;
               if (â˜ƒxx > 0.0F) {
                  float â˜ƒxxx = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
                  float â˜ƒxxxx = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
                  this.setDeltaMovement(
                     this.getDeltaMovement()
                        .add((double)(-0.4F * â˜ƒxxx * this.playerJumpPendingScale), 0.0, (double)(0.4F * â˜ƒxxxx * this.playerJumpPendingScale))
                  );
               }

               this.playerJumpPendingScale = 0.0F;
            }

            this.flyingSpeed = this.getSpeed() * 0.1F;
            if (this.isControlledByLocalInstance()) {
               this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
               super.travel(new Vec3((double)â˜ƒx, â˜ƒ.y, (double)â˜ƒxx));
            } else if (â˜ƒ instanceof Player) {
               this.setDeltaMovement(Vec3.ZERO);
            }

            if (this.onGround) {
               this.playerJumpPendingScale = 0.0F;
               this.setIsJumping(false);
            }

            this.calculateEntityAnimation(this, false);
            this.tryCheckInsideBlocks();
         } else {
            this.flyingSpeed = 0.02F;
            super.travel(â˜ƒ);
         }
      }
   }

   protected void playJumpSound() {
      this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("EatingHaystack", this.isEating());
      â˜ƒ.putBoolean("Bred", this.isBred());
      â˜ƒ.putInt("Temper", this.getTemper());
      â˜ƒ.putBoolean("Tame", this.isTamed());
      if (this.getOwnerUUID() != null) {
         â˜ƒ.putUUID("Owner", this.getOwnerUUID());
      }

      if (!this.inventory.getItem(0).isEmpty()) {
         â˜ƒ.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setEating(â˜ƒ.getBoolean("EatingHaystack"));
      this.setBred(â˜ƒ.getBoolean("Bred"));
      this.setTemper(â˜ƒ.getInt("Temper"));
      this.setTamed(â˜ƒ.getBoolean("Tame"));
      UUID â˜ƒ;
      if (â˜ƒ.hasUUID("Owner")) {
         â˜ƒ = â˜ƒ.getUUID("Owner");
      } else {
         String â˜ƒ = â˜ƒ.getString("Owner");
         â˜ƒ = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), â˜ƒ);
      }

      if (â˜ƒ != null) {
         this.setOwnerUUID(â˜ƒ);
      }

      if (â˜ƒ.contains("SaddleItem", 10)) {
         ItemStack â˜ƒ = ItemStack.of(â˜ƒ.getCompound("SaddleItem"));
         if (â˜ƒ.is(Items.SADDLE)) {
            this.inventory.setItem(0, â˜ƒ);
         }
      }

      this.updateContainerEquipment();
   }

   @Override
   public boolean canMate(Animal var1) {
      return false;
   }

   protected boolean canParent() {
      return !this.isVehicle() && !this.isPassenger() && this.isTamed() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return null;
   }

   protected void setOffspringAttributes(AgeableMob var1, AbstractHorse var2) {
      double â˜ƒ = this.getAttributeBaseValue(Attributes.MAX_HEALTH)
         + â˜ƒ.getAttributeBaseValue(Attributes.MAX_HEALTH)
         + (double)this.generateRandomMaxHealth();
      â˜ƒ.getAttribute(Attributes.MAX_HEALTH).setBaseValue(â˜ƒ / 3.0);
      double â˜ƒx = this.getAttributeBaseValue(Attributes.JUMP_STRENGTH)
         + â˜ƒ.getAttributeBaseValue(Attributes.JUMP_STRENGTH)
         + this.generateRandomJumpStrength();
      â˜ƒ.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(â˜ƒx / 3.0);
      double â˜ƒxx = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + â˜ƒ.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + this.generateRandomSpeed();
      â˜ƒ.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(â˜ƒxx / 3.0);
   }

   @Override
   public boolean canBeControlledByRider() {
      return this.getControllingPassenger() instanceof LivingEntity;
   }

   public float getEatAnim(float var1) {
      return Mth.lerp(â˜ƒ, this.eatAnimO, this.eatAnim);
   }

   public float getStandAnim(float var1) {
      return Mth.lerp(â˜ƒ, this.standAnimO, this.standAnim);
   }

   public float getMouthAnim(float var1) {
      return Mth.lerp(â˜ƒ, this.mouthAnimO, this.mouthAnim);
   }

   @Override
   public void onPlayerJump(int var1) {
      if (this.isSaddled()) {
         if (â˜ƒ < 0) {
            â˜ƒ = 0;
         } else {
            this.allowStandSliding = true;
            this.stand();
         }

         if (â˜ƒ >= 90) {
            this.playerJumpPendingScale = 1.0F;
         } else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float)â˜ƒ / 90.0F;
         }
      }
   }

   @Override
   public boolean canJump() {
      return this.isSaddled();
   }

   @Override
   public void handleStartJump(int var1) {
      this.allowStandSliding = true;
      this.stand();
      this.playJumpSound();
   }

   @Override
   public void handleStopJump() {
   }

   protected void spawnTamingParticles(boolean var1) {
      ParticleOptions â˜ƒ = â˜ƒ ? ParticleTypes.HEART : ParticleTypes.SMOKE;

      for(int â˜ƒx = 0; â˜ƒx < 7; ++â˜ƒx) {
         double â˜ƒxx = this.random.nextGaussian() * 0.02;
         double â˜ƒxxx = this.random.nextGaussian() * 0.02;
         double â˜ƒxxxx = this.random.nextGaussian() * 0.02;
         this.level.addParticle(â˜ƒ, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 7) {
         this.spawnTamingParticles(true);
      } else if (â˜ƒ == 6) {
         this.spawnTamingParticles(false);
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   @Override
   public void positionRider(Entity var1) {
      super.positionRider(â˜ƒ);
      if (â˜ƒ instanceof Mob â˜ƒ) {
         this.yBodyRot = â˜ƒ.yBodyRot;
      }

      if (this.standAnimO > 0.0F) {
         float â˜ƒ = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         float â˜ƒx = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float â˜ƒxx = 0.7F * this.standAnimO;
         float â˜ƒxxx = 0.15F * this.standAnimO;
         â˜ƒ.setPos(
            this.getX() + (double)(â˜ƒxx * â˜ƒ),
            this.getY() + this.getPassengersRidingOffset() + â˜ƒ.getMyRidingOffset() + (double)â˜ƒxxx,
            this.getZ() - (double)(â˜ƒxx * â˜ƒx)
         );
         if (â˜ƒ instanceof LivingEntity) {
            ((LivingEntity)â˜ƒ).yBodyRot = this.yBodyRot;
         }
      }
   }

   protected float generateRandomMaxHealth() {
      return 15.0F + (float)this.random.nextInt(8) + (float)this.random.nextInt(9);
   }

   protected double generateRandomJumpStrength() {
      return 0.4F + this.random.nextDouble() * 0.2 + this.random.nextDouble() * 0.2 + this.random.nextDouble() * 0.2;
   }

   protected double generateRandomSpeed() {
      return (0.45F + this.random.nextDouble() * 0.3 + this.random.nextDouble() * 0.3 + this.random.nextDouble() * 0.3) * 0.25;
   }

   @Override
   public boolean onClimbable() {
      return false;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.95F;
   }

   public boolean canWearArmor() {
      return false;
   }

   public boolean isWearingArmor() {
      return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
   }

   public boolean isArmor(ItemStack var1) {
      return false;
   }

   private SlotAccess createEquipmentSlotAccess(final int var1, final Predicate<ItemStack> var2) {
      return new SlotAccess() {
         @Override
         public ItemStack get() {
            return AbstractHorse.this.inventory.getItem(â˜ƒ);
         }

         @Override
         public boolean set(ItemStack var1x) {
            if (!â˜ƒ.test(â˜ƒ)) {
               return false;
            } else {
               AbstractHorse.this.inventory.setItem(â˜ƒ, â˜ƒ);
               AbstractHorse.this.updateContainerEquipment();
               return true;
            }
         }
      };
   }

   @Override
   public SlotAccess getSlot(int var1) {
      int â˜ƒ = â˜ƒ - 400;
      if (â˜ƒ >= 0 && â˜ƒ < 2 && â˜ƒ < this.inventory.getContainerSize()) {
         if (â˜ƒ == 0) {
            return this.createEquipmentSlotAccess(â˜ƒ, var0 -> var0.isEmpty() || var0.is(Items.SADDLE));
         }

         if (â˜ƒ == 1) {
            if (!this.canWearArmor()) {
               return SlotAccess.NULL;
            }

            return this.createEquipmentSlotAccess(â˜ƒ, var1x -> var1x.isEmpty() || this.isArmor(var1x));
         }
      }

      int â˜ƒ = â˜ƒ - 500 + 2;
      return â˜ƒ >= 2 && â˜ƒ < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, â˜ƒ) : super.getSlot(â˜ƒ);
   }

   @Nullable
   @Override
   public Entity getControllingPassenger() {
      return this.getFirstPassenger();
   }

   @Nullable
   private Vec3 getDismountLocationInDirection(Vec3 var1, LivingEntity var2) {
      double â˜ƒ = this.getX() + â˜ƒ.x;
      double â˜ƒx = this.getBoundingBox().minY;
      double â˜ƒxx = this.getZ() + â˜ƒ.z;
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(Pose â˜ƒxxxx : â˜ƒ.getDismountPoses()) {
         â˜ƒxxx.set(â˜ƒ, â˜ƒx, â˜ƒxx);
         double â˜ƒxxxxx = this.getBoundingBox().maxY + 0.75;

         do {
            double â˜ƒxxxxxx = this.level.getBlockFloorHeight(â˜ƒxxx);
            if ((double)â˜ƒxxx.getY() + â˜ƒxxxxxx > â˜ƒxxxxx) {
               break;
            }

            if (DismountHelper.isBlockFloorValid(â˜ƒxxxxxx)) {
               AABB â˜ƒxxxxxx = â˜ƒ.getLocalBoundsForPose(â˜ƒxxxx);
               Vec3 â˜ƒxxxxxxx = new Vec3(â˜ƒ, (double)â˜ƒxxx.getY() + â˜ƒxxxxxx, â˜ƒxx);
               if (DismountHelper.canDismountTo(this.level, â˜ƒ, â˜ƒxxxxxx.move(â˜ƒxxxxxxx))) {
                  â˜ƒ.setPose(â˜ƒxxxx);
                  return â˜ƒxxxxxxx;
               }
            }

            â˜ƒxxx.move(Direction.UP);
         } while(!((double)â˜ƒxxx.getY() < â˜ƒxxxxx));
      }

      return null;
   }

   @Override
   public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
      Vec3 â˜ƒ = getCollisionHorizontalEscapeVector(
         (double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), this.getYRot() + (â˜ƒ.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F)
      );
      Vec3 â˜ƒx = this.getDismountLocationInDirection(â˜ƒ, â˜ƒ);
      if (â˜ƒx != null) {
         return â˜ƒx;
      } else {
         Vec3 â˜ƒ = getCollisionHorizontalEscapeVector(
            (double)this.getBbWidth(), (double)â˜ƒ.getBbWidth(), this.getYRot() + (â˜ƒ.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F)
         );
         Vec3 â˜ƒx = this.getDismountLocationInDirection(â˜ƒ, â˜ƒ);
         return â˜ƒx != null ? â˜ƒx : this.position();
      }
   }

   protected void randomizeAttributes() {
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (â˜ƒ == null) {
         â˜ƒ = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      this.randomizeAttributes();
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean hasInventoryChanged(Container var1) {
      return this.inventory != â˜ƒ;
   }
}
