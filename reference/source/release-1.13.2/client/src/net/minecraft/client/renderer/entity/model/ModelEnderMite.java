package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEnderMite extends ModelBase {
   private static final int[][] field_178716_a = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] field_178714_b = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int field_178715_c = field_178716_a.length;
   private final ModelRenderer[] field_178713_d = new ModelRenderer[field_178715_c];

   public ModelEnderMite() {
      float ☃ = -3.5F;

      for(int ☃x = 0; ☃x < this.field_178713_d.length; ++☃x) {
         this.field_178713_d[☃x] = new ModelRenderer(this, field_178714_b[☃x][0], field_178714_b[☃x][1]);
         this.field_178713_d[☃x]
            .func_78789_a(
               (float)field_178716_a[☃x][0] * -0.5F,
               0.0F,
               (float)field_178716_a[☃x][2] * -0.5F,
               field_178716_a[☃x][0],
               field_178716_a[☃x][1],
               field_178716_a[☃x][2]
            );
         this.field_178713_d[☃x].func_78793_a(0.0F, (float)(24 - field_178716_a[☃x][1]), ☃);
         if (☃x < this.field_178713_d.length - 1) {
            ☃ += (float)(field_178716_a[☃x][2] + field_178716_a[☃x + 1][2]) * 0.5F;
         }
      }
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);

      for(ModelRenderer ☃ : this.field_178713_d) {
         ☃.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for(int ☃ = 0; ☃ < this.field_178713_d.length; ++☃) {
         this.field_178713_d[☃].field_78796_g = MathHelper.func_76134_b(☃ * 0.9F + (float)☃ * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.01F
            * (float)(1 + Math.abs(☃ - 2));
         this.field_178713_d[☃].field_78800_c = MathHelper.func_76126_a(☃ * 0.9F + (float)☃ * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.1F
            * (float)Math.abs(☃ - 2);
      }
   }
}
