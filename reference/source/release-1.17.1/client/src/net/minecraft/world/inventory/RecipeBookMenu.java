package net.minecraft.world.inventory;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.crafting.Recipe;

public abstract class RecipeBookMenu<C extends Container> extends AbstractContainerMenu {
   public RecipeBookMenu(MenuType<?> var1, int var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public void handlePlacement(boolean var1, Recipe<?> var2, ServerPlayer var3) {
      new ServerPlaceRecipe<>(this).recipeClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public abstract void fillCraftSlotsStackedContents(StackedContents var1);

   public abstract void clearCraftingContent();

   public abstract boolean recipeMatches(Recipe<? super C> var1);

   public abstract int getResultSlotIndex();

   public abstract int getGridWidth();

   public abstract int getGridHeight();

   public abstract int getSize();

   public abstract RecipeBookType getRecipeBookType();

   public abstract boolean shouldMoveToInventory(int var1);
}
