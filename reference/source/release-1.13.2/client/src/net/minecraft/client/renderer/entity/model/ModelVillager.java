package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVillager extends ModelBase {
   protected ModelRenderer field_78191_a;
   protected ModelRenderer field_78189_b;
   protected ModelRenderer field_78190_c;
   protected ModelRenderer field_78187_d;
   protected ModelRenderer field_78188_e;
   protected ModelRenderer field_82898_f;

   public ModelVillager(float var1) {
      this(☃, 0.0F, 64, 64);
   }

   public ModelVillager(float var1, float var2, int var3, int var4) {
      this.field_78191_a = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_78191_a.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_78191_a.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, ☃);
      this.field_82898_f = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_82898_f.func_78793_a(0.0F, ☃ - 2.0F, 0.0F);
      this.field_82898_f.func_78784_a(24, 0).func_78790_a(-1.0F, -1.0F, -6.0F, 2, 4, 2, ☃);
      this.field_78191_a.func_78792_a(this.field_82898_f);
      this.field_78189_b = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_78189_b.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_78189_b.func_78784_a(16, 20).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, ☃);
      this.field_78189_b.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, ☃ + 0.5F);
      this.field_78190_c = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_78190_c.func_78793_a(0.0F, 0.0F + ☃ + 2.0F, 0.0F);
      this.field_78190_c.func_78784_a(44, 22).func_78790_a(-8.0F, -2.0F, -2.0F, 4, 8, 4, ☃);
      this.field_78190_c.func_78784_a(44, 22).func_205345_a(4.0F, -2.0F, -2.0F, 4, 8, 4, ☃, true);
      this.field_78190_c.func_78784_a(40, 38).func_78790_a(-4.0F, 2.0F, -2.0F, 8, 4, 4, ☃);
      this.field_78187_d = new ModelRenderer(this, 0, 22).func_78787_b(☃, ☃);
      this.field_78187_d.func_78793_a(-2.0F, 12.0F + ☃, 0.0F);
      this.field_78187_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
      this.field_78188_e = new ModelRenderer(this, 0, 22).func_78787_b(☃, ☃);
      this.field_78188_e.field_78809_i = true;
      this.field_78188_e.func_78793_a(2.0F, 12.0F + ☃, 0.0F);
      this.field_78188_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78191_a.func_78785_a(☃);
      this.field_78189_b.func_78785_a(☃);
      this.field_78187_d.func_78785_a(☃);
      this.field_78188_e.func_78785_a(☃);
      this.field_78190_c.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_78191_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78191_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78190_c.field_78797_d = 3.0F;
      this.field_78190_c.field_78798_e = -1.0F;
      this.field_78190_c.field_78795_f = -0.75F;
      this.field_78187_d.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃ * 0.5F;
      this.field_78188_e.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃ * 0.5F;
      this.field_78187_d.field_78796_g = 0.0F;
      this.field_78188_e.field_78796_g = 0.0F;
   }

   public ModelRenderer func_205072_a() {
      return this.field_78191_a;
   }
}
