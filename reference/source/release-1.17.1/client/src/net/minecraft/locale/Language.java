package net.minecraft.locale;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringDecomposer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Language {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final Pattern UNSUPPORTED_FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
   public static final String DEFAULT = "en_us";
   private static volatile Language instance = loadDefault();

   private static Language loadDefault() {
      Builder<String, String> â˜ƒ = ImmutableMap.builder();
      BiConsumer<String, String> â˜ƒx = â˜ƒ::put;
      String â˜ƒxx = "/assets/minecraft/lang/en_us.json";

      try {
         InputStream â˜ƒxxx = Language.class.getResourceAsStream("/assets/minecraft/lang/en_us.json");

         try {
            loadFromJson(â˜ƒxxx, â˜ƒx);
         } catch (Throwable var7) {
            if (â˜ƒxxx != null) {
               try {
                  â˜ƒxxx.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (â˜ƒxxx != null) {
            â˜ƒxxx.close();
         }
      } catch (JsonParseException | IOException var8) {
         LOGGER.error("Couldn't read strings from {}", "/assets/minecraft/lang/en_us.json", var8);
      }

      final Map<String, String> â˜ƒxxx = â˜ƒ.build();
      return new Language() {
         @Override
         public String getOrDefault(String var1) {
            return (String)â˜ƒ.getOrDefault(â˜ƒ, â˜ƒ);
         }

         @Override
         public boolean has(String var1) {
            return â˜ƒ.containsKey(â˜ƒ);
         }

         @Override
         public boolean isDefaultRightToLeft() {
            return false;
         }

         @Override
         public FormattedCharSequence getVisualOrder(FormattedText var1) {
            return var1x -> â˜ƒ.visit(
                     (var1xx, var2) -> StringDecomposer.iterateFormatted(var2, var1xx, var1x) ? Optional.empty() : FormattedText.STOP_ITERATION, Style.EMPTY
                  )
                  .isPresent();
         }
      };
   }

   public static void loadFromJson(InputStream var0, BiConsumer<String, String> var1) {
      JsonObject â˜ƒ = GSON.fromJson(new InputStreamReader(â˜ƒ, StandardCharsets.UTF_8), JsonObject.class);

      for(Entry<String, JsonElement> â˜ƒx : â˜ƒ.entrySet()) {
         String â˜ƒxx = UNSUPPORTED_FORMAT_PATTERN.matcher(GsonHelper.convertToString((JsonElement)â˜ƒx.getValue(), (String)â˜ƒx.getKey())).replaceAll("%$1s");
         â˜ƒ.accept((String)â˜ƒx.getKey(), â˜ƒxx);
      }
   }

   public static Language getInstance() {
      return instance;
   }

   public static void inject(Language var0) {
      instance = â˜ƒ;
   }

   public abstract String getOrDefault(String var1);

   public abstract boolean has(String var1);

   public abstract boolean isDefaultRightToLeft();

   public abstract FormattedCharSequence getVisualOrder(FormattedText var1);

   public List<FormattedCharSequence> getVisualOrder(List<FormattedText> var1) {
      return (List<FormattedCharSequence>)â˜ƒ.stream().map(this::getVisualOrder).collect(ImmutableList.toImmutableList());
   }
}
