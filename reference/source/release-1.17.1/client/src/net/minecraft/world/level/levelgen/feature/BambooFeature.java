package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class BambooFeature extends Feature<ProbabilityFeatureConfiguration> {
   private static final BlockState BAMBOO_TRUNK = Blocks.BAMBOO
      .defaultBlockState()
      .setValue(BambooBlock.AGE, Integer.valueOf(1))
      .setValue(BambooBlock.LEAVES, BambooLeaves.NONE)
      .setValue(BambooBlock.STAGE, Integer.valueOf(0));
   private static final BlockState BAMBOO_FINAL_LARGE = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.LARGE)
      .setValue(BambooBlock.STAGE, Integer.valueOf(1));
   private static final BlockState BAMBOO_TOP_LARGE = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.LARGE);
   private static final BlockState BAMBOO_TOP_SMALL = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.SMALL);

   public BambooFeature(Codec<ProbabilityFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> var1) {
      int â˜ƒ = 0;
      BlockPos â˜ƒx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      Random â˜ƒxxx = â˜ƒ.random();
      ProbabilityFeatureConfiguration â˜ƒxxxx = â˜ƒ.config();
      BlockPos.MutableBlockPos â˜ƒxxxxx = â˜ƒx.mutable();
      BlockPos.MutableBlockPos â˜ƒxxxxxx = â˜ƒx.mutable();
      if (â˜ƒxx.isEmptyBlock(â˜ƒxxxxx)) {
         if (Blocks.BAMBOO.defaultBlockState().canSurvive(â˜ƒxx, â˜ƒxxxxx)) {
            int â˜ƒxxxxxxx = â˜ƒxxx.nextInt(12) + 5;
            if (â˜ƒxxx.nextFloat() < â˜ƒxxxx.probability) {
               int â˜ƒxxxxxxxx = â˜ƒxxx.nextInt(4) + 1;

               for(int â˜ƒxxxxxxxxx = â˜ƒx.getX() - â˜ƒxxxxxxxx; â˜ƒxxxxxxxxx <= â˜ƒx.getX() + â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxx = â˜ƒx.getZ() - â˜ƒxxxxxxxx; â˜ƒxxxxxxxxxx <= â˜ƒx.getZ() + â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxxx) {
                     int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx - â˜ƒx.getX();
                     int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx - â˜ƒx.getZ();
                     if (â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx <= â˜ƒxxxxxxxx * â˜ƒxxxxxxxx) {
                        â˜ƒxxxxxx.set(â˜ƒxxxxxxxxx, â˜ƒxx.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx) - 1, â˜ƒxxxxxxxxxx);
                        if (isDirt(â˜ƒxx.getBlockState(â˜ƒxxxxxx))) {
                           â˜ƒxx.setBlock(â˜ƒxxxxxx, Blocks.PODZOL.defaultBlockState(), 2);
                        }
                     }
                  }
               }
            }

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxxxx && â˜ƒxx.isEmptyBlock(â˜ƒxxxxx); ++â˜ƒxxxxxxx) {
               â˜ƒxx.setBlock(â˜ƒxxxxx, BAMBOO_TRUNK, 2);
               â˜ƒxxxxx.move(Direction.UP, 1);
            }

            if (â˜ƒxxxxx.getY() - â˜ƒx.getY() >= 3) {
               â˜ƒxx.setBlock(â˜ƒxxxxx, BAMBOO_FINAL_LARGE, 2);
               â˜ƒxx.setBlock(â˜ƒxxxxx.move(Direction.DOWN, 1), BAMBOO_TOP_LARGE, 2);
               â˜ƒxx.setBlock(â˜ƒxxxxx.move(Direction.DOWN, 1), BAMBOO_TOP_SMALL, 2);
            }
         }

         ++â˜ƒ;
      }

      return â˜ƒ > 0;
   }
}
