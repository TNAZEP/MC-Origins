package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLAllocation {
   public static synchronized int func_74526_a(int var0) {
      int ☃ = GlStateManager.func_187442_t(☃);
      if (☃ == 0) {
         int ☃x = GlStateManager.func_187434_L();
         String ☃xx = "No error code reported";
         if (☃x != 0) {
            ☃xx = OpenGlHelper.func_195917_n(☃x);
         }

         throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + ☃ + ", GL error (" + ☃x + "): " + ☃xx);
      } else {
         return ☃;
      }
   }

   public static synchronized void func_178874_a(int var0, int var1) {
      GlStateManager.func_187449_e(☃, ☃);
   }

   public static synchronized void func_74523_b(int var0) {
      func_178874_a(☃, 1);
   }

   public static synchronized ByteBuffer func_74524_c(int var0) {
      return ByteBuffer.allocateDirect(☃).order(ByteOrder.nativeOrder());
   }

   public static FloatBuffer func_74529_h(int var0) {
      return func_74524_c(☃ << 2).asFloatBuffer();
   }
}
