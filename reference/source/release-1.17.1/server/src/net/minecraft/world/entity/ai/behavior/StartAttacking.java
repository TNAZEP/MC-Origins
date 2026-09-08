package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StartAttacking<E extends Mob> extends Behavior<E> {
   private final Predicate<E> canAttackPredicate;
   private final Function<E, Optional<? extends LivingEntity>> targetFinderFunction;

   public StartAttacking(Predicate<E> var1, Function<E, Optional<? extends LivingEntity>> var2) {
      super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED));
      this.canAttackPredicate = â˜ƒ;
      this.targetFinderFunction = â˜ƒ;
   }

   public StartAttacking(Function<E, Optional<? extends LivingEntity>> var1) {
      this(var0 -> true, â˜ƒ);
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      if (!this.canAttackPredicate.test(â˜ƒ)) {
         return false;
      } else {
         Optional<? extends LivingEntity> â˜ƒ = (Optional)this.targetFinderFunction.apply(â˜ƒ);
         return â˜ƒ.isPresent() ? â˜ƒ.canAttack((LivingEntity)â˜ƒ.get()) : false;
      }
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      ((Optional)this.targetFinderFunction.apply(â˜ƒ)).ifPresent(var2x -> this.setAttackTarget(â˜ƒ, var2x));
   }

   private void setAttackTarget(E var1, LivingEntity var2) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, â˜ƒ);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }
}
