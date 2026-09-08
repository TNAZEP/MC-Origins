package net.minecraft.server.packs;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class FilePackResources extends AbstractPackResources {
   public static final Splitter SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile zipFile;

   public FilePackResources(File var1) {
      super(â˜ƒ);
   }

   private ZipFile getOrCreateZipFile() throws IOException {
      if (this.zipFile == null) {
         this.zipFile = new ZipFile(this.file);
      }

      return this.zipFile;
   }

   @Override
   protected InputStream getResource(String var1) throws IOException {
      ZipFile â˜ƒ = this.getOrCreateZipFile();
      ZipEntry â˜ƒx = â˜ƒ.getEntry(â˜ƒ);
      if (â˜ƒx == null) {
         throw new ResourcePackFileNotFoundException(this.file, â˜ƒ);
      } else {
         return â˜ƒ.getInputStream(â˜ƒx);
      }
   }

   @Override
   public boolean hasResource(String var1) {
      try {
         return this.getOrCreateZipFile().getEntry(â˜ƒ) != null;
      } catch (IOException var3) {
         return false;
      }
   }

   @Override
   public Set<String> getNamespaces(PackType var1) {
      ZipFile â˜ƒ;
      try {
         â˜ƒ = this.getOrCreateZipFile();
      } catch (IOException var9) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> â˜ƒ = â˜ƒ.entries();
      Set<String> â˜ƒx = Sets.newHashSet();

      while(â˜ƒ.hasMoreElements()) {
         ZipEntry â˜ƒxx = (ZipEntry)â˜ƒ.nextElement();
         String â˜ƒxxx = â˜ƒxx.getName();
         if (â˜ƒxxx.startsWith(â˜ƒ.getDirectory() + "/")) {
            List<String> â˜ƒxxxx = Lists.newArrayList(SPLITTER.split(â˜ƒxxx));
            if (â˜ƒxxxx.size() > 1) {
               String â˜ƒxxxxx = (String)â˜ƒxxxx.get(1);
               if (â˜ƒxxxxx.equals(â˜ƒxxxxx.toLowerCase(Locale.ROOT))) {
                  â˜ƒx.add(â˜ƒxxxxx);
               } else {
                  this.logWarning(â˜ƒxxxxx);
               }
            }
         }
      }

      return â˜ƒx;
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   @Override
   public void close() {
      if (this.zipFile != null) {
         IOUtils.closeQuietly(this.zipFile);
         this.zipFile = null;
      }
   }

   @Override
   public Collection<ResourceLocation> getResources(PackType var1, String var2, String var3, int var4, Predicate<String> var5) {
      ZipFile â˜ƒ;
      try {
         â˜ƒ = this.getOrCreateZipFile();
      } catch (IOException var15) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> â˜ƒ = â˜ƒ.entries();
      List<ResourceLocation> â˜ƒx = Lists.<ResourceLocation>newArrayList();
      String â˜ƒxx = â˜ƒ.getDirectory() + "/" + â˜ƒ + "/";
      String â˜ƒxxx = â˜ƒxx + â˜ƒ + "/";

      while(â˜ƒ.hasMoreElements()) {
         ZipEntry â˜ƒxxxx = (ZipEntry)â˜ƒ.nextElement();
         if (!â˜ƒxxxx.isDirectory()) {
            String â˜ƒxxxxx = â˜ƒxxxx.getName();
            if (!â˜ƒxxxxx.endsWith(".mcmeta") && â˜ƒxxxxx.startsWith(â˜ƒxxx)) {
               String â˜ƒxxxxxx = â˜ƒxxxxx.substring(â˜ƒxx.length());
               String[] â˜ƒxxxxxxx = â˜ƒxxxxxx.split("/");
               if (â˜ƒxxxxxxx.length >= â˜ƒ + 1 && â˜ƒ.test(â˜ƒxxxxxxx[â˜ƒxxxxxxx.length - 1])) {
                  â˜ƒx.add(new ResourceLocation(â˜ƒ, â˜ƒxxxxxx));
               }
            }
         }
      }

      return â˜ƒx;
   }
}
