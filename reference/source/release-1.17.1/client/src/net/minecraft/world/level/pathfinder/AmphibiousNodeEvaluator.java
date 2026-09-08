package net.minecraft.world.level.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;

public class AmphibiousNodeEvaluator extends WalkNodeEvaluator {
   private final boolean prefersShallowSwimming;
   private float oldWalkableCost;
   private float oldWaterBorderCost;

   public AmphibiousNodeEvaluator(boolean var1) {
      this.prefersShallowSwimming = â˜ƒ;
   }

   @Override
   public void prepare(PathNavigationRegion var1, Mob var2) {
      super.prepare(â˜ƒ, â˜ƒ);
      â˜ƒ.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
      this.oldWalkableCost = â˜ƒ.getPathfindingMalus(BlockPathTypes.WALKABLE);
      â˜ƒ.setPathfindingMalus(BlockPathTypes.WALKABLE, 6.0F);
      this.oldWaterBorderCost = â˜ƒ.getPathfindingMalus(BlockPathTypes.WATER_BORDER);
      â˜ƒ.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 4.0F);
   }

   @Override
   public void done() {
      this.mob.setPathfindingMalus(BlockPathTypes.WALKABLE, this.oldWalkableCost);
      this.mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, this.oldWaterBorderCost);
      super.done();
   }

   @Override
   public Node getStart() {
      return this.getNode(Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY + 0.5), Mth.floor(this.mob.getBoundingBox().minZ));
   }

   @Override
   public Target getGoal(double var1, double var3, double var5) {
      return new Target(this.getNode(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ + 0.5), Mth.floor(â˜ƒ)));
   }

   @Override
   public int getNeighbors(Node[] var1, Node var2) {
      int â˜ƒx = super.getNeighbors(â˜ƒ, â˜ƒ);
      BlockPathTypes â˜ƒxx = this.getCachedBlockType(this.mob, â˜ƒ.x, â˜ƒ.y + 1, â˜ƒ.z);
      BlockPathTypes â˜ƒxxx = this.getCachedBlockType(this.mob, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      int â˜ƒ;
      if (this.mob.getPathfindingMalus(â˜ƒxx) >= 0.0F && â˜ƒxxx != BlockPathTypes.STICKY_HONEY) {
         â˜ƒ = Mth.floor(Math.max(1.0F, this.mob.maxUpStep));
      } else {
         â˜ƒ = 0;
      }

      double â˜ƒ = this.getFloorLevel(new BlockPos(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z));
      Node â˜ƒx = this.findAcceptedNode(â˜ƒ.x, â˜ƒ.y + 1, â˜ƒ.z, Math.max(0, â˜ƒ - 1), â˜ƒ, Direction.UP, â˜ƒxxx);
      Node â˜ƒxx = this.findAcceptedNode(â˜ƒ.x, â˜ƒ.y - 1, â˜ƒ.z, â˜ƒ, â˜ƒ, Direction.DOWN, â˜ƒxxx);
      if (this.isNeighborValid(â˜ƒx, â˜ƒ)) {
         â˜ƒ[â˜ƒx++] = â˜ƒx;
      }

      if (this.isNeighborValid(â˜ƒxx, â˜ƒ) && â˜ƒxxx != BlockPathTypes.TRAPDOOR) {
         â˜ƒ[â˜ƒx++] = â˜ƒxx;
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒx; ++â˜ƒ) {
         Node â˜ƒx = â˜ƒ[â˜ƒ];
         if (â˜ƒx.type == BlockPathTypes.WATER && this.prefersShallowSwimming && â˜ƒx.y < this.mob.level.getSeaLevel() - 10) {
            ++â˜ƒx.costMalus;
         }
      }

      return â˜ƒx;
   }

   @Override
   protected double getFloorLevel(BlockPos var1) {
      return this.mob.isInWater() ? (double)â˜ƒ.getY() + 0.5 : super.getFloorLevel(â˜ƒ);
   }

   @Override
   protected boolean isAmphibious() {
      return true;
   }

   @Override
   public BlockPathTypes getBlockPathType(BlockGetter var1, int var2, int var3, int var4) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      BlockPathTypes â˜ƒx = getBlockPathTypeRaw(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒ, â˜ƒ));
      if (â˜ƒx == BlockPathTypes.WATER) {
         for(Direction â˜ƒxx : Direction.values()) {
            BlockPathTypes â˜ƒxxx = getBlockPathTypeRaw(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒ, â˜ƒ).move(â˜ƒxx));
            if (â˜ƒxxx == BlockPathTypes.BLOCKED) {
               return BlockPathTypes.WATER_BORDER;
            }
         }

         return BlockPathTypes.WATER;
      } else {
         return getBlockPathTypeStatic(â˜ƒ, â˜ƒ);
      }
   }
}
