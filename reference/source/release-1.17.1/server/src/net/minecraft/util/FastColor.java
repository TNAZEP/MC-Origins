package net.minecraft.util;

public class FastColor {
   public static class ARGB32 {
      public static int alpha(int var0) {
         return â˜ƒ >>> 24;
      }

      public static int red(int var0) {
         return â˜ƒ >> 16 & 0xFF;
      }

      public static int green(int var0) {
         return â˜ƒ >> 8 & 0xFF;
      }

      public static int blue(int var0) {
         return â˜ƒ & 0xFF;
      }

      public static int color(int var0, int var1, int var2, int var3) {
         return â˜ƒ << 24 | â˜ƒ << 16 | â˜ƒ << 8 | â˜ƒ;
      }

      public static int multiply(int var0, int var1) {
         return color(alpha(â˜ƒ) * alpha(â˜ƒ) / 255, red(â˜ƒ) * red(â˜ƒ) / 255, green(â˜ƒ) * green(â˜ƒ) / 255, blue(â˜ƒ) * blue(â˜ƒ) / 255);
      }
   }
}
