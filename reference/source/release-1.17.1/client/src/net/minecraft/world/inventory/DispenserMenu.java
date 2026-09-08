package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DispenserMenu extends AbstractContainerMenu {
   private static final int SLOT_COUNT = 9;
   private static final int INV_SLOT_START = 9;
   private static final int INV_SLOT_END = 36;
   private static final int USE_ROW_SLOT_START = 36;
   private static final int USE_ROW_SLOT_END = 45;
   private final Container dispenser;

   public DispenserMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, new SimpleContainer(9));
   }

   public DispenserMenu(int var1, Inventory var2, Container var3) {
      super(MenuType.GENERIC_3x3, â˜ƒ);
      checkContainerSize(â˜ƒ, 9);
      this.dispenser = â˜ƒ;
      â˜ƒ.startOpen(â˜ƒ.player);

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 3, 62 + â˜ƒx * 18, 17 + â˜ƒ * 18));
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

   @Override
   public boolean stillValid(Player var1) {
      return this.dispenser.stillValid(â˜ƒ);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ < 9) {
            if (!this.moveItemStackTo(â˜ƒxx, 9, 45, true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 0, 9, false)) {
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

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.dispenser.stopOpen(â˜ƒ);
   }
}
