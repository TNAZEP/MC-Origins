package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;

public abstract class RenderLiving<T extends EntityLiving> extends RenderLivingBase<T> {
   public RenderLiving(RenderManager var1, ModelBase var2, float var3) {
      super(☃, ☃, ☃);
   }

   protected boolean func_177070_b(T var1) {
      return super.func_177070_b(☃) && (☃.func_94059_bO() || ☃.func_145818_k_() && ☃ == this.field_76990_c.field_147941_i);
   }

   public boolean func_177071_a(T var1, ICamera var2, double var3, double var5, double var7) {
      if (super.func_177071_a(☃, ☃, ☃, ☃, ☃)) {
         return true;
      } else if (☃.func_110167_bD() && ☃.func_110166_bE() != null) {
         Entity ☃ = ☃.func_110166_bE();
         return ☃.func_78546_a(☃.func_184177_bl());
      } else {
         return false;
      }
   }

   public void func_76986_a(T var1, double var2, double var4, double var6, float var8, float var9) {
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      if (!this.field_188301_f) {
         this.func_110827_b(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public void func_177105_a(T var1) {
      int ☃ = ☃.func_70070_b();
      int ☃x = ☃ % 65536;
      int ☃xx = ☃ / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)☃x, (float)☃xx);
   }

   private double func_110828_a(double var1, double var3, double var5) {
      return ☃ + (☃ - ☃) * ☃;
   }

   protected void func_110827_b(T var1, double var2, double var4, double var6, float var8, float var9) {
      Entity ☃ = ☃.func_110166_bE();
      if (☃ != null) {
         ☃ -= (1.6 - (double)☃.field_70131_O) * 0.5;
         Tessellator ☃x = Tessellator.func_178181_a();
         BufferBuilder ☃xx = ☃x.func_178180_c();
         double ☃xxx = this.func_110828_a((double)☃.field_70126_B, (double)☃.field_70177_z, (double)(☃ * 0.5F)) * (float) (Math.PI / 180.0);
         double ☃xxxx = this.func_110828_a((double)☃.field_70127_C, (double)☃.field_70125_A, (double)(☃ * 0.5F)) * (float) (Math.PI / 180.0);
         double ☃xxxxx = Math.cos(☃xxx);
         double ☃xxxxxx = Math.sin(☃xxx);
         double ☃xxxxxxx = Math.sin(☃xxxx);
         if (☃ instanceof EntityHanging) {
            ☃xxxxx = 0.0;
            ☃xxxxxx = 0.0;
            ☃xxxxxxx = -1.0;
         }

         double ☃x = Math.cos(☃xxxx);
         double ☃xx = this.func_110828_a(☃.field_70169_q, ☃.field_70165_t, (double)☃) - ☃xxxxx * 0.7 - ☃xxxxxx * 0.5 * ☃x;
         double ☃xxx = this.func_110828_a(☃.field_70167_r + (double)☃.func_70047_e() * 0.7, ☃.field_70163_u + (double)☃.func_70047_e() * 0.7, (double)☃)
            - ☃xxxxxxx * 0.5
            - 0.25;
         double ☃xxxx = this.func_110828_a(☃.field_70166_s, ☃.field_70161_v, (double)☃) - ☃xxxxxx * 0.7 + ☃xxxxx * 0.5 * ☃x;
         double ☃xxxxx = this.func_110828_a((double)☃.field_70760_ar, (double)☃.field_70761_aq, (double)☃) * (float) (Math.PI / 180.0) + (Math.PI / 2);
         ☃xxxxx = Math.cos(☃xxxxx) * (double)☃.field_70130_N * 0.4;
         ☃xxxxxx = Math.sin(☃xxxxx) * (double)☃.field_70130_N * 0.4;
         double ☃xxxxxx = this.func_110828_a(☃.field_70169_q, ☃.field_70165_t, (double)☃) + ☃xxxxx;
         double ☃xxxxxxx = this.func_110828_a(☃.field_70167_r, ☃.field_70163_u, (double)☃);
         double ☃xxxxxxxx = this.func_110828_a(☃.field_70166_s, ☃.field_70161_v, (double)☃) + ☃xxxxxx;
         ☃ += ☃xxxxx;
         ☃ += ☃xxxxxx;
         double ☃xxxxxxxxx = (double)((float)(☃xx - ☃xxxxxx));
         double ☃xxxxxxxxxx = (double)((float)(☃xxx - ☃xxxxxxx));
         double ☃xxxxxxxxxxx = (double)((float)(☃xxxx - ☃xxxxxxxx));
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179129_p();
         int ☃xxxxxxxxxxxx = 24;
         double ☃xxxxxxxxxxxxx = 0.025;
         ☃xx.func_181668_a(5, DefaultVertexFormats.field_181706_f);

         for(int ☃xxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxx <= 24; ++☃xxxxxxxxxxxxxx) {
            float ☃xxxxxxxxxxxxxxx = 0.5F;
            float ☃xxxxxxxxxxxxxxxx = 0.4F;
            float ☃xxxxxxxxxxxxxxxxx = 0.3F;
            if (☃xxxxxxxxxxxxxx % 2 == 0) {
               ☃xxxxxxxxxxxxxxx *= 0.7F;
               ☃xxxxxxxxxxxxxxxx *= 0.7F;
               ☃xxxxxxxxxxxxxxxxx *= 0.7F;
            }

            float ☃xxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxx / 24.0F;
            ☃xx.func_181662_b(
                  ☃ + ☃xxxxxxxxx * (double)☃xxxxxxxxxxxxxxx + 0.0,
                  ☃
                     + ☃xxxxxxxxxx * (double)(☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 0.5
                     + (double)((24.0F - (float)☃xxxxxxxxxxxxxx) / 18.0F + 0.125F),
                  ☃ + ☃xxxxxxxxxxx * (double)☃xxxxxxxxxxxxxxx
               )
               .func_181666_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
            ☃xx.func_181662_b(
                  ☃ + ☃xxxxxxxxx * (double)☃xxxxxxxxxxxxxxx + 0.025,
                  ☃
                     + ☃xxxxxxxxxx * (double)(☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 0.5
                     + (double)((24.0F - (float)☃xxxxxxxxxxxxxx) / 18.0F + 0.125F)
                     + 0.025,
                  ☃ + ☃xxxxxxxxxxx * (double)☃xxxxxxxxxxxxxxx
               )
               .func_181666_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
         }

         ☃x.func_78381_a();
         ☃xx.func_181668_a(5, DefaultVertexFormats.field_181706_f);

         for(int ☃xxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxx <= 24; ++☃xxxxxxxxxxxxxx) {
            float ☃xxxxxxxxxxxxxxx = 0.5F;
            float ☃xxxxxxxxxxxxxxxx = 0.4F;
            float ☃xxxxxxxxxxxxxxxxx = 0.3F;
            if (☃xxxxxxxxxxxxxx % 2 == 0) {
               ☃xxxxxxxxxxxxxxx *= 0.7F;
               ☃xxxxxxxxxxxxxxxx *= 0.7F;
               ☃xxxxxxxxxxxxxxxxx *= 0.7F;
            }

            float ☃xxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxx / 24.0F;
            ☃xx.func_181662_b(
                  ☃ + ☃xxxxxxxxx * (double)☃xxxxxxxxxxxxxxx + 0.0,
                  ☃
                     + ☃xxxxxxxxxx * (double)(☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 0.5
                     + (double)((24.0F - (float)☃xxxxxxxxxxxxxx) / 18.0F + 0.125F)
                     + 0.025,
                  ☃ + ☃xxxxxxxxxxx * (double)☃xxxxxxxxxxxxxxx
               )
               .func_181666_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
            ☃xx.func_181662_b(
                  ☃ + ☃xxxxxxxxx * (double)☃xxxxxxxxxxxxxxx + 0.025,
                  ☃
                     + ☃xxxxxxxxxx * (double)(☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 0.5
                     + (double)((24.0F - (float)☃xxxxxxxxxxxxxx) / 18.0F + 0.125F),
                  ☃ + ☃xxxxxxxxxxx * (double)☃xxxxxxxxxxxxxxx + 0.025
               )
               .func_181666_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, 1.0F)
               .func_181675_d();
         }

         ☃x.func_78381_a();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
         GlStateManager.func_179089_o();
      }
   }
}
