package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;

public class EnderpearlItem extends Item {
   public EnderpearlItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      â˜ƒ.playSound(
         null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      â˜ƒ.getCooldowns().addCooldown(this, 20);
      if (!â˜ƒ.isClientSide) {
         ThrownEnderpearl â˜ƒx = new ThrownEnderpearl(â˜ƒ, â˜ƒ);
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
