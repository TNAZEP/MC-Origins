package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.MathHelper;

public abstract class RenderArrow<T extends EntityArrow> extends Render<T> {
   public RenderArrow(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(T var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180548_c(☃);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179094_E();
      GlStateManager.func_179140_f();
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      GlStateManager.func_179114_b(☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃ - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃, 0.0F, 0.0F, 1.0F);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      int ☃xx = 0;
      float ☃xxx = 0.0F;
      float ☃xxxx = 0.5F;
      float ☃xxxxx = 0.0F;
      float ☃xxxxxx = 0.15625F;
      float ☃xxxxxxx = 0.0F;
      float ☃xxxxxxxx = 0.15625F;
      float ☃xxxxxxxxx = 0.15625F;
      float ☃xxxxxxxxxx = 0.3125F;
      float ☃xxxxxxxxxxx = 0.05625F;
      GlStateManager.func_179091_B();
      float ☃xxxxxxxxxxxx = (float)☃.field_70249_b - ☃;
      if (☃xxxxxxxxxxxx > 0.0F) {
         float ☃xxxxxxxxxxxxx = -MathHelper.func_76126_a(☃xxxxxxxxxxxx * 3.0F) * ☃xxxxxxxxxxxx;
         GlStateManager.func_179114_b(☃xxxxxxxxxxxxx, 0.0F, 0.0F, 1.0F);
      }

      GlStateManager.func_179114_b(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(0.05625F, 0.05625F, 0.05625F);
      GlStateManager.func_179109_b(-4.0F, 0.0F, 0.0F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      GlStateManager.func_187432_a(0.05625F, 0.0F, 0.0F);
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(-7.0, -2.0, -2.0).func_187315_a(0.0, 0.15625).func_181675_d();
      ☃x.func_181662_b(-7.0, -2.0, 2.0).func_187315_a(0.15625, 0.15625).func_181675_d();
      ☃x.func_181662_b(-7.0, 2.0, 2.0).func_187315_a(0.15625, 0.3125).func_181675_d();
      ☃x.func_181662_b(-7.0, 2.0, -2.0).func_187315_a(0.0, 0.3125).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_187432_a(-0.05625F, 0.0F, 0.0F);
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(-7.0, 2.0, -2.0).func_187315_a(0.0, 0.15625).func_181675_d();
      ☃x.func_181662_b(-7.0, 2.0, 2.0).func_187315_a(0.15625, 0.15625).func_181675_d();
      ☃x.func_181662_b(-7.0, -2.0, 2.0).func_187315_a(0.15625, 0.3125).func_181675_d();
      ☃x.func_181662_b(-7.0, -2.0, -2.0).func_187315_a(0.0, 0.3125).func_181675_d();
      ☃.func_78381_a();

      for(int ☃ = 0; ☃ < 4; ++☃) {
         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_187432_a(0.0F, 0.0F, 0.05625F);
         ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         ☃x.func_181662_b(-8.0, -2.0, 0.0).func_187315_a(0.0, 0.0).func_181675_d();
         ☃x.func_181662_b(8.0, -2.0, 0.0).func_187315_a(0.5, 0.0).func_181675_d();
         ☃x.func_181662_b(8.0, 2.0, 0.0).func_187315_a(0.5, 0.15625).func_181675_d();
         ☃x.func_181662_b(-8.0, 2.0, 0.0).func_187315_a(0.0, 0.15625).func_181675_d();
         ☃.func_78381_a();
      }

      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179145_e();
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }
}
