package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelIronGolem extends ModelBase {
   private final ModelRenderer field_78178_a;
   private final ModelRenderer field_78176_b;
   public ModelRenderer field_78177_c;
   private final ModelRenderer field_78174_d;
   private final ModelRenderer field_78175_e;
   private final ModelRenderer field_78173_f;

   public ModelIronGolem() {
      this(0.0F);
   }

   public ModelIronGolem(float var1) {
      this(☃, -7.0F);
   }

   public ModelIronGolem(float var1, float var2) {
      int ☃ = 128;
      int ☃x = 128;
      this.field_78178_a = new ModelRenderer(this).func_78787_b(128, 128);
      this.field_78178_a.func_78793_a(0.0F, 0.0F + ☃, -2.0F);
      this.field_78178_a.func_78784_a(0, 0).func_78790_a(-4.0F, -12.0F, -5.5F, 8, 10, 8, ☃);
      this.field_78178_a.func_78784_a(24, 0).func_78790_a(-1.0F, -5.0F, -7.5F, 2, 4, 2, ☃);
      this.field_78176_b = new ModelRenderer(this).func_78787_b(128, 128);
      this.field_78176_b.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_78176_b.func_78784_a(0, 40).func_78790_a(-9.0F, -2.0F, -6.0F, 18, 12, 11, ☃);
      this.field_78176_b.func_78784_a(0, 70).func_78790_a(-4.5F, 10.0F, -3.0F, 9, 5, 6, ☃ + 0.5F);
      this.field_78177_c = new ModelRenderer(this).func_78787_b(128, 128);
      this.field_78177_c.func_78793_a(0.0F, -7.0F, 0.0F);
      this.field_78177_c.func_78784_a(60, 21).func_78790_a(-13.0F, -2.5F, -3.0F, 4, 30, 6, ☃);
      this.field_78174_d = new ModelRenderer(this).func_78787_b(128, 128);
      this.field_78174_d.func_78793_a(0.0F, -7.0F, 0.0F);
      this.field_78174_d.func_78784_a(60, 58).func_78790_a(9.0F, -2.5F, -3.0F, 4, 30, 6, ☃);
      this.field_78175_e = new ModelRenderer(this, 0, 22).func_78787_b(128, 128);
      this.field_78175_e.func_78793_a(-4.0F, 18.0F + ☃, 0.0F);
      this.field_78175_e.func_78784_a(37, 0).func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, ☃);
      this.field_78173_f = new ModelRenderer(this, 0, 22).func_78787_b(128, 128);
      this.field_78173_f.field_78809_i = true;
      this.field_78173_f.func_78784_a(60, 0).func_78793_a(5.0F, 18.0F + ☃, 0.0F);
      this.field_78173_f.func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, ☃);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78178_a.func_78785_a(☃);
      this.field_78176_b.func_78785_a(☃);
      this.field_78175_e.func_78785_a(☃);
      this.field_78173_f.func_78785_a(☃);
      this.field_78177_c.func_78785_a(☃);
      this.field_78174_d.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_78178_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78178_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78175_e.field_78795_f = -1.5F * this.func_78172_a(☃, 13.0F) * ☃;
      this.field_78173_f.field_78795_f = 1.5F * this.func_78172_a(☃, 13.0F) * ☃;
      this.field_78175_e.field_78796_g = 0.0F;
      this.field_78173_f.field_78796_g = 0.0F;
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityIronGolem ☃ = (EntityIronGolem)☃;
      int ☃x = ☃.func_70854_o();
      if (☃x > 0) {
         this.field_78177_c.field_78795_f = -2.0F + 1.5F * this.func_78172_a((float)☃x - ☃, 10.0F);
         this.field_78174_d.field_78795_f = -2.0F + 1.5F * this.func_78172_a((float)☃x - ☃, 10.0F);
      } else {
         int ☃ = ☃.func_70853_p();
         if (☃ > 0) {
            this.field_78177_c.field_78795_f = -0.8F + 0.025F * this.func_78172_a((float)☃, 70.0F);
            this.field_78174_d.field_78795_f = 0.0F;
         } else {
            this.field_78177_c.field_78795_f = (-0.2F + 1.5F * this.func_78172_a(☃, 13.0F)) * ☃;
            this.field_78174_d.field_78795_f = (-0.2F - 1.5F * this.func_78172_a(☃, 13.0F)) * ☃;
         }
      }
   }

   private float func_78172_a(float var1, float var2) {
      return (Math.abs(☃ % ☃ - ☃ * 0.5F) - ☃ * 0.25F) / (☃ * 0.25F);
   }

   public ModelRenderer func_205071_a() {
      return this.field_78177_c;
   }
}
