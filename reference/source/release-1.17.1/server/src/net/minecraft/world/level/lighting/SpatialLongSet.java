package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.util.NoSuchElementException;
import net.minecraft.util.Mth;

public class SpatialLongSet extends LongLinkedOpenHashSet {
   private final SpatialLongSet.InternalMap map;

   public SpatialLongSet(int var1, float var2) {
      super(â˜ƒ, â˜ƒ);
      this.map = new SpatialLongSet.InternalMap(â˜ƒ / 64, â˜ƒ);
   }

   @Override
   public boolean add(long var1) {
      return this.map.addBit(â˜ƒ);
   }

   @Override
   public boolean rem(long var1) {
      return this.map.removeBit(â˜ƒ);
   }

   @Override
   public long removeFirstLong() {
      return this.map.removeFirstBit();
   }

   @Override
   public int size() {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   protected static class InternalMap extends Long2LongLinkedOpenHashMap {
      private static final int X_BITS = Mth.log2(60000000);
      private static final int Z_BITS = Mth.log2(60000000);
      private static final int Y_BITS = 64 - X_BITS - Z_BITS;
      private static final int Y_OFFSET = 0;
      private static final int Z_OFFSET = Y_BITS;
      private static final int X_OFFSET = Y_BITS + Z_BITS;
      private static final long OUTER_MASK = 3L << X_OFFSET | 3L | 3L << Z_OFFSET;
      private int lastPos = -1;
      private long lastOuterKey;
      private final int minSize;

      public InternalMap(int var1, float var2) {
         super(â˜ƒ, â˜ƒ);
         this.minSize = â˜ƒ;
      }

      static long getOuterKey(long var0) {
         return â˜ƒ & ~OUTER_MASK;
      }

      static int getInnerKey(long var0) {
         int â˜ƒ = (int)(â˜ƒ >>> X_OFFSET & 3L);
         int â˜ƒx = (int)(â˜ƒ >>> 0 & 3L);
         int â˜ƒxx = (int)(â˜ƒ >>> Z_OFFSET & 3L);
         return â˜ƒ << 4 | â˜ƒxx << 2 | â˜ƒx;
      }

      static long getFullKey(long var0, int var2) {
         â˜ƒ |= (long)(â˜ƒ >>> 4 & 3) << X_OFFSET;
         â˜ƒ |= (long)(â˜ƒ >>> 2 & 3) << Z_OFFSET;
         return â˜ƒ | (long)(â˜ƒ >>> 0 & 3) << 0;
      }

      public boolean addBit(long var1) {
         long â˜ƒx = getOuterKey(â˜ƒ);
         int â˜ƒxx = getInnerKey(â˜ƒ);
         long â˜ƒxxx = 1L << â˜ƒxx;
         int â˜ƒ;
         if (â˜ƒx == 0L) {
            if (this.containsNullKey) {
               return this.replaceBit(this.n, â˜ƒxxx);
            }

            this.containsNullKey = true;
            â˜ƒ = this.n;
         } else {
            if (this.lastPos != -1 && â˜ƒx == this.lastOuterKey) {
               return this.replaceBit(this.lastPos, â˜ƒxxx);
            }

            long[] â˜ƒ = this.key;
            â˜ƒ = (int)HashCommon.mix(â˜ƒx) & this.mask;

            for(long â˜ƒx = â˜ƒ[â˜ƒ]; â˜ƒx != 0L; â˜ƒx = â˜ƒ[â˜ƒ]) {
               if (â˜ƒx == â˜ƒx) {
                  this.lastPos = â˜ƒ;
                  this.lastOuterKey = â˜ƒx;
                  return this.replaceBit(â˜ƒ, â˜ƒxxx);
               }

               â˜ƒ = â˜ƒ + 1 & this.mask;
            }
         }

         this.key[â˜ƒ] = â˜ƒx;
         this.value[â˜ƒ] = â˜ƒxxx;
         if (this.size == 0) {
            this.first = this.last = â˜ƒ;
            this.link[â˜ƒ] = -1L;
         } else {
            this.link[this.last] ^= (this.link[this.last] ^ (long)â˜ƒ & 4294967295L) & 4294967295L;
            this.link[â˜ƒ] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
            this.last = â˜ƒ;
         }

         if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
         }

         return false;
      }

      private boolean replaceBit(int var1, long var2) {
         boolean â˜ƒ = (this.value[â˜ƒ] & â˜ƒ) != 0L;
         this.value[â˜ƒ] |= â˜ƒ;
         return â˜ƒ;
      }

      public boolean removeBit(long var1) {
         long â˜ƒ = getOuterKey(â˜ƒ);
         int â˜ƒx = getInnerKey(â˜ƒ);
         long â˜ƒxx = 1L << â˜ƒx;
         if (â˜ƒ == 0L) {
            return this.containsNullKey ? this.removeFromNullEntry(â˜ƒxx) : false;
         } else if (this.lastPos != -1 && â˜ƒ == this.lastOuterKey) {
            return this.removeFromEntry(this.lastPos, â˜ƒxx);
         } else {
            long[] â˜ƒ = this.key;
            int â˜ƒx = (int)HashCommon.mix(â˜ƒ) & this.mask;

            for(long â˜ƒxx = â˜ƒ[â˜ƒx]; â˜ƒxx != 0L; â˜ƒxx = â˜ƒ[â˜ƒx]) {
               if (â˜ƒ == â˜ƒxx) {
                  this.lastPos = â˜ƒx;
                  this.lastOuterKey = â˜ƒ;
                  return this.removeFromEntry(â˜ƒx, â˜ƒxx);
               }

               â˜ƒx = â˜ƒx + 1 & this.mask;
            }

            return false;
         }
      }

      private boolean removeFromNullEntry(long var1) {
         if ((this.value[this.n] & â˜ƒ) == 0L) {
            return false;
         } else {
            this.value[this.n] &= ~â˜ƒ;
            if (this.value[this.n] != 0L) {
               return true;
            } else {
               this.containsNullKey = false;
               --this.size;
               this.fixPointers(this.n);
               if (this.size < this.maxFill / 4 && this.n > 16) {
                  this.rehash(this.n / 2);
               }

               return true;
            }
         }
      }

      private boolean removeFromEntry(int var1, long var2) {
         if ((this.value[â˜ƒ] & â˜ƒ) == 0L) {
            return false;
         } else {
            this.value[â˜ƒ] &= ~â˜ƒ;
            if (this.value[â˜ƒ] != 0L) {
               return true;
            } else {
               this.lastPos = -1;
               --this.size;
               this.fixPointers(â˜ƒ);
               this.shiftKeys(â˜ƒ);
               if (this.size < this.maxFill / 4 && this.n > 16) {
                  this.rehash(this.n / 2);
               }

               return true;
            }
         }
      }

      public long removeFirstBit() {
         if (this.size == 0) {
            throw new NoSuchElementException();
         } else {
            int â˜ƒ = this.first;
            long â˜ƒx = this.key[â˜ƒ];
            int â˜ƒxx = Long.numberOfTrailingZeros(this.value[â˜ƒ]);
            this.value[â˜ƒ] &= ~(1L << â˜ƒxx);
            if (this.value[â˜ƒ] == 0L) {
               this.removeFirstLong();
               this.lastPos = -1;
            }

            return getFullKey(â˜ƒx, â˜ƒxx);
         }
      }

      @Override
      protected void rehash(int var1) {
         if (â˜ƒ > this.minSize) {
            super.rehash(â˜ƒ);
         }
      }
   }
}
