package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentMending extends Enchantment {
   public EnchantmentMending(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.BREAKABLE, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return ☃ * 25;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 50;
   }

   @Override
   public boolean func_185261_e() {
      return true;
   }

   @Override
   public int func_77325_b() {
      return 1;
   }
}
