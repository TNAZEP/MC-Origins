package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentOxygen extends Enchantment {
   public EnchantmentOxygen(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.ARMOR_HEAD, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 10 * ☃;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 30;
   }

   @Override
   public int func_77325_b() {
      return 3;
   }
}
