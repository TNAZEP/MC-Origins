package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StopAttackingIfTargetInvalid<E extends Mob> extends Behavior<E> {
   private static final int TIMEOUT_TO_GET_WITHIN_ATTACK_RANGE = 200;
   private final Predicate<LivingEntity> stopAttackingWhen;
   private final Consumer<E> onTargetErased;

   public StopAttackingIfTargetInvalid(Predicate<LivingEntity> var1, Consumer<E> var2) {
      super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED));
      this.stopAttackingWhen = â˜ƒ;
      this.onTargetErased = â˜ƒ;
   }

   public StopAttackingIfTargetInvalid(Predicate<LivingEntity> var1) {
      this(â˜ƒ, var0 -> {
      });
   }

   public StopAttackingIfTargetInvalid(Consumer<E> var1) {
      this(var0 -> false, â˜ƒ);
   }

   public StopAttackingIfTargetInvalid() {
      this(var0 -> false, var0 -> {
      });
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      LivingEntity â˜ƒ = this.getAttackTarget(â˜ƒ);
      if (!â˜ƒ.canAttack(â˜ƒ)) {
         this.clearAttackTarget(â˜ƒ);
      } else if (isTiredOfTryingToReachTarget(â˜ƒ)) {
         this.clearAttackTarget(â˜ƒ);
      } else if (this.isCurrentTargetDeadOrRemoved(â˜ƒ)) {
         this.clearAttackTarget(â˜ƒ);
      } else if (this.isCurrentTargetInDifferentLevel(â˜ƒ)) {
         this.clearAttackTarget(â˜ƒ);
      } else if (this.stopAttackingWhen.test(this.getAttackTarget(â˜ƒ))) {
         this.clearAttackTarget(â˜ƒ);
      }
   }

   private boolean isCurrentTargetInDifferentLevel(E var1) {
      return this.getAttackTarget(â˜ƒ).level != â˜ƒ.level;
   }

   private LivingEntity getAttackTarget(E var1) {
      return (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
   }

   private static <E extends LivingEntity> boolean isTiredOfTryingToReachTarget(E var0) {
      Optional<Long> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      return â˜ƒ.isPresent() && â˜ƒ.level.getGameTime() - â˜ƒ.get() > 200L;
   }

   private boolean isCurrentTargetDeadOrRemoved(E var1) {
      Optional<LivingEntity> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
      return â˜ƒ.isPresent() && !((LivingEntity)â˜ƒ.get()).isAlive();
   }

   protected void clearAttackTarget(E var1) {
      this.onTargetErased.accept(â˜ƒ);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
   }
}
