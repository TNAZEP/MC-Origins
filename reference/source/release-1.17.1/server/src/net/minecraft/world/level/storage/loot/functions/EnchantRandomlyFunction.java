package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnchantRandomlyFunction extends LootItemConditionalFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   final List<Enchantment> enchantments;

   EnchantRandomlyFunction(LootItemCondition[] var1, Collection<Enchantment> var2) {
      super(â˜ƒ);
      this.enchantments = ImmutableList.copyOf(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.ENCHANT_RANDOMLY;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Random â˜ƒx = â˜ƒ.getRandom();
      Enchantment â˜ƒ;
      if (this.enchantments.isEmpty()) {
         boolean â˜ƒxx = â˜ƒ.is(Items.BOOK);
         List<Enchantment> â˜ƒxxx = (List)Registry.ENCHANTMENT
            .stream()
            .filter(Enchantment::isDiscoverable)
            .filter(var2x -> â˜ƒ || var2x.canEnchant(â˜ƒ))
            .collect(Collectors.toList());
         if (â˜ƒxxx.isEmpty()) {
            LOGGER.warn("Couldn't find a compatible enchantment for {}", â˜ƒ);
            return â˜ƒ;
         }

         â˜ƒ = (Enchantment)â˜ƒxxx.get(â˜ƒx.nextInt(â˜ƒxxx.size()));
      } else {
         â˜ƒ = (Enchantment)this.enchantments.get(â˜ƒx.nextInt(this.enchantments.size()));
      }

      return enchantItem(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   private static ItemStack enchantItem(ItemStack var0, Enchantment var1, Random var2) {
      int â˜ƒ = Mth.nextInt(â˜ƒ, â˜ƒ.getMinLevel(), â˜ƒ.getMaxLevel());
      if (â˜ƒ.is(Items.BOOK)) {
         â˜ƒ = new ItemStack(Items.ENCHANTED_BOOK);
         EnchantedBookItem.addEnchantment(â˜ƒ, new EnchantmentInstance(â˜ƒ, â˜ƒ));
      } else {
         â˜ƒ.enchant(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   public static EnchantRandomlyFunction.Builder randomEnchantment() {
      return new EnchantRandomlyFunction.Builder();
   }

   public static LootItemConditionalFunction.Builder<?> randomApplicableEnchantment() {
      return simpleBuilder(var0 -> new EnchantRandomlyFunction(var0, ImmutableList.<Enchantment>of()));
   }

   public static class Builder extends LootItemConditionalFunction.Builder<EnchantRandomlyFunction.Builder> {
      private final Set<Enchantment> enchantments = Sets.<Enchantment>newHashSet();

      protected EnchantRandomlyFunction.Builder getThis() {
         return this;
      }

      public EnchantRandomlyFunction.Builder withEnchantment(Enchantment var1) {
         this.enchantments.add(â˜ƒ);
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new EnchantRandomlyFunction(this.getConditions(), this.enchantments);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<EnchantRandomlyFunction> {
      public void serialize(JsonObject var1, EnchantRandomlyFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         if (!â˜ƒ.enchantments.isEmpty()) {
            JsonArray â˜ƒ = new JsonArray();

            for(Enchantment â˜ƒx : â˜ƒ.enchantments) {
               ResourceLocation â˜ƒxx = Registry.ENCHANTMENT.getKey(â˜ƒx);
               if (â˜ƒxx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize enchantment " + â˜ƒx);
               }

               â˜ƒ.add(new JsonPrimitive(â˜ƒxx.toString()));
            }

            â˜ƒ.add("enchantments", â˜ƒ);
         }
      }

      public EnchantRandomlyFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         List<Enchantment> â˜ƒ = Lists.<Enchantment>newArrayList();
         if (â˜ƒ.has("enchantments")) {
            for(JsonElement â˜ƒx : GsonHelper.getAsJsonArray(â˜ƒ, "enchantments")) {
               String â˜ƒxx = GsonHelper.convertToString(â˜ƒx, "enchantment");
               Enchantment â˜ƒxxx = (Enchantment)Registry.ENCHANTMENT
                  .getOptional(new ResourceLocation(â˜ƒxx))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + â˜ƒ + "'"));
               â˜ƒ.add(â˜ƒxxx);
            }
         }

         return new EnchantRandomlyFunction(â˜ƒ, â˜ƒ);
      }
   }
}
