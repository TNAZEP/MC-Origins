package net.minecraft.world.inventory;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SmithingMenu extends ItemCombinerMenu {
   private final Level level;
   @Nullable
   private UpgradeRecipe selectedRecipe;
   private final List<UpgradeRecipe> recipes;

   public SmithingMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public SmithingMenu(int var1, Inventory var2, ContainerLevelAccess var3) {
      super(MenuType.SMITHING, â˜ƒ, â˜ƒ, â˜ƒ);
      this.level = â˜ƒ.player.level;
      this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING);
   }

   @Override
   protected boolean isValidBlock(BlockState var1) {
      return â˜ƒ.is(Blocks.SMITHING_TABLE);
   }

   @Override
   protected boolean mayPickup(Player var1, boolean var2) {
      return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
   }

   @Override
   protected void onTake(Player var1, ItemStack var2) {
      â˜ƒ.onCraftedBy(â˜ƒ.level, â˜ƒ, â˜ƒ.getCount());
      this.resultSlots.awardUsedRecipes(â˜ƒ);
      this.shrinkStackInSlot(0);
      this.shrinkStackInSlot(1);
      this.access.execute((var0, var1x) -> var0.levelEvent(1044, var1x, 0));
   }

   private void shrinkStackInSlot(int var1) {
      ItemStack â˜ƒ = this.inputSlots.getItem(â˜ƒ);
      â˜ƒ.shrink(1);
      this.inputSlots.setItem(â˜ƒ, â˜ƒ);
   }

   @Override
   public void createResult() {
      List<UpgradeRecipe> â˜ƒ = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.inputSlots, this.level);
      if (â˜ƒ.isEmpty()) {
         this.resultSlots.setItem(0, ItemStack.EMPTY);
      } else {
         this.selectedRecipe = (UpgradeRecipe)â˜ƒ.get(0);
         ItemStack â˜ƒ = this.selectedRecipe.assemble(this.inputSlots);
         this.resultSlots.setRecipeUsed(this.selectedRecipe);
         this.resultSlots.setItem(0, â˜ƒ);
      }
   }

   @Override
   protected boolean shouldQuickMoveToAdditionalSlot(ItemStack var1) {
      return this.recipes.stream().anyMatch(var1x -> var1x.isAdditionIngredient(â˜ƒ));
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
      return â˜ƒ.container != this.resultSlots && super.canTakeItemForPickAll(â˜ƒ, â˜ƒ);
   }
}
