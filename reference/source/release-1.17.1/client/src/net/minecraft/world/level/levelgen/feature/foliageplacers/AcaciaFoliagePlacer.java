package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class AcaciaFoliagePlacer extends FoliagePlacer {
   public static final Codec<AcaciaFoliagePlacer> CODEC = RecordCodecBuilder.create(var0 -> foliagePlacerParts(var0).apply(var0, AcaciaFoliagePlacer::new));

   public AcaciaFoliagePlacer(IntProvider var1, IntProvider var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected FoliagePlacerType<?> type() {
      return FoliagePlacerType.ACACIA_FOLIAGE_PLACER;
   }

   @Override
   protected void createFoliage(
      LevelSimulatedReader var1,
      BiConsumer<BlockPos, BlockState> var2,
      Random var3,
      TreeConfiguration var4,
      int var5,
      FoliagePlacer.FoliageAttachment var6,
      int var7,
      int var8,
      int var9
   ) {
      boolean â˜ƒ = â˜ƒ.doubleTrunk();
      BlockPos â˜ƒx = â˜ƒ.pos().above(â˜ƒ);
      this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ + â˜ƒ.radiusOffset(), -1 - â˜ƒ, â˜ƒ);
      this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ - 1, -â˜ƒ, â˜ƒ);
      this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ + â˜ƒ.radiusOffset() - 1, 0, â˜ƒ);
   }

   @Override
   public int foliageHeight(Random var1, int var2, TreeConfiguration var3) {
      return 0;
   }

   @Override
   protected boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      if (â˜ƒ == 0) {
         return (â˜ƒ > 1 || â˜ƒ > 1) && â˜ƒ != 0 && â˜ƒ != 0;
      } else {
         return â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ && â˜ƒ > 0;
      }
   }
}
