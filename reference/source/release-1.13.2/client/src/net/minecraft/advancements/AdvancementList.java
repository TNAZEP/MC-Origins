package net.minecraft.advancements;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementList {
   private static final Logger field_192091_a = LogManager.getLogger();
   private final Map<ResourceLocation, Advancement> field_192092_b = Maps.<ResourceLocation, Advancement>newHashMap();
   private final Set<Advancement> field_192093_c = Sets.<Advancement>newLinkedHashSet();
   private final Set<Advancement> field_192094_d = Sets.<Advancement>newLinkedHashSet();
   private AdvancementList.Listener field_192095_e;

   private void func_192090_a(Advancement var1) {
      for(Advancement ☃ : ☃.func_192069_e()) {
         this.func_192090_a(☃);
      }

      field_192091_a.info("Forgot about advancement {}", ☃.func_192067_g());
      this.field_192092_b.remove(☃.func_192067_g());
      if (☃.func_192070_b() == null) {
         this.field_192093_c.remove(☃);
         if (this.field_192095_e != null) {
            this.field_192095_e.func_191928_b(☃);
         }
      } else {
         this.field_192094_d.remove(☃);
         if (this.field_192095_e != null) {
            this.field_192095_e.func_191929_d(☃);
         }
      }
   }

   public void func_192085_a(Set<ResourceLocation> var1) {
      for(ResourceLocation ☃ : ☃) {
         Advancement ☃x = (Advancement)this.field_192092_b.get(☃);
         if (☃x == null) {
            field_192091_a.warn("Told to remove advancement {} but I don't know what that is", ☃);
         } else {
            this.func_192090_a(☃x);
         }
      }
   }

   public void func_192083_a(Map<ResourceLocation, Advancement.Builder> var1) {
      Function<ResourceLocation, Advancement> ☃ = Functions.forMap(this.field_192092_b, null);

      while(!☃.isEmpty()) {
         boolean ☃x = false;
         Iterator<Entry<ResourceLocation, Advancement.Builder>> ☃xx = ☃.entrySet().iterator();

         while(☃xx.hasNext()) {
            Entry<ResourceLocation, Advancement.Builder> ☃xxx = (Entry)☃xx.next();
            ResourceLocation ☃xxxx = (ResourceLocation)☃xxx.getKey();
            Advancement.Builder ☃xxxxx = (Advancement.Builder)☃xxx.getValue();
            if (☃xxxxx.func_192058_a(☃)) {
               Advancement ☃xxxxxx = ☃xxxxx.func_192056_a(☃xxxx);
               this.field_192092_b.put(☃xxxx, ☃xxxxxx);
               ☃x = true;
               ☃xx.remove();
               if (☃xxxxxx.func_192070_b() == null) {
                  this.field_192093_c.add(☃xxxxxx);
                  if (this.field_192095_e != null) {
                     this.field_192095_e.func_191931_a(☃xxxxxx);
                  }
               } else {
                  this.field_192094_d.add(☃xxxxxx);
                  if (this.field_192095_e != null) {
                     this.field_192095_e.func_191932_c(☃xxxxxx);
                  }
               }
            }
         }

         if (!☃x) {
            for(Entry<ResourceLocation, Advancement.Builder> ☃xxx : ☃.entrySet()) {
               field_192091_a.error("Couldn't load advancement {}: {}", ☃xxx.getKey(), ☃xxx.getValue());
            }
            break;
         }
      }

      field_192091_a.info("Loaded {} advancements", this.field_192092_b.size());
   }

   public void func_192087_a() {
      this.field_192092_b.clear();
      this.field_192093_c.clear();
      this.field_192094_d.clear();
      if (this.field_192095_e != null) {
         this.field_192095_e.func_191930_a();
      }
   }

   public Iterable<Advancement> func_192088_b() {
      return this.field_192093_c;
   }

   public Collection<Advancement> func_195651_c() {
      return this.field_192092_b.values();
   }

   @Nullable
   public Advancement func_192084_a(ResourceLocation var1) {
      return (Advancement)this.field_192092_b.get(☃);
   }

   public void func_192086_a(@Nullable AdvancementList.Listener var1) {
      this.field_192095_e = ☃;
      if (☃ != null) {
         for(Advancement ☃ : this.field_192093_c) {
            ☃.func_191931_a(☃);
         }

         for(Advancement ☃ : this.field_192094_d) {
            ☃.func_191932_c(☃);
         }
      }
   }

   public interface Listener {
      void func_191931_a(Advancement var1);

      void func_191928_b(Advancement var1);

      void func_191932_c(Advancement var1);

      void func_191929_d(Advancement var1);

      void func_191930_a();
   }
}
