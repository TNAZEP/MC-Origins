package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelQuadruped extends ModelBase {
   protected ModelRenderer field_78150_a;
   protected ModelRenderer field_78148_b;
   protected ModelRenderer field_78149_c;
   protected ModelRenderer field_78146_d;
   protected ModelRenderer field_78147_e;
   protected ModelRenderer field_78144_f;
   protected float field_78145_g = 8.0F;
   protected float field_78151_h = 4.0F;

   public ModelQuadruped(int var1, float var2) {
      this.field_78150_a = new ModelRenderer(this, 0, 0);
      this.field_78150_a.func_78790_a(-4.0F, -4.0F, -8.0F, 8, 8, 8, ☃);
      this.field_78150_a.func_78793_a(0.0F, (float)(18 - ☃), -6.0F);
      this.field_78148_b = new ModelRenderer(this, 28, 8);
      this.field_78148_b.func_78790_a(-5.0F, -10.0F, -7.0F, 10, 16, 8, ☃);
      this.field_78148_b.func_78793_a(0.0F, (float)(17 - ☃), 2.0F);
      this.field_78149_c = new ModelRenderer(this, 0, 16);
      this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, ☃, 4, ☃);
      this.field_78149_c.func_78793_a(-3.0F, (float)(24 - ☃), 7.0F);
      this.field_78146_d = new ModelRenderer(this, 0, 16);
      this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, ☃, 4, ☃);
      this.field_78146_d.func_78793_a(3.0F, (float)(24 - ☃), 7.0F);
      this.field_78147_e = new ModelRenderer(this, 0, 16);
      this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, ☃, 4, ☃);
      this.field_78147_e.func_78793_a(-3.0F, (float)(24 - ☃), -5.0F);
      this.field_78144_f = new ModelRenderer(this, 0, 16);
      this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, ☃, 4, ☃);
      this.field_78144_f.func_78793_a(3.0F, (float)(24 - ☃), -5.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (this.field_78091_s) {
         float ☃ = 2.0F;
         GlStateManager.func_179094_E();
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
      this.field_78150_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78150_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78148_b.field_78795_f = (float) (Math.PI / 2);
      this.field_78149_c.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
      this.field_78146_d.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
      this.field_78147_e.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
      this.field_78144_f.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
   }
}
