package net.minecraft.world.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;

public class ThrowablePotionItem extends PotionItem {
   public ThrowablePotionItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!â˜ƒ.isClientSide) {
         ThrownPotion â˜ƒx = new ThrownPotion(â˜ƒ, â˜ƒ);
         â˜ƒx.setItem(â˜ƒ);
         â˜ƒx.shootFromRotation(â˜ƒ, â˜ƒ.getXRot(), â˜ƒ.getYRot(), -20.0F, 0.5F, 1.0F);
         â˜ƒ.addFreshEntity(â˜ƒx);
      }

      â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      if (!â˜ƒ.getAbilities().instabuild) {
         â˜ƒ.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
   }
}
