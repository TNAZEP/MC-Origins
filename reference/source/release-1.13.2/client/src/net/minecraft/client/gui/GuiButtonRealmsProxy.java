package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsButton;

public class GuiButtonRealmsProxy extends GuiButton {
   private final RealmsButton field_154318_o;

   public GuiButtonRealmsProxy(RealmsButton var1, int var2, int var3, int var4, String var5) {
      super(☃, ☃, ☃, ☃);
      this.field_154318_o = ☃;
   }

   public GuiButtonRealmsProxy(RealmsButton var1, int var2, int var3, int var4, String var5, int var6, int var7) {
      super(☃, ☃, ☃, ☃, ☃, ☃);
      this.field_154318_o = ☃;
   }

   public int func_207707_c() {
      return this.field_146127_k;
   }

   public boolean func_207710_d() {
      return this.field_146124_l;
   }

   public void func_207706_c(boolean var1) {
      this.field_146124_l = ☃;
   }

   public void func_207705_a(String var1) {
      super.field_146126_j = ☃;
   }

   @Override
   public int func_146117_b() {
      return super.func_146117_b();
   }

   public int func_207708_e() {
      return this.field_146129_i;
   }

   @Override
   public void func_194829_a(double var1, double var3) {
      this.field_154318_o.onClick(☃, ☃);
   }

   @Override
   public void func_194831_b(double var1, double var3) {
      this.field_154318_o.onRelease(☃, ☃);
   }

   @Override
   public void func_146119_b(Minecraft var1, int var2, int var3) {
      this.field_154318_o.renderBg(☃, ☃);
   }

   public RealmsButton func_154317_g() {
      return this.field_154318_o;
   }

   @Override
   public int func_146114_a(boolean var1) {
      return this.field_154318_o.getYImage(☃);
   }

   public int func_154312_c(boolean var1) {
      return super.func_146114_a(☃);
   }

   public int func_207709_g() {
      return this.field_146121_g;
   }
}
