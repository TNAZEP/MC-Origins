package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSilverfish extends ModelBase {
   private final ModelRenderer[] field_78171_a;
   private final ModelRenderer[] field_78169_b;
   private final float[] field_78170_c = new float[7];
   private static final int[][] field_78167_d = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] field_78168_e = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public ModelSilverfish() {
      this.field_78171_a = new ModelRenderer[7];
      float ☃ = -3.5F;

      for(int ☃x = 0; ☃x < this.field_78171_a.length; ++☃x) {
         this.field_78171_a[☃x] = new ModelRenderer(this, field_78168_e[☃x][0], field_78168_e[☃x][1]);
         this.field_78171_a[☃x]
            .func_78789_a(
               (float)field_78167_d[☃x][0] * -0.5F, 0.0F, (float)field_78167_d[☃x][2] * -0.5F, field_78167_d[☃x][0], field_78167_d[☃x][1], field_78167_d[☃x][2]
            );
         this.field_78171_a[☃x].func_78793_a(0.0F, (float)(24 - field_78167_d[☃x][1]), ☃);
         this.field_78170_c[☃x] = ☃;
         if (☃x < this.field_78171_a.length - 1) {
            ☃ += (float)(field_78167_d[☃x][2] + field_78167_d[☃x + 1][2]) * 0.5F;
         }
      }

      this.field_78169_b = new ModelRenderer[3];
      this.field_78169_b[0] = new ModelRenderer(this, 20, 0);
      this.field_78169_b[0].func_78789_a(-5.0F, 0.0F, (float)field_78167_d[2][2] * -0.5F, 10, 8, field_78167_d[2][2]);
      this.field_78169_b[0].func_78793_a(0.0F, 16.0F, this.field_78170_c[2]);
      this.field_78169_b[1] = new ModelRenderer(this, 20, 11);
      this.field_78169_b[1].func_78789_a(-3.0F, 0.0F, (float)field_78167_d[4][2] * -0.5F, 6, 4, field_78167_d[4][2]);
      this.field_78169_b[1].func_78793_a(0.0F, 20.0F, this.field_78170_c[4]);
      this.field_78169_b[2] = new ModelRenderer(this, 20, 18);
      this.field_78169_b[2].func_78789_a(-3.0F, 0.0F, (float)field_78167_d[4][2] * -0.5F, 6, 5, field_78167_d[1][2]);
      this.field_78169_b[2].func_78793_a(0.0F, 19.0F, this.field_78170_c[1]);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);

      for(ModelRenderer ☃ : this.field_78171_a) {
         ☃.func_78785_a(☃);
      }

      for(ModelRenderer ☃ : this.field_78169_b) {
         ☃.func_78785_a(☃);
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for(int ☃ = 0; ☃ < this.field_78171_a.length; ++☃) {
         this.field_78171_a[☃].field_78796_g = MathHelper.func_76134_b(☃ * 0.9F + (float)☃ * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.05F
            * (float)(1 + Math.abs(☃ - 2));
         this.field_78171_a[☃].field_78800_c = MathHelper.func_76126_a(☃ * 0.9F + (float)☃ * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.2F
            * (float)Math.abs(☃ - 2);
      }

      this.field_78169_b[0].field_78796_g = this.field_78171_a[2].field_78796_g;
      this.field_78169_b[1].field_78796_g = this.field_78171_a[4].field_78796_g;
      this.field_78169_b[1].field_78800_c = this.field_78171_a[4].field_78800_c;
      this.field_78169_b[2].field_78796_g = this.field_78171_a[1].field_78796_g;
      this.field_78169_b[2].field_78800_c = this.field_78171_a[1].field_78800_c;
   }
}
