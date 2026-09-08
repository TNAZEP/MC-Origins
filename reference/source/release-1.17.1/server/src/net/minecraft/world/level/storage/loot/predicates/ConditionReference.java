package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConditionReference implements LootItemCondition {
   private static final Logger LOGGER = LogManager.getLogger();
   final ResourceLocation name;

   ConditionReference(ResourceLocation var1) {
      this.name = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.REFERENCE;
   }

   @Override
   public void validate(ValidationContext var1) {
      if (â˜ƒ.hasVisitedCondition(this.name)) {
         â˜ƒ.reportProblem("Condition " + this.name + " is recursively called");
      } else {
         LootItemCondition.super.validate(â˜ƒ);
         LootItemCondition â˜ƒ = â˜ƒ.resolveCondition(this.name);
         if (â˜ƒ == null) {
            â˜ƒ.reportProblem("Unknown condition table called " + this.name);
         } else {
            â˜ƒ.validate(â˜ƒ.enterTable(".{" + this.name + "}", this.name));
         }
      }
   }

   public boolean test(LootContext var1) {
      LootItemCondition â˜ƒ = â˜ƒ.getCondition(this.name);
      if (â˜ƒ.addVisitedCondition(â˜ƒ)) {
         boolean var3;
         try {
            var3 = â˜ƒ.test(â˜ƒ);
         } finally {
            â˜ƒ.removeVisitedCondition(â˜ƒ);
         }

         return var3;
      } else {
         LOGGER.warn("Detected infinite loop in loot tables");
         return false;
      }
   }

   public static LootItemCondition.Builder conditionReference(ResourceLocation var0) {
      return () -> new ConditionReference(â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConditionReference> {
      public void serialize(JsonObject var1, ConditionReference var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("name", â˜ƒ.name.toString());
      }

      public ConditionReference deserialize(JsonObject var1, JsonDeserializationContext var2) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "name"));
         return new ConditionReference(â˜ƒ);
      }
   }
}
