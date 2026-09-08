package net.minecraft.world.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class StonecutterMenu extends AbstractContainerMenu {
   public static final int INPUT_SLOT = 0;
   public static final int RESULT_SLOT = 1;
   private static final int INV_SLOT_START = 2;
   private static final int INV_SLOT_END = 29;
   private static final int USE_ROW_SLOT_START = 29;
   private static final int USE_ROW_SLOT_END = 38;
   private final ContainerLevelAccess access;
   private final DataSlot selectedRecipeIndex = DataSlot.standalone();
   private final Level level;
   private List<StonecutterRecipe> recipes = Lists.<StonecutterRecipe>newArrayList();
   private ItemStack input = ItemStack.EMPTY;
   long lastSoundTime;
   final Slot inputSlot;
   final Slot resultSlot;
   Runnable slotUpdateListener = () -> {
   };
   public final Container container = new SimpleContainer(1) {
      @Override
      public void setChanged() {
         super.setChanged();
         StonecutterMenu.this.slotsChanged(this);
         StonecutterMenu.this.slotUpdateListener.run();
      }
   };
   final ResultContainer resultContainer = new ResultContainer();

   public StonecutterMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public StonecutterMenu(int var1, Inventory var2, final ContainerLevelAccess var3) {
      super(MenuType.STONECUTTER, â˜ƒ);
      this.access = â˜ƒ;
      this.level = â˜ƒ.player.level;
      this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
      this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return false;
         }

         @Override
         public void onTake(Player var1, ItemStack var2) {
            â˜ƒ.onCraftedBy(â˜ƒ.level, â˜ƒ, â˜ƒ.getCount());
            StonecutterMenu.this.resultContainer.awardUsedRecipes(â˜ƒ);
            ItemStack â˜ƒ = StonecutterMenu.this.inputSlot.remove(1);
            if (!â˜ƒ.isEmpty()) {
               StonecutterMenu.this.setupResultSlot();
            }

            â˜ƒ.execute((var1x, var2x) -> {
               long â˜ƒ = var1x.getGameTime();
               if (StonecutterMenu.this.lastSoundTime != â˜ƒ) {
                  var1x.playSound(null, var2x, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  StonecutterMenu.this.lastSoundTime = â˜ƒ;
               }
            });
            super.onTake(â˜ƒ, â˜ƒ);
         }
      });

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 8 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }

      this.addDataSlot(this.selectedRecipeIndex);
   }

   public int getSelectedRecipeIndex() {
      return this.selectedRecipeIndex.get();
   }

   public List<StonecutterRecipe> getRecipes() {
      return this.recipes;
   }

   public int getNumRecipes() {
      return this.recipes.size();
   }

   public boolean hasInputItem() {
      return this.inputSlot.hasItem() && !this.recipes.isEmpty();
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.STONECUTTER);
   }

   @Override
   public boolean clickMenuButton(Player var1, int var2) {
      if (this.isValidRecipeIndex(â˜ƒ)) {
         this.selectedRecipeIndex.set(â˜ƒ);
         this.setupResultSlot();
      }

      return true;
   }

   private boolean isValidRecipeIndex(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.recipes.size();
   }

   @Override
   public void slotsChanged(Container var1) {
      ItemStack â˜ƒ = this.inputSlot.getItem();
      if (!â˜ƒ.is(this.input.getItem())) {
         this.input = â˜ƒ.copy();
         this.setupRecipeList(â˜ƒ, â˜ƒ);
      }
   }

   private void setupRecipeList(Container var1, ItemStack var2) {
      this.recipes.clear();
      this.selectedRecipeIndex.set(-1);
      this.resultSlot.set(ItemStack.EMPTY);
      if (!â˜ƒ.isEmpty()) {
         this.recipes = this.level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, â˜ƒ, this.level);
      }
   }

   void setupResultSlot() {
      if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
         StonecutterRecipe â˜ƒ = (StonecutterRecipe)this.recipes.get(this.selectedRecipeIndex.get());
         this.resultContainer.setRecipeUsed(â˜ƒ);
         this.resultSlot.set(â˜ƒ.assemble(this.container));
      } else {
         this.resultSlot.set(ItemStack.EMPTY);
      }

      this.broadcastChanges();
   }

   @Override
   public MenuType<?> getType() {
      return MenuType.STONECUTTER;
   }

   public void registerUpdateListener(Runnable var1) {
      this.slotUpdateListener = â˜ƒ;
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
      return â˜ƒ.container != this.resultContainer && super.canTakeItemForPickAll(â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         Item â˜ƒxxx = â˜ƒxx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == 1) {
            â˜ƒxxx.onCraftedBy(â˜ƒxx, â˜ƒ.level, â˜ƒ);
            if (!this.moveItemStackTo(â˜ƒxx, 2, 38, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ == 0) {
            if (!this.moveItemStackTo(â˜ƒxx, 2, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.level.getRecipeManager().getRecipeFor(RecipeType.STONECUTTING, new SimpleContainer(â˜ƒxx), this.level).isPresent()) {
            if (!this.moveItemStackTo(â˜ƒxx, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 2 && â˜ƒ < 29) {
            if (!this.moveItemStackTo(â˜ƒxx, 29, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 29 && â˜ƒ < 38 && !this.moveItemStackTo(â˜ƒxx, 2, 29, false)) {
            return ItemStack.EMPTY;
         }

         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.set(ItemStack.EMPTY);
         }

         â˜ƒx.setChanged();
         if (â˜ƒxx.getCount() == â˜ƒ.getCount()) {
            return ItemStack.EMPTY;
         }

         â˜ƒx.onTake(â˜ƒ, â˜ƒxx);
         this.broadcastChanges();
      }

      return â˜ƒ;
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.resultContainer.removeItemNoUpdate(1);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.container));
   }
}
