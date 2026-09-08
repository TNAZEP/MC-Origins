package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootTableReference extends LootPoolSingletonContainer {
   final ResourceLocation name;

   LootTableReference(ResourceLocation var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.name = â˜ƒ;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.REFERENCE;
   }

   @Override
   public void createItemStack(Consumer<ItemStack> var1, LootContext var2) {
      LootTable â˜ƒ = â˜ƒ.getLootTable(this.name);
      â˜ƒ.getRandomItemsRaw(â˜ƒ, â˜ƒ);
   }

   @Override
   public void validate(ValidationContext var1) {
      if (â˜ƒ.hasVisitedTable(this.name)) {
         â˜ƒ.reportProblem("Table " + this.name + " is recursively called");
      } else {
         super.validate(â˜ƒ);
         LootTable â˜ƒ = â˜ƒ.resolveLootTable(this.name);
         if (â˜ƒ == null) {
            â˜ƒ.reportProblem("Unknown loot table called " + this.name);
         } else {
            â˜ƒ.validate(â˜ƒ.enterTable("->{" + this.name + "}", this.name));
         }
      }
   }

   public static LootPoolSingletonContainer.Builder<?> lootTableReference(ResourceLocation var0) {
      return simpleBuilder((var1, var2, var3, var4) -> new LootTableReference(â˜ƒ, var1, var2, var3, var4));
   }

   public static class Serializer extends LootPoolSingletonContainer.Serializer<LootTableReference> {
      public void serializeCustom(JsonObject var1, LootTableReference var2, JsonSerializationContext var3) {
         super.serializeCustom(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("name", â˜ƒ.name.toString());
      }

      protected LootTableReference deserialize(
         JsonObject var1, JsonDeserializationContext var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6
      ) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "name"));
         return new LootTableReference(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
