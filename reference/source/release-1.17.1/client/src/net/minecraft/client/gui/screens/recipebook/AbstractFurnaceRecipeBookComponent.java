package net.minecraft.client.gui.screens.recipebook;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractFurnaceRecipeBookComponent extends RecipeBookComponent {
   @Nullable
   private Ingredient fuels;

   @Override
   protected void initFilterButtonTextures() {
      this.filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
   }

   @Override
   public void slotClicked(@Nullable Slot var1) {
      super.slotClicked(â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.index < this.menu.getSize()) {
         this.ghostRecipe.clear();
      }
   }

   @Override
   public void setupGhostRecipe(Recipe<?> var1, List<Slot> var2) {
      ItemStack â˜ƒ = â˜ƒ.getResultItem();
      this.ghostRecipe.setRecipe(â˜ƒ);
      this.ghostRecipe.addIngredient(Ingredient.of(â˜ƒ), ((Slot)â˜ƒ.get(2)).x, ((Slot)â˜ƒ.get(2)).y);
      NonNullList<Ingredient> â˜ƒx = â˜ƒ.getIngredients();
      Slot â˜ƒxx = (Slot)â˜ƒ.get(1);
      if (â˜ƒxx.getItem().isEmpty()) {
         if (this.fuels == null) {
            this.fuels = Ingredient.of(this.getFuelItems().stream().map(ItemStack::new));
         }

         this.ghostRecipe.addIngredient(this.fuels, â˜ƒxx.x, â˜ƒxx.y);
      }

      Iterator<Ingredient> â˜ƒ = â˜ƒx.iterator();

      for(int â˜ƒx = 0; â˜ƒx < 2; ++â˜ƒx) {
         if (!â˜ƒ.hasNext()) {
            return;
         }

         Ingredient â˜ƒxx = (Ingredient)â˜ƒ.next();
         if (!â˜ƒxx.isEmpty()) {
            Slot â˜ƒxxx = (Slot)â˜ƒ.get(â˜ƒx);
            this.ghostRecipe.addIngredient(â˜ƒxx, â˜ƒxxx.x, â˜ƒxxx.y);
         }
      }
   }

   protected abstract Set<Item> getFuelItems();
}
