package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;

public class RealmsParentalConsentScreen extends RealmsScreen {
   private static final Component MESSAGE = new TranslatableComponent("mco.account.privacyinfo");
   private final Screen nextScreen;
   private MultiLineLabel messageLines = MultiLineLabel.EMPTY;

   public RealmsParentalConsentScreen(Screen var1) {
      super(NarratorChatListener.NO_TITLE);
      this.nextScreen = â˜ƒ;
   }

   @Override
   public void init() {
      Component â˜ƒ = new TranslatableComponent("mco.account.update");
      Component â˜ƒx = CommonComponents.GUI_BACK;
      int â˜ƒxx = Math.max(this.font.width(â˜ƒ), this.font.width(â˜ƒx)) + 30;
      Component â˜ƒxxx = new TranslatableComponent("mco.account.privacy.info");
      int â˜ƒxxxx = (int)((double)this.font.width(â˜ƒxxx) * 1.2);
      this.addRenderableWidget(
         new Button(this.width / 2 - â˜ƒxxxx / 2, row(11), â˜ƒxxxx, 20, â˜ƒxxx, var0 -> Util.getPlatform().openUri("https://aka.ms/MinecraftGDPR"))
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - (â˜ƒxx + 5), row(13), â˜ƒxx, 20, â˜ƒ, var0 -> Util.getPlatform().openUri("https://aka.ms/UpdateMojangAccount"))
      );
      this.addRenderableWidget(new Button(this.width / 2 + 5, row(13), â˜ƒxx, 20, â˜ƒx, var1x -> this.minecraft.setScreen(this.nextScreen)));
      this.messageLines = MultiLineLabel.create(this.font, MESSAGE, (int)Math.round((double)this.width * 0.9));
   }

   @Override
   public Component getNarrationMessage() {
      return MESSAGE;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.messageLines.renderCentered(â˜ƒ, this.width / 2, 15, 15, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
