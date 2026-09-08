package net.minecraft.block;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BlockSourceImpl;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockDropper extends BlockDispenser {
   private static final IBehaviorDispenseItem field_149947_P = new BehaviorDefaultDispenseItem();

   public BlockDropper(Block.Properties var1) {
      super(☃);
   }

   @Override
   protected IBehaviorDispenseItem func_149940_a(ItemStack var1) {
      return field_149947_P;
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityDropper();
   }

   @Override
   protected void func_176439_d(World var1, BlockPos var2) {
      BlockSourceImpl ☃ = new BlockSourceImpl(☃, ☃);
      TileEntityDispenser ☃x = ☃.func_150835_j();
      int ☃xx = ☃x.func_146017_i();
      if (☃xx < 0) {
         ☃.func_175718_b(1001, ☃, 0);
      } else {
         ItemStack ☃ = ☃x.func_70301_a(☃xx);
         if (!☃.func_190926_b()) {
            EnumFacing ☃xx = ☃.func_180495_p(☃).func_177229_b(field_176441_a);
            IInventory ☃xxx = TileEntityHopper.func_195484_a(☃, ☃.func_177972_a(☃xx));
            ItemStack ☃x;
            if (☃xxx == null) {
               ☃x = field_149947_P.dispense(☃, ☃);
            } else {
               ☃x = TileEntityHopper.func_174918_a(☃x, ☃xxx, ☃.func_77946_l().func_77979_a(1), ☃xx.func_176734_d());
               if (☃x.func_190926_b()) {
                  ☃x = ☃.func_77946_l();
                  ☃x.func_190918_g(1);
               } else {
                  ☃x = ☃.func_77946_l();
               }
            }

            ☃x.func_70299_a(☃xx, ☃x);
         }
      }
   }
}
