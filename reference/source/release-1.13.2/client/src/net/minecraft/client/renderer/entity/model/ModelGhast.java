package net.minecraft.client.renderer.entity.model;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGhast extends ModelBase {
   private final ModelRenderer field_78128_a;
   private final ModelRenderer[] field_78127_b = new ModelRenderer[9];

   public ModelGhast() {
      int ☃ = -16;
      this.field_78128_a = new ModelRenderer(this, 0, 0);
      this.field_78128_a.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
      this.field_78128_a.field_78797_d += 8.0F;
      Random ☃x = new Random(1660L);

      for(int ☃xx = 0; ☃xx < this.field_78127_b.length; ++☃xx) {
         this.field_78127_b[☃xx] = new ModelRenderer(this, 0, 0);
         float ☃xxx = (((float)(☃xx % 3) - (float)(☃xx / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float ☃xxxx = ((float)(☃xx / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int ☃xxxxx = ☃x.nextInt(7) + 8;
         this.field_78127_b[☃xx].func_78789_a(-1.0F, 0.0F, -1.0F, 2, ☃xxxxx, 2);
         this.field_78127_b[☃xx].field_78800_c = ☃xxx;
         this.field_78127_b[☃xx].field_78798_e = ☃xxxx;
         this.field_78127_b[☃xx].field_78797_d = 15.0F;
      }
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for(int ☃ = 0; ☃ < this.field_78127_b.length; ++☃) {
         this.field_78127_b[☃].field_78795_f = 0.2F * MathHelper.func_76126_a(☃ * 0.3F + (float)☃) + 0.4F;
      }
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F);
      this.field_78128_a.func_78785_a(☃);

      for(ModelRenderer ☃ : this.field_78127_b) {
         ☃.func_78785_a(☃);
      }

      GlStateManager.func_179121_F();
   }
}
