package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockDirtSnowy extends Block {
   public static final BooleanProperty field_196382_a = BlockStateProperties.field_208196_w;

   protected BlockDirtSnowy(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196382_a, Boolean.valueOf(false)));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ != EnumFacing.UP) {
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         Block ☃ = ☃.func_177230_c();
         return ☃.func_206870_a(field_196382_a, Boolean.valueOf(☃ == Blocks.field_196604_cC || ☃ == Blocks.field_150433_aE));
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      Block ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a().func_177984_a()).func_177230_c();
      return this.func_176223_P().func_206870_a(field_196382_a, Boolean.valueOf(☃ == Blocks.field_196604_cC || ☃ == Blocks.field_150433_aE));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196382_a);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Blocks.field_150346_d;
   }
}
