package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantScreen extends AbstractContainerScreen<MerchantMenu> {
   private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation("textures/gui/container/villager2.png");
   private static final int TEXTURE_WIDTH = 512;
   private static final int TEXTURE_HEIGHT = 256;
   private static final int MERCHANT_MENU_PART_X = 99;
   private static final int PROGRESS_BAR_X = 136;
   private static final int PROGRESS_BAR_Y = 16;
   private static final int SELL_ITEM_1_X = 5;
   private static final int SELL_ITEM_2_X = 35;
   private static final int BUY_ITEM_X = 68;
   private static final int LABEL_Y = 6;
   private static final int NUMBER_OF_OFFER_BUTTONS = 7;
   private static final int TRADE_BUTTON_X = 5;
   private static final int TRADE_BUTTON_HEIGHT = 20;
   private static final int TRADE_BUTTON_WIDTH = 89;
   private static final int SCROLLER_HEIGHT = 27;
   private static final int SCROLLER_WIDTH = 6;
   private static final int SCROLL_BAR_HEIGHT = 139;
   private static final int SCROLL_BAR_TOP_POS_Y = 18;
   private static final int SCROLL_BAR_START_X = 94;
   private static final Component TRADES_LABEL = new TranslatableComponent("merchant.trades");
   private static final Component LEVEL_SEPARATOR = new TextComponent(" - ");
   private static final Component DEPRECATED_TOOLTIP = new TranslatableComponent("merchant.deprecated");
   private int shopItem;
   private final MerchantScreen.TradeOfferButton[] tradeOfferButtons = new MerchantScreen.TradeOfferButton[7];
   int scrollOff;
   private boolean isDragging;

   public MerchantScreen(MerchantMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.imageWidth = 276;
      this.inventoryLabelX = 107;
   }

   private void postButtonClick() {
      this.menu.setSelectionHint(this.shopItem);
      this.menu.tryMoveItems(this.shopItem);
      this.minecraft.getConnection().send(new ServerboundSelectTradePacket(this.shopItem));
   }

   @Override
   protected void init() {
      super.init();
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      int â˜ƒxx = â˜ƒx + 16 + 2;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 7; ++â˜ƒxxx) {
         this.tradeOfferButtons[â˜ƒxxx] = this.addRenderableWidget(new MerchantScreen.TradeOfferButton(â˜ƒ + 5, â˜ƒxx, â˜ƒxxx, var1x -> {
            if (var1x instanceof MerchantScreen.TradeOfferButton) {
               this.shopItem = ((MerchantScreen.TradeOfferButton)var1x).getIndex() + this.scrollOff;
               this.postButtonClick();
            }
         }));
         â˜ƒxx += 20;
      }
   }

   @Override
   protected void renderLabels(PoseStack var1, int var2, int var3) {
      int â˜ƒ = this.menu.getTraderLevel();
      if (â˜ƒ > 0 && â˜ƒ <= 5 && this.menu.showProgressBar()) {
         Component â˜ƒx = this.title.copy().append(LEVEL_SEPARATOR).append(new TranslatableComponent("merchant.level." + â˜ƒ));
         int â˜ƒxx = this.font.width(â˜ƒx);
         int â˜ƒxxx = 49 + this.imageWidth / 2 - â˜ƒxx / 2;
         this.font.draw(â˜ƒ, â˜ƒx, (float)â˜ƒxxx, 6.0F, 4210752);
      } else {
         this.font.draw(â˜ƒ, this.title, (float)(49 + this.imageWidth / 2 - this.font.width(this.title) / 2), 6.0F, 4210752);
      }

      this.font.draw(â˜ƒ, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
      int â˜ƒ = this.font.width(TRADES_LABEL);
      this.font.draw(â˜ƒ, TRADES_LABEL, (float)(5 - â˜ƒ / 2 + 48), 6.0F, 4210752);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      blit(â˜ƒ, â˜ƒ, â˜ƒx, this.getBlitOffset(), 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 512);
      MerchantOffers â˜ƒxx = this.menu.getOffers();
      if (!â˜ƒxx.isEmpty()) {
         int â˜ƒxxx = this.shopItem;
         if (â˜ƒxxx < 0 || â˜ƒxxx >= â˜ƒxx.size()) {
            return;
         }

         MerchantOffer â˜ƒxxx = (MerchantOffer)â˜ƒxx.get(â˜ƒxxx);
         if (â˜ƒxxx.isOutOfStock()) {
            RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            blit(â˜ƒ, this.leftPos + 83 + 99, this.topPos + 35, this.getBlitOffset(), 311.0F, 0.0F, 28, 21, 256, 512);
         }
      }
   }

   private void renderProgressBar(PoseStack var1, int var2, int var3, MerchantOffer var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
      int â˜ƒ = this.menu.getTraderLevel();
      int â˜ƒx = this.menu.getTraderXp();
      if (â˜ƒ < 5) {
         blit(â˜ƒ, â˜ƒ + 136, â˜ƒ + 16, this.getBlitOffset(), 0.0F, 186.0F, 102, 5, 256, 512);
         int â˜ƒxx = VillagerData.getMinXpPerLevel(â˜ƒ);
         if (â˜ƒx >= â˜ƒxx && VillagerData.canLevelUp(â˜ƒ)) {
            int â˜ƒxxx = 100;
            float â˜ƒxxxx = 100.0F / (float)(VillagerData.getMaxXpPerLevel(â˜ƒ) - â˜ƒxx);
            int â˜ƒxxxxx = Math.min(Mth.floor(â˜ƒxxxx * (float)(â˜ƒx - â˜ƒxx)), 100);
            blit(â˜ƒ, â˜ƒ + 136, â˜ƒ + 16, this.getBlitOffset(), 0.0F, 191.0F, â˜ƒxxxxx + 1, 5, 256, 512);
            int â˜ƒxxxxxx = this.menu.getFutureTraderXp();
            if (â˜ƒxxxxxx > 0) {
               int â˜ƒxxxxxxx = Math.min(Mth.floor((float)â˜ƒxxxxxx * â˜ƒxxxx), 100 - â˜ƒxxxxx);
               blit(â˜ƒ, â˜ƒ + 136 + â˜ƒxxxxx + 1, â˜ƒ + 16 + 1, this.getBlitOffset(), 2.0F, 182.0F, â˜ƒxxxxxxx, 3, 256, 512);
            }
         }
      }
   }

   private void renderScroller(PoseStack var1, int var2, int var3, MerchantOffers var4) {
      int â˜ƒ = â˜ƒ.size() + 1 - 7;
      if (â˜ƒ > 1) {
         int â˜ƒx = 139 - (27 + (â˜ƒ - 1) * 139 / â˜ƒ);
         int â˜ƒxx = 1 + â˜ƒx / â˜ƒ + 139 / â˜ƒ;
         int â˜ƒxxx = 113;
         int â˜ƒxxxx = Math.min(113, this.scrollOff * â˜ƒxx);
         if (this.scrollOff == â˜ƒ - 1) {
            â˜ƒxxxx = 113;
         }

         blit(â˜ƒ, â˜ƒ + 94, â˜ƒ + 18 + â˜ƒxxxx, this.getBlitOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
      } else {
         blit(â˜ƒ, â˜ƒ + 94, â˜ƒ + 18, this.getBlitOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      MerchantOffers â˜ƒ = this.menu.getOffers();
      if (!â˜ƒ.isEmpty()) {
         int â˜ƒx = (this.width - this.imageWidth) / 2;
         int â˜ƒxx = (this.height - this.imageHeight) / 2;
         int â˜ƒxxx = â˜ƒxx + 16 + 1;
         int â˜ƒxxxx = â˜ƒx + 5 + 5;
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
         this.renderScroller(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ);
         int â˜ƒxxxxx = 0;

         for(MerchantOffer â˜ƒxxxxxx : â˜ƒ) {
            if (!this.canScroll(â˜ƒ.size()) || â˜ƒxxxxx >= this.scrollOff && â˜ƒxxxxx < 7 + this.scrollOff) {
               ItemStack â˜ƒxxxxxxx = â˜ƒxxxxxx.getBaseCostA();
               ItemStack â˜ƒxxxxxxxx = â˜ƒxxxxxx.getCostA();
               ItemStack â˜ƒxxxxxxxxx = â˜ƒxxxxxx.getCostB();
               ItemStack â˜ƒxxxxxxxxxx = â˜ƒxxxxxx.getResult();
               this.itemRenderer.blitOffset = 100.0F;
               int â˜ƒxxxxxxxxxxx = â˜ƒxxx + 2;
               this.renderAndDecorateCostA(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxxxxxx);
               if (!â˜ƒxxxxxxxxx.isEmpty()) {
                  this.itemRenderer.renderAndDecorateFakeItem(â˜ƒxxxxxxxxx, â˜ƒx + 5 + 35, â˜ƒxxxxxxxxxxx);
                  this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒxxxxxxxxx, â˜ƒx + 5 + 35, â˜ƒxxxxxxxxxxx);
               }

               this.renderButtonArrows(â˜ƒ, â˜ƒxxxxxx, â˜ƒx, â˜ƒxxxxxxxxxxx);
               this.itemRenderer.renderAndDecorateFakeItem(â˜ƒxxxxxxxxxx, â˜ƒx + 5 + 68, â˜ƒxxxxxxxxxxx);
               this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒxxxxxxxxxx, â˜ƒx + 5 + 68, â˜ƒxxxxxxxxxxx);
               this.itemRenderer.blitOffset = 0.0F;
               â˜ƒxxx += 20;
               ++â˜ƒxxxxx;
            } else {
               ++â˜ƒxxxxx;
            }
         }

         int â˜ƒxxxxxx = this.shopItem;
         MerchantOffer â˜ƒxxxxxxx = (MerchantOffer)â˜ƒ.get(â˜ƒxxxxxx);
         if (this.menu.showProgressBar()) {
            this.renderProgressBar(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxx);
         }

         if (â˜ƒxxxxxxx.isOutOfStock() && this.isHovering(186, 35, 22, 21, (double)â˜ƒ, (double)â˜ƒ) && this.menu.canRestock()) {
            this.renderTooltip(â˜ƒ, DEPRECATED_TOOLTIP, â˜ƒ, â˜ƒ);
         }

         for(MerchantScreen.TradeOfferButton â˜ƒxxxxxx : this.tradeOfferButtons) {
            if (â˜ƒxxxxxx.isHovered()) {
               â˜ƒxxxxxx.renderToolTip(â˜ƒ, â˜ƒ, â˜ƒ);
            }

            â˜ƒxxxxxx.visible = â˜ƒxxxxxx.index < this.menu.getOffers().size();
         }

         RenderSystem.enableDepthTest();
      }

      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void renderButtonArrows(PoseStack var1, MerchantOffer var2, int var3, int var4) {
      RenderSystem.enableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
      if (â˜ƒ.isOutOfStock()) {
         blit(â˜ƒ, â˜ƒ + 5 + 35 + 20, â˜ƒ + 3, this.getBlitOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
      } else {
         blit(â˜ƒ, â˜ƒ + 5 + 35 + 20, â˜ƒ + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
      }
   }

   private void renderAndDecorateCostA(PoseStack var1, ItemStack var2, ItemStack var3, int var4, int var5) {
      this.itemRenderer.renderAndDecorateFakeItem(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.getCount() == â˜ƒ.getCount()) {
         this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getCount() == 1 ? "1" : null);
         this.itemRenderer.renderGuiItemDecorations(this.font, â˜ƒ, â˜ƒ + 14, â˜ƒ, â˜ƒ.getCount() == 1 ? "1" : null);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, VILLAGER_LOCATION);
         this.setBlitOffset(this.getBlitOffset() + 300);
         blit(â˜ƒ, â˜ƒ + 7, â˜ƒ + 12, this.getBlitOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
         this.setBlitOffset(this.getBlitOffset() - 300);
      }
   }

   private boolean canScroll(int var1) {
      return â˜ƒ > 7;
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      int â˜ƒ = this.menu.getOffers().size();
      if (this.canScroll(â˜ƒ)) {
         int â˜ƒx = â˜ƒ - 7;
         this.scrollOff = (int)((double)this.scrollOff - â˜ƒ);
         this.scrollOff = Mth.clamp(this.scrollOff, 0, â˜ƒx);
      }

      return true;
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      int â˜ƒ = this.menu.getOffers().size();
      if (this.isDragging) {
         int â˜ƒx = this.topPos + 18;
         int â˜ƒxx = â˜ƒx + 139;
         int â˜ƒxxx = â˜ƒ - 7;
         float â˜ƒxxxx = ((float)â˜ƒ - (float)â˜ƒx - 13.5F) / ((float)(â˜ƒxx - â˜ƒx) - 27.0F);
         â˜ƒxxxx = â˜ƒxxxx * (float)â˜ƒxxx + 0.5F;
         this.scrollOff = Mth.clamp((int)â˜ƒxxxx, 0, â˜ƒxxx);
         return true;
      } else {
         return super.mouseDragged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      this.isDragging = false;
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      if (this.canScroll(this.menu.getOffers().size())
         && â˜ƒ > (double)(â˜ƒ + 94)
         && â˜ƒ < (double)(â˜ƒ + 94 + 6)
         && â˜ƒ > (double)(â˜ƒx + 18)
         && â˜ƒ <= (double)(â˜ƒx + 18 + 139 + 1)) {
         this.isDragging = true;
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   class TradeOfferButton extends Button {
      final int index;

      public TradeOfferButton(int var2, int var3, int var4, Button.OnPress var5) {
         super(â˜ƒ, â˜ƒ, 89, 20, TextComponent.EMPTY, â˜ƒ);
         this.index = â˜ƒ;
         this.visible = false;
      }

      public int getIndex() {
         return this.index;
      }

      @Override
      public void renderToolTip(PoseStack var1, int var2, int var3) {
         if (this.isHovered && MerchantScreen.this.menu.getOffers().size() > this.index + MerchantScreen.this.scrollOff) {
            if (â˜ƒ < this.x + 20) {
               ItemStack â˜ƒ = ((MerchantOffer)MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff)).getCostA();
               MerchantScreen.this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            } else if (â˜ƒ < this.x + 50 && â˜ƒ > this.x + 30) {
               ItemStack â˜ƒ = ((MerchantOffer)MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff)).getCostB();
               if (!â˜ƒ.isEmpty()) {
                  MerchantScreen.this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               }
            } else if (â˜ƒ > this.x + 65) {
               ItemStack â˜ƒ = ((MerchantOffer)MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff)).getResult();
               MerchantScreen.this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }
}
