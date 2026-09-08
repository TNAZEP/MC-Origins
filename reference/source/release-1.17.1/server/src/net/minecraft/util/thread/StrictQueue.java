package net.minecraft.util.thread;

import com.google.common.collect.Queues;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public interface StrictQueue<T, F> {
   @Nullable
   F pop();

   boolean push(T var1);

   boolean isEmpty();

   int size();

   public static final class FixedPriorityQueue implements StrictQueue<StrictQueue.IntRunnable, Runnable> {
      private final List<Queue<Runnable>> queueList;

      public FixedPriorityQueue(int var1) {
         this.queueList = (List)IntStream.range(0, â˜ƒ).mapToObj(var0 -> Queues.newConcurrentLinkedQueue()).collect(Collectors.toList());
      }

      @Nullable
      public Runnable pop() {
         for(Queue<Runnable> â˜ƒ : this.queueList) {
            Runnable â˜ƒx = (Runnable)â˜ƒ.poll();
            if (â˜ƒx != null) {
               return â˜ƒx;
            }
         }

         return null;
      }

      public boolean push(StrictQueue.IntRunnable var1) {
         int â˜ƒ = â˜ƒ.getPriority();
         ((Queue)this.queueList.get(â˜ƒ)).add(â˜ƒ);
         return true;
      }

      @Override
      public boolean isEmpty() {
         return this.queueList.stream().allMatch(Collection::isEmpty);
      }

      @Override
      public int size() {
         int â˜ƒ = 0;

         for(Queue<Runnable> â˜ƒx : this.queueList) {
            â˜ƒ += â˜ƒx.size();
         }

         return â˜ƒ;
      }
   }

   public static final class IntRunnable implements Runnable {
      private final int priority;
      private final Runnable task;

      public IntRunnable(int var1, Runnable var2) {
         this.priority = â˜ƒ;
         this.task = â˜ƒ;
      }

      public void run() {
         this.task.run();
      }

      public int getPriority() {
         return this.priority;
      }
   }

   public static final class QueueStrictQueue<T> implements StrictQueue<T, T> {
      private final Queue<T> queue;

      public QueueStrictQueue(Queue<T> var1) {
         this.queue = â˜ƒ;
      }

      @Nullable
      @Override
      public T pop() {
         return (T)this.queue.poll();
      }

      @Override
      public boolean push(T var1) {
         return this.queue.add(â˜ƒ);
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }

      @Override
      public int size() {
         return this.queue.size();
      }
   }
}
