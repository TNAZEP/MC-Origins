package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.util.ResourceLocation;

public class RenderDragonFireball extends Render<EntityDragonFireball> {
   private static final ResourceLocation field_188314_a = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");

   public RenderDragonFireball(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityDragonFireball var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      this.func_180548_c(☃);
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      float ☃xx = 1.0F;
      float ☃xxx = 0.5F;
      float ☃xxxx = 0.25F;
      GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      ☃x.func_181668_a(7, DefaultVertexFormats.field_181710_j);
      ☃x.func_181662_b(-0.5, -0.25, 0.0).func_187315_a(0.0, 1.0).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(0.5, -0.25, 0.0).func_187315_a(1.0, 1.0).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(0.5, 0.75, 0.0).func_187315_a(1.0, 0.0).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_181662_b(-0.5, 0.75, 0.0).func_187315_a(0.0, 0.0).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃.func_78381_a();
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityDragonFireball var1) {
      return field_188314_a;
   }
}
