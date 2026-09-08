package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class TridentRiptideEnchantment extends Enchantment {
   public TridentRiptideEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.TRIDENT, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 10 + â˜ƒ * 7;
   }

   @Override
   public int getMaxCost(int var1) {
      return 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      return super.checkCompatibility(â˜ƒ) && â˜ƒ != Enchantments.LOYALTY && â˜ƒ != Enchantments.CHANNELING;
   }
}
