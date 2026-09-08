package com.mojang.blaze3d.font;

import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeGlyphProvider implements GlyphProvider {
   private final ByteBuffer fontMemory;
   final STBTTFontinfo font;
   final float oversample;
   private final IntSet skip = new IntArraySet();
   final float shiftX;
   final float shiftY;
   final float pointScale;
   final float ascent;

   public TrueTypeGlyphProvider(ByteBuffer var1, STBTTFontinfo var2, float var3, float var4, float var5, float var6, String var7) {
      this.fontMemory = â˜ƒ;
      this.font = â˜ƒ;
      this.oversample = â˜ƒ;
      â˜ƒ.codePoints().forEach(this.skip::add);
      this.shiftX = â˜ƒ * â˜ƒ;
      this.shiftY = â˜ƒ * â˜ƒ;
      this.pointScale = STBTruetype.stbtt_ScaleForPixelHeight(â˜ƒ, â˜ƒ * â˜ƒ);

      try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
         IntBuffer â˜ƒx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxxx = â˜ƒ.mallocInt(1);
         STBTruetype.stbtt_GetFontVMetrics(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         this.ascent = (float)â˜ƒx.get(0) * this.pointScale;
      }
   }

   @Nullable
   public TrueTypeGlyphProvider.Glyph getGlyph(int var1) {
      if (this.skip.contains(â˜ƒ)) {
         return null;
      } else {
         Object var10;
         try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
            IntBuffer â˜ƒx = â˜ƒ.mallocInt(1);
            IntBuffer â˜ƒxx = â˜ƒ.mallocInt(1);
            IntBuffer â˜ƒxxx = â˜ƒ.mallocInt(1);
            IntBuffer â˜ƒxxxx = â˜ƒ.mallocInt(1);
            int â˜ƒxxxxx = STBTruetype.stbtt_FindGlyphIndex(this.font, â˜ƒ);
            if (â˜ƒxxxxx == 0) {
               return null;
            }

            STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(
               this.font, â˜ƒxxxxx, this.pointScale, this.pointScale, this.shiftX, this.shiftY, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx
            );
            int â˜ƒx = â˜ƒxxx.get(0) - â˜ƒx.get(0);
            int â˜ƒxx = â˜ƒxxxx.get(0) - â˜ƒxx.get(0);
            if (â˜ƒx > 0 && â˜ƒxx > 0) {
               IntBuffer â˜ƒxxx = â˜ƒ.mallocInt(1);
               IntBuffer â˜ƒxxxx = â˜ƒ.mallocInt(1);
               STBTruetype.stbtt_GetGlyphHMetrics(this.font, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx);
               return new TrueTypeGlyphProvider.Glyph(
                  â˜ƒx.get(0),
                  â˜ƒxxx.get(0),
                  -â˜ƒxx.get(0),
                  -â˜ƒxxxx.get(0),
                  (float)â˜ƒxxx.get(0) * this.pointScale,
                  (float)â˜ƒxxxx.get(0) * this.pointScale,
                  â˜ƒxxxxx
               );
            }

            var10 = null;
         }

         return (TrueTypeGlyphProvider.Glyph)var10;
      }
   }

   @Override
   public void close() {
      this.font.free();
      MemoryUtil.memFree(this.fontMemory);
   }

   @Override
   public IntSet getSupportedGlyphs() {
      return (IntSet)IntStream.range(0, 65535)
         .filter(var1 -> !this.skip.contains(var1))
         .collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
   }

   class Glyph implements RawGlyph {
      private final int width;
      private final int height;
      private final float bearingX;
      private final float bearingY;
      private final float advance;
      private final int index;

      Glyph(int var2, int var3, int var4, int var5, float var6, float var7, int var8) {
         this.width = â˜ƒ - â˜ƒ;
         this.height = â˜ƒ - â˜ƒ;
         this.advance = â˜ƒ / TrueTypeGlyphProvider.this.oversample;
         this.bearingX = (â˜ƒ + (float)â˜ƒ + TrueTypeGlyphProvider.this.shiftX) / TrueTypeGlyphProvider.this.oversample;
         this.bearingY = (TrueTypeGlyphProvider.this.ascent - (float)â˜ƒ + TrueTypeGlyphProvider.this.shiftY) / TrueTypeGlyphProvider.this.oversample;
         this.index = â˜ƒ;
      }

      @Override
      public int getPixelWidth() {
         return this.width;
      }

      @Override
      public int getPixelHeight() {
         return this.height;
      }

      @Override
      public float getOversample() {
         return TrueTypeGlyphProvider.this.oversample;
      }

      @Override
      public float getAdvance() {
         return this.advance;
      }

      @Override
      public float getBearingX() {
         return this.bearingX;
      }

      @Override
      public float getBearingY() {
         return this.bearingY;
      }

      @Override
      public void upload(int var1, int var2) {
         NativeImage â˜ƒ = new NativeImage(NativeImage.Format.LUMINANCE, this.width, this.height, false);
         â˜ƒ.copyFromFont(
            TrueTypeGlyphProvider.this.font,
            this.index,
            this.width,
            this.height,
            TrueTypeGlyphProvider.this.pointScale,
            TrueTypeGlyphProvider.this.pointScale,
            TrueTypeGlyphProvider.this.shiftX,
            TrueTypeGlyphProvider.this.shiftY,
            0,
            0
         );
         â˜ƒ.upload(0, â˜ƒ, â˜ƒ, 0, 0, this.width, this.height, false, true);
      }

      @Override
      public boolean isColored() {
         return false;
      }
   }
}
