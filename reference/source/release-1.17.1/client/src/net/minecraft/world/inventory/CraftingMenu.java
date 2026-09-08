package net.minecraft.world.inventory;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class CraftingMenu extends RecipeBookMenu<CraftingContainer> {
   public static final int RESULT_SLOT = 0;
   private static final int CRAFT_SLOT_START = 1;
   private static final int CRAFT_SLOT_END = 10;
   private static final int INV_SLOT_START = 10;
   private static final int INV_SLOT_END = 37;
   private static final int USE_ROW_SLOT_START = 37;
   private static final int USE_ROW_SLOT_END = 46;
   private final CraftingContainer craftSlots = new CraftingContainer(this, 3, 3);
   private final ResultContainer resultSlots = new ResultContainer();
   private final ContainerLevelAccess access;
   private final Player player;

   public CraftingMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public CraftingMenu(int var1, Inventory var2, ContainerLevelAccess var3) {
      super(MenuType.CRAFTING, â˜ƒ);
      this.access = â˜ƒ;
      this.player = â˜ƒ.player;
      this.addSlot(new ResultSlot(â˜ƒ.player, this.craftSlots, this.resultSlots, 0, 124, 35));

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
            this.addSlot(new Slot(this.craftSlots, â˜ƒx + â˜ƒ * 3, 30 + â˜ƒx * 18, 17 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 8 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }
   }

   protected static void slotChangedCraftingGrid(AbstractContainerMenu var0, Level var1, Player var2, CraftingContainer var3, ResultContainer var4) {
      if (!â˜ƒ.isClientSide) {
         ServerPlayer â˜ƒ = (ServerPlayer)â˜ƒ;
         ItemStack â˜ƒx = ItemStack.EMPTY;
         Optional<CraftingRecipe> â˜ƒxx = â˜ƒ.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, â˜ƒ, â˜ƒ);
         if (â˜ƒxx.isPresent()) {
            CraftingRecipe â˜ƒxxx = (CraftingRecipe)â˜ƒxx.get();
            if (â˜ƒ.setRecipeUsed(â˜ƒ, â˜ƒ, â˜ƒxxx)) {
               â˜ƒx = â˜ƒxxx.assemble(â˜ƒ);
            }
         }

         â˜ƒ.setItem(0, â˜ƒx);
         â˜ƒ.setRemoteSlot(0, â˜ƒx);
         â˜ƒ.connection.send(new ClientboundContainerSetSlotPacket(â˜ƒ.containerId, â˜ƒ.incrementStateId(), 0, â˜ƒx));
      }
   }

   @Override
   public void slotsChanged(Container var1) {
      this.access.execute((var1x, var2) -> slotChangedCraftingGrid(this, var1x, this.player, this.craftSlots, this.resultSlots));
   }

   @Override
   public void fillCraftSlotsStackedContents(StackedContents var1) {
      this.craftSlots.fillStackedContents(â˜ƒ);
   }

   @Override
   public void clearCraftingContent() {
      this.craftSlots.clearContent();
      this.resultSlots.clearContent();
   }

   @Override
   public boolean recipeMatches(Recipe<? super CraftingContainer> var1) {
      return â˜ƒ.matches(this.craftSlots, this.player.level);
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.craftSlots));
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.CRAFTING_TABLE);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == 0) {
            this.access.execute((var2x, var3x) -> â˜ƒ.getItem().onCraftedBy(â˜ƒ, var2x, â˜ƒ));
            if (!this.moveItemStackTo(â˜ƒxx, 10, 46, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ >= 10 && â˜ƒ < 46) {
            if (!this.moveItemStackTo(â˜ƒxx, 1, 10, false)) {
               if (â˜ƒ < 37) {
                  if (!this.moveItemStackTo(â˜ƒxx, 37, 46, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (!this.moveItemStackTo(â˜ƒxx, 10, 37, false)) {
                  return ItemStack.EMPTY;
               }
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 10, 46, false)) {
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
         if (â˜ƒ == 0) {
            â˜ƒ.drop(â˜ƒxx, false);
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
      return â˜ƒ.container != this.resultSlots && super.canTakeItemForPickAll(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getResultSlotIndex() {
      return 0;
   }

   @Override
   public int getGridWidth() {
      return this.craftSlots.getWidth();
   }

   @Override
   public int getGridHeight() {
      return this.craftSlots.getHeight();
   }

   @Override
   public int getSize() {
      return 10;
   }

   @Override
   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   @Override
   public boolean shouldMoveToInventory(int var1) {
      return â˜ƒ != this.getResultSlotIndex();
   }
}
