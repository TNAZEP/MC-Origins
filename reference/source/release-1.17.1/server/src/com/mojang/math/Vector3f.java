package com.mojang.math;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class Vector3f {
   public static final Codec<Vector3f> CODEC = Codec.FLOAT
      .listOf()
      .comapFlatMap(
         var0 -> Util.fixedSize(var0, 3).map(var0x -> new Vector3f(var0x.get(0), var0x.get(1), var0x.get(2))), var0 -> ImmutableList.of(var0.x, var0.y, var0.z)
      );
   public static Vector3f XN = new Vector3f(-1.0F, 0.0F, 0.0F);
   public static Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
   public static Vector3f YN = new Vector3f(0.0F, -1.0F, 0.0F);
   public static Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
   public static Vector3f ZN = new Vector3f(0.0F, 0.0F, -1.0F);
   public static Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);
   public static Vector3f ZERO = new Vector3f(0.0F, 0.0F, 0.0F);
   private float x;
   private float y;
   private float z;

   public Vector3f() {
   }

   public Vector3f(float var1, float var2, float var3) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   public Vector3f(Vector4f var1) {
      this(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }

   public Vector3f(Vec3 var1) {
      this((float)â˜ƒ.x, (float)â˜ƒ.y, (float)â˜ƒ.z);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Vector3f â˜ƒ = (Vector3f)â˜ƒ;
         if (Float.compare(â˜ƒ.x, this.x) != 0) {
            return false;
         } else if (Float.compare(â˜ƒ.y, this.y) != 0) {
            return false;
         } else {
            return Float.compare(â˜ƒ.z, this.z) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = Float.floatToIntBits(this.x);
      â˜ƒ = 31 * â˜ƒ + Float.floatToIntBits(this.y);
      return 31 * â˜ƒ + Float.floatToIntBits(this.z);
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

   public void mul(float var1) {
      this.x *= â˜ƒ;
      this.y *= â˜ƒ;
      this.z *= â˜ƒ;
   }

   public void mul(float var1, float var2, float var3) {
      this.x *= â˜ƒ;
      this.y *= â˜ƒ;
      this.z *= â˜ƒ;
   }

   public void clamp(Vector3f var1, Vector3f var2) {
      this.x = Mth.clamp(this.x, â˜ƒ.x(), â˜ƒ.x());
      this.y = Mth.clamp(this.y, â˜ƒ.x(), â˜ƒ.y());
      this.z = Mth.clamp(this.z, â˜ƒ.z(), â˜ƒ.z());
   }

   public void clamp(float var1, float var2) {
      this.x = Mth.clamp(this.x, â˜ƒ, â˜ƒ);
      this.y = Mth.clamp(this.y, â˜ƒ, â˜ƒ);
      this.z = Mth.clamp(this.z, â˜ƒ, â˜ƒ);
   }

   public void set(float var1, float var2, float var3) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   public void load(Vector3f var1) {
      this.x = â˜ƒ.x;
      this.y = â˜ƒ.y;
      this.z = â˜ƒ.z;
   }

   public void add(float var1, float var2, float var3) {
      this.x += â˜ƒ;
      this.y += â˜ƒ;
      this.z += â˜ƒ;
   }

   public void add(Vector3f var1) {
      this.x += â˜ƒ.x;
      this.y += â˜ƒ.y;
      this.z += â˜ƒ.z;
   }

   public void sub(Vector3f var1) {
      this.x -= â˜ƒ.x;
      this.y -= â˜ƒ.y;
      this.z -= â˜ƒ.z;
   }

   public float dot(Vector3f var1) {
      return this.x * â˜ƒ.x + this.y * â˜ƒ.y + this.z * â˜ƒ.z;
   }

   public boolean normalize() {
      float â˜ƒ = this.x * this.x + this.y * this.y + this.z * this.z;
      if ((double)â˜ƒ < 1.0E-5) {
         return false;
      } else {
         float â˜ƒ = Mth.fastInvSqrt(â˜ƒ);
         this.x *= â˜ƒ;
         this.y *= â˜ƒ;
         this.z *= â˜ƒ;
         return true;
      }
   }

   public void cross(Vector3f var1) {
      float â˜ƒ = this.x;
      float â˜ƒx = this.y;
      float â˜ƒxx = this.z;
      float â˜ƒxxx = â˜ƒ.x();
      float â˜ƒxxxx = â˜ƒ.y();
      float â˜ƒxxxxx = â˜ƒ.z();
      this.x = â˜ƒx * â˜ƒxxxxx - â˜ƒxx * â˜ƒxxxx;
      this.y = â˜ƒxx * â˜ƒxxx - â˜ƒ * â˜ƒxxxxx;
      this.z = â˜ƒ * â˜ƒxxxx - â˜ƒx * â˜ƒxxx;
   }

   public void transform(Matrix3f var1) {
      float â˜ƒ = this.x;
      float â˜ƒx = this.y;
      float â˜ƒxx = this.z;
      this.x = â˜ƒ.m00 * â˜ƒ + â˜ƒ.m01 * â˜ƒx + â˜ƒ.m02 * â˜ƒxx;
      this.y = â˜ƒ.m10 * â˜ƒ + â˜ƒ.m11 * â˜ƒx + â˜ƒ.m12 * â˜ƒxx;
      this.z = â˜ƒ.m20 * â˜ƒ + â˜ƒ.m21 * â˜ƒx + â˜ƒ.m22 * â˜ƒxx;
   }

   public void transform(Quaternion var1) {
      Quaternion â˜ƒ = new Quaternion(â˜ƒ);
      â˜ƒ.mul(new Quaternion(this.x(), this.y(), this.z(), 0.0F));
      Quaternion â˜ƒx = new Quaternion(â˜ƒ);
      â˜ƒx.conj();
      â˜ƒ.mul(â˜ƒx);
      this.set(â˜ƒ.i(), â˜ƒ.j(), â˜ƒ.k());
   }

   public void lerp(Vector3f var1, float var2) {
      float â˜ƒ = 1.0F - â˜ƒ;
      this.x = this.x * â˜ƒ + â˜ƒ.x * â˜ƒ;
      this.y = this.y * â˜ƒ + â˜ƒ.y * â˜ƒ;
      this.z = this.z * â˜ƒ + â˜ƒ.z * â˜ƒ;
   }

   public Quaternion rotation(float var1) {
      return new Quaternion(this, â˜ƒ, false);
   }

   public Quaternion rotationDegrees(float var1) {
      return new Quaternion(this, â˜ƒ, true);
   }

   public Vector3f copy() {
      return new Vector3f(this.x, this.y, this.z);
   }

   public void map(Float2FloatFunction var1) {
      this.x = â˜ƒ.get(this.x);
      this.y = â˜ƒ.get(this.y);
      this.z = â˜ƒ.get(this.z);
   }

   public String toString() {
      return "[" + this.x + ", " + this.y + ", " + this.z + "]";
   }
}
