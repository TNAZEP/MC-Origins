package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public abstract class SimpleOptionsSubScreen extends OptionsSubScreen {
   private final Option[] smallOptions;
   @Nullable
   private AbstractWidget narratorButton;
   private OptionsList list;

   public SimpleOptionsSubScreen(Screen var1, Options var2, Component var3, Option[] var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.smallOptions = â˜ƒ;
   }

   @Override
   protected void init() {
      this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
      this.list.addSmall(this.smallOptions);
      this.addWidget(this.list);
      this.createFooter();
      this.narratorButton = this.list.findOption(Option.NARRATOR);
      if (this.narratorButton != null) {
         this.narratorButton.active = NarratorChatListener.INSTANCE.isActive();
      }
   }

   protected void createFooter() {
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.list.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      List<FormattedCharSequence> â˜ƒ = tooltipAt(this.list, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void updateNarratorButton() {
      if (this.narratorButton instanceof CycleButton) {
         ((CycleButton)this.narratorButton).setValue(this.options.narratorStatus);
      }
   }
}
