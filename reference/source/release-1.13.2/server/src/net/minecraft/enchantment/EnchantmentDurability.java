package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class EnchantmentDurability extends Enchantment {
   protected EnchantmentDurability(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.BREAKABLE, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 5 + (☃ - 1) * 8;
   }

   @Override
   public int func_77317_b(int var1) {
      return super.func_77321_a(☃) + 50;
   }

   @Override
   public int func_77325_b() {
      return 3;
   }

   @Override
   public boolean func_92089_a(ItemStack var1) {
      return ☃.func_77984_f() ? true : super.func_92089_a(☃);
   }

   public static boolean func_92097_a(ItemStack var0, int var1, Random var2) {
      if (☃.func_77973_b() instanceof ItemArmor && ☃.nextFloat() < 0.6F) {
         return false;
      } else {
         return ☃.nextInt(☃ + 1) > 0;
      }
   }
}
