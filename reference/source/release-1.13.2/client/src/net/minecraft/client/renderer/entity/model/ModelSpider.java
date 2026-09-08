package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSpider extends ModelBase {
   private final ModelRenderer field_78209_a;
   private final ModelRenderer field_78207_b;
   private final ModelRenderer field_78208_c;
   private final ModelRenderer field_78205_d;
   private final ModelRenderer field_78206_e;
   private final ModelRenderer field_78203_f;
   private final ModelRenderer field_78204_g;
   private final ModelRenderer field_78212_h;
   private final ModelRenderer field_78213_i;
   private final ModelRenderer field_78210_j;
   private final ModelRenderer field_78211_k;

   public ModelSpider() {
      float ☃ = 0.0F;
      int ☃x = 15;
      this.field_78209_a = new ModelRenderer(this, 32, 4);
      this.field_78209_a.func_78790_a(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
      this.field_78209_a.func_78793_a(0.0F, 15.0F, -3.0F);
      this.field_78207_b = new ModelRenderer(this, 0, 0);
      this.field_78207_b.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
      this.field_78207_b.func_78793_a(0.0F, 15.0F, 0.0F);
      this.field_78208_c = new ModelRenderer(this, 0, 12);
      this.field_78208_c.func_78790_a(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
      this.field_78208_c.func_78793_a(0.0F, 15.0F, 9.0F);
      this.field_78205_d = new ModelRenderer(this, 18, 0);
      this.field_78205_d.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78205_d.func_78793_a(-4.0F, 15.0F, 2.0F);
      this.field_78206_e = new ModelRenderer(this, 18, 0);
      this.field_78206_e.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78206_e.func_78793_a(4.0F, 15.0F, 2.0F);
      this.field_78203_f = new ModelRenderer(this, 18, 0);
      this.field_78203_f.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78203_f.func_78793_a(-4.0F, 15.0F, 1.0F);
      this.field_78204_g = new ModelRenderer(this, 18, 0);
      this.field_78204_g.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78204_g.func_78793_a(4.0F, 15.0F, 1.0F);
      this.field_78212_h = new ModelRenderer(this, 18, 0);
      this.field_78212_h.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78212_h.func_78793_a(-4.0F, 15.0F, 0.0F);
      this.field_78213_i = new ModelRenderer(this, 18, 0);
      this.field_78213_i.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78213_i.func_78793_a(4.0F, 15.0F, 0.0F);
      this.field_78210_j = new ModelRenderer(this, 18, 0);
      this.field_78210_j.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78210_j.func_78793_a(-4.0F, 15.0F, -1.0F);
      this.field_78211_k = new ModelRenderer(this, 18, 0);
      this.field_78211_k.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.field_78211_k.func_78793_a(4.0F, 15.0F, -1.0F);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_78209_a.func_78785_a(☃);
      this.field_78207_b.func_78785_a(☃);
      this.field_78208_c.func_78785_a(☃);
      this.field_78205_d.func_78785_a(☃);
      this.field_78206_e.func_78785_a(☃);
      this.field_78203_f.func_78785_a(☃);
      this.field_78204_g.func_78785_a(☃);
      this.field_78212_h.func_78785_a(☃);
      this.field_78213_i.func_78785_a(☃);
      this.field_78210_j.func_78785_a(☃);
      this.field_78211_k.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.field_78209_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_78209_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      float ☃ = (float) (Math.PI / 4);
      this.field_78205_d.field_78808_h = (float) (-Math.PI / 4);
      this.field_78206_e.field_78808_h = (float) (Math.PI / 4);
      this.field_78203_f.field_78808_h = -0.58119464F;
      this.field_78204_g.field_78808_h = 0.58119464F;
      this.field_78212_h.field_78808_h = -0.58119464F;
      this.field_78213_i.field_78808_h = 0.58119464F;
      this.field_78210_j.field_78808_h = (float) (-Math.PI / 4);
      this.field_78211_k.field_78808_h = (float) (Math.PI / 4);
      float ☃x = -0.0F;
      float ☃xx = (float) (Math.PI / 8);
      this.field_78205_d.field_78796_g = (float) (Math.PI / 4);
      this.field_78206_e.field_78796_g = (float) (-Math.PI / 4);
      this.field_78203_f.field_78796_g = (float) (Math.PI / 8);
      this.field_78204_g.field_78796_g = (float) (-Math.PI / 8);
      this.field_78212_h.field_78796_g = (float) (-Math.PI / 8);
      this.field_78213_i.field_78796_g = (float) (Math.PI / 8);
      this.field_78210_j.field_78796_g = (float) (-Math.PI / 4);
      this.field_78211_k.field_78796_g = (float) (Math.PI / 4);
      float ☃xxx = -(MathHelper.func_76134_b(☃ * 0.6662F * 2.0F + 0.0F) * 0.4F) * ☃;
      float ☃xxxx = -(MathHelper.func_76134_b(☃ * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * ☃;
      float ☃xxxxx = -(MathHelper.func_76134_b(☃ * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * ☃;
      float ☃xxxxxx = -(MathHelper.func_76134_b(☃ * 0.6662F * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * ☃;
      float ☃xxxxxxx = Math.abs(MathHelper.func_76126_a(☃ * 0.6662F + 0.0F) * 0.4F) * ☃;
      float ☃xxxxxxxx = Math.abs(MathHelper.func_76126_a(☃ * 0.6662F + (float) Math.PI) * 0.4F) * ☃;
      float ☃xxxxxxxxx = Math.abs(MathHelper.func_76126_a(☃ * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * ☃;
      float ☃xxxxxxxxxx = Math.abs(MathHelper.func_76126_a(☃ * 0.6662F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * ☃;
      this.field_78205_d.field_78796_g += ☃xxx;
      this.field_78206_e.field_78796_g += -☃xxx;
      this.field_78203_f.field_78796_g += ☃xxxx;
      this.field_78204_g.field_78796_g += -☃xxxx;
      this.field_78212_h.field_78796_g += ☃xxxxx;
      this.field_78213_i.field_78796_g += -☃xxxxx;
      this.field_78210_j.field_78796_g += ☃xxxxxx;
      this.field_78211_k.field_78796_g += -☃xxxxxx;
      this.field_78205_d.field_78808_h += ☃xxxxxxx;
      this.field_78206_e.field_78808_h += -☃xxxxxxx;
      this.field_78203_f.field_78808_h += ☃xxxxxxxx;
      this.field_78204_g.field_78808_h += -☃xxxxxxxx;
      this.field_78212_h.field_78808_h += ☃xxxxxxxxx;
      this.field_78213_i.field_78808_h += -☃xxxxxxxxx;
      this.field_78210_j.field_78808_h += ☃xxxxxxxxxx;
      this.field_78211_k.field_78808_h += -☃xxxxxxxxxx;
   }
}
