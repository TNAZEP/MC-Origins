package net.minecraft.tags;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagManager implements PreparableReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RegistryAccess registryAccess;
   private TagContainer tags = TagContainer.EMPTY;

   public TagManager(RegistryAccess var1) {
      this.registryAccess = â˜ƒ;
   }

   public TagContainer getTags() {
      return this.tags;
   }

   @Override
   public CompletableFuture<Void> reload(
      PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6
   ) {
      List<TagManager.LoaderInfo<?>> â˜ƒ = Lists.<TagManager.LoaderInfo<?>>newArrayList();
      StaticTags.visitHelpers(var4x -> {
         TagManager.LoaderInfo<?> â˜ƒ = this.createLoader(â˜ƒ, â˜ƒ, var4x);
         if (â˜ƒ != null) {
            â˜ƒ.add(â˜ƒ);
         }
      });
      return CompletableFuture.allOf((CompletableFuture[])â˜ƒ.stream().map(var0 -> var0.pendingLoad).toArray(var0 -> new CompletableFuture[var0]))
         .thenCompose(â˜ƒ::wait)
         .thenAcceptAsync(
            var2x -> {
               TagContainer.Builder â˜ƒ = new TagContainer.Builder();
               â˜ƒ.forEach(var1x -> var1x.addToBuilder(â˜ƒ));
               TagContainer â˜ƒx = â˜ƒ.build();
               Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> â˜ƒxx = StaticTags.getAllMissingTags(â˜ƒx);
               if (!â˜ƒxx.isEmpty()) {
                  throw new IllegalStateException(
                     "Missing required tags: "
                        + (String)â˜ƒxx.entries().stream().map(var0 -> var0.getKey() + ":" + var0.getValue()).sorted().collect(Collectors.joining(","))
                  );
               } else {
                  SerializationTags.bind(â˜ƒx);
                  this.tags = â˜ƒx;
               }
            },
            â˜ƒ
         );
   }

   @Nullable
   private <T> TagManager.LoaderInfo<T> createLoader(ResourceManager var1, Executor var2, StaticTagHelper<T> var3) {
      Optional<? extends Registry<T>> â˜ƒ = this.registryAccess.registry(â˜ƒ.getKey());
      if (â˜ƒ.isPresent()) {
         Registry<T> â˜ƒx = (Registry)â˜ƒ.get();
         TagLoader<T> â˜ƒxx = new TagLoader<>(â˜ƒx::getOptional, â˜ƒ.getDirectory());
         CompletableFuture<? extends TagCollection<T>> â˜ƒxxx = CompletableFuture.supplyAsync(() -> â˜ƒ.loadAndBuild(â˜ƒ), â˜ƒ);
         return new TagManager.LoaderInfo<>(â˜ƒ, â˜ƒxxx);
      } else {
         LOGGER.warn("Can't find registry for {}", â˜ƒ.getKey());
         return null;
      }
   }

   static class LoaderInfo<T> {
      private final StaticTagHelper<T> helper;
      final CompletableFuture<? extends TagCollection<T>> pendingLoad;

      LoaderInfo(StaticTagHelper<T> var1, CompletableFuture<? extends TagCollection<T>> var2) {
         this.helper = â˜ƒ;
         this.pendingLoad = â˜ƒ;
      }

      public void addToBuilder(TagContainer.Builder var1) {
         â˜ƒ.add(this.helper.getKey(), (TagCollection<T>)this.pendingLoad.join());
      }
   }
}
