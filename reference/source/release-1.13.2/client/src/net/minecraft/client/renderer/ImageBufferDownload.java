package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.NativeImage;

public class ImageBufferDownload implements IImageBuffer {
   @Override
   public NativeImage func_195786_a(NativeImage var1) {
      boolean ☃ = ☃.func_195714_b() == 32;
      if (☃) {
         NativeImage ☃x = new NativeImage(64, 64, true);
         ☃x.func_195703_a(☃);
         ☃.close();
         ☃ = ☃x;
         ☃x.func_195715_a(0, 32, 64, 32, 0);
         ☃x.func_195699_a(4, 16, 16, 32, 4, 4, true, false);
         ☃x.func_195699_a(8, 16, 16, 32, 4, 4, true, false);
         ☃x.func_195699_a(0, 20, 24, 32, 4, 12, true, false);
         ☃x.func_195699_a(4, 20, 16, 32, 4, 12, true, false);
         ☃x.func_195699_a(8, 20, 8, 32, 4, 12, true, false);
         ☃x.func_195699_a(12, 20, 16, 32, 4, 12, true, false);
         ☃x.func_195699_a(44, 16, -8, 32, 4, 4, true, false);
         ☃x.func_195699_a(48, 16, -8, 32, 4, 4, true, false);
         ☃x.func_195699_a(40, 20, 0, 32, 4, 12, true, false);
         ☃x.func_195699_a(44, 20, -8, 32, 4, 12, true, false);
         ☃x.func_195699_a(48, 20, -16, 32, 4, 12, true, false);
         ☃x.func_195699_a(52, 20, -8, 32, 4, 12, true, false);
      }

      func_195787_b(☃, 0, 0, 32, 16);
      if (☃) {
         func_195788_a(☃, 32, 0, 64, 32);
      }

      func_195787_b(☃, 0, 16, 64, 32);
      func_195787_b(☃, 16, 48, 48, 64);
      return ☃;
   }

   @Override
   public void func_152634_a() {
   }

   private static void func_195788_a(NativeImage var0, int var1, int var2, int var3, int var4) {
      for(int ☃ = ☃; ☃ < ☃; ++☃) {
         for(int ☃x = ☃; ☃x < ☃; ++☃x) {
            int ☃xx = ☃.func_195709_a(☃, ☃x);
            if ((☃xx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for(int ☃ = ☃; ☃ < ☃; ++☃) {
         for(int ☃x = ☃; ☃x < ☃; ++☃x) {
            ☃.func_195700_a(☃, ☃x, ☃.func_195709_a(☃, ☃x) & 16777215);
         }
      }
   }

   private static void func_195787_b(NativeImage var0, int var1, int var2, int var3, int var4) {
      for(int ☃ = ☃; ☃ < ☃; ++☃) {
         for(int ☃x = ☃; ☃x < ☃; ++☃x) {
            ☃.func_195700_a(☃, ☃x, ☃.func_195709_a(☃, ☃x) | 0xFF000000);
         }
      }
   }
}
