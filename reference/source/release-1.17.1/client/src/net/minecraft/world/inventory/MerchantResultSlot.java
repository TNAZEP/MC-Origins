package net.minecraft.world.inventory;

import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;

public class MerchantResultSlot extends Slot {
   private final MerchantContainer slots;
   private final Player player;
   private int removeCount;
   private final Merchant merchant;

   public MerchantResultSlot(Player var1, Merchant var2, MerchantContainer var3, int var4, int var5, int var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.player = â˜ƒ;
      this.merchant = â˜ƒ;
      this.slots = â˜ƒ;
   }

   @Override
   public boolean mayPlace(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack remove(int var1) {
      if (this.hasItem()) {
         this.removeCount += Math.min(â˜ƒ, this.getItem().getCount());
      }

      return super.remove(â˜ƒ);
   }

   @Override
   protected void onQuickCraft(ItemStack var1, int var2) {
      this.removeCount += â˜ƒ;
      this.checkTakeAchievements(â˜ƒ);
   }

   @Override
   protected void checkTakeAchievements(ItemStack var1) {
      â˜ƒ.onCraftedBy(this.player.level, this.player, this.removeCount);
      this.removeCount = 0;
   }

   @Override
   public void onTake(Player var1, ItemStack var2) {
      this.checkTakeAchievements(â˜ƒ);
      MerchantOffer â˜ƒ = this.slots.getActiveOffer();
      if (â˜ƒ != null) {
         ItemStack â˜ƒx = this.slots.getItem(0);
         ItemStack â˜ƒxx = this.slots.getItem(1);
         if (â˜ƒ.take(â˜ƒx, â˜ƒxx) || â˜ƒ.take(â˜ƒxx, â˜ƒx)) {
            this.merchant.notifyTrade(â˜ƒ);
            â˜ƒ.awardStat(Stats.TRADED_WITH_VILLAGER);
            this.slots.setItem(0, â˜ƒx);
            this.slots.setItem(1, â˜ƒxx);
         }

         this.merchant.overrideXp(this.merchant.getVillagerXp() + â˜ƒ.getXp());
      }
   }
}
