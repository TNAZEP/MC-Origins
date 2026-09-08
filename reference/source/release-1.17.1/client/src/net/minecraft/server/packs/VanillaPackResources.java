package net.minecraft.server.packs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaPackResources implements PackResources, ResourceProvider {
   public static Path generatedDir;
   private static final Logger LOGGER = LogManager.getLogger();
   public static Class<?> clientObject;
   private static final Map<PackType, Path> ROOT_DIR_BY_TYPE = Util.make(() -> {
      synchronized(VanillaPackResources.class) {
         Builder<PackType, Path> â˜ƒ = ImmutableMap.builder();

         for(PackType â˜ƒx : PackType.values()) {
            String â˜ƒxx = "/" + â˜ƒx.getDirectory() + "/.mcassetsroot";
            URL â˜ƒxxx = VanillaPackResources.class.getResource(â˜ƒxx);
            if (â˜ƒxxx == null) {
               LOGGER.error("File {} does not exist in classpath", â˜ƒxx);
            } else {
               try {
                  URI â˜ƒxx = â˜ƒxxx.toURI();
                  String â˜ƒxxx = â˜ƒxx.getScheme();
                  if (!"jar".equals(â˜ƒxxx) && !"file".equals(â˜ƒxxx)) {
                     LOGGER.warn("Assets URL '{}' uses unexpected schema", â˜ƒxx);
                  }

                  Path â˜ƒxx = safeGetPath(â˜ƒxx);
                  â˜ƒ.put(â˜ƒx, â˜ƒxx.getParent());
               } catch (Exception var12) {
                  LOGGER.error("Couldn't resolve path to vanilla assets", var12);
               }
            }
         }

         return â˜ƒ.build();
      }
   });
   public final PackMetadataSection packMetadata;
   public final Set<String> namespaces;

   private static Path safeGetPath(URI var0) throws IOException {
      try {
         return Paths.get(â˜ƒ);
      } catch (FileSystemNotFoundException var3) {
      } catch (Throwable var4) {
         LOGGER.warn("Unable to get path for: {}", â˜ƒ, var4);
      }

      try {
         FileSystems.newFileSystem(â˜ƒ, Collections.emptyMap());
      } catch (FileSystemAlreadyExistsException var2) {
      }

      return Paths.get(â˜ƒ);
   }

   public VanillaPackResources(PackMetadataSection var1, String... var2) {
      this.packMetadata = â˜ƒ;
      this.namespaces = ImmutableSet.copyOf(â˜ƒ);
   }

   @Override
   public InputStream getRootResource(String var1) throws IOException {
      if (!â˜ƒ.contains("/") && !â˜ƒ.contains("\\")) {
         if (generatedDir != null) {
            Path â˜ƒ = generatedDir.resolve(â˜ƒ);
            if (Files.exists(â˜ƒ, new LinkOption[0])) {
               return Files.newInputStream(â˜ƒ);
            }
         }

         return this.getResourceAsStream(â˜ƒ);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   @Override
   public InputStream getResource(PackType var1, ResourceLocation var2) throws IOException {
      InputStream â˜ƒ = this.getResourceAsStream(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ;
      } else {
         throw new FileNotFoundException(â˜ƒ.getPath());
      }
   }

   @Override
   public Collection<ResourceLocation> getResources(PackType var1, String var2, String var3, int var4, Predicate<String> var5) {
      Set<ResourceLocation> â˜ƒ = Sets.<ResourceLocation>newHashSet();
      if (generatedDir != null) {
         try {
            getResources(â˜ƒ, â˜ƒ, â˜ƒ, generatedDir.resolve(â˜ƒ.getDirectory()), â˜ƒ, â˜ƒ);
         } catch (IOException var13) {
         }

         if (â˜ƒ == PackType.CLIENT_RESOURCES) {
            Enumeration<URL> â˜ƒx = null;

            try {
               â˜ƒx = clientObject.getClassLoader().getResources(â˜ƒ.getDirectory() + "/");
            } catch (IOException var12) {
            }

            while(â˜ƒx != null && â˜ƒx.hasMoreElements()) {
               try {
                  URI â˜ƒxx = ((URL)â˜ƒx.nextElement()).toURI();
                  if ("file".equals(â˜ƒxx.getScheme())) {
                     getResources(â˜ƒ, â˜ƒ, â˜ƒ, Paths.get(â˜ƒxx), â˜ƒ, â˜ƒ);
                  }
               } catch (IOException | URISyntaxException var11) {
               }
            }
         }
      }

      try {
         Path â˜ƒ = (Path)ROOT_DIR_BY_TYPE.get(â˜ƒ);
         if (â˜ƒ != null) {
            getResources(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            LOGGER.error("Can't access assets root for type: {}", â˜ƒ);
         }
      } catch (NoSuchFileException | FileNotFoundException var9) {
      } catch (IOException var10) {
         LOGGER.error("Couldn't get a list of all vanilla resources", var10);
      }

      return â˜ƒ;
   }

   private static void getResources(Collection<ResourceLocation> var0, int var1, String var2, Path var3, String var4, Predicate<String> var5) throws IOException {
      Path â˜ƒ = â˜ƒ.resolve(â˜ƒ);
      Stream<Path> â˜ƒx = Files.walk(â˜ƒ.resolve(â˜ƒ), â˜ƒ, new FileVisitOption[0]);

      try {
         â˜ƒx.filter(var1x -> !var1x.endsWith(".mcmeta") && Files.isRegularFile(var1x, new LinkOption[0]) && â˜ƒ.test(var1x.getFileName().toString()))
            .map(var2x -> new ResourceLocation(â˜ƒ, â˜ƒ.relativize(var2x).toString().replaceAll("\\\\", "/")))
            .forEach(â˜ƒ::add);
      } catch (Throwable var11) {
         if (â˜ƒx != null) {
            try {
               â˜ƒx.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }
         }

         throw var11;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }
   }

   @Nullable
   protected InputStream getResourceAsStream(PackType var1, ResourceLocation var2) {
      String â˜ƒ = createPath(â˜ƒ, â˜ƒ);
      if (generatedDir != null) {
         Path â˜ƒx = generatedDir.resolve(â˜ƒ.getDirectory() + "/" + â˜ƒ.getNamespace() + "/" + â˜ƒ.getPath());
         if (Files.exists(â˜ƒx, new LinkOption[0])) {
            try {
               return Files.newInputStream(â˜ƒx);
            } catch (IOException var7) {
            }
         }
      }

      try {
         URL â˜ƒ = VanillaPackResources.class.getResource(â˜ƒ);
         return isResourceUrlValid(â˜ƒ, â˜ƒ) ? â˜ƒ.openStream() : null;
      } catch (IOException var6) {
         return VanillaPackResources.class.getResourceAsStream(â˜ƒ);
      }
   }

   private static String createPath(PackType var0, ResourceLocation var1) {
      return "/" + â˜ƒ.getDirectory() + "/" + â˜ƒ.getNamespace() + "/" + â˜ƒ.getPath();
   }

   private static boolean isResourceUrlValid(String var0, @Nullable URL var1) throws IOException {
      return â˜ƒ != null && (â˜ƒ.getProtocol().equals("jar") || FolderPackResources.validatePath(new File(â˜ƒ.getFile()), â˜ƒ));
   }

   @Nullable
   protected InputStream getResourceAsStream(String var1) {
      return VanillaPackResources.class.getResourceAsStream("/" + â˜ƒ);
   }

   @Override
   public boolean hasResource(PackType var1, ResourceLocation var2) {
      String â˜ƒ = createPath(â˜ƒ, â˜ƒ);
      if (generatedDir != null) {
         Path â˜ƒx = generatedDir.resolve(â˜ƒ.getDirectory() + "/" + â˜ƒ.getNamespace() + "/" + â˜ƒ.getPath());
         if (Files.exists(â˜ƒx, new LinkOption[0])) {
            return true;
         }
      }

      try {
         URL â˜ƒ = VanillaPackResources.class.getResource(â˜ƒ);
         return isResourceUrlValid(â˜ƒ, â˜ƒ);
      } catch (IOException var5) {
         return false;
      }
   }

   @Override
   public Set<String> getNamespaces(PackType var1) {
      return this.namespaces;
   }

   @Nullable
   @Override
   public <T> T getMetadataSection(MetadataSectionSerializer<T> var1) throws IOException {
      try {
         InputStream â˜ƒ = this.getRootResource("pack.mcmeta");

         Object var4;
         label57: {
            try {
               if (â˜ƒ != null) {
                  T â˜ƒx = AbstractPackResources.getMetadataFromStream(â˜ƒ, â˜ƒ);
                  if (â˜ƒx != null) {
                     var4 = â˜ƒx;
                     break label57;
                  }
               }
            } catch (Throwable var6) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }

            return (T)(â˜ƒ == PackMetadataSection.SERIALIZER ? this.packMetadata : null);
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }

         return (T)var4;
      } catch (FileNotFoundException | RuntimeException var7) {
         return (T)(â˜ƒ == PackMetadataSection.SERIALIZER ? this.packMetadata : null);
      }
   }

   @Override
   public String getName() {
      return "Default";
   }

   @Override
   public void close() {
   }

   @Override
   public Resource getResource(final ResourceLocation var1) throws IOException {
      return new Resource() {
         @Nullable
         InputStream inputStream;

         public void close() throws IOException {
            if (this.inputStream != null) {
               this.inputStream.close();
            }
         }

         @Override
         public ResourceLocation getLocation() {
            return â˜ƒ;
         }

         @Override
         public InputStream getInputStream() {
            try {
               this.inputStream = VanillaPackResources.this.getResource(PackType.CLIENT_RESOURCES, â˜ƒ);
            } catch (IOException var2) {
               throw new UncheckedIOException("Could not get client resource from vanilla pack", var2);
            }

            return this.inputStream;
         }

         @Override
         public boolean hasMetadata() {
            return false;
         }

         @Nullable
         @Override
         public <T> T getMetadata(MetadataSectionSerializer<T> var1x) {
            return null;
         }

         @Override
         public String getSourceName() {
            return â˜ƒ.toString();
         }
      };
   }
}
