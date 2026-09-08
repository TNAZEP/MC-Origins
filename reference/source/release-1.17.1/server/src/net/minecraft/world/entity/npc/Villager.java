package net.minecraft.world.entity.npc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.sensing.GolemSensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class Villager extends AbstractVillager implements ReputationEventHandler, VillagerDataHolder {
   private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.VILLAGER_DATA);
   public static final int BREEDING_FOOD_THRESHOLD = 12;
   public static final Map<Item, Integer> FOOD_POINTS = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
   private static final int TRADES_PER_LEVEL = 2;
   private static final Set<Item> WANTED_ITEMS = ImmutableSet.of(
      Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS
   );
   private static final int MAX_GOSSIP_TOPICS = 10;
   private static final int GOSSIP_COOLDOWN = 1200;
   private static final int GOSSIP_DECAY_INTERVAL = 24000;
   private static final int REPUTATION_CHANGE_PER_EVENT = 25;
   private static final int HOW_FAR_AWAY_TO_TALK_TO_OTHER_VILLAGERS_ABOUT_GOLEMS = 10;
   private static final int HOW_MANY_VILLAGERS_NEED_TO_AGREE_TO_SPAWN_A_GOLEM = 5;
   private static final long TIME_SINCE_SLEEPING_FOR_GOLEM_SPAWNING = 24000L;
   @VisibleForTesting
   public static final float SPEED_MODIFIER = 0.5F;
   private int updateMerchantTimer;
   private boolean increaseProfessionLevelOnUpdate;
   @Nullable
   private Player lastTradedPlayer;
   private boolean chasing;
   private byte foodLevel;
   private final GossipContainer gossips = new GossipContainer();
   private long lastGossipTime;
   private long lastGossipDecayTime;
   private int villagerXp;
   private long lastRestockGameTime;
   private int numberOfRestocksToday;
   private long lastRestockCheckDayTime;
   private boolean assignProfessionWhenSpawned;
   private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
      MemoryModuleType.HOME,
      MemoryModuleType.JOB_SITE,
      MemoryModuleType.POTENTIAL_JOB_SITE,
      MemoryModuleType.MEETING_POINT,
      MemoryModuleType.NEAREST_LIVING_ENTITIES,
      MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
      MemoryModuleType.VISIBLE_VILLAGER_BABIES,
      MemoryModuleType.NEAREST_PLAYERS,
      MemoryModuleType.NEAREST_VISIBLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
      MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
      MemoryModuleType.WALK_TARGET,
      MemoryModuleType.LOOK_TARGET,
      MemoryModuleType.INTERACTION_TARGET,
      MemoryModuleType.BREED_TARGET,
      MemoryModuleType.PATH,
      MemoryModuleType.DOORS_TO_CLOSE,
      MemoryModuleType.NEAREST_BED,
      MemoryModuleType.HURT_BY,
      MemoryModuleType.HURT_BY_ENTITY,
      MemoryModuleType.NEAREST_HOSTILE,
      MemoryModuleType.SECONDARY_JOB_SITE,
      MemoryModuleType.HIDING_PLACE,
      MemoryModuleType.HEARD_BELL_TIME,
      MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
      MemoryModuleType.LAST_SLEPT,
      MemoryModuleType.LAST_WOKEN,
      MemoryModuleType.LAST_WORKED_AT_POI,
      MemoryModuleType.GOLEM_DETECTED_RECENTLY
   );
   private static final ImmutableList<SensorType<? extends Sensor<? super Villager>>> SENSOR_TYPES = ImmutableList.of(
      SensorType.NEAREST_LIVING_ENTITIES,
      SensorType.NEAREST_PLAYERS,
      SensorType.NEAREST_ITEMS,
      SensorType.NEAREST_BED,
      SensorType.HURT_BY,
      SensorType.VILLAGER_HOSTILES,
      SensorType.VILLAGER_BABIES,
      SensorType.SECONDARY_POIS,
      SensorType.GOLEM_DETECTED
   );
   public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, PoiType>> POI_MEMORIES = ImmutableMap.of(
      MemoryModuleType.HOME,
      (BiPredicate)(var0, var1) -> var1 == PoiType.HOME,
      MemoryModuleType.JOB_SITE,
      (BiPredicate)(var0, var1) -> var0.getVillagerData().getProfession().getJobPoiType() == var1,
      MemoryModuleType.POTENTIAL_JOB_SITE,
      (BiPredicate)(var0, var1) -> PoiType.ALL_JOBS.test(var1),
      MemoryModuleType.MEETING_POINT,
      (BiPredicate)(var0, var1) -> var1 == PoiType.MEETING
   );

   public Villager(EntityType<? extends Villager> var1, Level var2) {
      this(â˜ƒ, â˜ƒ, VillagerType.PLAINS);
   }

   public Villager(EntityType<? extends Villager> var1, Level var2, VillagerType var3) {
      super(â˜ƒ, â˜ƒ);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.getNavigation().setCanFloat(true);
      this.setCanPickUpLoot(true);
      this.setVillagerData(this.getVillagerData().setType(â˜ƒ).setProfession(VillagerProfession.NONE));
   }

   @Override
   public Brain<Villager> getBrain() {
      return super.getBrain();
   }

   @Override
   protected Brain.Provider<Villager> brainProvider() {
      return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
   }

   @Override
   protected Brain<?> makeBrain(Dynamic<?> var1) {
      Brain<Villager> â˜ƒ = this.brainProvider().makeBrain(â˜ƒ);
      this.registerBrainGoals(â˜ƒ);
      return â˜ƒ;
   }

   public void refreshBrain(ServerLevel var1) {
      Brain<Villager> â˜ƒ = this.getBrain();
      â˜ƒ.stopAll(â˜ƒ, this);
      this.brain = â˜ƒ.copyWithoutBehaviors();
      this.registerBrainGoals(this.getBrain());
   }

   private void registerBrainGoals(Brain<Villager> var1) {
      VillagerProfession â˜ƒ = this.getVillagerData().getProfession();
      if (this.isBaby()) {
         â˜ƒ.setSchedule(Schedule.VILLAGER_BABY);
         â˜ƒ.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
      } else {
         â˜ƒ.setSchedule(Schedule.VILLAGER_DEFAULT);
         â˜ƒ.addActivityWithConditions(
            Activity.WORK, VillagerGoalPackages.getWorkPackage(â˜ƒ, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT))
         );
      }

      â˜ƒ.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(â˜ƒ, 0.5F));
      â˜ƒ.addActivityWithConditions(
         Activity.MEET, VillagerGoalPackages.getMeetPackage(â˜ƒ, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT))
      );
      â˜ƒ.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(â˜ƒ, 0.5F));
      â˜ƒ.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(â˜ƒ, 0.5F));
      â˜ƒ.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(â˜ƒ, 0.5F));
      â˜ƒ.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(â˜ƒ, 0.5F));
      â˜ƒ.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(â˜ƒ, 0.5F));
      â˜ƒ.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(â˜ƒ, 0.5F));
      â˜ƒ.setCoreActivities(ImmutableSet.of(Activity.CORE));
      â˜ƒ.setDefaultActivity(Activity.IDLE);
      â˜ƒ.setActiveActivityIfPossible(Activity.IDLE);
      â˜ƒ.updateActivityFromSchedule(this.level.getDayTime(), this.level.getGameTime());
   }

   @Override
   protected void ageBoundaryReached() {
      super.ageBoundaryReached();
      if (this.level instanceof ServerLevel) {
         this.refreshBrain((ServerLevel)this.level);
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 48.0);
   }

   public boolean assignProfessionWhenSpawned() {
      return this.assignProfessionWhenSpawned;
   }

   @Override
   protected void customServerAiStep() {
      this.level.getProfiler().push("villagerBrain");
      this.getBrain().tick((ServerLevel)this.level, this);
      this.level.getProfiler().pop();
      if (this.assignProfessionWhenSpawned) {
         this.assignProfessionWhenSpawned = false;
      }

      if (!this.isTrading() && this.updateMerchantTimer > 0) {
         --this.updateMerchantTimer;
         if (this.updateMerchantTimer <= 0) {
            if (this.increaseProfessionLevelOnUpdate) {
               this.increaseMerchantCareer();
               this.increaseProfessionLevelOnUpdate = false;
            }

            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
         }
      }

      if (this.lastTradedPlayer != null && this.level instanceof ServerLevel) {
         ((ServerLevel)this.level).onReputationEvent(ReputationEventType.TRADE, this.lastTradedPlayer, this);
         this.level.broadcastEntityEvent(this, (byte)14);
         this.lastTradedPlayer = null;
      }

      if (!this.isNoAi() && this.random.nextInt(100) == 0) {
         Raid â˜ƒ = ((ServerLevel)this.level).getRaidAt(this.blockPosition());
         if (â˜ƒ != null && â˜ƒ.isActive() && !â˜ƒ.isOver()) {
            this.level.broadcastEntityEvent(this, (byte)42);
         }
      }

      if (this.getVillagerData().getProfession() == VillagerProfession.NONE && this.isTrading()) {
         this.stopTrading();
      }

      super.customServerAiStep();
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getUnhappyCounter() > 0) {
         this.setUnhappyCounter(this.getUnhappyCounter() - 1);
      }

      this.maybeDecayGossip();
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.VILLAGER_SPAWN_EGG) || !this.isAlive() || this.isTrading() || this.isSleeping()) {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      } else if (this.isBaby()) {
         this.setUnhappy();
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         boolean â˜ƒ = this.getOffers().isEmpty();
         if (â˜ƒ == InteractionHand.MAIN_HAND) {
            if (â˜ƒ && !this.level.isClientSide) {
               this.setUnhappy();
            }

            â˜ƒ.awardStat(Stats.TALKED_TO_VILLAGER);
         }

         if (â˜ƒ) {
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         } else {
            if (!this.level.isClientSide && !this.offers.isEmpty()) {
               this.startTrading(â˜ƒ);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }
      }
   }

   private void setUnhappy() {
      this.setUnhappyCounter(40);
      if (!this.level.isClientSide()) {
         this.playSound(SoundEvents.VILLAGER_NO, this.getSoundVolume(), this.getVoicePitch());
      }
   }

   private void startTrading(Player var1) {
      this.updateSpecialPrices(â˜ƒ);
      this.setTradingPlayer(â˜ƒ);
      this.openTradingScreen(â˜ƒ, this.getDisplayName(), this.getVillagerData().getLevel());
   }

   @Override
   public void setTradingPlayer(@Nullable Player var1) {
      boolean â˜ƒ = this.getTradingPlayer() != null && â˜ƒ == null;
      super.setTradingPlayer(â˜ƒ);
      if (â˜ƒ) {
         this.stopTrading();
      }
   }

   @Override
   protected void stopTrading() {
      super.stopTrading();
      this.resetSpecialPrices();
   }

   private void resetSpecialPrices() {
      for(MerchantOffer â˜ƒ : this.getOffers()) {
         â˜ƒ.resetSpecialPriceDiff();
      }
   }

   @Override
   public boolean canRestock() {
      return true;
   }

   public void restock() {
      this.updateDemand();

      for(MerchantOffer â˜ƒ : this.getOffers()) {
         â˜ƒ.resetUses();
      }

      this.lastRestockGameTime = this.level.getGameTime();
      ++this.numberOfRestocksToday;
   }

   private boolean needsToRestock() {
      for(MerchantOffer â˜ƒ : this.getOffers()) {
         if (â˜ƒ.needsRestock()) {
            return true;
         }
      }

      return false;
   }

   private boolean allowedToRestock() {
      return this.numberOfRestocksToday == 0 || this.numberOfRestocksToday < 2 && this.level.getGameTime() > this.lastRestockGameTime + 2400L;
   }

   public boolean shouldRestock() {
      long â˜ƒ = this.lastRestockGameTime + 12000L;
      long â˜ƒx = this.level.getGameTime();
      boolean â˜ƒxx = â˜ƒx > â˜ƒ;
      long â˜ƒxxx = this.level.getDayTime();
      if (this.lastRestockCheckDayTime > 0L) {
         long â˜ƒxxxx = this.lastRestockCheckDayTime / 24000L;
         long â˜ƒxxxxx = â˜ƒxxx / 24000L;
         â˜ƒxx |= â˜ƒxxxxx > â˜ƒxxxx;
      }

      this.lastRestockCheckDayTime = â˜ƒxxx;
      if (â˜ƒxx) {
         this.lastRestockGameTime = â˜ƒx;
         this.resetNumberOfRestocks();
      }

      return this.allowedToRestock() && this.needsToRestock();
   }

   private void catchUpDemand() {
      int â˜ƒ = 2 - this.numberOfRestocksToday;
      if (â˜ƒ > 0) {
         for(MerchantOffer â˜ƒx : this.getOffers()) {
            â˜ƒx.resetUses();
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.updateDemand();
      }
   }

   private void updateDemand() {
      for(MerchantOffer â˜ƒ : this.getOffers()) {
         â˜ƒ.updateDemand();
      }
   }

   private void updateSpecialPrices(Player var1) {
      int â˜ƒ = this.getPlayerReputation(â˜ƒ);
      if (â˜ƒ != 0) {
         for(MerchantOffer â˜ƒx : this.getOffers()) {
            â˜ƒx.addToSpecialPriceDiff(-Mth.floor((float)â˜ƒ * â˜ƒx.getPriceMultiplier()));
         }
      }

      if (â˜ƒ.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
         MobEffectInstance â˜ƒ = â˜ƒ.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
         int â˜ƒx = â˜ƒ.getAmplifier();

         for(MerchantOffer â˜ƒxx : this.getOffers()) {
            double â˜ƒxxx = 0.3 + 0.0625 * (double)â˜ƒx;
            int â˜ƒxxxx = (int)Math.floor(â˜ƒxxx * (double)â˜ƒxx.getBaseCostA().getCount());
            â˜ƒxx.addToSpecialPriceDiff(-Math.max(â˜ƒxxxx, 1));
         }
      }
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent(var1x -> â˜ƒ.put("VillagerData", var1x));
      â˜ƒ.putByte("FoodLevel", this.foodLevel);
      â˜ƒ.put("Gossips", this.gossips.store(NbtOps.INSTANCE).getValue());
      â˜ƒ.putInt("Xp", this.villagerXp);
      â˜ƒ.putLong("LastRestock", this.lastRestockGameTime);
      â˜ƒ.putLong("LastGossipDecay", this.lastGossipDecayTime);
      â˜ƒ.putInt("RestocksToday", this.numberOfRestocksToday);
      if (this.assignProfessionWhenSpawned) {
         â˜ƒ.putBoolean("AssignProfessionWhenSpawned", true);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("VillagerData", 10)) {
         DataResult<VillagerData> â˜ƒ = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ.get("VillagerData")));
         â˜ƒ.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
      }

      if (â˜ƒ.contains("Offers", 10)) {
         this.offers = new MerchantOffers(â˜ƒ.getCompound("Offers"));
      }

      if (â˜ƒ.contains("FoodLevel", 1)) {
         this.foodLevel = â˜ƒ.getByte("FoodLevel");
      }

      ListTag â˜ƒ = â˜ƒ.getList("Gossips", 10);
      this.gossips.update(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ));
      if (â˜ƒ.contains("Xp", 3)) {
         this.villagerXp = â˜ƒ.getInt("Xp");
      }

      this.lastRestockGameTime = â˜ƒ.getLong("LastRestock");
      this.lastGossipDecayTime = â˜ƒ.getLong("LastGossipDecay");
      this.setCanPickUpLoot(true);
      if (this.level instanceof ServerLevel) {
         this.refreshBrain((ServerLevel)this.level);
      }

      this.numberOfRestocksToday = â˜ƒ.getInt("RestocksToday");
      if (â˜ƒ.contains("AssignProfessionWhenSpawned")) {
         this.assignProfessionWhenSpawned = â˜ƒ.getBoolean("AssignProfessionWhenSpawned");
      }
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return false;
   }

   @Nullable
   @Override
   protected SoundEvent getAmbientSound() {
      if (this.isSleeping()) {
         return null;
      } else {
         return this.isTrading() ? SoundEvents.VILLAGER_TRADE : SoundEvents.VILLAGER_AMBIENT;
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.VILLAGER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.VILLAGER_DEATH;
   }

   public void playWorkSound() {
      SoundEvent â˜ƒ = this.getVillagerData().getProfession().getWorkSound();
      if (â˜ƒ != null) {
         this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
      }
   }

   @Override
   public void setVillagerData(VillagerData var1) {
      VillagerData â˜ƒ = this.getVillagerData();
      if (â˜ƒ.getProfession() != â˜ƒ.getProfession()) {
         this.offers = null;
      }

      this.entityData.set(DATA_VILLAGER_DATA, â˜ƒ);
   }

   @Override
   public VillagerData getVillagerData() {
      return this.entityData.get(DATA_VILLAGER_DATA);
   }

   @Override
   protected void rewardTradeXp(MerchantOffer var1) {
      int â˜ƒ = 3 + this.random.nextInt(4);
      this.villagerXp += â˜ƒ.getXp();
      this.lastTradedPlayer = this.getTradingPlayer();
      if (this.shouldIncreaseLevel()) {
         this.updateMerchantTimer = 40;
         this.increaseProfessionLevelOnUpdate = true;
         â˜ƒ += 5;
      }

      if (â˜ƒ.shouldRewardExp()) {
         this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY() + 0.5, this.getZ(), â˜ƒ));
      }
   }

   public void setChasing(boolean var1) {
      this.chasing = â˜ƒ;
   }

   public boolean isChasing() {
      return this.chasing;
   }

   @Override
   public void setLastHurtByMob(@Nullable LivingEntity var1) {
      if (â˜ƒ != null && this.level instanceof ServerLevel) {
         ((ServerLevel)this.level).onReputationEvent(ReputationEventType.VILLAGER_HURT, â˜ƒ, this);
         if (this.isAlive() && â˜ƒ instanceof Player) {
            this.level.broadcastEntityEvent(this, (byte)13);
         }
      }

      super.setLastHurtByMob(â˜ƒ);
   }

   @Override
   public void die(DamageSource var1) {
      LOGGER.info("Villager {} died, message: '{}'", this, â˜ƒ.getLocalizedDeathMessage(this).getString());
      Entity â˜ƒ = â˜ƒ.getEntity();
      if (â˜ƒ != null) {
         this.tellWitnessesThatIWasMurdered(â˜ƒ);
      }

      this.releaseAllPois();
      super.die(â˜ƒ);
   }

   private void releaseAllPois() {
      this.releasePoi(MemoryModuleType.HOME);
      this.releasePoi(MemoryModuleType.JOB_SITE);
      this.releasePoi(MemoryModuleType.POTENTIAL_JOB_SITE);
      this.releasePoi(MemoryModuleType.MEETING_POINT);
   }

   private void tellWitnessesThatIWasMurdered(Entity var1) {
      if (this.level instanceof ServerLevel) {
         Optional<List<LivingEntity>> â˜ƒ = this.brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
         if (â˜ƒ.isPresent()) {
            ServerLevel â˜ƒx = (ServerLevel)this.level;
            ((List)â˜ƒ.get())
               .stream()
               .filter(var0 -> var0 instanceof ReputationEventHandler)
               .forEach(var2x -> â˜ƒ.onReputationEvent(ReputationEventType.VILLAGER_KILLED, â˜ƒ, (ReputationEventHandler)var2x));
         }
      }
   }

   public void releasePoi(MemoryModuleType<GlobalPos> var1) {
      if (this.level instanceof ServerLevel) {
         MinecraftServer â˜ƒ = ((ServerLevel)this.level).getServer();
         this.brain.getMemory(â˜ƒ).ifPresent(var3 -> {
            ServerLevel â˜ƒ = â˜ƒ.getLevel(var3.dimension());
            if (â˜ƒ != null) {
               PoiManager â˜ƒx = â˜ƒ.getPoiManager();
               Optional<PoiType> â˜ƒxx = â˜ƒx.getType(var3.pos());
               BiPredicate<Villager, PoiType> â˜ƒxxx = (BiPredicate)POI_MEMORIES.get(â˜ƒ);
               if (â˜ƒxx.isPresent() && â˜ƒxxx.test(this, (PoiType)â˜ƒxx.get())) {
                  â˜ƒx.release(var3.pos());
                  DebugPackets.sendPoiTicketCountPacket(â˜ƒ, var3.pos());
               }
            }
         });
      }
   }

   @Override
   public boolean canBreed() {
      return this.foodLevel + this.countFoodPointsInInventory() >= 12 && this.getAge() == 0;
   }

   private boolean hungry() {
      return this.foodLevel < 12;
   }

   private void eatUntilFull() {
      if (this.hungry() && this.countFoodPointsInInventory() != 0) {
         for(int â˜ƒ = 0; â˜ƒ < this.getInventory().getContainerSize(); ++â˜ƒ) {
            ItemStack â˜ƒx = this.getInventory().getItem(â˜ƒ);
            if (!â˜ƒx.isEmpty()) {
               Integer â˜ƒxx = (Integer)FOOD_POINTS.get(â˜ƒx.getItem());
               if (â˜ƒxx != null) {
                  int â˜ƒxxx = â˜ƒx.getCount();

                  for(int â˜ƒxxxx = â˜ƒxxx; â˜ƒxxxx > 0; --â˜ƒxxxx) {
                     this.foodLevel = (byte)(this.foodLevel + â˜ƒxx);
                     this.getInventory().removeItem(â˜ƒ, 1);
                     if (!this.hungry()) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   public int getPlayerReputation(Player var1) {
      return this.gossips.getReputation(â˜ƒ.getUUID(), var0 -> true);
   }

   private void digestFood(int var1) {
      this.foodLevel = (byte)(this.foodLevel - â˜ƒ);
   }

   public void eatAndDigestFood() {
      this.eatUntilFull();
      this.digestFood(12);
   }

   public void setOffers(MerchantOffers var1) {
      this.offers = â˜ƒ;
   }

   private boolean shouldIncreaseLevel() {
      int â˜ƒ = this.getVillagerData().getLevel();
      return VillagerData.canLevelUp(â˜ƒ) && this.villagerXp >= VillagerData.getMaxXpPerLevel(â˜ƒ);
   }

   private void increaseMerchantCareer() {
      this.setVillagerData(this.getVillagerData().setLevel(this.getVillagerData().getLevel() + 1));
      this.updateTrades();
   }

   @Override
   protected Component getTypeName() {
      return new TranslatableComponent(
         this.getType().getDescriptionId() + "." + Registry.VILLAGER_PROFESSION.getKey(this.getVillagerData().getProfession()).getPath()
      );
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 12) {
         this.addParticlesAroundSelf(ParticleTypes.HEART);
      } else if (â˜ƒ == 13) {
         this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
      } else if (â˜ƒ == 14) {
         this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
      } else if (â˜ƒ == 42) {
         this.addParticlesAroundSelf(ParticleTypes.SPLASH);
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (â˜ƒ == MobSpawnType.BREEDING) {
         this.setVillagerData(this.getVillagerData().setProfession(VillagerProfession.NONE));
      }

      if (â˜ƒ == MobSpawnType.COMMAND || â˜ƒ == MobSpawnType.SPAWN_EGG || â˜ƒ == MobSpawnType.SPAWNER || â˜ƒ == MobSpawnType.DISPENSER) {
         this.setVillagerData(this.getVillagerData().setType(VillagerType.byBiome(â˜ƒ.getBiomeName(this.blockPosition()))));
      }

      if (â˜ƒ == MobSpawnType.STRUCTURE) {
         this.assignProfessionWhenSpawned = true;
      }

      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Villager getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      double â˜ƒx = this.random.nextDouble();
      VillagerType â˜ƒ;
      if (â˜ƒx < 0.5) {
         â˜ƒ = VillagerType.byBiome(â˜ƒ.getBiomeName(this.blockPosition()));
      } else if (â˜ƒx < 0.75) {
         â˜ƒ = this.getVillagerData().getType();
      } else {
         â˜ƒ = ((Villager)â˜ƒ).getVillagerData().getType();
      }

      Villager â˜ƒ = new Villager(EntityType.VILLAGER, â˜ƒ, â˜ƒ);
      â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ.blockPosition()), MobSpawnType.BREEDING, null, null);
      return â˜ƒ;
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
      if (â˜ƒ.getDifficulty() != Difficulty.PEACEFUL) {
         LOGGER.info("Villager {} was struck by lightning {}.", this, â˜ƒ);
         Witch â˜ƒ = EntityType.WITCH.create(â˜ƒ);
         â˜ƒ.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
         â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ.blockPosition()), MobSpawnType.CONVERSION, null, null);
         â˜ƒ.setNoAi(this.isNoAi());
         if (this.hasCustomName()) {
            â˜ƒ.setCustomName(this.getCustomName());
            â˜ƒ.setCustomNameVisible(this.isCustomNameVisible());
         }

         â˜ƒ.setPersistenceRequired();
         â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
         this.releaseAllPois();
         this.discard();
      } else {
         super.thunderHit(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      ItemStack â˜ƒ = â˜ƒ.getItem();
      if (this.wantsToPickUp(â˜ƒ)) {
         SimpleContainer â˜ƒx = this.getInventory();
         boolean â˜ƒxx = â˜ƒx.canAddItem(â˜ƒ);
         if (!â˜ƒxx) {
            return;
         }

         this.onItemPickup(â˜ƒ);
         this.take(â˜ƒ, â˜ƒ.getCount());
         ItemStack â˜ƒx = â˜ƒx.addItem(â˜ƒ);
         if (â˜ƒx.isEmpty()) {
            â˜ƒ.discard();
         } else {
            â˜ƒ.setCount(â˜ƒx.getCount());
         }
      }
   }

   @Override
   public boolean wantsToPickUp(ItemStack var1) {
      Item â˜ƒ = â˜ƒ.getItem();
      return (WANTED_ITEMS.contains(â˜ƒ) || this.getVillagerData().getProfession().getRequestedItems().contains(â˜ƒ)) && this.getInventory().canAddItem(â˜ƒ);
   }

   public boolean hasExcessFood() {
      return this.countFoodPointsInInventory() >= 24;
   }

   public boolean wantsMoreFood() {
      return this.countFoodPointsInInventory() < 12;
   }

   private int countFoodPointsInInventory() {
      SimpleContainer â˜ƒ = this.getInventory();
      return FOOD_POINTS.entrySet().stream().mapToInt(var1x -> â˜ƒ.countItem((Item)var1x.getKey()) * var1x.getValue()).sum();
   }

   public boolean hasFarmSeeds() {
      return this.getInventory().hasAnyOf(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
   }

   @Override
   protected void updateTrades() {
      VillagerData â˜ƒ = this.getVillagerData();
      Int2ObjectMap<VillagerTrades.ItemListing[]> â˜ƒx = (Int2ObjectMap)VillagerTrades.TRADES.get(â˜ƒ.getProfession());
      if (â˜ƒx != null && !â˜ƒx.isEmpty()) {
         VillagerTrades.ItemListing[] â˜ƒxx = (VillagerTrades.ItemListing[])â˜ƒx.get(â˜ƒ.getLevel());
         if (â˜ƒxx != null) {
            MerchantOffers â˜ƒxxx = this.getOffers();
            this.addOffersFromItemListings(â˜ƒxxx, â˜ƒxx, 2);
         }
      }
   }

   public void gossip(ServerLevel var1, Villager var2, long var3) {
      if ((â˜ƒ < this.lastGossipTime || â˜ƒ >= this.lastGossipTime + 1200L) && (â˜ƒ < â˜ƒ.lastGossipTime || â˜ƒ >= â˜ƒ.lastGossipTime + 1200L)) {
         this.gossips.transferFrom(â˜ƒ.gossips, this.random, 10);
         this.lastGossipTime = â˜ƒ;
         â˜ƒ.lastGossipTime = â˜ƒ;
         this.spawnGolemIfNeeded(â˜ƒ, â˜ƒ, 5);
      }
   }

   private void maybeDecayGossip() {
      long â˜ƒ = this.level.getGameTime();
      if (this.lastGossipDecayTime == 0L) {
         this.lastGossipDecayTime = â˜ƒ;
      } else if (â˜ƒ >= this.lastGossipDecayTime + 24000L) {
         this.gossips.decay();
         this.lastGossipDecayTime = â˜ƒ;
      }
   }

   public void spawnGolemIfNeeded(ServerLevel var1, long var2, int var4) {
      if (this.wantsToSpawnGolem(â˜ƒ)) {
         AABB â˜ƒ = this.getBoundingBox().inflate(10.0, 10.0, 10.0);
         List<Villager> â˜ƒx = â˜ƒ.getEntitiesOfClass(Villager.class, â˜ƒ);
         List<Villager> â˜ƒxx = (List)â˜ƒx.stream().filter(var2x -> var2x.wantsToSpawnGolem(â˜ƒ)).limit(5L).collect(Collectors.toList());
         if (â˜ƒxx.size() >= â˜ƒ) {
            IronGolem â˜ƒxxx = this.trySpawnGolem(â˜ƒ);
            if (â˜ƒxxx != null) {
               â˜ƒx.forEach(GolemSensor::golemDetected);
            }
         }
      }
   }

   public boolean wantsToSpawnGolem(long var1) {
      if (!this.golemSpawnConditionsMet(this.level.getGameTime())) {
         return false;
      } else {
         return !this.brain.hasMemoryValue(MemoryModuleType.GOLEM_DETECTED_RECENTLY);
      }
   }

   @Nullable
   private IronGolem trySpawnGolem(ServerLevel var1) {
      BlockPos â˜ƒ = this.blockPosition();

      for(int â˜ƒx = 0; â˜ƒx < 10; ++â˜ƒx) {
         double â˜ƒxx = (double)(â˜ƒ.random.nextInt(16) - 8);
         double â˜ƒxxx = (double)(â˜ƒ.random.nextInt(16) - 8);
         BlockPos â˜ƒxxxx = this.findSpawnPositionForGolemInColumn(â˜ƒ, â˜ƒxx, â˜ƒxxx);
         if (â˜ƒxxxx != null) {
            IronGolem â˜ƒxxxxx = EntityType.IRON_GOLEM.create(â˜ƒ, null, null, null, â˜ƒxxxx, MobSpawnType.MOB_SUMMONED, false, false);
            if (â˜ƒxxxxx != null) {
               if (â˜ƒxxxxx.checkSpawnRules(â˜ƒ, MobSpawnType.MOB_SUMMONED) && â˜ƒxxxxx.checkSpawnObstruction(â˜ƒ)) {
                  â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxxxx);
                  return â˜ƒxxxxx;
               }

               â˜ƒxxxxx.discard();
            }
         }
      }

      return null;
   }

   @Nullable
   private BlockPos findSpawnPositionForGolemInColumn(BlockPos var1, double var2, double var4) {
      int â˜ƒ = 6;
      BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ, 6.0, â˜ƒ);
      BlockState â˜ƒxx = this.level.getBlockState(â˜ƒx);

      for(int â˜ƒxxx = 6; â˜ƒxxx >= -6; --â˜ƒxxx) {
         BlockPos â˜ƒxxxx = â˜ƒx;
         BlockState â˜ƒxxxxx = â˜ƒxx;
         â˜ƒx = â˜ƒx.below();
         â˜ƒxx = this.level.getBlockState(â˜ƒx);
         if ((â˜ƒxxxxx.isAir() || â˜ƒxxxxx.getMaterial().isLiquid()) && â˜ƒxx.getMaterial().isSolidBlocking()) {
            return â˜ƒxxxx;
         }
      }

      return null;
   }

   @Override
   public void onReputationEventFrom(ReputationEventType var1, Entity var2) {
      if (â˜ƒ == ReputationEventType.ZOMBIE_VILLAGER_CURED) {
         this.gossips.add(â˜ƒ.getUUID(), GossipType.MAJOR_POSITIVE, 20);
         this.gossips.add(â˜ƒ.getUUID(), GossipType.MINOR_POSITIVE, 25);
      } else if (â˜ƒ == ReputationEventType.TRADE) {
         this.gossips.add(â˜ƒ.getUUID(), GossipType.TRADING, 2);
      } else if (â˜ƒ == ReputationEventType.VILLAGER_HURT) {
         this.gossips.add(â˜ƒ.getUUID(), GossipType.MINOR_NEGATIVE, 25);
      } else if (â˜ƒ == ReputationEventType.VILLAGER_KILLED) {
         this.gossips.add(â˜ƒ.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
      }
   }

   @Override
   public int getVillagerXp() {
      return this.villagerXp;
   }

   public void setVillagerXp(int var1) {
      this.villagerXp = â˜ƒ;
   }

   private void resetNumberOfRestocks() {
      this.catchUpDemand();
      this.numberOfRestocksToday = 0;
   }

   public GossipContainer getGossips() {
      return this.gossips;
   }

   public void setGossips(Tag var1) {
      this.gossips.update(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ));
   }

   @Override
   protected void sendDebugPackets() {
      super.sendDebugPackets();
      DebugPackets.sendEntityBrain(this);
   }

   @Override
   public void startSleeping(BlockPos var1) {
      super.startSleeping(â˜ƒ);
      this.brain.setMemory(MemoryModuleType.LAST_SLEPT, this.level.getGameTime());
      this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
      this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }

   @Override
   public void stopSleeping() {
      super.stopSleeping();
      this.brain.setMemory(MemoryModuleType.LAST_WOKEN, this.level.getGameTime());
   }

   private boolean golemSpawnConditionsMet(long var1) {
      Optional<Long> â˜ƒ = this.brain.getMemory(MemoryModuleType.LAST_SLEPT);
      if (â˜ƒ.isPresent()) {
         return â˜ƒ - â˜ƒ.get() < 24000L;
      } else {
         return false;
      }
   }
}
