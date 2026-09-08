package net.minecraft.client.renderer.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Variant {
   private final ResourceLocation field_188050_a;
   private final ModelRotation field_188051_b;
   private final boolean field_188052_c;
   private final int field_188053_d;

   public Variant(ResourceLocation var1, ModelRotation var2, boolean var3, int var4) {
      this.field_188050_a = ☃;
      this.field_188051_b = ☃;
      this.field_188052_c = ☃;
      this.field_188053_d = ☃;
   }

   public ResourceLocation func_188046_a() {
      return this.field_188050_a;
   }

   public ModelRotation func_188048_b() {
      return this.field_188051_b;
   }

   public boolean func_188049_c() {
      return this.field_188052_c;
   }

   public int func_188047_d() {
      return this.field_188053_d;
   }

   public String toString() {
      return "Variant{modelLocation="
         + this.field_188050_a
         + ", rotation="
         + this.field_188051_b
         + ", uvLock="
         + this.field_188052_c
         + ", weight="
         + this.field_188053_d
         + '}';
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof Variant)) {
         return false;
      } else {
         Variant ☃ = (Variant)☃;
         return this.field_188050_a.equals(☃.field_188050_a)
            && this.field_188051_b == ☃.field_188051_b
            && this.field_188052_c == ☃.field_188052_c
            && this.field_188053_d == ☃.field_188053_d;
      }
   }

   public int hashCode() {
      int ☃ = this.field_188050_a.hashCode();
      ☃ = 31 * ☃ + this.field_188051_b.hashCode();
      ☃ = 31 * ☃ + Boolean.valueOf(this.field_188052_c).hashCode();
      return 31 * ☃ + this.field_188053_d;
   }

   public static class Deserializer implements JsonDeserializer<Variant> {
      public Variant deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         ResourceLocation ☃x = this.func_188043_b(☃);
         ModelRotation ☃xx = this.func_188042_a(☃);
         boolean ☃xxx = this.func_188044_d(☃);
         int ☃xxxx = this.func_188045_c(☃);
         return new Variant(☃x, ☃xx, ☃xxx, ☃xxxx);
      }

      private boolean func_188044_d(JsonObject var1) {
         return JsonUtils.func_151209_a(☃, "uvlock", false);
      }

      protected ModelRotation func_188042_a(JsonObject var1) {
         int ☃ = JsonUtils.func_151208_a(☃, "x", 0);
         int ☃x = JsonUtils.func_151208_a(☃, "y", 0);
         ModelRotation ☃xx = ModelRotation.func_177524_a(☃, ☃x);
         if (☃xx == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + ☃ + ", y: " + ☃x);
         } else {
            return ☃xx;
         }
      }

      protected ResourceLocation func_188043_b(JsonObject var1) {
         return new ResourceLocation(JsonUtils.func_151200_h(☃, "model"));
      }

      protected int func_188045_c(JsonObject var1) {
         int ☃ = JsonUtils.func_151208_a(☃, "weight", 1);
         if (☃ < 1) {
            throw new JsonParseException("Invalid weight " + ☃ + " found, expected integer >= 1");
         } else {
            return ☃;
         }
      }
   }
}
