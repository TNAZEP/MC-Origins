package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;

public class RealmsSettingsScreen extends RealmsScreen {
   private static final int COMPONENT_WIDTH = 212;
   private static final Component NAME_LABEL = new TranslatableComponent("mco.configure.world.name");
   private static final Component DESCRIPTION_LABEL = new TranslatableComponent("mco.configure.world.description");
   private final RealmsConfigureWorldScreen configureWorldScreen;
   private final RealmsServer serverData;
   private Button doneButton;
   private EditBox descEdit;
   private EditBox nameEdit;

   public RealmsSettingsScreen(RealmsConfigureWorldScreen var1, RealmsServer var2) {
      super(new TranslatableComponent("mco.configure.world.settings.title"));
      this.configureWorldScreen = â˜ƒ;
      this.serverData = â˜ƒ;
   }

   @Override
   public void tick() {
      this.nameEdit.tick();
      this.descEdit.tick();
      this.doneButton.active = !this.nameEdit.getValue().trim().isEmpty();
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      int â˜ƒ = this.width / 2 - 106;
      this.doneButton = this.addRenderableWidget(
         new Button(â˜ƒ - 2, row(12), 106, 20, new TranslatableComponent("mco.configure.world.buttons.done"), var1x -> this.save())
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 2, row(12), 106, 20, CommonComponents.GUI_CANCEL, var1x -> this.minecraft.setScreen(this.configureWorldScreen))
      );
      String â˜ƒx = this.serverData.state == RealmsServer.State.OPEN ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
      Button â˜ƒxx = new Button(this.width / 2 - 53, row(0), 106, 20, new TranslatableComponent(â˜ƒx), var1x -> {
         if (this.serverData.state == RealmsServer.State.OPEN) {
            Component â˜ƒ = new TranslatableComponent("mco.configure.world.close.question.line1");
            Component â˜ƒx = new TranslatableComponent("mco.configure.world.close.question.line2");
            this.minecraft.setScreen(new RealmsLongConfirmationScreen(var1xx -> {
               if (var1xx) {
                  this.configureWorldScreen.closeTheWorld(this);
               } else {
                  this.minecraft.setScreen(this);
               }
            }, RealmsLongConfirmationScreen.Type.Info, â˜ƒ, â˜ƒx, true));
         } else {
            this.configureWorldScreen.openTheWorld(false, this);
         }
      });
      this.addRenderableWidget(â˜ƒxx);
      this.nameEdit = new EditBox(this.minecraft.font, â˜ƒ, row(4), 212, 20, null, new TranslatableComponent("mco.configure.world.name"));
      this.nameEdit.setMaxLength(32);
      this.nameEdit.setValue(this.serverData.getName());
      this.addWidget(this.nameEdit);
      this.magicalSpecialHackyFocus(this.nameEdit);
      this.descEdit = new EditBox(this.minecraft.font, â˜ƒ, row(8), 212, 20, null, new TranslatableComponent("mco.configure.world.description"));
      this.descEdit.setMaxLength(32);
      this.descEdit.setValue(this.serverData.getDescription());
      this.addWidget(this.descEdit);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.setScreen(this.configureWorldScreen);
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 17, 16777215);
      this.font.draw(â˜ƒ, NAME_LABEL, (float)(this.width / 2 - 106), (float)row(3), 10526880);
      this.font.draw(â˜ƒ, DESCRIPTION_LABEL, (float)(this.width / 2 - 106), (float)row(7), 10526880);
      this.nameEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.descEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void save() {
      this.configureWorldScreen.saveSettings(this.nameEdit.getValue(), this.descEdit.getValue());
   }
}
