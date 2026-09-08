package net.minecraft.server.packs.resources;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfiledReloadInstance extends SimpleReloadInstance<ProfiledReloadInstance.State> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Stopwatch total = Stopwatch.createUnstarted();

   public ProfiledReloadInstance(ResourceManager var1, List<PreparableReloadListener> var2, Executor var3, Executor var4, CompletableFuture<Unit> var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (var1x, var2x, var3x, var4x, var5x) -> {
         AtomicLong â˜ƒ = new AtomicLong();
         AtomicLong â˜ƒx = new AtomicLong();
         ActiveProfiler â˜ƒxx = new ActiveProfiler(Util.timeSource, () -> 0, false);
         ActiveProfiler â˜ƒxxx = new ActiveProfiler(Util.timeSource, () -> 0, false);
         CompletableFuture<Void> â˜ƒxxxx = var3x.reload(var1x, var2x, â˜ƒxx, â˜ƒxxx, var2xx -> var4x.execute(() -> {
               long â˜ƒ = Util.getNanos();
               var2xx.run();
               â˜ƒ.addAndGet(Util.getNanos() - â˜ƒ);
            }), var2xx -> var5x.execute(() -> {
               long â˜ƒ = Util.getNanos();
               var2xx.run();
               â˜ƒ.addAndGet(Util.getNanos() - â˜ƒ);
            }));
         return â˜ƒxxxx.thenApplyAsync(var5xx -> new ProfiledReloadInstance.State(var3x.getName(), â˜ƒ.getResults(), â˜ƒ.getResults(), â˜ƒ, â˜ƒ), â˜ƒ);
      }, â˜ƒ);
      this.total.start();
      this.allDone.thenAcceptAsync(this::finish, â˜ƒ);
   }

   private void finish(List<ProfiledReloadInstance.State> var1) {
      this.total.stop();
      int â˜ƒ = 0;
      LOGGER.info("Resource reload finished after {} ms", this.total.elapsed(TimeUnit.MILLISECONDS));

      for(ProfiledReloadInstance.State â˜ƒx : â˜ƒ) {
         ProfileResults â˜ƒxx = â˜ƒx.preparationResult;
         ProfileResults â˜ƒxxx = â˜ƒx.reloadResult;
         int â˜ƒxxxx = (int)((double)â˜ƒx.preparationNanos.get() / 1000000.0);
         int â˜ƒxxxxx = (int)((double)â˜ƒx.reloadNanos.get() / 1000000.0);
         int â˜ƒxxxxxx = â˜ƒxxxx + â˜ƒxxxxx;
         String â˜ƒxxxxxxx = â˜ƒx.name;
         LOGGER.info("{} took approximately {} ms ({} ms preparing, {} ms applying)", â˜ƒxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxx, â˜ƒxxxxx);
         â˜ƒ += â˜ƒxxxxx;
      }

      LOGGER.info("Total blocking time: {} ms", â˜ƒ);
   }

   public static class State {
      final String name;
      final ProfileResults preparationResult;
      final ProfileResults reloadResult;
      final AtomicLong preparationNanos;
      final AtomicLong reloadNanos;

      State(String var1, ProfileResults var2, ProfileResults var3, AtomicLong var4, AtomicLong var5) {
         this.name = â˜ƒ;
         this.preparationResult = â˜ƒ;
         this.reloadResult = â˜ƒ;
         this.preparationNanos = â˜ƒ;
         this.reloadNanos = â˜ƒ;
      }
   }
}
