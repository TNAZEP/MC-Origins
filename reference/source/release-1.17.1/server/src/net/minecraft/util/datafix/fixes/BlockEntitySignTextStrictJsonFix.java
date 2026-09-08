package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

public class BlockEntitySignTextStrictJsonFix extends NamedEntityFix {
   public static final Gson GSON = new GsonBuilder().registerTypeAdapter(Component.class, new JsonDeserializer<Component>() {
      public MutableComponent deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (â˜ƒ.isJsonPrimitive()) {
            return new TextComponent(â˜ƒ.getAsString());
         } else if (â˜ƒ.isJsonArray()) {
            JsonArray â˜ƒ = â˜ƒ.getAsJsonArray();
            MutableComponent â˜ƒx = null;

            for(JsonElement â˜ƒxx : â˜ƒ) {
               MutableComponent â˜ƒxxx = this.deserialize(â˜ƒxx, â˜ƒxx.getClass(), â˜ƒ);
               if (â˜ƒx == null) {
                  â˜ƒx = â˜ƒxxx;
               } else {
                  â˜ƒx.append(â˜ƒxxx);
               }
            }

            return â˜ƒx;
         } else {
            throw new JsonParseException("Don't know how to turn " + â˜ƒ + " into a Component");
         }
      }
   }).create();

   public BlockEntitySignTextStrictJsonFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "BlockEntitySignTextStrictJsonFix", References.BLOCK_ENTITY, "Sign");
   }

   private Dynamic<?> updateLine(Dynamic<?> var1, String var2) {
      String â˜ƒ = â˜ƒ.get(â˜ƒ).asString("");
      Component â˜ƒx = null;
      if (!"null".equals(â˜ƒ) && !StringUtils.isEmpty(â˜ƒ)) {
         if (â˜ƒ.charAt(0) == '"' && â˜ƒ.charAt(â˜ƒ.length() - 1) == '"' || â˜ƒ.charAt(0) == '{' && â˜ƒ.charAt(â˜ƒ.length() - 1) == '}') {
            try {
               â˜ƒx = GsonHelper.fromJson(GSON, â˜ƒ, Component.class, true);
               if (â˜ƒx == null) {
                  â˜ƒx = TextComponent.EMPTY;
               }
            } catch (JsonParseException var8) {
            }

            if (â˜ƒx == null) {
               try {
                  â˜ƒx = Component.Serializer.fromJson(â˜ƒ);
               } catch (JsonParseException var7) {
               }
            }

            if (â˜ƒx == null) {
               try {
                  â˜ƒx = Component.Serializer.fromJsonLenient(â˜ƒ);
               } catch (JsonParseException var6) {
               }
            }

            if (â˜ƒx == null) {
               â˜ƒx = new TextComponent(â˜ƒ);
            }
         } else {
            â˜ƒx = new TextComponent(â˜ƒ);
         }
      } else {
         â˜ƒx = TextComponent.EMPTY;
      }

      return â˜ƒ.set(â˜ƒ, â˜ƒ.createString(Component.Serializer.toJson(â˜ƒx)));
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      return â˜ƒ.update(DSL.remainderFinder(), var1x -> {
         var1x = this.updateLine(var1x, "Text1");
         var1x = this.updateLine(var1x, "Text2");
         var1x = this.updateLine(var1x, "Text3");
         return this.updateLine(var1x, "Text4");
      });
   }
}
