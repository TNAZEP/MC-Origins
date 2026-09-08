package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromAttackTargetIfTargetOutOfReach extends Behavior<Mob> {
   private static final int PROJECTILE_ATTACK_RANGE_BUFFER = 1;
   private final Function<LivingEntity, Float> speedModifier;

   public SetWalkTargetFromAttackTargetIfTargetOutOfReach(float var1) {
      this(var1x -> â˜ƒ);
   }

   public SetWalkTargetFromAttackTargetIfTargetOutOfReach(Function<LivingEntity, Float> var1) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryStatus.REGISTERED
         )
      );
      this.speedModifier = â˜ƒ;
   }

   protected void start(ServerLevel var1, Mob var2, long var3) {
      LivingEntity â˜ƒ = (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
      if (BehaviorUtils.canSee(â˜ƒ, â˜ƒ) && BehaviorUtils.isWithinAttackRange(â˜ƒ, â˜ƒ, 1)) {
         this.clearWalkTarget(â˜ƒ);
      } else {
         this.setWalkAndLookTarget(â˜ƒ, â˜ƒ);
      }
   }

   private void setWalkAndLookTarget(LivingEntity var1, LivingEntity var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ, true));
      WalkTarget â˜ƒx = new WalkTarget(new EntityTracker(â˜ƒ, false), this.speedModifier.apply(â˜ƒ), 0);
      â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, â˜ƒx);
   }

   private void clearWalkTarget(LivingEntity var1) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
   }
}
