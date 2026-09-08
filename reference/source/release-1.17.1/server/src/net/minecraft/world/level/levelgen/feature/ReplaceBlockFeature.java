package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;

public class ReplaceBlockFeature extends Feature<ReplaceBlockConfiguration> {
   public ReplaceBlockFeature(Codec<ReplaceBlockConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<ReplaceBlockConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      ReplaceBlockConfiguration â˜ƒxx = â˜ƒ.config();

      for(OreConfiguration.TargetBlockState â˜ƒxxx : â˜ƒxx.targetStates) {
         if (â˜ƒxxx.target.test(â˜ƒ.getBlockState(â˜ƒx), â˜ƒ.random())) {
            â˜ƒ.setBlock(â˜ƒx, â˜ƒxxx.state, 2);
            break;
         }
      }

      return true;
   }
}
