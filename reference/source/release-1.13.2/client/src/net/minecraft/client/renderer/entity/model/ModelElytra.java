package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class ModelElytra extends ModelBase {
   private final ModelRenderer field_187060_a;
   private final ModelRenderer field_187061_b = new ModelRenderer(this, 22, 0);

   public ModelElytra() {
      this.field_187061_b.func_78790_a(-10.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
      this.field_187060_a = new ModelRenderer(this, 22, 0);
      this.field_187060_a.field_78809_i = true;
      this.field_187060_a.func_78790_a(0.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.func_179101_C();
      GlStateManager.func_179129_p();
      if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70631_g_()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 1.5F, -0.1F);
         this.field_187061_b.func_78785_a(☃);
         this.field_187060_a.func_78785_a(☃);
         GlStateManager.func_179121_F();
      } else {
         this.field_187061_b.func_78785_a(☃);
         this.field_187060_a.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      float ☃ = (float) (Math.PI / 12);
      float ☃x = (float) (-Math.PI / 12);
      float ☃xx = 0.0F;
      float ☃xxx = 0.0F;
      if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_184613_cA()) {
         float ☃xxxx = 1.0F;
         if (☃.field_70181_x < 0.0) {
            Vec3d ☃xxxxx = new Vec3d(☃.field_70159_w, ☃.field_70181_x, ☃.field_70179_y).func_72432_b();
            ☃xxxx = 1.0F - (float)Math.pow(-☃xxxxx.field_72448_b, 1.5);
         }

         ☃ = ☃xxxx * (float) (Math.PI / 9) + (1.0F - ☃xxxx) * ☃;
         ☃x = ☃xxxx * (float) (-Math.PI / 2) + (1.0F - ☃xxxx) * ☃x;
      } else if (☃.func_70093_af()) {
         ☃ = (float) (Math.PI * 2.0 / 9.0);
         ☃x = (float) (-Math.PI / 4);
         ☃xx = 3.0F;
         ☃xxx = 0.08726646F;
      }

      this.field_187061_b.field_78800_c = 5.0F;
      this.field_187061_b.field_78797_d = ☃xx;
      if (☃ instanceof AbstractClientPlayer) {
         AbstractClientPlayer ☃ = (AbstractClientPlayer)☃;
         ☃.field_184835_a = (float)((double)☃.field_184835_a + (double)(☃ - ☃.field_184835_a) * 0.1);
         ☃.field_184836_b = (float)((double)☃.field_184836_b + (double)(☃xxx - ☃.field_184836_b) * 0.1);
         ☃.field_184837_c = (float)((double)☃.field_184837_c + (double)(☃x - ☃.field_184837_c) * 0.1);
         this.field_187061_b.field_78795_f = ☃.field_184835_a;
         this.field_187061_b.field_78796_g = ☃.field_184836_b;
         this.field_187061_b.field_78808_h = ☃.field_184837_c;
      } else {
         this.field_187061_b.field_78795_f = ☃;
         this.field_187061_b.field_78808_h = ☃x;
         this.field_187061_b.field_78796_g = ☃xxx;
      }

      this.field_187060_a.field_78800_c = -this.field_187061_b.field_78800_c;
      this.field_187060_a.field_78796_g = -this.field_187061_b.field_78796_g;
      this.field_187060_a.field_78797_d = this.field_187061_b.field_78797_d;
      this.field_187060_a.field_78795_f = this.field_187061_b.field_78795_f;
      this.field_187060_a.field_78808_h = -this.field_187061_b.field_78808_h;
   }
}
