package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Criterion {
   private final CriterionTriggerInstance trigger;

   public Criterion(CriterionTriggerInstance var1) {
      this.trigger = â˜ƒ;
   }

   public Criterion() {
      this.trigger = null;
   }

   public void serializeToNetwork(FriendlyByteBuf var1) {
   }

   public static Criterion criterionFromJson(JsonObject var0, DeserializationContext var1) {
      ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "trigger"));
      CriterionTrigger<?> â˜ƒx = CriteriaTriggers.getCriterion(â˜ƒ);
      if (â˜ƒx == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + â˜ƒ);
      } else {
         CriterionTriggerInstance â˜ƒ = â˜ƒx.createInstance(GsonHelper.getAsJsonObject(â˜ƒ, "conditions", new JsonObject()), â˜ƒ);
         return new Criterion(â˜ƒ);
      }
   }

   public static Criterion criterionFromNetwork(FriendlyByteBuf var0) {
      return new Criterion();
   }

   public static Map<String, Criterion> criteriaFromJson(JsonObject var0, DeserializationContext var1) {
      Map<String, Criterion> â˜ƒ = Maps.newHashMap();

      for(Entry<String, JsonElement> â˜ƒx : â˜ƒ.entrySet()) {
         â˜ƒ.put((String)â˜ƒx.getKey(), criterionFromJson(GsonHelper.convertToJsonObject((JsonElement)â˜ƒx.getValue(), "criterion"), â˜ƒ));
      }

      return â˜ƒ;
   }

   public static Map<String, Criterion> criteriaFromNetwork(FriendlyByteBuf var0) {
      return â˜ƒ.readMap(FriendlyByteBuf::readUtf, Criterion::criterionFromNetwork);
   }

   public static void serializeToNetwork(Map<String, Criterion> var0, FriendlyByteBuf var1) {
      â˜ƒ.writeMap(â˜ƒ, FriendlyByteBuf::writeUtf, (var0x, var1x) -> var1x.serializeToNetwork(var0x));
   }

   @Nullable
   public CriterionTriggerInstance getTrigger() {
      return this.trigger;
   }

   public JsonElement serializeToJson() {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("trigger", this.trigger.getCriterion().toString());
      JsonObject â˜ƒx = this.trigger.serializeToJson(SerializationContext.INSTANCE);
      if (â˜ƒx.size() != 0) {
         â˜ƒ.add("conditions", â˜ƒx);
      }

      return â˜ƒ;
   }
}
