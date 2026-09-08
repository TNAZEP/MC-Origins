package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;

public class FishingHookPredicate {
   public static final FishingHookPredicate ANY = new FishingHookPredicate(false);
   private static final String IN_OPEN_WATER_KEY = "in_open_water";
   private final boolean inOpenWater;

   private FishingHookPredicate(boolean var1) {
      this.inOpenWater = â˜ƒ;
   }

   public static FishingHookPredicate inOpenWater(boolean var0) {
      return new FishingHookPredicate(â˜ƒ);
   }

   public static FishingHookPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "fishing_hook");
         JsonElement â˜ƒx = â˜ƒ.get("in_open_water");
         return â˜ƒx != null ? new FishingHookPredicate(GsonHelper.convertToBoolean(â˜ƒx, "in_open_water")) : ANY;
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("in_open_water", new JsonPrimitive(this.inOpenWater));
         return â˜ƒ;
      }
   }

   public boolean matches(Entity var1) {
      if (this == ANY) {
         return true;
      } else if (!(â˜ƒ instanceof FishingHook)) {
         return false;
      } else {
         FishingHook â˜ƒ = (FishingHook)â˜ƒ;
         return this.inOpenWater == â˜ƒ.isOpenWaterFishing();
      }
   }
}
