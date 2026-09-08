package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public abstract class BlockHorizontal extends Block {
   public static final DirectionProperty field_185512_D = BlockStateProperties.field_208157_J;

   protected BlockHorizontal(Block.Properties var1) {
      super(☃);
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_185512_D, ☃.func_185831_a(☃.func_177229_b(field_185512_D)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_185512_D)));
   }
}
