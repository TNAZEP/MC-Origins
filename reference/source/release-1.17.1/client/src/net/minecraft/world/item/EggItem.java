package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;

public class EggItem extends Item {
   public EggItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      â˜ƒ.playSound(
         null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      if (!â˜ƒ.isClientSide) {
         ThrownEgg â˜ƒx = new ThrownEgg(â˜ƒ, â˜ƒ);
         â˜ƒx.setItem(â˜ƒ);
         â˜ƒx.shootFromRotation(â˜ƒ, â˜ƒ.getXRot(), â˜ƒ.getYRot(), 0.0F, 1.5F, 1.0F);
         â˜ƒ.addFreshEntity(â˜ƒx);
      }

      â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      if (!â˜ƒ.getAbilities().instabuild) {
         â˜ƒ.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
   }
}
