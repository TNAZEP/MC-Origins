package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class FlyingMob extends Mob {
   protected FlyingMob(EntityType<? extends FlyingMob> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      return false;
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.isInWater()) {
         this.moveRelative(0.02F, â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
      } else if (this.isInLava()) {
         this.moveRelative(0.02F, â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
      } else {
         float â˜ƒ = 0.91F;
         if (this.onGround) {
            â˜ƒ = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getFriction() * 0.91F;
         }

         float â˜ƒ = 0.16277137F / (â˜ƒ * â˜ƒ * â˜ƒ);
         â˜ƒ = 0.91F;
         if (this.onGround) {
            â˜ƒ = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getFriction() * 0.91F;
         }

         this.moveRelative(this.onGround ? 0.1F * â˜ƒ : 0.02F, â˜ƒ);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale((double)â˜ƒ));
      }

      this.calculateEntityAnimation(this, false);
   }

   @Override
   public boolean onClimbable() {
      return false;
   }
}
