package net.minecraft.client.renderer.tileentity;

import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class TileEntityEndPortalRenderer extends TileEntityRenderer<TileEntityEndPortal> {
   private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
   private static final Random field_147527_e = new Random(31100L);
   private static final FloatBuffer field_188201_h = GLAllocation.func_74529_h(16);
   private static final FloatBuffer field_188202_i = GLAllocation.func_74529_h(16);
   private final FloatBuffer field_147528_b = GLAllocation.func_74529_h(16);

   public void func_199341_a(TileEntityEndPortal var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.func_179140_f();
      field_147527_e.setSeed(31100L);
      GlStateManager.func_179111_a(2982, field_188201_h);
      GlStateManager.func_179111_a(2983, field_188202_i);
      double ☃ = ☃ * ☃ + ☃ * ☃ + ☃ * ☃;
      int ☃x = this.func_191286_a(☃);
      float ☃xx = this.func_191287_c();
      boolean ☃xxx = false;

      for(int ☃xxxx = 0; ☃xxxx < ☃x; ++☃xxxx) {
         GlStateManager.func_179094_E();
         float ☃xxxxx = 2.0F / (float)(18 - ☃xxxx);
         if (☃xxxx == 0) {
            this.func_147499_a(field_147529_c);
            ☃xxxxx = 0.15F;
            GlStateManager.func_179147_l();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (☃xxxx >= 1) {
            this.func_147499_a(field_147526_d);
            ☃xxx = true;
            Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
         }

         if (☃xxxx == 1) {
            GlStateManager.func_179147_l();
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
         }

         GlStateManager.func_179149_a(GlStateManager.TexGen.S, 9216);
         GlStateManager.func_179149_a(GlStateManager.TexGen.T, 9216);
         GlStateManager.func_179149_a(GlStateManager.TexGen.R, 9216);
         GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9474, this.func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
         GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9474, this.func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
         GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9474, this.func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
         GlStateManager.func_179087_a(GlStateManager.TexGen.S);
         GlStateManager.func_179087_a(GlStateManager.TexGen.T);
         GlStateManager.func_179087_a(GlStateManager.TexGen.R);
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179094_E();
         GlStateManager.func_179096_D();
         GlStateManager.func_179109_b(0.5F, 0.5F, 0.0F);
         GlStateManager.func_179152_a(0.5F, 0.5F, 1.0F);
         float ☃xxxxx = (float)(☃xxxx + 1);
         GlStateManager.func_179109_b(17.0F / ☃xxxxx, (2.0F + ☃xxxxx / 1.5F) * ((float)Util.func_211177_b() % 800000.0F / 800000.0F), 0.0F);
         GlStateManager.func_179114_b((☃xxxxx * ☃xxxxx * 4321.0F + ☃xxxxx * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179152_a(4.5F - ☃xxxxx / 4.0F, 4.5F - ☃xxxxx / 4.0F, 1.0F);
         GlStateManager.func_179110_a(field_188202_i);
         GlStateManager.func_179110_a(field_188201_h);
         Tessellator ☃xxxxxx = Tessellator.func_178181_a();
         BufferBuilder ☃xxxxxxx = ☃xxxxxx.func_178180_c();
         ☃xxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         float ☃xxxxxxxx = (field_147527_e.nextFloat() * 0.5F + 0.1F) * ☃xxxxx;
         float ☃xxxxxxxxx = (field_147527_e.nextFloat() * 0.5F + 0.4F) * ☃xxxxx;
         float ☃xxxxxxxxxx = (field_147527_e.nextFloat() * 0.5F + 0.5F) * ☃xxxxx;
         if (☃.func_184313_a(EnumFacing.SOUTH)) {
            ☃xxxxxxx.func_181662_b(☃, ☃, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃ + 1.0, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃ + 1.0, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
         }

         if (☃.func_184313_a(EnumFacing.NORTH)) {
            ☃xxxxxxx.func_181662_b(☃, ☃ + 1.0, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃ + 1.0, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
         }

         if (☃.func_184313_a(EnumFacing.EAST)) {
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃ + 1.0, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃ + 1.0, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
         }

         if (☃.func_184313_a(EnumFacing.WEST)) {
            ☃xxxxxxx.func_181662_b(☃, ☃, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃ + 1.0, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃ + 1.0, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
         }

         if (☃.func_184313_a(EnumFacing.DOWN)) {
            ☃xxxxxxx.func_181662_b(☃, ☃, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
         }

         if (☃.func_184313_a(EnumFacing.UP)) {
            ☃xxxxxxx.func_181662_b(☃, ☃ + (double)☃xx, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃ + (double)☃xx, ☃ + 1.0).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃ + 1.0, ☃ + (double)☃xx, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
            ☃xxxxxxx.func_181662_b(☃, ☃ + (double)☃xx, ☃).func_181666_a(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 1.0F).func_181675_d();
         }

         ☃xxxxxx.func_78381_a();
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5888);
         this.func_147499_a(field_147529_c);
      }

      GlStateManager.func_179084_k();
      GlStateManager.func_179100_b(GlStateManager.TexGen.S);
      GlStateManager.func_179100_b(GlStateManager.TexGen.T);
      GlStateManager.func_179100_b(GlStateManager.TexGen.R);
      GlStateManager.func_179145_e();
      if (☃xxx) {
         Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
      }
   }

   protected int func_191286_a(double var1) {
      int ☃;
      if (☃ > 36864.0) {
         ☃ = 1;
      } else if (☃ > 25600.0) {
         ☃ = 3;
      } else if (☃ > 16384.0) {
         ☃ = 5;
      } else if (☃ > 9216.0) {
         ☃ = 7;
      } else if (☃ > 4096.0) {
         ☃ = 9;
      } else if (☃ > 1024.0) {
         ☃ = 11;
      } else if (☃ > 576.0) {
         ☃ = 13;
      } else if (☃ > 256.0) {
         ☃ = 14;
      } else {
         ☃ = 15;
      }

      return ☃;
   }

   protected float func_191287_c() {
      return 0.75F;
   }

   private FloatBuffer func_147525_a(float var1, float var2, float var3, float var4) {
      this.field_147528_b.clear();
      this.field_147528_b.put(☃).put(☃).put(☃).put(☃);
      this.field_147528_b.flip();
      return this.field_147528_b;
   }
}
