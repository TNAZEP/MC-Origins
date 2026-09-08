package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractCookingRecipe implements Recipe<Container> {
   protected final RecipeType<?> type;
   protected final ResourceLocation id;
   protected final String group;
   protected final Ingredient ingredient;
   protected final ItemStack result;
   protected final float experience;
   protected final int cookingTime;

   public AbstractCookingRecipe(RecipeType<?> var1, ResourceLocation var2, String var3, Ingredient var4, ItemStack var5, float var6, int var7) {
      this.type = â˜ƒ;
      this.id = â˜ƒ;
      this.group = â˜ƒ;
      this.ingredient = â˜ƒ;
      this.result = â˜ƒ;
      this.experience = â˜ƒ;
      this.cookingTime = â˜ƒ;
   }

   @Override
   public boolean matches(Container var1, Level var2) {
      return this.ingredient.test(â˜ƒ.getItem(0));
   }

   @Override
   public ItemStack assemble(Container var1) {
      return this.result.copy();
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return true;
   }

   @Override
   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> â˜ƒ = NonNullList.create();
      â˜ƒ.add(this.ingredient);
      return â˜ƒ;
   }

   public float getExperience() {
      return this.experience;
   }

   @Override
   public ItemStack getResultItem() {
      return this.result;
   }

   @Override
   public String getGroup() {
      return this.group;
   }

   public int getCookingTime() {
      return this.cookingTime;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public RecipeType<?> getType() {
      return this.type;
   }
}
