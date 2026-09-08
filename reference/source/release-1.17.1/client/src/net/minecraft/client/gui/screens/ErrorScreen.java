package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ErrorScreen extends Screen {
   private final Component message;

   public ErrorScreen(Component var1, Component var2) {
      super(â˜ƒ);
      this.message = â˜ƒ;
   }

   @Override
   protected void init() {
      super.init();
      this.addRenderableWidget(new Button(this.width / 2 - 100, 140, 200, 20, CommonComponents.GUI_CANCEL, var1 -> this.minecraft.setScreen(null)));
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.fillGradient(â˜ƒ, 0, 0, this.width, this.height, -12574688, -11530224);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 90, 16777215);
      drawCenteredString(â˜ƒ, this.font, this.message, this.width / 2, 110, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }
}
