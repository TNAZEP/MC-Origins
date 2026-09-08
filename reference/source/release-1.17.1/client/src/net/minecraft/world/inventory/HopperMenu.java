package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HopperMenu extends AbstractContainerMenu {
   public static final int CONTAINER_SIZE = 5;
   private final Container hopper;

   public HopperMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, new SimpleContainer(5));
   }

   public HopperMenu(int var1, Inventory var2, Container var3) {
      super(MenuType.HOPPER, â˜ƒ);
      this.hopper = â˜ƒ;
      checkContainerSize(â˜ƒ, 5);
      â˜ƒ.startOpen(â˜ƒ.player);
      int â˜ƒ = 51;

      for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒx, 44 + â˜ƒx * 18, 20));
      }

      for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒxx + â˜ƒx * 9 + 9, 8 + â˜ƒxx * 18, â˜ƒx * 18 + 51));
         }
      }

      for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒx, 8 + â˜ƒx * 18, 109));
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.hopper.stillValid(â˜ƒ);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ < this.hopper.getContainerSize()) {
            if (!this.moveItemStackTo(â˜ƒxx, this.hopper.getContainerSize(), this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 0, this.hopper.getContainerSize(), false)) {
            return ItemStack.EMPTY;
         }

         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.set(ItemStack.EMPTY);
         } else {
            â˜ƒx.setChanged();
         }
      }

      return â˜ƒ;
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.hopper.stopOpen(â˜ƒ);
   }
}
