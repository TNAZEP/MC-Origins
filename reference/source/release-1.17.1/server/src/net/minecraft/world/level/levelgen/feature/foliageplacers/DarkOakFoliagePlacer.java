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

public class DarkOakFoliagePlacer extends FoliagePlacer {
   public static final Codec<DarkOakFoliagePlacer> CODEC = RecordCodecBuilder.create(var0 -> foliagePlacerParts(var0).apply(var0, DarkOakFoliagePlacer::new));

   public DarkOakFoliagePlacer(IntProvider var1, IntProvider var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected FoliagePlacerType<?> type() {
      return FoliagePlacerType.DARK_OAK_FOLIAGE_PLACER;
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
      BlockPos â˜ƒ = â˜ƒ.pos().above(â˜ƒ);
      boolean â˜ƒx = â˜ƒ.doubleTrunk();
      if (â˜ƒx) {
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 2, -1, â˜ƒx);
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 3, 0, â˜ƒx);
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 2, 1, â˜ƒx);
         if (â˜ƒ.nextBoolean()) {
            this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 2, â˜ƒx);
         }
      } else {
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 2, -1, â˜ƒx);
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1, 0, â˜ƒx);
      }
   }

   @Override
   public int foliageHeight(Random var1, int var2, TreeConfiguration var3) {
      return 4;
   }

   @Override
   protected boolean shouldSkipLocationSigned(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return â˜ƒ != 0 || !â˜ƒ || â˜ƒ != -â˜ƒ && â˜ƒ < â˜ƒ || â˜ƒ != -â˜ƒ && â˜ƒ < â˜ƒ ? super.shouldSkipLocationSigned(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : true;
   }

   @Override
   protected boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      if (â˜ƒ == -1 && !â˜ƒ) {
         return â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ;
      } else if (â˜ƒ == 1) {
         return â˜ƒ + â˜ƒ > â˜ƒ * 2 - 2;
      } else {
         return false;
      }
   }
}
