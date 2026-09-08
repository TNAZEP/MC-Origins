package net.minecraft.client.renderer.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

public class RenderLightningBolt extends Render<EntityLightningBolt> {
   public RenderLightningBolt(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityLightningBolt var1, double var2, double var4, double var6, float var8, float var9) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      double[] ☃xx = new double[8];
      double[] ☃xxx = new double[8];
      double ☃xxxx = 0.0;
      double ☃xxxxx = 0.0;
      Random ☃xxxxxx = new Random(☃.field_70264_a);

      for(int ☃xxxxxxx = 7; ☃xxxxxxx >= 0; --☃xxxxxxx) {
         ☃xx[☃xxxxxxx] = ☃xxxx;
         ☃xxx[☃xxxxxxx] = ☃xxxxx;
         ☃xxxx += (double)(☃xxxxxx.nextInt(11) - 5);
         ☃xxxxx += (double)(☃xxxxxx.nextInt(11) - 5);
      }

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < 4; ++☃xxxxxxx) {
         Random ☃xxxxxxxx = new Random(☃.field_70264_a);

         for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < 3; ++☃xxxxxxxxx) {
            int ☃xxxxxxxxxx = 7;
            int ☃xxxxxxxxxxx = 0;
            if (☃xxxxxxxxx > 0) {
               ☃xxxxxxxxxx = 7 - ☃xxxxxxxxx;
            }

            if (☃xxxxxxxxx > 0) {
               ☃xxxxxxxxxxx = ☃xxxxxxxxxx - 2;
            }

            double ☃xxxxxxxxxx = ☃xx[☃xxxxxxxxxx] - ☃xxxx;
            double ☃xxxxxxxxxxx = ☃xxx[☃xxxxxxxxxx] - ☃xxxxx;

            for(int ☃xxxxxxxxxxxx = ☃xxxxxxxxxx; ☃xxxxxxxxxxxx >= ☃xxxxxxxxxxx; --☃xxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxx = ☃xxxxxxxxxx;
               double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxx;
               if (☃xxxxxxxxx == 0) {
                  ☃xxxxxxxxxx += (double)(☃xxxxxxxx.nextInt(11) - 5);
                  ☃xxxxxxxxxxx += (double)(☃xxxxxxxx.nextInt(11) - 5);
               } else {
                  ☃xxxxxxxxxx += (double)(☃xxxxxxxx.nextInt(31) - 15);
                  ☃xxxxxxxxxxx += (double)(☃xxxxxxxx.nextInt(31) - 15);
               }

               ☃x.func_181668_a(5, DefaultVertexFormats.field_181706_f);
               float ☃xxxxxxxxxxxxx = 0.5F;
               float ☃xxxxxxxxxxxxxx = 0.45F;
               float ☃xxxxxxxxxxxxxxx = 0.45F;
               float ☃xxxxxxxxxxxxxxxx = 0.5F;
               double ☃xxxxxxxxxxxxxxxxx = 0.1 + (double)☃xxxxxxx * 0.2;
               if (☃xxxxxxxxx == 0) {
                  ☃xxxxxxxxxxxxxxxxx *= (double)☃xxxxxxxxxxxx * 0.1 + 1.0;
               }

               double ☃xxxxxxxxxxxxx = 0.1 + (double)☃xxxxxxx * 0.2;
               if (☃xxxxxxxxx == 0) {
                  ☃xxxxxxxxxxxxx *= (double)(☃xxxxxxxxxxxx - 1) * 0.1 + 1.0;
               }

               for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < 5; ++☃xxxxxxxxxxxxx) {
                  double ☃xxxxxxxxxxxxxx = ☃ - ☃xxxxxxxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxx = ☃ - ☃xxxxxxxxxxxxxxxxx;
                  if (☃xxxxxxxxxxxxx == 1 || ☃xxxxxxxxxxxxx == 2) {
                     ☃xxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxx * 2.0;
                  }

                  if (☃xxxxxxxxxxxxx == 2 || ☃xxxxxxxxxxxxx == 3) {
                     ☃xxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxx * 2.0;
                  }

                  double ☃xxxxxxxxxxxxxx = ☃ - ☃xxxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxx = ☃ - ☃xxxxxxxxxxxxx;
                  if (☃xxxxxxxxxxxxx == 1 || ☃xxxxxxxxxxxxx == 2) {
                     ☃xxxxxxxxxxxxxx += ☃xxxxxxxxxxxxx * 2.0;
                  }

                  if (☃xxxxxxxxxxxxx == 2 || ☃xxxxxxxxxxxxx == 3) {
                     ☃xxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxx * 2.0;
                  }

                  ☃x.func_181662_b(☃xxxxxxxxxxxxxx + ☃xxxxxxxxxx, ☃ + (double)(☃xxxxxxxxxxxx * 16), ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxx)
                     .func_181666_a(0.45F, 0.45F, 0.5F, 0.3F)
                     .func_181675_d();
                  ☃x.func_181662_b(☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx, ☃ + (double)((☃xxxxxxxxxxxx + 1) * 16), ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxx)
                     .func_181666_a(0.45F, 0.45F, 0.5F, 0.3F)
                     .func_181675_d();
               }

               ☃.func_78381_a();
            }
         }
      }

      GlStateManager.func_179084_k();
      GlStateManager.func_179145_e();
      GlStateManager.func_179098_w();
   }

   @Nullable
   protected ResourceLocation func_110775_a(EntityLightningBolt var1) {
      return null;
   }
}
