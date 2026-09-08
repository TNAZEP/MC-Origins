package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelChicken extends ModelBase {
   private final ModelRenderer field_78142_a;
   private final ModelRenderer field_78140_b;
   private final ModelRenderer field_78141_c;
   private final ModelRenderer field_78138_d;
   private final ModelRenderer field_78139_e;
   private final ModelRenderer field_78136_f;
   private final ModelRenderer field_78137_g;
   private final ModelRenderer field_78143_h;

   public ModelChicken() {
      int ☃ = 16;
      this.field_78142_a = new ModelRenderer(this, 0, 0);
      this.field_78142_a.func_78790_a(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
      this.field_78142_a.func_78793_a(0.0F, 15.0F, -4.0F);
      this.field_78137_g = new ModelRenderer(this, 14, 0);
      this.field_78137_g.func_78790_a(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
      this.field_78137_g.func_78793_a(0.0F, 15.0F, -4.0F);
      this.field_78143_h = new ModelRenderer(this, 14, 4);
      this.field_78143_h.func_78790_a(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
      this.field_78143_h.func_78793_a(0.0F, 15.0F, -4.0F);
      this.field_78140_b = new ModelRenderer(this, 0, 9);
      this.field_78140_b.func_78790_a(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
      this.field_78140_b.func_78793_a(0.0F, 16.0F, 0.0F);
      this.field_78141_c = new ModelRenderer(this, 26, 0);
      this.field_78141_c.func_78789_a(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.field_78141_c.func_78793_a(-2.0F, 19.0F, 1.0F);
      this.field_78138_d = new ModelRenderer(this, 26, 0);
      this.field_78138_d.func_78789_a(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.field_78138_d.func_78793_a(1.0F, 19.0F, 1.0F);
      this.field_78139_e = new ModelRenderer(this, 24, 13);
      this.field_78139_e.func_78789_a(0.0F, 0.0F, -3.0F, 1, 4, 6);
      this.field_78139_e.func_78793_a(-4.0F, 13.0F, 0.0F);
      this.field_78136_f = new ModelRenderer(this, 24, 13);
      this.field_78136_f.func_78789_a(-1.0F, 0.0F, -3.0F, 1, 4, 6);
      this.field_78136_f.func_78793_a(4.0F, 13.0F, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (this.field_78091_s) {
         float ☃ = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 5.0F * ☃, 2.0F * ☃);
         this.field_78142_a.func_78785_a(☃);
         this.field_78137_g.func_78785_a(☃);
         this.field_78143_h.func_78785_a(☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * ☃, 0.0F);
         this.field_78140_b.func_78785_a(☃);
         this.field_78141_c.func_78785_a(☃);
         this.field_78138_d.func_78785_a(☃);
         this.field_78139_e.func_78785_a(☃);
         this.field_78136_f.func_78785_a(☃);
         GlStateManager.func_179121_F();
      } else {
         this.field_78142_a.func_78785_a(☃);
         this.field_78137_g.func_78785_a(☃);
         this.field_78143_h.func_78785_a(☃);
         this.field_78140_b.func_78785_a(☃);
         this.field_78141_c.func_78785_a(☃);
         this.field_78138_d.func_78785_a(☃);
         this.field_78139_e.func_78785_a(☃);
         this.field_78136_f.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_78142_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78142_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78137_g.field_78795_f = this.field_78142_a.field_78795_f;
      this.field_78137_g.field_78796_g = this.field_78142_a.field_78796_g;
      this.field_78143_h.field_78795_f = this.field_78142_a.field_78795_f;
      this.field_78143_h.field_78796_g = this.field_78142_a.field_78796_g;
      this.field_78140_b.field_78795_f = (float) (Math.PI / 2);
      this.field_78141_c.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
      this.field_78138_d.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
      this.field_78139_e.field_78808_h = ☃;
      this.field_78136_f.field_78808_h = -☃;
   }
}
