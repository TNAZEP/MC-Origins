package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.PlayerModelPart;

public class SkinCustomizationScreen extends OptionsSubScreen {
   public SkinCustomizationScreen(Screen var1, Options var2) {
      super(â˜ƒ, â˜ƒ, new TranslatableComponent("options.skinCustomisation.title"));
   }

   @Override
   protected void init() {
      int â˜ƒ = 0;

      for(PlayerModelPart â˜ƒx : PlayerModelPart.values()) {
         this.addRenderableWidget(
            CycleButton.onOffBuilder(this.options.isModelPartEnabled(â˜ƒx))
               .create(
                  this.width / 2 - 155 + â˜ƒ % 2 * 160,
                  this.height / 6 + 24 * (â˜ƒ >> 1),
                  150,
                  20,
                  â˜ƒx.getName(),
                  (var2, var3) -> this.options.toggleModelPart(â˜ƒ, var3)
               )
         );
         ++â˜ƒ;
      }

      this.addRenderableWidget(Option.MAIN_HAND.createButton(this.options, this.width / 2 - 155 + â˜ƒ % 2 * 160, this.height / 6 + 24 * (â˜ƒ >> 1), 150));
      if (++â˜ƒ % 2 == 1) {
         ++â˜ƒ;
      }

      this.addRenderableWidget(
         new Button(
            this.width / 2 - 100, this.height / 6 + 24 * (â˜ƒ >> 1), 200, 20, CommonComponents.GUI_DONE, var1x -> this.minecraft.setScreen(this.lastScreen)
         )
      );
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
