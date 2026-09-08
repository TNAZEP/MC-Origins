package net.minecraft.entity.ai.attributes;

import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;

public class RangedAttribute extends BaseAttribute {
   private final double field_111120_a;
   private final double field_111118_b;
   private String field_111119_c;

   public RangedAttribute(@Nullable IAttribute var1, String var2, double var3, double var5, double var7) {
      super(☃, ☃, ☃);
      this.field_111120_a = ☃;
      this.field_111118_b = ☃;
      if (☃ > ☃) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      } else if (☃ < ☃) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      } else if (☃ > ☃) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public RangedAttribute func_111117_a(String var1) {
      this.field_111119_c = ☃;
      return this;
   }

   public String func_111116_f() {
      return this.field_111119_c;
   }

   @Override
   public double func_111109_a(double var1) {
      return MathHelper.func_151237_a(☃, this.field_111120_a, this.field_111118_b);
   }
}
