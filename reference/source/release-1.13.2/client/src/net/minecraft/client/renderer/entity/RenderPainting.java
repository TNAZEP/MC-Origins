package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderPainting extends Render<EntityPainting> {
   private static final ResourceLocation field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");

   public RenderPainting(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityPainting var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(☃, ☃, ☃);
      GlStateManager.func_179114_b(180.0F - ☃, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179091_B();
      this.func_180548_c(☃);
      PaintingType ☃ = ☃.field_70522_e;
      float ☃x = 0.0625F;
      GlStateManager.func_179152_a(0.0625F, 0.0625F, 0.0625F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      this.func_77010_a(☃, ☃.func_200834_b(), ☃.func_200832_c(), ☃.func_200833_d(), ☃.func_200835_e());
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityPainting var1) {
      return field_110807_a;
   }

   private void func_77010_a(EntityPainting var1, int var2, int var3, int var4, int var5) {
      float ☃ = (float)(-☃) / 2.0F;
      float ☃x = (float)(-☃) / 2.0F;
      float ☃xx = 0.5F;
      float ☃xxx = 0.75F;
      float ☃xxxx = 0.8125F;
      float ☃xxxxx = 0.0F;
      float ☃xxxxxx = 0.0625F;
      float ☃xxxxxxx = 0.75F;
      float ☃xxxxxxxx = 0.8125F;
      float ☃xxxxxxxxx = 0.001953125F;
      float ☃xxxxxxxxxx = 0.001953125F;
      float ☃xxxxxxxxxxx = 0.7519531F;
      float ☃xxxxxxxxxxxx = 0.7519531F;
      float ☃xxxxxxxxxxxxx = 0.0F;
      float ☃xxxxxxxxxxxxxx = 0.0625F;

      for(int ☃xxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxx < ☃ / 16; ++☃xxxxxxxxxxxxxxx) {
         for(int ☃xxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxx < ☃ / 16; ++☃xxxxxxxxxxxxxxxx) {
            float ☃xxxxxxxxxxxxxxxxx = ☃ + (float)((☃xxxxxxxxxxxxxxx + 1) * 16);
            float ☃xxxxxxxxxxxxxxxxxx = ☃ + (float)(☃xxxxxxxxxxxxxxx * 16);
            float ☃xxxxxxxxxxxxxxxxxxx = ☃x + (float)((☃xxxxxxxxxxxxxxxx + 1) * 16);
            float ☃xxxxxxxxxxxxxxxxxxxx = ☃x + (float)(☃xxxxxxxxxxxxxxxx * 16);
            this.func_77008_a(☃, (☃xxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx) / 2.0F, (☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxx) / 2.0F);
            float ☃xxxxxxxxxxxxxxxxxxxxx = (float)(☃ + ☃ - ☃xxxxxxxxxxxxxxx * 16) / 256.0F;
            float ☃xxxxxxxxxxxxxxxxxxxxxx = (float)(☃ + ☃ - (☃xxxxxxxxxxxxxxx + 1) * 16) / 256.0F;
            float ☃xxxxxxxxxxxxxxxxxxxxxxx = (float)(☃ + ☃ - ☃xxxxxxxxxxxxxxxx * 16) / 256.0F;
            float ☃xxxxxxxxxxxxxxxxxxxxxxxx = (float)(☃ + ☃ - (☃xxxxxxxxxxxxxxxx + 1) * 16) / 256.0F;
            Tessellator ☃xxxxxxxxxxxxxxxxxxxxxxxxx = Tessellator.func_178181_a();
            BufferBuilder ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxx.func_178180_c();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181710_j);
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a((double)☃xxxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxx)
               .func_181663_c(0.0F, 0.0F, -1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a((double)☃xxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxx)
               .func_181663_c(0.0F, 0.0F, -1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a((double)☃xxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxxx)
               .func_181663_c(0.0F, 0.0F, -1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a((double)☃xxxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxxx)
               .func_181663_c(0.0F, 0.0F, -1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.75, 0.0)
               .func_181663_c(0.0F, 0.0F, 1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.8125, 0.0)
               .func_181663_c(0.0F, 0.0F, 1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.8125, 0.0625)
               .func_181663_c(0.0F, 0.0F, 1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.75, 0.0625)
               .func_181663_c(0.0F, 0.0F, 1.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.75, 0.001953125)
               .func_181663_c(0.0F, 1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.8125, 0.001953125)
               .func_181663_c(0.0F, 1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.8125, 0.001953125)
               .func_181663_c(0.0F, 1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.75, 0.001953125)
               .func_181663_c(0.0F, 1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.75, 0.001953125)
               .func_181663_c(0.0F, -1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.8125, 0.001953125)
               .func_181663_c(0.0F, -1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.8125, 0.001953125)
               .func_181663_c(0.0F, -1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.75, 0.001953125)
               .func_181663_c(0.0F, -1.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.7519531F, 0.0)
               .func_181663_c(-1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.7519531F, 0.0625)
               .func_181663_c(-1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.7519531F, 0.0625)
               .func_181663_c(-1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.7519531F, 0.0)
               .func_181663_c(-1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.7519531F, 0.0)
               .func_181663_c(1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, -0.5)
               .func_187315_a(0.7519531F, 0.0625)
               .func_181663_c(1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.7519531F, 0.0625)
               .func_181663_c(1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_181662_b((double)☃xxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx, 0.5)
               .func_187315_a(0.7519531F, 0.0)
               .func_181663_c(1.0F, 0.0F, 0.0F)
               .func_181675_d();
            ☃xxxxxxxxxxxxxxxxxxxxxxxxx.func_78381_a();
         }
      }
   }

   private void func_77008_a(EntityPainting var1, float var2, float var3) {
      int ☃ = MathHelper.func_76128_c(☃.field_70165_t);
      int ☃x = MathHelper.func_76128_c(☃.field_70163_u + (double)(☃ / 16.0F));
      int ☃xx = MathHelper.func_76128_c(☃.field_70161_v);
      EnumFacing ☃xxx = ☃.field_174860_b;
      if (☃xxx == EnumFacing.NORTH) {
         ☃ = MathHelper.func_76128_c(☃.field_70165_t + (double)(☃ / 16.0F));
      }

      if (☃xxx == EnumFacing.WEST) {
         ☃xx = MathHelper.func_76128_c(☃.field_70161_v - (double)(☃ / 16.0F));
      }

      if (☃xxx == EnumFacing.SOUTH) {
         ☃ = MathHelper.func_76128_c(☃.field_70165_t - (double)(☃ / 16.0F));
      }

      if (☃xxx == EnumFacing.EAST) {
         ☃xx = MathHelper.func_76128_c(☃.field_70161_v + (double)(☃ / 16.0F));
      }

      int ☃ = this.field_76990_c.field_78722_g.func_175626_b(new BlockPos(☃, ☃x, ☃xx), 0);
      int ☃x = ☃ % 65536;
      int ☃xx = ☃ / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)☃x, (float)☃xx);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
   }
}
