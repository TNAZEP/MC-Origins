package net.minecraft.enchantment;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentDamage extends Enchantment {
   private static final String[] field_77359_A = new String[]{"all", "undead", "arthropods"};
   private static final int[] field_77360_B = new int[]{1, 5, 5};
   private static final int[] field_77362_C = new int[]{11, 8, 8};
   private static final int[] field_77358_D = new int[]{20, 20, 20};
   public final int field_77361_a;

   public EnchantmentDamage(Enchantment.Rarity var1, int var2, EntityEquipmentSlot... var3) {
      super(☃, EnumEnchantmentType.WEAPON, ☃);
      this.field_77361_a = ☃;
   }

   @Override
   public int func_77321_a(int var1) {
      return field_77360_B[this.field_77361_a] + (☃ - 1) * field_77362_C[this.field_77361_a];
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + field_77358_D[this.field_77361_a];
   }

   @Override
   public int func_77325_b() {
      return 5;
   }

   @Override
   public float func_152376_a(int var1, CreatureAttribute var2) {
      if (this.field_77361_a == 0) {
         return 1.0F + (float)Math.max(0, ☃ - 1) * 0.5F;
      } else if (this.field_77361_a == 1 && ☃ == CreatureAttribute.UNDEAD) {
         return (float)☃ * 2.5F;
      } else {
         return this.field_77361_a == 2 && ☃ == CreatureAttribute.ARTHROPOD ? (float)☃ * 2.5F : 0.0F;
      }
   }

   @Override
   public boolean func_77326_a(Enchantment var1) {
      return !(☃ instanceof EnchantmentDamage);
   }

   @Override
   public boolean func_92089_a(ItemStack var1) {
      return ☃.func_77973_b() instanceof ItemAxe ? true : super.func_92089_a(☃);
   }

   @Override
   public void func_151368_a(EntityLivingBase var1, Entity var2, int var3) {
      if (☃ instanceof EntityLivingBase) {
         EntityLivingBase ☃ = (EntityLivingBase)☃;
         if (this.field_77361_a == 2 && ☃.func_70668_bt() == CreatureAttribute.ARTHROPOD) {
            int ☃x = 20 + ☃.func_70681_au().nextInt(10 * ☃);
            ☃.func_195064_c(new PotionEffect(MobEffects.field_76421_d, ☃x, 3));
         }
      }
   }
}
