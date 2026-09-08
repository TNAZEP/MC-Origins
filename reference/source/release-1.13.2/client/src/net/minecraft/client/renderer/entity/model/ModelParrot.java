package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.util.math.MathHelper;

public class ModelParrot extends ModelBase {
   private final ModelRenderer field_192764_a;
   private final ModelRenderer field_192765_b;
   private final ModelRenderer field_192766_c;
   private final ModelRenderer field_192767_d;
   private final ModelRenderer field_192768_e;
   private final ModelRenderer field_192769_f;
   private final ModelRenderer field_192770_g;
   private final ModelRenderer field_192771_h;
   private final ModelRenderer field_192772_i;
   private final ModelRenderer field_192773_j;
   private final ModelRenderer field_192774_k;
   private ModelParrot.State field_192775_l = ModelParrot.State.STANDING;

   public ModelParrot() {
      this.field_78090_t = 32;
      this.field_78089_u = 32;
      this.field_192764_a = new ModelRenderer(this, 2, 8);
      this.field_192764_a.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 6, 3);
      this.field_192764_a.func_78793_a(0.0F, 16.5F, -3.0F);
      this.field_192765_b = new ModelRenderer(this, 22, 1);
      this.field_192765_b.func_78789_a(-1.5F, -1.0F, -1.0F, 3, 4, 1);
      this.field_192765_b.func_78793_a(0.0F, 21.07F, 1.16F);
      this.field_192766_c = new ModelRenderer(this, 19, 8);
      this.field_192766_c.func_78789_a(-0.5F, 0.0F, -1.5F, 1, 5, 3);
      this.field_192766_c.func_78793_a(1.5F, 16.94F, -2.76F);
      this.field_192767_d = new ModelRenderer(this, 19, 8);
      this.field_192767_d.func_78789_a(-0.5F, 0.0F, -1.5F, 1, 5, 3);
      this.field_192767_d.func_78793_a(-1.5F, 16.94F, -2.76F);
      this.field_192768_e = new ModelRenderer(this, 2, 2);
      this.field_192768_e.func_78789_a(-1.0F, -1.5F, -1.0F, 2, 3, 2);
      this.field_192768_e.func_78793_a(0.0F, 15.69F, -2.76F);
      this.field_192769_f = new ModelRenderer(this, 10, 0);
      this.field_192769_f.func_78789_a(-1.0F, -0.5F, -2.0F, 2, 1, 4);
      this.field_192769_f.func_78793_a(0.0F, -2.0F, -1.0F);
      this.field_192768_e.func_78792_a(this.field_192769_f);
      this.field_192770_g = new ModelRenderer(this, 11, 7);
      this.field_192770_g.func_78789_a(-0.5F, -1.0F, -0.5F, 1, 2, 1);
      this.field_192770_g.func_78793_a(0.0F, -0.5F, -1.5F);
      this.field_192768_e.func_78792_a(this.field_192770_g);
      this.field_192771_h = new ModelRenderer(this, 16, 7);
      this.field_192771_h.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192771_h.func_78793_a(0.0F, -1.75F, -2.45F);
      this.field_192768_e.func_78792_a(this.field_192771_h);
      this.field_192772_i = new ModelRenderer(this, 2, 18);
      this.field_192772_i.func_78789_a(0.0F, -4.0F, -2.0F, 0, 5, 4);
      this.field_192772_i.func_78793_a(0.0F, -2.15F, 0.15F);
      this.field_192768_e.func_78792_a(this.field_192772_i);
      this.field_192773_j = new ModelRenderer(this, 14, 18);
      this.field_192773_j.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192773_j.func_78793_a(1.0F, 22.0F, -1.05F);
      this.field_192774_k = new ModelRenderer(this, 14, 18);
      this.field_192774_k.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192774_k.func_78793_a(-1.0F, 22.0F, -1.05F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.field_192764_a.func_78785_a(☃);
      this.field_192766_c.func_78785_a(☃);
      this.field_192767_d.func_78785_a(☃);
      this.field_192765_b.func_78785_a(☃);
      this.field_192768_e.func_78785_a(☃);
      this.field_192773_j.func_78785_a(☃);
      this.field_192774_k.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float ☃ = ☃ * 0.3F;
      this.field_192768_e.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_192768_e.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_192768_e.field_78808_h = 0.0F;
      this.field_192768_e.field_78800_c = 0.0F;
      this.field_192764_a.field_78800_c = 0.0F;
      this.field_192765_b.field_78800_c = 0.0F;
      this.field_192767_d.field_78800_c = -1.5F;
      this.field_192766_c.field_78800_c = 1.5F;
      if (this.field_192775_l != ModelParrot.State.SITTING) {
         if (this.field_192775_l == ModelParrot.State.PARTY) {
            float ☃x = MathHelper.func_76134_b((float)☃.field_70173_aa);
            float ☃xx = MathHelper.func_76126_a((float)☃.field_70173_aa);
            this.field_192768_e.field_78800_c = ☃x;
            this.field_192768_e.field_78797_d = 15.69F + ☃xx;
            this.field_192768_e.field_78795_f = 0.0F;
            this.field_192768_e.field_78796_g = 0.0F;
            this.field_192768_e.field_78808_h = MathHelper.func_76126_a((float)☃.field_70173_aa) * 0.4F;
            this.field_192764_a.field_78800_c = ☃x;
            this.field_192764_a.field_78797_d = 16.5F + ☃xx;
            this.field_192766_c.field_78808_h = -0.0873F - ☃;
            this.field_192766_c.field_78800_c = 1.5F + ☃x;
            this.field_192766_c.field_78797_d = 16.94F + ☃xx;
            this.field_192767_d.field_78808_h = 0.0873F + ☃;
            this.field_192767_d.field_78800_c = -1.5F + ☃x;
            this.field_192767_d.field_78797_d = 16.94F + ☃xx;
            this.field_192765_b.field_78800_c = ☃x;
            this.field_192765_b.field_78797_d = 21.07F + ☃xx;
         } else {
            if (this.field_192775_l == ModelParrot.State.STANDING) {
               this.field_192773_j.field_78795_f += MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
               this.field_192774_k.field_78795_f += MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
            }

            this.field_192768_e.field_78797_d = 15.69F + ☃;
            this.field_192765_b.field_78795_f = 1.015F + MathHelper.func_76134_b(☃ * 0.6662F) * 0.3F * ☃;
            this.field_192765_b.field_78797_d = 21.07F + ☃;
            this.field_192764_a.field_78797_d = 16.5F + ☃;
            this.field_192766_c.field_78808_h = -0.0873F - ☃;
            this.field_192766_c.field_78797_d = 16.94F + ☃;
            this.field_192767_d.field_78808_h = 0.0873F + ☃;
            this.field_192767_d.field_78797_d = 16.94F + ☃;
            this.field_192773_j.field_78797_d = 22.0F + ☃;
            this.field_192774_k.field_78797_d = 22.0F + ☃;
         }
      }
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      this.field_192772_i.field_78795_f = -0.2214F;
      this.field_192764_a.field_78795_f = 0.4937F;
      this.field_192766_c.field_78795_f = -0.6981F;
      this.field_192766_c.field_78796_g = (float) -Math.PI;
      this.field_192767_d.field_78795_f = -0.6981F;
      this.field_192767_d.field_78796_g = (float) -Math.PI;
      this.field_192773_j.field_78795_f = -0.0299F;
      this.field_192774_k.field_78795_f = -0.0299F;
      this.field_192773_j.field_78797_d = 22.0F;
      this.field_192774_k.field_78797_d = 22.0F;
      if (☃ instanceof EntityParrot) {
         EntityParrot ☃ = (EntityParrot)☃;
         if (☃.func_192004_dr()) {
            this.field_192773_j.field_78808_h = (float) (-Math.PI / 9);
            this.field_192774_k.field_78808_h = (float) (Math.PI / 9);
            this.field_192775_l = ModelParrot.State.PARTY;
            return;
         }

         if (☃.func_70906_o()) {
            float ☃ = 1.9F;
            this.field_192768_e.field_78797_d = 17.59F;
            this.field_192765_b.field_78795_f = 1.5388988F;
            this.field_192765_b.field_78797_d = 22.97F;
            this.field_192764_a.field_78797_d = 18.4F;
            this.field_192766_c.field_78808_h = -0.0873F;
            this.field_192766_c.field_78797_d = 18.84F;
            this.field_192767_d.field_78808_h = 0.0873F;
            this.field_192767_d.field_78797_d = 18.84F;
            ++this.field_192773_j.field_78797_d;
            ++this.field_192774_k.field_78797_d;
            ++this.field_192773_j.field_78795_f;
            ++this.field_192774_k.field_78795_f;
            this.field_192775_l = ModelParrot.State.SITTING;
         } else if (☃.func_192002_a()) {
            this.field_192773_j.field_78795_f += (float) (Math.PI * 2.0 / 9.0);
            this.field_192774_k.field_78795_f += (float) (Math.PI * 2.0 / 9.0);
            this.field_192775_l = ModelParrot.State.FLYING;
         } else {
            this.field_192775_l = ModelParrot.State.STANDING;
         }

         this.field_192773_j.field_78808_h = 0.0F;
         this.field_192774_k.field_78808_h = 0.0F;
      } else {
         this.field_192775_l = ModelParrot.State.ON_SHOULDER;
      }
   }

   static enum State {
      FLYING,
      STANDING,
      SITTING,
      PARTY,
      ON_SHOULDER;
   }
}
