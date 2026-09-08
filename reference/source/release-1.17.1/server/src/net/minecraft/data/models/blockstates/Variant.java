package net.minecraft.data.models.blockstates;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Variant implements Supplier<JsonElement> {
   private final Map<VariantProperty<?>, VariantProperty<?>.Value> values = Maps.<VariantProperty<?>, VariantProperty<?>.Value>newLinkedHashMap();

   public <T> Variant with(VariantProperty<T> var1, T var2) {
      VariantProperty<?>.Value â˜ƒ = (VariantProperty.Value)this.values.put(â˜ƒ, â˜ƒ.withValue(â˜ƒ));
      if (â˜ƒ != null) {
         throw new IllegalStateException("Replacing value of " + â˜ƒ + " with " + â˜ƒ);
      } else {
         return this;
      }
   }

   public static Variant variant() {
      return new Variant();
   }

   public static Variant merge(Variant var0, Variant var1) {
      Variant â˜ƒ = new Variant();
      â˜ƒ.values.putAll(â˜ƒ.values);
      â˜ƒ.values.putAll(â˜ƒ.values);
      return â˜ƒ;
   }

   public JsonElement get() {
      JsonObject â˜ƒ = new JsonObject();
      this.values.values().forEach(var1x -> var1x.addToVariant(â˜ƒ));
      return â˜ƒ;
   }

   public static JsonElement convertList(List<Variant> var0) {
      if (â˜ƒ.size() == 1) {
         return ((Variant)â˜ƒ.get(0)).get();
      } else {
         JsonArray â˜ƒ = new JsonArray();
         â˜ƒ.forEach(var1x -> â˜ƒ.add(var1x.get()));
         return â˜ƒ;
      }
   }
}
