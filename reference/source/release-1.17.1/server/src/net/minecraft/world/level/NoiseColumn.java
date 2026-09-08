package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class NoiseColumn {
   private final int minY;
   private final BlockState[] column;

   public NoiseColumn(int var1, BlockState[] var2) {
      this.minY = â˜ƒ;
      this.column = â˜ƒ;
   }

   public BlockState getBlockState(BlockPos var1) {
      int â˜ƒ = â˜ƒ.getY() - this.minY;
      return â˜ƒ >= 0 && â˜ƒ < this.column.length ? this.column[â˜ƒ] : Blocks.AIR.defaultBlockState();
   }
}
