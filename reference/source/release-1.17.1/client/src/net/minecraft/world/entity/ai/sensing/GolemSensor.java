package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class GolemSensor extends Sensor<LivingEntity> {
   private static final int GOLEM_SCAN_RATE = 200;
   private static final int MEMORY_TIME_TO_LIVE = 600;

   public GolemSensor() {
      this(200);
   }

   public GolemSensor(int var1) {
      super(â˜ƒ);
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      checkForNearbyGolem(â˜ƒ);
   }

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_LIVING_ENTITIES);
   }

   public static void checkForNearbyGolem(LivingEntity var0) {
      Optional<List<LivingEntity>> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES);
      if (â˜ƒ.isPresent()) {
         boolean â˜ƒx = ((List)â˜ƒ.get()).stream().anyMatch(var0x -> var0x.getType().equals(EntityType.IRON_GOLEM));
         if (â˜ƒx) {
            golemDetected(â˜ƒ);
         }
      }
   }

   public static void golemDetected(LivingEntity var0) {
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.GOLEM_DETECTED_RECENTLY, true, 600L);
   }
}
