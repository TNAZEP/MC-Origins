package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssetIndex {
   protected static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, File> rootFiles = Maps.newHashMap();
   private final Map<ResourceLocation, File> namespacedFiles = Maps.newHashMap();

   protected AssetIndex() {
   }

   public AssetIndex(File var1, String var2) {
      File â˜ƒ = new File(â˜ƒ, "objects");
      File â˜ƒx = new File(â˜ƒ, "indexes/" + â˜ƒ + ".json");
      BufferedReader â˜ƒxx = null;

      try {
         â˜ƒxx = Files.newReader(â˜ƒx, StandardCharsets.UTF_8);
         JsonObject â˜ƒxxx = GsonHelper.parse(â˜ƒxx);
         JsonObject â˜ƒxxxx = GsonHelper.getAsJsonObject(â˜ƒxxx, "objects", null);
         if (â˜ƒxxxx != null) {
            for(Entry<String, JsonElement> â˜ƒxxxxx : â˜ƒxxxx.entrySet()) {
               JsonObject â˜ƒxxxxxx = (JsonObject)â˜ƒxxxxx.getValue();
               String â˜ƒxxxxxxx = (String)â˜ƒxxxxx.getKey();
               String[] â˜ƒxxxxxxxx = â˜ƒxxxxxxx.split("/", 2);
               String â˜ƒxxxxxxxxx = GsonHelper.getAsString(â˜ƒxxxxxx, "hash");
               File â˜ƒxxxxxxxxxx = new File(â˜ƒ, â˜ƒxxxxxxxxx.substring(0, 2) + "/" + â˜ƒxxxxxxxxx);
               if (â˜ƒxxxxxxxx.length == 1) {
                  this.rootFiles.put(â˜ƒxxxxxxxx[0], â˜ƒxxxxxxxxxx);
               } else {
                  this.namespacedFiles.put(new ResourceLocation(â˜ƒxxxxxxxx[0], â˜ƒxxxxxxxx[1]), â˜ƒxxxxxxxxxx);
               }
            }
         }
      } catch (JsonParseException var19) {
         LOGGER.error("Unable to parse resource index file: {}", â˜ƒx);
      } catch (FileNotFoundException var20) {
         LOGGER.error("Can't find the resource index file: {}", â˜ƒx);
      } finally {
         IOUtils.closeQuietly(â˜ƒxx);
      }
   }

   @Nullable
   public File getFile(ResourceLocation var1) {
      return (File)this.namespacedFiles.get(â˜ƒ);
   }

   @Nullable
   public File getRootFile(String var1) {
      return (File)this.rootFiles.get(â˜ƒ);
   }

   public Collection<ResourceLocation> getFiles(String var1, String var2, int var3, Predicate<String> var4) {
      return (Collection<ResourceLocation>)this.namespacedFiles.keySet().stream().filter(var3x -> {
         String â˜ƒ = var3x.getPath();
         return var3x.getNamespace().equals(â˜ƒ) && !â˜ƒ.endsWith(".mcmeta") && â˜ƒ.startsWith(â˜ƒ + "/") && â˜ƒ.test(â˜ƒ);
      }).collect(Collectors.toList());
   }
}
