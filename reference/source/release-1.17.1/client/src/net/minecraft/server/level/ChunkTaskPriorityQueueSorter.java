package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkTaskPriorityQueueSorter implements ChunkHolder.LevelChangeListener, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<ProcessorHandle<?>, ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>>> queues;
   private final Set<ProcessorHandle<?>> sleeping;
   private final ProcessorMailbox<StrictQueue.IntRunnable> mailbox;

   public ChunkTaskPriorityQueueSorter(List<ProcessorHandle<?>> var1, Executor var2, int var3) {
      this.queues = (Map)â˜ƒ.stream().collect(Collectors.toMap(Function.identity(), var1x -> new ChunkTaskPriorityQueue(var1x.name() + "_queue", â˜ƒ)));
      this.sleeping = Sets.<ProcessorHandle<?>>newHashSet(â˜ƒ);
      this.mailbox = new ProcessorMailbox<>(new StrictQueue.FixedPriorityQueue(4), â˜ƒ, "sorter");
   }

   public static <T> ChunkTaskPriorityQueueSorter.Message<T> message(Function<ProcessorHandle<Unit>, T> var0, long var1, IntSupplier var3) {
      return new ChunkTaskPriorityQueueSorter.Message<>(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static ChunkTaskPriorityQueueSorter.Message<Runnable> message(Runnable var0, long var1, IntSupplier var3) {
      return new ChunkTaskPriorityQueueSorter.Message(var1x -> () -> {
            â˜ƒ.run();
            var1x.tell(Unit.INSTANCE);
         }, â˜ƒ, â˜ƒ);
   }

   public static ChunkTaskPriorityQueueSorter.Message<Runnable> message(ChunkHolder var0, Runnable var1) {
      return message(â˜ƒ, â˜ƒ.getPos().toLong(), â˜ƒ::getQueueLevel);
   }

   public static <T> ChunkTaskPriorityQueueSorter.Message<T> message(ChunkHolder var0, Function<ProcessorHandle<Unit>, T> var1) {
      return message(â˜ƒ, â˜ƒ.getPos().toLong(), â˜ƒ::getQueueLevel);
   }

   public static ChunkTaskPriorityQueueSorter.Release release(Runnable var0, long var1, boolean var3) {
      return new ChunkTaskPriorityQueueSorter.Release(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public <T> ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<T>> getProcessor(ProcessorHandle<T> var1, boolean var2) {
      return (ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<T>>)this.mailbox.ask(var3 -> new StrictQueue.IntRunnable(0, () -> {
            this.getQueue(â˜ƒ);
            var3.tell(ProcessorHandle.of("chunk priority sorter around " + â˜ƒ.name(), var3x -> this.submit(â˜ƒ, var3x.task, var3x.pos, var3x.level, â˜ƒ)));
         })).join();
   }

   public ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> getReleaseProcessor(ProcessorHandle<Runnable> var1) {
      return (ProcessorHandle<ChunkTaskPriorityQueueSorter.Release>)this.mailbox
         .ask(
            var2 -> new StrictQueue.IntRunnable(
                  0,
                  () -> var2.tell(
                        ProcessorHandle.of("chunk priority sorter around " + â˜ƒ.name(), var2x -> this.release(â˜ƒ, var2x.pos, var2x.task, var2x.clearQueue))
                     )
               )
         )
         .join();
   }

   @Override
   public void onLevelChange(ChunkPos var1, IntSupplier var2, int var3, IntConsumer var4) {
      this.mailbox.tell(new StrictQueue.IntRunnable(0, () -> {
         int â˜ƒ = â˜ƒ.getAsInt();
         this.queues.values().forEach(var3x -> var3x.resortChunkTasks(â˜ƒ, â˜ƒ, â˜ƒ));
         â˜ƒ.accept(â˜ƒ);
      }));
   }

   private <T> void release(ProcessorHandle<T> var1, long var2, Runnable var4, boolean var5) {
      this.mailbox.tell(new StrictQueue.IntRunnable(1, () -> {
         ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> â˜ƒ = this.getQueue(â˜ƒ);
         â˜ƒ.release(â˜ƒ, â˜ƒ);
         if (this.sleeping.remove(â˜ƒ)) {
            this.pollTask(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.run();
      }));
   }

   private <T> void submit(ProcessorHandle<T> var1, Function<ProcessorHandle<Unit>, T> var2, long var3, IntSupplier var5, boolean var6) {
      this.mailbox.tell(new StrictQueue.IntRunnable(2, () -> {
         ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> â˜ƒ = this.getQueue(â˜ƒ);
         int â˜ƒx = â˜ƒ.getAsInt();
         â˜ƒ.submit(Optional.of(â˜ƒ), â˜ƒ, â˜ƒx);
         if (â˜ƒ) {
            â˜ƒ.submit(Optional.empty(), â˜ƒ, â˜ƒx);
         }

         if (this.sleeping.remove(â˜ƒ)) {
            this.pollTask(â˜ƒ, â˜ƒ);
         }
      }));
   }

   private <T> void pollTask(ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> var1, ProcessorHandle<T> var2) {
      this.mailbox.tell(new StrictQueue.IntRunnable(3, () -> {
         Stream<Either<Function<ProcessorHandle<Unit>, T>, Runnable>> â˜ƒ = â˜ƒ.pop();
         if (â˜ƒ == null) {
            this.sleeping.add(â˜ƒ);
         } else {
            Util.sequence((List)â˜ƒ.map(var1x -> var1x.map(â˜ƒ::ask, var0x -> {
                  var0x.run();
                  return CompletableFuture.completedFuture(Unit.INSTANCE);
               })).collect(Collectors.toList())).thenAccept(var3x -> this.pollTask(â˜ƒ, â˜ƒ));
         }
      }));
   }

   private <T> ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> getQueue(ProcessorHandle<T> var1) {
      ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>> â˜ƒ = (ChunkTaskPriorityQueue)this.queues.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw (IllegalArgumentException)Util.pauseInIde((T)(new IllegalArgumentException("No queue for: " + â˜ƒ)));
      } else {
         return â˜ƒ;
      }
   }

   @VisibleForTesting
   public String getDebugStatus() {
      return (String)this.queues
            .entrySet()
            .stream()
            .map(
               var0 -> ((ProcessorHandle)var0.getKey()).name()
                     + "=["
                     + (String)((ChunkTaskPriorityQueue)var0.getValue())
                        .getAcquired()
                        .stream()
                        .map(var0x -> var0x + ":" + new ChunkPos(var0x))
                        .collect(Collectors.joining(","))
                     + "]"
            )
            .collect(Collectors.joining(","))
         + ", s="
         + this.sleeping.size();
   }

   public void close() {
      this.queues.keySet().forEach(ProcessorHandle::close);
   }

   public static final class Message<T> {
      final Function<ProcessorHandle<Unit>, T> task;
      final long pos;
      final IntSupplier level;

      Message(Function<ProcessorHandle<Unit>, T> var1, long var2, IntSupplier var4) {
         this.task = â˜ƒ;
         this.pos = â˜ƒ;
         this.level = â˜ƒ;
      }
   }

   public static final class Release {
      final Runnable task;
      final long pos;
      final boolean clearQueue;

      Release(Runnable var1, long var2, boolean var4) {
         this.task = â˜ƒ;
         this.pos = â˜ƒ;
         this.clearQueue = â˜ƒ;
      }
   }
}
