package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;

public final class PotionUtil {
   public static String func_188410_a(PotionEffect var0, float var1) {
      if (☃.func_100011_g()) {
         return "**:**";
      } else {
         int ☃ = MathHelper.func_76141_d((float)☃.func_76459_b() * ☃);
         return StringUtils.func_76337_a(☃);
      }
   }

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
