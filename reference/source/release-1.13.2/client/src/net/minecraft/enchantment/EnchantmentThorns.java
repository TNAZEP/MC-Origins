package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentThorns extends Enchantment {
   public EnchantmentThorns(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.ARMOR_CHEST, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return 10 + 20 * (☃ - 1);
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
      return ☃.func_77973_b() instanceof ItemArmor ? true : super.func_92089_a(☃);
   }

   @Override
   public void func_151367_b(EntityLivingBase var1, Entity var2, int var3) {
      Random ☃ = ☃.func_70681_au();
      ItemStack ☃x = EnchantmentHelper.func_92099_a(Enchantments.field_92091_k, ☃);
      if (func_92094_a(☃, ☃)) {
         if (☃ != null) {
            ☃.func_70097_a(DamageSource.func_92087_a(☃), (float)func_92095_b(☃, ☃));
         }

         if (!☃x.func_190926_b()) {
            ☃x.func_77972_a(3, ☃);
         }
      } else if (!☃x.func_190926_b()) {
         ☃x.func_77972_a(1, ☃);
      }
   }

   public static boolean func_92094_a(int var0, Random var1) {
      if (☃ <= 0) {
         return false;
      } else {
         return ☃.nextFloat() < 0.15F * (float)☃;
      }
   }

   public static int func_92095_b(int var0, Random var1) {
      return ☃ > 10 ? ☃ - 10 : 1 + ☃.nextInt(4);
   }
}
