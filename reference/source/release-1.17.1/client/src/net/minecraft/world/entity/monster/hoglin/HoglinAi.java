package net.minecraft.world.entity.monster.hoglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.BecomePassiveIfMemoryPresent;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;

public class HoglinAi {
   public static final int REPELLENT_DETECTION_RANGE_HORIZONTAL = 8;
   public static final int REPELLENT_DETECTION_RANGE_VERTICAL = 4;
   private static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(5, 20);
   private static final int ATTACK_DURATION = 200;
   private static final int DESIRED_DISTANCE_FROM_PIGLIN_WHEN_IDLING = 8;
   private static final int DESIRED_DISTANCE_FROM_PIGLIN_WHEN_RETREATING = 15;
   private static final int ATTACK_INTERVAL = 40;
   private static final int BABY_ATTACK_INTERVAL = 15;
   private static final int REPELLENT_PACIFY_TIME = 200;
   private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
   private static final float SPEED_MULTIPLIER_WHEN_AVOIDING_REPELLENT = 1.0F;
   private static final float SPEED_MULTIPLIER_WHEN_RETREATING = 1.3F;
   private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.6F;
   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.4F;
   private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 0.6F;

   protected static Brain<?> makeBrain(Brain<Hoglin> var0) {
      initCoreActivity(â˜ƒ);
      initIdleActivity(â˜ƒ);
      initFightActivity(â˜ƒ);
      initRetreatActivity(â˜ƒ);
      â˜ƒ.setCoreActivities(ImmutableSet.of(Activity.CORE));
      â˜ƒ.setDefaultActivity(Activity.IDLE);
      â˜ƒ.useDefaultActivity();
      return â˜ƒ;
   }

   private static void initCoreActivity(Brain<Hoglin> var0) {
      â˜ƒ.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink()));
   }

   private static void initIdleActivity(Brain<Hoglin> var0) {
      â˜ƒ.addActivity(
         Activity.IDLE,
         10,
         ImmutableList.of(
            new BecomePassiveIfMemoryPresent(MemoryModuleType.NEAREST_REPELLENT, 200),
            new AnimalMakeLove(EntityType.HOGLIN, 0.6F),
            SetWalkTargetAwayFrom.pos(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true),
            new StartAttacking(HoglinAi::findNearestValidAttackTarget),
            new RunIf<PathfinderMob>(Hoglin::isAdult, SetWalkTargetAwayFrom.entity(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, 0.4F, 8, false)),
            new RunSometimes<LivingEntity>(new SetEntityLookTarget(8.0F), UniformInt.of(30, 60)),
            new BabyFollowAdult(ADULT_FOLLOW_RANGE, 0.6F),
            createIdleMovementBehaviors()
         )
      );
   }

   private static void initFightActivity(Brain<Hoglin> var0) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.FIGHT,
         10,
         ImmutableList.of(
            new BecomePassiveIfMemoryPresent(MemoryModuleType.NEAREST_REPELLENT, 200),
            new AnimalMakeLove(EntityType.HOGLIN, 0.6F),
            new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0F),
            new RunIf<>(Hoglin::isAdult, new MeleeAttack(40)),
            new RunIf<>(AgeableMob::isBaby, new MeleeAttack(15)),
            new StopAttackingIfTargetInvalid(),
            new EraseMemoryIf(HoglinAi::isBreeding, MemoryModuleType.ATTACK_TARGET)
         ),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static void initRetreatActivity(Brain<Hoglin> var0) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.AVOID,
         10,
         ImmutableList.of(
            SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.3F, 15, false),
            createIdleMovementBehaviors(),
            new RunSometimes<LivingEntity>(new SetEntityLookTarget(8.0F), UniformInt.of(30, 60)),
            new EraseMemoryIf(HoglinAi::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)
         ),
         MemoryModuleType.AVOID_TARGET
      );
   }

   private static RunOne<Hoglin> createIdleMovementBehaviors() {
      return new RunOne<>(
         ImmutableList.of(Pair.of(new RandomStroll(0.4F), 2), Pair.of(new SetWalkTargetFromLookTarget(0.4F, 3), 2), Pair.of(new DoNothing(30, 60), 1))
      );
   }

   protected static void updateActivity(Hoglin var0) {
      Brain<Hoglin> â˜ƒ = â˜ƒ.getBrain();
      Activity â˜ƒx = (Activity)â˜ƒ.getActiveNonCoreActivity().orElse(null);
      â˜ƒ.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
      Activity â˜ƒxx = (Activity)â˜ƒ.getActiveNonCoreActivity().orElse(null);
      if (â˜ƒx != â˜ƒxx) {
         getSoundForCurrentActivity(â˜ƒ).ifPresent(â˜ƒ::playSound);
      }

      â˜ƒ.setAggressive(â˜ƒ.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
   }

   protected static void onHitTarget(Hoglin var0, LivingEntity var1) {
      if (!â˜ƒ.isBaby()) {
         if (â˜ƒ.getType() == EntityType.PIGLIN && piglinsOutnumberHoglins(â˜ƒ)) {
            setAvoidTarget(â˜ƒ, â˜ƒ);
            broadcastRetreat(â˜ƒ, â˜ƒ);
         } else {
            broadcastAttackTarget(â˜ƒ, â˜ƒ);
         }
      }
   }

   private static void broadcastRetreat(Hoglin var0, LivingEntity var1) {
      getVisibleAdultHoglins(â˜ƒ).forEach(var1x -> retreatFromNearestTarget(var1x, â˜ƒ));
   }

   private static void retreatFromNearestTarget(Hoglin var0, LivingEntity var1) {
      Brain<Hoglin> â˜ƒ = â˜ƒ.getBrain();
      LivingEntity var2 = BehaviorUtils.getNearestTarget(â˜ƒ, â˜ƒ.getMemory(MemoryModuleType.AVOID_TARGET), â˜ƒ);
      var2 = BehaviorUtils.getNearestTarget(â˜ƒ, â˜ƒ.getMemory(MemoryModuleType.ATTACK_TARGET), var2);
      setAvoidTarget(â˜ƒ, var2);
   }

   private static void setAvoidTarget(Hoglin var0, LivingEntity var1) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, â˜ƒ, (long)RETREAT_DURATION.sample(â˜ƒ.level.random));
   }

   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Hoglin var0) {
      return !isPacified(â˜ƒ) && !isBreeding(â˜ƒ) ? â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
   }

   static boolean isPosNearNearestRepellent(Hoglin var0, BlockPos var1) {
      Optional<BlockPos> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_REPELLENT);
      return â˜ƒ.isPresent() && ((BlockPos)â˜ƒ.get()).closerThan(â˜ƒ, 8.0);
   }

   private static boolean wantsToStopFleeing(Hoglin var0) {
      return â˜ƒ.isAdult() && !piglinsOutnumberHoglins(â˜ƒ);
   }

   private static boolean piglinsOutnumberHoglins(Hoglin var0) {
      if (â˜ƒ.isBaby()) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0);
         int â˜ƒx = â˜ƒ.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0) + 1;
         return â˜ƒ > â˜ƒx;
      }
   }

   protected static void wasHurtBy(Hoglin var0, LivingEntity var1) {
      Brain<Hoglin> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.eraseMemory(MemoryModuleType.PACIFIED);
      â˜ƒ.eraseMemory(MemoryModuleType.BREED_TARGET);
      if (â˜ƒ.isBaby()) {
         retreatFromNearestTarget(â˜ƒ, â˜ƒ);
      } else {
         maybeRetaliate(â˜ƒ, â˜ƒ);
      }
   }

   private static void maybeRetaliate(Hoglin var0, LivingEntity var1) {
      if (!â˜ƒ.getBrain().isActive(Activity.AVOID) || â˜ƒ.getType() != EntityType.PIGLIN) {
         if (Sensor.isEntityAttackable(â˜ƒ, â˜ƒ)) {
            if (â˜ƒ.getType() != EntityType.HOGLIN) {
               if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(â˜ƒ, â˜ƒ, 4.0)) {
                  setAttackTarget(â˜ƒ, â˜ƒ);
                  broadcastAttackTarget(â˜ƒ, â˜ƒ);
               }
            }
         }
      }
   }

   private static void setAttackTarget(Hoglin var0, LivingEntity var1) {
      Brain<Hoglin> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      â˜ƒ.eraseMemory(MemoryModuleType.BREED_TARGET);
      â˜ƒ.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, â˜ƒ, 200L);
   }

   private static void broadcastAttackTarget(Hoglin var0, LivingEntity var1) {
      getVisibleAdultHoglins(â˜ƒ).forEach(var1x -> setAttackTargetIfCloserThanCurrent(var1x, â˜ƒ));
   }

   private static void setAttackTargetIfCloserThanCurrent(Hoglin var0, LivingEntity var1) {
      if (!isPacified(â˜ƒ)) {
         Optional<LivingEntity> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
         LivingEntity â˜ƒx = BehaviorUtils.getNearestTarget(â˜ƒ, â˜ƒ, â˜ƒ);
         setAttackTarget(â˜ƒ, â˜ƒx);
      }
   }

   public static Optional<SoundEvent> getSoundForCurrentActivity(Hoglin var0) {
      return â˜ƒ.getBrain().getActiveNonCoreActivity().map(var1 -> getSoundForActivity(â˜ƒ, var1));
   }

   private static SoundEvent getSoundForActivity(Hoglin var0, Activity var1) {
      if (â˜ƒ == Activity.AVOID || â˜ƒ.isConverting()) {
         return SoundEvents.HOGLIN_RETREAT;
      } else if (â˜ƒ == Activity.FIGHT) {
         return SoundEvents.HOGLIN_ANGRY;
      } else {
         return isNearRepellent(â˜ƒ) ? SoundEvents.HOGLIN_RETREAT : SoundEvents.HOGLIN_AMBIENT;
      }
   }

   private static List<Hoglin> getVisibleAdultHoglins(Hoglin var0) {
      return (List<Hoglin>)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS).orElse(ImmutableList.of());
   }

   private static boolean isNearRepellent(Hoglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_REPELLENT);
   }

   private static boolean isBreeding(Hoglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
   }

   protected static boolean isPacified(Hoglin var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.PACIFIED);
   }
}
