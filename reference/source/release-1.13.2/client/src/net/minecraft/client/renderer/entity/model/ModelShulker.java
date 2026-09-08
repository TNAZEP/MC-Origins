package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.util.math.MathHelper;

public class ModelShulker extends ModelBase {
   private final ModelRenderer field_187067_b;
   private final ModelRenderer field_187068_c;
   private final ModelRenderer field_187066_a;

   public ModelShulker() {
      this.field_78089_u = 64;
      this.field_78090_t = 64;
      this.field_187068_c = new ModelRenderer(this);
      this.field_187067_b = new ModelRenderer(this);
      this.field_187066_a = new ModelRenderer(this);
      this.field_187068_c.func_78784_a(0, 0).func_78789_a(-8.0F, -16.0F, -8.0F, 16, 12, 16);
      this.field_187068_c.func_78793_a(0.0F, 24.0F, 0.0F);
      this.field_187067_b.func_78784_a(0, 28).func_78789_a(-8.0F, -8.0F, -8.0F, 16, 8, 16);
      this.field_187067_b.func_78793_a(0.0F, 24.0F, 0.0F);
      this.field_187066_a.func_78784_a(0, 52).func_78789_a(-3.0F, 0.0F, -3.0F, 6, 6, 6);
      this.field_187066_a.func_78793_a(0.0F, 12.0F, 0.0F);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      EntityShulker ☃ = (EntityShulker)☃;
      float ☃x = ☃ - (float)☃.field_70173_aa;
      float ☃xx = (0.5F + ☃.func_184688_a(☃x)) * (float) Math.PI;
      float ☃xxx = -1.0F + MathHelper.func_76126_a(☃xx);
      float ☃xxxx = 0.0F;
      if (☃xx > (float) Math.PI) {
         ☃xxxx = MathHelper.func_76126_a(☃ * 0.1F) * 0.7F;
      }

      this.field_187068_c.func_78793_a(0.0F, 16.0F + MathHelper.func_76126_a(☃xx) * 8.0F + ☃xxxx, 0.0F);
      if (☃.func_184688_a(☃x) > 0.3F) {
         this.field_187068_c.field_78796_g = ☃xxx * ☃xxx * ☃xxx * ☃xxx * (float) Math.PI * 0.125F;
      } else {
         this.field_187068_c.field_78796_g = 0.0F;
      }

      this.field_187066_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      this.field_187066_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.field_187067_b.func_78785_a(☃);
      this.field_187068_c.func_78785_a(☃);
   }

   public ModelRenderer func_205069_a() {
      return this.field_187067_b;
   }

   public ModelRenderer func_205068_b() {
      return this.field_187068_c;
   }

   public ModelRenderer func_205067_c() {
      return this.field_187066_a;
   }
}
