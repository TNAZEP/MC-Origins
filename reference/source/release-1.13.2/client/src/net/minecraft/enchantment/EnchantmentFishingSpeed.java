package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentFishingSpeed extends Enchantment {
   protected EnchantmentFishingSpeed(Enchantment.Rarity var1, EnumEnchantmentType var2, EntityEquipmentSlot... var3) {
      super(☃, ☃, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 15 + (☃ - 1) * 9;
   }

   @Override
   public int func_77317_b(int var1) {
      return super.func_77321_a(☃) + 50;
   }

   @Override
   public int func_77325_b() {
      return 3;
   }
}
