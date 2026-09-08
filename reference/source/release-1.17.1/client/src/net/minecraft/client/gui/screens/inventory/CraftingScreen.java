package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;

public class CraftingScreen extends AbstractContainerScreen<CraftingMenu> implements RecipeUpdateListener {
   private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/crafting_table.png");
   private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
   private boolean widthTooNarrow;

   public CraftingScreen(CraftingMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void init() {
      super.init();
      this.widthTooNarrow = this.width < 379;
      this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
      this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
      this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, var1 -> {
         this.recipeBookComponent.toggleVisibility();
         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
         ((ImageButton)var1).setPosition(this.leftPos + 5, this.height / 2 - 49);
      }));
      this.addWidget(this.recipeBookComponent);
      this.setInitialFocus(this.recipeBookComponent);
      this.titleLabelX = 29;
   }

   @Override
   public void containerTick() {
      super.containerTick();
      this.recipeBookComponent.tick();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
         this.renderBg(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.recipeBookComponent.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         this.recipeBookComponent.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.recipeBookComponent.renderGhostRecipe(â˜ƒ, this.leftPos, this.topPos, true, â˜ƒ);
      }

      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
      this.recipeBookComponent.renderTooltip(â˜ƒ, this.leftPos, this.topPos, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, CRAFTING_TABLE_LOCATION);
      int â˜ƒ = this.leftPos;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
   }

   @Override
   protected boolean isHovering(int var1, int var2, int var3, int var4, double var5, double var7) {
      return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.recipeBookComponent.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         this.setFocused(this.recipeBookComponent);
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? true : super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected boolean hasClickedOutside(double var1, double var3, int var5, int var6, int var7) {
      boolean â˜ƒ = â˜ƒ < (double)â˜ƒ || â˜ƒ < (double)â˜ƒ || â˜ƒ >= (double)(â˜ƒ + this.imageWidth) || â˜ƒ >= (double)(â˜ƒ + this.imageHeight);
      return this.recipeBookComponent.hasClickedOutside(â˜ƒ, â˜ƒ, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, â˜ƒ) && â˜ƒ;
   }

   @Override
   protected void slotClicked(Slot var1, int var2, int var3, ClickType var4) {
      super.slotClicked(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.recipeBookComponent.slotClicked(â˜ƒ);
   }

   @Override
   public void recipesUpdated() {
      this.recipeBookComponent.recipesUpdated();
   }

   @Override
   public void removed() {
      this.recipeBookComponent.removed();
      super.removed();
   }

   @Override
   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBookComponent;
   }
}
