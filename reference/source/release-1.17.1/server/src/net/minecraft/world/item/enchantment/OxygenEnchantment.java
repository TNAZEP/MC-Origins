package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class OxygenEnchantment extends Enchantment {
   public OxygenEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.ARMOR_HEAD, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 10 * â˜ƒ;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 30;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}
