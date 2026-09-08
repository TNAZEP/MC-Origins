package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CopyMemoryWithExpiry;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.GoToCelebrateLocation;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.Mount;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StartCelebratingIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class PiglinAi {
   public static final int REPELLENT_DETECTION_RANGE_HORIZONTAL = 8;
   public static final int REPELLENT_DETECTION_RANGE_VERTICAL = 4;
   public static final Item BARTERING_ITEM = Items.GOLD_INGOT;
   private static final int PLAYER_ANGER_RANGE = 16;
   private static final int ANGER_DURATION = 600;
   private static final int ADMIRE_DURATION = 120;
   private static final int MAX_DISTANCE_TO_WALK_TO_ITEM = 9;
   private static final int MAX_TIME_TO_WALK_TO_ITEM = 200;
   private static final int HOW_LONG_TIME_TO_DISABLE_ADMIRE_WALKING_IF_CANT_REACH_ITEM = 200;
   private static final int CELEBRATION_TIME = 300;
   private static final UniformInt TIME_BETWEEN_HUNTS = TimeUtil.rangeOfSeconds(30, 120);
   private static final int BABY_FLEE_DURATION_AFTER_GETTING_HIT = 100;
   private static final int HIT_BY_PLAYER_MEMORY_TIMEOUT = 400;
   private static final int MAX_WALK_DISTANCE_TO_START_RIDING = 8;
   private static final UniformInt RIDE_START_INTERVAL = TimeUtil.rangeOfSeconds(10, 40);
   private static final UniformInt RIDE_DURATION = TimeUtil.rangeOfSeconds(10, 30);
   private static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(5, 20);
   private static final int MELEE_ATTACK_COOLDOWN = 20;
   private static final int EAT_COOLDOWN = 200;
   private static final int DESIRED_DISTANCE_FROM_ENTITY_WHEN_AVOIDING = 12;
   private static final int MAX_LOOK_DIST = 8;
   private static final int MAX_LOOK_DIST_FOR_PLAYER_HOLDING_LOVED_ITEM = 14;
   private static final int INTERACTION_RANGE = 8;
   private static final int MIN_DESIRED_DIST_FROM_TARGET_WHEN_HOLDING_CROSSBOW = 5;
   private static final float SPEED_WHEN_STRAFING_BACK_FROM_TARGET = 0.75F;
   private static final int DESIRED_DISTANCE_FROM_ZOMBIFIED = 6;
   private static final UniformInt AVOID_ZOMBIFIED_DURATION = TimeUtil.rangeOfSeconds(5, 7);
   private static final UniformInt BABY_AVOID_NEMESIS_DURATION = TimeUtil.rangeOfSeconds(5, 7);
   private static final float PROBABILITY_OF_CELEBRATION_DANCE = 0.1F;
   private static final float SPEED_MULTIPLIER_WHEN_AVOIDING = 1.0F;
   private static final float SPEED_MULTIPLIER_WHEN_RETREATING = 1.0F;
   private static final float SPEED_MULTIPLIER_WHEN_MOUNTING = 0.8F;
   private static final float SPEED_MULTIPLIER_WHEN_GOING_TO_WANTED_ITEM = 1.0F;
   private static final float SPEED_MULTIPLIER_WHEN_GOING_TO_CELEBRATE_LOCATION = 1.0F;
   private static final float SPEED_MULTIPLIER_WHEN_DANCING = 0.6F;
   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.6F;

   protected static Brain<?> makeBrain(Piglin var0, Brain<Piglin> var1) {
      initCoreActivity(â˜ƒ);
      initIdleActivity(â˜ƒ);
      initAdmireItemActivity(â˜ƒ);
      initFightActivity(â˜ƒ, â˜ƒ);
      initCelebrateActivity(â˜ƒ);
      initRetreatActivity(â˜ƒ);
      initRideHoglinActivity(â˜ƒ);
      â˜ƒ.setCoreActivities(ImmutableSet.of(Activity.CORE));
      â˜ƒ.setDefaultActivity(Activity.IDLE);
      â˜ƒ.useDefaultActivity();
      return â˜ƒ;
   }

   protected static void initMemories(Piglin var0) {
      int â˜ƒ = TIME_BETWEEN_HUNTS.sample(â˜ƒ.level.random);
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, true, (long)â˜ƒ);
   }

   private static void initCoreActivity(Brain<Piglin> var0) {
      â˜ƒ.addActivity(
         Activity.CORE,
         0,
         ImmutableList.of(
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink(),
            new InteractWithDoor(),
            babyAvoidNemesis(),
            avoidZombified(),
            new StopHoldingItemIfNoLongerAdmiring(),
            new StartAdmiringItemIfSeen(120),
            new StartCelebratingIfTargetDead(300, PiglinAi::wantsToDance),
            new StopBeingAngryIfTargetDead()
         )
      );
   }

   private static void initIdleActivity(Brain<Piglin> var0) {
      â˜ƒ.addActivity(
         Activity.IDLE,
         10,
         ImmutableList.of(
            new SetEntityLookTarget(PiglinAi::isPlayerHoldingLovedItem, 14.0F),
            new StartAttacking<>(AbstractPiglin::isAdult, PiglinAi::findNearestValidAttackTarget),
            new RunIf(Piglin::canHunt, new StartHuntingHoglin<>()),
            avoidRepellent(),
            babySometimesRideBabyHoglin(),
            createIdleLookBehaviors(),
            createIdleMovementBehaviors(),
            new SetLookAndInteract(EntityType.PLAYER, 4)
         )
      );
   }

   private static void initFightActivity(Piglin var0, Brain<Piglin> var1) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.FIGHT,
         10,
         ImmutableList.of(
            new StopAttackingIfTargetInvalid<>((Predicate<LivingEntity>)(var1x -> !isNearestValidAttackTarget(â˜ƒ, var1x))),
            new RunIf(PiglinAi::hasCrossbow, new BackUpIfTooClose<>(5, 0.75F)),
            new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0F),
            new MeleeAttack(20),
            new CrossbowAttack(),
            new RememberIfHoglinWasKilled(),
            new EraseMemoryIf(PiglinAi::isNearZombified, MemoryModuleType.ATTACK_TARGET)
         ),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static void initCelebrateActivity(Brain<Piglin> var0) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.CELEBRATE,
         10,
         ImmutableList.of(
            avoidRepellent(),
            new SetEntityLookTarget(PiglinAi::isPlayerHoldingLovedItem, 14.0F),
            new StartAttacking(AbstractPiglin::isAdult, PiglinAi::findNearestValidAttackTarget),
            new RunIf((Predicate)(var0x -> !var0x.isDancing()), new GoToCelebrateLocation(2, 1.0F)),
            new RunIf(Piglin::isDancing, new GoToCelebrateLocation(4, 0.6F)),
            new RunOne(
               ImmutableList.of(
                  Pair.of(new SetEntityLookTarget(EntityType.PIGLIN, 8.0F), 1), Pair.of(new RandomStroll(0.6F, 2, 1), 1), Pair.of(new DoNothing(10, 20), 1)
               )
            )
         ),
         MemoryModuleType.CELEBRATE_LOCATION
      );
   }

   private static void initAdmireItemActivity(Brain<Piglin> var0) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.ADMIRE_ITEM,
         10,
         ImmutableList.of(
            new GoToWantedItem<>(PiglinAi::isNotHoldingLovedItemInOffHand, 1.0F, true, 9),
            new StopAdmiringIfItemTooFarAway(9),
            new StopAdmiringIfTiredOfTryingToReachItem(200, 200)
         ),
         MemoryModuleType.ADMIRING_ITEM
      );
   }

   private static void initRetreatActivity(Brain<Piglin> var0) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.AVOID,
         10,
         ImmutableList.of(
            SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.0F, 12, true),
            createIdleLookBehaviors(),
            createIdleMovementBehaviors(),
            new EraseMemoryIf(PiglinAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)
         ),
         MemoryModuleType.AVOID_TARGET
      );
   }

   private static void initRideHoglinActivity(Brain<Piglin> var0) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.RIDE,
         10,
         ImmutableList.of(
            new Mount<>(0.8F),
            new SetEntityLookTarget(PiglinAi::isPlayerHoldingLovedItem, 8.0F),
            new RunIf(Entity::isPassenger, createIdleLookBehaviors()),
            new DismountOrSkipMounting(8, PiglinAi::wantsToStopRiding)
         ),
         MemoryModuleType.RIDE_TARGET
      );
   }

   private static RunOne<Piglin> createIdleLookBehaviors() {
      return new RunOne<>(
         ImmutableList.of(
            Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 8.0F), 1),
            Pair.of(new SetEntityLookTarget(EntityType.PIGLIN, 8.0F), 1),
            Pair.of(new SetEntityLookTarget(8.0F), 1),
            Pair.of(new DoNothing(30, 60), 1)
         )
      );
   }

   private static RunOne<Piglin> createIdleMovementBehaviors() {
      return new RunOne<>(
         ImmutableList.of(
            Pair.of(new RandomStroll(0.6F), 2),
            Pair.of(InteractWith.of(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
            Pair.of(new RunIf<>(PiglinAi::doesntSeeAnyPlayerHoldingLovedItem, new SetWalkTargetFromLookTarget(0.6F, 3)), 2),
            Pair.of(new DoNothing(30, 60), 1)
         )
      );
   }

   private static SetWalkTargetAwayFrom<BlockPos> avoidRepellent() {
      return SetWalkTargetAwayFrom.pos(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, false);
   }

   private static CopyMemoryWithExpiry<Piglin, LivingEntity> babyAvoidNemesis() {
      return new CopyMemoryWithExpiry<>(Piglin::isBaby, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, BABY_AVOID_NEMESIS_DURATION);
   }

   private static CopyMemoryWithExpiry<Piglin, LivingEntity> avoidZombified() {
      return new CopyMemoryWithExpiry<>(
         PiglinAi::isNearZombified, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.AVOID_TARGET, AVOID_ZOMBIFIED_DURATION
      );
   }

   protected static void updateActivity(Piglin var0) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      Activity â˜ƒx = (Activity)â˜ƒ.getActiveNonCoreActivity().orElse(null);
      â˜ƒ.setActiveActivityToFirstValid(
         ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, Activity.AVOID, Activity.CELEBRATE, Activity.RIDE, Activity.IDLE)
      );
      Activity â˜ƒxx = (Activity)â˜ƒ.getActiveNonCoreActivity().orElse(null);
      if (â˜ƒx != â˜ƒxx) {
         getSoundForCurrentActivity(â˜ƒ).ifPresent(â˜ƒ::playSound);
      }

      â˜ƒ.setAggressive(â˜ƒ.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
      if (!â˜ƒ.hasMemoryValue(MemoryModuleType.RIDE_TARGET) && isBabyRidingBaby(â˜ƒ)) {
         â˜ƒ.stopRiding();
      }

      if (!â˜ƒ.hasMemoryValue(MemoryModuleType.CELEBRATE_LOCATION)) {
         â˜ƒ.eraseMemory(MemoryModuleType.DANCING);
      }

      â˜ƒ.setDancing(â˜ƒ.hasMemoryValue(MemoryModuleType.DANCING));
   }

   private static boolean isBabyRidingBaby(Piglin var0) {
      if (!â˜ƒ.isBaby()) {
         return false;
      } else {
         Entity â˜ƒ = â˜ƒ.getVehicle();
         return â˜ƒ instanceof Piglin && ((Piglin)â˜ƒ).isBaby() || â˜ƒ instanceof Hoglin && ((Hoglin)â˜ƒ).isBaby();
      }
   }

   protected static void pickUpItem(Piglin var0, ItemEntity var1) {
      stopWalking(â˜ƒ);
      ItemStack â˜ƒ;
      if (â˜ƒ.getItem().is(Items.GOLD_NUGGET)) {
         â˜ƒ.take(â˜ƒ, â˜ƒ.getItem().getCount());
         â˜ƒ = â˜ƒ.getItem();
         â˜ƒ.discard();
      } else {
         â˜ƒ.take(â˜ƒ, 1);
         â˜ƒ = removeOneItemFromItemEntity(â˜ƒ);
      }

      if (isLovedItem(â˜ƒ)) {
         â˜ƒ.getBrain().eraseMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
         holdInOffhand(â˜ƒ, â˜ƒ);
         admireGoldItem(â˜ƒ);
      } else if (isFood(â˜ƒ) && !hasEatenRecently(â˜ƒ)) {
         eat(â˜ƒ);
      } else {
         boolean â˜ƒ = â˜ƒ.equipItemIfPossible(â˜ƒ);
         if (!â˜ƒ) {
            putInInventory(â˜ƒ, â˜ƒ);
         }
      }
   }

   private static void holdInOffhand(Piglin var0, ItemStack var1) {
      if (isHoldingItemInOffHand(â˜ƒ)) {
         â˜ƒ.spawnAtLocation(â˜ƒ.getItemInHand(InteractionHand.OFF_HAND));
      }

      â˜ƒ.holdInOffHand(â˜ƒ);
   }

   private static ItemStack removeOneItemFromItemEntity(ItemEntity var0) {
      ItemStack â˜ƒ = â˜ƒ.getItem();
      ItemStack â˜ƒx = â˜ƒ.split(1);
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.discard();
      } else {
         â˜ƒ.setItem(â˜ƒ);
      }

      return â˜ƒx;
   }

   protected static void stopHoldingOffHandItem(Piglin var0, boolean var1) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(InteractionHand.OFF_HAND);
      â˜ƒ.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
      if (â˜ƒ.isAdult()) {
         boolean â˜ƒx = isBarterCurrency(â˜ƒ);
         if (â˜ƒ && â˜ƒx) {
            throwItems(â˜ƒ, getBarterResponseItems(â˜ƒ));
         } else if (!â˜ƒx) {
            boolean â˜ƒx = â˜ƒ.equipItemIfPossible(â˜ƒ);
            if (!â˜ƒx) {
               putInInventory(â˜ƒ, â˜ƒ);
            }
         }
      } else {
         boolean â˜ƒ = â˜ƒ.equipItemIfPossible(â˜ƒ);
         if (!â˜ƒ) {
            ItemStack â˜ƒx = â˜ƒ.getMainHandItem();
            if (isLovedItem(â˜ƒx)) {
               putInInventory(â˜ƒ, â˜ƒx);
            } else {
               throwItems(â˜ƒ, Collections.singletonList(â˜ƒx));
            }

            â˜ƒ.holdInMainHand(â˜ƒ);
         }
      }
   }

   protected static void cancelAdmiring(Piglin var0) {
      if (isAdmiringItem(â˜ƒ) && !â˜ƒ.getOffhandItem().isEmpty()) {
         â˜ƒ.spawnAtLocation(â˜ƒ.getOffhandItem());
         â˜ƒ.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
      }
   }

   private static void putInInventory(Piglin var0, ItemStack var1) {
      ItemStack â˜ƒ = â˜ƒ.addToInventory(â˜ƒ);
      throwItemsTowardRandomPos(â˜ƒ, Collections.singletonList(â˜ƒ));
   }

   private static void throwItems(Piglin var0, List<ItemStack> var1) {
      Optional<Player> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
      if (â˜ƒ.isPresent()) {
         throwItemsTowardPlayer(â˜ƒ, (Player)â˜ƒ.get(), â˜ƒ);
      } else {
         throwItemsTowardRandomPos(â˜ƒ, â˜ƒ);
      }
   }

   private static void throwItemsTowardRandomPos(Piglin var0, List<ItemStack> var1) {
      throwItemsTowardPos(â˜ƒ, â˜ƒ, getRandomNearbyPos(â˜ƒ));
   }

   private static void throwItemsTowardPlayer(Piglin var0, Player var1, List<ItemStack> var2) {
      throwItemsTowardPos(â˜ƒ, â˜ƒ, â˜ƒ.position());
   }

   private static void throwItemsTowardPos(Piglin var0, List<ItemStack> var1, Vec3 var2) {
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.swing(InteractionHand.OFF_HAND);

         for(ItemStack â˜ƒ : â˜ƒ) {
            BehaviorUtils.throwItem(â˜ƒ, â˜ƒ, â˜ƒ.add(0.0, 1.0, 0.0));
         }
      }
   }

   private static List<ItemStack> getBarterResponseItems(Piglin var0) {
      LootTable â˜ƒ = â˜ƒ.level.getServer().getLootTables().get(BuiltInLootTables.PIGLIN_BARTERING);
      return â˜ƒ.getRandomItems(
         new LootContext.Builder((ServerLevel)â˜ƒ.level)
            .withParameter(LootContextParams.THIS_ENTITY, â˜ƒ)
            .withRandom(â˜ƒ.level.random)
            .create(LootContextParamSets.PIGLIN_BARTER)
      );
   }

   private static boolean wantsToDance(LivingEntity var0, LivingEntity var1) {
      if (â˜ƒ.getType() != EntityType.HOGLIN) {
         return false;
      } else {
         return new Random(â˜ƒ.level.getGameTime()).nextFloat() < 0.1F;
      }
   }

   protected static boolean wantsToPickup(Piglin var0, ItemStack var1) {
      if (â˜ƒ.isBaby() && â˜ƒ.is(ItemTags.IGNORED_BY_PIGLIN_BABIES)) {
         return false;
      } else if (â˜ƒ.is(ItemTags.PIGLIN_REPELLENTS)) {
         return false;
      } else if (isAdmiringDisabled(â˜ƒ) && â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
         return false;
      } else if (isBarterCurrency(â˜ƒ)) {
         return isNotHoldingLovedItemInOffHand(â˜ƒ);
      } else {
         boolean â˜ƒ = â˜ƒ.canAddToInventory(â˜ƒ);
         if (â˜ƒ.is(Items.GOLD_NUGGET)) {
            return â˜ƒ;
         } else if (isFood(â˜ƒ)) {
            return !hasEatenRecently(â˜ƒ) && â˜ƒ;
         } else if (!isLovedItem(â˜ƒ)) {
            return â˜ƒ.canReplaceCurrentItem(â˜ƒ);
         } else {
            return isNotHoldingLovedItemInOffHand(â˜ƒ) && â˜ƒ;
         }
      }
   }

   protected static boolean isLovedItem(ItemStack var0) {
      return â˜ƒ.is(ItemTags.PIGLIN_LOVED);
   }

   private static boolean wantsToStopRiding(Piglin var0, Entity var1) {
      if (!(â˜ƒ instanceof Mob)) {
         return false;
      } else {
         Mob â˜ƒ = (Mob)â˜ƒ;
         return !â˜ƒ.isBaby() || !â˜ƒ.isAlive() || wasHurtRecently(â˜ƒ) || wasHurtRecently(â˜ƒ) || â˜ƒ instanceof Piglin && â˜ƒ.getVehicle() == null;
      }
   }

   private static boolean isNearestValidAttackTarget(Piglin var0, LivingEntity var1) {
      return findNearestValidAttackTarget(â˜ƒ).filter(var1x -> var1x == â˜ƒ).isPresent();
   }

   private static boolean isNearZombified(Piglin var0) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      if (â˜ƒ.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)) {
         LivingEntity â˜ƒx = (LivingEntity)â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED).get();
         return â˜ƒ.closerThan(â˜ƒx, 6.0);
      } else {
         return false;
      }
   }

   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Piglin var0) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      if (isNearZombified(â˜ƒ)) {
         return Optional.empty();
      } else {
         Optional<LivingEntity> â˜ƒ = BehaviorUtils.getLivingEntityFromUUIDMemory(â˜ƒ, MemoryModuleType.ANGRY_AT);
         if (â˜ƒ.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(â˜ƒ, (LivingEntity)â˜ƒ.get())) {
            return â˜ƒ;
         } else {
            if (â˜ƒ.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
               Optional<Player> â˜ƒ = â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
               if (â˜ƒ.isPresent()) {
                  return â˜ƒ;
               }
            }

            Optional<Mob> â˜ƒ = â˜ƒ.getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
            if (â˜ƒ.isPresent()) {
               return â˜ƒ;
            } else {
               Optional<Player> â˜ƒ = â˜ƒ.getMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
               return â˜ƒ.isPresent() && Sensor.isEntityAttackable(â˜ƒ, (LivingEntity)â˜ƒ.get()) ? â˜ƒ : Optional.empty();
            }
         }
      }
   }

   public static void angerNearbyPiglins(Player var0, boolean var1) {
      List<Piglin> â˜ƒ = â˜ƒ.level.getEntitiesOfClass(Piglin.class, â˜ƒ.getBoundingBox().inflate(16.0));
      â˜ƒ.stream().filter(PiglinAi::isIdle).filter(var2x -> !â˜ƒ || BehaviorUtils.canSee(var2x, â˜ƒ)).forEach(var1x -> {
         if (var1x.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            setAngerTargetToNearestTargetablePlayerIfFound(var1x, â˜ƒ);
         } else {
            setAngerTarget(var1x, â˜ƒ);
         }
      });
   }

   public static InteractionResult mobInteract(Piglin var0, Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (canAdmire(â˜ƒ, â˜ƒ)) {
         ItemStack â˜ƒx = â˜ƒ.split(1);
         holdInOffhand(â˜ƒ, â˜ƒx);
         admireGoldItem(â˜ƒ);
         stopWalking(â˜ƒ);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   protected static boolean canAdmire(Piglin var0, ItemStack var1) {
      return !isAdmiringDisabled(â˜ƒ) && !isAdmiringItem(â˜ƒ) && â˜ƒ.isAdult() && isBarterCurrency(â˜ƒ);
   }

   protected static void wasHurtBy(Piglin var0, LivingEntity var1) {
      if (!(â˜ƒ instanceof Piglin)) {
         if (isHoldingItemInOffHand(â˜ƒ)) {
            stopHoldingOffHandItem(â˜ƒ, false);
         }

         Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
         â˜ƒ.eraseMemory(MemoryModuleType.CELEBRATE_LOCATION);
         â˜ƒ.eraseMemory(MemoryModuleType.DANCING);
         â˜ƒ.eraseMemory(MemoryModuleType.ADMIRING_ITEM);
         if (â˜ƒ instanceof Player) {
            â˜ƒ.setMemoryWithExpiry(MemoryModuleType.ADMIRING_DISABLED, true, 400L);
         }

         getAvoidTarget(â˜ƒ).ifPresent(var2x -> {
            if (var2x.getType() != â˜ƒ.getType()) {
               â˜ƒ.eraseMemory(MemoryModuleType.AVOID_TARGET);
            }
         });
         if (â˜ƒ.isBaby()) {
            â˜ƒ.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, â˜ƒ, 100L);
            if (Sensor.isEntityAttackableIgnoringLineOfSight(â˜ƒ, â˜ƒ)) {
               broadcastAngerTarget(â˜ƒ, â˜ƒ);
            }
         } else if (â˜ƒ.getType() == EntityType.HOGLIN && hoglinsOutnumberPiglins(â˜ƒ)) {
            setAvoidTargetAndDontHuntForAWhile(â˜ƒ, â˜ƒ);
            broadcastRetreat(â˜ƒ, â˜ƒ);
         } else {
            maybeRetaliate(â˜ƒ, â˜ƒ);
         }
      }
   }

   protected static void maybeRetaliate(AbstractPiglin var0, LivingEntity var1) {
      if (!â˜ƒ.getBrain().isActive(Activity.AVOID)) {
         if (Sensor.isEntityAttackableIgnoringLineOfSight(â˜ƒ, â˜ƒ)) {
            if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(â˜ƒ, â˜ƒ, 4.0)) {
               if (â˜ƒ.getType() == EntityType.PLAYER && â˜ƒ.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                  setAngerTargetToNearestTargetablePlayerIfFound(â˜ƒ, â˜ƒ);
                  broadcastUniversalAnger(â˜ƒ);
               } else {
                  setAngerTarget(â˜ƒ, â˜ƒ);
                  broadcastAngerTarget(â˜ƒ, â˜ƒ);
               }
            }
         }
      }
   }

   public static Optional<SoundEvent> getSoundForCurrentActivity(Piglin var0) {
      return â˜ƒ.getBrain().getActiveNonCoreActivity().map(var1 -> getSoundForActivity(â˜ƒ, var1));
   }

   private static SoundEvent getSoundForActivity(Piglin var0, Activity var1) {
      if (â˜ƒ == Activity.FIGHT) {
         return SoundEvents.PIGLIN_ANGRY;
      } else if (â˜ƒ.isConverting()) {
         return SoundEvents.PIGLIN_RETREAT;
      } else if (â˜ƒ == Activity.AVOID && isNearAvoidTarget(â˜ƒ)) {
         return SoundEvents.PIGLIN_RETREAT;
      } else if (â˜ƒ == Activity.ADMIRE_ITEM) {
         return SoundEvents.PIGLIN_ADMIRING_ITEM;
      } else if (â˜ƒ == Activity.CELEBRATE) {
         return SoundEvents.PIGLIN_CELEBRATE;
      } else if (seesPlayerHoldingLovedItem(â˜ƒ)) {
         return SoundEvents.PIGLIN_JEALOUS;
      } else {
         return isNearRepellent(â˜ƒ) ? SoundEvents.PIGLIN_RETREAT : SoundEvents.PIGLIN_AMBIENT;
      }
   }

   private static boolean isNearAvoidTarget(Piglin var0) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      return !â˜ƒ.hasMemoryValue(MemoryModuleType.AVOID_TARGET)
         ? false
         : ((LivingEntity)â˜ƒ.getMemory(MemoryModuleType.AVOID_TARGET).get()).closerThan(â˜ƒ, 12.0);
   }

   protected static boolean hasAnyoneNearbyHuntedRecently(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.HUNTED_RECENTLY)
         || getVisibleAdultPiglins(â˜ƒ).stream().anyMatch(var0x -> var0x.getBrain().hasMemoryValue(MemoryModuleType.HUNTED_RECENTLY));
   }

   private static List<AbstractPiglin> getVisibleAdultPiglins(Piglin var0) {
      return (List<AbstractPiglin>)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS).orElse(ImmutableList.of());
   }

   private static List<AbstractPiglin> getAdultPiglins(AbstractPiglin var0) {
      return (List<AbstractPiglin>)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS).orElse(ImmutableList.of());
   }

   public static boolean isWearingGold(LivingEntity var0) {
      for(ItemStack â˜ƒ : â˜ƒ.getArmorSlots()) {
         Item â˜ƒx = â˜ƒ.getItem();
         if (â˜ƒx instanceof ArmorItem && ((ArmorItem)â˜ƒx).getMaterial() == ArmorMaterials.GOLD) {
            return true;
         }
      }

      return false;
   }

   private static void stopWalking(Piglin var0) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getNavigation().stop();
   }

   private static RunSometimes<Piglin> babySometimesRideBabyHoglin() {
      return new RunSometimes<>(
         new CopyMemoryWithExpiry<>(Piglin::isBaby, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.RIDE_TARGET, RIDE_DURATION),
         RIDE_START_INTERVAL
      );
   }

   protected static void broadcastAngerTarget(AbstractPiglin var0, LivingEntity var1) {
      getAdultPiglins(â˜ƒ).forEach(var1x -> {
         if (â˜ƒ.getType() != EntityType.HOGLIN || var1x.canHunt() && ((Hoglin)â˜ƒ).canBeHunted()) {
            setAngerTargetIfCloserThanCurrent(var1x, â˜ƒ);
         }
      });
   }

   protected static void broadcastUniversalAnger(AbstractPiglin var0) {
      getAdultPiglins(â˜ƒ).forEach(var0x -> getNearestVisibleTargetablePlayer(var0x).ifPresent(var1 -> setAngerTarget(var0x, var1)));
   }

   protected static void broadcastDontKillAnyMoreHoglinsForAWhile(Piglin var0) {
      getVisibleAdultPiglins(â˜ƒ).forEach(PiglinAi::dontKillAnyMoreHoglinsForAWhile);
   }

   protected static void setAngerTarget(AbstractPiglin var0, LivingEntity var1) {
      if (Sensor.isEntityAttackableIgnoringLineOfSight(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, â˜ƒ.getUUID(), 600L);
         if (â˜ƒ.getType() == EntityType.HOGLIN && â˜ƒ.canHunt()) {
            dontKillAnyMoreHoglinsForAWhile(â˜ƒ);
         }

         if (â˜ƒ.getType() == EntityType.PLAYER && â˜ƒ.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
         }
      }
   }

   private static void setAngerTargetToNearestTargetablePlayerIfFound(AbstractPiglin var0, LivingEntity var1) {
      Optional<Player> â˜ƒ = getNearestVisibleTargetablePlayer(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         setAngerTarget(â˜ƒ, (LivingEntity)â˜ƒ.get());
      } else {
         setAngerTarget(â˜ƒ, â˜ƒ);
      }
   }

   private static void setAngerTargetIfCloserThanCurrent(AbstractPiglin var0, LivingEntity var1) {
      Optional<LivingEntity> â˜ƒ = getAngerTarget(â˜ƒ);
      LivingEntity â˜ƒx = BehaviorUtils.getNearestTarget(â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isPresent() || â˜ƒ.get() != â˜ƒx) {
         setAngerTarget(â˜ƒ, â˜ƒx);
      }
   }

   private static Optional<LivingEntity> getAngerTarget(AbstractPiglin var0) {
      return BehaviorUtils.getLivingEntityFromUUIDMemory(â˜ƒ, MemoryModuleType.ANGRY_AT);
   }

   public static Optional<LivingEntity> getAvoidTarget(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET) ? â˜ƒ.getBrain().getMemory(MemoryModuleType.AVOID_TARGET) : Optional.empty();
   }

   public static Optional<Player> getNearestVisibleTargetablePlayer(AbstractPiglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
         ? â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
         : Optional.empty();
   }

   private static void broadcastRetreat(Piglin var0, LivingEntity var1) {
      getVisibleAdultPiglins(â˜ƒ).stream().filter(var0x -> var0x instanceof Piglin).forEach(var1x -> retreatFromNearestTarget((Piglin)var1x, â˜ƒ));
   }

   private static void retreatFromNearestTarget(Piglin var0, LivingEntity var1) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      LivingEntity var3 = BehaviorUtils.getNearestTarget(â˜ƒ, â˜ƒ.getMemory(MemoryModuleType.AVOID_TARGET), â˜ƒ);
      var3 = BehaviorUtils.getNearestTarget(â˜ƒ, â˜ƒ.getMemory(MemoryModuleType.ATTACK_TARGET), var3);
      setAvoidTargetAndDontHuntForAWhile(â˜ƒ, var3);
   }

   private static boolean wantsToStopFleeing(Piglin var0) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      if (!â˜ƒ.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
         return true;
      } else {
         LivingEntity â˜ƒ = (LivingEntity)â˜ƒ.getMemory(MemoryModuleType.AVOID_TARGET).get();
         EntityType<?> â˜ƒx = â˜ƒ.getType();
         if (â˜ƒx == EntityType.HOGLIN) {
            return piglinsEqualOrOutnumberHoglins(â˜ƒ);
         } else if (isZombified(â˜ƒx)) {
            return !â˜ƒ.isMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, â˜ƒ);
         } else {
            return false;
         }
      }
   }

   private static boolean piglinsEqualOrOutnumberHoglins(Piglin var0) {
      return !hoglinsOutnumberPiglins(â˜ƒ);
   }

   private static boolean hoglinsOutnumberPiglins(Piglin var0) {
      int â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0) + 1;
      int â˜ƒx = â˜ƒ.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0);
      return â˜ƒx > â˜ƒ;
   }

   private static void setAvoidTargetAndDontHuntForAWhile(Piglin var0, LivingEntity var1) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, â˜ƒ, (long)RETREAT_DURATION.sample(â˜ƒ.level.random));
      dontKillAnyMoreHoglinsForAWhile(â˜ƒ);
   }

   protected static void dontKillAnyMoreHoglinsForAWhile(AbstractPiglin var0) {
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.HUNTED_RECENTLY, true, (long)TIME_BETWEEN_HUNTS.sample(â˜ƒ.level.random));
   }

   private static boolean seesPlayerHoldingWantedItem(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
   }

   private static void eat(Piglin var0) {
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.ATE_RECENTLY, true, 200L);
   }

   private static Vec3 getRandomNearbyPos(Piglin var0) {
      Vec3 â˜ƒ = LandRandomPos.getPos(â˜ƒ, 4, 2);
      return â˜ƒ == null ? â˜ƒ.position() : â˜ƒ;
   }

   private static boolean hasEatenRecently(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.ATE_RECENTLY);
   }

   protected static boolean isIdle(AbstractPiglin var0) {
      return â˜ƒ.getBrain().isActive(Activity.IDLE);
   }

   private static boolean hasCrossbow(LivingEntity var0) {
      return â˜ƒ.isHolding(Items.CROSSBOW);
   }

   private static void admireGoldItem(LivingEntity var0) {
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.ADMIRING_ITEM, true, 120L);
   }

   private static boolean isAdmiringItem(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM);
   }

   private static boolean isBarterCurrency(ItemStack var0) {
      return â˜ƒ.is(BARTERING_ITEM);
   }

   private static boolean isFood(ItemStack var0) {
      return â˜ƒ.is(ItemTags.PIGLIN_FOOD);
   }

   private static boolean isNearRepellent(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_REPELLENT);
   }

   private static boolean seesPlayerHoldingLovedItem(LivingEntity var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
   }

   private static boolean doesntSeeAnyPlayerHoldingLovedItem(LivingEntity var0) {
      return !seesPlayerHoldingLovedItem(â˜ƒ);
   }

   public static boolean isPlayerHoldingLovedItem(LivingEntity var0) {
      return â˜ƒ.getType() == EntityType.PLAYER && â˜ƒ.isHolding(PiglinAi::isLovedItem);
   }

   private static boolean isAdmiringDisabled(Piglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_DISABLED);
   }

   private static boolean wasHurtRecently(LivingEntity var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
   }

   private static boolean isHoldingItemInOffHand(Piglin var0) {
      return !â˜ƒ.getOffhandItem().isEmpty();
   }

   private static boolean isNotHoldingLovedItemInOffHand(Piglin var0) {
      return â˜ƒ.getOffhandItem().isEmpty() || !isLovedItem(â˜ƒ.getOffhandItem());
   }

   public static boolean isZombified(EntityType<?> var0) {
      return â˜ƒ == EntityType.ZOMBIFIED_PIGLIN || â˜ƒ == EntityType.ZOGLIN;
   }
}
