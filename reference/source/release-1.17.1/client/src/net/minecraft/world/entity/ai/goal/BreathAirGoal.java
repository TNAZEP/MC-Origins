package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class BreathAirGoal extends Goal {
   private final PathfinderMob mob;

   public BreathAirGoal(PathfinderMob var1) {
      this.mob = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   @Override
   public boolean canUse() {
      return this.mob.getAirSupply() < 140;
   }

   @Override
   public boolean canContinueToUse() {
      return this.canUse();
   }

   @Override
   public boolean isInterruptable() {
      return false;
   }

   @Override
   public void start() {
      this.findAirPosition();
   }

   private void findAirPosition() {
      Iterable<BlockPos> â˜ƒ = BlockPos.betweenClosed(
         Mth.floor(this.mob.getX() - 1.0),
         this.mob.getBlockY(),
         Mth.floor(this.mob.getZ() - 1.0),
         Mth.floor(this.mob.getX() + 1.0),
         Mth.floor(this.mob.getY() + 8.0),
         Mth.floor(this.mob.getZ() + 1.0)
      );
      BlockPos â˜ƒx = null;

      for(BlockPos â˜ƒxx : â˜ƒ) {
         if (this.givesAir(this.mob.level, â˜ƒxx)) {
            â˜ƒx = â˜ƒxx;
            break;
         }
      }

      if (â˜ƒx == null) {
         â˜ƒx = new BlockPos(this.mob.getX(), this.mob.getY() + 8.0, this.mob.getZ());
      }

      this.mob.getNavigation().moveTo((double)â˜ƒx.getX(), (double)(â˜ƒx.getY() + 1), (double)â˜ƒx.getZ(), 1.0);
   }

   @Override
   public void tick() {
      this.findAirPosition();
      this.mob.moveRelative(0.02F, new Vec3((double)this.mob.xxa, (double)this.mob.yya, (double)this.mob.zza));
      this.mob.move(MoverType.SELF, this.mob.getDeltaMovement());
   }

   private boolean givesAir(LevelReader var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return (â˜ƒ.getFluidState(â˜ƒ).isEmpty() || â˜ƒ.is(Blocks.BUBBLE_COLUMN)) && â˜ƒ.isPathfindable(â˜ƒ, â˜ƒ, PathComputationType.LAND);
   }
}
