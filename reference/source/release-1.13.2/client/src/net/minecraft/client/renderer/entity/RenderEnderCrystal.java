package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelEnderCrystal;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderEnderCrystal extends Render<EntityEnderCrystal> {
   private static final ResourceLocation field_110787_a = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
   private final ModelBase field_76995_b = new ModelEnderCrystal(0.0F, true);
   private final ModelBase field_188316_g = new ModelEnderCrystal(0.0F, false);

   public RenderEnderCrystal(RenderManager var1) {
      super(☃);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9) {
      float ☃ = (float)☃.field_70261_a + ☃;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      this.func_110776_a(field_110787_a);
      float ☃x = MathHelper.func_76126_a(☃ * 0.2F) / 2.0F + 0.5F;
      ☃x = ☃x * ☃x + ☃x;
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      if (☃.func_184520_k()) {
         this.field_76995_b.func_78088_a(☃, 0.0F, ☃ * 3.0F, ☃x * 0.2F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.field_188316_g.func_78088_a(☃, 0.0F, ☃ * 3.0F, ☃x * 0.2F, 0.0F, 0.0F, 0.0625F);
      }

      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179121_F();
      BlockPos ☃ = ☃.func_184518_j();
      if (☃ != null) {
         this.func_110776_a(RenderDragon.field_110843_g);
         float ☃x = (float)☃.func_177958_n() + 0.5F;
         float ☃xx = (float)☃.func_177956_o() + 0.5F;
         float ☃xxx = (float)☃.func_177952_p() + 0.5F;
         double ☃xxxx = (double)☃x - ☃.field_70165_t;
         double ☃xxxxx = (double)☃xx - ☃.field_70163_u;
         double ☃xxxxxx = (double)☃xxx - ☃.field_70161_v;
         RenderDragon.func_188325_a(
            ☃ + ☃xxxx,
            ☃ - 0.3 + (double)(☃x * 0.4F) + ☃xxxxx,
            ☃ + ☃xxxxxx,
            ☃,
            (double)☃x,
            (double)☃xx,
            (double)☃xxx,
            ☃.field_70261_a,
            ☃.field_70165_t,
            ☃.field_70163_u,
            ☃.field_70161_v
         );
      }

      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityEnderCrystal var1) {
      return field_110787_a;
   }

   public boolean func_177071_a(EntityEnderCrystal var1, ICamera var2, double var3, double var5, double var7) {
      return super.func_177071_a(☃, ☃, ☃, ☃, ☃) || ☃.func_184518_j() != null;
   }
}
