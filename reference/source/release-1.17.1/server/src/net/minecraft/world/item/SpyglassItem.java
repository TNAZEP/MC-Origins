package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpyglassItem extends Item {
   public static final int USE_DURATION = 1200;
   public static final float ZOOM_FOV_MODIFIER = 0.1F;

   public SpyglassItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 1200;
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.SPYGLASS;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      â˜ƒ.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
      â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      return ItemUtils.startUsingInstantly(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      this.stopUsing(â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
      this.stopUsing(â˜ƒ);
   }

   private void stopUsing(LivingEntity var1) {
      â˜ƒ.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
   }
}
