package net.minecraft.util;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class BitArray {
   private final long[] field_188145_a;
   private final int field_188146_b;
   private final long field_188147_c;
   private final int field_188148_d;

   public BitArray(int var1, int var2) {
      this(☃, ☃, new long[MathHelper.func_154354_b(☃ * ☃, 64) / 64]);
   }

   public BitArray(int var1, int var2, long[] var3) {
      Validate.inclusiveBetween(1L, 32L, (long)☃);
      this.field_188148_d = ☃;
      this.field_188146_b = ☃;
      this.field_188145_a = ☃;
      this.field_188147_c = (1L << ☃) - 1L;
      int ☃ = MathHelper.func_154354_b(☃ * ☃, 64) / 64;
      if (☃.length != ☃) {
         throw new RuntimeException("Invalid length given for storage, got: " + ☃.length + " but expected: " + ☃);
      }
   }

   public void func_188141_a(int var1, int var2) {
      Validate.inclusiveBetween(0L, (long)(this.field_188148_d - 1), (long)☃);
      Validate.inclusiveBetween(0L, this.field_188147_c, (long)☃);
      int ☃ = ☃ * this.field_188146_b;
      int ☃x = ☃ / 64;
      int ☃xx = ((☃ + 1) * this.field_188146_b - 1) / 64;
      int ☃xxx = ☃ % 64;
      this.field_188145_a[☃x] = this.field_188145_a[☃x] & ~(this.field_188147_c << ☃xxx) | ((long)☃ & this.field_188147_c) << ☃xxx;
      if (☃x != ☃xx) {
         int ☃xxxx = 64 - ☃xxx;
         int ☃xxxxx = this.field_188146_b - ☃xxxx;
         this.field_188145_a[☃xx] = this.field_188145_a[☃xx] >>> ☃xxxxx << ☃xxxxx | ((long)☃ & this.field_188147_c) >> ☃xxxx;
      }
   }

   public int func_188142_a(int var1) {
      Validate.inclusiveBetween(0L, (long)(this.field_188148_d - 1), (long)☃);
      int ☃ = ☃ * this.field_188146_b;
      int ☃x = ☃ / 64;
      int ☃xx = ((☃ + 1) * this.field_188146_b - 1) / 64;
      int ☃xxx = ☃ % 64;
      if (☃x == ☃xx) {
         return (int)(this.field_188145_a[☃x] >>> ☃xxx & this.field_188147_c);
      } else {
         int ☃ = 64 - ☃xxx;
         return (int)((this.field_188145_a[☃x] >>> ☃xxx | this.field_188145_a[☃xx] << ☃) & this.field_188147_c);
      }
   }

   public long[] func_188143_a() {
      return this.field_188145_a;
   }

   public int func_188144_b() {
      return this.field_188148_d;
   }

   public int func_208535_c() {
      return this.field_188146_b;
   }
}
