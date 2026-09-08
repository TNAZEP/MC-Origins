package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class UntouchingEnchantment extends Enchantment {
   protected UntouchingEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.DIGGER, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 15;
   }

   @Override
   public int getMaxCost(int var1) {
      return super.getMinCost(â˜ƒ) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 1;
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      return super.checkCompatibility(â˜ƒ) && â˜ƒ != Enchantments.BLOCK_FORTUNE;
   }
}
