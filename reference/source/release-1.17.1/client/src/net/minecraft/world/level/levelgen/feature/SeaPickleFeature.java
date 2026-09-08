package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;

public class SeaPickleFeature extends Feature<CountConfiguration> {
   public SeaPickleFeature(Codec<CountConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<CountConfiguration> var1) {
      int â˜ƒ = 0;
      Random â˜ƒx = â˜ƒ.random();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      int â˜ƒxxxx = â˜ƒ.config().count().sample(â˜ƒx);

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx; ++â˜ƒxxxxx) {
         int â˜ƒxxxxxx = â˜ƒx.nextInt(8) - â˜ƒx.nextInt(8);
         int â˜ƒxxxxxxx = â˜ƒx.nextInt(8) - â˜ƒx.nextInt(8);
         int â˜ƒxxxxxxxx = â˜ƒxx.getHeight(Heightmap.Types.OCEAN_FLOOR, â˜ƒxxx.getX() + â˜ƒxxxxxx, â˜ƒxxx.getZ() + â˜ƒxxxxxxx);
         BlockPos â˜ƒxxxxxxxxx = new BlockPos(â˜ƒxxx.getX() + â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxx.getZ() + â˜ƒxxxxxxx);
         BlockState â˜ƒxxxxxxxxxx = Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.PICKLES, Integer.valueOf(â˜ƒx.nextInt(4) + 1));
         if (â˜ƒxx.getBlockState(â˜ƒxxxxxxxxx).is(Blocks.WATER) && â˜ƒxxxxxxxxxx.canSurvive(â˜ƒxx, â˜ƒxxxxxxxxx)) {
            â˜ƒxx.setBlock(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, 2);
            ++â˜ƒ;
         }
      }

      return â˜ƒ > 0;
   }
}
