package net.minecraft.world.item.enchantment;

import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class ThornsEnchantment extends Enchantment {
   private static final float CHANCE_PER_LEVEL = 0.15F;

   public ThornsEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.ARMOR_CHEST, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return 10 + 20 * (â˜ƒ - 1);
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
      return â˜ƒ.getItem() instanceof ArmorItem ? true : super.canEnchant(â˜ƒ);
   }

   @Override
   public void doPostHurt(LivingEntity var1, Entity var2, int var3) {
      Random â˜ƒ = â˜ƒ.getRandom();
      Entry<EquipmentSlot, ItemStack> â˜ƒx = EnchantmentHelper.getRandomItemWith(Enchantments.THORNS, â˜ƒ);
      if (shouldHit(â˜ƒ, â˜ƒ)) {
         if (â˜ƒ != null) {
            â˜ƒ.hurt(DamageSource.thorns(â˜ƒ), (float)getDamage(â˜ƒ, â˜ƒ));
         }

         if (â˜ƒx != null) {
            ((ItemStack)â˜ƒx.getValue()).hurtAndBreak(2, â˜ƒ, var1x -> var1x.broadcastBreakEvent((EquipmentSlot)â˜ƒ.getKey()));
         }
      }
   }

   public static boolean shouldHit(int var0, Random var1) {
      if (â˜ƒ <= 0) {
         return false;
      } else {
         return â˜ƒ.nextFloat() < 0.15F * (float)â˜ƒ;
      }
   }

   public static int getDamage(int var0, Random var1) {
      return â˜ƒ > 10 ? â˜ƒ - 10 : 1 + â˜ƒ.nextInt(4);
   }
}
