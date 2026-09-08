package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;

public class RenderTntMinecart extends RenderMinecart<EntityMinecartTNT> {
   public RenderTntMinecart(RenderManager var1) {
      super(☃);
   }

   protected void func_188319_a(EntityMinecartTNT var1, float var2, IBlockState var3) {
      int ☃ = ☃.func_94104_d();
      if (☃ > -1 && (float)☃ - ☃ + 1.0F < 10.0F) {
         float ☃x = 1.0F - ((float)☃ - ☃ + 1.0F) / 10.0F;
         ☃x = MathHelper.func_76131_a(☃x, 0.0F, 1.0F);
         ☃x *= ☃x;
         ☃x *= ☃x;
         float ☃xx = 1.0F + ☃x * 0.3F;
         GlStateManager.func_179152_a(☃xx, ☃xx, ☃xx);
      }

      super.func_188319_a(☃, ☃, ☃);
      if (☃ > -1 && ☃ / 5 % 2 == 0) {
         BlockRendererDispatcher ☃ = Minecraft.func_71410_x().func_175602_ab();
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, (1.0F - ((float)☃ - ☃ + 1.0F) / 100.0F) * 0.8F);
         GlStateManager.func_179094_E();
         ☃.func_175016_a(Blocks.field_150335_W.func_176223_P(), 1.0F);
         GlStateManager.func_179121_F();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179084_k();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
      }
   }
}
