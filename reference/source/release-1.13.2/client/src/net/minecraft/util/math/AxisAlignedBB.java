package net.minecraft.util.math;

import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;

public class AxisAlignedBB {
   public final double field_72340_a;
   public final double field_72338_b;
   public final double field_72339_c;
   public final double field_72336_d;
   public final double field_72337_e;
   public final double field_72334_f;

   public AxisAlignedBB(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.field_72340_a = Math.min(☃, ☃);
      this.field_72338_b = Math.min(☃, ☃);
      this.field_72339_c = Math.min(☃, ☃);
      this.field_72336_d = Math.max(☃, ☃);
      this.field_72337_e = Math.max(☃, ☃);
      this.field_72334_f = Math.max(☃, ☃);
   }

   public AxisAlignedBB(BlockPos var1) {
      this(
         (double)☃.func_177958_n(),
         (double)☃.func_177956_o(),
         (double)☃.func_177952_p(),
         (double)(☃.func_177958_n() + 1),
         (double)(☃.func_177956_o() + 1),
         (double)(☃.func_177952_p() + 1)
      );
   }

   public AxisAlignedBB(BlockPos var1, BlockPos var2) {
      this(
         (double)☃.func_177958_n(),
         (double)☃.func_177956_o(),
         (double)☃.func_177952_p(),
         (double)☃.func_177958_n(),
         (double)☃.func_177956_o(),
         (double)☃.func_177952_p()
      );
   }

   public AxisAlignedBB(Vec3d var1, Vec3d var2) {
      this(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public double func_197745_a(EnumFacing.Axis var1) {
      return ☃.func_196051_a(this.field_72340_a, this.field_72338_b, this.field_72339_c);
   }

   public double func_197742_b(EnumFacing.Axis var1) {
      return ☃.func_196051_a(this.field_72336_d, this.field_72337_e, this.field_72334_f);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof AxisAlignedBB)) {
         return false;
      } else {
         AxisAlignedBB ☃ = (AxisAlignedBB)☃;
         if (Double.compare(☃.field_72340_a, this.field_72340_a) != 0) {
            return false;
         } else if (Double.compare(☃.field_72338_b, this.field_72338_b) != 0) {
            return false;
         } else if (Double.compare(☃.field_72339_c, this.field_72339_c) != 0) {
            return false;
         } else if (Double.compare(☃.field_72336_d, this.field_72336_d) != 0) {
            return false;
         } else if (Double.compare(☃.field_72337_e, this.field_72337_e) != 0) {
            return false;
         } else {
            return Double.compare(☃.field_72334_f, this.field_72334_f) == 0;
         }
      }
   }

   public int hashCode() {
      long ☃ = Double.doubleToLongBits(this.field_72340_a);
      int ☃x = (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72338_b);
      ☃x = 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72339_c);
      ☃x = 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72336_d);
      ☃x = 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72337_e);
      ☃x = 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
      ☃ = Double.doubleToLongBits(this.field_72334_f);
      return 31 * ☃x + (int)(☃ ^ ☃ >>> 32);
   }

   public AxisAlignedBB func_191195_a(double var1, double var3, double var5) {
      double ☃ = this.field_72340_a;
      double ☃x = this.field_72338_b;
      double ☃xx = this.field_72339_c;
      double ☃xxx = this.field_72336_d;
      double ☃xxxx = this.field_72337_e;
      double ☃xxxxx = this.field_72334_f;
      if (☃ < 0.0) {
         ☃ -= ☃;
      } else if (☃ > 0.0) {
         ☃xxx -= ☃;
      }

      if (☃ < 0.0) {
         ☃x -= ☃;
      } else if (☃ > 0.0) {
         ☃xxxx -= ☃;
      }

      if (☃ < 0.0) {
         ☃xx -= ☃;
      } else if (☃ > 0.0) {
         ☃xxxxx -= ☃;
      }

      return new AxisAlignedBB(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
   }

   public AxisAlignedBB func_72321_a(double var1, double var3, double var5) {
      double ☃ = this.field_72340_a;
      double ☃x = this.field_72338_b;
      double ☃xx = this.field_72339_c;
      double ☃xxx = this.field_72336_d;
      double ☃xxxx = this.field_72337_e;
      double ☃xxxxx = this.field_72334_f;
      if (☃ < 0.0) {
         ☃ += ☃;
      } else if (☃ > 0.0) {
         ☃xxx += ☃;
      }

      if (☃ < 0.0) {
         ☃x += ☃;
      } else if (☃ > 0.0) {
         ☃xxxx += ☃;
      }

      if (☃ < 0.0) {
         ☃xx += ☃;
      } else if (☃ > 0.0) {
         ☃xxxxx += ☃;
      }

      return new AxisAlignedBB(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
   }

   public AxisAlignedBB func_72314_b(double var1, double var3, double var5) {
      double ☃ = this.field_72340_a - ☃;
      double ☃x = this.field_72338_b - ☃;
      double ☃xx = this.field_72339_c - ☃;
      double ☃xxx = this.field_72336_d + ☃;
      double ☃xxxx = this.field_72337_e + ☃;
      double ☃xxxxx = this.field_72334_f + ☃;
      return new AxisAlignedBB(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
   }

   public AxisAlignedBB func_186662_g(double var1) {
      return this.func_72314_b(☃, ☃, ☃);
   }

   public AxisAlignedBB func_191500_a(AxisAlignedBB var1) {
      double ☃ = Math.max(this.field_72340_a, ☃.field_72340_a);
      double ☃x = Math.max(this.field_72338_b, ☃.field_72338_b);
      double ☃xx = Math.max(this.field_72339_c, ☃.field_72339_c);
      double ☃xxx = Math.min(this.field_72336_d, ☃.field_72336_d);
      double ☃xxxx = Math.min(this.field_72337_e, ☃.field_72337_e);
      double ☃xxxxx = Math.min(this.field_72334_f, ☃.field_72334_f);
      return new AxisAlignedBB(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
   }

   public AxisAlignedBB func_111270_a(AxisAlignedBB var1) {
      double ☃ = Math.min(this.field_72340_a, ☃.field_72340_a);
      double ☃x = Math.min(this.field_72338_b, ☃.field_72338_b);
      double ☃xx = Math.min(this.field_72339_c, ☃.field_72339_c);
      double ☃xxx = Math.max(this.field_72336_d, ☃.field_72336_d);
      double ☃xxxx = Math.max(this.field_72337_e, ☃.field_72337_e);
      double ☃xxxxx = Math.max(this.field_72334_f, ☃.field_72334_f);
      return new AxisAlignedBB(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx);
   }

   public AxisAlignedBB func_72317_d(double var1, double var3, double var5) {
      return new AxisAlignedBB(
         this.field_72340_a + ☃, this.field_72338_b + ☃, this.field_72339_c + ☃, this.field_72336_d + ☃, this.field_72337_e + ☃, this.field_72334_f + ☃
      );
   }

   public AxisAlignedBB func_186670_a(BlockPos var1) {
      return new AxisAlignedBB(
         this.field_72340_a + (double)☃.func_177958_n(),
         this.field_72338_b + (double)☃.func_177956_o(),
         this.field_72339_c + (double)☃.func_177952_p(),
         this.field_72336_d + (double)☃.func_177958_n(),
         this.field_72337_e + (double)☃.func_177956_o(),
         this.field_72334_f + (double)☃.func_177952_p()
      );
   }

   public AxisAlignedBB func_191194_a(Vec3d var1) {
      return this.func_72317_d(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public boolean func_72326_a(AxisAlignedBB var1) {
      return this.func_186668_a(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f);
   }

   public boolean func_186668_a(double var1, double var3, double var5, double var7, double var9, double var11) {
      return this.field_72340_a < ☃
         && this.field_72336_d > ☃
         && this.field_72338_b < ☃
         && this.field_72337_e > ☃
         && this.field_72339_c < ☃
         && this.field_72334_f > ☃;
   }

   public boolean func_189973_a(Vec3d var1, Vec3d var2) {
      return this.func_186668_a(
         Math.min(☃.field_72450_a, ☃.field_72450_a),
         Math.min(☃.field_72448_b, ☃.field_72448_b),
         Math.min(☃.field_72449_c, ☃.field_72449_c),
         Math.max(☃.field_72450_a, ☃.field_72450_a),
         Math.max(☃.field_72448_b, ☃.field_72448_b),
         Math.max(☃.field_72449_c, ☃.field_72449_c)
      );
   }

   public boolean func_72318_a(Vec3d var1) {
      return this.func_197744_e(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public boolean func_197744_e(double var1, double var3, double var5) {
      return ☃ >= this.field_72340_a
         && ☃ < this.field_72336_d
         && ☃ >= this.field_72338_b
         && ☃ < this.field_72337_e
         && ☃ >= this.field_72339_c
         && ☃ < this.field_72334_f;
   }

   public double func_72320_b() {
      double ☃ = this.field_72336_d - this.field_72340_a;
      double ☃x = this.field_72337_e - this.field_72338_b;
      double ☃xx = this.field_72334_f - this.field_72339_c;
      return (☃ + ☃x + ☃xx) / 3.0;
   }

   public AxisAlignedBB func_211539_f(double var1, double var3, double var5) {
      return this.func_72314_b(-☃, -☃, -☃);
   }

   public AxisAlignedBB func_186664_h(double var1) {
      return this.func_186662_g(-☃);
   }

   @Nullable
   public RayTraceResult func_72327_a(Vec3d var1, Vec3d var2) {
      return this.func_197739_a(☃, ☃, null);
   }

   @Nullable
   public RayTraceResult func_197739_a(Vec3d var1, Vec3d var2, @Nullable BlockPos var3) {
      double[] ☃ = new double[]{1.0};
      EnumFacing ☃x = null;
      double ☃xx = ☃.field_72450_a - ☃.field_72450_a;
      double ☃xxx = ☃.field_72448_b - ☃.field_72448_b;
      double ☃xxxx = ☃.field_72449_c - ☃.field_72449_c;
      ☃x = func_197741_a(☃ == null ? this : this.func_186670_a(☃), ☃, ☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
      if (☃x == null) {
         return null;
      } else {
         double ☃ = ☃[0];
         return new RayTraceResult(☃.func_72441_c(☃ * ☃xx, ☃ * ☃xxx, ☃ * ☃xxxx), ☃x, ☃ == null ? BlockPos.field_177992_a : ☃);
      }
   }

   @Nullable
   public static RayTraceResult func_197743_a(Iterable<AxisAlignedBB> var0, Vec3d var1, Vec3d var2, BlockPos var3) {
      double[] ☃ = new double[]{1.0};
      EnumFacing ☃x = null;
      double ☃xx = ☃.field_72450_a - ☃.field_72450_a;
      double ☃xxx = ☃.field_72448_b - ☃.field_72448_b;
      double ☃xxxx = ☃.field_72449_c - ☃.field_72449_c;

      for(AxisAlignedBB ☃xxxxx : ☃) {
         ☃x = func_197741_a(☃xxxxx.func_186670_a(☃), ☃, ☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
      }

      if (☃x == null) {
         return null;
      } else {
         double ☃xxxxx = ☃[0];
         return new RayTraceResult(☃.func_72441_c(☃xxxxx * ☃xx, ☃xxxxx * ☃xxx, ☃xxxxx * ☃xxxx), ☃x, ☃);
      }
   }

   @Nullable
   private static EnumFacing func_197741_a(AxisAlignedBB var0, Vec3d var1, double[] var2, @Nullable EnumFacing var3, double var4, double var6, double var8) {
      if (☃ > 1.0E-7) {
         ☃ = func_197740_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_72340_a,
            ☃.field_72338_b,
            ☃.field_72337_e,
            ☃.field_72339_c,
            ☃.field_72334_f,
            EnumFacing.WEST,
            ☃.field_72450_a,
            ☃.field_72448_b,
            ☃.field_72449_c
         );
      } else if (☃ < -1.0E-7) {
         ☃ = func_197740_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_72336_d,
            ☃.field_72338_b,
            ☃.field_72337_e,
            ☃.field_72339_c,
            ☃.field_72334_f,
            EnumFacing.EAST,
            ☃.field_72450_a,
            ☃.field_72448_b,
            ☃.field_72449_c
         );
      }

      if (☃ > 1.0E-7) {
         ☃ = func_197740_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_72338_b,
            ☃.field_72339_c,
            ☃.field_72334_f,
            ☃.field_72340_a,
            ☃.field_72336_d,
            EnumFacing.DOWN,
            ☃.field_72448_b,
            ☃.field_72449_c,
            ☃.field_72450_a
         );
      } else if (☃ < -1.0E-7) {
         ☃ = func_197740_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_72337_e,
            ☃.field_72339_c,
            ☃.field_72334_f,
            ☃.field_72340_a,
            ☃.field_72336_d,
            EnumFacing.UP,
            ☃.field_72448_b,
            ☃.field_72449_c,
            ☃.field_72450_a
         );
      }

      if (☃ > 1.0E-7) {
         ☃ = func_197740_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_72339_c,
            ☃.field_72340_a,
            ☃.field_72336_d,
            ☃.field_72338_b,
            ☃.field_72337_e,
            EnumFacing.NORTH,
            ☃.field_72449_c,
            ☃.field_72450_a,
            ☃.field_72448_b
         );
      } else if (☃ < -1.0E-7) {
         ☃ = func_197740_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_72334_f,
            ☃.field_72340_a,
            ☃.field_72336_d,
            ☃.field_72338_b,
            ☃.field_72337_e,
            EnumFacing.SOUTH,
            ☃.field_72449_c,
            ☃.field_72450_a,
            ☃.field_72448_b
         );
      }

      return ☃;
   }

   @Nullable
   private static EnumFacing func_197740_a(
      double[] var0,
      @Nullable EnumFacing var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      EnumFacing var18,
      double var19,
      double var21,
      double var23
   ) {
      double ☃ = (☃ - ☃) / ☃;
      double ☃x = ☃ + ☃ * ☃;
      double ☃xx = ☃ + ☃ * ☃;
      if (0.0 < ☃ && ☃ < ☃[0] && ☃ - 1.0E-7 < ☃x && ☃x < ☃ + 1.0E-7 && ☃ - 1.0E-7 < ☃xx && ☃xx < ☃ + 1.0E-7) {
         ☃[0] = ☃;
         return ☃;
      } else {
         return ☃;
      }
   }

   public String toString() {
      return "box["
         + this.field_72340_a
         + ", "
         + this.field_72338_b
         + ", "
         + this.field_72339_c
         + " -> "
         + this.field_72336_d
         + ", "
         + this.field_72337_e
         + ", "
         + this.field_72334_f
         + "]";
   }

   public boolean func_181656_b() {
      return Double.isNaN(this.field_72340_a)
         || Double.isNaN(this.field_72338_b)
         || Double.isNaN(this.field_72339_c)
         || Double.isNaN(this.field_72336_d)
         || Double.isNaN(this.field_72337_e)
         || Double.isNaN(this.field_72334_f);
   }

   public Vec3d func_189972_c() {
      return new Vec3d(
         this.field_72340_a + (this.field_72336_d - this.field_72340_a) * 0.5,
         this.field_72338_b + (this.field_72337_e - this.field_72338_b) * 0.5,
         this.field_72339_c + (this.field_72334_f - this.field_72339_c) * 0.5
      );
   }
}
