package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class PanicGoal extends Goal {
   protected final PathfinderMob mob;
   protected final double speedModifier;
   protected double posX;
   protected double posY;
   protected double posZ;
   protected boolean isRunning;

   public PanicGoal(PathfinderMob var1, double var2) {
      this.mob = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.mob.getLastHurtByMob() == null && !this.mob.isOnFire()) {
         return false;
      } else {
         if (this.mob.isOnFire()) {
            BlockPos â˜ƒ = this.lookForWater(this.mob.level, this.mob, 5, 4);
            if (â˜ƒ != null) {
               this.posX = (double)â˜ƒ.getX();
               this.posY = (double)â˜ƒ.getY();
               this.posZ = (double)â˜ƒ.getZ();
               return true;
            }
         }

         return this.findRandomPosition();
      }
   }

   protected boolean findRandomPosition() {
      Vec3 â˜ƒ = DefaultRandomPos.getPos(this.mob, 5, 4);
      if (â˜ƒ == null) {
         return false;
      } else {
         this.posX = â˜ƒ.x;
         this.posY = â˜ƒ.y;
         this.posZ = â˜ƒ.z;
         return true;
      }
   }

   public boolean isRunning() {
      return this.isRunning;
   }

   @Override
   public void start() {
      this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
      this.isRunning = true;
   }

   @Override
   public void stop() {
      this.isRunning = false;
   }

   @Override
   public boolean canContinueToUse() {
      return !this.mob.getNavigation().isDone();
   }

   @Nullable
   protected BlockPos lookForWater(BlockGetter var1, Entity var2, int var3, int var4) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      int â˜ƒx = â˜ƒ.getX();
      int â˜ƒxx = â˜ƒ.getY();
      int â˜ƒxxx = â˜ƒ.getZ();
      float â˜ƒxxxx = (float)(â˜ƒ * â˜ƒ * â˜ƒ * 2);
      BlockPos â˜ƒxxxxx = null;
      BlockPos.MutableBlockPos â˜ƒxxxxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxxxxx = â˜ƒx - â˜ƒ; â˜ƒxxxxxxx <= â˜ƒx + â˜ƒ; ++â˜ƒxxxxxxx) {
         for(int â˜ƒxxxxxxxx = â˜ƒxx - â˜ƒ; â˜ƒxxxxxxxx <= â˜ƒxx + â˜ƒ; ++â˜ƒxxxxxxxx) {
            for(int â˜ƒxxxxxxxxx = â˜ƒxxx - â˜ƒ; â˜ƒxxxxxxxxx <= â˜ƒxxx + â˜ƒ; ++â˜ƒxxxxxxxxx) {
               â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
               if (â˜ƒ.getFluidState(â˜ƒxxxxxx).is(FluidTags.WATER)) {
                  float â˜ƒxxxxxxxxxx = (float)(
                     (â˜ƒxxxxxxx - â˜ƒx) * (â˜ƒxxxxxxx - â˜ƒx)
                        + (â˜ƒxxxxxxxx - â˜ƒxx) * (â˜ƒxxxxxxxx - â˜ƒxx)
                        + (â˜ƒxxxxxxxxx - â˜ƒxxx) * (â˜ƒxxxxxxxxx - â˜ƒxxx)
                  );
                  if (â˜ƒxxxxxxxxxx < â˜ƒxxxx) {
                     â˜ƒxxxx = â˜ƒxxxxxxxxxx;
                     â˜ƒxxxxx = new BlockPos(â˜ƒxxxxxx);
                  }
               }
            }
         }
      }

      return â˜ƒxxxxx;
   }
}
