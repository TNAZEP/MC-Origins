package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;

public class ModelShulkerBullet extends ModelBase {
   private final ModelRenderer field_187069_a;

   public ModelShulkerBullet() {
      this.field_78090_t = 64;
      this.field_78089_u = 32;
      this.field_187069_a = new ModelRenderer(this);
      this.field_187069_a.func_78784_a(0, 0).func_78790_a(-4.0F, -4.0F, -1.0F, 8, 8, 2, 0.0F);
      this.field_187069_a.func_78784_a(0, 10).func_78790_a(-1.0F, -4.0F, -4.0F, 2, 8, 8, 0.0F);
      this.field_187069_a.func_78784_a(20, 0).func_78790_a(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
      this.field_187069_a.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_187069_a.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_187069_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_187069_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
   }
}
