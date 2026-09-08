package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;

public class GeodeFeature extends Feature<GeodeConfiguration> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public GeodeFeature(Codec<GeodeConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<GeodeConfiguration> var1) {
      GeodeConfiguration â˜ƒ = â˜ƒ.config();
      Random â˜ƒx = â˜ƒ.random();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxxx = â˜ƒ.level();
      int â˜ƒxxxx = â˜ƒ.minGenOffset;
      int â˜ƒxxxxx = â˜ƒ.maxGenOffset;
      List<Pair<BlockPos, Integer>> â˜ƒxxxxxx = Lists.<Pair<BlockPos, Integer>>newLinkedList();
      int â˜ƒxxxxxxx = â˜ƒ.distributionPoints.sample(â˜ƒx);
      WorldgenRandom â˜ƒxxxxxxxx = new WorldgenRandom(â˜ƒxxx.getSeed());
      NormalNoise â˜ƒxxxxxxxxx = NormalNoise.create(â˜ƒxxxxxxxx, -4, 1.0);
      List<BlockPos> â˜ƒxxxxxxxxxx = Lists.<BlockPos>newLinkedList();
      double â˜ƒxxxxxxxxxxx = (double)â˜ƒxxxxxxx / (double)â˜ƒ.outerWallDistance.getMaxValue();
      GeodeLayerSettings â˜ƒxxxxxxxxxxxx = â˜ƒ.geodeLayerSettings;
      GeodeBlockSettings â˜ƒxxxxxxxxxxxxx = â˜ƒ.geodeBlockSettings;
      GeodeCrackSettings â˜ƒxxxxxxxxxxxxxx = â˜ƒ.geodeCrackSettings;
      double â˜ƒxxxxxxxxxxxxxxx = 1.0 / Math.sqrt(â˜ƒxxxxxxxxxxxx.filling);
      double â˜ƒxxxxxxxxxxxxxxxx = 1.0 / Math.sqrt(â˜ƒxxxxxxxxxxxx.innerLayer + â˜ƒxxxxxxxxxxx);
      double â˜ƒxxxxxxxxxxxxxxxxx = 1.0 / Math.sqrt(â˜ƒxxxxxxxxxxxx.middleLayer + â˜ƒxxxxxxxxxxx);
      double â˜ƒxxxxxxxxxxxxxxxxxx = 1.0 / Math.sqrt(â˜ƒxxxxxxxxxxxx.outerLayer + â˜ƒxxxxxxxxxxx);
      double â˜ƒxxxxxxxxxxxxxxxxxxx = 1.0 / Math.sqrt(â˜ƒxxxxxxxxxxxxxx.baseCrackSize + â˜ƒx.nextDouble() / 2.0 + (â˜ƒxxxxxxx > 3 ? â˜ƒxxxxxxxxxxx : 0.0));
      boolean â˜ƒxxxxxxxxxxxxxxxxxxxx = (double)â˜ƒx.nextFloat() < â˜ƒxxxxxxxxxxxxxx.generateCrackChance;
      int â˜ƒxxxxxxxxxxxxxxxxxxxxx = 0;

      for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
         int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.outerWallDistance.sample(â˜ƒx);
         int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.outerWallDistance.sample(â˜ƒx);
         int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.outerWallDistance.sample(â˜ƒx);
         BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx);
         BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxx.getBlockState(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx);
         if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx.isAir() || â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx.is(BlockTags.GEODE_INVALID_BLOCKS)) {
            if (++â˜ƒxxxxxxxxxxxxxxxxxxxxx > â˜ƒ.invalidBlocksThreshold) {
               return false;
            }
         }

         â˜ƒxxxxxx.add(Pair.of(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒ.pointOffset.sample(â˜ƒx)));
      }

      if (â˜ƒxxxxxxxxxxxxxxxxxxxx) {
         int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒx.nextInt(4);
         int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx * 2 + 1;
         if (â˜ƒxxxxxxxxxxxxxxxxxxxxxx == 0) {
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, 7, 0));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, 5, 0));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, 1, 0));
         } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxx == 1) {
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(0, 7, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(0, 5, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(0, 1, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
         } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxx == 2) {
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, 7, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, 5, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, 1, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
         } else {
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(0, 7, 0));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(0, 5, 0));
            â˜ƒxxxxxxxxxx.add(â˜ƒxx.offset(0, 1, 0));
         }
      }

      List<BlockPos> â˜ƒxxxxxxxxxxxxxxxxxxxxxx = Lists.<BlockPos>newArrayList();
      Predicate<BlockState> â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = isReplaceable(â˜ƒ.geodeBlockSettings.cannotReplace);

      for(BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx : BlockPos.betweenClosed(â˜ƒxx.offset(â˜ƒxxxx, â˜ƒxxxx, â˜ƒxxxx), â˜ƒxx.offset(â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx))) {
         double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.getValue(
               (double)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.getX(), (double)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.getY(), (double)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.getZ()
            )
            * â˜ƒ.noiseMultiplier;
         double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0;
         double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0;

         for(Pair<BlockPos, Integer> â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx : â˜ƒxxxxxx) {
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx += Mth.fastInvSqrt(
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.distSqr(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFirst())
                     + (double)((Integer)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSecond()).intValue()
               )
               + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx;
         }

         for(BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxx) {
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx += Mth.fastInvSqrt(
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.distSqr(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx) + (double)â˜ƒxxxxxxxxxxxxxx.crackPointOffset
               )
               + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx;
         }

         if (!(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxxxxx)) {
            if (â˜ƒxxxxxxxxxxxxxxxxxxxx && â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxxxx && â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxx) {
               this.safeSetBlock(â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, Blocks.AIR.defaultBlockState(), â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);

               for(Direction â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx : DIRECTIONS) {
                  BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.relative(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                  FluidState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxx.getFluidState(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                  if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
                     â˜ƒxxx.getLiquidTicks().scheduleTick(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getType(), 0);
                  }
               }
            } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxx) {
               this.safeSetBlock(
                  â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx.fillingProvider.getState(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx), â˜ƒxxxxxxxxxxxxxxxxxxxxxxx
               );
            } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxx) {
               boolean â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)â˜ƒx.nextFloat() < â˜ƒ.useAlternateLayer0Chance;
               if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  this.safeSetBlock(
                     â˜ƒxxx,
                     â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
                     â˜ƒxxxxxxxxxxxxx.alternateInnerLayerProvider.getState(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx),
                     â˜ƒxxxxxxxxxxxxxxxxxxxxxxx
                  );
               } else {
                  this.safeSetBlock(
                     â˜ƒxxx,
                     â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
                     â˜ƒxxxxxxxxxxxxx.innerLayerProvider.getState(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx),
                     â˜ƒxxxxxxxxxxxxxxxxxxxxxxx
                  );
               }

               if ((!â˜ƒ.placementsRequireLayer0Alternate || â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx) && (double)â˜ƒx.nextFloat() < â˜ƒ.usePotentialPlacementsChance) {
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxx.add(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.immutable());
               }
            } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxx) {
               this.safeSetBlock(
                  â˜ƒxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx.middleLayerProvider.getState(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx),
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxxx
               );
            } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxxx) {
               this.safeSetBlock(
                  â˜ƒxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx.outerLayerProvider.getState(â˜ƒx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx),
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxxx
               );
            }
         }
      }

      List<BlockState> â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.innerPlacements;

      for(BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
         BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = Util.getRandom(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒx);

         for(Direction â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx : DIRECTIONS) {
            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.hasProperty(BlockStateProperties.FACING)) {
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.setValue(BlockStateProperties.FACING, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            }

            BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx.relative(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxx.getBlockState(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.hasProperty(BlockStateProperties.WATERLOGGED)) {
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.setValue(
                  BlockStateProperties.WATERLOGGED, Boolean.valueOf(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFluidState().isSource())
               );
            }

            if (BuddingAmethystBlock.canClusterGrowAtState(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
               this.safeSetBlock(â˜ƒxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);
               break;
            }
         }
      }

      return true;
   }
}
