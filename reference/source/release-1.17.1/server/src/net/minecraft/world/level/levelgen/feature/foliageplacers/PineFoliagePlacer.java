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

public class PineFoliagePlacer extends FoliagePlacer {
   public static final Codec<PineFoliagePlacer> CODEC = RecordCodecBuilder.create(
      var0 -> foliagePlacerParts(var0).and(IntProvider.codec(0, 24).fieldOf("height").forGetter(var0x -> var0x.height)).apply(var0, PineFoliagePlacer::new)
   );
   private final IntProvider height;

   public PineFoliagePlacer(IntProvider var1, IntProvider var2, IntProvider var3) {
      super(â˜ƒ, â˜ƒ);
      this.height = â˜ƒ;
   }

   @Override
   protected FoliagePlacerType<?> type() {
      return FoliagePlacerType.PINE_FOLIAGE_PLACER;
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
      int â˜ƒ = 0;

      for(int â˜ƒx = â˜ƒ; â˜ƒx >= â˜ƒ - â˜ƒ; --â˜ƒx) {
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.pos(), â˜ƒ, â˜ƒx, â˜ƒ.doubleTrunk());
         if (â˜ƒ >= 1 && â˜ƒx == â˜ƒ - â˜ƒ + 1) {
            --â˜ƒ;
         } else if (â˜ƒ < â˜ƒ + â˜ƒ.radiusOffset()) {
            ++â˜ƒ;
         }
      }
   }

   @Override
   public int foliageRadius(Random var1, int var2) {
      return super.foliageRadius(â˜ƒ, â˜ƒ) + â˜ƒ.nextInt(Math.max(â˜ƒ + 1, 1));
   }

   @Override
   public int foliageHeight(Random var1, int var2, TreeConfiguration var3) {
      return this.height.sample(â˜ƒ);
   }

   @Override
   protected boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ && â˜ƒ > 0;
   }
}
