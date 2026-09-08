package com.mojang.realmsclient.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.annotation.Nullable;

public class SkinProcessor {
   private int[] pixels;
   private int width;
   private int height;

   @Nullable
   public BufferedImage process(BufferedImage var1) {
      if (â˜ƒ == null) {
         return null;
      } else {
         this.width = 64;
         this.height = 64;
         BufferedImage â˜ƒ = new BufferedImage(this.width, this.height, 2);
         Graphics â˜ƒx = â˜ƒ.getGraphics();
         â˜ƒx.drawImage(â˜ƒ, 0, 0, null);
         boolean â˜ƒxx = â˜ƒ.getHeight() == 32;
         if (â˜ƒxx) {
            â˜ƒx.setColor(new Color(0, 0, 0, 0));
            â˜ƒx.fillRect(0, 32, 64, 32);
            â˜ƒx.drawImage(â˜ƒ, 24, 48, 20, 52, 4, 16, 8, 20, null);
            â˜ƒx.drawImage(â˜ƒ, 28, 48, 24, 52, 8, 16, 12, 20, null);
            â˜ƒx.drawImage(â˜ƒ, 20, 52, 16, 64, 8, 20, 12, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 24, 52, 20, 64, 4, 20, 8, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 28, 52, 24, 64, 0, 20, 4, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 32, 52, 28, 64, 12, 20, 16, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 40, 48, 36, 52, 44, 16, 48, 20, null);
            â˜ƒx.drawImage(â˜ƒ, 44, 48, 40, 52, 48, 16, 52, 20, null);
            â˜ƒx.drawImage(â˜ƒ, 36, 52, 32, 64, 48, 20, 52, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 40, 52, 36, 64, 44, 20, 48, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 44, 52, 40, 64, 40, 20, 44, 32, null);
            â˜ƒx.drawImage(â˜ƒ, 48, 52, 44, 64, 52, 20, 56, 32, null);
         }

         â˜ƒx.dispose();
         this.pixels = ((DataBufferInt)â˜ƒ.getRaster().getDataBuffer()).getData();
         this.setNoAlpha(0, 0, 32, 16);
         if (â˜ƒxx) {
            this.doLegacyTransparencyHack(32, 0, 64, 32);
         }

         this.setNoAlpha(0, 16, 64, 32);
         this.setNoAlpha(16, 48, 48, 64);
         return â˜ƒ;
      }
   }

   private void doLegacyTransparencyHack(int var1, int var2, int var3, int var4) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            int â˜ƒxx = this.pixels[â˜ƒ + â˜ƒx * this.width];
            if ((â˜ƒxx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            this.pixels[â˜ƒ + â˜ƒx * this.width] &= 16777215;
         }
      }
   }

   private void setNoAlpha(int var1, int var2, int var3, int var4) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            this.pixels[â˜ƒ + â˜ƒx * this.width] |= -16777216;
         }
      }
   }
}
