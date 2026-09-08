package net.minecraft.realms;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class DisconnectedRealmsScreen extends RealmsScreen {
   private final Component reason;
   private MultiLineLabel message = MultiLineLabel.EMPTY;
   private final Screen parent;
   private int textHeight;

   public DisconnectedRealmsScreen(Screen var1, Component var2, Component var3) {
      super(â˜ƒ);
      this.parent = â˜ƒ;
      this.reason = â˜ƒ;
   }

   @Override
   public void init() {
      Minecraft â˜ƒ = Minecraft.getInstance();
      â˜ƒ.setConnectedToRealms(false);
      â˜ƒ.getClientPackSource().clearServerPack();
      this.message = MultiLineLabel.create(this.font, this.reason, this.width - 50);
      this.textHeight = this.message.getLineCount() * 9;
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + 9, 200, 20, CommonComponents.GUI_BACK, var2 -> â˜ƒ.setScreen(this.parent))
      );
   }

   @Override
   public Component getNarrationMessage() {
      return new TextComponent("").append(this.title).append(": ").append(this.reason);
   }

   @Override
   public void onClose() {
      Minecraft.getInstance().setScreen(this.parent);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 11184810);
      this.message.renderCentered(â˜ƒ, this.width / 2, this.height / 2 - this.textHeight / 2);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
