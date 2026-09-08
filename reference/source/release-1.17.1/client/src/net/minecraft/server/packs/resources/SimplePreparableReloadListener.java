package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.util.profiling.ProfilerFiller;

public abstract class SimplePreparableReloadListener<T> implements PreparableReloadListener {
   @Override
   public final CompletableFuture<Void> reload(
      PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6
   ) {
      return CompletableFuture.supplyAsync(() -> this.prepare(â˜ƒ, â˜ƒ), â˜ƒ)
         .thenCompose(â˜ƒ::wait)
         .thenAcceptAsync(var3x -> this.apply((T)var3x, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   protected abstract T prepare(ResourceManager var1, ProfilerFiller var2);

   protected abstract void apply(T var1, ResourceManager var2, ProfilerFiller var3);
}
