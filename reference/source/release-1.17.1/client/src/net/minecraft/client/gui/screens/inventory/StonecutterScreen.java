package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class StonecutterScreen extends AbstractContainerScreen<StonecutterMenu> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/stonecutter.png");
   private static final int SCROLLER_WIDTH = 12;
   private static final int SCROLLER_HEIGHT = 15;
   private static final int RECIPES_COLUMNS = 4;
   private static final int RECIPES_ROWS = 3;
   private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
   private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
   private static final int SCROLLER_FULL_HEIGHT = 54;
   private static final int RECIPES_X = 52;
   private static final int RECIPES_Y = 14;
   private float scrollOffs;
   private boolean scrolling;
   private int startIndex;
   private boolean displayRecipes;

   public StonecutterScreen(StonecutterMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerUpdateListener(this::containerChanged);
      --this.titleLabelY;
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
      int â˜ƒ = this.leftPos;
      int â˜ƒx = this.topPos;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      int â˜ƒxx = (int)(41.0F * this.scrollOffs);
      this.blit(â˜ƒ, â˜ƒ + 119, â˜ƒx + 15 + â˜ƒxx, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
      int â˜ƒxxx = this.leftPos + 52;
      int â˜ƒxxxx = this.topPos + 14;
      int â˜ƒxxxxx = this.startIndex + 12;
      this.renderButtons(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      this.renderRecipes(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   @Override
   protected void renderTooltip(PoseStack var1, int var2, int var3) {
      super.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.displayRecipes) {
         int â˜ƒ = this.leftPos + 52;
         int â˜ƒx = this.topPos + 14;
         int â˜ƒxx = this.startIndex + 12;
         List<StonecutterRecipe> â˜ƒxxx = this.menu.getRecipes();

         for(int â˜ƒxxxx = this.startIndex; â˜ƒxxxx < â˜ƒxx && â˜ƒxxxx < this.menu.getNumRecipes(); ++â˜ƒxxxx) {
            int â˜ƒxxxxx = â˜ƒxxxx - this.startIndex;
            int â˜ƒxxxxxx = â˜ƒ + â˜ƒxxxxx % 4 * 16;
            int â˜ƒxxxxxxx = â˜ƒx + â˜ƒxxxxx / 4 * 18 + 2;
            if (â˜ƒ >= â˜ƒxxxxxx && â˜ƒ < â˜ƒxxxxxx + 16 && â˜ƒ >= â˜ƒxxxxxxx && â˜ƒ < â˜ƒxxxxxxx + 18) {
               this.renderTooltip(â˜ƒ, ((StonecutterRecipe)â˜ƒxxx.get(â˜ƒxxxx)).getResultItem(), â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   private void renderButtons(PoseStack var1, int var2, int var3, int var4, int var5, int var6) {
      for(int â˜ƒ = this.startIndex; â˜ƒ < â˜ƒ && â˜ƒ < this.menu.getNumRecipes(); ++â˜ƒ) {
         int â˜ƒx = â˜ƒ - this.startIndex;
         int â˜ƒxx = â˜ƒ + â˜ƒx % 4 * 16;
         int â˜ƒxxx = â˜ƒx / 4;
         int â˜ƒxxxx = â˜ƒ + â˜ƒxxx * 18 + 2;
         int â˜ƒxxxxx = this.imageHeight;
         if (â˜ƒ == this.menu.getSelectedRecipeIndex()) {
            â˜ƒxxxxx += 18;
         } else if (â˜ƒ >= â˜ƒxx && â˜ƒ >= â˜ƒxxxx && â˜ƒ < â˜ƒxx + 16 && â˜ƒ < â˜ƒxxxx + 18) {
            â˜ƒxxxxx += 36;
         }

         this.blit(â˜ƒ, â˜ƒxx, â˜ƒxxxx - 1, 0, â˜ƒxxxxx, 16, 18);
      }
   }

   private void renderRecipes(int var1, int var2, int var3) {
      List<StonecutterRecipe> â˜ƒ = this.menu.getRecipes();

      for(int â˜ƒx = this.startIndex; â˜ƒx < â˜ƒ && â˜ƒx < this.menu.getNumRecipes(); ++â˜ƒx) {
         int â˜ƒxx = â˜ƒx - this.startIndex;
         int â˜ƒxxx = â˜ƒ + â˜ƒxx % 4 * 16;
         int â˜ƒxxxx = â˜ƒxx / 4;
         int â˜ƒxxxxx = â˜ƒ + â˜ƒxxxx * 18 + 2;
         this.minecraft.getItemRenderer().renderAndDecorateItem(((StonecutterRecipe)â˜ƒ.get(â˜ƒx)).getResultItem(), â˜ƒxxx, â˜ƒxxxxx);
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      this.scrolling = false;
      if (this.displayRecipes) {
         int â˜ƒ = this.leftPos + 52;
         int â˜ƒx = this.topPos + 14;
         int â˜ƒxx = this.startIndex + 12;

         for(int â˜ƒxxx = this.startIndex; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒxxx - this.startIndex;
            double â˜ƒxxxxx = â˜ƒ - (double)(â˜ƒ + â˜ƒxxxx % 4 * 16);
            double â˜ƒxxxxxx = â˜ƒ - (double)(â˜ƒx + â˜ƒxxxx / 4 * 18);
            if (â˜ƒxxxxx >= 0.0 && â˜ƒxxxxxx >= 0.0 && â˜ƒxxxxx < 16.0 && â˜ƒxxxxxx < 18.0 && this.menu.clickMenuButton(this.minecraft.player, â˜ƒxxx)) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
               this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, â˜ƒxxx);
               return true;
            }
         }

         â˜ƒ = this.leftPos + 119;
         â˜ƒx = this.topPos + 9;
         if (â˜ƒ >= (double)â˜ƒ && â˜ƒ < (double)(â˜ƒ + 12) && â˜ƒ >= (double)â˜ƒx && â˜ƒ < (double)(â˜ƒx + 54)) {
            this.scrolling = true;
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (this.scrolling && this.isScrollBarActive()) {
         int â˜ƒ = this.topPos + 14;
         int â˜ƒx = â˜ƒ + 54;
         this.scrollOffs = ((float)â˜ƒ - (float)â˜ƒ - 7.5F) / ((float)(â˜ƒx - â˜ƒ) - 15.0F);
         this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
         this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5) * 4;
         return true;
      } else {
         return super.mouseDragged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      if (this.isScrollBarActive()) {
         int â˜ƒ = this.getOffscreenRows();
         this.scrollOffs = (float)((double)this.scrollOffs - â˜ƒ / (double)â˜ƒ);
         this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
         this.startIndex = (int)((double)(this.scrollOffs * (float)â˜ƒ) + 0.5) * 4;
      }

      return true;
   }

   private boolean isScrollBarActive() {
      return this.displayRecipes && this.menu.getNumRecipes() > 12;
   }

   protected int getOffscreenRows() {
      return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
   }

   private void containerChanged() {
      this.displayRecipes = this.menu.hasInputItem();
      if (!this.displayRecipes) {
         this.scrollOffs = 0.0F;
         this.startIndex = 0;
      }
   }
}
