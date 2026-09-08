package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class NativeImage implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int OFFSET_A = 24;
   private static final int OFFSET_B = 16;
   private static final int OFFSET_G = 8;
   private static final int OFFSET_R = 0;
   private static final Set<StandardOpenOption> OPEN_OPTIONS = EnumSet.of(
      StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
   );
   private final NativeImage.Format format;
   private final int width;
   private final int height;
   private final boolean useStbFree;
   private long pixels;
   private final long size;

   public NativeImage(int var1, int var2, boolean var3) {
      this(NativeImage.Format.RGBA, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public NativeImage(NativeImage.Format var1, int var2, int var3, boolean var4) {
      if (â˜ƒ > 0 && â˜ƒ > 0) {
         this.format = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.size = (long)â˜ƒ * (long)â˜ƒ * (long)â˜ƒ.components();
         this.useStbFree = false;
         if (â˜ƒ) {
            this.pixels = MemoryUtil.nmemCalloc(1L, this.size);
         } else {
            this.pixels = MemoryUtil.nmemAlloc(this.size);
         }
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + â˜ƒ + "x" + â˜ƒ);
      }
   }

   private NativeImage(NativeImage.Format var1, int var2, int var3, boolean var4, long var5) {
      if (â˜ƒ > 0 && â˜ƒ > 0) {
         this.format = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.useStbFree = â˜ƒ;
         this.pixels = â˜ƒ;
         this.size = (long)â˜ƒ * (long)â˜ƒ * (long)â˜ƒ.components();
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + â˜ƒ + "x" + â˜ƒ);
      }
   }

   public String toString() {
      return "NativeImage[" + this.format + " " + this.width + "x" + this.height + "@" + this.pixels + (this.useStbFree ? "S" : "N") + "]";
   }

   private boolean isOutsideBounds(int var1, int var2) {
      return â˜ƒ < 0 || â˜ƒ >= this.width || â˜ƒ < 0 || â˜ƒ >= this.height;
   }

   public static NativeImage read(InputStream var0) throws IOException {
      return read(NativeImage.Format.RGBA, â˜ƒ);
   }

   public static NativeImage read(@Nullable NativeImage.Format var0, InputStream var1) throws IOException {
      ByteBuffer â˜ƒ = null;

      NativeImage var3;
      try {
         â˜ƒ = TextureUtil.readResource(â˜ƒ);
         â˜ƒ.rewind();
         var3 = read(â˜ƒ, â˜ƒ);
      } finally {
         MemoryUtil.memFree(â˜ƒ);
         IOUtils.closeQuietly(â˜ƒ);
      }

      return var3;
   }

   public static NativeImage read(ByteBuffer var0) throws IOException {
      return read(NativeImage.Format.RGBA, â˜ƒ);
   }

   public static NativeImage read(@Nullable NativeImage.Format var0, ByteBuffer var1) throws IOException {
      if (â˜ƒ != null && !â˜ƒ.supportedByStb()) {
         throw new UnsupportedOperationException("Don't know how to read format " + â˜ƒ);
      } else if (MemoryUtil.memAddress(â˜ƒ) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         NativeImage var7;
         try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
            IntBuffer â˜ƒx = â˜ƒ.mallocInt(1);
            IntBuffer â˜ƒxx = â˜ƒ.mallocInt(1);
            IntBuffer â˜ƒxxx = â˜ƒ.mallocInt(1);
            ByteBuffer â˜ƒxxxx = STBImage.stbi_load_from_memory(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ == null ? 0 : â˜ƒ.components);
            if (â˜ƒxxxx == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            var7 = new NativeImage(
               â˜ƒ == null ? NativeImage.Format.getStbFormat(â˜ƒxxx.get(0)) : â˜ƒ, â˜ƒx.get(0), â˜ƒxx.get(0), true, MemoryUtil.memAddress(â˜ƒxxxx)
            );
         }

         return var7;
      }
   }

   private static void setFilter(boolean var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (â˜ƒ) {
         GlStateManager._texParameter(3553, 10241, â˜ƒ ? 9987 : 9729);
         GlStateManager._texParameter(3553, 10240, 9729);
      } else {
         GlStateManager._texParameter(3553, 10241, â˜ƒ ? 9986 : 9728);
         GlStateManager._texParameter(3553, 10240, 9728);
      }
   }

   private void checkAllocated() {
      if (this.pixels == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   public void close() {
      if (this.pixels != 0L) {
         if (this.useStbFree) {
            STBImage.nstbi_image_free(this.pixels);
         } else {
            MemoryUtil.nmemFree(this.pixels);
         }
      }

      this.pixels = 0L;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public NativeImage.Format format() {
      return this.format;
   }

   public int getPixelRGBA(int var1, int var2) {
      if (this.format != NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         this.checkAllocated();
         long â˜ƒ = ((long)â˜ƒ + (long)â˜ƒ * (long)this.width) * 4L;
         return MemoryUtil.memGetInt(this.pixels + â˜ƒ);
      }
   }

   public void setPixelRGBA(int var1, int var2, int var3) {
      if (this.format != NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         this.checkAllocated();
         long â˜ƒ = ((long)â˜ƒ + (long)â˜ƒ * (long)this.width) * 4L;
         MemoryUtil.memPutInt(this.pixels + â˜ƒ, â˜ƒ);
      }
   }

   public void setPixelLuminance(int var1, int var2, byte var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (!this.format.hasLuminance()) {
         throw new IllegalArgumentException(String.format("setPixelLuminance only works on image with luminance; have %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         this.checkAllocated();
         long â˜ƒ = ((long)â˜ƒ + (long)â˜ƒ * (long)this.width) * (long)this.format.components() + (long)(this.format.luminanceOffset() / 8);
         MemoryUtil.memPutByte(this.pixels + â˜ƒ, â˜ƒ);
      }
   }

   public byte getRedOrLuminance(int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (!this.format.hasLuminanceOrRed()) {
         throw new IllegalArgumentException(String.format("no red or luminance in %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         int â˜ƒ = (â˜ƒ + â˜ƒ * this.width) * this.format.components() + this.format.luminanceOrRedOffset() / 8;
         return MemoryUtil.memGetByte(this.pixels + (long)â˜ƒ);
      }
   }

   public byte getGreenOrLuminance(int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (!this.format.hasLuminanceOrGreen()) {
         throw new IllegalArgumentException(String.format("no green or luminance in %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         int â˜ƒ = (â˜ƒ + â˜ƒ * this.width) * this.format.components() + this.format.luminanceOrGreenOffset() / 8;
         return MemoryUtil.memGetByte(this.pixels + (long)â˜ƒ);
      }
   }

   public byte getBlueOrLuminance(int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (!this.format.hasLuminanceOrBlue()) {
         throw new IllegalArgumentException(String.format("no blue or luminance in %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         int â˜ƒ = (â˜ƒ + â˜ƒ * this.width) * this.format.components() + this.format.luminanceOrBlueOffset() / 8;
         return MemoryUtil.memGetByte(this.pixels + (long)â˜ƒ);
      }
   }

   public byte getLuminanceOrAlpha(int var1, int var2) {
      if (!this.format.hasLuminanceOrAlpha()) {
         throw new IllegalArgumentException(String.format("no luminance or alpha in %s", this.format));
      } else if (this.isOutsideBounds(â˜ƒ, â˜ƒ)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", â˜ƒ, â˜ƒ, this.width, this.height));
      } else {
         int â˜ƒ = (â˜ƒ + â˜ƒ * this.width) * this.format.components() + this.format.luminanceOrAlphaOffset() / 8;
         return MemoryUtil.memGetByte(this.pixels + (long)â˜ƒ);
      }
   }

   public void blendPixel(int var1, int var2, int var3) {
      if (this.format != NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
      } else {
         int â˜ƒ = this.getPixelRGBA(â˜ƒ, â˜ƒ);
         float â˜ƒx = (float)getA(â˜ƒ) / 255.0F;
         float â˜ƒxx = (float)getB(â˜ƒ) / 255.0F;
         float â˜ƒxxx = (float)getG(â˜ƒ) / 255.0F;
         float â˜ƒxxxx = (float)getR(â˜ƒ) / 255.0F;
         float â˜ƒxxxxx = (float)getA(â˜ƒ) / 255.0F;
         float â˜ƒxxxxxx = (float)getB(â˜ƒ) / 255.0F;
         float â˜ƒxxxxxxx = (float)getG(â˜ƒ) / 255.0F;
         float â˜ƒxxxxxxxx = (float)getR(â˜ƒ) / 255.0F;
         float â˜ƒxxxxxxxxx = 1.0F - â˜ƒx;
         float â˜ƒxxxxxxxxxx = â˜ƒx * â˜ƒx + â˜ƒxxxxx * â˜ƒxxxxxxxxx;
         float â˜ƒxxxxxxxxxxx = â˜ƒxx * â˜ƒx + â˜ƒxxxxxx * â˜ƒxxxxxxxxx;
         float â˜ƒxxxxxxxxxxxx = â˜ƒxxx * â˜ƒx + â˜ƒxxxxxxx * â˜ƒxxxxxxxxx;
         float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxx * â˜ƒx + â˜ƒxxxxxxxx * â˜ƒxxxxxxxxx;
         if (â˜ƒxxxxxxxxxx > 1.0F) {
            â˜ƒxxxxxxxxxx = 1.0F;
         }

         if (â˜ƒxxxxxxxxxxx > 1.0F) {
            â˜ƒxxxxxxxxxxx = 1.0F;
         }

         if (â˜ƒxxxxxxxxxxxx > 1.0F) {
            â˜ƒxxxxxxxxxxxx = 1.0F;
         }

         if (â˜ƒxxxxxxxxxxxxx > 1.0F) {
            â˜ƒxxxxxxxxxxxxx = 1.0F;
         }

         int â˜ƒ = (int)(â˜ƒxxxxxxxxxx * 255.0F);
         int â˜ƒx = (int)(â˜ƒxxxxxxxxxxx * 255.0F);
         int â˜ƒxx = (int)(â˜ƒxxxxxxxxxxxx * 255.0F);
         int â˜ƒxxx = (int)(â˜ƒxxxxxxxxxxxxx * 255.0F);
         this.setPixelRGBA(â˜ƒ, â˜ƒ, combine(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx));
      }
   }

   @Deprecated
   public int[] makePixelArray() {
      if (this.format != NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.checkAllocated();
         int[] â˜ƒ = new int[this.getWidth() * this.getHeight()];

         for(int â˜ƒx = 0; â˜ƒx < this.getHeight(); ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < this.getWidth(); ++â˜ƒxx) {
               int â˜ƒxxx = this.getPixelRGBA(â˜ƒxx, â˜ƒx);
               int â˜ƒxxxx = getA(â˜ƒxxx);
               int â˜ƒxxxxx = getB(â˜ƒxxx);
               int â˜ƒxxxxxx = getG(â˜ƒxxx);
               int â˜ƒxxxxxxx = getR(â˜ƒxxx);
               int â˜ƒxxxxxxxx = â˜ƒxxxx << 24 | â˜ƒxxxxxxx << 16 | â˜ƒxxxxxx << 8 | â˜ƒxxxxx;
               â˜ƒ[â˜ƒxx + â˜ƒx * this.getWidth()] = â˜ƒxxxxxxxx;
            }
         }

         return â˜ƒ;
      }
   }

   public void upload(int var1, int var2, int var3, boolean var4) {
      this.upload(â˜ƒ, â˜ƒ, â˜ƒ, 0, 0, this.width, this.height, false, â˜ƒ);
   }

   public void upload(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9) {
      this.upload(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, false, â˜ƒ, â˜ƒ);
   }

   public void upload(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10, boolean var11) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this._upload(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         this._upload(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void _upload(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10, boolean var11) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.checkAllocated();
      setFilter(â˜ƒ, â˜ƒ);
      if (â˜ƒ == this.getWidth()) {
         GlStateManager._pixelStore(3314, 0);
      } else {
         GlStateManager._pixelStore(3314, this.getWidth());
      }

      GlStateManager._pixelStore(3316, â˜ƒ);
      GlStateManager._pixelStore(3315, â˜ƒ);
      this.format.setUnpackPixelStoreState();
      GlStateManager._texSubImage2D(3553, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.format.glFormat(), 5121, this.pixels);
      if (â˜ƒ) {
         GlStateManager._texParameter(3553, 10242, 33071);
         GlStateManager._texParameter(3553, 10243, 33071);
      }

      if (â˜ƒ) {
         this.close();
      }
   }

   public void downloadTexture(int var1, boolean var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.checkAllocated();
      this.format.setPackPixelStoreState();
      GlStateManager._getTexImage(3553, â˜ƒ, this.format.glFormat(), 5121, this.pixels);
      if (â˜ƒ && this.format.hasAlpha()) {
         for(int â˜ƒ = 0; â˜ƒ < this.getHeight(); ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < this.getWidth(); ++â˜ƒx) {
               this.setPixelRGBA(â˜ƒx, â˜ƒ, this.getPixelRGBA(â˜ƒx, â˜ƒ) | 255 << this.format.alphaOffset());
            }
         }
      }
   }

   public void downloadDepthBuffer(float var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (this.format.components() != 1) {
         throw new IllegalStateException("Depth buffer must be stored in NativeImage with 1 component.");
      } else {
         this.checkAllocated();
         this.format.setPackPixelStoreState();
         GlStateManager._readPixels(0, 0, this.width, this.height, 6402, 5121, this.pixels);
      }
   }

   public void drawPixels() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      this.format.setUnpackPixelStoreState();
      GlStateManager._glDrawPixels(this.width, this.height, this.format.glFormat(), 5121, this.pixels);
   }

   public void writeToFile(String var1) throws IOException {
      this.writeToFile(FileSystems.getDefault().getPath(â˜ƒ));
   }

   public void writeToFile(File var1) throws IOException {
      this.writeToFile(â˜ƒ.toPath());
   }

   public void copyFromFont(STBTTFontinfo var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9, int var10) {
      if (â˜ƒ < 0 || â˜ƒ + â˜ƒ > this.getWidth() || â˜ƒ < 0 || â˜ƒ + â˜ƒ > this.getHeight()) {
         throw new IllegalArgumentException(
            String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.getWidth(), this.getHeight())
         );
      } else if (this.format.components() != 1) {
         throw new IllegalArgumentException("Can only write fonts into 1-component images.");
      } else {
         STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(
            â˜ƒ.address(), this.pixels + (long)â˜ƒ + (long)(â˜ƒ * this.getWidth()), â˜ƒ, â˜ƒ, this.getWidth(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ
         );
      }
   }

   public void writeToFile(Path var1) throws IOException {
      if (!this.format.supportedByStb()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.format);
      } else {
         this.checkAllocated();
         WritableByteChannel â˜ƒ = Files.newByteChannel(â˜ƒ, OPEN_OPTIONS);

         try {
            if (!this.writeToChannel(â˜ƒ)) {
               throw new IOException("Could not write image to the PNG file \"" + â˜ƒ.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
         } catch (Throwable var6) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      }
   }

   public byte[] asByteArray() throws IOException {
      ByteArrayOutputStream â˜ƒ = new ByteArrayOutputStream();

      byte[] var3;
      try {
         WritableByteChannel â˜ƒx = Channels.newChannel(â˜ƒ);

         try {
            if (!this.writeToChannel(â˜ƒx)) {
               throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
            }

            var3 = â˜ƒ.toByteArray();
         } catch (Throwable var7) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }
      } catch (Throwable var8) {
         try {
            â˜ƒ.close();
         } catch (Throwable var5) {
            var8.addSuppressed(var5);
         }

         throw var8;
      }

      â˜ƒ.close();
      return var3;
   }

   private boolean writeToChannel(WritableByteChannel var1) throws IOException {
      NativeImage.WriteCallback â˜ƒ = new NativeImage.WriteCallback(â˜ƒ);

      boolean var4;
      try {
         int â˜ƒx = Math.min(this.getHeight(), Integer.MAX_VALUE / this.getWidth() / this.format.components());
         if (â˜ƒx < this.getHeight()) {
            LOGGER.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", this.getHeight(), â˜ƒx);
         }

         if (STBImageWrite.nstbi_write_png_to_func(â˜ƒ.address(), 0L, this.getWidth(), â˜ƒx, this.format.components(), this.pixels, 0) != 0) {
            â˜ƒ.throwIfException();
            return true;
         }

         var4 = false;
      } finally {
         â˜ƒ.free();
      }

      return var4;
   }

   public void copyFrom(NativeImage var1) {
      if (â˜ƒ.format() != this.format) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int â˜ƒ = this.format.components();
         this.checkAllocated();
         â˜ƒ.checkAllocated();
         if (this.width == â˜ƒ.width) {
            MemoryUtil.memCopy(â˜ƒ.pixels, this.pixels, Math.min(this.size, â˜ƒ.size));
         } else {
            int â˜ƒ = Math.min(this.getWidth(), â˜ƒ.getWidth());
            int â˜ƒx = Math.min(this.getHeight(), â˜ƒ.getHeight());

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
               int â˜ƒxxx = â˜ƒxx * â˜ƒ.getWidth() * â˜ƒ;
               int â˜ƒxxxx = â˜ƒxx * this.getWidth() * â˜ƒ;
               MemoryUtil.memCopy(â˜ƒ.pixels + (long)â˜ƒxxx, this.pixels + (long)â˜ƒxxxx, (long)â˜ƒ);
            }
         }
      }
   }

   public void fillRect(int var1, int var2, int var3, int var4, int var5) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ + â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ + â˜ƒ; ++â˜ƒx) {
            this.setPixelRGBA(â˜ƒx, â˜ƒ, â˜ƒ);
         }
      }
   }

   public void copyRect(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, boolean var8) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            int â˜ƒxx = â˜ƒ ? â˜ƒ - 1 - â˜ƒx : â˜ƒx;
            int â˜ƒxxx = â˜ƒ ? â˜ƒ - 1 - â˜ƒ : â˜ƒ;
            int â˜ƒxxxx = this.getPixelRGBA(â˜ƒ + â˜ƒx, â˜ƒ + â˜ƒ);
            this.setPixelRGBA(â˜ƒ + â˜ƒ + â˜ƒxx, â˜ƒ + â˜ƒ + â˜ƒxxx, â˜ƒxxxx);
         }
      }
   }

   public void flipY() {
      this.checkAllocated();

      try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
         int â˜ƒx = this.format.components();
         int â˜ƒxx = this.getWidth() * â˜ƒx;
         long â˜ƒxxx = â˜ƒ.nmalloc(â˜ƒxx);

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < this.getHeight() / 2; ++â˜ƒxxxx) {
            int â˜ƒxxxxx = â˜ƒxxxx * this.getWidth() * â˜ƒx;
            int â˜ƒxxxxxx = (this.getHeight() - 1 - â˜ƒxxxx) * this.getWidth() * â˜ƒx;
            MemoryUtil.memCopy(this.pixels + (long)â˜ƒxxxxx, â˜ƒxxx, (long)â˜ƒxx);
            MemoryUtil.memCopy(this.pixels + (long)â˜ƒxxxxxx, this.pixels + (long)â˜ƒxxxxx, (long)â˜ƒxx);
            MemoryUtil.memCopy(â˜ƒxxx, this.pixels + (long)â˜ƒxxxxxx, (long)â˜ƒxx);
         }
      }
   }

   public void resizeSubRectTo(int var1, int var2, int var3, int var4, NativeImage var5) {
      this.checkAllocated();
      if (â˜ƒ.format() != this.format) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int â˜ƒ = this.format.components();
         STBImageResize.nstbir_resize_uint8(
            this.pixels + (long)((â˜ƒ + â˜ƒ * this.getWidth()) * â˜ƒ), â˜ƒ, â˜ƒ, this.getWidth() * â˜ƒ, â˜ƒ.pixels, â˜ƒ.getWidth(), â˜ƒ.getHeight(), 0, â˜ƒ
         );
      }
   }

   public void untrack() {
      DebugMemoryUntracker.untrack(this.pixels);
   }

   public static NativeImage fromBase64(String var0) throws IOException {
      byte[] â˜ƒ = Base64.getDecoder().decode(â˜ƒ.replaceAll("\n", "").getBytes(Charsets.UTF_8));

      NativeImage var4;
      try (MemoryStack â˜ƒx = MemoryStack.stackPush()) {
         ByteBuffer â˜ƒxx = â˜ƒx.malloc(â˜ƒ.length);
         â˜ƒxx.put(â˜ƒ);
         â˜ƒxx.rewind();
         var4 = read(â˜ƒxx);
      }

      return var4;
   }

   public static int getA(int var0) {
      return â˜ƒ >> 24 & 0xFF;
   }

   public static int getR(int var0) {
      return â˜ƒ >> 0 & 0xFF;
   }

   public static int getG(int var0) {
      return â˜ƒ >> 8 & 0xFF;
   }

   public static int getB(int var0) {
      return â˜ƒ >> 16 & 0xFF;
   }

   public static int combine(int var0, int var1, int var2, int var3) {
      return (â˜ƒ & 0xFF) << 24 | (â˜ƒ & 0xFF) << 16 | (â˜ƒ & 0xFF) << 8 | (â˜ƒ & 0xFF) << 0;
   }

   public static enum Format {
      RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      LUMINANCE_ALPHA(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      LUMINANCE(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      final int components;
      private final int glFormat;
      private final boolean hasRed;
      private final boolean hasGreen;
      private final boolean hasBlue;
      private final boolean hasLuminance;
      private final boolean hasAlpha;
      private final int redOffset;
      private final int greenOffset;
      private final int blueOffset;
      private final int luminanceOffset;
      private final int alphaOffset;
      private final boolean supportedByStb;

      private Format(
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
         this.components = â˜ƒ;
         this.glFormat = â˜ƒ;
         this.hasRed = â˜ƒ;
         this.hasGreen = â˜ƒ;
         this.hasBlue = â˜ƒ;
         this.hasLuminance = â˜ƒ;
         this.hasAlpha = â˜ƒ;
         this.redOffset = â˜ƒ;
         this.greenOffset = â˜ƒ;
         this.blueOffset = â˜ƒ;
         this.luminanceOffset = â˜ƒ;
         this.alphaOffset = â˜ƒ;
         this.supportedByStb = â˜ƒ;
      }

      public int components() {
         return this.components;
      }

      public void setPackPixelStoreState() {
         RenderSystem.assertThread(RenderSystem::isOnRenderThread);
         GlStateManager._pixelStore(3333, this.components());
      }

      public void setUnpackPixelStoreState() {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         GlStateManager._pixelStore(3317, this.components());
      }

      public int glFormat() {
         return this.glFormat;
      }

      public boolean hasRed() {
         return this.hasRed;
      }

      public boolean hasGreen() {
         return this.hasGreen;
      }

      public boolean hasBlue() {
         return this.hasBlue;
      }

      public boolean hasLuminance() {
         return this.hasLuminance;
      }

      public boolean hasAlpha() {
         return this.hasAlpha;
      }

      public int redOffset() {
         return this.redOffset;
      }

      public int greenOffset() {
         return this.greenOffset;
      }

      public int blueOffset() {
         return this.blueOffset;
      }

      public int luminanceOffset() {
         return this.luminanceOffset;
      }

      public int alphaOffset() {
         return this.alphaOffset;
      }

      public boolean hasLuminanceOrRed() {
         return this.hasLuminance || this.hasRed;
      }

      public boolean hasLuminanceOrGreen() {
         return this.hasLuminance || this.hasGreen;
      }

      public boolean hasLuminanceOrBlue() {
         return this.hasLuminance || this.hasBlue;
      }

      public boolean hasLuminanceOrAlpha() {
         return this.hasLuminance || this.hasAlpha;
      }

      public int luminanceOrRedOffset() {
         return this.hasLuminance ? this.luminanceOffset : this.redOffset;
      }

      public int luminanceOrGreenOffset() {
         return this.hasLuminance ? this.luminanceOffset : this.greenOffset;
      }

      public int luminanceOrBlueOffset() {
         return this.hasLuminance ? this.luminanceOffset : this.blueOffset;
      }

      public int luminanceOrAlphaOffset() {
         return this.hasLuminance ? this.luminanceOffset : this.alphaOffset;
      }

      public boolean supportedByStb() {
         return this.supportedByStb;
      }

      static NativeImage.Format getStbFormat(int var0) {
         switch(â˜ƒ) {
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

   public static enum InternalGlFormat {
      RGBA(6408),
      RGB(6407),
      RG(33319),
      RED(6403);

      private final int glFormat;

      private InternalGlFormat(int var3) {
         this.glFormat = â˜ƒ;
      }

      public int glFormat() {
         return this.glFormat;
      }
   }

   static class WriteCallback extends STBIWriteCallback {
      private final WritableByteChannel output;
      @Nullable
      private IOException exception;

      WriteCallback(WritableByteChannel var1) {
         this.output = â˜ƒ;
      }

      @Override
      public void invoke(long var1, long var3, int var5) {
         ByteBuffer â˜ƒ = getData(â˜ƒ, â˜ƒ);

         try {
            this.output.write(â˜ƒ);
         } catch (IOException var8) {
            this.exception = var8;
         }
      }

      public void throwIfException() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         }
      }
   }
}
