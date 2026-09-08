package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public interface Recipe<C extends Container> {
   boolean matches(C var1, Level var2);

   ItemStack assemble(C var1);

   boolean canCraftInDimensions(int var1, int var2);

   ItemStack getResultItem();

   default NonNullList<ItemStack> getRemainingItems(C var1) {
      NonNullList<ItemStack> â˜ƒ = NonNullList.withSize(â˜ƒ.getContainerSize(), ItemStack.EMPTY);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         Item â˜ƒxx = â˜ƒ.getItem(â˜ƒx).getItem();
         if (â˜ƒxx.hasCraftingRemainingItem()) {
            â˜ƒ.set(â˜ƒx, new ItemStack(â˜ƒxx.getCraftingRemainingItem()));
         }
      }

      return â˜ƒ;
   }

   default NonNullList<Ingredient> getIngredients() {
      return NonNullList.create();
   }

   default boolean isSpecial() {
      return false;
   }

   default String getGroup() {
      return "";
   }

   default ItemStack getToastSymbol() {
      return new ItemStack(Blocks.CRAFTING_TABLE);
   }

   ResourceLocation getId();

   RecipeSerializer<?> getSerializer();

   RecipeType<?> getType();

   default boolean isIncomplete() {
      NonNullList<Ingredient> â˜ƒ = this.getIngredients();
      return â˜ƒ.isEmpty() || â˜ƒ.stream().anyMatch(var0 -> var0.getItems().length == 0);
   }
}
