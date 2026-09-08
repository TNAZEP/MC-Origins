package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagLoader<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final String PATH_SUFFIX = ".json";
   private static final int PATH_SUFFIX_LENGTH = ".json".length();
   private final Function<ResourceLocation, Optional<T>> idToValue;
   private final String directory;

   public TagLoader(Function<ResourceLocation, Optional<T>> var1, String var2) {
      this.idToValue = â˜ƒ;
      this.directory = â˜ƒ;
   }

   public Map<ResourceLocation, Tag.Builder> load(ResourceManager var1) {
      Map<ResourceLocation, Tag.Builder> â˜ƒ = Maps.<ResourceLocation, Tag.Builder>newHashMap();

      for(ResourceLocation â˜ƒx : â˜ƒ.listResources(this.directory, var0 -> var0.endsWith(".json"))) {
         String â˜ƒxx = â˜ƒx.getPath();
         ResourceLocation â˜ƒxxx = new ResourceLocation(â˜ƒx.getNamespace(), â˜ƒxx.substring(this.directory.length() + 1, â˜ƒxx.length() - PATH_SUFFIX_LENGTH));

         try {
            for(Resource â˜ƒxxxx : â˜ƒ.getResources(â˜ƒx)) {
               try {
                  InputStream â˜ƒ;
                  try {
                     â˜ƒ = â˜ƒxxxx.getInputStream();

                     try {
                        Reader â˜ƒxxxxx = new BufferedReader(new InputStreamReader(â˜ƒ, StandardCharsets.UTF_8));

                        try {
                           JsonObject â˜ƒxxxxxx = GsonHelper.fromJson(GSON, â˜ƒxxxxx, JsonObject.class);
                           if (â˜ƒxxxxxx == null) {
                              LOGGER.error("Couldn't load tag list {} from {} in data pack {} as it is empty or null", â˜ƒxxx, â˜ƒx, â˜ƒxxxx.getSourceName());
                           } else {
                              ((Tag.Builder)â˜ƒ.computeIfAbsent(â˜ƒxxx, var0 -> Tag.Builder.tag())).addFromJson(â˜ƒxxxxxx, â˜ƒxxxx.getSourceName());
                           }
                        } catch (Throwable var23) {
                           try {
                              â˜ƒxxxxx.close();
                           } catch (Throwable var22) {
                              var23.addSuppressed(var22);
                           }

                           throw var23;
                        }

                        â˜ƒxxxxx.close();
                     } catch (Throwable var24) {
                        if (â˜ƒ != null) {
                           try {
                              â˜ƒ.close();
                           } catch (Throwable var21) {
                              var24.addSuppressed(var21);
                           }
                        }

                        throw var24;
                     }

                     if (â˜ƒ != null) {
                        â˜ƒ.close();
                     }
                  } catch (RuntimeException | IOException var25) {
                     â˜ƒ = var25;
                     LOGGER.error("Couldn't read tag list {} from {} in data pack {}", â˜ƒxxx, â˜ƒx, â˜ƒxxxx.getSourceName(), var25);
                  }
               } finally {
                  IOUtils.closeQuietly(â˜ƒxxxx);
               }
            }
         } catch (IOException var27) {
            LOGGER.error("Couldn't read tag list {} from {}", â˜ƒxxx, â˜ƒx, var27);
         }
      }

      return â˜ƒ;
   }

   private static void visitDependenciesAndElement(
      Map<ResourceLocation, Tag.Builder> var0,
      Multimap<ResourceLocation, ResourceLocation> var1,
      Set<ResourceLocation> var2,
      ResourceLocation var3,
      BiConsumer<ResourceLocation, Tag.Builder> var4
   ) {
      if (â˜ƒ.add(â˜ƒ)) {
         â˜ƒ.get(â˜ƒ).forEach(var4x -> visitDependenciesAndElement(â˜ƒ, â˜ƒ, â˜ƒ, var4x, â˜ƒ));
         Tag.Builder â˜ƒ = (Tag.Builder)â˜ƒ.get(â˜ƒ);
         if (â˜ƒ != null) {
            â˜ƒ.accept(â˜ƒ, â˜ƒ);
         }
      }
   }

   private static boolean isCyclic(Multimap<ResourceLocation, ResourceLocation> var0, ResourceLocation var1, ResourceLocation var2) {
      Collection<ResourceLocation> â˜ƒ = â˜ƒ.get(â˜ƒ);
      return â˜ƒ.contains(â˜ƒ) ? true : â˜ƒ.stream().anyMatch(var2x -> isCyclic(â˜ƒ, â˜ƒ, var2x));
   }

   private static void addDependencyIfNotCyclic(Multimap<ResourceLocation, ResourceLocation> var0, ResourceLocation var1, ResourceLocation var2) {
      if (!isCyclic(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.put(â˜ƒ, â˜ƒ);
      }
   }

   public TagCollection<T> build(Map<ResourceLocation, Tag.Builder> var1) {
      Map<ResourceLocation, Tag<T>> â˜ƒ = Maps.<ResourceLocation, Tag<T>>newHashMap();
      Function<ResourceLocation, Tag<T>> â˜ƒx = â˜ƒ::get;
      Function<ResourceLocation, T> â˜ƒxx = var1x -> ((Optional)this.idToValue.apply(var1x)).orElse(null);
      Multimap<ResourceLocation, ResourceLocation> â˜ƒxxx = HashMultimap.create();
      â˜ƒ.forEach((var1x, var2x) -> var2x.visitRequiredDependencies(var2xx -> addDependencyIfNotCyclic(â˜ƒ, var1x, var2xx)));
      â˜ƒ.forEach((var1x, var2x) -> var2x.visitOptionalDependencies(var2xx -> addDependencyIfNotCyclic(â˜ƒ, var1x, var2xx)));
      Set<ResourceLocation> â˜ƒxxxx = Sets.<ResourceLocation>newHashSet();
      â˜ƒ.keySet()
         .forEach(
            var6x -> visitDependenciesAndElement(
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒ,
                  var6x,
                  (var3x, var4x) -> var4x.build(â˜ƒ, â˜ƒ)
                        .ifLeft(
                           var1x -> LOGGER.error(
                                 "Couldn't load tag {} as it is missing following references: {}",
                                 var3x,
                                 var1x.stream().map(Objects::toString).collect(Collectors.joining(","))
                              )
                        )
                        .ifRight(var2x -> â˜ƒ.put(var3x, var2x))
               )
         );
      return TagCollection.of(â˜ƒ);
   }

   public TagCollection<T> loadAndBuild(ResourceManager var1) {
      return this.build(this.load(â˜ƒ));
   }
}
