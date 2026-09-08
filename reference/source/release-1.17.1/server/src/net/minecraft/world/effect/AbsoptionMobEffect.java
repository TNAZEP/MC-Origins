package net.minecraft.world.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class AbsoptionMobEffect extends MobEffect {
   protected AbsoptionMobEffect(MobEffectCategory var1, int var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void removeAttributeModifiers(LivingEntity var1, AttributeMap var2, int var3) {
      â˜ƒ.setAbsorptionAmount(â˜ƒ.getAbsorptionAmount() - (float)(4 * (â˜ƒ + 1)));
      super.removeAttributeModifiers(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void addAttributeModifiers(LivingEntity var1, AttributeMap var2, int var3) {
      â˜ƒ.setAbsorptionAmount(â˜ƒ.getAbsorptionAmount() + (float)(4 * (â˜ƒ + 1)));
      super.addAttributeModifiers(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
