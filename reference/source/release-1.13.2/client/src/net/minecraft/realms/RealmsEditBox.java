package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.IGuiEventListener;

public class RealmsEditBox extends RealmsGuiEventListener {
   private final GuiTextField editBox;

   public RealmsEditBox(int var1, int var2, int var3, int var4, int var5) {
      this.editBox = new GuiTextField(☃, Minecraft.func_71410_x().field_71466_p, ☃, ☃, ☃, ☃);
   }

   public String getValue() {
      return this.editBox.func_146179_b();
   }

   public void tick() {
      this.editBox.func_146178_a();
   }

   public void setValue(String var1) {
      this.editBox.func_146180_a(☃);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      return this.editBox.charTyped(☃, ☃);
   }

   @Override
   public IGuiEventListener getProxy() {
      return this.editBox;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      return this.editBox.keyPressed(☃, ☃, ☃);
   }

   public boolean isFocused() {
      return this.editBox.func_146206_l();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.editBox.mouseClicked(☃, ☃, ☃);
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      return this.editBox.mouseReleased(☃, ☃, ☃);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      return this.editBox.mouseDragged(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean mouseScrolled(double var1) {
      return this.editBox.mouseScrolled(☃);
   }

   public void render(int var1, int var2, float var3) {
      this.editBox.func_195608_a(☃, ☃, ☃);
   }

   public void setMaxLength(int var1) {
      this.editBox.func_146203_f(☃);
   }

   public void setIsEditable(boolean var1) {
      this.editBox.func_146184_c(☃);
   }
}
