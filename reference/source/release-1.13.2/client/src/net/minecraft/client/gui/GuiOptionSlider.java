package net.minecraft.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class GuiOptionSlider extends GuiButton {
   private double field_146134_p = 1.0;
   public boolean field_146135_o;
   private final GameSettings.Options field_146133_q;
   private final double field_146132_r;
   private final double field_146131_s;

   public GuiOptionSlider(int var1, int var2, int var3, GameSettings.Options var4) {
      this(☃, ☃, ☃, ☃, 0.0, 1.0);
   }

   public GuiOptionSlider(int var1, int var2, int var3, GameSettings.Options var4, double var5, double var7) {
      this(☃, ☃, ☃, 150, 20, ☃, ☃, ☃);
   }

   public GuiOptionSlider(int var1, int var2, int var3, int var4, int var5, GameSettings.Options var6, double var7, double var9) {
      super(☃, ☃, ☃, ☃, ☃, "");
      this.field_146133_q = ☃;
      this.field_146132_r = ☃;
      this.field_146131_s = ☃;
      Minecraft ☃ = Minecraft.func_71410_x();
      this.field_146134_p = ☃.func_198008_a(☃.field_71474_y.func_198015_a(☃));
      this.field_146126_j = ☃.field_71474_y.func_74297_c(☃);
   }

   @Override
   protected int func_146114_a(boolean var1) {
      return 0;
   }

   @Override
   protected void func_146119_b(Minecraft var1, int var2, int var3) {
      if (this.field_146125_m) {
         if (this.field_146135_o) {
            this.field_146134_p = (double)((float)(☃ - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8));
            this.field_146134_p = MathHelper.func_151237_a(this.field_146134_p, 0.0, 1.0);
         }

         if (this.field_146135_o || this.field_146133_q == GameSettings.Options.FULLSCREEN_RESOLUTION) {
            double ☃ = this.field_146133_q.func_198004_b(this.field_146134_p);
            ☃.field_71474_y.func_198016_a(this.field_146133_q, ☃);
            this.field_146134_p = this.field_146133_q.func_198008_a(☃);
            this.field_146126_j = ☃.field_71474_y.func_74297_c(this.field_146133_q);
         }

         ☃.func_110434_K().func_110577_a(field_146122_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_146128_h + (int)(this.field_146134_p * (double)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
         this.func_73729_b(this.field_146128_h + (int)(this.field_146134_p * (double)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
      }
   }

   @Override
   public final void func_194829_a(double var1, double var3) {
      this.field_146134_p = (☃ - (double)(this.field_146128_h + 4)) / (double)(this.field_146120_f - 8);
      this.field_146134_p = MathHelper.func_151237_a(this.field_146134_p, 0.0, 1.0);
      Minecraft ☃ = Minecraft.func_71410_x();
      ☃.field_71474_y.func_198016_a(this.field_146133_q, this.field_146133_q.func_198004_b(this.field_146134_p));
      this.field_146126_j = ☃.field_71474_y.func_74297_c(this.field_146133_q);
      this.field_146135_o = true;
   }

   @Override
   public void func_194831_b(double var1, double var3) {
      this.field_146135_o = false;
   }
}
