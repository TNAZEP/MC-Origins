package net.minecraft.client.resources.metadata.language;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class LanguageMetadataSectionSerializer implements MetadataSectionSerializer<LanguageMetadataSection> {
   private static final int MAX_LANGUAGE_LENGTH = 16;

   public LanguageMetadataSection fromJson(JsonObject var1) {
      Set<LanguageInfo> â˜ƒ = Sets.<LanguageInfo>newHashSet();

      for(Entry<String, JsonElement> â˜ƒx : â˜ƒ.entrySet()) {
         String â˜ƒxx = (String)â˜ƒx.getKey();
         if (â˜ƒxx.length() > 16) {
            throw new JsonParseException("Invalid language->'" + â˜ƒxx + "': language code must not be more than 16 characters long");
         }

         JsonObject â˜ƒxx = GsonHelper.convertToJsonObject((JsonElement)â˜ƒx.getValue(), "language");
         String â˜ƒxxx = GsonHelper.getAsString(â˜ƒxx, "region");
         String â˜ƒxxxx = GsonHelper.getAsString(â˜ƒxx, "name");
         boolean â˜ƒxxxxx = GsonHelper.getAsBoolean(â˜ƒxx, "bidirectional", false);
         if (â˜ƒxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + â˜ƒxx + "'->region: empty value");
         }

         if (â˜ƒxxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + â˜ƒxx + "'->name: empty value");
         }

         if (!â˜ƒ.add(new LanguageInfo(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx))) {
            throw new JsonParseException("Duplicate language->'" + â˜ƒxx + "' defined");
         }
      }

      return new LanguageMetadataSection(â˜ƒ);
   }

   @Override
   public String getMetadataSectionName() {
      return "language";
   }
}
