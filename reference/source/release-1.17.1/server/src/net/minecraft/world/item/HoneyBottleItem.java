package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HoneyBottleItem extends Item {
   private static final int DRINK_DURATION = 40;

   public HoneyBottleItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      super.finishUsingItem(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ instanceof ServerPlayer â˜ƒ) {
         CriteriaTriggers.CONSUME_ITEM.trigger(â˜ƒ, â˜ƒ);
         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      }

      if (!â˜ƒ.isClientSide) {
         â˜ƒ.removeEffect(MobEffects.POISON);
      }

      if (â˜ƒ.isEmpty()) {
         return new ItemStack(Items.GLASS_BOTTLE);
      } else {
         if (â˜ƒ instanceof Player && !((Player)â˜ƒ).getAbilities().instabuild) {
            ItemStack â˜ƒ = new ItemStack(Items.GLASS_BOTTLE);
            Player â˜ƒx = (Player)â˜ƒ;
            if (!â˜ƒx.getInventory().add(â˜ƒ)) {
               â˜ƒx.drop(â˜ƒ, false);
            }
         }

         return â˜ƒ;
      }
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 40;
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.DRINK;
   }

   @Override
   public SoundEvent getDrinkingSound() {
      return SoundEvents.HONEY_DRINK;
   }

   @Override
   public SoundEvent getEatingSound() {
      return SoundEvents.HONEY_DRINK;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      return ItemUtils.startUsingInstantly(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
