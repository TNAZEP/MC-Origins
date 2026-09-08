package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TryFindWater extends Behavior<PathfinderMob> {
   private final int range;
   private final float speedModifier;
   private long nextOkStartTime;

   public TryFindWater(int var1, float var2) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED
         )
      );
      this.range = â˜ƒ;
      this.speedModifier = â˜ƒ;
   }

   protected void stop(ServerLevel var1, PathfinderMob var2, long var3) {
      this.nextOkStartTime = â˜ƒ + 20L + 2L;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return !â˜ƒ.level.getFluidState(â˜ƒ.blockPosition()).is(FluidTags.WATER);
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      if (â˜ƒ >= this.nextOkStartTime) {
         BlockPos â˜ƒ = null;
         BlockPos â˜ƒx = null;
         BlockPos â˜ƒxx = â˜ƒ.blockPosition();

         for(BlockPos â˜ƒxxx : BlockPos.withinManhattan(â˜ƒxx, this.range, this.range, this.range)) {
            if (â˜ƒxxx.getX() != â˜ƒxx.getX() || â˜ƒxxx.getZ() != â˜ƒxx.getZ()) {
               BlockState â˜ƒxxxx = â˜ƒ.level.getBlockState(â˜ƒxxx.above());
               BlockState â˜ƒxxxxx = â˜ƒ.level.getBlockState(â˜ƒxxx);
               if (â˜ƒxxxxx.is(Blocks.WATER)) {
                  if (â˜ƒxxxx.isAir()) {
                     â˜ƒ = â˜ƒxxx.immutable();
                     break;
                  }

                  if (â˜ƒx == null && !â˜ƒxxx.closerThan(â˜ƒ.position(), 1.5)) {
                     â˜ƒx = â˜ƒxxx.immutable();
                  }
               }
            }
         }

         if (â˜ƒ == null) {
            â˜ƒ = â˜ƒx;
         }

         if (â˜ƒ != null) {
            this.nextOkStartTime = â˜ƒ + 40L;
            BehaviorUtils.setWalkAndLookTargetMemories(â˜ƒ, â˜ƒ, this.speedModifier, 0);
         }
      }
   }
}
