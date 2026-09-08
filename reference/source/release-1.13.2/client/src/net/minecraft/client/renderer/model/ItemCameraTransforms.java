package net.minecraft.client.renderer.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;

public class ItemCameraTransforms {
   public static final ItemCameraTransforms field_178357_a = new ItemCameraTransforms();
   public static float field_181690_b;
   public static float field_181691_c;
   public static float field_181692_d;
   public static float field_181693_e;
   public static float field_181694_f;
   public static float field_181695_g;
   public static float field_181696_h;
   public static float field_181697_i;
   public static float field_181698_j;
   public final ItemTransformVec3f field_188036_k;
   public final ItemTransformVec3f field_188037_l;
   public final ItemTransformVec3f field_188038_m;
   public final ItemTransformVec3f field_188039_n;
   public final ItemTransformVec3f field_178353_d;
   public final ItemTransformVec3f field_178354_e;
   public final ItemTransformVec3f field_181699_o;
   public final ItemTransformVec3f field_181700_p;

   private ItemCameraTransforms() {
      this(
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a,
         ItemTransformVec3f.field_178366_a
      );
   }

   public ItemCameraTransforms(ItemCameraTransforms var1) {
      this.field_188036_k = ☃.field_188036_k;
      this.field_188037_l = ☃.field_188037_l;
      this.field_188038_m = ☃.field_188038_m;
      this.field_188039_n = ☃.field_188039_n;
      this.field_178353_d = ☃.field_178353_d;
      this.field_178354_e = ☃.field_178354_e;
      this.field_181699_o = ☃.field_181699_o;
      this.field_181700_p = ☃.field_181700_p;
   }

   public ItemCameraTransforms(
      ItemTransformVec3f var1,
      ItemTransformVec3f var2,
      ItemTransformVec3f var3,
      ItemTransformVec3f var4,
      ItemTransformVec3f var5,
      ItemTransformVec3f var6,
      ItemTransformVec3f var7,
      ItemTransformVec3f var8
   ) {
      this.field_188036_k = ☃;
      this.field_188037_l = ☃;
      this.field_188038_m = ☃;
      this.field_188039_n = ☃;
      this.field_178353_d = ☃;
      this.field_178354_e = ☃;
      this.field_181699_o = ☃;
      this.field_181700_p = ☃;
   }

   public void func_181689_a(ItemCameraTransforms.TransformType var1) {
      func_188034_a(this.func_181688_b(☃), false);
   }

   public static void func_188034_a(ItemTransformVec3f var0, boolean var1) {
      if (☃ != ItemTransformVec3f.field_178366_a) {
         int ☃ = ☃ ? -1 : 1;
         GlStateManager.func_179109_b(
            (float)☃ * (field_181690_b + ☃.field_178365_c.func_195899_a()),
            field_181691_c + ☃.field_178365_c.func_195900_b(),
            field_181692_d + ☃.field_178365_c.func_195902_c()
         );
         float ☃x = field_181693_e + ☃.field_178364_b.func_195899_a();
         float ☃xx = field_181694_f + ☃.field_178364_b.func_195900_b();
         float ☃xxx = field_181695_g + ☃.field_178364_b.func_195902_c();
         if (☃) {
            ☃xx = -☃xx;
            ☃xxx = -☃xxx;
         }

         GlStateManager.func_199294_a(new Matrix4f(new Quaternion(☃x, ☃xx, ☃xxx, true)));
         GlStateManager.func_179152_a(
            field_181696_h + ☃.field_178363_d.func_195899_a(),
            field_181697_i + ☃.field_178363_d.func_195900_b(),
            field_181698_j + ☃.field_178363_d.func_195902_c()
         );
      }
   }

   public ItemTransformVec3f func_181688_b(ItemCameraTransforms.TransformType var1) {
      switch(☃) {
         case THIRD_PERSON_LEFT_HAND:
            return this.field_188036_k;
         case THIRD_PERSON_RIGHT_HAND:
            return this.field_188037_l;
         case FIRST_PERSON_LEFT_HAND:
            return this.field_188038_m;
         case FIRST_PERSON_RIGHT_HAND:
            return this.field_188039_n;
         case HEAD:
            return this.field_178353_d;
         case GUI:
            return this.field_178354_e;
         case GROUND:
            return this.field_181699_o;
         case FIXED:
            return this.field_181700_p;
         default:
            return ItemTransformVec3f.field_178366_a;
      }
   }

   public boolean func_181687_c(ItemCameraTransforms.TransformType var1) {
      return this.func_181688_b(☃) != ItemTransformVec3f.field_178366_a;
   }

   static class Deserializer implements JsonDeserializer<ItemCameraTransforms> {
      public ItemCameraTransforms deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         ItemTransformVec3f ☃x = this.func_181683_a(☃, ☃, "thirdperson_righthand");
         ItemTransformVec3f ☃xx = this.func_181683_a(☃, ☃, "thirdperson_lefthand");
         if (☃xx == ItemTransformVec3f.field_178366_a) {
            ☃xx = ☃x;
         }

         ItemTransformVec3f ☃ = this.func_181683_a(☃, ☃, "firstperson_righthand");
         ItemTransformVec3f ☃x = this.func_181683_a(☃, ☃, "firstperson_lefthand");
         if (☃x == ItemTransformVec3f.field_178366_a) {
            ☃x = ☃;
         }

         ItemTransformVec3f ☃ = this.func_181683_a(☃, ☃, "head");
         ItemTransformVec3f ☃x = this.func_181683_a(☃, ☃, "gui");
         ItemTransformVec3f ☃xx = this.func_181683_a(☃, ☃, "ground");
         ItemTransformVec3f ☃xxx = this.func_181683_a(☃, ☃, "fixed");
         return new ItemCameraTransforms(☃xx, ☃x, ☃x, ☃, ☃, ☃x, ☃xx, ☃xxx);
      }

      private ItemTransformVec3f func_181683_a(JsonDeserializationContext var1, JsonObject var2, String var3) {
         return ☃.has(☃) ? ☃.deserialize(☃.get(☃), ItemTransformVec3f.class) : ItemTransformVec3f.field_178366_a;
      }
   }

   public static enum TransformType {
      NONE,
      THIRD_PERSON_LEFT_HAND,
      THIRD_PERSON_RIGHT_HAND,
      FIRST_PERSON_LEFT_HAND,
      FIRST_PERSON_RIGHT_HAND,
      HEAD,
      GUI,
      GROUND,
      FIXED;
   }
}
