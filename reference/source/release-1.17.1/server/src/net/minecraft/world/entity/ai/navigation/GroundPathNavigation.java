package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class GroundPathNavigation extends PathNavigation {
   private boolean avoidSun;

   public GroundPathNavigation(Mob var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected PathFinder createPathFinder(int var1) {
      this.nodeEvaluator = new WalkNodeEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return new PathFinder(this.nodeEvaluator, â˜ƒ);
   }

   @Override
   protected boolean canUpdatePath() {
      return this.mob.isOnGround() || this.isInLiquid() || this.mob.isPassenger();
   }

   @Override
   protected Vec3 getTempMobPos() {
      return new Vec3(this.mob.getX(), (double)this.getSurfaceY(), this.mob.getZ());
   }

   @Override
   public Path createPath(BlockPos var1, int var2) {
      if (this.level.getBlockState(â˜ƒ).isAir()) {
         BlockPos â˜ƒ = â˜ƒ.below();

         while(â˜ƒ.getY() > this.level.getMinBuildHeight() && this.level.getBlockState(â˜ƒ).isAir()) {
            â˜ƒ = â˜ƒ.below();
         }

         if (â˜ƒ.getY() > this.level.getMinBuildHeight()) {
            return super.createPath(â˜ƒ.above(), â˜ƒ);
         }

         while(â˜ƒ.getY() < this.level.getMaxBuildHeight() && this.level.getBlockState(â˜ƒ).isAir()) {
            â˜ƒ = â˜ƒ.above();
         }

         â˜ƒ = â˜ƒ;
      }

      if (!this.level.getBlockState(â˜ƒ).getMaterial().isSolid()) {
         return super.createPath(â˜ƒ, â˜ƒ);
      } else {
         BlockPos â˜ƒ = â˜ƒ.above();

         while(â˜ƒ.getY() < this.level.getMaxBuildHeight() && this.level.getBlockState(â˜ƒ).getMaterial().isSolid()) {
            â˜ƒ = â˜ƒ.above();
         }

         return super.createPath(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public Path createPath(Entity var1, int var2) {
      return this.createPath(â˜ƒ.blockPosition(), â˜ƒ);
   }

   private int getSurfaceY() {
      if (this.mob.isInWater() && this.canFloat()) {
         int â˜ƒ = this.mob.getBlockY();
         BlockState â˜ƒx = this.level.getBlockState(new BlockPos(this.mob.getX(), (double)â˜ƒ, this.mob.getZ()));
         int â˜ƒxx = 0;

         while(â˜ƒx.is(Blocks.WATER)) {
            â˜ƒx = this.level.getBlockState(new BlockPos(this.mob.getX(), (double)(++â˜ƒ), this.mob.getZ()));
            if (++â˜ƒxx > 16) {
               return this.mob.getBlockY();
            }
         }

         return â˜ƒ;
      } else {
         return Mth.floor(this.mob.getY() + 0.5);
      }
   }

   @Override
   protected void trimPath() {
      super.trimPath();
      if (this.avoidSun) {
         if (this.level.canSeeSky(new BlockPos(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()))) {
            return;
         }

         for(int â˜ƒ = 0; â˜ƒ < this.path.getNodeCount(); ++â˜ƒ) {
            Node â˜ƒx = this.path.getNode(â˜ƒ);
            if (this.level.canSeeSky(new BlockPos(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z))) {
               this.path.truncateNodes(â˜ƒ);
               return;
            }
         }
      }
   }

   @Override
   protected boolean canMoveDirectly(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      int â˜ƒ = Mth.floor(â˜ƒ.x);
      int â˜ƒx = Mth.floor(â˜ƒ.z);
      double â˜ƒxx = â˜ƒ.x - â˜ƒ.x;
      double â˜ƒxxx = â˜ƒ.z - â˜ƒ.z;
      double â˜ƒxxxx = â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx;
      if (â˜ƒxxxx < 1.0E-8) {
         return false;
      } else {
         double â˜ƒ = 1.0 / Math.sqrt(â˜ƒxxxx);
         â˜ƒxx *= â˜ƒ;
         â˜ƒxxx *= â˜ƒ;
         â˜ƒ += 2;
         â˜ƒ += 2;
         if (!this.canWalkOn(â˜ƒ, Mth.floor(â˜ƒ.y), â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx)) {
            return false;
         } else {
            â˜ƒ -= 2;
            â˜ƒ -= 2;
            double â˜ƒ = 1.0 / Math.abs(â˜ƒxx);
            double â˜ƒx = 1.0 / Math.abs(â˜ƒxxx);
            double â˜ƒxx = (double)â˜ƒ - â˜ƒ.x;
            double â˜ƒxxx = (double)â˜ƒx - â˜ƒ.z;
            if (â˜ƒxx >= 0.0) {
               ++â˜ƒxx;
            }

            if (â˜ƒxxx >= 0.0) {
               ++â˜ƒxxx;
            }

            â˜ƒxx /= â˜ƒxx;
            â˜ƒxxx /= â˜ƒxxx;
            int â˜ƒ = â˜ƒxx < 0.0 ? -1 : 1;
            int â˜ƒx = â˜ƒxxx < 0.0 ? -1 : 1;
            int â˜ƒxx = Mth.floor(â˜ƒ.x);
            int â˜ƒxxx = Mth.floor(â˜ƒ.z);
            int â˜ƒxxxx = â˜ƒxx - â˜ƒ;
            int â˜ƒxxxxx = â˜ƒxxx - â˜ƒx;

            while(â˜ƒxxxx * â˜ƒ > 0 || â˜ƒxxxxx * â˜ƒx > 0) {
               if (â˜ƒxx < â˜ƒxxx) {
                  â˜ƒxx += â˜ƒ;
                  â˜ƒ += â˜ƒ;
                  â˜ƒxxxx = â˜ƒxx - â˜ƒ;
               } else {
                  â˜ƒxxx += â˜ƒx;
                  â˜ƒx += â˜ƒx;
                  â˜ƒxxxxx = â˜ƒxxx - â˜ƒx;
               }

               if (!this.canWalkOn(â˜ƒ, Mth.floor(â˜ƒ.y), â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean canWalkOn(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      int â˜ƒ = â˜ƒ - â˜ƒ / 2;
      int â˜ƒx = â˜ƒ - â˜ƒ / 2;
      if (!this.canWalkAbove(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ + â˜ƒ; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒx; â˜ƒx < â˜ƒx + â˜ƒ; ++â˜ƒx) {
               double â˜ƒxx = (double)â˜ƒ + 0.5 - â˜ƒ.x;
               double â˜ƒxxx = (double)â˜ƒx + 0.5 - â˜ƒ.z;
               if (!(â˜ƒxx * â˜ƒ + â˜ƒxxx * â˜ƒ < 0.0)) {
                  BlockPathTypes â˜ƒxxxx = this.nodeEvaluator.getBlockPathType(this.level, â˜ƒ, â˜ƒ - 1, â˜ƒx, this.mob, â˜ƒ, â˜ƒ, â˜ƒ, true, true);
                  if (!this.hasValidPathType(â˜ƒxxxx)) {
                     return false;
                  }

                  â˜ƒxxxx = this.nodeEvaluator.getBlockPathType(this.level, â˜ƒ, â˜ƒ, â˜ƒx, this.mob, â˜ƒ, â˜ƒ, â˜ƒ, true, true);
                  float â˜ƒxxxx = this.mob.getPathfindingMalus(â˜ƒxxxx);
                  if (â˜ƒxxxx < 0.0F || â˜ƒxxxx >= 8.0F) {
                     return false;
                  }

                  if (â˜ƒxxxx == BlockPathTypes.DAMAGE_FIRE || â˜ƒxxxx == BlockPathTypes.DANGER_FIRE || â˜ƒxxxx == BlockPathTypes.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   protected boolean hasValidPathType(BlockPathTypes var1) {
      if (â˜ƒ == BlockPathTypes.WATER) {
         return false;
      } else if (â˜ƒ == BlockPathTypes.LAVA) {
         return false;
      } else {
         return â˜ƒ != BlockPathTypes.OPEN;
      }
   }

   private boolean canWalkAbove(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      for(BlockPos â˜ƒ : BlockPos.betweenClosed(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ), new BlockPos(â˜ƒ + â˜ƒ - 1, â˜ƒ + â˜ƒ - 1, â˜ƒ + â˜ƒ - 1))) {
         double â˜ƒx = (double)â˜ƒ.getX() + 0.5 - â˜ƒ.x;
         double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5 - â˜ƒ.z;
         if (!(â˜ƒx * â˜ƒ + â˜ƒxx * â˜ƒ < 0.0) && !this.level.getBlockState(â˜ƒ).isPathfindable(this.level, â˜ƒ, PathComputationType.LAND)) {
            return false;
         }
      }

      return true;
   }

   public void setCanOpenDoors(boolean var1) {
      this.nodeEvaluator.setCanOpenDoors(â˜ƒ);
   }

   public boolean canPassDoors() {
      return this.nodeEvaluator.canPassDoors();
   }

   public void setCanPassDoors(boolean var1) {
      this.nodeEvaluator.setCanPassDoors(â˜ƒ);
   }

   public boolean canOpenDoors() {
      return this.nodeEvaluator.canPassDoors();
   }

   public void setAvoidSun(boolean var1) {
      this.avoidSun = â˜ƒ;
   }
}
