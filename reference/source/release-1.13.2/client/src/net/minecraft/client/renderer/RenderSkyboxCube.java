package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderSkyboxCube {
   private final ResourceLocation[] field_209143_a = new ResourceLocation[6];

   public RenderSkyboxCube(ResourceLocation var1) {
      for(int ☃ = 0; ☃ < 6; ++☃) {
         this.field_209143_a[☃] = new ResourceLocation(☃.func_110624_b(), ☃.func_110623_a() + '_' + ☃ + ".png");
      }
   }

   public void func_209142_a(Minecraft var1, float var2, float var3) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179094_E();
      GlStateManager.func_179096_D();
      GlStateManager.func_199294_a(
         Matrix4f.func_195876_a(85.0, (float)☃.field_195558_d.func_198109_k() / (float)☃.field_195558_d.func_198091_l(), 0.05F, 10.0F)
      );
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179094_E();
      GlStateManager.func_179096_D();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179129_p();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      int ☃xx = 2;

      for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
         GlStateManager.func_179094_E();
         float ☃xxxx = ((float)(☃xxx % 2) / 2.0F - 0.5F) / 256.0F;
         float ☃xxxxx = ((float)(☃xxx / 2) / 2.0F - 0.5F) / 256.0F;
         float ☃xxxxxx = 0.0F;
         GlStateManager.func_179109_b(☃xxxx, ☃xxxxx, 0.0F);
         GlStateManager.func_179114_b(☃, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(☃, 0.0F, 1.0F, 0.0F);

         for(int ☃xxxxxxx = 0; ☃xxxxxxx < 6; ++☃xxxxxxx) {
            ☃.func_110434_K().func_110577_a(this.field_209143_a[☃xxxxxxx]);
            ☃x.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            int ☃xxxxxxxx = 255 / (☃xxx + 1);
            if (☃xxxxxxx == 0) {
               ☃x.func_181662_b(-1.0, -1.0, 1.0).func_187315_a(0.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, 1.0, 1.0).func_187315_a(0.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, 1.0, 1.0).func_187315_a(1.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, -1.0, 1.0).func_187315_a(1.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
            }

            if (☃xxxxxxx == 1) {
               ☃x.func_181662_b(1.0, -1.0, 1.0).func_187315_a(0.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, 1.0, 1.0).func_187315_a(0.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, 1.0, -1.0).func_187315_a(1.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, -1.0, -1.0).func_187315_a(1.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
            }

            if (☃xxxxxxx == 2) {
               ☃x.func_181662_b(1.0, -1.0, -1.0).func_187315_a(0.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, 1.0, -1.0).func_187315_a(0.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, 1.0, -1.0).func_187315_a(1.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, -1.0, -1.0).func_187315_a(1.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
            }

            if (☃xxxxxxx == 3) {
               ☃x.func_181662_b(-1.0, -1.0, -1.0).func_187315_a(0.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, 1.0, -1.0).func_187315_a(0.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, 1.0, 1.0).func_187315_a(1.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, -1.0, 1.0).func_187315_a(1.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
            }

            if (☃xxxxxxx == 4) {
               ☃x.func_181662_b(-1.0, -1.0, -1.0).func_187315_a(0.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, -1.0, 1.0).func_187315_a(0.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, -1.0, 1.0).func_187315_a(1.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, -1.0, -1.0).func_187315_a(1.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
            }

            if (☃xxxxxxx == 5) {
               ☃x.func_181662_b(-1.0, 1.0, 1.0).func_187315_a(0.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(-1.0, 1.0, -1.0).func_187315_a(0.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, 1.0, -1.0).func_187315_a(1.0, 1.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
               ☃x.func_181662_b(1.0, 1.0, 1.0).func_187315_a(1.0, 0.0).func_181669_b(255, 255, 255, ☃xxxxxxxx).func_181675_d();
            }

            ☃.func_78381_a();
         }

         GlStateManager.func_179121_F();
         GlStateManager.func_179135_a(true, true, true, false);
      }

      ☃x.func_178969_c(0.0, 0.0, 0.0);
      GlStateManager.func_179135_a(true, true, true, true);
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179121_F();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179121_F();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179089_o();
      GlStateManager.func_179126_j();
   }
}
