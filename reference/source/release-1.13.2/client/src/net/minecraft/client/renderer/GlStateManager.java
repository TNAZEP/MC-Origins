package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.LWJGLMemoryUntracker;
import net.minecraft.util.Util;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.system.MemoryUtil;

public class GlStateManager {
   private static final FloatBuffer field_187450_a = Util.func_200696_a(
      MemoryUtil.memAllocFloat(16), var0 -> LWJGLMemoryUntracker.func_197933_a(MemoryUtil.memAddress(var0))
   );
   private static final FloatBuffer field_187451_b = Util.func_200696_a(
      MemoryUtil.memAllocFloat(4), var0 -> LWJGLMemoryUntracker.func_197933_a(MemoryUtil.memAddress(var0))
   );
   private static final GlStateManager.AlphaState field_199299_c = new GlStateManager.AlphaState();
   private static final GlStateManager.BooleanState field_199300_d = new GlStateManager.BooleanState(2896);
   private static final GlStateManager.BooleanState[] field_199301_e = (GlStateManager.BooleanState[])IntStream.range(0, 8)
      .mapToObj(var0 -> new GlStateManager.BooleanState(16384 + var0))
      .toArray(var0 -> new GlStateManager.BooleanState[var0]);
   private static final GlStateManager.ColorMaterialState field_199302_f = new GlStateManager.ColorMaterialState();
   private static final GlStateManager.BlendState field_179157_e = new GlStateManager.BlendState();
   private static final GlStateManager.DepthState field_179154_f = new GlStateManager.DepthState();
   private static final GlStateManager.FogState field_179155_g = new GlStateManager.FogState();
   private static final GlStateManager.CullState field_179167_h = new GlStateManager.CullState();
   private static final GlStateManager.PolygonOffsetState field_179168_i = new GlStateManager.PolygonOffsetState();
   private static final GlStateManager.ColorLogicState field_179165_j = new GlStateManager.ColorLogicState();
   private static final GlStateManager.TexGenState field_179166_k = new GlStateManager.TexGenState();
   private static final GlStateManager.ClearState field_179163_l = new GlStateManager.ClearState();
   private static final GlStateManager.StencilState field_179164_m = new GlStateManager.StencilState();
   private static final GlStateManager.BooleanState field_199303_p = new GlStateManager.BooleanState(2977);
   private static int field_179162_o;
   private static final GlStateManager.TextureState[] field_199304_r = (GlStateManager.TextureState[])IntStream.range(0, 8)
      .mapToObj(var0 -> new GlStateManager.TextureState())
      .toArray(var0 -> new GlStateManager.TextureState[var0]);
   private static int field_179173_q = 7425;
   private static final GlStateManager.BooleanState field_199305_t = new GlStateManager.BooleanState(32826);
   private static final GlStateManager.ColorMask field_199306_u = new GlStateManager.ColorMask();
   private static final GlStateManager.Color field_199307_v = new GlStateManager.Color();

   public static void func_179123_a() {
      GL11.glPushAttrib(8256);
   }

   public static void func_179099_b() {
      GL11.glPopAttrib();
   }

   public static void func_179118_c() {
      field_199299_c.field_179208_a.func_179198_a();
   }

   public static void func_179141_d() {
      field_199299_c.field_179208_a.func_179200_b();
   }

   public static void func_179092_a(int var0, float var1) {
      if (☃ != field_199299_c.field_179206_b || ☃ != field_199299_c.field_179207_c) {
         field_199299_c.field_179206_b = ☃;
         field_199299_c.field_179207_c = ☃;
         GL11.glAlphaFunc(☃, ☃);
      }
   }

   public static void func_179145_e() {
      field_199300_d.func_179200_b();
   }

   public static void func_179140_f() {
      field_199300_d.func_179198_a();
   }

   public static void func_179085_a(int var0) {
      field_199301_e[☃].func_179200_b();
   }

   public static void func_179122_b(int var0) {
      field_199301_e[☃].func_179198_a();
   }

   public static void func_179142_g() {
      field_199302_f.field_179191_a.func_179200_b();
   }

   public static void func_179119_h() {
      field_199302_f.field_179191_a.func_179198_a();
   }

   public static void func_179104_a(int var0, int var1) {
      if (☃ != field_199302_f.field_179189_b || ☃ != field_199302_f.field_179190_c) {
         field_199302_f.field_179189_b = ☃;
         field_199302_f.field_179190_c = ☃;
         GL11.glColorMaterial(☃, ☃);
      }
   }

   public static void func_187438_a(int var0, int var1, FloatBuffer var2) {
      GL11.glLightfv(☃, ☃, ☃);
   }

   public static void func_187424_a(int var0, FloatBuffer var1) {
      GL11.glLightModelfv(☃, ☃);
   }

   public static void func_187432_a(float var0, float var1, float var2) {
      GL11.glNormal3f(☃, ☃, ☃);
   }

   public static void func_179097_i() {
      field_179154_f.field_179052_a.func_179198_a();
   }

   public static void func_179126_j() {
      field_179154_f.field_179052_a.func_179200_b();
   }

   public static void func_179143_c(int var0) {
      if (☃ != field_179154_f.field_179051_c) {
         field_179154_f.field_179051_c = ☃;
         GL11.glDepthFunc(☃);
      }
   }

   public static void func_179132_a(boolean var0) {
      if (☃ != field_179154_f.field_179050_b) {
         field_179154_f.field_179050_b = ☃;
         GL11.glDepthMask(☃);
      }
   }

   public static void func_179084_k() {
      field_179157_e.field_179213_a.func_179198_a();
   }

   public static void func_179147_l() {
      field_179157_e.field_179213_a.func_179200_b();
   }

   public static void func_187401_a(GlStateManager.SourceFactor var0, GlStateManager.DestFactor var1) {
      func_179112_b(☃.field_187395_p, ☃.field_187345_o);
   }

   public static void func_179112_b(int var0, int var1) {
      if (☃ != field_179157_e.field_179211_b || ☃ != field_179157_e.field_179212_c) {
         field_179157_e.field_179211_b = ☃;
         field_179157_e.field_179212_c = ☃;
         GL11.glBlendFunc(☃, ☃);
      }
   }

   public static void func_187428_a(
      GlStateManager.SourceFactor var0, GlStateManager.DestFactor var1, GlStateManager.SourceFactor var2, GlStateManager.DestFactor var3
   ) {
      func_179120_a(☃.field_187395_p, ☃.field_187345_o, ☃.field_187395_p, ☃.field_187345_o);
   }

   public static void func_179120_a(int var0, int var1, int var2, int var3) {
      if (☃ != field_179157_e.field_179211_b || ☃ != field_179157_e.field_179212_c || ☃ != field_179157_e.field_179209_d || ☃ != field_179157_e.field_179210_e) {
         field_179157_e.field_179211_b = ☃;
         field_179157_e.field_179212_c = ☃;
         field_179157_e.field_179209_d = ☃;
         field_179157_e.field_179210_e = ☃;
         OpenGlHelper.func_148821_a(☃, ☃, ☃, ☃);
      }
   }

   public static void func_187398_d(int var0) {
      GL14.glBlendEquation(☃);
   }

   public static void func_187431_e(int var0) {
      field_187451_b.put(0, (float)(☃ >> 16 & 0xFF) / 255.0F);
      field_187451_b.put(1, (float)(☃ >> 8 & 0xFF) / 255.0F);
      field_187451_b.put(2, (float)(☃ >> 0 & 0xFF) / 255.0F);
      field_187451_b.put(3, (float)(☃ >> 24 & 0xFF) / 255.0F);
      func_187448_b(8960, 8705, field_187451_b);
      func_187399_a(8960, 8704, 34160);
      func_187399_a(8960, 34161, 7681);
      func_187399_a(8960, 34176, 34166);
      func_187399_a(8960, 34192, 768);
      func_187399_a(8960, 34162, 7681);
      func_187399_a(8960, 34184, 5890);
      func_187399_a(8960, 34200, 770);
   }

   public static void func_187417_n() {
      func_187399_a(8960, 8704, 8448);
      func_187399_a(8960, 34161, 8448);
      func_187399_a(8960, 34162, 8448);
      func_187399_a(8960, 34176, 5890);
      func_187399_a(8960, 34184, 5890);
      func_187399_a(8960, 34192, 768);
      func_187399_a(8960, 34200, 770);
   }

   public static void func_179127_m() {
      field_179155_g.field_179049_a.func_179200_b();
   }

   public static void func_179106_n() {
      field_179155_g.field_179049_a.func_179198_a();
   }

   public static void func_187430_a(GlStateManager.FogMode var0) {
      func_179093_d(☃.field_187351_d);
   }

   private static void func_179093_d(int var0) {
      if (☃ != field_179155_g.field_179047_b) {
         field_179155_g.field_179047_b = ☃;
         GL11.glFogi(2917, ☃);
      }
   }

   public static void func_179095_a(float var0) {
      if (☃ != field_179155_g.field_179048_c) {
         field_179155_g.field_179048_c = ☃;
         GL11.glFogf(2914, ☃);
      }
   }

   public static void func_179102_b(float var0) {
      if (☃ != field_179155_g.field_179045_d) {
         field_179155_g.field_179045_d = ☃;
         GL11.glFogf(2915, ☃);
      }
   }

   public static void func_179153_c(float var0) {
      if (☃ != field_179155_g.field_179046_e) {
         field_179155_g.field_179046_e = ☃;
         GL11.glFogf(2916, ☃);
      }
   }

   public static void func_187402_b(int var0, FloatBuffer var1) {
      GL11.glFogfv(☃, ☃);
   }

   public static void func_187412_c(int var0, int var1) {
      GL11.glFogi(☃, ☃);
   }

   public static void func_179089_o() {
      field_179167_h.field_179054_a.func_179200_b();
   }

   public static void func_179129_p() {
      field_179167_h.field_179054_a.func_179198_a();
   }

   public static void func_187407_a(GlStateManager.CullFace var0) {
      func_179107_e(☃.field_187328_d);
   }

   private static void func_179107_e(int var0) {
      if (☃ != field_179167_h.field_179053_b) {
         field_179167_h.field_179053_b = ☃;
         GL11.glCullFace(☃);
      }
   }

   public static void func_187409_d(int var0, int var1) {
      GL11.glPolygonMode(☃, ☃);
   }

   public static void func_179088_q() {
      field_179168_i.field_179044_a.func_179200_b();
   }

   public static void func_179113_r() {
      field_179168_i.field_179044_a.func_179198_a();
   }

   public static void func_179136_a(float var0, float var1) {
      if (☃ != field_179168_i.field_179043_c || ☃ != field_179168_i.field_179041_d) {
         field_179168_i.field_179043_c = ☃;
         field_179168_i.field_179041_d = ☃;
         GL11.glPolygonOffset(☃, ☃);
      }
   }

   public static void func_179115_u() {
      field_179165_j.field_179197_a.func_179200_b();
   }

   public static void func_179134_v() {
      field_179165_j.field_179197_a.func_179198_a();
   }

   public static void func_187422_a(GlStateManager.LogicOp var0) {
      func_179116_f(☃.field_187370_q);
   }

   public static void func_179116_f(int var0) {
      if (☃ != field_179165_j.field_179196_b) {
         field_179165_j.field_179196_b = ☃;
         GL11.glLogicOp(☃);
      }
   }

   public static void func_179087_a(GlStateManager.TexGen var0) {
      func_179125_c(☃).field_179067_a.func_179200_b();
   }

   public static void func_179100_b(GlStateManager.TexGen var0) {
      func_179125_c(☃).field_179067_a.func_179198_a();
   }

   public static void func_179149_a(GlStateManager.TexGen var0, int var1) {
      GlStateManager.TexGenCoord ☃ = func_179125_c(☃);
      if (☃ != ☃.field_179066_c) {
         ☃.field_179066_c = ☃;
         GL11.glTexGeni(☃.field_179065_b, 9472, ☃);
      }
   }

   public static void func_179105_a(GlStateManager.TexGen var0, int var1, FloatBuffer var2) {
      GL11.glTexGenfv(func_179125_c(☃).field_179065_b, ☃, ☃);
   }

   private static GlStateManager.TexGenCoord func_179125_c(GlStateManager.TexGen var0) {
      switch(☃) {
         case S:
            return field_179166_k.field_179064_a;
         case T:
            return field_179166_k.field_179062_b;
         case R:
            return field_179166_k.field_179063_c;
         case Q:
            return field_179166_k.field_179061_d;
         default:
            return field_179166_k.field_179064_a;
      }
   }

   public static void func_179138_g(int var0) {
      if (field_179162_o != ☃ - OpenGlHelper.field_77478_a) {
         field_179162_o = ☃ - OpenGlHelper.field_77478_a;
         OpenGlHelper.func_77473_a(☃);
      }
   }

   public static void func_179098_w() {
      field_199304_r[field_179162_o].field_179060_a.func_179200_b();
   }

   public static void func_179090_x() {
      field_199304_r[field_179162_o].field_179060_a.func_179198_a();
   }

   public static void func_187448_b(int var0, int var1, FloatBuffer var2) {
      GL11.glTexEnvfv(☃, ☃, ☃);
   }

   public static void func_187399_a(int var0, int var1, int var2) {
      GL11.glTexEnvi(☃, ☃, ☃);
   }

   public static void func_187436_a(int var0, int var1, float var2) {
      GL11.glTexEnvf(☃, ☃, ☃);
   }

   public static void func_187403_b(int var0, int var1, float var2) {
      GL11.glTexParameterf(☃, ☃, ☃);
   }

   public static void func_187421_b(int var0, int var1, int var2) {
      GL11.glTexParameteri(☃, ☃, ☃);
   }

   public static int func_187411_c(int var0, int var1, int var2) {
      return GL11.glGetTexLevelParameteri(☃, ☃, ☃);
   }

   public static int func_179146_y() {
      return GL11.glGenTextures();
   }

   public static void func_179150_h(int var0) {
      GL11.glDeleteTextures(☃);

      for(GlStateManager.TextureState ☃ : field_199304_r) {
         if (☃.field_179059_b == ☃) {
            ☃.field_179059_b = -1;
         }
      }
   }

   public static void func_179144_i(int var0) {
      if (☃ != field_199304_r[field_179162_o].field_179059_b) {
         field_199304_r[field_179162_o].field_179059_b = ☃;
         GL11.glBindTexture(3553, ☃);
      }
   }

   public static void func_187419_a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable IntBuffer var8) {
      GL11.glTexImage2D(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static void func_199298_a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8) {
      GL11.glTexSubImage2D(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static void func_199295_a(int var0, int var1, int var2, int var3, long var4) {
      GL11.glGetTexImage(☃, ☃, ☃, ☃, ☃);
   }

   public static void func_179108_z() {
      field_199303_p.func_179200_b();
   }

   public static void func_179133_A() {
      field_199303_p.func_179198_a();
   }

   public static void func_179103_j(int var0) {
      if (☃ != field_179173_q) {
         field_179173_q = ☃;
         GL11.glShadeModel(☃);
      }
   }

   public static void func_179091_B() {
      field_199305_t.func_179200_b();
   }

   public static void func_179101_C() {
      field_199305_t.func_179198_a();
   }

   public static void func_179083_b(int var0, int var1, int var2, int var3) {
      GlStateManager.Viewport.INSTANCE.field_199289_b = ☃;
      GlStateManager.Viewport.INSTANCE.field_199290_c = ☃;
      GlStateManager.Viewport.INSTANCE.field_199291_d = ☃;
      GlStateManager.Viewport.INSTANCE.field_199292_e = ☃;
      GL11.glViewport(☃, ☃, ☃, ☃);
   }

   public static void func_179135_a(boolean var0, boolean var1, boolean var2, boolean var3) {
      if (☃ != field_199306_u.field_179188_a || ☃ != field_199306_u.field_179186_b || ☃ != field_199306_u.field_179187_c || ☃ != field_199306_u.field_179185_d) {
         field_199306_u.field_179188_a = ☃;
         field_199306_u.field_179186_b = ☃;
         field_199306_u.field_179187_c = ☃;
         field_199306_u.field_179185_d = ☃;
         GL11.glColorMask(☃, ☃, ☃, ☃);
      }
   }

   public static void func_179151_a(double var0) {
      if (☃ != field_179163_l.field_179205_a) {
         field_179163_l.field_179205_a = ☃;
         GL11.glClearDepth(☃);
      }
   }

   public static void func_179082_a(float var0, float var1, float var2, float var3) {
      if (☃ != field_179163_l.field_179203_b.field_179195_a
         || ☃ != field_179163_l.field_179203_b.field_179193_b
         || ☃ != field_179163_l.field_179203_b.field_179194_c
         || ☃ != field_179163_l.field_179203_b.field_179192_d) {
         field_179163_l.field_179203_b.field_179195_a = ☃;
         field_179163_l.field_179203_b.field_179193_b = ☃;
         field_179163_l.field_179203_b.field_179194_c = ☃;
         field_179163_l.field_179203_b.field_179192_d = ☃;
         GL11.glClearColor(☃, ☃, ☃, ☃);
      }
   }

   public static void func_179086_m(int var0) {
      GL11.glClear(☃);
      if (Minecraft.field_142025_a) {
         func_187434_L();
      }
   }

   public static void func_179128_n(int var0) {
      GL11.glMatrixMode(☃);
   }

   public static void func_179096_D() {
      GL11.glLoadIdentity();
   }

   public static void func_179094_E() {
      GL11.glPushMatrix();
   }

   public static void func_179121_F() {
      GL11.glPopMatrix();
   }

   public static void func_179111_a(int var0, FloatBuffer var1) {
      GL11.glGetFloatv(☃, ☃);
   }

   public static void func_179130_a(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glOrtho(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static void func_179114_b(float var0, float var1, float var2, float var3) {
      GL11.glRotatef(☃, ☃, ☃, ☃);
   }

   public static void func_212477_a(double var0, double var2, double var4, double var6) {
      GL11.glRotated(☃, ☃, ☃, ☃);
   }

   public static void func_179152_a(float var0, float var1, float var2) {
      GL11.glScalef(☃, ☃, ☃);
   }

   public static void func_179139_a(double var0, double var2, double var4) {
      GL11.glScaled(☃, ☃, ☃);
   }

   public static void func_179109_b(float var0, float var1, float var2) {
      GL11.glTranslatef(☃, ☃, ☃);
   }

   public static void func_179137_b(double var0, double var2, double var4) {
      GL11.glTranslated(☃, ☃, ☃);
   }

   public static void func_179110_a(FloatBuffer var0) {
      GL11.glMultMatrixf(☃);
   }

   public static void func_199294_a(Matrix4f var0) {
      ☃.func_195879_b(field_187450_a);
      field_187450_a.rewind();
      GL11.glMultMatrixf(field_187450_a);
   }

   public static void func_179131_c(float var0, float var1, float var2, float var3) {
      if (☃ != field_199307_v.field_179195_a || ☃ != field_199307_v.field_179193_b || ☃ != field_199307_v.field_179194_c || ☃ != field_199307_v.field_179192_d) {
         field_199307_v.field_179195_a = ☃;
         field_199307_v.field_179193_b = ☃;
         field_199307_v.field_179194_c = ☃;
         field_199307_v.field_179192_d = ☃;
         GL11.glColor4f(☃, ☃, ☃, ☃);
      }
   }

   public static void func_179124_c(float var0, float var1, float var2) {
      func_179131_c(☃, ☃, ☃, 1.0F);
   }

   public static void func_179117_G() {
      field_199307_v.field_179195_a = -1.0F;
      field_199307_v.field_179193_b = -1.0F;
      field_199307_v.field_179194_c = -1.0F;
      field_199307_v.field_179192_d = -1.0F;
   }

   public static void func_204611_f(int var0, int var1, int var2) {
      GL11.glNormalPointer(☃, ☃, (long)☃);
   }

   public static void func_187446_a(int var0, int var1, ByteBuffer var2) {
      GL11.glNormalPointer(☃, ☃, ☃);
   }

   public static void func_187405_c(int var0, int var1, int var2, int var3) {
      GL11.glTexCoordPointer(☃, ☃, ☃, (long)☃);
   }

   public static void func_187404_a(int var0, int var1, int var2, ByteBuffer var3) {
      GL11.glTexCoordPointer(☃, ☃, ☃, ☃);
   }

   public static void func_187420_d(int var0, int var1, int var2, int var3) {
      GL11.glVertexPointer(☃, ☃, ☃, (long)☃);
   }

   public static void func_187427_b(int var0, int var1, int var2, ByteBuffer var3) {
      GL11.glVertexPointer(☃, ☃, ☃, ☃);
   }

   public static void func_187406_e(int var0, int var1, int var2, int var3) {
      GL11.glColorPointer(☃, ☃, ☃, (long)☃);
   }

   public static void func_187400_c(int var0, int var1, int var2, ByteBuffer var3) {
      GL11.glColorPointer(☃, ☃, ☃, ☃);
   }

   public static void func_187429_p(int var0) {
      GL11.glDisableClientState(☃);
   }

   public static void func_187410_q(int var0) {
      GL11.glEnableClientState(☃);
   }

   public static void func_187439_f(int var0, int var1, int var2) {
      GL11.glDrawArrays(☃, ☃, ☃);
   }

   public static void func_187441_d(float var0) {
      GL11.glLineWidth(☃);
   }

   public static void func_179148_o(int var0) {
      GL11.glCallList(☃);
   }

   public static void func_187449_e(int var0, int var1) {
      GL11.glDeleteLists(☃, ☃);
   }

   public static void func_187423_f(int var0, int var1) {
      GL11.glNewList(☃, ☃);
   }

   public static void func_187415_K() {
      GL11.glEndList();
   }

   public static int func_187442_t(int var0) {
      return GL11.glGenLists(☃);
   }

   public static void func_187425_g(int var0, int var1) {
      GL11.glPixelStorei(☃, ☃);
   }

   public static void func_199297_b(int var0, float var1) {
      GL11.glPixelTransferf(☃, ☃);
   }

   public static void func_199296_a(int var0, int var1, int var2, int var3, int var4, int var5, long var6) {
      GL11.glReadPixels(☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public static int func_187434_L() {
      return GL11.glGetError();
   }

   public static String func_187416_u(int var0) {
      return GL11.glGetString(☃);
   }

   public static void func_187408_a(GlStateManager.Profile var0) {
      ☃.func_187373_a();
   }

   public static void func_187440_b(GlStateManager.Profile var0) {
      ☃.func_187374_b();
   }

   static class AlphaState {
      public GlStateManager.BooleanState field_179208_a = new GlStateManager.BooleanState(3008);
      public int field_179206_b = 519;
      public float field_179207_c = -1.0F;

      private AlphaState() {
      }
   }

   static class BlendState {
      public GlStateManager.BooleanState field_179213_a = new GlStateManager.BooleanState(3042);
      public int field_179211_b = 1;
      public int field_179212_c = 0;
      public int field_179209_d = 1;
      public int field_179210_e = 0;

      private BlendState() {
      }
   }

   static class BooleanState {
      private final int field_179202_a;
      private boolean field_179201_b;

      public BooleanState(int var1) {
         this.field_179202_a = ☃;
      }

      public void func_179198_a() {
         this.func_179199_a(false);
      }

      public void func_179200_b() {
         this.func_179199_a(true);
      }

      public void func_179199_a(boolean var1) {
         if (☃ != this.field_179201_b) {
            this.field_179201_b = ☃;
            if (☃) {
               GL11.glEnable(this.field_179202_a);
            } else {
               GL11.glDisable(this.field_179202_a);
            }
         }
      }
   }

   static class ClearState {
      public double field_179205_a = 1.0;
      public GlStateManager.Color field_179203_b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);

      private ClearState() {
      }
   }

   static class Color {
      public float field_179195_a = 1.0F;
      public float field_179193_b = 1.0F;
      public float field_179194_c = 1.0F;
      public float field_179192_d = 1.0F;

      public Color() {
         this(1.0F, 1.0F, 1.0F, 1.0F);
      }

      public Color(float var1, float var2, float var3, float var4) {
         this.field_179195_a = ☃;
         this.field_179193_b = ☃;
         this.field_179194_c = ☃;
         this.field_179192_d = ☃;
      }
   }

   static class ColorLogicState {
      public GlStateManager.BooleanState field_179197_a = new GlStateManager.BooleanState(3058);
      public int field_179196_b = 5379;

      private ColorLogicState() {
      }
   }

   static class ColorMask {
      public boolean field_179188_a = true;
      public boolean field_179186_b = true;
      public boolean field_179187_c = true;
      public boolean field_179185_d = true;

      private ColorMask() {
      }
   }

   static class ColorMaterialState {
      public GlStateManager.BooleanState field_179191_a = new GlStateManager.BooleanState(2903);
      public int field_179189_b = 1032;
      public int field_179190_c = 5634;

      private ColorMaterialState() {
      }
   }

   public static enum CullFace {
      FRONT(1028),
      BACK(1029),
      FRONT_AND_BACK(1032);

      public final int field_187328_d;

      private CullFace(int var3) {
         this.field_187328_d = ☃;
      }
   }

   static class CullState {
      public GlStateManager.BooleanState field_179054_a = new GlStateManager.BooleanState(2884);
      public int field_179053_b = 1029;

      private CullState() {
      }
   }

   static class DepthState {
      public GlStateManager.BooleanState field_179052_a = new GlStateManager.BooleanState(2929);
      public boolean field_179050_b = true;
      public int field_179051_c = 513;

      private DepthState() {
      }
   }

   public static enum DestFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_COLOR(768),
      ZERO(0);

      public final int field_187345_o;

      private DestFactor(int var3) {
         this.field_187345_o = ☃;
      }
   }

   public static enum FogMode {
      LINEAR(9729),
      EXP(2048),
      EXP2(2049);

      public final int field_187351_d;

      private FogMode(int var3) {
         this.field_187351_d = ☃;
      }
   }

   static class FogState {
      public GlStateManager.BooleanState field_179049_a = new GlStateManager.BooleanState(2912);
      public int field_179047_b = 2048;
      public float field_179048_c = 1.0F;
      public float field_179045_d;
      public float field_179046_e = 1.0F;

      private FogState() {
      }
   }

   public static enum LogicOp {
      AND(5377),
      AND_INVERTED(5380),
      AND_REVERSE(5378),
      CLEAR(5376),
      COPY(5379),
      COPY_INVERTED(5388),
      EQUIV(5385),
      INVERT(5386),
      NAND(5390),
      NOOP(5381),
      NOR(5384),
      OR(5383),
      OR_INVERTED(5389),
      OR_REVERSE(5387),
      SET(5391),
      XOR(5382);

      public final int field_187370_q;

      private LogicOp(int var3) {
         this.field_187370_q = ☃;
      }
   }

   static class PolygonOffsetState {
      public GlStateManager.BooleanState field_179044_a = new GlStateManager.BooleanState(32823);
      public GlStateManager.BooleanState field_179042_b = new GlStateManager.BooleanState(10754);
      public float field_179043_c;
      public float field_179041_d;

      private PolygonOffsetState() {
      }
   }

   public static enum Profile {
      DEFAULT {
         @Override
         public void func_187373_a() {
            GlStateManager.func_179118_c();
            GlStateManager.func_179092_a(519, 0.0F);
            GlStateManager.func_179140_f();
            GlStateManager.func_187424_a(2899, RenderHelper.func_74521_a(0.2F, 0.2F, 0.2F, 1.0F));

            for(int ☃ = 0; ☃ < 8; ++☃) {
               GlStateManager.func_179122_b(☃);
               GlStateManager.func_187438_a(16384 + ☃, 4608, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
               GlStateManager.func_187438_a(16384 + ☃, 4611, RenderHelper.func_74521_a(0.0F, 0.0F, 1.0F, 0.0F));
               if (☃ == 0) {
                  GlStateManager.func_187438_a(16384 + ☃, 4609, RenderHelper.func_74521_a(1.0F, 1.0F, 1.0F, 1.0F));
                  GlStateManager.func_187438_a(16384 + ☃, 4610, RenderHelper.func_74521_a(1.0F, 1.0F, 1.0F, 1.0F));
               } else {
                  GlStateManager.func_187438_a(16384 + ☃, 4609, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
                  GlStateManager.func_187438_a(16384 + ☃, 4610, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 1.0F));
               }
            }

            GlStateManager.func_179119_h();
            GlStateManager.func_179104_a(1032, 5634);
            GlStateManager.func_179097_i();
            GlStateManager.func_179143_c(513);
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179084_k();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.func_187428_a(
               GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
            );
            GlStateManager.func_187398_d(32774);
            GlStateManager.func_179106_n();
            GlStateManager.func_187412_c(2917, 2048);
            GlStateManager.func_179095_a(1.0F);
            GlStateManager.func_179102_b(0.0F);
            GlStateManager.func_179153_c(1.0F);
            GlStateManager.func_187402_b(2918, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            if (GL.getCapabilities().GL_NV_fog_distance) {
               GlStateManager.func_187412_c(2917, 34140);
            }

            GlStateManager.func_179136_a(0.0F, 0.0F);
            GlStateManager.func_179134_v();
            GlStateManager.func_179116_f(5379);
            GlStateManager.func_179100_b(GlStateManager.TexGen.S);
            GlStateManager.func_179149_a(GlStateManager.TexGen.S, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9474, RenderHelper.func_74521_a(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9217, RenderHelper.func_74521_a(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179100_b(GlStateManager.TexGen.T);
            GlStateManager.func_179149_a(GlStateManager.TexGen.T, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9474, RenderHelper.func_74521_a(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9217, RenderHelper.func_74521_a(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.func_179100_b(GlStateManager.TexGen.R);
            GlStateManager.func_179149_a(GlStateManager.TexGen.R, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9474, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9217, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179100_b(GlStateManager.TexGen.Q);
            GlStateManager.func_179149_a(GlStateManager.TexGen.Q, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9217, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179138_g(0);
            GlStateManager.func_187421_b(3553, 10240, 9729);
            GlStateManager.func_187421_b(3553, 10241, 9986);
            GlStateManager.func_187421_b(3553, 10242, 10497);
            GlStateManager.func_187421_b(3553, 10243, 10497);
            GlStateManager.func_187421_b(3553, 33085, 1000);
            GlStateManager.func_187421_b(3553, 33083, 1000);
            GlStateManager.func_187421_b(3553, 33082, -1000);
            GlStateManager.func_187403_b(3553, 34049, 0.0F);
            GlStateManager.func_187399_a(8960, 8704, 8448);
            GlStateManager.func_187448_b(8960, 8705, RenderHelper.func_74521_a(0.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_187399_a(8960, 34161, 8448);
            GlStateManager.func_187399_a(8960, 34162, 8448);
            GlStateManager.func_187399_a(8960, 34176, 5890);
            GlStateManager.func_187399_a(8960, 34177, 34168);
            GlStateManager.func_187399_a(8960, 34178, 34166);
            GlStateManager.func_187399_a(8960, 34184, 5890);
            GlStateManager.func_187399_a(8960, 34185, 34168);
            GlStateManager.func_187399_a(8960, 34186, 34166);
            GlStateManager.func_187399_a(8960, 34192, 768);
            GlStateManager.func_187399_a(8960, 34193, 768);
            GlStateManager.func_187399_a(8960, 34194, 770);
            GlStateManager.func_187399_a(8960, 34200, 770);
            GlStateManager.func_187399_a(8960, 34201, 770);
            GlStateManager.func_187399_a(8960, 34202, 770);
            GlStateManager.func_187436_a(8960, 34163, 1.0F);
            GlStateManager.func_187436_a(8960, 3356, 1.0F);
            GlStateManager.func_179133_A();
            GlStateManager.func_179103_j(7425);
            GlStateManager.func_179101_C();
            GlStateManager.func_179135_a(true, true, true, true);
            GlStateManager.func_179151_a(1.0);
            GlStateManager.func_187441_d(1.0F);
            GlStateManager.func_187432_a(0.0F, 0.0F, 1.0F);
            GlStateManager.func_187409_d(1028, 6914);
            GlStateManager.func_187409_d(1029, 6914);
         }

         @Override
         public void func_187374_b() {
         }
      },
      PLAYER_SKIN {
         @Override
         public void func_187373_a() {
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
         }

         @Override
         public void func_187374_b() {
            GlStateManager.func_179084_k();
         }
      },
      TRANSPARENT_MODEL {
         @Override
         public void func_187373_a() {
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179147_l();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.func_179092_a(516, 0.003921569F);
         }

         @Override
         public void func_187374_b() {
            GlStateManager.func_179084_k();
            GlStateManager.func_179092_a(516, 0.1F);
            GlStateManager.func_179132_a(true);
         }
      };

      private Profile() {
      }

      public abstract void func_187373_a();

      public abstract void func_187374_b();
   }

   public static enum SourceFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_ALPHA_SATURATE(776),
      SRC_COLOR(768),
      ZERO(0);

      public final int field_187395_p;

      private SourceFactor(int var3) {
         this.field_187395_p = ☃;
      }
   }

   static class StencilFunc {
      public int field_179081_a = 519;
      public int field_179080_c = -1;

      private StencilFunc() {
      }
   }

   static class StencilState {
      public GlStateManager.StencilFunc field_179078_a = new GlStateManager.StencilFunc();
      public int field_179076_b = -1;
      public int field_179077_c = 7680;
      public int field_179074_d = 7680;
      public int field_179075_e = 7680;

      private StencilState() {
      }
   }

   public static enum TexGen {
      S,
      T,
      R,
      Q;
   }

   static class TexGenCoord {
      public GlStateManager.BooleanState field_179067_a;
      public int field_179065_b;
      public int field_179066_c = -1;

      public TexGenCoord(int var1, int var2) {
         this.field_179065_b = ☃;
         this.field_179067_a = new GlStateManager.BooleanState(☃);
      }
   }

   static class TexGenState {
      public GlStateManager.TexGenCoord field_179064_a = new GlStateManager.TexGenCoord(8192, 3168);
      public GlStateManager.TexGenCoord field_179062_b = new GlStateManager.TexGenCoord(8193, 3169);
      public GlStateManager.TexGenCoord field_179063_c = new GlStateManager.TexGenCoord(8194, 3170);
      public GlStateManager.TexGenCoord field_179061_d = new GlStateManager.TexGenCoord(8195, 3171);

      private TexGenState() {
      }
   }

   static class TextureState {
      public GlStateManager.BooleanState field_179060_a = new GlStateManager.BooleanState(3553);
      public int field_179059_b;

      private TextureState() {
      }
   }

   public static enum Viewport {
      INSTANCE;

      protected int field_199289_b;
      protected int field_199290_c;
      protected int field_199291_d;
      protected int field_199292_e;
   }
}
