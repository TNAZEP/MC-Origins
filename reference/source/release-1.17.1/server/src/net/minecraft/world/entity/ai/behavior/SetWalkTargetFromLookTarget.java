package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromLookTarget extends Behavior<LivingEntity> {
   private final Function<LivingEntity, Float> speedModifier;
   private final int closeEnoughDistance;
   private final Predicate<LivingEntity> canSetWalkTargetPredicate;

   public SetWalkTargetFromLookTarget(float var1, int var2) {
      this(var0 -> true, var1x -> â˜ƒ, â˜ƒ);
   }

   public SetWalkTargetFromLookTarget(Predicate<LivingEntity> var1, Function<LivingEntity, Float> var2, int var3) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_PRESENT));
      this.speedModifier = â˜ƒ;
      this.closeEnoughDistance = â˜ƒ;
      this.canSetWalkTargetPredicate = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      return this.canSetWalkTargetPredicate.test(â˜ƒ);
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      PositionTracker â˜ƒx = (PositionTracker)â˜ƒ.getMemory(MemoryModuleType.LOOK_TARGET).get();
      â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒx, this.speedModifier.apply(â˜ƒ), this.closeEnoughDistance));
   }
}
