package net.minecraft.world.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class HealthBoostMobEffect extends MobEffect {
   public HealthBoostMobEffect(MobEffectCategory var1, int var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void removeAttributeModifiers(LivingEntity var1, AttributeMap var2, int var3) {
      super.removeAttributeModifiers(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.getHealth() > â˜ƒ.getMaxHealth()) {
         â˜ƒ.setHealth(â˜ƒ.getMaxHealth());
      }
   }
}
