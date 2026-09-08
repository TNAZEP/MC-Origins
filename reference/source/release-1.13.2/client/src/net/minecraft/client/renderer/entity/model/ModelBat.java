package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.math.MathHelper;

public class ModelBat extends ModelBase {
   private final ModelRenderer field_82895_a;
   private final ModelRenderer field_82893_b;
   private final ModelRenderer field_82894_c;
   private final ModelRenderer field_82891_d;
   private final ModelRenderer field_82892_e;
   private final ModelRenderer field_82890_f;

   public ModelBat() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_82895_a = new ModelRenderer(this, 0, 0);
      this.field_82895_a.func_78789_a(-3.0F, -3.0F, -3.0F, 6, 6, 6);
      ModelRenderer ☃ = new ModelRenderer(this, 24, 0);
      ☃.func_78789_a(-4.0F, -6.0F, -2.0F, 3, 4, 1);
      this.field_82895_a.func_78792_a(☃);
      ModelRenderer ☃x = new ModelRenderer(this, 24, 0);
      ☃x.field_78809_i = true;
      ☃x.func_78789_a(1.0F, -6.0F, -2.0F, 3, 4, 1);
      this.field_82895_a.func_78792_a(☃x);
      this.field_82893_b = new ModelRenderer(this, 0, 16);
      this.field_82893_b.func_78789_a(-3.0F, 4.0F, -3.0F, 6, 12, 6);
      this.field_82893_b.func_78784_a(0, 34).func_78789_a(-5.0F, 16.0F, 0.0F, 10, 6, 1);
      this.field_82894_c = new ModelRenderer(this, 42, 0);
      this.field_82894_c.func_78789_a(-12.0F, 1.0F, 1.5F, 10, 16, 1);
      this.field_82892_e = new ModelRenderer(this, 24, 16);
      this.field_82892_e.func_78793_a(-12.0F, 1.0F, 1.5F);
      this.field_82892_e.func_78789_a(-8.0F, 1.0F, 0.0F, 8, 12, 1);
      this.field_82891_d = new ModelRenderer(this, 42, 0);
      this.field_82891_d.field_78809_i = true;
      this.field_82891_d.func_78789_a(2.0F, 1.0F, 1.5F, 10, 16, 1);
      this.field_82890_f = new ModelRenderer(this, 24, 16);
      this.field_82890_f.field_78809_i = true;
      this.field_82890_f.func_78793_a(12.0F, 1.0F, 1.5F);
      this.field_82890_f.func_78789_a(0.0F, 1.0F, 0.0F, 8, 12, 1);
      this.field_82893_b.func_78792_a(this.field_82894_c);
      this.field_82893_b.func_78792_a(this.field_82891_d);
      this.field_82894_c.func_78792_a(this.field_82892_e);
      this.field_82891_d.func_78792_a(this.field_82890_f);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_82895_a.func_78785_a(☃);
      this.field_82893_b.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      if (((EntityBat)☃).func_82235_h()) {
         this.field_82895_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
         this.field_82895_a.field_78796_g = (float) Math.PI - ☃ * (float) (Math.PI / 180.0);
         this.field_82895_a.field_78808_h = (float) Math.PI;
         this.field_82895_a.func_78793_a(0.0F, -2.0F, 0.0F);
         this.field_82894_c.func_78793_a(-3.0F, 0.0F, 3.0F);
         this.field_82891_d.func_78793_a(3.0F, 0.0F, 3.0F);
         this.field_82893_b.field_78795_f = (float) Math.PI;
         this.field_82894_c.field_78795_f = (float) (-Math.PI / 20);
         this.field_82894_c.field_78796_g = (float) (-Math.PI * 2.0 / 5.0);
         this.field_82892_e.field_78796_g = -1.7278761F;
         this.field_82891_d.field_78795_f = this.field_82894_c.field_78795_f;
         this.field_82891_d.field_78796_g = -this.field_82894_c.field_78796_g;
         this.field_82890_f.field_78796_g = -this.field_82892_e.field_78796_g;
      } else {
         this.field_82895_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
         this.field_82895_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
         this.field_82895_a.field_78808_h = 0.0F;
         this.field_82895_a.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_82894_c.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_82891_d.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_82893_b.field_78795_f = (float) (Math.PI / 4) + MathHelper.func_76134_b(☃ * 0.1F) * 0.15F;
         this.field_82893_b.field_78796_g = 0.0F;
         this.field_82894_c.field_78796_g = MathHelper.func_76134_b(☃ * 1.3F) * (float) Math.PI * 0.25F;
         this.field_82891_d.field_78796_g = -this.field_82894_c.field_78796_g;
         this.field_82892_e.field_78796_g = this.field_82894_c.field_78796_g * 0.5F;
         this.field_82890_f.field_78796_g = -this.field_82894_c.field_78796_g * 0.5F;
      }
   }
}
