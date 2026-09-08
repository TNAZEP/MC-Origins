package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.model.ModelGuardian;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class RenderGuardian extends RenderLiving<EntityGuardian> {
   private static final ResourceLocation field_177114_e = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation field_177117_k = new ResourceLocation("textures/entity/guardian_beam.png");

   public RenderGuardian(RenderManager var1) {
      super(☃, new ModelGuardian(), 0.5F);
   }

   public boolean func_177071_a(EntityGuardian var1, ICamera var2, double var3, double var5, double var7) {
      if (super.func_177071_a(☃, ☃, ☃, ☃, ☃)) {
         return true;
      } else {
         if (☃.func_175474_cn()) {
            EntityLivingBase ☃ = ☃.func_175466_co();
            if (☃ != null) {
               Vec3d ☃x = this.func_177110_a(☃, (double)☃.field_70131_O * 0.5, 1.0F);
               Vec3d ☃xx = this.func_177110_a(☃, (double)☃.func_70047_e(), 1.0F);
               if (☃.func_78546_a(
                  new AxisAlignedBB(☃xx.field_72450_a, ☃xx.field_72448_b, ☃xx.field_72449_c, ☃x.field_72450_a, ☃x.field_72448_b, ☃x.field_72449_c)
               )) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private Vec3d func_177110_a(EntityLivingBase var1, double var2, float var4) {
      double ☃ = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃x = ☃ + ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      return new Vec3d(☃, ☃x, ☃xx);
   }

   public void func_76986_a(EntityGuardian var1, double var2, double var4, double var6, float var8, float var9) {
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      EntityLivingBase ☃ = ☃.func_175466_co();
      if (☃ != null) {
         float ☃x = ☃.func_175477_p(☃);
         Tessellator ☃xx = Tessellator.func_178181_a();
         BufferBuilder ☃xxx = ☃xx.func_178180_c();
         this.func_110776_a(field_177117_k);
         GlStateManager.func_187421_b(3553, 10242, 10497);
         GlStateManager.func_187421_b(3553, 10243, 10497);
         GlStateManager.func_179140_f();
         GlStateManager.func_179129_p();
         GlStateManager.func_179084_k();
         GlStateManager.func_179132_a(true);
         float ☃xxxx = 240.0F;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
         float ☃xxxxx = (float)☃.field_70170_p.func_82737_E() + ☃;
         float ☃xxxxxx = ☃xxxxx * 0.5F % 1.0F;
         float ☃xxxxxxx = ☃.func_70047_e();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃, (float)☃ + ☃xxxxxxx, (float)☃);
         Vec3d ☃xxxxxxxx = this.func_177110_a(☃, (double)☃.field_70131_O * 0.5, ☃);
         Vec3d ☃xxxxxxxxx = this.func_177110_a(☃, (double)☃xxxxxxx, ☃);
         Vec3d ☃xxxxxxxxxx = ☃xxxxxxxx.func_178788_d(☃xxxxxxxxx);
         double ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_72433_c() + 1.0;
         ☃xxxxxxxxxx = ☃xxxxxxxxxx.func_72432_b();
         float ☃xxxxxxxxxxxx = (float)Math.acos(☃xxxxxxxxxx.field_72448_b);
         float ☃xxxxxxxxxxxxx = (float)Math.atan2(☃xxxxxxxxxx.field_72449_c, ☃xxxxxxxxxx.field_72450_a);
         GlStateManager.func_179114_b(((float) (Math.PI / 2) - ☃xxxxxxxxxxxxx) * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(☃xxxxxxxxxxxx * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         int ☃xxxxxxxxxxxxxx = 1;
         double ☃xxxxxxxxxxxxxxx = (double)☃xxxxx * 0.05 * -1.5;
         ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         float ☃xxxxxxxxxxxxxxxx = ☃x * ☃x;
         int ☃xxxxxxxxxxxxxxxxx = 64 + (int)(☃xxxxxxxxxxxxxxxx * 191.0F);
         int ☃xxxxxxxxxxxxxxxxxx = 32 + (int)(☃xxxxxxxxxxxxxxxx * 191.0F);
         int ☃xxxxxxxxxxxxxxxxxxx = 128 - (int)(☃xxxxxxxxxxxxxxxx * 64.0F);
         double ☃xxxxxxxxxxxxxxxxxxxx = 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxx = 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + (Math.PI * 3.0 / 4.0)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + (Math.PI * 3.0 / 4.0)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + (Math.PI / 4)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + (Math.PI / 4)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + (Math.PI * 5.0 / 4.0)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + (Math.PI * 5.0 / 4.0)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + (Math.PI * 7.0 / 4.0)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + (Math.PI * 7.0 / 4.0)) * 0.282;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + Math.PI) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + Math.PI) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + 0.0) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + 0.0) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + (Math.PI / 2)) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + (Math.PI / 2)) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.cos(☃xxxxxxxxxxxxxxx + (Math.PI * 3.0 / 2.0)) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 + Math.sin(☃xxxxxxxxxxxxxxx + (Math.PI * 3.0 / 2.0)) * 0.2;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.4999;
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)(-1.0F + ☃xxxxxx);
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx * 2.5 + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.4999, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.4999, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.4999, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.4999, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0;
         if (☃.field_70173_aa % 2 == 0) {
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.5;
         }

         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.5, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xxx.func_181662_b(☃xxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_187315_a(0.5, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            .func_181669_b(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, 255)
            .func_181675_d();
         ☃xx.func_78381_a();
         GlStateManager.func_179121_F();
      }
   }

   protected ResourceLocation func_110775_a(EntityGuardian var1) {
      return field_177114_e;
   }
}
