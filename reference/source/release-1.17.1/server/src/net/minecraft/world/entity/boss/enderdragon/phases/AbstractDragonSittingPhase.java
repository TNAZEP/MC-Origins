package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.projectile.AbstractArrow;

public abstract class AbstractDragonSittingPhase extends AbstractDragonPhaseInstance {
   public AbstractDragonSittingPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isSitting() {
      return true;
   }

   @Override
   public float onHurt(DamageSource var1, float var2) {
      if (â˜ƒ.getDirectEntity() instanceof AbstractArrow) {
         â˜ƒ.getDirectEntity().setSecondsOnFire(1);
         return 0.0F;
      } else {
         return super.onHurt(â˜ƒ, â˜ƒ);
      }
   }
}
