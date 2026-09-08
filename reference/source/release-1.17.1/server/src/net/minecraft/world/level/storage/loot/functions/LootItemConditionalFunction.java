package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootItemConditionalFunction implements LootItemFunction {
   protected final LootItemCondition[] predicates;
   private final Predicate<LootContext> compositePredicates;

   protected LootItemConditionalFunction(LootItemCondition[] var1) {
      this.predicates = â˜ƒ;
      this.compositePredicates = LootItemConditions.andConditions(â˜ƒ);
   }

   public final ItemStack apply(ItemStack var1, LootContext var2) {
      return this.compositePredicates.test(â˜ƒ) ? this.run(â˜ƒ, â˜ƒ) : â˜ƒ;
   }

   protected abstract ItemStack run(ItemStack var1, LootContext var2);

   @Override
   public void validate(ValidationContext var1) {
      LootItemFunction.super.validate(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < this.predicates.length; ++â˜ƒ) {
         this.predicates[â˜ƒ].validate(â˜ƒ.forChild(".conditions[" + â˜ƒ + "]"));
      }
   }

   protected static LootItemConditionalFunction.Builder<?> simpleBuilder(Function<LootItemCondition[], LootItemFunction> var0) {
      return new LootItemConditionalFunction.DummyBuilder(â˜ƒ);
   }

   public abstract static class Builder<T extends LootItemConditionalFunction.Builder<T>> implements LootItemFunction.Builder, ConditionUserBuilder<T> {
      private final List<LootItemCondition> conditions = Lists.<LootItemCondition>newArrayList();

      public T when(LootItemCondition.Builder var1) {
         this.conditions.add(â˜ƒ.build());
         return this.getThis();
      }

      public final T unwrap() {
         return this.getThis();
      }

      protected abstract T getThis();

      protected LootItemCondition[] getConditions() {
         return (LootItemCondition[])this.conditions.toArray(new LootItemCondition[0]);
      }
   }

   static final class DummyBuilder extends LootItemConditionalFunction.Builder<LootItemConditionalFunction.DummyBuilder> {
      private final Function<LootItemCondition[], LootItemFunction> constructor;

      public DummyBuilder(Function<LootItemCondition[], LootItemFunction> var1) {
         this.constructor = â˜ƒ;
      }

      protected LootItemConditionalFunction.DummyBuilder getThis() {
         return this;
      }

      @Override
      public LootItemFunction build() {
         return (LootItemFunction)this.constructor.apply(this.getConditions());
      }
   }

   public abstract static class Serializer<T extends LootItemConditionalFunction> implements net.minecraft.world.level.storage.loot.Serializer<T> {
      public void serialize(JsonObject var1, T var2, JsonSerializationContext var3) {
         if (!ArrayUtils.isEmpty((Object[])â˜ƒ.predicates)) {
            â˜ƒ.add("conditions", â˜ƒ.serialize(â˜ƒ.predicates));
         }
      }

      public final T deserialize(JsonObject var1, JsonDeserializationContext var2) {
         LootItemCondition[] â˜ƒ = (LootItemCondition[])GsonHelper.getAsObject(â˜ƒ, "conditions", new LootItemCondition[0], â˜ƒ, LootItemCondition[].class);
         return this.deserialize(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public abstract T deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);
   }
}
