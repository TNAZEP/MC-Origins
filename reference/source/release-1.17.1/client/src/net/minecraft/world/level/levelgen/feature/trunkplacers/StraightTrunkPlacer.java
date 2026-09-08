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

public class StraightTrunkPlacer extends TrunkPlacer {
   public static final Codec<StraightTrunkPlacer> CODEC = RecordCodecBuilder.create(var0 -> trunkPlacerParts(var0).apply(var0, StraightTrunkPlacer::new));

   public StraightTrunkPlacer(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.below(), â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.above(â˜ƒ), â˜ƒ);
      }

      return ImmutableList.of(new FoliagePlacer.FoliageAttachment(â˜ƒ.above(â˜ƒ), 0, false));
   }
}
