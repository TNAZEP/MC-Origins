package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class ArrowPiercingEnchantment extends Enchantment {
   public ArrowPiercingEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.CROSSBOW, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 1 + (â˜ƒ - 1) * 10;
   }

   @Override
   public int getMaxCost(int var1) {
      return 50;
   }

   @Override
   public int getMaxLevel() {
      return 4;
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      return super.checkCompatibility(â˜ƒ) && â˜ƒ != Enchantments.MULTISHOT;
   }
}
