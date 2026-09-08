package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes;
import net.minecraft.client.renderer.entity.model.ModelDragon;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderDragon extends RenderLiving<EntityDragon> {
   public static final ResourceLocation field_110843_g = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
   private static final ResourceLocation field_110842_f = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation field_110844_k = new ResourceLocation("textures/entity/enderdragon/dragon.png");

   public RenderDragon(RenderManager var1) {
      super(☃, new ModelDragon(0.0F), 0.5F);
      this.func_177094_a(new LayerEnderDragonEyes(this));
      this.func_177094_a(new LayerEnderDragonDeath());
   }

   protected void func_77043_a(EntityDragon var1, float var2, float var3, float var4) {
      float ☃ = (float)☃.func_70974_a(7, ☃)[0];
      float ☃x = (float)(☃.func_70974_a(5, ☃)[1] - ☃.func_70974_a(10, ☃)[1]);
      GlStateManager.func_179114_b(-☃, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(☃x * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
      if (☃.field_70725_aQ > 0) {
         float ☃xx = ((float)☃.field_70725_aQ + ☃ - 1.0F) / 20.0F * 1.6F;
         ☃xx = MathHelper.func_76129_c(☃xx);
         if (☃xx > 1.0F) {
            ☃xx = 1.0F;
         }

         GlStateManager.func_179114_b(☃xx * this.func_77037_a(☃), 0.0F, 0.0F, 1.0F);
      }
   }

   protected void func_77036_a(EntityDragon var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (☃.field_70995_bG > 0) {
         float ☃ = (float)☃.field_70995_bG / 200.0F;
         GlStateManager.func_179143_c(515);
         GlStateManager.func_179141_d();
         GlStateManager.func_179092_a(516, ☃);
         this.func_110776_a(field_110842_f);
         this.field_77045_g.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         GlStateManager.func_179092_a(516, 0.1F);
         GlStateManager.func_179143_c(514);
      }

      this.func_180548_c(☃);
      this.field_77045_g.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (☃.field_70737_aN > 0) {
         GlStateManager.func_179143_c(514);
         GlStateManager.func_179090_x();
         GlStateManager.func_179147_l();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.func_179131_c(1.0F, 0.0F, 0.0F, 0.5F);
         this.field_77045_g.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
         GlStateManager.func_179143_c(515);
      }
   }

   public void func_76986_a(EntityDragon var1, double var2, double var4, double var6, float var8, float var9) {
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      if (☃.field_70992_bH != null) {
         this.func_110776_a(field_110843_g);
         float ☃ = MathHelper.func_76126_a(((float)☃.field_70992_bH.field_70173_aa + ☃) * 0.2F) / 2.0F + 0.5F;
         ☃ = (☃ * ☃ + ☃) * 0.2F;
         func_188325_a(
            ☃,
            ☃,
            ☃,
            ☃,
            ☃.field_70165_t + (☃.field_70169_q - ☃.field_70165_t) * (double)(1.0F - ☃),
            ☃.field_70163_u + (☃.field_70167_r - ☃.field_70163_u) * (double)(1.0F - ☃),
            ☃.field_70161_v + (☃.field_70166_s - ☃.field_70161_v) * (double)(1.0F - ☃),
            ☃.field_70173_aa,
            ☃.field_70992_bH.field_70165_t,
            (double)☃ + ☃.field_70992_bH.field_70163_u,
            ☃.field_70992_bH.field_70161_v
         );
      }
   }

   public static void func_188325_a(
      double var0, double var2, double var4, float var6, double var7, double var9, double var11, int var13, double var14, double var16, double var18
   ) {
      float ☃ = (float)(☃ - ☃);
      float ☃x = (float)(☃ - 1.0 - ☃);
      float ☃xx = (float)(☃ - ☃);
      float ☃xxx = MathHelper.func_76129_c(☃ * ☃ + ☃xx * ☃xx);
      float ☃xxxx = MathHelper.func_76129_c(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃, (float)☃ + 2.0F, (float)☃);
      GlStateManager.func_179114_b((float)(-Math.atan2((double)☃xx, (double)☃)) * (180.0F / (float)Math.PI) - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(-Math.atan2((double)☃xxx, (double)☃x)) * (180.0F / (float)Math.PI) - 90.0F, 1.0F, 0.0F, 0.0F);
      Tessellator ☃xxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxx = ☃xxxxx.func_178180_c();
      RenderHelper.func_74518_a();
      GlStateManager.func_179129_p();
      GlStateManager.func_179103_j(7425);
      float ☃xxxxxxx = 0.0F - ((float)☃ + ☃) * 0.01F;
      float ☃xxxxxxxx = MathHelper.func_76129_c(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx) / 32.0F - ((float)☃ + ☃) * 0.01F;
      ☃xxxxxx.func_181668_a(5, DefaultVertexFormats.field_181709_i);
      int ☃xxxxxxxxx = 8;

      for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx <= 8; ++☃xxxxxxxxxx) {
         float ☃xxxxxxxxxxx = MathHelper.func_76126_a((float)(☃xxxxxxxxxx % 8) * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float ☃xxxxxxxxxxxx = MathHelper.func_76134_b((float)(☃xxxxxxxxxx % 8) * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float ☃xxxxxxxxxxxxx = (float)(☃xxxxxxxxxx % 8) / 8.0F;
         ☃xxxxxx.func_181662_b((double)(☃xxxxxxxxxxx * 0.2F), (double)(☃xxxxxxxxxxxx * 0.2F), 0.0)
            .func_187315_a((double)☃xxxxxxxxxxxxx, (double)☃xxxxxxx)
            .func_181669_b(0, 0, 0, 255)
            .func_181675_d();
         ☃xxxxxx.func_181662_b((double)☃xxxxxxxxxxx, (double)☃xxxxxxxxxxxx, (double)☃xxxx)
            .func_187315_a((double)☃xxxxxxxxxxxxx, (double)☃xxxxxxxx)
            .func_181669_b(255, 255, 255, 255)
            .func_181675_d();
      }

      ☃xxxxx.func_78381_a();
      GlStateManager.func_179089_o();
      GlStateManager.func_179103_j(7424);
      RenderHelper.func_74519_b();
      GlStateManager.func_179121_F();
   }

   protected ResourceLocation func_110775_a(EntityDragon var1) {
      return field_110844_k;
   }
}
