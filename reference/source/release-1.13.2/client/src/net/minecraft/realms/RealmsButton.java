package net.minecraft.realms;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonRealmsProxy;
import net.minecraft.util.ResourceLocation;

public abstract class RealmsButton {
   protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
   private final GuiButtonRealmsProxy proxy;

   public RealmsButton(int var1, int var2, int var3, String var4) {
      this.proxy = new GuiButtonRealmsProxy(this, ☃, ☃, ☃, ☃) {
         @Override
         public void func_194829_a(double var1, double var3) {
            RealmsButton.this.onClick(☃, ☃);
         }
      };
   }

   public RealmsButton(int var1, int var2, int var3, int var4, int var5, String var6) {
      this.proxy = new GuiButtonRealmsProxy(this, ☃, ☃, ☃, ☃, ☃, ☃) {
         @Override
         public void func_194829_a(double var1, double var3) {
            RealmsButton.this.onClick(☃, ☃);
         }
      };
   }

   public GuiButton getProxy() {
      return this.proxy;
   }

   public int id() {
      return this.proxy.func_207707_c();
   }

   public boolean active() {
      return this.proxy.func_207710_d();
   }

   public void active(boolean var1) {
      this.proxy.func_207706_c(☃);
   }

   public void msg(String var1) {
      this.proxy.func_207705_a(☃);
   }

   public int getWidth() {
      return this.proxy.func_146117_b();
   }

   public int getHeight() {
      return this.proxy.func_207709_g();
   }

   public int y() {
      return this.proxy.func_207708_e();
   }

   public void render(int var1, int var2, float var3) {
      this.proxy.func_194828_a(☃, ☃, ☃);
   }

   public void blit(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.func_73729_b(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void renderBg(int var1, int var2) {
   }

   public int getYImage(boolean var1) {
      return this.proxy.func_154312_c(☃);
   }

   public abstract void onClick(double var1, double var3);

   public void onRelease(double var1, double var3) {
   }
}
