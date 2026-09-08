package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WaterAvoidingRandomFlyingGoal extends WaterAvoidingRandomStrollGoal {
   public WaterAvoidingRandomFlyingGoal(PathfinderMob var1, double var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   protected Vec3 getPosition() {
      Vec3 â˜ƒ = null;
      if (this.mob.isInWater()) {
         â˜ƒ = LandRandomPos.getPos(this.mob, 15, 15);
      }

      if (this.mob.getRandom().nextFloat() >= this.probability) {
         â˜ƒ = this.getTreePos();
      }

      return â˜ƒ == null ? super.getPosition() : â˜ƒ;
   }

   @Nullable
   private Vec3 getTreePos() {
      BlockPos â˜ƒ = this.mob.blockPosition();
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

      for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(
         Mth.floor(this.mob.getX() - 3.0),
         Mth.floor(this.mob.getY() - 6.0),
         Mth.floor(this.mob.getZ() - 3.0),
         Mth.floor(this.mob.getX() + 3.0),
         Mth.floor(this.mob.getY() + 6.0),
         Mth.floor(this.mob.getZ() + 3.0)
      )) {
         if (!â˜ƒ.equals(â˜ƒxxx)) {
            BlockState â˜ƒxxxx = this.mob.level.getBlockState(â˜ƒxx.setWithOffset(â˜ƒxxx, Direction.DOWN));
            boolean â˜ƒxxxxx = â˜ƒxxxx.getBlock() instanceof LeavesBlock || â˜ƒxxxx.is(BlockTags.LOGS);
            if (â˜ƒxxxxx && this.mob.level.isEmptyBlock(â˜ƒxxx) && this.mob.level.isEmptyBlock(â˜ƒx.setWithOffset(â˜ƒxxx, Direction.UP))) {
               return Vec3.atBottomCenterOf(â˜ƒxxx);
            }
         }
      }

      return null;
   }
}
