package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderFish extends Render<EntityFishHook> {
   private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");

   public RenderFish(RenderManager var1) {
      super(☃);
   }

   public void func_76986_a(EntityFishHook var1, double var2, double var4, double var6, float var8, float var9) {
      EntityPlayer ☃ = ☃.func_190619_l();
      if (☃ != null && !this.field_188301_f) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
         GlStateManager.func_179091_B();
         GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
         this.func_180548_c(☃);
         Tessellator ☃x = Tessellator.func_178181_a();
         BufferBuilder ☃xx = ☃x.func_178180_c();
         int ☃xxx = 1;
         int ☃xxxx = 2;
         float ☃xxxxx = 0.03125F;
         float ☃xxxxxx = 0.0625F;
         float ☃xxxxxxx = 0.0625F;
         float ☃xxxxxxxx = 0.09375F;
         float ☃xxxxxxxxx = 1.0F;
         float ☃xxxxxxxxxx = 0.5F;
         float ☃xxxxxxxxxxx = 0.5F;
         GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(
            (float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F
         );
         if (this.field_188301_f) {
            GlStateManager.func_179142_g();
            GlStateManager.func_187431_e(this.func_188298_c(☃));
         }

         ☃xx.func_181668_a(7, DefaultVertexFormats.field_181710_j);
         ☃xx.func_181662_b(-0.5, -0.5, 0.0).func_187315_a(0.03125, 0.09375).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(0.5, -0.5, 0.0).func_187315_a(0.0625, 0.09375).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(0.5, 0.5, 0.0).func_187315_a(0.0625, 0.0625).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         ☃xx.func_181662_b(-0.5, 0.5, 0.0).func_187315_a(0.03125, 0.0625).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
         ☃x.func_78381_a();
         if (this.field_188301_f) {
            GlStateManager.func_187417_n();
            GlStateManager.func_179119_h();
         }

         GlStateManager.func_179101_C();
         GlStateManager.func_179121_F();
         int ☃x = ☃.func_184591_cq() == EnumHandSide.RIGHT ? 1 : -1;
         ItemStack ☃xx = ☃.func_184614_ca();
         if (☃xx.func_77973_b() != Items.field_151112_aM) {
            ☃x = -☃x;
         }

         float ☃xxxxx = ☃.func_70678_g(☃);
         float ☃xxxxxx = MathHelper.func_76126_a(MathHelper.func_76129_c(☃xxxxx) * (float) Math.PI);
         float ☃xxxxxxx = (☃.field_70760_ar + (☃.field_70761_aq - ☃.field_70760_ar) * ☃) * (float) (Math.PI / 180.0);
         double ☃xxxxxxxx = (double)MathHelper.func_76126_a(☃xxxxxxx);
         double ☃xxxxxxxxx = (double)MathHelper.func_76134_b(☃xxxxxxx);
         double ☃xxxxxxxxxx = (double)☃x * 0.35;
         double ☃xxxxxxxxxxx = 0.8;
         double ☃x;
         double ☃xx;
         double ☃xxx;
         double ☃xxxx;
         if ((this.field_76990_c.field_78733_k == null || this.field_76990_c.field_78733_k.field_74320_O <= 0) && ☃ == Minecraft.func_71410_x().field_71439_g) {
            double ☃xxxxxxxxxxxx = this.field_76990_c.field_78733_k.field_74334_X;
            ☃xxxxxxxxxxxx /= 100.0;
            Vec3d ☃xxxxxxxxxxxxx = new Vec3d((double)☃x * -0.36 * ☃xxxxxxxxxxxx, -0.045 * ☃xxxxxxxxxxxx, 0.4);
            ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_178789_a(-(☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃) * (float) (Math.PI / 180.0));
            ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_178785_b(-(☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃) * (float) (Math.PI / 180.0));
            ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_178785_b(☃xxxxxx * 0.5F);
            ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_178789_a(-☃xxxxxx * 0.7F);
            ☃x = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃ + ☃xxxxxxxxxxxxx.field_72450_a;
            ☃xx = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * (double)☃ + ☃xxxxxxxxxxxxx.field_72448_b;
            ☃xxx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃ + ☃xxxxxxxxxxxxx.field_72449_c;
            ☃xxxx = (double)☃.func_70047_e();
         } else {
            ☃x = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃ - ☃xxxxxxxxx * ☃xxxxxxxxxx - ☃xxxxxxxx * 0.8;
            ☃xx = ☃.field_70167_r + (double)☃.func_70047_e() + (☃.field_70163_u - ☃.field_70167_r) * (double)☃ - 0.45;
            ☃xxx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃ - ☃xxxxxxxx * ☃xxxxxxxxxx + ☃xxxxxxxxx * 0.8;
            ☃xxxx = ☃.func_70093_af() ? -0.1875 : 0.0;
         }

         double ☃x = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃;
         double ☃xx = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * (double)☃ + 0.25;
         double ☃xxx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃;
         double ☃xxxx = (double)((float)(☃x - ☃x));
         double ☃xxxxx = (double)((float)(☃xx - ☃xx)) + ☃xxxx;
         double ☃xxxxxx = (double)((float)(☃xxx - ☃xxx));
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         ☃xx.func_181668_a(3, DefaultVertexFormats.field_181706_f);
         int ☃xxxxxxx = 16;

         for(int ☃xxxxxxxx = 0; ☃xxxxxxxx <= 16; ++☃xxxxxxxx) {
            float ☃xxxxxxxxx = (float)☃xxxxxxxx / 16.0F;
            ☃xx.func_181662_b(
                  ☃ + ☃xxxx * (double)☃xxxxxxxxx, ☃ + ☃xxxxx * (double)(☃xxxxxxxxx * ☃xxxxxxxxx + ☃xxxxxxxxx) * 0.5 + 0.25, ☃ + ☃xxxxxx * (double)☃xxxxxxxxx
               )
               .func_181669_b(0, 0, 0, 255)
               .func_181675_d();
         }

         ☃x.func_78381_a();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
         super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   protected ResourceLocation func_110775_a(EntityFishHook var1) {
      return field_110792_a;
   }
}
