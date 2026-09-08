package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped {
   public boolean field_78126_a;
   public boolean field_78125_b;

   public ModelEnderman(float var1) {
      super(0.0F, -14.0F, 64, 32);
      float ☃ = -14.0F;
      this.field_178720_f = new ModelRenderer(this, 0, 16);
      this.field_178720_f.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, ☃ - 0.5F);
      this.field_178720_f.func_78793_a(0.0F, -14.0F, 0.0F);
      this.field_78115_e = new ModelRenderer(this, 32, 16);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, ☃);
      this.field_78115_e.func_78793_a(0.0F, -14.0F, 0.0F);
      this.field_178723_h = new ModelRenderer(this, 56, 0);
      this.field_178723_h.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, ☃);
      this.field_178723_h.func_78793_a(-3.0F, -12.0F, 0.0F);
      this.field_178724_i = new ModelRenderer(this, 56, 0);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, ☃);
      this.field_178724_i.func_78793_a(5.0F, -12.0F, 0.0F);
      this.field_178721_j = new ModelRenderer(this, 56, 0);
      this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, ☃);
      this.field_178721_j.func_78793_a(-2.0F, -2.0F, 0.0F);
      this.field_178722_k = new ModelRenderer(this, 56, 0);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, ☃);
      this.field_178722_k.func_78793_a(2.0F, -2.0F, 0.0F);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78116_c.field_78806_j = true;
      float ☃ = -14.0F;
      this.field_78115_e.field_78795_f = 0.0F;
      this.field_78115_e.field_78797_d = -14.0F;
      this.field_78115_e.field_78798_e = -0.0F;
      this.field_178721_j.field_78795_f -= 0.0F;
      this.field_178722_k.field_78795_f -= 0.0F;
      this.field_178723_h.field_78795_f = (float)((double)this.field_178723_h.field_78795_f * 0.5);
      this.field_178724_i.field_78795_f = (float)((double)this.field_178724_i.field_78795_f * 0.5);
      this.field_178721_j.field_78795_f = (float)((double)this.field_178721_j.field_78795_f * 0.5);
      this.field_178722_k.field_78795_f = (float)((double)this.field_178722_k.field_78795_f * 0.5);
      float ☃x = 0.4F;
      if (this.field_178723_h.field_78795_f > 0.4F) {
         this.field_178723_h.field_78795_f = 0.4F;
      }

      if (this.field_178724_i.field_78795_f > 0.4F) {
         this.field_178724_i.field_78795_f = 0.4F;
      }

      if (this.field_178723_h.field_78795_f < -0.4F) {
         this.field_178723_h.field_78795_f = -0.4F;
      }

      if (this.field_178724_i.field_78795_f < -0.4F) {
         this.field_178724_i.field_78795_f = -0.4F;
      }

      if (this.field_178721_j.field_78795_f > 0.4F) {
         this.field_178721_j.field_78795_f = 0.4F;
      }

      if (this.field_178722_k.field_78795_f > 0.4F) {
         this.field_178722_k.field_78795_f = 0.4F;
      }

      if (this.field_178721_j.field_78795_f < -0.4F) {
         this.field_178721_j.field_78795_f = -0.4F;
      }

      if (this.field_178722_k.field_78795_f < -0.4F) {
         this.field_178722_k.field_78795_f = -0.4F;
      }

      if (this.field_78126_a) {
         this.field_178723_h.field_78795_f = -0.5F;
         this.field_178724_i.field_78795_f = -0.5F;
         this.field_178723_h.field_78808_h = 0.05F;
         this.field_178724_i.field_78808_h = -0.05F;
      }

      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178721_j.field_78798_e = 0.0F;
      this.field_178722_k.field_78798_e = 0.0F;
      this.field_178721_j.field_78797_d = -5.0F;
      this.field_178722_k.field_78797_d = -5.0F;
      this.field_78116_c.field_78798_e = -0.0F;
      this.field_78116_c.field_78797_d = -13.0F;
      this.field_178720_f.field_78800_c = this.field_78116_c.field_78800_c;
      this.field_178720_f.field_78797_d = this.field_78116_c.field_78797_d;
      this.field_178720_f.field_78798_e = this.field_78116_c.field_78798_e;
      this.field_178720_f.field_78795_f = this.field_78116_c.field_78795_f;
      this.field_178720_f.field_78796_g = this.field_78116_c.field_78796_g;
      this.field_178720_f.field_78808_h = this.field_78116_c.field_78808_h;
      if (this.field_78125_b) {
         float ☃ = 1.0F;
         this.field_78116_c.field_78797_d -= 5.0F;
      }
   }
}
