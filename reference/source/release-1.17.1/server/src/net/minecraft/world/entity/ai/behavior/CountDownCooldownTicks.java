package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CountDownCooldownTicks extends Behavior<LivingEntity> {
   private final MemoryModuleType<Integer> cooldownTicks;

   public CountDownCooldownTicks(MemoryModuleType<Integer> var1) {
      super(ImmutableMap.of(â˜ƒ, MemoryStatus.VALUE_PRESENT));
      this.cooldownTicks = â˜ƒ;
   }

   private Optional<Integer> getCooldownTickMemory(LivingEntity var1) {
      return â˜ƒ.getBrain().getMemory(this.cooldownTicks);
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   @Override
   protected boolean canStillUse(ServerLevel var1, LivingEntity var2, long var3) {
      Optional<Integer> â˜ƒ = this.getCooldownTickMemory(â˜ƒ);
      return â˜ƒ.isPresent() && â˜ƒ.get() > 0;
   }

   @Override
   protected void tick(ServerLevel var1, LivingEntity var2, long var3) {
      Optional<Integer> â˜ƒ = this.getCooldownTickMemory(â˜ƒ);
      â˜ƒ.getBrain().setMemory(this.cooldownTicks, â˜ƒ.get() - 1);
   }

   @Override
   protected void stop(ServerLevel var1, LivingEntity var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(this.cooldownTicks);
   }
}
