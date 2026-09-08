package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class OverlayRecipeComponent extends GuiComponent implements Widget, GuiEventListener {
   static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");
   private static final int MAX_ROW = 4;
   private static final int MAX_ROW_LARGE = 5;
   private static final float ITEM_RENDER_SCALE = 0.375F;
   private final List<OverlayRecipeComponent.OverlayRecipeButton> recipeButtons = Lists.<OverlayRecipeComponent.OverlayRecipeButton>newArrayList();
   private boolean isVisible;
   private int x;
   private int y;
   Minecraft minecraft;
   private RecipeCollection collection;
   @Nullable
   private Recipe<?> lastRecipeClicked;
   float time;
   boolean isFurnaceMenu;

   public void init(Minecraft var1, RecipeCollection var2, int var3, int var4, int var5, int var6, float var7) {
      this.minecraft = â˜ƒ;
      this.collection = â˜ƒ;
      if (â˜ƒ.player.containerMenu instanceof AbstractFurnaceMenu) {
         this.isFurnaceMenu = true;
      }

      boolean â˜ƒ = â˜ƒ.player.getRecipeBook().isFiltering((RecipeBookMenu<?>)â˜ƒ.player.containerMenu);
      List<Recipe<?>> â˜ƒx = â˜ƒ.getDisplayRecipes(true);
      List<Recipe<?>> â˜ƒxx = â˜ƒ ? Collections.emptyList() : â˜ƒ.getDisplayRecipes(false);
      int â˜ƒxxx = â˜ƒx.size();
      int â˜ƒxxxx = â˜ƒxxx + â˜ƒxx.size();
      int â˜ƒxxxxx = â˜ƒxxxx <= 16 ? 4 : 5;
      int â˜ƒxxxxxx = (int)Math.ceil((double)((float)â˜ƒxxxx / (float)â˜ƒxxxxx));
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      int â˜ƒxxxxxxx = 25;
      float â˜ƒxxxxxxxx = (float)(this.x + Math.min(â˜ƒxxxx, â˜ƒxxxxx) * 25);
      float â˜ƒxxxxxxxxx = (float)(â˜ƒ + 50);
      if (â˜ƒxxxxxxxx > â˜ƒxxxxxxxxx) {
         this.x = (int)((float)this.x - â˜ƒ * (float)((int)((â˜ƒxxxxxxxx - â˜ƒxxxxxxxxx) / â˜ƒ)));
      }

      float â˜ƒ = (float)(this.y + â˜ƒxxxxxx * 25);
      float â˜ƒx = (float)(â˜ƒ + 50);
      if (â˜ƒ > â˜ƒx) {
         this.y = (int)((float)this.y - â˜ƒ * (float)Mth.ceil((â˜ƒ - â˜ƒx) / â˜ƒ));
      }

      float â˜ƒ = (float)this.y;
      float â˜ƒx = (float)(â˜ƒ - 100);
      if (â˜ƒ < â˜ƒx) {
         this.y = (int)((float)this.y - â˜ƒ * (float)Mth.ceil((â˜ƒ - â˜ƒx) / â˜ƒ));
      }

      this.isVisible = true;
      this.recipeButtons.clear();

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒxxxx; ++â˜ƒ) {
         boolean â˜ƒx = â˜ƒ < â˜ƒxxx;
         Recipe<?> â˜ƒxx = â˜ƒx ? (Recipe)â˜ƒx.get(â˜ƒ) : (Recipe)â˜ƒxx.get(â˜ƒ - â˜ƒxxx);
         int â˜ƒxxx = this.x + 4 + 25 * (â˜ƒ % â˜ƒxxxxx);
         int â˜ƒxxxx = this.y + 5 + 25 * (â˜ƒ / â˜ƒxxxxx);
         if (this.isFurnaceMenu) {
            this.recipeButtons.add(new OverlayRecipeComponent.OverlaySmeltingRecipeButton(â˜ƒxxx, â˜ƒxxxx, â˜ƒxx, â˜ƒx));
         } else {
            this.recipeButtons.add(new OverlayRecipeComponent.OverlayRecipeButton(â˜ƒxxx, â˜ƒxxxx, â˜ƒxx, â˜ƒx));
         }
      }

      this.lastRecipeClicked = null;
   }

   @Override
   public boolean changeFocus(boolean var1) {
      return false;
   }

   public RecipeCollection getRecipeCollection() {
      return this.collection;
   }

   @Nullable
   public Recipe<?> getLastRecipeClicked() {
      return this.lastRecipeClicked;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (â˜ƒ != 0) {
         return false;
      } else {
         for(OverlayRecipeComponent.OverlayRecipeButton â˜ƒ : this.recipeButtons) {
            if (â˜ƒ.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ)) {
               this.lastRecipeClicked = â˜ƒ.recipe;
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isMouseOver(double var1, double var3) {
      return false;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.isVisible) {
         this.time += â˜ƒ;
         RenderSystem.enableBlend();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 0.0, 170.0);
         int â˜ƒ = this.recipeButtons.size() <= 16 ? 4 : 5;
         int â˜ƒx = Math.min(this.recipeButtons.size(), â˜ƒ);
         int â˜ƒxx = Mth.ceil((float)this.recipeButtons.size() / (float)â˜ƒ);
         int â˜ƒxxx = 24;
         int â˜ƒxxxx = 4;
         int â˜ƒxxxxx = 82;
         int â˜ƒxxxxxx = 208;
         this.nineInchSprite(â˜ƒ, â˜ƒx, â˜ƒxx, 24, 4, 82, 208);
         RenderSystem.disableBlend();

         for(OverlayRecipeComponent.OverlayRecipeButton â˜ƒxxxxxxx : this.recipeButtons) {
            â˜ƒxxxxxxx.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.popPose();
      }
   }

   private void nineInchSprite(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.blit(â˜ƒ, this.x, this.y, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.blit(â˜ƒ, this.x + â˜ƒ * 2 + â˜ƒ * â˜ƒ, this.y, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.blit(â˜ƒ, this.x, this.y + â˜ƒ * 2 + â˜ƒ * â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
      this.blit(â˜ƒ, this.x + â˜ƒ * 2 + â˜ƒ * â˜ƒ, this.y + â˜ƒ * 2 + â˜ƒ * â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.blit(â˜ƒ, this.x + â˜ƒ + â˜ƒ * â˜ƒ, this.y, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.blit(â˜ƒ, this.x + â˜ƒ + (â˜ƒ + 1) * â˜ƒ, this.y, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            if (â˜ƒ == 0) {
               this.blit(â˜ƒ, this.x, this.y + â˜ƒ + â˜ƒx * â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
               this.blit(â˜ƒ, this.x, this.y + â˜ƒ + (â˜ƒx + 1) * â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
            }

            this.blit(â˜ƒ, this.x + â˜ƒ + â˜ƒ * â˜ƒ, this.y + â˜ƒ + â˜ƒx * â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
            this.blit(â˜ƒ, this.x + â˜ƒ + (â˜ƒ + 1) * â˜ƒ, this.y + â˜ƒ + â˜ƒx * â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
            this.blit(â˜ƒ, this.x + â˜ƒ + â˜ƒ * â˜ƒ, this.y + â˜ƒ + (â˜ƒx + 1) * â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
            this.blit(â˜ƒ, this.x + â˜ƒ + (â˜ƒ + 1) * â˜ƒ - 1, this.y + â˜ƒ + (â˜ƒx + 1) * â˜ƒ - 1, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + 1, â˜ƒ + 1);
            if (â˜ƒ == â˜ƒ - 1) {
               this.blit(â˜ƒ, this.x + â˜ƒ * 2 + â˜ƒ * â˜ƒ, this.y + â˜ƒ + â˜ƒx * â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
               this.blit(â˜ƒ, this.x + â˜ƒ * 2 + â˜ƒ * â˜ƒ, this.y + â˜ƒ + (â˜ƒx + 1) * â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }

         this.blit(â˜ƒ, this.x + â˜ƒ + â˜ƒ * â˜ƒ, this.y + â˜ƒ * 2 + â˜ƒ * â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
         this.blit(â˜ƒ, this.x + â˜ƒ + (â˜ƒ + 1) * â˜ƒ, this.y + â˜ƒ * 2 + â˜ƒ * â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void setVisible(boolean var1) {
      this.isVisible = â˜ƒ;
   }

   public boolean isVisible() {
      return this.isVisible;
   }

   class OverlayRecipeButton extends AbstractWidget implements PlaceRecipe<Ingredient> {
      final Recipe<?> recipe;
      private final boolean isCraftable;
      protected final List<OverlayRecipeComponent.OverlayRecipeButton.Pos> ingredientPos = Lists.<OverlayRecipeComponent.OverlayRecipeButton.Pos>newArrayList();

      public OverlayRecipeButton(int var2, int var3, Recipe<?> var4, boolean var5) {
         super(â˜ƒ, â˜ƒ, 200, 20, TextComponent.EMPTY);
         this.width = 24;
         this.height = 24;
         this.recipe = â˜ƒ;
         this.isCraftable = â˜ƒ;
         this.calculateIngredientsPositions(â˜ƒ);
      }

      protected void calculateIngredientsPositions(Recipe<?> var1) {
         this.placeRecipe(3, 3, -1, â˜ƒ, â˜ƒ.getIngredients().iterator(), 0);
      }

      @Override
      public void updateNarration(NarrationElementOutput var1) {
         this.defaultButtonNarrationText(â˜ƒ);
      }

      @Override
      public void addItemToSlot(Iterator<Ingredient> var1, int var2, int var3, int var4, int var5) {
         ItemStack[] â˜ƒ = ((Ingredient)â˜ƒ.next()).getItems();
         if (â˜ƒ.length != 0) {
            this.ingredientPos.add(new OverlayRecipeComponent.OverlayRecipeButton.Pos(3 + â˜ƒ * 7, 3 + â˜ƒ * 7, â˜ƒ));
         }
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         RenderSystem.setShaderTexture(0, OverlayRecipeComponent.RECIPE_BOOK_LOCATION);
         int â˜ƒ = 152;
         if (!this.isCraftable) {
            â˜ƒ += 26;
         }

         int â˜ƒ = OverlayRecipeComponent.this.isFurnaceMenu ? 130 : 78;
         if (this.isHovered()) {
            â˜ƒ += 26;
         }

         this.blit(â˜ƒ, this.x, this.y, â˜ƒ, â˜ƒ, this.width, this.height);
         PoseStack â˜ƒ = RenderSystem.getModelViewStack();
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)(this.x + 2), (double)(this.y + 2), 125.0);

         for(OverlayRecipeComponent.OverlayRecipeButton.Pos â˜ƒx : this.ingredientPos) {
            â˜ƒ.pushPose();
            â˜ƒ.translate((double)â˜ƒx.x, (double)â˜ƒx.y, 0.0);
            â˜ƒ.scale(0.375F, 0.375F, 1.0F);
            â˜ƒ.translate(-8.0, -8.0, 0.0);
            RenderSystem.applyModelViewMatrix();
            OverlayRecipeComponent.this.minecraft
               .getItemRenderer()
               .renderAndDecorateItem(â˜ƒx.ingredients[Mth.floor(OverlayRecipeComponent.this.time / 30.0F) % â˜ƒx.ingredients.length], 0, 0);
            â˜ƒ.popPose();
         }

         â˜ƒ.popPose();
         RenderSystem.applyModelViewMatrix();
      }

      protected class Pos {
         public final ItemStack[] ingredients;
         public final int x;
         public final int y;

         public Pos(int var2, int var3, ItemStack[] var4) {
            this.x = â˜ƒ;
            this.y = â˜ƒ;
            this.ingredients = â˜ƒ;
         }
      }
   }

   class OverlaySmeltingRecipeButton extends OverlayRecipeComponent.OverlayRecipeButton {
      public OverlaySmeltingRecipeButton(int var2, int var3, Recipe<?> var4, boolean var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      protected void calculateIngredientsPositions(Recipe<?> var1) {
         ItemStack[] â˜ƒ = â˜ƒ.getIngredients().get(0).getItems();
         this.ingredientPos.add(new OverlayRecipeComponent.OverlayRecipeButton.Pos(10, 10, â˜ƒ));
      }
   }
}
