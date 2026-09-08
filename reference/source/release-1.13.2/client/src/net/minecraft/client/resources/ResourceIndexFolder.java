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
import net.minecraft.util.ResourceLocation;

public class ResourceIndexFolder extends ResourceIndex {
   private final File field_188548_a;

   public ResourceIndexFolder(File var1) {
      this.field_188548_a = ☃;
   }

   @Override
   public File func_188547_a(ResourceLocation var1) {
      return new File(this.field_188548_a, ☃.toString().replace(':', '/'));
   }

   @Override
   public File func_200009_a(String var1) {
      return new File(this.field_188548_a, ☃);
   }

   @Override
   public Collection<String> func_211685_a(String var1, int var2, Predicate<String> var3) {
      Path ☃ = this.field_188548_a.toPath().resolve("minecraft/");

      try {
         Stream<Path> ☃x = Files.walk(☃.resolve(☃), ☃, new FileVisitOption[0]);
         Throwable var6 = null;

         Collection var7;
         try {
            var7 = (Collection)☃x.filter(var0 -> Files.isRegularFile(var0, new LinkOption[0]))
               .filter(var0 -> !var0.endsWith(".mcmeta"))
               .map(☃::relativize)
               .map(Object::toString)
               .map(var0 -> var0.replaceAll("\\\\", "/"))
               .filter(☃)
               .collect(Collectors.toList());
         } catch (Throwable var18) {
            var6 = var18;
            throw var18;
         } finally {
            if (☃x != null) {
               if (var6 != null) {
                  try {
                     ☃x.close();
                  } catch (Throwable var17) {
                     var6.addSuppressed(var17);
                  }
               } else {
                  ☃x.close();
               }
            }
         }

         return var7;
      } catch (NoSuchFileException var20) {
      } catch (IOException var21) {
         field_152783_a.warn("Unable to getFiles on {}", ☃, var21);
      }

      return Collections.emptyList();
   }
}
