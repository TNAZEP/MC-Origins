package net.minecraft.util.math;

import java.util.EnumSet;
import net.minecraft.util.EnumFacing;

public class Vec3d {
   public static final Vec3d field_186680_a = new Vec3d(0.0, 0.0, 0.0);
   public final double field_72450_a;
   public final double field_72448_b;
   public final double field_72449_c;

   public Vec3d(double var1, double var3, double var5) {
      this.field_72450_a = ☃;
      this.field_72448_b = ☃;
      this.field_72449_c = ☃;
   }

   public Vec3d(Vec3i var1) {
      this((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p());
   }

   public Vec3d func_72444_a(Vec3d var1) {
      return new Vec3d(☃.field_72450_a - this.field_72450_a, ☃.field_72448_b - this.field_72448_b, ☃.field_72449_c - this.field_72449_c);
   }

   public Vec3d func_72432_b() {
      double ☃ = (double)MathHelper.func_76133_a(
         this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c
      );
      return ☃ < 1.0E-4 ? field_186680_a : new Vec3d(this.field_72450_a / ☃, this.field_72448_b / ☃, this.field_72449_c / ☃);
   }

   public double func_72430_b(Vec3d var1) {
      return this.field_72450_a * ☃.field_72450_a + this.field_72448_b * ☃.field_72448_b + this.field_72449_c * ☃.field_72449_c;
   }

   public Vec3d func_72431_c(Vec3d var1) {
      return new Vec3d(
         this.field_72448_b * ☃.field_72449_c - this.field_72449_c * ☃.field_72448_b,
         this.field_72449_c * ☃.field_72450_a - this.field_72450_a * ☃.field_72449_c,
         this.field_72450_a * ☃.field_72448_b - this.field_72448_b * ☃.field_72450_a
      );
   }

   public Vec3d func_178788_d(Vec3d var1) {
      return this.func_178786_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public Vec3d func_178786_a(double var1, double var3, double var5) {
      return this.func_72441_c(-☃, -☃, -☃);
   }

   public Vec3d func_178787_e(Vec3d var1) {
      return this.func_72441_c(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public Vec3d func_72441_c(double var1, double var3, double var5) {
      return new Vec3d(this.field_72450_a + ☃, this.field_72448_b + ☃, this.field_72449_c + ☃);
   }

   public double func_72438_d(Vec3d var1) {
      double ☃ = ☃.field_72450_a - this.field_72450_a;
      double ☃x = ☃.field_72448_b - this.field_72448_b;
      double ☃xx = ☃.field_72449_c - this.field_72449_c;
      return (double)MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
   }

   public double func_72436_e(Vec3d var1) {
      double ☃ = ☃.field_72450_a - this.field_72450_a;
      double ☃x = ☃.field_72448_b - this.field_72448_b;
      double ☃xx = ☃.field_72449_c - this.field_72449_c;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public double func_186679_c(double var1, double var3, double var5) {
      double ☃ = ☃ - this.field_72450_a;
      double ☃x = ☃ - this.field_72448_b;
      double ☃xx = ☃ - this.field_72449_c;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public Vec3d func_186678_a(double var1) {
      return new Vec3d(this.field_72450_a * ☃, this.field_72448_b * ☃, this.field_72449_c * ☃);
   }

   public double func_72433_c() {
      return (double)MathHelper.func_76133_a(
         this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c
      );
   }

   public double func_189985_c() {
      return this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof Vec3d)) {
         return false;
      } else {
         Vec3d ☃ = (Vec3d)☃;
         if (Double.compare(☃.field_72450_a, this.field_72450_a) != 0) {
            return false;
         } else if (Double.compare(☃.field_72448_b, this.field_72448_b) != 0) {
            return false;
         } else {
            return Double.compare(☃.field_72449_c, this.field_72449_c) == 0;
         }
      }
   }

   public int hashCode() {
      long ☃ = Double.doubleToLongBits(this.field_72450_a);
      int ☃x = (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72448_b);
      ☃x = 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72449_c);
      return 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
   }

   public String toString() {
      return "(" + this.field_72450_a + ", " + this.field_72448_b + ", " + this.field_72449_c + ")";
   }

   public Vec3d func_178789_a(float var1) {
      float ☃ = MathHelper.func_76134_b(☃);
      float ☃x = MathHelper.func_76126_a(☃);
      double ☃xx = this.field_72450_a;
      double ☃xxx = this.field_72448_b * (double)☃ + this.field_72449_c * (double)☃x;
      double ☃xxxx = this.field_72449_c * (double)☃ - this.field_72448_b * (double)☃x;
      return new Vec3d(☃xx, ☃xxx, ☃xxxx);
   }

   public Vec3d func_178785_b(float var1) {
      float ☃ = MathHelper.func_76134_b(☃);
      float ☃x = MathHelper.func_76126_a(☃);
      double ☃xx = this.field_72450_a * (double)☃ + this.field_72449_c * (double)☃x;
      double ☃xxx = this.field_72448_b;
      double ☃xxxx = this.field_72449_c * (double)☃ - this.field_72450_a * (double)☃x;
      return new Vec3d(☃xx, ☃xxx, ☃xxxx);
   }

   public static Vec3d func_189984_a(Vec2f var0) {
      return func_189986_a(☃.field_189982_i, ☃.field_189983_j);
   }

   public static Vec3d func_189986_a(float var0, float var1) {
      float ☃ = MathHelper.func_76134_b(-☃ * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃x = MathHelper.func_76126_a(-☃ * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃xx = -MathHelper.func_76134_b(-☃ * (float) (Math.PI / 180.0));
      float ☃xxx = MathHelper.func_76126_a(-☃ * (float) (Math.PI / 180.0));
      return new Vec3d((double)(☃x * ☃xx), (double)☃xxx, (double)(☃ * ☃xx));
   }

   public Vec3d func_197746_a(EnumSet<EnumFacing.Axis> var1) {
      double ☃ = ☃.contains(EnumFacing.Axis.X) ? (double)MathHelper.func_76128_c(this.field_72450_a) : this.field_72450_a;
      double ☃x = ☃.contains(EnumFacing.Axis.Y) ? (double)MathHelper.func_76128_c(this.field_72448_b) : this.field_72448_b;
      double ☃xx = ☃.contains(EnumFacing.Axis.Z) ? (double)MathHelper.func_76128_c(this.field_72449_c) : this.field_72449_c;
      return new Vec3d(☃, ☃x, ☃xx);
   }
}
