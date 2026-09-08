package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.IResourcePack;
import net.minecraft.util.text.translation.LanguageMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements IResourceManagerReloadListener {
   private static final Logger field_147648_b = LogManager.getLogger();
   protected static final Locale field_135049_a = new Locale();
   private String field_135048_c;
   private final Map<String, Language> field_135046_d = Maps.newHashMap();

   public LanguageManager(String var1) {
      this.field_135048_c = ☃;
      I18n.func_135051_a(field_135049_a);
   }

   public void func_135043_a(List<IResourcePack> var1) {
      this.field_135046_d.clear();

      for(IResourcePack ☃ : ☃) {
         try {
            LanguageMetadataSection ☃x = ☃.func_195760_a(LanguageMetadataSection.field_195818_a);
            if (☃x != null) {
               for(Language ☃xx : ☃x.func_135018_a()) {
                  if (!this.field_135046_d.containsKey(☃xx.func_135034_a())) {
                     this.field_135046_d.put(☃xx.func_135034_a(), ☃xx);
                  }
               }
            }
         } catch (IOException | RuntimeException var7) {
            field_147648_b.warn("Unable to parse language metadata section of resourcepack: {}", ☃.func_195762_a(), var7);
         }
      }
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      List<String> ☃ = Lists.newArrayList("en_us");
      if (!"en_us".equals(this.field_135048_c)) {
         ☃.add(this.field_135048_c);
      }

      field_135049_a.func_195811_a(☃, ☃);
      LanguageMap.func_135063_a(field_135049_a.field_135032_a);
   }

   public boolean func_135044_b() {
      return this.func_135041_c() != null && this.func_135041_c().func_135035_b();
   }

   public void func_135045_a(Language var1) {
      this.field_135048_c = ☃.func_135034_a();
   }

   public Language func_135041_c() {
      String ☃ = this.field_135046_d.containsKey(this.field_135048_c) ? this.field_135048_c : "en_us";
      return (Language)this.field_135046_d.get(☃);
   }

   public SortedSet<Language> func_135040_d() {
      return Sets.<Language>newTreeSet(this.field_135046_d.values());
   }

   public Language func_191960_a(String var1) {
      return (Language)this.field_135046_d.get(☃);
   }
}
