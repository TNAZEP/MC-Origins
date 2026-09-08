package net.minecraft.client.renderer;

import java.util.Arrays;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public final class Vector3f {
   private final float[] field_195907_a;

   public Vector3f(Vector3f var1) {
      this.field_195907_a = Arrays.copyOf(☃.field_195907_a, 3);
   }

   public Vector3f() {
      this.field_195907_a = new float[3];
   }

   public Vector3f(float var1, float var2, float var3) {
      this.field_195907_a = new float[]{☃, ☃, ☃};
   }

   public Vector3f(EnumFacing var1) {
      Vec3i ☃ = ☃.func_176730_m();
      this.field_195907_a = new float[]{(float)☃.func_177958_n(), (float)☃.func_177956_o(), (float)☃.func_177952_p()};
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         Vector3f ☃ = (Vector3f)☃;
         return Arrays.equals(this.field_195907_a, ☃.field_195907_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_195907_a);
   }

   public float func_195899_a() {
      return this.field_195907_a[0];
   }

   public float func_195900_b() {
      return this.field_195907_a[1];
   }

   public float func_195902_c() {
      return this.field_195907_a[2];
   }

   public void func_195898_a(float var1) {
      for(int ☃ = 0; ☃ < 3; ++☃) {
         this.field_195907_a[☃] *= ☃;
      }
   }

   public void func_195901_a(float var1, float var2) {
      this.field_195907_a[0] = MathHelper.func_76131_a(this.field_195907_a[0], ☃, ☃);
      this.field_195907_a[1] = MathHelper.func_76131_a(this.field_195907_a[1], ☃, ☃);
      this.field_195907_a[2] = MathHelper.func_76131_a(this.field_195907_a[2], ☃, ☃);
   }

   public void func_195905_a(float var1, float var2, float var3) {
      this.field_195907_a[0] = ☃;
      this.field_195907_a[1] = ☃;
      this.field_195907_a[2] = ☃;
   }

   public void func_195904_b(float var1, float var2, float var3) {
      this.field_195907_a[0] += ☃;
      this.field_195907_a[1] += ☃;
      this.field_195907_a[2] += ☃;
   }

   public void func_195897_a(Vector3f var1) {
      for(int ☃ = 0; ☃ < 3; ++☃) {
         this.field_195907_a[☃] -= ☃.field_195907_a[☃];
      }
   }

   public float func_195903_b(Vector3f var1) {
      float ☃ = 0.0F;

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         ☃ += this.field_195907_a[☃x] * ☃.field_195907_a[☃x];
      }

      return ☃;
   }

   public void func_195906_d() {
      float ☃ = 0.0F;

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         ☃ += this.field_195907_a[☃x] * this.field_195907_a[☃x];
      }

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         this.field_195907_a[☃x] /= ☃;
      }
   }

   public void func_195896_c(Vector3f var1) {
      float ☃ = this.field_195907_a[0];
      float ☃x = this.field_195907_a[1];
      float ☃xx = this.field_195907_a[2];
      float ☃xxx = ☃.func_195899_a();
      float ☃xxxx = ☃.func_195900_b();
      float ☃xxxxx = ☃.func_195902_c();
      this.field_195907_a[0] = ☃x * ☃xxxxx - ☃xx * ☃xxxx;
      this.field_195907_a[1] = ☃xx * ☃xxx - ☃ * ☃xxxxx;
      this.field_195907_a[2] = ☃ * ☃xxxx - ☃x * ☃xxx;
   }
}
