package net.minecraft.world.item;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;

public class SaddleItem extends Item {
   public SaddleItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult interactLivingEntity(ItemStack var1, Player var2, LivingEntity var3, InteractionHand var4) {
      if (â˜ƒ instanceof Saddleable â˜ƒ && â˜ƒ.isAlive() && !â˜ƒ.isSaddled() && â˜ƒ.isSaddleable()) {
         if (!â˜ƒ.level.isClientSide) {
            â˜ƒ.equipSaddle(SoundSource.NEUTRAL);
            â˜ƒ.shrink(1);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.level.isClientSide);
      }

      return InteractionResult.PASS;
   }
}
