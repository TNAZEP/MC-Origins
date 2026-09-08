package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockBeetroot extends BlockCrops {
   public static final IntegerProperty field_185531_a = BlockStateProperties.field_208168_U;
   private static final VoxelShape[] field_196394_c = new VoxelShape[]{
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
   };

   public BlockBeetroot(Block.Properties var1) {
      super(☃);
   }

   @Override
   public IntegerProperty func_185524_e() {
      return field_185531_a;
   }

   @Override
   public int func_185526_g() {
      return 3;
   }

   @Override
   protected IItemProvider func_199772_f() {
      return Items.field_185163_cU;
   }

   @Override
   protected IItemProvider func_199773_g() {
      return Items.field_185164_cV;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.nextInt(3) != 0) {
         super.func_196267_b(☃, ☃, ☃, ☃);
      }
   }

   @Override
   protected int func_185529_b(World var1) {
      return super.func_185529_b(☃) / 3;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185531_a);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196394_c[☃.func_177229_b(this.func_185524_e())];
   }
}
