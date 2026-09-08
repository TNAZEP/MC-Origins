package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArrowInfinite extends Enchantment {
   public EnchantmentArrowInfinite(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.BOW, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 20;
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
   public boolean func_77326_a(Enchantment var1) {
      return ☃ instanceof EnchantmentMending ? false : super.func_77326_a(☃);
   }
}
