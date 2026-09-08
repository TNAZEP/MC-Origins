package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
   private final ModelRenderer field_78202_a;
   private final ModelRenderer[] field_78201_b = new ModelRenderer[8];

   public ModelSquid() {
      int ☃ = -16;
      this.field_78202_a = new ModelRenderer(this, 0, 0);
      this.field_78202_a.func_78789_a(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.field_78202_a.field_78797_d += 8.0F;

      for(int ☃x = 0; ☃x < this.field_78201_b.length; ++☃x) {
         this.field_78201_b[☃x] = new ModelRenderer(this, 48, 0);
         double ☃xx = (double)☃x * Math.PI * 2.0 / (double)this.field_78201_b.length;
         float ☃xxx = (float)Math.cos(☃xx) * 5.0F;
         float ☃xxxx = (float)Math.sin(☃xx) * 5.0F;
         this.field_78201_b[☃x].func_78789_a(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.field_78201_b[☃x].field_78800_c = ☃xxx;
         this.field_78201_b[☃x].field_78798_e = ☃xxxx;
         this.field_78201_b[☃x].field_78797_d = 15.0F;
         ☃xx = (double)☃x * Math.PI * -2.0 / (double)this.field_78201_b.length + (Math.PI / 2);
         this.field_78201_b[☃x].field_78796_g = (float)☃xx;
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for(ModelRenderer ☃ : this.field_78201_b) {
         ☃.field_78795_f = ☃;
      }
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78202_a.func_78785_a(☃);

      for(ModelRenderer ☃ : this.field_78201_b) {
         ☃.func_78785_a(☃);
      }
   }
}
