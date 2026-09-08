package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class WallClimberNavigation extends GroundPathNavigation {
   private BlockPos pathToPosition;

   public WallClimberNavigation(Mob var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Path createPath(BlockPos var1, int var2) {
      this.pathToPosition = â˜ƒ;
      return super.createPath(â˜ƒ, â˜ƒ);
   }

   @Override
   public Path createPath(Entity var1, int var2) {
      this.pathToPosition = â˜ƒ.blockPosition();
      return super.createPath(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean moveTo(Entity var1, double var2) {
      Path â˜ƒ = this.createPath(â˜ƒ, 0);
      if (â˜ƒ != null) {
         return this.moveTo(â˜ƒ, â˜ƒ);
      } else {
         this.pathToPosition = â˜ƒ.blockPosition();
         this.speedModifier = â˜ƒ;
         return true;
      }
   }

   @Override
   public void tick() {
      if (!this.isDone()) {
         super.tick();
      } else {
         if (this.pathToPosition != null) {
            if (!this.pathToPosition.closerThan(this.mob.position(), (double)this.mob.getBbWidth())
               && (
                  !(this.mob.getY() > (double)this.pathToPosition.getY())
                     || !new BlockPos((double)this.pathToPosition.getX(), this.mob.getY(), (double)this.pathToPosition.getZ())
                        .closerThan(this.mob.position(), (double)this.mob.getBbWidth())
               )) {
               this.mob
                  .getMoveControl()
                  .setWantedPosition(
                     (double)this.pathToPosition.getX(), (double)this.pathToPosition.getY(), (double)this.pathToPosition.getZ(), this.speedModifier
                  );
            } else {
               this.pathToPosition = null;
            }
         }
      }
   }
}
