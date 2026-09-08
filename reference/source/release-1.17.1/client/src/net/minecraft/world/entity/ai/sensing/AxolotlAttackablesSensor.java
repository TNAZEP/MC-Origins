package net.minecraft.world.entity.ai.sensing;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class AxolotlAttackablesSensor extends NearestVisibleLivingEntitySensor {
   public static final float TARGET_DETECTION_DISTANCE = 8.0F;

   @Override
   protected boolean isMatchingEntity(LivingEntity var1, LivingEntity var2) {
      if (Sensor.isEntityAttackable(â˜ƒ, â˜ƒ) && (this.isHostileTarget(â˜ƒ) || this.isHuntTarget(â˜ƒ, â˜ƒ))) {
         return this.isClose(â˜ƒ, â˜ƒ) && â˜ƒ.isInWaterOrBubble();
      } else {
         return false;
      }
   }

   private boolean isHuntTarget(LivingEntity var1, LivingEntity var2) {
      return !â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN) && EntityTypeTags.AXOLOTL_HUNT_TARGETS.contains(â˜ƒ.getType());
   }

   private boolean isHostileTarget(LivingEntity var1) {
      return EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES.contains(â˜ƒ.getType());
   }

   private boolean isClose(LivingEntity var1, LivingEntity var2) {
      return â˜ƒ.distanceToSqr(â˜ƒ) <= 64.0;
   }

   @Override
   protected MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_ATTACKABLE;
   }
}
