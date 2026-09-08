package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockFlowingFluid extends Block implements IBucketPickupHandler {
   public static final IntegerProperty field_176367_b = BlockStateProperties.field_208132_ag;
   protected final FlowingFluid field_204517_c;
   private final List<IFluidState> field_212565_c;
   private final Map<IBlockState, VoxelShape> field_196481_b = Maps.<IBlockState, VoxelShape>newIdentityHashMap();

   protected BlockFlowingFluid(FlowingFluid var1, Block.Properties var2) {
      super(☃);
      this.field_204517_c = ☃;
      this.field_212565_c = Lists.<IFluidState>newArrayList();
      this.field_212565_c.add(☃.func_207204_a(false));

      for(int ☃ = 1; ☃ < 8; ++☃) {
         this.field_212565_c.add(☃.func_207207_a(8 - ☃, false));
      }

      this.field_212565_c.add(☃.func_207207_a(8, true));
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176367_b, Integer.valueOf(0)));
   }

   @Override
   public void func_196265_a(IBlockState var1, World var2, BlockPos var3, Random var4) {
      ☃.func_204610_c(☃).func_206891_b(☃, ☃, ☃);
   }

   @Override
   public boolean func_200123_i(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return false;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return !this.field_204517_c.func_207185_a(FluidTags.field_206960_b);
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      int ☃ = ☃.func_177229_b(field_176367_b);
      return (IFluidState)this.field_212565_c.get(Math.min(☃, 8));
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_200293_a(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      IFluidState ☃ = ☃.func_204610_c(☃.func_177984_a());
      return ☃.func_206886_c().func_207187_a(this.field_204517_c) ? VoxelShapes.func_197868_b() : (VoxelShape)this.field_196481_b.computeIfAbsent(☃, var0 -> {
         IFluidState ☃ = var0.func_204520_s();
         return VoxelShapes.func_197873_a(0.0, 0.0, 0.0, 1.0, (double)☃.func_206885_f(), 1.0);
      });
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.INVISIBLE;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return this.field_204517_c.func_205569_a(☃);
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (this.func_204515_c(☃, ☃, ☃)) {
         ☃.func_205219_F_().func_205360_a(☃, ☃.func_204520_s().func_206886_c(), this.func_149738_a(☃));
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_204520_s().func_206889_d() || ☃.func_204520_s().func_206889_d()) {
         ☃.func_205219_F_().func_205360_a(☃, ☃.func_204520_s().func_206886_c(), this.func_149738_a(☃));
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (this.func_204515_c(☃, ☃, ☃)) {
         ☃.func_205219_F_().func_205360_a(☃, ☃.func_204520_s().func_206886_c(), this.func_149738_a(☃));
      }
   }

   public boolean func_204515_c(World var1, BlockPos var2, IBlockState var3) {
      if (this.field_204517_c.func_207185_a(FluidTags.field_206960_b)) {
         boolean ☃ = false;

         for(EnumFacing ☃x : EnumFacing.values()) {
            if (☃x != EnumFacing.DOWN && ☃.func_204610_c(☃.func_177972_a(☃x)).func_206884_a(FluidTags.field_206959_a)) {
               ☃ = true;
               break;
            }
         }

         if (☃) {
            IFluidState ☃x = ☃.func_204610_c(☃);
            if (☃x.func_206889_d()) {
               ☃.func_175656_a(☃, Blocks.field_150343_Z.func_176223_P());
               this.func_180688_d(☃, ☃);
               return false;
            }

            if (☃x.func_206885_f() >= 0.44444445F) {
               ☃.func_175656_a(☃, Blocks.field_150347_e.func_176223_P());
               this.func_180688_d(☃, ☃);
               return false;
            }
         }
      }

      return true;
   }

   protected void func_180688_d(IWorld var1, BlockPos var2) {
      double ☃ = (double)☃.func_177958_n();
      double ☃x = (double)☃.func_177956_o();
      double ☃xx = (double)☃.func_177952_p();
      ☃.func_184133_a(
         null, ☃, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.5F, 2.6F + (☃.func_201674_k().nextFloat() - ☃.func_201674_k().nextFloat()) * 0.8F
      );

      for(int ☃xxx = 0; ☃xxx < 8; ++☃xxx) {
         ☃.func_195594_a(Particles.field_197594_E, ☃ + Math.random(), ☃x + 1.2, ☃xx + Math.random(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176367_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_176367_b) == 0) {
         ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 11);
         return this.field_204517_c;
      } else {
         return Fluids.field_204541_a;
      }
   }
}
