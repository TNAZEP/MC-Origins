package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class OffsetDoubleList extends AbstractDoubleList {
   private final DoubleList field_197888_a;
   private final double field_197889_b;

   public OffsetDoubleList(DoubleList var1, double var2) {
      this.field_197888_a = ☃;
      this.field_197889_b = ☃;
   }

   @Override
   public double getDouble(int var1) {
      return this.field_197888_a.getDouble(☃) + this.field_197889_b;
   }

   public int size() {
      return this.field_197888_a.size();
   }
}
