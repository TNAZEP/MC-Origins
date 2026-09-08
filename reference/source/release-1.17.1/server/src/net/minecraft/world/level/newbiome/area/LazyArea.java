package net.minecraft.world.level.newbiome.area;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.newbiome.layer.traits.PixelTransformer;

public final class LazyArea implements Area {
   private final PixelTransformer transformer;
   private final Long2IntLinkedOpenHashMap cache;
   private final int maxCache;

   public LazyArea(Long2IntLinkedOpenHashMap var1, int var2, PixelTransformer var3) {
      this.cache = â˜ƒ;
      this.maxCache = â˜ƒ;
      this.transformer = â˜ƒ;
   }

   @Override
   public int get(int var1, int var2) {
      long â˜ƒ = ChunkPos.asLong(â˜ƒ, â˜ƒ);
      synchronized(this.cache) {
         int â˜ƒx = this.cache.get(â˜ƒ);
         if (â˜ƒx != Integer.MIN_VALUE) {
            return â˜ƒx;
         } else {
            int â˜ƒx = this.transformer.apply(â˜ƒ, â˜ƒ);
            this.cache.put(â˜ƒ, â˜ƒx);
            if (this.cache.size() > this.maxCache) {
               for(int â˜ƒxx = 0; â˜ƒxx < this.maxCache / 16; ++â˜ƒxx) {
                  this.cache.removeFirstInt();
               }
            }

            return â˜ƒx;
         }
      }
   }

   public int getMaxCache() {
      return this.maxCache;
   }
}
