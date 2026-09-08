package net.minecraft.world.item.enchantment;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;

public class DamageEnchantment extends Enchantment {
   public static final int ALL = 0;
   public static final int UNDEAD = 1;
   public static final int ARTHROPODS = 2;
   private static final String[] NAMES = new String[]{"all", "undead", "arthropods"};
   private static final int[] MIN_COST = new int[]{1, 5, 5};
   private static final int[] LEVEL_COST = new int[]{11, 8, 8};
   private static final int[] LEVEL_COST_SPAN = new int[]{20, 20, 20};
   public final int type;

   public DamageEnchantment(Enchantment.Rarity var1, int var2, EquipmentSlot... var3) {
      super(â˜ƒ, EnchantmentCategory.WEAPON, â˜ƒ);
      this.type = â˜ƒ;
   }

   @Override
   public int getMinCost(int var1) {
      return MIN_COST[this.type] + (â˜ƒ - 1) * LEVEL_COST[this.type];
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + LEVEL_COST_SPAN[this.type];
   }

   @Override
   public int getMaxLevel() {
      return 5;
   }

   @Override
   public float getDamageBonus(int var1, MobType var2) {
      if (this.type == 0) {
         return 1.0F + (float)Math.max(0, â˜ƒ - 1) * 0.5F;
      } else if (this.type == 1 && â˜ƒ == MobType.UNDEAD) {
         return (float)â˜ƒ * 2.5F;
      } else {
         return this.type == 2 && â˜ƒ == MobType.ARTHROPOD ? (float)â˜ƒ * 2.5F : 0.0F;
      }
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      return !(â˜ƒ instanceof DamageEnchantment);
   }

   @Override
   public boolean canEnchant(ItemStack var1) {
      return â˜ƒ.getItem() instanceof AxeItem ? true : super.canEnchant(â˜ƒ);
   }

   @Override
   public void doPostAttack(LivingEntity var1, Entity var2, int var3) {
      if (â˜ƒ instanceof LivingEntity â˜ƒ && this.type == 2 && â˜ƒ > 0 && â˜ƒ.getMobType() == MobType.ARTHROPOD) {
         int â˜ƒx = 20 + â˜ƒ.getRandom().nextInt(10 * â˜ƒ);
         â˜ƒ.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, â˜ƒx, 3));
      }
   }
}
