package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HorseInventoryMenu extends AbstractContainerMenu {
   private final Container horseContainer;
   private final AbstractHorse horse;

   public HorseInventoryMenu(int var1, Inventory var2, Container var3, final AbstractHorse var4) {
      super(null, â˜ƒ);
      this.horseContainer = â˜ƒ;
      this.horse = â˜ƒ;
      int â˜ƒ = 3;
      â˜ƒ.startOpen(â˜ƒ.player);
      int â˜ƒx = -18;
      this.addSlot(new Slot(â˜ƒ, 0, 8, 18) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.is(Items.SADDLE) && !this.hasItem() && â˜ƒ.isSaddleable();
         }

         @Override
         public boolean isActive() {
            return â˜ƒ.isSaddleable();
         }
      });
      this.addSlot(new Slot(â˜ƒ, 1, 8, 36) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.isArmor(â˜ƒ);
         }

         @Override
         public boolean isActive() {
            return â˜ƒ.canWearArmor();
         }

         @Override
         public int getMaxStackSize() {
            return 1;
         }
      });
      if (this.hasChest(â˜ƒ)) {
         for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < ((AbstractChestedHorse)â˜ƒ).getInventoryColumns(); ++â˜ƒxxx) {
               this.addSlot(new Slot(â˜ƒ, 2 + â˜ƒxxx + â˜ƒxx * ((AbstractChestedHorse)â˜ƒ).getInventoryColumns(), 80 + â˜ƒxxx * 18, 18 + â˜ƒxx * 18));
            }
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 8 + â˜ƒx * 18, 102 + â˜ƒ * 18 + -18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return !this.horse.hasInventoryChanged(this.horseContainer)
         && this.horseContainer.stillValid(â˜ƒ)
         && this.horse.isAlive()
         && this.horse.distanceTo(â˜ƒ) < 8.0F;
   }

   private boolean hasChest(AbstractHorse var1) {
      return â˜ƒ instanceof AbstractChestedHorse && ((AbstractChestedHorse)â˜ƒ).hasChest();
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         int â˜ƒxxx = this.horseContainer.getContainerSize();
         if (â˜ƒ < â˜ƒxxx) {
            if (!this.moveItemStackTo(â˜ƒxx, â˜ƒxxx, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).mayPlace(â˜ƒxx) && !this.getSlot(1).hasItem()) {
            if (!this.moveItemStackTo(â˜ƒxx, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).mayPlace(â˜ƒxx)) {
            if (!this.moveItemStackTo(â˜ƒxx, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒxxx <= 2 || !this.moveItemStackTo(â˜ƒxx, 2, â˜ƒxxx, false)) {
            int â˜ƒxx = â˜ƒxxx + 27;
            int â˜ƒxxx = â˜ƒxx + 9;
            if (â˜ƒ >= â˜ƒxx && â˜ƒ < â˜ƒxxx) {
               if (!this.moveItemStackTo(â˜ƒxx, â˜ƒxxx, â˜ƒxx, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= â˜ƒxxx && â˜ƒ < â˜ƒxx) {
               if (!this.moveItemStackTo(â˜ƒxx, â˜ƒxx, â˜ƒxxx, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(â˜ƒxx, â˜ƒxx, â˜ƒxx, false)) {
               return ItemStack.EMPTY;
            }

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
      this.horseContainer.stopOpen(â˜ƒ);
   }
}
