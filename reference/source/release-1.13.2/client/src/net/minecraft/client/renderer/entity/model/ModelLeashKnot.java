package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;

public class ModelLeashKnot extends ModelBase {
   private final ModelRenderer field_110723_a;

   public ModelLeashKnot() {
      this(0, 0, 32, 32);
   }

   public ModelLeashKnot(int var1, int var2, int var3, int var4) {
      this.field_78090_t = ☃;
      this.field_78089_u = ☃;
      this.field_110723_a = new ModelRenderer(this, ☃, ☃);
      this.field_110723_a.func_78790_a(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
      this.field_110723_a.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_110723_a.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_110723_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_110723_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
   }
}
