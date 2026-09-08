package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.AABB;

public class NearestLivingEntitySensor extends Sensor<LivingEntity> {
   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      AABB â˜ƒ = â˜ƒ.getBoundingBox().inflate(16.0, 16.0, 16.0);
      List<LivingEntity> â˜ƒx = â˜ƒ.getEntitiesOfClass(LivingEntity.class, â˜ƒ, var1x -> var1x != â˜ƒ && var1x.isAlive());
      â˜ƒx.sort(Comparator.comparingDouble(â˜ƒ::distanceToSqr));
      Brain<?> â˜ƒxx = â˜ƒ.getBrain();
      â˜ƒxx.setMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES, â˜ƒx);
      â˜ƒxx.setMemory(
         MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, (List)â˜ƒx.stream().filter(var1x -> isEntityTargetable(â˜ƒ, var1x)).collect(Collectors.toList())
      );
   }

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   }
}
