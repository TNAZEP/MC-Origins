package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;

public class FillLayerFeature extends Feature<LayerConfiguration> {
   public FillLayerFeature(Codec<LayerConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<LayerConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      LayerConfiguration â˜ƒx = â˜ƒ.config();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
            int â˜ƒxxxxxx = â˜ƒ.getX() + â˜ƒxxxx;
            int â˜ƒxxxxxxx = â˜ƒ.getZ() + â˜ƒxxxxx;
            int â˜ƒxxxxxxxx = â˜ƒxx.getMinBuildHeight() + â˜ƒx.height;
            â˜ƒxxx.set(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
            if (â˜ƒxx.getBlockState(â˜ƒxxx).isAir()) {
               â˜ƒxx.setBlock(â˜ƒxxx, â˜ƒx.state, 2);
            }
         }
      }

      return true;
   }
}
