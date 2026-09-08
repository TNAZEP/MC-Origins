package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {
   @Override
   public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
      World ☃ = ☃.func_197524_h();
      IPosition ☃x = BlockDispenser.func_149939_a(☃);
      EnumFacing ☃xx = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
      IProjectile ☃xxx = this.func_82499_a(☃, ☃x, ☃);
      ☃xxx.func_70186_c(
         (double)☃xx.func_82601_c(), (double)((float)☃xx.func_96559_d() + 0.1F), (double)☃xx.func_82599_e(), this.func_82500_b(), this.func_82498_a()
      );
      ☃.func_72838_d((Entity)☃xxx);
      ☃.func_190918_g(1);
      return ☃;
   }

   @Override
   protected void func_82485_a(IBlockSource var1) {
      ☃.func_197524_h().func_175718_b(1002, ☃.func_180699_d(), 0);
   }

   protected abstract IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3);

   protected float func_82498_a() {
      return 6.0F;
   }

   protected float func_82500_b() {
      return 1.1F;
   }
}
