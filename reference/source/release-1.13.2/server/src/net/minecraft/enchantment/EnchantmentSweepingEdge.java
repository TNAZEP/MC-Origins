package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentSweepingEdge extends Enchantment {
   public EnchantmentSweepingEdge(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.WEAPON, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 5 + (☃ - 1) * 9;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 15;
   }

   @Override
   public int func_77325_b() {
      return 3;
   }

   public static float func_191526_e(int var0) {
      return 1.0F - 1.0F / (float)(☃ + 1);
   }
}
