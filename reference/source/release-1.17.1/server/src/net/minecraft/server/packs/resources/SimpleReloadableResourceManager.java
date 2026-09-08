package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements ReloadableResourceManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, FallbackResourceManager> namespacedPacks = Maps.newHashMap();
   private final List<PreparableReloadListener> listeners = Lists.<PreparableReloadListener>newArrayList();
   private final Set<String> namespaces = Sets.newLinkedHashSet();
   private final List<PackResources> packs = Lists.<PackResources>newArrayList();
   private final PackType type;

   public SimpleReloadableResourceManager(PackType var1) {
      this.type = â˜ƒ;
   }

   public void add(PackResources var1) {
      this.packs.add(â˜ƒ);

      for(String â˜ƒ : â˜ƒ.getNamespaces(this.type)) {
         this.namespaces.add(â˜ƒ);
         FallbackResourceManager â˜ƒx = (FallbackResourceManager)this.namespacedPacks.get(â˜ƒ);
         if (â˜ƒx == null) {
            â˜ƒx = new FallbackResourceManager(this.type, â˜ƒ);
            this.namespacedPacks.put(â˜ƒ, â˜ƒx);
         }

         â˜ƒx.add(â˜ƒ);
      }
   }

   @Override
   public Set<String> getNamespaces() {
      return this.namespaces;
   }

   @Override
   public Resource getResource(ResourceLocation var1) throws IOException {
      ResourceManager â˜ƒ = (ResourceManager)this.namespacedPacks.get(â˜ƒ.getNamespace());
      if (â˜ƒ != null) {
         return â˜ƒ.getResource(â˜ƒ);
      } else {
         throw new FileNotFoundException(â˜ƒ.toString());
      }
   }

   @Override
   public boolean hasResource(ResourceLocation var1) {
      ResourceManager â˜ƒ = (ResourceManager)this.namespacedPacks.get(â˜ƒ.getNamespace());
      return â˜ƒ != null ? â˜ƒ.hasResource(â˜ƒ) : false;
   }

   @Override
   public List<Resource> getResources(ResourceLocation var1) throws IOException {
      ResourceManager â˜ƒ = (ResourceManager)this.namespacedPacks.get(â˜ƒ.getNamespace());
      if (â˜ƒ != null) {
         return â˜ƒ.getResources(â˜ƒ);
      } else {
         throw new FileNotFoundException(â˜ƒ.toString());
      }
   }

   @Override
   public Collection<ResourceLocation> listResources(String var1, Predicate<String> var2) {
      Set<ResourceLocation> â˜ƒ = Sets.<ResourceLocation>newHashSet();

      for(FallbackResourceManager â˜ƒx : this.namespacedPacks.values()) {
         â˜ƒ.addAll(â˜ƒx.listResources(â˜ƒ, â˜ƒ));
      }

      List<ResourceLocation> â˜ƒx = Lists.<ResourceLocation>newArrayList(â˜ƒ);
      Collections.sort(â˜ƒx);
      return â˜ƒx;
   }

   private void clear() {
      this.namespacedPacks.clear();
      this.namespaces.clear();
      this.packs.forEach(PackResources::close);
      this.packs.clear();
   }

   @Override
   public void close() {
      this.clear();
   }

   @Override
   public void registerReloadListener(PreparableReloadListener var1) {
      this.listeners.add(â˜ƒ);
   }

   @Override
   public ReloadInstance createReload(Executor var1, Executor var2, CompletableFuture<Unit> var3, List<PackResources> var4) {
      LOGGER.info("Reloading ResourceManager: {}", () -> â˜ƒ.stream().map(PackResources::getName).collect(Collectors.joining(", ")));
      this.clear();

      for(PackResources â˜ƒ : â˜ƒ) {
         try {
            this.add(â˜ƒ);
         } catch (Exception var8) {
            LOGGER.error("Failed to add resource pack {}", â˜ƒ.getName(), var8);
            return new SimpleReloadableResourceManager.FailingReloadInstance(new SimpleReloadableResourceManager.ResourcePackLoadingFailure(â˜ƒ, var8));
         }
      }

      return (ReloadInstance)(LOGGER.isDebugEnabled()
         ? new ProfiledReloadInstance(this, Lists.<PreparableReloadListener>newArrayList(this.listeners), â˜ƒ, â˜ƒ, â˜ƒ)
         : SimpleReloadInstance.of(this, Lists.<PreparableReloadListener>newArrayList(this.listeners), â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Override
   public Stream<PackResources> listPacks() {
      return this.packs.stream();
   }

   static class FailingReloadInstance implements ReloadInstance {
      private final SimpleReloadableResourceManager.ResourcePackLoadingFailure exception;
      private final CompletableFuture<Unit> failedFuture;

      public FailingReloadInstance(SimpleReloadableResourceManager.ResourcePackLoadingFailure var1) {
         this.exception = â˜ƒ;
         this.failedFuture = new CompletableFuture();
         this.failedFuture.completeExceptionally(â˜ƒ);
      }

      @Override
      public CompletableFuture<Unit> done() {
         return this.failedFuture;
      }

      @Override
      public float getActualProgress() {
         return 0.0F;
      }

      @Override
      public boolean isDone() {
         return true;
      }

      @Override
      public void checkExceptions() {
         throw this.exception;
      }
   }

   public static class ResourcePackLoadingFailure extends RuntimeException {
      private final PackResources pack;

      public ResourcePackLoadingFailure(PackResources var1, Throwable var2) {
         super(â˜ƒ.getName(), â˜ƒ);
         this.pack = â˜ƒ;
      }

      public PackResources getPack() {
         return this.pack;
      }
   }
}
