package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

final class IndirectMerger implements IDoubleListMerger {
   private final DoubleArrayList field_197856_a;
   private final IntArrayList field_197857_b;
   private final IntArrayList field_197858_c;

   IndirectMerger(DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      int ☃ = 0;
      int ☃x = 0;
      double ☃xx = Double.NaN;
      int ☃xxx = ☃.size();
      int ☃xxxx = ☃.size();
      int ☃xxxxx = ☃xxx + ☃xxxx;
      this.field_197856_a = new DoubleArrayList(☃xxxxx);
      this.field_197857_b = new IntArrayList(☃xxxxx);
      this.field_197858_c = new IntArrayList(☃xxxxx);

      while(true) {
         boolean ☃xxxxxx = ☃ < ☃xxx;
         boolean ☃xxxxxxx = ☃x < ☃xxxx;
         if (!☃xxxxxx && !☃xxxxxxx) {
            if (this.field_197856_a.isEmpty()) {
               this.field_197856_a.add(Math.min(☃.getDouble(☃xxx - 1), ☃.getDouble(☃xxxx - 1)));
            }

            return;
         }

         boolean ☃xxxxxx = ☃xxxxxx && (!☃xxxxxxx || ☃.getDouble(☃) < ☃.getDouble(☃x) + 1.0E-7);
         double ☃xxxxxxx = ☃xxxxxx ? ☃.getDouble(☃++) : ☃.getDouble(☃x++);
         if ((☃ != 0 && ☃xxxxxx || ☃xxxxxx || ☃) && (☃x != 0 && ☃xxxxxxx || !☃xxxxxx || ☃)) {
            if (!(☃xx > ☃xxxxxxx - 1.0E-7)) {
               this.field_197857_b.add(☃ - 1);
               this.field_197858_c.add(☃x - 1);
               this.field_197856_a.add(☃xxxxxxx);
               ☃xx = ☃xxxxxxx;
            } else if (!this.field_197856_a.isEmpty()) {
               this.field_197857_b.set(this.field_197857_b.size() - 1, ☃ - 1);
               this.field_197858_c.set(this.field_197858_c.size() - 1, ☃x - 1);
            }
         }
      }
   }

   @Override
   public boolean func_197855_a(IDoubleListMerger.Consumer var1) {
      for(int ☃ = 0; ☃ < this.field_197856_a.size() - 1; ++☃) {
         if (!☃.merge(this.field_197857_b.getInt(☃), this.field_197858_c.getInt(☃), ☃)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList func_212435_a() {
      return this.field_197856_a;
   }
}
