package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;

public class LevelLightEngine implements LightEventListener {
   public static final int MAX_SOURCE_LEVEL = 15;
   public static final int LIGHT_SECTION_PADDING = 1;
   protected final LevelHeightAccessor levelHeightAccessor;
   @Nullable
   private final LayerLightEngine<?, ?> blockEngine;
   @Nullable
   private final LayerLightEngine<?, ?> skyEngine;

   public LevelLightEngine(LightChunkGetter var1, boolean var2, boolean var3) {
      this.levelHeightAccessor = â˜ƒ.getLevel();
      this.blockEngine = â˜ƒ ? new BlockLightEngine(â˜ƒ) : null;
      this.skyEngine = â˜ƒ ? new SkyLightEngine(â˜ƒ) : null;
   }

   @Override
   public void checkBlock(BlockPos var1) {
      if (this.blockEngine != null) {
         this.blockEngine.checkBlock(â˜ƒ);
      }

      if (this.skyEngine != null) {
         this.skyEngine.checkBlock(â˜ƒ);
      }
   }

   @Override
   public void onBlockEmissionIncrease(BlockPos var1, int var2) {
      if (this.blockEngine != null) {
         this.blockEngine.onBlockEmissionIncrease(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean hasLightWork() {
      if (this.skyEngine != null && this.skyEngine.hasLightWork()) {
         return true;
      } else {
         return this.blockEngine != null && this.blockEngine.hasLightWork();
      }
   }

   @Override
   public int runUpdates(int var1, boolean var2, boolean var3) {
      if (this.blockEngine != null && this.skyEngine != null) {
         int â˜ƒ = â˜ƒ / 2;
         int â˜ƒx = this.blockEngine.runUpdates(â˜ƒ, â˜ƒ, â˜ƒ);
         int â˜ƒxx = â˜ƒ - â˜ƒ + â˜ƒx;
         int â˜ƒxxx = this.skyEngine.runUpdates(â˜ƒxx, â˜ƒ, â˜ƒ);
         return â˜ƒx == 0 && â˜ƒxxx > 0 ? this.blockEngine.runUpdates(â˜ƒxxx, â˜ƒ, â˜ƒ) : â˜ƒxxx;
      } else if (this.blockEngine != null) {
         return this.blockEngine.runUpdates(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return this.skyEngine != null ? this.skyEngine.runUpdates(â˜ƒ, â˜ƒ, â˜ƒ) : â˜ƒ;
      }
   }

   @Override
   public void updateSectionStatus(SectionPos var1, boolean var2) {
      if (this.blockEngine != null) {
         this.blockEngine.updateSectionStatus(â˜ƒ, â˜ƒ);
      }

      if (this.skyEngine != null) {
         this.skyEngine.updateSectionStatus(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void enableLightSources(ChunkPos var1, boolean var2) {
      if (this.blockEngine != null) {
         this.blockEngine.enableLightSources(â˜ƒ, â˜ƒ);
      }

      if (this.skyEngine != null) {
         this.skyEngine.enableLightSources(â˜ƒ, â˜ƒ);
      }
   }

   public LayerLightEventListener getLayerListener(LightLayer var1) {
      if (â˜ƒ == LightLayer.BLOCK) {
         return (LayerLightEventListener)(this.blockEngine == null ? LayerLightEventListener.DummyLightLayerEventListener.INSTANCE : this.blockEngine);
      } else {
         return (LayerLightEventListener)(this.skyEngine == null ? LayerLightEventListener.DummyLightLayerEventListener.INSTANCE : this.skyEngine);
      }
   }

   public String getDebugData(LightLayer var1, SectionPos var2) {
      if (â˜ƒ == LightLayer.BLOCK) {
         if (this.blockEngine != null) {
            return this.blockEngine.getDebugData(â˜ƒ.asLong());
         }
      } else if (this.skyEngine != null) {
         return this.skyEngine.getDebugData(â˜ƒ.asLong());
      }

      return "n/a";
   }

   public void queueSectionData(LightLayer var1, SectionPos var2, @Nullable DataLayer var3, boolean var4) {
      if (â˜ƒ == LightLayer.BLOCK) {
         if (this.blockEngine != null) {
            this.blockEngine.queueSectionData(â˜ƒ.asLong(), â˜ƒ, â˜ƒ);
         }
      } else if (this.skyEngine != null) {
         this.skyEngine.queueSectionData(â˜ƒ.asLong(), â˜ƒ, â˜ƒ);
      }
   }

   public void retainData(ChunkPos var1, boolean var2) {
      if (this.blockEngine != null) {
         this.blockEngine.retainData(â˜ƒ, â˜ƒ);
      }

      if (this.skyEngine != null) {
         this.skyEngine.retainData(â˜ƒ, â˜ƒ);
      }
   }

   public int getRawBrightness(BlockPos var1, int var2) {
      int â˜ƒ = this.skyEngine == null ? 0 : this.skyEngine.getLightValue(â˜ƒ) - â˜ƒ;
      int â˜ƒx = this.blockEngine == null ? 0 : this.blockEngine.getLightValue(â˜ƒ);
      return Math.max(â˜ƒx, â˜ƒ);
   }

   public int getLightSectionCount() {
      return this.levelHeightAccessor.getSectionsCount() + 2;
   }

   public int getMinLightSection() {
      return this.levelHeightAccessor.getMinSection() - 1;
   }

   public int getMaxLightSection() {
      return this.getMinLightSection() + this.getLightSectionCount();
   }
}
