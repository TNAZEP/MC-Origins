package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;

public class LocationPredicate {
   public static final LocationPredicate field_193455_a = new LocationPredicate(
      MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, null, null, null
   );
   private final MinMaxBounds.FloatBound field_193457_c;
   private final MinMaxBounds.FloatBound field_193458_d;
   private final MinMaxBounds.FloatBound field_193459_e;
   @Nullable
   private final Biome field_193456_b;
   @Nullable
   private final String field_193460_f;
   @Nullable
   private final DimensionType field_193461_g;

   public LocationPredicate(
      MinMaxBounds.FloatBound var1,
      MinMaxBounds.FloatBound var2,
      MinMaxBounds.FloatBound var3,
      @Nullable Biome var4,
      @Nullable String var5,
      @Nullable DimensionType var6
   ) {
      this.field_193457_c = ☃;
      this.field_193458_d = ☃;
      this.field_193459_e = ☃;
      this.field_193456_b = ☃;
      this.field_193460_f = ☃;
      this.field_193461_g = ☃;
   }

   public static LocationPredicate func_204010_a(Biome var0) {
      return new LocationPredicate(
         MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, ☃, null, null
      );
   }

   public static LocationPredicate func_204008_a(DimensionType var0) {
      return new LocationPredicate(
         MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, null, null, ☃
      );
   }

   public static LocationPredicate func_204007_a(String var0) {
      return new LocationPredicate(
         MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, MinMaxBounds.FloatBound.field_211359_e, null, ☃, null
      );
   }

   public boolean func_193452_a(WorldServer var1, double var2, double var4, double var6) {
      return this.func_193453_a(☃, (float)☃, (float)☃, (float)☃);
   }

   public boolean func_193453_a(WorldServer var1, float var2, float var3, float var4) {
      if (!this.field_193457_c.func_211354_d(☃)) {
         return false;
      } else if (!this.field_193458_d.func_211354_d(☃)) {
         return false;
      } else if (!this.field_193459_e.func_211354_d(☃)) {
         return false;
      } else if (this.field_193461_g != null && this.field_193461_g != ☃.field_73011_w.func_186058_p()) {
         return false;
      } else {
         BlockPos ☃ = new BlockPos((double)☃, (double)☃, (double)☃);
         if (this.field_193456_b != null && this.field_193456_b != ☃.func_180494_b(☃)) {
            return false;
         } else {
            return this.field_193460_f == null || Feature.func_202280_a(☃, this.field_193460_f, ☃);
         }
      }
   }

   public JsonElement func_204009_a() {
      if (this == field_193455_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         if (!this.field_193457_c.func_211335_c() || !this.field_193458_d.func_211335_c() || !this.field_193459_e.func_211335_c()) {
            JsonObject ☃x = new JsonObject();
            ☃x.add("x", this.field_193457_c.func_200321_c());
            ☃x.add("y", this.field_193458_d.func_200321_c());
            ☃x.add("z", this.field_193459_e.func_200321_c());
            ☃.add("position", ☃x);
         }

         if (this.field_193461_g != null) {
            ☃.addProperty("dimension", DimensionType.func_212678_a(this.field_193461_g).toString());
         }

         if (this.field_193460_f != null) {
            ☃.addProperty("feature", this.field_193460_f);
         }

         if (this.field_193456_b != null) {
            ☃.addProperty("biome", IRegistry.field_212624_m.func_177774_c(this.field_193456_b).toString());
         }

         return ☃;
      }
   }

   public static LocationPredicate func_193454_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "location");
         JsonObject ☃x = JsonUtils.func_151218_a(☃, "position", new JsonObject());
         MinMaxBounds.FloatBound ☃xx = MinMaxBounds.FloatBound.func_211356_a(☃x.get("x"));
         MinMaxBounds.FloatBound ☃xxx = MinMaxBounds.FloatBound.func_211356_a(☃x.get("y"));
         MinMaxBounds.FloatBound ☃xxxx = MinMaxBounds.FloatBound.func_211356_a(☃x.get("z"));
         DimensionType ☃xxxxx = ☃.has("dimension") ? DimensionType.func_193417_a(new ResourceLocation(JsonUtils.func_151200_h(☃, "dimension"))) : null;
         String ☃xxxxxx = ☃.has("feature") ? JsonUtils.func_151200_h(☃, "feature") : null;
         Biome ☃xxxxxxx = null;
         if (☃.has("biome")) {
            ResourceLocation ☃xxxxxxxx = new ResourceLocation(JsonUtils.func_151200_h(☃, "biome"));
            ☃xxxxxxx = IRegistry.field_212624_m.func_212608_b(☃xxxxxxxx);
            if (☃xxxxxxx == null) {
               throw new JsonSyntaxException("Unknown biome '" + ☃xxxxxxxx + "'");
            }
         }

         return new LocationPredicate(☃xx, ☃xxx, ☃xxxx, ☃xxxxxxx, ☃xxxxxx, ☃xxxxx);
      } else {
         return field_193455_a;
      }
   }
}
