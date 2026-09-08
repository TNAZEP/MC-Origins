package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

public class SimpleDoubleMerger implements IDoubleListMerger {
   private final DoubleList field_210220_a;

   public SimpleDoubleMerger(DoubleList var1) {
      this.field_210220_a = ☃;
   }

   @Override
   public boolean func_197855_a(IDoubleListMerger.Consumer var1) {
      for(int ☃ = 0; ☃ <= this.field_210220_a.size(); ++☃) {
         if (!☃.merge(☃, ☃, ☃)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList func_212435_a() {
      return this.field_210220_a;
   }
}
