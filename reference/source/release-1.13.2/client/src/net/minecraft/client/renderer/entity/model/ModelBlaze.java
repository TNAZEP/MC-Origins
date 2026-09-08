package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBlaze extends ModelBase {
   private final ModelRenderer[] field_78106_a = new ModelRenderer[12];
   private final ModelRenderer field_78105_b;

   public ModelBlaze() {
      for(int ☃ = 0; ☃ < this.field_78106_a.length; ++☃) {
         this.field_78106_a[☃] = new ModelRenderer(this, 0, 16);
         this.field_78106_a[☃].func_78789_a(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }

      this.field_78105_b = new ModelRenderer(this, 0, 0);
      this.field_78105_b.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78105_b.func_78785_a(☃);

      for(ModelRenderer ☃ : this.field_78106_a) {
         ☃.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float ☃ = ☃ * (float) Math.PI * -0.1F;

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         this.field_78106_a[☃x].field_78797_d = -2.0F + MathHelper.func_76134_b(((float)(☃x * 2) + ☃) * 0.25F);
         this.field_78106_a[☃x].field_78800_c = MathHelper.func_76134_b(☃) * 9.0F;
         this.field_78106_a[☃x].field_78798_e = MathHelper.func_76126_a(☃) * 9.0F;
         ++☃;
      }

      ☃ = (float) (Math.PI / 4) + ☃ * (float) Math.PI * 0.03F;

      for(int ☃x = 4; ☃x < 8; ++☃x) {
         this.field_78106_a[☃x].field_78797_d = 2.0F + MathHelper.func_76134_b(((float)(☃x * 2) + ☃) * 0.25F);
         this.field_78106_a[☃x].field_78800_c = MathHelper.func_76134_b(☃) * 7.0F;
         this.field_78106_a[☃x].field_78798_e = MathHelper.func_76126_a(☃) * 7.0F;
         ++☃;
      }

      ☃ = 0.47123894F + ☃ * (float) Math.PI * -0.05F;

      for(int ☃x = 8; ☃x < 12; ++☃x) {
         this.field_78106_a[☃x].field_78797_d = 11.0F + MathHelper.func_76134_b(((float)☃x * 1.5F + ☃) * 0.5F);
         this.field_78106_a[☃x].field_78800_c = MathHelper.func_76134_b(☃) * 5.0F;
         this.field_78106_a[☃x].field_78798_e = MathHelper.func_76126_a(☃) * 5.0F;
         ++☃;
      }

      this.field_78105_b.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78105_b.field_78795_f = ☃ * (float) (Math.PI / 180.0);
   }
}
