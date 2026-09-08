package net.minecraft.util;

import java.util.Random;

public class SharedSeedRandom extends Random {
   private int field_202428_a;

   public SharedSeedRandom() {
   }

   public SharedSeedRandom(long var1) {
      super(☃);
   }

   public void func_202423_a(int var1) {
      for(int ☃ = 0; ☃ < ☃; ++☃) {
         this.next(1);
      }
   }

   protected int next(int var1) {
      ++this.field_202428_a;
      return super.next(☃);
   }

   public long func_202422_a(int var1, int var2) {
      long ☃ = (long)☃ * 341873128712L + (long)☃ * 132897987541L;
      this.setSeed(☃);
      return ☃;
   }

   public long func_202424_a(long var1, int var3, int var4) {
      this.setSeed(☃);
      long ☃ = this.nextLong() | 1L;
      long ☃x = this.nextLong() | 1L;
      long ☃xx = (long)☃ * ☃ + (long)☃ * ☃x ^ ☃;
      this.setSeed(☃xx);
      return ☃xx;
   }

   public long func_202426_b(long var1, int var3, int var4) {
      long ☃ = ☃ + (long)☃ + (long)(10000 * ☃);
      this.setSeed(☃);
      return ☃;
   }

   public long func_202425_c(long var1, int var3, int var4) {
      this.setSeed(☃);
      long ☃ = this.nextLong();
      long ☃x = this.nextLong();
      long ☃xx = (long)☃ * ☃ ^ (long)☃ * ☃x ^ ☃;
      this.setSeed(☃xx);
      return ☃xx;
   }

   public long func_202427_a(long var1, int var3, int var4, int var5) {
      long ☃ = (long)☃ * 341873128712L + (long)☃ * 132897987541L + ☃ + (long)☃;
      this.setSeed(☃);
      return ☃;
   }

   public static Random func_205190_a(int var0, int var1, long var2, long var4) {
      return new Random(☃ + (long)(☃ * ☃ * 4987142) + (long)(☃ * 5947611) + (long)(☃ * ☃) * 4392871L + (long)(☃ * 389711) ^ ☃);
   }
}
