package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelBiped extends ModelBase {
   public ModelRenderer field_78116_c;
   public ModelRenderer field_178720_f;
   public ModelRenderer field_78115_e;
   public ModelRenderer field_178723_h;
   public ModelRenderer field_178724_i;
   public ModelRenderer field_178721_j;
   public ModelRenderer field_178722_k;
   public ModelBiped.ArmPose field_187075_l = ModelBiped.ArmPose.EMPTY;
   public ModelBiped.ArmPose field_187076_m = ModelBiped.ArmPose.EMPTY;
   public boolean field_78117_n;
   public float field_205061_a;

   public ModelBiped() {
      this(0.0F);
   }

   public ModelBiped(float var1) {
      this(☃, 0.0F, 64, 32);
   }

   public ModelBiped(float var1, float var2, int var3, int var4) {
      this.field_78090_t = ☃;
      this.field_78089_u = ☃;
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, ☃);
      this.field_78116_c.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_178720_f = new ModelRenderer(this, 32, 0);
      this.field_178720_f.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, ☃ + 0.5F);
      this.field_178720_f.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_78115_e = new ModelRenderer(this, 16, 16);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, ☃);
      this.field_78115_e.func_78793_a(0.0F, 0.0F + ☃, 0.0F);
      this.field_178723_h = new ModelRenderer(this, 40, 16);
      this.field_178723_h.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, ☃);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F + ☃, 0.0F);
      this.field_178724_i = new ModelRenderer(this, 40, 16);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, ☃);
      this.field_178724_i.func_78793_a(5.0F, 2.0F + ☃, 0.0F);
      this.field_178721_j = new ModelRenderer(this, 0, 16);
      this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
      this.field_178721_j.func_78793_a(-1.9F, 12.0F + ☃, 0.0F);
      this.field_178722_k = new ModelRenderer(this, 0, 16);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
      this.field_178722_k.func_78793_a(1.9F, 12.0F + ☃, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      GlStateManager.func_179094_E();
      if (this.field_78091_s) {
         float ☃ = 2.0F;
         GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
         GlStateManager.func_179109_b(0.0F, 16.0F * ☃, 0.0F);
         this.field_78116_c.func_78785_a(☃);
         GlStateManager.func_179121_F();
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * ☃, 0.0F);
         this.field_78115_e.func_78785_a(☃);
         this.field_178723_h.func_78785_a(☃);
         this.field_178724_i.func_78785_a(☃);
         this.field_178721_j.func_78785_a(☃);
         this.field_178722_k.func_78785_a(☃);
         this.field_178720_f.func_78785_a(☃);
      } else {
         if (☃.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         this.field_78116_c.func_78785_a(☃);
         this.field_78115_e.func_78785_a(☃);
         this.field_178723_h.func_78785_a(☃);
         this.field_178724_i.func_78785_a(☃);
         this.field_178721_j.func_78785_a(☃);
         this.field_178722_k.func_78785_a(☃);
         this.field_178720_f.func_78785_a(☃);
      }

      GlStateManager.func_179121_F();
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      this.field_205061_a = ☃.func_205015_b(☃);
      super.func_78086_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      boolean ☃ = ☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_184599_cB() > 4;
      boolean ☃x = ☃.func_203007_ba();
      this.field_78116_c.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      if (☃) {
         this.field_78116_c.field_78795_f = (float) (-Math.PI / 4);
      } else if (this.field_205061_a > 0.0F) {
         if (☃x) {
            this.field_78116_c.field_78795_f = this.func_205060_a(this.field_78116_c.field_78795_f, (float) (-Math.PI / 4), this.field_205061_a);
         } else {
            this.field_78116_c.field_78795_f = this.func_205060_a(this.field_78116_c.field_78795_f, ☃ * (float) (Math.PI / 180.0), this.field_205061_a);
         }
      } else {
         this.field_78116_c.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      }

      this.field_78115_e.field_78796_g = 0.0F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      float ☃ = 1.0F;
      if (☃) {
         ☃ = (float)(☃.field_70159_w * ☃.field_70159_w + ☃.field_70181_x * ☃.field_70181_x + ☃.field_70179_y * ☃.field_70179_y);
         ☃ /= 0.2F;
         ☃ *= ☃ * ☃;
      }

      if (☃ < 1.0F) {
         ☃ = 1.0F;
      }

      this.field_178723_h.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 2.0F * ☃ * 0.5F / ☃;
      this.field_178724_i.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 2.0F * ☃ * 0.5F / ☃;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178721_j.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃ / ☃;
      this.field_178722_k.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃ / ☃;
      this.field_178721_j.field_78796_g = 0.0F;
      this.field_178722_k.field_78796_g = 0.0F;
      this.field_178721_j.field_78808_h = 0.0F;
      this.field_178722_k.field_78808_h = 0.0F;
      if (this.field_78093_q) {
         this.field_178723_h.field_78795_f += (float) (-Math.PI / 5);
         this.field_178724_i.field_78795_f += (float) (-Math.PI / 5);
         this.field_178721_j.field_78795_f = -1.4137167F;
         this.field_178721_j.field_78796_g = (float) (Math.PI / 10);
         this.field_178721_j.field_78808_h = 0.07853982F;
         this.field_178722_k.field_78795_f = -1.4137167F;
         this.field_178722_k.field_78796_g = (float) (-Math.PI / 10);
         this.field_178722_k.field_78808_h = -0.07853982F;
      }

      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178723_h.field_78808_h = 0.0F;
      switch(this.field_187075_l) {
         case EMPTY:
            this.field_178724_i.field_78796_g = 0.0F;
            break;
         case BLOCK:
            this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5F - 0.9424779F;
            this.field_178724_i.field_78796_g = (float) (Math.PI / 6);
            break;
         case ITEM:
            this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5F - (float) (Math.PI / 10);
            this.field_178724_i.field_78796_g = 0.0F;
      }

      switch(this.field_187076_m) {
         case EMPTY:
            this.field_178723_h.field_78796_g = 0.0F;
            break;
         case BLOCK:
            this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - 0.9424779F;
            this.field_178723_h.field_78796_g = (float) (-Math.PI / 6);
            break;
         case ITEM:
            this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - (float) (Math.PI / 10);
            this.field_178723_h.field_78796_g = 0.0F;
            break;
         case THROW_SPEAR:
            this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5F - (float) Math.PI;
            this.field_178723_h.field_78796_g = 0.0F;
      }

      if (this.field_187075_l == ModelBiped.ArmPose.THROW_SPEAR
         && this.field_187076_m != ModelBiped.ArmPose.BLOCK
         && this.field_187076_m != ModelBiped.ArmPose.THROW_SPEAR
         && this.field_187076_m != ModelBiped.ArmPose.BOW_AND_ARROW) {
         this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5F - (float) Math.PI;
         this.field_178724_i.field_78796_g = 0.0F;
      }

      if (this.field_78095_p > 0.0F) {
         EnumHandSide ☃ = this.func_187072_a(☃);
         ModelRenderer ☃x = this.func_187074_a(☃);
         float ☃xx = this.field_78095_p;
         this.field_78115_e.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(☃xx) * (float) (Math.PI * 2)) * 0.2F;
         if (☃ == EnumHandSide.LEFT) {
            this.field_78115_e.field_78796_g *= -1.0F;
         }

         this.field_178723_h.field_78798_e = MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178723_h.field_78800_c = -MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178724_i.field_78798_e = -MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178724_i.field_78800_c = MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0F;
         this.field_178723_h.field_78796_g += this.field_78115_e.field_78796_g;
         this.field_178724_i.field_78796_g += this.field_78115_e.field_78796_g;
         this.field_178724_i.field_78795_f += this.field_78115_e.field_78796_g;
         ☃xx = 1.0F - this.field_78095_p;
         ☃xx *= ☃xx;
         ☃xx *= ☃xx;
         ☃xx = 1.0F - ☃xx;
         float ☃ = MathHelper.func_76126_a(☃xx * (float) Math.PI);
         float ☃x = MathHelper.func_76126_a(this.field_78095_p * (float) Math.PI) * -(this.field_78116_c.field_78795_f - 0.7F) * 0.75F;
         ☃x.field_78795_f = (float)((double)☃x.field_78795_f - ((double)☃ * 1.2 + (double)☃x));
         ☃x.field_78796_g += this.field_78115_e.field_78796_g * 2.0F;
         ☃x.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * (float) Math.PI) * -0.4F;
      }

      if (this.field_78117_n) {
         this.field_78115_e.field_78795_f = 0.5F;
         this.field_178723_h.field_78795_f += 0.4F;
         this.field_178724_i.field_78795_f += 0.4F;
         this.field_178721_j.field_78798_e = 4.0F;
         this.field_178722_k.field_78798_e = 4.0F;
         this.field_178721_j.field_78797_d = 9.0F;
         this.field_178722_k.field_78797_d = 9.0F;
         this.field_78116_c.field_78797_d = 1.0F;
      } else {
         this.field_78115_e.field_78795_f = 0.0F;
         this.field_178721_j.field_78798_e = 0.1F;
         this.field_178722_k.field_78798_e = 0.1F;
         this.field_178721_j.field_78797_d = 12.0F;
         this.field_178722_k.field_78797_d = 12.0F;
         this.field_78116_c.field_78797_d = 0.0F;
      }

      this.field_178723_h.field_78808_h += MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.field_78795_f += MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
      this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
      if (this.field_187076_m == ModelBiped.ArmPose.BOW_AND_ARROW) {
         this.field_178723_h.field_78796_g = -0.1F + this.field_78116_c.field_78796_g;
         this.field_178724_i.field_78796_g = 0.1F + this.field_78116_c.field_78796_g + 0.4F;
         this.field_178723_h.field_78795_f = (float) (-Math.PI / 2) + this.field_78116_c.field_78795_f;
         this.field_178724_i.field_78795_f = (float) (-Math.PI / 2) + this.field_78116_c.field_78795_f;
      } else if (this.field_187075_l == ModelBiped.ArmPose.BOW_AND_ARROW
         && this.field_187076_m != ModelBiped.ArmPose.THROW_SPEAR
         && this.field_187076_m != ModelBiped.ArmPose.BLOCK) {
         this.field_178723_h.field_78796_g = -0.1F + this.field_78116_c.field_78796_g - 0.4F;
         this.field_178724_i.field_78796_g = 0.1F + this.field_78116_c.field_78796_g;
         this.field_178723_h.field_78795_f = (float) (-Math.PI / 2) + this.field_78116_c.field_78795_f;
         this.field_178724_i.field_78795_f = (float) (-Math.PI / 2) + this.field_78116_c.field_78795_f;
      }

      if (this.field_205061_a > 0.0F) {
         float ☃ = ☃ % 26.0F;
         float ☃x = this.field_78095_p > 0.0F ? 0.0F : this.field_205061_a;
         if (☃ < 14.0F) {
            this.field_178724_i.field_78795_f = this.func_205060_a(this.field_178724_i.field_78795_f, 0.0F, this.field_205061_a);
            this.field_178723_h.field_78795_f = this.func_205059_b(this.field_178723_h.field_78795_f, 0.0F, ☃x);
            this.field_178724_i.field_78796_g = this.func_205060_a(this.field_178724_i.field_78796_g, (float) Math.PI, this.field_205061_a);
            this.field_178723_h.field_78796_g = this.func_205059_b(this.field_178723_h.field_78796_g, (float) Math.PI, ☃x);
            this.field_178724_i.field_78808_h = this.func_205060_a(
               this.field_178724_i.field_78808_h, (float) Math.PI + 1.8707964F * this.func_203068_a(☃) / this.func_203068_a(14.0F), this.field_205061_a
            );
            this.field_178723_h.field_78808_h = this.func_205059_b(
               this.field_178723_h.field_78808_h, (float) Math.PI - 1.8707964F * this.func_203068_a(☃) / this.func_203068_a(14.0F), ☃x
            );
         } else if (☃ >= 14.0F && ☃ < 22.0F) {
            float ☃ = (☃ - 14.0F) / 8.0F;
            this.field_178724_i.field_78795_f = this.func_205060_a(this.field_178724_i.field_78795_f, (float) (Math.PI / 2) * ☃, this.field_205061_a);
            this.field_178723_h.field_78795_f = this.func_205059_b(this.field_178723_h.field_78795_f, (float) (Math.PI / 2) * ☃, ☃x);
            this.field_178724_i.field_78796_g = this.func_205060_a(this.field_178724_i.field_78796_g, (float) Math.PI, this.field_205061_a);
            this.field_178723_h.field_78796_g = this.func_205059_b(this.field_178723_h.field_78796_g, (float) Math.PI, ☃x);
            this.field_178724_i.field_78808_h = this.func_205060_a(this.field_178724_i.field_78808_h, 5.012389F - 1.8707964F * ☃, this.field_205061_a);
            this.field_178723_h.field_78808_h = this.func_205059_b(this.field_178723_h.field_78808_h, 1.2707963F + 1.8707964F * ☃, ☃x);
         } else if (☃ >= 22.0F && ☃ < 26.0F) {
            float ☃ = (☃ - 22.0F) / 4.0F;
            this.field_178724_i.field_78795_f = this.func_205060_a(
               this.field_178724_i.field_78795_f, (float) (Math.PI / 2) - (float) (Math.PI / 2) * ☃, this.field_205061_a
            );
            this.field_178723_h.field_78795_f = this.func_205059_b(this.field_178723_h.field_78795_f, (float) (Math.PI / 2) - (float) (Math.PI / 2) * ☃, ☃x);
            this.field_178724_i.field_78796_g = this.func_205060_a(this.field_178724_i.field_78796_g, (float) Math.PI, this.field_205061_a);
            this.field_178723_h.field_78796_g = this.func_205059_b(this.field_178723_h.field_78796_g, (float) Math.PI, ☃x);
            this.field_178724_i.field_78808_h = this.func_205060_a(this.field_178724_i.field_78808_h, (float) Math.PI, this.field_205061_a);
            this.field_178723_h.field_78808_h = this.func_205059_b(this.field_178723_h.field_78808_h, (float) Math.PI, ☃x);
         }

         float ☃ = 0.3F;
         float ☃x = 0.33333334F;
         this.field_178722_k.field_78795_f = this.func_205059_b(
            this.field_178722_k.field_78795_f, 0.3F * MathHelper.func_76134_b(☃ * 0.33333334F + (float) Math.PI), this.field_205061_a
         );
         this.field_178721_j.field_78795_f = this.func_205059_b(
            this.field_178721_j.field_78795_f, 0.3F * MathHelper.func_76134_b(☃ * 0.33333334F), this.field_205061_a
         );
      }

      func_178685_a(this.field_78116_c, this.field_178720_f);
   }

   protected float func_205060_a(float var1, float var2, float var3) {
      float ☃ = ☃ - ☃;

      while(☃ < (float) -Math.PI) {
         ☃ += (float) (Math.PI * 2);
      }

      while(☃ >= (float) Math.PI) {
         ☃ -= (float) (Math.PI * 2);
      }

      return ☃ + ☃ * ☃;
   }

   private float func_205059_b(float var1, float var2, float var3) {
      return ☃ + (☃ - ☃) * ☃;
   }

   private float func_203068_a(float var1) {
      return -65.0F * ☃ + ☃ * ☃;
   }

   @Override
   public void func_178686_a(ModelBase var1) {
      super.func_178686_a(☃);
      if (☃ instanceof ModelBiped) {
         ModelBiped ☃ = (ModelBiped)☃;
         this.field_187075_l = ☃.field_187075_l;
         this.field_187076_m = ☃.field_187076_m;
         this.field_78117_n = ☃.field_78117_n;
      }
   }

   public void func_178719_a(boolean var1) {
      this.field_78116_c.field_78806_j = ☃;
      this.field_178720_f.field_78806_j = ☃;
      this.field_78115_e.field_78806_j = ☃;
      this.field_178723_h.field_78806_j = ☃;
      this.field_178724_i.field_78806_j = ☃;
      this.field_178721_j.field_78806_j = ☃;
      this.field_178722_k.field_78806_j = ☃;
   }

   public void func_187073_a(float var1, EnumHandSide var2) {
      this.func_187074_a(☃).func_78794_c(☃);
   }

   protected ModelRenderer func_187074_a(EnumHandSide var1) {
      return ☃ == EnumHandSide.LEFT ? this.field_178724_i : this.field_178723_h;
   }

   protected EnumHandSide func_187072_a(Entity var1) {
      if (☃ instanceof EntityLivingBase) {
         EntityLivingBase ☃ = (EntityLivingBase)☃;
         EnumHandSide ☃x = ☃.func_184591_cq();
         return ☃.field_184622_au == EnumHand.MAIN_HAND ? ☃x : ☃x.func_188468_a();
      } else {
         return EnumHandSide.RIGHT;
      }
   }

   public static enum ArmPose {
      EMPTY,
      ITEM,
      BLOCK,
      BOW_AND_ARROW,
      THROW_SPEAR;
   }
}
