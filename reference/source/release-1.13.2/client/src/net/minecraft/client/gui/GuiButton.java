package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public abstract class GuiButton extends Gui implements IGuiEventListener {
   protected static final ResourceLocation field_146122_a = new ResourceLocation("textures/gui/widgets.png");
   protected int field_146120_f = 200;
   protected int field_146121_g = 20;
   public int field_146128_h;
   public int field_146129_i;
   public String field_146126_j;
   public int field_146127_k;
   public boolean field_146124_l = true;
   public boolean field_146125_m = true;
   protected boolean field_146123_n;
   private boolean field_194832_o;

   public GuiButton(int var1, int var2, int var3, String var4) {
      this(☃, ☃, ☃, 200, 20, ☃);
   }

   public GuiButton(int var1, int var2, int var3, int var4, int var5, String var6) {
      this.field_146127_k = ☃;
      this.field_146128_h = ☃;
      this.field_146129_i = ☃;
      this.field_146120_f = ☃;
      this.field_146121_g = ☃;
      this.field_146126_j = ☃;
   }

   protected int func_146114_a(boolean var1) {
      int ☃ = 1;
      if (!this.field_146124_l) {
         ☃ = 0;
      } else if (☃) {
         ☃ = 2;
      }

      return ☃;
   }

   public void func_194828_a(int var1, int var2, float var3) {
      if (this.field_146125_m) {
         Minecraft ☃ = Minecraft.func_71410_x();
         FontRenderer ☃x = ☃.field_71466_p;
         ☃.func_110434_K().func_110577_a(field_146122_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = ☃ >= this.field_146128_h
            && ☃ >= this.field_146129_i
            && ☃ < this.field_146128_h + this.field_146120_f
            && ☃ < this.field_146129_i + this.field_146121_g;
         int ☃xx = this.func_146114_a(this.field_146123_n);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + ☃xx * 20, this.field_146120_f / 2, this.field_146121_g);
         this.func_73729_b(
            this.field_146128_h + this.field_146120_f / 2,
            this.field_146129_i,
            200 - this.field_146120_f / 2,
            46 + ☃xx * 20,
            this.field_146120_f / 2,
            this.field_146121_g
         );
         this.func_146119_b(☃, ☃, ☃);
         int ☃xxx = 14737632;
         if (!this.field_146124_l) {
            ☃xxx = 10526880;
         } else if (this.field_146123_n) {
            ☃xxx = 16777120;
         }

         this.func_73732_a(☃x, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, ☃xxx);
      }
   }

   protected void func_146119_b(Minecraft var1, int var2, int var3) {
   }

   public void func_194829_a(double var1, double var3) {
      this.field_194832_o = true;
   }

   public void func_194831_b(double var1, double var3) {
      this.field_194832_o = false;
   }

   protected void func_194827_a(double var1, double var3, double var5, double var7) {
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (☃ == 0) {
         boolean ☃ = this.func_199400_c(☃, ☃);
         if (☃) {
            this.func_146113_a(Minecraft.func_71410_x().func_147118_V());
            this.func_194829_a(☃, ☃);
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (☃ == 0) {
         this.func_194831_b(☃, ☃);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (☃ == 0) {
         this.func_194827_a(☃, ☃, ☃, ☃);
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_199400_c(double var1, double var3) {
      return this.field_146124_l
         && this.field_146125_m
         && ☃ >= (double)this.field_146128_h
         && ☃ >= (double)this.field_146129_i
         && ☃ < (double)(this.field_146128_h + this.field_146120_f)
         && ☃ < (double)(this.field_146129_i + this.field_146121_g);
   }

   public boolean func_146115_a() {
      return this.field_146123_n;
   }

   public void func_146111_b(int var1, int var2) {
   }

   public void func_146113_a(SoundHandler var1) {
      ☃.func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
   }

   public int func_146117_b() {
      return this.field_146120_f;
   }

   public void func_175211_a(int var1) {
      this.field_146120_f = ☃;
   }
}
