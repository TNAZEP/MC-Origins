package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MilkBucketItem extends Item {
   private static final int DRINK_DURATION = 32;

   public MilkBucketItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      if (â˜ƒ instanceof ServerPlayer â˜ƒ) {
         CriteriaTriggers.CONSUME_ITEM.trigger(â˜ƒ, â˜ƒ);
         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      }

      if (â˜ƒ instanceof Player && !((Player)â˜ƒ).getAbilities().instabuild) {
         â˜ƒ.shrink(1);
      }

      if (!â˜ƒ.isClientSide) {
         â˜ƒ.removeAllEffects();
      }

      return â˜ƒ.isEmpty() ? new ItemStack(Items.BUCKET) : â˜ƒ;
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 32;
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.DRINK;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      return ItemUtils.startUsingInstantly(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
