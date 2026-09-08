package net.minecraft.world.item.trading;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Merchant {
   void setTradingPlayer(@Nullable Player var1);

   @Nullable
   Player getTradingPlayer();

   MerchantOffers getOffers();

   void overrideOffers(MerchantOffers var1);

   void notifyTrade(MerchantOffer var1);

   void notifyTradeUpdated(ItemStack var1);

   Level getLevel();

   int getVillagerXp();

   void overrideXp(int var1);

   boolean showProgressBar();

   SoundEvent getNotifyTradeSound();

   default boolean canRestock() {
      return false;
   }

   default void openTradingScreen(Player var1, Component var2, int var3) {
      OptionalInt â˜ƒ = â˜ƒ.openMenu(new SimpleMenuProvider((var1x, var2x, var3x) -> new MerchantMenu(var1x, var2x, this), â˜ƒ));
      if (â˜ƒ.isPresent()) {
         MerchantOffers â˜ƒx = this.getOffers();
         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.sendMerchantOffers(â˜ƒ.getAsInt(), â˜ƒx, â˜ƒ, this.getVillagerXp(), this.showProgressBar(), this.canRestock());
         }
      }
   }
}
