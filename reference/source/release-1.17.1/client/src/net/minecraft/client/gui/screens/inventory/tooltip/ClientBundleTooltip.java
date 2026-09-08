package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.item.ItemStack;

public class ClientBundleTooltip implements ClientTooltipComponent {
   public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
   private static final int MARGIN_Y = 4;
   private static final int BORDER_WIDTH = 1;
   private static final int TEX_SIZE = 128;
   private static final int SLOT_SIZE_X = 18;
   private static final int SLOT_SIZE_Y = 20;
   private final NonNullList<ItemStack> items;
   private final int weight;

   public ClientBundleTooltip(BundleTooltip var1) {
      this.items = â˜ƒ.getItems();
      this.weight = â˜ƒ.getWeight();
   }

   @Override
   public int getHeight() {
      return this.gridSizeY() * 20 + 2 + 4;
   }

   @Override
   public int getWidth(Font var1) {
      return this.gridSizeX() * 18 + 2;
   }

   @Override
   public void renderImage(Font var1, int var2, int var3, PoseStack var4, ItemRenderer var5, int var6, TextureManager var7) {
      int â˜ƒ = this.gridSizeX();
      int â˜ƒx = this.gridSizeY();
      boolean â˜ƒxx = this.weight >= 64;
      int â˜ƒxxx = 0;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒx; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒ; ++â˜ƒxxxxx) {
            int â˜ƒxxxxxx = â˜ƒ + â˜ƒxxxxx * 18 + 1;
            int â˜ƒxxxxxxx = â˜ƒ + â˜ƒxxxx * 20 + 1;
            this.renderSlot(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxx++, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      this.drawBorder(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void renderSlot(int var1, int var2, int var3, boolean var4, Font var5, PoseStack var6, ItemRenderer var7, int var8, TextureManager var9) {
      if (â˜ƒ >= this.items.size()) {
         this.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ ? ClientBundleTooltip.Texture.BLOCKED_SLOT : ClientBundleTooltip.Texture.SLOT);
      } else {
         ItemStack â˜ƒ = this.items.get(â˜ƒ);
         this.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.SLOT);
         â˜ƒ.renderAndDecorateItem(â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, â˜ƒ);
         â˜ƒ.renderGuiItemDecorations(â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ + 1);
         if (â˜ƒ == 0) {
            AbstractContainerScreen.renderSlotHighlight(â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, â˜ƒ);
         }
      }
   }

   private void drawBorder(int var1, int var2, int var3, int var4, PoseStack var5, int var6, TextureManager var7) {
      this.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_CORNER_TOP);
      this.blit(â˜ƒ, â˜ƒ + â˜ƒ * 18 + 1, â˜ƒ, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_CORNER_TOP);

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.blit(â˜ƒ, â˜ƒ + 1 + â˜ƒ * 18, â˜ƒ, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_HORIZONTAL_TOP);
         this.blit(â˜ƒ, â˜ƒ + 1 + â˜ƒ * 18, â˜ƒ + â˜ƒ * 20, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_HORIZONTAL_BOTTOM);
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.blit(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ * 20 + 1, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_VERTICAL);
         this.blit(â˜ƒ, â˜ƒ + â˜ƒ * 18 + 1, â˜ƒ + â˜ƒ * 20 + 1, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_VERTICAL);
      }

      this.blit(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ * 20, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_CORNER_BOTTOM);
      this.blit(â˜ƒ, â˜ƒ + â˜ƒ * 18 + 1, â˜ƒ + â˜ƒ * 20, â˜ƒ, â˜ƒ, ClientBundleTooltip.Texture.BORDER_CORNER_BOTTOM);
   }

   private void blit(PoseStack var1, int var2, int var3, int var4, TextureManager var5, ClientBundleTooltip.Texture var6) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (float)â˜ƒ.x, (float)â˜ƒ.y, â˜ƒ.w, â˜ƒ.h, 128, 128);
   }

   private int gridSizeX() {
      return Math.max(2, (int)Math.ceil(Math.sqrt((double)this.items.size() + 1.0)));
   }

   private int gridSizeY() {
      return (int)Math.ceil(((double)this.items.size() + 1.0) / (double)this.gridSizeX());
   }

   static enum Texture {
      SLOT(0, 0, 18, 20),
      BLOCKED_SLOT(0, 40, 18, 20),
      BORDER_VERTICAL(0, 18, 1, 20),
      BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
      BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
      BORDER_CORNER_TOP(0, 20, 1, 1),
      BORDER_CORNER_BOTTOM(0, 60, 1, 1);

      public final int x;
      public final int y;
      public final int w;
      public final int h;

      private Texture(int var3, int var4, int var5, int var6) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
         this.w = â˜ƒ;
         this.h = â˜ƒ;
      }
   }
}
