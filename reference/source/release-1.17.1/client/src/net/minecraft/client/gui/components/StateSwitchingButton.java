package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class StateSwitchingButton extends AbstractWidget {
   protected ResourceLocation resourceLocation;
   protected boolean isStateTriggered;
   protected int xTexStart;
   protected int yTexStart;
   protected int xDiffTex;
   protected int yDiffTex;

   public StateSwitchingButton(int var1, int var2, int var3, int var4, boolean var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, TextComponent.EMPTY);
      this.isStateTriggered = â˜ƒ;
   }

   public void initTextureValues(int var1, int var2, int var3, int var4, ResourceLocation var5) {
      this.xTexStart = â˜ƒ;
      this.yTexStart = â˜ƒ;
      this.xDiffTex = â˜ƒ;
      this.yDiffTex = â˜ƒ;
      this.resourceLocation = â˜ƒ;
   }

   public void setStateTriggered(boolean var1) {
      this.isStateTriggered = â˜ƒ;
   }

   public boolean isStateTriggered() {
      return this.isStateTriggered;
   }

   public void setPosition(int var1, int var2) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      this.defaultButtonNarrationText(â˜ƒ);
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, this.resourceLocation);
      RenderSystem.disableDepthTest();
      int â˜ƒ = this.xTexStart;
      int â˜ƒx = this.yTexStart;
      if (this.isStateTriggered) {
         â˜ƒ += this.xDiffTex;
      }

      if (this.isHovered()) {
         â˜ƒx += this.yDiffTex;
      }

      this.blit(â˜ƒ, this.x, this.y, â˜ƒ, â˜ƒx, this.width, this.height);
      RenderSystem.enableDepthTest();
   }
}
