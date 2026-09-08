package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class FlyingPathNavigation extends PathNavigation {
   public FlyingPathNavigation(Mob var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected PathFinder createPathFinder(int var1) {
      this.nodeEvaluator = new FlyNodeEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return new PathFinder(this.nodeEvaluator, â˜ƒ);
   }

   @Override
   protected boolean canUpdatePath() {
      return this.canFloat() && this.isInLiquid() || !this.mob.isPassenger();
   }

   @Override
   protected Vec3 getTempMobPos() {
      return this.mob.position();
   }

   @Override
   public Path createPath(Entity var1, int var2) {
      return this.createPath(â˜ƒ.blockPosition(), â˜ƒ);
   }

   @Override
   public void tick() {
      ++this.tick;
      if (this.hasDelayedRecomputation) {
         this.recomputePath();
      }

      if (!this.isDone()) {
         if (this.canUpdatePath()) {
            this.followThePath();
         } else if (this.path != null && !this.path.isDone()) {
            Vec3 â˜ƒ = this.path.getNextEntityPos(this.mob);
            if (this.mob.getBlockX() == Mth.floor(â˜ƒ.x) && this.mob.getBlockY() == Mth.floor(â˜ƒ.y) && this.mob.getBlockZ() == Mth.floor(â˜ƒ.z)) {
               this.path.advance();
            }
         }

         DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
         if (!this.isDone()) {
            Vec3 â˜ƒ = this.path.getNextEntityPos(this.mob);
            this.mob.getMoveControl().setWantedPosition(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, this.speedModifier);
         }
      }
   }

   @Override
   protected boolean canMoveDirectly(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      int â˜ƒ = Mth.floor(â˜ƒ.x);
      int â˜ƒx = Mth.floor(â˜ƒ.y);
      int â˜ƒxx = Mth.floor(â˜ƒ.z);
      double â˜ƒxxx = â˜ƒ.x - â˜ƒ.x;
      double â˜ƒxxxx = â˜ƒ.y - â˜ƒ.y;
      double â˜ƒxxxxx = â˜ƒ.z - â˜ƒ.z;
      double â˜ƒxxxxxx = â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx;
      if (â˜ƒxxxxxx < 1.0E-8) {
         return false;
      } else {
         double â˜ƒ = 1.0 / Math.sqrt(â˜ƒxxxxxx);
         â˜ƒxxx *= â˜ƒ;
         â˜ƒxxxx *= â˜ƒ;
         â˜ƒxxxxx *= â˜ƒ;
         double â˜ƒx = 1.0 / Math.abs(â˜ƒxxx);
         double â˜ƒxx = 1.0 / Math.abs(â˜ƒxxxx);
         double â˜ƒxxx = 1.0 / Math.abs(â˜ƒxxxxx);
         double â˜ƒxxxx = (double)â˜ƒ - â˜ƒ.x;
         double â˜ƒxxxxx = (double)â˜ƒx - â˜ƒ.y;
         double â˜ƒxxxxxx = (double)â˜ƒxx - â˜ƒ.z;
         if (â˜ƒxxx >= 0.0) {
            ++â˜ƒxxxx;
         }

         if (â˜ƒxxxx >= 0.0) {
            ++â˜ƒxxxxx;
         }

         if (â˜ƒxxxxx >= 0.0) {
            ++â˜ƒxxxxxx;
         }

         â˜ƒxxxx /= â˜ƒxxx;
         â˜ƒxxxxx /= â˜ƒxxxx;
         â˜ƒxxxxxx /= â˜ƒxxxxx;
         int â˜ƒ = â˜ƒxxx < 0.0 ? -1 : 1;
         int â˜ƒx = â˜ƒxxxx < 0.0 ? -1 : 1;
         int â˜ƒxx = â˜ƒxxxxx < 0.0 ? -1 : 1;
         int â˜ƒxxx = Mth.floor(â˜ƒ.x);
         int â˜ƒxxxx = Mth.floor(â˜ƒ.y);
         int â˜ƒxxxxx = Mth.floor(â˜ƒ.z);
         int â˜ƒxxxxxx = â˜ƒxxx - â˜ƒ;
         int â˜ƒxxxxxxx = â˜ƒxxxx - â˜ƒx;
         int â˜ƒxxxxxxxx = â˜ƒxxxxx - â˜ƒxx;

         while(â˜ƒxxxxxx * â˜ƒ > 0 || â˜ƒxxxxxxx * â˜ƒx > 0 || â˜ƒxxxxxxxx * â˜ƒxx > 0) {
            if (â˜ƒxxxx < â˜ƒxxxxxx && â˜ƒxxxx <= â˜ƒxxxxx) {
               â˜ƒxxxx += â˜ƒx;
               â˜ƒ += â˜ƒ;
               â˜ƒxxxxxx = â˜ƒxxx - â˜ƒ;
            } else if (â˜ƒxxxxx < â˜ƒxxxx && â˜ƒxxxxx <= â˜ƒxxxxxx) {
               â˜ƒxxxxx += â˜ƒxx;
               â˜ƒx += â˜ƒx;
               â˜ƒxxxxxxx = â˜ƒxxxx - â˜ƒx;
            } else {
               â˜ƒxxxxxx += â˜ƒxxx;
               â˜ƒxx += â˜ƒxx;
               â˜ƒxxxxxxxx = â˜ƒxxxxx - â˜ƒxx;
            }
         }

         return true;
      }
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

   @Override
   public boolean isStableDestination(BlockPos var1) {
      return this.level.getBlockState(â˜ƒ).entityCanStandOn(this.level, â˜ƒ, this.mob);
   }
}
