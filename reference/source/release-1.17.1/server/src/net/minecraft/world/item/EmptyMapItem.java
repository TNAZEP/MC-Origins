package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EmptyMapItem extends ComplexItem {
   public EmptyMapItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.isClientSide) {
         return InteractionResultHolder.success(â˜ƒ);
      } else {
         if (!â˜ƒ.getAbilities().instabuild) {
            â˜ƒ.shrink(1);
         }

         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         â˜ƒ.level.playSound(null, â˜ƒ, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, â˜ƒ.getSoundSource(), 1.0F, 1.0F);
         ItemStack â˜ƒ = MapItem.create(â˜ƒ, â˜ƒ.getBlockX(), â˜ƒ.getBlockZ(), (byte)0, true, false);
         if (â˜ƒ.isEmpty()) {
            return InteractionResultHolder.consume(â˜ƒ);
         } else {
            if (!â˜ƒ.getInventory().add(â˜ƒ.copy())) {
               â˜ƒ.drop(â˜ƒ, false);
            }

            return InteractionResultHolder.consume(â˜ƒ);
         }
      }
   }
}
