package net.minecraft.util.math;

import com.google.common.base.MoreObjects;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.EnumFacing;

public class MutableBoundingBox {
   public int field_78897_a;
   public int field_78895_b;
   public int field_78896_c;
   public int field_78893_d;
   public int field_78894_e;
   public int field_78892_f;

   public MutableBoundingBox() {
   }

   public MutableBoundingBox(int[] var1) {
      if (☃.length == 6) {
         this.field_78897_a = ☃[0];
         this.field_78895_b = ☃[1];
         this.field_78896_c = ☃[2];
         this.field_78893_d = ☃[3];
         this.field_78894_e = ☃[4];
         this.field_78892_f = ☃[5];
      }
   }

   public static MutableBoundingBox func_78887_a() {
      return new MutableBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
   }

   public static MutableBoundingBox func_175897_a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, EnumFacing var9) {
      switch(☃) {
         case NORTH:
            return new MutableBoundingBox(☃ + ☃, ☃ + ☃, ☃ - ☃ + 1 + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃);
         case SOUTH:
            return new MutableBoundingBox(☃ + ☃, ☃ + ☃, ☃ + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃);
         case WEST:
            return new MutableBoundingBox(☃ - ☃ + 1 + ☃, ☃ + ☃, ☃ + ☃, ☃ + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃);
         case EAST:
            return new MutableBoundingBox(☃ + ☃, ☃ + ☃, ☃ + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃);
         default:
            return new MutableBoundingBox(☃ + ☃, ☃ + ☃, ☃ + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃, ☃ + ☃ - 1 + ☃);
      }
   }

   public static MutableBoundingBox func_175899_a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return new MutableBoundingBox(Math.min(☃, ☃), Math.min(☃, ☃), Math.min(☃, ☃), Math.max(☃, ☃), Math.max(☃, ☃), Math.max(☃, ☃));
   }

   public MutableBoundingBox(MutableBoundingBox var1) {
      this.field_78897_a = ☃.field_78897_a;
      this.field_78895_b = ☃.field_78895_b;
      this.field_78896_c = ☃.field_78896_c;
      this.field_78893_d = ☃.field_78893_d;
      this.field_78894_e = ☃.field_78894_e;
      this.field_78892_f = ☃.field_78892_f;
   }

   public MutableBoundingBox(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_78897_a = ☃;
      this.field_78895_b = ☃;
      this.field_78896_c = ☃;
      this.field_78893_d = ☃;
      this.field_78894_e = ☃;
      this.field_78892_f = ☃;
   }

   public MutableBoundingBox(Vec3i var1, Vec3i var2) {
      this.field_78897_a = Math.min(☃.func_177958_n(), ☃.func_177958_n());
      this.field_78895_b = Math.min(☃.func_177956_o(), ☃.func_177956_o());
      this.field_78896_c = Math.min(☃.func_177952_p(), ☃.func_177952_p());
      this.field_78893_d = Math.max(☃.func_177958_n(), ☃.func_177958_n());
      this.field_78894_e = Math.max(☃.func_177956_o(), ☃.func_177956_o());
      this.field_78892_f = Math.max(☃.func_177952_p(), ☃.func_177952_p());
   }

   public MutableBoundingBox(int var1, int var2, int var3, int var4) {
      this.field_78897_a = ☃;
      this.field_78896_c = ☃;
      this.field_78893_d = ☃;
      this.field_78892_f = ☃;
      this.field_78895_b = 1;
      this.field_78894_e = 512;
   }

   public boolean func_78884_a(MutableBoundingBox var1) {
      return this.field_78893_d >= ☃.field_78897_a
         && this.field_78897_a <= ☃.field_78893_d
         && this.field_78892_f >= ☃.field_78896_c
         && this.field_78896_c <= ☃.field_78892_f
         && this.field_78894_e >= ☃.field_78895_b
         && this.field_78895_b <= ☃.field_78894_e;
   }

   public boolean func_78885_a(int var1, int var2, int var3, int var4) {
      return this.field_78893_d >= ☃ && this.field_78897_a <= ☃ && this.field_78892_f >= ☃ && this.field_78896_c <= ☃;
   }

   public void func_78888_b(MutableBoundingBox var1) {
      this.field_78897_a = Math.min(this.field_78897_a, ☃.field_78897_a);
      this.field_78895_b = Math.min(this.field_78895_b, ☃.field_78895_b);
      this.field_78896_c = Math.min(this.field_78896_c, ☃.field_78896_c);
      this.field_78893_d = Math.max(this.field_78893_d, ☃.field_78893_d);
      this.field_78894_e = Math.max(this.field_78894_e, ☃.field_78894_e);
      this.field_78892_f = Math.max(this.field_78892_f, ☃.field_78892_f);
   }

   public void func_78886_a(int var1, int var2, int var3) {
      this.field_78897_a += ☃;
      this.field_78895_b += ☃;
      this.field_78896_c += ☃;
      this.field_78893_d += ☃;
      this.field_78894_e += ☃;
      this.field_78892_f += ☃;
   }

   public boolean func_175898_b(Vec3i var1) {
      return ☃.func_177958_n() >= this.field_78897_a
         && ☃.func_177958_n() <= this.field_78893_d
         && ☃.func_177952_p() >= this.field_78896_c
         && ☃.func_177952_p() <= this.field_78892_f
         && ☃.func_177956_o() >= this.field_78895_b
         && ☃.func_177956_o() <= this.field_78894_e;
   }

   public Vec3i func_175896_b() {
      return new Vec3i(this.field_78893_d - this.field_78897_a, this.field_78894_e - this.field_78895_b, this.field_78892_f - this.field_78896_c);
   }

   public int func_78883_b() {
      return this.field_78893_d - this.field_78897_a + 1;
   }

   public int func_78882_c() {
      return this.field_78894_e - this.field_78895_b + 1;
   }

   public int func_78880_d() {
      return this.field_78892_f - this.field_78896_c + 1;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("x0", this.field_78897_a)
         .add("y0", this.field_78895_b)
         .add("z0", this.field_78896_c)
         .add("x1", this.field_78893_d)
         .add("y1", this.field_78894_e)
         .add("z1", this.field_78892_f)
         .toString();
   }

   public NBTTagIntArray func_151535_h() {
      return new NBTTagIntArray(
         new int[]{this.field_78897_a, this.field_78895_b, this.field_78896_c, this.field_78893_d, this.field_78894_e, this.field_78892_f}
      );
   }
}
