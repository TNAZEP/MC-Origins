package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class LootPool {
   final LootPoolEntryContainer[] entries;
   final LootItemCondition[] conditions;
   private final Predicate<LootContext> compositeCondition;
   final LootItemFunction[] functions;
   private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
   final NumberProvider rolls;
   final NumberProvider bonusRolls;

   LootPool(LootPoolEntryContainer[] var1, LootItemCondition[] var2, LootItemFunction[] var3, NumberProvider var4, NumberProvider var5) {
      this.entries = â˜ƒ;
      this.conditions = â˜ƒ;
      this.compositeCondition = LootItemConditions.andConditions(â˜ƒ);
      this.functions = â˜ƒ;
      this.compositeFunction = LootItemFunctions.compose(â˜ƒ);
      this.rolls = â˜ƒ;
      this.bonusRolls = â˜ƒ;
   }

   private void addRandomItem(Consumer<ItemStack> var1, LootContext var2) {
      Random â˜ƒ = â˜ƒ.getRandom();
      List<LootPoolEntry> â˜ƒx = Lists.<LootPoolEntry>newArrayList();
      MutableInt â˜ƒxx = new MutableInt();

      for(LootPoolEntryContainer â˜ƒxxx : this.entries) {
         â˜ƒxxx.expand(â˜ƒ, var3x -> {
            int â˜ƒ = var3x.getWeight(â˜ƒ.getLuck());
            if (â˜ƒ > 0) {
               â˜ƒ.add(var3x);
               â˜ƒ.add(â˜ƒ);
            }
         });
      }

      int â˜ƒxxx = â˜ƒx.size();
      if (â˜ƒxx.intValue() != 0 && â˜ƒxxx != 0) {
         if (â˜ƒxxx == 1) {
            ((LootPoolEntry)â˜ƒx.get(0)).createItemStack(â˜ƒ, â˜ƒ);
         } else {
            int â˜ƒxxxx = â˜ƒ.nextInt(â˜ƒxx.intValue());

            for(LootPoolEntry â˜ƒxxxxx : â˜ƒx) {
               â˜ƒxxxx -= â˜ƒxxxxx.getWeight(â˜ƒ.getLuck());
               if (â˜ƒxxxx < 0) {
                  â˜ƒxxxxx.createItemStack(â˜ƒ, â˜ƒ);
                  return;
               }
            }
         }
      }
   }

   public void addRandomItems(Consumer<ItemStack> var1, LootContext var2) {
      if (this.compositeCondition.test(â˜ƒ)) {
         Consumer<ItemStack> â˜ƒ = LootItemFunction.decorate(this.compositeFunction, â˜ƒ, â˜ƒ);
         int â˜ƒx = this.rolls.getInt(â˜ƒ) + Mth.floor(this.bonusRolls.getFloat(â˜ƒ) * â˜ƒ.getLuck());

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
            this.addRandomItem(â˜ƒ, â˜ƒ);
         }
      }
   }

   public void validate(ValidationContext var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.conditions.length; ++â˜ƒ) {
         this.conditions[â˜ƒ].validate(â˜ƒ.forChild(".condition[" + â˜ƒ + "]"));
      }

      for(int â˜ƒ = 0; â˜ƒ < this.functions.length; ++â˜ƒ) {
         this.functions[â˜ƒ].validate(â˜ƒ.forChild(".functions[" + â˜ƒ + "]"));
      }

      for(int â˜ƒ = 0; â˜ƒ < this.entries.length; ++â˜ƒ) {
         this.entries[â˜ƒ].validate(â˜ƒ.forChild(".entries[" + â˜ƒ + "]"));
      }

      this.rolls.validate(â˜ƒ.forChild(".rolls"));
      this.bonusRolls.validate(â˜ƒ.forChild(".bonusRolls"));
   }

   public static LootPool.Builder lootPool() {
      return new LootPool.Builder();
   }

   public static class Builder implements FunctionUserBuilder<LootPool.Builder>, ConditionUserBuilder<LootPool.Builder> {
      private final List<LootPoolEntryContainer> entries = Lists.<LootPoolEntryContainer>newArrayList();
      private final List<LootItemCondition> conditions = Lists.<LootItemCondition>newArrayList();
      private final List<LootItemFunction> functions = Lists.<LootItemFunction>newArrayList();
      private NumberProvider rolls = ConstantValue.exactly(1.0F);
      private NumberProvider bonusRolls = ConstantValue.exactly(0.0F);

      public LootPool.Builder setRolls(NumberProvider var1) {
         this.rolls = â˜ƒ;
         return this;
      }

      public LootPool.Builder unwrap() {
         return this;
      }

      public LootPool.Builder setBonusRolls(NumberProvider var1) {
         this.bonusRolls = â˜ƒ;
         return this;
      }

      public LootPool.Builder add(LootPoolEntryContainer.Builder<?> var1) {
         this.entries.add(â˜ƒ.build());
         return this;
      }

      public LootPool.Builder when(LootItemCondition.Builder var1) {
         this.conditions.add(â˜ƒ.build());
         return this;
      }

      public LootPool.Builder apply(LootItemFunction.Builder var1) {
         this.functions.add(â˜ƒ.build());
         return this;
      }

      public LootPool build() {
         if (this.rolls == null) {
            throw new IllegalArgumentException("Rolls not set");
         } else {
            return new LootPool(
               (LootPoolEntryContainer[])this.entries.toArray(new LootPoolEntryContainer[0]),
               (LootItemCondition[])this.conditions.toArray(new LootItemCondition[0]),
               (LootItemFunction[])this.functions.toArray(new LootItemFunction[0]),
               this.rolls,
               this.bonusRolls
            );
         }
      }
   }

   public static class Serializer implements JsonDeserializer<LootPool>, JsonSerializer<LootPool> {
      public LootPool deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "loot pool");
         LootPoolEntryContainer[] â˜ƒx = (LootPoolEntryContainer[])GsonHelper.getAsObject(â˜ƒ, "entries", â˜ƒ, LootPoolEntryContainer[].class);
         LootItemCondition[] â˜ƒxx = (LootItemCondition[])GsonHelper.getAsObject(â˜ƒ, "conditions", new LootItemCondition[0], â˜ƒ, LootItemCondition[].class);
         LootItemFunction[] â˜ƒxxx = (LootItemFunction[])GsonHelper.getAsObject(â˜ƒ, "functions", new LootItemFunction[0], â˜ƒ, LootItemFunction[].class);
         NumberProvider â˜ƒxxxx = GsonHelper.getAsObject(â˜ƒ, "rolls", â˜ƒ, NumberProvider.class);
         NumberProvider â˜ƒxxxxx = GsonHelper.getAsObject(â˜ƒ, "bonus_rolls", ConstantValue.exactly(0.0F), â˜ƒ, NumberProvider.class);
         return new LootPool(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      }

      public JsonElement serialize(LootPool var1, Type var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("rolls", â˜ƒ.serialize(â˜ƒ.rolls));
         â˜ƒ.add("bonus_rolls", â˜ƒ.serialize(â˜ƒ.bonusRolls));
         â˜ƒ.add("entries", â˜ƒ.serialize(â˜ƒ.entries));
         if (!ArrayUtils.isEmpty((Object[])â˜ƒ.conditions)) {
            â˜ƒ.add("conditions", â˜ƒ.serialize(â˜ƒ.conditions));
         }

         if (!ArrayUtils.isEmpty((Object[])â˜ƒ.functions)) {
            â˜ƒ.add("functions", â˜ƒ.serialize(â˜ƒ.functions));
         }

         return â˜ƒ;
      }
   }
}
