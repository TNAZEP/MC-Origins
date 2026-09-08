package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;

public class ContainerScreen extends AbstractContainerScreen<ChestMenu> implements MenuAccess<ChestMenu> {
   private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
   private final int containerRows;

   public ContainerScreen(ChestMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.passEvents = false;
      int â˜ƒ = 222;
      int â˜ƒx = 114;
      this.containerRows = â˜ƒ.getRowCount();
      this.imageHeight = 114 + this.containerRows * 18;
      this.inventoryLabelY = this.imageHeight - 94;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
   }
}
