package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowKnockback extends Enchantment {
   public EnchantmentArrowKnockback(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.BOW, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 12 + (☃ - 1) * 20;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 25;
   }

   @Override
   public int func_77325_b() {
      return 2;
   }
}
