package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.GrindstoneMenu;

public class GrindstoneScreen extends AbstractContainerScreen<GrindstoneMenu> {
   private static final ResourceLocation GRINDSTONE_LOCATION = new ResourceLocation("textures/gui/container/grindstone.png");

   public GrindstoneScreen(GrindstoneMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.renderBg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, GRINDSTONE_LOCATION);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(2).hasItem()) {
         this.blit(â˜ƒ, â˜ƒ + 92, â˜ƒx + 31, this.imageWidth, 0, 28, 21);
      }
   }
}
