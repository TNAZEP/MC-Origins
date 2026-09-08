package net.minecraft.client.renderer.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;

public class BlockFaceUV {
   public float[] field_178351_a;
   public final int field_178350_b;

   public BlockFaceUV(@Nullable float[] var1, int var2) {
      this.field_178351_a = ☃;
      this.field_178350_b = ☃;
   }

   public float func_178348_a(int var1) {
      if (this.field_178351_a == null) {
         throw new NullPointerException("uvs");
      } else {
         int ☃ = this.func_178347_d(☃);
         return this.field_178351_a[☃ != 0 && ☃ != 1 ? 2 : 0];
      }
   }

   public float func_178346_b(int var1) {
      if (this.field_178351_a == null) {
         throw new NullPointerException("uvs");
      } else {
         int ☃ = this.func_178347_d(☃);
         return this.field_178351_a[☃ != 0 && ☃ != 3 ? 3 : 1];
      }
   }

   private int func_178347_d(int var1) {
      return (☃ + this.field_178350_b / 90) % 4;
   }

   public int func_178345_c(int var1) {
      return (☃ + 4 - this.field_178350_b / 90) % 4;
   }

   public void func_178349_a(float[] var1) {
      if (this.field_178351_a == null) {
         this.field_178351_a = ☃;
      }
   }

   static class Deserializer implements JsonDeserializer<BlockFaceUV> {
      public BlockFaceUV deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         float[] ☃x = this.func_178292_b(☃);
         int ☃xx = this.func_178291_a(☃);
         return new BlockFaceUV(☃x, ☃xx);
      }

      protected int func_178291_a(JsonObject var1) {
         int ☃ = JsonUtils.func_151208_a(☃, "rotation", 0);
         if (☃ >= 0 && ☃ % 90 == 0 && ☃ / 90 <= 3) {
            return ☃;
         } else {
            throw new JsonParseException("Invalid rotation " + ☃ + " found, only 0/90/180/270 allowed");
         }
      }

      @Nullable
      private float[] func_178292_b(JsonObject var1) {
         if (!☃.has("uv")) {
            return null;
         } else {
            JsonArray ☃ = JsonUtils.func_151214_t(☃, "uv");
            if (☃.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + ☃.size());
            } else {
               float[] ☃ = new float[4];

               for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
                  ☃[☃x] = JsonUtils.func_151220_d(☃.get(☃x), "uv[" + ☃x + "]");
               }

               return ☃;
            }
         }
      }
   }
}
