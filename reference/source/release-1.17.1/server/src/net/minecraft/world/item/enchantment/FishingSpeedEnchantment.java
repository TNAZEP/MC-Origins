package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class FishingSpeedEnchantment extends Enchantment {
   protected FishingSpeedEnchantment(Enchantment.Rarity var1, EnchantmentCategory var2, EquipmentSlot... var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 15 + (â˜ƒ - 1) * 9;
   }

   @Override
   public int getMaxCost(int var1) {
      return super.getMinCost(â˜ƒ) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}
