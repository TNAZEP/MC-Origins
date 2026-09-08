package net.minecraft.world.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import org.apache.commons.lang3.ArrayUtils;

public class LootPool {
   private final LootEntry[] field_186453_a;
   private final LootCondition[] field_186454_b;
   private final RandomValueRange field_186455_c;
   private final RandomValueRange field_186456_d;

   public LootPool(LootEntry[] var1, LootCondition[] var2, RandomValueRange var3, RandomValueRange var4) {
      this.field_186453_a = ☃;
      this.field_186454_b = ☃;
      this.field_186455_c = ☃;
      this.field_186456_d = ☃;
   }

   protected void func_186452_a(Collection<ItemStack> var1, Random var2, LootContext var3) {
      List<LootEntry> ☃ = Lists.<LootEntry>newArrayList();
      int ☃x = 0;

      for(LootEntry ☃xx : this.field_186453_a) {
         if (LootConditionManager.func_186638_a(☃xx.field_186366_e, ☃, ☃)) {
            int ☃xxx = ☃xx.func_186361_a(☃.func_186491_f());
            if (☃xxx > 0) {
               ☃.add(☃xx);
               ☃x += ☃xxx;
            }
         }
      }

      if (☃x != 0 && !☃.isEmpty()) {
         int ☃xx = ☃.nextInt(☃x);

         for(LootEntry ☃xxx : ☃) {
            ☃xx -= ☃xxx.func_186361_a(☃.func_186491_f());
            if (☃xx < 0) {
               ☃xxx.func_186363_a(☃, ☃, ☃);
               return;
            }
         }
      }
   }

   public void func_186449_b(Collection<ItemStack> var1, Random var2, LootContext var3) {
      if (LootConditionManager.func_186638_a(this.field_186454_b, ☃, ☃)) {
         int ☃ = this.field_186455_c.func_186511_a(☃) + MathHelper.func_76141_d(this.field_186456_d.func_186507_b(☃) * ☃.func_186491_f());

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            this.func_186452_a(☃, ☃, ☃);
         }
      }
   }

   public static class Serializer implements JsonDeserializer<LootPool>, JsonSerializer<LootPool> {
      public LootPool deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "loot pool");
         LootEntry[] ☃x = (LootEntry[])JsonUtils.func_188174_a(☃, "entries", ☃, LootEntry[].class);
         LootCondition[] ☃xx = (LootCondition[])JsonUtils.func_188177_a(☃, "conditions", new LootCondition[0], ☃, LootCondition[].class);
         RandomValueRange ☃xxx = JsonUtils.func_188174_a(☃, "rolls", ☃, RandomValueRange.class);
         RandomValueRange ☃xxxx = JsonUtils.func_188177_a(☃, "bonus_rolls", new RandomValueRange(0.0F, 0.0F), ☃, RandomValueRange.class);
         return new LootPool(☃x, ☃xx, ☃xxx, ☃xxxx);
      }

      public JsonElement serialize(LootPool var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         ☃.add("entries", ☃.serialize(☃.field_186453_a));
         ☃.add("rolls", ☃.serialize(☃.field_186455_c));
         if (☃.field_186456_d.func_186509_a() != 0.0F && ☃.field_186456_d.func_186512_b() != 0.0F) {
            ☃.add("bonus_rolls", ☃.serialize(☃.field_186456_d));
         }

         if (!ArrayUtils.isEmpty((Object[])☃.field_186454_b)) {
            ☃.add("conditions", ☃.serialize(☃.field_186454_b));
         }

         return ☃;
      }
   }
}
