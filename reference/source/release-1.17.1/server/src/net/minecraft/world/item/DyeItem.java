package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;

public class DyeItem extends Item {
   private static final Map<DyeColor, DyeItem> ITEM_BY_COLOR = Maps.newEnumMap(DyeColor.class);
   private final DyeColor dyeColor;

   public DyeItem(DyeColor var1, Item.Properties var2) {
      super(â˜ƒ);
      this.dyeColor = â˜ƒ;
      ITEM_BY_COLOR.put(â˜ƒ, this);
   }

   @Override
   public InteractionResult interactLivingEntity(ItemStack var1, Player var2, LivingEntity var3, InteractionHand var4) {
      if (â˜ƒ instanceof Sheep â˜ƒ && â˜ƒ.isAlive() && !â˜ƒ.isSheared() && â˜ƒ.getColor() != this.dyeColor) {
         â˜ƒ.level.playSound(â˜ƒ, â˜ƒ, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
         if (!â˜ƒ.level.isClientSide) {
            â˜ƒ.setColor(this.dyeColor);
            â˜ƒ.shrink(1);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.level.isClientSide);
      }

      return InteractionResult.PASS;
   }

   public DyeColor getDyeColor() {
      return this.dyeColor;
   }

   public static DyeItem byColor(DyeColor var0) {
      return (DyeItem)ITEM_BY_COLOR.get(â˜ƒ);
   }
}
