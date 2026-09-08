package com.mojang.math;

import java.nio.FloatBuffer;

public final class Matrix4f {
   private static final int ORDER = 4;
   protected float m00;
   protected float m01;
   protected float m02;
   protected float m03;
   protected float m10;
   protected float m11;
   protected float m12;
   protected float m13;
   protected float m20;
   protected float m21;
   protected float m22;
   protected float m23;
   protected float m30;
   protected float m31;
   protected float m32;
   protected float m33;

   public Matrix4f() {
   }

   public Matrix4f(Matrix4f var1) {
      this.m00 = â˜ƒ.m00;
      this.m01 = â˜ƒ.m01;
      this.m02 = â˜ƒ.m02;
      this.m03 = â˜ƒ.m03;
      this.m10 = â˜ƒ.m10;
      this.m11 = â˜ƒ.m11;
      this.m12 = â˜ƒ.m12;
      this.m13 = â˜ƒ.m13;
      this.m20 = â˜ƒ.m20;
      this.m21 = â˜ƒ.m21;
      this.m22 = â˜ƒ.m22;
      this.m23 = â˜ƒ.m23;
      this.m30 = â˜ƒ.m30;
      this.m31 = â˜ƒ.m31;
      this.m32 = â˜ƒ.m32;
      this.m33 = â˜ƒ.m33;
   }

   public Matrix4f(Quaternion var1) {
      float â˜ƒ = â˜ƒ.i();
      float â˜ƒx = â˜ƒ.j();
      float â˜ƒxx = â˜ƒ.k();
      float â˜ƒxxx = â˜ƒ.r();
      float â˜ƒxxxx = 2.0F * â˜ƒ * â˜ƒ;
      float â˜ƒxxxxx = 2.0F * â˜ƒx * â˜ƒx;
      float â˜ƒxxxxxx = 2.0F * â˜ƒxx * â˜ƒxx;
      this.m00 = 1.0F - â˜ƒxxxxx - â˜ƒxxxxxx;
      this.m11 = 1.0F - â˜ƒxxxxxx - â˜ƒxxxx;
      this.m22 = 1.0F - â˜ƒxxxx - â˜ƒxxxxx;
      this.m33 = 1.0F;
      float â˜ƒxxxxxxx = â˜ƒ * â˜ƒx;
      float â˜ƒxxxxxxxx = â˜ƒx * â˜ƒxx;
      float â˜ƒxxxxxxxxx = â˜ƒxx * â˜ƒ;
      float â˜ƒxxxxxxxxxx = â˜ƒ * â˜ƒxxx;
      float â˜ƒxxxxxxxxxxx = â˜ƒx * â˜ƒxxx;
      float â˜ƒxxxxxxxxxxxx = â˜ƒxx * â˜ƒxxx;
      this.m10 = 2.0F * (â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxx);
      this.m01 = 2.0F * (â˜ƒxxxxxxx - â˜ƒxxxxxxxxxxxx);
      this.m20 = 2.0F * (â˜ƒxxxxxxxxx - â˜ƒxxxxxxxxxxx);
      this.m02 = 2.0F * (â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxxx);
      this.m21 = 2.0F * (â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxx);
      this.m12 = 2.0F * (â˜ƒxxxxxxxx - â˜ƒxxxxxxxxxx);
   }

   public boolean isInteger() {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.m30 = 1.0F;
      â˜ƒ.m31 = 1.0F;
      â˜ƒ.m32 = 1.0F;
      â˜ƒ.m33 = 0.0F;
      Matrix4f â˜ƒx = this.copy();
      â˜ƒx.multiply(â˜ƒ);
      return isInteger(â˜ƒx.m00 / â˜ƒx.m03)
         && isInteger(â˜ƒx.m10 / â˜ƒx.m13)
         && isInteger(â˜ƒx.m20 / â˜ƒx.m23)
         && isInteger(â˜ƒx.m01 / â˜ƒx.m03)
         && isInteger(â˜ƒx.m11 / â˜ƒx.m13)
         && isInteger(â˜ƒx.m21 / â˜ƒx.m23)
         && isInteger(â˜ƒx.m02 / â˜ƒx.m03)
         && isInteger(â˜ƒx.m12 / â˜ƒx.m13)
         && isInteger(â˜ƒx.m22 / â˜ƒx.m23);
   }

   private static boolean isInteger(float var0) {
      return (double)Math.abs(â˜ƒ - (float)Math.round(â˜ƒ)) <= 1.0E-5;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Matrix4f â˜ƒ = (Matrix4f)â˜ƒ;
         return Float.compare(â˜ƒ.m00, this.m00) == 0
            && Float.compare(â˜ƒ.m01, this.m01) == 0
            && Float.compare(â˜ƒ.m02, this.m02) == 0
            && Float.compare(â˜ƒ.m03, this.m03) == 0
            && Float.compare(â˜ƒ.m10, this.m10) == 0
            && Float.compare(â˜ƒ.m11, this.m11) == 0
            && Float.compare(â˜ƒ.m12, this.m12) == 0
            && Float.compare(â˜ƒ.m13, this.m13) == 0
            && Float.compare(â˜ƒ.m20, this.m20) == 0
            && Float.compare(â˜ƒ.m21, this.m21) == 0
            && Float.compare(â˜ƒ.m22, this.m22) == 0
            && Float.compare(â˜ƒ.m23, this.m23) == 0
            && Float.compare(â˜ƒ.m30, this.m30) == 0
            && Float.compare(â˜ƒ.m31, this.m31) == 0
            && Float.compare(â˜ƒ.m32, this.m32) == 0
            && Float.compare(â˜ƒ.m33, this.m33) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = this.m00 != 0.0F ? Float.floatToIntBits(this.m00) : 0;
      â˜ƒ = 31 * â˜ƒ + (this.m01 != 0.0F ? Float.floatToIntBits(this.m01) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m02 != 0.0F ? Float.floatToIntBits(this.m02) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m03 != 0.0F ? Float.floatToIntBits(this.m03) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m10 != 0.0F ? Float.floatToIntBits(this.m10) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m11 != 0.0F ? Float.floatToIntBits(this.m11) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m12 != 0.0F ? Float.floatToIntBits(this.m12) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m13 != 0.0F ? Float.floatToIntBits(this.m13) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m20 != 0.0F ? Float.floatToIntBits(this.m20) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m21 != 0.0F ? Float.floatToIntBits(this.m21) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m22 != 0.0F ? Float.floatToIntBits(this.m22) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m23 != 0.0F ? Float.floatToIntBits(this.m23) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m30 != 0.0F ? Float.floatToIntBits(this.m30) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m31 != 0.0F ? Float.floatToIntBits(this.m31) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m32 != 0.0F ? Float.floatToIntBits(this.m32) : 0);
      return 31 * â˜ƒ + (this.m33 != 0.0F ? Float.floatToIntBits(this.m33) : 0);
   }

   private static int bufferIndex(int var0, int var1) {
      return â˜ƒ * 4 + â˜ƒ;
   }

   public void load(FloatBuffer var1) {
      this.m00 = â˜ƒ.get(bufferIndex(0, 0));
      this.m01 = â˜ƒ.get(bufferIndex(0, 1));
      this.m02 = â˜ƒ.get(bufferIndex(0, 2));
      this.m03 = â˜ƒ.get(bufferIndex(0, 3));
      this.m10 = â˜ƒ.get(bufferIndex(1, 0));
      this.m11 = â˜ƒ.get(bufferIndex(1, 1));
      this.m12 = â˜ƒ.get(bufferIndex(1, 2));
      this.m13 = â˜ƒ.get(bufferIndex(1, 3));
      this.m20 = â˜ƒ.get(bufferIndex(2, 0));
      this.m21 = â˜ƒ.get(bufferIndex(2, 1));
      this.m22 = â˜ƒ.get(bufferIndex(2, 2));
      this.m23 = â˜ƒ.get(bufferIndex(2, 3));
      this.m30 = â˜ƒ.get(bufferIndex(3, 0));
      this.m31 = â˜ƒ.get(bufferIndex(3, 1));
      this.m32 = â˜ƒ.get(bufferIndex(3, 2));
      this.m33 = â˜ƒ.get(bufferIndex(3, 3));
   }

   public void loadTransposed(FloatBuffer var1) {
      this.m00 = â˜ƒ.get(bufferIndex(0, 0));
      this.m01 = â˜ƒ.get(bufferIndex(1, 0));
      this.m02 = â˜ƒ.get(bufferIndex(2, 0));
      this.m03 = â˜ƒ.get(bufferIndex(3, 0));
      this.m10 = â˜ƒ.get(bufferIndex(0, 1));
      this.m11 = â˜ƒ.get(bufferIndex(1, 1));
      this.m12 = â˜ƒ.get(bufferIndex(2, 1));
      this.m13 = â˜ƒ.get(bufferIndex(3, 1));
      this.m20 = â˜ƒ.get(bufferIndex(0, 2));
      this.m21 = â˜ƒ.get(bufferIndex(1, 2));
      this.m22 = â˜ƒ.get(bufferIndex(2, 2));
      this.m23 = â˜ƒ.get(bufferIndex(3, 2));
      this.m30 = â˜ƒ.get(bufferIndex(0, 3));
      this.m31 = â˜ƒ.get(bufferIndex(1, 3));
      this.m32 = â˜ƒ.get(bufferIndex(2, 3));
      this.m33 = â˜ƒ.get(bufferIndex(3, 3));
   }

   public void load(FloatBuffer var1, boolean var2) {
      if (â˜ƒ) {
         this.loadTransposed(â˜ƒ);
      } else {
         this.load(â˜ƒ);
      }
   }

   public void load(Matrix4f var1) {
      this.m00 = â˜ƒ.m00;
      this.m01 = â˜ƒ.m01;
      this.m02 = â˜ƒ.m02;
      this.m03 = â˜ƒ.m03;
      this.m10 = â˜ƒ.m10;
      this.m11 = â˜ƒ.m11;
      this.m12 = â˜ƒ.m12;
      this.m13 = â˜ƒ.m13;
      this.m20 = â˜ƒ.m20;
      this.m21 = â˜ƒ.m21;
      this.m22 = â˜ƒ.m22;
      this.m23 = â˜ƒ.m23;
      this.m30 = â˜ƒ.m30;
      this.m31 = â˜ƒ.m31;
      this.m32 = â˜ƒ.m32;
      this.m33 = â˜ƒ.m33;
   }

   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append("Matrix4f:\n");
      â˜ƒ.append(this.m00);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m01);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m02);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m03);
      â˜ƒ.append("\n");
      â˜ƒ.append(this.m10);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m11);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m12);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m13);
      â˜ƒ.append("\n");
      â˜ƒ.append(this.m20);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m21);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m22);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m23);
      â˜ƒ.append("\n");
      â˜ƒ.append(this.m30);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m31);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m32);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m33);
      â˜ƒ.append("\n");
      return â˜ƒ.toString();
   }

   public void store(FloatBuffer var1) {
      â˜ƒ.put(bufferIndex(0, 0), this.m00);
      â˜ƒ.put(bufferIndex(0, 1), this.m01);
      â˜ƒ.put(bufferIndex(0, 2), this.m02);
      â˜ƒ.put(bufferIndex(0, 3), this.m03);
      â˜ƒ.put(bufferIndex(1, 0), this.m10);
      â˜ƒ.put(bufferIndex(1, 1), this.m11);
      â˜ƒ.put(bufferIndex(1, 2), this.m12);
      â˜ƒ.put(bufferIndex(1, 3), this.m13);
      â˜ƒ.put(bufferIndex(2, 0), this.m20);
      â˜ƒ.put(bufferIndex(2, 1), this.m21);
      â˜ƒ.put(bufferIndex(2, 2), this.m22);
      â˜ƒ.put(bufferIndex(2, 3), this.m23);
      â˜ƒ.put(bufferIndex(3, 0), this.m30);
      â˜ƒ.put(bufferIndex(3, 1), this.m31);
      â˜ƒ.put(bufferIndex(3, 2), this.m32);
      â˜ƒ.put(bufferIndex(3, 3), this.m33);
   }

   public void storeTransposed(FloatBuffer var1) {
      â˜ƒ.put(bufferIndex(0, 0), this.m00);
      â˜ƒ.put(bufferIndex(1, 0), this.m01);
      â˜ƒ.put(bufferIndex(2, 0), this.m02);
      â˜ƒ.put(bufferIndex(3, 0), this.m03);
      â˜ƒ.put(bufferIndex(0, 1), this.m10);
      â˜ƒ.put(bufferIndex(1, 1), this.m11);
      â˜ƒ.put(bufferIndex(2, 1), this.m12);
      â˜ƒ.put(bufferIndex(3, 1), this.m13);
      â˜ƒ.put(bufferIndex(0, 2), this.m20);
      â˜ƒ.put(bufferIndex(1, 2), this.m21);
      â˜ƒ.put(bufferIndex(2, 2), this.m22);
      â˜ƒ.put(bufferIndex(3, 2), this.m23);
      â˜ƒ.put(bufferIndex(0, 3), this.m30);
      â˜ƒ.put(bufferIndex(1, 3), this.m31);
      â˜ƒ.put(bufferIndex(2, 3), this.m32);
      â˜ƒ.put(bufferIndex(3, 3), this.m33);
   }

   public void store(FloatBuffer var1, boolean var2) {
      if (â˜ƒ) {
         this.storeTransposed(â˜ƒ);
      } else {
         this.store(â˜ƒ);
      }
   }

   public void setIdentity() {
      this.m00 = 1.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = 1.0F;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 1.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public float adjugateAndDet() {
      float â˜ƒ = this.m00 * this.m11 - this.m01 * this.m10;
      float â˜ƒx = this.m00 * this.m12 - this.m02 * this.m10;
      float â˜ƒxx = this.m00 * this.m13 - this.m03 * this.m10;
      float â˜ƒxxx = this.m01 * this.m12 - this.m02 * this.m11;
      float â˜ƒxxxx = this.m01 * this.m13 - this.m03 * this.m11;
      float â˜ƒxxxxx = this.m02 * this.m13 - this.m03 * this.m12;
      float â˜ƒxxxxxx = this.m20 * this.m31 - this.m21 * this.m30;
      float â˜ƒxxxxxxx = this.m20 * this.m32 - this.m22 * this.m30;
      float â˜ƒxxxxxxxx = this.m20 * this.m33 - this.m23 * this.m30;
      float â˜ƒxxxxxxxxx = this.m21 * this.m32 - this.m22 * this.m31;
      float â˜ƒxxxxxxxxxx = this.m21 * this.m33 - this.m23 * this.m31;
      float â˜ƒxxxxxxxxxxx = this.m22 * this.m33 - this.m23 * this.m32;
      float â˜ƒxxxxxxxxxxxx = this.m11 * â˜ƒxxxxxxxxxxx - this.m12 * â˜ƒxxxxxxxxxx + this.m13 * â˜ƒxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxx = -this.m10 * â˜ƒxxxxxxxxxxx + this.m12 * â˜ƒxxxxxxxx - this.m13 * â˜ƒxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxx = this.m10 * â˜ƒxxxxxxxxxx - this.m11 * â˜ƒxxxxxxxx + this.m13 * â˜ƒxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxx = -this.m10 * â˜ƒxxxxxxxxx + this.m11 * â˜ƒxxxxxxx - this.m12 * â˜ƒxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxx = -this.m01 * â˜ƒxxxxxxxxxxx + this.m02 * â˜ƒxxxxxxxxxx - this.m03 * â˜ƒxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxx = this.m00 * â˜ƒxxxxxxxxxxx - this.m02 * â˜ƒxxxxxxxx + this.m03 * â˜ƒxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxx = -this.m00 * â˜ƒxxxxxxxxxx + this.m01 * â˜ƒxxxxxxxx - this.m03 * â˜ƒxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxxx = this.m00 * â˜ƒxxxxxxxxx - this.m01 * â˜ƒxxxxxxx + this.m02 * â˜ƒxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxx = this.m31 * â˜ƒxxxxx - this.m32 * â˜ƒxxxx + this.m33 * â˜ƒxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxx = -this.m30 * â˜ƒxxxxx + this.m32 * â˜ƒxx - this.m33 * â˜ƒx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxx = this.m30 * â˜ƒxxxx - this.m31 * â˜ƒxx + this.m33 * â˜ƒ;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = -this.m30 * â˜ƒxxx + this.m31 * â˜ƒx - this.m32 * â˜ƒ;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = -this.m21 * â˜ƒxxxxx + this.m22 * â˜ƒxxxx - this.m23 * â˜ƒxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = this.m20 * â˜ƒxxxxx - this.m22 * â˜ƒxx + this.m23 * â˜ƒx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = -this.m20 * â˜ƒxxxx + this.m21 * â˜ƒxx - this.m23 * â˜ƒ;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.m20 * â˜ƒxxx - this.m21 * â˜ƒx + this.m22 * â˜ƒ;
      this.m00 = â˜ƒxxxxxxxxxxxx;
      this.m10 = â˜ƒxxxxxxxxxxxxx;
      this.m20 = â˜ƒxxxxxxxxxxxxxx;
      this.m30 = â˜ƒxxxxxxxxxxxxxxx;
      this.m01 = â˜ƒxxxxxxxxxxxxxxxx;
      this.m11 = â˜ƒxxxxxxxxxxxxxxxxx;
      this.m21 = â˜ƒxxxxxxxxxxxxxxxxxx;
      this.m31 = â˜ƒxxxxxxxxxxxxxxxxxxx;
      this.m02 = â˜ƒxxxxxxxxxxxxxxxxxxxx;
      this.m12 = â˜ƒxxxxxxxxxxxxxxxxxxxxx;
      this.m22 = â˜ƒxxxxxxxxxxxxxxxxxxxxxx;
      this.m32 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx;
      this.m03 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx;
      this.m13 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx;
      this.m23 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx;
      this.m33 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      return â˜ƒ * â˜ƒxxxxxxxxxxx - â˜ƒx * â˜ƒxxxxxxxxxx + â˜ƒxx * â˜ƒxxxxxxxxx + â˜ƒxxx * â˜ƒxxxxxxxx - â˜ƒxxxx * â˜ƒxxxxxxx + â˜ƒxxxxx * â˜ƒxxxxxx;
   }

   public float determinant() {
      float â˜ƒ = this.m00 * this.m11 - this.m01 * this.m10;
      float â˜ƒx = this.m00 * this.m12 - this.m02 * this.m10;
      float â˜ƒxx = this.m00 * this.m13 - this.m03 * this.m10;
      float â˜ƒxxx = this.m01 * this.m12 - this.m02 * this.m11;
      float â˜ƒxxxx = this.m01 * this.m13 - this.m03 * this.m11;
      float â˜ƒxxxxx = this.m02 * this.m13 - this.m03 * this.m12;
      float â˜ƒxxxxxx = this.m20 * this.m31 - this.m21 * this.m30;
      float â˜ƒxxxxxxx = this.m20 * this.m32 - this.m22 * this.m30;
      float â˜ƒxxxxxxxx = this.m20 * this.m33 - this.m23 * this.m30;
      float â˜ƒxxxxxxxxx = this.m21 * this.m32 - this.m22 * this.m31;
      float â˜ƒxxxxxxxxxx = this.m21 * this.m33 - this.m23 * this.m31;
      float â˜ƒxxxxxxxxxxx = this.m22 * this.m33 - this.m23 * this.m32;
      return â˜ƒ * â˜ƒxxxxxxxxxxx - â˜ƒx * â˜ƒxxxxxxxxxx + â˜ƒxx * â˜ƒxxxxxxxxx + â˜ƒxxx * â˜ƒxxxxxxxx - â˜ƒxxxx * â˜ƒxxxxxxx + â˜ƒxxxxx * â˜ƒxxxxxx;
   }

   public void transpose() {
      float â˜ƒ = this.m10;
      this.m10 = this.m01;
      this.m01 = â˜ƒ;
      â˜ƒ = this.m20;
      this.m20 = this.m02;
      this.m02 = â˜ƒ;
      â˜ƒ = this.m21;
      this.m21 = this.m12;
      this.m12 = â˜ƒ;
      â˜ƒ = this.m30;
      this.m30 = this.m03;
      this.m03 = â˜ƒ;
      â˜ƒ = this.m31;
      this.m31 = this.m13;
      this.m13 = â˜ƒ;
      â˜ƒ = this.m32;
      this.m32 = this.m23;
      this.m23 = â˜ƒ;
   }

   public boolean invert() {
      float â˜ƒ = this.adjugateAndDet();
      if (Math.abs(â˜ƒ) > 1.0E-6F) {
         this.multiply(â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   public void multiply(Matrix4f var1) {
      float â˜ƒ = this.m00 * â˜ƒ.m00 + this.m01 * â˜ƒ.m10 + this.m02 * â˜ƒ.m20 + this.m03 * â˜ƒ.m30;
      float â˜ƒx = this.m00 * â˜ƒ.m01 + this.m01 * â˜ƒ.m11 + this.m02 * â˜ƒ.m21 + this.m03 * â˜ƒ.m31;
      float â˜ƒxx = this.m00 * â˜ƒ.m02 + this.m01 * â˜ƒ.m12 + this.m02 * â˜ƒ.m22 + this.m03 * â˜ƒ.m32;
      float â˜ƒxxx = this.m00 * â˜ƒ.m03 + this.m01 * â˜ƒ.m13 + this.m02 * â˜ƒ.m23 + this.m03 * â˜ƒ.m33;
      float â˜ƒxxxx = this.m10 * â˜ƒ.m00 + this.m11 * â˜ƒ.m10 + this.m12 * â˜ƒ.m20 + this.m13 * â˜ƒ.m30;
      float â˜ƒxxxxx = this.m10 * â˜ƒ.m01 + this.m11 * â˜ƒ.m11 + this.m12 * â˜ƒ.m21 + this.m13 * â˜ƒ.m31;
      float â˜ƒxxxxxx = this.m10 * â˜ƒ.m02 + this.m11 * â˜ƒ.m12 + this.m12 * â˜ƒ.m22 + this.m13 * â˜ƒ.m32;
      float â˜ƒxxxxxxx = this.m10 * â˜ƒ.m03 + this.m11 * â˜ƒ.m13 + this.m12 * â˜ƒ.m23 + this.m13 * â˜ƒ.m33;
      float â˜ƒxxxxxxxx = this.m20 * â˜ƒ.m00 + this.m21 * â˜ƒ.m10 + this.m22 * â˜ƒ.m20 + this.m23 * â˜ƒ.m30;
      float â˜ƒxxxxxxxxx = this.m20 * â˜ƒ.m01 + this.m21 * â˜ƒ.m11 + this.m22 * â˜ƒ.m21 + this.m23 * â˜ƒ.m31;
      float â˜ƒxxxxxxxxxx = this.m20 * â˜ƒ.m02 + this.m21 * â˜ƒ.m12 + this.m22 * â˜ƒ.m22 + this.m23 * â˜ƒ.m32;
      float â˜ƒxxxxxxxxxxx = this.m20 * â˜ƒ.m03 + this.m21 * â˜ƒ.m13 + this.m22 * â˜ƒ.m23 + this.m23 * â˜ƒ.m33;
      float â˜ƒxxxxxxxxxxxx = this.m30 * â˜ƒ.m00 + this.m31 * â˜ƒ.m10 + this.m32 * â˜ƒ.m20 + this.m33 * â˜ƒ.m30;
      float â˜ƒxxxxxxxxxxxxx = this.m30 * â˜ƒ.m01 + this.m31 * â˜ƒ.m11 + this.m32 * â˜ƒ.m21 + this.m33 * â˜ƒ.m31;
      float â˜ƒxxxxxxxxxxxxxx = this.m30 * â˜ƒ.m02 + this.m31 * â˜ƒ.m12 + this.m32 * â˜ƒ.m22 + this.m33 * â˜ƒ.m32;
      float â˜ƒxxxxxxxxxxxxxxx = this.m30 * â˜ƒ.m03 + this.m31 * â˜ƒ.m13 + this.m32 * â˜ƒ.m23 + this.m33 * â˜ƒ.m33;
      this.m00 = â˜ƒ;
      this.m01 = â˜ƒx;
      this.m02 = â˜ƒxx;
      this.m03 = â˜ƒxxx;
      this.m10 = â˜ƒxxxx;
      this.m11 = â˜ƒxxxxx;
      this.m12 = â˜ƒxxxxxx;
      this.m13 = â˜ƒxxxxxxx;
      this.m20 = â˜ƒxxxxxxxx;
      this.m21 = â˜ƒxxxxxxxxx;
      this.m22 = â˜ƒxxxxxxxxxx;
      this.m23 = â˜ƒxxxxxxxxxxx;
      this.m30 = â˜ƒxxxxxxxxxxxx;
      this.m31 = â˜ƒxxxxxxxxxxxxx;
      this.m32 = â˜ƒxxxxxxxxxxxxxx;
      this.m33 = â˜ƒxxxxxxxxxxxxxxx;
   }

   public void multiply(Quaternion var1) {
      this.multiply(new Matrix4f(â˜ƒ));
   }

   public void multiply(float var1) {
      this.m00 *= â˜ƒ;
      this.m01 *= â˜ƒ;
      this.m02 *= â˜ƒ;
      this.m03 *= â˜ƒ;
      this.m10 *= â˜ƒ;
      this.m11 *= â˜ƒ;
      this.m12 *= â˜ƒ;
      this.m13 *= â˜ƒ;
      this.m20 *= â˜ƒ;
      this.m21 *= â˜ƒ;
      this.m22 *= â˜ƒ;
      this.m23 *= â˜ƒ;
      this.m30 *= â˜ƒ;
      this.m31 *= â˜ƒ;
      this.m32 *= â˜ƒ;
      this.m33 *= â˜ƒ;
   }

   public void add(Matrix4f var1) {
      this.m00 += â˜ƒ.m00;
      this.m01 += â˜ƒ.m01;
      this.m02 += â˜ƒ.m02;
      this.m03 += â˜ƒ.m03;
      this.m10 += â˜ƒ.m10;
      this.m11 += â˜ƒ.m11;
      this.m12 += â˜ƒ.m12;
      this.m13 += â˜ƒ.m13;
      this.m20 += â˜ƒ.m20;
      this.m21 += â˜ƒ.m21;
      this.m22 += â˜ƒ.m22;
      this.m23 += â˜ƒ.m23;
      this.m30 += â˜ƒ.m30;
      this.m31 += â˜ƒ.m31;
      this.m32 += â˜ƒ.m32;
      this.m33 += â˜ƒ.m33;
   }

   public void subtract(Matrix4f var1) {
      this.m00 -= â˜ƒ.m00;
      this.m01 -= â˜ƒ.m01;
      this.m02 -= â˜ƒ.m02;
      this.m03 -= â˜ƒ.m03;
      this.m10 -= â˜ƒ.m10;
      this.m11 -= â˜ƒ.m11;
      this.m12 -= â˜ƒ.m12;
      this.m13 -= â˜ƒ.m13;
      this.m20 -= â˜ƒ.m20;
      this.m21 -= â˜ƒ.m21;
      this.m22 -= â˜ƒ.m22;
      this.m23 -= â˜ƒ.m23;
      this.m30 -= â˜ƒ.m30;
      this.m31 -= â˜ƒ.m31;
      this.m32 -= â˜ƒ.m32;
      this.m33 -= â˜ƒ.m33;
   }

   public float trace() {
      return this.m00 + this.m11 + this.m22 + this.m33;
   }

   public static Matrix4f perspective(double var0, float var2, float var3, float var4) {
      float â˜ƒ = (float)(1.0 / Math.tan(â˜ƒ * (float) (Math.PI / 180.0) / 2.0));
      Matrix4f â˜ƒx = new Matrix4f();
      â˜ƒx.m00 = â˜ƒ / â˜ƒ;
      â˜ƒx.m11 = â˜ƒ;
      â˜ƒx.m22 = (â˜ƒ + â˜ƒ) / (â˜ƒ - â˜ƒ);
      â˜ƒx.m32 = -1.0F;
      â˜ƒx.m23 = 2.0F * â˜ƒ * â˜ƒ / (â˜ƒ - â˜ƒ);
      return â˜ƒx;
   }

   public static Matrix4f orthographic(float var0, float var1, float var2, float var3) {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.m00 = 2.0F / â˜ƒ;
      â˜ƒ.m11 = 2.0F / â˜ƒ;
      float â˜ƒx = â˜ƒ - â˜ƒ;
      â˜ƒ.m22 = -2.0F / â˜ƒx;
      â˜ƒ.m33 = 1.0F;
      â˜ƒ.m03 = -1.0F;
      â˜ƒ.m13 = 1.0F;
      â˜ƒ.m23 = -(â˜ƒ + â˜ƒ) / â˜ƒx;
      return â˜ƒ;
   }

   public static Matrix4f orthographic(float var0, float var1, float var2, float var3, float var4, float var5) {
      Matrix4f â˜ƒ = new Matrix4f();
      float â˜ƒx = â˜ƒ - â˜ƒ;
      float â˜ƒxx = â˜ƒ - â˜ƒ;
      float â˜ƒxxx = â˜ƒ - â˜ƒ;
      â˜ƒ.m00 = 2.0F / â˜ƒx;
      â˜ƒ.m11 = 2.0F / â˜ƒxx;
      â˜ƒ.m22 = -2.0F / â˜ƒxxx;
      â˜ƒ.m03 = -(â˜ƒ + â˜ƒ) / â˜ƒx;
      â˜ƒ.m13 = -(â˜ƒ + â˜ƒ) / â˜ƒxx;
      â˜ƒ.m23 = -(â˜ƒ + â˜ƒ) / â˜ƒxxx;
      â˜ƒ.m33 = 1.0F;
      return â˜ƒ;
   }

   public void translate(Vector3f var1) {
      this.m03 += â˜ƒ.x();
      this.m13 += â˜ƒ.y();
      this.m23 += â˜ƒ.z();
   }

   public Matrix4f copy() {
      return new Matrix4f(this);
   }

   public void multiplyWithTranslation(float var1, float var2, float var3) {
      this.m03 += this.m00 * â˜ƒ + this.m01 * â˜ƒ + this.m02 * â˜ƒ;
      this.m13 += this.m10 * â˜ƒ + this.m11 * â˜ƒ + this.m12 * â˜ƒ;
      this.m23 += this.m20 * â˜ƒ + this.m21 * â˜ƒ + this.m22 * â˜ƒ;
      this.m33 += this.m30 * â˜ƒ + this.m31 * â˜ƒ + this.m32 * â˜ƒ;
   }

   public static Matrix4f createScaleMatrix(float var0, float var1, float var2) {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.m00 = â˜ƒ;
      â˜ƒ.m11 = â˜ƒ;
      â˜ƒ.m22 = â˜ƒ;
      â˜ƒ.m33 = 1.0F;
      return â˜ƒ;
   }

   public static Matrix4f createTranslateMatrix(float var0, float var1, float var2) {
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.m00 = 1.0F;
      â˜ƒ.m11 = 1.0F;
      â˜ƒ.m22 = 1.0F;
      â˜ƒ.m33 = 1.0F;
      â˜ƒ.m03 = â˜ƒ;
      â˜ƒ.m13 = â˜ƒ;
      â˜ƒ.m23 = â˜ƒ;
      return â˜ƒ;
   }
}
