package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class ArrowKnockbackEnchantment extends Enchantment {
   public ArrowKnockbackEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.BOW, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 12 + (â˜ƒ - 1) * 20;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 25;
   }

   @Override
   public int getMaxLevel() {
      return 2;
   }
}
