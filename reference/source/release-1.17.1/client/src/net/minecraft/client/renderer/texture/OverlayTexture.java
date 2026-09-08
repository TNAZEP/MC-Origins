package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;

public class OverlayTexture implements AutoCloseable {
   private static final int SIZE = 16;
   public static final int NO_WHITE_U = 0;
   public static final int RED_OVERLAY_V = 3;
   public static final int WHITE_OVERLAY_V = 10;
   public static final int NO_OVERLAY = pack(0, 10);
   private final DynamicTexture texture = new DynamicTexture(16, 16, false);

   public OverlayTexture() {
      NativeImage â˜ƒ = this.texture.getPixels();

      for(int â˜ƒx = 0; â˜ƒx < 16; ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < 16; ++â˜ƒxx) {
            if (â˜ƒx < 8) {
               â˜ƒ.setPixelRGBA(â˜ƒxx, â˜ƒx, -1308622593);
            } else {
               int â˜ƒxxx = (int)((1.0F - (float)â˜ƒxx / 15.0F * 0.75F) * 255.0F);
               â˜ƒ.setPixelRGBA(â˜ƒxx, â˜ƒx, â˜ƒxxx << 24 | 16777215);
            }
         }
      }

      RenderSystem.activeTexture(33985);
      this.texture.bind();
      â˜ƒ.upload(0, 0, 0, 0, 0, â˜ƒ.getWidth(), â˜ƒ.getHeight(), false, true, false, false);
      RenderSystem.activeTexture(33984);
   }

   public void close() {
      this.texture.close();
   }

   public void setupOverlayColor() {
      RenderSystem.setupOverlayColor(this.texture::getId, 16);
   }

   public static int u(float var0) {
      return (int)(â˜ƒ * 15.0F);
   }

   public static int v(boolean var0) {
      return â˜ƒ ? 3 : 10;
   }

   public static int pack(int var0, int var1) {
      return â˜ƒ | â˜ƒ << 16;
   }

   public static int pack(float var0, boolean var1) {
      return pack(u(â˜ƒ), v(â˜ƒ));
   }

   public void teardownOverlayColor() {
      RenderSystem.teardownOverlayColor();
   }
}
