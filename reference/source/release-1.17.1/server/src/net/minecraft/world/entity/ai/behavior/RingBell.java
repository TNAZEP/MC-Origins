package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RingBell extends Behavior<LivingEntity> {
   private static final float BELL_RING_CHANCE = 0.95F;
   public static final int RING_BELL_FROM_DISTANCE = 3;

   public RingBell() {
      super(ImmutableMap.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT));
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      return â˜ƒ.random.nextFloat() > 0.95F;
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      BlockPos â˜ƒx = ((GlobalPos)â˜ƒ.getMemory(MemoryModuleType.MEETING_POINT).get()).pos();
      if (â˜ƒx.closerThan(â˜ƒ.blockPosition(), 3.0)) {
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         if (â˜ƒxx.is(Blocks.BELL)) {
            BellBlock â˜ƒxxx = (BellBlock)â˜ƒxx.getBlock();
            â˜ƒxxx.attemptToRing(â˜ƒ, â˜ƒ, â˜ƒx, null);
         }
      }
   }
}
