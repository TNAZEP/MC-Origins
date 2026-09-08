package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemSeedFood extends ItemFood {
   private final IBlockState field_195972_b;

   public ItemSeedFood(int var1, float var2, Block var3, Item.Properties var4) {
      super(☃, ☃, false, ☃);
      this.field_195972_b = ☃.func_176223_P();
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      IWorld ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a().func_177984_a();
      if (☃.func_196000_l() == EnumFacing.UP && ☃.func_175623_d(☃x) && this.field_195972_b.func_196955_c(☃, ☃x)) {
         ☃.func_180501_a(☃x, this.field_195972_b, 11);
         EntityPlayer ☃xx = ☃.func_195999_j();
         ItemStack ☃xxx = ☃.func_195996_i();
         if (☃xx instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃xx, ☃x, ☃xxx);
         }

         ☃xxx.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.PASS;
      }
   }
}
