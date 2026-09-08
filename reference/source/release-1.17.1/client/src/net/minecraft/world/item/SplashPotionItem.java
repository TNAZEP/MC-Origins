package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SplashPotionItem extends ThrowablePotionItem {
   public SplashPotionItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      â˜ƒ.playSound(
         null,
         â˜ƒ.getX(),
         â˜ƒ.getY(),
         â˜ƒ.getZ(),
         SoundEvents.SPLASH_POTION_THROW,
         SoundSource.PLAYERS,
         0.5F,
         0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      return super.use(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
