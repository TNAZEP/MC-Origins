package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SlimeBlock extends HalfTransparentBlock {
   public SlimeBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      if (â˜ƒ.isSuppressingBounce()) {
         super.fallOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         â˜ƒ.causeFallDamage(â˜ƒ, 0.0F, DamageSource.FALL);
      }
   }

   @Override
   public void updateEntityAfterFallOn(BlockGetter var1, Entity var2) {
      if (â˜ƒ.isSuppressingBounce()) {
         super.updateEntityAfterFallOn(â˜ƒ, â˜ƒ);
      } else {
         this.bounceUp(â˜ƒ);
      }
   }

   private void bounceUp(Entity var1) {
      Vec3 â˜ƒ = â˜ƒ.getDeltaMovement();
      if (â˜ƒ.y < 0.0) {
         double â˜ƒx = â˜ƒ instanceof LivingEntity ? 1.0 : 0.8;
         â˜ƒ.setDeltaMovement(â˜ƒ.x, -â˜ƒ.y * â˜ƒx, â˜ƒ.z);
      }
   }

   @Override
   public void stepOn(Level var1, BlockPos var2, BlockState var3, Entity var4) {
      double â˜ƒ = Math.abs(â˜ƒ.getDeltaMovement().y);
      if (â˜ƒ < 0.1 && !â˜ƒ.isSteppingCarefully()) {
         double â˜ƒx = 0.4 + â˜ƒ * 0.2;
         â˜ƒ.setDeltaMovement(â˜ƒ.getDeltaMovement().multiply(â˜ƒx, 1.0, â˜ƒx));
      }

      super.stepOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
