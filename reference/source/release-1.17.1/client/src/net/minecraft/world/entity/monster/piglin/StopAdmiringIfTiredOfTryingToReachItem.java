package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StopAdmiringIfTiredOfTryingToReachItem<E extends Piglin> extends Behavior<E> {
   private final int maxTimeToReachItem;
   private final int disableTime;

   public StopAdmiringIfTiredOfTryingToReachItem(int var1, int var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ADMIRING_ITEM,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
            MemoryStatus.REGISTERED,
            MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
            MemoryStatus.REGISTERED
         )
      );
      this.maxTimeToReachItem = â˜ƒ;
      this.disableTime = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      return â˜ƒ.getOffhandItem().isEmpty();
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      Brain<Piglin> â˜ƒ = â˜ƒ.getBrain();
      Optional<Integer> â˜ƒx = â˜ƒ.getMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
      if (!â˜ƒx.isPresent()) {
         â˜ƒ.setMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, 0);
      } else {
         int â˜ƒ = â˜ƒx.get();
         if (â˜ƒ > this.maxTimeToReachItem) {
            â˜ƒ.eraseMemory(MemoryModuleType.ADMIRING_ITEM);
            â˜ƒ.eraseMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            â˜ƒ.setMemoryWithExpiry(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, true, (long)this.disableTime);
         } else {
            â˜ƒ.setMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, â˜ƒ + 1);
         }
      }
   }
}
