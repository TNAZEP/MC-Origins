package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public abstract class AbstractProjectileDispenseBehavior extends DefaultDispenseItemBehavior {
   @Override
   public ItemStack execute(BlockSource var1, ItemStack var2) {
      Level â˜ƒ = â˜ƒ.getLevel();
      Position â˜ƒx = DispenserBlock.getDispensePosition(â˜ƒ);
      Direction â˜ƒxx = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
      Projectile â˜ƒxxx = this.getProjectile(â˜ƒ, â˜ƒx, â˜ƒ);
      â˜ƒxxx.shoot((double)â˜ƒxx.getStepX(), (double)((float)â˜ƒxx.getStepY() + 0.1F), (double)â˜ƒxx.getStepZ(), this.getPower(), this.getUncertainty());
      â˜ƒ.addFreshEntity(â˜ƒxxx);
      â˜ƒ.shrink(1);
      return â˜ƒ;
   }

   @Override
   protected void playSound(BlockSource var1) {
      â˜ƒ.getLevel().levelEvent(1002, â˜ƒ.getPos(), 0);
   }

   protected abstract Projectile getProjectile(Level var1, Position var2, ItemStack var3);

   protected float getUncertainty() {
      return 6.0F;
   }

   protected float getPower() {
      return 1.1F;
   }
}
