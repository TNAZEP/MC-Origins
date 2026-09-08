package net.minecraft.enchantment;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentRiptide extends Enchantment {
   public EnchantmentRiptide(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.TRIDENT, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 10 + ☃ * 7;
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
      return super.func_77326_a(☃) && ☃ != Enchantments.field_203193_C && ☃ != Enchantments.field_203196_F;
   }
}
