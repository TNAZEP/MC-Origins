package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.lwjgl.opengl.GL;

public class FogRenderer {
   private final FloatBuffer field_205091_a = GLAllocation.func_74529_h(16);
   private final FloatBuffer field_205092_b = GLAllocation.func_74529_h(16);
   private float field_205093_c;
   private float field_205094_d;
   private float field_205095_e;
   private float field_205098_h = -1.0F;
   private float field_205099_i = -1.0F;
   private float field_205100_j = -1.0F;
   private int field_205101_k = -1;
   private int field_205102_l = -1;
   private long field_205103_m = -1L;
   private final GameRenderer field_205104_n;
   private final Minecraft field_205105_o;

   public FogRenderer(GameRenderer var1) {
      this.field_205104_n = ☃;
      this.field_205105_o = ☃.func_205000_l();
      this.field_205091_a.put(0.0F).put(0.0F).put(0.0F).put(1.0F).flip();
   }

   public void func_78466_h(float var1) {
      World ☃ = this.field_205105_o.field_71441_e;
      Entity ☃x = this.field_205105_o.func_175606_aa();
      IBlockState ☃xx = ActiveRenderInfo.func_186703_a(this.field_205105_o.field_71441_e, ☃x, ☃);
      IFluidState ☃xxx = ActiveRenderInfo.func_206243_b(this.field_205105_o.field_71441_e, ☃x, ☃);
      if (☃xxx.func_206884_a(FluidTags.field_206959_a)) {
         this.func_205086_b(☃x, ☃, ☃);
      } else if (☃xxx.func_206884_a(FluidTags.field_206960_b)) {
         this.field_205093_c = 0.6F;
         this.field_205094_d = 0.1F;
         this.field_205095_e = 0.0F;
         this.field_205103_m = -1L;
      } else {
         this.func_205089_a(☃x, ☃, ☃);
         this.field_205103_m = -1L;
      }

      double ☃ = (☃x.field_70137_T + (☃x.field_70163_u - ☃x.field_70137_T) * (double)☃) * ☃.field_73011_w.func_76565_k();
      if (☃x instanceof EntityLivingBase && ((EntityLivingBase)☃x).func_70644_a(MobEffects.field_76440_q)) {
         int ☃x = ((EntityLivingBase)☃x).func_70660_b(MobEffects.field_76440_q).func_76459_b();
         if (☃x < 20) {
            ☃ *= (double)(1.0F - (float)☃x / 20.0F);
         } else {
            ☃ = 0.0;
         }
      }

      if (☃ < 1.0) {
         if (☃ < 0.0) {
            ☃ = 0.0;
         }

         ☃ *= ☃;
         this.field_205093_c = (float)((double)this.field_205093_c * ☃);
         this.field_205094_d = (float)((double)this.field_205094_d * ☃);
         this.field_205095_e = (float)((double)this.field_205095_e * ☃);
      }

      if (this.field_205104_n.func_205002_d(☃) > 0.0F) {
         float ☃ = this.field_205104_n.func_205002_d(☃);
         this.field_205093_c = this.field_205093_c * (1.0F - ☃) + this.field_205093_c * 0.7F * ☃;
         this.field_205094_d = this.field_205094_d * (1.0F - ☃) + this.field_205094_d * 0.6F * ☃;
         this.field_205095_e = this.field_205095_e * (1.0F - ☃) + this.field_205095_e * 0.6F * ☃;
      }

      if (☃xxx.func_206884_a(FluidTags.field_206959_a)) {
         float ☃ = 0.0F;
         if (☃x instanceof EntityPlayerSP) {
            EntityPlayerSP ☃x = (EntityPlayerSP)☃x;
            ☃ = ☃x.func_203719_J();
         }

         float ☃ = 1.0F / this.field_205093_c;
         if (☃ > 1.0F / this.field_205094_d) {
            ☃ = 1.0F / this.field_205094_d;
         }

         if (☃ > 1.0F / this.field_205095_e) {
            ☃ = 1.0F / this.field_205095_e;
         }

         this.field_205093_c = this.field_205093_c * (1.0F - ☃) + this.field_205093_c * ☃ * ☃;
         this.field_205094_d = this.field_205094_d * (1.0F - ☃) + this.field_205094_d * ☃ * ☃;
         this.field_205095_e = this.field_205095_e * (1.0F - ☃) + this.field_205095_e * ☃ * ☃;
      } else if (☃x instanceof EntityLivingBase && ((EntityLivingBase)☃x).func_70644_a(MobEffects.field_76439_r)) {
         float ☃ = this.field_205104_n.func_180438_a((EntityLivingBase)☃x, ☃);
         float ☃x = 1.0F / this.field_205093_c;
         if (☃x > 1.0F / this.field_205094_d) {
            ☃x = 1.0F / this.field_205094_d;
         }

         if (☃x > 1.0F / this.field_205095_e) {
            ☃x = 1.0F / this.field_205095_e;
         }

         this.field_205093_c = this.field_205093_c * (1.0F - ☃) + this.field_205093_c * ☃x * ☃;
         this.field_205094_d = this.field_205094_d * (1.0F - ☃) + this.field_205094_d * ☃x * ☃;
         this.field_205095_e = this.field_205095_e * (1.0F - ☃) + this.field_205095_e * ☃x * ☃;
      }

      GlStateManager.func_179082_a(this.field_205093_c, this.field_205094_d, this.field_205095_e, 0.0F);
   }

   private void func_205089_a(Entity var1, World var2, float var3) {
      float ☃ = 0.25F + 0.75F * (float)this.field_205105_o.field_71474_y.field_151451_c / 32.0F;
      ☃ = 1.0F - (float)Math.pow((double)☃, 0.25);
      Vec3d ☃x = ☃.func_72833_a(this.field_205105_o.func_175606_aa(), ☃);
      float ☃xx = (float)☃x.field_72450_a;
      float ☃xxx = (float)☃x.field_72448_b;
      float ☃xxxx = (float)☃x.field_72449_c;
      Vec3d ☃xxxxx = ☃.func_72948_g(☃);
      this.field_205093_c = (float)☃xxxxx.field_72450_a;
      this.field_205094_d = (float)☃xxxxx.field_72448_b;
      this.field_205095_e = (float)☃xxxxx.field_72449_c;
      if (this.field_205105_o.field_71474_y.field_151451_c >= 4) {
         double ☃xxxxxx = MathHelper.func_76126_a(☃.func_72929_e(☃)) > 0.0F ? -1.0 : 1.0;
         Vec3d ☃xxxxxxx = new Vec3d(☃xxxxxx, 0.0, 0.0);
         float ☃xxxxxxxx = (float)☃.func_70676_i(☃).func_72430_b(☃xxxxxxx);
         if (☃xxxxxxxx < 0.0F) {
            ☃xxxxxxxx = 0.0F;
         }

         if (☃xxxxxxxx > 0.0F) {
            float[] ☃xxxxxx = ☃.field_73011_w.func_76560_a(☃.func_72826_c(☃), ☃);
            if (☃xxxxxx != null) {
               ☃xxxxxxxx *= ☃xxxxxx[3];
               this.field_205093_c = this.field_205093_c * (1.0F - ☃xxxxxxxx) + ☃xxxxxx[0] * ☃xxxxxxxx;
               this.field_205094_d = this.field_205094_d * (1.0F - ☃xxxxxxxx) + ☃xxxxxx[1] * ☃xxxxxxxx;
               this.field_205095_e = this.field_205095_e * (1.0F - ☃xxxxxxxx) + ☃xxxxxx[2] * ☃xxxxxxxx;
            }
         }
      }

      this.field_205093_c += (☃xx - this.field_205093_c) * ☃;
      this.field_205094_d += (☃xxx - this.field_205094_d) * ☃;
      this.field_205095_e += (☃xxxx - this.field_205095_e) * ☃;
      float ☃ = ☃.func_72867_j(☃);
      if (☃ > 0.0F) {
         float ☃x = 1.0F - ☃ * 0.5F;
         float ☃xx = 1.0F - ☃ * 0.4F;
         this.field_205093_c *= ☃x;
         this.field_205094_d *= ☃x;
         this.field_205095_e *= ☃xx;
      }

      float ☃ = ☃.func_72819_i(☃);
      if (☃ > 0.0F) {
         float ☃x = 1.0F - ☃ * 0.5F;
         this.field_205093_c *= ☃x;
         this.field_205094_d *= ☃x;
         this.field_205095_e *= ☃x;
      }
   }

   private void func_205086_b(Entity var1, IWorldReaderBase var2, float var3) {
      long ☃ = Util.func_211177_b();
      int ☃x = ☃.func_180494_b(new BlockPos(ActiveRenderInfo.func_178806_a(☃, (double)☃))).func_204274_p();
      if (this.field_205103_m < 0L) {
         this.field_205101_k = ☃x;
         this.field_205102_l = ☃x;
         this.field_205103_m = ☃;
      }

      int ☃ = this.field_205101_k >> 16 & 0xFF;
      int ☃x = this.field_205101_k >> 8 & 0xFF;
      int ☃xx = this.field_205101_k & 0xFF;
      int ☃xxx = this.field_205102_l >> 16 & 0xFF;
      int ☃xxxx = this.field_205102_l >> 8 & 0xFF;
      int ☃xxxxx = this.field_205102_l & 0xFF;
      float ☃xxxxxx = MathHelper.func_76131_a((float)(☃ - this.field_205103_m) / 5000.0F, 0.0F, 1.0F);
      float ☃xxxxxxx = (float)☃xxx + (float)(☃ - ☃xxx) * ☃xxxxxx;
      float ☃xxxxxxxx = (float)☃xxxx + (float)(☃x - ☃xxxx) * ☃xxxxxx;
      float ☃xxxxxxxxx = (float)☃xxxxx + (float)(☃xx - ☃xxxxx) * ☃xxxxxx;
      this.field_205093_c = ☃xxxxxxx / 255.0F;
      this.field_205094_d = ☃xxxxxxxx / 255.0F;
      this.field_205095_e = ☃xxxxxxxxx / 255.0F;
      if (this.field_205101_k != ☃x) {
         this.field_205101_k = ☃x;
         this.field_205102_l = MathHelper.func_76141_d(☃xxxxxxx) << 16 | MathHelper.func_76141_d(☃xxxxxxxx) << 8 | MathHelper.func_76141_d(☃xxxxxxxxx);
         this.field_205103_m = ☃;
      }
   }

   public void func_78468_a(int var1, float var2) {
      Entity ☃ = this.field_205105_o.func_175606_aa();
      this.func_205090_a(false);
      GlStateManager.func_187432_a(0.0F, -1.0F, 0.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      IFluidState ☃x = ActiveRenderInfo.func_206243_b(this.field_205105_o.field_71441_e, ☃, ☃);
      if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70644_a(MobEffects.field_76440_q)) {
         float ☃xx = 5.0F;
         int ☃xxx = ((EntityLivingBase)☃).func_70660_b(MobEffects.field_76440_q).func_76459_b();
         if (☃xxx < 20) {
            ☃xx = 5.0F + (this.field_205104_n.func_205001_m() - 5.0F) * (1.0F - (float)☃xxx / 20.0F);
         }

         GlStateManager.func_187430_a(GlStateManager.FogMode.LINEAR);
         if (☃ == -1) {
            GlStateManager.func_179102_b(0.0F);
            GlStateManager.func_179153_c(☃xx * 0.8F);
         } else {
            GlStateManager.func_179102_b(☃xx * 0.25F);
            GlStateManager.func_179153_c(☃xx);
         }

         if (GL.getCapabilities().GL_NV_fog_distance) {
            GlStateManager.func_187412_c(34138, 34139);
         }
      } else if (☃x.func_206884_a(FluidTags.field_206959_a)) {
         GlStateManager.func_187430_a(GlStateManager.FogMode.EXP2);
         if (☃ instanceof EntityLivingBase) {
            if (☃ instanceof EntityPlayerSP) {
               EntityPlayerSP ☃ = (EntityPlayerSP)☃;
               float ☃x = 0.05F - ☃.func_203719_J() * ☃.func_203719_J() * 0.03F;
               Biome ☃xx = ☃.field_70170_p.func_180494_b(new BlockPos(☃));
               if (☃xx == Biomes.field_76780_h || ☃xx == Biomes.field_150599_m) {
                  ☃x += 0.005F;
               }

               GlStateManager.func_179095_a(☃x);
            } else {
               GlStateManager.func_179095_a(0.05F);
            }
         } else {
            GlStateManager.func_179095_a(0.1F);
         }
      } else if (☃x.func_206884_a(FluidTags.field_206960_b)) {
         GlStateManager.func_187430_a(GlStateManager.FogMode.EXP);
         GlStateManager.func_179095_a(2.0F);
      } else {
         float ☃ = this.field_205104_n.func_205001_m();
         GlStateManager.func_187430_a(GlStateManager.FogMode.LINEAR);
         if (☃ == -1) {
            GlStateManager.func_179102_b(0.0F);
            GlStateManager.func_179153_c(☃);
         } else {
            GlStateManager.func_179102_b(☃ * 0.75F);
            GlStateManager.func_179153_c(☃);
         }

         if (GL.getCapabilities().GL_NV_fog_distance) {
            GlStateManager.func_187412_c(34138, 34139);
         }

         if (this.field_205105_o.field_71441_e.field_73011_w.func_76568_b((int)☃.field_70165_t, (int)☃.field_70161_v)
            || this.field_205105_o.field_71456_v.func_184046_j().func_184056_f()) {
            GlStateManager.func_179102_b(☃ * 0.05F);
            GlStateManager.func_179153_c(Math.min(☃, 192.0F) * 0.5F);
         }
      }

      GlStateManager.func_179142_g();
      GlStateManager.func_179127_m();
      GlStateManager.func_179104_a(1028, 4608);
   }

   public void func_205090_a(boolean var1) {
      if (☃) {
         GlStateManager.func_187402_b(2918, this.field_205091_a);
      } else {
         GlStateManager.func_187402_b(2918, this.func_205087_b());
      }
   }

   private FloatBuffer func_205087_b() {
      if (this.field_205098_h != this.field_205093_c || this.field_205099_i != this.field_205094_d || this.field_205100_j != this.field_205095_e) {
         this.field_205092_b.clear();
         this.field_205092_b.put(this.field_205093_c).put(this.field_205094_d).put(this.field_205095_e).put(1.0F);
         this.field_205092_b.flip();
         this.field_205098_h = this.field_205093_c;
         this.field_205099_i = this.field_205094_d;
         this.field_205100_j = this.field_205095_e;
      }

      return this.field_205092_b;
   }
}
