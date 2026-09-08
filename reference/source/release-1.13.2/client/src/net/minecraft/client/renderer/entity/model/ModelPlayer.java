package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class ModelPlayer extends ModelBiped {
   public ModelRenderer field_178734_a;
   public ModelRenderer field_178732_b;
   public ModelRenderer field_178733_c;
   public ModelRenderer field_178731_d;
   public ModelRenderer field_178730_v;
   private final ModelRenderer field_178729_w;
   private final ModelRenderer field_178736_x;
   private final boolean field_178735_y;

   public ModelPlayer(float var1, boolean var2) {
      super(☃, 0.0F, 64, 64);
      this.field_178735_y = ☃;
      this.field_178736_x = new ModelRenderer(this, 24, 0);
      this.field_178736_x.func_78790_a(-3.0F, -6.0F, -1.0F, 6, 6, 1, ☃);
      this.field_178729_w = new ModelRenderer(this, 0, 0);
      this.field_178729_w.func_78787_b(64, 32);
      this.field_178729_w.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, ☃);
      if (☃) {
         this.field_178724_i = new ModelRenderer(this, 32, 48);
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 3, 12, 4, ☃);
         this.field_178724_i.func_78793_a(5.0F, 2.5F, 0.0F);
         this.field_178723_h = new ModelRenderer(this, 40, 16);
         this.field_178723_h.func_78790_a(-2.0F, -2.0F, -2.0F, 3, 12, 4, ☃);
         this.field_178723_h.func_78793_a(-5.0F, 2.5F, 0.0F);
         this.field_178734_a = new ModelRenderer(this, 48, 48);
         this.field_178734_a.func_78790_a(-1.0F, -2.0F, -2.0F, 3, 12, 4, ☃ + 0.25F);
         this.field_178734_a.func_78793_a(5.0F, 2.5F, 0.0F);
         this.field_178732_b = new ModelRenderer(this, 40, 32);
         this.field_178732_b.func_78790_a(-2.0F, -2.0F, -2.0F, 3, 12, 4, ☃ + 0.25F);
         this.field_178732_b.func_78793_a(-5.0F, 2.5F, 10.0F);
      } else {
         this.field_178724_i = new ModelRenderer(this, 32, 48);
         this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, ☃);
         this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_178734_a = new ModelRenderer(this, 48, 48);
         this.field_178734_a.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, ☃ + 0.25F);
         this.field_178734_a.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_178732_b = new ModelRenderer(this, 40, 32);
         this.field_178732_b.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, ☃ + 0.25F);
         this.field_178732_b.func_78793_a(-5.0F, 2.0F, 10.0F);
      }

      this.field_178722_k = new ModelRenderer(this, 16, 48);
      this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃);
      this.field_178722_k.func_78793_a(1.9F, 12.0F, 0.0F);
      this.field_178733_c = new ModelRenderer(this, 0, 48);
      this.field_178733_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃ + 0.25F);
      this.field_178733_c.func_78793_a(1.9F, 12.0F, 0.0F);
      this.field_178731_d = new ModelRenderer(this, 0, 32);
      this.field_178731_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, ☃ + 0.25F);
      this.field_178731_d.func_78793_a(-1.9F, 12.0F, 0.0F);
      this.field_178730_v = new ModelRenderer(this, 16, 32);
      this.field_178730_v.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, ☃ + 0.25F);
      this.field_178730_v.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      GlStateManager.func_179094_E();
      if (this.field_78091_s) {
         float ☃ = 2.0F;
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         GlStateManager.func_179109_b(0.0F, 24.0F * ☃, 0.0F);
         this.field_178733_c.func_78785_a(☃);
         this.field_178731_d.func_78785_a(☃);
         this.field_178734_a.func_78785_a(☃);
         this.field_178732_b.func_78785_a(☃);
         this.field_178730_v.func_78785_a(☃);
      } else {
         if (☃.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         this.field_178733_c.func_78785_a(☃);
         this.field_178731_d.func_78785_a(☃);
         this.field_178734_a.func_78785_a(☃);
         this.field_178732_b.func_78785_a(☃);
         this.field_178730_v.func_78785_a(☃);
      }

      GlStateManager.func_179121_F();
   }

   public void func_178727_b(float var1) {
      func_178685_a(this.field_78116_c, this.field_178736_x);
      this.field_178736_x.field_78800_c = 0.0F;
      this.field_178736_x.field_78797_d = 0.0F;
      this.field_178736_x.func_78785_a(☃);
   }

   public void func_178728_c(float var1) {
      this.field_178729_w.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      func_178685_a(this.field_178722_k, this.field_178733_c);
      func_178685_a(this.field_178721_j, this.field_178731_d);
      func_178685_a(this.field_178724_i, this.field_178734_a);
      func_178685_a(this.field_178723_h, this.field_178732_b);
      func_178685_a(this.field_78115_e, this.field_178730_v);
      if (☃.func_70093_af()) {
         this.field_178729_w.field_78797_d = 2.0F;
      } else {
         this.field_178729_w.field_78797_d = 0.0F;
      }
   }

   @Override
   public void func_178719_a(boolean var1) {
      super.func_178719_a(☃);
      this.field_178734_a.field_78806_j = ☃;
      this.field_178732_b.field_78806_j = ☃;
      this.field_178733_c.field_78806_j = ☃;
      this.field_178731_d.field_78806_j = ☃;
      this.field_178730_v.field_78806_j = ☃;
      this.field_178729_w.field_78806_j = ☃;
      this.field_178736_x.field_78806_j = ☃;
   }

   @Override
   public void func_187073_a(float var1, EnumHandSide var2) {
      ModelRenderer ☃ = this.func_187074_a(☃);
      if (this.field_178735_y) {
         float ☃x = 0.5F * (float)(☃ == EnumHandSide.RIGHT ? 1 : -1);
         ☃.field_78800_c += ☃x;
         ☃.func_78794_c(☃);
         ☃.field_78800_c -= ☃x;
      } else {
         ☃.func_78794_c(☃);
      }
   }
}
