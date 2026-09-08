package net.minecraft.client.gui.fonts.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.util.Util;

public enum GlyphProviderTypes {
   BITMAP("bitmap", TextureGlyphProvider.Factory::func_211633_a),
   TTF("ttf", TrueTypeGlyphProvider.Factory::func_211624_a),
   LEGACY_UNICODE("legacy_unicode", TextureGlyphProviderUnicode.Factory::func_211629_a);

   private static final Map<String, GlyphProviderTypes> field_211640_d = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      for(GlyphProviderTypes ☃ : values()) {
         var0.put(☃.field_211641_e, ☃);
      }
   });
   private final String field_211641_e;
   private final Function<JsonObject, IGlyphProviderFactory> field_211642_f;

   private GlyphProviderTypes(String var3, Function<JsonObject, IGlyphProviderFactory> var4) {
      this.field_211641_e = ☃;
      this.field_211642_f = ☃;
   }

   public static GlyphProviderTypes func_211638_a(String var0) {
      GlyphProviderTypes ☃ = (GlyphProviderTypes)field_211640_d.get(☃);
      if (☃ == null) {
         throw new IllegalArgumentException("Invalid type: " + ☃);
      } else {
         return ☃;
      }
   }

   public IGlyphProviderFactory func_211637_a(JsonObject var1) {
      return (IGlyphProviderFactory)this.field_211642_f.apply(☃);
   }
}
