package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonLandingApproachPhase extends AbstractDragonPhaseInstance {
   private static final TargetingConditions NEAR_EGG_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight();
   private Path currentPath;
   private Vec3 targetLocation;

   public DragonLandingApproachPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public EnderDragonPhase<DragonLandingApproachPhase> getPhase() {
      return EnderDragonPhase.LANDING_APPROACH;
   }

   @Override
   public void begin() {
      this.currentPath = null;
      this.targetLocation = null;
   }

   @Override
   public void doServerTick() {
      double â˜ƒ = this.targetLocation == null ? 0.0 : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (â˜ƒ < 100.0 || â˜ƒ > 22500.0 || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
         this.findNewTarget();
      }
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return this.targetLocation;
   }

   private void findNewTarget() {
      if (this.currentPath == null || this.currentPath.isDone()) {
         int â˜ƒx = this.dragon.findClosestNode();
         BlockPos â˜ƒxx = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
         Player â˜ƒxxx = this.dragon.level.getNearestPlayer(NEAR_EGG_TARGETING, this.dragon, (double)â˜ƒxx.getX(), (double)â˜ƒxx.getY(), (double)â˜ƒxx.getZ());
         int â˜ƒ;
         if (â˜ƒxxx != null) {
            Vec3 â˜ƒxxxx = new Vec3(â˜ƒxxx.getX(), 0.0, â˜ƒxxx.getZ()).normalize();
            â˜ƒ = this.dragon.findClosestNode(-â˜ƒxxxx.x * 40.0, 105.0, -â˜ƒxxxx.z * 40.0);
         } else {
            â˜ƒ = this.dragon.findClosestNode(40.0, (double)â˜ƒxx.getY(), 0.0);
         }

         Node â˜ƒ = new Node(â˜ƒxx.getX(), â˜ƒxx.getY(), â˜ƒxx.getZ());
         this.currentPath = this.dragon.findPath(â˜ƒx, â˜ƒ, â˜ƒ);
         if (this.currentPath != null) {
            this.currentPath.advance();
         }
      }

      this.navigateToNextPathNode();
      if (this.currentPath != null && this.currentPath.isDone()) {
         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.LANDING);
      }
   }

   private void navigateToNextPathNode() {
      if (this.currentPath != null && !this.currentPath.isDone()) {
         Vec3i â˜ƒ = this.currentPath.getNextNodePos();
         this.currentPath.advance();
         double â˜ƒx = (double)â˜ƒ.getX();
         double â˜ƒxx = (double)â˜ƒ.getZ();

         double â˜ƒ;
         do {
            â˜ƒ = (double)((float)â˜ƒ.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
         } while(â˜ƒ < (double)â˜ƒ.getY());

         this.targetLocation = new Vec3(â˜ƒx, â˜ƒ, â˜ƒxx);
      }
   }
}
