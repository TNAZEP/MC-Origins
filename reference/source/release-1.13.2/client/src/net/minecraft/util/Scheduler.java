package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Scheduler<K, T extends ITaskType<K, T>, R> {
   private static final Logger field_202856_b = LogManager.getLogger();
   protected final ExecutorService field_202855_a;
   private final ExecutorService field_202857_c;
   private final AtomicInteger field_202858_d = new AtomicInteger(1);
   private final List<CompletableFuture<R>> field_202859_e = Lists.newArrayList();
   private CompletableFuture<R> field_202860_f = CompletableFuture.completedFuture(null);
   private CompletableFuture<R> field_202861_g = CompletableFuture.completedFuture(null);
   private final Supplier<Map<T, CompletableFuture<R>>> field_202862_h;
   private final Supplier<Map<T, CompletableFuture<Void>>> field_202863_i;
   private final T field_202864_j;

   public Scheduler(String var1, int var2, T var3, Supplier<Map<T, CompletableFuture<R>>> var4, Supplier<Map<T, CompletableFuture<Void>>> var5) {
      this.field_202864_j = ☃;
      this.field_202862_h = ☃;
      this.field_202863_i = ☃;
      if (☃ == 0) {
         this.field_202855_a = MoreExecutors.newDirectExecutorService();
      } else {
         this.field_202855_a = Executors.newSingleThreadExecutor(new NamedThreadFactory(☃ + "-Scheduler"));
      }

      if (☃ <= 1) {
         this.field_202857_c = MoreExecutors.newDirectExecutorService();
      } else {
         this.field_202857_c = new ForkJoinPool(☃ - 1, var2x -> new ForkJoinWorkerThread(var2x) {
               {
                  this.setName(☃ + "-Worker-" + Scheduler.this.field_202858_d.getAndIncrement());
               }
            }, (var0, var1x) -> field_202856_b.error(String.format("Caught exception in thread %s", var0), var1x), true);
      }
   }

   public CompletableFuture<R> func_202851_b(K var1) {
      CompletableFuture<R> ☃ = this.field_202860_f;
      Supplier<CompletableFuture<R>> ☃x = () -> this.func_201494_a_(☃).func_202914_a(☃, this.field_202864_j);
      CompletableFuture<CompletableFuture<R>> ☃xx = CompletableFuture.supplyAsync(☃x, this.field_202855_a);
      CompletableFuture<R> ☃xxx = ☃xx.thenComposeAsync(var0 -> var0, this.field_202857_c);
      this.field_202859_e.add(☃xxx);
      return ☃xxx;
   }

   public CompletableFuture<R> func_202845_a() {
      CompletableFuture<R> ☃ = (CompletableFuture)this.field_202859_e.remove(this.field_202859_e.size() - 1);
      CompletableFuture<R> ☃x = CompletableFuture.allOf((CompletableFuture[])this.field_202859_e.toArray(new CompletableFuture[0])).thenCompose(var1x -> ☃);
      this.field_202861_g = ☃x;
      this.field_202859_e.clear();
      this.field_202860_f = ☃x;
      return ☃x;
   }

   protected Scheduler<K, T, R>.a func_201494_a_(K var1) {
      return this.func_212252_a_(☃, true);
   }

   @Nullable
   protected abstract Scheduler<K, T, R>.a func_212252_a_(K var1, boolean var2);

   public void func_202854_b() throws InterruptedException {
      this.field_202855_a.shutdown();
      this.field_202855_a.awaitTermination(1L, TimeUnit.DAYS);
      this.field_202857_c.shutdown();
      this.field_202857_c.awaitTermination(1L, TimeUnit.DAYS);
   }

   protected abstract R func_201493_a_(K var1, T var2, Map<K, R> var3);

   @Nullable
   public R func_212537_b(K var1, boolean var2) {
      Scheduler<K, T, R>.FutureWrapper ☃ = this.func_212252_a_(☃, ☃);
      return (R)(☃ != null ? ☃.func_202917_a() : null);
   }

   public CompletableFuture<R> func_202846_c() {
      CompletableFuture<R> ☃ = this.field_202861_g;
      return ☃.thenApply(var0 -> var0);
   }

   protected abstract void func_205607_b_(K var1, Scheduler<K, T, R>.a var2);

   protected abstract Scheduler<K, T, R>.a func_205606_a_(K var1, Scheduler<K, T, R>.a var2);

   public final class FutureWrapper {
      private final Map<T, CompletableFuture<R>> field_202920_b = (Map<T, CompletableFuture<R>>)Scheduler.this.field_202862_h.get();
      private final K field_202921_c;
      private final R field_202922_d;

      public FutureWrapper(K var2, R var3, T var4) {
         this.field_202921_c = ☃;

         for(this.field_202922_d = ☃; ☃ != null; ☃ = ☃.func_201497_a_()) {
            this.field_202920_b.put(☃, CompletableFuture.completedFuture(☃));
         }
      }

      public R func_202917_a() {
         return this.field_202922_d;
      }

      private CompletableFuture<R> func_202914_a(CompletableFuture<R> var1, T var2) {
         Map<K, CompletableFuture<R>> ☃ = new ConcurrentHashMap();
         return (CompletableFuture<R>)this.field_202920_b
            .computeIfAbsent(
               ☃,
               var4 -> {
                  if (☃.func_201497_a_() == null) {
                     return CompletableFuture.completedFuture(this.field_202922_d);
                  } else {
                     ☃.func_201492_a_(this.field_202921_c, (var3x, var4x) -> Scheduler.this.func_205606_a_((K)var3x, Scheduler.this.func_201494_a_((K)var3x)));
                     CompletableFuture<?>[] ☃ = (CompletableFuture[])Streams.concat(Stream.of(☃), ☃.values().stream())
                        .toArray(var0 -> new CompletableFuture[var0]);
                     CompletableFuture<R> ☃x = CompletableFuture.allOf(☃)
                        .thenApplyAsync(var3x -> Scheduler.this.func_201493_a_(this.field_202921_c, ☃, Maps.transformValues(☃, var0 -> {
                              try {
                                 return (R)var0.get();
                              } catch (ExecutionException | InterruptedException var2xx) {
                                 throw new RuntimeException(var2xx);
                              }
                           })), Scheduler.this.field_202857_c)
                        .thenApplyAsync(var2x -> {
                           for(K ☃ : ☃.keySet()) {
                              Scheduler.this.func_205607_b_(☃, Scheduler.this.func_201494_a_(☃));
                           }
         
                           return var2x;
                        }, Scheduler.this.field_202855_a);
                     this.field_202920_b.put(☃, ☃x);
                     return ☃x;
                  }
               }
            );
      }
   }
}
