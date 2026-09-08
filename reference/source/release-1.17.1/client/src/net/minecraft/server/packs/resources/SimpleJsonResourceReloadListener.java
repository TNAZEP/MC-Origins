package net.minecraft.server.packs.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SimpleJsonResourceReloadListener extends SimplePreparableReloadListener<Map<ResourceLocation, JsonElement>> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String PATH_SUFFIX = ".json";
   private static final int PATH_SUFFIX_LENGTH = ".json".length();
   private final Gson gson;
   private final String directory;

   public SimpleJsonResourceReloadListener(Gson var1, String var2) {
      this.gson = â˜ƒ;
      this.directory = â˜ƒ;
   }

   protected Map<ResourceLocation, JsonElement> prepare(ResourceManager var1, ProfilerFiller var2) {
      Map<ResourceLocation, JsonElement> â˜ƒ = Maps.<ResourceLocation, JsonElement>newHashMap();
      int â˜ƒx = this.directory.length() + 1;

      for(ResourceLocation â˜ƒxx : â˜ƒ.listResources(this.directory, var0 -> var0.endsWith(".json"))) {
         String â˜ƒxxx = â˜ƒxx.getPath();
         ResourceLocation â˜ƒxxxx = new ResourceLocation(â˜ƒxx.getNamespace(), â˜ƒxxx.substring(â˜ƒx, â˜ƒxxx.length() - PATH_SUFFIX_LENGTH));

         try {
            Resource â˜ƒxxxxx = â˜ƒ.getResource(â˜ƒxx);

            try {
               InputStream â˜ƒxxxxxx = â˜ƒxxxxx.getInputStream();

               try {
                  Reader â˜ƒxxxxxxx = new BufferedReader(new InputStreamReader(â˜ƒxxxxxx, StandardCharsets.UTF_8));

                  try {
                     JsonElement â˜ƒxxxxxxxx = GsonHelper.fromJson(this.gson, â˜ƒxxxxxxx, JsonElement.class);
                     if (â˜ƒxxxxxxxx != null) {
                        JsonElement â˜ƒxxxxxxxxx = (JsonElement)â˜ƒ.put(â˜ƒxxxx, â˜ƒxxxxxxxx);
                        if (â˜ƒxxxxxxxxx != null) {
                           throw new IllegalStateException("Duplicate data file ignored with ID " + â˜ƒxxxx);
                        }
                     } else {
                        LOGGER.error("Couldn't load data file {} from {} as it's null or empty", â˜ƒxxxx, â˜ƒxx);
                     }
                  } catch (Throwable var17) {
                     try {
                        â˜ƒxxxxxxx.close();
                     } catch (Throwable var16) {
                        var17.addSuppressed(var16);
                     }

                     throw var17;
                  }

                  â˜ƒxxxxxxx.close();
               } catch (Throwable var18) {
                  if (â˜ƒxxxxxx != null) {
                     try {
                        â˜ƒxxxxxx.close();
                     } catch (Throwable var15) {
                        var18.addSuppressed(var15);
                     }
                  }

                  throw var18;
               }

               if (â˜ƒxxxxxx != null) {
                  â˜ƒxxxxxx.close();
               }
            } catch (Throwable var19) {
               if (â˜ƒxxxxx != null) {
                  try {
                     â˜ƒxxxxx.close();
                  } catch (Throwable var14) {
                     var19.addSuppressed(var14);
                  }
               }

               throw var19;
            }

            if (â˜ƒxxxxx != null) {
               â˜ƒxxxxx.close();
            }
         } catch (IllegalArgumentException | IOException | JsonParseException var20) {
            LOGGER.error("Couldn't parse data file {} from {}", â˜ƒxxxx, â˜ƒxx, var20);
         }
      }

      return â˜ƒ;
   }
}
