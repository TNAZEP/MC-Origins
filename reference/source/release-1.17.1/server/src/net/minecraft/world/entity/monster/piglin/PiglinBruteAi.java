package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StrollAroundPoi;
import net.minecraft.world.entity.ai.behavior.StrollToPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;

public class PiglinBruteAi {
   private static final int ANGER_DURATION = 600;
   private static final int MELEE_ATTACK_COOLDOWN = 20;
   private static final double ACTIVITY_SOUND_LIKELIHOOD_PER_TICK = 0.0125;
   private static final int MAX_LOOK_DIST = 8;
   private static final int INTERACTION_RANGE = 8;
   private static final double TARGETING_RANGE = 12.0;
   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.6F;
   private static final int HOME_CLOSE_ENOUGH_DISTANCE = 2;
   private static final int HOME_TOO_FAR_DISTANCE = 100;
   private static final int HOME_STROLL_AROUND_DISTANCE = 5;

   protected static Brain<?> makeBrain(PiglinBrute var0, Brain<PiglinBrute> var1) {
      initCoreActivity(â˜ƒ, â˜ƒ);
      initIdleActivity(â˜ƒ, â˜ƒ);
      initFightActivity(â˜ƒ, â˜ƒ);
      â˜ƒ.setCoreActivities(ImmutableSet.of(Activity.CORE));
      â˜ƒ.setDefaultActivity(Activity.IDLE);
      â˜ƒ.useDefaultActivity();
      return â˜ƒ;
   }

   protected static void initMemories(PiglinBrute var0) {
      GlobalPos â˜ƒ = GlobalPos.of(â˜ƒ.level.dimension(), â˜ƒ.blockPosition());
      â˜ƒ.getBrain().setMemory(MemoryModuleType.HOME, â˜ƒ);
   }

   private static void initCoreActivity(PiglinBrute var0, Brain<PiglinBrute> var1) {
      â˜ƒ.addActivity(
         Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), new InteractWithDoor(), new StopBeingAngryIfTargetDead<>())
      );
   }

   private static void initIdleActivity(PiglinBrute var0, Brain<PiglinBrute> var1) {
      â˜ƒ.addActivity(
         Activity.IDLE,
         10,
         ImmutableList.of(
            new StartAttacking<>(PiglinBruteAi::findNearestValidAttackTarget),
            createIdleLookBehaviors(),
            createIdleMovementBehaviors(),
            new SetLookAndInteract(EntityType.PLAYER, 4)
         )
      );
   }

   private static void initFightActivity(PiglinBrute var0, Brain<PiglinBrute> var1) {
      â˜ƒ.addActivityAndRemoveMemoryWhenStopped(
         Activity.FIGHT,
         10,
         ImmutableList.of(
            new StopAttackingIfTargetInvalid<>((Predicate<LivingEntity>)(var1x -> !isNearestValidAttackTarget(â˜ƒ, var1x))),
            new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0F),
            new MeleeAttack(20)
         ),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static RunOne<PiglinBrute> createIdleLookBehaviors() {
      return new RunOne<>(
         ImmutableList.of(
            Pair.of(new SetEntityLookTarget(EntityType.PLAYER, 8.0F), 1),
            Pair.of(new SetEntityLookTarget(EntityType.PIGLIN, 8.0F), 1),
            Pair.of(new SetEntityLookTarget(EntityType.PIGLIN_BRUTE, 8.0F), 1),
            Pair.of(new SetEntityLookTarget(8.0F), 1),
            Pair.of(new DoNothing(30, 60), 1)
         )
      );
   }

   private static RunOne<PiglinBrute> createIdleMovementBehaviors() {
      return new RunOne<>(
         ImmutableList.of(
            Pair.of(new RandomStroll(0.6F), 2),
            Pair.of(InteractWith.of(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
            Pair.of(InteractWith.of(EntityType.PIGLIN_BRUTE, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
            Pair.of(new StrollToPoi(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
            Pair.of(new StrollAroundPoi(MemoryModuleType.HOME, 0.6F, 5), 2),
            Pair.of(new DoNothing(30, 60), 1)
         )
      );
   }

   protected static void updateActivity(PiglinBrute var0) {
      Brain<PiglinBrute> â˜ƒ = â˜ƒ.getBrain();
      Activity â˜ƒx = (Activity)â˜ƒ.getActiveNonCoreActivity().orElse(null);
      â˜ƒ.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
      Activity â˜ƒxx = (Activity)â˜ƒ.getActiveNonCoreActivity().orElse(null);
      if (â˜ƒx != â˜ƒxx) {
         playActivitySound(â˜ƒ);
      }

      â˜ƒ.setAggressive(â˜ƒ.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
   }

   private static boolean isNearestValidAttackTarget(AbstractPiglin var0, LivingEntity var1) {
      return findNearestValidAttackTarget(â˜ƒ).filter(var1x -> var1x == â˜ƒ).isPresent();
   }

   private static Optional<? extends LivingEntity> findNearestValidAttackTarget(AbstractPiglin var0) {
      Optional<LivingEntity> â˜ƒ = BehaviorUtils.getLivingEntityFromUUIDMemory(â˜ƒ, MemoryModuleType.ANGRY_AT);
      if (â˜ƒ.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(â˜ƒ, (LivingEntity)â˜ƒ.get())) {
         return â˜ƒ;
      } else {
         Optional<? extends LivingEntity> â˜ƒ = getTargetIfWithinRange(â˜ƒ, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
         return â˜ƒ.isPresent() ? â˜ƒ : â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
      }
   }

   private static Optional<? extends LivingEntity> getTargetIfWithinRange(AbstractPiglin var0, MemoryModuleType<? extends LivingEntity> var1) {
      return â˜ƒ.getBrain().getMemory(â˜ƒ).filter(var1x -> var1x.closerThan(â˜ƒ, 12.0));
   }

   protected static void wasHurtBy(PiglinBrute var0, LivingEntity var1) {
      if (!(â˜ƒ instanceof AbstractPiglin)) {
         PiglinAi.maybeRetaliate(â˜ƒ, â˜ƒ);
      }
   }

   protected static void setAngerTarget(PiglinBrute var0, LivingEntity var1) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, â˜ƒ.getUUID(), 600L);
   }

   protected static void maybePlayActivitySound(PiglinBrute var0) {
      if ((double)â˜ƒ.level.random.nextFloat() < 0.0125) {
         playActivitySound(â˜ƒ);
      }
   }

   private static void playActivitySound(PiglinBrute var0) {
      â˜ƒ.getBrain().getActiveNonCoreActivity().ifPresent(var1 -> {
         if (var1 == Activity.FIGHT) {
            â˜ƒ.playAngrySound();
         }
      });
   }
}
