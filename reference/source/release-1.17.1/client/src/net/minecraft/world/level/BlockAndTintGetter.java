package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.lighting.LevelLightEngine;

public interface BlockAndTintGetter extends BlockGetter {
   float getShade(Direction var1, boolean var2);

   LevelLightEngine getLightEngine();

   int getBlockTint(BlockPos var1, ColorResolver var2);

   default int getBrightness(LightLayer var1, BlockPos var2) {
      return this.getLightEngine().getLayerListener(â˜ƒ).getLightValue(â˜ƒ);
   }

   default int getRawBrightness(BlockPos var1, int var2) {
      return this.getLightEngine().getRawBrightness(â˜ƒ, â˜ƒ);
   }

   default boolean canSeeSky(BlockPos var1) {
      return this.getBrightness(LightLayer.SKY, â˜ƒ) >= this.getMaxLightLevel();
   }
}
