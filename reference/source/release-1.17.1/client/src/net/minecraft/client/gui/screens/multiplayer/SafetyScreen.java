package net.minecraft.client.gui.screens.multiplayer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class SafetyScreen extends Screen {
   private final Screen previous;
   private static final Component TITLE = new TranslatableComponent("multiplayerWarning.header").withStyle(ChatFormatting.BOLD);
   private static final Component CONTENT = new TranslatableComponent("multiplayerWarning.message");
   private static final Component CHECK = new TranslatableComponent("multiplayerWarning.check");
   private static final Component NARRATION = TITLE.copy().append("\n").append(CONTENT);
   private Checkbox stopShowing;
   private MultiLineLabel message = MultiLineLabel.EMPTY;

   public SafetyScreen(Screen var1) {
      super(NarratorChatListener.NO_TITLE);
      this.previous = â˜ƒ;
   }

   @Override
   protected void init() {
      super.init();
      this.message = MultiLineLabel.create(this.font, CONTENT, this.width - 50);
      int â˜ƒ = (this.message.getLineCount() + 1) * 9 * 2;
      this.addRenderableWidget(new Button(this.width / 2 - 155, 100 + â˜ƒ, 150, 20, CommonComponents.GUI_PROCEED, var1x -> {
         if (this.stopShowing.selected()) {
            this.minecraft.options.skipMultiplayerWarning = true;
            this.minecraft.options.save();
         }

         this.minecraft.setScreen(new JoinMultiplayerScreen(this.previous));
      }));
      this.addRenderableWidget(
         new Button(this.width / 2 - 155 + 160, 100 + â˜ƒ, 150, 20, CommonComponents.GUI_BACK, var1x -> this.minecraft.setScreen(this.previous))
      );
      this.stopShowing = new Checkbox(this.width / 2 - 155 + 80, 76 + â˜ƒ, 150, 20, CHECK, false);
      this.addRenderableWidget(this.stopShowing);
   }

   @Override
   public Component getNarrationMessage() {
      return NARRATION;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderDirtBackground(0);
      drawString(â˜ƒ, this.font, TITLE, 25, 30, 16777215);
      this.message.renderLeftAligned(â˜ƒ, 25, 70, 9 * 2, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
