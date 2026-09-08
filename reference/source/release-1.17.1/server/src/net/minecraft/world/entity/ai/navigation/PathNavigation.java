package net.minecraft.world.entity.ai.navigation;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public abstract class PathNavigation {
   private static final int MAX_TIME_RECOMPUTE = 20;
   protected final Mob mob;
   protected final Level level;
   @Nullable
   protected Path path;
   protected double speedModifier;
   protected int tick;
   protected int lastStuckCheck;
   protected Vec3 lastStuckCheckPos = Vec3.ZERO;
   protected Vec3i timeoutCachedNode = Vec3i.ZERO;
   protected long timeoutTimer;
   protected long lastTimeoutCheck;
   protected double timeoutLimit;
   protected float maxDistanceToWaypoint = 0.5F;
   protected boolean hasDelayedRecomputation;
   protected long timeLastRecompute;
   protected NodeEvaluator nodeEvaluator;
   private BlockPos targetPos;
   private int reachRange;
   private float maxVisitedNodesMultiplier = 1.0F;
   private final PathFinder pathFinder;
   private boolean isStuck;

   public PathNavigation(Mob var1, Level var2) {
      this.mob = â˜ƒ;
      this.level = â˜ƒ;
      int â˜ƒ = Mth.floor(â˜ƒ.getAttributeValue(Attributes.FOLLOW_RANGE) * 16.0);
      this.pathFinder = this.createPathFinder(â˜ƒ);
   }

   public void resetMaxVisitedNodesMultiplier() {
      this.maxVisitedNodesMultiplier = 1.0F;
   }

   public void setMaxVisitedNodesMultiplier(float var1) {
      this.maxVisitedNodesMultiplier = â˜ƒ;
   }

   public BlockPos getTargetPos() {
      return this.targetPos;
   }

   protected abstract PathFinder createPathFinder(int var1);

   public void setSpeedModifier(double var1) {
      this.speedModifier = â˜ƒ;
   }

   public boolean hasDelayedRecomputation() {
      return this.hasDelayedRecomputation;
   }

   public void recomputePath() {
      if (this.level.getGameTime() - this.timeLastRecompute > 20L) {
         if (this.targetPos != null) {
            this.path = null;
            this.path = this.createPath(this.targetPos, this.reachRange);
            this.timeLastRecompute = this.level.getGameTime();
            this.hasDelayedRecomputation = false;
         }
      } else {
         this.hasDelayedRecomputation = true;
      }
   }

   @Nullable
   public final Path createPath(double var1, double var3, double var5, int var7) {
      return this.createPath(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   @Nullable
   public Path createPath(Stream<BlockPos> var1, int var2) {
      return this.createPath((Set<BlockPos>)â˜ƒ.collect(Collectors.toSet()), 8, false, â˜ƒ);
   }

   @Nullable
   public Path createPath(Set<BlockPos> var1, int var2) {
      return this.createPath(â˜ƒ, 8, false, â˜ƒ);
   }

   @Nullable
   public Path createPath(BlockPos var1, int var2) {
      return this.createPath(ImmutableSet.of(â˜ƒ), 8, false, â˜ƒ);
   }

   @Nullable
   public Path createPath(BlockPos var1, int var2, int var3) {
      return this.createPath(ImmutableSet.of(â˜ƒ), 8, false, â˜ƒ, (float)â˜ƒ);
   }

   @Nullable
   public Path createPath(Entity var1, int var2) {
      return this.createPath(ImmutableSet.of(â˜ƒ.blockPosition()), 16, true, â˜ƒ);
   }

   @Nullable
   protected Path createPath(Set<BlockPos> var1, int var2, boolean var3, int var4) {
      return this.createPath(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (float)this.mob.getAttributeValue(Attributes.FOLLOW_RANGE));
   }

   @Nullable
   protected Path createPath(Set<BlockPos> var1, int var2, boolean var3, int var4, float var5) {
      if (â˜ƒ.isEmpty()) {
         return null;
      } else if (this.mob.getY() < (double)this.level.getMinBuildHeight()) {
         return null;
      } else if (!this.canUpdatePath()) {
         return null;
      } else if (this.path != null && !this.path.isDone() && â˜ƒ.contains(this.targetPos)) {
         return this.path;
      } else {
         this.level.getProfiler().push("pathfind");
         BlockPos â˜ƒ = â˜ƒ ? this.mob.blockPosition().above() : this.mob.blockPosition();
         int â˜ƒx = (int)(â˜ƒ + (float)â˜ƒ);
         PathNavigationRegion â˜ƒxx = new PathNavigationRegion(this.level, â˜ƒ.offset(-â˜ƒx, -â˜ƒx, -â˜ƒx), â˜ƒ.offset(â˜ƒx, â˜ƒx, â˜ƒx));
         Path â˜ƒxxx = this.pathFinder.findPath(â˜ƒxx, this.mob, â˜ƒ, â˜ƒ, â˜ƒ, this.maxVisitedNodesMultiplier);
         this.level.getProfiler().pop();
         if (â˜ƒxxx != null && â˜ƒxxx.getTarget() != null) {
            this.targetPos = â˜ƒxxx.getTarget();
            this.reachRange = â˜ƒ;
            this.resetStuckTimeout();
         }

         return â˜ƒxxx;
      }
   }

   public boolean moveTo(double var1, double var3, double var5, double var7) {
      return this.moveTo(this.createPath(â˜ƒ, â˜ƒ, â˜ƒ, 1), â˜ƒ);
   }

   public boolean moveTo(Entity var1, double var2) {
      Path â˜ƒ = this.createPath(â˜ƒ, 1);
      return â˜ƒ != null && this.moveTo(â˜ƒ, â˜ƒ);
   }

   public boolean moveTo(@Nullable Path var1, double var2) {
      if (â˜ƒ == null) {
         this.path = null;
         return false;
      } else {
         if (!â˜ƒ.sameAs(this.path)) {
            this.path = â˜ƒ;
         }

         if (this.isDone()) {
            return false;
         } else {
            this.trimPath();
            if (this.path.getNodeCount() <= 0) {
               return false;
            } else {
               this.speedModifier = â˜ƒ;
               Vec3 â˜ƒ = this.getTempMobPos();
               this.lastStuckCheck = this.tick;
               this.lastStuckCheckPos = â˜ƒ;
               return true;
            }
         }
      }
   }

   @Nullable
   public Path getPath() {
      return this.path;
   }

   public void tick() {
      ++this.tick;
      if (this.hasDelayedRecomputation) {
         this.recomputePath();
      }

      if (!this.isDone()) {
         if (this.canUpdatePath()) {
            this.followThePath();
         } else if (this.path != null && !this.path.isDone()) {
            Vec3 â˜ƒ = this.getTempMobPos();
            Vec3 â˜ƒx = this.path.getNextEntityPos(this.mob);
            if (â˜ƒ.y > â˜ƒx.y && !this.mob.isOnGround() && Mth.floor(â˜ƒ.x) == Mth.floor(â˜ƒx.x) && Mth.floor(â˜ƒ.z) == Mth.floor(â˜ƒx.z)) {
               this.path.advance();
            }
         }

         DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
         if (!this.isDone()) {
            Vec3 â˜ƒ = this.path.getNextEntityPos(this.mob);
            BlockPos â˜ƒx = new BlockPos(â˜ƒ);
            this.mob
               .getMoveControl()
               .setWantedPosition(
                  â˜ƒ.x, this.level.getBlockState(â˜ƒx.below()).isAir() ? â˜ƒ.y : WalkNodeEvaluator.getFloorLevel(this.level, â˜ƒx), â˜ƒ.z, this.speedModifier
               );
         }
      }
   }

   protected void followThePath() {
      Vec3 â˜ƒ = this.getTempMobPos();
      this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
      Vec3i â˜ƒx = this.path.getNextNodePos();
      double â˜ƒxx = Math.abs(this.mob.getX() - ((double)â˜ƒx.getX() + 0.5));
      double â˜ƒxxx = Math.abs(this.mob.getY() - (double)â˜ƒx.getY());
      double â˜ƒxxxx = Math.abs(this.mob.getZ() - ((double)â˜ƒx.getZ() + 0.5));
      boolean â˜ƒxxxxx = â˜ƒxx < (double)this.maxDistanceToWaypoint && â˜ƒxxxx < (double)this.maxDistanceToWaypoint && â˜ƒxxx < 1.0;
      if (â˜ƒxxxxx || this.mob.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(â˜ƒ)) {
         this.path.advance();
      }

      this.doStuckDetection(â˜ƒ);
   }

   private boolean shouldTargetNextNodeInDirection(Vec3 var1) {
      if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
         return false;
      } else {
         Vec3 â˜ƒ = Vec3.atBottomCenterOf(this.path.getNextNodePos());
         if (!â˜ƒ.closerThan(â˜ƒ, 2.0)) {
            return false;
         } else {
            Vec3 â˜ƒ = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
            Vec3 â˜ƒx = â˜ƒ.subtract(â˜ƒ);
            Vec3 â˜ƒxx = â˜ƒ.subtract(â˜ƒ);
            return â˜ƒx.dot(â˜ƒxx) > 0.0;
         }
      }
   }

   protected void doStuckDetection(Vec3 var1) {
      if (this.tick - this.lastStuckCheck > 100) {
         if (â˜ƒ.distanceToSqr(this.lastStuckCheckPos) < 2.25) {
            this.isStuck = true;
            this.stop();
         } else {
            this.isStuck = false;
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
            double â˜ƒ = â˜ƒ.distanceTo(Vec3.atBottomCenterOf(this.timeoutCachedNode));
            this.timeoutLimit = this.mob.getSpeed() > 0.0F ? â˜ƒ / (double)this.mob.getSpeed() * 1000.0 : 0.0;
         }

         if (this.timeoutLimit > 0.0 && (double)this.timeoutTimer > this.timeoutLimit * 3.0) {
            this.timeoutPath();
         }

         this.lastTimeoutCheck = Util.getMillis();
      }
   }

   private void timeoutPath() {
      this.resetStuckTimeout();
      this.stop();
   }

   private void resetStuckTimeout() {
      this.timeoutCachedNode = Vec3i.ZERO;
      this.timeoutTimer = 0L;
      this.timeoutLimit = 0.0;
      this.isStuck = false;
   }

   public boolean isDone() {
      return this.path == null || this.path.isDone();
   }

   public boolean isInProgress() {
      return !this.isDone();
   }

   public void stop() {
      this.path = null;
   }

   protected abstract Vec3 getTempMobPos();

   protected abstract boolean canUpdatePath();

   protected boolean isInLiquid() {
      return this.mob.isInWaterOrBubble() || this.mob.isInLava();
   }

   protected void trimPath() {
      if (this.path != null) {
         for(int â˜ƒ = 0; â˜ƒ < this.path.getNodeCount(); ++â˜ƒ) {
            Node â˜ƒx = this.path.getNode(â˜ƒ);
            Node â˜ƒxx = â˜ƒ + 1 < this.path.getNodeCount() ? this.path.getNode(â˜ƒ + 1) : null;
            BlockState â˜ƒxxx = this.level.getBlockState(new BlockPos(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z));
            if (â˜ƒxxx.is(BlockTags.CAULDRONS)) {
               this.path.replaceNode(â˜ƒ, â˜ƒx.cloneAndMove(â˜ƒx.x, â˜ƒx.y + 1, â˜ƒx.z));
               if (â˜ƒxx != null && â˜ƒx.y >= â˜ƒxx.y) {
                  this.path.replaceNode(â˜ƒ + 1, â˜ƒx.cloneAndMove(â˜ƒxx.x, â˜ƒx.y + 1, â˜ƒxx.z));
               }
            }
         }
      }
   }

   protected abstract boolean canMoveDirectly(Vec3 var1, Vec3 var2, int var3, int var4, int var5);

   public boolean isStableDestination(BlockPos var1) {
      BlockPos â˜ƒ = â˜ƒ.below();
      return this.level.getBlockState(â˜ƒ).isSolidRender(this.level, â˜ƒ);
   }

   public NodeEvaluator getNodeEvaluator() {
      return this.nodeEvaluator;
   }

   public void setCanFloat(boolean var1) {
      this.nodeEvaluator.setCanFloat(â˜ƒ);
   }

   public boolean canFloat() {
      return this.nodeEvaluator.canFloat();
   }

   public void recomputePath(BlockPos var1) {
      if (this.path != null && !this.path.isDone() && this.path.getNodeCount() != 0) {
         Node â˜ƒ = this.path.getEndNode();
         Vec3 â˜ƒx = new Vec3(((double)â˜ƒ.x + this.mob.getX()) / 2.0, ((double)â˜ƒ.y + this.mob.getY()) / 2.0, ((double)â˜ƒ.z + this.mob.getZ()) / 2.0);
         if (â˜ƒ.closerThan(â˜ƒx, (double)(this.path.getNodeCount() - this.path.getNextNodeIndex()))) {
            this.recomputePath();
         }
      }
   }

   public float getMaxDistanceToWaypoint() {
      return this.maxDistanceToWaypoint;
   }

   public boolean isStuck() {
      return this.isStuck;
   }
}
