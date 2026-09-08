package net.minecraft.world.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class ArrowItem extends Item {
   public ArrowItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public AbstractArrow createArrow(Level var1, ItemStack var2, LivingEntity var3) {
      Arrow â˜ƒ = new Arrow(â˜ƒ, â˜ƒ);
      â˜ƒ.setEffectsFromItem(â˜ƒ);
      return â˜ƒ;
   }
}
