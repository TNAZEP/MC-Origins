package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Items;

public class StopHoldingItemIfNoLongerAdmiring<E extends Piglin> extends Behavior<E> {
   public StopHoldingItemIfNoLongerAdmiring() {
      super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      return !â˜ƒ.getOffhandItem().isEmpty() && !â˜ƒ.getOffhandItem().is(Items.SHIELD);
   }

   protected void start(ServerLevel var1, E var2, long var3) {
      PiglinAi.stopHoldingOffHandItem(â˜ƒ, true);
   }
}
