package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;
import net.minecraft.util.Mth;

public abstract class DynamicGraphMinFixedPoint {
   private static final int NO_COMPUTED_LEVEL = 255;
   private final int levelCount;
   private final LongLinkedOpenHashSet[] queues;
   private final Long2ByteMap computedLevels;
   private int firstQueuedLevel;
   private volatile boolean hasWork;

   protected DynamicGraphMinFixedPoint(int var1, final int var2, final int var3) {
      if (â˜ƒ >= 254) {
         throw new IllegalArgumentException("Level count must be < 254.");
      } else {
         this.levelCount = â˜ƒ;
         this.queues = new LongLinkedOpenHashSet[â˜ƒ];

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
            this.queues[â˜ƒ] = new LongLinkedOpenHashSet(â˜ƒ, 0.5F) {
               @Override
               protected void rehash(int var1) {
                  if (â˜ƒ > â˜ƒ) {
                     super.rehash(â˜ƒ);
                  }
               }
            };
         }

         this.computedLevels = new Long2ByteOpenHashMap(â˜ƒ, 0.5F) {
            @Override
            protected void rehash(int var1) {
               if (â˜ƒ > â˜ƒ) {
                  super.rehash(â˜ƒ);
               }
            }
         };
         this.computedLevels.defaultReturnValue((byte)-1);
         this.firstQueuedLevel = â˜ƒ;
      }
   }

   private int getKey(int var1, int var2) {
      int â˜ƒ = â˜ƒ;
      if (â˜ƒ > â˜ƒ) {
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ > this.levelCount - 1) {
         â˜ƒ = this.levelCount - 1;
      }

      return â˜ƒ;
   }

   private void checkFirstQueuedLevel(int var1) {
      int â˜ƒ = this.firstQueuedLevel;
      this.firstQueuedLevel = â˜ƒ;

      for(int â˜ƒx = â˜ƒ + 1; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         if (!this.queues[â˜ƒx].isEmpty()) {
            this.firstQueuedLevel = â˜ƒx;
            break;
         }
      }
   }

   protected void removeFromQueue(long var1) {
      int â˜ƒ = this.computedLevels.get(â˜ƒ) & 255;
      if (â˜ƒ != 255) {
         int â˜ƒx = this.getLevel(â˜ƒ);
         int â˜ƒxx = this.getKey(â˜ƒx, â˜ƒ);
         this.dequeue(â˜ƒ, â˜ƒxx, this.levelCount, true);
         this.hasWork = this.firstQueuedLevel < this.levelCount;
      }
   }

   public void removeIf(LongPredicate var1) {
      LongList â˜ƒ = new LongArrayList();
      this.computedLevels.keySet().forEach(var2x -> {
         if (â˜ƒ.test(var2x)) {
            â˜ƒ.add(var2x);
         }
      });
      â˜ƒ.forEach(this::removeFromQueue);
   }

   private void dequeue(long var1, int var3, int var4, boolean var5) {
      if (â˜ƒ) {
         this.computedLevels.remove(â˜ƒ);
      }

      this.queues[â˜ƒ].remove(â˜ƒ);
      if (this.queues[â˜ƒ].isEmpty() && this.firstQueuedLevel == â˜ƒ) {
         this.checkFirstQueuedLevel(â˜ƒ);
      }
   }

   private void enqueue(long var1, int var3, int var4) {
      this.computedLevels.put(â˜ƒ, (byte)â˜ƒ);
      this.queues[â˜ƒ].add(â˜ƒ);
      if (this.firstQueuedLevel > â˜ƒ) {
         this.firstQueuedLevel = â˜ƒ;
      }
   }

   protected void checkNode(long var1) {
      this.checkEdge(â˜ƒ, â˜ƒ, this.levelCount - 1, false);
   }

   protected void checkEdge(long var1, long var3, int var5, boolean var6) {
      this.checkEdge(â˜ƒ, â˜ƒ, â˜ƒ, this.getLevel(â˜ƒ), this.computedLevels.get(â˜ƒ) & 255, â˜ƒ);
      this.hasWork = this.firstQueuedLevel < this.levelCount;
   }

   private void checkEdge(long var1, long var3, int var5, int var6, int var7, boolean var8) {
      if (!this.isSource(â˜ƒ)) {
         â˜ƒ = Mth.clamp(â˜ƒ, 0, this.levelCount - 1);
         â˜ƒ = Mth.clamp(â˜ƒ, 0, this.levelCount - 1);
         boolean â˜ƒ;
         if (â˜ƒ == 255) {
            â˜ƒ = true;
            â˜ƒ = â˜ƒ;
         } else {
            â˜ƒ = false;
         }

         int â˜ƒ;
         if (â˜ƒ) {
            â˜ƒ = Math.min(â˜ƒ, â˜ƒ);
         } else {
            â˜ƒ = Mth.clamp(this.getComputedLevel(â˜ƒ, â˜ƒ, â˜ƒ), 0, this.levelCount - 1);
         }

         int â˜ƒ = this.getKey(â˜ƒ, â˜ƒ);
         if (â˜ƒ != â˜ƒ) {
            int â˜ƒx = this.getKey(â˜ƒ, â˜ƒ);
            if (â˜ƒ != â˜ƒx && !â˜ƒ) {
               this.dequeue(â˜ƒ, â˜ƒ, â˜ƒx, false);
            }

            this.enqueue(â˜ƒ, â˜ƒ, â˜ƒx);
         } else if (!â˜ƒ) {
            this.dequeue(â˜ƒ, â˜ƒ, this.levelCount, true);
         }
      }
   }

   protected final void checkNeighbor(long var1, long var3, int var5, boolean var6) {
      int â˜ƒ = this.computedLevels.get(â˜ƒ) & 255;
      int â˜ƒx = Mth.clamp(this.computeLevelFromNeighbor(â˜ƒ, â˜ƒ, â˜ƒ), 0, this.levelCount - 1);
      if (â˜ƒ) {
         this.checkEdge(â˜ƒ, â˜ƒ, â˜ƒx, this.getLevel(â˜ƒ), â˜ƒ, true);
      } else {
         int â˜ƒ;
         boolean â˜ƒx;
         if (â˜ƒ == 255) {
            â˜ƒx = true;
            â˜ƒ = Mth.clamp(this.getLevel(â˜ƒ), 0, this.levelCount - 1);
         } else {
            â˜ƒ = â˜ƒ;
            â˜ƒx = false;
         }

         if (â˜ƒx == â˜ƒ) {
            this.checkEdge(â˜ƒ, â˜ƒ, this.levelCount - 1, â˜ƒx ? â˜ƒ : this.getLevel(â˜ƒ), â˜ƒ, false);
         }
      }
   }

   protected final boolean hasWork() {
      return this.hasWork;
   }

   protected final int runUpdates(int var1) {
      if (this.firstQueuedLevel >= this.levelCount) {
         return â˜ƒ;
      } else {
         while(this.firstQueuedLevel < this.levelCount && â˜ƒ > 0) {
            --â˜ƒ;
            LongLinkedOpenHashSet â˜ƒ = this.queues[this.firstQueuedLevel];
            long â˜ƒx = â˜ƒ.removeFirstLong();
            int â˜ƒxx = Mth.clamp(this.getLevel(â˜ƒx), 0, this.levelCount - 1);
            if (â˜ƒ.isEmpty()) {
               this.checkFirstQueuedLevel(this.levelCount);
            }

            int â˜ƒ = this.computedLevels.remove(â˜ƒx) & 255;
            if (â˜ƒ < â˜ƒxx) {
               this.setLevel(â˜ƒx, â˜ƒ);
               this.checkNeighborsAfterUpdate(â˜ƒx, â˜ƒ, true);
            } else if (â˜ƒ > â˜ƒxx) {
               this.enqueue(â˜ƒx, â˜ƒ, this.getKey(this.levelCount - 1, â˜ƒ));
               this.setLevel(â˜ƒx, this.levelCount - 1);
               this.checkNeighborsAfterUpdate(â˜ƒx, â˜ƒxx, false);
            }
         }

         this.hasWork = this.firstQueuedLevel < this.levelCount;
         return â˜ƒ;
      }
   }

   public int getQueueSize() {
      return this.computedLevels.size();
   }

   protected abstract boolean isSource(long var1);

   protected abstract int getComputedLevel(long var1, long var3, int var5);

   protected abstract void checkNeighborsAfterUpdate(long var1, int var3, boolean var4);

   protected abstract int getLevel(long var1);

   protected abstract void setLevel(long var1, int var3);

   protected abstract int computeLevelFromNeighbor(long var1, long var3, int var5);
}
