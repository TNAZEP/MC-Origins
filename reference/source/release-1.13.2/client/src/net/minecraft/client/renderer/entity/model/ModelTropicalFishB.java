package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTropicalFishB extends ModelBase {
   private final ModelRenderer field_204240_a;
   private final ModelRenderer field_204241_b;
   private final ModelRenderer field_204242_c;
   private final ModelRenderer field_204243_d;
   private final ModelRenderer field_204244_e;
   private final ModelRenderer field_204245_f;

   public ModelTropicalFishB() {
      this(0.0F);
   }

   public ModelTropicalFishB(float var1) {
      this.field_78090_t = 32;
      this.field_78089_u = 32;
      int ☃ = 19;
      this.field_204240_a = new ModelRenderer(this, 0, 20);
      this.field_204240_a.func_78790_a(-1.0F, -3.0F, -3.0F, 2, 6, 6, ☃);
      this.field_204240_a.func_78793_a(0.0F, 19.0F, 0.0F);
      this.field_204241_b = new ModelRenderer(this, 21, 16);
      this.field_204241_b.func_78790_a(0.0F, -3.0F, 0.0F, 0, 6, 5, ☃);
      this.field_204241_b.func_78793_a(0.0F, 19.0F, 3.0F);
      this.field_204242_c = new ModelRenderer(this, 2, 16);
      this.field_204242_c.func_78790_a(-2.0F, 0.0F, 0.0F, 2, 2, 0, ☃);
      this.field_204242_c.func_78793_a(-1.0F, 20.0F, 0.0F);
      this.field_204242_c.field_78796_g = (float) (Math.PI / 4);
      this.field_204243_d = new ModelRenderer(this, 2, 12);
      this.field_204243_d.func_78790_a(0.0F, 0.0F, 0.0F, 2, 2, 0, ☃);
      this.field_204243_d.func_78793_a(1.0F, 20.0F, 0.0F);
      this.field_204243_d.field_78796_g = (float) (-Math.PI / 4);
      this.field_204244_e = new ModelRenderer(this, 20, 11);
      this.field_204244_e.func_78790_a(0.0F, -4.0F, 0.0F, 0, 4, 6, ☃);
      this.field_204244_e.func_78793_a(0.0F, 16.0F, -3.0F);
      this.field_204245_f = new ModelRenderer(this, 20, 21);
      this.field_204245_f.func_78790_a(0.0F, 0.0F, 0.0F, 0, 4, 6, ☃);
      this.field_204245_f.func_78793_a(0.0F, 22.0F, -3.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_204240_a.func_78785_a(☃);
      this.field_204241_b.func_78785_a(☃);
      this.field_204242_c.func_78785_a(☃);
      this.field_204243_d.func_78785_a(☃);
      this.field_204244_e.func_78785_a(☃);
      this.field_204245_f.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float ☃ = 1.0F;
      if (!☃.func_70090_H()) {
         ☃ = 1.5F;
      }

      this.field_204241_b.field_78796_g = -☃ * 0.45F * MathHelper.func_76126_a(0.6F * ☃);
   }
}
