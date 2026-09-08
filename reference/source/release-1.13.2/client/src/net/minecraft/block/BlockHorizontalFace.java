package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class BlockHorizontalFace extends BlockHorizontal {
   public static final EnumProperty<AttachFace> field_196366_M = BlockStateProperties.field_208158_K;

   protected BlockHorizontalFace(Block.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      EnumFacing ☃ = func_196365_i(☃).func_176734_d();
      BlockPos ☃x = ☃.func_177972_a(☃);
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      Block ☃xxx = ☃xx.func_177230_c();
      if (func_193384_b(☃xxx)) {
         return false;
      } else {
         boolean ☃ = ☃xx.func_193401_d(☃, ☃x, ☃.func_176734_d()) == BlockFaceShape.SOLID;
         if (☃ == EnumFacing.UP) {
            return ☃xxx == Blocks.field_150438_bZ || ☃;
         } else {
            return !func_193382_c(☃xxx) && ☃;
         }
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      for(EnumFacing ☃ : ☃.func_196009_e()) {
         IBlockState ☃x;
         if (☃.func_176740_k() == EnumFacing.Axis.Y) {
            ☃x = this.func_176223_P()
               .func_206870_a(field_196366_M, ☃ == EnumFacing.UP ? AttachFace.CEILING : AttachFace.FLOOR)
               .func_206870_a(field_185512_D, ☃.func_195992_f());
         } else {
            ☃x = this.func_176223_P().func_206870_a(field_196366_M, AttachFace.WALL).func_206870_a(field_185512_D, ☃.func_176734_d());
         }

         if (☃x.func_196955_c(☃.func_195991_k(), ☃.func_195995_a())) {
            return ☃x;
         }
      }

      return null;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return func_196365_i(☃).func_176734_d() == ☃ && !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected static EnumFacing func_196365_i(IBlockState var0) {
      switch((AttachFace)☃.func_177229_b(field_196366_M)) {
         case CEILING:
            return EnumFacing.DOWN;
         case FLOOR:
            return EnumFacing.UP;
         default:
            return ☃.func_177229_b(field_185512_D);
      }
   }
}
