package net.minecraft.world.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ChorusFruitItem extends Item {
   public ChorusFruitItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      ItemStack â˜ƒ = super.finishUsingItem(â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isClientSide) {
         double â˜ƒx = â˜ƒ.getX();
         double â˜ƒxx = â˜ƒ.getY();
         double â˜ƒxxx = â˜ƒ.getZ();

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
            double â˜ƒxxxxx = â˜ƒ.getX() + (â˜ƒ.getRandom().nextDouble() - 0.5) * 16.0;
            double â˜ƒxxxxxx = Mth.clamp(
               â˜ƒ.getY() + (double)(â˜ƒ.getRandom().nextInt(16) - 8),
               (double)â˜ƒ.getMinBuildHeight(),
               (double)(â˜ƒ.getMinBuildHeight() + ((ServerLevel)â˜ƒ).getLogicalHeight() - 1)
            );
            double â˜ƒxxxxxxx = â˜ƒ.getZ() + (â˜ƒ.getRandom().nextDouble() - 0.5) * 16.0;
            if (â˜ƒ.isPassenger()) {
               â˜ƒ.stopRiding();
            }

            if (â˜ƒ.randomTeleport(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, true)) {
               SoundEvent â˜ƒxxxxx = â˜ƒ instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
               â˜ƒ.playSound(null, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxxx, SoundSource.PLAYERS, 1.0F, 1.0F);
               â˜ƒ.playSound(â˜ƒxxxxx, 1.0F, 1.0F);
               break;
            }
         }

         if (â˜ƒ instanceof Player) {
            ((Player)â˜ƒ).getCooldowns().addCooldown(this, 20);
         }
      }

      return â˜ƒ;
   }
}
