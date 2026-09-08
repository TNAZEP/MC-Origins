package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootPoolEntryContainer implements ComposableEntryContainer {
   protected final LootItemCondition[] conditions;
   private final Predicate<LootContext> compositeCondition;

   protected LootPoolEntryContainer(LootItemCondition[] var1) {
      this.conditions = â˜ƒ;
      this.compositeCondition = LootItemConditions.andConditions(â˜ƒ);
   }

   public void validate(ValidationContext var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.conditions.length; ++â˜ƒ) {
         this.conditions[â˜ƒ].validate(â˜ƒ.forChild(".condition[" + â˜ƒ + "]"));
      }
   }

   protected final boolean canRun(LootContext var1) {
      return this.compositeCondition.test(â˜ƒ);
   }

   public abstract LootPoolEntryType getType();

   public abstract static class Builder<T extends LootPoolEntryContainer.Builder<T>> implements ConditionUserBuilder<T> {
      private final List<LootItemCondition> conditions = Lists.<LootItemCondition>newArrayList();

      protected abstract T getThis();

      public T when(LootItemCondition.Builder var1) {
         this.conditions.add(â˜ƒ.build());
         return this.getThis();
      }

      public final T unwrap() {
         return this.getThis();
      }

      protected LootItemCondition[] getConditions() {
         return (LootItemCondition[])this.conditions.toArray(new LootItemCondition[0]);
      }

      public AlternativesEntry.Builder otherwise(LootPoolEntryContainer.Builder<?> var1) {
         return new AlternativesEntry.Builder(this, â˜ƒ);
      }

      public EntryGroup.Builder append(LootPoolEntryContainer.Builder<?> var1) {
         return new EntryGroup.Builder(this, â˜ƒ);
      }

      public SequentialEntry.Builder then(LootPoolEntryContainer.Builder<?> var1) {
         return new SequentialEntry.Builder(this, â˜ƒ);
      }

      public abstract LootPoolEntryContainer build();
   }

   public abstract static class Serializer<T extends LootPoolEntryContainer> implements net.minecraft.world.level.storage.loot.Serializer<T> {
      public final void serialize(JsonObject var1, T var2, JsonSerializationContext var3) {
         if (!ArrayUtils.isEmpty((Object[])â˜ƒ.conditions)) {
            â˜ƒ.add("conditions", â˜ƒ.serialize(â˜ƒ.conditions));
         }

         this.serializeCustom(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public final T deserialize(JsonObject var1, JsonDeserializationContext var2) {
         LootItemCondition[] â˜ƒ = (LootItemCondition[])GsonHelper.getAsObject(â˜ƒ, "conditions", new LootItemCondition[0], â˜ƒ, LootItemCondition[].class);
         return this.deserializeCustom(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public abstract void serializeCustom(JsonObject var1, T var2, JsonSerializationContext var3);

      public abstract T deserializeCustom(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);
   }
}
