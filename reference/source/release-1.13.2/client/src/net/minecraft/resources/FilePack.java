package net.minecraft.resources;

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
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class FilePack extends AbstractResourcePack {
   public static final Splitter field_195774_b = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile field_195775_c;

   public FilePack(File var1) {
      super(☃);
   }

   private ZipFile func_195773_b() throws IOException {
      if (this.field_195775_c == null) {
         this.field_195775_c = new ZipFile(this.field_195771_a);
      }

      return this.field_195775_c;
   }

   @Override
   protected InputStream func_195766_a(String var1) throws IOException {
      ZipFile ☃ = this.func_195773_b();
      ZipEntry ☃x = ☃.getEntry(☃);
      if (☃x == null) {
         throw new ResourcePackFileNotFoundException(this.field_195771_a, ☃);
      } else {
         return ☃.getInputStream(☃x);
      }
   }

   @Override
   public boolean func_195768_c(String var1) {
      try {
         return this.func_195773_b().getEntry(☃) != null;
      } catch (IOException var3) {
         return false;
      }
   }

   @Override
   public Set<String> func_195759_a(ResourcePackType var1) {
      ZipFile ☃;
      try {
         ☃ = this.func_195773_b();
      } catch (IOException var9) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> ☃ = ☃.entries();
      Set<String> ☃x = Sets.newHashSet();

      while(☃.hasMoreElements()) {
         ZipEntry ☃xx = (ZipEntry)☃.nextElement();
         String ☃xxx = ☃xx.getName();
         if (☃xxx.startsWith(☃.func_198956_a() + "/")) {
            List<String> ☃xxxx = Lists.newArrayList(field_195774_b.split(☃xxx));
            if (☃xxxx.size() > 1) {
               String ☃xxxxx = (String)☃xxxx.get(1);
               if (☃xxxxx.equals(☃xxxxx.toLowerCase(Locale.ROOT))) {
                  ☃x.add(☃xxxxx);
               } else {
                  this.func_195769_d(☃xxxxx);
               }
            }
         }
      }

      return ☃x;
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   public void close() {
      if (this.field_195775_c != null) {
         IOUtils.closeQuietly(this.field_195775_c);
         this.field_195775_c = null;
      }
   }

   @Override
   public Collection<ResourceLocation> func_195758_a(ResourcePackType var1, String var2, int var3, Predicate<String> var4) {
      ZipFile ☃;
      try {
         ☃ = this.func_195773_b();
      } catch (IOException var15) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> ☃ = ☃.entries();
      List<ResourceLocation> ☃x = Lists.<ResourceLocation>newArrayList();
      String ☃xx = ☃.func_198956_a() + "/";

      while(☃.hasMoreElements()) {
         ZipEntry ☃xxx = (ZipEntry)☃.nextElement();
         if (!☃xxx.isDirectory() && ☃xxx.getName().startsWith(☃xx)) {
            String ☃xxxx = ☃xxx.getName().substring(☃xx.length());
            if (!☃xxxx.endsWith(".mcmeta")) {
               int ☃xxxxx = ☃xxxx.indexOf(47);
               if (☃xxxxx >= 0) {
                  String ☃xxxxxx = ☃xxxx.substring(☃xxxxx + 1);
                  if (☃xxxxxx.startsWith(☃ + "/")) {
                     String[] ☃xxxxxxx = ☃xxxxxx.substring(☃.length() + 2).split("/");
                     if (☃xxxxxxx.length >= ☃ + 1 && ☃.test(☃xxxxxx)) {
                        String ☃xxxxxxxx = ☃xxxx.substring(0, ☃xxxxx);
                        ☃x.add(new ResourceLocation(☃xxxxxxxx, ☃xxxxxx));
                     }
                  }
               }
            }
         }
      }

      return ☃x;
   }
}
