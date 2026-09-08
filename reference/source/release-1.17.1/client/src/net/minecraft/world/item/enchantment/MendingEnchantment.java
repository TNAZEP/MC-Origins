package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class MendingEnchantment extends Enchantment {
   public MendingEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.BREAKABLE, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return â˜ƒ * 25;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 50;
   }

   @Override
   public boolean isTreasureOnly() {
      return true;
   }

   @Override
   public int getMaxLevel() {
      return 1;
   }
}
