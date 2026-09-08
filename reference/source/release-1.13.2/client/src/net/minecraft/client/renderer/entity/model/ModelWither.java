package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.math.MathHelper;

public class ModelWither extends ModelBase {
   private final ModelRenderer[] field_82905_a;
   private final ModelRenderer[] field_82904_b;

   public ModelWither(float var1) {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_82905_a = new ModelRenderer[3];
      this.field_82905_a[0] = new ModelRenderer(this, 0, 16);
      this.field_82905_a[0].func_78790_a(-10.0F, 3.9F, -0.5F, 20, 3, 3, ☃);
      this.field_82905_a[1] = new ModelRenderer(this).func_78787_b(this.field_78090_t, this.field_78089_u);
      this.field_82905_a[1].func_78793_a(-2.0F, 6.9F, -0.5F);
      this.field_82905_a[1].func_78784_a(0, 22).func_78790_a(0.0F, 0.0F, 0.0F, 3, 10, 3, ☃);
      this.field_82905_a[1].func_78784_a(24, 22).func_78790_a(-4.0F, 1.5F, 0.5F, 11, 2, 2, ☃);
      this.field_82905_a[1].func_78784_a(24, 22).func_78790_a(-4.0F, 4.0F, 0.5F, 11, 2, 2, ☃);
      this.field_82905_a[1].func_78784_a(24, 22).func_78790_a(-4.0F, 6.5F, 0.5F, 11, 2, 2, ☃);
      this.field_82905_a[2] = new ModelRenderer(this, 12, 22);
      this.field_82905_a[2].func_78790_a(0.0F, 0.0F, 0.0F, 3, 6, 3, ☃);
      this.field_82904_b = new ModelRenderer[3];
      this.field_82904_b[0] = new ModelRenderer(this, 0, 0);
      this.field_82904_b[0].func_78790_a(-4.0F, -4.0F, -4.0F, 8, 8, 8, ☃);
      this.field_82904_b[1] = new ModelRenderer(this, 32, 0);
      this.field_82904_b[1].func_78790_a(-4.0F, -4.0F, -4.0F, 6, 6, 6, ☃);
      this.field_82904_b[1].field_78800_c = -8.0F;
      this.field_82904_b[1].field_78797_d = 4.0F;
      this.field_82904_b[2] = new ModelRenderer(this, 32, 0);
      this.field_82904_b[2].func_78790_a(-4.0F, -4.0F, -4.0F, 6, 6, 6, ☃);
      this.field_82904_b[2].field_78800_c = 10.0F;
      this.field_82904_b[2].field_78797_d = 4.0F;
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);

      for(ModelRenderer ☃ : this.field_82904_b) {
         ☃.func_78785_a(☃);
      }

      for(ModelRenderer ☃ : this.field_82905_a) {
         ☃.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float ☃ = MathHelper.func_76134_b(☃ * 0.1F);
      this.field_82905_a[1].field_78795_f = (0.065F + 0.05F * ☃) * (float) Math.PI;
      this.field_82905_a[2]
         .func_78793_a(
            -2.0F,
            6.9F + MathHelper.func_76134_b(this.field_82905_a[1].field_78795_f) * 10.0F,
            -0.5F + MathHelper.func_76126_a(this.field_82905_a[1].field_78795_f) * 10.0F
         );
      this.field_82905_a[2].field_78795_f = (0.265F + 0.1F * ☃) * (float) Math.PI;
      this.field_82904_b[0].field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_82904_b[0].field_78795_f = ☃ * (float) (Math.PI / 180.0);
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityWither ☃ = (EntityWither)☃;

      for(int ☃x = 1; ☃x < 3; ++☃x) {
         this.field_82904_b[☃x].field_78796_g = (☃.func_82207_a(☃x - 1) - ☃.field_70761_aq) * (float) (Math.PI / 180.0);
         this.field_82904_b[☃x].field_78795_f = ☃.func_82210_r(☃x - 1) * (float) (Math.PI / 180.0);
      }
   }
}
