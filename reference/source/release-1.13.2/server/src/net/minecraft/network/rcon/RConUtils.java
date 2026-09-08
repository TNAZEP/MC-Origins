package net.minecraft.network.rcon;

import java.nio.charset.StandardCharsets;

public class RConUtils {
   public static final char[] field_72666_a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String func_72661_a(byte[] var0, int var1, int var2) {
      int ☃ = ☃ - 1;
      int ☃x = ☃ > ☃ ? ☃ : ☃;

      while(0 != ☃[☃x] && ☃x < ☃) {
         ++☃x;
      }

      return new String(☃, ☃, ☃x - ☃, StandardCharsets.UTF_8);
   }

   public static int func_72662_b(byte[] var0, int var1) {
      return func_72665_b(☃, ☃, ☃.length);
   }

   public static int func_72665_b(byte[] var0, int var1, int var2) {
      return 0 > ☃ - ☃ - 4 ? 0 : ☃[☃ + 3] << 24 | (☃[☃ + 2] & 0xFF) << 16 | (☃[☃ + 1] & 0xFF) << 8 | ☃[☃] & 0xFF;
   }

   public static int func_72664_c(byte[] var0, int var1, int var2) {
      return 0 > ☃ - ☃ - 4 ? 0 : ☃[☃] << 24 | (☃[☃ + 1] & 0xFF) << 16 | (☃[☃ + 2] & 0xFF) << 8 | ☃[☃ + 3] & 0xFF;
   }

   public static String func_72663_a(byte var0) {
      return "" + field_72666_a[(☃ & 240) >>> 4] + field_72666_a[☃ & 15];
   }
}
