package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBook extends ModelBase {
   private final ModelRenderer field_78102_a = new ModelRenderer(this).func_78784_a(0, 0).func_78789_a(-6.0F, -5.0F, 0.0F, 6, 10, 0);
   private final ModelRenderer field_78100_b = new ModelRenderer(this).func_78784_a(16, 0).func_78789_a(0.0F, -5.0F, 0.0F, 6, 10, 0);
   private final ModelRenderer field_78101_c;
   private final ModelRenderer field_78098_d;
   private final ModelRenderer field_78099_e;
   private final ModelRenderer field_78096_f;
   private final ModelRenderer field_78097_g = new ModelRenderer(this).func_78784_a(12, 0).func_78789_a(-1.0F, -5.0F, 0.0F, 2, 10, 0);

   public ModelBook() {
      this.field_78101_c = new ModelRenderer(this).func_78784_a(0, 10).func_78789_a(0.0F, -4.0F, -0.99F, 5, 8, 1);
      this.field_78098_d = new ModelRenderer(this).func_78784_a(12, 10).func_78789_a(0.0F, -4.0F, -0.01F, 5, 8, 1);
      this.field_78099_e = new ModelRenderer(this).func_78784_a(24, 10).func_78789_a(0.0F, -4.0F, 0.0F, 5, 8, 0);
      this.field_78096_f = new ModelRenderer(this).func_78784_a(24, 10).func_78789_a(0.0F, -4.0F, 0.0F, 5, 8, 0);
      this.field_78102_a.func_78793_a(0.0F, 0.0F, -1.0F);
      this.field_78100_b.func_78793_a(0.0F, 0.0F, 1.0F);
      this.field_78097_g.field_78796_g = (float) (Math.PI / 2);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78102_a.func_78785_a(☃);
      this.field_78100_b.func_78785_a(☃);
      this.field_78097_g.func_78785_a(☃);
      this.field_78101_c.func_78785_a(☃);
      this.field_78098_d.func_78785_a(☃);
      this.field_78099_e.func_78785_a(☃);
      this.field_78096_f.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float ☃ = (MathHelper.func_76126_a(☃ * 0.02F) * 0.1F + 1.25F) * ☃;
      this.field_78102_a.field_78796_g = (float) Math.PI + ☃;
      this.field_78100_b.field_78796_g = -☃;
      this.field_78101_c.field_78796_g = ☃;
      this.field_78098_d.field_78796_g = -☃;
      this.field_78099_e.field_78796_g = ☃ - ☃ * 2.0F * ☃;
      this.field_78096_f.field_78796_g = ☃ - ☃ * 2.0F * ☃;
      this.field_78101_c.field_78800_c = MathHelper.func_76126_a(☃);
      this.field_78098_d.field_78800_c = MathHelper.func_76126_a(☃);
      this.field_78099_e.field_78800_c = MathHelper.func_76126_a(☃);
      this.field_78096_f.field_78800_c = MathHelper.func_76126_a(☃);
   }
}
