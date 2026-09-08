package net.minecraft.client.renderer;

import java.util.Arrays;

public class Vector4f {
   private final float[] field_195916_a;

   public Vector4f(Vector4f var1) {
      this.field_195916_a = Arrays.copyOf(☃.field_195916_a, 4);
   }

   public Vector4f() {
      this.field_195916_a = new float[4];
   }

   public Vector4f(float var1, float var2, float var3, float var4) {
      this.field_195916_a = new float[]{☃, ☃, ☃, ☃};
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         Vector4f ☃ = (Vector4f)☃;
         return Arrays.equals(this.field_195916_a, ☃.field_195916_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_195916_a);
   }

   public float func_195910_a() {
      return this.field_195916_a[0];
   }

   public float func_195913_b() {
      return this.field_195916_a[1];
   }

   public float func_195914_c() {
      return this.field_195916_a[2];
   }

   public float func_195915_d() {
      return this.field_195916_a[3];
   }

   public void func_195909_a(Vector3f var1) {
      this.field_195916_a[0] *= ☃.func_195899_a();
      this.field_195916_a[1] *= ☃.func_195900_b();
      this.field_195916_a[2] *= ☃.func_195902_c();
   }

   public void func_195911_a(float var1, float var2, float var3, float var4) {
      this.field_195916_a[0] = ☃;
      this.field_195916_a[1] = ☃;
      this.field_195916_a[2] = ☃;
      this.field_195916_a[3] = ☃;
   }

   public void func_195908_a(Matrix4f var1) {
      float[] ☃ = Arrays.copyOf(this.field_195916_a, 4);

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         this.field_195916_a[☃x] = 0.0F;

         for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
            this.field_195916_a[☃x] += ☃.func_195885_a(☃x, ☃xx) * ☃[☃xx];
         }
      }
   }

   public void func_195912_a(Quaternion var1) {
      Quaternion ☃ = new Quaternion(☃);
      ☃.func_195890_a(new Quaternion(this.func_195910_a(), this.func_195913_b(), this.func_195914_c(), 0.0F));
      Quaternion ☃x = new Quaternion(☃);
      ☃x.func_195892_e();
      ☃.func_195890_a(☃x);
      this.func_195911_a(☃.func_195889_a(), ☃.func_195891_b(), ☃.func_195893_c(), this.func_195915_d());
   }
}
