package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;

public class BrewingStandScreen extends AbstractContainerScreen<BrewingStandMenu> {
   private static final ResourceLocation BREWING_STAND_LOCATION = new ResourceLocation("textures/gui/container/brewing_stand.png");
   private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

   public BrewingStandScreen(BrewingStandMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void init() {
      super.init();
      this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
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
      RenderSystem.setShaderTexture(0, BREWING_STAND_LOCATION);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      int â˜ƒxx = this.menu.getFuel();
      int â˜ƒxxx = Mth.clamp((18 * â˜ƒxx + 20 - 1) / 20, 0, 18);
      if (â˜ƒxxx > 0) {
         this.blit(â˜ƒ, â˜ƒ + 60, â˜ƒx + 44, 176, 29, â˜ƒxxx, 4);
      }

      int â˜ƒ = this.menu.getBrewingTicks();
      if (â˜ƒ > 0) {
         int â˜ƒx = (int)(28.0F * (1.0F - (float)â˜ƒ / 400.0F));
         if (â˜ƒx > 0) {
            this.blit(â˜ƒ, â˜ƒ + 97, â˜ƒx + 16, 176, 0, 9, â˜ƒx);
         }

         â˜ƒx = BUBBLELENGTHS[â˜ƒ / 2 % 7];
         if (â˜ƒx > 0) {
            this.blit(â˜ƒ, â˜ƒ + 63, â˜ƒx + 14 + 29 - â˜ƒx, 185, 29 - â˜ƒx, 12, â˜ƒx);
         }
      }
   }
}
