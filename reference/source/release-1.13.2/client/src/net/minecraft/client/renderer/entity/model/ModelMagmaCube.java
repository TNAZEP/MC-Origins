package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {
   private final ModelRenderer[] field_78109_a = new ModelRenderer[8];
   private final ModelRenderer field_78108_b;

   public ModelMagmaCube() {
      for(int ☃ = 0; ☃ < this.field_78109_a.length; ++☃) {
         int ☃x = 0;
         int ☃xx = ☃;
         if (☃ == 2) {
            ☃x = 24;
            ☃xx = 10;
         } else if (☃ == 3) {
            ☃x = 24;
            ☃xx = 19;
         }

         this.field_78109_a[☃] = new ModelRenderer(this, ☃x, ☃xx);
         this.field_78109_a[☃].func_78789_a(-4.0F, (float)(16 + ☃), -4.0F, 8, 1, 8);
      }

      this.field_78108_b = new ModelRenderer(this, 0, 16);
      this.field_78108_b.func_78789_a(-2.0F, 18.0F, -2.0F, 4, 4, 4);
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityMagmaCube ☃ = (EntityMagmaCube)☃;
      float ☃x = ☃.field_70812_c + (☃.field_70811_b - ☃.field_70812_c) * ☃;
      if (☃x < 0.0F) {
         ☃x = 0.0F;
      }

      for(int ☃ = 0; ☃ < this.field_78109_a.length; ++☃) {
         this.field_78109_a[☃].field_78797_d = (float)(-(4 - ☃)) * ☃x * 1.7F;
      }
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78108_b.func_78785_a(☃);

      for(ModelRenderer ☃ : this.field_78109_a) {
         ☃.func_78785_a(☃);
      }
   }
}
