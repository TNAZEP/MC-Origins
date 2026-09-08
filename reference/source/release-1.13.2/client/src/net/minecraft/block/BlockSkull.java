package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockSkull extends BlockAbstractSkull {
   public static final IntegerProperty field_196294_a = BlockStateProperties.field_208138_am;
   protected static final VoxelShape field_196295_b = Block.func_208617_a(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

   protected BlockSkull(BlockSkull.ISkullType var1, Block.Properties var2) {
      super(☃, ☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196294_a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196295_b;
   }

   @Override
   public VoxelShape func_196247_c(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return VoxelShapes.func_197880_a();
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P()
         .func_206870_a(field_196294_a, Integer.valueOf(MathHelper.func_76128_c((double)(☃.func_195990_h() * 16.0F / 360.0F) + 0.5) & 15));
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_196294_a, Integer.valueOf(☃.func_185833_a(☃.func_177229_b(field_196294_a), 16)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_206870_a(field_196294_a, Integer.valueOf(☃.func_185802_a(☃.func_177229_b(field_196294_a), 16)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196294_a);
   }

   public interface ISkullType {
   }

   public static enum Types implements BlockSkull.ISkullType {
      SKELETON,
      WITHER_SKELETON,
      PLAYER,
      ZOMBIE,
      CREEPER,
      DRAGON;
   }
}
