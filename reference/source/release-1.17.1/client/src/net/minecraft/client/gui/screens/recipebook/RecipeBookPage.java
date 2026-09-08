package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBookPage {
   public static final int ITEMS_PER_PAGE = 20;
   private final List<RecipeButton> buttons = Lists.<RecipeButton>newArrayListWithCapacity(20);
   @Nullable
   private RecipeButton hoveredButton;
   private final OverlayRecipeComponent overlay = new OverlayRecipeComponent();
   private Minecraft minecraft;
   private final List<RecipeShownListener> showListeners = Lists.<RecipeShownListener>newArrayList();
   private List<RecipeCollection> recipeCollections = ImmutableList.of();
   private StateSwitchingButton forwardButton;
   private StateSwitchingButton backButton;
   private int totalPages;
   private int currentPage;
   private RecipeBook recipeBook;
   @Nullable
   private Recipe<?> lastClickedRecipe;
   @Nullable
   private RecipeCollection lastClickedRecipeCollection;

   public RecipeBookPage() {
      for(int â˜ƒ = 0; â˜ƒ < 20; ++â˜ƒ) {
         this.buttons.add(new RecipeButton());
      }
   }

   public void init(Minecraft var1, int var2, int var3) {
      this.minecraft = â˜ƒ;
      this.recipeBook = â˜ƒ.player.getRecipeBook();

      for(int â˜ƒ = 0; â˜ƒ < this.buttons.size(); ++â˜ƒ) {
         ((RecipeButton)this.buttons.get(â˜ƒ)).setPosition(â˜ƒ + 11 + 25 * (â˜ƒ % 5), â˜ƒ + 31 + 25 * (â˜ƒ / 5));
      }

      this.forwardButton = new StateSwitchingButton(â˜ƒ + 93, â˜ƒ + 137, 12, 17, false);
      this.forwardButton.initTextureValues(1, 208, 13, 18, RecipeBookComponent.RECIPE_BOOK_LOCATION);
      this.backButton = new StateSwitchingButton(â˜ƒ + 38, â˜ƒ + 137, 12, 17, true);
      this.backButton.initTextureValues(1, 208, 13, 18, RecipeBookComponent.RECIPE_BOOK_LOCATION);
   }

   public void addListener(RecipeBookComponent var1) {
      this.showListeners.remove(â˜ƒ);
      this.showListeners.add(â˜ƒ);
   }

   public void updateCollections(List<RecipeCollection> var1, boolean var2) {
      this.recipeCollections = â˜ƒ;
      this.totalPages = (int)Math.ceil((double)â˜ƒ.size() / 20.0);
      if (this.totalPages <= this.currentPage || â˜ƒ) {
         this.currentPage = 0;
      }

      this.updateButtonsForPage();
   }

   private void updateButtonsForPage() {
      int â˜ƒ = 20 * this.currentPage;

      for(int â˜ƒx = 0; â˜ƒx < this.buttons.size(); ++â˜ƒx) {
         RecipeButton â˜ƒxx = (RecipeButton)this.buttons.get(â˜ƒx);
         if (â˜ƒ + â˜ƒx < this.recipeCollections.size()) {
            RecipeCollection â˜ƒxxx = (RecipeCollection)this.recipeCollections.get(â˜ƒ + â˜ƒx);
            â˜ƒxx.init(â˜ƒxxx, this);
            â˜ƒxx.visible = true;
         } else {
            â˜ƒxx.visible = false;
         }
      }

      this.updateArrowButtons();
   }

   private void updateArrowButtons() {
      this.forwardButton.visible = this.totalPages > 1 && this.currentPage < this.totalPages - 1;
      this.backButton.visible = this.totalPages > 1 && this.currentPage > 0;
   }

   public void render(PoseStack var1, int var2, int var3, int var4, int var5, float var6) {
      if (this.totalPages > 1) {
         String â˜ƒ = this.currentPage + 1 + "/" + this.totalPages;
         int â˜ƒx = this.minecraft.font.width(â˜ƒ);
         this.minecraft.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ - â˜ƒx / 2 + 73), (float)(â˜ƒ + 141), -1);
      }

      this.hoveredButton = null;

      for(RecipeButton â˜ƒ : this.buttons) {
         â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.visible && â˜ƒ.isHovered()) {
            this.hoveredButton = â˜ƒ;
         }
      }

      this.backButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.forwardButton.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.overlay.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void renderTooltip(PoseStack var1, int var2, int var3) {
      if (this.minecraft.screen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
         this.minecraft.screen.renderComponentTooltip(â˜ƒ, this.hoveredButton.getTooltipText(this.minecraft.screen), â˜ƒ, â˜ƒ);
      }
   }

   @Nullable
   public Recipe<?> getLastClickedRecipe() {
      return this.lastClickedRecipe;
   }

   @Nullable
   public RecipeCollection getLastClickedRecipeCollection() {
      return this.lastClickedRecipeCollection;
   }

   public void setInvisible() {
      this.overlay.setVisible(false);
   }

   public boolean mouseClicked(double var1, double var3, int var5, int var6, int var7, int var8, int var9) {
      this.lastClickedRecipe = null;
      this.lastClickedRecipeCollection = null;
      if (this.overlay.isVisible()) {
         if (this.overlay.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
            this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
            this.lastClickedRecipeCollection = this.overlay.getRecipeCollection();
         } else {
            this.overlay.setVisible(false);
         }

         return true;
      } else if (this.forwardButton.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         ++this.currentPage;
         this.updateButtonsForPage();
         return true;
      } else if (this.backButton.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         --this.currentPage;
         this.updateButtonsForPage();
         return true;
      } else {
         for(RecipeButton â˜ƒ : this.buttons) {
            if (â˜ƒ.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
               if (â˜ƒ == 0) {
                  this.lastClickedRecipe = â˜ƒ.getRecipe();
                  this.lastClickedRecipeCollection = â˜ƒ.getCollection();
               } else if (â˜ƒ == 1 && !this.overlay.isVisible() && !â˜ƒ.isOnlyOption()) {
                  this.overlay.init(this.minecraft, â˜ƒ.getCollection(), â˜ƒ.x, â˜ƒ.y, â˜ƒ + â˜ƒ / 2, â˜ƒ + 13 + â˜ƒ / 2, (float)â˜ƒ.getWidth());
               }

               return true;
            }
         }

         return false;
      }
   }

   public void recipesShown(List<Recipe<?>> var1) {
      for(RecipeShownListener â˜ƒ : this.showListeners) {
         â˜ƒ.recipesShown(â˜ƒ);
      }
   }

   public Minecraft getMinecraft() {
      return this.minecraft;
   }

   public RecipeBook getRecipeBook() {
      return this.recipeBook;
   }

   protected void listButtons(Consumer<AbstractWidget> var1) {
      â˜ƒ.accept(this.forwardButton);
      â˜ƒ.accept(this.backButton);
      this.buttons.forEach(â˜ƒ);
   }
}
