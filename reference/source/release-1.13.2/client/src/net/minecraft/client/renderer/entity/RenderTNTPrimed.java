package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderTNTPrimed extends Render<EntityTNTPrimed> {
   public RenderTNTPrimed(RenderManager var1) {
      super(☃);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(EntityTNTPrimed var1, double var2, double var4, double var6, float var8, float var9) {
      BlockRendererDispatcher ☃ = Minecraft.func_71410_x().func_175602_ab();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃, (float)☃ + 0.5F, (float)☃);
      if ((float)☃.func_184536_l() - ☃ + 1.0F < 10.0F) {
         float ☃x = 1.0F - ((float)☃.func_184536_l() - ☃ + 1.0F) / 10.0F;
         ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
         ☃x *= ☃x;
         ☃x *= ☃x;
         float ☃xx = 1.0F + ☃x * 0.3F;
         GlStateManager.func_179152_a(☃xx, ☃xx, ☃xx);
      }

      float ☃ = (1.0F - ((float)☃.func_184536_l() - ☃ + 1.0F) / 100.0F) * 0.8F;
      this.func_180548_c(☃);
      GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(-0.5F, -0.5F, 0.5F);
      ☃.func_175016_a(Blocks.field_150335_W.func_176223_P(), ☃.func_70013_c());
      GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
         ☃.func_175016_a(Blocks.field_150335_W.func_176223_P(), 1.0F);
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      } else if (☃.func_184536_l() / 5 % 2 == 0) {
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, ☃);
         GlStateManager.func_179136_a(-3.0F, -3.0F);
         GlStateManager.func_179088_q();
         ☃.func_175016_a(Blocks.field_150335_W.func_176223_P(), 1.0F);
         GlStateManager.func_179136_a(0.0F, 0.0F);
         GlStateManager.func_179113_r();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179084_k();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
      }

      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityTNTPrimed var1) {
      return TextureMap.field_110575_b;
   }
}
