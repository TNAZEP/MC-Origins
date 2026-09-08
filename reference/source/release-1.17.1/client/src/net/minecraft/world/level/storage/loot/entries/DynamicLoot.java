package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DynamicLoot extends LootPoolSingletonContainer {
   final ResourceLocation name;

   DynamicLoot(ResourceLocation var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.name = â˜ƒ;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.DYNAMIC;
   }

   @Override
   public void createItemStack(Consumer<ItemStack> var1, LootContext var2) {
      â˜ƒ.addDynamicDrops(this.name, â˜ƒ);
   }

   public static LootPoolSingletonContainer.Builder<?> dynamicEntry(ResourceLocation var0) {
      return simpleBuilder((var1, var2, var3, var4) -> new DynamicLoot(â˜ƒ, var1, var2, var3, var4));
   }

   public static class Serializer extends LootPoolSingletonContainer.Serializer<DynamicLoot> {
      public void serializeCustom(JsonObject var1, DynamicLoot var2, JsonSerializationContext var3) {
         super.serializeCustom(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("name", â˜ƒ.name.toString());
      }

      protected DynamicLoot deserialize(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "name"));
         return new DynamicLoot(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
