package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BaseCommandBlock;

public abstract class AbstractCommandBlockEditScreen extends Screen {
   private static final Component SET_COMMAND_LABEL = new TranslatableComponent("advMode.setCommand");
   private static final Component COMMAND_LABEL = new TranslatableComponent("advMode.command");
   private static final Component PREVIOUS_OUTPUT_LABEL = new TranslatableComponent("advMode.previousOutput");
   protected EditBox commandEdit;
   protected EditBox previousEdit;
   protected Button doneButton;
   protected Button cancelButton;
   protected CycleButton<Boolean> outputButton;
   CommandSuggestions commandSuggestions;

   public AbstractCommandBlockEditScreen() {
      super(NarratorChatListener.NO_TITLE);
   }

   @Override
   public void tick() {
      this.commandEdit.tick();
   }

   abstract BaseCommandBlock getCommandBlock();

   abstract int getPreviousY();

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.doneButton = this.addRenderableWidget(
         new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, CommonComponents.GUI_DONE, var1x -> this.onDone())
      );
      this.cancelButton = this.addRenderableWidget(
         new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, CommonComponents.GUI_CANCEL, var1x -> this.onClose())
      );
      boolean â˜ƒ = this.getCommandBlock().isTrackOutput();
      this.outputButton = this.addRenderableWidget(
         CycleButton.booleanBuilder(new TextComponent("O"), new TextComponent("X"))
            .withInitialValue(â˜ƒ)
            .displayOnlyValue()
            .create(this.width / 2 + 150 - 20, this.getPreviousY(), 20, 20, new TranslatableComponent("advMode.trackOutput"), (var1x, var2) -> {
               BaseCommandBlock â˜ƒ = this.getCommandBlock();
               â˜ƒ.setTrackOutput(var2);
               this.updatePreviousOutput(var2);
            })
      );
      this.commandEdit = new EditBox(this.font, this.width / 2 - 150, 50, 300, 20, new TranslatableComponent("advMode.command")) {
         @Override
         protected MutableComponent createNarrationMessage() {
            return super.createNarrationMessage().append(AbstractCommandBlockEditScreen.this.commandSuggestions.getNarrationMessage());
         }
      };
      this.commandEdit.setMaxLength(32500);
      this.commandEdit.setResponder(this::onEdited);
      this.addWidget(this.commandEdit);
      this.previousEdit = new EditBox(this.font, this.width / 2 - 150, this.getPreviousY(), 276, 20, new TranslatableComponent("advMode.previousOutput"));
      this.previousEdit.setMaxLength(32500);
      this.previousEdit.setEditable(false);
      this.previousEdit.setValue("-");
      this.addWidget(this.previousEdit);
      this.setInitialFocus(this.commandEdit);
      this.commandEdit.setFocus(true);
      this.commandSuggestions = new CommandSuggestions(this.minecraft, this, this.commandEdit, this.font, true, true, 0, 7, false, Integer.MIN_VALUE);
      this.commandSuggestions.setAllowSuggestions(true);
      this.commandSuggestions.updateCommandInfo();
      this.updatePreviousOutput(â˜ƒ);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.commandEdit.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.commandEdit.setValue(â˜ƒ);
      this.commandSuggestions.updateCommandInfo();
   }

   protected void updatePreviousOutput(boolean var1) {
      this.previousEdit.setValue(â˜ƒ ? this.getCommandBlock().getLastOutput().getString() : "-");
   }

   protected void onDone() {
      BaseCommandBlock â˜ƒ = this.getCommandBlock();
      this.populateAndSendPacket(â˜ƒ);
      if (!â˜ƒ.isTrackOutput()) {
         â˜ƒ.setLastOutput(null);
      }

      this.minecraft.setScreen(null);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   protected abstract void populateAndSendPacket(BaseCommandBlock var1);

   private void onEdited(String var1) {
      this.commandSuggestions.updateCommandInfo();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.commandSuggestions.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ != 257 && â˜ƒ != 335) {
         return false;
      } else {
         this.onDone();
         return true;
      }
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      return this.commandSuggestions.mouseScrolled(â˜ƒ) ? true : super.mouseScrolled(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.commandSuggestions.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ) ? true : super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, SET_COMMAND_LABEL, this.width / 2, 20, 16777215);
      drawString(â˜ƒ, this.font, COMMAND_LABEL, this.width / 2 - 150, 40, 10526880);
      this.commandEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒ = 75;
      if (!this.previousEdit.getValue().isEmpty()) {
         â˜ƒ += 5 * 9 + 1 + this.getPreviousY() - 135;
         drawString(â˜ƒ, this.font, PREVIOUS_OUTPUT_LABEL, this.width / 2 - 150, â˜ƒ + 4, 10526880);
         this.previousEdit.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.commandSuggestions.render(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
