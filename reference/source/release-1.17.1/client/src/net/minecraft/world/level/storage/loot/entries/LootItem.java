package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItem extends LootPoolSingletonContainer {
   final Item item;

   LootItem(Item var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.item = â˜ƒ;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.ITEM;
   }

   @Override
   public void createItemStack(Consumer<ItemStack> var1, LootContext var2) {
      â˜ƒ.accept(new ItemStack(this.item));
   }

   public static LootPoolSingletonContainer.Builder<?> lootTableItem(ItemLike var0) {
      return simpleBuilder((var1, var2, var3, var4) -> new LootItem(â˜ƒ.asItem(), var1, var2, var3, var4));
   }

   public static class Serializer extends LootPoolSingletonContainer.Serializer<LootItem> {
      public void serializeCustom(JsonObject var1, LootItem var2, JsonSerializationContext var3) {
         super.serializeCustom(â˜ƒ, â˜ƒ, â˜ƒ);
         ResourceLocation â˜ƒ = Registry.ITEM.getKey(â˜ƒ.item);
         if (â˜ƒ == null) {
            throw new IllegalArgumentException("Can't serialize unknown item " + â˜ƒ.item);
         } else {
            â˜ƒ.addProperty("name", â˜ƒ.toString());
         }
      }

      protected LootItem deserialize(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6) {
         Item â˜ƒ = GsonHelper.getAsItem(â˜ƒ, "name");
         return new LootItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
