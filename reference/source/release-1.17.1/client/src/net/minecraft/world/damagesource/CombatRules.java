package net.minecraft.world.damagesource;

import net.minecraft.util.Mth;

public class CombatRules {
   public static final float MAX_ARMOR = 20.0F;
   public static final float ARMOR_PROTECTION_DIVIDER = 25.0F;
   public static final float BASE_ARMOR_TOUGHNESS = 2.0F;
   public static final float MIN_ARMOR_RATIO = 0.2F;
   private static final int NUM_ARMOR_ITEMS = 4;

   public static float getDamageAfterAbsorb(float var0, float var1, float var2) {
      float â˜ƒ = 2.0F + â˜ƒ / 4.0F;
      float â˜ƒx = Mth.clamp(â˜ƒ - â˜ƒ / â˜ƒ, â˜ƒ * 0.2F, 20.0F);
      return â˜ƒ * (1.0F - â˜ƒx / 25.0F);
   }

   public static float getDamageAfterMagicAbsorb(float var0, float var1) {
      float â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 20.0F);
      return â˜ƒ * (1.0F - â˜ƒ / 25.0F);
   }
}
