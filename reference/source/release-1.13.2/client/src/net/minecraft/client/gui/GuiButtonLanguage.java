package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public abstract class GuiButtonLanguage extends GuiButton {
   public GuiButtonLanguage(int var1, int var2, int var3) {
      super(☃, ☃, ☃, 20, 20, "");
   }

   @Override
   public void func_194828_a(int var1, int var2, float var3) {
      if (this.field_146125_m) {
         Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiButton.field_146122_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         boolean ☃ = ☃ >= this.field_146128_h
            && ☃ >= this.field_146129_i
            && ☃ < this.field_146128_h + this.field_146120_f
            && ☃ < this.field_146129_i + this.field_146121_g;
         int ☃x = 106;
         if (☃) {
            ☃x += this.field_146121_g;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, ☃x, this.field_146120_f, this.field_146121_g);
      }
   }
}
