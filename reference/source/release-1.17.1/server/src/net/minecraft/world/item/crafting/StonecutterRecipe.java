package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class StonecutterRecipe extends SingleItemRecipe {
   public StonecutterRecipe(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4) {
      super(RecipeType.STONECUTTING, RecipeSerializer.STONECUTTER, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean matches(Container var1, Level var2) {
      return this.ingredient.test(â˜ƒ.getItem(0));
   }

   @Override
   public ItemStack getToastSymbol() {
      return new ItemStack(Blocks.STONECUTTER);
   }
}
