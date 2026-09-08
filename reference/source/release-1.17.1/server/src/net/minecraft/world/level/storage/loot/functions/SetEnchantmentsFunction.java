package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetEnchantmentsFunction extends LootItemConditionalFunction {
   final Map<Enchantment, NumberProvider> enchantments;
   final boolean add;

   SetEnchantmentsFunction(LootItemCondition[] var1, Map<Enchantment, NumberProvider> var2, boolean var3) {
      super(â˜ƒ);
      this.enchantments = ImmutableMap.copyOf(â˜ƒ);
      this.add = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_ENCHANTMENTS;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return (Set<LootContextParam<?>>)this.enchantments
         .values()
         .stream()
         .flatMap(var0 -> var0.getReferencedContextParams().stream())
         .collect(ImmutableSet.toImmutableSet());
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Object2IntMap<Enchantment> â˜ƒ = new Object2IntOpenHashMap<>();
      this.enchantments.forEach((var2x, var3x) -> â˜ƒ.put(var2x, var3x.getInt(â˜ƒ)));
      if (â˜ƒ.getItem() == Items.BOOK) {
         ItemStack â˜ƒx = new ItemStack(Items.ENCHANTED_BOOK);
         â˜ƒ.forEach((var1x, var2x) -> EnchantedBookItem.addEnchantment(â˜ƒ, new EnchantmentInstance(var1x, var2x)));
         return â˜ƒx;
      } else {
         Map<Enchantment, Integer> â˜ƒ = EnchantmentHelper.getEnchantments(â˜ƒ);
         if (this.add) {
            â˜ƒ.forEach((var1x, var2x) -> updateEnchantment(â˜ƒ, var1x, Math.max(â˜ƒ.getOrDefault(var1x, 0) + var2x, 0)));
         } else {
            â˜ƒ.forEach((var1x, var2x) -> updateEnchantment(â˜ƒ, var1x, Math.max(var2x, 0)));
         }

         EnchantmentHelper.setEnchantments(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }
   }

   private static void updateEnchantment(Map<Enchantment, Integer> var0, Enchantment var1, int var2) {
      if (â˜ƒ == 0) {
         â˜ƒ.remove(â˜ƒ);
      } else {
         â˜ƒ.put(â˜ƒ, â˜ƒ);
      }
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetEnchantmentsFunction.Builder> {
      private final Map<Enchantment, NumberProvider> enchantments = Maps.<Enchantment, NumberProvider>newHashMap();
      private final boolean add;

      public Builder() {
         this(false);
      }

      public Builder(boolean var1) {
         this.add = â˜ƒ;
      }

      protected SetEnchantmentsFunction.Builder getThis() {
         return this;
      }

      public SetEnchantmentsFunction.Builder withEnchantment(Enchantment var1, NumberProvider var2) {
         this.enchantments.put(â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new SetEnchantmentsFunction(this.getConditions(), this.enchantments, this.add);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetEnchantmentsFunction> {
      public void serialize(JsonObject var1, SetEnchantmentsFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.enchantments.forEach((var2x, var3x) -> {
            ResourceLocation â˜ƒ = Registry.ENCHANTMENT.getKey(var2x);
            if (â˜ƒ == null) {
               throw new IllegalArgumentException("Don't know how to serialize enchantment " + var2x);
            } else {
               â˜ƒ.add(â˜ƒ.toString(), â˜ƒ.serialize(var3x));
            }
         });
         â˜ƒ.add("enchantments", â˜ƒ);
         â˜ƒ.addProperty("add", â˜ƒ.add);
      }

      public SetEnchantmentsFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         Map<Enchantment, NumberProvider> â˜ƒ = Maps.<Enchantment, NumberProvider>newHashMap();
         if (â˜ƒ.has("enchantments")) {
            JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "enchantments");

            for(Entry<String, JsonElement> â˜ƒxx : â˜ƒx.entrySet()) {
               String â˜ƒxxx = (String)â˜ƒxx.getKey();
               JsonElement â˜ƒxxxx = (JsonElement)â˜ƒxx.getValue();
               Enchantment â˜ƒxxxxx = (Enchantment)Registry.ENCHANTMENT
                  .getOptional(new ResourceLocation(â˜ƒxxx))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + â˜ƒ + "'"));
               NumberProvider â˜ƒxxxxxx = â˜ƒ.deserialize(â˜ƒxxxx, NumberProvider.class);
               â˜ƒ.put(â˜ƒxxxxx, â˜ƒxxxxxx);
            }
         }

         boolean â˜ƒ = GsonHelper.getAsBoolean(â˜ƒ, "add", false);
         return new SetEnchantmentsFunction(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
