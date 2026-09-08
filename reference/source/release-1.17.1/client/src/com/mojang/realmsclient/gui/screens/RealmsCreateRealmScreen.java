package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.util.task.WorldCreationTask;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;

public class RealmsCreateRealmScreen extends RealmsScreen {
   private static final Component NAME_LABEL = new TranslatableComponent("mco.configure.world.name");
   private static final Component DESCRIPTION_LABEL = new TranslatableComponent("mco.configure.world.description");
   private final RealmsServer server;
   private final RealmsMainScreen lastScreen;
   private EditBox nameBox;
   private EditBox descriptionBox;
   private Button createButton;

   public RealmsCreateRealmScreen(RealmsServer var1, RealmsMainScreen var2) {
      super(new TranslatableComponent("mco.selectServer.create"));
      this.server = â˜ƒ;
      this.lastScreen = â˜ƒ;
   }

   @Override
   public void tick() {
      if (this.nameBox != null) {
         this.nameBox.tick();
      }

      if (this.descriptionBox != null) {
         this.descriptionBox.tick();
      }
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.createButton = this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 120 + 17, 97, 20, new TranslatableComponent("mco.create.world"), var1 -> this.createWorld())
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 5, this.height / 4 + 120 + 17, 95, 20, CommonComponents.GUI_CANCEL, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
      this.createButton.active = false;
      this.nameBox = new EditBox(this.minecraft.font, this.width / 2 - 100, 65, 200, 20, null, new TranslatableComponent("mco.configure.world.name"));
      this.addWidget(this.nameBox);
      this.setInitialFocus(this.nameBox);
      this.descriptionBox = new EditBox(
         this.minecraft.font, this.width / 2 - 100, 115, 200, 20, null, new TranslatableComponent("mco.configure.world.description")
      );
      this.addWidget(this.descriptionBox);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      boolean â˜ƒ = super.charTyped(â˜ƒ, â˜ƒ);
      this.createButton.active = this.valid();
      return â˜ƒ;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.setScreen(this.lastScreen);
         return true;
      } else {
         boolean â˜ƒ = super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         this.createButton.active = this.valid();
         return â˜ƒ;
      }
   }

   private void createWorld() {
      if (this.valid()) {
         RealmsResetWorldScreen â˜ƒ = new RealmsResetWorldScreen(
            this.lastScreen,
            this.server,
            new TranslatableComponent("mco.selectServer.create"),
            new TranslatableComponent("mco.create.world.subtitle"),
            10526880,
            new TranslatableComponent("mco.create.world.skip"),
            () -> this.minecraft.execute(() -> this.minecraft.setScreen(this.lastScreen.newScreen())),
            () -> this.minecraft.setScreen(this.lastScreen.newScreen())
         );
         â˜ƒ.setResetTitle(new TranslatableComponent("mco.create.world.reset.title"));
         this.minecraft
            .setScreen(
               new RealmsLongRunningMcoTaskScreen(
                  this.lastScreen, new WorldCreationTask(this.server.id, this.nameBox.getValue(), this.descriptionBox.getValue(), â˜ƒ)
               )
            );
      }
   }

   private boolean valid() {
      return !this.nameBox.getValue().trim().isEmpty();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 11, 16777215);
      this.font.draw(â˜ƒ, NAME_LABEL, (float)(this.width / 2 - 100), 52.0F, 10526880);
      this.font.draw(â˜ƒ, DESCRIPTION_LABEL, (float)(this.width / 2 - 100), 102.0F, 10526880);
      if (this.nameBox != null) {
         this.nameBox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      if (this.descriptionBox != null) {
         this.descriptionBox.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
