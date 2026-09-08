package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.item.ItemStack;

public class ItemCombinerScreen<T extends ItemCombinerMenu> extends AbstractContainerScreen<T> implements ContainerListener {
   private final ResourceLocation menuResource;

   public ItemCombinerScreen(T var1, Inventory var2, Component var3, ResourceLocation var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.menuResource = â˜ƒ;
   }

   protected void subInit() {
   }

   @Override
   protected void init() {
      super.init();
      this.subInit();
      this.menu.addSlotListener(this);
   }

   @Override
   public void removed() {
      super.removed();
      this.menu.removeSlotListener(this);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.disableBlend();
      this.renderFg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void renderFg(PoseStack var1, int var2, int var3, float var4) {
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, this.menuResource);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      this.blit(â˜ƒ, â˜ƒ + 59, â˜ƒx + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
      if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(2).hasItem()) {
         this.blit(â˜ƒ, â˜ƒ + 99, â˜ƒx + 45, this.imageWidth, 0, 28, 21);
      }
   }

   @Override
   public void dataChanged(AbstractContainerMenu var1, int var2, int var3) {
   }

   @Override
   public void slotChanged(AbstractContainerMenu var1, int var2, ItemStack var3) {
   }
}
