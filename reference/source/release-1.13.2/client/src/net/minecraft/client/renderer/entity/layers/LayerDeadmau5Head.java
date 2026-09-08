package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class LayerDeadmau5Head implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer field_177208_a;

   public LayerDeadmau5Head(RenderPlayer var1) {
      this.field_177208_a = ☃;
   }

   public void func_177141_a(AbstractClientPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if ("deadmau5".equals(☃.func_200200_C_().getString()) && ☃.func_152123_o() && !☃.func_82150_aj()) {
         this.field_177208_a.func_110776_a(☃.func_110306_p());

         for(int ☃ = 0; ☃ < 2; ++☃) {
            float ☃x = ☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃ - (☃.field_70760_ar + (☃.field_70761_aq - ☃.field_70760_ar) * ☃);
            float ☃xx = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
            GlStateManager.func_179094_E();
            GlStateManager.func_179114_b(☃x, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(☃xx, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.375F * (float)(☃ * 2 - 1), 0.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -0.375F, 0.0F);
            GlStateManager.func_179114_b(-☃xx, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-☃x, 0.0F, 1.0F, 0.0F);
            float ☃xxx = 1.3333334F;
            GlStateManager.func_179152_a(1.3333334F, 1.3333334F, 1.3333334F);
            this.field_177208_a.func_177087_b().func_178727_b(0.0625F);
            GlStateManager.func_179121_F();
         }
      }
   }

   @Override
   public boolean func_177142_b() {
      return true;
   }
}
