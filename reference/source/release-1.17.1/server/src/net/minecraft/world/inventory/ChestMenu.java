package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ChestMenu extends AbstractContainerMenu {
   private static final int SLOTS_PER_ROW = 9;
   private final Container container;
   private final int containerRows;

   private ChestMenu(MenuType<?> var1, int var2, Inventory var3, int var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, new SimpleContainer(9 * â˜ƒ), â˜ƒ);
   }

   public static ChestMenu oneRow(int var0, Inventory var1) {
      return new ChestMenu(MenuType.GENERIC_9x1, â˜ƒ, â˜ƒ, 1);
   }

   public static ChestMenu twoRows(int var0, Inventory var1) {
      return new ChestMenu(MenuType.GENERIC_9x2, â˜ƒ, â˜ƒ, 2);
   }

   public static ChestMenu threeRows(int var0, Inventory var1) {
      return new ChestMenu(MenuType.GENERIC_9x3, â˜ƒ, â˜ƒ, 3);
   }

   public static ChestMenu fourRows(int var0, Inventory var1) {
      return new ChestMenu(MenuType.GENERIC_9x4, â˜ƒ, â˜ƒ, 4);
   }

   public static ChestMenu fiveRows(int var0, Inventory var1) {
      return new ChestMenu(MenuType.GENERIC_9x5, â˜ƒ, â˜ƒ, 5);
   }

   public static ChestMenu sixRows(int var0, Inventory var1) {
      return new ChestMenu(MenuType.GENERIC_9x6, â˜ƒ, â˜ƒ, 6);
   }

   public static ChestMenu threeRows(int var0, Inventory var1, Container var2) {
      return new ChestMenu(MenuType.GENERIC_9x3, â˜ƒ, â˜ƒ, â˜ƒ, 3);
   }

   public static ChestMenu sixRows(int var0, Inventory var1, Container var2) {
      return new ChestMenu(MenuType.GENERIC_9x6, â˜ƒ, â˜ƒ, â˜ƒ, 6);
   }

   public ChestMenu(MenuType<?> var1, int var2, Inventory var3, Container var4, int var5) {
      super(â˜ƒ, â˜ƒ);
      checkContainerSize(â˜ƒ, â˜ƒ * 9);
      this.container = â˜ƒ;
      this.containerRows = â˜ƒ;
      â˜ƒ.startOpen(â˜ƒ.player);
      int â˜ƒ = (this.containerRows - 4) * 18;

      for(int â˜ƒx = 0; â˜ƒx < this.containerRows; ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒxx + â˜ƒx * 9, 8 + â˜ƒxx * 18, 18 + â˜ƒx * 18));
         }
      }

      for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒxx + â˜ƒx * 9 + 9, 8 + â˜ƒxx * 18, 103 + â˜ƒx * 18 + â˜ƒ));
         }
      }

      for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒx, 8 + â˜ƒx * 18, 161 + â˜ƒ));
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
         if (â˜ƒ < this.containerRows * 9) {
            if (!this.moveItemStackTo(â˜ƒxx, this.containerRows * 9, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 0, this.containerRows * 9, false)) {
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

   public Container getContainer() {
      return this.container;
   }

   public int getRowCount() {
      return this.containerRows;
   }
}
