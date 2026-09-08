package net.minecraft.world.level.levelgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface BaseStoneSource {
   default BlockState getBaseBlock(BlockPos var1) {
      return this.getBaseBlock(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   BlockState getBaseBlock(int var1, int var2, int var3);
}
