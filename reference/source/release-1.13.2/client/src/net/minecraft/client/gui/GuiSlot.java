package net.minecraft.client.gui;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

public abstract class GuiSlot extends GuiEventHandler {
   protected final Minecraft field_148161_k;
   protected int field_148155_a;
   protected int field_148158_l;
   protected int field_148153_b;
   protected int field_148154_c;
   protected int field_148151_d;
   protected int field_148152_e;
   protected final int field_148149_f;
   protected boolean field_148163_i = true;
   protected int field_148157_o = -2;
   protected double field_148169_q;
   protected int field_148168_r;
   protected long field_148167_s = Long.MIN_VALUE;
   protected boolean field_178041_q = true;
   protected boolean field_148166_t = true;
   protected boolean field_148165_u;
   protected int field_148160_j;
   private boolean field_195084_v;

   public GuiSlot(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_148161_k = ☃;
      this.field_148155_a = ☃;
      this.field_148158_l = ☃;
      this.field_148153_b = ☃;
      this.field_148154_c = ☃;
      this.field_148149_f = ☃;
      this.field_148152_e = 0;
      this.field_148151_d = ☃;
   }

   public void func_148122_a(int var1, int var2, int var3, int var4) {
      this.field_148155_a = ☃;
      this.field_148158_l = ☃;
      this.field_148153_b = ☃;
      this.field_148154_c = ☃;
      this.field_148152_e = 0;
      this.field_148151_d = ☃;
   }

   public void func_193651_b(boolean var1) {
      this.field_148166_t = ☃;
   }

   protected void func_148133_a(boolean var1, int var2) {
      this.field_148165_u = ☃;
      this.field_148160_j = ☃;
      if (!☃) {
         this.field_148160_j = 0;
      }
   }

   public boolean func_195082_l() {
      return this.field_178041_q;
   }

   protected abstract int func_148127_b();

   public void func_195080_b(int var1) {
   }

   @Override
   protected List<? extends IGuiEventListener> func_195074_b() {
      return Collections.emptyList();
   }

   protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
      return true;
   }

   protected abstract boolean func_148131_a(int var1);

   protected int func_148138_e() {
      return this.func_148127_b() * this.field_148149_f + this.field_148160_j;
   }

   protected abstract void func_148123_a();

   protected void func_192639_a(int var1, int var2, int var3, float var4) {
   }

   protected abstract void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7);

   protected void func_148129_a(int var1, int var2, Tessellator var3) {
   }

   protected void func_148132_a(int var1, int var2) {
   }

   protected void func_148142_b(int var1, int var2) {
   }

   public int func_195083_a(double var1, double var3) {
      int ☃ = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2;
      int ☃x = this.field_148152_e + this.field_148155_a / 2 + this.func_148139_c() / 2;
      int ☃xx = MathHelper.func_76128_c(☃ - (double)this.field_148153_b) - this.field_148160_j + (int)this.field_148169_q - 4;
      int ☃xxx = ☃xx / this.field_148149_f;
      return ☃ < (double)this.func_148137_d() && ☃ >= (double)☃ && ☃ <= (double)☃x && ☃xxx >= 0 && ☃xx >= 0 && ☃xxx < this.func_148127_b() ? ☃xxx : -1;
   }

   protected void func_148121_k() {
      this.field_148169_q = MathHelper.func_151237_a(this.field_148169_q, 0.0, (double)this.func_148135_f());
   }

   public int func_148135_f() {
      return Math.max(0, this.func_148138_e() - (this.field_148154_c - this.field_148153_b - 4));
   }

   public int func_148148_g() {
      return (int)this.field_148169_q;
   }

   public boolean func_195079_b(double var1, double var3) {
      return ☃ >= (double)this.field_148153_b && ☃ <= (double)this.field_148154_c && ☃ >= (double)this.field_148152_e && ☃ <= (double)this.field_148151_d;
   }

   public void func_148145_f(int var1) {
      this.field_148169_q += (double)☃;
      this.func_148121_k();
      this.field_148157_o = -2;
   }

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
         this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110325_k);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         float ☃xxxx = 32.0F;
         ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         ☃xxx.func_181662_b((double)this.field_148152_e, (double)this.field_148154_c, 0.0)
            .func_187315_a((double)((float)this.field_148152_e / 32.0F), (double)((float)(this.field_148154_c + (int)this.field_148169_q) / 32.0F))
            .func_181669_b(32, 32, 32, 255)
            .func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148151_d, (double)this.field_148154_c, 0.0)
            .func_187315_a((double)((float)this.field_148151_d / 32.0F), (double)((float)(this.field_148154_c + (int)this.field_148169_q) / 32.0F))
            .func_181669_b(32, 32, 32, 255)
            .func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148151_d, (double)this.field_148153_b, 0.0)
            .func_187315_a((double)((float)this.field_148151_d / 32.0F), (double)((float)(this.field_148153_b + (int)this.field_148169_q) / 32.0F))
            .func_181669_b(32, 32, 32, 255)
            .func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148152_e, (double)this.field_148153_b, 0.0)
            .func_187315_a((double)((float)this.field_148152_e / 32.0F), (double)((float)(this.field_148153_b + (int)this.field_148169_q) / 32.0F))
            .func_181669_b(32, 32, 32, 255)
            .func_181675_d();
         ☃xx.func_78381_a();
         int ☃xxxxx = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
         int ☃xxxxxx = this.field_148153_b + 4 - (int)this.field_148169_q;
         if (this.field_148165_u) {
            this.func_148129_a(☃xxxxx, ☃xxxxxx, ☃xx);
         }

         this.func_192638_a(☃xxxxx, ☃xxxxxx, ☃, ☃, ☃);
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
         int ☃ = 4;
         ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         ☃xxx.func_181662_b((double)this.field_148152_e, (double)(this.field_148153_b + 4), 0.0)
            .func_187315_a(0.0, 1.0)
            .func_181669_b(0, 0, 0, 0)
            .func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148151_d, (double)(this.field_148153_b + 4), 0.0)
            .func_187315_a(1.0, 1.0)
            .func_181669_b(0, 0, 0, 0)
            .func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148151_d, (double)this.field_148153_b, 0.0).func_187315_a(1.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148152_e, (double)this.field_148153_b, 0.0).func_187315_a(0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃xx.func_78381_a();
         ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         ☃xxx.func_181662_b((double)this.field_148152_e, (double)this.field_148154_c, 0.0).func_187315_a(0.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148151_d, (double)this.field_148154_c, 0.0).func_187315_a(1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148151_d, (double)(this.field_148154_c - 4), 0.0)
            .func_187315_a(1.0, 0.0)
            .func_181669_b(0, 0, 0, 0)
            .func_181675_d();
         ☃xxx.func_181662_b((double)this.field_148152_e, (double)(this.field_148154_c - 4), 0.0)
            .func_187315_a(0.0, 0.0)
            .func_181669_b(0, 0, 0, 0)
            .func_181675_d();
         ☃xx.func_78381_a();
         int ☃x = this.func_148135_f();
         if (☃x > 0) {
            int ☃xx = (int)((float)((this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b)) / (float)this.func_148138_e());
            ☃xx = MathHelper.func_76125_a(☃xx, 32, this.field_148154_c - this.field_148153_b - 8);
            int ☃xxx = (int)this.field_148169_q * (this.field_148154_c - this.field_148153_b - ☃xx) / ☃x + this.field_148153_b;
            if (☃xxx < this.field_148153_b) {
               ☃xxx = this.field_148153_b;
            }

            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xxx.func_181662_b((double)☃, (double)this.field_148154_c, 0.0).func_187315_a(0.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)this.field_148154_c, 0.0).func_187315_a(1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)this.field_148153_b, 0.0).func_187315_a(1.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃, (double)this.field_148153_b, 0.0).func_187315_a(0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xx.func_78381_a();
            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xxx.func_181662_b((double)☃, (double)(☃xxx + ☃xx), 0.0).func_187315_a(0.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)(☃xxx + ☃xx), 0.0).func_187315_a(1.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃x, (double)☃xxx, 0.0).func_187315_a(1.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃, (double)☃xxx, 0.0).func_187315_a(0.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xx.func_78381_a();
            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xxx.func_181662_b((double)☃, (double)(☃xxx + ☃xx - 1), 0.0).func_187315_a(0.0, 1.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xxx.func_181662_b((double)(☃x - 1), (double)(☃xxx + ☃xx - 1), 0.0).func_187315_a(1.0, 1.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xxx.func_181662_b((double)(☃x - 1), (double)☃xxx, 0.0).func_187315_a(1.0, 0.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xxx.func_181662_b((double)☃, (double)☃xxx, 0.0).func_187315_a(0.0, 0.0).func_181669_b(192, 192, 192, 255).func_181675_d();
            ☃xx.func_78381_a();
         }

         this.func_148142_b(☃, ☃);
         GlStateManager.func_179098_w();
         GlStateManager.func_179103_j(7424);
         GlStateManager.func_179141_d();
         GlStateManager.func_179084_k();
      }
   }

   protected void func_195077_a(double var1, double var3, int var5) {
      this.field_195084_v = ☃ == 0 && ☃ >= (double)this.func_148137_d() && ☃ < (double)(this.func_148137_d() + 6);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      this.func_195077_a(☃, ☃, ☃);
      if (this.func_195082_l() && this.func_195079_b(☃, ☃)) {
         int ☃ = this.func_195083_a(☃, ☃);
         if (☃ == -1 && ☃ == 0) {
            this.func_148132_a(
               (int)(☃ - (double)(this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2)),
               (int)(☃ - (double)this.field_148153_b) + (int)this.field_148169_q - 4
            );
            return true;
         } else if (☃ != -1 && this.func_195078_a(☃, ☃, ☃, ☃)) {
            if (this.func_195074_b().size() > ☃) {
               this.func_195073_a((IGuiEventListener)this.func_195074_b().get(☃));
            }

            this.func_195072_d(true);
            this.func_195080_b(☃);
            return true;
         } else {
            return this.field_195084_v;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (this.getFocused() != null) {
         this.getFocused().mouseReleased(☃, ☃, ☃);
      }

      this.func_195074_b().forEach(var5x -> var5x.mouseReleased(☃, ☃, ☃));
      return false;
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (super.mouseDragged(☃, ☃, ☃, ☃, ☃)) {
         return true;
      } else if (this.func_195082_l() && ☃ == 0 && this.field_195084_v) {
         if (☃ < (double)this.field_148153_b) {
            this.field_148169_q = 0.0;
         } else if (☃ > (double)this.field_148154_c) {
            this.field_148169_q = (double)this.func_148135_f();
         } else {
            double ☃ = (double)this.func_148135_f();
            if (☃ < 1.0) {
               ☃ = 1.0;
            }

            int ☃ = (int)((float)((this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b)) / (float)this.func_148138_e());
            ☃ = MathHelper.func_76125_a(☃, 32, this.field_148154_c - this.field_148153_b - 8);
            double ☃x = ☃ / (double)(this.field_148154_c - this.field_148153_b - ☃);
            if (☃x < 1.0) {
               ☃x = 1.0;
            }

            this.field_148169_q += ☃ * ☃x;
            this.func_148121_k();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseScrolled(double var1) {
      if (!this.func_195082_l()) {
         return false;
      } else {
         this.field_148169_q -= ☃ * (double)this.field_148149_f / 2.0;
         return true;
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      return !this.func_195082_l() ? false : super.keyPressed(☃, ☃, ☃);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      return !this.func_195082_l() ? false : super.charTyped(☃, ☃);
   }

   public int func_148139_c() {
      return 220;
   }

   protected void func_192638_a(int var1, int var2, int var3, int var4, float var5) {
      int ☃ = this.func_148127_b();
      Tessellator ☃x = Tessellator.func_178181_a();
      BufferBuilder ☃xx = ☃x.func_178180_c();

      for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
         int ☃xxxx = ☃ + ☃xxx * this.field_148149_f + this.field_148160_j;
         int ☃xxxxx = this.field_148149_f - 4;
         if (☃xxxx > this.field_148154_c || ☃xxxx + ☃xxxxx < this.field_148153_b) {
            this.func_192639_a(☃xxx, ☃, ☃xxxx, ☃);
         }

         if (this.field_148166_t && this.func_148131_a(☃xxx)) {
            int ☃xxxx = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2;
            int ☃xxxxx = this.field_148152_e + this.field_148155_a / 2 + this.func_148139_c() / 2;
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.func_179090_x();
            ☃xx.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            ☃xx.func_181662_b((double)☃xxxx, (double)(☃xxxx + ☃xxxxx + 2), 0.0).func_187315_a(0.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xx.func_181662_b((double)☃xxxxx, (double)(☃xxxx + ☃xxxxx + 2), 0.0).func_187315_a(1.0, 1.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xx.func_181662_b((double)☃xxxxx, (double)(☃xxxx - 2), 0.0).func_187315_a(1.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xx.func_181662_b((double)☃xxxx, (double)(☃xxxx - 2), 0.0).func_187315_a(0.0, 0.0).func_181669_b(128, 128, 128, 255).func_181675_d();
            ☃xx.func_181662_b((double)(☃xxxx + 1), (double)(☃xxxx + ☃xxxxx + 1), 0.0).func_187315_a(0.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xx.func_181662_b((double)(☃xxxxx - 1), (double)(☃xxxx + ☃xxxxx + 1), 0.0).func_187315_a(1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xx.func_181662_b((double)(☃xxxxx - 1), (double)(☃xxxx - 1), 0.0).func_187315_a(1.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃xx.func_181662_b((double)(☃xxxx + 1), (double)(☃xxxx - 1), 0.0).func_187315_a(0.0, 0.0).func_181669_b(0, 0, 0, 255).func_181675_d();
            ☃x.func_78381_a();
            GlStateManager.func_179098_w();
         }

         this.func_192637_a(☃xxx, ☃, ☃xxxx, ☃xxxxx, ☃, ☃, ☃);
      }
   }

   protected int func_148137_d() {
      return this.field_148155_a / 2 + 124;
   }

   protected void func_148136_c(int var1, int var2, int var3, int var4) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110325_k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      float ☃xx = 32.0F;
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      ☃x.func_181662_b((double)this.field_148152_e, (double)☃, 0.0).func_187315_a(0.0, (double)((float)☃ / 32.0F)).func_181669_b(64, 64, 64, ☃).func_181675_d();
      ☃x.func_181662_b((double)(this.field_148152_e + this.field_148155_a), (double)☃, 0.0)
         .func_187315_a((double)((float)this.field_148155_a / 32.0F), (double)((float)☃ / 32.0F))
         .func_181669_b(64, 64, 64, ☃)
         .func_181675_d();
      ☃x.func_181662_b((double)(this.field_148152_e + this.field_148155_a), (double)☃, 0.0)
         .func_187315_a((double)((float)this.field_148155_a / 32.0F), (double)((float)☃ / 32.0F))
         .func_181669_b(64, 64, 64, ☃)
         .func_181675_d();
      ☃x.func_181662_b((double)this.field_148152_e, (double)☃, 0.0).func_187315_a(0.0, (double)((float)☃ / 32.0F)).func_181669_b(64, 64, 64, ☃).func_181675_d();
      ☃.func_78381_a();
   }

   public void func_148140_g(int var1) {
      this.field_148152_e = ☃;
      this.field_148151_d = ☃ + this.field_148155_a;
   }

   public int func_148146_j() {
      return this.field_148149_f;
   }
}
