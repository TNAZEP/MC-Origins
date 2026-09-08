package net.minecraft.world.level.levelgen;

import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class WorldGenerationContext {
   private final int minY;
   private final int height;

   public WorldGenerationContext(ChunkGenerator var1, LevelHeightAccessor var2) {
      this.minY = Math.max(â˜ƒ.getMinBuildHeight(), â˜ƒ.getMinY());
      this.height = Math.min(â˜ƒ.getHeight(), â˜ƒ.getGenDepth());
   }

   public int getMinGenY() {
      return this.minY;
   }

   public int getGenDepth() {
      return this.height;
   }
}
