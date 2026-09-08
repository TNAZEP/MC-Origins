package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonTakeoffPhase extends AbstractDragonPhaseInstance {
   private boolean firstTick;
   private Path currentPath;
   private Vec3 targetLocation;

   public DragonTakeoffPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public void doServerTick() {
      if (!this.firstTick && this.currentPath != null) {
         BlockPos â˜ƒ = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
         if (!â˜ƒ.closerThan(this.dragon.position(), 10.0)) {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
         }
      } else {
         this.firstTick = false;
         this.findNewTarget();
      }
   }

   @Override
   public void begin() {
      this.firstTick = true;
      this.currentPath = null;
      this.targetLocation = null;
   }

   private void findNewTarget() {
      int â˜ƒ = this.dragon.findClosestNode();
      Vec3 â˜ƒx = this.dragon.getHeadLookVector(1.0F);
      int â˜ƒxx = this.dragon.findClosestNode(-â˜ƒx.x * 40.0, 105.0, -â˜ƒx.z * 40.0);
      if (this.dragon.getDragonFight() != null && this.dragon.getDragonFight().getCrystalsAlive() > 0) {
         â˜ƒxx %= 12;
         if (â˜ƒxx < 0) {
            â˜ƒxx += 12;
         }
      } else {
         â˜ƒxx -= 12;
         â˜ƒxx &= 7;
         â˜ƒxx += 12;
      }

      this.currentPath = this.dragon.findPath(â˜ƒ, â˜ƒxx, null);
      this.navigateToNextPathNode();
   }

   private void navigateToNextPathNode() {
      if (this.currentPath != null) {
         this.currentPath.advance();
         if (!this.currentPath.isDone()) {
            Vec3i â˜ƒ = this.currentPath.getNextNodePos();
            this.currentPath.advance();

            double â˜ƒ;
            do {
               â˜ƒ = (double)((float)â˜ƒ.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
            } while(â˜ƒ < (double)â˜ƒ.getY());

            this.targetLocation = new Vec3((double)â˜ƒ.getX(), â˜ƒ, (double)â˜ƒ.getZ());
         }
      }
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return this.targetLocation;
   }

   @Override
   public EnderDragonPhase<DragonTakeoffPhase> getPhase() {
      return EnderDragonPhase.TAKEOFF;
   }
}
