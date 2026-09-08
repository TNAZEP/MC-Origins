package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BlockMushroom extends BlockBush implements IGrowable {
   protected static final VoxelShape field_196385_a = Block.func_208617_a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

   public BlockMushroom(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196385_a;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.nextInt(25) == 0) {
         int ☃ = 5;
         int ☃x = 4;

         for(BlockPos ☃xx : BlockPos.func_177975_b(☃.func_177982_a(-4, -1, -4), ☃.func_177982_a(4, 1, 4))) {
            if (☃.func_180495_p(☃xx).func_177230_c() == this) {
               if (--☃ <= 0) {
                  return;
               }
            }
         }

         BlockPos ☃xx = ☃.func_177982_a(☃.nextInt(3) - 1, ☃.nextInt(2) - ☃.nextInt(2), ☃.nextInt(3) - 1);

         for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
            if (☃.func_175623_d(☃xx) && ☃.func_196955_c(☃, ☃xx)) {
               ☃ = ☃xx;
            }

            ☃xx = ☃.func_177982_a(☃.nextInt(3) - 1, ☃.nextInt(2) - ☃.nextInt(2), ☃.nextInt(3) - 1);
         }

         if (☃.func_175623_d(☃xx) && ☃.func_196955_c(☃, ☃xx)) {
            ☃.func_180501_a(☃xx, ☃, 2);
         }
      }
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_200015_d(☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      BlockPos ☃ = ☃.func_177977_b();
      IBlockState ☃x = ☃.func_180495_p(☃);
      Block ☃xx = ☃x.func_177230_c();
      if (☃xx != Blocks.field_150391_bh && ☃xx != Blocks.field_196661_l) {
         return ☃.func_201669_a(☃, 0) < 13 && this.func_200014_a_(☃x, ☃, ☃);
      } else {
         return true;
      }
   }

   public boolean func_176485_d(IWorld var1, BlockPos var2, IBlockState var3, Random var4) {
      ☃.func_175698_g(☃);
      Feature<NoFeatureConfig> ☃ = null;
      if (this == Blocks.field_150338_P) {
         ☃ = Feature.field_202319_S;
      } else if (this == Blocks.field_150337_Q) {
         ☃ = Feature.field_202318_R;
      }

      if (☃ != null && ☃.func_212245_a(☃, ☃.func_72863_F().func_201711_g(), ☃, ☃, IFeatureConfig.field_202429_e)) {
         return true;
      } else {
         ☃.func_180501_a(☃, ☃, 3);
         return false;
      }
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return (double)☃.nextFloat() < 0.4;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.func_176485_d(☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_201783_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return true;
   }
}
