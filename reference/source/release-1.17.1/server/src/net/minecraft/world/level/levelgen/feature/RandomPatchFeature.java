package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class RandomPatchFeature extends Feature<RandomPatchConfiguration> {
   public RandomPatchFeature(Codec<RandomPatchConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<RandomPatchConfiguration> var1) {
      RandomPatchConfiguration â˜ƒx = â˜ƒ.config();
      Random â˜ƒxx = â˜ƒ.random();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxxxx = â˜ƒ.level();
      BlockState â˜ƒxxxxx = â˜ƒx.stateProvider.getState(â˜ƒxx, â˜ƒxxx);
      BlockPos â˜ƒ;
      if (â˜ƒx.project) {
         â˜ƒ = â˜ƒxxxx.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, â˜ƒxxx);
      } else {
         â˜ƒ = â˜ƒxxx;
      }

      int â˜ƒ = 0;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.tries; ++â˜ƒxx) {
         â˜ƒx.setWithOffset(
            â˜ƒ,
            â˜ƒxx.nextInt(â˜ƒx.xspread + 1) - â˜ƒxx.nextInt(â˜ƒx.xspread + 1),
            â˜ƒxx.nextInt(â˜ƒx.yspread + 1) - â˜ƒxx.nextInt(â˜ƒx.yspread + 1),
            â˜ƒxx.nextInt(â˜ƒx.zspread + 1) - â˜ƒxx.nextInt(â˜ƒx.zspread + 1)
         );
         BlockPos â˜ƒxxx = â˜ƒx.below();
         BlockState â˜ƒxxxx = â˜ƒxxxx.getBlockState(â˜ƒxxx);
         if ((â˜ƒxxxx.isEmptyBlock(â˜ƒx) || â˜ƒx.canReplace && â˜ƒxxxx.getBlockState(â˜ƒx).getMaterial().isReplaceable())
            && â˜ƒxxxxx.canSurvive(â˜ƒxxxx, â˜ƒx)
            && (â˜ƒx.whitelist.isEmpty() || â˜ƒx.whitelist.contains(â˜ƒxxxx.getBlock()))
            && !â˜ƒx.blacklist.contains(â˜ƒxxxx)
            && (
               !â˜ƒx.needWater
                  || â˜ƒxxxx.getFluidState(â˜ƒxxx.west()).is(FluidTags.WATER)
                  || â˜ƒxxxx.getFluidState(â˜ƒxxx.east()).is(FluidTags.WATER)
                  || â˜ƒxxxx.getFluidState(â˜ƒxxx.north()).is(FluidTags.WATER)
                  || â˜ƒxxxx.getFluidState(â˜ƒxxx.south()).is(FluidTags.WATER)
            )) {
            â˜ƒx.blockPlacer.place(â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx, â˜ƒxx);
            ++â˜ƒ;
         }
      }

      return â˜ƒ > 0;
   }
}
