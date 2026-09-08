package com.mojang.math;

import net.minecraft.util.Mth;

public final class Quaternion {
   public static final Quaternion ONE = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private float i;
   private float j;
   private float k;
   private float r;

   public Quaternion(float var1, float var2, float var3, float var4) {
      this.i = â˜ƒ;
      this.j = â˜ƒ;
      this.k = â˜ƒ;
      this.r = â˜ƒ;
   }

   public Quaternion(Vector3f var1, float var2, boolean var3) {
      if (â˜ƒ) {
         â˜ƒ *= (float) (Math.PI / 180.0);
      }

      float â˜ƒ = sin(â˜ƒ / 2.0F);
      this.i = â˜ƒ.x() * â˜ƒ;
      this.j = â˜ƒ.y() * â˜ƒ;
      this.k = â˜ƒ.z() * â˜ƒ;
      this.r = cos(â˜ƒ / 2.0F);
   }

   public Quaternion(float var1, float var2, float var3, boolean var4) {
      if (â˜ƒ) {
         â˜ƒ *= (float) (Math.PI / 180.0);
         â˜ƒ *= (float) (Math.PI / 180.0);
         â˜ƒ *= (float) (Math.PI / 180.0);
      }

      float â˜ƒ = sin(0.5F * â˜ƒ);
      float â˜ƒx = cos(0.5F * â˜ƒ);
      float â˜ƒxx = sin(0.5F * â˜ƒ);
      float â˜ƒxxx = cos(0.5F * â˜ƒ);
      float â˜ƒxxxx = sin(0.5F * â˜ƒ);
      float â˜ƒxxxxx = cos(0.5F * â˜ƒ);
      this.i = â˜ƒ * â˜ƒxxx * â˜ƒxxxxx + â˜ƒx * â˜ƒxx * â˜ƒxxxx;
      this.j = â˜ƒx * â˜ƒxx * â˜ƒxxxxx - â˜ƒ * â˜ƒxxx * â˜ƒxxxx;
      this.k = â˜ƒ * â˜ƒxx * â˜ƒxxxxx + â˜ƒx * â˜ƒxxx * â˜ƒxxxx;
      this.r = â˜ƒx * â˜ƒxxx * â˜ƒxxxxx - â˜ƒ * â˜ƒxx * â˜ƒxxxx;
   }

   public Quaternion(Quaternion var1) {
      this.i = â˜ƒ.i;
      this.j = â˜ƒ.j;
      this.k = â˜ƒ.k;
      this.r = â˜ƒ.r;
   }

   public static Quaternion fromYXZ(float var0, float var1, float var2) {
      Quaternion â˜ƒ = ONE.copy();
      â˜ƒ.mul(new Quaternion(0.0F, (float)Math.sin((double)(â˜ƒ / 2.0F)), 0.0F, (float)Math.cos((double)(â˜ƒ / 2.0F))));
      â˜ƒ.mul(new Quaternion((float)Math.sin((double)(â˜ƒ / 2.0F)), 0.0F, 0.0F, (float)Math.cos((double)(â˜ƒ / 2.0F))));
      â˜ƒ.mul(new Quaternion(0.0F, 0.0F, (float)Math.sin((double)(â˜ƒ / 2.0F)), (float)Math.cos((double)(â˜ƒ / 2.0F))));
      return â˜ƒ;
   }

   public static Quaternion fromXYZDegrees(Vector3f var0) {
      return fromXYZ((float)Math.toRadians((double)â˜ƒ.x()), (float)Math.toRadians((double)â˜ƒ.y()), (float)Math.toRadians((double)â˜ƒ.z()));
   }

   public static Quaternion fromXYZ(Vector3f var0) {
      return fromXYZ(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }

   public static Quaternion fromXYZ(float var0, float var1, float var2) {
      Quaternion â˜ƒ = ONE.copy();
      â˜ƒ.mul(new Quaternion((float)Math.sin((double)(â˜ƒ / 2.0F)), 0.0F, 0.0F, (float)Math.cos((double)(â˜ƒ / 2.0F))));
      â˜ƒ.mul(new Quaternion(0.0F, (float)Math.sin((double)(â˜ƒ / 2.0F)), 0.0F, (float)Math.cos((double)(â˜ƒ / 2.0F))));
      â˜ƒ.mul(new Quaternion(0.0F, 0.0F, (float)Math.sin((double)(â˜ƒ / 2.0F)), (float)Math.cos((double)(â˜ƒ / 2.0F))));
      return â˜ƒ;
   }

   public Vector3f toXYZ() {
      float â˜ƒ = this.r() * this.r();
      float â˜ƒx = this.i() * this.i();
      float â˜ƒxx = this.j() * this.j();
      float â˜ƒxxx = this.k() * this.k();
      float â˜ƒxxxx = â˜ƒ + â˜ƒx + â˜ƒxx + â˜ƒxxx;
      float â˜ƒxxxxx = 2.0F * this.r() * this.i() - 2.0F * this.j() * this.k();
      float â˜ƒxxxxxx = (float)Math.asin((double)(â˜ƒxxxxx / â˜ƒxxxx));
      return Math.abs(â˜ƒxxxxx) > 0.999F * â˜ƒxxxx
         ? new Vector3f(2.0F * (float)Math.atan2((double)this.i(), (double)this.r()), â˜ƒxxxxxx, 0.0F)
         : new Vector3f(
            (float)Math.atan2((double)(2.0F * this.j() * this.k() + 2.0F * this.i() * this.r()), (double)(â˜ƒ - â˜ƒx - â˜ƒxx + â˜ƒxxx)),
            â˜ƒxxxxxx,
            (float)Math.atan2((double)(2.0F * this.i() * this.j() + 2.0F * this.r() * this.k()), (double)(â˜ƒ + â˜ƒx - â˜ƒxx - â˜ƒxxx))
         );
   }

   public Vector3f toXYZDegrees() {
      Vector3f â˜ƒ = this.toXYZ();
      return new Vector3f((float)Math.toDegrees((double)â˜ƒ.x()), (float)Math.toDegrees((double)â˜ƒ.y()), (float)Math.toDegrees((double)â˜ƒ.z()));
   }

   public Vector3f toYXZ() {
      float â˜ƒ = this.r() * this.r();
      float â˜ƒx = this.i() * this.i();
      float â˜ƒxx = this.j() * this.j();
      float â˜ƒxxx = this.k() * this.k();
      float â˜ƒxxxx = â˜ƒ + â˜ƒx + â˜ƒxx + â˜ƒxxx;
      float â˜ƒxxxxx = 2.0F * this.r() * this.i() - 2.0F * this.j() * this.k();
      float â˜ƒxxxxxx = (float)Math.asin((double)(â˜ƒxxxxx / â˜ƒxxxx));
      return Math.abs(â˜ƒxxxxx) > 0.999F * â˜ƒxxxx
         ? new Vector3f(â˜ƒxxxxxx, 2.0F * (float)Math.atan2((double)this.j(), (double)this.r()), 0.0F)
         : new Vector3f(
            â˜ƒxxxxxx,
            (float)Math.atan2((double)(2.0F * this.i() * this.k() + 2.0F * this.j() * this.r()), (double)(â˜ƒ - â˜ƒx - â˜ƒxx + â˜ƒxxx)),
            (float)Math.atan2((double)(2.0F * this.i() * this.j() + 2.0F * this.r() * this.k()), (double)(â˜ƒ - â˜ƒx + â˜ƒxx - â˜ƒxxx))
         );
   }

   public Vector3f toYXZDegrees() {
      Vector3f â˜ƒ = this.toYXZ();
      return new Vector3f((float)Math.toDegrees((double)â˜ƒ.x()), (float)Math.toDegrees((double)â˜ƒ.y()), (float)Math.toDegrees((double)â˜ƒ.z()));
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Quaternion â˜ƒ = (Quaternion)â˜ƒ;
         if (Float.compare(â˜ƒ.i, this.i) != 0) {
            return false;
         } else if (Float.compare(â˜ƒ.j, this.j) != 0) {
            return false;
         } else if (Float.compare(â˜ƒ.k, this.k) != 0) {
            return false;
         } else {
            return Float.compare(â˜ƒ.r, this.r) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = Float.floatToIntBits(this.i);
      â˜ƒ = 31 * â˜ƒ + Float.floatToIntBits(this.j);
      â˜ƒ = 31 * â˜ƒ + Float.floatToIntBits(this.k);
      return 31 * â˜ƒ + Float.floatToIntBits(this.r);
   }

   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append("Quaternion[").append(this.r()).append(" + ");
      â˜ƒ.append(this.i()).append("i + ");
      â˜ƒ.append(this.j()).append("j + ");
      â˜ƒ.append(this.k()).append("k]");
      return â˜ƒ.toString();
   }

   public float i() {
      return this.i;
   }

   public float j() {
      return this.j;
   }

   public float k() {
      return this.k;
   }

   public float r() {
      return this.r;
   }

   public void mul(Quaternion var1) {
      float â˜ƒ = this.i();
      float â˜ƒx = this.j();
      float â˜ƒxx = this.k();
      float â˜ƒxxx = this.r();
      float â˜ƒxxxx = â˜ƒ.i();
      float â˜ƒxxxxx = â˜ƒ.j();
      float â˜ƒxxxxxx = â˜ƒ.k();
      float â˜ƒxxxxxxx = â˜ƒ.r();
      this.i = â˜ƒxxx * â˜ƒxxxx + â˜ƒ * â˜ƒxxxxxxx + â˜ƒx * â˜ƒxxxxxx - â˜ƒxx * â˜ƒxxxxx;
      this.j = â˜ƒxxx * â˜ƒxxxxx - â˜ƒ * â˜ƒxxxxxx + â˜ƒx * â˜ƒxxxxxxx + â˜ƒxx * â˜ƒxxxx;
      this.k = â˜ƒxxx * â˜ƒxxxxxx + â˜ƒ * â˜ƒxxxxx - â˜ƒx * â˜ƒxxxx + â˜ƒxx * â˜ƒxxxxxxx;
      this.r = â˜ƒxxx * â˜ƒxxxxxxx - â˜ƒ * â˜ƒxxxx - â˜ƒx * â˜ƒxxxxx - â˜ƒxx * â˜ƒxxxxxx;
   }

   public void mul(float var1) {
      this.i *= â˜ƒ;
      this.j *= â˜ƒ;
      this.k *= â˜ƒ;
      this.r *= â˜ƒ;
   }

   public void conj() {
      this.i = -this.i;
      this.j = -this.j;
      this.k = -this.k;
   }

   public void set(float var1, float var2, float var3, float var4) {
      this.i = â˜ƒ;
      this.j = â˜ƒ;
      this.k = â˜ƒ;
      this.r = â˜ƒ;
   }

   private static float cos(float var0) {
      return (float)Math.cos((double)â˜ƒ);
   }

   private static float sin(float var0) {
      return (float)Math.sin((double)â˜ƒ);
   }

   public void normalize() {
      float â˜ƒ = this.i() * this.i() + this.j() * this.j() + this.k() * this.k() + this.r() * this.r();
      if (â˜ƒ > 1.0E-6F) {
         float â˜ƒx = Mth.fastInvSqrt(â˜ƒ);
         this.i *= â˜ƒx;
         this.j *= â˜ƒx;
         this.k *= â˜ƒx;
         this.r *= â˜ƒx;
      } else {
         this.i = 0.0F;
         this.j = 0.0F;
         this.k = 0.0F;
         this.r = 0.0F;
      }
   }

   public void slerp(Quaternion var1, float var2) {
      throw new UnsupportedOperationException();
   }

   public Quaternion copy() {
      return new Quaternion(this);
   }
}
