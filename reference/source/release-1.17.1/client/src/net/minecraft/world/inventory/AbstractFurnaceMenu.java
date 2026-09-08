package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public abstract class AbstractFurnaceMenu extends RecipeBookMenu<Container> {
   public static final int INGREDIENT_SLOT = 0;
   public static final int FUEL_SLOT = 1;
   public static final int RESULT_SLOT = 2;
   public static final int SLOT_COUNT = 3;
   public static final int DATA_COUNT = 4;
   private static final int INV_SLOT_START = 3;
   private static final int INV_SLOT_END = 30;
   private static final int USE_ROW_SLOT_START = 30;
   private static final int USE_ROW_SLOT_END = 39;
   private final Container container;
   private final ContainerData data;
   protected final Level level;
   private final RecipeType<? extends AbstractCookingRecipe> recipeType;
   private final RecipeBookType recipeBookType;

   protected AbstractFurnaceMenu(MenuType<?> var1, RecipeType<? extends AbstractCookingRecipe> var2, RecipeBookType var3, int var4, Inventory var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, new SimpleContainer(3), new SimpleContainerData(4));
   }

   protected AbstractFurnaceMenu(
      MenuType<?> var1, RecipeType<? extends AbstractCookingRecipe> var2, RecipeBookType var3, int var4, Inventory var5, Container var6, ContainerData var7
   ) {
      super(â˜ƒ, â˜ƒ);
      this.recipeType = â˜ƒ;
      this.recipeBookType = â˜ƒ;
      checkContainerSize(â˜ƒ, 3);
      checkContainerDataCount(â˜ƒ, 4);
      this.container = â˜ƒ;
      this.data = â˜ƒ;
      this.level = â˜ƒ.player.level;
      this.addSlot(new Slot(â˜ƒ, 0, 56, 17));
      this.addSlot(new FurnaceFuelSlot(this, â˜ƒ, 1, 56, 53));
      this.addSlot(new FurnaceResultSlot(â˜ƒ.player, â˜ƒ, 2, 116, 35));

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 8 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }

      this.addDataSlots(â˜ƒ);
   }

   @Override
   public void fillCraftSlotsStackedContents(StackedContents var1) {
      if (this.container instanceof StackedContentsCompatible) {
         ((StackedContentsCompatible)this.container).fillStackedContents(â˜ƒ);
      }
   }

   @Override
   public void clearCraftingContent() {
      this.getSlot(0).set(ItemStack.EMPTY);
      this.getSlot(2).set(ItemStack.EMPTY);
   }

   @Override
   public boolean recipeMatches(Recipe<? super Container> var1) {
      return â˜ƒ.matches(this.container, this.level);
   }

   @Override
   public int getResultSlotIndex() {
      return 2;
   }

   @Override
   public int getGridWidth() {
      return 1;
   }

   @Override
   public int getGridHeight() {
      return 1;
   }

   @Override
   public int getSize() {
      return 3;
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.container.stillValid(â˜ƒ);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == 2) {
            if (!this.moveItemStackTo(â˜ƒxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ != 1 && â˜ƒ != 0) {
            if (this.canSmelt(â˜ƒxx)) {
               if (!this.moveItemStackTo(â˜ƒxx, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (this.isFuel(â˜ƒxx)) {
               if (!this.moveItemStackTo(â˜ƒxx, 1, 2, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 3 && â˜ƒ < 30) {
               if (!this.moveItemStackTo(â˜ƒxx, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 30 && â˜ƒ < 39 && !this.moveItemStackTo(â˜ƒxx, 3, 30, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.set(ItemStack.EMPTY);
         } else {
            â˜ƒx.setChanged();
         }

         if (â˜ƒxx.getCount() == â˜ƒ.getCount()) {
            return ItemStack.EMPTY;
         }

         â˜ƒx.onTake(â˜ƒ, â˜ƒxx);
      }

      return â˜ƒ;
   }

   protected boolean canSmelt(ItemStack var1) {
      return this.level.getRecipeManager().getRecipeFor(this.recipeType, new SimpleContainer(â˜ƒ), this.level).isPresent();
   }

   protected boolean isFuel(ItemStack var1) {
      return AbstractFurnaceBlockEntity.isFuel(â˜ƒ);
   }

   public int getBurnProgress() {
      int â˜ƒ = this.data.get(2);
      int â˜ƒx = this.data.get(3);
      return â˜ƒx != 0 && â˜ƒ != 0 ? â˜ƒ * 24 / â˜ƒx : 0;
   }

   public int getLitProgress() {
      int â˜ƒ = this.data.get(1);
      if (â˜ƒ == 0) {
         â˜ƒ = 200;
      }

      return this.data.get(0) * 13 / â˜ƒ;
   }

   public boolean isLit() {
      return this.data.get(0) > 0;
   }

   @Override
   public RecipeBookType getRecipeBookType() {
      return this.recipeBookType;
   }

   @Override
   public boolean shouldMoveToInventory(int var1) {
      return â˜ƒ != 1;
   }
}
