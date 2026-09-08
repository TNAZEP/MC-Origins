package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelLlamaSpit;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.util.ResourceLocation;

public class RenderLlamaSpit extends Render<EntityLlamaSpit> {
   private static final ResourceLocation field_191333_a = new ResourceLocation("textures/entity/llama/spit.png");
   private final ModelLlamaSpit field_191334_f = new ModelLlamaSpit();

   public RenderLlamaSpit(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityLlamaSpit var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃, (float)☃ + 0.15F, (float)☃);
      GlStateManager.func_179114_b(☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃ - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃, 0.0F, 0.0F, 1.0F);
      this.func_180548_c(☃);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      this.field_191334_f.func_78088_a(☃, ☃, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityLlamaSpit var1) {
      return field_191333_a;
   }
}
