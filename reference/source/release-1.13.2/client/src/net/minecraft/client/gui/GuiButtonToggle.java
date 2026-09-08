package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonToggle extends GuiButton {
   protected ResourceLocation field_191760_o;
   protected boolean field_191755_p;
   protected int field_191756_q;
   protected int field_191757_r;
   protected int field_191758_s;
   protected int field_191759_t;

   public GuiButtonToggle(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      super(☃, ☃, ☃, ☃, ☃, "");
      this.field_191755_p = ☃;
   }

   public void func_191751_a(int var1, int var2, int var3, int var4, ResourceLocation var5) {
      this.field_191756_q = ☃;
      this.field_191757_r = ☃;
      this.field_191758_s = ☃;
      this.field_191759_t = ☃;
      this.field_191760_o = ☃;
   }

   public void func_191753_b(boolean var1) {
      this.field_191755_p = ☃;
   }

   public boolean func_191754_c() {
      return this.field_191755_p;
   }

   public void func_191752_c(int var1, int var2) {
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
         ☃.func_110434_K().func_110577_a(this.field_191760_o);
         GlStateManager.func_179097_i();
         int ☃x = this.field_191756_q;
         int ☃xx = this.field_191757_r;
         if (this.field_191755_p) {
            ☃x += this.field_191758_s;
         }

         if (this.field_146123_n) {
            ☃xx += this.field_191759_t;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, ☃x, ☃xx, this.field_146120_f, this.field_146121_g);
         GlStateManager.func_179126_j();
      }
   }
}
