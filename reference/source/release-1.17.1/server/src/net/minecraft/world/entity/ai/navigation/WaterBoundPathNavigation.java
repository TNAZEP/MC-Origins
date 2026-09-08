package net.minecraft.world.entity.ai.navigation;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class WaterBoundPathNavigation extends PathNavigation {
   private boolean allowBreaching;

   public WaterBoundPathNavigation(Mob var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected PathFinder createPathFinder(int var1) {
      this.allowBreaching = this.mob.getType() == EntityType.DOLPHIN;
      this.nodeEvaluator = new SwimNodeEvaluator(this.allowBreaching);
      return new PathFinder(this.nodeEvaluator, â˜ƒ);
   }

   @Override
   protected boolean canUpdatePath() {
      return this.allowBreaching || this.isInLiquid();
   }

   @Override
   protected Vec3 getTempMobPos() {
      return new Vec3(this.mob.getX(), this.mob.getY(0.5), this.mob.getZ());
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
   protected void followThePath() {
      if (this.path != null) {
         Vec3 â˜ƒ = this.getTempMobPos();
         float â˜ƒx = this.mob.getBbWidth();
         float â˜ƒxx = â˜ƒx > 0.75F ? â˜ƒx / 2.0F : 0.75F - â˜ƒx / 2.0F;
         Vec3 â˜ƒxxx = this.mob.getDeltaMovement();
         if (Math.abs(â˜ƒxxx.x) > 0.2 || Math.abs(â˜ƒxxx.z) > 0.2) {
            â˜ƒxx = (float)((double)â˜ƒxx * â˜ƒxxx.length() * 6.0);
         }

         int â˜ƒ = 6;
         Vec3 â˜ƒx = Vec3.atBottomCenterOf(this.path.getNextNodePos());
         if (Math.abs(this.mob.getX() - â˜ƒx.x) < (double)â˜ƒxx
            && Math.abs(this.mob.getZ() - â˜ƒx.z) < (double)â˜ƒxx
            && Math.abs(this.mob.getY() - â˜ƒx.y) < (double)(â˜ƒxx * 2.0F)) {
            this.path.advance();
         }

         for(int â˜ƒ = Math.min(this.path.getNextNodeIndex() + 6, this.path.getNodeCount() - 1); â˜ƒ > this.path.getNextNodeIndex(); --â˜ƒ) {
            â˜ƒx = this.path.getEntityPosAtNode(this.mob, â˜ƒ);
            if (!(â˜ƒx.distanceToSqr(â˜ƒ) > 36.0) && this.canMoveDirectly(â˜ƒ, â˜ƒx, 0, 0, 0)) {
               this.path.setNextNodeIndex(â˜ƒ);
               break;
            }
         }

         this.doStuckDetection(â˜ƒ);
      }
   }

   @Override
   protected void doStuckDetection(Vec3 var1) {
      if (this.tick - this.lastStuckCheck > 100) {
         if (â˜ƒ.distanceToSqr(this.lastStuckCheckPos) < 2.25) {
            this.stop();
         }

         this.lastStuckCheck = this.tick;
         this.lastStuckCheckPos = â˜ƒ;
      }

      if (this.path != null && !this.path.isDone()) {
         Vec3i â˜ƒ = this.path.getNextNodePos();
         if (â˜ƒ.equals(this.timeoutCachedNode)) {
            this.timeoutTimer += Util.getMillis() - this.lastTimeoutCheck;
         } else {
            this.timeoutCachedNode = â˜ƒ;
            double â˜ƒ = â˜ƒ.distanceTo(Vec3.atCenterOf(this.timeoutCachedNode));
            this.timeoutLimit = this.mob.getSpeed() > 0.0F ? â˜ƒ / (double)this.mob.getSpeed() * 100.0 : 0.0;
         }

         if (this.timeoutLimit > 0.0 && (double)this.timeoutTimer > this.timeoutLimit * 2.0) {
            this.timeoutCachedNode = Vec3i.ZERO;
            this.timeoutTimer = 0L;
            this.timeoutLimit = 0.0;
            this.stop();
         }

         this.lastTimeoutCheck = Util.getMillis();
      }
   }

   @Override
   protected boolean canMoveDirectly(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      Vec3 â˜ƒ = new Vec3(â˜ƒ.x, â˜ƒ.y + (double)this.mob.getBbHeight() * 0.5, â˜ƒ.z);
      return this.level.clip(new ClipContext(â˜ƒ, â˜ƒ, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob)).getType() == HitResult.Type.MISS;
   }

   @Override
   public boolean isStableDestination(BlockPos var1) {
      return !this.level.getBlockState(â˜ƒ).isSolidRender(this.level, â˜ƒ);
   }

   @Override
   public void setCanFloat(boolean var1) {
   }
}
