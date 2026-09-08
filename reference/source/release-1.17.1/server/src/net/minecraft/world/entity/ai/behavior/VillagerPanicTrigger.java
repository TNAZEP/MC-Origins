package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

public class VillagerPanicTrigger extends Behavior<Villager> {
   public VillagerPanicTrigger() {
      super(ImmutableMap.of());
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return isHurt(â˜ƒ) || hasHostile(â˜ƒ);
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      if (isHurt(â˜ƒ) || hasHostile(â˜ƒ)) {
         Brain<?> â˜ƒ = â˜ƒ.getBrain();
         if (!â˜ƒ.isActive(Activity.PANIC)) {
            â˜ƒ.eraseMemory(MemoryModuleType.PATH);
            â˜ƒ.eraseMemory(MemoryModuleType.WALK_TARGET);
            â˜ƒ.eraseMemory(MemoryModuleType.LOOK_TARGET);
            â˜ƒ.eraseMemory(MemoryModuleType.BREED_TARGET);
            â˜ƒ.eraseMemory(MemoryModuleType.INTERACTION_TARGET);
         }

         â˜ƒ.setActiveActivityIfPossible(Activity.PANIC);
      }
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      if (â˜ƒ % 100L == 0L) {
         â˜ƒ.spawnGolemIfNeeded(â˜ƒ, â˜ƒ, 3);
      }
   }

   public static boolean hasHostile(LivingEntity var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
   }

   public static boolean isHurt(LivingEntity var0) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
   }
}
