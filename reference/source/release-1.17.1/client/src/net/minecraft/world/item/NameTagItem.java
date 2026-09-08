package net.minecraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class NameTagItem extends Item {
   public NameTagItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult interactLivingEntity(ItemStack var1, Player var2, LivingEntity var3, InteractionHand var4) {
      if (â˜ƒ.hasCustomHoverName() && !(â˜ƒ instanceof Player)) {
         if (!â˜ƒ.level.isClientSide && â˜ƒ.isAlive()) {
            â˜ƒ.setCustomName(â˜ƒ.getHoverName());
            if (â˜ƒ instanceof Mob) {
               ((Mob)â˜ƒ).setPersistenceRequired();
            }

            â˜ƒ.shrink(1);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }
}
