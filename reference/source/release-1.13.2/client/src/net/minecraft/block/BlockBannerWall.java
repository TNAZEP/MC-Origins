package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class BlockBannerWall extends BlockAbstractBanner {
   public static final DirectionProperty field_196290_a = BlockHorizontal.field_185512_D;
   private static final Map<EnumFacing, VoxelShape> field_196291_b = Maps.newEnumMap(
      ImmutableMap.of(
         EnumFacing.NORTH,
         Block.func_208617_a(0.0, 0.0, 14.0, 16.0, 12.5, 16.0),
         EnumFacing.SOUTH,
         Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 12.5, 2.0),
         EnumFacing.WEST,
         Block.func_208617_a(14.0, 0.0, 0.0, 16.0, 12.5, 16.0),
         EnumFacing.EAST,
         Block.func_208617_a(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)
      )
   );

   public BlockBannerWall(EnumDyeColor var1, Block.Properties var2) {
      super(☃, ☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196290_a, EnumFacing.NORTH));
   }

   @Override
   public String func_149739_a() {
      return this.func_199767_j().func_77658_a();
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return ☃.func_180495_p(☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_196290_a)).func_176734_d())).func_185904_a().func_76220_a();
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == ((EnumFacing)☃.func_177229_b(field_196290_a)).func_176734_d() && !☃.func_196955_c(☃, ☃)
         ? Blocks.field_150350_a.func_176223_P()
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return (VoxelShape)field_196291_b.get(☃.func_177229_b(field_196290_a));
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = this.func_176223_P();
      IWorldReaderBase ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a();
      EnumFacing[] ☃xxx = ☃.func_196009_e();

      for(EnumFacing ☃xxxx : ☃xxx) {
         if (☃xxxx.func_176740_k().func_176722_c()) {
            EnumFacing ☃xxxxx = ☃xxxx.func_176734_d();
            ☃ = ☃.func_206870_a(field_196290_a, ☃xxxxx);
            if (☃.func_196955_c(☃x, ☃xx)) {
               return ☃;
            }
         }
      }

      return null;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_196290_a, ☃.func_185831_a(☃.func_177229_b(field_196290_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_196290_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196290_a);
   }
}
