package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;

public class StopAdmiringIfItemTooFarAway<E extends Piglin> extends Behavior<E> {
   private final int maxDistanceToItem;

   public StopAdmiringIfItemTooFarAway(int var1) {
      super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.REGISTERED));
      this.maxDistanceToItem = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      if (!â˜ƒ.getOffhandItem().isEmpty()) {
         return false;
      } else {
         Optional<ItemEntity> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
         if (!â˜ƒ.isPresent()) {
            return true;
         } else {
            return !((ItemEntity)â˜ƒ.get()).closerThan(â˜ƒ, (double)this.maxDistanceToItem);
         }
      }
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.ADMIRING_ITEM);
   }
}
