package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;

public class MipmapGenerator {
   private static final int ALPHA_CUTOUT_CUTOFF = 96;
   private static final float[] POW22 = Util.make(new float[256], var0 -> {
      for(int â˜ƒ = 0; â˜ƒ < var0.length; ++â˜ƒ) {
         var0[â˜ƒ] = (float)Math.pow((double)((float)â˜ƒ / 255.0F), 2.2);
      }
   });

   private MipmapGenerator() {
   }

   public static NativeImage[] generateMipLevels(NativeImage var0, int var1) {
      NativeImage[] â˜ƒ = new NativeImage[â˜ƒ + 1];
      â˜ƒ[0] = â˜ƒ;
      if (â˜ƒ > 0) {
         boolean â˜ƒx = false;

         label51:
         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getWidth(); ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.getHeight(); ++â˜ƒxxx) {
               if (â˜ƒ.getPixelRGBA(â˜ƒxx, â˜ƒxxx) >> 24 == 0) {
                  â˜ƒx = true;
                  break label51;
               }
            }
         }

         for(int â˜ƒxx = 1; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
            NativeImage â˜ƒxxx = â˜ƒ[â˜ƒxx - 1];
            NativeImage â˜ƒxxxx = new NativeImage(â˜ƒxxx.getWidth() >> 1, â˜ƒxxx.getHeight() >> 1, false);
            int â˜ƒxxxxx = â˜ƒxxxx.getWidth();
            int â˜ƒxxxxxx = â˜ƒxxxx.getHeight();

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
               for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxxx) {
                  â˜ƒxxxx.setPixelRGBA(
                     â˜ƒxxxxxxx,
                     â˜ƒxxxxxxxx,
                     alphaBlend(
                        â˜ƒxxx.getPixelRGBA(â˜ƒxxxxxxx * 2 + 0, â˜ƒxxxxxxxx * 2 + 0),
                        â˜ƒxxx.getPixelRGBA(â˜ƒxxxxxxx * 2 + 1, â˜ƒxxxxxxxx * 2 + 0),
                        â˜ƒxxx.getPixelRGBA(â˜ƒxxxxxxx * 2 + 0, â˜ƒxxxxxxxx * 2 + 1),
                        â˜ƒxxx.getPixelRGBA(â˜ƒxxxxxxx * 2 + 1, â˜ƒxxxxxxxx * 2 + 1),
                        â˜ƒx
                     )
                  );
               }
            }

            â˜ƒ[â˜ƒxx] = â˜ƒxxxx;
         }
      }

      return â˜ƒ;
   }

   private static int alphaBlend(int var0, int var1, int var2, int var3, boolean var4) {
      if (â˜ƒ) {
         float â˜ƒ = 0.0F;
         float â˜ƒx = 0.0F;
         float â˜ƒxx = 0.0F;
         float â˜ƒxxx = 0.0F;
         if (â˜ƒ >> 24 != 0) {
            â˜ƒ += getPow22(â˜ƒ >> 24);
            â˜ƒx += getPow22(â˜ƒ >> 16);
            â˜ƒxx += getPow22(â˜ƒ >> 8);
            â˜ƒxxx += getPow22(â˜ƒ >> 0);
         }

         if (â˜ƒ >> 24 != 0) {
            â˜ƒ += getPow22(â˜ƒ >> 24);
            â˜ƒx += getPow22(â˜ƒ >> 16);
            â˜ƒxx += getPow22(â˜ƒ >> 8);
            â˜ƒxxx += getPow22(â˜ƒ >> 0);
         }

         if (â˜ƒ >> 24 != 0) {
            â˜ƒ += getPow22(â˜ƒ >> 24);
            â˜ƒx += getPow22(â˜ƒ >> 16);
            â˜ƒxx += getPow22(â˜ƒ >> 8);
            â˜ƒxxx += getPow22(â˜ƒ >> 0);
         }

         if (â˜ƒ >> 24 != 0) {
            â˜ƒ += getPow22(â˜ƒ >> 24);
            â˜ƒx += getPow22(â˜ƒ >> 16);
            â˜ƒxx += getPow22(â˜ƒ >> 8);
            â˜ƒxxx += getPow22(â˜ƒ >> 0);
         }

         â˜ƒ /= 4.0F;
         â˜ƒx /= 4.0F;
         â˜ƒxx /= 4.0F;
         â˜ƒxxx /= 4.0F;
         int â˜ƒ = (int)(Math.pow((double)â˜ƒ, 0.45454545454545453) * 255.0);
         int â˜ƒx = (int)(Math.pow((double)â˜ƒx, 0.45454545454545453) * 255.0);
         int â˜ƒxx = (int)(Math.pow((double)â˜ƒxx, 0.45454545454545453) * 255.0);
         int â˜ƒxxx = (int)(Math.pow((double)â˜ƒxxx, 0.45454545454545453) * 255.0);
         if (â˜ƒ < 96) {
            â˜ƒ = 0;
         }

         return â˜ƒ << 24 | â˜ƒx << 16 | â˜ƒxx << 8 | â˜ƒxxx;
      } else {
         int â˜ƒ = gammaBlend(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 24);
         int â˜ƒx = gammaBlend(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 16);
         int â˜ƒxx = gammaBlend(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 8);
         int â˜ƒxxx = gammaBlend(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0);
         return â˜ƒ << 24 | â˜ƒx << 16 | â˜ƒxx << 8 | â˜ƒxxx;
      }
   }

   private static int gammaBlend(int var0, int var1, int var2, int var3, int var4) {
      float â˜ƒ = getPow22(â˜ƒ >> â˜ƒ);
      float â˜ƒx = getPow22(â˜ƒ >> â˜ƒ);
      float â˜ƒxx = getPow22(â˜ƒ >> â˜ƒ);
      float â˜ƒxxx = getPow22(â˜ƒ >> â˜ƒ);
      float â˜ƒxxxx = (float)((double)((float)Math.pow((double)(â˜ƒ + â˜ƒx + â˜ƒxx + â˜ƒxxx) * 0.25, 0.45454545454545453)));
      return (int)((double)â˜ƒxxxx * 255.0);
   }

   private static float getPow22(int var0) {
      return POW22[â˜ƒ & 0xFF];
   }
}
