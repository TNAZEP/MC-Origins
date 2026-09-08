package net.minecraft.world.level;

public class FoliageColor {
   private static int[] pixels = new int[65536];

   public static void init(int[] var0) {
      pixels = â˜ƒ;
   }

   public static int get(double var0, double var2) {
      â˜ƒ *= â˜ƒ;
      int â˜ƒ = (int)((1.0 - â˜ƒ) * 255.0);
      int â˜ƒx = (int)((1.0 - â˜ƒ) * 255.0);
      int â˜ƒxx = â˜ƒx << 8 | â˜ƒ;
      return â˜ƒxx >= pixels.length ? getDefaultColor() : pixels[â˜ƒxx];
   }

   public static int getEvergreenColor() {
      return 6396257;
   }

   public static int getBirchColor() {
      return 8431445;
   }

   public static int getDefaultColor() {
      return 4764952;
   }
}
