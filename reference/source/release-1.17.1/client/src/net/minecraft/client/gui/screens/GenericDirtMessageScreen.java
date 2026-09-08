package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

public class GenericDirtMessageScreen extends Screen {
   public GenericDirtMessageScreen(Component var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderDirtBackground(0);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 70, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
