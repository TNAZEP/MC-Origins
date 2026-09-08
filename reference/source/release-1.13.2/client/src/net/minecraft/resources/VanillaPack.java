package net.minecraft.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaPack implements IResourcePack {
   public static Path field_199754_a;
   private static final Logger field_195784_b = LogManager.getLogger();
   public static Class<?> field_211688_b;
   public final Set<String> field_195783_a;

   public VanillaPack(String... var1) {
      this.field_195783_a = ImmutableSet.copyOf(☃);
   }

   @Override
   public InputStream func_195763_b(String var1) throws IOException {
      if (!☃.contains("/") && !☃.contains("\\")) {
         if (field_199754_a != null) {
            Path ☃ = field_199754_a.resolve(☃);
            if (Files.exists(☃, new LinkOption[0])) {
               return Files.newInputStream(☃);
            }
         }

         return this.func_200010_a(☃);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   @Override
   public InputStream func_195761_a(ResourcePackType var1, ResourceLocation var2) throws IOException {
      InputStream ☃ = this.func_195782_c(☃, ☃);
      if (☃ != null) {
         return ☃;
      } else {
         throw new FileNotFoundException(☃.func_110623_a());
      }
   }

   @Override
   public Collection<ResourceLocation> func_195758_a(ResourcePackType var1, String var2, int var3, Predicate<String> var4) {
      Set<ResourceLocation> ☃ = Sets.<ResourceLocation>newHashSet();
      if (field_199754_a != null) {
         try {
            ☃.addAll(this.func_195781_a(☃, "minecraft", field_199754_a.resolve(☃.func_198956_a()).resolve("minecraft"), ☃, ☃));
         } catch (IOException var26) {
         }

         if (☃ == ResourcePackType.CLIENT_RESOURCES) {
            Enumeration<URL> ☃x = null;

            try {
               ☃x = field_211688_b.getClassLoader().getResources(☃.func_198956_a() + "/minecraft");
            } catch (IOException var25) {
            }

            while(☃x != null && ☃x.hasMoreElements()) {
               try {
                  URI ☃xx = ((URL)☃x.nextElement()).toURI();
                  if ("file".equals(☃xx.getScheme())) {
                     ☃.addAll(this.func_195781_a(☃, "minecraft", Paths.get(☃xx), ☃, ☃));
                  }
               } catch (IOException | URISyntaxException var24) {
               }
            }
         }
      }

      try {
         URL ☃ = VanillaPack.class.getResource("/" + ☃.func_198956_a() + "/.mcassetsroot");
         if (☃ == null) {
            field_195784_b.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
            return ☃;
         }

         URI ☃ = ☃.toURI();
         if ("file".equals(☃.getScheme())) {
            URL ☃x = new URL(☃.toString().substring(0, ☃.toString().length() - ".mcassetsroot".length()) + "minecraft");
            if (☃x == null) {
               return ☃;
            }

            Path ☃x = Paths.get(☃x.toURI());
            ☃.addAll(this.func_195781_a(☃, "minecraft", ☃x, ☃, ☃));
         } else if ("jar".equals(☃.getScheme())) {
            FileSystem ☃ = FileSystems.newFileSystem(☃, Collections.emptyMap());
            Throwable var33 = null;

            try {
               Path ☃x = ☃.getPath("/" + ☃.func_198956_a() + "/minecraft");
               ☃.addAll(this.func_195781_a(☃, "minecraft", ☃x, ☃, ☃));
            } catch (Throwable var23) {
               var33 = var23;
               throw var23;
            } finally {
               if (☃ != null) {
                  if (var33 != null) {
                     try {
                        ☃.close();
                     } catch (Throwable var22) {
                        var33.addSuppressed(var22);
                     }
                  } else {
                     ☃.close();
                  }
               }
            }
         } else {
            field_195784_b.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", ☃);
         }
      } catch (NoSuchFileException | FileNotFoundException var28) {
      } catch (IOException | URISyntaxException var29) {
         field_195784_b.error("Couldn't get a list of all vanilla resources", var29);
      }

      return ☃;
   }

   private Collection<ResourceLocation> func_195781_a(int var1, String var2, Path var3, String var4, Predicate<String> var5) throws IOException {
      List<ResourceLocation> ☃ = Lists.<ResourceLocation>newArrayList();
      Iterator<Path> ☃x = Files.walk(☃.resolve(☃), ☃, new FileVisitOption[0]).iterator();

      while(☃x.hasNext()) {
         Path ☃xx = (Path)☃x.next();
         if (!☃xx.endsWith(".mcmeta") && Files.isRegularFile(☃xx, new LinkOption[0]) && ☃.test(☃xx.getFileName().toString())) {
            ☃.add(new ResourceLocation(☃, ☃.relativize(☃xx).toString().replaceAll("\\\\", "/")));
         }
      }

      return ☃;
   }

   @Nullable
   protected InputStream func_195782_c(ResourcePackType var1, ResourceLocation var2) {
      String ☃ = "/" + ☃.func_198956_a() + "/" + ☃.func_110624_b() + "/" + ☃.func_110623_a();
      if (field_199754_a != null) {
         Path ☃x = field_199754_a.resolve(☃.func_198956_a() + "/" + ☃.func_110624_b() + "/" + ☃.func_110623_a());
         if (Files.exists(☃x, new LinkOption[0])) {
            try {
               return Files.newInputStream(☃x);
            } catch (IOException var7) {
            }
         }
      }

      try {
         URL ☃ = VanillaPack.class.getResource(☃);
         return ☃ != null && FolderPack.func_195777_a(new File(☃.getFile()), ☃) ? VanillaPack.class.getResourceAsStream(☃) : null;
      } catch (IOException var6) {
         return VanillaPack.class.getResourceAsStream(☃);
      }
   }

   @Nullable
   protected InputStream func_200010_a(String var1) {
      return VanillaPack.class.getResourceAsStream("/" + ☃);
   }

   @Override
   public boolean func_195764_b(ResourcePackType var1, ResourceLocation var2) {
      InputStream ☃ = this.func_195782_c(☃, ☃);
      boolean ☃x = ☃ != null;
      IOUtils.closeQuietly(☃);
      return ☃x;
   }

   @Override
   public Set<String> func_195759_a(ResourcePackType var1) {
      return this.field_195783_a;
   }

   @Nullable
   @Override
   public <T> T func_195760_a(IMetadataSectionSerializer<T> var1) throws IOException {
      try {
         InputStream ☃ = this.func_195763_b("pack.mcmeta");
         Throwable var3 = null;

         Object var4;
         try {
            var4 = AbstractResourcePack.<T>func_195770_a(☃, ☃);
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (☃ != null) {
               if (var3 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  ☃.close();
               }
            }
         }

         return (T)var4;
      } catch (FileNotFoundException | RuntimeException var16) {
         return null;
      }
   }

   @Override
   public String func_195762_a() {
      return "Default";
   }

   public void close() {
   }
}
