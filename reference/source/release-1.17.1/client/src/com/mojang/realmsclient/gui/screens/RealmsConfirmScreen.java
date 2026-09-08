package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsConfirmScreen extends RealmsScreen {
   protected BooleanConsumer callback;
   private final Component title1;
   private final Component title2;

   public RealmsConfirmScreen(BooleanConsumer var1, Component var2, Component var3) {
      super(NarratorChatListener.NO_TITLE);
      this.callback = â˜ƒ;
      this.title1 = â˜ƒ;
      this.title2 = â˜ƒ;
   }

   @Override
   public void init() {
      this.addRenderableWidget(new Button(this.width / 2 - 105, row(9), 100, 20, CommonComponents.GUI_YES, var1 -> this.callback.accept(true)));
      this.addRenderableWidget(new Button(this.width / 2 + 5, row(9), 100, 20, CommonComponents.GUI_NO, var1 -> this.callback.accept(false)));
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title1, this.width / 2, row(3), 16777215);
      drawCenteredString(â˜ƒ, this.font, this.title2, this.width / 2, row(5), 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
