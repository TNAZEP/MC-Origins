package net.minecraft.client.renderer.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockBannerWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBanner;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileEntityBannerRenderer extends TileEntityRenderer<TileEntityBanner> {
   private final ModelBanner field_178465_e = new ModelBanner();

   public void func_199341_a(TileEntityBanner var1, double var2, double var4, double var6, float var8, int var9) {
      float ☃x = 0.6666667F;
      boolean ☃xx = ☃.func_145831_w() == null;
      GlStateManager.func_179094_E();
      ModelRenderer ☃xxx = this.field_178465_e.func_205057_b();
      long ☃;
      if (☃xx) {
         ☃ = 0L;
         GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
         ☃xxx.field_78806_j = true;
      } else {
         ☃ = ☃.func_145831_w().func_82737_E();
         IBlockState ☃ = ☃.func_195044_w();
         if (☃.func_177230_c() instanceof BlockBanner) {
            GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.5F, (float)☃ + 0.5F);
            GlStateManager.func_179114_b((float)(-☃.func_177229_b(BlockBanner.field_176448_b) * 360) / 16.0F, 0.0F, 1.0F, 0.0F);
            ☃xxx.field_78806_j = true;
         } else {
            GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ - 0.16666667F, (float)☃ + 0.5F);
            GlStateManager.func_179114_b(-((EnumFacing)☃.func_177229_b(BlockBannerWall.field_196290_a)).func_185119_l(), 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, -0.3125F, -0.4375F);
            ☃xxx.field_78806_j = false;
         }
      }

      BlockPos ☃ = ☃.func_174877_v();
      float ☃x = (float)((long)(☃.func_177958_n() * 7 + ☃.func_177956_o() * 9 + ☃.func_177952_p() * 13) + ☃) + ☃;
      this.field_178465_e.func_205056_c().field_78795_f = (-0.0125F + 0.01F * MathHelper.func_76134_b(☃x * (float) Math.PI * 0.02F)) * (float) Math.PI;
      GlStateManager.func_179091_B();
      ResourceLocation ☃xx = this.func_178463_a(☃);
      if (☃xx != null) {
         this.func_147499_a(☃xx);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(0.6666667F, -0.6666667F, -0.6666667F);
         this.field_178465_e.func_178687_a();
         GlStateManager.func_179121_F();
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }

   @Nullable
   private ResourceLocation func_178463_a(TileEntityBanner var1) {
      return BannerTextures.field_178466_c.func_187478_a(☃.func_175116_e(), ☃.func_175114_c(), ☃.func_175110_d());
   }
}
