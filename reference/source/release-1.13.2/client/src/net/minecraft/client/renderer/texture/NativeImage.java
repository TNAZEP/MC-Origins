package net.minecraft.client.renderer.texture;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.LWJGLMemoryUntracker;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class NativeImage implements AutoCloseable {
   private static final Set<StandardOpenOption> field_209272_a = EnumSet.of(
      StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
   );
   private final NativeImage.PixelFormat field_211680_b;
   private final int field_195719_a;
   private final int field_195720_b;
   private final boolean field_195721_c;
   private long field_195722_d;
   private final int field_195723_e;

   public NativeImage(int var1, int var2, boolean var3) {
      this(NativeImage.PixelFormat.RGBA, ☃, ☃, ☃);
   }

   public NativeImage(NativeImage.PixelFormat var1, int var2, int var3, boolean var4) {
      this.field_211680_b = ☃;
      this.field_195719_a = ☃;
      this.field_195720_b = ☃;
      this.field_195723_e = ☃ * ☃ * ☃.func_211651_a();
      this.field_195721_c = false;
      if (☃) {
         this.field_195722_d = MemoryUtil.nmemCalloc(1L, (long)this.field_195723_e);
      } else {
         this.field_195722_d = MemoryUtil.nmemAlloc((long)this.field_195723_e);
      }
   }

   private NativeImage(NativeImage.PixelFormat var1, int var2, int var3, boolean var4, long var5) {
      this.field_211680_b = ☃;
      this.field_195719_a = ☃;
      this.field_195720_b = ☃;
      this.field_195721_c = ☃;
      this.field_195722_d = ☃;
      this.field_195723_e = ☃ * ☃ * ☃.func_211651_a();
   }

   public String toString() {
      return "NativeImage["
         + this.field_211680_b
         + " "
         + this.field_195719_a
         + "x"
         + this.field_195720_b
         + "@"
         + this.field_195722_d
         + (this.field_195721_c ? "S" : "N")
         + "]";
   }

   public static NativeImage func_195713_a(InputStream var0) throws IOException {
      return func_211679_a(NativeImage.PixelFormat.RGBA, ☃);
   }

   public static NativeImage func_211679_a(@Nullable NativeImage.PixelFormat var0, InputStream var1) throws IOException {
      ByteBuffer ☃ = null;

      NativeImage var3;
      try {
         ☃ = TextureUtil.func_195724_a(☃);
         ☃.rewind();
         var3 = func_211677_a(☃, ☃);
      } finally {
         MemoryUtil.memFree(☃);
         IOUtils.closeQuietly(☃);
      }

      return var3;
   }

   public static NativeImage func_195704_a(ByteBuffer var0) throws IOException {
      return func_211677_a(NativeImage.PixelFormat.RGBA, ☃);
   }

   public static NativeImage func_211677_a(@Nullable NativeImage.PixelFormat var0, ByteBuffer var1) throws IOException {
      if (☃ != null && !☃.func_211654_w()) {
         throw new UnsupportedOperationException("Don't know how to read format " + ☃);
      } else if (MemoryUtil.memAddress(☃) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         NativeImage var8;
         try (MemoryStack ☃ = MemoryStack.stackPush()) {
            IntBuffer ☃x = ☃.mallocInt(1);
            IntBuffer ☃xx = ☃.mallocInt(1);
            IntBuffer ☃xxx = ☃.mallocInt(1);
            ByteBuffer ☃xxxx = STBImage.stbi_load_from_memory(☃, ☃x, ☃xx, ☃xxx, ☃ == null ? 0 : ☃.field_211659_e);
            if (☃xxxx == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            var8 = new NativeImage(
               ☃ == null ? NativeImage.PixelFormat.func_211646_b(☃xxx.get(0)) : ☃, ☃x.get(0), ☃xx.get(0), true, MemoryUtil.memAddress(☃xxxx)
            );
         }

         return var8;
      }
   }

   private static void func_195707_b(boolean var0) {
      if (☃) {
         GlStateManager.func_187421_b(3553, 10242, 10496);
         GlStateManager.func_187421_b(3553, 10243, 10496);
      } else {
         GlStateManager.func_187421_b(3553, 10242, 10497);
         GlStateManager.func_187421_b(3553, 10243, 10497);
      }
   }

   private static void func_195705_a(boolean var0, boolean var1) {
      if (☃) {
         GlStateManager.func_187421_b(3553, 10241, ☃ ? 9987 : 9729);
         GlStateManager.func_187421_b(3553, 10240, 9729);
      } else {
         GlStateManager.func_187421_b(3553, 10241, ☃ ? 9986 : 9728);
         GlStateManager.func_187421_b(3553, 10240, 9728);
      }
   }

   private void func_195696_g() {
      if (this.field_195722_d == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   public void close() {
      if (this.field_195722_d != 0L) {
         if (this.field_195721_c) {
            STBImage.nstbi_image_free(this.field_195722_d);
         } else {
            MemoryUtil.nmemFree(this.field_195722_d);
         }
      }

      this.field_195722_d = 0L;
   }

   public int func_195702_a() {
      return this.field_195719_a;
   }

   public int func_195714_b() {
      return this.field_195720_b;
   }

   public NativeImage.PixelFormat func_211678_c() {
      return this.field_211680_b;
   }

   public int func_195709_a(int var1, int var2) {
      if (this.field_211680_b != NativeImage.PixelFormat.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.field_211680_b));
      } else if (☃ <= this.field_195719_a && ☃ <= this.field_195720_b) {
         this.func_195696_g();
         return MemoryUtil.memIntBuffer(this.field_195722_d, this.field_195723_e).get(☃ + ☃ * this.field_195719_a);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", ☃, ☃, this.field_195719_a, this.field_195720_b));
      }
   }

   public void func_195700_a(int var1, int var2, int var3) {
      if (this.field_211680_b != NativeImage.PixelFormat.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.field_211680_b));
      } else if (☃ <= this.field_195719_a && ☃ <= this.field_195720_b) {
         this.func_195696_g();
         MemoryUtil.memIntBuffer(this.field_195722_d, this.field_195723_e).put(☃ + ☃ * this.field_195719_a, ☃);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", ☃, ☃, this.field_195719_a, this.field_195720_b));
      }
   }

   public byte func_211675_e(int var1, int var2) {
      if (!this.field_211680_b.func_211653_r()) {
         throw new IllegalArgumentException(String.format("no luminance or alpha in %s", this.field_211680_b));
      } else if (☃ <= this.field_195719_a && ☃ <= this.field_195720_b) {
         return MemoryUtil.memByteBuffer(this.field_195722_d, this.field_195723_e)
            .get((☃ + ☃ * this.field_195719_a) * this.field_211680_b.func_211651_a() + this.field_211680_b.func_211647_v() / 8);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", ☃, ☃, this.field_195719_a, this.field_195720_b));
      }
   }

   public void func_195718_b(int var1, int var2, int var3) {
      if (this.field_211680_b != NativeImage.PixelFormat.RGBA) {
         throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
      } else {
         int ☃ = this.func_195709_a(☃, ☃);
         float ☃x = (float)(☃ >> 24 & 0xFF) / 255.0F;
         float ☃xx = (float)(☃ >> 16 & 0xFF) / 255.0F;
         float ☃xxx = (float)(☃ >> 8 & 0xFF) / 255.0F;
         float ☃xxxx = (float)(☃ >> 0 & 0xFF) / 255.0F;
         float ☃xxxxx = (float)(☃ >> 24 & 0xFF) / 255.0F;
         float ☃xxxxxx = (float)(☃ >> 16 & 0xFF) / 255.0F;
         float ☃xxxxxxx = (float)(☃ >> 8 & 0xFF) / 255.0F;
         float ☃xxxxxxxx = (float)(☃ >> 0 & 0xFF) / 255.0F;
         float ☃xxxxxxxxx = 1.0F - ☃x;
         float ☃xxxxxxxxxx = ☃x * ☃x + ☃xxxxx * ☃xxxxxxxxx;
         float ☃xxxxxxxxxxx = ☃xx * ☃x + ☃xxxxxx * ☃xxxxxxxxx;
         float ☃xxxxxxxxxxxx = ☃xxx * ☃x + ☃xxxxxxx * ☃xxxxxxxxx;
         float ☃xxxxxxxxxxxxx = ☃xxxx * ☃x + ☃xxxxxxxx * ☃xxxxxxxxx;
         if (☃xxxxxxxxxx > 1.0F) {
            ☃xxxxxxxxxx = 1.0F;
         }

         if (☃xxxxxxxxxxx > 1.0F) {
            ☃xxxxxxxxxxx = 1.0F;
         }

         if (☃xxxxxxxxxxxx > 1.0F) {
            ☃xxxxxxxxxxxx = 1.0F;
         }

         if (☃xxxxxxxxxxxxx > 1.0F) {
            ☃xxxxxxxxxxxxx = 1.0F;
         }

         int ☃ = (int)(☃xxxxxxxxxx * 255.0F);
         int ☃x = (int)(☃xxxxxxxxxxx * 255.0F);
         int ☃xx = (int)(☃xxxxxxxxxxxx * 255.0F);
         int ☃xxx = (int)(☃xxxxxxxxxxxxx * 255.0F);
         this.func_195700_a(☃, ☃, ☃ << 24 | ☃x << 16 | ☃xx << 8 | ☃xxx << 0);
      }
   }

   @Deprecated
   public int[] func_195716_c() {
      if (this.field_211680_b != NativeImage.PixelFormat.RGBA) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.func_195696_g();
         int[] ☃ = new int[this.func_195702_a() * this.func_195714_b()];

         for(int ☃x = 0; ☃x < this.func_195714_b(); ++☃x) {
            for(int ☃xx = 0; ☃xx < this.func_195702_a(); ++☃xx) {
               int ☃xxx = this.func_195709_a(☃xx, ☃x);
               int ☃xxxx = ☃xxx >> 24 & 0xFF;
               int ☃xxxxx = ☃xxx >> 16 & 0xFF;
               int ☃xxxxxx = ☃xxx >> 8 & 0xFF;
               int ☃xxxxxxx = ☃xxx >> 0 & 0xFF;
               int ☃xxxxxxxx = ☃xxxx << 24 | ☃xxxxxxx << 16 | ☃xxxxxx << 8 | ☃xxxxx;
               ☃[☃xx + ☃x * this.func_195702_a()] = ☃xxxxxxxx;
            }
         }

         return ☃;
      }
   }

   public void func_195697_a(int var1, int var2, int var3, boolean var4) {
      this.func_195706_a(☃, ☃, ☃, 0, 0, this.field_195719_a, this.field_195720_b, ☃);
   }

   public void func_195706_a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.func_195712_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, false, false, ☃);
   }

   public void func_195712_a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10) {
      this.func_195696_g();
      func_195705_a(☃, ☃);
      func_195707_b(☃);
      if (☃ == this.func_195702_a()) {
         GlStateManager.func_187425_g(3314, 0);
      } else {
         GlStateManager.func_187425_g(3314, this.func_195702_a());
      }

      GlStateManager.func_187425_g(3316, ☃);
      GlStateManager.func_187425_g(3315, ☃);
      this.field_211680_b.func_211658_c();
      GlStateManager.func_199298_a(3553, ☃, ☃, ☃, ☃, ☃, this.field_211680_b.func_211650_d(), 5121, this.field_195722_d);
   }

   public void func_195717_a(int var1, boolean var2) {
      this.func_195696_g();
      this.field_211680_b.func_211656_b();
      GlStateManager.func_199295_a(3553, ☃, this.field_211680_b.func_211650_d(), 5121, this.field_195722_d);
      if (☃ && this.field_211680_b.func_211645_i()) {
         for(int ☃ = 0; ☃ < this.func_195714_b(); ++☃) {
            for(int ☃x = 0; ☃x < this.func_195702_a(); ++☃x) {
               this.func_195700_a(☃x, ☃, this.func_195709_a(☃x, ☃) | 255 << this.field_211680_b.func_211648_n());
            }
         }
      }
   }

   public void func_195701_a(boolean var1) {
      this.func_195696_g();
      this.field_211680_b.func_211656_b();
      if (☃) {
         GlStateManager.func_199297_b(3357, Float.MAX_VALUE);
      }

      GlStateManager.func_199296_a(0, 0, this.field_195719_a, this.field_195720_b, this.field_211680_b.func_211650_d(), 5121, this.field_195722_d);
      if (☃) {
         GlStateManager.func_199297_b(3357, 0.0F);
      }
   }

   public void func_209271_a(File var1) throws IOException {
      this.func_209270_a(☃.toPath());
   }

   public void func_211676_a(STBTTFontinfo var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9, int var10) {
      if (☃ < 0 || ☃ + ☃ > this.func_195702_a() || ☃ < 0 || ☃ + ☃ > this.func_195714_b()) {
         throw new IllegalArgumentException(
            String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", ☃, ☃, ☃, ☃, this.func_195702_a(), this.func_195714_b())
         );
      } else if (this.field_211680_b.func_211651_a() != 1) {
         throw new IllegalArgumentException("Can only write fonts into 1-component images.");
      } else {
         STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(
            ☃.address(), this.field_195722_d + (long)☃ + (long)(☃ * this.func_195702_a()), ☃, ☃, this.func_195702_a(), ☃, ☃, ☃, ☃, ☃
         );
      }
   }

   public void func_209270_a(Path var1) throws IOException {
      if (!this.field_211680_b.func_211654_w()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.field_211680_b);
      } else {
         this.func_195696_g();
         WritableByteChannel ☃ = Files.newByteChannel(☃, field_209272_a);
         Throwable var3 = null;

         try {
            NativeImage.WriteCallback ☃x = new NativeImage.WriteCallback(☃);

            try {
               if (!STBImageWrite.stbi_write_png_to_func(
                  ☃x,
                  0L,
                  this.func_195702_a(),
                  this.func_195714_b(),
                  this.field_211680_b.func_211651_a(),
                  MemoryUtil.memByteBuffer(this.field_195722_d, this.field_195723_e),
                  0
               )) {
                  throw new IOException("Could not write image to the PNG file \"" + ☃.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
               }
            } finally {
               ☃x.free();
            }

            ☃x.func_209267_a();
         } catch (Throwable var19) {
            var3 = var19;
            throw var19;
         } finally {
            if (☃ != null) {
               if (var3 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var17) {
                     var3.addSuppressed(var17);
                  }
               } else {
                  ☃.close();
               }
            }
         }
      }
   }

   public void func_195703_a(NativeImage var1) {
      if (☃.func_211678_c() != this.field_211680_b) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int ☃ = this.field_211680_b.func_211651_a();
         this.func_195696_g();
         ☃.func_195696_g();
         if (this.field_195719_a == ☃.field_195719_a) {
            MemoryUtil.memCopy(☃.field_195722_d, this.field_195722_d, (long)Math.min(this.field_195723_e, ☃.field_195723_e));
         } else {
            int ☃ = Math.min(this.func_195702_a(), ☃.func_195702_a());
            int ☃x = Math.min(this.func_195714_b(), ☃.func_195714_b());

            for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
               int ☃xxx = ☃xx * ☃.func_195702_a() * ☃;
               int ☃xxxx = ☃xx * this.func_195702_a() * ☃;
               MemoryUtil.memCopy(☃.field_195722_d + (long)☃xxx, this.field_195722_d + (long)☃xxxx, (long)☃);
            }
         }
      }
   }

   public void func_195715_a(int var1, int var2, int var3, int var4, int var5) {
      for(int ☃ = ☃; ☃ < ☃ + ☃; ++☃) {
         for(int ☃x = ☃; ☃x < ☃ + ☃; ++☃x) {
            this.func_195700_a(☃x, ☃, ☃);
         }
      }
   }

   public void func_195699_a(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, boolean var8) {
      for(int ☃ = 0; ☃ < ☃; ++☃) {
         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            int ☃xx = ☃ ? ☃ - 1 - ☃x : ☃x;
            int ☃xxx = ☃ ? ☃ - 1 - ☃ : ☃;
            int ☃xxxx = this.func_195709_a(☃ + ☃x, ☃ + ☃);
            this.func_195700_a(☃ + ☃ + ☃xx, ☃ + ☃ + ☃xxx, ☃xxxx);
         }
      }
   }

   public void func_195710_e() {
      this.func_195696_g();

      try (MemoryStack ☃ = MemoryStack.stackPush()) {
         int ☃x = this.field_211680_b.func_211651_a();
         int ☃xx = this.func_195702_a() * ☃x;
         long ☃xxx = ☃.nmalloc(☃xx);

         for(int ☃xxxx = 0; ☃xxxx < this.func_195714_b() / 2; ++☃xxxx) {
            int ☃xxxxx = ☃xxxx * this.func_195702_a() * ☃x;
            int ☃xxxxxx = (this.func_195714_b() - 1 - ☃xxxx) * this.func_195702_a() * ☃x;
            MemoryUtil.memCopy(this.field_195722_d + (long)☃xxxxx, ☃xxx, (long)☃xx);
            MemoryUtil.memCopy(this.field_195722_d + (long)☃xxxxxx, this.field_195722_d + (long)☃xxxxx, (long)☃xx);
            MemoryUtil.memCopy(☃xxx, this.field_195722_d + (long)☃xxxxxx, (long)☃xx);
         }
      }
   }

   public void func_195708_a(int var1, int var2, int var3, int var4, NativeImage var5) {
      this.func_195696_g();
      if (☃.func_211678_c() != this.field_211680_b) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int ☃ = this.field_211680_b.func_211651_a();
         STBImageResize.nstbir_resize_uint8(
            this.field_195722_d + (long)((☃ + ☃ * this.func_195702_a()) * ☃),
            ☃,
            ☃,
            this.func_195702_a() * ☃,
            ☃.field_195722_d,
            ☃.func_195702_a(),
            ☃.func_195714_b(),
            0,
            ☃
         );
      }
   }

   public void func_195711_f() {
      LWJGLMemoryUntracker.func_197933_a(this.field_195722_d);
   }

   public static enum PixelFormat {
      RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      LUMINANCE_ALPHA(2, 6410, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      LUMINANCE(1, 6409, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      private final int field_211659_e;
      private final int field_211660_f;
      private final boolean field_211661_g;
      private final boolean field_211662_h;
      private final boolean field_211663_i;
      private final boolean field_211664_j;
      private final boolean field_211665_k;
      private final int field_211666_l;
      private final int field_211667_m;
      private final int field_211668_n;
      private final int field_211669_o;
      private final int field_211670_p;
      private final boolean field_211671_q;

      private PixelFormat(
         int var3,
         int var4,
         boolean var5,
         boolean var6,
         boolean var7,
         boolean var8,
         boolean var9,
         int var10,
         int var11,
         int var12,
         int var13,
         int var14,
         boolean var15
      ) {
         this.field_211659_e = ☃;
         this.field_211660_f = ☃;
         this.field_211661_g = ☃;
         this.field_211662_h = ☃;
         this.field_211663_i = ☃;
         this.field_211664_j = ☃;
         this.field_211665_k = ☃;
         this.field_211666_l = ☃;
         this.field_211667_m = ☃;
         this.field_211668_n = ☃;
         this.field_211669_o = ☃;
         this.field_211670_p = ☃;
         this.field_211671_q = ☃;
      }

      public int func_211651_a() {
         return this.field_211659_e;
      }

      public void func_211656_b() {
         GlStateManager.func_187425_g(3333, this.func_211651_a());
      }

      public void func_211658_c() {
         GlStateManager.func_187425_g(3317, this.func_211651_a());
      }

      public int func_211650_d() {
         return this.field_211660_f;
      }

      public boolean func_211645_i() {
         return this.field_211665_k;
      }

      public int func_211648_n() {
         return this.field_211670_p;
      }

      public boolean func_211653_r() {
         return this.field_211664_j || this.field_211665_k;
      }

      public int func_211647_v() {
         return this.field_211664_j ? this.field_211669_o : this.field_211670_p;
      }

      public boolean func_211654_w() {
         return this.field_211671_q;
      }

      private static NativeImage.PixelFormat func_211646_b(int var0) {
         switch(☃) {
            case 1:
               return LUMINANCE;
            case 2:
               return LUMINANCE_ALPHA;
            case 3:
               return RGB;
            case 4:
            default:
               return RGBA;
         }
      }
   }

   public static enum PixelFormatGLCode {
      RGBA(6408),
      RGB(6407),
      LUMINANCE_ALPHA(6410),
      LUMINANCE(6409),
      INTENSITY(32841);

      private final int field_211673_f;

      private PixelFormatGLCode(int var3) {
         this.field_211673_f = ☃;
      }

      int func_211672_a() {
         return this.field_211673_f;
      }
   }

   static class WriteCallback extends STBIWriteCallback {
      private final WritableByteChannel field_209268_a;
      private IOException field_209269_b;

      private WriteCallback(WritableByteChannel var1) {
         this.field_209268_a = ☃;
      }

      @Override
      public void invoke(long var1, long var3, int var5) {
         ByteBuffer ☃ = getData(☃, ☃);

         try {
            this.field_209268_a.write(☃);
         } catch (IOException var8) {
            this.field_209269_b = var8;
         }
      }

      public void func_209267_a() throws IOException {
         if (this.field_209269_b != null) {
            throw this.field_209269_b;
         }
      }
   }
}
