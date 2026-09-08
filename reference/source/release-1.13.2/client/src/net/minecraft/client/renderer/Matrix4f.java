package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.util.Arrays;

public final class Matrix4f {
   private final float[] field_195888_a;

   public Matrix4f() {
      this.field_195888_a = new float[16];
   }

   public Matrix4f(Quaternion var1) {
      this();
      float ☃ = ☃.func_195889_a();
      float ☃x = ☃.func_195891_b();
      float ☃xx = ☃.func_195893_c();
      float ☃xxx = ☃.func_195894_d();
      float ☃xxxx = 2.0F * ☃ * ☃;
      float ☃xxxxx = 2.0F * ☃x * ☃x;
      float ☃xxxxxx = 2.0F * ☃xx * ☃xx;
      this.field_195888_a[0] = 1.0F - ☃xxxxx - ☃xxxxxx;
      this.field_195888_a[5] = 1.0F - ☃xxxxxx - ☃xxxx;
      this.field_195888_a[10] = 1.0F - ☃xxxx - ☃xxxxx;
      this.field_195888_a[15] = 1.0F;
      float ☃xxxxxxx = ☃ * ☃x;
      float ☃xxxxxxxx = ☃x * ☃xx;
      float ☃xxxxxxxxx = ☃xx * ☃;
      float ☃xxxxxxxxxx = ☃ * ☃xxx;
      float ☃xxxxxxxxxxx = ☃x * ☃xxx;
      float ☃xxxxxxxxxxxx = ☃xx * ☃xxx;
      this.field_195888_a[1] = 2.0F * (☃xxxxxxx + ☃xxxxxxxxxxxx);
      this.field_195888_a[4] = 2.0F * (☃xxxxxxx - ☃xxxxxxxxxxxx);
      this.field_195888_a[2] = 2.0F * (☃xxxxxxxxx - ☃xxxxxxxxxxx);
      this.field_195888_a[8] = 2.0F * (☃xxxxxxxxx + ☃xxxxxxxxxxx);
      this.field_195888_a[6] = 2.0F * (☃xxxxxxxx + ☃xxxxxxxxxx);
      this.field_195888_a[9] = 2.0F * (☃xxxxxxxx - ☃xxxxxxxxxx);
   }

   public Matrix4f(Matrix4f var1) {
      this.field_195888_a = Arrays.copyOf(☃.field_195888_a, 16);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         Matrix4f ☃ = (Matrix4f)☃;
         return Arrays.equals(this.field_195888_a, ☃.field_195888_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_195888_a);
   }

   public void func_195874_a(FloatBuffer var1) {
      this.func_195883_a(☃, false);
   }

   public void func_195883_a(FloatBuffer var1, boolean var2) {
      if (☃) {
         for(int ☃ = 0; ☃ < 4; ++☃) {
            for(int ☃x = 0; ☃x < 4; ++☃x) {
               this.field_195888_a[☃ * 4 + ☃x] = ☃.get(☃x * 4 + ☃);
            }
         }
      } else {
         ☃.get(this.field_195888_a);
      }
   }

   public String toString() {
      StringBuilder ☃ = new StringBuilder();
      ☃.append("Matrix4f:\n");

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
            ☃.append(this.field_195888_a[☃x + ☃xx * 4]);
            if (☃xx != 3) {
               ☃.append(" ");
            }
         }

         ☃.append("\n");
      }

      return ☃.toString();
   }

   public void func_195879_b(FloatBuffer var1) {
      this.func_195873_b(☃, false);
   }

   public void func_195873_b(FloatBuffer var1, boolean var2) {
      if (☃) {
         for(int ☃ = 0; ☃ < 4; ++☃) {
            for(int ☃x = 0; ☃x < 4; ++☃x) {
               ☃.put(☃x * 4 + ☃, this.field_195888_a[☃ * 4 + ☃x]);
            }
         }
      } else {
         ☃.put(this.field_195888_a);
      }
   }

   public void func_195884_a() {
      this.field_195888_a[0] = 1.0F;
      this.field_195888_a[1] = 0.0F;
      this.field_195888_a[2] = 0.0F;
      this.field_195888_a[3] = 0.0F;
      this.field_195888_a[4] = 0.0F;
      this.field_195888_a[5] = 1.0F;
      this.field_195888_a[6] = 0.0F;
      this.field_195888_a[7] = 0.0F;
      this.field_195888_a[8] = 0.0F;
      this.field_195888_a[9] = 0.0F;
      this.field_195888_a[10] = 1.0F;
      this.field_195888_a[11] = 0.0F;
      this.field_195888_a[12] = 0.0F;
      this.field_195888_a[13] = 0.0F;
      this.field_195888_a[14] = 0.0F;
      this.field_195888_a[15] = 1.0F;
   }

   public float func_195885_a(int var1, int var2) {
      return this.field_195888_a[☃ + 4 * ☃];
   }

   public void func_195878_a(int var1, int var2, float var3) {
      this.field_195888_a[☃ + 4 * ☃] = ☃;
   }

   public void func_195882_a(Matrix4f var1) {
      float[] ☃ = Arrays.copyOf(this.field_195888_a, 16);

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
            this.field_195888_a[☃x + ☃xx * 4] = 0.0F;

            for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
               this.field_195888_a[☃x + ☃xx * 4] += ☃[☃x + ☃xxx * 4] * ☃.field_195888_a[☃xxx + ☃xx * 4];
            }
         }
      }
   }

   public void func_195875_a(float var1) {
      for(int ☃ = 0; ☃ < 16; ++☃) {
         this.field_195888_a[☃] *= ☃;
      }
   }

   public void func_195880_b(Matrix4f var1) {
      for(int ☃ = 0; ☃ < 16; ++☃) {
         this.field_195888_a[☃] += ☃.field_195888_a[☃];
      }
   }

   public void func_195886_c(Matrix4f var1) {
      for(int ☃ = 0; ☃ < 16; ++☃) {
         this.field_195888_a[☃] -= ☃.field_195888_a[☃];
      }
   }

   public float func_195881_b() {
      float ☃ = 0.0F;

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         ☃ += this.field_195888_a[☃x + 4 * ☃x];
      }

      return ☃;
   }

   public void func_195887_c() {
      Matrix4f ☃ = new Matrix4f();
      Matrix4f ☃x = new Matrix4f(this);
      Matrix4f ☃xx = new Matrix4f(this);
      ☃x.func_195882_a(this);
      ☃xx.func_195882_a(☃x);
      float ☃xxx = this.func_195881_b();
      float ☃xxxx = ☃x.func_195881_b();
      float ☃xxxxx = ☃xx.func_195881_b();
      this.func_195875_a((☃xxxx - ☃xxx * ☃xxx) / 2.0F);
      ☃.func_195884_a();
      ☃.func_195875_a((☃xxx * ☃xxx * ☃xxx - 3.0F * ☃xxx * ☃xxxx + 2.0F * ☃xxxxx) / 6.0F);
      this.func_195880_b(☃);
      ☃x.func_195875_a(☃xxx);
      this.func_195880_b(☃x);
      this.func_195886_c(☃xx);
   }

   public static Matrix4f func_195876_a(double var0, float var2, float var3, float var4) {
      float ☃ = (float)(1.0 / Math.tan(☃ * (float) (Math.PI / 180.0) / 2.0));
      Matrix4f ☃x = new Matrix4f();
      ☃x.func_195878_a(0, 0, ☃ / ☃);
      ☃x.func_195878_a(1, 1, ☃);
      ☃x.func_195878_a(2, 2, (☃ + ☃) / (☃ - ☃));
      ☃x.func_195878_a(3, 2, -1.0F);
      ☃x.func_195878_a(2, 3, 2.0F * ☃ * ☃ / (☃ - ☃));
      return ☃x;
   }

   public static Matrix4f func_195877_a(float var0, float var1, float var2, float var3) {
      Matrix4f ☃ = new Matrix4f();
      ☃.func_195878_a(0, 0, 2.0F / ☃);
      ☃.func_195878_a(1, 1, 2.0F / ☃);
      float ☃x = ☃ - ☃;
      ☃.func_195878_a(2, 2, -2.0F / ☃x);
      ☃.func_195878_a(3, 3, 1.0F);
      ☃.func_195878_a(0, 3, -1.0F);
      ☃.func_195878_a(1, 3, -1.0F);
      ☃.func_195878_a(2, 3, -(☃ + ☃) / ☃x);
      return ☃;
   }
}
