package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class Axolotl extends Animal implements LerpingModel, Bucketable {
   public static final int TOTAL_PLAYDEAD_TIME = 200;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Axolotl>>> SENSOR_TYPES = ImmutableList.of(
      SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, SensorType.AXOLOTL_ATTACKABLES, SensorType.AXOLOTL_TEMPTATIONS
   );
   protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
      MemoryModuleType.BREED_TARGET,
      MemoryModuleType.NEAREST_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      MemoryModuleType.PATH,
      MemoryModuleType.ATTACK_TARGET,
      MemoryModuleType.ATTACK_COOLING_DOWN,
      MemoryModuleType.NEAREST_VISIBLE_ADULT,
      MemoryModuleType.HURT_BY_ENTITY,
      MemoryModuleType.PLAY_DEAD_TICKS,
      MemoryModuleType.NEAREST_ATTACKABLE,
      MemoryModuleType.TEMPTING_PLAYER,
      MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
      MemoryModuleType.IS_TEMPTED,
      MemoryModuleType.HAS_HUNTING_COOLDOWN
   );
   private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_PLAYING_DEAD = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.BOOLEAN);
   public static final double PLAYER_REGEN_DETECTION_RANGE = 20.0;
   public static final int RARE_VARIANT_CHANCE = 1200;
   private static final int AXOLOTL_TOTAL_AIR_SUPPLY = 6000;
   public static final String VARIANT_TAG = "Variant";
   private static final int REHYDRATE_AIR_SUPPLY = 1800;
   private static final int REGEN_BUFF_MAX_DURATION = 2400;
   private final Map<String, Vector3f> modelRotationValues = Maps.newHashMap();
   private static final int REGEN_BUFF_BASE_DURATION = 100;

   public Axolotl(EntityType<? extends Axolotl> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
      this.moveControl = new Axolotl.AxolotlMoveControl(this);
      this.lookControl = new Axolotl.AxolotlLookControl(this, 20);
      this.maxUpStep = 1.0F;
   }

   @Override
   public Map<String, Vector3f> getModelRotationValues() {
      return this.modelRotationValues;
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return 0.0F;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_VARIANT, 0);
      this.entityData.define(DATA_PLAYING_DEAD, false);
      this.entityData.define(FROM_BUCKET, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Variant", this.getVariant().getId());
      â˜ƒ.putBoolean("FromBucket", this.fromBucket());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setVariant(Axolotl.Variant.BY_ID[â˜ƒ.getInt("Variant")]);
      this.setFromBucket(â˜ƒ.getBoolean("FromBucket"));
   }

   @Override
   public void playAmbientSound() {
      if (!this.isPlayingDead()) {
         super.playAmbientSound();
      }
   }

   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      boolean â˜ƒ = false;
      if (â˜ƒ == MobSpawnType.BUCKET) {
         return â˜ƒ;
      } else {
         if (â˜ƒ instanceof Axolotl.AxolotlGroupData) {
            if (((Axolotl.AxolotlGroupData)â˜ƒ).getGroupSize() >= 2) {
               â˜ƒ = true;
            }
         } else {
            â˜ƒ = new Axolotl.AxolotlGroupData(
               Axolotl.Variant.getCommonSpawnVariant(this.level.random), Axolotl.Variant.getCommonSpawnVariant(this.level.random)
            );
         }

         this.setVariant(((Axolotl.AxolotlGroupData)â˜ƒ).getVariant(this.level.random));
         if (â˜ƒ) {
            this.setAge(-24000);
         }

         return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void baseTick() {
      int â˜ƒ = this.getAirSupply();
      super.baseTick();
      if (!this.isNoAi()) {
         this.handleAirSupply(â˜ƒ);
      }
   }

   protected void handleAirSupply(int var1) {
      if (this.isAlive() && !this.isInWaterRainOrBubble()) {
         this.setAirSupply(â˜ƒ - 1);
         if (this.getAirSupply() == -20) {
            this.setAirSupply(0);
            this.hurt(DamageSource.DRY_OUT, 2.0F);
         }
      } else {
         this.setAirSupply(this.getMaxAirSupply());
      }
   }

   public void rehydrate() {
      int â˜ƒ = this.getAirSupply() + 1800;
      this.setAirSupply(Math.min(â˜ƒ, this.getMaxAirSupply()));
   }

   @Override
   public int getMaxAirSupply() {
      return 6000;
   }

   public Axolotl.Variant getVariant() {
      return Axolotl.Variant.BY_ID[this.entityData.get(DATA_VARIANT)];
   }

   private void setVariant(Axolotl.Variant var1) {
      this.entityData.set(DATA_VARIANT, â˜ƒ.getId());
   }

   private static boolean useRareVariant(Random var0) {
      return â˜ƒ.nextInt(1200) == 0;
   }

   @Override
   public boolean checkSpawnObstruction(LevelReader var1) {
      return â˜ƒ.isUnobstructed(this);
   }

   @Override
   public boolean canBreatheUnderwater() {
      return true;
   }

   @Override
   public boolean isPushedByFluid() {
      return false;
   }

   @Override
   public MobType getMobType() {
      return MobType.WATER;
   }

   public void setPlayingDead(boolean var1) {
      this.entityData.set(DATA_PLAYING_DEAD, â˜ƒ);
   }

   public boolean isPlayingDead() {
      return this.entityData.get(DATA_PLAYING_DEAD);
   }

   @Override
   public boolean fromBucket() {
      return this.entityData.get(FROM_BUCKET);
   }

   @Override
   public void setFromBucket(boolean var1) {
      this.entityData.set(FROM_BUCKET, â˜ƒ);
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      Axolotl â˜ƒ = EntityType.AXOLOTL.create(â˜ƒ);
      if (â˜ƒ != null) {
         Axolotl.Variant â˜ƒx;
         if (useRareVariant(this.random)) {
            â˜ƒx = Axolotl.Variant.getRareSpawnVariant(this.random);
         } else {
            â˜ƒx = this.random.nextBoolean() ? this.getVariant() : ((Axolotl)â˜ƒ).getVariant();
         }

         â˜ƒ.setVariant(â˜ƒx);
         â˜ƒ.setPersistenceRequired();
      }

      return â˜ƒ;
   }

   @Override
   public double getMeleeAttackRangeSqr(LivingEntity var1) {
      return 1.5 + (double)â˜ƒ.getBbWidth() * 2.0;
   }

   @Override
   public boolean isFood(ItemStack var1) {
      return ItemTags.AXOLOTL_TEMPT_ITEMS.contains(â˜ƒ.getItem());
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return true;
   }

   @Override
   protected void customServerAiStep() {
      this.level.getProfiler().push("axolotlBrain");
      this.getBrain().tick((ServerLevel)this.level, this);
      this.level.getProfiler().pop();
      this.level.getProfiler().push("axolotlActivityUpdate");
      AxolotlAi.updateActivity(this);
      this.level.getProfiler().pop();
      if (!this.isNoAi()) {
         Optional<Integer> â˜ƒ = this.getBrain().getMemory(MemoryModuleType.PLAY_DEAD_TICKS);
         this.setPlayingDead(â˜ƒ.isPresent() && â˜ƒ.get() > 0);
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0).add(Attributes.MOVEMENT_SPEED, 1.0).add(Attributes.ATTACK_DAMAGE, 2.0);
   }

   @Override
   protected PathNavigation createNavigation(Level var1) {
      return new Axolotl.AxolotlPathNavigation(this, â˜ƒ);
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      boolean â˜ƒ = â˜ƒ.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
      if (â˜ƒ) {
         this.doEnchantDamageEffects(this, â˜ƒ);
         this.playSound(SoundEvents.AXOLOTL_ATTACK, 1.0F, 1.0F);
      }

      return â˜ƒ;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      float â˜ƒ = this.getHealth();
      if (!this.level.isClientSide
         && !this.isNoAi()
         && this.level.random.nextInt(3) == 0
         && ((float)this.level.random.nextInt(3) < â˜ƒ || â˜ƒ / this.getMaxHealth() < 0.5F)
         && â˜ƒ < â˜ƒ
         && this.isInWater()
         && (â˜ƒ.getEntity() != null || â˜ƒ.getDirectEntity() != null)
         && !this.isPlayingDead()) {
         this.brain.setMemory(MemoryModuleType.PLAY_DEAD_TICKS, 200);
      }

      return super.hurt(â˜ƒ, â˜ƒ);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.655F;
   }

   @Override
   public int getMaxHeadXRot() {
      return 1;
   }

   @Override
   public int getMaxHeadYRot() {
      return 1;
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      return (InteractionResult)Bucketable.bucketMobPickup(â˜ƒ, â˜ƒ, this).orElse(super.mobInteract(â˜ƒ, â˜ƒ));
   }

   @Override
   public void saveToBucketTag(ItemStack var1) {
      Bucketable.saveDefaultDataToBucketTag(this, â˜ƒ);
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      â˜ƒ.putInt("Variant", this.getVariant().getId());
      â˜ƒ.putInt("Age", this.getAge());
      Brain<?> â˜ƒx = this.getBrain();
      if (â˜ƒx.hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
         â˜ƒ.putLong("HuntingCooldown", â˜ƒx.getTimeUntilExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
      }
   }

   @Override
   public void loadFromBucketTag(CompoundTag var1) {
      Bucketable.loadDefaultDataFromBucketTag(this, â˜ƒ);
      this.setVariant(Axolotl.Variant.BY_ID[â˜ƒ.getInt("Variant")]);
      if (â˜ƒ.contains("Age")) {
         this.setAge(â˜ƒ.getInt("Age"));
      }

      if (â˜ƒ.contains("HuntingCooldown")) {
         this.getBrain().setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, â˜ƒ.getLong("HuntingCooldown"));
      }
   }

   @Override
   public ItemStack getBucketItemStack() {
      return new ItemStack(Items.AXOLOTL_BUCKET);
   }

   @Override
   public SoundEvent getPickupSound() {
      return SoundEvents.BUCKET_FILL_AXOLOTL;
   }

   @Override
   public boolean canBeSeenAsEnemy() {
      return !this.isPlayingDead() && super.canBeSeenAsEnemy();
   }

   public static void onStopAttacking(Axolotl var0) {
      Optional<LivingEntity> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
      if (â˜ƒ.isPresent()) {
         Level â˜ƒx = â˜ƒ.level;
         LivingEntity â˜ƒxx = (LivingEntity)â˜ƒ.get();
         if (â˜ƒxx.isDeadOrDying()) {
            DamageSource â˜ƒxxx = â˜ƒxx.getLastDamageSource();
            if (â˜ƒxxx != null) {
               Entity â˜ƒxxxx = â˜ƒxxx.getEntity();
               if (â˜ƒxxxx != null && â˜ƒxxxx.getType() == EntityType.PLAYER) {
                  Player â˜ƒxxxxx = (Player)â˜ƒxxxx;
                  List<Player> â˜ƒxxxxxx = â˜ƒx.getEntitiesOfClass(Player.class, â˜ƒ.getBoundingBox().inflate(20.0));
                  if (â˜ƒxxxxxx.contains(â˜ƒxxxxx)) {
                     â˜ƒ.applySupportingEffects(â˜ƒxxxxx);
                  }
               }
            }
         }
      }
   }

   public void applySupportingEffects(Player var1) {
      MobEffectInstance â˜ƒ = â˜ƒ.getEffect(MobEffects.REGENERATION);
      int â˜ƒx = â˜ƒ != null ? â˜ƒ.getDuration() : 0;
      if (â˜ƒx < 2400) {
         â˜ƒx = Math.min(2400, 100 + â˜ƒx);
         â˜ƒ.addEffect(new MobEffectInstance(MobEffects.REGENERATION, â˜ƒx, 0), this);
      }

      â˜ƒ.removeEffect(MobEffects.DIG_SLOWDOWN);
   }

   @Override
   public boolean requiresCustomPersistence() {
      return super.requiresCustomPersistence() || this.fromBucket();
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.AXOLOTL_HURT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.AXOLOTL_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      return this.isInWater() ? SoundEvents.AXOLOTL_IDLE_WATER : SoundEvents.AXOLOTL_IDLE_AIR;
   }

   @Override
   protected SoundEvent getSwimSplashSound() {
      return SoundEvents.AXOLOTL_SPLASH;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.AXOLOTL_SWIM;
   }

   @Override
   protected Brain.Provider<Axolotl> brainProvider() {
      return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
   }

   @Override
   protected Brain<?> makeBrain(Dynamic<?> var1) {
      return AxolotlAi.makeBrain(this.brainProvider().makeBrain(â˜ƒ));
   }

   @Override
   public Brain<Axolotl> getBrain() {
      return super.getBrain();
   }

   @Override
   protected void sendDebugPackets() {
      super.sendDebugPackets();
      DebugPackets.sendEntityBrain(this);
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(this.getSpeed(), â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
      } else {
         super.travel(â˜ƒ);
      }
   }

   @Override
   protected void usePlayerItem(Player var1, InteractionHand var2, ItemStack var3) {
      if (â˜ƒ.is(Items.TROPICAL_FISH_BUCKET)) {
         â˜ƒ.setItemInHand(â˜ƒ, new ItemStack(Items.WATER_BUCKET));
      } else {
         super.usePlayerItem(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return !this.fromBucket() && !this.hasCustomName();
   }

   public static class AxolotlGroupData extends AgeableMob.AgeableMobGroupData {
      public final Axolotl.Variant[] types;

      public AxolotlGroupData(Axolotl.Variant... var1) {
         super(false);
         this.types = â˜ƒ;
      }

      public Axolotl.Variant getVariant(Random var1) {
         return this.types[â˜ƒ.nextInt(this.types.length)];
      }
   }

   class AxolotlLookControl extends SmoothSwimmingLookControl {
      public AxolotlLookControl(Axolotl var2, int var3) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      public void tick() {
         if (!Axolotl.this.isPlayingDead()) {
            super.tick();
         }
      }
   }

   static class AxolotlMoveControl extends SmoothSwimmingMoveControl {
      private final Axolotl axolotl;

      public AxolotlMoveControl(Axolotl var1) {
         super(â˜ƒ, 85, 10, 0.1F, 0.5F, false);
         this.axolotl = â˜ƒ;
      }

      @Override
      public void tick() {
         if (!this.axolotl.isPlayingDead()) {
            super.tick();
         }
      }
   }

   static class AxolotlPathNavigation extends WaterBoundPathNavigation {
      AxolotlPathNavigation(Axolotl var1, Level var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected boolean canUpdatePath() {
         return true;
      }

      @Override
      protected PathFinder createPathFinder(int var1) {
         this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
         return new PathFinder(this.nodeEvaluator, â˜ƒ);
      }

      @Override
      public boolean isStableDestination(BlockPos var1) {
         return !this.level.getBlockState(â˜ƒ.below()).isAir();
      }
   }

   public static enum Variant {
      LUCY(0, "lucy", true),
      WILD(1, "wild", true),
      GOLD(2, "gold", true),
      CYAN(3, "cyan", true),
      BLUE(4, "blue", false);

      public static final Axolotl.Variant[] BY_ID = (Axolotl.Variant[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(Axolotl.Variant::getId))
         .toArray(var0 -> new Axolotl.Variant[var0]);
      private final int id;
      private final String name;
      private final boolean common;

      private Variant(int var3, String var4, boolean var5) {
         this.id = â˜ƒ;
         this.name = â˜ƒ;
         this.common = â˜ƒ;
      }

      public int getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public static Axolotl.Variant getCommonSpawnVariant(Random var0) {
         return getSpawnVariant(â˜ƒ, true);
      }

      public static Axolotl.Variant getRareSpawnVariant(Random var0) {
         return getSpawnVariant(â˜ƒ, false);
      }

      private static Axolotl.Variant getSpawnVariant(Random var0, boolean var1) {
         Axolotl.Variant[] â˜ƒ = (Axolotl.Variant[])Arrays.stream(BY_ID).filter(var1x -> var1x.common == â˜ƒ).toArray(var0x -> new Axolotl.Variant[var0x]);
         return Util.getRandom((Axolotl.Variant[])â˜ƒ, â˜ƒ);
      }
   }
}
