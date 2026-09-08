package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.GrowingPlantConfiguration;

public class GrowingPlantFeature extends Feature<GrowingPlantConfiguration> {
   public GrowingPlantFeature(Codec<GrowingPlantConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<GrowingPlantConfiguration> var1) {
      LevelAccessor â˜ƒ = â˜ƒ.level();
      GrowingPlantConfiguration â˜ƒx = â˜ƒ.config();
      Random â˜ƒxx = â˜ƒ.random();
      int â˜ƒxxx = ((IntProvider)â˜ƒx.heightDistribution.getRandomValue(â˜ƒxx).orElseThrow(IllegalStateException::new)).sample(â˜ƒxx);
      BlockPos.MutableBlockPos â˜ƒxxxx = â˜ƒ.origin().mutable();
      BlockPos.MutableBlockPos â˜ƒxxxxx = â˜ƒxxxx.mutable().move(â˜ƒx.direction);
      BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);

      for(int â˜ƒxxxxxxx = 1; â˜ƒxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxx) {
         BlockState â˜ƒxxxxxxxx = â˜ƒxxxxxx;
         â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
         if (â˜ƒxxxxxxxx.isAir() || â˜ƒx.allowWater && â˜ƒxxxxxxxx.getFluidState().is(FluidTags.WATER)) {
            if (â˜ƒxxxxxxx == â˜ƒxxx || !â˜ƒxxxxxx.isAir()) {
               â˜ƒ.setBlock(â˜ƒxxxx, â˜ƒx.headProvider.getState(â˜ƒxx, â˜ƒxxxx), 2);
               break;
            }

            â˜ƒ.setBlock(â˜ƒxxxx, â˜ƒx.bodyProvider.getState(â˜ƒxx, â˜ƒxxxx), 2);
         }

         â˜ƒxxxxx.move(â˜ƒx.direction);
         â˜ƒxxxx.move(â˜ƒx.direction);
      }

      return true;
   }
}
