package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class ResultSlot extends Slot {
   private final CraftingContainer craftSlots;
   private final Player player;
   private int removeCount;

   public ResultSlot(Player var1, CraftingContainer var2, Container var3, int var4, int var5, int var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.player = â˜ƒ;
      this.craftSlots = â˜ƒ;
   }

   @Override
   public boolean mayPlace(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack remove(int var1) {
      if (this.hasItem()) {
         this.removeCount += Math.min(â˜ƒ, this.getItem().getCount());
      }

      return super.remove(â˜ƒ);
   }

   @Override
   protected void onQuickCraft(ItemStack var1, int var2) {
      this.removeCount += â˜ƒ;
      this.checkTakeAchievements(â˜ƒ);
   }

   @Override
   protected void onSwapCraft(int var1) {
      this.removeCount += â˜ƒ;
   }

   @Override
   protected void checkTakeAchievements(ItemStack var1) {
      if (this.removeCount > 0) {
         â˜ƒ.onCraftedBy(this.player.level, this.player, this.removeCount);
      }

      if (this.container instanceof RecipeHolder) {
         ((RecipeHolder)this.container).awardUsedRecipes(this.player);
      }

      this.removeCount = 0;
   }

   @Override
   public void onTake(Player var1, ItemStack var2) {
      this.checkTakeAchievements(â˜ƒ);
      NonNullList<ItemStack> â˜ƒ = â˜ƒ.level.getRecipeManager().getRemainingItemsFor(RecipeType.CRAFTING, this.craftSlots, â˜ƒ.level);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         ItemStack â˜ƒxx = this.craftSlots.getItem(â˜ƒx);
         ItemStack â˜ƒxxx = â˜ƒ.get(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            this.craftSlots.removeItem(â˜ƒx, 1);
            â˜ƒxx = this.craftSlots.getItem(â˜ƒx);
         }

         if (!â˜ƒxxx.isEmpty()) {
            if (â˜ƒxx.isEmpty()) {
               this.craftSlots.setItem(â˜ƒx, â˜ƒxxx);
            } else if (ItemStack.isSame(â˜ƒxx, â˜ƒxxx) && ItemStack.tagMatches(â˜ƒxx, â˜ƒxxx)) {
               â˜ƒxxx.grow(â˜ƒxx.getCount());
               this.craftSlots.setItem(â˜ƒx, â˜ƒxxx);
            } else if (!this.player.getInventory().add(â˜ƒxxx)) {
               this.player.drop(â˜ƒxxx, false);
            }
         }
      }
   }
}
