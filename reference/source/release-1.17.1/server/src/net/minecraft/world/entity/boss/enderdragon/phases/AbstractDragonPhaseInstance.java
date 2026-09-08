package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDragonPhaseInstance implements DragonPhaseInstance {
   protected final EnderDragon dragon;

   public AbstractDragonPhaseInstance(EnderDragon var1) {
      this.dragon = â˜ƒ;
   }

   @Override
   public boolean isSitting() {
      return false;
   }

   @Override
   public void doClientTick() {
   }

   @Override
   public void doServerTick() {
   }

   @Override
   public void onCrystalDestroyed(EndCrystal var1, BlockPos var2, DamageSource var3, @Nullable Player var4) {
   }

   @Override
   public void begin() {
   }

   @Override
   public void end() {
   }

   @Override
   public float getFlySpeed() {
      return 0.6F;
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return null;
   }

   @Override
   public float onHurt(DamageSource var1, float var2) {
      return â˜ƒ;
   }

   @Override
   public float getTurnSpeed() {
      float â˜ƒ = (float)this.dragon.getDeltaMovement().horizontalDistance() + 1.0F;
      float â˜ƒx = Math.min(â˜ƒ, 40.0F);
      return 0.7F / â˜ƒx / â˜ƒ;
   }
}
