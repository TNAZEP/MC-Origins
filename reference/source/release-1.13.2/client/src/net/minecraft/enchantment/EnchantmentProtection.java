package net.minecraft.enchantment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class EnchantmentProtection extends Enchantment {
   public final EnchantmentProtection.Type field_77356_a;

   public EnchantmentProtection(Enchantment.Rarity var1, EnchantmentProtection.Type var2, EntityEquipmentSlot... var3) {
      super(☃, EnumEnchantmentType.ARMOR, ☃);
      this.field_77356_a = ☃;
      if (☃ == EnchantmentProtection.Type.FALL) {
         this.field_77351_y = EnumEnchantmentType.ARMOR_FEET;
      }
   }

   @Override
   public int func_77321_a(int var1) {
      return this.field_77356_a.func_185316_b() + (☃ - 1) * this.field_77356_a.func_185315_c();
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + this.field_77356_a.func_185315_c();
   }

   @Override
   public int func_77325_b() {
      return 4;
   }

   @Override
   public int func_77318_a(int var1, DamageSource var2) {
      if (☃.func_76357_e()) {
         return 0;
      } else if (this.field_77356_a == EnchantmentProtection.Type.ALL) {
         return ☃;
      } else if (this.field_77356_a == EnchantmentProtection.Type.FIRE && ☃.func_76347_k()) {
         return ☃ * 2;
      } else if (this.field_77356_a == EnchantmentProtection.Type.FALL && ☃ == DamageSource.field_76379_h) {
         return ☃ * 3;
      } else if (this.field_77356_a == EnchantmentProtection.Type.EXPLOSION && ☃.func_94541_c()) {
         return ☃ * 2;
      } else {
         return this.field_77356_a == EnchantmentProtection.Type.PROJECTILE && ☃.func_76352_a() ? ☃ * 2 : 0;
      }
   }

   @Override
   public boolean func_77326_a(Enchantment var1) {
      if (☃ instanceof EnchantmentProtection) {
         EnchantmentProtection ☃ = (EnchantmentProtection)☃;
         if (this.field_77356_a == ☃.field_77356_a) {
            return false;
         } else {
            return this.field_77356_a == EnchantmentProtection.Type.FALL || ☃.field_77356_a == EnchantmentProtection.Type.FALL;
         }
      } else {
         return super.func_77326_a(☃);
      }
   }

   public static int func_92093_a(EntityLivingBase var0, int var1) {
      int ☃ = EnchantmentHelper.func_185284_a(Enchantments.field_77329_d, ☃);
      if (☃ > 0) {
         ☃ -= MathHelper.func_76141_d((float)☃ * (float)☃ * 0.15F);
      }

      return ☃;
   }

   public static double func_92092_a(EntityLivingBase var0, double var1) {
      int ☃ = EnchantmentHelper.func_185284_a(Enchantments.field_185297_d, ☃);
      if (☃ > 0) {
         ☃ -= (double)MathHelper.func_76128_c(☃ * (double)((float)☃ * 0.15F));
      }

      return ☃;
   }

   public static enum Type {
      ALL("all", 1, 11),
      FIRE("fire", 10, 8),
      FALL("fall", 5, 6),
      EXPLOSION("explosion", 5, 8),
      PROJECTILE("projectile", 3, 6);

      private final String field_185322_f;
      private final int field_185323_g;
      private final int field_185324_h;

      private Type(String var3, int var4, int var5) {
         this.field_185322_f = ☃;
         this.field_185323_g = ☃;
         this.field_185324_h = ☃;
      }

      public int func_185316_b() {
         return this.field_185323_g;
      }

      public int func_185315_c() {
         return this.field_185324_h;
      }
   }
}
