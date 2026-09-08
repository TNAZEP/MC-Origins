package net.minecraft.client.gui.fonts;

import java.io.Closeable;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class FontTexture extends AbstractTexture implements Closeable {
   private final ResourceLocation field_211133_f;
   private final boolean field_211512_g;
   private final FontTexture.Entry field_211135_h;

   public FontTexture(ResourceLocation var1, boolean var2) {
      this.field_211133_f = ☃;
      this.field_211512_g = ☃;
      this.field_211135_h = new FontTexture.Entry(0, 0, 256, 256);
      TextureUtil.func_211681_a(☃ ? NativeImage.PixelFormatGLCode.RGBA : NativeImage.PixelFormatGLCode.INTENSITY, this.func_110552_b(), 256, 256);
   }

   @Override
   public void func_195413_a(IResourceManager var1) {
   }

   public void close() {
      this.func_147631_c();
   }

   @Nullable
   public TexturedGlyph func_211131_a(IGlyphInfo var1) {
      if (☃.func_211579_f() != this.field_211512_g) {
         return null;
      } else {
         FontTexture.Entry ☃ = this.field_211135_h.func_211224_a(☃);
         if (☃ != null) {
            this.func_195412_h();
            ☃.func_211573_a(☃.field_211225_a, ☃.field_211226_b);
            float ☃x = 256.0F;
            float ☃xx = 256.0F;
            float ☃xxx = 0.01F;
            return new TexturedGlyph(
               this.field_211133_f,
               ((float)☃.field_211225_a + 0.01F) / 256.0F,
               ((float)☃.field_211225_a - 0.01F + (float)☃.func_211202_a()) / 256.0F,
               ((float)☃.field_211226_b + 0.01F) / 256.0F,
               ((float)☃.field_211226_b - 0.01F + (float)☃.func_211203_b()) / 256.0F,
               ☃.func_211198_f(),
               ☃.func_211199_g(),
               ☃.func_211200_h(),
               ☃.func_211204_i()
            );
         } else {
            return null;
         }
      }
   }

   public ResourceLocation func_211132_a() {
      return this.field_211133_f;
   }

   static class Entry {
      final int field_211225_a;
      final int field_211226_b;
      final int field_211227_c;
      final int field_211228_d;
      FontTexture.Entry field_211229_e;
      FontTexture.Entry field_211230_f;
      boolean field_211231_g;

      private Entry(int var1, int var2, int var3, int var4) {
         this.field_211225_a = ☃;
         this.field_211226_b = ☃;
         this.field_211227_c = ☃;
         this.field_211228_d = ☃;
      }

      @Nullable
      FontTexture.Entry func_211224_a(IGlyphInfo var1) {
         if (this.field_211229_e != null && this.field_211230_f != null) {
            FontTexture.Entry ☃ = this.field_211229_e.func_211224_a(☃);
            if (☃ == null) {
               ☃ = this.field_211230_f.func_211224_a(☃);
            }

            return ☃;
         } else if (this.field_211231_g) {
            return null;
         } else {
            int ☃ = ☃.func_211202_a();
            int ☃x = ☃.func_211203_b();
            if (☃ > this.field_211227_c || ☃x > this.field_211228_d) {
               return null;
            } else if (☃ == this.field_211227_c && ☃x == this.field_211228_d) {
               this.field_211231_g = true;
               return this;
            } else {
               int ☃ = this.field_211227_c - ☃;
               int ☃x = this.field_211228_d - ☃x;
               if (☃ > ☃x) {
                  this.field_211229_e = new FontTexture.Entry(this.field_211225_a, this.field_211226_b, ☃, this.field_211228_d);
                  this.field_211230_f = new FontTexture.Entry(
                     this.field_211225_a + ☃ + 1, this.field_211226_b, this.field_211227_c - ☃ - 1, this.field_211228_d
                  );
               } else {
                  this.field_211229_e = new FontTexture.Entry(this.field_211225_a, this.field_211226_b, this.field_211227_c, ☃x);
                  this.field_211230_f = new FontTexture.Entry(
                     this.field_211225_a, this.field_211226_b + ☃x + 1, this.field_211227_c, this.field_211228_d - ☃x - 1
                  );
               }

               return this.field_211229_e.func_211224_a(☃);
            }
         }
      }
   }
}
