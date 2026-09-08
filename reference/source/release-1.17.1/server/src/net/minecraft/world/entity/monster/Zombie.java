package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Zombie extends Monster {
   private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(
      SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.5, AttributeModifier.Operation.MULTIPLY_BASE
   );
   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> DATA_SPECIAL_TYPE_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_DROWNED_CONVERSION_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
   public static final float ZOMBIE_LEADER_CHANCE = 0.05F;
   public static final int REINFORCEMENT_ATTEMPTS = 50;
   public static final int REINFORCEMENT_RANGE_MAX = 40;
   public static final int REINFORCEMENT_RANGE_MIN = 7;
   private static final float BREAK_DOOR_CHANCE = 0.1F;
   private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = var0 -> var0 == Difficulty.HARD;
   private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
   private boolean canBreakDoors;
   private int inWaterTime;
   private int conversionTime;

   public Zombie(EntityType<? extends Zombie> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Zombie(Level var1) {
      this(EntityType.ZOMBIE, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(4, new Zombie.ZombieAttackTurtleEggGoal(this, 1.0, 3));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.addBehaviourGoals();
   }

   protected void addBehaviourGoals() {
      this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin.class));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 35.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23F)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.ARMOR, 2.0)
         .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.getEntityData().define(DATA_BABY_ID, false);
      this.getEntityData().define(DATA_SPECIAL_TYPE_ID, 0);
      this.getEntityData().define(DATA_DROWNED_CONVERSION_ID, false);
   }

   public boolean isUnderWaterConverting() {
      return this.getEntityData().get(DATA_DROWNED_CONVERSION_ID);
   }

   public boolean canBreakDoors() {
      return this.canBreakDoors;
   }

   public void setCanBreakDoors(boolean var1) {
      if (this.supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation(this)) {
         if (this.canBreakDoors != â˜ƒ) {
            this.canBreakDoors = â˜ƒ;
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(â˜ƒ);
            if (â˜ƒ) {
               this.goalSelector.addGoal(1, this.breakDoorGoal);
            } else {
               this.goalSelector.removeGoal(this.breakDoorGoal);
            }
         }
      } else if (this.canBreakDoors) {
         this.goalSelector.removeGoal(this.breakDoorGoal);
         this.canBreakDoors = false;
      }
   }

   protected boolean supportsBreakDoorGoal() {
      return true;
   }

   @Override
   public boolean isBaby() {
      return this.getEntityData().get(DATA_BABY_ID);
   }

   @Override
   protected int getExperienceReward(Player var1) {
      if (this.isBaby()) {
         this.xpReward = (int)((float)this.xpReward * 2.5F);
      }

      return super.getExperienceReward(â˜ƒ);
   }

   @Override
   public void setBaby(boolean var1) {
      this.getEntityData().set(DATA_BABY_ID, â˜ƒ);
      if (this.level != null && !this.level.isClientSide) {
         AttributeInstance â˜ƒ = this.getAttribute(Attributes.MOVEMENT_SPEED);
         â˜ƒ.removeModifier(SPEED_MODIFIER_BABY);
         if (â˜ƒ) {
            â˜ƒ.addTransientModifier(SPEED_MODIFIER_BABY);
         }
      }
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_BABY_ID.equals(â˜ƒ)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   protected boolean convertsInWater() {
      return true;
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
         if (this.isUnderWaterConverting()) {
            --this.conversionTime;
            if (this.conversionTime < 0) {
               this.doUnderWaterConversion();
            }
         } else if (this.convertsInWater()) {
            if (this.isEyeInFluid(FluidTags.WATER)) {
               ++this.inWaterTime;
               if (this.inWaterTime >= 600) {
                  this.startUnderWaterConversion(300);
               }
            } else {
               this.inWaterTime = -1;
            }
         }
      }

      super.tick();
   }

   @Override
   public void aiStep() {
      if (this.isAlive()) {
         boolean â˜ƒ = this.isSunSensitive() && this.isSunBurnTick();
         if (â˜ƒ) {
            ItemStack â˜ƒx = this.getItemBySlot(EquipmentSlot.HEAD);
            if (!â˜ƒx.isEmpty()) {
               if (â˜ƒx.isDamageableItem()) {
                  â˜ƒx.setDamageValue(â˜ƒx.getDamageValue() + this.random.nextInt(2));
                  if (â˜ƒx.getDamageValue() >= â˜ƒx.getMaxDamage()) {
                     this.broadcastBreakEvent(EquipmentSlot.HEAD);
                     this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                  }
               }

               â˜ƒ = false;
            }

            if (â˜ƒ) {
               this.setSecondsOnFire(8);
            }
         }
      }

      super.aiStep();
   }

   private void startUnderWaterConversion(int var1) {
      this.conversionTime = â˜ƒ;
      this.getEntityData().set(DATA_DROWNED_CONVERSION_ID, true);
   }

   protected void doUnderWaterConversion() {
      this.convertToZombieType(EntityType.DROWNED);
      if (!this.isSilent()) {
         this.level.levelEvent(null, 1040, this.blockPosition(), 0);
      }
   }

   protected void convertToZombieType(EntityType<? extends Zombie> var1) {
      Zombie â˜ƒ = this.convertTo(â˜ƒ, true);
      if (â˜ƒ != null) {
         â˜ƒ.handleAttributes(â˜ƒ.level.getCurrentDifficultyAt(â˜ƒ.blockPosition()).getSpecialMultiplier());
         â˜ƒ.setCanBreakDoors(â˜ƒ.supportsBreakDoorGoal() && this.canBreakDoors());
      }
   }

   protected boolean isSunSensitive() {
      return true;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (!super.hurt(â˜ƒ, â˜ƒ)) {
         return false;
      } else if (!(this.level instanceof ServerLevel)) {
         return false;
      } else {
         ServerLevel â˜ƒ = (ServerLevel)this.level;
         LivingEntity â˜ƒx = this.getTarget();
         if (â˜ƒx == null && â˜ƒ.getEntity() instanceof LivingEntity) {
            â˜ƒx = (LivingEntity)â˜ƒ.getEntity();
         }

         if (â˜ƒx != null
            && this.level.getDifficulty() == Difficulty.HARD
            && (double)this.random.nextFloat() < this.getAttributeValue(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
            && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            int â˜ƒ = Mth.floor(this.getX());
            int â˜ƒx = Mth.floor(this.getY());
            int â˜ƒxx = Mth.floor(this.getZ());
            Zombie â˜ƒxxx = new Zombie(this.level);

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 50; ++â˜ƒxxxx) {
               int â˜ƒxxxxx = â˜ƒ + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
               int â˜ƒxxxxxx = â˜ƒx + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
               int â˜ƒxxxxxxx = â˜ƒxx + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
               BlockPos â˜ƒxxxxxxxx = new BlockPos(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
               EntityType<?> â˜ƒxxxxxxxxx = â˜ƒxxx.getType();
               SpawnPlacements.Type â˜ƒxxxxxxxxxx = SpawnPlacements.getPlacementType(â˜ƒxxxxxxxxx);
               if (NaturalSpawner.isSpawnPositionOk(â˜ƒxxxxxxxxxx, this.level, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx)
                  && SpawnPlacements.checkSpawnRules(â˜ƒxxxxxxxxx, â˜ƒ, MobSpawnType.REINFORCEMENT, â˜ƒxxxxxxxx, this.level.random)) {
                  â˜ƒxxx.setPos((double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx);
                  if (!this.level.hasNearbyAlivePlayer((double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx, 7.0)
                     && this.level.isUnobstructed(â˜ƒxxx)
                     && this.level.noCollision(â˜ƒxxx)
                     && !this.level.containsAnyLiquid(â˜ƒxxx.getBoundingBox())) {
                     â˜ƒxxx.setTarget(â˜ƒx);
                     â˜ƒxxx.finalizeSpawn(â˜ƒ, this.level.getCurrentDifficultyAt(â˜ƒxxx.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                     â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxx);
                     this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                        .addPermanentModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05F, AttributeModifier.Operation.ADDITION));
                     â˜ƒxxx.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                        .addPermanentModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05F, AttributeModifier.Operation.ADDITION));
                     break;
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      boolean â˜ƒ = super.doHurtTarget(â˜ƒ);
      if (â˜ƒ) {
         float â˜ƒx = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
         if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < â˜ƒx * 0.3F) {
            â˜ƒ.setSecondsOnFire(2 * (int)â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ZOMBIE_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.ZOMBIE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ZOMBIE_DEATH;
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.ZOMBIE_STEP;
   }

   @Override
   protected void playStepSound(BlockPos var1, BlockState var2) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   @Override
   public MobType getMobType() {
      return MobType.UNDEAD;
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      super.populateDefaultEquipmentSlots(â˜ƒ);
      if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
         int â˜ƒ = this.random.nextInt(3);
         if (â˜ƒ == 0) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
         } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
         }
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("IsBaby", this.isBaby());
      â˜ƒ.putBoolean("CanBreakDoors", this.canBreakDoors());
      â˜ƒ.putInt("InWaterTime", this.isInWater() ? this.inWaterTime : -1);
      â˜ƒ.putInt("DrownedConversionTime", this.isUnderWaterConverting() ? this.conversionTime : -1);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setBaby(â˜ƒ.getBoolean("IsBaby"));
      this.setCanBreakDoors(â˜ƒ.getBoolean("CanBreakDoors"));
      this.inWaterTime = â˜ƒ.getInt("InWaterTime");
      if (â˜ƒ.contains("DrownedConversionTime", 99) && â˜ƒ.getInt("DrownedConversionTime") > -1) {
         this.startUnderWaterConversion(â˜ƒ.getInt("DrownedConversionTime"));
      }
   }

   @Override
   public void killed(ServerLevel var1, LivingEntity var2) {
      super.killed(â˜ƒ, â˜ƒ);
      if ((â˜ƒ.getDifficulty() == Difficulty.NORMAL || â˜ƒ.getDifficulty() == Difficulty.HARD) && â˜ƒ instanceof Villager) {
         if (â˜ƒ.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
            return;
         }

         Villager â˜ƒ = (Villager)â˜ƒ;
         ZombieVillager â˜ƒx = â˜ƒ.convertTo(EntityType.ZOMBIE_VILLAGER, false);
         â˜ƒx.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒx.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), null);
         â˜ƒx.setVillagerData(â˜ƒ.getVillagerData());
         â˜ƒx.setGossips(â˜ƒ.getGossips().store(NbtOps.INSTANCE).getValue());
         â˜ƒx.setTradeOffers(â˜ƒ.getOffers().createTag());
         â˜ƒx.setVillagerXp(â˜ƒ.getVillagerXp());
         if (!this.isSilent()) {
            â˜ƒ.levelEvent(null, 1026, this.blockPosition(), 0);
         }
      }
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return this.isBaby() ? 0.93F : 1.74F;
   }

   @Override
   public boolean canHoldItem(ItemStack var1) {
      return â˜ƒ.is(Items.EGG) && this.isBaby() && this.isPassenger() ? false : super.canHoldItem(â˜ƒ);
   }

   @Override
   public boolean wantsToPickUp(ItemStack var1) {
      return â˜ƒ.is(Items.GLOW_INK_SAC) ? false : super.wantsToPickUp(â˜ƒ);
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = â˜ƒ.getSpecialMultiplier();
      this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = new Zombie.ZombieGroupData(getSpawnAsBabyOdds(â˜ƒ.getRandom()), true);
      }

      if (â˜ƒ instanceof Zombie.ZombieGroupData â˜ƒ) {
         if (â˜ƒ.isBaby) {
            this.setBaby(true);
            if (â˜ƒ.canSpawnJockey) {
               if ((double)â˜ƒ.getRandom().nextFloat() < 0.05) {
                  List<Chicken> â˜ƒx = â˜ƒ.getEntitiesOfClass(
                     Chicken.class, this.getBoundingBox().inflate(5.0, 3.0, 5.0), EntitySelector.ENTITY_NOT_BEING_RIDDEN
                  );
                  if (!â˜ƒx.isEmpty()) {
                     Chicken â˜ƒxx = (Chicken)â˜ƒx.get(0);
                     â˜ƒxx.setChickenJockey(true);
                     this.startRiding(â˜ƒxx);
                  }
               } else if ((double)â˜ƒ.getRandom().nextFloat() < 0.05) {
                  Chicken â˜ƒx = EntityType.CHICKEN.create(this.level);
                  â˜ƒx.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                  â˜ƒx.finalizeSpawn(â˜ƒ, â˜ƒ, MobSpawnType.JOCKEY, null, null);
                  â˜ƒx.setChickenJockey(true);
                  this.startRiding(â˜ƒx);
                  â˜ƒ.addFreshEntity(â˜ƒx);
               }
            }
         }

         this.setCanBreakDoors(this.supportsBreakDoorGoal() && this.random.nextFloat() < â˜ƒ * 0.1F);
         this.populateDefaultEquipmentSlots(â˜ƒ);
         this.populateDefaultEquipmentEnchantments(â˜ƒ);
      }

      if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
         LocalDate â˜ƒ = LocalDate.now();
         int â˜ƒx = â˜ƒ.get(ChronoField.DAY_OF_MONTH);
         int â˜ƒxx = â˜ƒ.get(ChronoField.MONTH_OF_YEAR);
         if (â˜ƒxx == 10 && â˜ƒx == 31 && this.random.nextFloat() < 0.25F) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
            this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
         }
      }

      this.handleAttributes(â˜ƒ);
      return â˜ƒ;
   }

   public static boolean getSpawnAsBabyOdds(Random var0) {
      return â˜ƒ.nextFloat() < 0.05F;
   }

   protected void handleAttributes(float var1) {
      this.randomizeReinforcementsChance();
      this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)
         .addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05F, AttributeModifier.Operation.ADDITION));
      double â˜ƒ = this.random.nextDouble() * 1.5 * (double)â˜ƒ;
      if (â˜ƒ > 1.0) {
         this.getAttribute(Attributes.FOLLOW_RANGE)
            .addPermanentModifier(new AttributeModifier("Random zombie-spawn bonus", â˜ƒ, AttributeModifier.Operation.MULTIPLY_TOTAL));
      }

      if (this.random.nextFloat() < â˜ƒ * 0.05F) {
         this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
            .addPermanentModifier(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25 + 0.5, AttributeModifier.Operation.ADDITION));
         this.getAttribute(Attributes.MAX_HEALTH)
            .addPermanentModifier(
               new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0 + 1.0, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
         this.setCanBreakDoors(this.supportsBreakDoorGoal());
      }
   }

   protected void randomizeReinforcementsChance() {
      this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.random.nextDouble() * 0.1F);
   }

   @Override
   public double getMyRidingOffset() {
      return this.isBaby() ? 0.0 : -0.45;
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      Entity â˜ƒx = â˜ƒ.getEntity();
      if (â˜ƒx instanceof Creeper â˜ƒ && â˜ƒ.canDropMobsSkull()) {
         ItemStack â˜ƒxx = this.getSkull();
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.increaseDroppedSkulls();
            this.spawnAtLocation(â˜ƒxx);
         }
      }
   }

   protected ItemStack getSkull() {
      return new ItemStack(Items.ZOMBIE_HEAD);
   }

   class ZombieAttackTurtleEggGoal extends RemoveBlockGoal {
      ZombieAttackTurtleEggGoal(PathfinderMob var2, double var3, int var5) {
         super(Blocks.TURTLE_EGG, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public void playDestroyProgressSound(LevelAccessor var1, BlockPos var2) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + Zombie.this.random.nextFloat() * 0.2F);
      }

      @Override
      public void playBreakSound(Level var1, BlockPos var2) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + â˜ƒ.random.nextFloat() * 0.2F);
      }

      @Override
      public double acceptedDistance() {
         return 1.14;
      }
   }

   public static class ZombieGroupData implements SpawnGroupData {
      public final boolean isBaby;
      public final boolean canSpawnJockey;

      public ZombieGroupData(boolean var1, boolean var2) {
         this.isBaby = â˜ƒ;
         this.canSpawnJockey = â˜ƒ;
      }
   }
}
