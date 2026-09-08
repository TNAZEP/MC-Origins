package net.minecraft.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockEndRod extends BlockDirectional {
   protected static final VoxelShape field_185630_a = Block.func_208617_a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   protected static final VoxelShape field_185631_b = Block.func_208617_a(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
   protected static final VoxelShape field_185632_c = Block.func_208617_a(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

   protected BlockEndRod(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176387_N, EnumFacing.UP));
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176387_N, ☃.func_185831_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_206870_a(field_176387_N, ☃.func_185803_b(☃.func_177229_b(field_176387_N)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch(((EnumFacing)☃.func_177229_b(field_176387_N)).func_176740_k()) {
         case X:
         default:
            return field_185632_c;
         case Z:
            return field_185631_b;
         case Y:
            return field_185630_a;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      EnumFacing ☃ = ☃.func_196000_l();
      IBlockState ☃x = ☃.func_195991_k().func_180495_p(☃.func_195995_a().func_177972_a(☃.func_176734_d()));
      return ☃x.func_177230_c() == this && ☃x.func_177229_b(field_176387_N) == ☃
         ? this.func_176223_P().func_206870_a(field_176387_N, ☃.func_176734_d())
         : this.func_176223_P().func_206870_a(field_176387_N, ☃);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176387_N);
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.NORMAL;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
