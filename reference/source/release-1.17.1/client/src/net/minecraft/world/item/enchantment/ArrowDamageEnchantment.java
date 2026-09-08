package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class ArrowDamageEnchantment extends Enchantment {
   public ArrowDamageEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.BOW, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 1 + (â˜ƒ - 1) * 10;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 15;
   }

   @Override
   public int getMaxLevel() {
      return 5;
   }
}
