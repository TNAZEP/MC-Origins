package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;

public class ItemFrameItem extends HangingEntityItem {
   public ItemFrameItem(EntityType<? extends HangingEntity> var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean mayPlace(Player var1, Direction var2, ItemStack var3, BlockPos var4) {
      return !â˜ƒ.level.isOutsideBuildHeight(â˜ƒ) && â˜ƒ.mayUseItemAt(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
