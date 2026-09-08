package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelSkeletonHead;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;

public class RenderWitherSkull extends Render<EntityWitherSkull> {
   private static final ResourceLocation field_110811_a = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation field_110810_f = new ResourceLocation("textures/entity/wither/wither.png");
   private final ModelSkeletonHead field_82401_a = new ModelSkeletonHead();

   public RenderWitherSkull(RenderManager var1) {
      super(☃);
   }

   private float func_82400_a(float var1, float var2, float var3) {
      float ☃ = ☃ - ☃;

      while(☃ < -180.0F) {
         ☃ += 360.0F;
      }

      while(☃ >= 180.0F) {
         ☃ -= 360.0F;
      }

      return ☃ + ☃ * ☃;
   }

   public void func_76986_a(EntityWitherSkull var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179129_p();
      float ☃ = this.func_82400_a(☃.field_70126_B, ☃.field_70177_z, ☃);
      float ☃x = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      float ☃xx = 0.0625F;
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      GlStateManager.func_179141_d();
      this.func_180548_c(☃);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      this.field_82401_a.func_78088_a(☃, 0.0F, 0.0F, 0.0F, ☃, ☃x, 0.0625F);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityWitherSkull var1) {
      return ☃.func_82342_d() ? field_110811_a : field_110810_f;
   }
}
