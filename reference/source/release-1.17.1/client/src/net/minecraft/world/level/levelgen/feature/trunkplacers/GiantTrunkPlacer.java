package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class GiantTrunkPlacer extends TrunkPlacer {
   public static final Codec<GiantTrunkPlacer> CODEC = RecordCodecBuilder.create(var0 -> trunkPlacerParts(var0).apply(var0, GiantTrunkPlacer::new));

   public GiantTrunkPlacer(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.GIANT_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      BlockPos â˜ƒ = â˜ƒ.below();
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.east(), â˜ƒ);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.south(), â˜ƒ);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.south().east(), â˜ƒ);
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         placeLogIfFreeWithOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, 0, â˜ƒxx, 0);
         if (â˜ƒxx < â˜ƒ - 1) {
            placeLogIfFreeWithOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, 1, â˜ƒxx, 0);
            placeLogIfFreeWithOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, 1, â˜ƒxx, 1);
            placeLogIfFreeWithOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, 0, â˜ƒxx, 1);
         }
      }

      return ImmutableList.of(new FoliagePlacer.FoliageAttachment(â˜ƒ.above(â˜ƒ), 0, true));
   }

   private static void placeLogIfFreeWithOffset(
      LevelSimulatedReader var0,
      BiConsumer<BlockPos, BlockState> var1,
      Random var2,
      BlockPos.MutableBlockPos var3,
      TreeConfiguration var4,
      BlockPos var5,
      int var6,
      int var7,
      int var8
   ) {
      â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      placeLogIfFree(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
