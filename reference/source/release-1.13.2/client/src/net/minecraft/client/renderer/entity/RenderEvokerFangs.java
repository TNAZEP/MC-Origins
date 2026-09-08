package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelEvokerFangs;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.util.ResourceLocation;

public class RenderEvokerFangs extends Render<EntityEvokerFangs> {
   private static final ResourceLocation field_191329_a = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
   private final ModelEvokerFangs field_191330_f = new ModelEvokerFangs();

   public RenderEvokerFangs(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityEvokerFangs var1, double var2, double var4, double var6, float var8, float var9) {
      float ☃ = ☃.func_190550_a(☃);
      if (☃ != 0.0F) {
         float ☃x = 2.0F;
         if (☃ > 0.9F) {
            ☃x = (float)((double)☃x * ((1.0 - (double)☃) / 0.1F));
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179129_p();
         GlStateManager.func_179141_d();
         this.func_180548_c(☃);
         GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
         GlStateManager.func_179114_b(90.0F - ☃.field_70177_z, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179152_a(-☃x, -☃x, ☃x);
         float ☃x = 0.03125F;
         GlStateManager.func_179109_b(0.0F, -0.626F, 0.0F);
         this.field_191330_f.func_78088_a(☃, ☃, 0.0F, 0.0F, ☃.field_70177_z, ☃.field_70125_A, 0.03125F);
         GlStateManager.func_179121_F();
         GlStateManager.func_179089_o();
         super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   protected ResourceLocation func_110775_a(EntityEvokerFangs var1) {
      return field_191329_a;
   }
}
