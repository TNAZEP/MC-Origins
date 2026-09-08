package net.minecraft.client.gui.fonts;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.Util;

public enum DefaultGlyph implements IGlyphInfo {
   INSTANCE;

   private static final NativeImage field_211581_b = Util.func_200696_a(new NativeImage(NativeImage.PixelFormat.RGBA, 5, 8, false), var0 -> {
      for(int ☃ = 0; ☃ < 8; ++☃) {
         for(int ☃x = 0; ☃x < 5; ++☃x) {
            boolean ☃xx = ☃x == 0 || ☃x + 1 == 5 || ☃ == 0 || ☃ + 1 == 8;
            var0.func_195700_a(☃x, ☃, ☃xx ? -1 : 0);
         }
      }

      var0.func_195711_f();
   });

   @Override
   public int func_211202_a() {
      return 5;
   }

   @Override
   public int func_211203_b() {
      return 8;
   }

   @Override
   public float getAdvance() {
      return 6.0F;
   }

   @Override
   public float func_211578_g() {
      return 1.0F;
   }

   @Override
   public void func_211573_a(int var1, int var2) {
      field_211581_b.func_195697_a(0, ☃, ☃, false);
   }

   @Override
   public boolean func_211579_f() {
      return true;
   }
}
