package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;

public class SoundOptionsScreen extends OptionsSubScreen {
   public SoundOptionsScreen(Screen var1, Options var2) {
      super(â˜ƒ, â˜ƒ, new TranslatableComponent("options.sounds.title"));
   }

   @Override
   protected void init() {
      int â˜ƒ = 0;
      this.addRenderableWidget(
         new VolumeSlider(this.minecraft, this.width / 2 - 155 + â˜ƒ % 2 * 160, this.height / 6 - 12 + 24 * (â˜ƒ >> 1), SoundSource.MASTER, 310)
      );
      â˜ƒ += 2;

      for(SoundSource â˜ƒx : SoundSource.values()) {
         if (â˜ƒx != SoundSource.MASTER) {
            this.addRenderableWidget(new VolumeSlider(this.minecraft, this.width / 2 - 155 + â˜ƒ % 2 * 160, this.height / 6 - 12 + 24 * (â˜ƒ >> 1), â˜ƒx, 150));
            ++â˜ƒ;
         }
      }

      this.addRenderableWidget(Option.SHOW_SUBTITLES.createButton(this.options, this.width / 2 - 75, this.height / 6 - 12 + 24 * (++â˜ƒ >> 1), 150));
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, var1x -> this.minecraft.setScreen(this.lastScreen))
      );
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 15, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
