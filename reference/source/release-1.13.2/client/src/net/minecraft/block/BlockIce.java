package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockIce extends BlockBreakable {
   public BlockIce(Block.Properties var1) {
      super(☃);
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return Blocks.field_150355_j.func_176223_P().func_200016_a(☃, ☃);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
      ☃.func_71020_j(0.005F);
      if (this.func_149700_E() && EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, ☃) > 0) {
         func_180635_a(☃, ☃, this.func_180643_i(☃));
      } else {
         if (☃.field_73011_w.func_177500_n()) {
            ☃.func_175698_g(☃);
            return;
         }

         int ☃ = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, ☃);
         ☃.func_196949_c(☃, ☃, ☃);
         Material ☃x = ☃.func_180495_p(☃.func_177977_b()).func_185904_a();
         if (☃x.func_76230_c() || ☃x.func_76224_d()) {
            ☃.func_175656_a(☃, Blocks.field_150355_j.func_176223_P());
         }
      }
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_175642_b(EnumLightType.BLOCK, ☃) > 11 - ☃.func_200016_a(☃, ☃)) {
         this.func_196454_d(☃, ☃, ☃);
      }
   }

   protected void func_196454_d(IBlockState var1, World var2, BlockPos var3) {
      if (☃.field_73011_w.func_177500_n()) {
         ☃.func_175698_g(☃);
      } else {
         ☃.func_196949_c(☃, ☃, 0);
         ☃.func_175656_a(☃, Blocks.field_150355_j.func_176223_P());
         ☃.func_190524_a(☃, Blocks.field_150355_j, ☃);
      }
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.NORMAL;
   }
}
