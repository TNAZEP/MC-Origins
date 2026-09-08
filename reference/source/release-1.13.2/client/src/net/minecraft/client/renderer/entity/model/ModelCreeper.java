package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCreeper extends ModelBase {
   private final ModelRenderer field_78135_a;
   private final ModelRenderer field_78133_b;
   private final ModelRenderer field_78134_c;
   private final ModelRenderer field_78131_d;
   private final ModelRenderer field_78132_e;
   private final ModelRenderer field_78129_f;
   private final ModelRenderer field_78130_g;

   public ModelCreeper() {
      this(0.0F);
   }

   public ModelCreeper(float var1) {
      int ☃ = 6;
      this.field_78135_a = new ModelRenderer(this, 0, 0);
      this.field_78135_a.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, ☃);
      this.field_78135_a.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78133_b = new ModelRenderer(this, 32, 0);
      this.field_78133_b.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, ☃ + 0.5F);
      this.field_78133_b.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78134_c = new ModelRenderer(this, 16, 16);
      this.field_78134_c.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, ☃);
      this.field_78134_c.func_78793_a(0.0F, 6.0F, 0.0F);
      this.field_78131_d = new ModelRenderer(this, 0, 16);
      this.field_78131_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, ☃);
      this.field_78131_d.func_78793_a(-2.0F, 18.0F, 4.0F);
      this.field_78132_e = new ModelRenderer(this, 0, 16);
      this.field_78132_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, ☃);
      this.field_78132_e.func_78793_a(2.0F, 18.0F, 4.0F);
      this.field_78129_f = new ModelRenderer(this, 0, 16);
      this.field_78129_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, ☃);
      this.field_78129_f.func_78793_a(-2.0F, 18.0F, -4.0F);
      this.field_78130_g = new ModelRenderer(this, 0, 16);
      this.field_78130_g.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, ☃);
      this.field_78130_g.func_78793_a(2.0F, 18.0F, -4.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78135_a.func_78785_a(☃);
      this.field_78134_c.func_78785_a(☃);
      this.field_78131_d.func_78785_a(☃);
      this.field_78132_e.func_78785_a(☃);
      this.field_78129_f.func_78785_a(☃);
      this.field_78130_g.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_78135_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78135_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_78131_d.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
      this.field_78132_e.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
      this.field_78129_f.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F + (float) Math.PI) * 1.4F * ☃;
      this.field_78130_g.field_78795_f = MathHelper.func_76134_b(☃ * 0.6662F) * 1.4F * ☃;
   }
}
