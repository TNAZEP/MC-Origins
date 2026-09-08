package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.Util;

public enum GlyphProviderBuilderType {
   BITMAP("bitmap", BitmapProvider.Builder::fromJson),
   TTF("ttf", TrueTypeGlyphProviderBuilder::fromJson),
   LEGACY_UNICODE("legacy_unicode", LegacyUnicodeBitmapsProvider.Builder::fromJson);

   private static final Map<String, GlyphProviderBuilderType> BY_NAME = Util.make(Maps.newHashMap(), var0 -> {
      for(GlyphProviderBuilderType â˜ƒ : values()) {
         var0.put(â˜ƒ.name, â˜ƒ);
      }
   });
   private final String name;
   private final Function<JsonObject, GlyphProviderBuilder> factory;

   private GlyphProviderBuilderType(String var3, Function<JsonObject, GlyphProviderBuilder> var4) {
      this.name = â˜ƒ;
      this.factory = â˜ƒ;
   }

   public static GlyphProviderBuilderType byName(String var0) {
      GlyphProviderBuilderType â˜ƒ = (GlyphProviderBuilderType)BY_NAME.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Invalid type: " + â˜ƒ);
      } else {
         return â˜ƒ;
      }
   }

   public GlyphProviderBuilder create(JsonObject var1) {
      return (GlyphProviderBuilder)this.factory.apply(â˜ƒ);
   }
}
