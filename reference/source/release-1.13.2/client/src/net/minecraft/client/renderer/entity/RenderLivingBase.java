package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RenderLivingBase<T extends EntityLivingBase> extends Render<T> {
   private static final Logger field_147923_a = LogManager.getLogger();
   private static final DynamicTexture field_177096_e = Util.func_200696_a(new DynamicTexture(16, 16, false), var0 -> {
      var0.func_195414_e().func_195711_f();

      for(int ☃ = 0; ☃ < 16; ++☃) {
         for(int ☃x = 0; ☃x < 16; ++☃x) {
            var0.func_195414_e().func_195700_a(☃x, ☃, -1);
         }
      }

      var0.func_110564_a();
   });
   protected ModelBase field_77045_g;
   protected FloatBuffer field_177095_g = GLAllocation.func_74529_h(4);
   protected List<LayerRenderer<T>> field_177097_h = Lists.<LayerRenderer<T>>newArrayList();
   protected boolean field_188323_j;

   public RenderLivingBase(RenderManager var1, ModelBase var2, float var3) {
      super(☃);
      this.field_77045_g = ☃;
      this.field_76989_e = ☃;
   }

   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean func_177094_a(U var1) {
      return this.field_177097_h.add(☃);
   }

   public ModelBase func_177087_b() {
      return this.field_77045_g;
   }

   protected float func_77034_a(float var1, float var2, float var3) {
      float ☃ = ☃ - ☃;

      while(☃ < -180.0F) {
         ☃ += 360.0F;
      }

      while(☃ >= 180.0F) {
         ☃ -= 360.0F;
      }

      return ☃ + ☃ * ☃;
   }

   public void func_76986_a(T var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179129_p();
      this.field_77045_g.field_78095_p = this.func_77040_d(☃, ☃);
      this.field_77045_g.field_78093_q = ☃.func_184218_aH();
      this.field_77045_g.field_78091_s = ☃.func_70631_g_();

      try {
         float ☃ = this.func_77034_a(☃.field_70760_ar, ☃.field_70761_aq, ☃);
         float ☃x = this.func_77034_a(☃.field_70758_at, ☃.field_70759_as, ☃);
         float ☃xx = ☃x - ☃;
         if (☃.func_184218_aH() && ☃.func_184187_bx() instanceof EntityLivingBase) {
            EntityLivingBase ☃xxx = (EntityLivingBase)☃.func_184187_bx();
            ☃ = this.func_77034_a(☃xxx.field_70760_ar, ☃xxx.field_70761_aq, ☃);
            ☃xx = ☃x - ☃;
            float ☃xxxx = MathHelper.func_76142_g(☃xx);
            if (☃xxxx < -85.0F) {
               ☃xxxx = -85.0F;
            }

            if (☃xxxx >= 85.0F) {
               ☃xxxx = 85.0F;
            }

            ☃ = ☃x - ☃xxxx;
            if (☃xxxx * ☃xxxx > 2500.0F) {
               ☃ += ☃xxxx * 0.2F;
            }

            ☃xx = ☃x - ☃;
         }

         float ☃ = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
         this.func_77039_a(☃, ☃, ☃, ☃);
         float ☃x = this.func_77044_a(☃, ☃);
         this.func_77043_a(☃, ☃x, ☃, ☃);
         float ☃xx = this.func_188322_c(☃, ☃);
         float ☃xxx = 0.0F;
         float ☃xxxx = 0.0F;
         if (!☃.func_184218_aH()) {
            ☃xxx = ☃.field_184618_aE + (☃.field_70721_aZ - ☃.field_184618_aE) * ☃;
            ☃xxxx = ☃.field_184619_aG - ☃.field_70721_aZ * (1.0F - ☃);
            if (☃.func_70631_g_()) {
               ☃xxxx *= 3.0F;
            }

            if (☃xxx > 1.0F) {
               ☃xxx = 1.0F;
            }
         }

         GlStateManager.func_179141_d();
         this.field_77045_g.func_78086_a(☃, ☃xxxx, ☃xxx, ☃);
         this.field_77045_g.func_78087_a(☃xxxx, ☃xxx, ☃x, ☃xx, ☃, ☃xx, ☃);
         if (this.field_188301_f) {
            boolean ☃ = this.func_177088_c(☃);
            GlStateManager.func_179142_g();
            GlStateManager.func_187431_e(this.func_188298_c(☃));
            if (!this.field_188323_j) {
               this.func_77036_a(☃, ☃xxxx, ☃xxx, ☃x, ☃xx, ☃, ☃xx);
            }

            if (!(☃ instanceof EntityPlayer) || !((EntityPlayer)☃).func_175149_v()) {
               this.func_177093_a(☃, ☃xxxx, ☃xxx, ☃, ☃x, ☃xx, ☃, ☃xx);
            }

            GlStateManager.func_187417_n();
            GlStateManager.func_179119_h();
            if (☃) {
               this.func_180565_e();
            }
         } else {
            boolean ☃ = this.func_177090_c(☃, ☃);
            this.func_77036_a(☃, ☃xxxx, ☃xxx, ☃x, ☃xx, ☃, ☃xx);
            if (☃) {
               this.func_177091_f();
            }

            GlStateManager.func_179132_a(true);
            if (!(☃ instanceof EntityPlayer) || !((EntityPlayer)☃).func_175149_v()) {
               this.func_177093_a(☃, ☃xxxx, ☃xxx, ☃, ☃x, ☃xx, ☃, ☃xx);
            }
         }

         GlStateManager.func_179101_C();
      } catch (Exception var19) {
         field_147923_a.error("Couldn't render entity", var19);
      }

      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179098_w();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      GlStateManager.func_179089_o();
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public float func_188322_c(T var1, float var2) {
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(-1.0F, -1.0F, 1.0F);
      this.func_77041_b(☃, ☃);
      float ☃ = 0.0625F;
      GlStateManager.func_179109_b(0.0F, -1.501F, 0.0F);
      return 0.0625F;
   }

   protected boolean func_177088_c(T var1) {
      GlStateManager.func_179140_f();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179090_x();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      return true;
   }

   protected void func_180565_e() {
      GlStateManager.func_179145_e();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179098_w();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   protected void func_77036_a(T var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      boolean ☃ = this.func_193115_c(☃);
      boolean ☃x = !☃ && !☃.func_98034_c(Minecraft.func_71410_x().field_71439_g);
      if (☃ || ☃x) {
         if (!this.func_180548_c(☃)) {
            return;
         }

         if (☃x) {
            GlStateManager.func_187408_a(GlStateManager.Profile.TRANSPARENT_MODEL);
         }

         this.field_77045_g.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         if (☃x) {
            GlStateManager.func_187440_b(GlStateManager.Profile.TRANSPARENT_MODEL);
         }
      }
   }

   protected boolean func_193115_c(T var1) {
      return !☃.func_82150_aj() || this.field_188301_f;
   }

   protected boolean func_177090_c(T var1, float var2) {
      return this.func_177092_a(☃, ☃, true);
   }

   protected boolean func_177092_a(T var1, float var2, boolean var3) {
      float ☃ = ☃.func_70013_c();
      int ☃x = this.func_77030_a(☃, ☃, ☃);
      boolean ☃xx = (☃x >> 24 & 0xFF) > 0;
      boolean ☃xxx = ☃.field_70737_aN > 0 || ☃.field_70725_aQ > 0;
      if (!☃xx && !☃xxx) {
         return false;
      } else if (!☃xx && !☃) {
         return false;
      } else {
         GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
         GlStateManager.func_179098_w();
         GlStateManager.func_187399_a(8960, 8704, OpenGlHelper.field_176095_s);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176099_x, 8448);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_77478_a);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176081_B, 768);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176082_C, 768);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176077_E, 7681);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_77478_a);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176085_I, 770);
         GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
         GlStateManager.func_179098_w();
         GlStateManager.func_187399_a(8960, 8704, OpenGlHelper.field_176095_s);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176099_x, OpenGlHelper.field_176094_t);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176092_v);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176080_A, OpenGlHelper.field_176092_v);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176081_B, 768);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176082_C, 768);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176076_D, 770);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176077_E, 7681);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176085_I, 770);
         this.field_177095_g.position(0);
         if (☃xxx) {
            this.field_177095_g.put(1.0F);
            this.field_177095_g.put(0.0F);
            this.field_177095_g.put(0.0F);
            this.field_177095_g.put(0.3F);
         } else {
            float ☃ = (float)(☃x >> 24 & 0xFF) / 255.0F;
            float ☃x = (float)(☃x >> 16 & 0xFF) / 255.0F;
            float ☃xx = (float)(☃x >> 8 & 0xFF) / 255.0F;
            float ☃xxx = (float)(☃x & 0xFF) / 255.0F;
            this.field_177095_g.put(☃x);
            this.field_177095_g.put(☃xx);
            this.field_177095_g.put(☃xxx);
            this.field_177095_g.put(1.0F - ☃);
         }

         this.field_177095_g.flip();
         GlStateManager.func_187448_b(8960, 8705, this.field_177095_g);
         GlStateManager.func_179138_g(OpenGlHelper.field_176096_r);
         GlStateManager.func_179098_w();
         GlStateManager.func_179144_i(field_177096_e.func_110552_b());
         GlStateManager.func_187399_a(8960, 8704, OpenGlHelper.field_176095_s);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176099_x, 8448);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176091_w);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_77476_b);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176081_B, 768);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176082_C, 768);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176077_E, 7681);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
         GlStateManager.func_187399_a(8960, OpenGlHelper.field_176085_I, 770);
         GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
         return true;
      }
   }

   protected void func_177091_f() {
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      GlStateManager.func_179098_w();
      GlStateManager.func_187399_a(8960, 8704, OpenGlHelper.field_176095_s);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176099_x, 8448);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_77478_a);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176081_B, 768);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176082_C, 768);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176077_E, 8448);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_77478_a);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176079_G, OpenGlHelper.field_176093_u);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176085_I, 770);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176086_J, 770);
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_187399_a(8960, 8704, OpenGlHelper.field_176095_s);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176099_x, 8448);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176081_B, 768);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176082_C, 768);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176098_y, 5890);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176077_E, 8448);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176085_I, 770);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176078_F, 5890);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179138_g(OpenGlHelper.field_176096_r);
      GlStateManager.func_179090_x();
      GlStateManager.func_179144_i(0);
      GlStateManager.func_187399_a(8960, 8704, OpenGlHelper.field_176095_s);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176099_x, 8448);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176081_B, 768);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176082_C, 768);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176098_y, 5890);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176077_E, 8448);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176085_I, 770);
      GlStateManager.func_187399_a(8960, OpenGlHelper.field_176078_F, 5890);
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   protected void func_77039_a(T var1, double var2, double var4, double var6) {
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
   }

   protected void func_77043_a(T var1, float var2, float var3, float var4) {
      GlStateManager.func_179114_b(180.0F - ☃, 0.0F, 1.0F, 0.0F);
      if (☃.field_70725_aQ > 0) {
         float ☃ = ((float)☃.field_70725_aQ + ☃ - 1.0F) / 20.0F * 1.6F;
         ☃ = MathHelper.func_76129_c(☃);
         if (☃ > 1.0F) {
            ☃ = 1.0F;
         }

         GlStateManager.func_179114_b(☃ * this.func_77037_a(☃), 0.0F, 0.0F, 1.0F);
      } else if (☃.func_204805_cN()) {
         GlStateManager.func_179114_b(-90.0F - ☃.field_70125_A, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(((float)☃.field_70173_aa + ☃) * -75.0F, 0.0F, 1.0F, 0.0F);
      } else if (☃.func_145818_k_() || ☃ instanceof EntityPlayer) {
         String ☃ = TextFormatting.func_110646_a(☃.func_200200_C_().getString());
         if (☃ != null
            && ("Dinnerbone".equals(☃) || "Grumm".equals(☃))
            && (!(☃ instanceof EntityPlayer) || ((EntityPlayer)☃).func_175148_a(EnumPlayerModelParts.CAPE))) {
            GlStateManager.func_179109_b(0.0F, ☃.field_70131_O + 0.1F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
         }
      }
   }

   protected float func_77040_d(T var1, float var2) {
      return ☃.func_70678_g(☃);
   }

   protected float func_77044_a(T var1, float var2) {
      return (float)☃.field_70173_aa + ☃;
   }

   protected void func_177093_a(T var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      for(LayerRenderer<T> ☃ : this.field_177097_h) {
         boolean ☃x = this.func_177092_a(☃, ☃, ☃.func_177142_b());
         ☃.func_177141_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
         if (☃x) {
            this.func_177091_f();
         }
      }
   }

   protected float func_77037_a(T var1) {
      return 90.0F;
   }

   protected int func_77030_a(T var1, float var2, float var3) {
      return 0;
   }

   protected void func_77041_b(T var1, float var2) {
   }

   public void func_177067_a(T var1, double var2, double var4, double var6) {
      if (this.func_177070_b(☃)) {
         double ☃ = ☃.func_70068_e(this.field_76990_c.field_78734_h);
         float ☃x = ☃.func_70093_af() ? 32.0F : 64.0F;
         if (!(☃ >= (double)(☃x * ☃x))) {
            String ☃xx = ☃.func_145748_c_().func_150254_d();
            GlStateManager.func_179092_a(516, 0.1F);
            this.func_188296_a(☃, ☃, ☃, ☃, ☃xx, ☃);
         }
      }
   }

   protected boolean func_177070_b(T var1) {
      EntityPlayerSP ☃ = Minecraft.func_71410_x().field_71439_g;
      boolean ☃x = !☃.func_98034_c(☃);
      if (☃ != ☃) {
         Team ☃xx = ☃.func_96124_cp();
         Team ☃xxx = ☃.func_96124_cp();
         if (☃xx != null) {
            Team.EnumVisible ☃xxxx = ☃xx.func_178770_i();
            switch(☃xxxx) {
               case ALWAYS:
                  return ☃x;
               case NEVER:
                  return false;
               case HIDE_FOR_OTHER_TEAMS:
                  return ☃xxx == null ? ☃x : ☃xx.func_142054_a(☃xxx) && (☃xx.func_98297_h() || ☃x);
               case HIDE_FOR_OWN_TEAM:
                  return ☃xxx == null ? ☃x : !☃xx.func_142054_a(☃xxx) && ☃x;
               default:
                  return true;
            }
         }
      }

      return Minecraft.func_71382_s() && ☃ != this.field_76990_c.field_78734_h && ☃x && !☃.func_184207_aI();
   }
}
