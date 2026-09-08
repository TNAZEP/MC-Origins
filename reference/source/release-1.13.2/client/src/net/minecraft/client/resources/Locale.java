package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Locale {
   private static final Gson field_200700_b = new Gson();
   private static final Logger field_199755_b = LogManager.getLogger();
   private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   Map<String, String> field_135032_a = Maps.newHashMap();

   public synchronized void func_195811_a(IResourceManager var1, List<String> var2) {
      this.field_135032_a.clear();

      for(String ☃ : ☃) {
         String ☃x = String.format("lang/%s.json", ☃);

         for(String ☃xx : ☃.func_199001_a()) {
            try {
               ResourceLocation ☃xxx = new ResourceLocation(☃xx, ☃x);
               this.func_135028_a(☃.func_199004_b(☃xxx));
            } catch (FileNotFoundException var9) {
            } catch (Exception var10) {
               field_199755_b.warn("Skipped language file: {}:{} ({})", ☃xx, ☃x, var10.toString());
            }
         }
      }
   }

   private void func_135028_a(List<IResource> var1) {
      for(IResource ☃ : ☃) {
         InputStream ☃x = ☃.func_199027_b();

         try {
            this.func_135021_a(☃x);
         } finally {
            IOUtils.closeQuietly(☃x);
         }
      }
   }

   private void func_135021_a(InputStream var1) {
      JsonElement ☃ = field_200700_b.fromJson(new InputStreamReader(☃, StandardCharsets.UTF_8), JsonElement.class);
      JsonObject ☃x = JsonUtils.func_151210_l(☃, "strings");

      for(Entry<String, JsonElement> ☃xx : ☃x.entrySet()) {
         String ☃xxx = field_135031_c.matcher(JsonUtils.func_151206_a((JsonElement)☃xx.getValue(), (String)☃xx.getKey())).replaceAll("%$1s");
         this.field_135032_a.put(☃xx.getKey(), ☃xxx);
      }
   }

   private String func_135026_c(String var1) {
      String ☃ = (String)this.field_135032_a.get(☃);
      return ☃ == null ? ☃ : ☃;
   }

   public String func_135023_a(String var1, Object[] var2) {
      String ☃ = this.func_135026_c(☃);

      try {
         return String.format(☃, ☃);
      } catch (IllegalFormatException var5) {
         return "Format error: " + ☃;
      }
   }

   public boolean func_188568_a(String var1) {
      return this.field_135032_a.containsKey(☃);
   }
}
