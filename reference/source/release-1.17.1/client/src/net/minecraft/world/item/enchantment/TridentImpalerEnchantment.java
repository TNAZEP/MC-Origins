package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;

public class TridentImpalerEnchantment extends Enchantment {
   public TridentImpalerEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.TRIDENT, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 1 + (â˜ƒ - 1) * 8;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 20;
   }

   @Override
   public int getMaxLevel() {
      return 5;
   }

   @Override
   public float getDamageBonus(int var1, MobType var2) {
      return â˜ƒ == MobType.WATER ? (float)â˜ƒ * 2.5F : 0.0F;
   }
}
