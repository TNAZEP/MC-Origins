package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class BlockBasePressurePlate extends Block {
   protected static final VoxelShape field_185509_a = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
   protected static final VoxelShape field_185510_b = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
   protected static final AxisAlignedBB field_185511_c = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

   protected BlockBasePressurePlate(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return this.func_176576_e(☃) > 0 ? field_185509_a : field_185510_b;
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 20;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_181623_g() {
      return true;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == EnumFacing.DOWN && !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      return ☃.func_185896_q() || ☃.func_177230_c() instanceof BlockFence;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         int ☃ = this.func_176576_e(☃);
         if (☃ > 0) {
            this.func_180666_a(☃, ☃, ☃, ☃);
         }
      }
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.field_72995_K) {
         int ☃ = this.func_176576_e(☃);
         if (☃ == 0) {
            this.func_180666_a(☃, ☃, ☃, ☃);
         }
      }
   }

   protected void func_180666_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      int ☃ = this.func_180669_e(☃, ☃);
      boolean ☃x = ☃ > 0;
      boolean ☃xx = ☃ > 0;
      if (☃ != ☃) {
         ☃ = this.func_176575_a(☃, ☃);
         ☃.func_180501_a(☃, ☃, 2);
         this.func_176578_d(☃, ☃);
         ☃.func_175704_b(☃, ☃);
      }

      if (!☃xx && ☃x) {
         this.func_185508_c(☃, ☃);
      } else if (☃xx && !☃x) {
         this.func_185507_b(☃, ☃);
      }

      if (☃xx) {
         ☃.func_205220_G_().func_205360_a(new BlockPos(☃), this, this.func_149738_a(☃));
      }
   }

   protected abstract void func_185507_b(IWorld var1, BlockPos var2);

   protected abstract void func_185508_c(IWorld var1, BlockPos var2);

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         if (this.func_176576_e(☃) > 0) {
            this.func_176578_d(☃, ☃);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   protected void func_176578_d(World var1, BlockPos var2) {
      ☃.func_195593_d(☃, this);
      ☃.func_195593_d(☃.func_177977_b(), this);
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return this.func_176576_e(☃);
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.UP ? this.func_176576_e(☃) : 0;
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.DESTROY;
   }

   protected abstract int func_180669_e(World var1, BlockPos var2);

   protected abstract int func_176576_e(IBlockState var1);

   protected abstract IBlockState func_176575_a(IBlockState var1, int var2);

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
