package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.math.MathHelper;

public class ModelHorseArmorBase extends ModelBase {
   protected ModelRenderer field_199049_a;
   protected ModelRenderer field_199050_b;
   private final ModelRenderer field_199051_c;
   private final ModelRenderer field_199052_d;
   private final ModelRenderer field_199053_e;
   private final ModelRenderer field_199054_f;
   private final ModelRenderer field_199055_g;
   private final ModelRenderer[] field_199056_h;
   private final ModelRenderer[] field_209234_i;

   public ModelHorseArmorBase() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_199049_a = new ModelRenderer(this, 0, 32);
      this.field_199049_a.func_78790_a(-5.0F, -8.0F, -17.0F, 10, 10, 22, 0.05F);
      this.field_199049_a.func_78793_a(0.0F, 11.0F, 5.0F);
      this.field_199050_b = new ModelRenderer(this, 0, 35);
      this.field_199050_b.func_78789_a(-2.05F, -6.0F, -2.0F, 4, 12, 7);
      this.field_199050_b.field_78795_f = (float) (Math.PI / 6);
      ModelRenderer ☃ = new ModelRenderer(this, 0, 13);
      ☃.func_78789_a(-3.0F, -11.0F, -2.0F, 6, 5, 7);
      ModelRenderer ☃x = new ModelRenderer(this, 56, 36);
      ☃x.func_78789_a(-1.0F, -11.0F, 5.01F, 2, 16, 2);
      ModelRenderer ☃xx = new ModelRenderer(this, 0, 25);
      ☃xx.func_78789_a(-2.0F, -11.0F, -7.0F, 4, 5, 5);
      this.field_199050_b.func_78792_a(☃);
      this.field_199050_b.func_78792_a(☃x);
      this.field_199050_b.func_78792_a(☃xx);
      this.func_199047_a(this.field_199050_b);
      this.field_199051_c = new ModelRenderer(this, 48, 21);
      this.field_199051_c.field_78809_i = true;
      this.field_199051_c.func_78789_a(-3.0F, -1.01F, -1.0F, 4, 11, 4);
      this.field_199051_c.func_78793_a(4.0F, 14.0F, 7.0F);
      this.field_199052_d = new ModelRenderer(this, 48, 21);
      this.field_199052_d.func_78789_a(-1.0F, -1.01F, -1.0F, 4, 11, 4);
      this.field_199052_d.func_78793_a(-4.0F, 14.0F, 7.0F);
      this.field_199053_e = new ModelRenderer(this, 48, 21);
      this.field_199053_e.field_78809_i = true;
      this.field_199053_e.func_78789_a(-3.0F, -1.01F, -1.9F, 4, 11, 4);
      this.field_199053_e.func_78793_a(4.0F, 6.0F, -12.0F);
      this.field_199054_f = new ModelRenderer(this, 48, 21);
      this.field_199054_f.func_78789_a(-1.0F, -1.01F, -1.9F, 4, 11, 4);
      this.field_199054_f.func_78793_a(-4.0F, 6.0F, -12.0F);
      this.field_199055_g = new ModelRenderer(this, 42, 36);
      this.field_199055_g.func_78789_a(-1.5F, 0.0F, 0.0F, 3, 14, 4);
      this.field_199055_g.func_78793_a(0.0F, -5.0F, 2.0F);
      this.field_199055_g.field_78795_f = (float) (Math.PI / 6);
      this.field_199049_a.func_78792_a(this.field_199055_g);
      ModelRenderer ☃xxx = new ModelRenderer(this, 26, 0);
      ☃xxx.func_78790_a(-5.0F, -8.0F, -9.0F, 10, 9, 9, 0.5F);
      this.field_199049_a.func_78792_a(☃xxx);
      ModelRenderer ☃xxxx = new ModelRenderer(this, 29, 5);
      ☃xxxx.func_78789_a(2.0F, -9.0F, -6.0F, 1, 2, 2);
      this.field_199050_b.func_78792_a(☃xxxx);
      ModelRenderer ☃xxxxx = new ModelRenderer(this, 29, 5);
      ☃xxxxx.func_78789_a(-3.0F, -9.0F, -6.0F, 1, 2, 2);
      this.field_199050_b.func_78792_a(☃xxxxx);
      ModelRenderer ☃xxxxxx = new ModelRenderer(this, 32, 2);
      ☃xxxxxx.func_78789_a(3.1F, -6.0F, -8.0F, 0, 3, 16);
      ☃xxxxxx.field_78795_f = (float) (-Math.PI / 6);
      this.field_199050_b.func_78792_a(☃xxxxxx);
      ModelRenderer ☃xxxxxxx = new ModelRenderer(this, 32, 2);
      ☃xxxxxxx.func_78789_a(-3.1F, -6.0F, -8.0F, 0, 3, 16);
      ☃xxxxxxx.field_78795_f = (float) (-Math.PI / 6);
      this.field_199050_b.func_78792_a(☃xxxxxxx);
      ModelRenderer ☃xxxxxxxx = new ModelRenderer(this, 1, 1);
      ☃xxxxxxxx.func_78790_a(-3.0F, -11.0F, -1.9F, 6, 5, 6, 0.2F);
      this.field_199050_b.func_78792_a(☃xxxxxxxx);
      ModelRenderer ☃xxxxxxxxx = new ModelRenderer(this, 19, 0);
      ☃xxxxxxxxx.func_78790_a(-2.0F, -11.0F, -4.0F, 4, 5, 2, 0.2F);
      this.field_199050_b.func_78792_a(☃xxxxxxxxx);
      this.field_199056_h = new ModelRenderer[]{☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxxxx, ☃xxxxxxxxx};
      this.field_209234_i = new ModelRenderer[]{☃xxxxxx, ☃xxxxxxx};
   }

   protected void func_199047_a(ModelRenderer var1) {
      ModelRenderer ☃ = new ModelRenderer(this, 19, 16);
      ☃.func_78790_a(0.55F, -13.0F, 4.0F, 2, 3, 1, -0.001F);
      ModelRenderer ☃x = new ModelRenderer(this, 19, 16);
      ☃x.func_78790_a(-2.55F, -13.0F, 4.0F, 2, 3, 1, -0.001F);
      ☃.func_78792_a(☃);
      ☃.func_78792_a(☃x);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      AbstractHorse ☃ = (AbstractHorse)☃;
      boolean ☃x = ☃.func_70631_g_();
      float ☃xx = ☃.func_110254_bY();
      boolean ☃xxx = ☃.func_110257_ck();
      boolean ☃xxxx = ☃.func_184207_aI();

      for(ModelRenderer ☃xxxxx : this.field_199056_h) {
         ☃xxxxx.field_78806_j = ☃xxx;
      }

      for(ModelRenderer ☃xxxxx : this.field_209234_i) {
         ☃xxxxx.field_78806_j = ☃xxxx && ☃xxx;
      }

      if (☃x) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(☃xx, 0.5F + ☃xx * 0.5F, ☃xx);
         GlStateManager.func_179109_b(0.0F, 0.95F * (1.0F - ☃xx), 0.0F);
      }

      this.field_199051_c.func_78785_a(☃);
      this.field_199052_d.func_78785_a(☃);
      this.field_199053_e.func_78785_a(☃);
      this.field_199054_f.func_78785_a(☃);
      if (☃x) {
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(☃xx, ☃xx, ☃xx);
         GlStateManager.func_179109_b(0.0F, 2.3F * (1.0F - ☃xx), 0.0F);
      }

      this.field_199049_a.func_78785_a(☃);
      if (☃x) {
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         float ☃xxxxx = ☃xx + 0.1F * ☃xx;
         GlStateManager.func_179152_a(☃xxxxx, ☃xxxxx, ☃xxxxx);
         GlStateManager.func_179109_b(0.0F, 2.25F * (1.0F - ☃xxxxx), 0.1F * (1.4F - ☃xxxxx));
      }

      this.field_199050_b.func_78785_a(☃);
      if (☃x) {
         GlStateManager.func_179121_F();
      }
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      super.func_78086_a(☃, ☃, ☃, ☃);
      float ☃ = this.func_199048_a(☃.field_70760_ar, ☃.field_70761_aq, ☃);
      float ☃x = this.func_199048_a(☃.field_70758_at, ☃.field_70759_as, ☃);
      float ☃xx = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      float ☃xxx = ☃x - ☃;
      float ☃xxxx = ☃xx * (float) (Math.PI / 180.0);
      if (☃xxx > 20.0F) {
         ☃xxx = 20.0F;
      }

      if (☃xxx < -20.0F) {
         ☃xxx = -20.0F;
      }

      if (☃ > 0.2F) {
         ☃xxxx += MathHelper.func_76134_b(☃ * 0.4F) * 0.15F * ☃;
      }

      AbstractHorse ☃ = (AbstractHorse)☃;
      float ☃x = ☃.func_110258_o(☃);
      float ☃xx = ☃.func_110223_p(☃);
      float ☃xxx = 1.0F - ☃xx;
      float ☃xxxx = ☃.func_110201_q(☃);
      boolean ☃xxxxx = ☃.field_110278_bp != 0;
      float ☃xxxxxx = (float)☃.field_70173_aa + ☃;
      this.field_199050_b.field_78797_d = 4.0F;
      this.field_199050_b.field_78798_e = -12.0F;
      this.field_199049_a.field_78795_f = 0.0F;
      this.field_199050_b.field_78795_f = (float) (Math.PI / 6) + ☃xxxx;
      this.field_199050_b.field_78796_g = ☃xxx * (float) (Math.PI / 180.0);
      float ☃xxxxxxx = ☃.func_70090_H() ? 0.2F : 1.0F;
      float ☃xxxxxxxx = MathHelper.func_76134_b(☃xxxxxxx * ☃ * 0.6662F + (float) Math.PI);
      float ☃xxxxxxxxx = ☃xxxxxxxx * 0.8F * ☃;
      float ☃xxxxxxxxxx = (1.0F - Math.max(☃xx, ☃x)) * ((float) (Math.PI / 6) + ☃xxxx + ☃xxxx * MathHelper.func_76126_a(☃xxxxxx) * 0.05F);
      this.field_199050_b.field_78795_f = ☃xx * ((float) (Math.PI / 12) + ☃xxxx) + ☃x * (2.1816616F + MathHelper.func_76126_a(☃xxxxxx) * 0.05F) + ☃xxxxxxxxxx;
      this.field_199050_b.field_78796_g = ☃xx * ☃xxx * (float) (Math.PI / 180.0) + (1.0F - Math.max(☃xx, ☃x)) * this.field_199050_b.field_78796_g;
      this.field_199050_b.field_78797_d = ☃xx * -4.0F + ☃x * 11.0F + (1.0F - Math.max(☃xx, ☃x)) * this.field_199050_b.field_78797_d;
      this.field_199050_b.field_78798_e = ☃xx * -4.0F + ☃x * -12.0F + (1.0F - Math.max(☃xx, ☃x)) * this.field_199050_b.field_78798_e;
      this.field_199049_a.field_78795_f = ☃xx * (float) (-Math.PI / 4) + ☃xxx * this.field_199049_a.field_78795_f;
      float ☃xxxxxxxxxxx = (float) (Math.PI / 12) * ☃xx;
      float ☃xxxxxxxxxxxx = MathHelper.func_76134_b(☃xxxxxx * 0.6F + (float) Math.PI);
      this.field_199053_e.field_78797_d = 2.0F * ☃xx + 14.0F * ☃xxx;
      this.field_199053_e.field_78798_e = -6.0F * ☃xx - 10.0F * ☃xxx;
      this.field_199054_f.field_78797_d = this.field_199053_e.field_78797_d;
      this.field_199054_f.field_78798_e = this.field_199053_e.field_78798_e;
      float ☃xxxxxxxxxxxxx = ((float) (-Math.PI / 3) + ☃xxxxxxxxxxxx) * ☃xx + ☃xxxxxxxxx * ☃xxx;
      float ☃xxxxxxxxxxxxxx = ((float) (-Math.PI / 3) - ☃xxxxxxxxxxxx) * ☃xx - ☃xxxxxxxxx * ☃xxx;
      this.field_199051_c.field_78795_f = ☃xxxxxxxxxxx - ☃xxxxxxxx * 0.5F * ☃ * ☃xxx;
      this.field_199052_d.field_78795_f = ☃xxxxxxxxxxx + ☃xxxxxxxx * 0.5F * ☃ * ☃xxx;
      this.field_199053_e.field_78795_f = ☃xxxxxxxxxxxxx;
      this.field_199054_f.field_78795_f = ☃xxxxxxxxxxxxxx;
      this.field_199055_g.field_78795_f = (float) (Math.PI / 6) + ☃ * 0.75F;
      this.field_199055_g.field_78797_d = -5.0F + ☃;
      this.field_199055_g.field_78798_e = 2.0F + ☃ * 2.0F;
      if (☃xxxxx) {
         this.field_199055_g.field_78796_g = MathHelper.func_76134_b(☃xxxxxx * 0.7F);
      } else {
         this.field_199055_g.field_78796_g = 0.0F;
      }
   }

   private float func_199048_a(float var1, float var2, float var3) {
      float ☃ = ☃ - ☃;

      while(☃ < -180.0F) {
         ☃ += 360.0F;
      }

      while(☃ >= 180.0F) {
         ☃ -= 360.0F;
      }

      return ☃ + ☃ * ☃;
   }
}
