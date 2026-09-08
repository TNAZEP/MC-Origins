package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCactus extends Block {
   public static final IntegerProperty field_176587_a = BlockStateProperties.field_208171_X;
   protected static final VoxelShape field_196400_b = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
   protected static final VoxelShape field_196401_c = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   protected BlockCactus(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176587_a, Integer.valueOf(0)));
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.func_196955_c(☃, ☃)) {
         ☃.func_175655_b(☃, true);
      } else {
         BlockPos ☃ = ☃.func_177984_a();
         if (☃.func_175623_d(☃)) {
            int ☃x = 1;

            while(☃.func_180495_p(☃.func_177979_c(☃x)).func_177230_c() == this) {
               ++☃x;
            }

            if (☃x < 3) {
               int ☃xx = ☃.func_177229_b(field_176587_a);
               if (☃xx == 15) {
                  ☃.func_175656_a(☃, this.func_176223_P());
                  IBlockState ☃xxx = ☃.func_206870_a(field_176587_a, Integer.valueOf(0));
                  ☃.func_180501_a(☃, ☃xxx, 4);
                  ☃xxx.func_189546_a(☃, ☃, this, ☃);
               } else {
                  ☃.func_180501_a(☃, ☃.func_206870_a(field_176587_a, Integer.valueOf(☃xx + 1)), 4);
               }
            }
         }
      }
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196400_b;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196401_c;
   }

   @Override
   public boolean func_200124_e(IBlockState var1) {
      return true;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (!☃.func_196955_c(☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
         IBlockState ☃x = ☃.func_180495_p(☃.func_177972_a(☃));
         Material ☃xx = ☃x.func_185904_a();
         if (☃xx.func_76220_a() || ☃.func_204610_c(☃.func_177972_a(☃)).func_206884_a(FluidTags.field_206960_b)) {
            return false;
         }
      }

      Block ☃ = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
      return (☃ == Blocks.field_150434_aF || ☃ == Blocks.field_150354_m || ☃ == Blocks.field_196611_F)
         && !☃.func_180495_p(☃.func_177984_a()).func_185904_a().func_76224_d();
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      ☃.func_70097_a(DamageSource.field_76367_g, 1.0F);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176587_a);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
