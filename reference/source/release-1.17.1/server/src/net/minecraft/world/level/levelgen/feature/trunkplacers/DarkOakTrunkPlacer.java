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
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class DarkOakTrunkPlacer extends TrunkPlacer {
   public static final Codec<DarkOakTrunkPlacer> CODEC = RecordCodecBuilder.create(var0 -> trunkPlacerParts(var0).apply(var0, DarkOakTrunkPlacer::new));

   public DarkOakTrunkPlacer(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.DARK_OAK_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      List<FoliagePlacer.FoliageAttachment> â˜ƒ = Lists.<FoliagePlacer.FoliageAttachment>newArrayList();
      BlockPos â˜ƒx = â˜ƒ.below();
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx.east(), â˜ƒ);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx.south(), â˜ƒ);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx.south().east(), â˜ƒ);
      Direction â˜ƒxx = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
      int â˜ƒxxx = â˜ƒ - â˜ƒ.nextInt(4);
      int â˜ƒxxxx = 2 - â˜ƒ.nextInt(3);
      int â˜ƒxxxxx = â˜ƒ.getX();
      int â˜ƒxxxxxx = â˜ƒ.getY();
      int â˜ƒxxxxxxx = â˜ƒ.getZ();
      int â˜ƒxxxxxxxx = â˜ƒxxxxx;
      int â˜ƒxxxxxxxxx = â˜ƒxxxxxxx;
      int â˜ƒxxxxxxxxxx = â˜ƒxxxxxx + â˜ƒ - 1;

      for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxxxx) {
         if (â˜ƒxxxxxxxxxxx >= â˜ƒxxx && â˜ƒxxxx > 0) {
            â˜ƒxxxxxxxx += â˜ƒxx.getStepX();
            â˜ƒxxxxxxxxx += â˜ƒxx.getStepZ();
            --â˜ƒxxxx;
         }

         int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx + â˜ƒxxxxxxxxxxx;
         BlockPos â˜ƒxxxxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxx);
         if (TreeFeature.isAirOrLeaves(â˜ƒ, â˜ƒxxxxxxxxxxxxx)) {
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxx, â˜ƒ);
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxx.east(), â˜ƒ);
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxx.south(), â˜ƒ);
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxx.east().south(), â˜ƒ);
         }
      }

      â˜ƒ.add(new FoliagePlacer.FoliageAttachment(new BlockPos(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx), 0, true));

      for(int â˜ƒxxxxxxxxxxx = -1; â˜ƒxxxxxxxxxxx <= 2; ++â˜ƒxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxx = -1; â˜ƒxxxxxxxxxxxx <= 2; ++â˜ƒxxxxxxxxxxxx) {
            if ((â˜ƒxxxxxxxxxxx < 0 || â˜ƒxxxxxxxxxxx > 1 || â˜ƒxxxxxxxxxxxx < 0 || â˜ƒxxxxxxxxxxxx > 1) && â˜ƒ.nextInt(3) <= 0) {
               int â˜ƒxxxxxxxxxxxxx = â˜ƒ.nextInt(3) + 2;

               for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxx) {
                  placeLog(â˜ƒ, â˜ƒ, â˜ƒ, new BlockPos(â˜ƒxxxxx + â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxx - 1, â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxx), â˜ƒ);
               }

               â˜ƒ.add(new FoliagePlacer.FoliageAttachment(new BlockPos(â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxxxx), 0, false));
            }
         }
      }

      return â˜ƒ;
   }
}
