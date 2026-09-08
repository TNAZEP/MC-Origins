package net.minecraft.data;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashCache {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Path path;
   private final Path cachePath;
   private int hits;
   private final Map<Path, String> oldCache = Maps.newHashMap();
   private final Map<Path, String> newCache = Maps.newHashMap();
   private final Set<Path> keep = Sets.newHashSet();

   public HashCache(Path var1, String var2) throws IOException {
      this.path = â˜ƒ;
      Path â˜ƒ = â˜ƒ.resolve(".cache");
      Files.createDirectories(â˜ƒ);
      this.cachePath = â˜ƒ.resolve(â˜ƒ);
      this.walkOutputFiles().forEach(var1x -> this.oldCache.put(var1x, ""));
      if (Files.isReadable(this.cachePath)) {
         IOUtils.readLines(Files.newInputStream(this.cachePath), Charsets.UTF_8).forEach(var2x -> {
            int â˜ƒ = var2x.indexOf(32);
            this.oldCache.put(â˜ƒ.resolve(var2x.substring(â˜ƒ + 1)), var2x.substring(0, â˜ƒ));
         });
      }
   }

   public void purgeStaleAndWrite() throws IOException {
      this.removeStale();

      Writer â˜ƒ;
      try {
         â˜ƒ = Files.newBufferedWriter(this.cachePath);
      } catch (IOException var3) {
         LOGGER.warn("Unable write cachefile {}: {}", this.cachePath, var3.toString());
         return;
      }

      IOUtils.writeLines(
         (Collection<?>)this.newCache
            .entrySet()
            .stream()
            .map(var1x -> (String)var1x.getValue() + " " + this.path.relativize((Path)var1x.getKey()))
            .collect(Collectors.toList()),
         System.lineSeparator(),
         â˜ƒ
      );
      â˜ƒ.close();
      LOGGER.debug("Caching: cache hits: {}, created: {} removed: {}", this.hits, this.newCache.size() - this.hits, this.oldCache.size());
   }

   @Nullable
   public String getHash(Path var1) {
      return (String)this.oldCache.get(â˜ƒ);
   }

   public void putNew(Path var1, String var2) {
      this.newCache.put(â˜ƒ, â˜ƒ);
      if (Objects.equals(this.oldCache.remove(â˜ƒ), â˜ƒ)) {
         ++this.hits;
      }
   }

   public boolean had(Path var1) {
      return this.oldCache.containsKey(â˜ƒ);
   }

   public void keep(Path var1) {
      this.keep.add(â˜ƒ);
   }

   private void removeStale() throws IOException {
      this.walkOutputFiles().forEach(var1 -> {
         if (this.had(var1) && !this.keep.contains(var1)) {
            try {
               Files.delete(var1);
            } catch (IOException var3) {
               LOGGER.debug("Unable to delete: {} ({})", var1, var3.toString());
            }
         }
      });
   }

   private Stream<Path> walkOutputFiles() throws IOException {
      return Files.walk(this.path).filter(var1 -> !Objects.equals(this.cachePath, var1) && !Files.isDirectory(var1, new LinkOption[0]));
   }
}
