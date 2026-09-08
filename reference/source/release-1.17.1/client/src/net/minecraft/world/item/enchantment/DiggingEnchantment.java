package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiggingEnchantment extends Enchantment {
   protected DiggingEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.DIGGER, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 1 + 10 * (â˜ƒ - 1);
   }

   @Override
   public int getMaxCost(int var1) {
      return super.getMinCost(â˜ƒ) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 5;
   }

   @Override
   public boolean canEnchant(ItemStack var1) {
      return â˜ƒ.is(Items.SHEARS) ? true : super.canEnchant(â˜ƒ);
   }
}
