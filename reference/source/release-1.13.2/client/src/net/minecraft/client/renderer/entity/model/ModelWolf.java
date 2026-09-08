package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.MathHelper;

public class ModelWolf extends ModelBase {
   private final ModelRenderer field_78185_a;
   private final ModelRenderer field_78183_b;
   private final ModelRenderer field_78184_c;
   private final ModelRenderer field_78181_d;
   private final ModelRenderer field_78182_e;
   private final ModelRenderer field_78179_f;
   private final ModelRenderer field_78180_g;
   private final ModelRenderer field_78186_h;

   public ModelWolf() {
      float ☃ = 0.0F;
      float ☃x = 13.5F;
      this.field_78185_a = new ModelRenderer(this, 0, 0);
      this.field_78185_a.func_78790_a(-2.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F);
      this.field_78185_a.func_78793_a(-1.0F, 13.5F, -7.0F);
      this.field_78183_b = new ModelRenderer(this, 18, 14);
      this.field_78183_b.func_78790_a(-3.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F);
      this.field_78183_b.func_78793_a(0.0F, 14.0F, 2.0F);
      this.field_78186_h = new ModelRenderer(this, 21, 0);
      this.field_78186_h.func_78790_a(-3.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
      this.field_78186_h.func_78793_a(-1.0F, 14.0F, 2.0F);
      this.field_78184_c = new ModelRenderer(this, 0, 18);
      this.field_78184_c.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78184_c.func_78793_a(-2.5F, 16.0F, 7.0F);
      this.field_78181_d = new ModelRenderer(this, 0, 18);
      this.field_78181_d.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78181_d.func_78793_a(0.5F, 16.0F, 7.0F);
      this.field_78182_e = new ModelRenderer(this, 0, 18);
      this.field_78182_e.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78182_e.func_78793_a(-2.5F, 16.0F, -4.0F);
      this.field_78179_f = new ModelRenderer(this, 0, 18);
      this.field_78179_f.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78179_f.func_78793_a(0.5F, 16.0F, -4.0F);
      this.field_78180_g = new ModelRenderer(this, 9, 18);
      this.field_78180_g.func_78790_a(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.field_78180_g.func_78793_a(-1.0F, 12.0F, 8.0F);
      this.field_78185_a.func_78784_a(16, 14).func_78790_a(-2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
      this.field_78185_a.func_78784_a(16, 14).func_78790_a(2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
      this.field_78185_a.func_78784_a(0, 10).func_78790_a(-0.5F, 0.0F, -5.0F, 3, 3, 4, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (this.field_78091_s) {
         float ☃ = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 5.0F * ☃, 2.0F * ☃);
         this.field_78185_a.func_78791_b(☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * ☃, 0.0F);
         this.field_78183_b.func_78785_a(☃);
         this.field_78184_c.func_78785_a(☃);
         this.field_78181_d.func_78785_a(☃);
         this.field_78182_e.func_78785_a(☃);
         this.field_78179_f.func_78785_a(☃);
         this.field_78180_g.func_78791_b(☃);
         this.field_78186_h.func_78785_a(☃);
         GlStateManager.func_179121_F();
      } else {
         this.field_78185_a.func_78791_b(☃);
         this.field_78183_b.func_78785_a(☃);
         this.field_78184_c.func_78785_a(☃);
         this.field_78181_d.func_78785_a(☃);
         this.field_78182_e.func_78785_a(☃);
         this.field_78179_f.func_78785_a(☃);
         this.field_78180_g.func_78791_b(☃);
         this.field_78186_h.func_78785_a(☃);
      }
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityWolf ☃ = (EntityWolf)☃;
      if (☃.func_70919_bu()) {
         this.field_78180_g.field_78796_g = 0.0F;
      } else {
         this.field_78180_g.field_78796_g = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
      }

      if (☃.func_70906_o()) {
         this.field_78186_h.func_78793_a(-1.0F, 16.0F, -3.0F);
         this.field_78186_h.field_78795_f = (float) (Math.PI * 2.0 / 5.0);
         this.field_78186_h.field_78796_g = 0.0F;
         this.field_78183_b.func_78793_a(0.0F, 18.0F, 0.0F);
         this.field_78183_b.field_78795_f = (float) (Math.PI / 4);
         this.field_78180_g.func_78793_a(-1.0F, 21.0F, 6.0F);
         this.field_78184_c.func_78793_a(-2.5F, 22.0F, 2.0F);
         this.field_78184_c.field_78795_f = (float) (Math.PI * 3.0 / 2.0);
         this.field_78181_d.func_78793_a(0.5F, 22.0F, 2.0F);
         this.field_78181_d.field_78795_f = (float) (Math.PI * 3.0 / 2.0);
         this.field_78182_e.field_78795_f = 5.811947F;
         this.field_78182_e.func_78793_a(-2.49F, 17.0F, -4.0F);
         this.field_78179_f.field_78795_f = 5.811947F;
         this.field_78179_f.func_78793_a(0.51F, 17.0F, -4.0F);
      } else {
         this.field_78183_b.func_78793_a(0.0F, 14.0F, 2.0F);
         this.field_78183_b.field_78795_f = (float) (Math.PI / 2);
         this.field_78186_h.func_78793_a(-1.0F, 14.0F, -3.0F);
         this.field_78186_h.field_78795_f = this.field_78183_b.field_78795_f;
         this.field_78180_g.func_78793_a(-1.0F, 12.0F, 8.0F);
         this.field_78184_c.func_78793_a(-2.5F, 16.0F, 7.0F);
         this.field_78181_d.func_78793_a(0.5F, 16.0F, 7.0F);
         this.field_78182_e.func_78793_a(-2.5F, 16.0F, -4.0F);
         this.field_78179_f.func_78793_a(0.5F, 16.0F, -4.0F);
         this.field_78184_c.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
         this.field_78181_d.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
         this.field_78182_e.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
         this.field_78179_f.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
      }

      this.field_78185_a.field_78808_h = ☃.func_70917_k(☃) + ☃.func_70923_f(☃, 0.0F);
      this.field_78186_h.field_78808_h = ☃.func_70923_f(☃, -0.08F);
      this.field_78183_b.field_78808_h = ☃.func_70923_f(☃, -0.16F);
      this.field_78180_g.field_78808_h = ☃.func_70923_f(☃, -0.2F);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78185_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78185_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78180_g.field_78795_f = ☃;
   }
}
