package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;

public class BlockPart {
   public final Vector3f field_178241_a;
   public final Vector3f field_178239_b;
   public final Map<EnumFacing, BlockPartFace> field_178240_c;
   public final BlockPartRotation field_178237_d;
   public final boolean field_178238_e;

   public BlockPart(Vector3f var1, Vector3f var2, Map<EnumFacing, BlockPartFace> var3, @Nullable BlockPartRotation var4, boolean var5) {
      this.field_178241_a = ☃;
      this.field_178239_b = ☃;
      this.field_178240_c = ☃;
      this.field_178237_d = ☃;
      this.field_178238_e = ☃;
      this.func_178235_a();
   }

   private void func_178235_a() {
      for(Entry<EnumFacing, BlockPartFace> ☃ : this.field_178240_c.entrySet()) {
         float[] ☃x = this.func_178236_a((EnumFacing)☃.getKey());
         ((BlockPartFace)☃.getValue()).field_178243_e.func_178349_a(☃x);
      }
   }

   private float[] func_178236_a(EnumFacing var1) {
      switch(☃) {
         case DOWN:
            return new float[]{
               this.field_178241_a.func_195899_a(),
               16.0F - this.field_178239_b.func_195902_c(),
               this.field_178239_b.func_195899_a(),
               16.0F - this.field_178241_a.func_195902_c()
            };
         case UP:
            return new float[]{
               this.field_178241_a.func_195899_a(),
               this.field_178241_a.func_195902_c(),
               this.field_178239_b.func_195899_a(),
               this.field_178239_b.func_195902_c()
            };
         case NORTH:
         default:
            return new float[]{
               16.0F - this.field_178239_b.func_195899_a(),
               16.0F - this.field_178239_b.func_195900_b(),
               16.0F - this.field_178241_a.func_195899_a(),
               16.0F - this.field_178241_a.func_195900_b()
            };
         case SOUTH:
            return new float[]{
               this.field_178241_a.func_195899_a(),
               16.0F - this.field_178239_b.func_195900_b(),
               this.field_178239_b.func_195899_a(),
               16.0F - this.field_178241_a.func_195900_b()
            };
         case WEST:
            return new float[]{
               this.field_178241_a.func_195902_c(),
               16.0F - this.field_178239_b.func_195900_b(),
               this.field_178239_b.func_195902_c(),
               16.0F - this.field_178241_a.func_195900_b()
            };
         case EAST:
            return new float[]{
               16.0F - this.field_178239_b.func_195902_c(),
               16.0F - this.field_178239_b.func_195900_b(),
               16.0F - this.field_178241_a.func_195902_c(),
               16.0F - this.field_178241_a.func_195900_b()
            };
      }
   }

   static class Deserializer implements JsonDeserializer<BlockPart> {
      public BlockPart deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         Vector3f ☃x = this.func_199330_e(☃);
         Vector3f ☃xx = this.func_199329_d(☃);
         BlockPartRotation ☃xxx = this.func_178256_a(☃);
         Map<EnumFacing, BlockPartFace> ☃xxxx = this.func_178250_a(☃, ☃);
         if (☃.has("shade") && !JsonUtils.func_180199_c(☃, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean ☃ = JsonUtils.func_151209_a(☃, "shade", true);
            return new BlockPart(☃x, ☃xx, ☃xxxx, ☃xxx, ☃);
         }
      }

      @Nullable
      private BlockPartRotation func_178256_a(JsonObject var1) {
         BlockPartRotation ☃ = null;
         if (☃.has("rotation")) {
            JsonObject ☃x = JsonUtils.func_152754_s(☃, "rotation");
            Vector3f ☃xx = this.func_199328_a(☃x, "origin");
            ☃xx.func_195898_a(0.0625F);
            EnumFacing.Axis ☃xxx = this.func_178252_c(☃x);
            float ☃xxxx = this.func_178255_b(☃x);
            boolean ☃xxxxx = JsonUtils.func_151209_a(☃x, "rescale", false);
            ☃ = new BlockPartRotation(☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
         }

         return ☃;
      }

      private float func_178255_b(JsonObject var1) {
         float ☃ = JsonUtils.func_151217_k(☃, "angle");
         if (☃ != 0.0F && MathHelper.func_76135_e(☃) != 22.5F && MathHelper.func_76135_e(☃) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + ☃ + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return ☃;
         }
      }

      private EnumFacing.Axis func_178252_c(JsonObject var1) {
         String ☃ = JsonUtils.func_151200_h(☃, "axis");
         EnumFacing.Axis ☃x = EnumFacing.Axis.func_176717_a(☃.toLowerCase(Locale.ROOT));
         if (☃x == null) {
            throw new JsonParseException("Invalid rotation axis: " + ☃);
         } else {
            return ☃x;
         }
      }

      private Map<EnumFacing, BlockPartFace> func_178250_a(JsonDeserializationContext var1, JsonObject var2) {
         Map<EnumFacing, BlockPartFace> ☃ = this.func_178253_b(☃, ☃);
         if (☃.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return ☃;
         }
      }

      private Map<EnumFacing, BlockPartFace> func_178253_b(JsonDeserializationContext var1, JsonObject var2) {
         Map<EnumFacing, BlockPartFace> ☃ = Maps.newEnumMap(EnumFacing.class);
         JsonObject ☃x = JsonUtils.func_152754_s(☃, "faces");

         for(Entry<String, JsonElement> ☃xx : ☃x.entrySet()) {
            EnumFacing ☃xxx = this.func_178248_a((String)☃xx.getKey());
            ☃.put(☃xxx, ☃.deserialize((JsonElement)☃xx.getValue(), BlockPartFace.class));
         }

         return ☃;
      }

      private EnumFacing func_178248_a(String var1) {
         EnumFacing ☃ = EnumFacing.func_176739_a(☃);
         if (☃ == null) {
            throw new JsonParseException("Unknown facing: " + ☃);
         } else {
            return ☃;
         }
      }

      private Vector3f func_199329_d(JsonObject var1) {
         Vector3f ☃ = this.func_199328_a(☃, "to");
         if (!(☃.func_195899_a() < -16.0F)
            && !(☃.func_195900_b() < -16.0F)
            && !(☃.func_195902_c() < -16.0F)
            && !(☃.func_195899_a() > 32.0F)
            && !(☃.func_195900_b() > 32.0F)
            && !(☃.func_195902_c() > 32.0F)) {
            return ☃;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + ☃);
         }
      }

      private Vector3f func_199330_e(JsonObject var1) {
         Vector3f ☃ = this.func_199328_a(☃, "from");
         if (!(☃.func_195899_a() < -16.0F)
            && !(☃.func_195900_b() < -16.0F)
            && !(☃.func_195902_c() < -16.0F)
            && !(☃.func_195899_a() > 32.0F)
            && !(☃.func_195900_b() > 32.0F)
            && !(☃.func_195902_c() > 32.0F)) {
            return ☃;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + ☃);
         }
      }

      private Vector3f func_199328_a(JsonObject var1, String var2) {
         JsonArray ☃ = JsonUtils.func_151214_t(☃, ☃);
         if (☃.size() != 3) {
            throw new JsonParseException("Expected 3 " + ☃ + " values, found: " + ☃.size());
         } else {
            float[] ☃ = new float[3];

            for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
               ☃[☃x] = JsonUtils.func_151220_d(☃.get(☃x), ☃ + "[" + ☃x + "]");
            }

            return new Vector3f(☃[0], ☃[1], ☃[2]);
         }
      }
   }
}
