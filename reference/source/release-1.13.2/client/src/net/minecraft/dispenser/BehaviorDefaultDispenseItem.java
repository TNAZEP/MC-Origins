package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {
   @Override
   public final ItemStack dispense(IBlockSource var1, ItemStack var2) {
      ItemStack ☃ = this.func_82487_b(☃, ☃);
      this.func_82485_a(☃);
      this.func_82489_a(☃, ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
      return ☃;
   }

   protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
      EnumFacing ☃ = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
      IPosition ☃x = BlockDispenser.func_149939_a(☃);
      ItemStack ☃xx = ☃.func_77979_a(1);
      func_82486_a(☃.func_197524_h(), ☃xx, 6, ☃, ☃x);
      return ☃;
   }

   public static void func_82486_a(World var0, ItemStack var1, int var2, EnumFacing var3, IPosition var4) {
      double ☃ = ☃.func_82615_a();
      double ☃x = ☃.func_82617_b();
      double ☃xx = ☃.func_82616_c();
      if (☃.func_176740_k() == EnumFacing.Axis.Y) {
         ☃x -= 0.125;
      } else {
         ☃x -= 0.15625;
      }

      EntityItem ☃ = new EntityItem(☃, ☃, ☃x, ☃xx, ☃);
      double ☃x = ☃.field_73012_v.nextDouble() * 0.1 + 0.2;
      ☃.field_70159_w = (double)☃.func_82601_c() * ☃x;
      ☃.field_70181_x = 0.2F;
      ☃.field_70179_y = (double)☃.func_82599_e() * ☃x;
      ☃.field_70159_w += ☃.field_73012_v.nextGaussian() * 0.0075F * (double)☃;
      ☃.field_70181_x += ☃.field_73012_v.nextGaussian() * 0.0075F * (double)☃;
      ☃.field_70179_y += ☃.field_73012_v.nextGaussian() * 0.0075F * (double)☃;
      ☃.func_72838_d(☃);
   }

   protected void func_82485_a(IBlockSource var1) {
      ☃.func_197524_h().func_175718_b(1000, ☃.func_180699_d(), 0);
   }

   protected void func_82489_a(IBlockSource var1, EnumFacing var2) {
      ☃.func_197524_h().func_175718_b(2000, ☃.func_180699_d(), ☃.func_176745_a());
   }
}
