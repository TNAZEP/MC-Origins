package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockObserver extends BlockDirectional {
   public static final BooleanProperty field_190963_a = BlockStateProperties.field_208194_u;

   public BlockObserver(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176387_N, EnumFacing.SOUTH).func_206870_a(field_190963_a, Boolean.valueOf(false))
      );
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176387_N, field_190963_a);
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176387_N, ☃.func_185831_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176387_N)));
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_177229_b(field_190963_a)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_190963_a, Boolean.valueOf(false)), 2);
      } else {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_190963_a, Boolean.valueOf(true)), 2);
         ☃.func_205220_G_().func_205360_a(☃, this, 2);
      }

      this.func_190961_e(☃, ☃, ☃);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_176387_N) == ☃ && !☃.func_177229_b(field_190963_a)) {
         this.func_203420_a(☃, ☃);
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   private void func_203420_a(IWorld var1, BlockPos var2) {
      if (!☃.func_201670_d() && !☃.func_205220_G_().func_205359_a(☃, this)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 2);
      }
   }

   protected void func_190961_e(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_176387_N);
      BlockPos ☃x = ☃.func_177972_a(☃.func_176734_d());
      ☃.func_190524_a(☃x, this, ☃);
      ☃.func_175695_a(☃x, this, ☃);
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_185911_a(☃, ☃, ☃);
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_190963_a) && ☃.func_177229_b(field_176387_N) == ☃ ? 15 : 0;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (!☃.func_201670_d() && ☃.func_177229_b(field_190963_a) && !☃.func_205220_G_().func_205359_a(☃, this)) {
            IBlockState ☃ = ☃.func_206870_a(field_190963_a, Boolean.valueOf(false));
            ☃.func_180501_a(☃, ☃, 18);
            this.func_190961_e(☃, ☃, ☃);
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (!☃.field_72995_K && ☃.func_177229_b(field_190963_a) && ☃.func_205220_G_().func_205359_a(☃, this)) {
            this.func_190961_e(☃, ☃, ☃.func_206870_a(field_190963_a, Boolean.valueOf(false)));
         }
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_176387_N, ☃.func_196010_d().func_176734_d().func_176734_d());
   }
}
