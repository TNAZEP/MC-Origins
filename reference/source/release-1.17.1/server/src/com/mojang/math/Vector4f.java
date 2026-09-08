package com.mojang.math;

import net.minecraft.util.Mth;

public class Vector4f {
   private float x;
   private float y;
   private float z;
   private float w;

   public Vector4f() {
   }

   public Vector4f(float var1, float var2, float var3, float var4) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.w = â˜ƒ;
   }

   public Vector4f(Vector3f var1) {
      this(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z(), 1.0F);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Vector4f â˜ƒ = (Vector4f)â˜ƒ;
         if (Float.compare(â˜ƒ.x, this.x) != 0) {
            return false;
         } else if (Float.compare(â˜ƒ.y, this.y) != 0) {
            return false;
         } else if (Float.compare(â˜ƒ.z, this.z) != 0) {
            return false;
         } else {
            return Float.compare(â˜ƒ.w, this.w) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = Float.floatToIntBits(this.x);
      â˜ƒ = 31 * â˜ƒ + Float.floatToIntBits(this.y);
      â˜ƒ = 31 * â˜ƒ + Float.floatToIntBits(this.z);
      return 31 * â˜ƒ + Float.floatToIntBits(this.w);
   }

   public float x() {
      return this.x;
   }

   public float y() {
      return this.y;
   }

   public float z() {
      return this.z;
   }

   public float w() {
      return this.w;
   }

   public void mul(float var1) {
      this.x *= â˜ƒ;
      this.y *= â˜ƒ;
      this.z *= â˜ƒ;
      this.w *= â˜ƒ;
   }

   public void mul(Vector3f var1) {
      this.x *= â˜ƒ.x();
      this.y *= â˜ƒ.y();
      this.z *= â˜ƒ.z();
   }

   public void set(float var1, float var2, float var3, float var4) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.w = â˜ƒ;
   }

   public void add(float var1, float var2, float var3, float var4) {
      this.x += â˜ƒ;
      this.y += â˜ƒ;
      this.z += â˜ƒ;
      this.w += â˜ƒ;
   }

   public float dot(Vector4f var1) {
      return this.x * â˜ƒ.x + this.y * â˜ƒ.y + this.z * â˜ƒ.z + this.w * â˜ƒ.w;
   }

   public boolean normalize() {
      float â˜ƒ = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
      if ((double)â˜ƒ < 1.0E-5) {
         return false;
      } else {
         float â˜ƒ = Mth.fastInvSqrt(â˜ƒ);
         this.x *= â˜ƒ;
         this.y *= â˜ƒ;
         this.z *= â˜ƒ;
         this.w *= â˜ƒ;
         return true;
      }
   }

   public void transform(Matrix4f var1) {
      float â˜ƒ = this.x;
      float â˜ƒx = this.y;
      float â˜ƒxx = this.z;
      float â˜ƒxxx = this.w;
      this.x = â˜ƒ.m00 * â˜ƒ + â˜ƒ.m01 * â˜ƒx + â˜ƒ.m02 * â˜ƒxx + â˜ƒ.m03 * â˜ƒxxx;
      this.y = â˜ƒ.m10 * â˜ƒ + â˜ƒ.m11 * â˜ƒx + â˜ƒ.m12 * â˜ƒxx + â˜ƒ.m13 * â˜ƒxxx;
      this.z = â˜ƒ.m20 * â˜ƒ + â˜ƒ.m21 * â˜ƒx + â˜ƒ.m22 * â˜ƒxx + â˜ƒ.m23 * â˜ƒxxx;
      this.w = â˜ƒ.m30 * â˜ƒ + â˜ƒ.m31 * â˜ƒx + â˜ƒ.m32 * â˜ƒxx + â˜ƒ.m33 * â˜ƒxxx;
   }

   public void transform(Quaternion var1) {
      Quaternion â˜ƒ = new Quaternion(â˜ƒ);
      â˜ƒ.mul(new Quaternion(this.x(), this.y(), this.z(), 0.0F));
      Quaternion â˜ƒx = new Quaternion(â˜ƒ);
      â˜ƒx.conj();
      â˜ƒ.mul(â˜ƒx);
      this.set(â˜ƒ.i(), â˜ƒ.j(), â˜ƒ.k(), this.w());
   }

   public void perspectiveDivide() {
      this.x /= this.w;
      this.y /= this.w;
      this.z /= this.w;
      this.w = 1.0F;
   }

   public void lerp(Vector4f var1, float var2) {
      float â˜ƒ = 1.0F - â˜ƒ;
      this.x = this.x * â˜ƒ + â˜ƒ.x * â˜ƒ;
      this.y = this.y * â˜ƒ + â˜ƒ.y * â˜ƒ;
      this.z = this.z * â˜ƒ + â˜ƒ.z * â˜ƒ;
      this.w = this.w * â˜ƒ + â˜ƒ.w * â˜ƒ;
   }

   public String toString() {
      return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
   }
}
