package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class DisconnectedScreen extends Screen {
   private final Component reason;
   private MultiLineLabel message = MultiLineLabel.EMPTY;
   private final Screen parent;
   private int textHeight;

   public DisconnectedScreen(Screen var1, Component var2, Component var3) {
      super(â˜ƒ);
      this.parent = â˜ƒ;
      this.reason = â˜ƒ;
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   protected void init() {
      this.message = MultiLineLabel.create(this.font, this.reason, this.width - 50);
      this.textHeight = this.message.getLineCount() * 9;
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100,
            Math.min(this.height / 2 + this.textHeight / 2 + 9, this.height - 30),
            200,
            20,
            new TranslatableComponent("gui.toMenu"),
            var1 -> this.minecraft.setScreen(this.parent)
         )
      );
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 11184810);
      this.message.renderCentered(â˜ƒ, this.width / 2, this.height / 2 - this.textHeight / 2);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
