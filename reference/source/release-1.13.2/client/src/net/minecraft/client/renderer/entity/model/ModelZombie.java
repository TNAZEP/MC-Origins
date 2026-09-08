package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;

public class ModelZombie extends ModelBiped {
   public ModelZombie() {
      this(0.0F, false);
   }

   protected ModelZombie(float var1, float var2, int var3, int var4) {
      super(☃, ☃, ☃, ☃);
   }

   public ModelZombie(float var1, boolean var2) {
      super(☃, 0.0F, 64, ☃ ? 32 : 64);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      boolean ☃ = ☃ instanceof EntityZombie && ((EntityZombie)☃).func_184734_db();
      float ☃x = MathHelper.func_76126_a(this.field_78095_p * (float) Math.PI);
      float ☃xx = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * (float) Math.PI);
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78796_g = -(0.1F - ☃x * 0.6F);
      this.field_178724_i.field_78796_g = 0.1F - ☃x * 0.6F;
      float ☃xxx = (float) -Math.PI / (☃ ? 1.5F : 2.25F);
      this.field_178723_h.field_78795_f = ☃xxx;
      this.field_178724_i.field_78795_f = ☃xxx;
      this.field_178723_h.field_78795_f += ☃x * 1.2F - ☃xx * 0.4F;
      this.field_178724_i.field_78795_f += ☃x * 1.2F - ☃xx * 0.4F;
      this.field_178723_h.field_78808_h += MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(☃ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.field_78795_f += MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
      this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(☃ * 0.067F) * 0.05F;
   }
}
