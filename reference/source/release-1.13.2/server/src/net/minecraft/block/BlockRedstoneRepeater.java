package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
   public static final BooleanProperty field_176411_a = BlockStateProperties.field_208191_r;
   public static final IntegerProperty field_176410_b = BlockStateProperties.field_208126_aa;

   protected BlockRedstoneRepeater(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_185512_D, EnumFacing.NORTH)
            .func_206870_a(field_176410_b, Integer.valueOf(1))
            .func_206870_a(field_176411_a, Boolean.valueOf(false))
            .func_206870_a(field_196348_c, Boolean.valueOf(false))
      );
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (!☃.field_71075_bZ.field_75099_e) {
         return false;
      } else {
         ☃.func_180501_a(☃, ☃.func_177231_a(field_176410_b), 3);
         return true;
      }
   }

   @Override
   protected int func_196346_i(IBlockState var1) {
      return ☃.func_177229_b(field_176410_b) * 2;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = super.func_196258_a(☃);
      return ☃.func_206870_a(field_176411_a, Boolean.valueOf(this.func_176405_b(☃.func_195991_k(), ☃.func_195995_a(), ☃)));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return !☃.func_201670_d() && ☃.func_176740_k() != ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k()
         ? ☃.func_206870_a(field_176411_a, Boolean.valueOf(this.func_176405_b(☃, ☃, ☃)))
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_176405_b(IWorldReaderBase var1, BlockPos var2, IBlockState var3) {
      return this.func_176407_c(☃, ☃, ☃) > 0;
   }

   @Override
   protected boolean func_185545_A(IBlockState var1) {
      return func_185546_B(☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176410_b, field_176411_a, field_196348_c);
   }
}
