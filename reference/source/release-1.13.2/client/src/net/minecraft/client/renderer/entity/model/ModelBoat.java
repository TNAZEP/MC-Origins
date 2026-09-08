package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.math.MathHelper;

public class ModelBoat extends ModelBase implements IMultipassModel {
   private final ModelRenderer[] field_78103_a = new ModelRenderer[5];
   private final ModelRenderer[] field_187057_b = new ModelRenderer[2];
   private final ModelRenderer field_187058_c;

   public ModelBoat() {
      this.field_78103_a[0] = new ModelRenderer(this, 0, 0).func_78787_b(128, 64);
      this.field_78103_a[1] = new ModelRenderer(this, 0, 19).func_78787_b(128, 64);
      this.field_78103_a[2] = new ModelRenderer(this, 0, 27).func_78787_b(128, 64);
      this.field_78103_a[3] = new ModelRenderer(this, 0, 35).func_78787_b(128, 64);
      this.field_78103_a[4] = new ModelRenderer(this, 0, 43).func_78787_b(128, 64);
      int ☃ = 32;
      int ☃x = 6;
      int ☃xx = 20;
      int ☃xxx = 4;
      int ☃xxxx = 28;
      this.field_78103_a[0].func_78790_a(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
      this.field_78103_a[0].func_78793_a(0.0F, 3.0F, 1.0F);
      this.field_78103_a[1].func_78790_a(-13.0F, -7.0F, -1.0F, 18, 6, 2, 0.0F);
      this.field_78103_a[1].func_78793_a(-15.0F, 4.0F, 4.0F);
      this.field_78103_a[2].func_78790_a(-8.0F, -7.0F, -1.0F, 16, 6, 2, 0.0F);
      this.field_78103_a[2].func_78793_a(15.0F, 4.0F, 0.0F);
      this.field_78103_a[3].func_78790_a(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
      this.field_78103_a[3].func_78793_a(0.0F, 4.0F, -9.0F);
      this.field_78103_a[4].func_78790_a(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
      this.field_78103_a[4].func_78793_a(0.0F, 4.0F, 9.0F);
      this.field_78103_a[0].field_78795_f = (float) (Math.PI / 2);
      this.field_78103_a[1].field_78796_g = (float) (Math.PI * 3.0 / 2.0);
      this.field_78103_a[2].field_78796_g = (float) (Math.PI / 2);
      this.field_78103_a[3].field_78796_g = (float) Math.PI;
      this.field_187057_b[0] = this.func_187056_a(true);
      this.field_187057_b[0].func_78793_a(3.0F, -5.0F, 9.0F);
      this.field_187057_b[1] = this.func_187056_a(false);
      this.field_187057_b[1].func_78793_a(3.0F, -5.0F, -9.0F);
      this.field_187057_b[1].field_78796_g = (float) Math.PI;
      this.field_187057_b[0].field_78808_h = (float) (Math.PI / 16);
      this.field_187057_b[1].field_78808_h = (float) (Math.PI / 16);
      this.field_187058_c = new ModelRenderer(this, 0, 0).func_78787_b(128, 64);
      this.field_187058_c.func_78790_a(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
      this.field_187058_c.func_78793_a(0.0F, -3.0F, 1.0F);
      this.field_187058_c.field_78795_f = (float) (Math.PI / 2);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
      EntityBoat ☃ = (EntityBoat)☃;
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);

      for(int ☃x = 0; ☃x < 5; ++☃x) {
         this.field_78103_a[☃x].func_78785_a(☃);
      }

      this.func_187055_a(☃, 0, ☃, ☃);
      this.func_187055_a(☃, 1, ☃, ☃);
   }

   @Override
   public void func_187054_b(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179135_a(false, false, false, false);
      this.field_187058_c.func_78785_a(☃);
      GlStateManager.func_179135_a(true, true, true, true);
   }

   protected ModelRenderer func_187056_a(boolean var1) {
      ModelRenderer ☃ = new ModelRenderer(this, 62, ☃ ? 0 : 20).func_78787_b(128, 64);
      int ☃x = 20;
      int ☃xx = 7;
      int ☃xxx = 6;
      float ☃xxxx = -5.0F;
      ☃.func_78789_a(-1.0F, 0.0F, -5.0F, 2, 2, 18);
      ☃.func_78789_a(☃ ? -1.001F : 0.001F, -3.0F, 8.0F, 1, 6, 7);
      return ☃;
   }

   protected void func_187055_a(EntityBoat var1, int var2, float var3, float var4) {
      float ☃ = ☃.func_184448_a(☃, ☃);
      ModelRenderer ☃x = this.field_187057_b[☃];
      ☃x.field_78795_f = (float)MathHelper.func_151238_b((float) (-Math.PI / 3), (float) (-Math.PI / 12), (double)((MathHelper.func_76126_a(-☃) + 1.0F) / 2.0F));
      ☃x.field_78796_g = (float)MathHelper.func_151238_b(
         (float) (-Math.PI / 4), (float) (Math.PI / 4), (double)((MathHelper.func_76126_a(-☃ + 1.0F) + 1.0F) / 2.0F)
      );
      if (☃ == 1) {
         ☃x.field_78796_g = (float) Math.PI - ☃x.field_78796_g;
      }

      ☃x.func_78785_a(☃);
   }
}
