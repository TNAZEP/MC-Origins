package net.minecraft.world.level.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class GsonAdapterFactory {
   public static <E, T extends SerializerType<E>> GsonAdapterFactory.Builder<E, T> builder(Registry<T> var0, String var1, String var2, Function<E, T> var3) {
      return new GsonAdapterFactory.Builder<>(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static class Builder<E, T extends SerializerType<E>> {
      private final Registry<T> registry;
      private final String elementName;
      private final String typeKey;
      private final Function<E, T> typeGetter;
      @Nullable
      private Pair<T, GsonAdapterFactory.InlineSerializer<? extends E>> inlineType;
      @Nullable
      private T defaultType;

      Builder(Registry<T> var1, String var2, String var3, Function<E, T> var4) {
         this.registry = â˜ƒ;
         this.elementName = â˜ƒ;
         this.typeKey = â˜ƒ;
         this.typeGetter = â˜ƒ;
      }

      public GsonAdapterFactory.Builder<E, T> withInlineSerializer(T var1, GsonAdapterFactory.InlineSerializer<? extends E> var2) {
         this.inlineType = Pair.of(â˜ƒ, â˜ƒ);
         return this;
      }

      public GsonAdapterFactory.Builder<E, T> withDefaultType(T var1) {
         this.defaultType = â˜ƒ;
         return this;
      }

      public Object build() {
         return new GsonAdapterFactory.JsonAdapter<>(this.registry, this.elementName, this.typeKey, this.typeGetter, this.defaultType, this.inlineType);
      }
   }

   public interface InlineSerializer<T> {
      JsonElement serialize(T var1, JsonSerializationContext var2);

      T deserialize(JsonElement var1, JsonDeserializationContext var2);
   }

   static class JsonAdapter<E, T extends SerializerType<E>> implements JsonDeserializer<E>, JsonSerializer<E> {
      private final Registry<T> registry;
      private final String elementName;
      private final String typeKey;
      private final Function<E, T> typeGetter;
      @Nullable
      private final T defaultType;
      @Nullable
      private final Pair<T, GsonAdapterFactory.InlineSerializer<? extends E>> inlineType;

      JsonAdapter(
         Registry<T> var1,
         String var2,
         String var3,
         Function<E, T> var4,
         @Nullable T var5,
         @Nullable Pair<T, GsonAdapterFactory.InlineSerializer<? extends E>> var6
      ) {
         this.registry = â˜ƒ;
         this.elementName = â˜ƒ;
         this.typeKey = â˜ƒ;
         this.typeGetter = â˜ƒ;
         this.defaultType = â˜ƒ;
         this.inlineType = â˜ƒ;
      }

      @Override
      public E deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (â˜ƒ.isJsonObject()) {
            JsonObject â˜ƒx = GsonHelper.convertToJsonObject(â˜ƒ, this.elementName);
            String â˜ƒxx = GsonHelper.getAsString(â˜ƒx, this.typeKey, "");
            T â˜ƒ;
            if (â˜ƒxx.isEmpty()) {
               â˜ƒ = this.defaultType;
            } else {
               ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒxx);
               â˜ƒ = this.registry.get(â˜ƒ);
            }

            if (â˜ƒ == null) {
               throw new JsonSyntaxException("Unknown type '" + â˜ƒxx + "'");
            } else {
               return â˜ƒ.getSerializer().deserialize(â˜ƒx, â˜ƒ);
            }
         } else if (this.inlineType == null) {
            throw new UnsupportedOperationException("Object " + â˜ƒ + " can't be deserialized");
         } else {
            return this.inlineType.getSecond().deserialize(â˜ƒ, â˜ƒ);
         }
      }

      @Override
      public JsonElement serialize(E var1, Type var2, JsonSerializationContext var3) {
         T â˜ƒ = (T)this.typeGetter.apply(â˜ƒ);
         if (this.inlineType != null && this.inlineType.getFirst() == â˜ƒ) {
            return this.inlineType.getSecond().serialize(â˜ƒ, â˜ƒ);
         } else if (â˜ƒ == null) {
            throw new JsonSyntaxException("Unknown type: " + â˜ƒ);
         } else {
            JsonObject â˜ƒ = new JsonObject();
            â˜ƒ.addProperty(this.typeKey, this.registry.getKey(â˜ƒ).toString());
            â˜ƒ.getSerializer().serialize(â˜ƒ, â˜ƒ, â˜ƒ);
            return â˜ƒ;
         }
      }
   }
}
