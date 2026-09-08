package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeButton extends AbstractWidget {
   private static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");
   private static final float ANIMATION_TIME = 15.0F;
   private static final int BACKGROUND_SIZE = 25;
   public static final int TICKS_TO_SWAP = 30;
   private static final Component MORE_RECIPES_TOOLTIP = new TranslatableComponent("gui.recipebook.moreRecipes");
   private RecipeBookMenu<?> menu;
   private RecipeBook book;
   private RecipeCollection collection;
   private float time;
   private float animationTime;
   private int currentIndex;

   public RecipeButton() {
      super(0, 0, 25, 25, TextComponent.EMPTY);
   }

   public void init(RecipeCollection var1, RecipeBookPage var2) {
      this.collection = â˜ƒ;
      this.menu = (RecipeBookMenu)â˜ƒ.getMinecraft().player.containerMenu;
      this.book = â˜ƒ.getRecipeBook();
      List<Recipe<?>> â˜ƒ = â˜ƒ.getRecipes(this.book.isFiltering(this.menu));

      for(Recipe<?> â˜ƒx : â˜ƒ) {
         if (this.book.willHighlight(â˜ƒx)) {
            â˜ƒ.recipesShown(â˜ƒ);
            this.animationTime = 15.0F;
            break;
         }
      }
   }

   public RecipeCollection getCollection() {
      return this.collection;
   }

   public void setPosition(int var1, int var2) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      if (!Screen.hasControlDown()) {
         this.time += â˜ƒ;
      }

      Minecraft â˜ƒ = Minecraft.getInstance();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
      int â˜ƒx = 29;
      if (!this.collection.hasCraftable()) {
         â˜ƒx += 25;
      }

      int â˜ƒ = 206;
      if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
         â˜ƒ += 25;
      }

      boolean â˜ƒ = this.animationTime > 0.0F;
      PoseStack â˜ƒx = RenderSystem.getModelViewStack();
      if (â˜ƒ) {
         float â˜ƒxx = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float) Math.PI));
         â˜ƒx.pushPose();
         â˜ƒx.translate((double)(this.x + 8), (double)(this.y + 12), 0.0);
         â˜ƒx.scale(â˜ƒxx, â˜ƒxx, 1.0F);
         â˜ƒx.translate((double)(-(this.x + 8)), (double)(-(this.y + 12)), 0.0);
         RenderSystem.applyModelViewMatrix();
         this.animationTime -= â˜ƒ;
      }

      this.blit(â˜ƒ, this.x, this.y, â˜ƒx, â˜ƒ, this.width, this.height);
      List<Recipe<?>> â˜ƒ = this.getOrderedRecipes();
      this.currentIndex = Mth.floor(this.time / 30.0F) % â˜ƒ.size();
      ItemStack â˜ƒx = ((Recipe)â˜ƒ.get(this.currentIndex)).getResultItem();
      int â˜ƒxx = 4;
      if (this.collection.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
         â˜ƒ.getItemRenderer().renderAndDecorateItem(â˜ƒx, this.x + â˜ƒxx + 1, this.y + â˜ƒxx + 1, 0, 10);
         --â˜ƒxx;
      }

      â˜ƒ.getItemRenderer().renderAndDecorateFakeItem(â˜ƒx, this.x + â˜ƒxx, this.y + â˜ƒxx);
      if (â˜ƒ) {
         â˜ƒx.popPose();
         RenderSystem.applyModelViewMatrix();
      }
   }

   private List<Recipe<?>> getOrderedRecipes() {
      List<Recipe<?>> â˜ƒ = this.collection.getDisplayRecipes(true);
      if (!this.book.isFiltering(this.menu)) {
         â˜ƒ.addAll(this.collection.getDisplayRecipes(false));
      }

      return â˜ƒ;
   }

   public boolean isOnlyOption() {
      return this.getOrderedRecipes().size() == 1;
   }

   public Recipe<?> getRecipe() {
      List<Recipe<?>> â˜ƒ = this.getOrderedRecipes();
      return (Recipe<?>)â˜ƒ.get(this.currentIndex);
   }

   public List<Component> getTooltipText(Screen var1) {
      ItemStack â˜ƒ = ((Recipe)this.getOrderedRecipes().get(this.currentIndex)).getResultItem();
      List<Component> â˜ƒx = Lists.<Component>newArrayList(â˜ƒ.getTooltipFromItem(â˜ƒ));
      if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
         â˜ƒx.add(MORE_RECIPES_TOOLTIP);
      }

      return â˜ƒx;
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      ItemStack â˜ƒ = ((Recipe)this.getOrderedRecipes().get(this.currentIndex)).getResultItem();
      â˜ƒ.add(NarratedElementType.TITLE, new TranslatableComponent("narration.recipe", â˜ƒ.getHoverName()));
      if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
         â˜ƒ.add(
            NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.hovered"), new TranslatableComponent("narration.recipe.usage.more")
         );
      } else {
         â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.hovered"));
      }
   }

   @Override
   public int getWidth() {
      return 25;
   }

   @Override
   protected boolean isValidClickButton(int var1) {
      return â˜ƒ == 0 || â˜ƒ == 1;
   }
}
