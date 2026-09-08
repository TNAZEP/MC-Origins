package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SleepInBed extends Behavior<LivingEntity> {
   public static final int COOLDOWN_AFTER_BEING_WOKEN = 100;
   private long nextOkStartTime;

   public SleepInBed() {
      super(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT, MemoryModuleType.LAST_WOKEN, MemoryStatus.REGISTERED));
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      if (â˜ƒ.isPassenger()) {
         return false;
      } else {
         Brain<?> â˜ƒ = â˜ƒ.getBrain();
         GlobalPos â˜ƒx = (GlobalPos)â˜ƒ.getMemory(MemoryModuleType.HOME).get();
         if (â˜ƒ.dimension() != â˜ƒx.dimension()) {
            return false;
         } else {
            Optional<Long> â˜ƒ = â˜ƒ.getMemory(MemoryModuleType.LAST_WOKEN);
            if (â˜ƒ.isPresent()) {
               long â˜ƒx = â˜ƒ.getGameTime() - â˜ƒ.get();
               if (â˜ƒx > 0L && â˜ƒx < 100L) {
                  return false;
               }
            }

            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒx.pos());
            return â˜ƒx.pos().closerThan(â˜ƒ.position(), 2.0) && â˜ƒ.is(BlockTags.BEDS) && !â˜ƒ.getValue(BedBlock.OCCUPIED);
         }
      }
   }

   @Override
   protected boolean canStillUse(ServerLevel var1, LivingEntity var2, long var3) {
      Optional<GlobalPos> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.HOME);
      if (!â˜ƒ.isPresent()) {
         return false;
      } else {
         BlockPos â˜ƒ = ((GlobalPos)â˜ƒ.get()).pos();
         return â˜ƒ.getBrain().isActive(Activity.REST) && â˜ƒ.getY() > (double)â˜ƒ.getY() + 0.4 && â˜ƒ.closerThan(â˜ƒ.position(), 1.14);
      }
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      if (â˜ƒ > this.nextOkStartTime) {
         InteractWithDoor.closeDoorsThatIHaveOpenedOrPassedThrough(â˜ƒ, â˜ƒ, null, null);
         â˜ƒ.startSleeping(((GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.HOME).get()).pos());
      }
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   @Override
   protected void stop(ServerLevel var1, LivingEntity var2, long var3) {
      if (â˜ƒ.isSleeping()) {
         â˜ƒ.stopSleeping();
         this.nextOkStartTime = â˜ƒ + 40L;
      }
   }
}
