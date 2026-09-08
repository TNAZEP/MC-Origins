package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTransparentBlock extends Block {
   protected HalfTransparentBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
      return â˜ƒ.is(this) ? true : super.skipRendering(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
