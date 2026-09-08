package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;

public class PageButton extends Button {
   private final boolean isForward;
   private final boolean playTurnSound;

   public PageButton(int var1, int var2, boolean var3, Button.OnPress var4, boolean var5) {
      super(â˜ƒ, â˜ƒ, 23, 13, TextComponent.EMPTY, â˜ƒ);
      this.isForward = â˜ƒ;
      this.playTurnSound = â˜ƒ;
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, BookViewScreen.BOOK_LOCATION);
      int â˜ƒ = 0;
      int â˜ƒx = 192;
      if (this.isHovered()) {
         â˜ƒ += 23;
      }

      if (!this.isForward) {
         â˜ƒx += 13;
      }

      this.blit(â˜ƒ, this.x, this.y, â˜ƒ, â˜ƒx, 23, 13);
   }

   @Override
   public void playDownSound(SoundManager var1) {
      if (this.playTurnSound) {
         â˜ƒ.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
      }
   }
}
