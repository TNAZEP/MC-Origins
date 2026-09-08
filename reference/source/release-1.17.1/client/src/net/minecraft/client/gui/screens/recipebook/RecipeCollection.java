package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeCollection {
   private final List<Recipe<?>> recipes;
   private final boolean singleResultItem;
   private final Set<Recipe<?>> craftable = Sets.<Recipe<?>>newHashSet();
   private final Set<Recipe<?>> fitsDimensions = Sets.<Recipe<?>>newHashSet();
   private final Set<Recipe<?>> known = Sets.<Recipe<?>>newHashSet();

   public RecipeCollection(List<Recipe<?>> var1) {
      this.recipes = ImmutableList.copyOf(â˜ƒ);
      if (â˜ƒ.size() <= 1) {
         this.singleResultItem = true;
      } else {
         this.singleResultItem = allRecipesHaveSameResult(â˜ƒ);
      }
   }

   private static boolean allRecipesHaveSameResult(List<Recipe<?>> var0) {
      int â˜ƒ = â˜ƒ.size();
      ItemStack â˜ƒx = ((Recipe)â˜ƒ.get(0)).getResultItem();

      for(int â˜ƒxx = 1; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         ItemStack â˜ƒxxx = ((Recipe)â˜ƒ.get(â˜ƒxx)).getResultItem();
         if (!ItemStack.isSame(â˜ƒx, â˜ƒxxx) || !ItemStack.tagMatches(â˜ƒx, â˜ƒxxx)) {
            return false;
         }
      }

      return true;
   }

   public boolean hasKnownRecipes() {
      return !this.known.isEmpty();
   }

   public void updateKnownRecipes(RecipeBook var1) {
      for(Recipe<?> â˜ƒ : this.recipes) {
         if (â˜ƒ.contains(â˜ƒ)) {
            this.known.add(â˜ƒ);
         }
      }
   }

   public void canCraft(StackedContents var1, int var2, int var3, RecipeBook var4) {
      for(Recipe<?> â˜ƒ : this.recipes) {
         boolean â˜ƒx = â˜ƒ.canCraftInDimensions(â˜ƒ, â˜ƒ) && â˜ƒ.contains(â˜ƒ);
         if (â˜ƒx) {
            this.fitsDimensions.add(â˜ƒ);
         } else {
            this.fitsDimensions.remove(â˜ƒ);
         }

         if (â˜ƒx && â˜ƒ.canCraft(â˜ƒ, null)) {
            this.craftable.add(â˜ƒ);
         } else {
            this.craftable.remove(â˜ƒ);
         }
      }
   }

   public boolean isCraftable(Recipe<?> var1) {
      return this.craftable.contains(â˜ƒ);
   }

   public boolean hasCraftable() {
      return !this.craftable.isEmpty();
   }

   public boolean hasFitting() {
      return !this.fitsDimensions.isEmpty();
   }

   public List<Recipe<?>> getRecipes() {
      return this.recipes;
   }

   public List<Recipe<?>> getRecipes(boolean var1) {
      List<Recipe<?>> â˜ƒ = Lists.<Recipe<?>>newArrayList();
      Set<Recipe<?>> â˜ƒx = â˜ƒ ? this.craftable : this.fitsDimensions;

      for(Recipe<?> â˜ƒxx : this.recipes) {
         if (â˜ƒx.contains(â˜ƒxx)) {
            â˜ƒ.add(â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   public List<Recipe<?>> getDisplayRecipes(boolean var1) {
      List<Recipe<?>> â˜ƒ = Lists.<Recipe<?>>newArrayList();

      for(Recipe<?> â˜ƒx : this.recipes) {
         if (this.fitsDimensions.contains(â˜ƒx) && this.craftable.contains(â˜ƒx) == â˜ƒ) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   public boolean hasSingleResultItem() {
      return this.singleResultItem;
   }
}
