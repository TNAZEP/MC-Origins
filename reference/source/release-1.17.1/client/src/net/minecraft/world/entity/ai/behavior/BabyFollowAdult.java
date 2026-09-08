package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BabyFollowAdult<E extends AgeableMob> extends Behavior<E> {
   private final UniformInt followRange;
   private final Function<LivingEntity, Float> speedModifier;

   public BabyFollowAdult(UniformInt var1, float var2) {
      this(â˜ƒ, var1x -> â˜ƒ);
   }

   public BabyFollowAdult(UniformInt var1, Function<LivingEntity, Float> var2) {
      super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.followRange = â˜ƒ;
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      if (!â˜ƒ.isBaby()) {
         return false;
      } else {
         AgeableMob â˜ƒ = this.getNearestAdult(â˜ƒ);
         return â˜ƒ.closerThan(â˜ƒ, (double)(this.followRange.getMaxValue() + 1)) && !â˜ƒ.closerThan(â˜ƒ, (double)this.followRange.getMinValue());
      }
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      BehaviorUtils.setWalkAndLookTargetMemories(â˜ƒ, this.getNearestAdult(â˜ƒ), this.speedModifier.apply(â˜ƒ), this.followRange.getMinValue() - 1);
   }

   private AgeableMob getNearestAdult(E var1) {
      return (AgeableMob)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT).get();
   }
}
