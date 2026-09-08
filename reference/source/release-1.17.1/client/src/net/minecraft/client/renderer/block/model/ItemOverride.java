package net.minecraft.client.renderer.block.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ItemOverride {
   private final ResourceLocation model;
   private final List<ItemOverride.Predicate> predicates;

   public ItemOverride(ResourceLocation var1, List<ItemOverride.Predicate> var2) {
      this.model = â˜ƒ;
      this.predicates = ImmutableList.copyOf(â˜ƒ);
   }

   public ResourceLocation getModel() {
      return this.model;
   }

   public Stream<ItemOverride.Predicate> getPredicates() {
      return this.predicates.stream();
   }

   protected static class Deserializer implements JsonDeserializer<ItemOverride> {
      public ItemOverride deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "model"));
         List<ItemOverride.Predicate> â˜ƒxx = this.getPredicates(â˜ƒ);
         return new ItemOverride(â˜ƒx, â˜ƒxx);
      }

      protected List<ItemOverride.Predicate> getPredicates(JsonObject var1) {
         Map<ResourceLocation, Float> â˜ƒ = Maps.newLinkedHashMap();
         JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "predicate");

         for(Entry<String, JsonElement> â˜ƒxx : â˜ƒx.entrySet()) {
            â˜ƒ.put(new ResourceLocation((String)â˜ƒxx.getKey()), GsonHelper.convertToFloat((JsonElement)â˜ƒxx.getValue(), (String)â˜ƒxx.getKey()));
         }

         return (List<ItemOverride.Predicate>)â˜ƒ.entrySet()
            .stream()
            .map(var0 -> new ItemOverride.Predicate((ResourceLocation)var0.getKey(), var0.getValue()))
            .collect(ImmutableList.toImmutableList());
      }
   }

   public static class Predicate {
      private final ResourceLocation property;
      private final float value;

      public Predicate(ResourceLocation var1, float var2) {
         this.property = â˜ƒ;
         this.value = â˜ƒ;
      }

      public ResourceLocation getProperty() {
         return this.property;
      }

      public float getValue() {
         return this.value;
      }
   }
}
