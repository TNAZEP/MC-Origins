package net.minecraft.world.item.enchantment;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class ProtectionEnchantment extends Enchantment {
   public final ProtectionEnchantment.Type type;

   public ProtectionEnchantment(Enchantment.Rarity var1, ProtectionEnchantment.Type var2, EquipmentSlot... var3) {
      super(â˜ƒ, â˜ƒ == ProtectionEnchantment.Type.FALL ? EnchantmentCategory.ARMOR_FEET : EnchantmentCategory.ARMOR, â˜ƒ);
      this.type = â˜ƒ;
   }

   @Override
   public int getMinCost(int var1) {
      return this.type.getMinCost() + (â˜ƒ - 1) * this.type.getLevelCost();
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + this.type.getLevelCost();
   }

   @Override
   public int getMaxLevel() {
      return 4;
   }

   @Override
   public int getDamageProtection(int var1, DamageSource var2) {
      if (â˜ƒ.isBypassInvul()) {
         return 0;
      } else if (this.type == ProtectionEnchantment.Type.ALL) {
         return â˜ƒ;
      } else if (this.type == ProtectionEnchantment.Type.FIRE && â˜ƒ.isFire()) {
         return â˜ƒ * 2;
      } else if (this.type == ProtectionEnchantment.Type.FALL && â˜ƒ.isFall()) {
         return â˜ƒ * 3;
      } else if (this.type == ProtectionEnchantment.Type.EXPLOSION && â˜ƒ.isExplosion()) {
         return â˜ƒ * 2;
      } else {
         return this.type == ProtectionEnchantment.Type.PROJECTILE && â˜ƒ.isProjectile() ? â˜ƒ * 2 : 0;
      }
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      if (â˜ƒ instanceof ProtectionEnchantment â˜ƒ) {
         if (this.type == â˜ƒ.type) {
            return false;
         } else {
            return this.type == ProtectionEnchantment.Type.FALL || â˜ƒ.type == ProtectionEnchantment.Type.FALL;
         }
      } else {
         return super.checkCompatibility(â˜ƒ);
      }
   }

   public static int getFireAfterDampener(LivingEntity var0, int var1) {
      int â˜ƒ = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, â˜ƒ);
      if (â˜ƒ > 0) {
         â˜ƒ -= Mth.floor((float)â˜ƒ * (float)â˜ƒ * 0.15F);
      }

      return â˜ƒ;
   }

   public static double getExplosionKnockbackAfterDampener(LivingEntity var0, double var1) {
      int â˜ƒ = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, â˜ƒ);
      if (â˜ƒ > 0) {
         â˜ƒ -= (double)Mth.floor(â˜ƒ * (double)((float)â˜ƒ * 0.15F));
      }

      return â˜ƒ;
   }

   public static enum Type {
      ALL(1, 11),
      FIRE(10, 8),
      FALL(5, 6),
      EXPLOSION(5, 8),
      PROJECTILE(3, 6);

      private final int minCost;
      private final int levelCost;

      private Type(int var3, int var4) {
         this.minCost = â˜ƒ;
         this.levelCost = â˜ƒ;
      }

      public int getMinCost() {
         return this.minCost;
      }

      public int getLevelCost() {
         return this.levelCost;
      }
   }
}
