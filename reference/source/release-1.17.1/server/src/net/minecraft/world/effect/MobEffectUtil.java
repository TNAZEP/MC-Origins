package net.minecraft.world.effect;

import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;

public final class MobEffectUtil {
   public static String formatDuration(MobEffectInstance var0, float var1) {
      if (â˜ƒ.isNoCounter()) {
         return "**:**";
      } else {
         int â˜ƒ = Mth.floor((float)â˜ƒ.getDuration() * â˜ƒ);
         return StringUtil.formatTickDuration(â˜ƒ);
      }
   }

   public static boolean hasDigSpeed(LivingEntity var0) {
      return â˜ƒ.hasEffect(MobEffects.DIG_SPEED) || â˜ƒ.hasEffect(MobEffects.CONDUIT_POWER);
   }

   public static int getDigSpeedAmplification(LivingEntity var0) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;
      if (â˜ƒ.hasEffect(MobEffects.DIG_SPEED)) {
         â˜ƒ = â˜ƒ.getEffect(MobEffects.DIG_SPEED).getAmplifier();
      }

      if (â˜ƒ.hasEffect(MobEffects.CONDUIT_POWER)) {
         â˜ƒx = â˜ƒ.getEffect(MobEffects.CONDUIT_POWER).getAmplifier();
      }

      return Math.max(â˜ƒ, â˜ƒx);
   }

   public static boolean hasWaterBreathing(LivingEntity var0) {
      return â˜ƒ.hasEffect(MobEffects.WATER_BREATHING) || â˜ƒ.hasEffect(MobEffects.CONDUIT_POWER);
   }
}
