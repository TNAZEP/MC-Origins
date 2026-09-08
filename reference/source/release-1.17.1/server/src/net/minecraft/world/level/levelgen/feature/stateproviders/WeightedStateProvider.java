package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;

public class WeightedStateProvider extends BlockStateProvider {
   public static final Codec<WeightedStateProvider> CODEC = SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC)
      .<WeightedStateProvider>comapFlatMap(WeightedStateProvider::create, var0 -> var0.weightedList)
      .fieldOf("entries")
      .codec();
   private final SimpleWeightedRandomList<BlockState> weightedList;

   private static DataResult<WeightedStateProvider> create(SimpleWeightedRandomList<BlockState> var0) {
      return â˜ƒ.isEmpty() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new WeightedStateProvider(â˜ƒ));
   }

   public WeightedStateProvider(SimpleWeightedRandomList<BlockState> var1) {
      this.weightedList = â˜ƒ;
   }

   public WeightedStateProvider(SimpleWeightedRandomList.Builder<BlockState> var1) {
      this(â˜ƒ.build());
   }

   @Override
   protected BlockStateProviderType<?> type() {
      return BlockStateProviderType.WEIGHTED_STATE_PROVIDER;
   }

   @Override
   public BlockState getState(Random var1, BlockPos var2) {
      return (BlockState)this.weightedList.getRandomValue(â˜ƒ).orElseThrow(IllegalStateException::new);
   }
}
