package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

public class DistancePredicate {
   public static final DistancePredicate ANY = new DistancePredicate(
      MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY
   );
   private final MinMaxBounds.Doubles x;
   private final MinMaxBounds.Doubles y;
   private final MinMaxBounds.Doubles z;
   private final MinMaxBounds.Doubles horizontal;
   private final MinMaxBounds.Doubles absolute;

   public DistancePredicate(
      MinMaxBounds.Doubles var1, MinMaxBounds.Doubles var2, MinMaxBounds.Doubles var3, MinMaxBounds.Doubles var4, MinMaxBounds.Doubles var5
   ) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.horizontal = â˜ƒ;
      this.absolute = â˜ƒ;
   }

   public static DistancePredicate horizontal(MinMaxBounds.Doubles var0) {
      return new DistancePredicate(MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, â˜ƒ, MinMaxBounds.Doubles.ANY);
   }

   public static DistancePredicate vertical(MinMaxBounds.Doubles var0) {
      return new DistancePredicate(MinMaxBounds.Doubles.ANY, â˜ƒ, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY);
   }

   public static DistancePredicate absolute(MinMaxBounds.Doubles var0) {
      return new DistancePredicate(MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, â˜ƒ);
   }

   public boolean matches(double var1, double var3, double var5, double var7, double var9, double var11) {
      float â˜ƒ = (float)(â˜ƒ - â˜ƒ);
      float â˜ƒx = (float)(â˜ƒ - â˜ƒ);
      float â˜ƒxx = (float)(â˜ƒ - â˜ƒ);
      if (!this.x.matches((double)Mth.abs(â˜ƒ)) || !this.y.matches((double)Mth.abs(â˜ƒx)) || !this.z.matches((double)Mth.abs(â˜ƒxx))) {
         return false;
      } else if (!this.horizontal.matchesSqr((double)(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx))) {
         return false;
      } else {
         return this.absolute.matchesSqr((double)(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx));
      }
   }

   public static DistancePredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "distance");
         MinMaxBounds.Doubles â˜ƒx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("x"));
         MinMaxBounds.Doubles â˜ƒxx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("y"));
         MinMaxBounds.Doubles â˜ƒxxx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("z"));
         MinMaxBounds.Doubles â˜ƒxxxx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("horizontal"));
         MinMaxBounds.Doubles â˜ƒxxxxx = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("absolute"));
         return new DistancePredicate(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("x", this.x.serializeToJson());
         â˜ƒ.add("y", this.y.serializeToJson());
         â˜ƒ.add("z", this.z.serializeToJson());
         â˜ƒ.add("horizontal", this.horizontal.serializeToJson());
         â˜ƒ.add("absolute", this.absolute.serializeToJson());
         return â˜ƒ;
      }
   }
}
