package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CartographyTableScreen extends AbstractContainerScreen<CartographyTableMenu> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/cartography_table.png");

   public CartographyTableScreen(CartographyTableMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.titleLabelY -= 2;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      this.renderBackground(â˜ƒ);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, BG_LOCATION);
      int â˜ƒxx = this.leftPos;
      int â˜ƒxxx = this.topPos;
      this.blit(â˜ƒ, â˜ƒxx, â˜ƒxxx, 0, 0, this.imageWidth, this.imageHeight);
      ItemStack â˜ƒxxxx = this.menu.getSlot(1).getItem();
      boolean â˜ƒxxxxx = â˜ƒxxxx.is(Items.MAP);
      boolean â˜ƒxxxxxx = â˜ƒxxxx.is(Items.PAPER);
      boolean â˜ƒxxxxxxx = â˜ƒxxxx.is(Items.GLASS_PANE);
      ItemStack â˜ƒxxxxxxxx = this.menu.getSlot(0).getItem();
      boolean â˜ƒxxxxxxxxx = false;
      Integer â˜ƒ;
      MapItemSavedData â˜ƒx;
      if (â˜ƒxxxxxxxx.is(Items.FILLED_MAP)) {
         â˜ƒ = MapItem.getMapId(â˜ƒxxxxxxxx);
         â˜ƒx = MapItem.getSavedData(â˜ƒ, this.minecraft.level);
         if (â˜ƒx != null) {
            if (â˜ƒx.locked) {
               â˜ƒxxxxxxxxx = true;
               if (â˜ƒxxxxxx || â˜ƒxxxxxxx) {
                  this.blit(â˜ƒ, â˜ƒxx + 35, â˜ƒxxx + 31, this.imageWidth + 50, 132, 28, 21);
               }
            }

            if (â˜ƒxxxxxx && â˜ƒx.scale >= 4) {
               â˜ƒxxxxxxxxx = true;
               this.blit(â˜ƒ, â˜ƒxx + 35, â˜ƒxxx + 31, this.imageWidth + 50, 132, 28, 21);
            }
         }
      } else {
         â˜ƒ = null;
         â˜ƒx = null;
      }

      this.renderResultingMap(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxx);
   }

   private void renderResultingMap(
      PoseStack var1, @Nullable Integer var2, @Nullable MapItemSavedData var3, boolean var4, boolean var5, boolean var6, boolean var7
   ) {
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      if (â˜ƒ && !â˜ƒ) {
         this.blit(â˜ƒ, â˜ƒ + 67, â˜ƒx + 13, this.imageWidth, 66, 66, 66);
         this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 85, â˜ƒx + 31, 0.226F);
      } else if (â˜ƒ) {
         this.blit(â˜ƒ, â˜ƒ + 67 + 16, â˜ƒx + 13, this.imageWidth, 132, 50, 66);
         this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 86, â˜ƒx + 16, 0.34F);
         RenderSystem.setShaderTexture(0, BG_LOCATION);
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 0.0, 1.0);
         this.blit(â˜ƒ, â˜ƒ + 67, â˜ƒx + 13 + 16, this.imageWidth, 132, 50, 66);
         this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 70, â˜ƒx + 32, 0.34F);
         â˜ƒ.popPose();
      } else if (â˜ƒ) {
         this.blit(â˜ƒ, â˜ƒ + 67, â˜ƒx + 13, this.imageWidth, 0, 66, 66);
         this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 71, â˜ƒx + 17, 0.45F);
         RenderSystem.setShaderTexture(0, BG_LOCATION);
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 0.0, 1.0);
         this.blit(â˜ƒ, â˜ƒ + 66, â˜ƒx + 12, 0, this.imageHeight, 66, 66);
         â˜ƒ.popPose();
      } else {
         this.blit(â˜ƒ, â˜ƒ + 67, â˜ƒx + 13, this.imageWidth, 0, 66, 66);
         this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 71, â˜ƒx + 17, 0.45F);
      }
   }

   private void renderMap(PoseStack var1, @Nullable Integer var2, @Nullable MapItemSavedData var3, int var4, int var5, float var6) {
      if (â˜ƒ != null && â˜ƒ != null) {
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)â˜ƒ, (double)â˜ƒ, 1.0);
         â˜ƒ.scale(â˜ƒ, â˜ƒ, 1.0F);
         MultiBufferSource.BufferSource â˜ƒ = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
         this.minecraft.gameRenderer.getMapRenderer().render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, 15728880);
         â˜ƒ.endBatch();
         â˜ƒ.popPose();
      }
   }
}
