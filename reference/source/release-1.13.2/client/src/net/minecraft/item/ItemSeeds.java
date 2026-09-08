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

public class ItemSeeds extends Item {
   private final IBlockState field_195978_a;

   public ItemSeeds(Block var1, Item.Properties var2) {
      super(☃);
      this.field_195978_a = ☃.func_176223_P();
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      IWorld ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a().func_177984_a();
      if (☃.func_196000_l() == EnumFacing.UP && ☃.func_175623_d(☃x) && this.field_195978_a.func_196955_c(☃, ☃x)) {
         ☃.func_180501_a(☃x, this.field_195978_a, 11);
         ItemStack ☃xx = ☃.func_195996_i();
         EntityPlayer ☃xxx = ☃.func_195999_j();
         if (☃xxx instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃xxx, ☃x, ☃xx);
         }

         ☃xx.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }
}
