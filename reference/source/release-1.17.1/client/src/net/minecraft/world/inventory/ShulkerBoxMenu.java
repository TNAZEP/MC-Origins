package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxMenu extends AbstractContainerMenu {
   private static final int CONTAINER_SIZE = 27;
   private final Container container;

   public ShulkerBoxMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, new SimpleContainer(27));
   }

   public ShulkerBoxMenu(int var1, Inventory var2, Container var3) {
      super(MenuType.SHULKER_BOX, â˜ƒ);
      checkContainerSize(â˜ƒ, 27);
      this.container = â˜ƒ;
      â˜ƒ.startOpen(â˜ƒ.player);
      int â˜ƒ = 3;
      int â˜ƒx = 9;

      for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 9; ++â˜ƒxxx) {
            this.addSlot(new ShulkerBoxSlot(â˜ƒ, â˜ƒxxx + â˜ƒxx * 9, 8 + â˜ƒxxx * 18, 18 + â˜ƒxx * 18));
         }
      }

      for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 9; ++â˜ƒxxx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒxxx + â˜ƒxx * 9 + 9, 8 + â˜ƒxxx * 18, 84 + â˜ƒxx * 18));
         }
      }

      for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒxx, 8 + â˜ƒxx * 18, 142));
      }
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
         if (â˜ƒ < this.container.getContainerSize()) {
            if (!this.moveItemStackTo(â˜ƒxx, this.container.getContainerSize(), this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 0, this.container.getContainerSize(), false)) {
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
      this.container.stopOpen(â˜ƒ);
   }
}
