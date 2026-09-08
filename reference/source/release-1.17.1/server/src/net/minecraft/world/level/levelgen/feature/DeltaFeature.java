package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;

public class DeltaFeature extends Feature<DeltaFeatureConfiguration> {
   private static final ImmutableList<Block> CANNOT_REPLACE = ImmutableList.of(
      Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER
   );
   private static final Direction[] DIRECTIONS = Direction.values();
   private static final double RIM_SPAWN_CHANCE = 0.9;

   public DeltaFeature(Codec<DeltaFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<DeltaFeatureConfiguration> var1) {
      boolean â˜ƒ = false;
      Random â˜ƒx = â˜ƒ.random();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      DeltaFeatureConfiguration â˜ƒxxx = â˜ƒ.config();
      BlockPos â˜ƒxxxx = â˜ƒ.origin();
      boolean â˜ƒxxxxx = â˜ƒx.nextDouble() < 0.9;
      int â˜ƒxxxxxx = â˜ƒxxxxx ? â˜ƒxxx.rimSize().sample(â˜ƒx) : 0;
      int â˜ƒxxxxxxx = â˜ƒxxxxx ? â˜ƒxxx.rimSize().sample(â˜ƒx) : 0;
      boolean â˜ƒxxxxxxxx = â˜ƒxxxxx && â˜ƒxxxxxx != 0 && â˜ƒxxxxxxx != 0;
      int â˜ƒxxxxxxxxx = â˜ƒxxx.size().sample(â˜ƒx);
      int â˜ƒxxxxxxxxxx = â˜ƒxxx.size().sample(â˜ƒx);
      int â˜ƒxxxxxxxxxxx = Math.max(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);

      for(BlockPos â˜ƒxxxxxxxxxxxx : BlockPos.withinManhattan(â˜ƒxxxx, â˜ƒxxxxxxxxx, 0, â˜ƒxxxxxxxxxx)) {
         if (â˜ƒxxxxxxxxxxxx.distManhattan(â˜ƒxxxx) > â˜ƒxxxxxxxxxxx) {
            break;
         }

         if (isClear(â˜ƒxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxx)) {
            if (â˜ƒxxxxxxxx) {
               â˜ƒ = true;
               this.setBlock(â˜ƒxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxx.rim());
            }

            BlockPos â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.offset(â˜ƒxxxxxx, 0, â˜ƒxxxxxxx);
            if (isClear(â˜ƒxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxx)) {
               â˜ƒ = true;
               this.setBlock(â˜ƒxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxx.contents());
            }
         }
      }

      return â˜ƒ;
   }

   private static boolean isClear(LevelAccessor var0, BlockPos var1, DeltaFeatureConfiguration var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ.is(â˜ƒ.contents().getBlock())) {
         return false;
      } else if (CANNOT_REPLACE.contains(â˜ƒ.getBlock())) {
         return false;
      } else {
         for(Direction â˜ƒ : DIRECTIONS) {
            boolean â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ)).isAir();
            if (â˜ƒx && â˜ƒ != Direction.UP || !â˜ƒx && â˜ƒ == Direction.UP) {
               return false;
            }
         }

         return true;
      }
   }
}
