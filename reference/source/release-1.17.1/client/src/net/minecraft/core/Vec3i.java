package net.minecraft.core;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.Util;
import net.minecraft.util.Mth;

@Immutable
public class Vec3i implements Comparable<Vec3i> {
   public static final Codec<Vec3i> CODEC = Codec.INT_STREAM
      .comapFlatMap(
         var0 -> Util.fixedSize(var0, 3).map(var0x -> new Vec3i(var0x[0], var0x[1], var0x[2])),
         var0 -> IntStream.of(new int[]{var0.getX(), var0.getY(), var0.getZ()})
      );
   public static final Vec3i ZERO = new Vec3i(0, 0, 0);
   private int x;
   private int y;
   private int z;

   public Vec3i(int var1, int var2, int var3) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   public Vec3i(double var1, double var3, double var5) {
      this(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ), Mth.floor(â˜ƒ));
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Vec3i)) {
         return false;
      } else {
         Vec3i â˜ƒ = (Vec3i)â˜ƒ;
         if (this.getX() != â˜ƒ.getX()) {
            return false;
         } else if (this.getY() != â˜ƒ.getY()) {
            return false;
         } else {
            return this.getZ() == â˜ƒ.getZ();
         }
      }
   }

   public int hashCode() {
      return (this.getY() + this.getZ() * 31) * 31 + this.getX();
   }

   public int compareTo(Vec3i var1) {
      if (this.getY() == â˜ƒ.getY()) {
         return this.getZ() == â˜ƒ.getZ() ? this.getX() - â˜ƒ.getX() : this.getZ() - â˜ƒ.getZ();
      } else {
         return this.getY() - â˜ƒ.getY();
      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   protected Vec3i setX(int var1) {
      this.x = â˜ƒ;
      return this;
   }

   protected Vec3i setY(int var1) {
      this.y = â˜ƒ;
      return this;
   }

   protected Vec3i setZ(int var1) {
      this.z = â˜ƒ;
      return this;
   }

   public Vec3i offset(double var1, double var3, double var5) {
      return â˜ƒ == 0.0 && â˜ƒ == 0.0 && â˜ƒ == 0.0 ? this : new Vec3i((double)this.getX() + â˜ƒ, (double)this.getY() + â˜ƒ, (double)this.getZ() + â˜ƒ);
   }

   public Vec3i offset(int var1, int var2, int var3) {
      return â˜ƒ == 0 && â˜ƒ == 0 && â˜ƒ == 0 ? this : new Vec3i(this.getX() + â˜ƒ, this.getY() + â˜ƒ, this.getZ() + â˜ƒ);
   }

   public Vec3i offset(Vec3i var1) {
      return this.offset(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public Vec3i subtract(Vec3i var1) {
      return this.offset(-â˜ƒ.getX(), -â˜ƒ.getY(), -â˜ƒ.getZ());
   }

   public Vec3i multiply(int var1) {
      if (â˜ƒ == 1) {
         return this;
      } else {
         return â˜ƒ == 0 ? ZERO : new Vec3i(this.getX() * â˜ƒ, this.getY() * â˜ƒ, this.getZ() * â˜ƒ);
      }
   }

   public Vec3i above() {
      return this.above(1);
   }

   public Vec3i above(int var1) {
      return this.relative(Direction.UP, â˜ƒ);
   }

   public Vec3i below() {
      return this.below(1);
   }

   public Vec3i below(int var1) {
      return this.relative(Direction.DOWN, â˜ƒ);
   }

   public Vec3i north() {
      return this.north(1);
   }

   public Vec3i north(int var1) {
      return this.relative(Direction.NORTH, â˜ƒ);
   }

   public Vec3i south() {
      return this.south(1);
   }

   public Vec3i south(int var1) {
      return this.relative(Direction.SOUTH, â˜ƒ);
   }

   public Vec3i west() {
      return this.west(1);
   }

   public Vec3i west(int var1) {
      return this.relative(Direction.WEST, â˜ƒ);
   }

   public Vec3i east() {
      return this.east(1);
   }

   public Vec3i east(int var1) {
      return this.relative(Direction.EAST, â˜ƒ);
   }

   public Vec3i relative(Direction var1) {
      return this.relative(â˜ƒ, 1);
   }

   public Vec3i relative(Direction var1, int var2) {
      return â˜ƒ == 0 ? this : new Vec3i(this.getX() + â˜ƒ.getStepX() * â˜ƒ, this.getY() + â˜ƒ.getStepY() * â˜ƒ, this.getZ() + â˜ƒ.getStepZ() * â˜ƒ);
   }

   public Vec3i relative(Direction.Axis var1, int var2) {
      if (â˜ƒ == 0) {
         return this;
      } else {
         int â˜ƒ = â˜ƒ == Direction.Axis.X ? â˜ƒ : 0;
         int â˜ƒx = â˜ƒ == Direction.Axis.Y ? â˜ƒ : 0;
         int â˜ƒxx = â˜ƒ == Direction.Axis.Z ? â˜ƒ : 0;
         return new Vec3i(this.getX() + â˜ƒ, this.getY() + â˜ƒx, this.getZ() + â˜ƒxx);
      }
   }

   public Vec3i cross(Vec3i var1) {
      return new Vec3i(
         this.getY() * â˜ƒ.getZ() - this.getZ() * â˜ƒ.getY(),
         this.getZ() * â˜ƒ.getX() - this.getX() * â˜ƒ.getZ(),
         this.getX() * â˜ƒ.getY() - this.getY() * â˜ƒ.getX()
      );
   }

   public boolean closerThan(Vec3i var1, double var2) {
      return this.distSqr((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), false) < â˜ƒ * â˜ƒ;
   }

   public boolean closerThan(Position var1, double var2) {
      return this.distSqr(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z(), true) < â˜ƒ * â˜ƒ;
   }

   public double distSqr(Vec3i var1) {
      return this.distSqr((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), true);
   }

   public double distSqr(Position var1, boolean var2) {
      return this.distSqr(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z(), â˜ƒ);
   }

   public double distSqr(Vec3i var1, boolean var2) {
      return this.distSqr((double)â˜ƒ.x, (double)â˜ƒ.y, (double)â˜ƒ.z, â˜ƒ);
   }

   public double distSqr(double var1, double var3, double var5, boolean var7) {
      double â˜ƒ = â˜ƒ ? 0.5 : 0.0;
      double â˜ƒx = (double)this.getX() + â˜ƒ - â˜ƒ;
      double â˜ƒxx = (double)this.getY() + â˜ƒ - â˜ƒ;
      double â˜ƒxxx = (double)this.getZ() + â˜ƒ - â˜ƒ;
      return â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx;
   }

   public int distManhattan(Vec3i var1) {
      float â˜ƒ = (float)Math.abs(â˜ƒ.getX() - this.getX());
      float â˜ƒx = (float)Math.abs(â˜ƒ.getY() - this.getY());
      float â˜ƒxx = (float)Math.abs(â˜ƒ.getZ() - this.getZ());
      return (int)(â˜ƒ + â˜ƒx + â˜ƒxx);
   }

   public int get(Direction.Axis var1) {
      return â˜ƒ.choose(this.x, this.y, this.z);
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
   }

   public String toShortString() {
      return this.getX() + ", " + this.getY() + ", " + this.getZ();
   }
}
