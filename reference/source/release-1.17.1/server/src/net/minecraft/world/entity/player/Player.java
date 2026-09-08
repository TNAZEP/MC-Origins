package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public abstract class Player extends LivingEntity {
   public static final String UUID_PREFIX_OFFLINE_PLAYER = "OfflinePlayer:";
   public static final int MAX_NAME_LENGTH = 16;
   public static final int MAX_HEALTH = 20;
   public static final int SLEEP_DURATION = 100;
   public static final int WAKE_UP_DURATION = 10;
   public static final int ENDER_SLOT_OFFSET = 200;
   public static final float CROUCH_BB_HEIGHT = 1.5F;
   public static final float SWIMMING_BB_WIDTH = 0.6F;
   public static final float SWIMMING_BB_HEIGHT = 0.6F;
   public static final float DEFAULT_EYE_HEIGHT = 1.62F;
   public static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.scalable(0.6F, 1.8F);
   private static final Map<Pose, EntityDimensions> POSES = ImmutableMap.builder()
      .put(Pose.STANDING, STANDING_DIMENSIONS)
      .put(Pose.SLEEPING, SLEEPING_DIMENSIONS)
      .put(Pose.FALL_FLYING, EntityDimensions.scalable(0.6F, 0.6F))
      .put(Pose.SWIMMING, EntityDimensions.scalable(0.6F, 0.6F))
      .put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.6F, 0.6F))
      .put(Pose.CROUCHING, EntityDimensions.scalable(0.6F, 1.5F))
      .put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F))
      .build();
   private static final int FLY_ACHIEVEMENT_SPEED = 25;
   private static final EntityDataAccessor<Float> DATA_PLAYER_ABSORPTION_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DATA_SCORE_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Byte> DATA_PLAYER_MODE_CUSTOMISATION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BYTE);
   protected static final EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BYTE);
   protected static final EntityDataAccessor<CompoundTag> DATA_SHOULDER_LEFT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);
   protected static final EntityDataAccessor<CompoundTag> DATA_SHOULDER_RIGHT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);
   private long timeEntitySatOnShoulder;
   private final Inventory inventory = new Inventory(this);
   protected PlayerEnderChestContainer enderChestInventory = new PlayerEnderChestContainer();
   public final InventoryMenu inventoryMenu;
   public AbstractContainerMenu containerMenu;
   protected FoodData foodData = new FoodData();
   protected int jumpTriggerTime;
   public float oBob;
   public float bob;
   public int takeXpDelay;
   public double xCloakO;
   public double yCloakO;
   public double zCloakO;
   public double xCloak;
   public double yCloak;
   public double zCloak;
   private int sleepCounter;
   protected boolean wasUnderwater;
   private final Abilities abilities = new Abilities();
   public int experienceLevel;
   public int totalExperience;
   public float experienceProgress;
   protected int enchantmentSeed;
   protected final float defaultFlySpeed = 0.02F;
   private int lastLevelUpTime;
   private final GameProfile gameProfile;
   private boolean reducedDebugInfo;
   private ItemStack lastItemInMainHand = ItemStack.EMPTY;
   private final ItemCooldowns cooldowns = this.createItemCooldowns();
   @Nullable
   public FishingHook fishing;

   public Player(Level var1, BlockPos var2, float var3, GameProfile var4) {
      super(EntityType.PLAYER, â˜ƒ);
      this.setUUID(createPlayerUUID(â˜ƒ));
      this.gameProfile = â˜ƒ;
      this.inventoryMenu = new InventoryMenu(this.inventory, !â˜ƒ.isClientSide, this);
      this.containerMenu = this.inventoryMenu;
      this.moveTo((double)â˜ƒ.getX() + 0.5, (double)(â˜ƒ.getY() + 1), (double)â˜ƒ.getZ() + 0.5, â˜ƒ, 0.0F);
      this.rotOffs = 180.0F;
   }

   public boolean blockActionRestricted(Level var1, BlockPos var2, GameType var3) {
      if (!â˜ƒ.isBlockPlacingRestricted()) {
         return false;
      } else if (â˜ƒ == GameType.SPECTATOR) {
         return true;
      } else if (this.mayBuild()) {
         return false;
      } else {
         ItemStack â˜ƒ = this.getMainHandItem();
         return â˜ƒ.isEmpty() || !â˜ƒ.hasAdventureModeBreakTagForBlock(â˜ƒ.getTagManager(), new BlockInWorld(â˜ƒ, â˜ƒ, false));
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return LivingEntity.createLivingAttributes()
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.1F)
         .add(Attributes.ATTACK_SPEED)
         .add(Attributes.LUCK);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_PLAYER_ABSORPTION_ID, 0.0F);
      this.entityData.define(DATA_SCORE_ID, 0);
      this.entityData.define(DATA_PLAYER_MODE_CUSTOMISATION, (byte)0);
      this.entityData.define(DATA_PLAYER_MAIN_HAND, (byte)1);
      this.entityData.define(DATA_SHOULDER_LEFT, new CompoundTag());
      this.entityData.define(DATA_SHOULDER_RIGHT, new CompoundTag());
   }

   @Override
   public void tick() {
      this.noPhysics = this.isSpectator();
      if (this.isSpectator()) {
         this.onGround = false;
      }

      if (this.takeXpDelay > 0) {
         --this.takeXpDelay;
      }

      if (this.isSleeping()) {
         ++this.sleepCounter;
         if (this.sleepCounter > 100) {
            this.sleepCounter = 100;
         }

         if (!this.level.isClientSide && this.level.isDay()) {
            this.stopSleepInBed(false, true);
         }
      } else if (this.sleepCounter > 0) {
         ++this.sleepCounter;
         if (this.sleepCounter >= 110) {
            this.sleepCounter = 0;
         }
      }

      this.updateIsUnderwater();
      super.tick();
      if (!this.level.isClientSide && this.containerMenu != null && !this.containerMenu.stillValid(this)) {
         this.closeContainer();
         this.containerMenu = this.inventoryMenu;
      }

      this.moveCloak();
      if (!this.level.isClientSide) {
         this.foodData.tick(this);
         this.awardStat(Stats.PLAY_TIME);
         this.awardStat(Stats.TOTAL_WORLD_TIME);
         if (this.isAlive()) {
            this.awardStat(Stats.TIME_SINCE_DEATH);
         }

         if (this.isDiscrete()) {
            this.awardStat(Stats.CROUCH_TIME);
         }

         if (!this.isSleeping()) {
            this.awardStat(Stats.TIME_SINCE_REST);
         }
      }

      int â˜ƒ = 29999999;
      double â˜ƒx = Mth.clamp(this.getX(), -2.9999999E7, 2.9999999E7);
      double â˜ƒxx = Mth.clamp(this.getZ(), -2.9999999E7, 2.9999999E7);
      if (â˜ƒx != this.getX() || â˜ƒxx != this.getZ()) {
         this.setPos(â˜ƒx, this.getY(), â˜ƒxx);
      }

      ++this.attackStrengthTicker;
      ItemStack â˜ƒ = this.getMainHandItem();
      if (!ItemStack.matches(this.lastItemInMainHand, â˜ƒ)) {
         if (!ItemStack.isSameIgnoreDurability(this.lastItemInMainHand, â˜ƒ)) {
            this.resetAttackStrengthTicker();
         }

         this.lastItemInMainHand = â˜ƒ.copy();
      }

      this.turtleHelmetTick();
      this.cooldowns.tick();
      this.updatePlayerPose();
   }

   public boolean isSecondaryUseActive() {
      return this.isShiftKeyDown();
   }

   protected boolean wantsToStopRiding() {
      return this.isShiftKeyDown();
   }

   protected boolean isStayingOnGroundSurface() {
      return this.isShiftKeyDown();
   }

   protected boolean updateIsUnderwater() {
      this.wasUnderwater = this.isEyeInFluid(FluidTags.WATER);
      return this.wasUnderwater;
   }

   private void turtleHelmetTick() {
      ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.HEAD);
      if (â˜ƒ.is(Items.TURTLE_HELMET) && !this.isEyeInFluid(FluidTags.WATER)) {
         this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false, true));
      }
   }

   protected ItemCooldowns createItemCooldowns() {
      return new ItemCooldowns();
   }

   private void moveCloak() {
      this.xCloakO = this.xCloak;
      this.yCloakO = this.yCloak;
      this.zCloakO = this.zCloak;
      double â˜ƒ = this.getX() - this.xCloak;
      double â˜ƒx = this.getY() - this.yCloak;
      double â˜ƒxx = this.getZ() - this.zCloak;
      double â˜ƒxxx = 10.0;
      if (â˜ƒ > 10.0) {
         this.xCloak = this.getX();
         this.xCloakO = this.xCloak;
      }

      if (â˜ƒxx > 10.0) {
         this.zCloak = this.getZ();
         this.zCloakO = this.zCloak;
      }

      if (â˜ƒx > 10.0) {
         this.yCloak = this.getY();
         this.yCloakO = this.yCloak;
      }

      if (â˜ƒ < -10.0) {
         this.xCloak = this.getX();
         this.xCloakO = this.xCloak;
      }

      if (â˜ƒxx < -10.0) {
         this.zCloak = this.getZ();
         this.zCloakO = this.zCloak;
      }

      if (â˜ƒx < -10.0) {
         this.yCloak = this.getY();
         this.yCloakO = this.yCloak;
      }

      this.xCloak += â˜ƒ * 0.25;
      this.zCloak += â˜ƒxx * 0.25;
      this.yCloak += â˜ƒx * 0.25;
   }

   protected void updatePlayerPose() {
      if (this.canEnterPose(Pose.SWIMMING)) {
         Pose â˜ƒ;
         if (this.isFallFlying()) {
            â˜ƒ = Pose.FALL_FLYING;
         } else if (this.isSleeping()) {
            â˜ƒ = Pose.SLEEPING;
         } else if (this.isSwimming()) {
            â˜ƒ = Pose.SWIMMING;
         } else if (this.isAutoSpinAttack()) {
            â˜ƒ = Pose.SPIN_ATTACK;
         } else if (this.isShiftKeyDown() && !this.abilities.flying) {
            â˜ƒ = Pose.CROUCHING;
         } else {
            â˜ƒ = Pose.STANDING;
         }

         Pose â˜ƒ;
         if (this.isSpectator() || this.isPassenger() || this.canEnterPose(â˜ƒ)) {
            â˜ƒ = â˜ƒ;
         } else if (this.canEnterPose(Pose.CROUCHING)) {
            â˜ƒ = Pose.CROUCHING;
         } else {
            â˜ƒ = Pose.SWIMMING;
         }

         this.setPose(â˜ƒ);
      }
   }

   @Override
   public int getPortalWaitTime() {
      return this.abilities.invulnerable ? 1 : 80;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.PLAYER_SWIM;
   }

   @Override
   protected SoundEvent getSwimSplashSound() {
      return SoundEvents.PLAYER_SPLASH;
   }

   @Override
   protected SoundEvent getSwimHighSpeedSplashSound() {
      return SoundEvents.PLAYER_SPLASH_HIGH_SPEED;
   }

   @Override
   public int getDimensionChangingDelay() {
      return 10;
   }

   @Override
   public void playSound(SoundEvent var1, float var2, float var3) {
      this.level.playSound(this, this.getX(), this.getY(), this.getZ(), â˜ƒ, this.getSoundSource(), â˜ƒ, â˜ƒ);
   }

   public void playNotifySound(SoundEvent var1, SoundSource var2, float var3, float var4) {
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.PLAYERS;
   }

   @Override
   protected int getFireImmuneTicks() {
      return 20;
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 9) {
         this.completeUsingItem();
      } else if (â˜ƒ == 23) {
         this.reducedDebugInfo = false;
      } else if (â˜ƒ == 22) {
         this.reducedDebugInfo = true;
      } else if (â˜ƒ == 43) {
         this.addParticlesAroundSelf(ParticleTypes.CLOUD);
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   private void addParticlesAroundSelf(ParticleOptions var1) {
      for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
         double â˜ƒx = this.random.nextGaussian() * 0.02;
         double â˜ƒxx = this.random.nextGaussian() * 0.02;
         double â˜ƒxxx = this.random.nextGaussian() * 0.02;
         this.level.addParticle(â˜ƒ, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   protected void closeContainer() {
      this.containerMenu = this.inventoryMenu;
   }

   @Override
   public void rideTick() {
      if (!this.level.isClientSide && this.wantsToStopRiding() && this.isPassenger()) {
         this.stopRiding();
         this.setShiftKeyDown(false);
      } else {
         double â˜ƒ = this.getX();
         double â˜ƒx = this.getY();
         double â˜ƒxx = this.getZ();
         super.rideTick();
         this.oBob = this.bob;
         this.bob = 0.0F;
         this.checkRidingStatistics(this.getX() - â˜ƒ, this.getY() - â˜ƒx, this.getZ() - â˜ƒxx);
      }
   }

   @Override
   protected void serverAiStep() {
      super.serverAiStep();
      this.updateSwingTime();
      this.yHeadRot = this.getYRot();
   }

   @Override
   public void aiStep() {
      if (this.jumpTriggerTime > 0) {
         --this.jumpTriggerTime;
      }

      if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
         if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) {
            this.heal(1.0F);
         }

         if (this.foodData.needsFood() && this.tickCount % 10 == 0) {
            this.foodData.setFoodLevel(this.foodData.getFoodLevel() + 1);
         }
      }

      this.inventory.tick();
      this.oBob = this.bob;
      super.aiStep();
      this.flyingSpeed = 0.02F;
      if (this.isSprinting()) {
         this.flyingSpeed = (float)((double)this.flyingSpeed + 0.005999999865889549);
      }

      this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
      float â˜ƒ;
      if (this.onGround && !this.isDeadOrDying() && !this.isSwimming()) {
         â˜ƒ = Math.min(0.1F, (float)this.getDeltaMovement().horizontalDistance());
      } else {
         â˜ƒ = 0.0F;
      }

      this.bob += (â˜ƒ - this.bob) * 0.4F;
      if (this.getHealth() > 0.0F && !this.isSpectator()) {
         AABB â˜ƒ;
         if (this.isPassenger() && !this.getVehicle().isRemoved()) {
            â˜ƒ = this.getBoundingBox().minmax(this.getVehicle().getBoundingBox()).inflate(1.0, 0.0, 1.0);
         } else {
            â˜ƒ = this.getBoundingBox().inflate(1.0, 0.5, 1.0);
         }

         List<Entity> â˜ƒ = this.level.getEntities(this, â˜ƒ);
         List<Entity> â˜ƒx = Lists.<Entity>newArrayList();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
            Entity â˜ƒxxx = (Entity)â˜ƒ.get(â˜ƒxx);
            if (â˜ƒxxx.getType() == EntityType.EXPERIENCE_ORB) {
               â˜ƒx.add(â˜ƒxxx);
            } else if (!â˜ƒxxx.isRemoved()) {
               this.touch(â˜ƒxxx);
            }
         }

         if (!â˜ƒx.isEmpty()) {
            this.touch(Util.getRandom(â˜ƒx, this.random));
         }
      }

      this.playShoulderEntityAmbientSound(this.getShoulderEntityLeft());
      this.playShoulderEntityAmbientSound(this.getShoulderEntityRight());
      if (!this.level.isClientSide && (this.fallDistance > 0.5F || this.isInWater()) || this.abilities.flying || this.isSleeping() || this.isInPowderSnow) {
         this.removeEntitiesOnShoulder();
      }
   }

   private void playShoulderEntityAmbientSound(@Nullable CompoundTag var1) {
      if (â˜ƒ != null && (!â˜ƒ.contains("Silent") || !â˜ƒ.getBoolean("Silent")) && this.level.random.nextInt(200) == 0) {
         String â˜ƒ = â˜ƒ.getString("id");
         EntityType.byString(â˜ƒ)
            .filter(var0 -> var0 == EntityType.PARROT)
            .ifPresent(
               var1x -> {
                  if (!Parrot.imitateNearbyMobs(this.level, this)) {
                     this.level
                        .playSound(
                           null,
                           this.getX(),
                           this.getY(),
                           this.getZ(),
                           Parrot.getAmbient(this.level, this.level.random),
                           this.getSoundSource(),
                           1.0F,
                           Parrot.getPitch(this.level.random)
                        );
                  }
               }
            );
      }
   }

   private void touch(Entity var1) {
      â˜ƒ.playerTouch(this);
   }

   public int getScore() {
      return this.entityData.get(DATA_SCORE_ID);
   }

   public void setScore(int var1) {
      this.entityData.set(DATA_SCORE_ID, â˜ƒ);
   }

   public void increaseScore(int var1) {
      int â˜ƒ = this.getScore();
      this.entityData.set(DATA_SCORE_ID, â˜ƒ + â˜ƒ);
   }

   @Override
   public void die(DamageSource var1) {
      super.die(â˜ƒ);
      this.reapplyPosition();
      if (!this.isSpectator()) {
         this.dropAllDeathLoot(â˜ƒ);
      }

      if (â˜ƒ != null) {
         this.setDeltaMovement(
            (double)(-Mth.cos((this.hurtDir + this.getYRot()) * (float) (Math.PI / 180.0)) * 0.1F),
            0.1F,
            (double)(-Mth.sin((this.hurtDir + this.getYRot()) * (float) (Math.PI / 180.0)) * 0.1F)
         );
      } else {
         this.setDeltaMovement(0.0, 0.1, 0.0);
      }

      this.awardStat(Stats.DEATHS);
      this.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
      this.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
      this.clearFire();
      this.setSharedFlagOnFire(false);
   }

   @Override
   protected void dropEquipment() {
      super.dropEquipment();
      if (!this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
         this.destroyVanishingCursedItems();
         this.inventory.dropAll();
      }
   }

   protected void destroyVanishingCursedItems() {
      for(int â˜ƒ = 0; â˜ƒ < this.inventory.getContainerSize(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.inventory.getItem(â˜ƒ);
         if (!â˜ƒx.isEmpty() && EnchantmentHelper.hasVanishingCurse(â˜ƒx)) {
            this.inventory.removeItemNoUpdate(â˜ƒ);
         }
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      if (â˜ƒ == DamageSource.ON_FIRE) {
         return SoundEvents.PLAYER_HURT_ON_FIRE;
      } else if (â˜ƒ == DamageSource.DROWN) {
         return SoundEvents.PLAYER_HURT_DROWN;
      } else if (â˜ƒ == DamageSource.SWEET_BERRY_BUSH) {
         return SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH;
      } else {
         return â˜ƒ == DamageSource.FREEZE ? SoundEvents.PLAYER_HURT_FREEZE : SoundEvents.PLAYER_HURT;
      }
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PLAYER_DEATH;
   }

   @Nullable
   public ItemEntity drop(ItemStack var1, boolean var2) {
      return this.drop(â˜ƒ, false, â˜ƒ);
   }

   @Nullable
   public ItemEntity drop(ItemStack var1, boolean var2, boolean var3) {
      if (â˜ƒ.isEmpty()) {
         return null;
      } else {
         if (this.level.isClientSide) {
            this.swing(InteractionHand.MAIN_HAND);
         }

         double â˜ƒ = this.getEyeY() - 0.3F;
         ItemEntity â˜ƒx = new ItemEntity(this.level, this.getX(), â˜ƒ, this.getZ(), â˜ƒ);
         â˜ƒx.setPickUpDelay(40);
         if (â˜ƒ) {
            â˜ƒx.setThrower(this.getUUID());
         }

         if (â˜ƒ) {
            float â˜ƒ = this.random.nextFloat() * 0.5F;
            float â˜ƒx = this.random.nextFloat() * (float) (Math.PI * 2);
            â˜ƒx.setDeltaMovement((double)(-Mth.sin(â˜ƒx) * â˜ƒ), 0.2F, (double)(Mth.cos(â˜ƒx) * â˜ƒ));
         } else {
            float â˜ƒ = 0.3F;
            float â˜ƒx = Mth.sin(this.getXRot() * (float) (Math.PI / 180.0));
            float â˜ƒxx = Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
            float â˜ƒxxx = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
            float â˜ƒxxxx = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
            float â˜ƒxxxxx = this.random.nextFloat() * (float) (Math.PI * 2);
            float â˜ƒxxxxxx = 0.02F * this.random.nextFloat();
            â˜ƒx.setDeltaMovement(
               (double)(-â˜ƒxxx * â˜ƒxx * 0.3F) + Math.cos((double)â˜ƒxxxxx) * (double)â˜ƒxxxxxx,
               (double)(-â˜ƒx * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F),
               (double)(â˜ƒxxxx * â˜ƒxx * 0.3F) + Math.sin((double)â˜ƒxxxxx) * (double)â˜ƒxxxxxx
            );
         }

         return â˜ƒx;
      }
   }

   public float getDestroySpeed(BlockState var1) {
      float â˜ƒ = this.inventory.getDestroySpeed(â˜ƒ);
      if (â˜ƒ > 1.0F) {
         int â˜ƒx = EnchantmentHelper.getBlockEfficiency(this);
         ItemStack â˜ƒxx = this.getMainHandItem();
         if (â˜ƒx > 0 && !â˜ƒxx.isEmpty()) {
            â˜ƒ += (float)(â˜ƒx * â˜ƒx + 1);
         }
      }

      if (MobEffectUtil.hasDigSpeed(this)) {
         â˜ƒ *= 1.0F + (float)(MobEffectUtil.getDigSpeedAmplification(this) + 1) * 0.2F;
      }

      if (this.hasEffect(MobEffects.DIG_SLOWDOWN)) {
         â˜ƒ *= switch(this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) {
            case 0 -> 0.3F;
            case 1 -> 0.09F;
            case 2 -> 0.0027F;
            default -> 8.1E-4F;
         };
      }

      if (this.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
         â˜ƒ /= 5.0F;
      }

      if (!this.onGround) {
         â˜ƒ /= 5.0F;
      }

      return â˜ƒ;
   }

   public boolean hasCorrectToolForDrops(BlockState var1) {
      return !â˜ƒ.requiresCorrectToolForDrops() || this.inventory.getSelected().isCorrectToolForDrops(â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setUUID(createPlayerUUID(this.gameProfile));
      ListTag â˜ƒ = â˜ƒ.getList("Inventory", 10);
      this.inventory.load(â˜ƒ);
      this.inventory.selected = â˜ƒ.getInt("SelectedItemSlot");
      this.sleepCounter = â˜ƒ.getShort("SleepTimer");
      this.experienceProgress = â˜ƒ.getFloat("XpP");
      this.experienceLevel = â˜ƒ.getInt("XpLevel");
      this.totalExperience = â˜ƒ.getInt("XpTotal");
      this.enchantmentSeed = â˜ƒ.getInt("XpSeed");
      if (this.enchantmentSeed == 0) {
         this.enchantmentSeed = this.random.nextInt();
      }

      this.setScore(â˜ƒ.getInt("Score"));
      this.foodData.readAdditionalSaveData(â˜ƒ);
      this.abilities.loadSaveData(â˜ƒ);
      this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)this.abilities.getWalkingSpeed());
      if (â˜ƒ.contains("EnderItems", 9)) {
         this.enderChestInventory.fromTag(â˜ƒ.getList("EnderItems", 10));
      }

      if (â˜ƒ.contains("ShoulderEntityLeft", 10)) {
         this.setShoulderEntityLeft(â˜ƒ.getCompound("ShoulderEntityLeft"));
      }

      if (â˜ƒ.contains("ShoulderEntityRight", 10)) {
         this.setShoulderEntityRight(â˜ƒ.getCompound("ShoulderEntityRight"));
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
      â˜ƒ.put("Inventory", this.inventory.save(new ListTag()));
      â˜ƒ.putInt("SelectedItemSlot", this.inventory.selected);
      â˜ƒ.putShort("SleepTimer", (short)this.sleepCounter);
      â˜ƒ.putFloat("XpP", this.experienceProgress);
      â˜ƒ.putInt("XpLevel", this.experienceLevel);
      â˜ƒ.putInt("XpTotal", this.totalExperience);
      â˜ƒ.putInt("XpSeed", this.enchantmentSeed);
      â˜ƒ.putInt("Score", this.getScore());
      this.foodData.addAdditionalSaveData(â˜ƒ);
      this.abilities.addSaveData(â˜ƒ);
      â˜ƒ.put("EnderItems", this.enderChestInventory.createTag());
      if (!this.getShoulderEntityLeft().isEmpty()) {
         â˜ƒ.put("ShoulderEntityLeft", this.getShoulderEntityLeft());
      }

      if (!this.getShoulderEntityRight().isEmpty()) {
         â˜ƒ.put("ShoulderEntityRight", this.getShoulderEntityRight());
      }
   }

   @Override
   public boolean isInvulnerableTo(DamageSource var1) {
      if (super.isInvulnerableTo(â˜ƒ)) {
         return true;
      } else if (â˜ƒ == DamageSource.DROWN) {
         return !this.level.getGameRules().getBoolean(GameRules.RULE_DROWNING_DAMAGE);
      } else if (â˜ƒ.isFall()) {
         return !this.level.getGameRules().getBoolean(GameRules.RULE_FALL_DAMAGE);
      } else if (â˜ƒ.isFire()) {
         return !this.level.getGameRules().getBoolean(GameRules.RULE_FIRE_DAMAGE);
      } else if (â˜ƒ == DamageSource.FREEZE) {
         return !this.level.getGameRules().getBoolean(GameRules.RULE_FREEZE_DAMAGE);
      } else {
         return false;
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (this.abilities.invulnerable && !â˜ƒ.isBypassInvul()) {
         return false;
      } else {
         this.noActionTime = 0;
         if (this.isDeadOrDying()) {
            return false;
         } else {
            this.removeEntitiesOnShoulder();
            if (â˜ƒ.scalesWithDifficulty()) {
               if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
                  â˜ƒ = 0.0F;
               }

               if (this.level.getDifficulty() == Difficulty.EASY) {
                  â˜ƒ = Math.min(â˜ƒ / 2.0F + 1.0F, â˜ƒ);
               }

               if (this.level.getDifficulty() == Difficulty.HARD) {
                  â˜ƒ = â˜ƒ * 3.0F / 2.0F;
               }
            }

            return â˜ƒ == 0.0F ? false : super.hurt(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   protected void blockUsingShield(LivingEntity var1) {
      super.blockUsingShield(â˜ƒ);
      if (â˜ƒ.getMainHandItem().getItem() instanceof AxeItem) {
         this.disableShield(true);
      }
   }

   @Override
   public boolean canBeSeenAsEnemy() {
      return !this.getAbilities().invulnerable && super.canBeSeenAsEnemy();
   }

   public boolean canHarmPlayer(Player var1) {
      Team â˜ƒ = this.getTeam();
      Team â˜ƒx = â˜ƒ.getTeam();
      if (â˜ƒ == null) {
         return true;
      } else {
         return !â˜ƒ.isAlliedTo(â˜ƒx) ? true : â˜ƒ.isAllowFriendlyFire();
      }
   }

   @Override
   protected void hurtArmor(DamageSource var1, float var2) {
      this.inventory.hurtArmor(â˜ƒ, â˜ƒ, Inventory.ALL_ARMOR_SLOTS);
   }

   @Override
   protected void hurtHelmet(DamageSource var1, float var2) {
      this.inventory.hurtArmor(â˜ƒ, â˜ƒ, Inventory.HELMET_SLOT_ONLY);
   }

   @Override
   protected void hurtCurrentlyUsedShield(float var1) {
      if (this.useItem.is(Items.SHIELD)) {
         if (!this.level.isClientSide) {
            this.awardStat(Stats.ITEM_USED.get(this.useItem.getItem()));
         }

         if (â˜ƒ >= 3.0F) {
            int â˜ƒ = 1 + Mth.floor(â˜ƒ);
            InteractionHand â˜ƒx = this.getUsedItemHand();
            this.useItem.hurtAndBreak(â˜ƒ, this, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
            if (this.useItem.isEmpty()) {
               if (â˜ƒx == InteractionHand.MAIN_HAND) {
                  this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
               } else {
                  this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
               }

               this.useItem = ItemStack.EMPTY;
               this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
            }
         }
      }
   }

   @Override
   protected void actuallyHurt(DamageSource var1, float var2) {
      if (!this.isInvulnerableTo(â˜ƒ)) {
         â˜ƒ = this.getDamageAfterArmorAbsorb(â˜ƒ, â˜ƒ);
         â˜ƒ = this.getDamageAfterMagicAbsorb(â˜ƒ, â˜ƒ);
         float var8 = Math.max(â˜ƒ - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (â˜ƒ - var8));
         float â˜ƒ = â˜ƒ - var8;
         if (â˜ƒ > 0.0F && â˜ƒ < 3.4028235E37F) {
            this.awardStat(Stats.DAMAGE_ABSORBED, Math.round(â˜ƒ * 10.0F));
         }

         if (var8 != 0.0F) {
            this.causeFoodExhaustion(â˜ƒ.getFoodExhaustion());
            float â˜ƒ = this.getHealth();
            this.setHealth(this.getHealth() - var8);
            this.getCombatTracker().recordDamage(â˜ƒ, â˜ƒ, var8);
            if (var8 < 3.4028235E37F) {
               this.awardStat(Stats.DAMAGE_TAKEN, Math.round(var8 * 10.0F));
            }
         }
      }
   }

   @Override
   protected boolean onSoulSpeedBlock() {
      return !this.abilities.flying && super.onSoulSpeedBlock();
   }

   public void openTextEdit(SignBlockEntity var1) {
   }

   public void openMinecartCommandBlock(BaseCommandBlock var1) {
   }

   public void openCommandBlock(CommandBlockEntity var1) {
   }

   public void openStructureBlock(StructureBlockEntity var1) {
   }

   public void openJigsawBlock(JigsawBlockEntity var1) {
   }

   public void openHorseInventory(AbstractHorse var1, Container var2) {
   }

   public OptionalInt openMenu(@Nullable MenuProvider var1) {
      return OptionalInt.empty();
   }

   public void sendMerchantOffers(int var1, MerchantOffers var2, int var3, int var4, boolean var5, boolean var6) {
   }

   public void openItemGui(ItemStack var1, InteractionHand var2) {
   }

   public InteractionResult interactOn(Entity var1, InteractionHand var2) {
      if (this.isSpectator()) {
         if (â˜ƒ instanceof MenuProvider) {
            this.openMenu((MenuProvider)â˜ƒ);
         }

         return InteractionResult.PASS;
      } else {
         ItemStack â˜ƒ = this.getItemInHand(â˜ƒ);
         ItemStack â˜ƒx = â˜ƒ.copy();
         InteractionResult â˜ƒxx = â˜ƒ.interact(this, â˜ƒ);
         if (â˜ƒxx.consumesAction()) {
            if (this.abilities.instabuild && â˜ƒ == this.getItemInHand(â˜ƒ) && â˜ƒ.getCount() < â˜ƒx.getCount()) {
               â˜ƒ.setCount(â˜ƒx.getCount());
            }

            return â˜ƒxx;
         } else {
            if (!â˜ƒ.isEmpty() && â˜ƒ instanceof LivingEntity) {
               if (this.abilities.instabuild) {
                  â˜ƒ = â˜ƒx;
               }

               InteractionResult â˜ƒ = â˜ƒ.interactLivingEntity(this, (LivingEntity)â˜ƒ, â˜ƒ);
               if (â˜ƒ.consumesAction()) {
                  if (â˜ƒ.isEmpty() && !this.abilities.instabuild) {
                     this.setItemInHand(â˜ƒ, ItemStack.EMPTY);
                  }

                  return â˜ƒ;
               }
            }

            return InteractionResult.PASS;
         }
      }
   }

   @Override
   public double getMyRidingOffset() {
      return -0.35;
   }

   @Override
   public void removeVehicle() {
      super.removeVehicle();
      this.boardingCooldown = 0;
   }

   @Override
   protected boolean isImmobile() {
      return super.isImmobile() || this.isSleeping();
   }

   @Override
   public boolean isAffectedByFluids() {
      return !this.abilities.flying;
   }

   @Override
   protected Vec3 maybeBackOffFromEdge(Vec3 var1, MoverType var2) {
      if (!this.abilities.flying && (â˜ƒ == MoverType.SELF || â˜ƒ == MoverType.PLAYER) && this.isStayingOnGroundSurface() && this.isAboveGround()) {
         double â˜ƒ = â˜ƒ.x;
         double â˜ƒx = â˜ƒ.z;
         double â˜ƒxx = 0.05;

         while(â˜ƒ != 0.0 && this.level.noCollision(this, this.getBoundingBox().move(â˜ƒ, (double)(-this.maxUpStep), 0.0))) {
            if (â˜ƒ < 0.05 && â˜ƒ >= -0.05) {
               â˜ƒ = 0.0;
            } else if (â˜ƒ > 0.0) {
               â˜ƒ -= 0.05;
            } else {
               â˜ƒ += 0.05;
            }
         }

         while(â˜ƒx != 0.0 && this.level.noCollision(this, this.getBoundingBox().move(0.0, (double)(-this.maxUpStep), â˜ƒx))) {
            if (â˜ƒx < 0.05 && â˜ƒx >= -0.05) {
               â˜ƒx = 0.0;
            } else if (â˜ƒx > 0.0) {
               â˜ƒx -= 0.05;
            } else {
               â˜ƒx += 0.05;
            }
         }

         while(â˜ƒ != 0.0 && â˜ƒx != 0.0 && this.level.noCollision(this, this.getBoundingBox().move(â˜ƒ, (double)(-this.maxUpStep), â˜ƒx))) {
            if (â˜ƒ < 0.05 && â˜ƒ >= -0.05) {
               â˜ƒ = 0.0;
            } else if (â˜ƒ > 0.0) {
               â˜ƒ -= 0.05;
            } else {
               â˜ƒ += 0.05;
            }

            if (â˜ƒx < 0.05 && â˜ƒx >= -0.05) {
               â˜ƒx = 0.0;
            } else if (â˜ƒx > 0.0) {
               â˜ƒx -= 0.05;
            } else {
               â˜ƒx += 0.05;
            }
         }

         â˜ƒ = new Vec3(â˜ƒ, â˜ƒ.y, â˜ƒx);
      }

      return â˜ƒ;
   }

   private boolean isAboveGround() {
      return this.onGround
         || this.fallDistance < this.maxUpStep
            && !this.level.noCollision(this, this.getBoundingBox().move(0.0, (double)(this.fallDistance - this.maxUpStep), 0.0));
   }

   public void attack(Entity var1) {
      if (â˜ƒ.isAttackable()) {
         if (!â˜ƒ.skipAttackInteraction(this)) {
            float â˜ƒx = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float â˜ƒ;
            if (â˜ƒ instanceof LivingEntity) {
               â˜ƒ = EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)â˜ƒ).getMobType());
            } else {
               â˜ƒ = EnchantmentHelper.getDamageBonus(this.getMainHandItem(), MobType.UNDEFINED);
            }

            float â˜ƒ = this.getAttackStrengthScale(0.5F);
            â˜ƒx *= 0.2F + â˜ƒ * â˜ƒ * 0.8F;
            â˜ƒ *= â˜ƒ;
            this.resetAttackStrengthTicker();
            if (â˜ƒx > 0.0F || â˜ƒ > 0.0F) {
               boolean â˜ƒx = â˜ƒ > 0.9F;
               boolean â˜ƒxx = false;
               int â˜ƒxxx = 0;
               â˜ƒxxx += EnchantmentHelper.getKnockbackBonus(this);
               if (this.isSprinting() && â˜ƒx) {
                  this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, this.getSoundSource(), 1.0F, 1.0F);
                  ++â˜ƒxxx;
                  â˜ƒxx = true;
               }

               boolean â˜ƒx = â˜ƒx
                  && this.fallDistance > 0.0F
                  && !this.onGround
                  && !this.onClimbable()
                  && !this.isInWater()
                  && !this.hasEffect(MobEffects.BLINDNESS)
                  && !this.isPassenger()
                  && â˜ƒ instanceof LivingEntity;
               â˜ƒx = â˜ƒx && !this.isSprinting();
               if (â˜ƒx) {
                  â˜ƒx *= 1.5F;
               }

               â˜ƒx += â˜ƒ;
               boolean â˜ƒx = false;
               double â˜ƒxx = (double)(this.walkDist - this.walkDistO);
               if (â˜ƒx && !â˜ƒx && !â˜ƒxx && this.onGround && â˜ƒxx < (double)this.getSpeed()) {
                  ItemStack â˜ƒxxx = this.getItemInHand(InteractionHand.MAIN_HAND);
                  if (â˜ƒxxx.getItem() instanceof SwordItem) {
                     â˜ƒx = true;
                  }
               }

               float â˜ƒx = 0.0F;
               boolean â˜ƒxx = false;
               int â˜ƒxxx = EnchantmentHelper.getFireAspect(this);
               if (â˜ƒ instanceof LivingEntity) {
                  â˜ƒx = ((LivingEntity)â˜ƒ).getHealth();
                  if (â˜ƒxxx > 0 && !â˜ƒ.isOnFire()) {
                     â˜ƒxx = true;
                     â˜ƒ.setSecondsOnFire(1);
                  }
               }

               Vec3 â˜ƒx = â˜ƒ.getDeltaMovement();
               boolean â˜ƒxx = â˜ƒ.hurt(DamageSource.playerAttack(this), â˜ƒx);
               if (â˜ƒxx) {
                  if (â˜ƒxxx > 0) {
                     if (â˜ƒ instanceof LivingEntity) {
                        ((LivingEntity)â˜ƒ)
                           .knockback(
                              (double)((float)â˜ƒxxx * 0.5F),
                              (double)Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)),
                              (double)(-Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)))
                           );
                     } else {
                        â˜ƒ.push(
                           (double)(-Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * (float)â˜ƒxxx * 0.5F),
                           0.1,
                           (double)(Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * (float)â˜ƒxxx * 0.5F)
                        );
                     }

                     this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
                     this.setSprinting(false);
                  }

                  if (â˜ƒx) {
                     float â˜ƒxxx = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * â˜ƒx;

                     for(LivingEntity â˜ƒxxxx : this.level.getEntitiesOfClass(LivingEntity.class, â˜ƒ.getBoundingBox().inflate(1.0, 0.25, 1.0))) {
                        if (â˜ƒxxxx != this
                           && â˜ƒxxxx != â˜ƒ
                           && !this.isAlliedTo(â˜ƒxxxx)
                           && (!(â˜ƒxxxx instanceof ArmorStand) || !((ArmorStand)â˜ƒxxxx).isMarker())
                           && this.distanceToSqr(â˜ƒxxxx) < 9.0) {
                           â˜ƒxxxx.knockback(
                              0.4F, (double)Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)), (double)(-Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)))
                           );
                           â˜ƒxxxx.hurt(DamageSource.playerAttack(this), â˜ƒxxx);
                        }
                     }

                     this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0F, 1.0F);
                     this.sweepAttack();
                  }

                  if (â˜ƒ instanceof ServerPlayer && â˜ƒ.hurtMarked) {
                     ((ServerPlayer)â˜ƒ).connection.send(new ClientboundSetEntityMotionPacket(â˜ƒ));
                     â˜ƒ.hurtMarked = false;
                     â˜ƒ.setDeltaMovement(â˜ƒx);
                  }

                  if (â˜ƒx) {
                     this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, this.getSoundSource(), 1.0F, 1.0F);
                     this.crit(â˜ƒ);
                  }

                  if (!â˜ƒx && !â˜ƒx) {
                     if (â˜ƒx) {
                        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, this.getSoundSource(), 1.0F, 1.0F);
                     } else {
                        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, this.getSoundSource(), 1.0F, 1.0F);
                     }
                  }

                  if (â˜ƒ > 0.0F) {
                     this.magicCrit(â˜ƒ);
                  }

                  this.setLastHurtMob(â˜ƒ);
                  if (â˜ƒ instanceof LivingEntity) {
                     EnchantmentHelper.doPostHurtEffects((LivingEntity)â˜ƒ, this);
                  }

                  EnchantmentHelper.doPostDamageEffects(this, â˜ƒ);
                  ItemStack â˜ƒxxx = this.getMainHandItem();
                  Entity â˜ƒxxxx = â˜ƒ;
                  if (â˜ƒ instanceof EnderDragonPart) {
                     â˜ƒxxxx = ((EnderDragonPart)â˜ƒ).parentMob;
                  }

                  if (!this.level.isClientSide && !â˜ƒxxx.isEmpty() && â˜ƒxxxx instanceof LivingEntity) {
                     â˜ƒxxx.hurtEnemy((LivingEntity)â˜ƒxxxx, this);
                     if (â˜ƒxxx.isEmpty()) {
                        this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                     }
                  }

                  if (â˜ƒ instanceof LivingEntity) {
                     float â˜ƒxxx = â˜ƒx - ((LivingEntity)â˜ƒ).getHealth();
                     this.awardStat(Stats.DAMAGE_DEALT, Math.round(â˜ƒxxx * 10.0F));
                     if (â˜ƒxxx > 0) {
                        â˜ƒ.setSecondsOnFire(â˜ƒxxx * 4);
                     }

                     if (this.level instanceof ServerLevel && â˜ƒxxx > 2.0F) {
                        int â˜ƒxxx = (int)((double)â˜ƒxxx * 0.5);
                        ((ServerLevel)this.level)
                           .sendParticles(ParticleTypes.DAMAGE_INDICATOR, â˜ƒ.getX(), â˜ƒ.getY(0.5), â˜ƒ.getZ(), â˜ƒxxx, 0.1, 0.0, 0.1, 0.2);
                     }
                  }

                  this.causeFoodExhaustion(0.1F);
               } else {
                  this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, this.getSoundSource(), 1.0F, 1.0F);
                  if (â˜ƒxx) {
                     â˜ƒ.clearFire();
                  }
               }
            }
         }
      }
   }

   @Override
   protected void doAutoAttackOnTouch(LivingEntity var1) {
      this.attack(â˜ƒ);
   }

   public void disableShield(boolean var1) {
      float â˜ƒ = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
      if (â˜ƒ) {
         â˜ƒ += 0.75F;
      }

      if (this.random.nextFloat() < â˜ƒ) {
         this.getCooldowns().addCooldown(Items.SHIELD, 100);
         this.stopUsingItem();
         this.level.broadcastEntityEvent(this, (byte)30);
      }
   }

   public void crit(Entity var1) {
   }

   public void magicCrit(Entity var1) {
   }

   public void sweepAttack() {
      double â˜ƒ = (double)(-Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)));
      double â˜ƒx = (double)Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
      if (this.level instanceof ServerLevel) {
         ((ServerLevel)this.level).sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + â˜ƒ, this.getY(0.5), this.getZ() + â˜ƒx, 0, â˜ƒ, 0.0, â˜ƒx, 0.0);
      }
   }

   public void respawn() {
   }

   @Override
   public void remove(Entity.RemovalReason var1) {
      super.remove(â˜ƒ);
      this.inventoryMenu.removed(this);
      if (this.containerMenu != null) {
         this.containerMenu.removed(this);
      }
   }

   public boolean isLocalPlayer() {
      return false;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   public Abilities getAbilities() {
      return this.abilities;
   }

   public void updateTutorialInventoryAction(ItemStack var1, ItemStack var2, ClickAction var3) {
   }

   public Either<Player.BedSleepingProblem, Unit> startSleepInBed(BlockPos var1) {
      this.startSleeping(â˜ƒ);
      this.sleepCounter = 0;
      return Either.right(Unit.INSTANCE);
   }

   public void stopSleepInBed(boolean var1, boolean var2) {
      super.stopSleeping();
      if (this.level instanceof ServerLevel && â˜ƒ) {
         ((ServerLevel)this.level).updateSleepingPlayerList();
      }

      this.sleepCounter = â˜ƒ ? 0 : 100;
   }

   @Override
   public void stopSleeping() {
      this.stopSleepInBed(true, true);
   }

   public static Optional<Vec3> findRespawnPositionAndUseSpawnBlock(ServerLevel var0, BlockPos var1, float var2, boolean var3, boolean var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      Block â˜ƒx = â˜ƒ.getBlock();
      if (â˜ƒx instanceof RespawnAnchorBlock && â˜ƒ.getValue(RespawnAnchorBlock.CHARGE) > 0 && RespawnAnchorBlock.canSetSpawn(â˜ƒ)) {
         Optional<Vec3> â˜ƒxx = RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, â˜ƒ, â˜ƒ);
         if (!â˜ƒ && â˜ƒxx.isPresent()) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(RespawnAnchorBlock.CHARGE, Integer.valueOf(â˜ƒ.getValue(RespawnAnchorBlock.CHARGE) - 1)), 3);
         }

         return â˜ƒxx;
      } else if (â˜ƒx instanceof BedBlock && BedBlock.canSetSpawn(â˜ƒ)) {
         return BedBlock.findStandUpPosition(EntityType.PLAYER, â˜ƒ, â˜ƒ, â˜ƒ);
      } else if (!â˜ƒ) {
         return Optional.empty();
      } else {
         boolean â˜ƒ = â˜ƒx.isPossibleToRespawnInThis();
         boolean â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.above()).getBlock().isPossibleToRespawnInThis();
         return â˜ƒ && â˜ƒx ? Optional.of(new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.1, (double)â˜ƒ.getZ() + 0.5)) : Optional.empty();
      }
   }

   public boolean isSleepingLongEnough() {
      return this.isSleeping() && this.sleepCounter >= 100;
   }

   public int getSleepTimer() {
      return this.sleepCounter;
   }

   public void displayClientMessage(Component var1, boolean var2) {
   }

   public void awardStat(ResourceLocation var1) {
      this.awardStat(Stats.CUSTOM.get(â˜ƒ));
   }

   public void awardStat(ResourceLocation var1, int var2) {
      this.awardStat(Stats.CUSTOM.get(â˜ƒ), â˜ƒ);
   }

   public void awardStat(Stat<?> var1) {
      this.awardStat(â˜ƒ, 1);
   }

   public void awardStat(Stat<?> var1, int var2) {
   }

   public void resetStat(Stat<?> var1) {
   }

   public int awardRecipes(Collection<Recipe<?>> var1) {
      return 0;
   }

   public void awardRecipesByKey(ResourceLocation[] var1) {
   }

   public int resetRecipes(Collection<Recipe<?>> var1) {
      return 0;
   }

   @Override
   public void jumpFromGround() {
      super.jumpFromGround();
      this.awardStat(Stats.JUMP);
      if (this.isSprinting()) {
         this.causeFoodExhaustion(0.2F);
      } else {
         this.causeFoodExhaustion(0.05F);
      }
   }

   @Override
   public void travel(Vec3 var1) {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      if (this.isSwimming() && !this.isPassenger()) {
         double â˜ƒxxx = this.getLookAngle().y;
         double â˜ƒxxxx = â˜ƒxxx < -0.2 ? 0.085 : 0.06;
         if (â˜ƒxxx <= 0.0
            || this.jumping
            || !this.level.getBlockState(new BlockPos(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())).getFluidState().isEmpty()) {
            Vec3 â˜ƒxxxxx = this.getDeltaMovement();
            this.setDeltaMovement(â˜ƒxxxxx.add(0.0, (â˜ƒxxx - â˜ƒxxxxx.y) * â˜ƒxxxx, 0.0));
         }
      }

      if (this.abilities.flying && !this.isPassenger()) {
         double â˜ƒ = this.getDeltaMovement().y;
         float â˜ƒx = this.flyingSpeed;
         this.flyingSpeed = this.abilities.getFlyingSpeed() * (float)(this.isSprinting() ? 2 : 1);
         super.travel(â˜ƒ);
         Vec3 â˜ƒxx = this.getDeltaMovement();
         this.setDeltaMovement(â˜ƒxx.x, â˜ƒ * 0.6, â˜ƒxx.z);
         this.flyingSpeed = â˜ƒx;
         this.fallDistance = 0.0F;
         this.setSharedFlag(7, false);
      } else {
         super.travel(â˜ƒ);
      }

      this.checkMovementStatistics(this.getX() - â˜ƒ, this.getY() - â˜ƒx, this.getZ() - â˜ƒxx);
   }

   @Override
   public void updateSwimming() {
      if (this.abilities.flying) {
         this.setSwimming(false);
      } else {
         super.updateSwimming();
      }
   }

   protected boolean freeAt(BlockPos var1) {
      return !this.level.getBlockState(â˜ƒ).isSuffocating(this.level, â˜ƒ);
   }

   @Override
   public float getSpeed() {
      return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
   }

   public void checkMovementStatistics(double var1, double var3, double var5) {
      if (!this.isPassenger()) {
         if (this.isSwimming()) {
            int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
            if (â˜ƒ > 0) {
               this.awardStat(Stats.SWIM_ONE_CM, â˜ƒ);
               this.causeFoodExhaustion(0.01F * (float)â˜ƒ * 0.01F);
            }
         } else if (this.isEyeInFluid(FluidTags.WATER)) {
            int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
            if (â˜ƒ > 0) {
               this.awardStat(Stats.WALK_UNDER_WATER_ONE_CM, â˜ƒ);
               this.causeFoodExhaustion(0.01F * (float)â˜ƒ * 0.01F);
            }
         } else if (this.isInWater()) {
            int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
            if (â˜ƒ > 0) {
               this.awardStat(Stats.WALK_ON_WATER_ONE_CM, â˜ƒ);
               this.causeFoodExhaustion(0.01F * (float)â˜ƒ * 0.01F);
            }
         } else if (this.onClimbable()) {
            if (â˜ƒ > 0.0) {
               this.awardStat(Stats.CLIMB_ONE_CM, (int)Math.round(â˜ƒ * 100.0));
            }
         } else if (this.onGround) {
            int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
            if (â˜ƒ > 0) {
               if (this.isSprinting()) {
                  this.awardStat(Stats.SPRINT_ONE_CM, â˜ƒ);
                  this.causeFoodExhaustion(0.1F * (float)â˜ƒ * 0.01F);
               } else if (this.isCrouching()) {
                  this.awardStat(Stats.CROUCH_ONE_CM, â˜ƒ);
                  this.causeFoodExhaustion(0.0F * (float)â˜ƒ * 0.01F);
               } else {
                  this.awardStat(Stats.WALK_ONE_CM, â˜ƒ);
                  this.causeFoodExhaustion(0.0F * (float)â˜ƒ * 0.01F);
               }
            }
         } else if (this.isFallFlying()) {
            int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
            this.awardStat(Stats.AVIATE_ONE_CM, â˜ƒ);
         } else {
            int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
            if (â˜ƒ > 25) {
               this.awardStat(Stats.FLY_ONE_CM, â˜ƒ);
            }
         }
      }
   }

   private void checkRidingStatistics(double var1, double var3, double var5) {
      if (this.isPassenger()) {
         int â˜ƒ = Math.round((float)Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) * 100.0F);
         if (â˜ƒ > 0) {
            Entity â˜ƒx = this.getVehicle();
            if (â˜ƒx instanceof AbstractMinecart) {
               this.awardStat(Stats.MINECART_ONE_CM, â˜ƒ);
            } else if (â˜ƒx instanceof Boat) {
               this.awardStat(Stats.BOAT_ONE_CM, â˜ƒ);
            } else if (â˜ƒx instanceof Pig) {
               this.awardStat(Stats.PIG_ONE_CM, â˜ƒ);
            } else if (â˜ƒx instanceof AbstractHorse) {
               this.awardStat(Stats.HORSE_ONE_CM, â˜ƒ);
            } else if (â˜ƒx instanceof Strider) {
               this.awardStat(Stats.STRIDER_ONE_CM, â˜ƒ);
            }
         }
      }
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      if (this.abilities.mayfly) {
         return false;
      } else {
         if (â˜ƒ >= 2.0F) {
            this.awardStat(Stats.FALL_ONE_CM, (int)Math.round((double)â˜ƒ * 100.0));
         }

         return super.causeFallDamage(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public boolean tryToStartFallFlying() {
      if (!this.onGround && !this.isFallFlying() && !this.isInWater() && !this.hasEffect(MobEffects.LEVITATION)) {
         ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.CHEST);
         if (â˜ƒ.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(â˜ƒ)) {
            this.startFallFlying();
            return true;
         }
      }

      return false;
   }

   public void startFallFlying() {
      this.setSharedFlag(7, true);
   }

   public void stopFallFlying() {
      this.setSharedFlag(7, true);
      this.setSharedFlag(7, false);
   }

   @Override
   protected void doWaterSplashEffect() {
      if (!this.isSpectator()) {
         super.doWaterSplashEffect();
      }
   }

   @Override
   protected SoundEvent getFallDamageSound(int var1) {
      return â˜ƒ > 4 ? SoundEvents.PLAYER_BIG_FALL : SoundEvents.PLAYER_SMALL_FALL;
   }

   @Override
   public void killed(ServerLevel var1, LivingEntity var2) {
      this.awardStat(Stats.ENTITY_KILLED.get(â˜ƒ.getType()));
   }

   @Override
   public void makeStuckInBlock(BlockState var1, Vec3 var2) {
      if (!this.abilities.flying) {
         super.makeStuckInBlock(â˜ƒ, â˜ƒ);
      }
   }

   public void giveExperiencePoints(int var1) {
      this.increaseScore(â˜ƒ);
      this.experienceProgress += (float)â˜ƒ / (float)this.getXpNeededForNextLevel();
      this.totalExperience = Mth.clamp(this.totalExperience + â˜ƒ, 0, Integer.MAX_VALUE);

      while(this.experienceProgress < 0.0F) {
         float â˜ƒ = this.experienceProgress * (float)this.getXpNeededForNextLevel();
         if (this.experienceLevel > 0) {
            this.giveExperienceLevels(-1);
            this.experienceProgress = 1.0F + â˜ƒ / (float)this.getXpNeededForNextLevel();
         } else {
            this.giveExperienceLevels(-1);
            this.experienceProgress = 0.0F;
         }
      }

      while(this.experienceProgress >= 1.0F) {
         this.experienceProgress = (this.experienceProgress - 1.0F) * (float)this.getXpNeededForNextLevel();
         this.giveExperienceLevels(1);
         this.experienceProgress /= (float)this.getXpNeededForNextLevel();
      }
   }

   public int getEnchantmentSeed() {
      return this.enchantmentSeed;
   }

   public void onEnchantmentPerformed(ItemStack var1, int var2) {
      this.experienceLevel -= â˜ƒ;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experienceProgress = 0.0F;
         this.totalExperience = 0;
      }

      this.enchantmentSeed = this.random.nextInt();
   }

   public void giveExperienceLevels(int var1) {
      this.experienceLevel += â˜ƒ;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experienceProgress = 0.0F;
         this.totalExperience = 0;
      }

      if (â˜ƒ > 0 && this.experienceLevel % 5 == 0 && (float)this.lastLevelUpTime < (float)this.tickCount - 100.0F) {
         float â˜ƒ = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
         this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_LEVELUP, this.getSoundSource(), â˜ƒ * 0.75F, 1.0F);
         this.lastLevelUpTime = this.tickCount;
      }
   }

   public int getXpNeededForNextLevel() {
      if (this.experienceLevel >= 30) {
         return 112 + (this.experienceLevel - 30) * 9;
      } else {
         return this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
      }
   }

   public void causeFoodExhaustion(float var1) {
      if (!this.abilities.invulnerable) {
         if (!this.level.isClientSide) {
            this.foodData.addExhaustion(â˜ƒ);
         }
      }
   }

   public FoodData getFoodData() {
      return this.foodData;
   }

   public boolean canEat(boolean var1) {
      return this.abilities.invulnerable || â˜ƒ || this.foodData.needsFood();
   }

   public boolean isHurt() {
      return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
   }

   public boolean mayBuild() {
      return this.abilities.mayBuild;
   }

   public boolean mayUseItemAt(BlockPos var1, Direction var2, ItemStack var3) {
      if (this.abilities.mayBuild) {
         return true;
      } else {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.getOpposite());
         BlockInWorld â˜ƒx = new BlockInWorld(this.level, â˜ƒ, false);
         return â˜ƒ.hasAdventureModePlaceTagForBlock(this.level.getTagManager(), â˜ƒx);
      }
   }

   @Override
   protected int getExperienceReward(Player var1) {
      if (!this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && !this.isSpectator()) {
         int â˜ƒ = this.experienceLevel * 7;
         return â˜ƒ > 100 ? 100 : â˜ƒ;
      } else {
         return 0;
      }
   }

   @Override
   protected boolean isAlwaysExperienceDropper() {
      return true;
   }

   @Override
   public boolean shouldShowName() {
      return true;
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return this.abilities.flying || this.onGround && this.isDiscrete() ? Entity.MovementEmission.NONE : Entity.MovementEmission.ALL;
   }

   public void onUpdateAbilities() {
   }

   @Override
   public Component getName() {
      return new TextComponent(this.gameProfile.getName());
   }

   public PlayerEnderChestContainer getEnderChestInventory() {
      return this.enderChestInventory;
   }

   @Override
   public ItemStack getItemBySlot(EquipmentSlot var1) {
      if (â˜ƒ == EquipmentSlot.MAINHAND) {
         return this.inventory.getSelected();
      } else if (â˜ƒ == EquipmentSlot.OFFHAND) {
         return this.inventory.offhand.get(0);
      } else {
         return â˜ƒ.getType() == EquipmentSlot.Type.ARMOR ? this.inventory.armor.get(â˜ƒ.getIndex()) : ItemStack.EMPTY;
      }
   }

   @Override
   public void setItemSlot(EquipmentSlot var1, ItemStack var2) {
      this.verifyEquippedItem(â˜ƒ);
      if (â˜ƒ == EquipmentSlot.MAINHAND) {
         this.equipEventAndSound(â˜ƒ);
         this.inventory.items.set(this.inventory.selected, â˜ƒ);
      } else if (â˜ƒ == EquipmentSlot.OFFHAND) {
         this.equipEventAndSound(â˜ƒ);
         this.inventory.offhand.set(0, â˜ƒ);
      } else if (â˜ƒ.getType() == EquipmentSlot.Type.ARMOR) {
         this.equipEventAndSound(â˜ƒ);
         this.inventory.armor.set(â˜ƒ.getIndex(), â˜ƒ);
      }
   }

   public boolean addItem(ItemStack var1) {
      this.equipEventAndSound(â˜ƒ);
      return this.inventory.add(â˜ƒ);
   }

   @Override
   public Iterable<ItemStack> getHandSlots() {
      return Lists.<ItemStack>newArrayList(this.getMainHandItem(), this.getOffhandItem());
   }

   @Override
   public Iterable<ItemStack> getArmorSlots() {
      return this.inventory.armor;
   }

   public boolean setEntityOnShoulder(CompoundTag var1) {
      if (this.isPassenger() || !this.onGround || this.isInWater() || this.isInPowderSnow) {
         return false;
      } else if (this.getShoulderEntityLeft().isEmpty()) {
         this.setShoulderEntityLeft(â˜ƒ);
         this.timeEntitySatOnShoulder = this.level.getGameTime();
         return true;
      } else if (this.getShoulderEntityRight().isEmpty()) {
         this.setShoulderEntityRight(â˜ƒ);
         this.timeEntitySatOnShoulder = this.level.getGameTime();
         return true;
      } else {
         return false;
      }
   }

   protected void removeEntitiesOnShoulder() {
      if (this.timeEntitySatOnShoulder + 20L < this.level.getGameTime()) {
         this.respawnEntityOnShoulder(this.getShoulderEntityLeft());
         this.setShoulderEntityLeft(new CompoundTag());
         this.respawnEntityOnShoulder(this.getShoulderEntityRight());
         this.setShoulderEntityRight(new CompoundTag());
      }
   }

   private void respawnEntityOnShoulder(CompoundTag var1) {
      if (!this.level.isClientSide && !â˜ƒ.isEmpty()) {
         EntityType.create(â˜ƒ, this.level).ifPresent(var1x -> {
            if (var1x instanceof TamableAnimal) {
               ((TamableAnimal)var1x).setOwnerUUID(this.uuid);
            }

            var1x.setPos(this.getX(), this.getY() + 0.7F, this.getZ());
            ((ServerLevel)this.level).addWithUUID(var1x);
         });
      }
   }

   @Override
   public abstract boolean isSpectator();

   @Override
   public boolean isSwimming() {
      return !this.abilities.flying && !this.isSpectator() && super.isSwimming();
   }

   public abstract boolean isCreative();

   @Override
   public boolean isPushedByFluid() {
      return !this.abilities.flying;
   }

   public Scoreboard getScoreboard() {
      return this.level.getScoreboard();
   }

   @Override
   public Component getDisplayName() {
      MutableComponent â˜ƒ = PlayerTeam.formatNameForTeam(this.getTeam(), this.getName());
      return this.decorateDisplayNameComponent(â˜ƒ);
   }

   private MutableComponent decorateDisplayNameComponent(MutableComponent var1) {
      String â˜ƒ = this.getGameProfile().getName();
      return â˜ƒ.withStyle(
         var2x -> var2x.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + â˜ƒ + " "))
               .withHoverEvent(this.createHoverEvent())
               .withInsertion(â˜ƒ)
      );
   }

   @Override
   public String getScoreboardName() {
      return this.getGameProfile().getName();
   }

   @Override
   public float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      switch(â˜ƒ) {
         case SWIMMING:
         case FALL_FLYING:
         case SPIN_ATTACK:
            return 0.4F;
         case CROUCHING:
            return 1.27F;
         default:
            return 1.62F;
      }
   }

   @Override
   public void setAbsorptionAmount(float var1) {
      if (â˜ƒ < 0.0F) {
         â˜ƒ = 0.0F;
      }

      this.getEntityData().set(DATA_PLAYER_ABSORPTION_ID, â˜ƒ);
   }

   @Override
   public float getAbsorptionAmount() {
      return this.getEntityData().get(DATA_PLAYER_ABSORPTION_ID);
   }

   public static UUID createPlayerUUID(GameProfile var0) {
      UUID â˜ƒ = â˜ƒ.getId();
      if (â˜ƒ == null) {
         â˜ƒ = createPlayerUUID(â˜ƒ.getName());
      }

      return â˜ƒ;
   }

   public static UUID createPlayerUUID(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + â˜ƒ).getBytes(StandardCharsets.UTF_8));
   }

   public boolean isModelPartShown(PlayerModelPart var1) {
      return (this.getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION) & â˜ƒ.getMask()) == â˜ƒ.getMask();
   }

   @Override
   public SlotAccess getSlot(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.inventory.items.size()) {
         return SlotAccess.forContainer(this.inventory, â˜ƒ);
      } else {
         int â˜ƒ = â˜ƒ - 200;
         return â˜ƒ >= 0 && â˜ƒ < this.enderChestInventory.getContainerSize() ? SlotAccess.forContainer(this.enderChestInventory, â˜ƒ) : super.getSlot(â˜ƒ);
      }
   }

   public boolean isReducedDebugInfo() {
      return this.reducedDebugInfo;
   }

   public void setReducedDebugInfo(boolean var1) {
      this.reducedDebugInfo = â˜ƒ;
   }

   @Override
   public void setRemainingFireTicks(int var1) {
      super.setRemainingFireTicks(this.abilities.invulnerable ? Math.min(â˜ƒ, 1) : â˜ƒ);
   }

   @Override
   public HumanoidArm getMainArm() {
      return this.entityData.get(DATA_PLAYER_MAIN_HAND) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   public void setMainArm(HumanoidArm var1) {
      this.entityData.set(DATA_PLAYER_MAIN_HAND, (byte)(â˜ƒ == HumanoidArm.LEFT ? 0 : 1));
   }

   public CompoundTag getShoulderEntityLeft() {
      return this.entityData.get(DATA_SHOULDER_LEFT);
   }

   protected void setShoulderEntityLeft(CompoundTag var1) {
      this.entityData.set(DATA_SHOULDER_LEFT, â˜ƒ);
   }

   public CompoundTag getShoulderEntityRight() {
      return this.entityData.get(DATA_SHOULDER_RIGHT);
   }

   protected void setShoulderEntityRight(CompoundTag var1) {
      this.entityData.set(DATA_SHOULDER_RIGHT, â˜ƒ);
   }

   public float getCurrentItemAttackStrengthDelay() {
      return (float)(1.0 / this.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0);
   }

   public float getAttackStrengthScale(float var1) {
      return Mth.clamp(((float)this.attackStrengthTicker + â˜ƒ) / this.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
   }

   public void resetAttackStrengthTicker() {
      this.attackStrengthTicker = 0;
   }

   public ItemCooldowns getCooldowns() {
      return this.cooldowns;
   }

   @Override
   protected float getBlockSpeedFactor() {
      return !this.abilities.flying && !this.isFallFlying() ? super.getBlockSpeedFactor() : 1.0F;
   }

   public float getLuck() {
      return (float)this.getAttributeValue(Attributes.LUCK);
   }

   public boolean canUseGameMasterBlocks() {
      return this.abilities.instabuild && this.getPermissionLevel() >= 2;
   }

   @Override
   public boolean canTakeItem(ItemStack var1) {
      EquipmentSlot â˜ƒ = Mob.getEquipmentSlotForItem(â˜ƒ);
      return this.getItemBySlot(â˜ƒ).isEmpty();
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return (EntityDimensions)POSES.getOrDefault(â˜ƒ, STANDING_DIMENSIONS);
   }

   @Override
   public ImmutableList<Pose> getDismountPoses() {
      return ImmutableList.of(Pose.STANDING, Pose.CROUCHING, Pose.SWIMMING);
   }

   @Override
   public ItemStack getProjectile(ItemStack var1) {
      if (!(â˜ƒ.getItem() instanceof ProjectileWeaponItem)) {
         return ItemStack.EMPTY;
      } else {
         Predicate<ItemStack> â˜ƒ = ((ProjectileWeaponItem)â˜ƒ.getItem()).getSupportedHeldProjectiles();
         ItemStack â˜ƒx = ProjectileWeaponItem.getHeldProjectile(this, â˜ƒ);
         if (!â˜ƒx.isEmpty()) {
            return â˜ƒx;
         } else {
            â˜ƒ = ((ProjectileWeaponItem)â˜ƒ.getItem()).getAllSupportedProjectiles();

            for(int â˜ƒ = 0; â˜ƒ < this.inventory.getContainerSize(); ++â˜ƒ) {
               ItemStack â˜ƒx = this.inventory.getItem(â˜ƒ);
               if (â˜ƒ.test(â˜ƒx)) {
                  return â˜ƒx;
               }
            }

            return this.abilities.instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
         }
      }
   }

   @Override
   public ItemStack eat(Level var1, ItemStack var2) {
      this.getFoodData().eat(â˜ƒ.getItem(), â˜ƒ);
      this.awardStat(Stats.ITEM_USED.get(â˜ƒ.getItem()));
      â˜ƒ.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, â˜ƒ.random.nextFloat() * 0.1F + 0.9F);
      if (this instanceof ServerPlayer) {
         CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)this, â˜ƒ);
      }

      return super.eat(â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean shouldRemoveSoulSpeed(BlockState var1) {
      return this.abilities.flying || super.shouldRemoveSoulSpeed(â˜ƒ);
   }

   @Override
   public Vec3 getRopeHoldPosition(float var1) {
      double â˜ƒ = 0.22 * (this.getMainArm() == HumanoidArm.RIGHT ? -1.0 : 1.0);
      float â˜ƒx = Mth.lerp(â˜ƒ * 0.5F, this.getXRot(), this.xRotO) * (float) (Math.PI / 180.0);
      float â˜ƒxx = Mth.lerp(â˜ƒ, this.yBodyRotO, this.yBodyRot) * (float) (Math.PI / 180.0);
      if (this.isFallFlying() || this.isAutoSpinAttack()) {
         Vec3 â˜ƒxxxx = this.getViewVector(â˜ƒ);
         Vec3 â˜ƒxxxxx = this.getDeltaMovement();
         double â˜ƒxxxxxx = â˜ƒxxxxx.horizontalDistanceSqr();
         double â˜ƒxxxxxxx = â˜ƒxxxx.horizontalDistanceSqr();
         float â˜ƒxxx;
         if (â˜ƒxxxxxx > 0.0 && â˜ƒxxxxxxx > 0.0) {
            double â˜ƒxxxxxxxx = (â˜ƒxxxxx.x * â˜ƒxxxx.x + â˜ƒxxxxx.z * â˜ƒxxxx.z) / Math.sqrt(â˜ƒxxxxxx * â˜ƒxxxxxxx);
            double â˜ƒxxxxxxxxx = â˜ƒxxxxx.x * â˜ƒxxxx.z - â˜ƒxxxxx.z * â˜ƒxxxx.x;
            â˜ƒxxx = (float)(Math.signum(â˜ƒxxxxxxxxx) * Math.acos(â˜ƒxxxxxxxx));
         } else {
            â˜ƒxxx = 0.0F;
         }

         return this.getPosition(â˜ƒ).add(new Vec3(â˜ƒ, -0.11, 0.85).zRot(-â˜ƒxxx).xRot(-â˜ƒx).yRot(-â˜ƒxx));
      } else if (this.isVisuallySwimming()) {
         return this.getPosition(â˜ƒ).add(new Vec3(â˜ƒ, 0.2, -0.15).xRot(-â˜ƒx).yRot(-â˜ƒxx));
      } else {
         double â˜ƒ = this.getBoundingBox().getYsize() - 1.0;
         double â˜ƒx = this.isCrouching() ? -0.2 : 0.07;
         return this.getPosition(â˜ƒ).add(new Vec3(â˜ƒ, â˜ƒ, â˜ƒx).yRot(-â˜ƒxx));
      }
   }

   @Override
   public boolean isAlwaysTicking() {
      return true;
   }

   public boolean isScoping() {
      return this.isUsingItem() && this.getUseItem().is(Items.SPYGLASS);
   }

   @Override
   public boolean shouldBeSaved() {
      return false;
   }

   public static enum BedSleepingProblem {
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW(new TranslatableComponent("block.minecraft.bed.no_sleep")),
      TOO_FAR_AWAY(new TranslatableComponent("block.minecraft.bed.too_far_away")),
      OBSTRUCTED(new TranslatableComponent("block.minecraft.bed.obstructed")),
      OTHER_PROBLEM,
      NOT_SAFE(new TranslatableComponent("block.minecraft.bed.not_safe"));

      @Nullable
      private final Component message;

      private BedSleepingProblem() {
         this.message = null;
      }

      private BedSleepingProblem(Component var3) {
         this.message = â˜ƒ;
      }

      @Nullable
      public Component getMessage() {
         return this.message;
      }
   }
}
