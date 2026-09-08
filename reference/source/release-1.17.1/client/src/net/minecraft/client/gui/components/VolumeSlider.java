package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;

public class VolumeSlider extends AbstractOptionSliderButton {
   private final SoundSource source;

   public VolumeSlider(Minecraft var1, int var2, int var3, SoundSource var4, int var5) {
      super(â˜ƒ.options, â˜ƒ, â˜ƒ, â˜ƒ, 20, (double)â˜ƒ.options.getSoundSourceVolume(â˜ƒ));
      this.source = â˜ƒ;
      this.updateMessage();
   }

   @Override
   protected void updateMessage() {
      Component â˜ƒ = (Component)((float)this.value == (float)this.getYImage(false)
         ? CommonComponents.OPTION_OFF
         : new TextComponent((int)(this.value * 100.0) + "%"));
      this.setMessage(new TranslatableComponent("soundCategory." + this.source.getName()).append(": ").append(â˜ƒ));
   }

   @Override
   protected void applyValue() {
      this.options.setSoundCategoryVolume(this.source, (float)this.value);
      this.options.save();
   }
}
