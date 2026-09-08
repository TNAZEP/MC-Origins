package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class BlockWall extends BlockFourWay {
   public static final BooleanProperty field_176256_a = BlockStateProperties.field_208149_B;
   private final VoxelShape[] field_196422_D;
   private final VoxelShape[] field_196423_E;

   public BlockWall(Block.Properties var1) {
      super(0.0F, 3.0F, 0.0F, 14.0F, 24.0F, ☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176256_a, Boolean.valueOf(true))
            .func_206870_a(field_196409_a, Boolean.valueOf(false))
            .func_206870_a(field_196411_b, Boolean.valueOf(false))
            .func_206870_a(field_196413_c, Boolean.valueOf(false))
            .func_206870_a(field_196414_y, Boolean.valueOf(false))
            .func_206870_a(field_204514_u, Boolean.valueOf(false))
      );
      this.field_196422_D = this.func_196408_a(4.0F, 3.0F, 16.0F, 0.0F, 14.0F);
      this.field_196423_E = this.func_196408_a(4.0F, 3.0F, 24.0F, 0.0F, 24.0F);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177229_b(field_176256_a) ? this.field_196422_D[this.func_196406_i(☃)] : super.func_196244_b(☃, ☃, ☃);
   }

   @Override
   public VoxelShape func_196268_f(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177229_b(field_176256_a) ? this.field_196423_E[this.func_196406_i(☃)] : super.func_196268_f(☃, ☃, ☃);
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }

   private boolean func_196421_a(IBlockState var1, BlockFaceShape var2) {
      Block ☃ = ☃.func_177230_c();
      boolean ☃x = ☃ == BlockFaceShape.MIDDLE_POLE_THICK || ☃ == BlockFaceShape.MIDDLE_POLE && ☃ instanceof BlockFenceGate;
      return !func_194143_e(☃) && ☃ == BlockFaceShape.SOLID || ☃x;
   }

   public static boolean func_194143_e(Block var0) {
      return Block.func_193382_c(☃)
         || ☃ == Blocks.field_180401_cv
         || ☃ == Blocks.field_150440_ba
         || ☃ == Blocks.field_150423_aK
         || ☃ == Blocks.field_196625_cS
         || ☃ == Blocks.field_196628_cT
         || ☃ == Blocks.field_185778_de
         || ☃ == Blocks.field_150335_W;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IWorldReaderBase ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IFluidState ☃xx = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      BlockPos ☃xxx = ☃x.func_177978_c();
      BlockPos ☃xxxx = ☃x.func_177974_f();
      BlockPos ☃xxxxx = ☃x.func_177968_d();
      BlockPos ☃xxxxxx = ☃x.func_177976_e();
      IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxx);
      IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃xxxx);
      IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxx);
      IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxxxx);
      boolean ☃xxxxxxxxxxx = this.func_196421_a(☃xxxxxxx, ☃xxxxxxx.func_193401_d(☃, ☃xxx, EnumFacing.SOUTH));
      boolean ☃xxxxxxxxxxxx = this.func_196421_a(☃xxxxxxxx, ☃xxxxxxxx.func_193401_d(☃, ☃xxxx, EnumFacing.WEST));
      boolean ☃xxxxxxxxxxxxx = this.func_196421_a(☃xxxxxxxxx, ☃xxxxxxxxx.func_193401_d(☃, ☃xxxxx, EnumFacing.NORTH));
      boolean ☃xxxxxxxxxxxxxx = this.func_196421_a(☃xxxxxxxxxx, ☃xxxxxxxxxx.func_193401_d(☃, ☃xxxxxx, EnumFacing.EAST));
      boolean ☃xxxxxxxxxxxxxxx = (!☃xxxxxxxxxxx || ☃xxxxxxxxxxxx || !☃xxxxxxxxxxxxx || ☃xxxxxxxxxxxxxx)
         && (☃xxxxxxxxxxx || !☃xxxxxxxxxxxx || ☃xxxxxxxxxxxxx || !☃xxxxxxxxxxxxxx);
      return this.func_176223_P()
         .func_206870_a(field_176256_a, Boolean.valueOf(☃xxxxxxxxxxxxxxx || !☃.func_175623_d(☃x.func_177984_a())))
         .func_206870_a(field_196409_a, Boolean.valueOf(☃xxxxxxxxxxx))
         .func_206870_a(field_196411_b, Boolean.valueOf(☃xxxxxxxxxxxx))
         .func_206870_a(field_196413_c, Boolean.valueOf(☃xxxxxxxxxxxxx))
         .func_206870_a(field_196414_y, Boolean.valueOf(☃xxxxxxxxxxxxxx))
         .func_206870_a(field_204514_u, Boolean.valueOf(☃xx.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204514_u)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      if (☃ == EnumFacing.DOWN) {
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         boolean ☃ = ☃ == EnumFacing.NORTH ? this.func_196421_a(☃, ☃.func_193401_d(☃, ☃, ☃.func_176734_d())) : ☃.func_177229_b(field_196409_a);
         boolean ☃x = ☃ == EnumFacing.EAST ? this.func_196421_a(☃, ☃.func_193401_d(☃, ☃, ☃.func_176734_d())) : ☃.func_177229_b(field_196411_b);
         boolean ☃xx = ☃ == EnumFacing.SOUTH ? this.func_196421_a(☃, ☃.func_193401_d(☃, ☃, ☃.func_176734_d())) : ☃.func_177229_b(field_196413_c);
         boolean ☃xxx = ☃ == EnumFacing.WEST ? this.func_196421_a(☃, ☃.func_193401_d(☃, ☃, ☃.func_176734_d())) : ☃.func_177229_b(field_196414_y);
         boolean ☃xxxx = (!☃ || ☃x || !☃xx || ☃xxx) && (☃ || !☃x || ☃xx || !☃xxx);
         return ☃.func_206870_a(field_176256_a, Boolean.valueOf(☃xxxx || !☃.func_175623_d(☃.func_177984_a())))
            .func_206870_a(field_196409_a, Boolean.valueOf(☃))
            .func_206870_a(field_196411_b, Boolean.valueOf(☃x))
            .func_206870_a(field_196413_c, Boolean.valueOf(☃xx))
            .func_206870_a(field_196414_y, Boolean.valueOf(☃xxx));
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176256_a, field_196409_a, field_196411_b, field_196414_y, field_196413_c, field_204514_u);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ != EnumFacing.UP && ☃ != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
   }
}
