package net.minecraft.server.packs;

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
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderPackResources extends AbstractPackResources {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final boolean ON_WINDOWS = Util.getPlatform() == Util.OS.WINDOWS;
   private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');

   public FolderPackResources(File var1) {
      super(â˜ƒ);
   }

   public static boolean validatePath(File var0, String var1) throws IOException {
      String â˜ƒ = â˜ƒ.getCanonicalPath();
      if (ON_WINDOWS) {
         â˜ƒ = BACKSLASH_MATCHER.replaceFrom(â˜ƒ, '/');
      }

      return â˜ƒ.endsWith(â˜ƒ);
   }

   @Override
   protected InputStream getResource(String var1) throws IOException {
      File â˜ƒ = this.getFile(â˜ƒ);
      if (â˜ƒ == null) {
         throw new ResourcePackFileNotFoundException(this.file, â˜ƒ);
      } else {
         return new FileInputStream(â˜ƒ);
      }
   }

   @Override
   protected boolean hasResource(String var1) {
      return this.getFile(â˜ƒ) != null;
   }

   @Nullable
   private File getFile(String var1) {
      try {
         File â˜ƒ = new File(this.file, â˜ƒ);
         if (â˜ƒ.isFile() && validatePath(â˜ƒ, â˜ƒ)) {
            return â˜ƒ;
         }
      } catch (IOException var3) {
      }

      return null;
   }

   @Override
   public Set<String> getNamespaces(PackType var1) {
      Set<String> â˜ƒ = Sets.newHashSet();
      File â˜ƒx = new File(this.file, â˜ƒ.getDirectory());
      File[] â˜ƒxx = â˜ƒx.listFiles(DirectoryFileFilter.DIRECTORY);
      if (â˜ƒxx != null) {
         for(File â˜ƒxxx : â˜ƒxx) {
            String â˜ƒxxxx = getRelativePath(â˜ƒx, â˜ƒxxx);
            if (â˜ƒxxxx.equals(â˜ƒxxxx.toLowerCase(Locale.ROOT))) {
               â˜ƒ.add(â˜ƒxxxx.substring(0, â˜ƒxxxx.length() - 1));
            } else {
               this.logWarning(â˜ƒxxxx);
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   public void close() {
   }

   @Override
   public Collection<ResourceLocation> getResources(PackType var1, String var2, String var3, int var4, Predicate<String> var5) {
      File â˜ƒ = new File(this.file, â˜ƒ.getDirectory());
      List<ResourceLocation> â˜ƒx = Lists.<ResourceLocation>newArrayList();
      this.listResources(new File(new File(â˜ƒ, â˜ƒ), â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ + "/", â˜ƒ);
      return â˜ƒx;
   }

   private void listResources(File var1, int var2, String var3, List<ResourceLocation> var4, String var5, Predicate<String> var6) {
      File[] â˜ƒ = â˜ƒ.listFiles();
      if (â˜ƒ != null) {
         for(File â˜ƒx : â˜ƒ) {
            if (â˜ƒx.isDirectory()) {
               if (â˜ƒ > 0) {
                  this.listResources(â˜ƒx, â˜ƒ - 1, â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒx.getName() + "/", â˜ƒ);
               }
            } else if (!â˜ƒx.getName().endsWith(".mcmeta") && â˜ƒ.test(â˜ƒx.getName())) {
               try {
                  â˜ƒ.add(new ResourceLocation(â˜ƒ, â˜ƒ + â˜ƒx.getName()));
               } catch (ResourceLocationException var13) {
                  LOGGER.error(var13.getMessage());
               }
            }
         }
      }
   }
}
