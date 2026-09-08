package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class VillagerBabiesSensor extends Sensor<LivingEntity> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES, this.getNearestVillagerBabies(â˜ƒ));
   }

   private List<LivingEntity> getNearestVillagerBabies(LivingEntity var1) {
      return (List<LivingEntity>)this.getVisibleEntities(â˜ƒ).stream().filter(this::isVillagerBaby).collect(Collectors.toList());
   }

   private boolean isVillagerBaby(LivingEntity var1) {
      return â˜ƒ.getType() == EntityType.VILLAGER && â˜ƒ.isBaby();
   }

   private List<LivingEntity> getVisibleEntities(LivingEntity var1) {
      return (List<LivingEntity>)â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(Lists.newArrayList());
   }
}
