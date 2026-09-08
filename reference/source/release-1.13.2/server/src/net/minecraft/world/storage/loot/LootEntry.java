package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public abstract class LootEntry {
   protected final int field_186364_c;
   protected final int field_186365_d;
   protected final LootCondition[] field_186366_e;

   protected LootEntry(int var1, int var2, LootCondition[] var3) {
      this.field_186364_c = ☃;
      this.field_186365_d = ☃;
      this.field_186366_e = ☃;
   }

   public int func_186361_a(float var1) {
      return Math.max(MathHelper.func_76141_d((float)this.field_186364_c + (float)this.field_186365_d * ☃), 0);
   }

   public abstract void func_186363_a(Collection<ItemStack> var1, Random var2, LootContext var3);

   protected abstract void func_186362_a(JsonObject var1, JsonSerializationContext var2);

   public static class Serializer implements JsonDeserializer<LootEntry>, JsonSerializer<LootEntry> {
      public LootEntry deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃x = JsonUtils.func_151210_l(☃, "loot item");
         String ☃xx = JsonUtils.func_151200_h(☃x, "type");
         int ☃xxx = JsonUtils.func_151208_a(☃x, "weight", 1);
         int ☃xxxx = JsonUtils.func_151208_a(☃x, "quality", 0);
         LootCondition[] ☃;
         if (☃x.has("conditions")) {
            ☃ = (LootCondition[])JsonUtils.func_188174_a(☃x, "conditions", ☃, LootCondition[].class);
         } else {
            ☃ = new LootCondition[0];
         }

         if ("item".equals(☃xx)) {
            return LootEntryItem.func_186367_a(☃x, ☃, ☃xxx, ☃xxxx, ☃);
         } else if ("loot_table".equals(☃xx)) {
            return LootEntryTable.func_186370_a(☃x, ☃, ☃xxx, ☃xxxx, ☃);
         } else if ("empty".equals(☃xx)) {
            return LootEntryEmpty.func_186372_a(☃x, ☃, ☃xxx, ☃xxxx, ☃);
         } else {
            throw new JsonSyntaxException("Unknown loot entry type '" + ☃xx + "'");
         }
      }

      public JsonElement serialize(LootEntry var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("weight", ☃.field_186364_c);
         ☃.addProperty("quality", ☃.field_186365_d);
         if (☃.field_186366_e.length > 0) {
            ☃.add("conditions", ☃.serialize(☃.field_186366_e));
         }

         if (☃ instanceof LootEntryItem) {
            ☃.addProperty("type", "item");
         } else if (☃ instanceof LootEntryTable) {
            ☃.addProperty("type", "loot_table");
         } else {
            if (!(☃ instanceof LootEntryEmpty)) {
               throw new IllegalArgumentException("Don't know how to serialize " + ☃);
            }

            ☃.addProperty("type", "empty");
         }

         ☃.func_186362_a(☃, ☃);
         return ☃;
      }
   }
}
