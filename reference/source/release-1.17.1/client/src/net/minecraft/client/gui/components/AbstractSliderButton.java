package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public abstract class AbstractSliderButton extends AbstractWidget {
   protected double value;

   public AbstractSliderButton(int var1, int var2, int var3, int var4, Component var5, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.value = â˜ƒ;
   }

   @Override
   protected int getYImage(boolean var1) {
      return 0;
   }

   @Override
   protected MutableComponent createNarrationMessage() {
      return new TranslatableComponent("gui.narrate.slider", this.getMessage());
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, this.createNarrationMessage());
      if (this.active) {
         if (this.isFocused()) {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.slider.usage.focused"));
         } else {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.slider.usage.hovered"));
         }
      }
   }

   @Override
   protected void renderBg(PoseStack var1, Minecraft var2, int var3, int var4) {
      RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int â˜ƒ = (this.isHovered() ? 2 : 1) * 20;
      this.blit(â˜ƒ, this.x + (int)(this.value * (double)(this.width - 8)), this.y, 0, 46 + â˜ƒ, 4, 20);
      this.blit(â˜ƒ, this.x + (int)(this.value * (double)(this.width - 8)) + 4, this.y, 196, 46 + â˜ƒ, 4, 20);
   }

   @Override
   public void onClick(double var1, double var3) {
      this.setValueFromMouse(â˜ƒ);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      boolean â˜ƒ = â˜ƒ == 263;
      if (â˜ƒ || â˜ƒ == 262) {
         float â˜ƒx = â˜ƒ ? -1.0F : 1.0F;
         this.setValue(this.value + (double)(â˜ƒx / (float)(this.width - 8)));
      }

      return false;
   }

   private void setValueFromMouse(double var1) {
      this.setValue((â˜ƒ - (double)(this.x + 4)) / (double)(this.width - 8));
   }

   private void setValue(double var1) {
      double â˜ƒ = this.value;
      this.value = Mth.clamp(â˜ƒ, 0.0, 1.0);
      if (â˜ƒ != this.value) {
         this.applyValue();
      }

      this.updateMessage();
   }

   @Override
   protected void onDrag(double var1, double var3, double var5, double var7) {
      this.setValueFromMouse(â˜ƒ);
      super.onDrag(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void playDownSound(SoundManager var1) {
   }

   @Override
   public void onRelease(double var1, double var3) {
      super.playDownSound(Minecraft.getInstance().getSoundManager());
   }

   protected abstract void updateMessage();

   protected abstract void applyValue();
}
