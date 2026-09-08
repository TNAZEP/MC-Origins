package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
   public void func_177141_a(EntityDragon var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (☃.field_70995_bG > 0) {
         Tessellator ☃ = Tessellator.func_178181_a();
         BufferBuilder ☃x = ☃.func_178180_c();
         RenderHelper.func_74518_a();
         float ☃xx = ((float)☃.field_70995_bG + ☃) / 200.0F;
         float ☃xxx = 0.0F;
         if (☃xx > 0.8F) {
            ☃xxx = (☃xx - 0.8F) / 0.2F;
         }

         Random ☃ = new Random(432L);
         GlStateManager.func_179090_x();
         GlStateManager.func_179103_j(7425);
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
         GlStateManager.func_179118_c();
         GlStateManager.func_179089_o();
         GlStateManager.func_179132_a(false);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, -1.0F, -2.0F);

         for(int ☃x = 0; (float)☃x < (☃xx + ☃xx * ☃xx) / 2.0F * 60.0F; ++☃x) {
            GlStateManager.func_179114_b(☃.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(☃.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(☃.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(☃.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(☃.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(☃.nextFloat() * 360.0F + ☃xx * 90.0F, 0.0F, 0.0F, 1.0F);
            float ☃xx = ☃.nextFloat() * 20.0F + 5.0F + ☃xxx * 10.0F;
            float ☃xxx = ☃.nextFloat() * 2.0F + 1.0F + ☃xxx * 2.0F;
            ☃x.func_181668_a(6, DefaultVertexFormats.field_181706_f);
            ☃x.func_181662_b(0.0, 0.0, 0.0).func_181669_b(255, 255, 255, (int)(255.0F * (1.0F - ☃xxx))).func_181675_d();
            ☃x.func_181662_b(-0.866 * (double)☃xxx, (double)☃xx, (double)(-0.5F * ☃xxx)).func_181669_b(255, 0, 255, 0).func_181675_d();
            ☃x.func_181662_b(0.866 * (double)☃xxx, (double)☃xx, (double)(-0.5F * ☃xxx)).func_181669_b(255, 0, 255, 0).func_181675_d();
            ☃x.func_181662_b(0.0, (double)☃xx, (double)(1.0F * ☃xxx)).func_181669_b(255, 0, 255, 0).func_181675_d();
            ☃x.func_181662_b(-0.866 * (double)☃xxx, (double)☃xx, (double)(-0.5F * ☃xxx)).func_181669_b(255, 0, 255, 0).func_181675_d();
            ☃.func_78381_a();
         }

         GlStateManager.func_179121_F();
         GlStateManager.func_179132_a(true);
         GlStateManager.func_179129_p();
         GlStateManager.func_179084_k();
         GlStateManager.func_179103_j(7424);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179098_w();
         GlStateManager.func_179141_d();
         RenderHelper.func_74519_b();
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
