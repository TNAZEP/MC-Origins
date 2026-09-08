package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
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
import net.minecraft.world.World;

public class BlockRedstoneTorchWall extends BlockRedstoneTorch {
   public static final DirectionProperty field_196530_b = BlockHorizontal.field_185512_D;
   public static final BooleanProperty field_196531_c = BlockRedstoneTorch.field_196528_a;

   protected BlockRedstoneTorchWall(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_196530_b, EnumFacing.NORTH).func_206870_a(field_196531_c, Boolean.valueOf(true))
      );
   }

   @Override
   public String func_149739_a() {
      return this.func_199767_j().func_77658_a();
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return Blocks.field_196591_bQ.func_196244_b(☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return Blocks.field_196591_bQ.func_196260_a(☃, ☃, ☃);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return Blocks.field_196591_bQ.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = Blocks.field_196591_bQ.func_196258_a(☃);
      return ☃ == null ? null : this.func_176223_P().func_206870_a(field_196530_b, ☃.func_177229_b(field_196530_b));
   }

   @Override
   protected boolean func_176597_g(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ((EnumFacing)☃.func_177229_b(field_196530_b)).func_176734_d();
      return ☃.func_175709_b(☃.func_177972_a(☃), ☃);
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_196531_c) && ☃.func_177229_b(field_196530_b) != ☃ ? 15 : 0;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return Blocks.field_196591_bQ.func_185499_a(☃, ☃);
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return Blocks.field_196591_bQ.func_185471_a(☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196530_b, field_196531_c);
   }
}
