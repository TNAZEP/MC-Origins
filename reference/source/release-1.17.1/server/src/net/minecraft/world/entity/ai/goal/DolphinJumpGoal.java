package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class DolphinJumpGoal extends JumpGoal {
   private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
   private final Dolphin dolphin;
   private final int interval;
   private boolean breached;

   public DolphinJumpGoal(Dolphin var1, int var2) {
      this.dolphin = â˜ƒ;
      this.interval = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      if (this.dolphin.getRandom().nextInt(this.interval) != 0) {
         return false;
      } else {
         Direction â˜ƒ = this.dolphin.getMotionDirection();
         int â˜ƒx = â˜ƒ.getStepX();
         int â˜ƒxx = â˜ƒ.getStepZ();
         BlockPos â˜ƒxxx = this.dolphin.blockPosition();

         for(int â˜ƒxxxx : STEPS_TO_CHECK) {
            if (!this.waterIsClear(â˜ƒxxx, â˜ƒx, â˜ƒxx, â˜ƒxxxx) || !this.surfaceIsClear(â˜ƒxxx, â˜ƒx, â˜ƒxx, â˜ƒxxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean waterIsClear(BlockPos var1, int var2, int var3, int var4) {
      BlockPos â˜ƒ = â˜ƒ.offset(â˜ƒ * â˜ƒ, 0, â˜ƒ * â˜ƒ);
      return this.dolphin.level.getFluidState(â˜ƒ).is(FluidTags.WATER) && !this.dolphin.level.getBlockState(â˜ƒ).getMaterial().blocksMotion();
   }

   private boolean surfaceIsClear(BlockPos var1, int var2, int var3, int var4) {
      return this.dolphin.level.getBlockState(â˜ƒ.offset(â˜ƒ * â˜ƒ, 1, â˜ƒ * â˜ƒ)).isAir()
         && this.dolphin.level.getBlockState(â˜ƒ.offset(â˜ƒ * â˜ƒ, 2, â˜ƒ * â˜ƒ)).isAir();
   }

   @Override
   public boolean canContinueToUse() {
      double â˜ƒ = this.dolphin.getDeltaMovement().y;
      return (!(â˜ƒ * â˜ƒ < 0.03F) || this.dolphin.getXRot() == 0.0F || !(Math.abs(this.dolphin.getXRot()) < 10.0F) || !this.dolphin.isInWater())
         && !this.dolphin.isOnGround();
   }

   @Override
   public boolean isInterruptable() {
      return false;
   }

   @Override
   public void start() {
      Direction â˜ƒ = this.dolphin.getMotionDirection();
      this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add((double)â˜ƒ.getStepX() * 0.6, 0.7, (double)â˜ƒ.getStepZ() * 0.6));
      this.dolphin.getNavigation().stop();
   }

   @Override
   public void stop() {
      this.dolphin.setXRot(0.0F);
   }

   @Override
   public void tick() {
      boolean â˜ƒ = this.breached;
      if (!â˜ƒ) {
         FluidState â˜ƒx = this.dolphin.level.getFluidState(this.dolphin.blockPosition());
         this.breached = â˜ƒx.is(FluidTags.WATER);
      }

      if (this.breached && !â˜ƒ) {
         this.dolphin.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
      }

      Vec3 â˜ƒ = this.dolphin.getDeltaMovement();
      if (â˜ƒ.y * â˜ƒ.y < 0.03F && this.dolphin.getXRot() != 0.0F) {
         this.dolphin.setXRot(Mth.rotlerp(this.dolphin.getXRot(), 0.0F, 0.2F));
      } else if (â˜ƒ.length() > 1.0E-5F) {
         double â˜ƒ = â˜ƒ.horizontalDistance();
         double â˜ƒx = Math.atan2(-â˜ƒ.y, â˜ƒ) * 180.0F / (float)Math.PI;
         this.dolphin.setXRot((float)â˜ƒx);
      }
   }
}
