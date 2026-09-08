package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class GuiSpectator extends Gui implements ISpectatorMenuRecipient {
   private static final ResourceLocation field_175267_f = new ResourceLocation("textures/gui/widgets.png");
   public static final ResourceLocation field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
   private final Minecraft field_175268_g;
   private long field_175270_h;
   private SpectatorMenu field_175271_i;

   public GuiSpectator(Minecraft var1) {
      this.field_175268_g = ☃;
   }

   public void func_175260_a(int var1) {
      this.field_175270_h = Util.func_211177_b();
      if (this.field_175271_i != null) {
         this.field_175271_i.func_178644_b(☃);
      } else {
         this.field_175271_i = new SpectatorMenu(this);
      }
   }

   private float func_175265_c() {
      long ☃ = this.field_175270_h - Util.func_211177_b() + 5000L;
      return MathHelper.func_76131_a((float)☃ / 2000.0F, 0.0F, 1.0F);
   }

   public void func_195622_a(float var1) {
      if (this.field_175271_i != null) {
         float ☃ = this.func_175265_c();
         if (☃ <= 0.0F) {
            this.field_175271_i.func_178641_d();
         } else {
            int ☃ = this.field_175268_g.field_195558_d.func_198107_o() / 2;
            float ☃x = this.field_73735_i;
            this.field_73735_i = -90.0F;
            float ☃xx = (float)this.field_175268_g.field_195558_d.func_198087_p() - 22.0F * ☃;
            SpectatorDetails ☃xxx = this.field_175271_i.func_178646_f();
            this.func_195624_a(☃, ☃, ☃xx, ☃xxx);
            this.field_73735_i = ☃x;
         }
      }
   }

   protected void func_195624_a(float var1, int var2, float var3, SpectatorDetails var4) {
      GlStateManager.func_179091_B();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, ☃);
      this.field_175268_g.func_110434_K().func_110577_a(field_175267_f);
      this.func_175174_a((float)(☃ - 91), ☃, 0, 0, 182, 22);
      if (☃.func_178681_b() >= 0) {
         this.func_175174_a((float)(☃ - 91 - 1 + ☃.func_178681_b() * 20), ☃ - 1.0F, 0, 22, 24, 22);
      }

      RenderHelper.func_74520_c();

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_175266_a(☃, this.field_175268_g.field_195558_d.func_198107_o() / 2 - 90 + ☃ * 20 + 2, ☃ + 3.0F, ☃, ☃.func_178680_a(☃));
      }

      RenderHelper.func_74518_a();
      GlStateManager.func_179101_C();
      GlStateManager.func_179084_k();
   }

   private void func_175266_a(int var1, int var2, float var3, float var4, ISpectatorMenuObject var5) {
      this.field_175268_g.func_110434_K().func_110577_a(field_175269_a);
      if (☃ != SpectatorMenu.field_178657_a) {
         int ☃ = (int)(☃ * 255.0F);
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃, ☃, 0.0F);
         float ☃x = ☃.func_178662_A_() ? 1.0F : 0.25F;
         GlStateManager.func_179131_c(☃x, ☃x, ☃x, ☃);
         ☃.func_178663_a(☃x, ☃);
         GlStateManager.func_179121_F();
         String ☃xx = String.valueOf(this.field_175268_g.field_71474_y.field_151456_ac[☃].func_197978_k());
         if (☃ > 3 && ☃.func_178662_A_()) {
            this.field_175268_g
               .field_71466_p
               .func_175063_a(☃xx, (float)(☃ + 19 - 2 - this.field_175268_g.field_71466_p.func_78256_a(☃xx)), ☃ + 6.0F + 3.0F, 16777215 + (☃ << 24));
         }
      }
   }

   public void func_195623_a() {
      int ☃ = (int)(this.func_175265_c() * 255.0F);
      if (☃ > 3 && this.field_175271_i != null) {
         ISpectatorMenuObject ☃x = this.field_175271_i.func_178645_b();
         String ☃xx = ☃x == SpectatorMenu.field_178657_a
            ? this.field_175271_i.func_178650_c().func_178670_b().func_150254_d()
            : ☃x.func_178664_z_().func_150254_d();
         if (☃xx != null) {
            int ☃xxx = (this.field_175268_g.field_195558_d.func_198107_o() - this.field_175268_g.field_71466_p.func_78256_a(☃xx)) / 2;
            int ☃xxxx = this.field_175268_g.field_195558_d.func_198087_p() - 35;
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(
               GlStateManager.SourceFactor.SRC_ALPHA,
               GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
               GlStateManager.SourceFactor.ONE,
               GlStateManager.DestFactor.ZERO
            );
            this.field_175268_g.field_71466_p.func_175063_a(☃xx, (float)☃xxx, (float)☃xxxx, 16777215 + (☃ << 24));
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
         }
      }
   }

   @Override
   public void func_175257_a(SpectatorMenu var1) {
      this.field_175271_i = null;
      this.field_175270_h = 0L;
   }

   public boolean func_175262_a() {
      return this.field_175271_i != null;
   }

   public void func_195621_a(double var1) {
      int ☃ = this.field_175271_i.func_178648_e() + (int)☃;

      while(
         ☃ >= 0 && ☃ <= 8 && (this.field_175271_i.func_178643_a(☃) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(☃).func_178662_A_())
      ) {
         ☃ = (int)((double)☃ + ☃);
      }

      if (☃ >= 0 && ☃ <= 8) {
         this.field_175271_i.func_178644_b(☃);
         this.field_175270_h = Util.func_211177_b();
      }
   }

   public void func_175261_b() {
      this.field_175270_h = Util.func_211177_b();
      if (this.func_175262_a()) {
         int ☃ = this.field_175271_i.func_178648_e();
         if (☃ != -1) {
            this.field_175271_i.func_178644_b(☃);
         }
      } else {
         this.field_175271_i = new SpectatorMenu(this);
      }
   }
}
