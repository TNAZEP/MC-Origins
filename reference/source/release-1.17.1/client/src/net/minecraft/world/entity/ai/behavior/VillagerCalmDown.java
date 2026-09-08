package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;

public class VillagerCalmDown extends Behavior<Villager> {
   private static final int SAFE_DISTANCE_FROM_DANGER = 36;

   public VillagerCalmDown() {
      super(ImmutableMap.of());
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      boolean â˜ƒ = VillagerPanicTrigger.isHurt(â˜ƒ) || VillagerPanicTrigger.hasHostile(â˜ƒ) || isCloseToEntityThatHurtMe(â˜ƒ);
      if (!â˜ƒ) {
         â˜ƒ.getBrain().eraseMemory(MemoryModuleType.HURT_BY);
         â˜ƒ.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
         â˜ƒ.getBrain().updateActivityFromSchedule(â˜ƒ.getDayTime(), â˜ƒ.getGameTime());
      }
   }

   private static boolean isCloseToEntityThatHurtMe(Villager var0) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter(var1 -> var1.distanceToSqr(â˜ƒ) <= 36.0).isPresent();
   }
}
