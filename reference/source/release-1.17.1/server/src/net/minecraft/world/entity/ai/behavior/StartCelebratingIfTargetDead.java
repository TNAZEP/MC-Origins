package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.GameRules;

public class StartCelebratingIfTargetDead extends Behavior<LivingEntity> {
   private final int celebrateDuration;
   private final BiPredicate<LivingEntity, LivingEntity> dancePredicate;

   public StartCelebratingIfTargetDead(int var1, BiPredicate<LivingEntity, LivingEntity> var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.ANGRY_AT,
            MemoryStatus.REGISTERED,
            MemoryModuleType.CELEBRATE_LOCATION,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.DANCING,
            MemoryStatus.REGISTERED
         )
      );
      this.celebrateDuration = â˜ƒ;
      this.dancePredicate = â˜ƒ;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      return this.getAttackTarget(â˜ƒ).isDeadOrDying();
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      LivingEntity â˜ƒ = this.getAttackTarget(â˜ƒ);
      if (this.dancePredicate.test(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.DANCING, true, (long)this.celebrateDuration);
      }

      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.CELEBRATE_LOCATION, â˜ƒ.blockPosition(), (long)this.celebrateDuration);
      if (â˜ƒ.getType() != EntityType.PLAYER || â˜ƒ.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
         â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
         â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
      }
   }

   private LivingEntity getAttackTarget(LivingEntity var1) {
      return (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
   }
}
