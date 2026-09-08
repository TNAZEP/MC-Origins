package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;

public class RealmsGenericErrorScreen extends RealmsScreen {
   private final Screen nextScreen;
   private Component line1;
   private Component line2;

   public RealmsGenericErrorScreen(RealmsServiceException var1, Screen var2) {
      super(NarratorChatListener.NO_TITLE);
      this.nextScreen = â˜ƒ;
      this.errorMessage(â˜ƒ);
   }

   public RealmsGenericErrorScreen(Component var1, Screen var2) {
      super(NarratorChatListener.NO_TITLE);
      this.nextScreen = â˜ƒ;
      this.errorMessage(â˜ƒ);
   }

   public RealmsGenericErrorScreen(Component var1, Component var2, Screen var3) {
      super(NarratorChatListener.NO_TITLE);
      this.nextScreen = â˜ƒ;
      this.errorMessage(â˜ƒ, â˜ƒ);
   }

   private void errorMessage(RealmsServiceException var1) {
      if (â˜ƒ.errorCode == -1) {
         this.line1 = new TextComponent("An error occurred (" + â˜ƒ.httpResultCode + "):");
         this.line2 = new TextComponent(â˜ƒ.httpResponseContent);
      } else {
         this.line1 = new TextComponent("Realms (" + â˜ƒ.errorCode + "):");
         String â˜ƒ = "mco.errorMessage." + â˜ƒ.errorCode;
         this.line2 = (Component)(I18n.exists(â˜ƒ) ? new TranslatableComponent(â˜ƒ) : Component.nullToEmpty(â˜ƒ.errorMsg));
      }
   }

   private void errorMessage(Component var1) {
      this.line1 = new TextComponent("An error occurred: ");
      this.line2 = â˜ƒ;
   }

   private void errorMessage(Component var1, Component var2) {
      this.line1 = â˜ƒ;
      this.line2 = â˜ƒ;
   }

   @Override
   public void init() {
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height - 52, 200, 20, new TextComponent("Ok"), var1 -> this.minecraft.setScreen(this.nextScreen))
      );
   }

   @Override
   public Component getNarrationMessage() {
      return new TextComponent("").append(this.line1).append(": ").append(this.line2);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.line1, this.width / 2, 80, 16777215);
      drawCenteredString(â˜ƒ, this.font, this.line2, this.width / 2, 100, 16711680);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
