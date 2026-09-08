package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockEnchantmentTable extends BlockContainer {
   protected static final VoxelShape field_196322_a = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

   protected BlockEnchantmentTable(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196322_a;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      super.func_180655_c(☃, ☃, ☃, ☃);

      for(int ☃ = -2; ☃ <= 2; ++☃) {
         for(int ☃x = -2; ☃x <= 2; ++☃x) {
            if (☃ > -2 && ☃ < 2 && ☃x == -1) {
               ☃x = 2;
            }

            if (☃.nextInt(16) == 0) {
               for(int ☃xx = 0; ☃xx <= 1; ++☃xx) {
                  BlockPos ☃xxx = ☃.func_177982_a(☃, ☃xx, ☃x);
                  if (☃.func_180495_p(☃xxx).func_177230_c() == Blocks.field_150342_X) {
                     if (!☃.func_175623_d(☃.func_177982_a(☃ / 2, 0, ☃x / 2))) {
                        break;
                     }

                     ☃.func_195594_a(
                        Particles.field_197623_p,
                        (double)☃.func_177958_n() + 0.5,
                        (double)☃.func_177956_o() + 2.0,
                        (double)☃.func_177952_p() + 0.5,
                        (double)((float)☃ + ☃.nextFloat()) - 0.5,
                        (double)((float)☃xx - ☃.nextFloat() - 1.0F),
                        (double)((float)☃x + ☃.nextFloat()) - 0.5
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityEnchantmentTable();
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityEnchantmentTable) {
            ☃.func_180468_a((TileEntityEnchantmentTable)☃);
         }

         return true;
      }
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityEnchantmentTable) {
            ((TileEntityEnchantmentTable)☃).func_200229_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
