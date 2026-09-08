package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkPos;

public class ChunkTaskPriorityQueue<T> {
   public static final int PRIORITY_LEVEL_COUNT = ChunkMap.MAX_CHUNK_DISTANCE + 2;
   private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> taskQueue = (List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>>)IntStream.range(
         0, PRIORITY_LEVEL_COUNT
      )
      .mapToObj(var0 -> new Long2ObjectLinkedOpenHashMap())
      .collect(Collectors.toList());
   private volatile int firstQueue = PRIORITY_LEVEL_COUNT;
   private final String name;
   private final LongSet acquired = new LongOpenHashSet();
   private final int maxTasks;

   public ChunkTaskPriorityQueue(String var1, int var2) {
      this.name = â˜ƒ;
      this.maxTasks = â˜ƒ;
   }

   protected void resortChunkTasks(int var1, ChunkPos var2, int var3) {
      if (â˜ƒ < PRIORITY_LEVEL_COUNT) {
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> â˜ƒ = (Long2ObjectLinkedOpenHashMap)this.taskQueue.get(â˜ƒ);
         List<Optional<T>> â˜ƒx = (List)â˜ƒ.remove(â˜ƒ.toLong());
         if (â˜ƒ == this.firstQueue) {
            while(this.firstQueue < PRIORITY_LEVEL_COUNT && ((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(this.firstQueue)).isEmpty()) {
               ++this.firstQueue;
            }
         }

         if (â˜ƒx != null && !â˜ƒx.isEmpty()) {
            ((List)((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(â˜ƒ)).computeIfAbsent(â˜ƒ.toLong(), var0 -> Lists.newArrayList())).addAll(â˜ƒx);
            this.firstQueue = Math.min(this.firstQueue, â˜ƒ);
         }
      }
   }

   protected void submit(Optional<T> var1, long var2, int var4) {
      ((List)((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(â˜ƒ)).computeIfAbsent(â˜ƒ, var0 -> Lists.newArrayList())).add(â˜ƒ);
      this.firstQueue = Math.min(this.firstQueue, â˜ƒ);
   }

   protected void release(long var1, boolean var3) {
      for(Long2ObjectLinkedOpenHashMap<List<Optional<T>>> â˜ƒ : this.taskQueue) {
         List<Optional<T>> â˜ƒx = (List)â˜ƒ.get(â˜ƒ);
         if (â˜ƒx != null) {
            if (â˜ƒ) {
               â˜ƒx.clear();
            } else {
               â˜ƒx.removeIf(var0 -> !var0.isPresent());
            }

            if (â˜ƒx.isEmpty()) {
               â˜ƒ.remove(â˜ƒ);
            }
         }
      }

      while(this.firstQueue < PRIORITY_LEVEL_COUNT && ((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(this.firstQueue)).isEmpty()) {
         ++this.firstQueue;
      }

      this.acquired.remove(â˜ƒ);
   }

   private Runnable acquire(long var1) {
      return () -> this.acquired.add(â˜ƒ);
   }

   @Nullable
   public Stream<Either<T, Runnable>> pop() {
      if (this.acquired.size() >= this.maxTasks) {
         return null;
      } else if (this.firstQueue >= PRIORITY_LEVEL_COUNT) {
         return null;
      } else {
         int â˜ƒ = this.firstQueue;
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> â˜ƒx = (Long2ObjectLinkedOpenHashMap)this.taskQueue.get(â˜ƒ);
         long â˜ƒxx = â˜ƒx.firstLongKey();
         List<Optional<T>> â˜ƒxxx = (List)â˜ƒx.removeFirst();

         while(this.firstQueue < PRIORITY_LEVEL_COUNT && ((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(this.firstQueue)).isEmpty()) {
            ++this.firstQueue;
         }

         return â˜ƒxxx.stream().map(var3x -> (Either)var3x.map(Either::left).orElseGet(() -> Either.right(this.acquire(â˜ƒ))));
      }
   }

   public String toString() {
      return this.name + " " + this.firstQueue + "...";
   }

   @VisibleForTesting
   LongSet getAcquired() {
      return new LongOpenHashSet(this.acquired);
   }
}
