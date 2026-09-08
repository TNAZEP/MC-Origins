package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class DirectJoinServerScreen extends Screen {
   private static final Component ENTER_IP_LABEL = new TranslatableComponent("addServer.enterIp");
   private Button selectButton;
   private final ServerData serverData;
   private EditBox ipEdit;
   private final BooleanConsumer callback;
   private final Screen lastScreen;

   public DirectJoinServerScreen(Screen var1, BooleanConsumer var2, ServerData var3) {
      super(new TranslatableComponent("selectServer.direct"));
      this.lastScreen = â˜ƒ;
      this.serverData = â˜ƒ;
      this.callback = â˜ƒ;
   }

   @Override
   public void tick() {
      this.ipEdit.tick();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (!this.selectButton.active || this.getFocused() != this.ipEdit || â˜ƒ != 257 && â˜ƒ != 335) {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         this.onSelect();
         return true;
      }
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.selectButton = this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20, new TranslatableComponent("selectServer.select"), var1 -> this.onSelect())
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, CommonComponents.GUI_CANCEL, var1 -> this.callback.accept(false))
      );
      this.ipEdit = new EditBox(this.font, this.width / 2 - 100, 116, 200, 20, new TranslatableComponent("addServer.enterIp"));
      this.ipEdit.setMaxLength(128);
      this.ipEdit.setFocus(true);
      this.ipEdit.setValue(this.minecraft.options.lastMpIp);
      this.ipEdit.setResponder(var1 -> this.updateSelectButtonStatus());
      this.addWidget(this.ipEdit);
      this.setInitialFocus(this.ipEdit);
      this.updateSelectButtonStatus();
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.ipEdit.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.ipEdit.setValue(â˜ƒ);
   }

   private void onSelect() {
      this.serverData.ip = this.ipEdit.getValue();
      this.callback.accept(true);
   }

   @Override
   public void onClose() {
      this.minecraft.setScreen(this.lastScreen);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
      this.minecraft.options.lastMpIp = this.ipEdit.getValue();
      this.minecraft.options.save();
   }

   private void updateSelectButtonStatus() {
      this.selectButton.active = ServerAddress.isValidAddress(this.ipEdit.getValue());
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
      drawString(â˜ƒ, this.font, ENTER_IP_LABEL, this.width / 2 - 100, 100, 10526880);
      this.ipEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
