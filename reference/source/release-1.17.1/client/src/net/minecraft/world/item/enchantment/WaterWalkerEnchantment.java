package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class WaterWalkerEnchantment extends Enchantment {
   public WaterWalkerEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.ARMOR_FEET, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return â˜ƒ * 10;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 15;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      return super.checkCompatibility(â˜ƒ) && â˜ƒ != Enchantments.FROST_WALKER;
   }
}
