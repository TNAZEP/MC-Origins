package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;

public class RootSystemFeature extends Feature<RootSystemConfiguration> {
   public RootSystemFeature(Codec<RootSystemConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<RootSystemConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      if (!â˜ƒ.getBlockState(â˜ƒx).isAir()) {
         return false;
      } else {
         Random â˜ƒ = â˜ƒ.random();
         BlockPos â˜ƒx = â˜ƒ.origin();
         RootSystemConfiguration â˜ƒxx = â˜ƒ.config();
         BlockPos.MutableBlockPos â˜ƒxxx = â˜ƒx.mutable();
         if (this.placeDirtAndTree(â˜ƒ, â˜ƒ.chunkGenerator(), â˜ƒxx, â˜ƒ, â˜ƒxxx, â˜ƒx)) {
            this.placeRoots(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxxx);
         }

         return true;
      }
   }

   private boolean spaceForTree(WorldGenLevel var1, RootSystemConfiguration var2, BlockPos var3) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      for(int â˜ƒx = 1; â˜ƒx <= â˜ƒ.requiredVerticalSpaceForTree; ++â˜ƒx) {
         â˜ƒ.move(Direction.UP);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
         if (!isAllowedTreeSpace(â˜ƒxx, â˜ƒx, â˜ƒ.allowedVerticalWaterForTree)) {
            return false;
         }
      }

      return true;
   }

   private static boolean isAllowedTreeSpace(BlockState var0, int var1, int var2) {
      return â˜ƒ.isAir() || â˜ƒ <= â˜ƒ && â˜ƒ.getFluidState().is(FluidTags.WATER);
   }

   private boolean placeDirtAndTree(
      WorldGenLevel var1, ChunkGenerator var2, RootSystemConfiguration var3, Random var4, BlockPos.MutableBlockPos var5, BlockPos var6
   ) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.rootColumnMaxHeight; ++â˜ƒxx) {
         â˜ƒ.move(Direction.UP);
         if (TreeFeature.validTreePos(â˜ƒ, â˜ƒ)) {
            if (this.spaceForTree(â˜ƒ, â˜ƒ, â˜ƒ)) {
               BlockPos â˜ƒxxx = â˜ƒ.below();
               if (â˜ƒ.getFluidState(â˜ƒxxx).is(FluidTags.LAVA) || !â˜ƒ.getBlockState(â˜ƒxxx).getMaterial().isSolid()) {
                  return false;
               }

               if (this.tryPlaceAzaleaTree(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
                  return true;
               }
            }
         } else {
            this.placeRootedDirt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
         }
      }

      return false;
   }

   private boolean tryPlaceAzaleaTree(WorldGenLevel var1, ChunkGenerator var2, RootSystemConfiguration var3, Random var4, BlockPos var5) {
      return ((ConfiguredFeature)â˜ƒ.treeFeature.get()).place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void placeRootedDirt(WorldGenLevel var1, RootSystemConfiguration var2, Random var3, int var4, int var5, BlockPos.MutableBlockPos var6) {
      int â˜ƒ = â˜ƒ.rootRadius;
      Tag<Block> â˜ƒx = BlockTags.getAllTags().getTag(â˜ƒ.rootReplaceable);
      Predicate<BlockState> â˜ƒxx = â˜ƒx == null ? var0 -> true : var1x -> var1x.is(â˜ƒ);

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.rootPlacementAttempts; ++â˜ƒxxx) {
         â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ), 0, â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ));
         if (â˜ƒxx.test(â˜ƒ.getBlockState(â˜ƒ))) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.rootStateProvider.getState(â˜ƒ, â˜ƒ), 2);
         }

         â˜ƒ.setX(â˜ƒ);
         â˜ƒ.setZ(â˜ƒ);
      }
   }

   private void placeRoots(WorldGenLevel var1, RootSystemConfiguration var2, Random var3, BlockPos var4, BlockPos.MutableBlockPos var5) {
      int â˜ƒ = â˜ƒ.hangingRootRadius;
      int â˜ƒx = â˜ƒ.hangingRootsVerticalSpan;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.hangingRootPlacementAttempts; ++â˜ƒxx) {
         â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ), â˜ƒ.nextInt(â˜ƒx) - â˜ƒ.nextInt(â˜ƒx), â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ));
         if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
            BlockState â˜ƒxxx = â˜ƒ.hangingRootStateProvider.getState(â˜ƒ, â˜ƒ);
            if (â˜ƒxxx.canSurvive(â˜ƒ, â˜ƒ) && â˜ƒ.getBlockState(â˜ƒ.above()).isFaceSturdy(â˜ƒ, â˜ƒ, Direction.DOWN)) {
               â˜ƒ.setBlock(â˜ƒ, â˜ƒxxx, 2);
            }
         }
      }
   }
}
