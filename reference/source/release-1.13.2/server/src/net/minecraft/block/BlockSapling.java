package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.trees.AbstractTree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockSapling extends BlockBush implements IGrowable {
   public static final IntegerProperty field_176479_b = BlockStateProperties.field_208137_al;
   protected static final VoxelShape field_196386_b = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
   private final AbstractTree field_196387_c;

   protected BlockSapling(AbstractTree var1, Block.Properties var2) {
      super(☃);
      this.field_196387_c = ☃;
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176479_b, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196386_b;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      super.func_196267_b(☃, ☃, ☃, ☃);
      if (☃.func_201696_r(☃.func_177984_a()) >= 9 && ☃.nextInt(7) == 0) {
         this.func_176478_d(☃, ☃, ☃, ☃);
      }
   }

   public void func_176478_d(IWorld var1, BlockPos var2, IBlockState var3, Random var4) {
      if (☃.func_177229_b(field_176479_b) == 0) {
         ☃.func_180501_a(☃, ☃.func_177231_a(field_176479_b), 4);
      } else {
         this.field_196387_c.func_196935_a(☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return (double)☃.field_73012_v.nextFloat() < 0.45;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.func_176478_d(☃, ☃, ☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176479_b);
   }
}
