package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantContainer implements Container {
   private final Merchant merchant;
   private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
   @Nullable
   private MerchantOffer activeOffer;
   private int selectionHint;
   private int futureXp;

   public MerchantContainer(Merchant var1) {
      this.merchant = â˜ƒ;
   }

   @Override
   public int getContainerSize() {
      return this.itemStacks.size();
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.itemStacks) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getItem(int var1) {
      return this.itemStacks.get(â˜ƒ);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      ItemStack â˜ƒ = this.itemStacks.get(â˜ƒ);
      if (â˜ƒ == 2 && !â˜ƒ.isEmpty()) {
         return ContainerHelper.removeItem(this.itemStacks, â˜ƒ, â˜ƒ.getCount());
      } else {
         ItemStack â˜ƒ = ContainerHelper.removeItem(this.itemStacks, â˜ƒ, â˜ƒ);
         if (!â˜ƒ.isEmpty() && this.isPaymentSlot(â˜ƒ)) {
            this.updateSellItem();
         }

         return â˜ƒ;
      }
   }

   private boolean isPaymentSlot(int var1) {
      return â˜ƒ == 0 || â˜ƒ == 1;
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      return ContainerHelper.takeItem(this.itemStacks, â˜ƒ);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.itemStacks.set(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty() && â˜ƒ.getCount() > this.getMaxStackSize()) {
         â˜ƒ.setCount(this.getMaxStackSize());
      }

      if (this.isPaymentSlot(â˜ƒ)) {
         this.updateSellItem();
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.merchant.getTradingPlayer() == â˜ƒ;
   }

   @Override
   public void setChanged() {
      this.updateSellItem();
   }

   public void updateSellItem() {
      this.activeOffer = null;
      ItemStack â˜ƒ;
      ItemStack â˜ƒx;
      if (this.itemStacks.get(0).isEmpty()) {
         â˜ƒ = this.itemStacks.get(1);
         â˜ƒx = ItemStack.EMPTY;
      } else {
         â˜ƒ = this.itemStacks.get(0);
         â˜ƒx = this.itemStacks.get(1);
      }

      if (â˜ƒ.isEmpty()) {
         this.setItem(2, ItemStack.EMPTY);
         this.futureXp = 0;
      } else {
         MerchantOffers â˜ƒ = this.merchant.getOffers();
         if (!â˜ƒ.isEmpty()) {
            MerchantOffer â˜ƒx = â˜ƒ.getRecipeFor(â˜ƒ, â˜ƒx, this.selectionHint);
            if (â˜ƒx == null || â˜ƒx.isOutOfStock()) {
               this.activeOffer = â˜ƒx;
               â˜ƒx = â˜ƒ.getRecipeFor(â˜ƒx, â˜ƒ, this.selectionHint);
            }

            if (â˜ƒx != null && !â˜ƒx.isOutOfStock()) {
               this.activeOffer = â˜ƒx;
               this.setItem(2, â˜ƒx.assemble());
               this.futureXp = â˜ƒx.getXp();
            } else {
               this.setItem(2, ItemStack.EMPTY);
               this.futureXp = 0;
            }
         }

         this.merchant.notifyTradeUpdated(this.getItem(2));
      }
   }

   @Nullable
   public MerchantOffer getActiveOffer() {
      return this.activeOffer;
   }

   public void setSelectionHint(int var1) {
      this.selectionHint = â˜ƒ;
      this.updateSellItem();
   }

   @Override
   public void clearContent() {
      this.itemStacks.clear();
   }

   public int getFutureXp() {
      return this.futureXp;
   }
}
