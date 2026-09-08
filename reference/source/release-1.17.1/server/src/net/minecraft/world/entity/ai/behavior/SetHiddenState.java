package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class SetHiddenState extends Behavior<LivingEntity> {
   private static final int HIDE_TIMEOUT = 300;
   private final int closeEnoughDist;
   private final int stayHiddenTicks;
   private int ticksHidden;

   public SetHiddenState(int var1, int var2) {
      super(ImmutableMap.of(MemoryModuleType.HIDING_PLACE, MemoryStatus.VALUE_PRESENT, MemoryModuleType.HEARD_BELL_TIME, MemoryStatus.VALUE_PRESENT));
      this.stayHiddenTicks = â˜ƒ * 20;
      this.ticksHidden = 0;
      this.closeEnoughDist = â˜ƒ;
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      Optional<Long> â˜ƒx = â˜ƒ.getMemory(MemoryModuleType.HEARD_BELL_TIME);
      boolean â˜ƒxx = â˜ƒx.get() + 300L <= â˜ƒ;
      if (this.ticksHidden <= this.stayHiddenTicks && !â˜ƒxx) {
         BlockPos â˜ƒxxx = ((GlobalPos)â˜ƒ.getMemory(MemoryModuleType.HIDING_PLACE).get()).pos();
         if (â˜ƒxxx.closerThan(â˜ƒ.blockPosition(), (double)this.closeEnoughDist)) {
            ++this.ticksHidden;
         }
      } else {
         â˜ƒ.eraseMemory(MemoryModuleType.HEARD_BELL_TIME);
         â˜ƒ.eraseMemory(MemoryModuleType.HIDING_PLACE);
         â˜ƒ.updateActivityFromSchedule(â˜ƒ.getDayTime(), â˜ƒ.getGameTime());
         this.ticksHidden = 0;
      }
   }
}
