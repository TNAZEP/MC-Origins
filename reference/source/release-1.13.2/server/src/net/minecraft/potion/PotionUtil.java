package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;

public final class PotionUtil {
   public static boolean func_205135_a(EntityLivingBase var0) {
      return ☃.func_70644_a(MobEffects.field_76422_e) || ☃.func_70644_a(MobEffects.field_205136_C);
   }

   public static int func_205134_b(EntityLivingBase var0) {
      int ☃ = 0;
      int ☃x = 0;
      if (☃.func_70644_a(MobEffects.field_76422_e)) {
         ☃ = ☃.func_70660_b(MobEffects.field_76422_e).func_76458_c();
      }

      if (☃.func_70644_a(MobEffects.field_205136_C)) {
         ☃x = ☃.func_70660_b(MobEffects.field_205136_C).func_76458_c();
      }

      return Math.max(☃, ☃x);
   }

   public static boolean func_205133_c(EntityLivingBase var0) {
      return ☃.func_70644_a(MobEffects.field_76427_o) || ☃.func_70644_a(MobEffects.field_205136_C);
   }
}
