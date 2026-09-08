package net.minecraft.data;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryCache {
   private static final Logger field_208324_a = LogManager.getLogger();
   private final Path field_208325_b;
   private final Path field_208326_c;
   private int field_208327_d;
   private final Map<Path, String> field_208328_e = Maps.newHashMap();
   private final Map<Path, String> field_208329_f = Maps.newHashMap();

   public DirectoryCache(Path var1, String var2) throws IOException {
      this.field_208325_b = ☃;
      Path ☃ = ☃.resolve(".cache");
      Files.createDirectories(☃);
      this.field_208326_c = ☃.resolve(☃);
      this.func_209398_c().forEach(var1x -> {
      });
      if (Files.isReadable(this.field_208326_c)) {
         IOUtils.readLines(Files.newInputStream(this.field_208326_c), Charsets.UTF_8).forEach(var2x -> {
            int ☃ = var2x.indexOf(32);
            this.field_208328_e.put(☃.resolve(var2x.substring(☃ + 1)), var2x.substring(0, ☃));
         });
      }
   }

   public void func_208317_a() throws IOException {
      this.func_209400_b();

      Writer ☃;
      try {
         ☃ = Files.newBufferedWriter(this.field_208326_c);
      } catch (IOException var3) {
         field_208324_a.warn("Unable write cachefile {}: {}", this.field_208326_c, var3.toString());
         return;
      }

      IOUtils.writeLines(
         (Collection<?>)this.field_208329_f
            .entrySet()
            .stream()
            .map(var1x -> (String)var1x.getValue() + ' ' + this.field_208325_b.relativize((Path)var1x.getKey()))
            .collect(Collectors.toList()),
         System.lineSeparator(),
         ☃
      );
      ☃.close();
      field_208324_a.debug(
         "Caching: cache hits: {}, created: {} removed: {}", this.field_208327_d, this.field_208329_f.size() - this.field_208327_d, this.field_208328_e.size()
      );
   }

   @Nullable
   public String func_208323_a(Path var1) {
      return (String)this.field_208328_e.get(☃);
   }

   public void func_208316_a(Path var1, String var2) {
      this.field_208329_f.put(☃, ☃);
      if (Objects.equals(this.field_208328_e.remove(☃), ☃)) {
         ++this.field_208327_d;
      }
   }

   public boolean func_208320_b(Path var1) {
      return this.field_208328_e.containsKey(☃);
   }

   private void func_209400_b() throws IOException {
      this.func_209398_c().forEach(var1 -> {
         if (this.func_208320_b(var1)) {
            try {
               Files.delete(var1);
            } catch (IOException var3) {
               field_208324_a.debug("Unable to delete: {} ({})", var1, var3.toString());
            }
         }
      });
   }

   private Stream<Path> func_209398_c() throws IOException {
      return Files.walk(this.field_208325_b).filter(var1 -> !Objects.equals(this.field_208326_c, var1) && !Files.isDirectory(var1, new LinkOption[0]));
   }
}
