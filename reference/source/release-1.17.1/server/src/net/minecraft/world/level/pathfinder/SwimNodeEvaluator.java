package net.minecraft.world.level.pathfinder;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class SwimNodeEvaluator extends NodeEvaluator {
   private final boolean allowBreaching;

   public SwimNodeEvaluator(boolean var1) {
      this.allowBreaching = â˜ƒ;
   }

   @Override
   public Node getStart() {
      return super.getNode(
         Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY + 0.5), Mth.floor(this.mob.getBoundingBox().minZ)
      );
   }

   @Override
   public Target getGoal(double var1, double var3, double var5) {
      return new Target(
         super.getNode(Mth.floor(â˜ƒ - (double)(this.mob.getBbWidth() / 2.0F)), Mth.floor(â˜ƒ + 0.5), Mth.floor(â˜ƒ - (double)(this.mob.getBbWidth() / 2.0F)))
      );
   }

   @Override
   public int getNeighbors(Node[] var1, Node var2) {
      int â˜ƒ = 0;

      for(Direction â˜ƒx : Direction.values()) {
         Node â˜ƒxx = this.getWaterNode(â˜ƒ.x + â˜ƒx.getStepX(), â˜ƒ.y + â˜ƒx.getStepY(), â˜ƒ.z + â˜ƒx.getStepZ());
         if (â˜ƒxx != null && !â˜ƒxx.closed) {
            â˜ƒ[â˜ƒ++] = â˜ƒxx;
         }
      }

      return â˜ƒ;
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4, Mob var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      return this.getBlockPathType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ);
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.isEmpty() && â˜ƒxx.isPathfindable(â˜ƒ, â˜ƒ.below(), PathComputationType.WATER) && â˜ƒxx.isAir()) {
         return BlockPathTypes.BREACH;
      } else {
         return â˜ƒx.is(FluidTags.WATER) && â˜ƒxx.isPathfindable(â˜ƒ, â˜ƒ, PathComputationType.WATER) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
      }
   }

   @Nullable
   private Node getWaterNode(int var1, int var2, int var3) {
      BlockPathTypes â˜ƒ = this.isFree(â˜ƒ, â˜ƒ, â˜ƒ);
      return (!this.allowBreaching || â˜ƒ != BlockPathTypes.BREACH) && â˜ƒ != BlockPathTypes.WATER ? null : this.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   protected Node getNode(int var1, int var2, int var3) {
      Node â˜ƒ = null;
      BlockPathTypes â˜ƒx = this.getBlockPathType(this.mob.level, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒxx = this.mob.getPathfindingMalus(â˜ƒx);
      if (â˜ƒxx >= 0.0F) {
         â˜ƒ = super.getNode(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.type = â˜ƒx;
         â˜ƒ.costMalus = Math.max(â˜ƒ.costMalus, â˜ƒxx);
         if (this.level.getFluidState(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ)).isEmpty()) {
            â˜ƒ.costMalus += 8.0F;
         }
      }

      return â˜ƒx == BlockPathTypes.OPEN ? â˜ƒ : â˜ƒ;
   }

   private BlockPathTypes isFree(int var1, int var2, int var3) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ + this.entityWidth; ++â˜ƒx) {
         for(int â˜ƒxx = â˜ƒ; â˜ƒxx < â˜ƒ + this.entityHeight; ++â˜ƒxx) {
            for(int â˜ƒxxx = â˜ƒ; â˜ƒxxx < â˜ƒ + this.entityDepth; ++â˜ƒxxx) {
               FluidState â˜ƒxxxx = this.level.getFluidState(â˜ƒ.set(â˜ƒx, â˜ƒxx, â˜ƒxxx));
               BlockState â˜ƒxxxxx = this.level.getBlockState(â˜ƒ.set(â˜ƒx, â˜ƒxx, â˜ƒxxx));
               if (â˜ƒxxxx.isEmpty() && â˜ƒxxxxx.isPathfindable(this.level, â˜ƒ.below(), PathComputationType.WATER) && â˜ƒxxxxx.isAir()) {
                  return BlockPathTypes.BREACH;
               }

               if (!â˜ƒxxxx.is(FluidTags.WATER)) {
                  return BlockPathTypes.BLOCKED;
               }
            }
         }
      }

      BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
      return â˜ƒx.isPathfindable(this.level, â˜ƒ, PathComputationType.WATER) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
   }
}
