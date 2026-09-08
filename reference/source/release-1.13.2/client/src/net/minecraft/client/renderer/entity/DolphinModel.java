package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDolphin;
import net.minecraft.util.math.MathHelper;

public class DolphinModel extends ModelBase {
   private final ModelRenderer field_205081_a;
   private final ModelRenderer field_205082_b;
   private final ModelRenderer field_205083_c;
   private final ModelRenderer field_205084_d;

   public DolphinModel() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      float ☃ = 18.0F;
      float ☃x = -8.0F;
      this.field_205082_b = new ModelRenderer(this, 22, 0);
      this.field_205082_b.func_78789_a(-4.0F, -7.0F, 0.0F, 8, 7, 13);
      this.field_205082_b.func_78793_a(0.0F, 22.0F, -5.0F);
      ModelRenderer ☃xx = new ModelRenderer(this, 51, 0);
      ☃xx.func_78789_a(-0.5F, 0.0F, 8.0F, 1, 4, 5);
      ☃xx.field_78795_f = (float) (Math.PI / 3);
      this.field_205082_b.func_78792_a(☃xx);
      ModelRenderer ☃xxx = new ModelRenderer(this, 48, 20);
      ☃xxx.field_78809_i = true;
      ☃xxx.func_78789_a(-0.5F, -4.0F, 0.0F, 1, 4, 7);
      ☃xxx.func_78793_a(2.0F, -2.0F, 4.0F);
      ☃xxx.field_78795_f = (float) (Math.PI / 3);
      ☃xxx.field_78808_h = (float) (Math.PI * 2.0 / 3.0);
      this.field_205082_b.func_78792_a(☃xxx);
      ModelRenderer ☃xxxx = new ModelRenderer(this, 48, 20);
      ☃xxxx.func_78789_a(-0.5F, -4.0F, 0.0F, 1, 4, 7);
      ☃xxxx.func_78793_a(-2.0F, -2.0F, 4.0F);
      ☃xxxx.field_78795_f = (float) (Math.PI / 3);
      ☃xxxx.field_78808_h = (float) (-Math.PI * 2.0 / 3.0);
      this.field_205082_b.func_78792_a(☃xxxx);
      this.field_205083_c = new ModelRenderer(this, 0, 19);
      this.field_205083_c.func_78789_a(-2.0F, -2.5F, 0.0F, 4, 5, 11);
      this.field_205083_c.func_78793_a(0.0F, -2.5F, 11.0F);
      this.field_205083_c.field_78795_f = -0.10471976F;
      this.field_205082_b.func_78792_a(this.field_205083_c);
      this.field_205084_d = new ModelRenderer(this, 19, 20);
      this.field_205084_d.func_78789_a(-5.0F, -0.5F, 0.0F, 10, 1, 6);
      this.field_205084_d.func_78793_a(0.0F, 0.0F, 9.0F);
      this.field_205084_d.field_78795_f = 0.0F;
      this.field_205083_c.func_78792_a(this.field_205084_d);
      this.field_205081_a = new ModelRenderer(this, 0, 0);
      this.field_205081_a.func_78789_a(-4.0F, -3.0F, -3.0F, 8, 7, 6);
      this.field_205081_a.func_78793_a(0.0F, -4.0F, -3.0F);
      ModelRenderer ☃xxxxx = new ModelRenderer(this, 0, 13);
      ☃xxxxx.func_78789_a(-1.0F, 2.0F, -7.0F, 2, 2, 4);
      this.field_205081_a.func_78792_a(☃xxxxx);
      this.field_205082_b.func_78792_a(this.field_205081_a);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.field_205082_b.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_205082_b.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_205082_b.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      if (☃ instanceof EntityDolphin) {
         EntityDolphin ☃ = (EntityDolphin)☃;
         if (☃.field_70159_w != 0.0 || ☃.field_70179_y != 0.0) {
            this.field_205082_b.field_78795_f += -0.05F + -0.05F * MathHelper.func_76134_b(☃ * 0.3F);
            this.field_205083_c.field_78795_f = -0.1F * MathHelper.func_76134_b(☃ * 0.3F);
            this.field_205084_d.field_78795_f = -0.2F * MathHelper.func_76134_b(☃ * 0.3F);
         }
      }
   }
}
