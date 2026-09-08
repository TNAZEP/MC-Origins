package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class PotionAttackDamage extends Potion {
   protected final double field_188416_a;

   protected PotionAttackDamage(boolean var1, int var2, double var3) {
      super(☃, ☃);
      this.field_188416_a = ☃;
   }

   @Override
   public double func_111183_a(int var1, AttributeModifier var2) {
      return this.field_188416_a * (double)(☃ + 1);
   }
}
