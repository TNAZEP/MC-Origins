package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelSkeleton extends ModelBiped {
   public ModelSkeleton() {
      this(0.0F, false);
   }

   public ModelSkeleton(float var1, boolean var2) {
      super(☃, 0.0F, 64, 32);
      if (!☃) {
         this.field_178723_h = new ModelRenderer(this, 40, 16);
         this.field_178723_h.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, ☃);
         this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
         this.field_178724_i = new ModelRenderer(this, 40, 16);
         this.field_178724_i.field_78809_i = true;
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, ☃);
         this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_178721_j = new ModelRenderer(this, 0, 16);
         this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, ☃);
         this.field_178721_j.func_78793_a(-2.0F, 12.0F, 0.0F);
         this.field_178722_k = new ModelRenderer(this, 0, 16);
         this.field_178722_k.field_78809_i = true;
         this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, ☃);
         this.field_178722_k.func_78793_a(2.0F, 12.0F, 0.0F);
      }
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      this.field_187076_m = ModelBiped.ArmPose.EMPTY;
      this.field_187075_l = ModelBiped.ArmPose.EMPTY;
      ItemStack ☃ = ☃.func_184586_b(EnumHand.MAIN_HAND);
      if (☃.func_77973_b() == Items.field_151031_f && ((AbstractSkeleton)☃).func_184725_db()) {
         if (☃.func_184591_cq() == EnumHandSide.RIGHT) {
            this.field_187076_m = ModelBiped.ArmPose.BOW_AND_ARROW;
         } else {
            this.field_187075_l = ModelBiped.ArmPose.BOW_AND_ARROW;
         }
      }

      super.func_78086_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      ItemStack ☃ = ((EntityLivingBase)☃).func_184614_ca();
      AbstractSkeleton ☃x = (AbstractSkeleton)☃;
      if (☃x.func_184725_db() && (☃.func_190926_b() || ☃.func_77973_b() != Items.field_151031_f)) {
         float ☃xx = MathHelper.func_76126_a(this.field_78095_p * (float) Math.PI);
         float ☃xxx = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * (float) Math.PI);
         this.field_178723_h.field_78808_h = 0.0F;
         this.field_178724_i.field_78808_h = 0.0F;
         this.field_178723_h.field_78796_g = -(0.1F - ☃xx * 0.6F);
         this.field_178724_i.field_78796_g = 0.1F - ☃xx * 0.6F;
         this.field_178723_h.field_78795_f = (float) (-Math.PI / 2);
         this.field_178724_i.field_78795_f = (float) (-Math.PI / 2);
         this.field_178723_h.field_78795_f -= ☃xx * 1.2F - ☃xxx * 0.4F;
         this.field_178724_i.field_78795_f -= ☃xx * 1.2F - ☃xxx * 0.4F;
         this.field_178723_h.field_78808_h += MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
         this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
         this.field_178723_h.field_78795_f += MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
         this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
      }
   }

   @Override
   public void func_187073_a(float var1, EnumHandSide var2) {
      float ☃ = ☃ == EnumHandSide.RIGHT ? 1.0F : -1.0F;
      ModelRenderer ☃x = this.func_187074_a(☃);
      ☃x.field_78800_c += ☃;
      ☃x.func_78794_c(☃);
      ☃x.field_78800_c -= ☃;
   }
}
