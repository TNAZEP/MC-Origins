package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
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

public class BlockCoralWallFanDead extends BlockCoralFan {
   public static final DirectionProperty field_211884_b = BlockHorizontal.field_185512_D;
   private static final Map<EnumFacing, VoxelShape> field_211885_c = Maps.newEnumMap(
      ImmutableMap.of(
         EnumFacing.NORTH,
         Block.func_208617_a(0.0, 4.0, 5.0, 16.0, 12.0, 16.0),
         EnumFacing.SOUTH,
         Block.func_208617_a(0.0, 4.0, 0.0, 16.0, 12.0, 11.0),
         EnumFacing.WEST,
         Block.func_208617_a(5.0, 4.0, 0.0, 16.0, 12.0, 16.0),
         EnumFacing.EAST,
         Block.func_208617_a(0.0, 4.0, 0.0, 11.0, 12.0, 16.0)
      )
   );

   protected BlockCoralWallFanDead(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_211884_b, EnumFacing.NORTH).func_206870_a(field_212560_b, Boolean.valueOf(true))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return (VoxelShape)field_211885_c.get(☃.func_177229_b(field_211884_b));
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_211884_b, ☃.func_185831_a(☃.func_177229_b(field_211884_b)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_211884_b)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_211884_b, field_212560_b);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_212560_b)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return ☃.func_176734_d() == ☃.func_177229_b(field_211884_b) && !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : ☃;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_211884_b);
      BlockPos ☃x = ☃.func_177972_a(☃.func_176734_d());
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      return ☃xx.func_193401_d(☃, ☃x, ☃) == BlockFaceShape.SOLID && !func_193382_c(☃xx.func_177230_c());
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = super.func_196258_a(☃);
      IWorldReaderBase ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a();
      EnumFacing[] ☃xxx = ☃.func_196009_e();

      for(EnumFacing ☃xxxx : ☃xxx) {
         if (☃xxxx.func_176740_k().func_176722_c()) {
            ☃ = ☃.func_206870_a(field_211884_b, ☃xxxx.func_176734_d());
            if (☃.func_196955_c(☃x, ☃xx)) {
               return ☃;
            }
         }
      }

      return null;
   }
}
