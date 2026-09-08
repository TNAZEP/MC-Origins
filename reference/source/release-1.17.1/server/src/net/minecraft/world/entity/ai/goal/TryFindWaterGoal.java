package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;

public class TryFindWaterGoal extends Goal {
   private final PathfinderMob mob;

   public TryFindWaterGoal(PathfinderMob var1) {
      this.mob = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      return this.mob.isOnGround() && !this.mob.level.getFluidState(this.mob.blockPosition()).is(FluidTags.WATER);
   }

   @Override
   public void start() {
      BlockPos â˜ƒ = null;

      for(BlockPos â˜ƒx : BlockPos.betweenClosed(
         Mth.floor(this.mob.getX() - 2.0),
         Mth.floor(this.mob.getY() - 2.0),
         Mth.floor(this.mob.getZ() - 2.0),
         Mth.floor(this.mob.getX() + 2.0),
         this.mob.getBlockY(),
         Mth.floor(this.mob.getZ() + 2.0)
      )) {
         if (this.mob.level.getFluidState(â˜ƒx).is(FluidTags.WATER)) {
            â˜ƒ = â˜ƒx;
            break;
         }
      }

      if (â˜ƒ != null) {
         this.mob.getMoveControl().setWantedPosition((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), 1.0);
      }
   }
}
