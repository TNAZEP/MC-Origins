package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;

public class DebugRendererChunkBorder implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_190072_a;

   public DebugRendererChunkBorder(Minecraft var1) {
      this.field_190072_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_190072_a.field_71439_g;
      Tessellator ☃x = Tessellator.func_178181_a();
      BufferBuilder ☃xx = ☃x.func_178180_c();
      double ☃xxx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xxxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      double ☃xxxxxx = 0.0 - ☃xxxx;
      double ☃xxxxxxx = 256.0 - ☃xxxx;
      GlStateManager.func_179090_x();
      GlStateManager.func_179084_k();
      double ☃xxxxxxxx = (double)(☃.field_70176_ah << 4) - ☃xxx;
      double ☃xxxxxxxxx = (double)(☃.field_70164_aj << 4) - ☃xxxxx;
      GlStateManager.func_187441_d(1.0F);
      ☃xx.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int ☃xxxxxxxxxx = -16; ☃xxxxxxxxxx <= 32; ☃xxxxxxxxxx += 16) {
         for(int ☃xxxxxxxxxxx = -16; ☃xxxxxxxxxxx <= 32; ☃xxxxxxxxxxx += 16) {
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(1.0F, 0.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(1.0F, 0.0F, 0.0F, 0.5F)
               .func_181675_d();
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(1.0F, 0.0F, 0.0F, 0.5F)
               .func_181675_d();
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(1.0F, 0.0F, 0.0F, 0.0F)
               .func_181675_d();
         }
      }

      for(int ☃xxxxxxxxxx = 2; ☃xxxxxxxxxx < 16; ☃xxxxxxxxxx += 2) {
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
      }

      for(int ☃xxxxxxxxxx = 2; ☃xxxxxxxxxx < 16; ☃xxxxxxxxxx += 2) {
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
      }

      for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx <= 256; ☃xxxxxxxxxx += 2) {
         double ☃xxxxxxxxxxx = (double)☃xxxxxxxxxx - ☃xxxx;
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxxxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(1.0F, 1.0F, 0.0F, 0.0F).func_181675_d();
      }

      ☃x.func_78381_a();
      GlStateManager.func_187441_d(2.0F);
      ☃xx.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx <= 16; ☃xxxxxxxxxx += 16) {
         for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx <= 16; ☃xxxxxxxxxxx += 16) {
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(0.25F, 0.25F, 1.0F, 0.0F)
               .func_181675_d();
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(0.25F, 0.25F, 1.0F, 1.0F)
               .func_181675_d();
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(0.25F, 0.25F, 1.0F, 1.0F)
               .func_181675_d();
            ☃xx.func_181662_b(☃xxxxxxxx + (double)☃xxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxx + (double)☃xxxxxxxxxxx)
               .func_181666_a(0.25F, 0.25F, 1.0F, 0.0F)
               .func_181675_d();
         }
      }

      for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx <= 256; ☃xxxxxxxxxx += 16) {
         double ☃xxxxxxxxxxx = (double)☃xxxxxxxxxx - ☃xxxx;
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(0.25F, 0.25F, 1.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(0.25F, 0.25F, 1.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(0.25F, 0.25F, 1.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxxxxxxx, ☃xxxxxxxxx + 16.0).func_181666_a(0.25F, 0.25F, 1.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx + 16.0, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(0.25F, 0.25F, 1.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(0.25F, 0.25F, 1.0F, 1.0F).func_181675_d();
         ☃xx.func_181662_b(☃xxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxx).func_181666_a(0.25F, 0.25F, 1.0F, 0.0F).func_181675_d();
      }

      ☃x.func_78381_a();
      GlStateManager.func_187441_d(1.0F);
      GlStateManager.func_179147_l();
      GlStateManager.func_179098_w();
   }
}
