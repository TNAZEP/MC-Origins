package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;

public class AlternativeLootItemCondition implements LootItemCondition {
   final LootItemCondition[] terms;
   private final Predicate<LootContext> composedPredicate;

   AlternativeLootItemCondition(LootItemCondition[] var1) {
      this.terms = â˜ƒ;
      this.composedPredicate = LootItemConditions.orConditions(â˜ƒ);
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.ALTERNATIVE;
   }

   public final boolean test(LootContext var1) {
      return this.composedPredicate.test(â˜ƒ);
   }

   @Override
   public void validate(ValidationContext var1) {
      LootItemCondition.super.validate(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < this.terms.length; ++â˜ƒ) {
         this.terms[â˜ƒ].validate(â˜ƒ.forChild(".term[" + â˜ƒ + "]"));
      }
   }

   public static AlternativeLootItemCondition.Builder alternative(LootItemCondition.Builder... var0) {
      return new AlternativeLootItemCondition.Builder(â˜ƒ);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final List<LootItemCondition> terms = Lists.<LootItemCondition>newArrayList();

      public Builder(LootItemCondition.Builder... var1) {
         for(LootItemCondition.Builder â˜ƒ : â˜ƒ) {
            this.terms.add(â˜ƒ.build());
         }
      }

      @Override
      public AlternativeLootItemCondition.Builder or(LootItemCondition.Builder var1) {
         this.terms.add(â˜ƒ.build());
         return this;
      }

      @Override
      public LootItemCondition build() {
         return new AlternativeLootItemCondition((LootItemCondition[])this.terms.toArray(new LootItemCondition[0]));
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<AlternativeLootItemCondition> {
      public void serialize(JsonObject var1, AlternativeLootItemCondition var2, JsonSerializationContext var3) {
         â˜ƒ.add("terms", â˜ƒ.serialize(â˜ƒ.terms));
      }

      public AlternativeLootItemCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         LootItemCondition[] â˜ƒ = (LootItemCondition[])GsonHelper.getAsObject(â˜ƒ, "terms", â˜ƒ, LootItemCondition[].class);
         return new AlternativeLootItemCondition(â˜ƒ);
      }
   }
}
