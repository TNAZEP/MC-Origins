package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockBubbleColumn extends Block implements IBucketPickupHandler {
   public static final BooleanProperty field_203160_a = BlockStateProperties.field_208179_f;

   public BlockBubbleColumn(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_203160_a, Boolean.valueOf(true)));
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177984_a());
      if (☃.func_196958_f()) {
         ☃.func_203002_i(☃.func_177229_b(field_203160_a));
         if (!☃.field_72995_K) {
            WorldServer ☃x = (WorldServer)☃;

            for(int ☃xx = 0; ☃xx < 2; ++☃xx) {
               ☃x.func_195598_a(
                  Particles.field_197606_Q,
                  (double)((float)☃.func_177958_n() + ☃.field_73012_v.nextFloat()),
                  (double)(☃.func_177956_o() + 1),
                  (double)((float)☃.func_177952_p() + ☃.field_73012_v.nextFloat()),
                  1,
                  0.0,
                  0.0,
                  0.0,
                  1.0
               );
               ☃x.func_195598_a(
                  Particles.field_197612_e,
                  (double)((float)☃.func_177958_n() + ☃.field_73012_v.nextFloat()),
                  (double)(☃.func_177956_o() + 1),
                  (double)((float)☃.func_177952_p() + ☃.field_73012_v.nextFloat()),
                  1,
                  0.0,
                  0.01,
                  0.0,
                  0.2
               );
            }
         }
      } else {
         ☃.func_203004_j(☃.func_177229_b(field_203160_a));
      }
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      func_203159_a(☃, ☃.func_177984_a(), func_203157_b(☃, ☃.func_177977_b()));
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      func_203159_a(☃, ☃.func_177984_a(), func_203157_b(☃, ☃));
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return Fluids.field_204546_a.func_207204_a(false);
   }

   public static void func_203159_a(IWorld var0, BlockPos var1, boolean var2) {
      if (func_208072_b(☃, ☃)) {
         ☃.func_180501_a(☃, Blocks.field_203203_C.func_176223_P().func_206870_a(field_203160_a, Boolean.valueOf(☃)), 2);
      }
   }

   public static boolean func_208072_b(IWorld var0, BlockPos var1) {
      IFluidState ☃ = ☃.func_204610_c(☃);
      return ☃.func_180495_p(☃).func_177230_c() == Blocks.field_150355_j && ☃.func_206882_g() >= 8 && ☃.func_206889_d();
   }

   private static boolean func_203157_b(IBlockReader var0, BlockPos var1) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      if (☃x == Blocks.field_203203_C) {
         return ☃.func_177229_b(field_203160_a);
      } else {
         return ☃x != Blocks.field_150425_aM;
      }
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 5;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      double ☃ = (double)☃.func_177958_n();
      double ☃x = (double)☃.func_177956_o();
      double ☃xx = (double)☃.func_177952_p();
      if (☃.func_177229_b(field_203160_a)) {
         ☃.func_195589_b(Particles.field_203218_U, ☃ + 0.5, ☃x + 0.8, ☃xx, 0.0, 0.0, 0.0);
         if (☃.nextInt(200) == 0) {
            ☃.func_184134_a(☃, ☃x, ☃xx, SoundEvents.field_203282_jc, SoundCategory.BLOCKS, 0.2F + ☃.nextFloat() * 0.2F, 0.9F + ☃.nextFloat() * 0.15F, false);
         }
      } else {
         ☃.func_195589_b(Particles.field_203220_f, ☃ + 0.5, ☃x, ☃xx + 0.5, 0.0, 0.04, 0.0);
         ☃.func_195589_b(Particles.field_203220_f, ☃ + (double)☃.nextFloat(), ☃x + (double)☃.nextFloat(), ☃xx + (double)☃.nextFloat(), 0.0, 0.04, 0.0);
         if (☃.nextInt(200) == 0) {
            ☃.func_184134_a(☃, ☃x, ☃xx, SoundEvents.field_203251_S, SoundCategory.BLOCKS, 0.2F + ☃.nextFloat() * 0.2F, 0.9F + ☃.nextFloat() * 0.15F, false);
         }
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (!☃.func_196955_c(☃, ☃)) {
         return Blocks.field_150355_j.func_176223_P();
      } else {
         if (☃ == EnumFacing.DOWN) {
            ☃.func_180501_a(☃, Blocks.field_203203_C.func_176223_P().func_206870_a(field_203160_a, Boolean.valueOf(func_203157_b(☃, ☃))), 2);
         } else if (☃ == EnumFacing.UP && ☃.func_177230_c() != Blocks.field_203203_C && func_208072_b(☃, ☃)) {
            ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
         }

         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      Block ☃ = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
      return ☃ == Blocks.field_203203_C || ☃ == Blocks.field_196814_hQ || ☃ == Blocks.field_150425_aM;
   }

   @Override
   public boolean func_149703_v() {
      return false;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.INVISIBLE;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_203160_a);
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 11);
      return Fluids.field_204546_a;
   }
}
