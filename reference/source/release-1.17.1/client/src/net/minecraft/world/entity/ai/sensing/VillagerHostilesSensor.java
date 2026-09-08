package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class VillagerHostilesSensor extends NearestVisibleLivingEntitySensor {
   private static final ImmutableMap<EntityType<?>, Float> ACCEPTABLE_DISTANCE_FROM_HOSTILES = ImmutableMap.builder()
      .put(EntityType.DROWNED, 8.0F)
      .put(EntityType.EVOKER, 12.0F)
      .put(EntityType.HUSK, 8.0F)
      .put(EntityType.ILLUSIONER, 12.0F)
      .put(EntityType.PILLAGER, 15.0F)
      .put(EntityType.RAVAGER, 12.0F)
      .put(EntityType.VEX, 8.0F)
      .put(EntityType.VINDICATOR, 10.0F)
      .put(EntityType.ZOGLIN, 10.0F)
      .put(EntityType.ZOMBIE, 8.0F)
      .put(EntityType.ZOMBIE_VILLAGER, 8.0F)
      .build();

   @Override
   protected boolean isMatchingEntity(LivingEntity var1, LivingEntity var2) {
      return this.isHostile(â˜ƒ) && this.isClose(â˜ƒ, â˜ƒ);
   }

   private boolean isClose(LivingEntity var1, LivingEntity var2) {
      float â˜ƒ = ACCEPTABLE_DISTANCE_FROM_HOSTILES.get(â˜ƒ.getType());
      return â˜ƒ.distanceToSqr(â˜ƒ) <= (double)(â˜ƒ * â˜ƒ);
   }

   @Override
   protected MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_HOSTILE;
   }

   private boolean isHostile(LivingEntity var1) {
      return ACCEPTABLE_DISTANCE_FROM_HOSTILES.containsKey(â˜ƒ.getType());
   }
}
