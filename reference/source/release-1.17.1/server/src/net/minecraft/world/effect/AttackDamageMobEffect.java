package net.minecraft.world.effect;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttackDamageMobEffect extends MobEffect {
   protected final double multiplier;

   protected AttackDamageMobEffect(MobEffectCategory var1, int var2, double var3) {
      super(â˜ƒ, â˜ƒ);
      this.multiplier = â˜ƒ;
   }

   @Override
   public double getAttributeModifierValue(int var1, AttributeModifier var2) {
      return this.multiplier * (double)(â˜ƒ + 1);
   }
}
