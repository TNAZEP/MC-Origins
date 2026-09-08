package com.mojang.math;

import com.mojang.datafixers.util.Pair;
import java.nio.FloatBuffer;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
   private static final int ORDER = 3;
   private static final float G = 3.0F + 2.0F * (float)Math.sqrt(2.0);
   private static final float CS = (float)Math.cos(Math.PI / 8);
   private static final float SS = (float)Math.sin(Math.PI / 8);
   private static final float SQ2 = 1.0F / (float)Math.sqrt(2.0);
   protected float m00;
   protected float m01;
   protected float m02;
   protected float m10;
   protected float m11;
   protected float m12;
   protected float m20;
   protected float m21;
   protected float m22;

   public Matrix3f() {
   }

   public Matrix3f(Quaternion var1) {
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

   public static Matrix3f createScaleMatrix(float var0, float var1, float var2) {
      Matrix3f â˜ƒ = new Matrix3f();
      â˜ƒ.m00 = â˜ƒ;
      â˜ƒ.m11 = â˜ƒ;
      â˜ƒ.m22 = â˜ƒ;
      return â˜ƒ;
   }

   public Matrix3f(Matrix4f var1) {
      this.m00 = â˜ƒ.m00;
      this.m01 = â˜ƒ.m01;
      this.m02 = â˜ƒ.m02;
      this.m10 = â˜ƒ.m10;
      this.m11 = â˜ƒ.m11;
      this.m12 = â˜ƒ.m12;
      this.m20 = â˜ƒ.m20;
      this.m21 = â˜ƒ.m21;
      this.m22 = â˜ƒ.m22;
   }

   public Matrix3f(Matrix3f var1) {
      this.m00 = â˜ƒ.m00;
      this.m01 = â˜ƒ.m01;
      this.m02 = â˜ƒ.m02;
      this.m10 = â˜ƒ.m10;
      this.m11 = â˜ƒ.m11;
      this.m12 = â˜ƒ.m12;
      this.m20 = â˜ƒ.m20;
      this.m21 = â˜ƒ.m21;
      this.m22 = â˜ƒ.m22;
   }

   private static Pair<Float, Float> approxGivensQuat(float var0, float var1, float var2) {
      float â˜ƒ = 2.0F * (â˜ƒ - â˜ƒ);
      if (G * â˜ƒ * â˜ƒ < â˜ƒ * â˜ƒ) {
         float â˜ƒx = Mth.fastInvSqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
         return Pair.of(â˜ƒx * â˜ƒ, â˜ƒx * â˜ƒ);
      } else {
         return Pair.of(SS, CS);
      }
   }

   private static Pair<Float, Float> qrGivensQuat(float var0, float var1) {
      float â˜ƒ = (float)Math.hypot((double)â˜ƒ, (double)â˜ƒ);
      float â˜ƒx = â˜ƒ > 1.0E-6F ? â˜ƒ : 0.0F;
      float â˜ƒxx = Math.abs(â˜ƒ) + Math.max(â˜ƒ, 1.0E-6F);
      if (â˜ƒ < 0.0F) {
         float â˜ƒxxx = â˜ƒx;
         â˜ƒx = â˜ƒxx;
         â˜ƒxx = â˜ƒxxx;
      }

      float â˜ƒ = Mth.fastInvSqrt(â˜ƒxx * â˜ƒxx + â˜ƒx * â˜ƒx);
      â˜ƒxx *= â˜ƒ;
      â˜ƒx *= â˜ƒ;
      return Pair.of(â˜ƒx, â˜ƒxx);
   }

   private static Quaternion stepJacobi(Matrix3f var0) {
      Matrix3f â˜ƒ = new Matrix3f();
      Quaternion â˜ƒx = Quaternion.ONE.copy();
      if (â˜ƒ.m01 * â˜ƒ.m01 + â˜ƒ.m10 * â˜ƒ.m10 > 1.0E-6F) {
         Pair<Float, Float> â˜ƒxx = approxGivensQuat(â˜ƒ.m00, 0.5F * (â˜ƒ.m01 + â˜ƒ.m10), â˜ƒ.m11);
         Float â˜ƒxxx = (Float)â˜ƒxx.getFirst();
         Float â˜ƒxxxx = (Float)â˜ƒxx.getSecond();
         Quaternion â˜ƒxxxxx = new Quaternion(0.0F, 0.0F, â˜ƒxxx, â˜ƒxxxx);
         float â˜ƒxxxxxx = â˜ƒxxxx * â˜ƒxxxx - â˜ƒxxx * â˜ƒxxx;
         float â˜ƒxxxxxxx = -2.0F * â˜ƒxxx * â˜ƒxxxx;
         float â˜ƒxxxxxxxx = â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxx * â˜ƒxxx;
         â˜ƒx.mul(â˜ƒxxxxx);
         â˜ƒ.setIdentity();
         â˜ƒ.m00 = â˜ƒxxxxxx;
         â˜ƒ.m11 = â˜ƒxxxxxx;
         â˜ƒ.m10 = -â˜ƒxxxxxxx;
         â˜ƒ.m01 = â˜ƒxxxxxxx;
         â˜ƒ.m22 = â˜ƒxxxxxxxx;
         â˜ƒ.mul(â˜ƒ);
         â˜ƒ.transpose();
         â˜ƒ.mul(â˜ƒ);
         â˜ƒ.load(â˜ƒ);
      }

      if (â˜ƒ.m02 * â˜ƒ.m02 + â˜ƒ.m20 * â˜ƒ.m20 > 1.0E-6F) {
         Pair<Float, Float> â˜ƒ = approxGivensQuat(â˜ƒ.m00, 0.5F * (â˜ƒ.m02 + â˜ƒ.m20), â˜ƒ.m22);
         float â˜ƒx = -â˜ƒ.getFirst();
         Float â˜ƒxx = (Float)â˜ƒ.getSecond();
         Quaternion â˜ƒxxx = new Quaternion(0.0F, â˜ƒx, 0.0F, â˜ƒxx);
         float â˜ƒxxxx = â˜ƒxx * â˜ƒxx - â˜ƒx * â˜ƒx;
         float â˜ƒxxxxx = -2.0F * â˜ƒx * â˜ƒxx;
         float â˜ƒxxxxxx = â˜ƒxx * â˜ƒxx + â˜ƒx * â˜ƒx;
         â˜ƒx.mul(â˜ƒxxx);
         â˜ƒ.setIdentity();
         â˜ƒ.m00 = â˜ƒxxxx;
         â˜ƒ.m22 = â˜ƒxxxx;
         â˜ƒ.m20 = â˜ƒxxxxx;
         â˜ƒ.m02 = -â˜ƒxxxxx;
         â˜ƒ.m11 = â˜ƒxxxxxx;
         â˜ƒ.mul(â˜ƒ);
         â˜ƒ.transpose();
         â˜ƒ.mul(â˜ƒ);
         â˜ƒ.load(â˜ƒ);
      }

      if (â˜ƒ.m12 * â˜ƒ.m12 + â˜ƒ.m21 * â˜ƒ.m21 > 1.0E-6F) {
         Pair<Float, Float> â˜ƒ = approxGivensQuat(â˜ƒ.m11, 0.5F * (â˜ƒ.m12 + â˜ƒ.m21), â˜ƒ.m22);
         Float â˜ƒx = (Float)â˜ƒ.getFirst();
         Float â˜ƒxx = (Float)â˜ƒ.getSecond();
         Quaternion â˜ƒxxx = new Quaternion(â˜ƒx, 0.0F, 0.0F, â˜ƒxx);
         float â˜ƒxxxx = â˜ƒxx * â˜ƒxx - â˜ƒx * â˜ƒx;
         float â˜ƒxxxxx = -2.0F * â˜ƒx * â˜ƒxx;
         float â˜ƒxxxxxx = â˜ƒxx * â˜ƒxx + â˜ƒx * â˜ƒx;
         â˜ƒx.mul(â˜ƒxxx);
         â˜ƒ.setIdentity();
         â˜ƒ.m11 = â˜ƒxxxx;
         â˜ƒ.m22 = â˜ƒxxxx;
         â˜ƒ.m21 = -â˜ƒxxxxx;
         â˜ƒ.m12 = â˜ƒxxxxx;
         â˜ƒ.m00 = â˜ƒxxxxxx;
         â˜ƒ.mul(â˜ƒ);
         â˜ƒ.transpose();
         â˜ƒ.mul(â˜ƒ);
         â˜ƒ.load(â˜ƒ);
      }

      return â˜ƒx;
   }

   private static void sortSingularValues(Matrix3f var0, Quaternion var1) {
      float â˜ƒ = â˜ƒ.m00 * â˜ƒ.m00 + â˜ƒ.m10 * â˜ƒ.m10 + â˜ƒ.m20 * â˜ƒ.m20;
      float â˜ƒx = â˜ƒ.m01 * â˜ƒ.m01 + â˜ƒ.m11 * â˜ƒ.m11 + â˜ƒ.m21 * â˜ƒ.m21;
      float â˜ƒxx = â˜ƒ.m02 * â˜ƒ.m02 + â˜ƒ.m12 * â˜ƒ.m12 + â˜ƒ.m22 * â˜ƒ.m22;
      if (â˜ƒ < â˜ƒx) {
         float â˜ƒxxx = â˜ƒ.m10;
         â˜ƒ.m10 = -â˜ƒ.m00;
         â˜ƒ.m00 = â˜ƒxxx;
         â˜ƒxxx = â˜ƒ.m11;
         â˜ƒ.m11 = -â˜ƒ.m01;
         â˜ƒ.m01 = â˜ƒxxx;
         â˜ƒxxx = â˜ƒ.m12;
         â˜ƒ.m12 = -â˜ƒ.m02;
         â˜ƒ.m02 = â˜ƒxxx;
         Quaternion â˜ƒxxxx = new Quaternion(0.0F, 0.0F, SQ2, SQ2);
         â˜ƒ.mul(â˜ƒxxxx);
         â˜ƒxxx = â˜ƒ;
         â˜ƒ = â˜ƒx;
         â˜ƒx = â˜ƒxxx;
      }

      if (â˜ƒ < â˜ƒxx) {
         float â˜ƒ = â˜ƒ.m20;
         â˜ƒ.m20 = -â˜ƒ.m00;
         â˜ƒ.m00 = â˜ƒ;
         â˜ƒ = â˜ƒ.m21;
         â˜ƒ.m21 = -â˜ƒ.m01;
         â˜ƒ.m01 = â˜ƒ;
         â˜ƒ = â˜ƒ.m22;
         â˜ƒ.m22 = -â˜ƒ.m02;
         â˜ƒ.m02 = â˜ƒ;
         Quaternion â˜ƒx = new Quaternion(0.0F, SQ2, 0.0F, SQ2);
         â˜ƒ.mul(â˜ƒx);
         â˜ƒxx = â˜ƒ;
      }

      if (â˜ƒx < â˜ƒxx) {
         float â˜ƒ = â˜ƒ.m20;
         â˜ƒ.m20 = -â˜ƒ.m10;
         â˜ƒ.m10 = â˜ƒ;
         â˜ƒ = â˜ƒ.m21;
         â˜ƒ.m21 = -â˜ƒ.m11;
         â˜ƒ.m11 = â˜ƒ;
         â˜ƒ = â˜ƒ.m22;
         â˜ƒ.m22 = -â˜ƒ.m12;
         â˜ƒ.m12 = â˜ƒ;
         Quaternion â˜ƒx = new Quaternion(SQ2, 0.0F, 0.0F, SQ2);
         â˜ƒ.mul(â˜ƒx);
      }
   }

   public void transpose() {
      float â˜ƒ = this.m01;
      this.m01 = this.m10;
      this.m10 = â˜ƒ;
      â˜ƒ = this.m02;
      this.m02 = this.m20;
      this.m20 = â˜ƒ;
      â˜ƒ = this.m12;
      this.m12 = this.m21;
      this.m21 = â˜ƒ;
   }

   public Triple<Quaternion, Vector3f, Quaternion> svdDecompose() {
      Quaternion â˜ƒ = Quaternion.ONE.copy();
      Quaternion â˜ƒx = Quaternion.ONE.copy();
      Matrix3f â˜ƒxx = this.copy();
      â˜ƒxx.transpose();
      â˜ƒxx.mul(this);

      for(int â˜ƒxxx = 0; â˜ƒxxx < 5; ++â˜ƒxxx) {
         â˜ƒx.mul(stepJacobi(â˜ƒxx));
      }

      â˜ƒx.normalize();
      Matrix3f â˜ƒxxx = new Matrix3f(this);
      â˜ƒxxx.mul(new Matrix3f(â˜ƒx));
      float â˜ƒxxxx = 1.0F;
      Pair<Float, Float> â˜ƒxxxxx = qrGivensQuat(â˜ƒxxx.m00, â˜ƒxxx.m10);
      Float â˜ƒxxxxxx = (Float)â˜ƒxxxxx.getFirst();
      Float â˜ƒxxxxxxx = (Float)â˜ƒxxxxx.getSecond();
      float â˜ƒxxxxxxxx = â˜ƒxxxxxxx * â˜ƒxxxxxxx - â˜ƒxxxxxx * â˜ƒxxxxxx;
      float â˜ƒxxxxxxxxx = -2.0F * â˜ƒxxxxxx * â˜ƒxxxxxxx;
      float â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx;
      Quaternion â˜ƒxxxxxxxxxxx = new Quaternion(0.0F, 0.0F, â˜ƒxxxxxx, â˜ƒxxxxxxx);
      â˜ƒ.mul(â˜ƒxxxxxxxxxxx);
      Matrix3f â˜ƒxxxxxxxxxxxx = new Matrix3f();
      â˜ƒxxxxxxxxxxxx.setIdentity();
      â˜ƒxxxxxxxxxxxx.m00 = â˜ƒxxxxxxxx;
      â˜ƒxxxxxxxxxxxx.m11 = â˜ƒxxxxxxxx;
      â˜ƒxxxxxxxxxxxx.m10 = â˜ƒxxxxxxxxx;
      â˜ƒxxxxxxxxxxxx.m01 = -â˜ƒxxxxxxxxx;
      â˜ƒxxxxxxxxxxxx.m22 = â˜ƒxxxxxxxxxx;
      â˜ƒxxxx *= â˜ƒxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxx.mul(â˜ƒxxx);
      â˜ƒxxxxx = qrGivensQuat(â˜ƒxxxxxxxxxxxx.m00, â˜ƒxxxxxxxxxxxx.m20);
      float â˜ƒxxxxxxxxxxxxx = -â˜ƒxxxxx.getFirst();
      Float â˜ƒxxxxxxxxxxxxxx = (Float)â˜ƒxxxxx.getSecond();
      float â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxx = -2.0F * â˜ƒxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxx;
      Quaternion â˜ƒxxxxxxxxxxxxxxxxxx = new Quaternion(0.0F, â˜ƒxxxxxxxxxxxxx, 0.0F, â˜ƒxxxxxxxxxxxxxx);
      â˜ƒ.mul(â˜ƒxxxxxxxxxxxxxxxxxx);
      Matrix3f â˜ƒxxxxxxxxxxxxxxxxxxx = new Matrix3f();
      â˜ƒxxxxxxxxxxxxxxxxxxx.setIdentity();
      â˜ƒxxxxxxxxxxxxxxxxxxx.m00 = â˜ƒxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxx.m22 = â˜ƒxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxx.m20 = -â˜ƒxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxx.m02 = â˜ƒxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxx.m11 = â˜ƒxxxxxxxxxxxxxxxxx;
      â˜ƒxxxx *= â˜ƒxxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxx.mul(â˜ƒxxxxxxxxxxxx);
      â˜ƒxxxxx = qrGivensQuat(â˜ƒxxxxxxxxxxxxxxxxxxx.m11, â˜ƒxxxxxxxxxxxxxxxxxxx.m21);
      Float â˜ƒxxxxxxxxxxxxxxxxxxxx = (Float)â˜ƒxxxxx.getFirst();
      Float â˜ƒxxxxxxxxxxxxxxxxxxxxx = (Float)â˜ƒxxxxx.getSecond();
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = -2.0F * â˜ƒxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx;
      Quaternion â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = new Quaternion(â˜ƒxxxxxxxxxxxxxxxxxxxx, 0.0F, 0.0F, â˜ƒxxxxxxxxxxxxxxxxxxxxx);
      â˜ƒ.mul(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx);
      Matrix3f â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = new Matrix3f();
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.setIdentity();
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m11 = â˜ƒxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m22 = â˜ƒxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m21 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m12 = -â˜ƒxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m00 = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒxxxx *= â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.mul(â˜ƒxxxxxxxxxxxxxxxxxxx);
      â˜ƒxxxx = 1.0F / â˜ƒxxxx;
      â˜ƒ.mul((float)Math.sqrt((double)â˜ƒxxxx));
      Vector3f â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Vector3f(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m00 * â˜ƒxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m11 * â˜ƒxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.m22 * â˜ƒxxxx
      );
      return Triple.of(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒx);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Matrix3f â˜ƒ = (Matrix3f)â˜ƒ;
         return Float.compare(â˜ƒ.m00, this.m00) == 0
            && Float.compare(â˜ƒ.m01, this.m01) == 0
            && Float.compare(â˜ƒ.m02, this.m02) == 0
            && Float.compare(â˜ƒ.m10, this.m10) == 0
            && Float.compare(â˜ƒ.m11, this.m11) == 0
            && Float.compare(â˜ƒ.m12, this.m12) == 0
            && Float.compare(â˜ƒ.m20, this.m20) == 0
            && Float.compare(â˜ƒ.m21, this.m21) == 0
            && Float.compare(â˜ƒ.m22, this.m22) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = this.m00 != 0.0F ? Float.floatToIntBits(this.m00) : 0;
      â˜ƒ = 31 * â˜ƒ + (this.m01 != 0.0F ? Float.floatToIntBits(this.m01) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m02 != 0.0F ? Float.floatToIntBits(this.m02) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m10 != 0.0F ? Float.floatToIntBits(this.m10) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m11 != 0.0F ? Float.floatToIntBits(this.m11) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m12 != 0.0F ? Float.floatToIntBits(this.m12) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m20 != 0.0F ? Float.floatToIntBits(this.m20) : 0);
      â˜ƒ = 31 * â˜ƒ + (this.m21 != 0.0F ? Float.floatToIntBits(this.m21) : 0);
      return 31 * â˜ƒ + (this.m22 != 0.0F ? Float.floatToIntBits(this.m22) : 0);
   }

   private static int bufferIndex(int var0, int var1) {
      return â˜ƒ * 3 + â˜ƒ;
   }

   public void load(FloatBuffer var1) {
      this.m00 = â˜ƒ.get(bufferIndex(0, 0));
      this.m01 = â˜ƒ.get(bufferIndex(0, 1));
      this.m02 = â˜ƒ.get(bufferIndex(0, 2));
      this.m10 = â˜ƒ.get(bufferIndex(1, 0));
      this.m11 = â˜ƒ.get(bufferIndex(1, 1));
      this.m12 = â˜ƒ.get(bufferIndex(1, 2));
      this.m20 = â˜ƒ.get(bufferIndex(2, 0));
      this.m21 = â˜ƒ.get(bufferIndex(2, 1));
      this.m22 = â˜ƒ.get(bufferIndex(2, 2));
   }

   public void loadTransposed(FloatBuffer var1) {
      this.m00 = â˜ƒ.get(bufferIndex(0, 0));
      this.m01 = â˜ƒ.get(bufferIndex(1, 0));
      this.m02 = â˜ƒ.get(bufferIndex(2, 0));
      this.m10 = â˜ƒ.get(bufferIndex(0, 1));
      this.m11 = â˜ƒ.get(bufferIndex(1, 1));
      this.m12 = â˜ƒ.get(bufferIndex(2, 1));
      this.m20 = â˜ƒ.get(bufferIndex(0, 2));
      this.m21 = â˜ƒ.get(bufferIndex(1, 2));
      this.m22 = â˜ƒ.get(bufferIndex(2, 2));
   }

   public void load(FloatBuffer var1, boolean var2) {
      if (â˜ƒ) {
         this.loadTransposed(â˜ƒ);
      } else {
         this.load(â˜ƒ);
      }
   }

   public void load(Matrix3f var1) {
      this.m00 = â˜ƒ.m00;
      this.m01 = â˜ƒ.m01;
      this.m02 = â˜ƒ.m02;
      this.m10 = â˜ƒ.m10;
      this.m11 = â˜ƒ.m11;
      this.m12 = â˜ƒ.m12;
      this.m20 = â˜ƒ.m20;
      this.m21 = â˜ƒ.m21;
      this.m22 = â˜ƒ.m22;
   }

   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder();
      â˜ƒ.append("Matrix3f:\n");
      â˜ƒ.append(this.m00);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m01);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m02);
      â˜ƒ.append("\n");
      â˜ƒ.append(this.m10);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m11);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m12);
      â˜ƒ.append("\n");
      â˜ƒ.append(this.m20);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m21);
      â˜ƒ.append(" ");
      â˜ƒ.append(this.m22);
      â˜ƒ.append("\n");
      return â˜ƒ.toString();
   }

   public void store(FloatBuffer var1) {
      â˜ƒ.put(bufferIndex(0, 0), this.m00);
      â˜ƒ.put(bufferIndex(0, 1), this.m01);
      â˜ƒ.put(bufferIndex(0, 2), this.m02);
      â˜ƒ.put(bufferIndex(1, 0), this.m10);
      â˜ƒ.put(bufferIndex(1, 1), this.m11);
      â˜ƒ.put(bufferIndex(1, 2), this.m12);
      â˜ƒ.put(bufferIndex(2, 0), this.m20);
      â˜ƒ.put(bufferIndex(2, 1), this.m21);
      â˜ƒ.put(bufferIndex(2, 2), this.m22);
   }

   public void storeTransposed(FloatBuffer var1) {
      â˜ƒ.put(bufferIndex(0, 0), this.m00);
      â˜ƒ.put(bufferIndex(1, 0), this.m01);
      â˜ƒ.put(bufferIndex(2, 0), this.m02);
      â˜ƒ.put(bufferIndex(0, 1), this.m10);
      â˜ƒ.put(bufferIndex(1, 1), this.m11);
      â˜ƒ.put(bufferIndex(2, 1), this.m12);
      â˜ƒ.put(bufferIndex(0, 2), this.m20);
      â˜ƒ.put(bufferIndex(1, 2), this.m21);
      â˜ƒ.put(bufferIndex(2, 2), this.m22);
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
      this.m10 = 0.0F;
      this.m11 = 1.0F;
      this.m12 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 1.0F;
   }

   public float adjugateAndDet() {
      float â˜ƒ = this.m11 * this.m22 - this.m12 * this.m21;
      float â˜ƒx = -(this.m10 * this.m22 - this.m12 * this.m20);
      float â˜ƒxx = this.m10 * this.m21 - this.m11 * this.m20;
      float â˜ƒxxx = -(this.m01 * this.m22 - this.m02 * this.m21);
      float â˜ƒxxxx = this.m00 * this.m22 - this.m02 * this.m20;
      float â˜ƒxxxxx = -(this.m00 * this.m21 - this.m01 * this.m20);
      float â˜ƒxxxxxx = this.m01 * this.m12 - this.m02 * this.m11;
      float â˜ƒxxxxxxx = -(this.m00 * this.m12 - this.m02 * this.m10);
      float â˜ƒxxxxxxxx = this.m00 * this.m11 - this.m01 * this.m10;
      float â˜ƒxxxxxxxxx = this.m00 * â˜ƒ + this.m01 * â˜ƒx + this.m02 * â˜ƒxx;
      this.m00 = â˜ƒ;
      this.m10 = â˜ƒx;
      this.m20 = â˜ƒxx;
      this.m01 = â˜ƒxxx;
      this.m11 = â˜ƒxxxx;
      this.m21 = â˜ƒxxxxx;
      this.m02 = â˜ƒxxxxxx;
      this.m12 = â˜ƒxxxxxxx;
      this.m22 = â˜ƒxxxxxxxx;
      return â˜ƒxxxxxxxxx;
   }

   public float determinant() {
      float â˜ƒ = this.m11 * this.m22 - this.m12 * this.m21;
      float â˜ƒx = -(this.m10 * this.m22 - this.m12 * this.m20);
      float â˜ƒxx = this.m10 * this.m21 - this.m11 * this.m20;
      return this.m00 * â˜ƒ + this.m01 * â˜ƒx + this.m02 * â˜ƒxx;
   }

   public boolean invert() {
      float â˜ƒ = this.adjugateAndDet();
      if (Math.abs(â˜ƒ) > 1.0E-6F) {
         this.mul(â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   public void set(int var1, int var2, float var3) {
      if (â˜ƒ == 0) {
         if (â˜ƒ == 0) {
            this.m00 = â˜ƒ;
         } else if (â˜ƒ == 1) {
            this.m01 = â˜ƒ;
         } else {
            this.m02 = â˜ƒ;
         }
      } else if (â˜ƒ == 1) {
         if (â˜ƒ == 0) {
            this.m10 = â˜ƒ;
         } else if (â˜ƒ == 1) {
            this.m11 = â˜ƒ;
         } else {
            this.m12 = â˜ƒ;
         }
      } else if (â˜ƒ == 0) {
         this.m20 = â˜ƒ;
      } else if (â˜ƒ == 1) {
         this.m21 = â˜ƒ;
      } else {
         this.m22 = â˜ƒ;
      }
   }

   public void mul(Matrix3f var1) {
      float â˜ƒ = this.m00 * â˜ƒ.m00 + this.m01 * â˜ƒ.m10 + this.m02 * â˜ƒ.m20;
      float â˜ƒx = this.m00 * â˜ƒ.m01 + this.m01 * â˜ƒ.m11 + this.m02 * â˜ƒ.m21;
      float â˜ƒxx = this.m00 * â˜ƒ.m02 + this.m01 * â˜ƒ.m12 + this.m02 * â˜ƒ.m22;
      float â˜ƒxxx = this.m10 * â˜ƒ.m00 + this.m11 * â˜ƒ.m10 + this.m12 * â˜ƒ.m20;
      float â˜ƒxxxx = this.m10 * â˜ƒ.m01 + this.m11 * â˜ƒ.m11 + this.m12 * â˜ƒ.m21;
      float â˜ƒxxxxx = this.m10 * â˜ƒ.m02 + this.m11 * â˜ƒ.m12 + this.m12 * â˜ƒ.m22;
      float â˜ƒxxxxxx = this.m20 * â˜ƒ.m00 + this.m21 * â˜ƒ.m10 + this.m22 * â˜ƒ.m20;
      float â˜ƒxxxxxxx = this.m20 * â˜ƒ.m01 + this.m21 * â˜ƒ.m11 + this.m22 * â˜ƒ.m21;
      float â˜ƒxxxxxxxx = this.m20 * â˜ƒ.m02 + this.m21 * â˜ƒ.m12 + this.m22 * â˜ƒ.m22;
      this.m00 = â˜ƒ;
      this.m01 = â˜ƒx;
      this.m02 = â˜ƒxx;
      this.m10 = â˜ƒxxx;
      this.m11 = â˜ƒxxxx;
      this.m12 = â˜ƒxxxxx;
      this.m20 = â˜ƒxxxxxx;
      this.m21 = â˜ƒxxxxxxx;
      this.m22 = â˜ƒxxxxxxxx;
   }

   public void mul(Quaternion var1) {
      this.mul(new Matrix3f(â˜ƒ));
   }

   public void mul(float var1) {
      this.m00 *= â˜ƒ;
      this.m01 *= â˜ƒ;
      this.m02 *= â˜ƒ;
      this.m10 *= â˜ƒ;
      this.m11 *= â˜ƒ;
      this.m12 *= â˜ƒ;
      this.m20 *= â˜ƒ;
      this.m21 *= â˜ƒ;
      this.m22 *= â˜ƒ;
   }

   public void add(Matrix3f var1) {
      this.m00 += â˜ƒ.m00;
      this.m01 += â˜ƒ.m01;
      this.m02 += â˜ƒ.m02;
      this.m10 += â˜ƒ.m10;
      this.m11 += â˜ƒ.m11;
      this.m12 += â˜ƒ.m12;
      this.m20 += â˜ƒ.m20;
      this.m21 += â˜ƒ.m21;
      this.m22 += â˜ƒ.m22;
   }

   public void sub(Matrix3f var1) {
      this.m00 -= â˜ƒ.m00;
      this.m01 -= â˜ƒ.m01;
      this.m02 -= â˜ƒ.m02;
      this.m10 -= â˜ƒ.m10;
      this.m11 -= â˜ƒ.m11;
      this.m12 -= â˜ƒ.m12;
      this.m20 -= â˜ƒ.m20;
      this.m21 -= â˜ƒ.m21;
      this.m22 -= â˜ƒ.m22;
   }

   public float trace() {
      return this.m00 + this.m11 + this.m22;
   }

   public Matrix3f copy() {
      return new Matrix3f(this);
   }
}
