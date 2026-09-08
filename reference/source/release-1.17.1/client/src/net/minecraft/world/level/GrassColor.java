package net.minecraft.world.level;

public class GrassColor {
   private static int[] pixels = new int[65536];

   public static void init(int[] var0) {
      pixels = â˜ƒ;
   }

   public static int get(double var0, double var2) {
      â˜ƒ *= â˜ƒ;
      int â˜ƒ = (int)((1.0 - â˜ƒ) * 255.0);
      int â˜ƒx = (int)((1.0 - â˜ƒ) * 255.0);
      int â˜ƒxx = â˜ƒx << 8 | â˜ƒ;
      return â˜ƒxx >= pixels.length ? -65281 : pixels[â˜ƒxx];
   }
}
