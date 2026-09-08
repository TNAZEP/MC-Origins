package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DefaultDispenseItemBehavior implements DispenseItemBehavior {
   @Override
   public final ItemStack dispense(BlockSource var1, ItemStack var2) {
      ItemStack â˜ƒ = this.execute(â˜ƒ, â˜ƒ);
      this.playSound(â˜ƒ);
      this.playAnimation(â˜ƒ, â˜ƒ.getBlockState().getValue(DispenserBlock.FACING));
      return â˜ƒ;
   }

   protected ItemStack execute(BlockSource var1, ItemStack var2) {
      Direction â˜ƒ = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
      Position â˜ƒx = DispenserBlock.getDispensePosition(â˜ƒ);
      ItemStack â˜ƒxx = â˜ƒ.split(1);
      spawnItem(â˜ƒ.getLevel(), â˜ƒxx, 6, â˜ƒ, â˜ƒx);
      return â˜ƒ;
   }

   public static void spawnItem(Level var0, ItemStack var1, int var2, Direction var3, Position var4) {
      double â˜ƒ = â˜ƒ.x();
      double â˜ƒx = â˜ƒ.y();
      double â˜ƒxx = â˜ƒ.z();
      if (â˜ƒ.getAxis() == Direction.Axis.Y) {
         â˜ƒx -= 0.125;
      } else {
         â˜ƒx -= 0.15625;
      }

      ItemEntity â˜ƒ = new ItemEntity(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ);
      double â˜ƒx = â˜ƒ.random.nextDouble() * 0.1 + 0.2;
      â˜ƒ.setDeltaMovement(
         â˜ƒ.random.nextGaussian() * 0.0075F * (double)â˜ƒ + (double)â˜ƒ.getStepX() * â˜ƒx,
         â˜ƒ.random.nextGaussian() * 0.0075F * (double)â˜ƒ + 0.2F,
         â˜ƒ.random.nextGaussian() * 0.0075F * (double)â˜ƒ + (double)â˜ƒ.getStepZ() * â˜ƒx
      );
      â˜ƒ.addFreshEntity(â˜ƒ);
   }

   protected void playSound(BlockSource var1) {
      â˜ƒ.getLevel().levelEvent(1000, â˜ƒ.getPos(), 0);
   }

   protected void playAnimation(BlockSource var1, Direction var2) {
      â˜ƒ.getLevel().levelEvent(2000, â˜ƒ.getPos(), â˜ƒ.get3DDataValue());
   }
}
