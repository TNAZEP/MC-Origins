package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEvokerFangs extends ModelBase {
   private final ModelRenderer field_191213_a = new ModelRenderer(this, 0, 0);
   private final ModelRenderer field_191214_b;
   private final ModelRenderer field_191215_c;

   public ModelEvokerFangs() {
      this.field_191213_a.func_78793_a(-5.0F, 22.0F, -5.0F);
      this.field_191213_a.func_78789_a(0.0F, 0.0F, 0.0F, 10, 12, 10);
      this.field_191214_b = new ModelRenderer(this, 40, 0);
      this.field_191214_b.func_78793_a(1.5F, 22.0F, -4.0F);
      this.field_191214_b.func_78789_a(0.0F, 0.0F, 0.0F, 4, 14, 8);
      this.field_191215_c = new ModelRenderer(this, 40, 0);
      this.field_191215_c.func_78793_a(-1.5F, 22.0F, 4.0F);
      this.field_191215_c.func_78789_a(0.0F, 0.0F, 0.0F, 4, 14, 8);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float ☃ = ☃ * 2.0F;
      if (☃ > 1.0F) {
         ☃ = 1.0F;
      }

      ☃ = 1.0F - ☃ * ☃ * ☃;
      this.field_191214_b.field_78808_h = (float) Math.PI - ☃ * 0.35F * (float) Math.PI;
      this.field_191215_c.field_78808_h = (float) Math.PI + ☃ * 0.35F * (float) Math.PI;
      this.field_191215_c.field_78796_g = (float) Math.PI;
      float ☃ = (☃ + MathHelper.func_76126_a(☃ * 2.7F)) * 0.6F * 12.0F;
      this.field_191214_b.field_78797_d = 24.0F - ☃;
      this.field_191215_c.field_78797_d = this.field_191214_b.field_78797_d;
      this.field_191213_a.field_78797_d = this.field_191214_b.field_78797_d;
      this.field_191213_a.func_78785_a(☃);
      this.field_191214_b.func_78785_a(☃);
      this.field_191215_c.func_78785_a(☃);
   }
}
