package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class LockIconButton extends Button {
   private boolean locked;

   public LockIconButton(int var1, int var2, Button.OnPress var3) {
      super(â˜ƒ, â˜ƒ, 20, 20, new TranslatableComponent("narrator.button.difficulty_lock"), â˜ƒ);
   }

   @Override
   protected MutableComponent createNarrationMessage() {
      return CommonComponents.joinForNarration(
         super.createNarrationMessage(),
         this.isLocked()
            ? new TranslatableComponent("narrator.button.difficulty_lock.locked")
            : new TranslatableComponent("narrator.button.difficulty_lock.unlocked")
      );
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean var1) {
      this.locked = â˜ƒ;
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, Button.WIDGETS_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      LockIconButton.Icon â˜ƒ;
      if (!this.active) {
         â˜ƒ = this.locked ? LockIconButton.Icon.LOCKED_DISABLED : LockIconButton.Icon.UNLOCKED_DISABLED;
      } else if (this.isHovered()) {
         â˜ƒ = this.locked ? LockIconButton.Icon.LOCKED_HOVER : LockIconButton.Icon.UNLOCKED_HOVER;
      } else {
         â˜ƒ = this.locked ? LockIconButton.Icon.LOCKED : LockIconButton.Icon.UNLOCKED;
      }

      this.blit(â˜ƒ, this.x, this.y, â˜ƒ.getX(), â˜ƒ.getY(), this.width, this.height);
   }

   static enum Icon {
      LOCKED(0, 146),
      LOCKED_HOVER(0, 166),
      LOCKED_DISABLED(0, 186),
      UNLOCKED(20, 146),
      UNLOCKED_HOVER(20, 166),
      UNLOCKED_DISABLED(20, 186);

      private final int x;
      private final int y;

      private Icon(int var3, int var4) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }
   }
}
