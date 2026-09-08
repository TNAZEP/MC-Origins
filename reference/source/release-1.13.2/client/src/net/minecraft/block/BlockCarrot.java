package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockCarrot extends BlockCrops {
   private static final VoxelShape[] field_196395_a = new VoxelShape[]{
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
   };

   public BlockCarrot(Block.Properties var1) {
      super(☃);
   }

   @Override
   protected IItemProvider func_199772_f() {
      return Items.field_151172_bF;
   }

   @Override
   protected IItemProvider func_199773_g() {
      return Items.field_151172_bF;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196395_a[☃.func_177229_b(this.func_185524_e())];
   }
}
