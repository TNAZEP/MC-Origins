package net.minecraft.client.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public class DirectAssetIndex extends AssetIndex {
   private final File assetsDirectory;

   public DirectAssetIndex(File var1) {
      this.assetsDirectory = â˜ƒ;
   }

   @Override
   public File getFile(ResourceLocation var1) {
      return new File(this.assetsDirectory, â˜ƒ.toString().replace(':', '/'));
   }

   @Override
   public File getRootFile(String var1) {
      return new File(this.assetsDirectory, â˜ƒ);
   }

   @Override
   public Collection<ResourceLocation> getFiles(String var1, String var2, int var3, Predicate<String> var4) {
      Path â˜ƒ = this.assetsDirectory.toPath().resolve(â˜ƒ);

      try {
         Stream<Path> â˜ƒx = Files.walk(â˜ƒ.resolve(â˜ƒ), â˜ƒ, new FileVisitOption[0]);

         Collection var7;
         try {
            var7 = (Collection)â˜ƒx.filter(var0 -> Files.isRegularFile(var0, new LinkOption[0]))
               .filter(var0 -> !var0.endsWith(".mcmeta"))
               .filter(var1x -> â˜ƒ.test(var1x.getFileName().toString()))
               .map(var2x -> new ResourceLocation(â˜ƒ, â˜ƒ.relativize(var2x).toString().replaceAll("\\\\", "/")))
               .collect(Collectors.toList());
         } catch (Throwable var10) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }
            }

            throw var10;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         return var7;
      } catch (NoSuchFileException var11) {
      } catch (IOException var12) {
         LOGGER.warn("Unable to getFiles on {}", â˜ƒ, var12);
      }

      return Collections.emptyList();
   }
}
