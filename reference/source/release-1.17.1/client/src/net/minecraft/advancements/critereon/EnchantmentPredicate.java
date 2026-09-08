package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentPredicate {
   public static final EnchantmentPredicate ANY = new EnchantmentPredicate();
   public static final EnchantmentPredicate[] NONE = new EnchantmentPredicate[0];
   private final Enchantment enchantment;
   private final MinMaxBounds.Ints level;

   public EnchantmentPredicate() {
      this.enchantment = null;
      this.level = MinMaxBounds.Ints.ANY;
   }

   public EnchantmentPredicate(@Nullable Enchantment var1, MinMaxBounds.Ints var2) {
      this.enchantment = â˜ƒ;
      this.level = â˜ƒ;
   }

   public boolean containedIn(Map<Enchantment, Integer> var1) {
      if (this.enchantment != null) {
         if (!â˜ƒ.containsKey(this.enchantment)) {
            return false;
         }

         int â˜ƒ = â˜ƒ.get(this.enchantment);
         if (this.level != null && !this.level.matches(â˜ƒ)) {
            return false;
         }
      } else if (this.level != null) {
         for(Integer â˜ƒ : â˜ƒ.values()) {
            if (this.level.matches(â˜ƒ)) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.enchantment != null) {
            â˜ƒ.addProperty("enchantment", Registry.ENCHANTMENT.getKey(this.enchantment).toString());
         }

         â˜ƒ.add("levels", this.level.serializeToJson());
         return â˜ƒ;
      }
   }

   public static EnchantmentPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "enchantment");
         Enchantment â˜ƒx = null;
         if (â˜ƒ.has("enchantment")) {
            ResourceLocation â˜ƒxx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "enchantment"));
            â˜ƒx = (Enchantment)Registry.ENCHANTMENT.getOptional(â˜ƒxx).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + â˜ƒ + "'"));
         }

         MinMaxBounds.Ints â˜ƒ = MinMaxBounds.Ints.fromJson(â˜ƒ.get("levels"));
         return new EnchantmentPredicate(â˜ƒx, â˜ƒ);
      } else {
         return ANY;
      }
   }

   public static EnchantmentPredicate[] fromJsonArray(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonArray â˜ƒ = GsonHelper.convertToJsonArray(â˜ƒ, "enchantments");
         EnchantmentPredicate[] â˜ƒx = new EnchantmentPredicate[â˜ƒ.size()];

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.length; ++â˜ƒxx) {
            â˜ƒx[â˜ƒxx] = fromJson(â˜ƒ.get(â˜ƒxx));
         }

         return â˜ƒx;
      } else {
         return NONE;
      }
   }
}
