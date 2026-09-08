package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class NonOverlappingMerger extends AbstractDoubleList implements IDoubleListMerger {
   private final DoubleList field_199638_a;
   private final DoubleList field_199639_b;
   private final boolean field_199640_c;

   public NonOverlappingMerger(DoubleList var1, DoubleList var2, boolean var3) {
      this.field_199638_a = ☃;
      this.field_199639_b = ☃;
      this.field_199640_c = ☃;
   }

   public int size() {
      return this.field_199638_a.size() + this.field_199639_b.size();
   }

   @Override
   public boolean func_197855_a(IDoubleListMerger.Consumer var1) {
      return this.field_199640_c ? this.func_199637_b((var1x, var2, var3) -> ☃.merge(var2, var1x, var3)) : this.func_199637_b(☃);
   }

   private boolean func_199637_b(IDoubleListMerger.Consumer var1) {
      int ☃ = this.field_199638_a.size() - 1;

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         if (!☃.merge(☃x, -1, ☃x)) {
            return false;
         }
      }

      if (!☃.merge(☃, -1, ☃)) {
         return false;
      } else {
         for(int ☃x = 0; ☃x < this.field_199639_b.size(); ++☃x) {
            if (!☃.merge(☃, ☃x, ☃ + 1 + ☃x)) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public double getDouble(int var1) {
      return ☃ < this.field_199638_a.size() ? this.field_199638_a.getDouble(☃) : this.field_199639_b.getDouble(☃ - this.field_199638_a.size());
   }

   @Override
   public DoubleList func_212435_a() {
      return this;
   }
}
