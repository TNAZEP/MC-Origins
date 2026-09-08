package net.minecraft.item;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.end.DragonFightManager;

public class ItemEndCrystal extends Item {
   public ItemEndCrystal(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      if (☃xx.func_177230_c() != Blocks.field_150343_Z && ☃xx.func_177230_c() != Blocks.field_150357_h) {
         return EnumActionResult.FAIL;
      } else {
         BlockPos ☃ = ☃x.func_177984_a();
         if (!☃.func_175623_d(☃)) {
            return EnumActionResult.FAIL;
         } else {
            double ☃ = (double)☃.func_177958_n();
            double ☃x = (double)☃.func_177956_o();
            double ☃xx = (double)☃.func_177952_p();
            List<Entity> ☃xxx = ☃.func_72839_b(null, new AxisAlignedBB(☃, ☃x, ☃xx, ☃ + 1.0, ☃x + 2.0, ☃xx + 1.0));
            if (!☃xxx.isEmpty()) {
               return EnumActionResult.FAIL;
            } else {
               if (!☃.field_72995_K) {
                  EntityEnderCrystal ☃ = new EntityEnderCrystal(☃, ☃ + 0.5, ☃x, ☃xx + 0.5);
                  ☃.func_184517_a(false);
                  ☃.func_72838_d(☃);
                  if (☃.field_73011_w instanceof EndDimension) {
                     DragonFightManager ☃x = ((EndDimension)☃.field_73011_w).func_186063_s();
                     ☃x.func_186106_e();
                  }
               }

               ☃.func_195996_i().func_190918_g(1);
               return EnumActionResult.SUCCESS;
            }
         }
      }
   }

   @Override
   public boolean func_77636_d(ItemStack var1) {
      return true;
   }
}
