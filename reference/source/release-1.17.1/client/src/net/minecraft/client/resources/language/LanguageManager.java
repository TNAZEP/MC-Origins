package net.minecraft.client.resources.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;
import net.minecraft.client.resources.metadata.language.LanguageMetadataSection;
import net.minecraft.locale.Language;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements ResourceManagerReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final String DEFAULT_LANGUAGE_CODE = "en_us";
   private static final LanguageInfo DEFAULT_LANGUAGE = new LanguageInfo("en_us", "US", "English", false);
   private Map<String, LanguageInfo> languages = ImmutableMap.of("en_us", DEFAULT_LANGUAGE);
   private String currentCode;
   private LanguageInfo currentLanguage = DEFAULT_LANGUAGE;

   public LanguageManager(String var1) {
      this.currentCode = â˜ƒ;
   }

   private static Map<String, LanguageInfo> extractLanguages(Stream<PackResources> var0) {
      Map<String, LanguageInfo> â˜ƒ = Maps.newHashMap();
      â˜ƒ.forEach(var1x -> {
         try {
            LanguageMetadataSection â˜ƒ = var1x.getMetadataSection(LanguageMetadataSection.SERIALIZER);
            if (â˜ƒ != null) {
               for(LanguageInfo â˜ƒx : â˜ƒ.getLanguages()) {
                  â˜ƒ.putIfAbsent(â˜ƒx.getCode(), â˜ƒx);
               }
            }
         } catch (IOException | RuntimeException var5) {
            LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", var1x.getName(), var5);
         }
      });
      return ImmutableMap.copyOf(â˜ƒ);
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      this.languages = extractLanguages(â˜ƒ.listPacks());
      LanguageInfo â˜ƒ = (LanguageInfo)this.languages.getOrDefault("en_us", DEFAULT_LANGUAGE);
      this.currentLanguage = (LanguageInfo)this.languages.getOrDefault(this.currentCode, â˜ƒ);
      List<LanguageInfo> â˜ƒx = Lists.<LanguageInfo>newArrayList(â˜ƒ);
      if (this.currentLanguage != â˜ƒ) {
         â˜ƒx.add(this.currentLanguage);
      }

      ClientLanguage â˜ƒ = ClientLanguage.loadFrom(â˜ƒ, â˜ƒx);
      I18n.setLanguage(â˜ƒ);
      Language.inject(â˜ƒ);
   }

   public void setSelected(LanguageInfo var1) {
      this.currentCode = â˜ƒ.getCode();
      this.currentLanguage = â˜ƒ;
   }

   public LanguageInfo getSelected() {
      return this.currentLanguage;
   }

   public SortedSet<LanguageInfo> getLanguages() {
      return Sets.<LanguageInfo>newTreeSet(this.languages.values());
   }

   public LanguageInfo getLanguage(String var1) {
      return (LanguageInfo)this.languages.get(â˜ƒ);
   }
}
