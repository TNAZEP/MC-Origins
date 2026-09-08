package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentBindingCurse extends Enchantment {
   public EnchantmentBindingCurse(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.WEARABLE, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 25;
   }

   @Override
   public int func_77317_b(int var1) {
      return 50;
   }

   @Override
   public int func_77325_b() {
      return 1;
   }

   @Override
   public boolean func_185261_e() {
      return true;
   }

   @Override
   public boolean func_190936_d() {
      return true;
   }
}
