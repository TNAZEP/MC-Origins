package net.minecraft.client.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.OpenGlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryUtil;

public class ShaderUniform extends ShaderDefault implements AutoCloseable {
   private static final Logger field_148104_a = LogManager.getLogger();
   private int field_148102_b;
   private final int field_148103_c;
   private final int field_148100_d;
   private final IntBuffer field_148101_e;
   private final FloatBuffer field_148098_f;
   private final String field_148099_g;
   private boolean field_148105_h;
   private final ShaderManager field_148106_i;

   public ShaderUniform(String var1, int var2, int var3, ShaderManager var4) {
      this.field_148099_g = ☃;
      this.field_148103_c = ☃;
      this.field_148100_d = ☃;
      this.field_148106_i = ☃;
      if (☃ <= 3) {
         this.field_148101_e = MemoryUtil.memAllocInt(☃);
         this.field_148098_f = null;
      } else {
         this.field_148101_e = null;
         this.field_148098_f = MemoryUtil.memAllocFloat(☃);
      }

      this.field_148102_b = -1;
      this.func_148096_h();
   }

   public void close() {
      if (this.field_148101_e != null) {
         MemoryUtil.memFree(this.field_148101_e);
      }

      if (this.field_148098_f != null) {
         MemoryUtil.memFree(this.field_148098_f);
      }
   }

   private void func_148096_h() {
      this.field_148105_h = true;
      if (this.field_148106_i != null) {
         this.field_148106_i.func_147985_d();
      }
   }

   public static int func_148085_a(String var0) {
      int ☃ = -1;
      if ("int".equals(☃)) {
         ☃ = 0;
      } else if ("float".equals(☃)) {
         ☃ = 4;
      } else if (☃.startsWith("matrix")) {
         if (☃.endsWith("2x2")) {
            ☃ = 8;
         } else if (☃.endsWith("3x3")) {
            ☃ = 9;
         } else if (☃.endsWith("4x4")) {
            ☃ = 10;
         }
      }

      return ☃;
   }

   public void func_148084_b(int var1) {
      this.field_148102_b = ☃;
   }

   public String func_148086_a() {
      return this.field_148099_g;
   }

   @Override
   public void func_148090_a(float var1) {
      this.field_148098_f.position(0);
      this.field_148098_f.put(0, ☃);
      this.func_148096_h();
   }

   @Override
   public void func_148087_a(float var1, float var2) {
      this.field_148098_f.position(0);
      this.field_148098_f.put(0, ☃);
      this.field_148098_f.put(1, ☃);
      this.func_148096_h();
   }

   @Override
   public void func_148095_a(float var1, float var2, float var3) {
      this.field_148098_f.position(0);
      this.field_148098_f.put(0, ☃);
      this.field_148098_f.put(1, ☃);
      this.field_148098_f.put(2, ☃);
      this.func_148096_h();
   }

   @Override
   public void func_148081_a(float var1, float var2, float var3, float var4) {
      this.field_148098_f.position(0);
      this.field_148098_f.put(☃);
      this.field_148098_f.put(☃);
      this.field_148098_f.put(☃);
      this.field_148098_f.put(☃);
      this.field_148098_f.flip();
      this.func_148096_h();
   }

   @Override
   public void func_148092_b(float var1, float var2, float var3, float var4) {
      this.field_148098_f.position(0);
      if (this.field_148100_d >= 4) {
         this.field_148098_f.put(0, ☃);
      }

      if (this.field_148100_d >= 5) {
         this.field_148098_f.put(1, ☃);
      }

      if (this.field_148100_d >= 6) {
         this.field_148098_f.put(2, ☃);
      }

      if (this.field_148100_d >= 7) {
         this.field_148098_f.put(3, ☃);
      }

      this.func_148096_h();
   }

   @Override
   public void func_148083_a(int var1, int var2, int var3, int var4) {
      this.field_148101_e.position(0);
      if (this.field_148100_d >= 0) {
         this.field_148101_e.put(0, ☃);
      }

      if (this.field_148100_d >= 1) {
         this.field_148101_e.put(1, ☃);
      }

      if (this.field_148100_d >= 2) {
         this.field_148101_e.put(2, ☃);
      }

      if (this.field_148100_d >= 3) {
         this.field_148101_e.put(3, ☃);
      }

      this.func_148096_h();
   }

   @Override
   public void func_148097_a(float[] var1) {
      if (☃.length < this.field_148103_c) {
         field_148104_a.warn("Uniform.set called with a too-small value array (expected {}, got {}). Ignoring.", this.field_148103_c, ☃.length);
      } else {
         this.field_148098_f.position(0);
         this.field_148098_f.put(☃);
         this.field_148098_f.position(0);
         this.func_148096_h();
      }
   }

   @Override
   public void func_195652_a(Matrix4f var1) {
      this.field_148098_f.position(0);
      ☃.func_195879_b(this.field_148098_f);
      this.func_148096_h();
   }

   public void func_148093_b() {
      if (!this.field_148105_h) {
      }

      this.field_148105_h = false;
      if (this.field_148100_d <= 3) {
         this.func_148091_i();
      } else if (this.field_148100_d <= 7) {
         this.func_148089_j();
      } else {
         if (this.field_148100_d > 10) {
            field_148104_a.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", this.field_148100_d);
            return;
         }

         this.func_148082_k();
      }
   }

   private void func_148091_i() {
      this.field_148098_f.clear();
      switch(this.field_148100_d) {
         case 0:
            OpenGlHelper.func_153181_a(this.field_148102_b, this.field_148101_e);
            break;
         case 1:
            OpenGlHelper.func_153182_b(this.field_148102_b, this.field_148101_e);
            break;
         case 2:
            OpenGlHelper.func_153192_c(this.field_148102_b, this.field_148101_e);
            break;
         case 3:
            OpenGlHelper.func_153162_d(this.field_148102_b, this.field_148101_e);
            break;
         default:
            field_148104_a.warn("Uniform.upload called, but count value ({}) is  not in the range of 1 to 4. Ignoring.", this.field_148103_c);
      }
   }

   private void func_148089_j() {
      this.field_148098_f.clear();
      switch(this.field_148100_d) {
         case 4:
            OpenGlHelper.func_153168_a(this.field_148102_b, this.field_148098_f);
            break;
         case 5:
            OpenGlHelper.func_153177_b(this.field_148102_b, this.field_148098_f);
            break;
         case 6:
            OpenGlHelper.func_153191_c(this.field_148102_b, this.field_148098_f);
            break;
         case 7:
            OpenGlHelper.func_153159_d(this.field_148102_b, this.field_148098_f);
            break;
         default:
            field_148104_a.warn("Uniform.upload called, but count value ({}) is not in the range of 1 to 4. Ignoring.", this.field_148103_c);
      }
   }

   private void func_148082_k() {
      this.field_148098_f.clear();
      switch(this.field_148100_d) {
         case 8:
            OpenGlHelper.func_153173_a(this.field_148102_b, false, this.field_148098_f);
            break;
         case 9:
            OpenGlHelper.func_153189_b(this.field_148102_b, false, this.field_148098_f);
            break;
         case 10:
            OpenGlHelper.func_153160_c(this.field_148102_b, false, this.field_148098_f);
      }
   }
}
