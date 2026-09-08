package net.minecraft.tags;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagCollection<T> {
   private static final Logger field_199918_a = LogManager.getLogger();
   private static final Gson field_199919_b = new Gson();
   private static final int field_199920_c = ".json".length();
   private final Map<ResourceLocation, Tag<T>> field_199921_d = Maps.<ResourceLocation, Tag<T>>newHashMap();
   private final Function<ResourceLocation, T> field_200040_e;
   private final Predicate<ResourceLocation> field_200156_f;
   private final String field_199923_f;
   private final boolean field_200041_g;
   private final String field_200157_i;

   public TagCollection(Predicate<ResourceLocation> var1, Function<ResourceLocation, T> var2, String var3, boolean var4, String var5) {
      this.field_200156_f = ☃;
      this.field_200040_e = ☃;
      this.field_199923_f = ☃;
      this.field_200041_g = ☃;
      this.field_200157_i = ☃;
   }

   public void func_199912_a(Tag<T> var1) {
      if (this.field_199921_d.containsKey(☃.func_199886_b())) {
         throw new IllegalArgumentException("Duplicate " + this.field_200157_i + " tag '" + ☃.func_199886_b() + "'");
      } else {
         this.field_199921_d.put(☃.func_199886_b(), ☃);
      }
   }

   @Nullable
   public Tag<T> func_199910_a(ResourceLocation var1) {
      return (Tag<T>)this.field_199921_d.get(☃);
   }

   public Tag<T> func_199915_b(ResourceLocation var1) {
      Tag<T> ☃ = (Tag)this.field_199921_d.get(☃);
      return ☃ == null ? new Tag<>(☃) : ☃;
   }

   public Collection<ResourceLocation> func_199908_a() {
      return this.field_199921_d.keySet();
   }

   public void func_199917_b() {
      this.field_199921_d.clear();
   }

   public void func_199909_a(IResourceManager var1) {
      Map<ResourceLocation, Tag.Builder<T>> ☃ = Maps.<ResourceLocation, Tag.Builder<T>>newHashMap();

      for(ResourceLocation ☃x : ☃.func_199003_a(this.field_199923_f, var0 -> var0.endsWith(".json"))) {
         String ☃xx = ☃x.func_110623_a();
         ResourceLocation ☃xxx = new ResourceLocation(☃x.func_110624_b(), ☃xx.substring(this.field_199923_f.length() + 1, ☃xx.length() - field_199920_c));

         try {
            for(IResource ☃xxxx : ☃.func_199004_b(☃x)) {
               try {
                  JsonObject ☃xxxxx = JsonUtils.func_188178_a(field_199919_b, IOUtils.toString(☃xxxx.func_199027_b(), StandardCharsets.UTF_8), JsonObject.class);
                  if (☃xxxxx == null) {
                     field_199918_a.error(
                        "Couldn't load {} tag list {} from {} in data pack {} as it's empty or null", this.field_200157_i, ☃xxx, ☃x, ☃xxxx.func_199026_d()
                     );
                  } else {
                     Tag.Builder<T> ☃xxxxx = (Tag.Builder)☃.getOrDefault(☃xxx, Tag.Builder.func_200047_a());
                     ☃xxxxx.func_200158_a(this.field_200156_f, this.field_200040_e, ☃xxxxx);
                     ☃.put(☃xxx, ☃xxxxx);
                  }
               } catch (RuntimeException | IOException var15) {
                  field_199918_a.error("Couldn't read {} tag list {} from {} in data pack {}", this.field_200157_i, ☃xxx, ☃x, ☃xxxx.func_199026_d(), var15);
               } finally {
                  IOUtils.closeQuietly(☃xxxx);
               }
            }
         } catch (IOException var17) {
            field_199918_a.error("Couldn't read {} tag list {} from {}", this.field_200157_i, ☃xxx, ☃x, var17);
         }
      }

      while(!☃.isEmpty()) {
         boolean ☃x = false;
         Iterator<Entry<ResourceLocation, Tag.Builder<T>>> ☃xx = ☃.entrySet().iterator();

         while(☃xx.hasNext()) {
            Entry<ResourceLocation, Tag.Builder<T>> ☃xxx = (Entry)☃xx.next();
            if (((Tag.Builder)☃xxx.getValue()).func_200160_a(this::func_199910_a)) {
               ☃x = true;
               this.func_199912_a(((Tag.Builder)☃xxx.getValue()).func_200051_a((ResourceLocation)☃xxx.getKey()));
               ☃xx.remove();
            }
         }

         if (!☃x) {
            for(Entry<ResourceLocation, Tag.Builder<T>> ☃xxx : ☃.entrySet()) {
               field_199918_a.error(
                  "Couldn't load {} tag {} as it either references another tag that doesn't exist, or ultimately references itself",
                  this.field_200157_i,
                  ☃xxx.getKey()
               );
            }
            break;
         }
      }

      for(Entry<ResourceLocation, Tag.Builder<T>> ☃x : ☃.entrySet()) {
         this.func_199912_a(((Tag.Builder)☃x.getValue()).func_200045_a(this.field_200041_g).func_200051_a((ResourceLocation)☃x.getKey()));
      }
   }

   public Map<ResourceLocation, Tag<T>> func_200039_c() {
      return this.field_199921_d;
   }
}
