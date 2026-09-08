package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootPoolSingletonContainer extends LootPoolEntryContainer {
   public static final int DEFAULT_WEIGHT = 1;
   public static final int DEFAULT_QUALITY = 0;
   protected final int weight;
   protected final int quality;
   protected final LootItemFunction[] functions;
   final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
   private final LootPoolEntry entry = new LootPoolSingletonContainer.EntryBase() {
      @Override
      public void createItemStack(Consumer<ItemStack> var1, LootContext var2) {
         LootPoolSingletonContainer.this.createItemStack(LootItemFunction.decorate(LootPoolSingletonContainer.this.compositeFunction, â˜ƒ, â˜ƒ), â˜ƒ);
      }
   };

   protected LootPoolSingletonContainer(int var1, int var2, LootItemCondition[] var3, LootItemFunction[] var4) {
      super(â˜ƒ);
      this.weight = â˜ƒ;
      this.quality = â˜ƒ;
      this.functions = â˜ƒ;
      this.compositeFunction = LootItemFunctions.compose(â˜ƒ);
   }

   @Override
   public void validate(ValidationContext var1) {
      super.validate(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < this.functions.length; ++â˜ƒ) {
         this.functions[â˜ƒ].validate(â˜ƒ.forChild(".functions[" + â˜ƒ + "]"));
      }
   }

   protected abstract void createItemStack(Consumer<ItemStack> var1, LootContext var2);

   @Override
   public boolean expand(LootContext var1, Consumer<LootPoolEntry> var2) {
      if (this.canRun(â˜ƒ)) {
         â˜ƒ.accept(this.entry);
         return true;
      } else {
         return false;
      }
   }

   public static LootPoolSingletonContainer.Builder<?> simpleBuilder(LootPoolSingletonContainer.EntryConstructor var0) {
      return new LootPoolSingletonContainer.DummyBuilder(â˜ƒ);
   }

   public abstract static class Builder<T extends LootPoolSingletonContainer.Builder<T>>
      extends LootPoolEntryContainer.Builder<T>
      implements FunctionUserBuilder<T> {
      protected int weight = 1;
      protected int quality = 0;
      private final List<LootItemFunction> functions = Lists.<LootItemFunction>newArrayList();

      public T apply(LootItemFunction.Builder var1) {
         this.functions.add(â˜ƒ.build());
         return this.getThis();
      }

      protected LootItemFunction[] getFunctions() {
         return (LootItemFunction[])this.functions.toArray(new LootItemFunction[0]);
      }

      public T setWeight(int var1) {
         this.weight = â˜ƒ;
         return this.getThis();
      }

      public T setQuality(int var1) {
         this.quality = â˜ƒ;
         return this.getThis();
      }
   }

   static class DummyBuilder extends LootPoolSingletonContainer.Builder<LootPoolSingletonContainer.DummyBuilder> {
      private final LootPoolSingletonContainer.EntryConstructor constructor;

      public DummyBuilder(LootPoolSingletonContainer.EntryConstructor var1) {
         this.constructor = â˜ƒ;
      }

      protected LootPoolSingletonContainer.DummyBuilder getThis() {
         return this;
      }

      @Override
      public LootPoolEntryContainer build() {
         return this.constructor.build(this.weight, this.quality, this.getConditions(), this.getFunctions());
      }
   }

   protected abstract class EntryBase implements LootPoolEntry {
      @Override
      public int getWeight(float var1) {
         return Math.max(Mth.floor((float)LootPoolSingletonContainer.this.weight + (float)LootPoolSingletonContainer.this.quality * â˜ƒ), 0);
      }
   }

   @FunctionalInterface
   protected interface EntryConstructor {
      LootPoolSingletonContainer build(int var1, int var2, LootItemCondition[] var3, LootItemFunction[] var4);
   }

   public abstract static class Serializer<T extends LootPoolSingletonContainer> extends LootPoolEntryContainer.Serializer<T> {
      public void serializeCustom(JsonObject var1, T var2, JsonSerializationContext var3) {
         if (â˜ƒ.weight != 1) {
            â˜ƒ.addProperty("weight", â˜ƒ.weight);
         }

         if (â˜ƒ.quality != 0) {
            â˜ƒ.addProperty("quality", â˜ƒ.quality);
         }

         if (!ArrayUtils.isEmpty((Object[])â˜ƒ.functions)) {
            â˜ƒ.add("functions", â˜ƒ.serialize(â˜ƒ.functions));
         }
      }

      public final T deserializeCustom(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "weight", 1);
         int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "quality", 0);
         LootItemFunction[] â˜ƒxx = (LootItemFunction[])GsonHelper.getAsObject(â˜ƒ, "functions", new LootItemFunction[0], â˜ƒ, LootItemFunction[].class);
         return this.deserialize(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx);
      }

      protected abstract T deserialize(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6);
   }
}
