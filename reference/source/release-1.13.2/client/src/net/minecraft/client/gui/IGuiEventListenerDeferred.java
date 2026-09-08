package net.minecraft.client.gui;

import javax.annotation.Nullable;

public interface IGuiEventListenerDeferred extends IGuiEventListener {
   @Nullable
   IGuiEventListener getFocused();

   @Override
   default boolean mouseClicked(double var1, double var3, int var5) {
      return this.getFocused() != null && this.getFocused().mouseClicked(☃, ☃, ☃);
   }

   @Override
   default boolean mouseReleased(double var1, double var3, int var5) {
      return this.getFocused() != null && this.getFocused().mouseReleased(☃, ☃, ☃);
   }

   @Override
   default boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      return this.getFocused() != null && this.getFocused().mouseDragged(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   default boolean mouseScrolled(double var1) {
      return this.getFocused() != null && this.getFocused().mouseScrolled(☃);
   }

   @Override
   default boolean keyPressed(int var1, int var2, int var3) {
      return this.getFocused() != null && this.getFocused().keyPressed(☃, ☃, ☃);
   }

   @Override
   default boolean keyReleased(int var1, int var2, int var3) {
      return this.getFocused() != null && this.getFocused().keyReleased(☃, ☃, ☃);
   }

   @Override
   default boolean charTyped(char var1, int var2) {
      return this.getFocused() != null && this.getFocused().charTyped(☃, ☃);
   }
}
