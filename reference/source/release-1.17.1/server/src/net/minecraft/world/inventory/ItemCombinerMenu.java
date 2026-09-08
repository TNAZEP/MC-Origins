package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ItemCombinerMenu extends AbstractContainerMenu {
   public static final int INPUT_SLOT = 0;
   public static final int ADDITIONAL_SLOT = 1;
   public static final int RESULT_SLOT = 2;
   private static final int INV_SLOT_START = 3;
   private static final int INV_SLOT_END = 30;
   private static final int USE_ROW_SLOT_START = 30;
   private static final int USE_ROW_SLOT_END = 39;
   protected final ResultContainer resultSlots = new ResultContainer();
   protected final Container inputSlots = new SimpleContainer(2) {
      @Override
      public void setChanged() {
         super.setChanged();
         ItemCombinerMenu.this.slotsChanged(this);
      }
   };
   protected final ContainerLevelAccess access;
   protected final Player player;

   protected abstract boolean mayPickup(Player var1, boolean var2);

   protected abstract void onTake(Player var1, ItemStack var2);

   protected abstract boolean isValidBlock(BlockState var1);

   public ItemCombinerMenu(@Nullable MenuType<?> var1, int var2, Inventory var3, ContainerLevelAccess var4) {
      super(â˜ƒ, â˜ƒ);
      this.access = â˜ƒ;
      this.player = â˜ƒ.player;
      this.addSlot(new Slot(this.inputSlots, 0, 27, 47));
      this.addSlot(new Slot(this.inputSlots, 1, 76, 47));
      this.addSlot(new Slot(this.resultSlots, 2, 134, 47) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return false;
         }

         @Override
         public boolean mayPickup(Player var1) {
            return ItemCombinerMenu.this.mayPickup(â˜ƒ, this.hasItem());
         }

         @Override
         public void onTake(Player var1, ItemStack var2) {
            ItemCombinerMenu.this.onTake(â˜ƒ, â˜ƒ);
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
   }

   public abstract void createResult();

   @Override
   public void slotsChanged(Container var1) {
      super.slotsChanged(â˜ƒ);
      if (â˜ƒ == this.inputSlots) {
         this.createResult();
      }
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.inputSlots));
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.access
         .evaluate(
            (var2, var3) -> !this.isValidBlock(var2.getBlockState(var3))
                  ? false
                  : â˜ƒ.distanceToSqr((double)var3.getX() + 0.5, (double)var3.getY() + 0.5, (double)var3.getZ() + 0.5) <= 64.0,
            true
         );
   }

   protected boolean shouldQuickMoveToAdditionalSlot(ItemStack var1) {
      return false;
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
         } else if (â˜ƒ != 0 && â˜ƒ != 1) {
            if (â˜ƒ >= 3 && â˜ƒ < 39) {
               int â˜ƒxx = this.shouldQuickMoveToAdditionalSlot(â˜ƒ) ? 1 : 0;
               if (!this.moveItemStackTo(â˜ƒxx, â˜ƒxx, 2, false)) {
                  return ItemStack.EMPTY;
               }
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
}
