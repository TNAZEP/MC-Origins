package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelIllager extends ModelBase {
   private final ModelRenderer field_191217_a;
   private final ModelRenderer field_193775_b;
   private final ModelRenderer field_191218_b;
   private final ModelRenderer field_191219_c;
   private final ModelRenderer field_191220_d;
   private final ModelRenderer field_191221_e;
   private final ModelRenderer field_191222_f;
   private final ModelRenderer field_191223_g;
   private final ModelRenderer field_191224_h;

   public ModelIllager(float var1, float var2, int var3, int var4) {
      this.field_191217_a = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_191217_a.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_191217_a.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, ☃);
      this.field_193775_b = new ModelRenderer(this, 32, 0).func_78787_b(☃, ☃);
      this.field_193775_b.func_78790_a(-4.0F, -10.0F, -4.0F, 8, 12, 8, ☃ + 0.45F);
      this.field_191217_a.func_78792_a(this.field_193775_b);
      this.field_193775_b.field_78806_j = false;
      this.field_191222_f = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_191222_f.func_78793_a(0.0F, ☃ - 2.0F, 0.0F);
      this.field_191222_f.func_78784_a(24, 0).func_78790_a(-1.0F, -1.0F, -6.0F, 2, 4, 2, ☃);
      this.field_191217_a.func_78792_a(this.field_191222_f);
      this.field_191218_b = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_191218_b.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_191218_b.func_78784_a(16, 20).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, ☃);
      this.field_191218_b.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, ☃ + 0.5F);
      this.field_191219_c = new ModelRenderer(this).func_78787_b(☃, ☃);
      this.field_191219_c.func_78793_a(0.0F, 0.0F + ☃ + 2.0F, 0.0F);
      this.field_191219_c.func_78784_a(44, 22).func_78790_a(-8.0F, -2.0F, -2.0F, 4, 8, 4, ☃);
      ModelRenderer ☃ = new ModelRenderer(this, 44, 22).func_78787_b(☃, ☃);
      ☃.field_78809_i = true;
      ☃.func_78790_a(4.0F, -2.0F, -2.0F, 4, 8, 4, ☃);
      this.field_191219_c.func_78792_a(☃);
      this.field_191219_c.func_78784_a(40, 38).func_78790_a(-4.0F, 2.0F, -2.0F, 8, 4, 4, ☃);
      this.field_191220_d = new ModelRenderer(this, 0, 22).func_78787_b(☃, ☃);
      this.field_191220_d.func_78793_a(-2.0F, 12.0F + ☃, 0.0F);
      this.field_191220_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
      this.field_191221_e = new ModelRenderer(this, 0, 22).func_78787_b(☃, ☃);
      this.field_191221_e.field_78809_i = true;
      this.field_191221_e.func_78793_a(2.0F, 12.0F + ☃, 0.0F);
      this.field_191221_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
      this.field_191223_g = new ModelRenderer(this, 40, 46).func_78787_b(☃, ☃);
      this.field_191223_g.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, ☃);
      this.field_191223_g.func_78793_a(-5.0F, 2.0F + ☃, 0.0F);
      this.field_191224_h = new ModelRenderer(this, 40, 46).func_78787_b(☃, ☃);
      this.field_191224_h.field_78809_i = true;
      this.field_191224_h.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, ☃);
      this.field_191224_h.func_78793_a(5.0F, 2.0F + ☃, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_191217_a.func_78785_a(☃);
      this.field_191218_b.func_78785_a(☃);
      this.field_191220_d.func_78785_a(☃);
      this.field_191221_e.func_78785_a(☃);
      AbstractIllager ☃ = (AbstractIllager)☃;
      if (☃.func_193077_p() == AbstractIllager.IllagerArmPose.CROSSED) {
         this.field_191219_c.func_78785_a(☃);
      } else {
         this.field_191223_g.func_78785_a(☃);
         this.field_191224_h.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_191217_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_191217_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_191219_c.field_78797_d = 3.0F;
      this.field_191219_c.field_78798_e = -1.0F;
      this.field_191219_c.field_78795_f = -0.75F;
      this.field_191220_d.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃ * 0.5F;
      this.field_191221_e.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃ * 0.5F;
      this.field_191220_d.field_78796_g = 0.0F;
      this.field_191221_e.field_78796_g = 0.0F;
      AbstractIllager.IllagerArmPose ☃ = ((AbstractIllager)☃).func_193077_p();
      if (☃ == AbstractIllager.IllagerArmPose.ATTACKING) {
         float ☃x = MathHelper.func_76126_a(this.field_78095_p * (float) Math.PI);
         float ☃xx = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * (float) Math.PI);
         this.field_191223_g.field_78808_h = 0.0F;
         this.field_191224_h.field_78808_h = 0.0F;
         this.field_191223_g.field_78796_g = (float) (Math.PI / 20);
         this.field_191224_h.field_78796_g = (float) (-Math.PI / 20);
         if (((EntityLivingBase)☃).func_184591_cq() == EnumHandSide.RIGHT) {
            this.field_191223_g.field_78795_f = -1.8849558F + MathHelper.func_76134_b(☃ * 0.09F) * 0.15F;
            this.field_191224_h.field_78795_f = -0.0F + MathHelper.func_76134_b(☃ * 0.19F) * 0.5F;
            this.field_191223_g.field_78795_f += ☃x * 2.2F - ☃xx * 0.4F;
            this.field_191224_h.field_78795_f += ☃x * 1.2F - ☃xx * 0.4F;
         } else {
            this.field_191223_g.field_78795_f = -0.0F + MathHelper.func_76134_b(☃ * 0.19F) * 0.5F;
            this.field_191224_h.field_78795_f = -1.8849558F + MathHelper.func_76134_b(☃ * 0.09F) * 0.15F;
            this.field_191223_g.field_78795_f += ☃x * 1.2F - ☃xx * 0.4F;
            this.field_191224_h.field_78795_f += ☃x * 2.2F - ☃xx * 0.4F;
         }

         this.field_191223_g.field_78808_h += MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
         this.field_191224_h.field_78808_h -= MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
         this.field_191223_g.field_78795_f += MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
         this.field_191224_h.field_78795_f -= MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
      } else if (☃ == AbstractIllager.IllagerArmPose.SPELLCASTING) {
         this.field_191223_g.field_78798_e = 0.0F;
         this.field_191223_g.field_78800_c = -5.0F;
         this.field_191224_h.field_78798_e = 0.0F;
         this.field_191224_h.field_78800_c = 5.0F;
         this.field_191223_g.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 0.25F;
         this.field_191224_h.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 0.25F;
         this.field_191223_g.field_78808_h = (float) (Math.PI * 3.0 / 4.0);
         this.field_191224_h.field_78808_h = (float) (-Math.PI * 3.0 / 4.0);
         this.field_191223_g.field_78796_g = 0.0F;
         this.field_191224_h.field_78796_g = 0.0F;
      } else if (☃ == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
         this.field_191223_g.field_78796_g = -0.1F + this.field_191217_a.field_78796_g;
         this.field_191223_g.field_78795_f = (float) (-Math.PI / 2) + this.field_191217_a.field_78795_f;
         this.field_191224_h.field_78795_f = -0.9424779F + this.field_191217_a.field_78795_f;
         this.field_191224_h.field_78796_g = this.field_191217_a.field_78796_g - 0.4F;
         this.field_191224_h.field_78808_h = (float) (Math.PI / 2);
      }
   }

   public ModelRenderer func_191216_a(EnumHandSide var1) {
      return ☃ == EnumHandSide.LEFT ? this.field_191224_h : this.field_191223_g;
   }

   public ModelRenderer func_205062_a() {
      return this.field_193775_b;
   }
}
