package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FishingRodItem extends Item implements Vanishable {
   public FishingRodItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.fishing != null) {
         if (!â˜ƒ.isClientSide) {
            int â˜ƒx = â˜ƒ.fishing.retrieve(â˜ƒ);
            â˜ƒ.hurtAndBreak(â˜ƒx, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
         }

         â˜ƒ.playSound(
            null,
            â˜ƒ.getX(),
            â˜ƒ.getY(),
            â˜ƒ.getZ(),
            SoundEvents.FISHING_BOBBER_RETRIEVE,
            SoundSource.NEUTRAL,
            1.0F,
            0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
         );
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.FISHING_ROD_REEL_IN, â˜ƒ);
      } else {
         â˜ƒ.playSound(
            null,
            â˜ƒ.getX(),
            â˜ƒ.getY(),
            â˜ƒ.getZ(),
            SoundEvents.FISHING_BOBBER_THROW,
            SoundSource.NEUTRAL,
            0.5F,
            0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
         );
         if (!â˜ƒ.isClientSide) {
            int â˜ƒ = EnchantmentHelper.getFishingSpeedBonus(â˜ƒ);
            int â˜ƒx = EnchantmentHelper.getFishingLuckBonus(â˜ƒ);
            â˜ƒ.addFreshEntity(new FishingHook(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ));
         }

         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.FISHING_ROD_CAST, â˜ƒ);
      }

      return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
   }

   @Override
   public int getEnchantmentValue() {
      return 1;
   }
}
