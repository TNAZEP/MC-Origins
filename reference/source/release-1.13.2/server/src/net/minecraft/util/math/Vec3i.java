package net.minecraft.util.math;

import com.google.common.base.MoreObjects;
import javax.annotation.concurrent.Immutable;

@Immutable
public class Vec3i implements Comparable<Vec3i> {
   public static final Vec3i field_177959_e = new Vec3i(0, 0, 0);
   private final int field_177962_a;
   private final int field_177960_b;
   private final int field_177961_c;

   public Vec3i(int var1, int var2, int var3) {
      this.field_177962_a = ☃;
      this.field_177960_b = ☃;
      this.field_177961_c = ☃;
   }

   public Vec3i(double var1, double var3, double var5) {
      this(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃));
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof Vec3i)) {
         return false;
      } else {
         Vec3i ☃ = (Vec3i)☃;
         if (this.func_177958_n() != ☃.func_177958_n()) {
            return false;
         } else if (this.func_177956_o() != ☃.func_177956_o()) {
            return false;
         } else {
            return this.func_177952_p() == ☃.func_177952_p();
         }
      }
   }

   public int hashCode() {
      return (this.func_177956_o() + this.func_177952_p() * 31) * 31 + this.func_177958_n();
   }

   public int compareTo(Vec3i var1) {
      if (this.func_177956_o() == ☃.func_177956_o()) {
         return this.func_177952_p() == ☃.func_177952_p() ? this.func_177958_n() - ☃.func_177958_n() : this.func_177952_p() - ☃.func_177952_p();
      } else {
         return this.func_177956_o() - ☃.func_177956_o();
      }
   }

   public int func_177958_n() {
      return this.field_177962_a;
   }

   public int func_177956_o() {
      return this.field_177960_b;
   }

   public int func_177952_p() {
      return this.field_177961_c;
   }

   public Vec3i func_177955_d(Vec3i var1) {
      return new Vec3i(
         this.func_177956_o() * ☃.func_177952_p() - this.func_177952_p() * ☃.func_177956_o(),
         this.func_177952_p() * ☃.func_177958_n() - this.func_177958_n() * ☃.func_177952_p(),
         this.func_177958_n() * ☃.func_177956_o() - this.func_177956_o() * ☃.func_177958_n()
      );
   }

   public double func_185332_f(int var1, int var2, int var3) {
      double ☃ = (double)(this.func_177958_n() - ☃);
      double ☃x = (double)(this.func_177956_o() - ☃);
      double ☃xx = (double)(this.func_177952_p() - ☃);
      return Math.sqrt(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
   }

   public double func_196233_m(Vec3i var1) {
      return this.func_185332_f(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public double func_177954_c(double var1, double var3, double var5) {
      double ☃ = (double)this.func_177958_n() - ☃;
      double ☃x = (double)this.func_177956_o() - ☃;
      double ☃xx = (double)this.func_177952_p() - ☃;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public double func_177957_d(double var1, double var3, double var5) {
      double ☃ = (double)this.func_177958_n() + 0.5 - ☃;
      double ☃x = (double)this.func_177956_o() + 0.5 - ☃;
      double ☃xx = (double)this.func_177952_p() + 0.5 - ☃;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public double func_177951_i(Vec3i var1) {
      return this.func_177954_c((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p());
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.func_177958_n()).add("y", this.func_177956_o()).add("z", this.func_177952_p()).toString();
   }
}
