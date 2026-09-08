package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;

public class ModelPolarBear extends ModelQuadruped {
   public ModelPolarBear() {
      super(12, 0.0F);
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.field_78150_a = new ModelRenderer(this, 0, 0);
      this.field_78150_a.func_78790_a(-3.5F, -3.0F, -3.0F, 7, 7, 7, 0.0F);
      this.field_78150_a.func_78793_a(0.0F, 10.0F, -16.0F);
      this.field_78150_a.func_78784_a(0, 44).func_78790_a(-2.5F, 1.0F, -6.0F, 5, 3, 3, 0.0F);
      this.field_78150_a.func_78784_a(26, 0).func_78790_a(-4.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
      ModelRenderer ☃ = this.field_78150_a.func_78784_a(26, 0);
      ☃.field_78809_i = true;
      ☃.func_78790_a(2.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
      this.field_78148_b = new ModelRenderer(this);
      this.field_78148_b.func_78784_a(0, 19).func_78790_a(-5.0F, -13.0F, -7.0F, 14, 14, 11, 0.0F);
      this.field_78148_b.func_78784_a(39, 0).func_78790_a(-4.0F, -25.0F, -7.0F, 12, 12, 10, 0.0F);
      this.field_78148_b.func_78793_a(-2.0F, 9.0F, 12.0F);
      int ☃x = 10;
      this.field_78149_c = new ModelRenderer(this, 50, 22);
      this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
      this.field_78149_c.func_78793_a(-3.5F, 14.0F, 6.0F);
      this.field_78146_d = new ModelRenderer(this, 50, 22);
      this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
      this.field_78146_d.func_78793_a(3.5F, 14.0F, 6.0F);
      this.field_78147_e = new ModelRenderer(this, 50, 40);
      this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
      this.field_78147_e.func_78793_a(-2.5F, 14.0F, -7.0F);
      this.field_78144_f = new ModelRenderer(this, 50, 40);
      this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
      this.field_78144_f.func_78793_a(2.5F, 14.0F, -7.0F);
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
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (this.field_78091_s) {
         float ☃ = 2.0F;
         this.field_78145_g = 16.0F;
         this.field_78151_h = 4.0F;
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.6666667F, 0.6666667F, 0.6666667F);
         GlStateManager.func_179109_b(0.0F, this.field_78145_g * ☃, this.field_78151_h * ☃);
         this.field_78150_a.func_78785_a(☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * ☃, 0.0F);
         this.field_78148_b.func_78785_a(☃);
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
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      float ☃ = ☃ - (float)☃.field_70173_aa;
      float ☃x = ((EntityPolarBear)☃).func_189795_r(☃);
      ☃x *= ☃x;
      float ☃xx = 1.0F - ☃x;
      this.field_78148_b.field_78795_f = (float) (Math.PI / 2) - ☃x * (float) Math.PI * 0.35F;
      this.field_78148_b.field_78797_d = 9.0F * ☃xx + 11.0F * ☃x;
      this.field_78147_e.field_78797_d = 14.0F * ☃xx - 6.0F * ☃x;
      this.field_78147_e.field_78798_e = -8.0F * ☃xx - 4.0F * ☃x;
      this.field_78147_e.field_78795_f -= ☃x * (float) Math.PI * 0.45F;
      this.field_78144_f.field_78797_d = this.field_78147_e.field_78797_d;
      this.field_78144_f.field_78798_e = this.field_78147_e.field_78798_e;
      this.field_78144_f.field_78795_f -= ☃x * (float) Math.PI * 0.45F;
      this.field_78150_a.field_78797_d = 10.0F * ☃xx - 12.0F * ☃x;
      this.field_78150_a.field_78798_e = -16.0F * ☃xx - 3.0F * ☃x;
      this.field_78150_a.field_78795_f += ☃x * (float) Math.PI * 0.15F;
   }
}
