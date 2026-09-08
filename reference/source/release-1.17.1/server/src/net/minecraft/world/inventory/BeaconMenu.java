package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class BeaconMenu extends AbstractContainerMenu {
   private static final int PAYMENT_SLOT = 0;
   private static final int SLOT_COUNT = 1;
   private static final int DATA_COUNT = 3;
   private static final int INV_SLOT_START = 1;
   private static final int INV_SLOT_END = 28;
   private static final int USE_ROW_SLOT_START = 28;
   private static final int USE_ROW_SLOT_END = 37;
   private final Container beacon = new SimpleContainer(1) {
      @Override
      public boolean canPlaceItem(int var1, ItemStack var2) {
         return â˜ƒ.is(ItemTags.BEACON_PAYMENT_ITEMS);
      }

      @Override
      public int getMaxStackSize() {
         return 1;
      }
   };
   private final BeaconMenu.PaymentSlot paymentSlot;
   private final ContainerLevelAccess access;
   private final ContainerData beaconData;

   public BeaconMenu(int var1, Container var2) {
      this(â˜ƒ, â˜ƒ, new SimpleContainerData(3), ContainerLevelAccess.NULL);
   }

   public BeaconMenu(int var1, Container var2, ContainerData var3, ContainerLevelAccess var4) {
      super(MenuType.BEACON, â˜ƒ);
      checkContainerDataCount(â˜ƒ, 3);
      this.beaconData = â˜ƒ;
      this.access = â˜ƒ;
      this.paymentSlot = new BeaconMenu.PaymentSlot(this.beacon, 0, 136, 110);
      this.addSlot(this.paymentSlot);
      this.addDataSlots(â˜ƒ);
      int â˜ƒ = 36;
      int â˜ƒx = 137;

      for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 9; ++â˜ƒxxx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒxxx + â˜ƒxx * 9 + 9, 36 + â˜ƒxxx * 18, 137 + â˜ƒxx * 18));
         }
      }

      for(int â˜ƒxx = 0; â˜ƒxx < 9; ++â˜ƒxx) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒxx, 36 + â˜ƒxx * 18, 195));
      }
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      if (!â˜ƒ.level.isClientSide) {
         ItemStack â˜ƒ = this.paymentSlot.remove(this.paymentSlot.getMaxStackSize());
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.drop(â˜ƒ, false);
         }
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.BEACON);
   }

   @Override
   public void setData(int var1, int var2) {
      super.setData(â˜ƒ, â˜ƒ);
      this.broadcastChanges();
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == 0) {
            if (!this.moveItemStackTo(â˜ƒxx, 1, 37, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (!this.paymentSlot.hasItem() && this.paymentSlot.mayPlace(â˜ƒxx) && â˜ƒxx.getCount() == 1) {
            if (!this.moveItemStackTo(â˜ƒxx, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 1 && â˜ƒ < 28) {
            if (!this.moveItemStackTo(â˜ƒxx, 28, 37, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 28 && â˜ƒ < 37) {
            if (!this.moveItemStackTo(â˜ƒxx, 1, 28, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 1, 37, false)) {
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

   public int getLevels() {
      return this.beaconData.get(0);
   }

   @Nullable
   public MobEffect getPrimaryEffect() {
      return MobEffect.byId(this.beaconData.get(1));
   }

   @Nullable
   public MobEffect getSecondaryEffect() {
      return MobEffect.byId(this.beaconData.get(2));
   }

   public void updateEffects(int var1, int var2) {
      if (this.paymentSlot.hasItem()) {
         this.beaconData.set(1, â˜ƒ);
         this.beaconData.set(2, â˜ƒ);
         this.paymentSlot.remove(1);
      }
   }

   public boolean hasPayment() {
      return !this.beacon.getItem(0).isEmpty();
   }

   class PaymentSlot extends Slot {
      public PaymentSlot(Container var2, int var3, int var4, int var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mayPlace(ItemStack var1) {
         return â˜ƒ.is(ItemTags.BEACON_PAYMENT_ITEMS);
      }

      @Override
      public int getMaxStackSize() {
         return 1;
      }
   }
}
