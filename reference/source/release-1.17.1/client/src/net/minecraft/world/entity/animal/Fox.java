package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.StrollThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Fox extends Animal {
   private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.BYTE);
   private static final int FLAG_SITTING = 1;
   public static final int FLAG_CROUCHING = 4;
   public static final int FLAG_INTERESTED = 8;
   public static final int FLAG_POUNCING = 16;
   private static final int FLAG_SLEEPING = 32;
   private static final int FLAG_FACEPLANTED = 64;
   private static final int FLAG_DEFENDING = 128;
   private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.OPTIONAL_UUID);
   static final Predicate<ItemEntity> ALLOWED_ITEMS = var0 -> !var0.hasPickUpDelay() && var0.isAlive();
   private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR = var0 -> {
      if (!(var0 instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity â˜ƒ = (LivingEntity)var0;
         return â˜ƒ.getLastHurtMob() != null && â˜ƒ.getLastHurtMobTimestamp() < â˜ƒ.tickCount + 600;
      }
   };
   static final Predicate<Entity> STALKABLE_PREY = var0 -> var0 instanceof Chicken || var0 instanceof Rabbit;
   private static final Predicate<Entity> AVOID_PLAYERS = var0 -> !var0.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(var0);
   private static final int MIN_TICKS_BEFORE_EAT = 600;
   private Goal landTargetGoal;
   private Goal turtleEggTargetGoal;
   private Goal fishTargetGoal;
   private float interestedAngle;
   private float interestedAngleO;
   float crouchAmount;
   float crouchAmountO;
   private int ticksSinceEaten;

   public Fox(EntityType<? extends Fox> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.lookControl = new Fox.FoxLookControl();
      this.moveControl = new Fox.FoxMoveControl();
      this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
      this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
      this.setCanPickUpLoot(true);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
      this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
      this.entityData.define(DATA_TYPE_ID, 0);
      this.entityData.define(DATA_FLAGS_ID, (byte)0);
   }

   @Override
   protected void registerGoals() {
      this.landTargetGoal = new NearestAttackableTargetGoal(this, Animal.class, 10, false, false, var0 -> var0 instanceof Chicken || var0 instanceof Rabbit);
      this.turtleEggTargetGoal = new NearestAttackableTargetGoal(this, Turtle.class, 10, false, false, Turtle.BABY_ON_LAND_SELECTOR);
      this.fishTargetGoal = new NearestAttackableTargetGoal(this, AbstractFish.class, 20, false, false, var0 -> var0 instanceof AbstractSchoolingFish);
      this.goalSelector.addGoal(0, new Fox.FoxFloatGoal());
      this.goalSelector.addGoal(1, new Fox.FaceplantGoal());
      this.goalSelector.addGoal(2, new Fox.FoxPanicGoal(2.2));
      this.goalSelector.addGoal(3, new Fox.FoxBreedGoal(1.0));
      this.goalSelector
         .addGoal(
            4,
            new AvoidEntityGoal(this, Player.class, 16.0F, 1.6, 1.4, var1 -> AVOID_PLAYERS.test(var1) && !this.trusts(var1.getUUID()) && !this.isDefending())
         );
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, Wolf.class, 8.0F, 1.6, 1.4, var1 -> !((Wolf)var1).isTame() && !this.isDefending()));
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, PolarBear.class, 8.0F, 1.6, 1.4, var1 -> !this.isDefending()));
      this.goalSelector.addGoal(5, new Fox.StalkPreyGoal());
      this.goalSelector.addGoal(6, new Fox.FoxPounceGoal());
      this.goalSelector.addGoal(6, new Fox.SeekShelterGoal(1.25));
      this.goalSelector.addGoal(7, new Fox.FoxMeleeAttackGoal(1.2F, true));
      this.goalSelector.addGoal(7, new Fox.SleepGoal());
      this.goalSelector.addGoal(8, new Fox.FoxFollowParentGoal(this, 1.25));
      this.goalSelector.addGoal(9, new Fox.FoxStrollThroughVillageGoal(32, 200));
      this.goalSelector.addGoal(10, new Fox.FoxEatBerriesGoal(1.2F, 12, 1));
      this.goalSelector.addGoal(10, new LeapAtTargetGoal(this, 0.4F));
      this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(11, new Fox.FoxSearchForItemsGoal());
      this.goalSelector.addGoal(12, new Fox.FoxLookAtPlayerGoal(this, Player.class, 24.0F));
      this.goalSelector.addGoal(13, new Fox.PerchAndSearchGoal());
      this.targetSelector
         .addGoal(
            3, new Fox.DefendTrustedTargetGoal(LivingEntity.class, false, false, var1 -> TRUSTED_TARGET_SELECTOR.test(var1) && !this.trusts(var1.getUUID()))
         );
   }

   @Override
   public SoundEvent getEatingSound(ItemStack var1) {
      return SoundEvents.FOX_EAT;
   }

   @Override
   public void aiStep() {
      if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
         ++this.ticksSinceEaten;
         ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (this.canEat(â˜ƒ)) {
            if (this.ticksSinceEaten > 600) {
               ItemStack â˜ƒx = â˜ƒ.finishUsingItem(this.level, this);
               if (!â˜ƒx.isEmpty()) {
                  this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒx);
               }

               this.ticksSinceEaten = 0;
            } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
               this.playSound(this.getEatingSound(â˜ƒ), 1.0F, 1.0F);
               this.level.broadcastEntityEvent(this, (byte)45);
            }
         }

         LivingEntity â˜ƒ = this.getTarget();
         if (â˜ƒ == null || !â˜ƒ.isAlive()) {
            this.setIsCrouching(false);
            this.setIsInterested(false);
         }
      }

      if (this.isSleeping() || this.isImmobile()) {
         this.jumping = false;
         this.xxa = 0.0F;
         this.zza = 0.0F;
      }

      super.aiStep();
      if (this.isDefending() && this.random.nextFloat() < 0.05F) {
         this.playSound(SoundEvents.FOX_AGGRO, 1.0F, 1.0F);
      }
   }

   @Override
   protected boolean isImmobile() {
      return this.isDeadOrDying();
   }

   private boolean canEat(ItemStack var1) {
      return â˜ƒ.getItem().isEdible() && this.getTarget() == null && this.onGround && !this.isSleeping();
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      if (this.random.nextFloat() < 0.2F) {
         float â˜ƒx = this.random.nextFloat();
         ItemStack â˜ƒ;
         if (â˜ƒx < 0.05F) {
            â˜ƒ = new ItemStack(Items.EMERALD);
         } else if (â˜ƒx < 0.2F) {
            â˜ƒ = new ItemStack(Items.EGG);
         } else if (â˜ƒx < 0.4F) {
            â˜ƒ = this.random.nextBoolean() ? new ItemStack(Items.RABBIT_FOOT) : new ItemStack(Items.RABBIT_HIDE);
         } else if (â˜ƒx < 0.6F) {
            â˜ƒ = new ItemStack(Items.WHEAT);
         } else if (â˜ƒx < 0.8F) {
            â˜ƒ = new ItemStack(Items.LEATHER);
         } else {
            â˜ƒ = new ItemStack(Items.FEATHER);
         }

         this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 45) {
         ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (!â˜ƒ.isEmpty()) {
            for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
               Vec3 â˜ƒxx = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                  .xRot(-this.getXRot() * (float) (Math.PI / 180.0))
                  .yRot(-this.getYRot() * (float) (Math.PI / 180.0));
               this.level
                  .addParticle(
                     new ItemParticleOption(ParticleTypes.ITEM, â˜ƒ),
                     this.getX() + this.getLookAngle().x / 2.0,
                     this.getY(),
                     this.getZ() + this.getLookAngle().z / 2.0,
                     â˜ƒxx.x,
                     â˜ƒxx.y + 0.05,
                     â˜ƒxx.z
                  );
            }
         }
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.MAX_HEALTH, 10.0)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.ATTACK_DAMAGE, 2.0);
   }

   public Fox getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Fox â˜ƒ = EntityType.FOX.create(â˜ƒ);
      â˜ƒ.setFoxType(this.random.nextBoolean() ? this.getFoxType() : ((Fox)â˜ƒ).getFoxType());
      return â˜ƒ;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      Optional<ResourceKey<Biome>> â˜ƒ = â˜ƒ.getBiomeName(this.blockPosition());
      Fox.Type â˜ƒx = Fox.Type.byBiome(â˜ƒ);
      boolean â˜ƒxx = false;
      if (â˜ƒ instanceof Fox.FoxGroupData) {
         â˜ƒx = ((Fox.FoxGroupData)â˜ƒ).type;
         if (((Fox.FoxGroupData)â˜ƒ).getGroupSize() >= 2) {
            â˜ƒxx = true;
         }
      } else {
         â˜ƒ = new Fox.FoxGroupData(â˜ƒx);
      }

      this.setFoxType(â˜ƒx);
      if (â˜ƒxx) {
         this.setAge(-24000);
      }

      if (â˜ƒ instanceof ServerLevel) {
         this.setTargetGoals();
      }

      this.populateDefaultEquipmentSlots(â˜ƒ);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void setTargetGoals() {
      if (this.getFoxType() == Fox.Type.RED) {
         this.targetSelector.addGoal(4, this.landTargetGoal);
         this.targetSelector.addGoal(4, this.turtleEggTargetGoal);
         this.targetSelector.addGoal(6, this.fishTargetGoal);
      } else {
         this.targetSelector.addGoal(4, this.fishTargetGoal);
         this.targetSelector.addGoal(6, this.landTargetGoal);
         this.targetSelector.addGoal(6, this.turtleEggTargetGoal);
      }
   }

   @Override
   protected void usePlayerItem(Player var1, InteractionHand var2, ItemStack var3) {
      if (this.isFood(â˜ƒ)) {
         this.playSound(this.getEatingSound(â˜ƒ), 1.0F, 1.0F);
      }

      super.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return this.isBaby() ? â˜ƒ.height * 0.85F : 0.4F;
   }

   public Fox.Type getFoxType() {
      return Fox.Type.byId(this.entityData.get(DATA_TYPE_ID));
   }

   private void setFoxType(Fox.Type var1) {
      this.entityData.set(DATA_TYPE_ID, â˜ƒ.getId());
   }

   List<UUID> getTrustedUUIDs() {
      List<UUID> â˜ƒ = Lists.newArrayList();
      â˜ƒ.add((UUID)((Optional)this.entityData.get(DATA_TRUSTED_ID_0)).orElse(null));
      â˜ƒ.add((UUID)((Optional)this.entityData.get(DATA_TRUSTED_ID_1)).orElse(null));
      return â˜ƒ;
   }

   void addTrustedUUID(@Nullable UUID var1) {
      if (((Optional)this.entityData.get(DATA_TRUSTED_ID_0)).isPresent()) {
         this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(â˜ƒ));
      } else {
         this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(â˜ƒ));
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      List<UUID> â˜ƒ = this.getTrustedUUIDs();
      ListTag â˜ƒx = new ListTag();

      for(UUID â˜ƒxx : â˜ƒ) {
         if (â˜ƒxx != null) {
            â˜ƒx.add(NbtUtils.createUUID(â˜ƒxx));
         }
      }

      â˜ƒ.put("Trusted", â˜ƒx);
      â˜ƒ.putBoolean("Sleeping", this.isSleeping());
      â˜ƒ.putString("Type", this.getFoxType().getName());
      â˜ƒ.putBoolean("Sitting", this.isSitting());
      â˜ƒ.putBoolean("Crouching", this.isCrouching());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      ListTag â˜ƒ = â˜ƒ.getList("Trusted", 11);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         this.addTrustedUUID(NbtUtils.loadUUID(â˜ƒ.get(â˜ƒx)));
      }

      this.setSleeping(â˜ƒ.getBoolean("Sleeping"));
      this.setFoxType(Fox.Type.byName(â˜ƒ.getString("Type")));
      this.setSitting(â˜ƒ.getBoolean("Sitting"));
      this.setIsCrouching(â˜ƒ.getBoolean("Crouching"));
      if (this.level instanceof ServerLevel) {
         this.setTargetGoals();
      }
   }

   public boolean isSitting() {
      return this.getFlag(1);
   }

   public void setSitting(boolean var1) {
      this.setFlag(1, â˜ƒ);
   }

   public boolean isFaceplanted() {
      return this.getFlag(64);
   }

   void setFaceplanted(boolean var1) {
      this.setFlag(64, â˜ƒ);
   }

   boolean isDefending() {
      return this.getFlag(128);
   }

   void setDefending(boolean var1) {
      this.setFlag(128, â˜ƒ);
   }

   @Override
   public boolean isSleeping() {
      return this.getFlag(32);
   }

   void setSleeping(boolean var1) {
      this.setFlag(32, â˜ƒ);
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
   public boolean canHoldItem(ItemStack var1) {
      Item â˜ƒ = â˜ƒ.getItem();
      ItemStack â˜ƒx = this.getItemBySlot(EquipmentSlot.MAINHAND);
      return â˜ƒx.isEmpty() || this.ticksSinceEaten > 0 && â˜ƒ.isEdible() && !â˜ƒx.getItem().isEdible();
   }

   private void spitOutItem(ItemStack var1) {
      if (!â˜ƒ.isEmpty() && !this.level.isClientSide) {
         ItemEntity â˜ƒ = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0, this.getZ() + this.getLookAngle().z, â˜ƒ);
         â˜ƒ.setPickUpDelay(40);
         â˜ƒ.setThrower(this.getUUID());
         this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
         this.level.addFreshEntity(â˜ƒ);
      }
   }

   private void dropItemStack(ItemStack var1) {
      ItemEntity â˜ƒ = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), â˜ƒ);
      this.level.addFreshEntity(â˜ƒ);
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      ItemStack â˜ƒ = â˜ƒ.getItem();
      if (this.canHoldItem(â˜ƒ)) {
         int â˜ƒx = â˜ƒ.getCount();
         if (â˜ƒx > 1) {
            this.dropItemStack(â˜ƒ.split(â˜ƒx - 1));
         }

         this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
         this.onItemPickup(â˜ƒ);
         this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ.split(1));
         this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
         this.take(â˜ƒ, â˜ƒ.getCount());
         â˜ƒ.discard();
         this.ticksSinceEaten = 0;
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isEffectiveAi()) {
         boolean â˜ƒ = this.isInWater();
         if (â˜ƒ || this.getTarget() != null || this.level.isThundering()) {
            this.wakeUp();
         }

         if (â˜ƒ || this.isSleeping()) {
            this.setSitting(false);
         }

         if (this.isFaceplanted() && this.level.random.nextFloat() < 0.2F) {
            BlockPos â˜ƒ = this.blockPosition();
            BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
            this.level.levelEvent(2001, â˜ƒ, Block.getId(â˜ƒx));
         }
      }

      this.interestedAngleO = this.interestedAngle;
      if (this.isInterested()) {
         this.interestedAngle += (1.0F - this.interestedAngle) * 0.4F;
      } else {
         this.interestedAngle += (0.0F - this.interestedAngle) * 0.4F;
      }

      this.crouchAmountO = this.crouchAmount;
      if (this.isCrouching()) {
         this.crouchAmount += 0.2F;
         if (this.crouchAmount > 3.0F) {
            this.crouchAmount = 3.0F;
         }
      } else {
         this.crouchAmount = 0.0F;
      }
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return â˜ƒ.is(ItemTags.FOX_FOOD);
   }

   @Override
   protected void onOffspringSpawnedFromEgg(Player var1, Mob var2) {
      ((Fox)â˜ƒ).addTrustedUUID(â˜ƒ.getUUID());
   }

   public boolean isPouncing() {
      return this.getFlag(16);
   }

   public void setIsPouncing(boolean var1) {
      this.setFlag(16, â˜ƒ);
   }

   public boolean isJumping() {
      return this.jumping;
   }

   public boolean isFullyCrouched() {
      return this.crouchAmount == 3.0F;
   }

   public void setIsCrouching(boolean var1) {
      this.setFlag(4, â˜ƒ);
   }

   @Override
   public boolean isCrouching() {
      return this.getFlag(4);
   }

   public void setIsInterested(boolean var1) {
      this.setFlag(8, â˜ƒ);
   }

   public boolean isInterested() {
      return this.getFlag(8);
   }

   public float getHeadRollAngle(float var1) {
      return Mth.lerp(â˜ƒ, this.interestedAngleO, this.interestedAngle) * 0.11F * (float) Math.PI;
   }

   public float getCrouchAmount(float var1) {
      return Mth.lerp(â˜ƒ, this.crouchAmountO, this.crouchAmount);
   }

   @Override
   public void setTarget(@Nullable LivingEntity var1) {
      if (this.isDefending() && â˜ƒ == null) {
         this.setDefending(false);
      }

      super.setTarget(â˜ƒ);
   }

   @Override
   protected int calculateFallDamage(float var1, float var2) {
      return Mth.ceil((â˜ƒ - 5.0F) * â˜ƒ);
   }

   void wakeUp() {
      this.setSleeping(false);
   }

   void clearStates() {
      this.setIsInterested(false);
      this.setIsCrouching(false);
      this.setSitting(false);
      this.setSleeping(false);
      this.setDefending(false);
      this.setFaceplanted(false);
   }

   boolean canMove() {
      return !this.isSleeping() && !this.isSitting() && !this.isFaceplanted();
   }

   @Override
   public void playAmbientSound() {
      SoundEvent â˜ƒ = this.getAmbientSound();
      if (â˜ƒ == SoundEvents.FOX_SCREECH) {
         this.playSound(â˜ƒ, 2.0F, this.getVoicePitch());
      } else {
         super.playAmbientSound();
      }
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.isSleeping()) {
         return SoundEvents.FOX_SLEEP;
      } else {
         if (!this.level.isDay() && this.random.nextFloat() < 0.1F) {
            List<Player> â˜ƒ = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0), EntitySelector.NO_SPECTATORS);
            if (â˜ƒ.isEmpty()) {
               return SoundEvents.FOX_SCREECH;
            }
         }

         return SoundEvents.FOX_AMBIENT;
      }
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.FOX_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.FOX_DEATH;
   }

   boolean trusts(UUID var1) {
      return this.getTrustedUUIDs().contains(â˜ƒ);
   }

   @Override
   protected void dropAllDeathLoot(DamageSource var1) {
      ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.MAINHAND);
      if (!â˜ƒ.isEmpty()) {
         this.spawnAtLocation(â˜ƒ);
         this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      }

      super.dropAllDeathLoot(â˜ƒ);
   }

   public static boolean isPathClear(Fox var0, LivingEntity var1) {
      double â˜ƒ = â˜ƒ.getZ() - â˜ƒ.getZ();
      double â˜ƒx = â˜ƒ.getX() - â˜ƒ.getX();
      double â˜ƒxx = â˜ƒ / â˜ƒx;
      int â˜ƒxxx = 6;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 6; ++â˜ƒxxxx) {
         double â˜ƒxxxxx = â˜ƒxx == 0.0 ? 0.0 : â˜ƒ * (double)((float)â˜ƒxxxx / 6.0F);
         double â˜ƒxxxxxx = â˜ƒxx == 0.0 ? â˜ƒx * (double)((float)â˜ƒxxxx / 6.0F) : â˜ƒxxxxx / â˜ƒxx;

         for(int â˜ƒxxxxxxx = 1; â˜ƒxxxxxxx < 4; ++â˜ƒxxxxxxx) {
            if (!â˜ƒ.level
               .getBlockState(new BlockPos(â˜ƒ.getX() + â˜ƒxxxxxx, â˜ƒ.getY() + (double)â˜ƒxxxxxxx, â˜ƒ.getZ() + â˜ƒxxxxx))
               .getMaterial()
               .isReplaceable()) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public Vec3 getLeashOffset() {
      return new Vec3(0.0, (double)(0.55F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
   }

   class DefendTrustedTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
      @Nullable
      private LivingEntity trustedLastHurtBy;
      private LivingEntity trustedLastHurt;
      private int timestamp;

      public DefendTrustedTargetGoal(Class<LivingEntity> var2, boolean var3, boolean var4, @Nullable Predicate<LivingEntity> var5) {
         super(Fox.this, â˜ƒ, 10, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean canUse() {
         if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
         } else {
            for(UUID â˜ƒ : Fox.this.getTrustedUUIDs()) {
               if (â˜ƒ != null && Fox.this.level instanceof ServerLevel) {
                  Entity â˜ƒxx = ((ServerLevel)Fox.this.level).getEntity(â˜ƒ);
                  if (â˜ƒxx instanceof LivingEntity â˜ƒx) {
                     this.trustedLastHurt = â˜ƒx;
                     this.trustedLastHurtBy = â˜ƒx.getLastHurtByMob();
                     int â˜ƒxxx = â˜ƒx.getLastHurtByMobTimestamp();
                     return â˜ƒxxx != this.timestamp && this.canAttack(this.trustedLastHurtBy, this.targetConditions);
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void start() {
         this.setTarget(this.trustedLastHurtBy);
         this.target = this.trustedLastHurtBy;
         if (this.trustedLastHurt != null) {
            this.timestamp = this.trustedLastHurt.getLastHurtByMobTimestamp();
         }

         Fox.this.playSound(SoundEvents.FOX_AGGRO, 1.0F, 1.0F);
         Fox.this.setDefending(true);
         Fox.this.wakeUp();
         super.start();
      }
   }

   class FaceplantGoal extends Goal {
      int countdown;

      public FaceplantGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         return Fox.this.isFaceplanted();
      }

      @Override
      public boolean canContinueToUse() {
         return this.canUse() && this.countdown > 0;
      }

      @Override
      public void start() {
         this.countdown = 40;
      }

      @Override
      public void stop() {
         Fox.this.setFaceplanted(false);
      }

      @Override
      public void tick() {
         --this.countdown;
      }
   }

   public class FoxAlertableEntitiesSelector implements Predicate<LivingEntity> {
      public boolean test(LivingEntity var1) {
         if (â˜ƒ instanceof Fox) {
            return false;
         } else if (â˜ƒ instanceof Chicken || â˜ƒ instanceof Rabbit || â˜ƒ instanceof Monster) {
            return true;
         } else if (â˜ƒ instanceof TamableAnimal) {
            return !((TamableAnimal)â˜ƒ).isTame();
         } else if (!(â˜ƒ instanceof Player) || !â˜ƒ.isSpectator() && !((Player)â˜ƒ).isCreative()) {
            if (Fox.this.trusts(â˜ƒ.getUUID())) {
               return false;
            } else {
               return !â˜ƒ.isSleeping() && !â˜ƒ.isDiscrete();
            }
         } else {
            return false;
         }
      }
   }

   abstract class FoxBehaviorGoal extends Goal {
      private final TargetingConditions alertableTargeting = TargetingConditions.forCombat()
         .range(12.0)
         .ignoreLineOfSight()
         .selector(Fox.this.new FoxAlertableEntitiesSelector());

      protected boolean hasShelter() {
         BlockPos â˜ƒ = new BlockPos(Fox.this.getX(), Fox.this.getBoundingBox().maxY, Fox.this.getZ());
         return !Fox.this.level.canSeeSky(â˜ƒ) && Fox.this.getWalkTargetValue(â˜ƒ) >= 0.0F;
      }

      protected boolean alertable() {
         return !Fox.this.level
            .getNearbyEntities(LivingEntity.class, this.alertableTargeting, Fox.this, Fox.this.getBoundingBox().inflate(12.0, 6.0, 12.0))
            .isEmpty();
      }
   }

   class FoxBreedGoal extends BreedGoal {
      public FoxBreedGoal(double var2) {
         super(Fox.this, â˜ƒ);
      }

      @Override
      public void start() {
         ((Fox)this.animal).clearStates();
         ((Fox)this.partner).clearStates();
         super.start();
      }

      @Override
      protected void breed() {
         ServerLevel â˜ƒ = (ServerLevel)this.level;
         Fox â˜ƒx = (Fox)this.animal.getBreedOffspring(â˜ƒ, this.partner);
         if (â˜ƒx != null) {
            ServerPlayer â˜ƒxx = this.animal.getLoveCause();
            ServerPlayer â˜ƒxxx = this.partner.getLoveCause();
            ServerPlayer â˜ƒxxxx = â˜ƒxx;
            if (â˜ƒxx != null) {
               â˜ƒx.addTrustedUUID(â˜ƒxx.getUUID());
            } else {
               â˜ƒxxxx = â˜ƒxxx;
            }

            if (â˜ƒxxx != null && â˜ƒxx != â˜ƒxxx) {
               â˜ƒx.addTrustedUUID(â˜ƒxxx.getUUID());
            }

            if (â˜ƒxxxx != null) {
               â˜ƒxxxx.awardStat(Stats.ANIMALS_BRED);
               CriteriaTriggers.BRED_ANIMALS.trigger(â˜ƒxxxx, this.animal, this.partner, â˜ƒx);
            }

            this.animal.setAge(6000);
            this.partner.setAge(6000);
            this.animal.resetLove();
            this.partner.resetLove();
            â˜ƒx.setAge(-24000);
            â˜ƒx.moveTo(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒx);
            this.level.broadcastEntityEvent(this.animal, (byte)18);
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
               this.level
                  .addFreshEntity(
                     new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1)
                  );
            }
         }
      }
   }

   public class FoxEatBerriesGoal extends MoveToBlockGoal {
      private static final int WAIT_TICKS = 40;
      protected int ticksWaited;

      public FoxEatBerriesGoal(double var2, int var4, int var5) {
         super(Fox.this, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public double acceptedDistance() {
         return 2.0;
      }

      @Override
      public boolean shouldRecalculatePath() {
         return this.tryTicks % 100 == 0;
      }

      @Override
      protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         return â˜ƒ.is(Blocks.SWEET_BERRY_BUSH) && â˜ƒ.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(â˜ƒ);
      }

      @Override
      public void tick() {
         if (this.isReachedTarget()) {
            if (this.ticksWaited >= 40) {
               this.onReachedTarget();
            } else {
               ++this.ticksWaited;
            }
         } else if (!this.isReachedTarget() && Fox.this.random.nextFloat() < 0.05F) {
            Fox.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
         }

         super.tick();
      }

      protected void onReachedTarget() {
         if (Fox.this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            BlockState â˜ƒ = Fox.this.level.getBlockState(this.blockPos);
            if (â˜ƒ.is(Blocks.SWEET_BERRY_BUSH)) {
               this.pickSweetBerries(â˜ƒ);
            } else if (CaveVines.hasGlowBerries(â˜ƒ)) {
               this.pickGlowBerry(â˜ƒ);
            }
         }
      }

      private void pickGlowBerry(BlockState var1) {
         CaveVines.use(â˜ƒ, Fox.this.level, this.blockPos);
      }

      private void pickSweetBerries(BlockState var1) {
         int â˜ƒ = â˜ƒ.getValue(SweetBerryBushBlock.AGE);
         â˜ƒ.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1));
         int â˜ƒx = 1 + Fox.this.level.random.nextInt(2) + (â˜ƒ == 3 ? 1 : 0);
         ItemStack â˜ƒxx = Fox.this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (â˜ƒxx.isEmpty()) {
            Fox.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
            --â˜ƒx;
         }

         if (â˜ƒx > 0) {
            Block.popResource(Fox.this.level, this.blockPos, new ItemStack(Items.SWEET_BERRIES, â˜ƒx));
         }

         Fox.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
         Fox.this.level.setBlock(this.blockPos, â˜ƒ.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1)), 2);
      }

      @Override
      public boolean canUse() {
         return !Fox.this.isSleeping() && super.canUse();
      }

      @Override
      public void start() {
         this.ticksWaited = 0;
         Fox.this.setSitting(false);
         super.start();
      }
   }

   class FoxFloatGoal extends FloatGoal {
      public FoxFloatGoal() {
         super(Fox.this);
      }

      @Override
      public void start() {
         super.start();
         Fox.this.clearStates();
      }

      @Override
      public boolean canUse() {
         return Fox.this.isInWater() && Fox.this.getFluidHeight(FluidTags.WATER) > 0.25 || Fox.this.isInLava();
      }
   }

   class FoxFollowParentGoal extends FollowParentGoal {
      private final Fox fox;

      public FoxFollowParentGoal(Fox var2, double var3) {
         super(â˜ƒ, â˜ƒ);
         this.fox = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return !this.fox.isDefending() && super.canUse();
      }

      @Override
      public boolean canContinueToUse() {
         return !this.fox.isDefending() && super.canContinueToUse();
      }

      @Override
      public void start() {
         this.fox.clearStates();
         super.start();
      }
   }

   public static class FoxGroupData extends AgeableMob.AgeableMobGroupData {
      public final Fox.Type type;

      public FoxGroupData(Fox.Type var1) {
         super(false);
         this.type = â˜ƒ;
      }
   }

   class FoxLookAtPlayerGoal extends LookAtPlayerGoal {
      public FoxLookAtPlayerGoal(Mob var2, Class<? extends LivingEntity> var3, float var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean canUse() {
         return super.canUse() && !Fox.this.isFaceplanted() && !Fox.this.isInterested();
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && !Fox.this.isFaceplanted() && !Fox.this.isInterested();
      }
   }

   public class FoxLookControl extends LookControl {
      public FoxLookControl() {
         super(Fox.this);
      }

      @Override
      public void tick() {
         if (!Fox.this.isSleeping()) {
            super.tick();
         }
      }

      @Override
      protected boolean resetXRotOnTick() {
         return !Fox.this.isPouncing() && !Fox.this.isCrouching() && !Fox.this.isInterested() && !Fox.this.isFaceplanted();
      }
   }

   class FoxMeleeAttackGoal extends MeleeAttackGoal {
      public FoxMeleeAttackGoal(double var2, boolean var4) {
         super(Fox.this, â˜ƒ, â˜ƒ);
      }

      @Override
      protected void checkAndPerformAttack(LivingEntity var1, double var2) {
         double â˜ƒ = this.getAttackReachSqr(â˜ƒ);
         if (â˜ƒ <= â˜ƒ && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(â˜ƒ);
            Fox.this.playSound(SoundEvents.FOX_BITE, 1.0F, 1.0F);
         }
      }

      @Override
      public void start() {
         Fox.this.setIsInterested(false);
         super.start();
      }

      @Override
      public boolean canUse() {
         return !Fox.this.isSitting() && !Fox.this.isSleeping() && !Fox.this.isCrouching() && !Fox.this.isFaceplanted() && super.canUse();
      }
   }

   class FoxMoveControl extends MoveControl {
      public FoxMoveControl() {
         super(Fox.this);
      }

      @Override
      public void tick() {
         if (Fox.this.canMove()) {
            super.tick();
         }
      }
   }

   class FoxPanicGoal extends PanicGoal {
      public FoxPanicGoal(double var2) {
         super(Fox.this, â˜ƒ);
      }

      @Override
      public boolean canUse() {
         return !Fox.this.isDefending() && super.canUse();
      }
   }

   public class FoxPounceGoal extends JumpGoal {
      @Override
      public boolean canUse() {
         if (!Fox.this.isFullyCrouched()) {
            return false;
         } else {
            LivingEntity â˜ƒ = Fox.this.getTarget();
            if (â˜ƒ != null && â˜ƒ.isAlive()) {
               if (â˜ƒ.getMotionDirection() != â˜ƒ.getDirection()) {
                  return false;
               } else {
                  boolean â˜ƒx = Fox.isPathClear(Fox.this, â˜ƒ);
                  if (!â˜ƒx) {
                     Fox.this.getNavigation().createPath(â˜ƒ, 0);
                     Fox.this.setIsCrouching(false);
                     Fox.this.setIsInterested(false);
                  }

                  return â˜ƒx;
               }
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean canContinueToUse() {
         LivingEntity â˜ƒ = Fox.this.getTarget();
         if (â˜ƒ != null && â˜ƒ.isAlive()) {
            double â˜ƒx = Fox.this.getDeltaMovement().y;
            return (!(â˜ƒx * â˜ƒx < 0.05F) || !(Math.abs(Fox.this.getXRot()) < 15.0F) || !Fox.this.onGround) && !Fox.this.isFaceplanted();
         } else {
            return false;
         }
      }

      @Override
      public boolean isInterruptable() {
         return false;
      }

      @Override
      public void start() {
         Fox.this.setJumping(true);
         Fox.this.setIsPouncing(true);
         Fox.this.setIsInterested(false);
         LivingEntity â˜ƒ = Fox.this.getTarget();
         Fox.this.getLookControl().setLookAt(â˜ƒ, 60.0F, 30.0F);
         Vec3 â˜ƒx = new Vec3(â˜ƒ.getX() - Fox.this.getX(), â˜ƒ.getY() - Fox.this.getY(), â˜ƒ.getZ() - Fox.this.getZ()).normalize();
         Fox.this.setDeltaMovement(Fox.this.getDeltaMovement().add(â˜ƒx.x * 0.8, 0.9, â˜ƒx.z * 0.8));
         Fox.this.getNavigation().stop();
      }

      @Override
      public void stop() {
         Fox.this.setIsCrouching(false);
         Fox.this.crouchAmount = 0.0F;
         Fox.this.crouchAmountO = 0.0F;
         Fox.this.setIsInterested(false);
         Fox.this.setIsPouncing(false);
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = Fox.this.getTarget();
         if (â˜ƒ != null) {
            Fox.this.getLookControl().setLookAt(â˜ƒ, 60.0F, 30.0F);
         }

         if (!Fox.this.isFaceplanted()) {
            Vec3 â˜ƒ = Fox.this.getDeltaMovement();
            if (â˜ƒ.y * â˜ƒ.y < 0.03F && Fox.this.getXRot() != 0.0F) {
               Fox.this.setXRot(Mth.rotlerp(Fox.this.getXRot(), 0.0F, 0.2F));
            } else {
               double â˜ƒ = â˜ƒ.horizontalDistance();
               double â˜ƒx = Math.signum(-â˜ƒ.y) * Math.acos(â˜ƒ / â˜ƒ.length()) * 180.0F / (float)Math.PI;
               Fox.this.setXRot((float)â˜ƒx);
            }
         }

         if (â˜ƒ != null && Fox.this.distanceTo(â˜ƒ) <= 2.0F) {
            Fox.this.doHurtTarget(â˜ƒ);
         } else if (Fox.this.getXRot() > 0.0F
            && Fox.this.onGround
            && (float)Fox.this.getDeltaMovement().y != 0.0F
            && Fox.this.level.getBlockState(Fox.this.blockPosition()).is(Blocks.SNOW)) {
            Fox.this.setXRot(60.0F);
            Fox.this.setTarget(null);
            Fox.this.setFaceplanted(true);
         }
      }
   }

   class FoxSearchForItemsGoal extends Goal {
      public FoxSearchForItemsGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public boolean canUse() {
         if (!Fox.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
         } else if (Fox.this.getTarget() != null || Fox.this.getLastHurtByMob() != null) {
            return false;
         } else if (!Fox.this.canMove()) {
            return false;
         } else if (Fox.this.getRandom().nextInt(10) != 0) {
            return false;
         } else {
            List<ItemEntity> â˜ƒ = Fox.this.level.getEntitiesOfClass(ItemEntity.class, Fox.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Fox.ALLOWED_ITEMS);
            return !â˜ƒ.isEmpty() && Fox.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
         }
      }

      @Override
      public void tick() {
         List<ItemEntity> â˜ƒ = Fox.this.level.getEntitiesOfClass(ItemEntity.class, Fox.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Fox.ALLOWED_ITEMS);
         ItemStack â˜ƒx = Fox.this.getItemBySlot(EquipmentSlot.MAINHAND);
         if (â˜ƒx.isEmpty() && !â˜ƒ.isEmpty()) {
            Fox.this.getNavigation().moveTo((Entity)â˜ƒ.get(0), 1.2F);
         }
      }

      @Override
      public void start() {
         List<ItemEntity> â˜ƒ = Fox.this.level.getEntitiesOfClass(ItemEntity.class, Fox.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Fox.ALLOWED_ITEMS);
         if (!â˜ƒ.isEmpty()) {
            Fox.this.getNavigation().moveTo((Entity)â˜ƒ.get(0), 1.2F);
         }
      }
   }

   class FoxStrollThroughVillageGoal extends StrollThroughVillageGoal {
      public FoxStrollThroughVillageGoal(int var2, int var3) {
         super(Fox.this, â˜ƒ);
      }

      @Override
      public void start() {
         Fox.this.clearStates();
         super.start();
      }

      @Override
      public boolean canUse() {
         return super.canUse() && this.canFoxMove();
      }

      @Override
      public boolean canContinueToUse() {
         return super.canContinueToUse() && this.canFoxMove();
      }

      private boolean canFoxMove() {
         return !Fox.this.isSleeping() && !Fox.this.isSitting() && !Fox.this.isDefending() && Fox.this.getTarget() == null;
      }
   }

   class PerchAndSearchGoal extends Fox.FoxBehaviorGoal {
      private double relX;
      private double relZ;
      private int lookTime;
      private int looksRemaining;

      public PerchAndSearchGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         return Fox.this.getLastHurtByMob() == null
            && Fox.this.getRandom().nextFloat() < 0.02F
            && !Fox.this.isSleeping()
            && Fox.this.getTarget() == null
            && Fox.this.getNavigation().isDone()
            && !this.alertable()
            && !Fox.this.isPouncing()
            && !Fox.this.isCrouching();
      }

      @Override
      public boolean canContinueToUse() {
         return this.looksRemaining > 0;
      }

      @Override
      public void start() {
         this.resetLook();
         this.looksRemaining = 2 + Fox.this.getRandom().nextInt(3);
         Fox.this.setSitting(true);
         Fox.this.getNavigation().stop();
      }

      @Override
      public void stop() {
         Fox.this.setSitting(false);
      }

      @Override
      public void tick() {
         --this.lookTime;
         if (this.lookTime <= 0) {
            --this.looksRemaining;
            this.resetLook();
         }

         Fox.this.getLookControl()
            .setLookAt(
               Fox.this.getX() + this.relX, Fox.this.getEyeY(), Fox.this.getZ() + this.relZ, (float)Fox.this.getMaxHeadYRot(), (float)Fox.this.getMaxHeadXRot()
            );
      }

      private void resetLook() {
         double â˜ƒ = (Math.PI * 2) * Fox.this.getRandom().nextDouble();
         this.relX = Math.cos(â˜ƒ);
         this.relZ = Math.sin(â˜ƒ);
         this.lookTime = 80 + Fox.this.getRandom().nextInt(20);
      }
   }

   class SeekShelterGoal extends FleeSunGoal {
      private int interval = 100;

      public SeekShelterGoal(double var2) {
         super(Fox.this, â˜ƒ);
      }

      @Override
      public boolean canUse() {
         if (Fox.this.isSleeping() || this.mob.getTarget() != null) {
            return false;
         } else if (Fox.this.level.isThundering()) {
            return true;
         } else if (this.interval > 0) {
            --this.interval;
            return false;
         } else {
            this.interval = 100;
            BlockPos â˜ƒ = this.mob.blockPosition();
            return Fox.this.level.isDay() && Fox.this.level.canSeeSky(â˜ƒ) && !((ServerLevel)Fox.this.level).isVillage(â˜ƒ) && this.setWantedPos();
         }
      }

      @Override
      public void start() {
         Fox.this.clearStates();
         super.start();
      }
   }

   class SleepGoal extends Fox.FoxBehaviorGoal {
      private static final int WAIT_TIME_BEFORE_SLEEP = 140;
      private int countdown = Fox.this.random.nextInt(140);

      public SleepGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         if (Fox.this.xxa == 0.0F && Fox.this.yya == 0.0F && Fox.this.zza == 0.0F) {
            return this.canSleep() || Fox.this.isSleeping();
         } else {
            return false;
         }
      }

      @Override
      public boolean canContinueToUse() {
         return this.canSleep();
      }

      private boolean canSleep() {
         if (this.countdown > 0) {
            --this.countdown;
            return false;
         } else {
            return Fox.this.level.isDay() && this.hasShelter() && !this.alertable() && !Fox.this.isInPowderSnow;
         }
      }

      @Override
      public void stop() {
         this.countdown = Fox.this.random.nextInt(140);
         Fox.this.clearStates();
      }

      @Override
      public void start() {
         Fox.this.setSitting(false);
         Fox.this.setIsCrouching(false);
         Fox.this.setIsInterested(false);
         Fox.this.setJumping(false);
         Fox.this.setSleeping(true);
         Fox.this.getNavigation().stop();
         Fox.this.getMoveControl().setWantedPosition(Fox.this.getX(), Fox.this.getY(), Fox.this.getZ(), 0.0);
      }
   }

   class StalkPreyGoal extends Goal {
      public StalkPreyGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         if (Fox.this.isSleeping()) {
            return false;
         } else {
            LivingEntity â˜ƒ = Fox.this.getTarget();
            return â˜ƒ != null
               && â˜ƒ.isAlive()
               && Fox.STALKABLE_PREY.test(â˜ƒ)
               && Fox.this.distanceToSqr(â˜ƒ) > 36.0
               && !Fox.this.isCrouching()
               && !Fox.this.isInterested()
               && !Fox.this.jumping;
         }
      }

      @Override
      public void start() {
         Fox.this.setSitting(false);
         Fox.this.setFaceplanted(false);
      }

      @Override
      public void stop() {
         LivingEntity â˜ƒ = Fox.this.getTarget();
         if (â˜ƒ != null && Fox.isPathClear(Fox.this, â˜ƒ)) {
            Fox.this.setIsInterested(true);
            Fox.this.setIsCrouching(true);
            Fox.this.getNavigation().stop();
            Fox.this.getLookControl().setLookAt(â˜ƒ, (float)Fox.this.getMaxHeadYRot(), (float)Fox.this.getMaxHeadXRot());
         } else {
            Fox.this.setIsInterested(false);
            Fox.this.setIsCrouching(false);
         }
      }

      @Override
      public void tick() {
         LivingEntity â˜ƒ = Fox.this.getTarget();
         Fox.this.getLookControl().setLookAt(â˜ƒ, (float)Fox.this.getMaxHeadYRot(), (float)Fox.this.getMaxHeadXRot());
         if (Fox.this.distanceToSqr(â˜ƒ) <= 36.0) {
            Fox.this.setIsInterested(true);
            Fox.this.setIsCrouching(true);
            Fox.this.getNavigation().stop();
         } else {
            Fox.this.getNavigation().moveTo(â˜ƒ, 1.5);
         }
      }
   }

   public static enum Type {
      RED(
         0,
         "red",
         Biomes.TAIGA,
         Biomes.TAIGA_HILLS,
         Biomes.TAIGA_MOUNTAINS,
         Biomes.GIANT_TREE_TAIGA,
         Biomes.GIANT_SPRUCE_TAIGA,
         Biomes.GIANT_TREE_TAIGA_HILLS,
         Biomes.GIANT_SPRUCE_TAIGA_HILLS
      ),
      SNOW(1, "snow", Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS);

      private static final Fox.Type[] BY_ID = (Fox.Type[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(Fox.Type::getId))
         .toArray(var0 -> new Fox.Type[var0]);
      private static final Map<String, Fox.Type> BY_NAME = (Map<String, Fox.Type>)Arrays.stream(values())
         .collect(Collectors.toMap(Fox.Type::getName, var0 -> var0));
      private final int id;
      private final String name;
      private final List<ResourceKey<Biome>> biomes;

      private Type(int var3, String var4, ResourceKey<Biome>... var5) {
         this.id = â˜ƒ;
         this.name = â˜ƒ;
         this.biomes = Arrays.asList(â˜ƒ);
      }

      public String getName() {
         return this.name;
      }

      public int getId() {
         return this.id;
      }

      public static Fox.Type byName(String var0) {
         return (Fox.Type)BY_NAME.getOrDefault(â˜ƒ, RED);
      }

      public static Fox.Type byId(int var0) {
         if (â˜ƒ < 0 || â˜ƒ > BY_ID.length) {
            â˜ƒ = 0;
         }

         return BY_ID[â˜ƒ];
      }

      public static Fox.Type byBiome(Optional<ResourceKey<Biome>> var0) {
         return â˜ƒ.isPresent() && SNOW.biomes.contains(â˜ƒ.get()) ? SNOW : RED;
      }
   }
}
