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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
   protected static final Logger field_152783_a = LogManager.getLogger();
   private final Map<String, File> field_152784_b = Maps.newHashMap();

   protected ResourceIndex() {
   }

   public ResourceIndex(File var1, String var2) {
      File ☃ = new File(☃, "objects");
      File ☃x = new File(☃, "indexes/" + ☃ + ".json");
      BufferedReader ☃xx = null;

      try {
         ☃xx = Files.newReader(☃x, StandardCharsets.UTF_8);
         JsonObject ☃xxx = JsonUtils.func_212743_a(☃xx);
         JsonObject ☃xxxx = JsonUtils.func_151218_a(☃xxx, "objects", null);
         if (☃xxxx != null) {
            for(Entry<String, JsonElement> ☃xxxxx : ☃xxxx.entrySet()) {
               JsonObject ☃xxxxxx = (JsonObject)☃xxxxx.getValue();
               String ☃xxxxxxx = (String)☃xxxxx.getKey();
               String[] ☃xxxxxxxx = ☃xxxxxxx.split("/", 2);
               String ☃xxxxxxxxx = ☃xxxxxxxx.length == 1 ? ☃xxxxxxxx[0] : ☃xxxxxxxx[0] + ":" + ☃xxxxxxxx[1];
               String ☃xxxxxxxxxx = JsonUtils.func_151200_h(☃xxxxxx, "hash");
               File ☃xxxxxxxxxxx = new File(☃, ☃xxxxxxxxxx.substring(0, 2) + "/" + ☃xxxxxxxxxx);
               this.field_152784_b.put(☃xxxxxxxxx, ☃xxxxxxxxxxx);
            }
         }
      } catch (JsonParseException var20) {
         field_152783_a.error("Unable to parse resource index file: {}", ☃x);
      } catch (FileNotFoundException var21) {
         field_152783_a.error("Can't find the resource index file: {}", ☃x);
      } finally {
         IOUtils.closeQuietly(☃xx);
      }
   }

   @Nullable
   public File func_188547_a(ResourceLocation var1) {
      String ☃ = ☃.toString();
      return (File)this.field_152784_b.get(☃);
   }

   @Nullable
   public File func_200009_a(String var1) {
      return (File)this.field_152784_b.get(☃);
   }

   public Collection<String> func_211685_a(String var1, int var2, Predicate<String> var3) {
      return (Collection<String>)this.field_152784_b
         .keySet()
         .stream()
         .filter(var0 -> !var0.endsWith(".mcmeta"))
         .map(ResourceLocation::new)
         .map(ResourceLocation::func_110623_a)
         .filter(var1x -> var1x.startsWith(☃ + "/"))
         .filter(☃)
         .collect(Collectors.toList());
   }
}
