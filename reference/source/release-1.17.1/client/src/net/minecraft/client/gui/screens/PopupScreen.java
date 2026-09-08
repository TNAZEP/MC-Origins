package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;

public class PopupScreen extends Screen {
   private static final int BUTTON_PADDING = 20;
   private static final int BUTTON_MARGIN = 5;
   private static final int BUTTON_HEIGHT = 20;
   private final Component narrationMessage;
   private final FormattedText message;
   private final ImmutableList<PopupScreen.ButtonOption> buttonOptions;
   private MultiLineLabel messageLines = MultiLineLabel.EMPTY;
   private int contentTop;
   private int buttonWidth;

   protected PopupScreen(Component var1, List<Component> var2, ImmutableList<PopupScreen.ButtonOption> var3) {
      super(â˜ƒ);
      this.message = FormattedText.composite(â˜ƒ);
      this.narrationMessage = CommonComponents.joinForNarration(â˜ƒ, ComponentUtils.formatList(â˜ƒ, TextComponent.EMPTY));
      this.buttonOptions = â˜ƒ;
   }

   @Override
   public Component getNarrationMessage() {
      return this.narrationMessage;
   }

   @Override
   public void init() {
      for(PopupScreen.ButtonOption â˜ƒ : this.buttonOptions) {
         this.buttonWidth = Math.max(this.buttonWidth, 20 + this.font.width(â˜ƒ.message) + 20);
      }

      int â˜ƒ = 5 + this.buttonWidth + 5;
      int â˜ƒx = â˜ƒ * this.buttonOptions.size();
      this.messageLines = MultiLineLabel.create(this.font, this.message, â˜ƒx);
      int â˜ƒxx = this.messageLines.getLineCount() * 9;
      this.contentTop = (int)((double)this.height / 2.0 - (double)â˜ƒxx / 2.0);
      int â˜ƒxxx = this.contentTop + â˜ƒxx + 9 * 2;
      int â˜ƒxxxx = (int)((double)this.width / 2.0 - (double)â˜ƒx / 2.0);

      for(PopupScreen.ButtonOption â˜ƒxxxxx : this.buttonOptions) {
         this.addRenderableWidget(new Button(â˜ƒxxxx, â˜ƒxxx, this.buttonWidth, 20, â˜ƒxxxxx.message, â˜ƒxxxxx.onPress));
         â˜ƒxxxx += â˜ƒ;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderDirtBackground(0);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, this.contentTop - 9 * 2, -1);
      this.messageLines.renderCentered(â˜ƒ, this.width / 2, this.contentTop);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   public static final class ButtonOption {
      final Component message;
      final Button.OnPress onPress;

      public ButtonOption(Component var1, Button.OnPress var2) {
         this.message = â˜ƒ;
         this.onPress = â˜ƒ;
      }
   }
}
