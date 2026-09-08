package net.minecraft.util.math;

import net.minecraft.entity.Entity;

public class ChunkPos {
   public final int field_77276_a;
   public final int field_77275_b;

   public ChunkPos(int var1, int var2) {
      this.field_77276_a = ☃;
      this.field_77275_b = ☃;
   }

   public ChunkPos(BlockPos var1) {
      this.field_77276_a = ☃.func_177958_n() >> 4;
      this.field_77275_b = ☃.func_177952_p() >> 4;
   }

   public ChunkPos(long var1) {
      this.field_77276_a = (int)☃;
      this.field_77275_b = (int)(☃ >> 32);
   }

   public long func_201841_a() {
      return func_77272_a(this.field_77276_a, this.field_77275_b);
   }

   public static long func_77272_a(int var0, int var1) {
      return (long)☃ & 4294967295L | ((long)☃ & 4294967295L) << 32;
   }

   public static int func_212578_a(long var0) {
      return (int)(☃ & 4294967295L);
   }

   public static int func_212579_b(long var0) {
      return (int)(☃ >>> 32 & 4294967295L);
   }

   public int hashCode() {
      int ☃ = 1664525 * this.field_77276_a + 1013904223;
      int ☃x = 1664525 * (this.field_77275_b ^ -559038737) + 1013904223;
      return ☃ ^ ☃x;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof ChunkPos)) {
         return false;
      } else {
         ChunkPos ☃ = (ChunkPos)☃;
         return this.field_77276_a == ☃.field_77276_a && this.field_77275_b == ☃.field_77275_b;
      }
   }

   public double func_185327_a(Entity var1) {
      double ☃ = (double)(this.field_77276_a * 16 + 8);
      double ☃x = (double)(this.field_77275_b * 16 + 8);
      double ☃xx = ☃ - ☃.field_70165_t;
      double ☃xxx = ☃x - ☃.field_70161_v;
      return ☃xx * ☃xx + ☃xxx * ☃xxx;
   }

   public int func_180334_c() {
      return this.field_77276_a << 4;
   }

   public int func_180333_d() {
      return this.field_77275_b << 4;
   }

   public int func_180332_e() {
      return (this.field_77276_a << 4) + 15;
   }

   public int func_180330_f() {
      return (this.field_77275_b << 4) + 15;
   }

   public BlockPos func_180331_a(int var1, int var2, int var3) {
      return new BlockPos((this.field_77276_a << 4) + ☃, ☃, (this.field_77275_b << 4) + ☃);
   }

   public String toString() {
      return "[" + this.field_77276_a + ", " + this.field_77275_b + "]";
   }

   public BlockPos func_206849_h() {
      return new BlockPos(this.field_77276_a << 4, 0, this.field_77275_b << 4);
   }
}
