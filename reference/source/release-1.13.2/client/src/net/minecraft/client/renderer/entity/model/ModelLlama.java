package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractChestHorse;

public class ModelLlama extends ModelQuadruped {
   private final ModelRenderer field_191226_i;
   private final ModelRenderer field_191227_j;

   public ModelLlama(float var1) {
      super(15, ☃);
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.field_78150_a = new ModelRenderer(this, 0, 0);
      this.field_78150_a.func_78790_a(-2.0F, -14.0F, -10.0F, 4, 4, 9, ☃);
      this.field_78150_a.func_78793_a(0.0F, 7.0F, -6.0F);
      this.field_78150_a.func_78784_a(0, 14).func_78790_a(-4.0F, -16.0F, -6.0F, 8, 18, 6, ☃);
      this.field_78150_a.func_78784_a(17, 0).func_78790_a(-4.0F, -19.0F, -4.0F, 3, 3, 2, ☃);
      this.field_78150_a.func_78784_a(17, 0).func_78790_a(1.0F, -19.0F, -4.0F, 3, 3, 2, ☃);
      this.field_78148_b = new ModelRenderer(this, 29, 0);
      this.field_78148_b.func_78790_a(-6.0F, -10.0F, -7.0F, 12, 18, 10, ☃);
      this.field_78148_b.func_78793_a(0.0F, 5.0F, 2.0F);
      this.field_191226_i = new ModelRenderer(this, 45, 28);
      this.field_191226_i.func_78790_a(-3.0F, 0.0F, 0.0F, 8, 8, 3, ☃);
      this.field_191226_i.func_78793_a(-8.5F, 3.0F, 3.0F);
      this.field_191226_i.field_78796_g = (float) (Math.PI / 2);
      this.field_191227_j = new ModelRenderer(this, 45, 41);
      this.field_191227_j.func_78790_a(-3.0F, 0.0F, 0.0F, 8, 8, 3, ☃);
      this.field_191227_j.func_78793_a(5.5F, 3.0F, 3.0F);
      this.field_191227_j.field_78796_g = (float) (Math.PI / 2);
      int ☃ = 4;
      int ☃x = 14;
      this.field_78149_c = new ModelRenderer(this, 29, 29);
      this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, ☃);
      this.field_78149_c.func_78793_a(-2.5F, 10.0F, 6.0F);
      this.field_78146_d = new ModelRenderer(this, 29, 29);
      this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, ☃);
      this.field_78146_d.func_78793_a(2.5F, 10.0F, 6.0F);
      this.field_78147_e = new ModelRenderer(this, 29, 29);
      this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, ☃);
      this.field_78147_e.func_78793_a(-2.5F, 10.0F, -4.0F);
      this.field_78144_f = new ModelRenderer(this, 29, 29);
      this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, ☃);
      this.field_78144_f.func_78793_a(2.5F, 10.0F, -4.0F);
      --this.field_78149_c.field_78800_c;
      ++this.field_78146_d.field_78800_c;
      this.field_78149_c.field_78798_e += 0.0F;
      this.field_78146_d.field_78798_e += 0.0F;
      --this.field_78147_e.field_78800_c;
      ++this.field_78144_f.field_78800_c;
      --this.field_78147_e.field_78798_e;
      --this.field_78144_f.field_78798_e;
      this.field_78151_h += 2.0F;
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      AbstractChestHorse ☃ = (AbstractChestHorse)☃;
      boolean ☃x = !☃.func_70631_g_() && ☃.func_190695_dh();
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (this.field_78091_s) {
         float ☃xx = 2.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, this.field_78145_g * ☃, this.field_78151_h * ☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         float ☃xxx = 0.7F;
         GlStateManager.func_179152_a(0.71428573F, 0.64935064F, 0.7936508F);
         GlStateManager.func_179109_b(0.0F, 21.0F * ☃, 0.22F);
         this.field_78150_a.func_78785_a(☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         float ☃xxxx = 1.1F;
         GlStateManager.func_179152_a(0.625F, 0.45454544F, 0.45454544F);
         GlStateManager.func_179109_b(0.0F, 33.0F * ☃, 0.0F);
         this.field_78148_b.func_78785_a(☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.45454544F, 0.41322312F, 0.45454544F);
         GlStateManager.func_179109_b(0.0F, 33.0F * ☃, 0.0F);
         this.field_78149_c.func_78785_a(☃);
         this.field_78146_d.func_78785_a(☃);
         this.field_78147_e.func_78785_a(☃);
         this.field_78144_f.func_78785_a(☃);
         GlStateManager.func_179121_F();
      } else {
         this.field_78150_a.func_78785_a(☃);
         this.field_78148_b.func_78785_a(☃);
         this.field_78149_c.func_78785_a(☃);
         this.field_78146_d.func_78785_a(☃);
         this.field_78147_e.func_78785_a(☃);
         this.field_78144_f.func_78785_a(☃);
      }

      if (☃x) {
         this.field_191226_i.func_78785_a(☃);
         this.field_191227_j.func_78785_a(☃);
      }
   }
}
