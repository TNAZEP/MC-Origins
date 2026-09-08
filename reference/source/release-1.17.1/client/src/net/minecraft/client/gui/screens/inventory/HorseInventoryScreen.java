package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;

public class HorseInventoryScreen extends AbstractContainerScreen<HorseInventoryMenu> {
   private static final ResourceLocation HORSE_INVENTORY_LOCATION = new ResourceLocation("textures/gui/container/horse.png");
   private final AbstractHorse horse;
   private float xMouse;
   private float yMouse;

   public HorseInventoryScreen(HorseInventoryMenu var1, Inventory var2, AbstractHorse var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ.getDisplayName());
      this.horse = â˜ƒ;
      this.passEvents = false;
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, HORSE_INVENTORY_LOCATION);
      int â˜ƒx = (this.width - this.imageWidth) / 2;
      int â˜ƒxx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒx, â˜ƒxx, 0, 0, this.imageWidth, this.imageHeight);
      if (this.horse instanceof AbstractChestedHorse â˜ƒ && â˜ƒ.hasChest()) {
         this.blit(â˜ƒ, â˜ƒx + 79, â˜ƒxx + 17, 0, this.imageHeight, â˜ƒ.getInventoryColumns() * 18, 54);
      }

      if (this.horse.isSaddleable()) {
         this.blit(â˜ƒ, â˜ƒx + 7, â˜ƒxx + 35 - 18, 18, this.imageHeight + 54, 18, 18);
      }

      if (this.horse.canWearArmor()) {
         if (this.horse instanceof Llama) {
            this.blit(â˜ƒ, â˜ƒx + 7, â˜ƒxx + 35, 36, this.imageHeight + 54, 18, 18);
         } else {
            this.blit(â˜ƒ, â˜ƒx + 7, â˜ƒxx + 35, 0, this.imageHeight + 54, 18, 18);
         }
      }

      InventoryScreen.renderEntityInInventory(â˜ƒx + 51, â˜ƒxx + 60, 17, (float)(â˜ƒx + 51) - this.xMouse, (float)(â˜ƒxx + 75 - 50) - this.yMouse, this.horse);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.xMouse = (float)â˜ƒ;
      this.yMouse = (float)â˜ƒ;
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
