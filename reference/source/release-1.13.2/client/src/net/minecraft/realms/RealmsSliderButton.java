package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public abstract class RealmsSliderButton extends RealmsButton {
   public double value = 1.0;
   public boolean sliding;
   private final double minValue;
   private final double maxValue;
   private int steps;

   public RealmsSliderButton(int var1, int var2, int var3, int var4, int var5, int var6) {
      this(☃, ☃, ☃, ☃, ☃, 0, 1.0, (double)☃);
   }

   public RealmsSliderButton(int var1, int var2, int var3, int var4, int var5, int var6, double var7, double var9) {
      super(☃, ☃, ☃, ☃, 20, "");
      this.minValue = ☃;
      this.maxValue = ☃;
      this.value = this.toPct((double)☃);
      this.getProxy().field_146126_j = this.getMessage();
   }

   public String getMessage() {
      return "";
   }

   public double toPct(double var1) {
      return MathHelper.func_151237_a((this.clamp(☃) - this.minValue) / (this.maxValue - this.minValue), 0.0, 1.0);
   }

   public double toValue(double var1) {
      return this.clamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.func_151237_a(☃, 0.0, 1.0));
   }

   public double clamp(double var1) {
      ☃ = this.clampSteps(☃);
      return MathHelper.func_151237_a(☃, this.minValue, this.maxValue);
   }

   protected double clampSteps(double var1) {
      if (this.steps > 0) {
         ☃ = (double)((long)this.steps * Math.round(☃ / (double)this.steps));
      }

      return ☃;
   }

   @Override
   public int getYImage(boolean var1) {
      return 0;
   }

   @Override
   public void renderBg(int var1, int var2) {
      if (this.getProxy().field_146125_m) {
         if (this.sliding) {
            this.value = (double)((float)(☃ - (this.getProxy().field_146128_h + 4)) / (float)(this.getProxy().func_146117_b() - 8));
            this.value = MathHelper.func_151237_a(this.value, 0.0, 1.0);
            double ☃ = this.toValue(this.value);
            this.clicked(☃);
            this.value = this.toPct(☃);
            this.getProxy().field_146126_j = this.getMessage();
         }

         Minecraft.func_71410_x().func_110434_K().func_110577_a(WIDGETS_LOCATION);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.blit(
            this.getProxy().field_146128_h + (int)(this.value * (double)(this.getProxy().func_146117_b() - 8)), this.getProxy().field_146129_i, 0, 66, 4, 20
         );
         this.blit(
            this.getProxy().field_146128_h + (int)(this.value * (double)(this.getProxy().func_146117_b() - 8)) + 4,
            this.getProxy().field_146129_i,
            196,
            66,
            4,
            20
         );
      }
   }

   @Override
   public void onClick(double var1, double var3) {
      this.value = (☃ - (double)(this.getProxy().field_146128_h + 4)) / (double)(this.getProxy().func_146117_b() - 8);
      this.value = MathHelper.func_151237_a(this.value, 0.0, 1.0);
      this.clicked(this.toValue(this.value));
      this.getProxy().field_146126_j = this.getMessage();
      this.sliding = true;
   }

   public void clicked(double var1) {
   }

   @Override
   public void onRelease(double var1, double var3) {
      this.sliding = false;
   }
}
