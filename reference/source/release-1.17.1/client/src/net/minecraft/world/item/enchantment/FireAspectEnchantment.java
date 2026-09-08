package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class FireAspectEnchantment extends Enchantment {
   protected FireAspectEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.WEAPON, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 10 + 20 * (â˜ƒ - 1);
   }

   @Override
   public int getMaxCost(int var1) {
      return super.getMinCost(â˜ƒ) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 2;
   }
}
