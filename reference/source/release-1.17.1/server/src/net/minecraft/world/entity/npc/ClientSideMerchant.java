package net.minecraft.world.entity.npc;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class ClientSideMerchant implements Merchant {
   private final Player source;
   private MerchantOffers offers = new MerchantOffers();
   private int xp;

   public ClientSideMerchant(Player var1) {
      this.source = â˜ƒ;
   }

   @Override
   public Player getTradingPlayer() {
      return this.source;
   }

   @Override
   public void setTradingPlayer(@Nullable Player var1) {
   }

   @Override
   public MerchantOffers getOffers() {
      return this.offers;
   }

   @Override
   public void overrideOffers(MerchantOffers var1) {
      this.offers = â˜ƒ;
   }

   @Override
   public void notifyTrade(MerchantOffer var1) {
      â˜ƒ.increaseUses();
   }

   @Override
   public void notifyTradeUpdated(ItemStack var1) {
   }

   @Override
   public Level getLevel() {
      return this.source.level;
   }

   @Override
   public int getVillagerXp() {
      return this.xp;
   }

   @Override
   public void overrideXp(int var1) {
      this.xp = â˜ƒ;
   }

   @Override
   public boolean showProgressBar() {
      return true;
   }

   @Override
   public SoundEvent getNotifyTradeSound() {
      return SoundEvents.VILLAGER_YES;
   }
}
