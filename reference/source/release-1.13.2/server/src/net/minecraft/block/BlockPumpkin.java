package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPumpkin extends BlockStemGrown {
   protected BlockPumpkin(Block.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151097_aZ) {
         if (!☃.field_72995_K) {
            EnumFacing ☃x = ☃.func_176740_k() == EnumFacing.Axis.Y ? ☃.func_174811_aO().func_176734_d() : ☃;
            ☃.func_184133_a(null, ☃, SoundEvents.field_199059_fV, SoundCategory.BLOCKS, 1.0F, 1.0F);
            ☃.func_180501_a(☃, Blocks.field_196625_cS.func_176223_P().func_206870_a(BlockCarvedPumpkin.field_196359_a, ☃x), 11);
            EntityItem ☃xx = new EntityItem(
               ☃,
               (double)☃.func_177958_n() + 0.5 + (double)☃x.func_82601_c() * 0.65,
               (double)☃.func_177956_o() + 0.1,
               (double)☃.func_177952_p() + 0.5 + (double)☃x.func_82599_e() * 0.65,
               new ItemStack(Items.field_151080_bb, 4)
            );
            ☃xx.field_70159_w = 0.05 * (double)☃x.func_82601_c() + ☃.field_73012_v.nextDouble() * 0.02;
            ☃xx.field_70181_x = 0.05;
            ☃xx.field_70179_y = 0.05 * (double)☃x.func_82599_e() + ☃.field_73012_v.nextDouble() * 0.02;
            ☃.func_72838_d(☃xx);
            ☃.func_77972_a(1, ☃);
         }

         return true;
      } else {
         return super.func_196250_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public BlockStem func_196524_d() {
      return (BlockStem)Blocks.field_150393_bb;
   }

   @Override
   public BlockAttachedStem func_196523_e() {
      return (BlockAttachedStem)Blocks.field_196711_ds;
   }
}
