package net.minecraft.world.entity.ai.attributes;

import net.minecraft.util.Mth;

public class RangedAttribute extends Attribute {
   private final double minValue;
   private final double maxValue;

   public RangedAttribute(String var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ);
      this.minValue = â˜ƒ;
      this.maxValue = â˜ƒ;
      if (â˜ƒ > â˜ƒ) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      } else if (â˜ƒ < â˜ƒ) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      } else if (â˜ƒ > â˜ƒ) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   @Override
   public double sanitizeValue(double var1) {
      return Mth.clamp(â˜ƒ, this.minValue, this.maxValue);
   }
}
