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

public class SpruceFoliagePlacer extends FoliagePlacer {
   public static final Codec<SpruceFoliagePlacer> CODEC = RecordCodecBuilder.create(
      var0 -> foliagePlacerParts(var0)
            .and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(var0x -> var0x.trunkHeight))
            .apply(var0, SpruceFoliagePlacer::new)
   );
   private final IntProvider trunkHeight;

   public SpruceFoliagePlacer(IntProvider var1, IntProvider var2, IntProvider var3) {
      super(â˜ƒ, â˜ƒ);
      this.trunkHeight = â˜ƒ;
   }

   @Override
   protected FoliagePlacerType<?> type() {
      return FoliagePlacerType.SPRUCE_FOLIAGE_PLACER;
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
      int â˜ƒx = â˜ƒ.nextInt(2);
      int â˜ƒxx = 1;
      int â˜ƒxxx = 0;

      for(int â˜ƒxxxx = â˜ƒ; â˜ƒxxxx >= -â˜ƒ; --â˜ƒxxxx) {
         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxxx, â˜ƒ.doubleTrunk());
         if (â˜ƒx >= â˜ƒxx) {
            â˜ƒx = â˜ƒxxx;
            â˜ƒxxx = 1;
            â˜ƒxx = Math.min(â˜ƒxx + 1, â˜ƒ + â˜ƒ.radiusOffset());
         } else {
            ++â˜ƒx;
         }
      }
   }

   @Override
   public int foliageHeight(Random var1, int var2, TreeConfiguration var3) {
      return Math.max(4, â˜ƒ - this.trunkHeight.sample(â˜ƒ));
   }

   @Override
   protected boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      return â˜ƒ == â˜ƒ && â˜ƒ == â˜ƒ && â˜ƒ > 0;
   }
}
