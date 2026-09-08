package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class SweepingEdgeEnchantment extends Enchantment {
   public SweepingEdgeEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.WEAPON, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 5 + (â˜ƒ - 1) * 9;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 15;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   public static float getSweepingDamageRatio(int var0) {
      return 1.0F - 1.0F / (float)(â˜ƒ + 1);
   }
}
