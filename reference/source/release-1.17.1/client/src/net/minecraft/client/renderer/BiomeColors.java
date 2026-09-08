package net.minecraft.client.renderer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;

public class BiomeColors {
   public static final ColorResolver GRASS_COLOR_RESOLVER = Biome::getGrassColor;
   public static final ColorResolver FOLIAGE_COLOR_RESOLVER = (var0, var1, var3) -> var0.getFoliageColor();
   public static final ColorResolver WATER_COLOR_RESOLVER = (var0, var1, var3) -> var0.getWaterColor();

   private static int getAverageColor(BlockAndTintGetter var0, BlockPos var1, ColorResolver var2) {
      return â˜ƒ.getBlockTint(â˜ƒ, â˜ƒ);
   }

   public static int getAverageGrassColor(BlockAndTintGetter var0, BlockPos var1) {
      return getAverageColor(â˜ƒ, â˜ƒ, GRASS_COLOR_RESOLVER);
   }

   public static int getAverageFoliageColor(BlockAndTintGetter var0, BlockPos var1) {
      return getAverageColor(â˜ƒ, â˜ƒ, FOLIAGE_COLOR_RESOLVER);
   }

   public static int getAverageWaterColor(BlockAndTintGetter var0, BlockPos var1) {
      return getAverageColor(â˜ƒ, â˜ƒ, WATER_COLOR_RESOLVER);
   }
}
