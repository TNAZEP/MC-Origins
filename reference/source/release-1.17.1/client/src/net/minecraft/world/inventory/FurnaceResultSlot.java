package net.minecraft.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnaceResultSlot extends Slot {
   private final Player player;
   private int removeCount;

   public FurnaceResultSlot(Player var1, Container var2, int var3, int var4, int var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.player = â˜ƒ;
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
   public void onTake(Player var1, ItemStack var2) {
      this.checkTakeAchievements(â˜ƒ);
      super.onTake(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void onQuickCraft(ItemStack var1, int var2) {
      this.removeCount += â˜ƒ;
      this.checkTakeAchievements(â˜ƒ);
   }

   @Override
   protected void checkTakeAchievements(ItemStack var1) {
      â˜ƒ.onCraftedBy(this.player.level, this.player, this.removeCount);
      if (this.player instanceof ServerPlayer && this.container instanceof AbstractFurnaceBlockEntity) {
         ((AbstractFurnaceBlockEntity)this.container).awardUsedRecipesAndPopExperience((ServerPlayer)this.player);
      }

      this.removeCount = 0;
   }
}
