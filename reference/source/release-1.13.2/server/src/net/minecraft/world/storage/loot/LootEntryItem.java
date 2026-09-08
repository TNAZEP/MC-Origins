package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class LootEntryItem extends LootEntry {
   protected final Item field_186368_a;
   protected final LootFunction[] field_186369_b;

   public LootEntryItem(Item var1, int var2, int var3, LootFunction[] var4, LootCondition[] var5) {
      super(☃, ☃, ☃);
      this.field_186368_a = ☃;
      this.field_186369_b = ☃;
   }

   @Override
   public void func_186363_a(Collection<ItemStack> var1, Random var2, LootContext var3) {
      ItemStack ☃ = new ItemStack(this.field_186368_a);

      for(LootFunction ☃x : this.field_186369_b) {
         if (LootConditionManager.func_186638_a(☃x.func_186554_a(), ☃, ☃)) {
            ☃ = ☃x.func_186553_a(☃, ☃, ☃);
         }
      }

      if (!☃.func_190926_b()) {
         if (☃.func_190916_E() < this.field_186368_a.func_77639_j()) {
            ☃.add(☃);
         } else {
            int ☃x = ☃.func_190916_E();

            while(☃x > 0) {
               ItemStack ☃xx = ☃.func_77946_l();
               ☃xx.func_190920_e(Math.min(☃.func_77976_d(), ☃x));
               ☃x -= ☃xx.func_190916_E();
               ☃.add(☃xx);
            }
         }
      }
   }

   @Override
   protected void func_186362_a(JsonObject var1, JsonSerializationContext var2) {
      if (this.field_186369_b != null && this.field_186369_b.length > 0) {
         ☃.add("functions", ☃.serialize(this.field_186369_b));
      }

      ResourceLocation ☃ = IRegistry.field_212630_s.func_177774_c(this.field_186368_a);
      if (☃ == null) {
         throw new IllegalArgumentException("Can't serialize unknown item " + this.field_186368_a);
      } else {
         ☃.addProperty("name", ☃.toString());
      }
   }

   public static LootEntryItem func_186367_a(JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootCondition[] var4) {
      Item ☃x = JsonUtils.func_188180_i(☃, "name");
      LootFunction[] ☃;
      if (☃.has("functions")) {
         ☃ = (LootFunction[])JsonUtils.func_188174_a(☃, "functions", ☃, LootFunction[].class);
      } else {
         ☃ = new LootFunction[0];
      }

      return new LootEntryItem(☃x, ☃, ☃, ☃, ☃);
   }
}
