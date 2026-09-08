package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;

public class DistancePredicate {
   public static final DistancePredicate field_193423_a = new DistancePredicate(
      MinMaxBounds.FloatBound.field_211359_e,
      MinMaxBounds.FloatBound.field_211359_e,
      MinMaxBounds.FloatBound.field_211359_e,
      MinMaxBounds.FloatBound.field_211359_e,
      MinMaxBounds.FloatBound.field_211359_e
   );
   private final MinMaxBounds.FloatBound field_193424_b;
   private final MinMaxBounds.FloatBound field_193425_c;
   private final MinMaxBounds.FloatBound field_193426_d;
   private final MinMaxBounds.FloatBound field_193427_e;
   private final MinMaxBounds.FloatBound field_193428_f;

   public DistancePredicate(
      MinMaxBounds.FloatBound var1, MinMaxBounds.FloatBound var2, MinMaxBounds.FloatBound var3, MinMaxBounds.FloatBound var4, MinMaxBounds.FloatBound var5
   ) {
      this.field_193424_b = ☃;
      this.field_193425_c = ☃;
      this.field_193426_d = ☃;
      this.field_193427_e = ☃;
      this.field_193428_f = ☃;
   }

   public static DistancePredicate func_203995_a(MinMaxBounds.FloatBound var0) {
      return new DistancePredicate(
         MinMaxBounds.FloatBound.field_211359_e,
         MinMaxBounds.FloatBound.field_211359_e,
         MinMaxBounds.FloatBound.field_211359_e,
         ☃,
         MinMaxBounds.FloatBound.field_211359_e
      );
   }

   public static DistancePredicate func_203993_b(MinMaxBounds.FloatBound var0) {
      return new DistancePredicate(
         MinMaxBounds.FloatBound.field_211359_e,
         ☃,
         MinMaxBounds.FloatBound.field_211359_e,
         MinMaxBounds.FloatBound.field_211359_e,
         MinMaxBounds.FloatBound.field_211359_e
      );
   }

   public boolean func_193422_a(double var1, double var3, double var5, double var7, double var9, double var11) {
      float ☃ = (float)(☃ - ☃);
      float ☃x = (float)(☃ - ☃);
      float ☃xx = (float)(☃ - ☃);
      if (!this.field_193424_b.func_211354_d(MathHelper.func_76135_e(☃))
         || !this.field_193425_c.func_211354_d(MathHelper.func_76135_e(☃x))
         || !this.field_193426_d.func_211354_d(MathHelper.func_76135_e(☃xx))) {
         return false;
      } else if (!this.field_193427_e.func_211351_a((double)(☃ * ☃ + ☃xx * ☃xx))) {
         return false;
      } else {
         return this.field_193428_f.func_211351_a((double)(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx));
      }
   }

   public static DistancePredicate func_193421_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "distance");
         MinMaxBounds.FloatBound ☃x = MinMaxBounds.FloatBound.func_211356_a(☃.get("x"));
         MinMaxBounds.FloatBound ☃xx = MinMaxBounds.FloatBound.func_211356_a(☃.get("y"));
         MinMaxBounds.FloatBound ☃xxx = MinMaxBounds.FloatBound.func_211356_a(☃.get("z"));
         MinMaxBounds.FloatBound ☃xxxx = MinMaxBounds.FloatBound.func_211356_a(☃.get("horizontal"));
         MinMaxBounds.FloatBound ☃xxxxx = MinMaxBounds.FloatBound.func_211356_a(☃.get("absolute"));
         return new DistancePredicate(☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
      } else {
         return field_193423_a;
      }
   }

   public JsonElement func_203994_a() {
      if (this == field_193423_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         ☃.add("x", this.field_193424_b.func_200321_c());
         ☃.add("y", this.field_193425_c.func_200321_c());
         ☃.add("z", this.field_193426_d.func_200321_c());
         ☃.add("horizontal", this.field_193427_e.func_200321_c());
         ☃.add("absolute", this.field_193428_f.func_200321_c());
         return ☃;
      }
   }
}
