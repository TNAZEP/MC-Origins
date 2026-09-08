package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootEntryTable extends LootEntry {
   protected final ResourceLocation field_186371_a;

   public LootEntryTable(ResourceLocation var1, int var2, int var3, LootCondition[] var4) {
      super(☃, ☃, ☃);
      this.field_186371_a = ☃;
   }

   @Override
   public void func_186363_a(Collection<ItemStack> var1, Random var2, LootContext var3) {
      LootTable ☃ = ☃.func_186497_e().func_186521_a(this.field_186371_a);
      ☃.addAll(☃.func_186462_a(☃, ☃));
   }

   @Override
   protected void func_186362_a(JsonObject var1, JsonSerializationContext var2) {
      ☃.addProperty("name", this.field_186371_a.toString());
   }

   public static LootEntryTable func_186370_a(JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootCondition[] var4) {
      ResourceLocation ☃ = new ResourceLocation(JsonUtils.func_151200_h(☃, "name"));
      return new LootEntryTable(☃, ☃, ☃, ☃);
   }
}
