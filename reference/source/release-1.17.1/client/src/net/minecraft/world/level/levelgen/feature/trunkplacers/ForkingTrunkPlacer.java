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
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class ForkingTrunkPlacer extends TrunkPlacer {
   public static final Codec<ForkingTrunkPlacer> CODEC = RecordCodecBuilder.create(var0 -> trunkPlacerParts(var0).apply(var0, ForkingTrunkPlacer::new));

   public ForkingTrunkPlacer(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.FORKING_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.below(), â˜ƒ);
      List<FoliagePlacer.FoliageAttachment> â˜ƒ = Lists.<FoliagePlacer.FoliageAttachment>newArrayList();
      Direction â˜ƒx = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
      int â˜ƒxx = â˜ƒ - â˜ƒ.nextInt(4) - 1;
      int â˜ƒxxx = 3 - â˜ƒ.nextInt(3);
      BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();
      int â˜ƒxxxxx = â˜ƒ.getX();
      int â˜ƒxxxxxx = â˜ƒ.getZ();
      int â˜ƒxxxxxxx = 0;

      for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxx) {
         int â˜ƒxxxxxxxxx = â˜ƒ.getY() + â˜ƒxxxxxxxx;
         if (â˜ƒxxxxxxxx >= â˜ƒxx && â˜ƒxxx > 0) {
            â˜ƒxxxxx += â˜ƒx.getStepX();
            â˜ƒxxxxxx += â˜ƒx.getStepZ();
            --â˜ƒxxx;
         }

         if (placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxx.set(â˜ƒxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxx), â˜ƒ)) {
            â˜ƒxxxxxxx = â˜ƒxxxxxxxxx + 1;
         }
      }

      â˜ƒ.add(new FoliagePlacer.FoliageAttachment(new BlockPos(â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx), 1, false));
      â˜ƒxxxxx = â˜ƒ.getX();
      â˜ƒxxxxxx = â˜ƒ.getZ();
      Direction â˜ƒxxxxxxxx = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
      if (â˜ƒxxxxxxxx != â˜ƒx) {
         int â˜ƒxxxxxxxxx = â˜ƒxx - â˜ƒ.nextInt(2) - 1;
         int â˜ƒxxxxxxxxxx = 1 + â˜ƒ.nextInt(3);
         â˜ƒxxxxxxx = 0;

         for(int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx; â˜ƒxxxxxxxxxxx < â˜ƒ && â˜ƒxxxxxxxxxx > 0; --â˜ƒxxxxxxxxxx) {
            if (â˜ƒxxxxxxxxxxx >= 1) {
               int â˜ƒxxxxxxxxxxxx = â˜ƒ.getY() + â˜ƒxxxxxxxxxxx;
               â˜ƒxxxxx += â˜ƒxxxxxxxx.getStepX();
               â˜ƒxxxxxx += â˜ƒxxxxxxxx.getStepZ();
               if (placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxx.set(â˜ƒxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxx), â˜ƒ)) {
                  â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxx + 1;
               }
            }

            ++â˜ƒxxxxxxxxxxx;
         }

         if (â˜ƒxxxxxxx > 1) {
            â˜ƒ.add(new FoliagePlacer.FoliageAttachment(new BlockPos(â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx), 0, false));
         }
      }

      return â˜ƒ;
   }
}
