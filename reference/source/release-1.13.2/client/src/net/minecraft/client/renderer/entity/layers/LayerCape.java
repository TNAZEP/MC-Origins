package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer field_177167_a;

   public LayerCape(RenderPlayer var1) {
      this.field_177167_a = ☃;
   }

   public void func_177141_a(AbstractClientPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (☃.func_152122_n() && !☃.func_82150_aj() && ☃.func_175148_a(EnumPlayerModelParts.CAPE) && ☃.func_110303_q() != null) {
         ItemStack ☃ = ☃.func_184582_a(EntityEquipmentSlot.CHEST);
         if (☃.func_77973_b() != Items.field_185160_cR) {
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_177167_a.func_110776_a(☃.func_110303_q());
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
            double ☃x = ☃.field_71091_bM
               + (☃.field_71094_bP - ☃.field_71091_bM) * (double)☃
               - (☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃);
            double ☃xx = ☃.field_71096_bN
               + (☃.field_71095_bQ - ☃.field_71096_bN) * (double)☃
               - (☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * (double)☃);
            double ☃xxx = ☃.field_71097_bO
               + (☃.field_71085_bR - ☃.field_71097_bO) * (double)☃
               - (☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃);
            float ☃xxxx = ☃.field_70760_ar + (☃.field_70761_aq - ☃.field_70760_ar);
            double ☃xxxxx = (double)MathHelper.func_76126_a(☃xxxx * (float) (Math.PI / 180.0));
            double ☃xxxxxx = (double)(-MathHelper.func_76134_b(☃xxxx * (float) (Math.PI / 180.0)));
            float ☃xxxxxxx = (float)☃xx * 10.0F;
            ☃xxxxxxx = MathHelper.func_76131_a(☃xxxxxxx, -6.0F, 32.0F);
            float ☃xxxxxxxx = (float)(☃x * ☃xxxxx + ☃xxx * ☃xxxxxx) * 100.0F;
            ☃xxxxxxxx = MathHelper.func_76131_a(☃xxxxxxxx, 0.0F, 150.0F);
            float ☃xxxxxxxxx = (float)(☃x * ☃xxxxxx - ☃xxx * ☃xxxxx) * 100.0F;
            ☃xxxxxxxxx = MathHelper.func_76131_a(☃xxxxxxxxx, -20.0F, 20.0F);
            if (☃xxxxxxxx < 0.0F) {
               ☃xxxxxxxx = 0.0F;
            }

            float ☃x = ☃.field_71107_bF + (☃.field_71109_bG - ☃.field_71107_bF) * ☃;
            ☃xxxxxxx += MathHelper.func_76126_a((☃.field_70141_P + (☃.field_70140_Q - ☃.field_70141_P) * ☃) * 6.0F) * 32.0F * ☃x;
            if (☃.func_70093_af()) {
               ☃xxxxxxx += 25.0F;
            }

            GlStateManager.func_179114_b(6.0F + ☃xxxxxxxx / 2.0F + ☃xxxxxxx, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(☃xxxxxxxxx / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(-☃xxxxxxxxx / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            this.field_177167_a.func_177087_b().func_178728_c(0.0625F);
            GlStateManager.func_179121_F();
         }
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
