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

public class RandomSpreadFoliagePlacer extends FoliagePlacer {
   public static final Codec<RandomSpreadFoliagePlacer> CODEC = RecordCodecBuilder.create(
      var0 -> foliagePlacerParts(var0)
            .and(
               var0.group(
                  IntProvider.codec(1, 512).fieldOf("foliage_height").forGetter(var0x -> var0x.foliageHeight),
                  Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter(var0x -> var0x.leafPlacementAttempts)
               )
            )
            .apply(var0, RandomSpreadFoliagePlacer::new)
   );
   private final IntProvider foliageHeight;
   private final int leafPlacementAttempts;

   public RandomSpreadFoliagePlacer(IntProvider var1, IntProvider var2, IntProvider var3, int var4) {
      super(â˜ƒ, â˜ƒ);
      this.foliageHeight = â˜ƒ;
      this.leafPlacementAttempts = â˜ƒ;
   }

   @Override
   protected FoliagePlacerType<?> type() {
      return FoliagePlacerType.RANDOM_SPREAD_FOLIAGE_PLACER;
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
      BlockPos â˜ƒ = â˜ƒ.pos();
      BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();

      for(int â˜ƒxx = 0; â˜ƒxx < this.leafPlacementAttempts; ++â˜ƒxx) {
         â˜ƒx.setWithOffset(â˜ƒ, â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ), â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ), â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ));
         tryPlaceLeaf(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }

   @Override
   public int foliageHeight(Random var1, int var2, TreeConfiguration var3) {
      return this.foliageHeight.sample(â˜ƒ);
   }

   @Override
   protected boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return false;
   }
}
