package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class ResultContainer implements Container, RecipeHolder {
   private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
   @Nullable
   private Recipe<?> recipeUsed;

   @Override
   public int getContainerSize() {
      return 1;
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.itemStacks) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getItem(int var1) {
      return this.itemStacks.get(0);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      return ContainerHelper.takeItem(this.itemStacks, 0);
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      return ContainerHelper.takeItem(this.itemStacks, 0);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.itemStacks.set(0, â˜ƒ);
   }

   @Override
   public void setChanged() {
   }

   @Override
   public boolean stillValid(Player var1) {
      return true;
   }

   @Override
   public void clearContent() {
      this.itemStacks.clear();
   }

   @Override
   public void setRecipeUsed(@Nullable Recipe<?> var1) {
      this.recipeUsed = â˜ƒ;
   }

   @Nullable
   @Override
   public Recipe<?> getRecipeUsed() {
      return this.recipeUsed;
   }
}
