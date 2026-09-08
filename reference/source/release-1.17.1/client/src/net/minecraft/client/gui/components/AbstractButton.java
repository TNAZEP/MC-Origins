package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public abstract class AbstractButton extends AbstractWidget {
   public AbstractButton(int var1, int var2, int var3, int var4, Component var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public abstract void onPress();

   @Override
   public void onClick(double var1, double var3) {
      this.onPress();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (!this.active || !this.visible) {
         return false;
      } else if (â˜ƒ != 257 && â˜ƒ != 32 && â˜ƒ != 335) {
         return false;
      } else {
         this.playDownSound(Minecraft.getInstance().getSoundManager());
         this.onPress();
         return true;
      }
   }
}
