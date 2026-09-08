package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.InactiveProfiler;

public class SimpleReloadInstance<S> implements ReloadInstance {
   private static final int PREPARATION_PROGRESS_WEIGHT = 2;
   private static final int EXTRA_RELOAD_PROGRESS_WEIGHT = 2;
   private static final int LISTENER_PROGRESS_WEIGHT = 1;
   protected final ResourceManager resourceManager;
   protected final CompletableFuture<Unit> allPreparations = new CompletableFuture();
   protected final CompletableFuture<List<S>> allDone;
   final Set<PreparableReloadListener> preparingListeners;
   private final int listenerCount;
   private int startedReloads;
   private int finishedReloads;
   private final AtomicInteger startedTaskCounter = new AtomicInteger();
   private final AtomicInteger doneTaskCounter = new AtomicInteger();

   public static SimpleReloadInstance<Void> of(
      ResourceManager var0, List<PreparableReloadListener> var1, Executor var2, Executor var3, CompletableFuture<Unit> var4
   ) {
      return new SimpleReloadInstance(
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         (var1x, var2x, var3x, var4x, var5) -> var3x.reload(var1x, var2x, InactiveProfiler.INSTANCE, InactiveProfiler.INSTANCE, â˜ƒ, var5),
         â˜ƒ
      );
   }

   protected SimpleReloadInstance(
      Executor var1,
      final Executor var2,
      ResourceManager var3,
      List<PreparableReloadListener> var4,
      SimpleReloadInstance.StateFactory<S> var5,
      CompletableFuture<Unit> var6
   ) {
      this.resourceManager = â˜ƒ;
      this.listenerCount = â˜ƒ.size();
      this.startedTaskCounter.incrementAndGet();
      â˜ƒ.thenRun(this.doneTaskCounter::incrementAndGet);
      List<CompletableFuture<S>> â˜ƒ = Lists.newArrayList();
      CompletableFuture<?> â˜ƒx = â˜ƒ;
      this.preparingListeners = Sets.<PreparableReloadListener>newHashSet(â˜ƒ);

      for(final PreparableReloadListener â˜ƒxx : â˜ƒ) {
         final CompletableFuture<?> â˜ƒxxx = â˜ƒx;
         CompletableFuture<S> â˜ƒxxxx = â˜ƒ.create(new PreparableReloadListener.PreparationBarrier() {
            @Override
            public <T> CompletableFuture<T> wait(T var1) {
               â˜ƒ.execute(() -> {
                  SimpleReloadInstance.this.preparingListeners.remove(â˜ƒ);
                  if (SimpleReloadInstance.this.preparingListeners.isEmpty()) {
                     SimpleReloadInstance.this.allPreparations.complete(Unit.INSTANCE);
                  }
               });
               return SimpleReloadInstance.this.allPreparations.thenCombine(â˜ƒ, (var1x, var2x) -> â˜ƒ);
            }
         }, â˜ƒ, â˜ƒxx, var2x -> {
            this.startedTaskCounter.incrementAndGet();
            â˜ƒ.execute(() -> {
               var2x.run();
               this.doneTaskCounter.incrementAndGet();
            });
         }, var2x -> {
            ++this.startedReloads;
            â˜ƒ.execute(() -> {
               var2x.run();
               ++this.finishedReloads;
            });
         });
         â˜ƒ.add(â˜ƒxxxx);
         â˜ƒx = â˜ƒxxxx;
      }

      this.allDone = Util.sequenceFailFast(â˜ƒ);
   }

   @Override
   public CompletableFuture<Unit> done() {
      return this.allDone.thenApply(var0 -> Unit.INSTANCE);
   }

   @Override
   public float getActualProgress() {
      int â˜ƒ = this.listenerCount - this.preparingListeners.size();
      float â˜ƒx = (float)(this.doneTaskCounter.get() * 2 + this.finishedReloads * 2 + â˜ƒ * 1);
      float â˜ƒxx = (float)(this.startedTaskCounter.get() * 2 + this.startedReloads * 2 + this.listenerCount * 1);
      return â˜ƒx / â˜ƒxx;
   }

   @Override
   public boolean isDone() {
      return this.allDone.isDone();
   }

   @Override
   public void checkExceptions() {
      if (this.allDone.isCompletedExceptionally()) {
         this.allDone.join();
      }
   }

   protected interface StateFactory<S> {
      CompletableFuture<S> create(
         PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, PreparableReloadListener var3, Executor var4, Executor var5
      );
   }
}
