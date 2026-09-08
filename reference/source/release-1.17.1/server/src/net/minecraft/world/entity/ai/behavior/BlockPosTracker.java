package net.minecraft.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BlockPosTracker implements PositionTracker {
   private final BlockPos blockPos;
   private final Vec3 centerPosition;

   public BlockPosTracker(BlockPos var1) {
      this.blockPos = â˜ƒ;
      this.centerPosition = Vec3.atCenterOf(â˜ƒ);
   }

   @Override
   public Vec3 currentPosition() {
      return this.centerPosition;
   }

   @Override
   public BlockPos currentBlockPosition() {
      return this.blockPos;
   }

   @Override
   public boolean isVisibleBy(LivingEntity var1) {
      return true;
   }

   public String toString() {
      return "BlockPosTracker{blockPos=" + this.blockPos + ", centerPosition=" + this.centerPosition + "}";
   }
}
