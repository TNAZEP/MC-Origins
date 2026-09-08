package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class ImageButton extends Button {
   private final ResourceLocation resourceLocation;
   private final int xTexStart;
   private final int yTexStart;
   private final int yDiffTex;
   private final int textureWidth;
   private final int textureHeight;

   public ImageButton(int var1, int var2, int var3, int var4, int var5, int var6, ResourceLocation var7, Button.OnPress var8) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 256, 256, â˜ƒ);
   }

   public ImageButton(int var1, int var2, int var3, int var4, int var5, int var6, int var7, ResourceLocation var8, Button.OnPress var9) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 256, 256, â˜ƒ);
   }

   public ImageButton(int var1, int var2, int var3, int var4, int var5, int var6, int var7, ResourceLocation var8, int var9, int var10, Button.OnPress var11) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, TextComponent.EMPTY);
   }

   public ImageButton(
      int var1, int var2, int var3, int var4, int var5, int var6, int var7, ResourceLocation var8, int var9, int var10, Button.OnPress var11, Component var12
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, NO_TOOLTIP, â˜ƒ);
   }

   public ImageButton(
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      ResourceLocation var8,
      int var9,
      int var10,
      Button.OnPress var11,
      Button.OnTooltip var12,
      Component var13
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.textureWidth = â˜ƒ;
      this.textureHeight = â˜ƒ;
      this.xTexStart = â˜ƒ;
      this.yTexStart = â˜ƒ;
      this.yDiffTex = â˜ƒ;
      this.resourceLocation = â˜ƒ;
   }

   public void setPosition(int var1, int var2) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, this.resourceLocation);
      int â˜ƒ = this.yTexStart;
      if (this.isHovered()) {
         â˜ƒ += this.yDiffTex;
      }

      RenderSystem.enableDepthTest();
      blit(â˜ƒ, this.x, this.y, (float)this.xTexStart, (float)â˜ƒ, this.width, this.height, this.textureWidth, this.textureHeight);
      if (this.isHovered()) {
         this.renderToolTip(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
