package net.minecraft.world.item.enchantment;

import java.util.Random;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class DigDurabilityEnchantment extends Enchantment {
   protected DigDurabilityEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.BREAKABLE, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 5 + (â˜ƒ - 1) * 8;
   }

   @Override
   public int getMaxCost(int var1) {
      return super.getMinCost(â˜ƒ) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean canEnchant(ItemStack var1) {
      return â˜ƒ.isDamageableItem() ? true : super.canEnchant(â˜ƒ);
   }

   public static boolean shouldIgnoreDurabilityDrop(ItemStack var0, int var1, Random var2) {
      if (â˜ƒ.getItem() instanceof ArmorItem && â˜ƒ.nextFloat() < 0.6F) {
         return false;
      } else {
         return â˜ƒ.nextInt(â˜ƒ + 1) > 0;
      }
   }
}
