package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;

public enum WhiteGlyph implements RawGlyph {
   INSTANCE;

   private static final int WIDTH = 5;
   private static final int HEIGHT = 8;
   private static final NativeImage IMAGE_DATA = Util.make(new NativeImage(NativeImage.Format.RGBA, 5, 8, false), var0 -> {
      for(int â˜ƒ = 0; â˜ƒ < 8; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
            if (â˜ƒx != 0 && â˜ƒx + 1 != 5 && â˜ƒ != 0 && â˜ƒ + 1 != 8) {
               boolean var4 = false;
            } else {
               boolean var10000 = true;
            }

            var0.setPixelRGBA(â˜ƒx, â˜ƒ, -1);
         }
      }

      var0.untrack();
   });

   @Override
   public int getPixelWidth() {
      return 5;
   }

   @Override
   public int getPixelHeight() {
      return 8;
   }

   @Override
   public float getAdvance() {
      return 6.0F;
   }

   @Override
   public float getOversample() {
      return 1.0F;
   }

   @Override
   public void upload(int var1, int var2) {
      IMAGE_DATA.upload(0, â˜ƒ, â˜ƒ, false);
   }

   @Override
   public boolean isColored() {
      return true;
   }
}
