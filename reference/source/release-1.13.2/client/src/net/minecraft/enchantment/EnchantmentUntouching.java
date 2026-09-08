package net.minecraft.enchantment;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentUntouching extends Enchantment {
   protected EnchantmentUntouching(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.DIGGER, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 15;
   }

   @Override
   public int func_77317_b(int var1) {
      return super.func_77321_a(☃) + 50;
   }

   @Override
   public int func_77325_b() {
      return 1;
   }

   @Override
   public boolean func_77326_a(Enchantment var1) {
      return super.func_77326_a(☃) && ☃ != Enchantments.field_185308_t;
   }
}
