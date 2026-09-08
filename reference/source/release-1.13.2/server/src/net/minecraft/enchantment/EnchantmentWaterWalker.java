package net.minecraft.enchantment;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentWaterWalker extends Enchantment {
   public EnchantmentWaterWalker(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.ARMOR_FEET, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return ☃ * 10;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 15;
   }

   @Override
   public int func_77325_b() {
      return 3;
   }

   @Override
   public boolean func_77326_a(Enchantment var1) {
      return super.func_77326_a(☃) && ☃ != Enchantments.field_185301_j;
   }
}
