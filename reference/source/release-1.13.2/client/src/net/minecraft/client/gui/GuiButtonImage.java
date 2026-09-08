package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public abstract class GuiButtonImage extends GuiButton {
   private final ResourceLocation field_191750_o;
   private final int field_191747_p;
   private final int field_191748_q;
   private final int field_191749_r;

   public GuiButtonImage(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, ResourceLocation var9) {
      super(☃, ☃, ☃, ☃, ☃, "");
      this.field_191747_p = ☃;
      this.field_191748_q = ☃;
      this.field_191749_r = ☃;
      this.field_191750_o = ☃;
   }

   public void func_191746_c(int var1, int var2) {
      this.field_146128_h = ☃;
      this.field_146129_i = ☃;
   }

   @Override
   public void func_194828_a(int var1, int var2, float var3) {
      if (this.field_146125_m) {
         this.field_146123_n = ☃ >= this.field_146128_h
            && ☃ >= this.field_146129_i
            && ☃ < this.field_146128_h + this.field_146120_f
            && ☃ < this.field_146129_i + this.field_146121_g;
         Minecraft ☃ = Minecraft.func_71410_x();
         ☃.func_110434_K().func_110577_a(this.field_191750_o);
         GlStateManager.func_179097_i();
         int ☃x = this.field_191748_q;
         if (this.field_146123_n) {
            ☃x += this.field_191749_r;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, this.field_191747_p, ☃x, this.field_146120_f, this.field_146121_g);
         GlStateManager.func_179126_j();
      }
   }
}
