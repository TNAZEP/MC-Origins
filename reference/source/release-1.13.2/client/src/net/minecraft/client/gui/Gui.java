package net.minecraft.client.gui;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public abstract class Gui {
   public static final ResourceLocation field_110325_k = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation field_110323_l = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation field_110324_m = new ResourceLocation("textures/gui/icons.png");
   protected float field_73735_i;

   protected void func_73730_a(int var1, int var2, int var3, int var4) {
      if (☃ < ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      func_73734_a(☃, ☃, ☃ + 1, ☃ + 1, ☃);
   }

   protected void func_73728_b(int var1, int var2, int var3, int var4) {
      if (☃ < ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      func_73734_a(☃, ☃ + 1, ☃ + 1, ☃, ☃);
   }

   public static void func_73734_a(int var0, int var1, int var2, int var3, int var4) {
      if (☃ < ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      if (☃ < ☃) {
         int ☃ = ☃;
         ☃ = ☃;
         ☃ = ☃;
      }

      float ☃ = (float)(☃ >> 24 & 0xFF) / 255.0F;
      float ☃x = (float)(☃ >> 16 & 0xFF) / 255.0F;
      float ☃xx = (float)(☃ >> 8 & 0xFF) / 255.0F;
      float ☃xxx = (float)(☃ & 0xFF) / 255.0F;
      Tessellator ☃xxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxx = ☃xxxx.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179131_c(☃x, ☃xx, ☃xxx, ☃);
      ☃xxxxx.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      ☃xxxxx.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃xxxxx.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃xxxxx.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃xxxxx.func_181662_b((double)☃, (double)☃, 0.0).func_181675_d();
      ☃xxxx.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   protected void func_73733_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      float ☃ = (float)(☃ >> 24 & 0xFF) / 255.0F;
      float ☃x = (float)(☃ >> 16 & 0xFF) / 255.0F;
      float ☃xx = (float)(☃ >> 8 & 0xFF) / 255.0F;
      float ☃xxx = (float)(☃ & 0xFF) / 255.0F;
      float ☃xxxx = (float)(☃ >> 24 & 0xFF) / 255.0F;
      float ☃xxxxx = (float)(☃ >> 16 & 0xFF) / 255.0F;
      float ☃xxxxxx = (float)(☃ >> 8 & 0xFF) / 255.0F;
      float ☃xxxxxxx = (float)(☃ & 0xFF) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179103_j(7425);
      Tessellator ☃xxxxxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxxxxx = ☃xxxxxxxx.func_178180_c();
      ☃xxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      ☃xxxxxxxxx.func_181662_b((double)☃, (double)☃, (double)this.field_73735_i).func_181666_a(☃x, ☃xx, ☃xxx, ☃).func_181675_d();
      ☃xxxxxxxxx.func_181662_b((double)☃, (double)☃, (double)this.field_73735_i).func_181666_a(☃x, ☃xx, ☃xxx, ☃).func_181675_d();
      ☃xxxxxxxxx.func_181662_b((double)☃, (double)☃, (double)this.field_73735_i).func_181666_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxx).func_181675_d();
      ☃xxxxxxxxx.func_181662_b((double)☃, (double)☃, (double)this.field_73735_i).func_181666_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxx).func_181675_d();
      ☃xxxxxxxx.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }

   public void func_73732_a(FontRenderer var1, String var2, int var3, int var4, int var5) {
      ☃.func_175063_a(☃, (float)(☃ - ☃.func_78256_a(☃) / 2), (float)☃, ☃);
   }

   public void func_73731_b(FontRenderer var1, String var2, int var3, int var4, int var5) {
      ☃.func_175063_a(☃, (float)☃, (float)☃, ☃);
   }

   public void func_73729_b(int var1, int var2, int var3, int var4, int var5, int var6) {
      float ☃ = 0.00390625F;
      float ☃x = 0.00390625F;
      Tessellator ☃xx = Tessellator.func_178181_a();
      BufferBuilder ☃xxx = ☃xx.func_178180_c();
      ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃xxx.func_181662_b((double)(☃ + 0), (double)(☃ + ☃), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 0) * 0.00390625F), (double)((float)(☃ + ☃) * 0.00390625F))
         .func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + ☃), (double)(☃ + ☃), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + ☃) * 0.00390625F), (double)((float)(☃ + ☃) * 0.00390625F))
         .func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + ☃), (double)(☃ + 0), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + ☃) * 0.00390625F), (double)((float)(☃ + 0) * 0.00390625F))
         .func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + 0), (double)(☃ + 0), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 0) * 0.00390625F), (double)((float)(☃ + 0) * 0.00390625F))
         .func_181675_d();
      ☃xx.func_78381_a();
   }

   public void func_175174_a(float var1, float var2, int var3, int var4, int var5, int var6) {
      float ☃ = 0.00390625F;
      float ☃x = 0.00390625F;
      Tessellator ☃xx = Tessellator.func_178181_a();
      BufferBuilder ☃xxx = ☃xx.func_178180_c();
      ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃xxx.func_181662_b((double)(☃ + 0.0F), (double)(☃ + (float)☃), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 0) * 0.00390625F), (double)((float)(☃ + ☃) * 0.00390625F))
         .func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + (float)☃), (double)(☃ + (float)☃), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + ☃) * 0.00390625F), (double)((float)(☃ + ☃) * 0.00390625F))
         .func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + (float)☃), (double)(☃ + 0.0F), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + ☃) * 0.00390625F), (double)((float)(☃ + 0) * 0.00390625F))
         .func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + 0.0F), (double)(☃ + 0.0F), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 0) * 0.00390625F), (double)((float)(☃ + 0) * 0.00390625F))
         .func_181675_d();
      ☃xx.func_78381_a();
   }

   public void func_175175_a(int var1, int var2, TextureAtlasSprite var3, int var4, int var5) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b((double)(☃ + 0), (double)(☃ + ☃), (double)this.field_73735_i)
         .func_187315_a((double)☃.func_94209_e(), (double)☃.func_94210_h())
         .func_181675_d();
      ☃x.func_181662_b((double)(☃ + ☃), (double)(☃ + ☃), (double)this.field_73735_i)
         .func_187315_a((double)☃.func_94212_f(), (double)☃.func_94210_h())
         .func_181675_d();
      ☃x.func_181662_b((double)(☃ + ☃), (double)(☃ + 0), (double)this.field_73735_i)
         .func_187315_a((double)☃.func_94212_f(), (double)☃.func_94206_g())
         .func_181675_d();
      ☃x.func_181662_b((double)(☃ + 0), (double)(☃ + 0), (double)this.field_73735_i)
         .func_187315_a((double)☃.func_94209_e(), (double)☃.func_94206_g())
         .func_181675_d();
      ☃.func_78381_a();
   }

   public static void func_146110_a(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      float ☃ = 1.0F / ☃;
      float ☃x = 1.0F / ☃;
      Tessellator ☃xx = Tessellator.func_178181_a();
      BufferBuilder ☃xxx = ☃xx.func_178180_c();
      ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃xxx.func_181662_b((double)☃, (double)(☃ + ☃), 0.0).func_187315_a((double)(☃ * ☃), (double)((☃ + (float)☃) * ☃x)).func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + ☃), (double)(☃ + ☃), 0.0).func_187315_a((double)((☃ + (float)☃) * ☃), (double)((☃ + (float)☃) * ☃x)).func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + ☃), (double)☃, 0.0).func_187315_a((double)((☃ + (float)☃) * ☃), (double)(☃ * ☃x)).func_181675_d();
      ☃xxx.func_181662_b((double)☃, (double)☃, 0.0).func_187315_a((double)(☃ * ☃), (double)(☃ * ☃x)).func_181675_d();
      ☃xx.func_78381_a();
   }

   public static void func_152125_a(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      float ☃ = 1.0F / ☃;
      float ☃x = 1.0F / ☃;
      Tessellator ☃xx = Tessellator.func_178181_a();
      BufferBuilder ☃xxx = ☃xx.func_178180_c();
      ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃xxx.func_181662_b((double)☃, (double)(☃ + ☃), 0.0).func_187315_a((double)(☃ * ☃), (double)((☃ + (float)☃) * ☃x)).func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + ☃), (double)(☃ + ☃), 0.0).func_187315_a((double)((☃ + (float)☃) * ☃), (double)((☃ + (float)☃) * ☃x)).func_181675_d();
      ☃xxx.func_181662_b((double)(☃ + ☃), (double)☃, 0.0).func_187315_a((double)((☃ + (float)☃) * ☃), (double)(☃ * ☃x)).func_181675_d();
      ☃xxx.func_181662_b((double)☃, (double)☃, 0.0).func_187315_a((double)(☃ * ☃), (double)(☃ * ☃x)).func_181675_d();
      ☃xx.func_78381_a();
   }
}
