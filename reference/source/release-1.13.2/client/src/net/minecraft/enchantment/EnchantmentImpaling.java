package net.minecraft.enchantment;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentImpaling extends Enchantment {
   public EnchantmentImpaling(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.TRIDENT, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 1 + (☃ - 1) * 8;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 20;
   }

   @Override
   public int func_77325_b() {
      return 5;
   }

   @Override
   public float func_152376_a(int var1, CreatureAttribute var2) {
      return ☃ == CreatureAttribute.field_203100_e ? (float)☃ * 2.5F : 0.0F;
   }
}
