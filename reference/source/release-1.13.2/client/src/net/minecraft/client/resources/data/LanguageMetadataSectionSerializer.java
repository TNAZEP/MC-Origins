package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.resources.Language;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;

public class LanguageMetadataSectionSerializer implements IMetadataSectionSerializer<LanguageMetadataSection> {
   public LanguageMetadataSection func_195812_a(JsonObject var1) {
      Set<Language> ☃ = Sets.<Language>newHashSet();

      for(Entry<String, JsonElement> ☃x : ☃.entrySet()) {
         String ☃xx = (String)☃x.getKey();
         if (☃xx.length() > 16) {
            throw new JsonParseException("Invalid language->'" + ☃xx + "': language code must not be more than " + 16 + " characters long");
         }

         JsonObject ☃xx = JsonUtils.func_151210_l((JsonElement)☃x.getValue(), "language");
         String ☃xxx = JsonUtils.func_151200_h(☃xx, "region");
         String ☃xxxx = JsonUtils.func_151200_h(☃xx, "name");
         boolean ☃xxxxx = JsonUtils.func_151209_a(☃xx, "bidirectional", false);
         if (☃xxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + ☃xx + "'->region: empty value");
         }

         if (☃xxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + ☃xx + "'->name: empty value");
         }

         if (!☃.add(new Language(☃xx, ☃xxx, ☃xxxx, ☃xxxxx))) {
            throw new JsonParseException("Duplicate language->'" + ☃xx + "' defined");
         }
      }

      return new LanguageMetadataSection(☃);
   }

   @Override
   public String func_110483_a() {
      return "language";
   }
}
