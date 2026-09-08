package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;

public class BasaltColumnsFeature extends Feature<ColumnFeatureConfiguration> {
   private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(
      Blocks.LAVA,
      Blocks.BEDROCK,
      Blocks.MAGMA_BLOCK,
      Blocks.SOUL_SAND,
      Blocks.NETHER_BRICKS,
      Blocks.NETHER_BRICK_FENCE,
      Blocks.NETHER_BRICK_STAIRS,
      Blocks.NETHER_WART,
      Blocks.CHEST,
      Blocks.SPAWNER
   );
   private static final int CLUSTERED_REACH = 5;
   private static final int CLUSTERED_SIZE = 50;
   private static final int UNCLUSTERED_REACH = 8;
   private static final int UNCLUSTERED_SIZE = 15;

   public BasaltColumnsFeature(Codec<ColumnFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<ColumnFeatureConfiguration> var1) {
      int â˜ƒ = â˜ƒ.chunkGenerator().getSeaLevel();
      BlockPos â˜ƒx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      Random â˜ƒxxx = â˜ƒ.random();
      ColumnFeatureConfiguration â˜ƒxxxx = â˜ƒ.config();
      if (!canPlaceAt(â˜ƒxx, â˜ƒ, â˜ƒx.mutable())) {
         return false;
      } else {
         int â˜ƒ = â˜ƒxxxx.height().sample(â˜ƒxxx);
         boolean â˜ƒx = â˜ƒxxx.nextFloat() < 0.9F;
         int â˜ƒxx = Math.min(â˜ƒ, â˜ƒx ? 5 : 8);
         int â˜ƒxxx = â˜ƒx ? 50 : 15;
         boolean â˜ƒxxxx = false;

         for(BlockPos â˜ƒxxxxx : BlockPos.randomBetweenClosed(
            â˜ƒxxx, â˜ƒxxx, â˜ƒx.getX() - â˜ƒxx, â˜ƒx.getY(), â˜ƒx.getZ() - â˜ƒxx, â˜ƒx.getX() + â˜ƒxx, â˜ƒx.getY(), â˜ƒx.getZ() + â˜ƒxx
         )) {
            int â˜ƒxxxxxx = â˜ƒ - â˜ƒxxxxx.distManhattan(â˜ƒx);
            if (â˜ƒxxxxxx >= 0) {
               â˜ƒxxxx |= this.placeColumn(â˜ƒxx, â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxx.reach().sample(â˜ƒxxx));
            }
         }

         return â˜ƒxxxx;
      }
   }

   private boolean placeColumn(LevelAccessor var1, int var2, BlockPos var3, int var4, int var5) {
      boolean â˜ƒ = false;

      for(BlockPos â˜ƒx : BlockPos.betweenClosed(â˜ƒ.getX() - â˜ƒ, â˜ƒ.getY(), â˜ƒ.getZ() - â˜ƒ, â˜ƒ.getX() + â˜ƒ, â˜ƒ.getY(), â˜ƒ.getZ() + â˜ƒ)) {
         int â˜ƒxx = â˜ƒx.distManhattan(â˜ƒ);
         BlockPos â˜ƒxxx = isAirOrLavaOcean(â˜ƒ, â˜ƒ, â˜ƒx) ? findSurface(â˜ƒ, â˜ƒ, â˜ƒx.mutable(), â˜ƒxx) : findAir(â˜ƒ, â˜ƒx.mutable(), â˜ƒxx);
         if (â˜ƒxxx != null) {
            int â˜ƒxxxx = â˜ƒ - â˜ƒxx / 2;

            for(BlockPos.MutableBlockPos â˜ƒxxxxx = â˜ƒxxx.mutable(); â˜ƒxxxx >= 0; --â˜ƒxxxx) {
               if (isAirOrLavaOcean(â˜ƒ, â˜ƒ, â˜ƒxxxxx)) {
                  this.setBlock(â˜ƒ, â˜ƒxxxxx, Blocks.BASALT.defaultBlockState());
                  â˜ƒxxxxx.move(Direction.UP);
                  â˜ƒ = true;
               } else {
                  if (!â˜ƒ.getBlockState(â˜ƒxxxxx).is(Blocks.BASALT)) {
                     break;
                  }

                  â˜ƒxxxxx.move(Direction.UP);
               }
            }
         }
      }

      return â˜ƒ;
   }

   @Nullable
   private static BlockPos findSurface(LevelAccessor var0, int var1, BlockPos.MutableBlockPos var2, int var3) {
      while(â˜ƒ.getY() > â˜ƒ.getMinBuildHeight() + 1 && â˜ƒ > 0) {
         --â˜ƒ;
         if (canPlaceAt(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return â˜ƒ;
         }

         â˜ƒ.move(Direction.DOWN);
      }

      return null;
   }

   private static boolean canPlaceAt(LevelAccessor var0, int var1, BlockPos.MutableBlockPos var2) {
      if (!isAirOrLavaOcean(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.move(Direction.DOWN));
         â˜ƒ.move(Direction.UP);
         return !â˜ƒ.isAir() && !CANNOT_PLACE_ON.contains(â˜ƒ.getBlock());
      }
   }

   @Nullable
   private static BlockPos findAir(LevelAccessor var0, BlockPos.MutableBlockPos var1, int var2) {
      while(â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight() && â˜ƒ > 0) {
         --â˜ƒ;
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (CANNOT_PLACE_ON.contains(â˜ƒ.getBlock())) {
            return null;
         }

         if (â˜ƒ.isAir()) {
            return â˜ƒ;
         }

         â˜ƒ.move(Direction.UP);
      }

      return null;
   }

   private static boolean isAirOrLavaOcean(LevelAccessor var0, int var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.LAVA) && â˜ƒ.getY() <= â˜ƒ;
   }
}
