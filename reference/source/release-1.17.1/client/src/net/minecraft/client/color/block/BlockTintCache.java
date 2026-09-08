package net.minecraft.client.color.block;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public class BlockTintCache {
   private static final int MAX_CACHE_ENTRIES = 256;
   private final ThreadLocal<BlockTintCache.LatestCacheInfo> latestChunkOnThread = ThreadLocal.withInitial(BlockTintCache.LatestCacheInfo::new);
   private final Long2ObjectLinkedOpenHashMap<int[]> cache = new Long2ObjectLinkedOpenHashMap<>(256, 0.25F);
   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

   public int getColor(BlockPos var1, IntSupplier var2) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
      BlockTintCache.LatestCacheInfo â˜ƒxx = (BlockTintCache.LatestCacheInfo)this.latestChunkOnThread.get();
      if (â˜ƒxx.x != â˜ƒ || â˜ƒxx.z != â˜ƒx) {
         â˜ƒxx.x = â˜ƒ;
         â˜ƒxx.z = â˜ƒx;
         â˜ƒxx.cache = this.findOrCreateChunkCache(â˜ƒ, â˜ƒx);
      }

      int â˜ƒ = â˜ƒ.getX() & 15;
      int â˜ƒx = â˜ƒ.getZ() & 15;
      int â˜ƒxx = â˜ƒx << 4 | â˜ƒ;
      int â˜ƒxxx = â˜ƒxx.cache[â˜ƒxx];
      if (â˜ƒxxx != -1) {
         return â˜ƒxxx;
      } else {
         int â˜ƒ = â˜ƒ.getAsInt();
         â˜ƒxx.cache[â˜ƒxx] = â˜ƒ;
         return â˜ƒ;
      }
   }

   public void invalidateForChunk(int var1, int var2) {
      try {
         this.lock.writeLock().lock();

         for(int â˜ƒ = -1; â˜ƒ <= 1; ++â˜ƒ) {
            for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
               long â˜ƒxx = ChunkPos.asLong(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒx);
               this.cache.remove(â˜ƒxx);
            }
         }
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   public void invalidateAll() {
      try {
         this.lock.writeLock().lock();
         this.cache.clear();
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   private int[] findOrCreateChunkCache(int var1, int var2) {
      long â˜ƒ = ChunkPos.asLong(â˜ƒ, â˜ƒ);
      this.lock.readLock().lock();

      int[] â˜ƒ;
      try {
         â˜ƒ = (int[])this.cache.get(â˜ƒ);
      } finally {
         this.lock.readLock().unlock();
      }

      if (â˜ƒ != null) {
         return â˜ƒ;
      } else {
         int[] â˜ƒx = new int[256];
         Arrays.fill(â˜ƒx, -1);

         try {
            this.lock.writeLock().lock();
            if (this.cache.size() >= 256) {
               this.cache.removeFirst();
            }

            this.cache.put(â˜ƒ, â˜ƒx);
         } finally {
            this.lock.writeLock().unlock();
         }

         return â˜ƒx;
      }
   }

   static class LatestCacheInfo {
      public int x = Integer.MIN_VALUE;
      public int z = Integer.MIN_VALUE;
      public int[] cache;

      private LatestCacheInfo() {
      }
   }
}
