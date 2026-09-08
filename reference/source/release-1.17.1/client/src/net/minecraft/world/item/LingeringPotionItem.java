package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class LingeringPotionItem extends ThrowablePotionItem {
   public LingeringPotionItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      PotionUtils.addPotionTooltip(â˜ƒ, â˜ƒ, 0.25F);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      â˜ƒ.playSound(
         null,
         â˜ƒ.getX(),
         â˜ƒ.getY(),
         â˜ƒ.getZ(),
         SoundEvents.LINGERING_POTION_THROW,
         SoundSource.NEUTRAL,
         0.5F,
         0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      return super.use(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
