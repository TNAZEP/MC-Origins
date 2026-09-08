package net.minecraft.client.gui.screens.packs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.util.FormattedCharSequence;

public class TransferableSelectionList extends ObjectSelectionList<TransferableSelectionList.PackEntry> {
   static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/resource_packs.png");
   static final Component INCOMPATIBLE_TITLE = new TranslatableComponent("pack.incompatible");
   static final Component INCOMPATIBLE_CONFIRM_TITLE = new TranslatableComponent("pack.incompatible.confirm.title");
   private final Component title;

   public TransferableSelectionList(Minecraft var1, int var2, int var3, Component var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, 32, â˜ƒ - 55 + 4, 36);
      this.title = â˜ƒ;
      this.centerListVertically = false;
      this.setRenderHeader(true, (int)(9.0F * 1.5F));
   }

   @Override
   protected void renderHeader(PoseStack var1, int var2, int var3, Tesselator var4) {
      Component â˜ƒ = new TextComponent("").append(this.title).withStyle(ChatFormatting.UNDERLINE, ChatFormatting.BOLD);
      this.minecraft.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + this.width / 2 - this.minecraft.font.width(â˜ƒ) / 2), (float)Math.min(this.y0 + 3, â˜ƒ), 16777215);
   }

   @Override
   public int getRowWidth() {
      return this.width;
   }

   @Override
   protected int getScrollbarPosition() {
      return this.x1 - 6;
   }

   public static class PackEntry extends ObjectSelectionList.Entry<TransferableSelectionList.PackEntry> {
      private static final int ICON_OVERLAY_X_MOVE_RIGHT = 0;
      private static final int ICON_OVERLAY_X_MOVE_LEFT = 32;
      private static final int ICON_OVERLAY_X_MOVE_DOWN = 64;
      private static final int ICON_OVERLAY_X_MOVE_UP = 96;
      private static final int ICON_OVERLAY_Y_UNSELECTED = 0;
      private static final int ICON_OVERLAY_Y_SELECTED = 32;
      private static final int MAX_DESCRIPTION_WIDTH_PIXELS = 157;
      private static final int MAX_NAME_WIDTH_PIXELS = 157;
      private static final String TOO_LONG_NAME_SUFFIX = "...";
      private final TransferableSelectionList parent;
      protected final Minecraft minecraft;
      protected final Screen screen;
      private final PackSelectionModel.Entry pack;
      private final FormattedCharSequence nameDisplayCache;
      private final MultiLineLabel descriptionDisplayCache;
      private final FormattedCharSequence incompatibleNameDisplayCache;
      private final MultiLineLabel incompatibleDescriptionDisplayCache;

      public PackEntry(Minecraft var1, TransferableSelectionList var2, Screen var3, PackSelectionModel.Entry var4) {
         this.minecraft = â˜ƒ;
         this.screen = â˜ƒ;
         this.pack = â˜ƒ;
         this.parent = â˜ƒ;
         this.nameDisplayCache = cacheName(â˜ƒ, â˜ƒ.getTitle());
         this.descriptionDisplayCache = cacheDescription(â˜ƒ, â˜ƒ.getExtendedDescription());
         this.incompatibleNameDisplayCache = cacheName(â˜ƒ, TransferableSelectionList.INCOMPATIBLE_TITLE);
         this.incompatibleDescriptionDisplayCache = cacheDescription(â˜ƒ, â˜ƒ.getCompatibility().getDescription());
      }

      private static FormattedCharSequence cacheName(Minecraft var0, Component var1) {
         int â˜ƒ = â˜ƒ.font.width(â˜ƒ);
         if (â˜ƒ > 157) {
            FormattedText â˜ƒx = FormattedText.composite(â˜ƒ.font.substrByWidth(â˜ƒ, 157 - â˜ƒ.font.width("...")), FormattedText.of("..."));
            return Language.getInstance().getVisualOrder(â˜ƒx);
         } else {
            return â˜ƒ.getVisualOrderText();
         }
      }

      private static MultiLineLabel cacheDescription(Minecraft var0, Component var1) {
         return MultiLineLabel.create(â˜ƒ.font, â˜ƒ, 157, 2);
      }

      @Override
      public Component getNarration() {
         return new TranslatableComponent("narrator.select", this.pack.getTitle());
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         PackCompatibility â˜ƒ = this.pack.getCompatibility();
         if (!â˜ƒ.isCompatible()) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.fill(â˜ƒ, â˜ƒ - 1, â˜ƒ - 1, â˜ƒ + â˜ƒ - 9, â˜ƒ + â˜ƒ + 1, -8978432);
         }

         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, this.pack.getIconTexture());
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 32, 32, 32, 32);
         FormattedCharSequence â˜ƒ = this.nameDisplayCache;
         MultiLineLabel â˜ƒx = this.descriptionDisplayCache;
         if (this.showHoverOverlay() && (this.minecraft.options.touchscreen || â˜ƒ)) {
            RenderSystem.setShaderTexture(0, TransferableSelectionList.ICON_OVERLAY_LOCATION);
            GuiComponent.fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 32, â˜ƒ + 32, -1601138544);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int â˜ƒxx = â˜ƒ - â˜ƒ;
            int â˜ƒxxx = â˜ƒ - â˜ƒ;
            if (!this.pack.getCompatibility().isCompatible()) {
               â˜ƒ = this.incompatibleNameDisplayCache;
               â˜ƒx = this.incompatibleDescriptionDisplayCache;
            }

            if (this.pack.canSelect()) {
               if (â˜ƒxx < 32) {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else {
               if (this.pack.canUnselect()) {
                  if (â˜ƒxx < 16) {
                     GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 32.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 32.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.pack.canMoveUp()) {
                  if (â˜ƒxx < 32 && â˜ƒxx > 16 && â˜ƒxxx < 16) {
                     GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.pack.canMoveDown()) {
                  if (â˜ƒxx < 32 && â˜ƒxx > 16 && â˜ƒxxx > 16) {
                     GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 64.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 64.0F, 0.0F, 32, 32, 256, 256);
                  }
               }
            }
         }

         this.minecraft.font.drawShadow(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 32 + 2), (float)(â˜ƒ + 1), 16777215);
         â˜ƒx.renderLeftAligned(â˜ƒ, â˜ƒ + 32 + 2, â˜ƒ + 12, 10, 8421504);
      }

      private boolean showHoverOverlay() {
         return !this.pack.isFixedPosition() || !this.pack.isRequired();
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         double â˜ƒ = â˜ƒ - (double)this.parent.getRowLeft();
         double â˜ƒx = â˜ƒ - (double)this.parent.getRowTop(this.parent.children().indexOf(this));
         if (this.showHoverOverlay() && â˜ƒ <= 32.0) {
            if (this.pack.canSelect()) {
               PackCompatibility â˜ƒxx = this.pack.getCompatibility();
               if (â˜ƒxx.isCompatible()) {
                  this.pack.select();
               } else {
                  Component â˜ƒxx = â˜ƒxx.getConfirmation();
                  this.minecraft.setScreen(new ConfirmScreen(var1x -> {
                     this.minecraft.setScreen(this.screen);
                     if (var1x) {
                        this.pack.select();
                     }
                  }, TransferableSelectionList.INCOMPATIBLE_CONFIRM_TITLE, â˜ƒxx));
               }

               return true;
            }

            if (â˜ƒ < 16.0 && this.pack.canUnselect()) {
               this.pack.unselect();
               return true;
            }

            if (â˜ƒ > 16.0 && â˜ƒx < 16.0 && this.pack.canMoveUp()) {
               this.pack.moveUp();
               return true;
            }

            if (â˜ƒ > 16.0 && â˜ƒx > 16.0 && this.pack.canMoveDown()) {
               this.pack.moveDown();
               return true;
            }
         }

         return false;
      }
   }
}
