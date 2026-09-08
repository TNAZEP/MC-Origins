package net.minecraft.world.phys;

import com.mojang.math.Vector3f;
import java.util.EnumSet;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;

public class Vec3 implements Position {
   public static final Vec3 ZERO = new Vec3(0.0, 0.0, 0.0);
   public final double x;
   public final double y;
   public final double z;

   public static Vec3 fromRGB24(int var0) {
      double â˜ƒ = (double)(â˜ƒ >> 16 & 0xFF) / 255.0;
      double â˜ƒx = (double)(â˜ƒ >> 8 & 0xFF) / 255.0;
      double â˜ƒxx = (double)(â˜ƒ & 0xFF) / 255.0;
      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public static Vec3 atCenterOf(Vec3i var0) {
      return new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5);
   }

   public static Vec3 atLowerCornerOf(Vec3i var0) {
      return new Vec3((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
   }

   public static Vec3 atBottomCenterOf(Vec3i var0) {
      return new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5);
   }

   public static Vec3 upFromBottomCenterOf(Vec3i var0, double var1) {
      return new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + â˜ƒ, (double)â˜ƒ.getZ() + 0.5);
   }

   public Vec3(double var1, double var3, double var5) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   public Vec3(Vector3f var1) {
      this((double)â˜ƒ.x(), (double)â˜ƒ.y(), (double)â˜ƒ.z());
   }

   public Vec3 vectorTo(Vec3 var1) {
      return new Vec3(â˜ƒ.x - this.x, â˜ƒ.y - this.y, â˜ƒ.z - this.z);
   }

   public Vec3 normalize() {
      double â˜ƒ = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
      return â˜ƒ < 1.0E-4 ? ZERO : new Vec3(this.x / â˜ƒ, this.y / â˜ƒ, this.z / â˜ƒ);
   }

   public double dot(Vec3 var1) {
      return this.x * â˜ƒ.x + this.y * â˜ƒ.y + this.z * â˜ƒ.z;
   }

   public Vec3 cross(Vec3 var1) {
      return new Vec3(this.y * â˜ƒ.z - this.z * â˜ƒ.y, this.z * â˜ƒ.x - this.x * â˜ƒ.z, this.x * â˜ƒ.y - this.y * â˜ƒ.x);
   }

   public Vec3 subtract(Vec3 var1) {
      return this.subtract(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public Vec3 subtract(double var1, double var3, double var5) {
      return this.add(-â˜ƒ, -â˜ƒ, -â˜ƒ);
   }

   public Vec3 add(Vec3 var1) {
      return this.add(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public Vec3 add(double var1, double var3, double var5) {
      return new Vec3(this.x + â˜ƒ, this.y + â˜ƒ, this.z + â˜ƒ);
   }

   public boolean closerThan(Position var1, double var2) {
      return this.distanceToSqr(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()) < â˜ƒ * â˜ƒ;
   }

   public double distanceTo(Vec3 var1) {
      double â˜ƒ = â˜ƒ.x - this.x;
      double â˜ƒx = â˜ƒ.y - this.y;
      double â˜ƒxx = â˜ƒ.z - this.z;
      return Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
   }

   public double distanceToSqr(Vec3 var1) {
      double â˜ƒ = â˜ƒ.x - this.x;
      double â˜ƒx = â˜ƒ.y - this.y;
      double â˜ƒxx = â˜ƒ.z - this.z;
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public double distanceToSqr(double var1, double var3, double var5) {
      double â˜ƒ = â˜ƒ - this.x;
      double â˜ƒx = â˜ƒ - this.y;
      double â˜ƒxx = â˜ƒ - this.z;
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public Vec3 scale(double var1) {
      return this.multiply(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Vec3 reverse() {
      return this.scale(-1.0);
   }

   public Vec3 multiply(Vec3 var1) {
      return this.multiply(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public Vec3 multiply(double var1, double var3, double var5) {
      return new Vec3(this.x * â˜ƒ, this.y * â˜ƒ, this.z * â˜ƒ);
   }

   public double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   public double lengthSqr() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public double horizontalDistance() {
      return Math.sqrt(this.x * this.x + this.z * this.z);
   }

   public double horizontalDistanceSqr() {
      return this.x * this.x + this.z * this.z;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Vec3)) {
         return false;
      } else {
         Vec3 â˜ƒ = (Vec3)â˜ƒ;
         if (Double.compare(â˜ƒ.x, this.x) != 0) {
            return false;
         } else if (Double.compare(â˜ƒ.y, this.y) != 0) {
            return false;
         } else {
            return Double.compare(â˜ƒ.z, this.z) == 0;
         }
      }
   }

   public int hashCode() {
      long â˜ƒ = Double.doubleToLongBits(this.x);
      int â˜ƒx = (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.y);
      â˜ƒx = 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.z);
      return 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
   }

   public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ")";
   }

   public Vec3 lerp(Vec3 var1, double var2) {
      return new Vec3(Mth.lerp(â˜ƒ, this.x, â˜ƒ.x), Mth.lerp(â˜ƒ, this.y, â˜ƒ.y), Mth.lerp(â˜ƒ, this.z, â˜ƒ.z));
   }

   public Vec3 xRot(float var1) {
      float â˜ƒ = Mth.cos(â˜ƒ);
      float â˜ƒx = Mth.sin(â˜ƒ);
      double â˜ƒxx = this.x;
      double â˜ƒxxx = this.y * (double)â˜ƒ + this.z * (double)â˜ƒx;
      double â˜ƒxxxx = this.z * (double)â˜ƒ - this.y * (double)â˜ƒx;
      return new Vec3(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   public Vec3 yRot(float var1) {
      float â˜ƒ = Mth.cos(â˜ƒ);
      float â˜ƒx = Mth.sin(â˜ƒ);
      double â˜ƒxx = this.x * (double)â˜ƒ + this.z * (double)â˜ƒx;
      double â˜ƒxxx = this.y;
      double â˜ƒxxxx = this.z * (double)â˜ƒ - this.x * (double)â˜ƒx;
      return new Vec3(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   public Vec3 zRot(float var1) {
      float â˜ƒ = Mth.cos(â˜ƒ);
      float â˜ƒx = Mth.sin(â˜ƒ);
      double â˜ƒxx = this.x * (double)â˜ƒ + this.y * (double)â˜ƒx;
      double â˜ƒxxx = this.y * (double)â˜ƒ - this.x * (double)â˜ƒx;
      double â˜ƒxxxx = this.z;
      return new Vec3(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   public static Vec3 directionFromRotation(Vec2 var0) {
      return directionFromRotation(â˜ƒ.x, â˜ƒ.y);
   }

   public static Vec3 directionFromRotation(float var0, float var1) {
      float â˜ƒ = Mth.cos(-â˜ƒ * (float) (Math.PI / 180.0) - (float) Math.PI);
      float â˜ƒx = Mth.sin(-â˜ƒ * (float) (Math.PI / 180.0) - (float) Math.PI);
      float â˜ƒxx = -Mth.cos(-â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒxxx = Mth.sin(-â˜ƒ * (float) (Math.PI / 180.0));
      return new Vec3((double)(â˜ƒx * â˜ƒxx), (double)â˜ƒxxx, (double)(â˜ƒ * â˜ƒxx));
   }

   public Vec3 align(EnumSet<Direction.Axis> var1) {
      double â˜ƒ = â˜ƒ.contains(Direction.Axis.X) ? (double)Mth.floor(this.x) : this.x;
      double â˜ƒx = â˜ƒ.contains(Direction.Axis.Y) ? (double)Mth.floor(this.y) : this.y;
      double â˜ƒxx = â˜ƒ.contains(Direction.Axis.Z) ? (double)Mth.floor(this.z) : this.z;
      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public double get(Direction.Axis var1) {
      return â˜ƒ.choose(this.x, this.y, this.z);
   }

   @Override
   public final double x() {
      return this.x;
   }

   @Override
   public final double y() {
      return this.y;
   }

   @Override
   public final double z() {
      return this.z;
   }
}
