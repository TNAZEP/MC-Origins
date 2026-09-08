package net.minecraft.resources;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Util;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderPack extends AbstractResourcePack {
   private static final Logger field_200699_b = LogManager.getLogger();
   private static final boolean field_195779_b = Util.func_110647_a() == Util.EnumOS.WINDOWS;
   private static final CharMatcher field_195780_c = CharMatcher.is('\\');

   public FolderPack(File var1) {
      super(☃);
   }

   public static boolean func_195777_a(File var0, String var1) throws IOException {
      String ☃ = ☃.getCanonicalPath();
      if (field_195779_b) {
         ☃ = field_195780_c.replaceFrom(☃, '/');
      }

      return ☃.endsWith(☃);
   }

   @Override
   protected InputStream func_195766_a(String var1) throws IOException {
      File ☃ = this.func_195776_e(☃);
      if (☃ == null) {
         throw new ResourcePackFileNotFoundException(this.field_195771_a, ☃);
      } else {
         return new FileInputStream(☃);
      }
   }

   @Override
   protected boolean func_195768_c(String var1) {
      return this.func_195776_e(☃) != null;
   }

   @Nullable
   private File func_195776_e(String var1) {
      try {
         File ☃ = new File(this.field_195771_a, ☃);
         if (☃.isFile() && func_195777_a(☃, ☃)) {
            return ☃;
         }
      } catch (IOException var3) {
      }

      return null;
   }

   @Override
   public Set<String> func_195759_a(ResourcePackType var1) {
      Set<String> ☃ = Sets.newHashSet();
      File ☃x = new File(this.field_195771_a, ☃.func_198956_a());
      File[] ☃xx = ☃x.listFiles(DirectoryFileFilter.DIRECTORY);
      if (☃xx != null) {
         for(File ☃xxx : ☃xx) {
            String ☃xxxx = func_195767_a(☃x, ☃xxx);
            if (☃xxxx.equals(☃xxxx.toLowerCase(Locale.ROOT))) {
               ☃.add(☃xxxx.substring(0, ☃xxxx.length() - 1));
            } else {
               this.func_195769_d(☃xxxx);
            }
         }
      }

      return ☃;
   }

   public void close() throws IOException {
   }

   @Override
   public Collection<ResourceLocation> func_195758_a(ResourcePackType var1, String var2, int var3, Predicate<String> var4) {
      File ☃ = new File(this.field_195771_a, ☃.func_198956_a());
      List<ResourceLocation> ☃x = Lists.<ResourceLocation>newArrayList();

      for(String ☃xx : this.func_195759_a(☃)) {
         this.func_199546_a(new File(new File(☃, ☃xx), ☃), ☃, ☃xx, ☃x, ☃ + "/", ☃);
      }

      return ☃x;
   }

   private void func_199546_a(File var1, int var2, String var3, List<ResourceLocation> var4, String var5, Predicate<String> var6) {
      File[] ☃ = ☃.listFiles();
      if (☃ != null) {
         for(File ☃x : ☃) {
            if (☃x.isDirectory()) {
               if (☃ > 0) {
                  this.func_199546_a(☃x, ☃ - 1, ☃, ☃, ☃ + ☃x.getName() + "/", ☃);
               }
            } else if (!☃x.getName().endsWith(".mcmeta") && ☃.test(☃x.getName())) {
               try {
                  ☃.add(new ResourceLocation(☃, ☃ + ☃x.getName()));
               } catch (ResourceLocationException var13) {
                  field_200699_b.error(var13.getMessage());
               }
            }
         }
      }
   }
}
