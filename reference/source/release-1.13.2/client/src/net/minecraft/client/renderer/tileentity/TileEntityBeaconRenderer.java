package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityBeaconRenderer extends TileEntityRenderer<TileEntityBeacon> {
   private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");

   public void func_199341_a(TileEntityBeacon var1, double var2, double var4, double var6, float var8, int var9) {
      this.func_188206_a(☃, ☃, ☃, (double)☃, (double)☃.func_146002_i(), ☃.func_174907_n(), ☃.func_145831_w().func_82737_E());
   }

   private void func_188206_a(double var1, double var3, double var5, double var7, double var9, List<TileEntityBeacon.BeamSegment> var11, long var12) {
      GlStateManager.func_179092_a(516, 0.1F);
      this.func_147499_a(field_147523_b);
      if (☃ > 0.0) {
         GlStateManager.func_179106_n();
         int ☃ = 0;

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            TileEntityBeacon.BeamSegment ☃xx = (TileEntityBeacon.BeamSegment)☃.get(☃x);
            func_188204_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃xx.func_177264_c(), ☃xx.func_177263_b());
            ☃ += ☃xx.func_177264_c();
         }

         GlStateManager.func_179127_m();
      }
   }

   private static void func_188204_a(double var0, double var2, double var4, double var6, double var8, long var10, int var12, int var13, float[] var14) {
      func_188205_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, 0.2, 0.25);
   }

   public static void func_188205_a(
      double var0, double var2, double var4, double var6, double var8, long var10, int var12, int var13, float[] var14, double var15, double var17
   ) {
      int ☃ = ☃ + ☃;
      GlStateManager.func_187421_b(3553, 10242, 10497);
      GlStateManager.func_187421_b(3553, 10243, 10497);
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(☃ + 0.5, ☃, ☃ + 0.5);
      Tessellator ☃x = Tessellator.func_178181_a();
      BufferBuilder ☃xx = ☃x.func_178180_c();
      double ☃xxx = (double)Math.floorMod(☃, 40L) + ☃;
      double ☃xxxx = ☃ < 0 ? ☃xxx : -☃xxx;
      double ☃xxxxx = MathHelper.func_181162_h(☃xxxx * 0.2 - (double)MathHelper.func_76128_c(☃xxxx * 0.1));
      float ☃xxxxxx = ☃[0];
      float ☃xxxxxxx = ☃[1];
      float ☃xxxxxxxx = ☃[2];
      GlStateManager.func_179094_E();
      GlStateManager.func_212477_a(☃xxx * 2.25 - 45.0, 0.0, 1.0, 0.0);
      double ☃xxxxxxxxx = 0.0;
      double ☃xxxxxxxxxx = 0.0;
      double ☃xxxxxxxxxxx = -☃;
      double ☃xxxxxxxxxxxx = 0.0;
      double ☃xxxxxxxxxxxxx = 0.0;
      double ☃xxxxxxxxxxxxxx = -☃;
      double ☃xxxxxxxxxxxxxxx = 0.0;
      double ☃xxxxxxxxxxxxxxxx = 1.0;
      double ☃xxxxxxxxxxxxxxxxx = -1.0 + ☃xxxxx;
      double ☃xxxxxxxxxxxxxxxxxx = (double)☃ * ☃ * (0.5 / ☃) + ☃xxxxxxxxxxxxxxxxx;
      ☃xx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      ☃xx.func_181662_b(0.0, (double)☃, ☃).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, 0.0).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, 0.0).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃xxxxxxxxxxxxxx)
         .func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F)
         .func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃xxxxxxxxxxxxxx)
         .func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F)
         .func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, 0.0).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, 0.0).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, 0.0).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, 0.0).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃xxxxxxxxxxxxxx)
         .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F)
         .func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃xxxxxxxxxxxxxx)
         .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F)
         .func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, 0.0).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, 0.0).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃xx.func_181662_b(0.0, (double)☃, ☃).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F).func_181675_d();
      ☃x.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179132_a(false);
      ☃xxxxxxxxx = -☃;
      double ☃xxxxxxxxxxxxxxxxxxx = -☃;
      ☃xxxxxxxxxx = -☃;
      ☃xxxxxxxxxxx = -☃;
      ☃xxxxxxxxxxxxxxx = 0.0;
      ☃xxxxxxxxxxxxxxxx = 1.0;
      ☃xxxxxxxxxxxxxxxxx = -1.0 + ☃xxxxx;
      ☃xxxxxxxxxxxxxxxxxx = (double)☃ * ☃ + ☃xxxxxxxxxxxxxxxxx;
      ☃xx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      ☃xx.func_181662_b(☃xxxxxxxxx, (double)☃, ☃xxxxxxxxxxxxxxxxxxx)
         .func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F)
         .func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxx, (double)☃, ☃xxxxxxxxxxxxxxxxxxx)
         .func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F)
         .func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃xxxxxxxxxx).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃xxxxxxxxxx).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, ☃).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, ☃).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃xxxxxxxxxx).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃xxxxxxxxxx).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃, (double)☃, ☃).func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, ☃).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxxxx, (double)☃, ☃).func_187315_a(1.0, ☃xxxxxxxxxxxxxxxxx).func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F).func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxx, (double)☃, ☃xxxxxxxxxxxxxxxxxxx)
         .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F)
         .func_181675_d();
      ☃xx.func_181662_b(☃xxxxxxxxx, (double)☃, ☃xxxxxxxxxxxxxxxxxxx)
         .func_187315_a(0.0, ☃xxxxxxxxxxxxxxxxxx)
         .func_181666_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.125F)
         .func_181675_d();
      ☃x.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179145_e();
      GlStateManager.func_179098_w();
      GlStateManager.func_179132_a(true);
   }

   public boolean func_188185_a(TileEntityBeacon var1) {
      return true;
   }
}
