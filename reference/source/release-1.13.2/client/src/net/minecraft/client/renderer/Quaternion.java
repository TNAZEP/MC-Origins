package net.minecraft.client.renderer;

import java.util.Arrays;
import net.minecraft.util.math.MathHelper;

public final class Quaternion {
   private final float[] field_195895_a;

   public Quaternion() {
      this.field_195895_a = new float[4];
      this.field_195895_a[4] = 1.0F;
   }

   public Quaternion(float var1, float var2, float var3, float var4) {
      this.field_195895_a = new float[4];
      this.field_195895_a[0] = ☃;
      this.field_195895_a[1] = ☃;
      this.field_195895_a[2] = ☃;
      this.field_195895_a[3] = ☃;
   }

   public Quaternion(Vector3f var1, float var2, boolean var3) {
      if (☃) {
         ☃ *= (float) (Math.PI / 180.0);
      }

      float ☃ = MathHelper.func_76126_a(☃ / 2.0F);
      this.field_195895_a = new float[4];
      this.field_195895_a[0] = ☃.func_195899_a() * ☃;
      this.field_195895_a[1] = ☃.func_195900_b() * ☃;
      this.field_195895_a[2] = ☃.func_195902_c() * ☃;
      this.field_195895_a[3] = MathHelper.func_76134_b(☃ / 2.0F);
   }

   public Quaternion(float var1, float var2, float var3, boolean var4) {
      if (☃) {
         ☃ *= (float) (Math.PI / 180.0);
         ☃ *= (float) (Math.PI / 180.0);
         ☃ *= (float) (Math.PI / 180.0);
      }

      float ☃ = MathHelper.func_76126_a(0.5F * ☃);
      float ☃x = MathHelper.func_76134_b(0.5F * ☃);
      float ☃xx = MathHelper.func_76126_a(0.5F * ☃);
      float ☃xxx = MathHelper.func_76134_b(0.5F * ☃);
      float ☃xxxx = MathHelper.func_76126_a(0.5F * ☃);
      float ☃xxxxx = MathHelper.func_76134_b(0.5F * ☃);
      this.field_195895_a = new float[4];
      this.field_195895_a[0] = ☃ * ☃xxx * ☃xxxxx + ☃x * ☃xx * ☃xxxx;
      this.field_195895_a[1] = ☃x * ☃xx * ☃xxxxx - ☃ * ☃xxx * ☃xxxx;
      this.field_195895_a[2] = ☃ * ☃xx * ☃xxxxx + ☃x * ☃xxx * ☃xxxx;
      this.field_195895_a[3] = ☃x * ☃xxx * ☃xxxxx - ☃ * ☃xx * ☃xxxx;
   }

   public Quaternion(Quaternion var1) {
      this.field_195895_a = Arrays.copyOf(☃.field_195895_a, 4);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         Quaternion ☃ = (Quaternion)☃;
         return Arrays.equals(this.field_195895_a, ☃.field_195895_a);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_195895_a);
   }

   public String toString() {
      StringBuilder ☃ = new StringBuilder();
      ☃.append("Quaternion[").append(this.func_195894_d()).append(" + ");
      ☃.append(this.func_195889_a()).append("i + ");
      ☃.append(this.func_195891_b()).append("j + ");
      ☃.append(this.func_195893_c()).append("k]");
      return ☃.toString();
   }

   public float func_195889_a() {
      return this.field_195895_a[0];
   }

   public float func_195891_b() {
      return this.field_195895_a[1];
   }

   public float func_195893_c() {
      return this.field_195895_a[2];
   }

   public float func_195894_d() {
      return this.field_195895_a[3];
   }

   public void func_195890_a(Quaternion var1) {
      float ☃ = this.func_195889_a();
      float ☃x = this.func_195891_b();
      float ☃xx = this.func_195893_c();
      float ☃xxx = this.func_195894_d();
      float ☃xxxx = ☃.func_195889_a();
      float ☃xxxxx = ☃.func_195891_b();
      float ☃xxxxxx = ☃.func_195893_c();
      float ☃xxxxxxx = ☃.func_195894_d();
      this.field_195895_a[0] = ☃xxx * ☃xxxx + ☃ * ☃xxxxxxx + ☃x * ☃xxxxxx - ☃xx * ☃xxxxx;
      this.field_195895_a[1] = ☃xxx * ☃xxxxx - ☃ * ☃xxxxxx + ☃x * ☃xxxxxxx + ☃xx * ☃xxxx;
      this.field_195895_a[2] = ☃xxx * ☃xxxxxx + ☃ * ☃xxxxx - ☃x * ☃xxxx + ☃xx * ☃xxxxxxx;
      this.field_195895_a[3] = ☃xxx * ☃xxxxxxx - ☃ * ☃xxxx - ☃x * ☃xxxxx - ☃xx * ☃xxxxxx;
   }

   public void func_195892_e() {
      this.field_195895_a[0] = -this.field_195895_a[0];
      this.field_195895_a[1] = -this.field_195895_a[1];
      this.field_195895_a[2] = -this.field_195895_a[2];
   }
}
