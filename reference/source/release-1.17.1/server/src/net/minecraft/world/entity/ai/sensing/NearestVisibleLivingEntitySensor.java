package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public abstract class NearestVisibleLivingEntitySensor extends Sensor<LivingEntity> {
   protected abstract boolean isMatchingEntity(LivingEntity var1, LivingEntity var2);

   protected abstract MemoryModuleType<LivingEntity> getMemory();

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(this.getMemory());
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      â˜ƒ.getBrain().setMemory(this.getMemory(), this.getNearestEntity(â˜ƒ));
   }

   private Optional<LivingEntity> getNearestEntity(LivingEntity var1) {
      return this.getVisibleEntities(â˜ƒ)
         .flatMap(var2 -> var2.stream().filter(var2x -> this.isMatchingEntity(â˜ƒ, var2x)).min(Comparator.comparingDouble(â˜ƒ::distanceToSqr)));
   }

   protected Optional<List<LivingEntity>> getVisibleEntities(LivingEntity var1) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   }
}
