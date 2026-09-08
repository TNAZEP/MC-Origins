package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class HurtBySensor extends Sensor<LivingEntity> {
   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY);
   }

   @Override
   protected void doTick(ServerLevel var1, LivingEntity var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      DamageSource â˜ƒx = â˜ƒ.getLastDamageSource();
      if (â˜ƒx != null) {
         â˜ƒ.setMemory(MemoryModuleType.HURT_BY, â˜ƒ.getLastDamageSource());
         Entity â˜ƒxx = â˜ƒx.getEntity();
         if (â˜ƒxx instanceof LivingEntity) {
            â˜ƒ.setMemory(MemoryModuleType.HURT_BY_ENTITY, (LivingEntity)â˜ƒxx);
         }
      } else {
         â˜ƒ.eraseMemory(MemoryModuleType.HURT_BY);
      }

      â˜ƒ.getMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent(var2x -> {
         if (!var2x.isAlive() || var2x.level != â˜ƒ) {
            â˜ƒ.eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
         }
      });
   }
}
