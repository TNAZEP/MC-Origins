package net.minecraft.data.tags;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TagsProvider<T> implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   protected final DataGenerator generator;
   protected final Registry<T> registry;
   private final Map<ResourceLocation, Tag.Builder> builders = Maps.<ResourceLocation, Tag.Builder>newLinkedHashMap();

   protected TagsProvider(DataGenerator var1, Registry<T> var2) {
      this.generator = â˜ƒ;
      this.registry = â˜ƒ;
   }

   protected abstract void addTags();

   @Override
   public void run(HashCache var1) {
      this.builders.clear();
      this.addTags();
      this.builders
         .forEach(
            (var2, var3) -> {
               List<Tag.BuilderEntry> â˜ƒ = (List)var3.getEntries()
                  .filter(var1x -> !var1x.getEntry().verifyIfPresent(this.registry::containsKey, this.builders::containsKey))
                  .collect(Collectors.toList());
               if (!â˜ƒ.isEmpty()) {
                  throw new IllegalArgumentException(
                     String.format(
                        "Couldn't define tag %s as it is missing following references: %s",
                        var2,
                        â˜ƒ.stream().map(Objects::toString).collect(Collectors.joining(","))
                     )
                  );
               } else {
                  JsonObject â˜ƒ = var3.serializeToJson();
                  Path â˜ƒx = this.getPath(var2);
      
                  try {
                     String â˜ƒxx = GSON.toJson((JsonElement)â˜ƒ);
                     String â˜ƒxxx = SHA1.hashUnencodedChars(â˜ƒxx).toString();
                     if (!Objects.equals(â˜ƒ.getHash(â˜ƒx), â˜ƒxxx) || !Files.exists(â˜ƒx, new LinkOption[0])) {
                        Files.createDirectories(â˜ƒx.getParent());
                        BufferedWriter â˜ƒxxxx = Files.newBufferedWriter(â˜ƒx);
      
                        try {
                           â˜ƒxxxx.write(â˜ƒxx);
                        } catch (Throwable var13) {
                           if (â˜ƒxxxx != null) {
                              try {
                                 â˜ƒxxxx.close();
                              } catch (Throwable var12) {
                                 var13.addSuppressed(var12);
                              }
                           }
      
                           throw var13;
                        }
      
                        if (â˜ƒxxxx != null) {
                           â˜ƒxxxx.close();
                        }
                     }
      
                     â˜ƒ.putNew(â˜ƒx, â˜ƒxxx);
                  } catch (IOException var14) {
                     LOGGER.error("Couldn't save tags to {}", â˜ƒx, var14);
                  }
               }
            }
         );
   }

   protected abstract Path getPath(ResourceLocation var1);

   protected TagsProvider.TagAppender<T> tag(Tag.Named<T> var1) {
      Tag.Builder â˜ƒ = this.getOrCreateRawBuilder(â˜ƒ);
      return new TagsProvider.TagAppender<>(â˜ƒ, this.registry, "vanilla");
   }

   protected Tag.Builder getOrCreateRawBuilder(Tag.Named<T> var1) {
      return (Tag.Builder)this.builders.computeIfAbsent(â˜ƒ.getName(), var0 -> new Tag.Builder());
   }

   protected static class TagAppender<T> {
      private final Tag.Builder builder;
      private final Registry<T> registry;
      private final String source;

      TagAppender(Tag.Builder var1, Registry<T> var2, String var3) {
         this.builder = â˜ƒ;
         this.registry = â˜ƒ;
         this.source = â˜ƒ;
      }

      public TagsProvider.TagAppender<T> add(T var1) {
         this.builder.addElement(this.registry.getKey(â˜ƒ), this.source);
         return this;
      }

      public TagsProvider.TagAppender<T> addOptional(ResourceLocation var1) {
         this.builder.addOptionalElement(â˜ƒ, this.source);
         return this;
      }

      public TagsProvider.TagAppender<T> addTag(Tag.Named<T> var1) {
         this.builder.addTag(â˜ƒ.getName(), this.source);
         return this;
      }

      public TagsProvider.TagAppender<T> addOptionalTag(ResourceLocation var1) {
         this.builder.addOptionalTag(â˜ƒ, this.source);
         return this;
      }

      @SafeVarargs
      public final TagsProvider.TagAppender<T> add(T... var1) {
         Stream.of(â˜ƒ).map(this.registry::getKey).forEach(var1x -> this.builder.addElement(var1x, this.source));
         return this;
      }
   }
}
