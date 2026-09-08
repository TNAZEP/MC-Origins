package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;

public interface ResourceManagerReloadListener extends PreparableReloadListener {
   @Override
   default CompletableFuture<Void> reload(
      PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6
   ) {
      return â˜ƒ.wait(Unit.INSTANCE).thenRunAsync(() -> {
         â˜ƒ.startTick();
         â˜ƒ.push("listener");
         this.onResourceManagerReload(â˜ƒ);
         â˜ƒ.pop();
         â˜ƒ.endTick();
      }, â˜ƒ);
   }

   void onResourceManagerReload(ResourceManager var1);
}
