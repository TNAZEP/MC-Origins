package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LecternMenu extends AbstractContainerMenu {
   private static final int DATA_COUNT = 1;
   private static final int SLOT_COUNT = 1;
   public static final int BUTTON_PREV_PAGE = 1;
   public static final int BUTTON_NEXT_PAGE = 2;
   public static final int BUTTON_TAKE_BOOK = 3;
   public static final int BUTTON_PAGE_JUMP_RANGE_START = 100;
   private final Container lectern;
   private final ContainerData lecternData;

   public LecternMenu(int var1) {
      this(â˜ƒ, new SimpleContainer(1), new SimpleContainerData(1));
   }

   public LecternMenu(int var1, Container var2, ContainerData var3) {
      super(MenuType.LECTERN, â˜ƒ);
      checkContainerSize(â˜ƒ, 1);
      checkContainerDataCount(â˜ƒ, 1);
      this.lectern = â˜ƒ;
      this.lecternData = â˜ƒ;
      this.addSlot(new Slot(â˜ƒ, 0, 0, 0) {
         @Override
         public void setChanged() {
            super.setChanged();
            LecternMenu.this.slotsChanged(this.container);
         }
      });
      this.addDataSlots(â˜ƒ);
   }

   @Override
   public boolean clickMenuButton(Player var1, int var2) {
      if (â˜ƒ >= 100) {
         int â˜ƒ = â˜ƒ - 100;
         this.setData(0, â˜ƒ);
         return true;
      } else {
         switch(â˜ƒ) {
            case 1: {
               int â˜ƒ = this.lecternData.get(0);
               this.setData(0, â˜ƒ - 1);
               return true;
            }
            case 2: {
               int â˜ƒ = this.lecternData.get(0);
               this.setData(0, â˜ƒ + 1);
               return true;
            }
            case 3: {
               if (!â˜ƒ.mayBuild()) {
                  return false;
               }

               ItemStack â˜ƒ = this.lectern.removeItemNoUpdate(0);
               this.lectern.setChanged();
               if (!â˜ƒ.getInventory().add(â˜ƒ)) {
                  â˜ƒ.drop(â˜ƒ, false);
               }

               return true;
            }
            default:
               return false;
         }
      }
   }

   @Override
   public void setData(int var1, int var2) {
      super.setData(â˜ƒ, â˜ƒ);
      this.broadcastChanges();
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.lectern.stillValid(â˜ƒ);
   }

   public ItemStack getBook() {
      return this.lectern.getItem(0);
   }

   public int getPage() {
      return this.lecternData.get(0);
   }
}
