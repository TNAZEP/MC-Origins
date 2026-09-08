package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.level.chunk.DataLayer;

public abstract class DataLayerStorageMap<M extends DataLayerStorageMap<M>> {
   private static final int CACHE_SIZE = 2;
   private final long[] lastSectionKeys = new long[2];
   private final DataLayer[] lastSections = new DataLayer[2];
   private boolean cacheEnabled;
   protected final Long2ObjectOpenHashMap<DataLayer> map;

   protected DataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> var1) {
      this.map = â˜ƒ;
      this.clearCache();
      this.cacheEnabled = true;
   }

   public abstract M copy();

   public void copyDataLayer(long var1) {
      this.map.put(â˜ƒ, this.map.get(â˜ƒ).copy());
      this.clearCache();
   }

   public boolean hasLayer(long var1) {
      return this.map.containsKey(â˜ƒ);
   }

   @Nullable
   public DataLayer getLayer(long var1) {
      if (this.cacheEnabled) {
         for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
            if (â˜ƒ == this.lastSectionKeys[â˜ƒ]) {
               return this.lastSections[â˜ƒ];
            }
         }
      }

      DataLayer â˜ƒ = this.map.get(â˜ƒ);
      if (â˜ƒ == null) {
         return null;
      } else {
         if (this.cacheEnabled) {
            for(int â˜ƒ = 1; â˜ƒ > 0; --â˜ƒ) {
               this.lastSectionKeys[â˜ƒ] = this.lastSectionKeys[â˜ƒ - 1];
               this.lastSections[â˜ƒ] = this.lastSections[â˜ƒ - 1];
            }

            this.lastSectionKeys[0] = â˜ƒ;
            this.lastSections[0] = â˜ƒ;
         }

         return â˜ƒ;
      }
   }

   @Nullable
   public DataLayer removeLayer(long var1) {
      return this.map.remove(â˜ƒ);
   }

   public void setLayer(long var1, DataLayer var3) {
      this.map.put(â˜ƒ, â˜ƒ);
   }

   public void clearCache() {
      for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
         this.lastSectionKeys[â˜ƒ] = Long.MAX_VALUE;
         this.lastSections[â˜ƒ] = null;
      }
   }

   public void disableCache() {
      this.cacheEnabled = false;
   }
}
