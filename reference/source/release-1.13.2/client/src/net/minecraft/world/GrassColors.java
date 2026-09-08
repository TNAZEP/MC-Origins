package net.minecraft.world;

public class GrassColors {
   private static int[] field_77481_a = new int[65536];

   public static void func_77479_a(int[] var0) {
      field_77481_a = ☃;
   }

   public static int func_77480_a(double var0, double var2) {
      ☃ *= ☃;
      int ☃ = (int)((1.0 - ☃) * 255.0);
      int ☃x = (int)((1.0 - ☃) * 255.0);
      int ☃xx = ☃x << 8 | ☃;
      return ☃xx > field_77481_a.length ? -65281 : field_77481_a[☃xx];
   }
}
