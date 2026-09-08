package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSnowMan extends ModelBase {
   private final ModelRenderer field_78196_a;
   private final ModelRenderer field_78194_b;
   private final ModelRenderer field_78195_c;
   private final ModelRenderer field_78192_d;
   private final ModelRenderer field_78193_e;

   public ModelSnowMan() {
      float ☃ = 4.0F;
      float ☃x = 0.0F;
      this.field_78195_c = new ModelRenderer(this, 0, 0).func_78787_b(64, 64);
      this.field_78195_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
      this.field_78195_c.func_78793_a(0.0F, 4.0F, 0.0F);
      this.field_78192_d = new ModelRenderer(this, 32, 0).func_78787_b(64, 64);
      this.field_78192_d.func_78790_a(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.field_78192_d.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78193_e = new ModelRenderer(this, 32, 0).func_78787_b(64, 64);
      this.field_78193_e.func_78790_a(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.field_78193_e.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78196_a = new ModelRenderer(this, 0, 16).func_78787_b(64, 64);
      this.field_78196_a.func_78790_a(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
      this.field_78196_a.func_78793_a(0.0F, 13.0F, 0.0F);
      this.field_78194_b = new ModelRenderer(this, 0, 36).func_78787_b(64, 64);
      this.field_78194_b.func_78790_a(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
      this.field_78194_b.func_78793_a(0.0F, 24.0F, 0.0F);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78195_c.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78195_c.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78196_a.field_78796_g = ☃ * (float) (Math.PI / 180.0) * 0.25F;
      float ☃ = MathHelper.func_76126_a(this.field_78196_a.field_78796_g);
      float ☃x = MathHelper.func_76134_b(this.field_78196_a.field_78796_g);
      this.field_78192_d.field_78808_h = 1.0F;
      this.field_78193_e.field_78808_h = -1.0F;
      this.field_78192_d.field_78796_g = 0.0F + this.field_78196_a.field_78796_g;
      this.field_78193_e.field_78796_g = (float) Math.PI + this.field_78196_a.field_78796_g;
      this.field_78192_d.field_78800_c = ☃x * 5.0F;
      this.field_78192_d.field_78798_e = -☃ * 5.0F;
      this.field_78193_e.field_78800_c = -☃x * 5.0F;
      this.field_78193_e.field_78798_e = ☃ * 5.0F;
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78196_a.func_78785_a(☃);
      this.field_78194_b.func_78785_a(☃);
      this.field_78195_c.func_78785_a(☃);
      this.field_78192_d.func_78785_a(☃);
      this.field_78193_e.func_78785_a(☃);
   }

   public ModelRenderer func_205070_a() {
      return this.field_78195_c;
   }
}
