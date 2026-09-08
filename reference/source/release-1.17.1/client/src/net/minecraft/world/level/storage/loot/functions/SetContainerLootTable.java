package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetContainerLootTable extends LootItemConditionalFunction {
   final ResourceLocation name;
   final long seed;

   SetContainerLootTable(LootItemCondition[] var1, ResourceLocation var2, long var3) {
      super(â˜ƒ);
      this.name = â˜ƒ;
      this.seed = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_LOOT_TABLE;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      if (â˜ƒ.isEmpty()) {
         return â˜ƒ;
      } else {
         CompoundTag â˜ƒ = new CompoundTag();
         â˜ƒ.putString("LootTable", this.name.toString());
         if (this.seed != 0L) {
            â˜ƒ.putLong("LootTableSeed", this.seed);
         }

         â˜ƒ.getOrCreateTag().put("BlockEntityTag", â˜ƒ);
         return â˜ƒ;
      }
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

   public static LootItemConditionalFunction.Builder<?> withLootTable(ResourceLocation var0) {
      return simpleBuilder(var1 -> new SetContainerLootTable(var1, â˜ƒ, 0L));
   }

   public static LootItemConditionalFunction.Builder<?> withLootTable(ResourceLocation var0, long var1) {
      return simpleBuilder(var3 -> new SetContainerLootTable(var3, â˜ƒ, â˜ƒ));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetContainerLootTable> {
      public void serialize(JsonObject var1, SetContainerLootTable var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("name", â˜ƒ.name.toString());
         if (â˜ƒ.seed != 0L) {
            â˜ƒ.addProperty("seed", â˜ƒ.seed);
         }
      }

      public SetContainerLootTable deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "name"));
         long â˜ƒx = GsonHelper.getAsLong(â˜ƒ, "seed", 0L);
         return new SetContainerLootTable(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }
}
