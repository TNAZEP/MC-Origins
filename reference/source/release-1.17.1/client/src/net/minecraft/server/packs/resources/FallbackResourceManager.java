package net.minecraft.server.packs.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements ResourceManager {
   static final Logger LOGGER = LogManager.getLogger();
   protected final List<PackResources> fallbacks = Lists.<PackResources>newArrayList();
   private final PackType type;
   private final String namespace;

   public FallbackResourceManager(PackType var1, String var2) {
      this.type = â˜ƒ;
      this.namespace = â˜ƒ;
   }

   public void add(PackResources var1) {
      this.fallbacks.add(â˜ƒ);
   }

   @Override
   public Set<String> getNamespaces() {
      return ImmutableSet.of(this.namespace);
   }

   @Override
   public Resource getResource(ResourceLocation var1) throws IOException {
      this.validateLocation(â˜ƒ);
      PackResources â˜ƒ = null;
      ResourceLocation â˜ƒx = getMetadataLocation(â˜ƒ);

      for(int â˜ƒxx = this.fallbacks.size() - 1; â˜ƒxx >= 0; --â˜ƒxx) {
         PackResources â˜ƒxxx = (PackResources)this.fallbacks.get(â˜ƒxx);
         if (â˜ƒ == null && â˜ƒxxx.hasResource(this.type, â˜ƒx)) {
            â˜ƒ = â˜ƒxxx;
         }

         if (â˜ƒxxx.hasResource(this.type, â˜ƒ)) {
            InputStream â˜ƒxxx = null;
            if (â˜ƒ != null) {
               â˜ƒxxx = this.getWrappedResource(â˜ƒx, â˜ƒ);
            }

            return new SimpleResource(â˜ƒxxx.getName(), â˜ƒ, this.getWrappedResource(â˜ƒ, â˜ƒxxx), â˜ƒxxx);
         }
      }

      throw new FileNotFoundException(â˜ƒ.toString());
   }

   @Override
   public boolean hasResource(ResourceLocation var1) {
      if (!this.isValidLocation(â˜ƒ)) {
         return false;
      } else {
         for(int â˜ƒ = this.fallbacks.size() - 1; â˜ƒ >= 0; --â˜ƒ) {
            PackResources â˜ƒx = (PackResources)this.fallbacks.get(â˜ƒ);
            if (â˜ƒx.hasResource(this.type, â˜ƒ)) {
               return true;
            }
         }

         return false;
      }
   }

   protected InputStream getWrappedResource(ResourceLocation var1, PackResources var2) throws IOException {
      InputStream â˜ƒ = â˜ƒ.getResource(this.type, â˜ƒ);
      return (InputStream)(LOGGER.isDebugEnabled() ? new FallbackResourceManager.LeakedResourceWarningInputStream(â˜ƒ, â˜ƒ, â˜ƒ.getName()) : â˜ƒ);
   }

   private void validateLocation(ResourceLocation var1) throws IOException {
      if (!this.isValidLocation(â˜ƒ)) {
         throw new IOException("Invalid relative path to resource: " + â˜ƒ);
      }
   }

   private boolean isValidLocation(ResourceLocation var1) {
      return !â˜ƒ.getPath().contains("..");
   }

   @Override
   public List<Resource> getResources(ResourceLocation var1) throws IOException {
      this.validateLocation(â˜ƒ);
      List<Resource> â˜ƒ = Lists.<Resource>newArrayList();
      ResourceLocation â˜ƒx = getMetadataLocation(â˜ƒ);

      for(PackResources â˜ƒxx : this.fallbacks) {
         if (â˜ƒxx.hasResource(this.type, â˜ƒ)) {
            InputStream â˜ƒxxx = â˜ƒxx.hasResource(this.type, â˜ƒx) ? this.getWrappedResource(â˜ƒx, â˜ƒxx) : null;
            â˜ƒ.add(new SimpleResource(â˜ƒxx.getName(), â˜ƒ, this.getWrappedResource(â˜ƒ, â˜ƒxx), â˜ƒxxx));
         }
      }

      if (â˜ƒ.isEmpty()) {
         throw new FileNotFoundException(â˜ƒ.toString());
      } else {
         return â˜ƒ;
      }
   }

   @Override
   public Collection<ResourceLocation> listResources(String var1, Predicate<String> var2) {
      List<ResourceLocation> â˜ƒ = Lists.<ResourceLocation>newArrayList();

      for(PackResources â˜ƒx : this.fallbacks) {
         â˜ƒ.addAll(â˜ƒx.getResources(this.type, this.namespace, â˜ƒ, Integer.MAX_VALUE, â˜ƒ));
      }

      Collections.sort(â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public Stream<PackResources> listPacks() {
      return this.fallbacks.stream();
   }

   static ResourceLocation getMetadataLocation(ResourceLocation var0) {
      return new ResourceLocation(â˜ƒ.getNamespace(), â˜ƒ.getPath() + ".mcmeta");
   }

   static class LeakedResourceWarningInputStream extends FilterInputStream {
      private final String message;
      private boolean closed;

      public LeakedResourceWarningInputStream(InputStream var1, ResourceLocation var2, String var3) {
         super(â˜ƒ);
         ByteArrayOutputStream â˜ƒ = new ByteArrayOutputStream();
         new Exception().printStackTrace(new PrintStream(â˜ƒ));
         this.message = "Leaked resource: '" + â˜ƒ + "' loaded from pack: '" + â˜ƒ + "'\n" + â˜ƒ;
      }

      public void close() throws IOException {
         super.close();
         this.closed = true;
      }

      protected void finalize() throws Throwable {
         if (!this.closed) {
            FallbackResourceManager.LOGGER.warn(this.message);
         }

         super.finalize();
      }
   }
}
