package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockSoulSand extends Block {
   protected static final VoxelShape field_196509_a = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

   public BlockSoulSand(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196509_a;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      ☃.field_70159_w *= 0.4;
      ☃.field_70179_y *= 0.4;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      BlockBubbleColumn.func_203159_a(☃, ☃.func_177984_a(), false);
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 20;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
