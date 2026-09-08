package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class InvertedLootItemCondition implements LootItemCondition {
   final LootItemCondition term;

   InvertedLootItemCondition(LootItemCondition var1) {
      this.term = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.INVERTED;
   }

   public final boolean test(LootContext var1) {
      return !this.term.test(â˜ƒ);
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.term.getReferencedContextParams();
   }

   @Override
   public void validate(ValidationContext var1) {
      LootItemCondition.super.validate(â˜ƒ);
      this.term.validate(â˜ƒ);
   }

   public static LootItemCondition.Builder invert(LootItemCondition.Builder var0) {
      InvertedLootItemCondition â˜ƒ = new InvertedLootItemCondition(â˜ƒ.build());
      return () -> â˜ƒ;
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<InvertedLootItemCondition> {
      public void serialize(JsonObject var1, InvertedLootItemCondition var2, JsonSerializationContext var3) {
         â˜ƒ.add("term", â˜ƒ.serialize(â˜ƒ.term));
      }

      public InvertedLootItemCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         LootItemCondition â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "term", â˜ƒ, LootItemCondition.class);
         return new InvertedLootItemCondition(â˜ƒ);
      }
   }
}
