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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
   static final Logger LOGGER = LogManager.getLogger();
   public static final LootTable EMPTY = new LootTable(LootContextParamSets.EMPTY, new LootPool[0], new LootItemFunction[0]);
   public static final LootContextParamSet DEFAULT_PARAM_SET = LootContextParamSets.ALL_PARAMS;
   final LootContextParamSet paramSet;
   final LootPool[] pools;
   final LootItemFunction[] functions;
   private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;

   LootTable(LootContextParamSet var1, LootPool[] var2, LootItemFunction[] var3) {
      this.paramSet = â˜ƒ;
      this.pools = â˜ƒ;
      this.functions = â˜ƒ;
      this.compositeFunction = LootItemFunctions.compose(â˜ƒ);
   }

   public static Consumer<ItemStack> createStackSplitter(Consumer<ItemStack> var0) {
      return var1 -> {
         if (var1.getCount() < var1.getMaxStackSize()) {
            â˜ƒ.accept(var1);
         } else {
            int â˜ƒ = var1.getCount();

            while(â˜ƒ > 0) {
               ItemStack â˜ƒx = var1.copy();
               â˜ƒx.setCount(Math.min(var1.getMaxStackSize(), â˜ƒ));
               â˜ƒ -= â˜ƒx.getCount();
               â˜ƒ.accept(â˜ƒx);
            }
         }
      };
   }

   public void getRandomItemsRaw(LootContext var1, Consumer<ItemStack> var2) {
      if (â˜ƒ.addVisitedTable(this)) {
         Consumer<ItemStack> â˜ƒ = LootItemFunction.decorate(this.compositeFunction, â˜ƒ, â˜ƒ);

         for(LootPool â˜ƒx : this.pools) {
            â˜ƒx.addRandomItems(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.removeVisitedTable(this);
      } else {
         LOGGER.warn("Detected infinite loop in loot tables");
      }
   }

   public void getRandomItems(LootContext var1, Consumer<ItemStack> var2) {
      this.getRandomItemsRaw(â˜ƒ, createStackSplitter(â˜ƒ));
   }

   public List<ItemStack> getRandomItems(LootContext var1) {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();
      this.getRandomItems(â˜ƒ, â˜ƒ::add);
      return â˜ƒ;
   }

   public LootContextParamSet getParamSet() {
      return this.paramSet;
   }

   public void validate(ValidationContext var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.pools.length; ++â˜ƒ) {
         this.pools[â˜ƒ].validate(â˜ƒ.forChild(".pools[" + â˜ƒ + "]"));
      }

      for(int â˜ƒ = 0; â˜ƒ < this.functions.length; ++â˜ƒ) {
         this.functions[â˜ƒ].validate(â˜ƒ.forChild(".functions[" + â˜ƒ + "]"));
      }
   }

   public void fill(Container var1, LootContext var2) {
      List<ItemStack> â˜ƒ = this.getRandomItems(â˜ƒ);
      Random â˜ƒx = â˜ƒ.getRandom();
      List<Integer> â˜ƒxx = this.getAvailableSlots(â˜ƒ, â˜ƒx);
      this.shuffleAndSplitItems(â˜ƒ, â˜ƒxx.size(), â˜ƒx);

      for(ItemStack â˜ƒxxx : â˜ƒ) {
         if (â˜ƒxx.isEmpty()) {
            LOGGER.warn("Tried to over-fill a container");
            return;
         }

         if (â˜ƒxxx.isEmpty()) {
            â˜ƒ.setItem(â˜ƒxx.remove(â˜ƒxx.size() - 1), ItemStack.EMPTY);
         } else {
            â˜ƒ.setItem(â˜ƒxx.remove(â˜ƒxx.size() - 1), â˜ƒxxx);
         }
      }
   }

   private void shuffleAndSplitItems(List<ItemStack> var1, int var2, Random var3) {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();
      Iterator<ItemStack> â˜ƒx = â˜ƒ.iterator();

      while(â˜ƒx.hasNext()) {
         ItemStack â˜ƒxx = (ItemStack)â˜ƒx.next();
         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.remove();
         } else if (â˜ƒxx.getCount() > 1) {
            â˜ƒ.add(â˜ƒxx);
            â˜ƒx.remove();
         }
      }

      while(â˜ƒ - â˜ƒ.size() - â˜ƒ.size() > 0 && !â˜ƒ.isEmpty()) {
         ItemStack â˜ƒxx = (ItemStack)â˜ƒ.remove(Mth.nextInt(â˜ƒ, 0, â˜ƒ.size() - 1));
         int â˜ƒxxx = Mth.nextInt(â˜ƒ, 1, â˜ƒxx.getCount() / 2);
         ItemStack â˜ƒxxxx = â˜ƒxx.split(â˜ƒxxx);
         if (â˜ƒxx.getCount() > 1 && â˜ƒ.nextBoolean()) {
            â˜ƒ.add(â˜ƒxx);
         } else {
            â˜ƒ.add(â˜ƒxx);
         }

         if (â˜ƒxxxx.getCount() > 1 && â˜ƒ.nextBoolean()) {
            â˜ƒ.add(â˜ƒxxxx);
         } else {
            â˜ƒ.add(â˜ƒxxxx);
         }
      }

      â˜ƒ.addAll(â˜ƒ);
      Collections.shuffle(â˜ƒ, â˜ƒ);
   }

   private List<Integer> getAvailableSlots(Container var1, Random var2) {
      List<Integer> â˜ƒ = Lists.newArrayList();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize(); ++â˜ƒx) {
         if (â˜ƒ.getItem(â˜ƒx).isEmpty()) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      Collections.shuffle(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static LootTable.Builder lootTable() {
      return new LootTable.Builder();
   }

   public static class Builder implements FunctionUserBuilder<LootTable.Builder> {
      private final List<LootPool> pools = Lists.<LootPool>newArrayList();
      private final List<LootItemFunction> functions = Lists.<LootItemFunction>newArrayList();
      private LootContextParamSet paramSet = LootTable.DEFAULT_PARAM_SET;

      public LootTable.Builder withPool(LootPool.Builder var1) {
         this.pools.add(â˜ƒ.build());
         return this;
      }

      public LootTable.Builder setParamSet(LootContextParamSet var1) {
         this.paramSet = â˜ƒ;
         return this;
      }

      public LootTable.Builder apply(LootItemFunction.Builder var1) {
         this.functions.add(â˜ƒ.build());
         return this;
      }

      public LootTable.Builder unwrap() {
         return this;
      }

      public LootTable build() {
         return new LootTable(
            this.paramSet, (LootPool[])this.pools.toArray(new LootPool[0]), (LootItemFunction[])this.functions.toArray(new LootItemFunction[0])
         );
      }
   }

   public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
      public LootTable deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "loot table");
         LootPool[] â˜ƒx = (LootPool[])GsonHelper.getAsObject(â˜ƒ, "pools", new LootPool[0], â˜ƒ, LootPool[].class);
         LootContextParamSet â˜ƒxx = null;
         if (â˜ƒ.has("type")) {
            String â˜ƒxxx = GsonHelper.getAsString(â˜ƒ, "type");
            â˜ƒxx = LootContextParamSets.get(new ResourceLocation(â˜ƒxxx));
         }

         LootItemFunction[] â˜ƒ = (LootItemFunction[])GsonHelper.getAsObject(â˜ƒ, "functions", new LootItemFunction[0], â˜ƒ, LootItemFunction[].class);
         return new LootTable(â˜ƒxx != null ? â˜ƒxx : LootContextParamSets.ALL_PARAMS, â˜ƒx, â˜ƒ);
      }

      public JsonElement serialize(LootTable var1, Type var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();
         if (â˜ƒ.paramSet != LootTable.DEFAULT_PARAM_SET) {
            ResourceLocation â˜ƒx = LootContextParamSets.getKey(â˜ƒ.paramSet);
            if (â˜ƒx != null) {
               â˜ƒ.addProperty("type", â˜ƒx.toString());
            } else {
               LootTable.LOGGER.warn("Failed to find id for param set {}", â˜ƒ.paramSet);
            }
         }

         if (â˜ƒ.pools.length > 0) {
            â˜ƒ.add("pools", â˜ƒ.serialize(â˜ƒ.pools));
         }

         if (!ArrayUtils.isEmpty((Object[])â˜ƒ.functions)) {
            â˜ƒ.add("functions", â˜ƒ.serialize(â˜ƒ.functions));
         }

         return â˜ƒ;
      }
   }
}
