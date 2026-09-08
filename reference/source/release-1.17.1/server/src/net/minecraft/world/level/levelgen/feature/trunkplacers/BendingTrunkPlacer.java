package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class BendingTrunkPlacer extends TrunkPlacer {
   public static final Codec<BendingTrunkPlacer> CODEC = RecordCodecBuilder.create(
      var0 -> trunkPlacerParts(var0)
            .and(
               var0.group(
                  ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height_for_leaves", 1).forGetter(var0x -> var0x.minHeightForLeaves),
                  IntProvider.codec(1, 64).fieldOf("bend_length").forGetter(var0x -> var0x.bendLength)
               )
            )
            .apply(var0, BendingTrunkPlacer::new)
   );
   private final int minHeightForLeaves;
   private final IntProvider bendLength;

   public BendingTrunkPlacer(int var1, int var2, int var3, int var4, IntProvider var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.minHeightForLeaves = â˜ƒ;
      this.bendLength = â˜ƒ;
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.BENDING_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      Direction â˜ƒ = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
      int â˜ƒx = â˜ƒ - 1;
      BlockPos.MutableBlockPos â˜ƒxx = â˜ƒ.mutable();
      BlockPos â˜ƒxxx = â˜ƒxx.below();
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒ);
      List<FoliagePlacer.FoliageAttachment> â˜ƒxxxx = Lists.<FoliagePlacer.FoliageAttachment>newArrayList();

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= â˜ƒx; ++â˜ƒxxxxx) {
         if (â˜ƒxxxxx + 1 >= â˜ƒx + â˜ƒ.nextInt(2)) {
            â˜ƒxx.move(â˜ƒ);
         }

         if (TreeFeature.validTreePos(â˜ƒ, â˜ƒxx)) {
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ);
         }

         if (â˜ƒxxxxx >= this.minHeightForLeaves) {
            â˜ƒxxxx.add(new FoliagePlacer.FoliageAttachment(â˜ƒxx.immutable(), 0, false));
         }

         â˜ƒxx.move(Direction.UP);
      }

      int â˜ƒxxxxx = this.bendLength.sample(â˜ƒ);

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxx) {
         if (TreeFeature.validTreePos(â˜ƒ, â˜ƒxx)) {
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ);
         }

         â˜ƒxxxx.add(new FoliagePlacer.FoliageAttachment(â˜ƒxx.immutable(), 0, false));
         â˜ƒxx.move(â˜ƒ);
      }

      return â˜ƒxxxx;
   }
}
