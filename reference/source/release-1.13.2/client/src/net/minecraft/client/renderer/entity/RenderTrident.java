package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.ModelTrident;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityTrident;
import net.minecraft.util.ResourceLocation;

public class RenderTrident extends Render<EntityTrident> {
   public static final ResourceLocation field_203087_a = new ResourceLocation("textures/entity/trident.png");
   private final ModelTrident field_203088_f = new ModelTrident();

   public RenderTrident(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityTrident var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180548_c(☃);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179094_E();
      GlStateManager.func_179140_f();
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      GlStateManager.func_179114_b(☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃ - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃ + 90.0F, 0.0F, 0.0F, 1.0F);
      this.field_203088_f.func_203079_a();
      GlStateManager.func_179121_F();
      this.func_203085_b(☃, ☃, ☃, ☃, ☃, ☃);
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      GlStateManager.func_179145_e();
   }

   protected ResourceLocation func_110775_a(EntityTrident var1) {
      return field_203087_a;
   }

   private double func_203086_a(double var1, double var3, double var5) {
      return ☃ + (☃ - ☃) * ☃;
   }

   protected void func_203085_b(EntityTrident var1, double var2, double var4, double var6, float var8, float var9) {
      Entity ☃ = ☃.func_212360_k();
      if (☃ != null && ☃.func_203047_q()) {
         Tessellator ☃x = Tessellator.func_178181_a();
         BufferBuilder ☃xx = ☃x.func_178180_c();
         double ☃xxx = this.func_203086_a((double)☃.field_70126_B, (double)☃.field_70177_z, (double)(☃ * 0.5F)) * (float) (Math.PI / 180.0);
         double ☃xxxx = Math.cos(☃xxx);
         double ☃xxxxx = Math.sin(☃xxx);
         double ☃xxxxxx = this.func_203086_a(☃.field_70169_q, ☃.field_70165_t, (double)☃);
         double ☃xxxxxxx = this.func_203086_a(☃.field_70167_r + (double)☃.func_70047_e() * 0.8, ☃.field_70163_u + (double)☃.func_70047_e() * 0.8, (double)☃);
         double ☃xxxxxxxx = this.func_203086_a(☃.field_70166_s, ☃.field_70161_v, (double)☃);
         double ☃xxxxxxxxx = ☃xxxx - ☃xxxxx;
         double ☃xxxxxxxxxx = ☃xxxxx + ☃xxxx;
         double ☃xxxxxxxxxxx = this.func_203086_a(☃.field_70169_q, ☃.field_70165_t, (double)☃);
         double ☃xxxxxxxxxxxx = this.func_203086_a(☃.field_70167_r, ☃.field_70163_u, (double)☃);
         double ☃xxxxxxxxxxxxx = this.func_203086_a(☃.field_70166_s, ☃.field_70161_v, (double)☃);
         double ☃xxxxxxxxxxxxxx = (double)((float)(☃xxxxxx - ☃xxxxxxxxxxx));
         double ☃xxxxxxxxxxxxxxx = (double)((float)(☃xxxxxxx - ☃xxxxxxxxxxxx));
         double ☃xxxxxxxxxxxxxxxx = (double)((float)(☃xxxxxxxx - ☃xxxxxxxxxxxxx));
         double ☃xxxxxxxxxxxxxxxxx = Math.sqrt(☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx);
         int ☃xxxxxxxxxxxxxxxxxx = ☃.func_145782_y() + ☃.field_70173_aa;
         double ☃xxxxxxxxxxxxxxxxxxx = (double)((float)☃xxxxxxxxxxxxxxxxxx + ☃) * -0.1;
         double ☃xxxxxxxxxxxxxxxxxxxx = Math.min(0.5, ☃xxxxxxxxxxxxxxxxx / 30.0);
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179129_p();
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 255.0F, 255.0F);
         ☃xx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
         int ☃xxxxxxxxxxxxxxxxxxxxx = 37;
         int ☃xxxxxxxxxxxxxxxxxxxxxx = 7 - ☃xxxxxxxxxxxxxxxxxx % 7;
         double ☃xxxxxxxxxxxxxxxxxxxxxxx = 0.1;

         for(int ☃xxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxx <= 37; ++☃xxxxxxxxxxxxxxxxxxxxxxxx) {
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxx = (double)☃xxxxxxxxxxxxxxxxxxxxxxxx / 37.0;
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = 1.0F - (float)((☃xxxxxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxx) % 7) / 7.0F;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxx * 2.0 - 1.0;
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = (1.0 - ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx) * ☃xxxxxxxxxxxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃
               + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxx
               + Math.sin(☃xxxxxxxxxxxxxxxxxxxxxxxxx * Math.PI * 8.0 + ☃xxxxxxxxxxxxxxxxxxx) * ☃xxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃
               + ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxx
               + Math.cos(☃xxxxxxxxxxxxxxxxxxxxxxxxx * Math.PI * 8.0 + ☃xxxxxxxxxxxxxxxxxxx) * 0.02
               + (0.1 + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx) * 1.0;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃
               + ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxx
               + Math.sin(☃xxxxxxxxxxxxxxxxxxxxxxxxx * Math.PI * 8.0 + ☃xxxxxxxxxxxxxxxxxxx) * ☃xxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.87F * ☃xxxxxxxxxxxxxxxxxxxxxxxxxx + 0.3F * (1.0F - ☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.91F * ☃xxxxxxxxxxxxxxxxxxxxxxxxxx + 0.6F * (1.0F - ☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.85F * ☃xxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F * (1.0F - ☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
            ☃xx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               .func_181666_a(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
            ☃xx.func_181662_b(
                  ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.1 * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.1 * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
               .func_181666_a(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
            if (☃xxxxxxxxxxxxxxxxxxxxxxxx > ☃.field_203052_f * 2) {
               break;
            }
         }

         ☃x.func_78381_a();
         ☃xx.func_181668_a(5, DefaultVertexFormats.field_181706_f);

         for(int ☃xxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxx <= 37; ++☃xxxxxxxxxxxxxxxxxxxxxxxx) {
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxx = (double)☃xxxxxxxxxxxxxxxxxxxxxxxx / 37.0;
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = 1.0F - (float)((☃xxxxxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxx) % 7) / 7.0F;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxx * 2.0 - 1.0;
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = (1.0 - ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx) * ☃xxxxxxxxxxxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃
               + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxx
               + Math.sin(☃xxxxxxxxxxxxxxxxxxxxxxxxx * Math.PI * 8.0 + ☃xxxxxxxxxxxxxxxxxxx) * ☃xxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃
               + ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxx
               + Math.cos(☃xxxxxxxxxxxxxxxxxxxxxxxxx * Math.PI * 8.0 + ☃xxxxxxxxxxxxxxxxxxx) * 0.01
               + (0.1 + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx) * 1.0;
            double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃
               + ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxx
               + Math.sin(☃xxxxxxxxxxxxxxxxxxxxxxxxx * Math.PI * 8.0 + ☃xxxxxxxxxxxxxxxxxxx) * ☃xxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.87F * ☃xxxxxxxxxxxxxxxxxxxxxxxxxx + 0.3F * (1.0F - ☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.91F * ☃xxxxxxxxxxxxxxxxxxxxxxxxxx + 0.6F * (1.0F - ☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
            float ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.85F * ☃xxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F * (1.0F - ☃xxxxxxxxxxxxxxxxxxxxxxxxxx);
            ☃xx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               .func_181666_a(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
            ☃xx.func_181662_b(
                  ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.1 * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.1 * ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
               .func_181666_a(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
            if (☃xxxxxxxxxxxxxxxxxxxxxxxx > ☃.field_203052_f * 2) {
               break;
            }
         }

         ☃x.func_78381_a();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
         GlStateManager.func_179089_o();
      }
   }
}
