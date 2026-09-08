package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelSign;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TileEntitySignRenderer extends TileEntityRenderer<TileEntitySign> {
   private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");
   private final ModelSign field_147514_c = new ModelSign();

   public void func_199341_a(TileEntitySign var1, double var2, double var4, double var6, float var8, int var9) {
      IBlockState ☃ = ☃.func_195044_w();
      GlStateManager.func_179094_E();
      float ☃x = 0.6666667F;
      if (☃.func_177230_c() == Blocks.field_196649_cc) {
         GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
         GlStateManager.func_179114_b(-((float)(☃.func_177229_b(BlockStandingSign.field_176413_a) * 360) / 16.0F), 0.0F, 1.0F, 0.0F);
         this.field_147514_c.func_205064_b().field_78806_j = true;
      } else {
         GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
         GlStateManager.func_179114_b(-((EnumFacing)☃.func_177229_b(BlockWallSign.field_176412_a)).func_185119_l(), 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179109_b(0.0F, -0.3125F, -0.4375F);
         this.field_147514_c.func_205064_b().field_78806_j = false;
      }

      if (☃ >= 0) {
         this.func_147499_a(field_178460_a[☃]);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(4.0F, 2.0F, 1.0F);
         GlStateManager.func_179109_b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.func_179128_n(5888);
      } else {
         this.func_147499_a(field_147513_b);
      }

      GlStateManager.func_179091_B();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.6666667F, -0.6666667F, -0.6666667F);
      this.field_147514_c.func_78164_a();
      GlStateManager.func_179121_F();
      FontRenderer ☃ = this.func_147498_b();
      float ☃x = 0.010416667F;
      GlStateManager.func_179109_b(0.0F, 0.33333334F, 0.046666667F);
      GlStateManager.func_179152_a(0.010416667F, -0.010416667F, 0.010416667F);
      GlStateManager.func_187432_a(0.0F, 0.0F, -0.010416667F);
      GlStateManager.func_179132_a(false);
      if (☃ < 0) {
         for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
            String ☃xxx = ☃.func_212364_a(☃xx, var1x -> {
               List<ITextComponent> ☃ = GuiUtilRenderComponents.func_178908_a(var1x, 90, ☃, false, true);
               return ☃.isEmpty() ? "" : ((ITextComponent)☃.get(0)).func_150254_d();
            });
            if (☃xxx != null) {
               if (☃xx == ☃.field_145918_i) {
                  ☃xxx = "> " + ☃xxx + " <";
               }

               ☃.func_211126_b(☃xxx, (float)(-☃.func_78256_a(☃xxx) / 2), (float)(☃xx * 10 - ☃.field_145915_a.length * 5), 0);
            }
         }
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
      if (☃ >= 0) {
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5888);
      }
   }
}
