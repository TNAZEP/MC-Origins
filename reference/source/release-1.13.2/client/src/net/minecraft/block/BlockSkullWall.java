package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockSkullWall extends BlockAbstractSkull {
   public static final DirectionProperty field_196302_a = BlockHorizontal.field_185512_D;
   private static final Map<EnumFacing, VoxelShape> field_196303_A = Maps.newEnumMap(
      ImmutableMap.of(
         EnumFacing.NORTH,
         Block.func_208617_a(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         EnumFacing.SOUTH,
         Block.func_208617_a(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         EnumFacing.EAST,
         Block.func_208617_a(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         EnumFacing.WEST,
         Block.func_208617_a(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );

   protected BlockSkullWall(BlockSkull.ISkullType var1, Block.Properties var2) {
      super(☃, ☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196302_a, EnumFacing.NORTH));
   }

   @Override
   public String func_149739_a() {
      return this.func_199767_j().func_77658_a();
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return (VoxelShape)field_196303_A.get(☃.func_177229_b(field_196302_a));
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = this.func_176223_P();
      IBlockReader ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a();
      EnumFacing[] ☃xxx = ☃.func_196009_e();

      for(EnumFacing ☃xxxx : ☃xxx) {
         if (☃xxxx.func_176740_k().func_176722_c()) {
            EnumFacing ☃xxxxx = ☃xxxx.func_176734_d();
            ☃ = ☃.func_206870_a(field_196302_a, ☃xxxxx);
            if (!☃x.func_180495_p(☃xx.func_177972_a(☃xxxx)).func_196953_a(☃)) {
               return ☃;
            }
         }
      }

      return null;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_196302_a, ☃.func_185831_a(☃.func_177229_b(field_196302_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_196302_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196302_a);
   }
}
