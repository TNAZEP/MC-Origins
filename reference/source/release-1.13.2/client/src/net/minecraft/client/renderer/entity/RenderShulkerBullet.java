package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelShulkerBullet;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderShulkerBullet extends Render<EntityShulkerBullet> {
   private static final ResourceLocation field_188348_a = new ResourceLocation("textures/entity/shulker/spark.png");
   private final ModelShulkerBullet field_188349_b = new ModelShulkerBullet();

   public RenderShulkerBullet(RenderManager var1) {
      super(☃);
   }

   private float func_188347_a(float var1, float var2, float var3) {
      float ☃ = ☃ - ☃;

      while(☃ < -180.0F) {
         ☃ += 360.0F;
      }

      while(☃ >= 180.0F) {
         ☃ -= 360.0F;
      }

      return ☃ + ☃ * ☃;
   }

   public void func_76986_a(EntityShulkerBullet var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      float ☃ = this.func_188347_a(☃.field_70126_B, ☃.field_70177_z, ☃);
      float ☃x = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      float ☃xx = (float)☃.field_70173_aa + ☃;
      GlStateManager.func_179109_b((float)☃, (float)☃ + 0.15F, (float)☃);
      GlStateManager.func_179114_b(MathHelper.func_76126_a(☃xx * 0.1F) * 180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(MathHelper.func_76134_b(☃xx * 0.1F) * 180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(MathHelper.func_76126_a(☃xx * 0.15F) * 360.0F, 0.0F, 0.0F, 1.0F);
      float ☃xxx = 0.03125F;
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      this.func_180548_c(☃);
      this.field_188349_b.func_78088_a(☃, 0.0F, 0.0F, 0.0F, ☃, ☃x, 0.03125F);
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.5F);
      GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F);
      this.field_188349_b.func_78088_a(☃, 0.0F, 0.0F, 0.0F, ☃, ☃x, 0.03125F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityShulkerBullet var1) {
      return field_188348_a;
   }
}
