package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class AdultSensor extends Sensor<AgeableMob> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   }

   protected void doTick(ServerLevel var1, AgeableMob var2) {
      â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).ifPresent(var2x -> this.setNearestVisibleAdult(â˜ƒ, var2x));
   }

   private void setNearestVisibleAdult(AgeableMob var1, List<LivingEntity> var2) {
      Optional<AgeableMob> â˜ƒ = â˜ƒ.stream()
         .filter(var1x -> var1x.getType() == â˜ƒ.getType())
         .map(var0 -> (AgeableMob)var0)
         .filter(var0 -> !var0.isBaby())
         .findFirst();
      â˜ƒ.getBrain().setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT, â˜ƒ);
   }
}
