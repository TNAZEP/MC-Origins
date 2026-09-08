package net.minecraft.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantMenu extends AbstractContainerMenu {
   protected static final int PAYMENT1_SLOT = 0;
   protected static final int PAYMENT2_SLOT = 1;
   protected static final int RESULT_SLOT = 2;
   private static final int INV_SLOT_START = 3;
   private static final int INV_SLOT_END = 30;
   private static final int USE_ROW_SLOT_START = 30;
   private static final int USE_ROW_SLOT_END = 39;
   private static final int SELLSLOT1_X = 136;
   private static final int SELLSLOT2_X = 162;
   private static final int BUYSLOT_X = 220;
   private static final int ROW_Y = 37;
   private final Merchant trader;
   private final MerchantContainer tradeContainer;
   private int merchantLevel;
   private boolean showProgressBar;
   private boolean canRestock;

   public MerchantMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, new ClientSideMerchant(â˜ƒ.player));
   }

   public MerchantMenu(int var1, Inventory var2, Merchant var3) {
      super(MenuType.MERCHANT, â˜ƒ);
      this.trader = â˜ƒ;
      this.tradeContainer = new MerchantContainer(â˜ƒ);
      this.addSlot(new Slot(this.tradeContainer, 0, 136, 37));
      this.addSlot(new Slot(this.tradeContainer, 1, 162, 37));
      this.addSlot(new MerchantResultSlot(â˜ƒ.player, â˜ƒ, this.tradeContainer, 2, 220, 37));

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 108 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 108 + â˜ƒ * 18, 142));
      }
   }

   public void setShowProgressBar(boolean var1) {
      this.showProgressBar = â˜ƒ;
   }

   @Override
   public void slotsChanged(Container var1) {
      this.tradeContainer.updateSellItem();
      super.slotsChanged(â˜ƒ);
   }

   public void setSelectionHint(int var1) {
      this.tradeContainer.setSelectionHint(â˜ƒ);
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.trader.getTradingPlayer() == â˜ƒ;
   }

   public int getTraderXp() {
      return this.trader.getVillagerXp();
   }

   public int getFutureTraderXp() {
      return this.tradeContainer.getFutureXp();
   }

   public void setXp(int var1) {
      this.trader.overrideXp(â˜ƒ);
   }

   public int getTraderLevel() {
      return this.merchantLevel;
   }

   public void setMerchantLevel(int var1) {
      this.merchantLevel = â˜ƒ;
   }

   public void setCanRestock(boolean var1) {
      this.canRestock = â˜ƒ;
   }

   public boolean canRestock() {
      return this.canRestock;
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
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
            this.playTradeSound();
         } else if (â˜ƒ != 0 && â˜ƒ != 1) {
            if (â˜ƒ >= 3 && â˜ƒ < 30) {
               if (!this.moveItemStackTo(â˜ƒxx, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 30 && â˜ƒ < 39 && !this.moveItemStackTo(â˜ƒxx, 3, 30, false)) {
               return ItemStack.EMPTY;
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

   private void playTradeSound() {
      if (!this.trader.getLevel().isClientSide) {
         Entity â˜ƒ = (Entity)this.trader;
         this.trader.getLevel().playLocalSound(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), this.trader.getNotifyTradeSound(), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
      }
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.trader.setTradingPlayer(null);
      if (!this.trader.getLevel().isClientSide) {
         if (!â˜ƒ.isAlive() || â˜ƒ instanceof ServerPlayer && ((ServerPlayer)â˜ƒ).hasDisconnected()) {
            ItemStack â˜ƒ = this.tradeContainer.removeItemNoUpdate(0);
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ.drop(â˜ƒ, false);
            }

            â˜ƒ = this.tradeContainer.removeItemNoUpdate(1);
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ.drop(â˜ƒ, false);
            }
         } else if (â˜ƒ instanceof ServerPlayer) {
            â˜ƒ.getInventory().placeItemBackInInventory(this.tradeContainer.removeItemNoUpdate(0));
            â˜ƒ.getInventory().placeItemBackInInventory(this.tradeContainer.removeItemNoUpdate(1));
         }
      }
   }

   public void tryMoveItems(int var1) {
      if (this.getOffers().size() > â˜ƒ) {
         ItemStack â˜ƒ = this.tradeContainer.getItem(0);
         if (!â˜ƒ.isEmpty()) {
            if (!this.moveItemStackTo(â˜ƒ, 3, 39, true)) {
               return;
            }

            this.tradeContainer.setItem(0, â˜ƒ);
         }

         ItemStack â˜ƒ = this.tradeContainer.getItem(1);
         if (!â˜ƒ.isEmpty()) {
            if (!this.moveItemStackTo(â˜ƒ, 3, 39, true)) {
               return;
            }

            this.tradeContainer.setItem(1, â˜ƒ);
         }

         if (this.tradeContainer.getItem(0).isEmpty() && this.tradeContainer.getItem(1).isEmpty()) {
            ItemStack â˜ƒ = ((MerchantOffer)this.getOffers().get(â˜ƒ)).getCostA();
            this.moveFromInventoryToPaymentSlot(0, â˜ƒ);
            ItemStack â˜ƒx = ((MerchantOffer)this.getOffers().get(â˜ƒ)).getCostB();
            this.moveFromInventoryToPaymentSlot(1, â˜ƒx);
         }
      }
   }

   private void moveFromInventoryToPaymentSlot(int var1, ItemStack var2) {
      if (!â˜ƒ.isEmpty()) {
         for(int â˜ƒ = 3; â˜ƒ < 39; ++â˜ƒ) {
            ItemStack â˜ƒx = this.slots.get(â˜ƒ).getItem();
            if (!â˜ƒx.isEmpty() && ItemStack.isSameItemSameTags(â˜ƒ, â˜ƒx)) {
               ItemStack â˜ƒxx = this.tradeContainer.getItem(â˜ƒ);
               int â˜ƒxxx = â˜ƒxx.isEmpty() ? 0 : â˜ƒxx.getCount();
               int â˜ƒxxxx = Math.min(â˜ƒ.getMaxStackSize() - â˜ƒxxx, â˜ƒx.getCount());
               ItemStack â˜ƒxxxxx = â˜ƒx.copy();
               int â˜ƒxxxxxx = â˜ƒxxx + â˜ƒxxxx;
               â˜ƒx.shrink(â˜ƒxxxx);
               â˜ƒxxxxx.setCount(â˜ƒxxxxxx);
               this.tradeContainer.setItem(â˜ƒ, â˜ƒxxxxx);
               if (â˜ƒxxxxxx >= â˜ƒ.getMaxStackSize()) {
                  break;
               }
            }
         }
      }
   }

   public void setOffers(MerchantOffers var1) {
      this.trader.overrideOffers(â˜ƒ);
   }

   public MerchantOffers getOffers() {
      return this.trader.getOffers();
   }

   public boolean showProgressBar() {
      return this.showProgressBar;
   }
}
