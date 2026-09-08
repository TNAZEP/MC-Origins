package net.minecraft.pathfinding;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;

public class PathPoint {
   public final int field_75839_a;
   public final int field_75837_b;
   public final int field_75838_c;
   private final int field_75840_j;
   public int field_75835_d = -1;
   public float field_75836_e;
   public float field_75833_f;
   public float field_75834_g;
   public PathPoint field_75841_h;
   public boolean field_75842_i;
   public float field_186284_j;
   public float field_186285_k;
   public float field_186286_l;
   public PathNodeType field_186287_m = PathNodeType.BLOCKED;

   public PathPoint(int var1, int var2, int var3) {
      this.field_75839_a = ☃;
      this.field_75837_b = ☃;
      this.field_75838_c = ☃;
      this.field_75840_j = func_75830_a(☃, ☃, ☃);
   }

   public PathPoint func_186283_a(int var1, int var2, int var3) {
      PathPoint ☃ = new PathPoint(☃, ☃, ☃);
      ☃.field_75835_d = this.field_75835_d;
      ☃.field_75836_e = this.field_75836_e;
      ☃.field_75833_f = this.field_75833_f;
      ☃.field_75834_g = this.field_75834_g;
      ☃.field_75841_h = this.field_75841_h;
      ☃.field_75842_i = this.field_75842_i;
      ☃.field_186284_j = this.field_186284_j;
      ☃.field_186285_k = this.field_186285_k;
      ☃.field_186286_l = this.field_186286_l;
      ☃.field_186287_m = this.field_186287_m;
      return ☃;
   }

   public static int func_75830_a(int var0, int var1, int var2) {
      return ☃ & 0xFF | (☃ & 32767) << 8 | (☃ & 32767) << 24 | (☃ < 0 ? Integer.MIN_VALUE : 0) | (☃ < 0 ? 32768 : 0);
   }

   public float func_75829_a(PathPoint var1) {
      float ☃ = (float)(☃.field_75839_a - this.field_75839_a);
      float ☃x = (float)(☃.field_75837_b - this.field_75837_b);
      float ☃xx = (float)(☃.field_75838_c - this.field_75838_c);
      return MathHelper.func_76129_c(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
   }

   public float func_75832_b(PathPoint var1) {
      float ☃ = (float)(☃.field_75839_a - this.field_75839_a);
      float ☃x = (float)(☃.field_75837_b - this.field_75837_b);
      float ☃xx = (float)(☃.field_75838_c - this.field_75838_c);
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public float func_186281_c(PathPoint var1) {
      float ☃ = (float)Math.abs(☃.field_75839_a - this.field_75839_a);
      float ☃x = (float)Math.abs(☃.field_75837_b - this.field_75837_b);
      float ☃xx = (float)Math.abs(☃.field_75838_c - this.field_75838_c);
      return ☃ + ☃x + ☃xx;
   }

   public boolean equals(Object var1) {
      if (!(☃ instanceof PathPoint)) {
         return false;
      } else {
         PathPoint ☃ = (PathPoint)☃;
         return this.field_75840_j == ☃.field_75840_j
            && this.field_75839_a == ☃.field_75839_a
            && this.field_75837_b == ☃.field_75837_b
            && this.field_75838_c == ☃.field_75838_c;
      }
   }

   public int hashCode() {
      return this.field_75840_j;
   }

   public boolean func_75831_a() {
      return this.field_75835_d >= 0;
   }

   public String toString() {
      return this.field_75839_a + ", " + this.field_75837_b + ", " + this.field_75838_c;
   }

   public static PathPoint func_186282_b(PacketBuffer var0) {
      PathPoint ☃ = new PathPoint(☃.readInt(), ☃.readInt(), ☃.readInt());
      ☃.field_186284_j = ☃.readFloat();
      ☃.field_186285_k = ☃.readFloat();
      ☃.field_186286_l = ☃.readFloat();
      ☃.field_75842_i = ☃.readBoolean();
      ☃.field_186287_m = PathNodeType.values()[☃.readInt()];
      ☃.field_75834_g = ☃.readFloat();
      return ☃;
   }
}
