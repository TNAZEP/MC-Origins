package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;

public class GlowLichenFeature extends Feature<GlowLichenConfiguration> {
   public GlowLichenFeature(Codec<GlowLichenConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<GlowLichenConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      GlowLichenConfiguration â˜ƒxxx = â˜ƒ.config();
      if (!isAirOrWater(â˜ƒ.getBlockState(â˜ƒx))) {
         return false;
      } else {
         List<Direction> â˜ƒ = getShuffledDirections(â˜ƒxxx, â˜ƒxx);
         if (placeGlowLichenIfPossible(â˜ƒ, â˜ƒx, â˜ƒ.getBlockState(â˜ƒx), â˜ƒxxx, â˜ƒxx, â˜ƒ)) {
            return true;
         } else {
            BlockPos.MutableBlockPos â˜ƒ = â˜ƒx.mutable();

            for(Direction â˜ƒx : â˜ƒ) {
               â˜ƒ.set(â˜ƒx);
               List<Direction> â˜ƒxx = getShuffledDirectionsExcept(â˜ƒxxx, â˜ƒxx, â˜ƒx.getOpposite());

               for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxxx.searchRange; ++â˜ƒxxx) {
                  â˜ƒ.setWithOffset(â˜ƒx, â˜ƒx);
                  BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒ);
                  if (!isAirOrWater(â˜ƒxxxx) && !â˜ƒxxxx.is(Blocks.GLOW_LICHEN)) {
                     break;
                  }

                  if (placeGlowLichenIfPossible(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxx, â˜ƒxx, â˜ƒxx)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public static boolean placeGlowLichenIfPossible(
      WorldGenLevel var0, BlockPos var1, BlockState var2, GlowLichenConfiguration var3, Random var4, List<Direction> var5
   ) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      for(Direction â˜ƒx : â˜ƒ) {
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.setWithOffset(â˜ƒ, â˜ƒx));
         if (â˜ƒ.canBePlacedOn(â˜ƒxx.getBlock())) {
            GlowLichenBlock â˜ƒxxx = (GlowLichenBlock)Blocks.GLOW_LICHEN;
            BlockState â˜ƒxxxx = â˜ƒxxx.getStateForPlacement(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            if (â˜ƒxxxx == null) {
               return false;
            }

            â˜ƒ.setBlock(â˜ƒ, â˜ƒxxxx, 3);
            â˜ƒ.getChunk(â˜ƒ).markPosForPostprocessing(â˜ƒ);
            if (â˜ƒ.nextFloat() < â˜ƒ.chanceOfSpreading) {
               â˜ƒxxx.spreadFromFaceTowardRandomDirection(â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, true);
            }

            return true;
         }
      }

      return false;
   }

   public static List<Direction> getShuffledDirections(GlowLichenConfiguration var0, Random var1) {
      List<Direction> â˜ƒ = Lists.<Direction>newArrayList(â˜ƒ.validDirections);
      Collections.shuffle(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static List<Direction> getShuffledDirectionsExcept(GlowLichenConfiguration var0, Random var1, Direction var2) {
      List<Direction> â˜ƒ = (List)â˜ƒ.validDirections.stream().filter(var1x -> var1x != â˜ƒ).collect(Collectors.toList());
      Collections.shuffle(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   private static boolean isAirOrWater(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.WATER);
   }
}
