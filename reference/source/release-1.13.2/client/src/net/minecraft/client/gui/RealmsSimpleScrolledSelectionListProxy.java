package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.realms.RealmsSimpleScrolledSelectionList;
import net.minecraft.util.math.MathHelper;

public class RealmsSimpleScrolledSelectionListProxy extends GuiSlot {
   private final RealmsSimpleScrolledSelectionList field_207727_v;

   public RealmsSimpleScrolledSelectionListProxy(RealmsSimpleScrolledSelectionList var1, int var2, int var3, int var4, int var5, int var6) {
      super(Minecraft.func_71410_x(), ☃, ☃, ☃, ☃, ☃);
      this.field_207727_v = ☃;
   }

   @Override
   protected int func_148127_b() {
      return this.field_207727_v.getItemCount();
   }

   @Override
   protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
      return this.field_207727_v.selectItem(☃, ☃, ☃, ☃);
   }

   @Override
   protected boolean func_148131_a(int var1) {
      return this.field_207727_v.isSelectedItem(☃);
   }

   @Override
   protected void func_148123_a() {
      this.field_207727_v.renderBackground();
   }

   @Override
   protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      this.field_207727_v.renderItem(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public int func_207726_c() {
      return this.field_148155_a;
   }

   @Override
   protected int func_148138_e() {
      return this.field_207727_v.getMaxPosition();
   }

   @Override
   protected int func_148137_d() {
      return this.field_207727_v.getScrollbarPosition();
   }

   @Override
   public void func_148128_a(int var1, int var2, float var3) {
      if (this.field_178041_q) {
         this.func_148123_a();
         int ☃ = this.func_148137_d();
         int ☃x = ☃ + 6;
         this.func_148121_k();
         GlStateManager.func_179140_f();
         GlStateManager.func_179106_n();
         Tessellator ☃xx = Tessellator.func_178181_a();
         BufferBuilder ☃xxx = ☃xx.func_178180_c();
         int ☃xxxx = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
         int ☃xxxxx = this.field_148153_b + 4 - (int)this.field_148169_q;
         if (this.field_148165_u) {
            this.func_148129_a(☃xxxx, ☃xxxxx, ☃xx);
         }

         this.func_192638_a(☃xxxx, ☃xxxxx, ☃, ☃, ☃);
         GlStateManager.func_179097_i();
         this.func_148136_c(0, this.field_148153_b, 255, 255);
         this.func_148136_c(this.field_148154_c, this.field_148158_l, 255, 255);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
         );
         GlStateManager.func_179118_c();
         GlStateManager.func_179103_j(7425);
         GlStateManager.func_179090_x();
         int ☃ = this.func_148135_f();
         if (☃ > 0) {
            int ☃x = (this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b) / this.func_148138_e();
            ☃x = MathHelper.func_76125_a(☃x, 32, this.field_148154_c - this.field_148153_b - 8);
            int ☃xx = (int)this.field_148169_q * (this.field_148154_c - this.field_148153_b - ☃x) / ☃ + this.field_148153_b;
            if (☃xx < this.field_148153_b) {
               ☃xx = this.field_148153_b;
            }

            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xxx.func_181662_b((double)☃, (double)this.field_148154_c, 0.0).func_187315_a(0.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)this.field_148154_c, 0.0).func_187315_a(1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)this.field_148153_b, 0.0).func_187315_a(1.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃, (double)this.field_148153_b, 0.0).func_187315_a(0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xx.func_78381_a();
            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xxx.func_181662_b((double)☃, (double)(☃xx + ☃x), 0.0).func_187315_a(0.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)(☃xx + ☃x), 0.0).func_187315_a(1.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)☃xx, 0.0).func_187315_a(1.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃, (double)☃xx, 0.0).func_187315_a(0.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xx.func_78381_a();
            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xxx.func_181662_b((double)☃, (double)(☃xx + ☃x - 1), 0.0).func_187315_a(0.0, 1.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xxx.func_181662_b((double)(☃x - 1), (double)(☃xx + ☃x - 1), 0.0).func_187315_a(1.0, 1.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xxx.func_181662_b((double)(☃x - 1), (double)☃xx, 0.0).func_187315_a(1.0, 0.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃, (double)☃xx, 0.0).func_187315_a(0.0, 0.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xx.func_78381_a();
         }

         this.func_148142_b(☃, ☃);
         GlStateManager.func_179098_w();
         GlStateManager.func_179103_j(7424);
         GlStateManager.func_179141_d();
         GlStateManager.func_179084_k();
      }
   }

   @Override
   public boolean mouseScrolled(double var1) {
      return this.field_207727_v.mouseScrolled(☃) ? true : super.mouseScrolled(☃);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.field_207727_v.mouseClicked(☃, ☃, ☃) ? true : super.mouseClicked(☃, ☃, ☃);
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      return this.field_207727_v.mouseReleased(☃, ☃, ☃);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      return this.field_207727_v.mouseDragged(☃, ☃, ☃, ☃, ☃);
   }
}
