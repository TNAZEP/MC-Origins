package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleBlockPlacer extends BlockPlacer {
   public static final Codec<SimpleBlockPlacer> CODEC = Codec.unit((Supplier<SimpleBlockPlacer>)(() -> SimpleBlockPlacer.INSTANCE));
   public static final SimpleBlockPlacer INSTANCE = new SimpleBlockPlacer();

   @Override
   protected BlockPlacerType<?> type() {
      return BlockPlacerType.SIMPLE_BLOCK_PLACER;
   }

   @Override
   public void place(LevelAccessor var1, BlockPos var2, BlockState var3, Random var4) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
   }
}
