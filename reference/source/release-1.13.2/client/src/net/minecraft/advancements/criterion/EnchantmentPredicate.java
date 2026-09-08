package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class EnchantmentPredicate {
   public static final EnchantmentPredicate field_192466_a = new EnchantmentPredicate();
   private final Enchantment field_192467_b;
   private final MinMaxBounds.IntBound field_192468_c;

   public EnchantmentPredicate() {
      this.field_192467_b = null;
      this.field_192468_c = MinMaxBounds.IntBound.field_211347_e;
   }

   public EnchantmentPredicate(@Nullable Enchantment var1, MinMaxBounds.IntBound var2) {
      this.field_192467_b = ☃;
      this.field_192468_c = ☃;
   }

   public boolean func_192463_a(Map<Enchantment, Integer> var1) {
      if (this.field_192467_b != null) {
         if (!☃.containsKey(this.field_192467_b)) {
            return false;
         }

         int ☃ = ☃.get(this.field_192467_b);
         if (this.field_192468_c != null && !this.field_192468_c.func_211339_d(☃)) {
            return false;
         }
      } else if (this.field_192468_c != null) {
         for(Integer ☃ : ☃.values()) {
            if (this.field_192468_c.func_211339_d(☃)) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public JsonElement func_200306_a() {
      if (this == field_192466_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         if (this.field_192467_b != null) {
            ☃.addProperty("enchantment", IRegistry.field_212628_q.func_177774_c(this.field_192467_b).toString());
         }

         ☃.add("levels", this.field_192468_c.func_200321_c());
         return ☃;
      }
   }

   public static EnchantmentPredicate func_192464_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "enchantment");
         Enchantment ☃x = null;
         if (☃.has("enchantment")) {
            ResourceLocation ☃xx = new ResourceLocation(JsonUtils.func_151200_h(☃, "enchantment"));
            ☃x = IRegistry.field_212628_q.func_212608_b(☃xx);
            if (☃x == null) {
               throw new JsonSyntaxException("Unknown enchantment '" + ☃xx + "'");
            }
         }

         MinMaxBounds.IntBound ☃ = MinMaxBounds.IntBound.func_211344_a(☃.get("levels"));
         return new EnchantmentPredicate(☃x, ☃);
      } else {
         return field_192466_a;
      }
   }

   public static EnchantmentPredicate[] func_192465_b(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonArray ☃ = JsonUtils.func_151207_m(☃, "enchantments");
         EnchantmentPredicate[] ☃x = new EnchantmentPredicate[☃.size()];

         for(int ☃xx = 0; ☃xx < ☃x.length; ++☃xx) {
            ☃x[☃xx] = func_192464_a(☃.get(☃xx));
         }

         return ☃x;
      } else {
         return new EnchantmentPredicate[0];
      }
   }
}
