package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Piglin extends AbstractPiglin implements CrossbowAttackMob, InventoryCarrier {
   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Piglin.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Piglin.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_IS_DANCING = SynchedEntityData.defineId(Piglin.class, EntityDataSerializers.BOOLEAN);
   private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
   private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(
      SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.2F, AttributeModifier.Operation.MULTIPLY_BASE
   );
   private static final int MAX_HEALTH = 16;
   private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.35F;
   private static final int ATTACK_DAMAGE = 5;
   private static final float CROSSBOW_POWER = 1.6F;
   private static final float CHANCE_OF_WEARING_EACH_ARMOUR_ITEM = 0.1F;
   private static final int MAX_PASSENGERS_ON_ONE_HOGLIN = 3;
   private static final float PROBABILITY_OF_SPAWNING_AS_BABY = 0.2F;
   private static final float BABY_EYE_HEIGHT_ADJUSTMENT = 0.81F;
   private static final double PROBABILITY_OF_SPAWNING_WITH_CROSSBOW_INSTEAD_OF_SWORD = 0.5;
   private final SimpleContainer inventory = new SimpleContainer(8);
   private boolean cannotHunt;
   protected static final ImmutableList<SensorType<? extends Sensor<? super Piglin>>> SENSOR_TYPES = ImmutableList.of(
      SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, SensorType.PIGLIN_SPECIFIC_SENSOR
   );
   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.DOORS_TO_CLOSE,
      MemoryModuleType.NEAREST_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
      MemoryModuleType.NEARBY_ADULT_PIGLINS,
      MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
      MemoryModuleType.HURT_BY,
      MemoryModuleType.HURT_BY_ENTITY,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      MemoryModuleType.ATTACK_TARGET,
      MemoryModuleType.ATTACK_COOLING_DOWN,
      MemoryModuleType.INTERACTION_TARGET,
      MemoryModuleType.PATH,
      MemoryModuleType.ANGRY_AT,
      MemoryModuleType.UNIVERSAL_ANGER,
      MemoryModuleType.AVOID_TARGET,
      MemoryModuleType.ADMIRING_ITEM,
      MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
      MemoryModuleType.ADMIRING_DISABLED,
      MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
      MemoryModuleType.CELEBRATE_LOCATION,
      MemoryModuleType.DANCING,
      MemoryModuleType.HUNTED_RECENTLY,
      MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN,
      MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
      MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED,
      MemoryModuleType.RIDE_TARGET,
      MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
      MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
      MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN,
      MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD,
      MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
      MemoryModuleType.ATE_RECENTLY,
      MemoryModuleType.NEAREST_REPELLENT
   );

   public Piglin(EntityType<? extends AbstractPiglin> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.xpReward = 5;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.isBaby()) {
         â˜ƒ.putBoolean("IsBaby", true);
      }

      if (this.cannotHunt) {
         â˜ƒ.putBoolean("CannotHunt", true);
      }

      â˜ƒ.put("Inventory", this.inventory.createTag());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setBaby(â˜ƒ.getBoolean("IsBaby"));
      this.setCannotHunt(â˜ƒ.getBoolean("CannotHunt"));
      this.inventory.fromTag(â˜ƒ.getList("Inventory", 10));
   }

   @VisibleForDebug
   @Override
   public Container getInventory() {
      return this.inventory;
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      this.inventory.removeAllItems().forEach(this::spawnAtLocation);
   }

   protected ItemStack addToInventory(ItemStack var1) {
      return this.inventory.addItem(â˜ƒ);
   }

   protected boolean canAddToInventory(ItemStack var1) {
      return this.inventory.canAddItem(â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_BABY_ID, false);
      this.entityData.define(DATA_IS_CHARGING_CROSSBOW, false);
      this.entityData.define(DATA_IS_DANCING, false);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      super.onSyncedDataUpdated(â˜ƒ);
      if (DATA_BABY_ID.equals(â˜ƒ)) {
         this.refreshDimensions();
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0).add(Attributes.MOVEMENT_SPEED, 0.35F).add(Attributes.ATTACK_DAMAGE, 5.0);
   }

   public static boolean checkPiglinSpawnRules(EntityType<Piglin> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return !â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.NETHER_WART_BLOCK);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (â˜ƒ != MobSpawnType.STRUCTURE) {
         if (â˜ƒ.getRandom().nextFloat() < 0.2F) {
            this.setBaby(true);
         } else if (this.isAdult()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon());
         }
      }

      PiglinAi.initMemories(this);
      this.populateDefaultEquipmentSlots(â˜ƒ);
      this.populateDefaultEquipmentEnchantments(â˜ƒ);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return !this.isPersistenceRequired();
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      if (this.isAdult()) {
         this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
         this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
         this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
         this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
      }
   }

   private void maybeWearArmor(EquipmentSlot var1, ItemStack var2) {
      if (this.level.random.nextFloat() < 0.1F) {
         this.setItemSlot(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected Brain.Provider<Piglin> brainProvider() {
      return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
   }

   @Override
   protected Brain<?> makeBrain(Dynamic<?> var1) {
      return PiglinAi.makeBrain(this, this.brainProvider().makeBrain(â˜ƒ));
   }

   @Override
   public Brain<Piglin> getBrain() {
      return super.getBrain();
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      InteractionResult â˜ƒ = super.mobInteract(â˜ƒ, â˜ƒ);
      if (â˜ƒ.consumesAction()) {
         return â˜ƒ;
      } else if (!this.level.isClientSide) {
         return PiglinAi.mobInteract(this, â˜ƒ, â˜ƒ);
      } else {
         boolean â˜ƒ = PiglinAi.canAdmire(this, â˜ƒ.getItemInHand(â˜ƒ)) && this.getArmPose() != PiglinArmPose.ADMIRING_ITEM;
         return â˜ƒ ? InteractionResult.SUCCESS : InteractionResult.PASS;
      }
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return this.isBaby() ? 0.93F : 1.74F;
   }

   @Override
   public double getPassengersRidingOffset() {
      return (double)this.getBbHeight() * 0.92;
   }

   @Override
   public void setBaby(boolean var1) {
      this.getEntityData().set(DATA_BABY_ID, â˜ƒ);
      if (!this.level.isClientSide) {
         AttributeInstance â˜ƒ = this.getAttribute(Attributes.MOVEMENT_SPEED);
         â˜ƒ.removeModifier(SPEED_MODIFIER_BABY);
         if (â˜ƒ) {
            â˜ƒ.addTransientModifier(SPEED_MODIFIER_BABY);
         }
      }
   }

   @Override
   public boolean isBaby() {
      return this.getEntityData().get(DATA_BABY_ID);
   }

   private void setCannotHunt(boolean var1) {
      this.cannotHunt = â˜ƒ;
   }

   @Override
   protected boolean canHunt() {
      return !this.cannotHunt;
   }

   @Override
   protected void customServerAiStep() {
      this.level.getProfiler().push("piglinBrain");
      this.getBrain().tick((ServerLevel)this.level, this);
      this.level.getProfiler().pop();
      PiglinAi.updateActivity(this);
      super.customServerAiStep();
   }

   @Override
   protected int getExperienceReward(Player var1) {
      return this.xpReward;
   }

   @Override
   protected void finishConversion(ServerLevel var1) {
      PiglinAi.cancelAdmiring(this);
      this.inventory.removeAllItems().forEach(this::spawnAtLocation);
      super.finishConversion(â˜ƒ);
   }

   private ItemStack createSpawnWeapon() {
      return (double)this.random.nextFloat() < 0.5 ? new ItemStack(Items.CROSSBOW) : new ItemStack(Items.GOLDEN_SWORD);
   }

   private boolean isChargingCrossbow() {
      return this.entityData.get(DATA_IS_CHARGING_CROSSBOW);
   }

   @Override
   public void setChargingCrossbow(boolean var1) {
      this.entityData.set(DATA_IS_CHARGING_CROSSBOW, â˜ƒ);
   }

   @Override
   public void onCrossbowAttackPerformed() {
      this.noActionTime = 0;
   }

   @Override
   public PiglinArmPose getArmPose() {
      if (this.isDancing()) {
         return PiglinArmPose.DANCING;
      } else if (PiglinAi.isLovedItem(this.getOffhandItem())) {
         return PiglinArmPose.ADMIRING_ITEM;
      } else if (this.isAggressive() && this.isHoldingMeleeWeapon()) {
         return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON;
      } else if (this.isChargingCrossbow()) {
         return PiglinArmPose.CROSSBOW_CHARGE;
      } else {
         return this.isAggressive() && this.isHolding(Items.CROSSBOW) ? PiglinArmPose.CROSSBOW_HOLD : PiglinArmPose.DEFAULT;
      }
   }

   public boolean isDancing() {
      return this.entityData.get(DATA_IS_DANCING);
   }

   public void setDancing(boolean var1) {
      this.entityData.set(DATA_IS_DANCING, â˜ƒ);
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      boolean â˜ƒ = super.hurt(â˜ƒ, â˜ƒ);
      if (this.level.isClientSide) {
         return false;
      } else {
         if (â˜ƒ && â˜ƒ.getEntity() instanceof LivingEntity) {
            PiglinAi.wasHurtBy(this, (LivingEntity)â˜ƒ.getEntity());
         }

         return â˜ƒ;
      }
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      this.performCrossbowAttack(this, 1.6F);
   }

   @Override
   public void shootCrossbowProjectile(LivingEntity var1, ItemStack var2, Projectile var3, float var4) {
      this.shootCrossbowProjectile(this, â˜ƒ, â˜ƒ, â˜ƒ, 1.6F);
   }

   @Override
   public boolean canFireProjectileWeapon(ProjectileWeaponItem var1) {
      return â˜ƒ == Items.CROSSBOW;
   }

   protected void holdInMainHand(ItemStack var1) {
      this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, â˜ƒ);
   }

   protected void holdInOffHand(ItemStack var1) {
      if (â˜ƒ.is(PiglinAi.BARTERING_ITEM)) {
         this.setItemSlot(EquipmentSlot.OFFHAND, â˜ƒ);
         this.setGuaranteedDrop(EquipmentSlot.OFFHAND);
      } else {
         this.setItemSlotAndDropWhenKilled(EquipmentSlot.OFFHAND, â˜ƒ);
      }
   }

   @Override
   public boolean wantsToPickUp(ItemStack var1) {
      return this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && this.canPickUpLoot() && PiglinAi.wantsToPickup(this, â˜ƒ);
   }

   protected boolean canReplaceCurrentItem(ItemStack var1) {
      EquipmentSlot â˜ƒ = Mob.getEquipmentSlotForItem(â˜ƒ);
      ItemStack â˜ƒx = this.getItemBySlot(â˜ƒ);
      return this.canReplaceCurrentItem(â˜ƒ, â˜ƒx);
   }

   @Override
   protected boolean canReplaceCurrentItem(ItemStack var1, ItemStack var2) {
      if (EnchantmentHelper.hasBindingCurse(â˜ƒ)) {
         return false;
      } else {
         boolean â˜ƒ = PiglinAi.isLovedItem(â˜ƒ) || â˜ƒ.is(Items.CROSSBOW);
         boolean â˜ƒx = PiglinAi.isLovedItem(â˜ƒ) || â˜ƒ.is(Items.CROSSBOW);
         if (â˜ƒ && !â˜ƒx) {
            return true;
         } else if (!â˜ƒ && â˜ƒx) {
            return false;
         } else {
            return this.isAdult() && !â˜ƒ.is(Items.CROSSBOW) && â˜ƒ.is(Items.CROSSBOW) ? false : super.canReplaceCurrentItem(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      this.onItemPickup(â˜ƒ);
      PiglinAi.pickUpItem(this, â˜ƒ);
   }

   @Override
   public boolean startRiding(Entity var1, boolean var2) {
      if (this.isBaby() && â˜ƒ.getType() == EntityType.HOGLIN) {
         â˜ƒ = this.getTopPassenger(â˜ƒ, 3);
      }

      return super.startRiding(â˜ƒ, â˜ƒ);
   }

   private Entity getTopPassenger(Entity var1, int var2) {
      List<Entity> â˜ƒ = â˜ƒ.getPassengers();
      return â˜ƒ != 1 && !â˜ƒ.isEmpty() ? this.getTopPassenger((Entity)â˜ƒ.get(0), â˜ƒ - 1) : â˜ƒ;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.level.isClientSide ? null : (SoundEvent)PiglinAi.getSoundForCurrentActivity(this).orElse(null);
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.PIGLIN_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PIGLIN_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(SoundEvents.PIGLIN_STEP, 0.15F, 1.0F);
   }

   protected void playSound(SoundEvent var1) {
      this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
   }

   @Override
   protected void playConvertedSound() {
      this.playSound(SoundEvents.PIGLIN_CONVERTED_TO_ZOMBIFIED);
   }
}
