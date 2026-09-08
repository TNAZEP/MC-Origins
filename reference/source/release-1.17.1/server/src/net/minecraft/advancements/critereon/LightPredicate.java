package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;

public class LightPredicate {
   public static final LightPredicate ANY = new LightPredicate(MinMaxBounds.Ints.ANY);
   private final MinMaxBounds.Ints composite;

   LightPredicate(MinMaxBounds.Ints var1) {
      this.composite = â˜ƒ;
   }

   public boolean matches(ServerLevel var1, BlockPos var2) {
      if (this == ANY) {
         return true;
      } else if (!â˜ƒ.isLoaded(â˜ƒ)) {
         return false;
      } else {
         return this.composite.matches(â˜ƒ.getMaxLocalRawBrightness(â˜ƒ));
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("light", this.composite.serializeToJson());
         return â˜ƒ;
      }
   }

   public static LightPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "light");
         MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("light"));
         return new LightPredicate(â˜ƒx);
      } else {
         return ANY;
      }
   }

   public static class Builder {
      private MinMaxBounds.Ints composite = MinMaxBounds.Ints.ANY;

      public static LightPredicate.Builder light() {
         return new LightPredicate.Builder();
      }

      public LightPredicate.Builder setComposite(MinMaxBounds.Ints var1) {
         this.composite = â˜ƒ;
         return this;
      }

      public LightPredicate build() {
         return new LightPredicate(this.composite);
      }
   }
}
