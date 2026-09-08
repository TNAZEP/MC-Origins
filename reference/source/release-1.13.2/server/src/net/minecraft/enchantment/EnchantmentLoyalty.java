package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentLoyalty extends Enchantment {
   public EnchantmentLoyalty(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.TRIDENT, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 5 + ☃ * 7;
   }

   @Override
   public int func_77317_b(int var1) {
      return 50;
   }

   @Override
   public int func_77325_b() {
      return 3;
   }

   @Override
   public boolean func_77326_a(Enchantment var1) {
      return super.func_77326_a(☃);
   }
}
