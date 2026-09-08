package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemBlock {
   public ItemLilyPad(Block var1, Item.Properties var2) {
      super(☃, ☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      return EnumActionResult.PASS;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      RayTraceResult ☃x = this.func_77621_a(☃, ☃, true);
      if (☃x == null) {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      } else {
         if (☃x.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃ = ☃x.func_178782_a();
            if (!☃.func_175660_a(☃, ☃) || !☃.func_175151_a(☃.func_177972_a(☃x.field_178784_b), ☃x.field_178784_b, ☃)) {
               return new ActionResult<>(EnumActionResult.FAIL, ☃);
            }

            BlockPos ☃ = ☃.func_177984_a();
            IBlockState ☃x = ☃.func_180495_p(☃);
            Material ☃xx = ☃x.func_185904_a();
            IFluidState ☃xxx = ☃.func_204610_c(☃);
            if ((☃xxx.func_206886_c() == Fluids.field_204546_a || ☃xx == Material.field_151588_w) && ☃.func_175623_d(☃)) {
               ☃.func_180501_a(☃, Blocks.field_196651_dG.func_176223_P(), 11);
               if (☃ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃, ☃, ☃);
               }

               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
               }

               ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
               ☃.func_184133_a(☃, ☃, SoundEvents.field_187916_gp, SoundCategory.BLOCKS, 1.0F, 1.0F);
               return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
            }
         }

         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      }
   }
}
